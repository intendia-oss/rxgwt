package com.intendia.rxgwt.user;

import static com.google.gwt.core.client.GWT.log;
import static com.intendia.rxgwt.client.RxGwt.retryDelay;
import static java.util.Objects.requireNonNull;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;

public class RxWidget {
    private static Observable.Transformer<?, ?> subscriptionHandler = o -> o // default handler
            .doOnError(GWT::reportUncaughtException)
            .compose(retryDelay(a -> log("attach subscription error '" + a.err + "', retry attempt " + a.idx, a.err)));

    public static void registerSubscriptionHandler(Observable.Transformer<?, ?> impl) {
        subscriptionHandler = requireNonNull(impl, "subscription handler required");
    }

    private final Widget wrap;
    private final List<Subscription> attachSubscriptions = new ArrayList<>();
    private final List<Observable<?>> attachObservables = new ArrayList<>();

    public RxWidget(Widget wrap) {
        this.wrap = wrap;
        wrap.addAttachHandler(event -> {
            if (event.isAttached()) {
                for (Observable<?> o : attachObservables) subscribe(o, attachSubscriptions);
            } else {
                for (Subscription s : attachSubscriptions) s.unsubscribe();
                attachSubscriptions.clear();
            }
        });
    }

    public void registerAttachObservable(Observable<?> o) {
        assert !attachObservables.contains(o) : "duplicate subscription, pretty sure this is a bug";
        attachObservables.add(o);
        if (wrap.isAttached()) subscribe(o, attachSubscriptions);
    }

    @SuppressWarnings("unchecked") private boolean subscribe(Observable o, List<Subscription> to) {
        return to.add(subscriptionHandler.call(o).subscribe());
    }
}
