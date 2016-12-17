package com.intendia.rxgwt.elemental;

import elemental.dom.ElementalMixinBase;
import elemental.events.Event;
import elemental.events.EventTarget;
import rx.Observable;
import rx.subscriptions.Subscriptions;

public class RxElemental {

    public static Observable<Event> observe(ElementalMixinBase element, String type) {
        return observe(element, type, false);
    }

    public static Observable<Event> observe(ElementalMixinBase source, String type, boolean useCapture) {
        return Observable.create(s -> {
            elemental.events.EventListener listener = s::onNext;
            source.addEventListener(type, listener, useCapture);
            s.add(Subscriptions.create(() -> source.removeEventListener(type, listener, useCapture)));
        });
    }

    public static Observable<Event> observe(EventTarget element, String type) {
        return observe(element, type, false);
    }

    public static Observable<Event> observe(EventTarget element, String type, boolean useCapture) {
        return Observable.create(s -> {
            elemental.events.EventListener listener = s::onNext;
            element.addEventListener(type, listener, useCapture);
            s.add(Subscriptions.create(() -> element.removeEventListener(type, listener, useCapture)));
        });
    }
}
