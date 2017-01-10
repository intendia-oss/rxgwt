package com.intendia.rxgwt.client;

import static java.lang.Math.min;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static rx.Observable.defer;
import static rx.Observable.error;
import static rx.Observable.just;
import static rx.Observable.timer;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.Scheduler;
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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SetSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public class RxGwt {
    public static long MAX_RETRY_TIME = MINUTES.toSeconds(1);

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

    public static <T> Observable.Operator<List<T>, T> bufferFinally() {
        return bufferFinally(Scheduler.get());
    }

    public static <T> Observable.Operator<List<T>, T> bufferFinally(Scheduler scheduler) {
        return new OperatorBufferFinally<>(scheduler);
    }

    public static <T> Observable.Operator<T, T> debounce(long windowDuration, TimeUnit unit) {
        return new OperatorDebounceTimer<>(windowDuration, unit);
    }

    public static <T> Observable.Operator<T, T> debounceFinally() {
        return new OperatorDebounceFinally<>(Scheduler.get());
    }

    public static <T> Observable.Operator<T, T> debounceFinally(Scheduler scheduler) {
        return new OperatorDebounceFinally<>(scheduler);
    }

    public static Single<Response> fromRequest(RequestBuilder requestBuilder) {
        //noinspection Convert2Lambda
        return Single.create(new Single.OnSubscribe<Response>() {
            @Override public void call(SingleSubscriber<? super Response> s) {
                try {
                    requestBuilder.setCallback(new RequestCallback() {
                        @Override public void onResponseReceived(Request req, Response res) { s.onSuccess(res); }
                        @Override public void onError(Request req, Throwable e) { s.onError(e); }
                    });
                    Request request = requestBuilder.send();
                    s.add(Subscriptions.create(request::cancel));
                } catch (RequestException e) {
                    s.onError(e);
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

    public static <T> Observable.Transformer<T, T> logInfo(Logger log, Func1<? super T, String> msg) {
        if (!log.isLoggable(Level.INFO)) return o -> o;
        else return o -> o.doOnNext(n -> log.info(msg.call(n)));
    }

    public static void register(Subscriber s, HandlerRegistration handlerRegistration) {
        s.add(Subscriptions.create(handlerRegistration::removeHandler));
    }

    public static <T> Observable.Transformer<T, T> retryDelay(Logger log, Level level, String msg) {
        return retryDelay(a -> log.log(level, msg + " (" + a.idx + " attempt)", a.err));
    }

    public static <T> Observable.Transformer<T, T> retryDelay(Action1<Attempt> onAttempt) {
        return retryDelay(onAttempt, Integer.MAX_VALUE);
    }

    public static <T> Observable.Transformer<T, T> retryDelay(Action1<Attempt> onAttempt, int maxRetry) {
        return o -> o.retryWhen(attempts -> attempts
                .zipWith(Observable.range(1, maxRetry), (err, i) -> new Attempt(i, err))
                .flatMap((Attempt x) -> {
                    if (x.idx > maxRetry) return error(x.err);
                    onAttempt.call(x);
                    return timer(min(x.idx * x.idx, MAX_RETRY_TIME), SECONDS);
                }));
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

    public static class Attempt {
        public final int idx;
        public final Throwable err;
        public Attempt(int idx, Throwable err) {
            this.idx = idx;
            this.err = err;
        }
    }

    private static class OperatorDebounceTimer<T> implements Observable.Operator<T, T> {
        private final int timeInMilliseconds;

        private OperatorDebounceTimer(long windowDuration, TimeUnit unit) {
            this.timeInMilliseconds = (int) unit.toMillis(windowDuration);
        }

        @Override public Subscriber<? super T> call(final Subscriber<? super T> child) {
            return new Subscriber<T>(child) {
                private double lastOnNext = 0;
                private @Nullable T last = null;
                private Timer timer = new Timer() {
                    @Override public void run() {
                        if (last != null) {
                            child.onNext(last);
                            last = null;
                        }
                    }
                };

                @Override public void onNext(T v) {
                    double now = JsDate.now();
                    if (lastOnNext == 0 || now - lastOnNext >= timeInMilliseconds) {
                        lastOnNext = now;
                        last = null;
                        child.onNext(v);
                    } else {
                        if (last == null) {
                            // wait till timer fires, this is faster than re-schedule on each element
                            timer.schedule(timeInMilliseconds);
                        }
                        last = v;
                    }
                }

                @Override public void onCompleted() {
                    if (last != null) {
                        child.onNext(last);
                    }
                    child.onCompleted();
                }

                @Override public void onError(Throwable e) { child.onError(e); }
            };
        }
    }

    private static class OperatorDebounceFinally<T> implements Observable.Operator<T, T> {
        private final Scheduler scheduler;

        private OperatorDebounceFinally(Scheduler scheduler) { this.scheduler = scheduler; }

        @Override public Subscriber<? super T> call(Subscriber<? super T> child) {
            return new Subscriber<T>(child) {
                private boolean hasNext = false;
                private boolean hasScheduled = false;
                private boolean backpressure = false;
                private long pending = 0L;
                private @Nullable T next = null;

                @Override public void onStart() {
                    setProducer(n -> {
                        if (!(n > 0)) throw new IllegalArgumentException("expected n > 0 but was " + n);
                        backpressure = n != Long.MAX_VALUE;
                        if (backpressure) pending += n;
                        tick();
                    });
                }

                private boolean hasRequested() { return !backpressure || pending > 0; }

                @Override public void onNext(T v) { next = v; hasNext = true; tick(); }

                @Override public void onCompleted() { emit(); child.onCompleted(); }

                @Override public void onError(Throwable e) { child.onError(e); }
                private void tick() {
                    if (!hasScheduled && hasNext && hasRequested()) {
                        hasScheduled = true;
                        scheduler.scheduleFinally(() -> {
                            if (isUnsubscribed()) return;
                            hasScheduled = false;
                            emit();
                        });
                    }
                }

                private void emit() {
                    if (hasNext && hasRequested()) {
                        T next = this.next;
                        this.next = null;
                        hasNext = false;
                        if (backpressure) pending--;
                        child.onNext(next);
                    }
                }
            };
        }
    }

    /**
     * This operation takes values from the specified {@link Observable} source and stores them in a buffer.
     * On "finally" (before GWT-generated code returns control to the browser's event loop) the buffer is emitted and
     * replaced with a new buffer. When the source {@link Observable} completes or produces an error, the current
     * buffer is emitted, and the event is propagated to all subscribed {@link Subscriber}s.
     *
     * @param <T> the buffered value type
     * @see Scheduler#scheduleFinally(Scheduler.RepeatingCommand)
     */
    private static class OperatorBufferFinally<T> implements Observable.Operator<List<T>, T> {
        private final Scheduler scheduler;

        private OperatorBufferFinally(Scheduler scheduler) { this.scheduler = scheduler; }

        @Override public Subscriber<? super T> call(final Subscriber<? super List<T>> child) {
            return new Subscriber<T>(child) {
                private @Nullable List<T> buffer = null;

                @Override public void onNext(T v) {
                    if (buffer == null) {
                        buffer = new ArrayList<>();
                        scheduler.scheduleFinally(() -> {
                            if (!isUnsubscribed()) child.onNext(buffer);
                            buffer = null;
                            return false;
                        });
                    }
                    buffer.add(v);
                }

                @Override public void onCompleted() {
                    if (buffer != null) {
                        child.onNext(buffer);
                        buffer = null;
                    }
                    child.onCompleted();
                }

                @Override public void onError(Throwable e) { child.onError(e); }
            };
        }
    }
}
