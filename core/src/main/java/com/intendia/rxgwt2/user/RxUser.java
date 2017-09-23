package com.intendia.rxgwt2.user;

import static io.reactivex.Observable.defer;
import static io.reactivex.Observable.just;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SetSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Bindings for the gwt-user package. */
public class RxUser {

    public static <T> Observable<Set<T>> bindSetSelectionChange(SetSelectionModel<T> source) {
        return RxHandlers.selectionChange(source).map(e -> source.getSelectedSet())
                .startWith(defer(() -> just(source.getSelectedSet())));
    }

    public static <T> Observable<T> bindSingleSelectionChange(SingleSelectionModel<T> source) {
        return RxHandlers.selectionChange(source).map(e -> source.getSelectedObject())
                .startWith(defer(() -> just(source.getSelectedObject())));
    }

    /** An observable that start with the source value and notify source value changes. */
    public static <T, V extends HasValueChangeHandlers<T> & TakesValue<T>> Observable<T> bindValueChange(V source) {
        return bindValueChange(source, TakesValue::getValue);
    }

    /** An observable that start with the source value and notify source value changes. */
    public static <T, V extends HasValueChangeHandlers<T>> Observable<T> bindValueChange(V source, Function<V, T> get) {
        return RxHandlers.valueChange(source).map(ValueChangeEvent::getValue)
                .startWith(defer(() -> just(get.apply(source))));
    }

    public static Single<Response> fromRequest(RequestBuilder requestBuilder) {
        //noinspection Convert2Lambda
        return Single.create(new SingleOnSubscribe<Response>() {
            @Override public void subscribe(SingleEmitter<Response> e) throws Exception {
                try {
                    requestBuilder.setCallback(new RequestCallback() {
                        @Override public void onResponseReceived(Request req, Response res) { e.onSuccess(res); }
                        @Override public void onError(Request req, Throwable ex) { e.onError(ex); }
                    });
                    Request request = requestBuilder.send();
                    e.setCancellable(request::cancel);
                } catch (RequestException ex) {
                    e.onError(ex);
                }
            }
        });
    }

    public static Single<Response> get(String url) {
        return fromRequest(new RequestBuilder(RequestBuilder.GET, url));
    }

    public static Observable<KeyDownEvent> keyDown(Widget source, int keyCode) {
        return RxEvents.keyDown(source).filter(e -> keyCode == e.getNativeKeyCode());
    }

    public static Observable<KeyPressEvent> keyPress(Widget source, char charCode) {
        return RxEvents.keyPress(source).filter(e -> charCode == e.getCharCode());
    }

    public static <T> ObservableTransformer<T, T> logInfo(Logger log, Function<? super T, String> msg) {
        if (!log.isLoggable(Level.INFO)) return o -> o;
        else return o -> o.doOnNext(n -> log.info(msg.apply(n)));
    }

    public static void register(ObservableEmitter<?> s, HandlerRegistration handlerRegistration) {
        s.setCancellable(handlerRegistration::removeHandler);
    }

    public static Observable<CloseEvent<Window>> windowClose() {
        return Observable.create(s -> register(s, Window.addCloseHandler(s::onNext)));
    }

    public static Observable<Window.ClosingEvent> windowClosing() {
        return Observable.create(s -> register(s, Window.addWindowClosingHandler(s::onNext)));
    }

    public static Observable<ResizeEvent> windowResize() {
        return Observable.create(s -> register(s, Window.addResizeHandler(s::onNext)));
    }

    public static Observable<Window.ScrollEvent> windowScroll() {
        return Observable.create(s -> register(s, Window.addWindowScrollHandler(s::onNext)));
    }
}
