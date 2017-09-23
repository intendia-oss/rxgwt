package com.intendia.rxgwt2.user;

import static com.google.gwt.core.client.GWT.log;
import static com.intendia.rxgwt2.client.RxGwt.retryDelay;
import static java.util.Objects.requireNonNull;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

public class RxWidget {
    private static CompletableTransformer subscriptionHandler = o -> o // default handler
            .doOnError(GWT::reportUncaughtException)
            .compose(retryDelay(a -> log("attach subscription error '" + a.err + "', retry attempt " + a.idx, a.err)));

    public static void registerSubscriptionHandler(CompletableTransformer impl) {
        subscriptionHandler = requireNonNull(impl, "subscription handler required");
    }

    private final Widget wrap;
    private final List<Disposable> attachSubscriptions = new ArrayList<>();
    private final List<Completable> attachObservables = new ArrayList<>();

    public RxWidget(Widget wrap) {
        this.wrap = wrap;
        wrap.addAttachHandler(event -> {
            if (event.isAttached()) {
                for (Completable o : attachObservables) subscribe(o, attachSubscriptions);
            } else {
                for (Disposable s : attachSubscriptions) s.dispose();
                attachSubscriptions.clear();
            }
        });
    }

    public void onAttach(Flowable<?> o) { onAttach(o.ignoreElements()); }

    public void onAttach(Observable<?> o) { onAttach(o.ignoreElements()); }

    public void onAttach(Completable o) {
        assert !attachObservables.contains(o) : "duplicate subscription, pretty sure this is a bug";
        attachObservables.add(o);
        if (wrap.isAttached()) subscribe(o, attachSubscriptions);
    }

    @SuppressWarnings("unchecked") private void subscribe(Completable o, List<Disposable> to) {
        to.add(o.compose(subscriptionHandler).subscribe());
    }
}
