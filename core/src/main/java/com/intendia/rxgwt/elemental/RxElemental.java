package com.intendia.rxgwt.elemental;

import com.google.gwt.event.shared.HandlerRegistration;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

public class RxElemental {

    public static void register(Subscriber<?> s, HandlerRegistration handlerRegistration) {
        s.add(Subscriptions.create(handlerRegistration::removeHandler));
    }
}
