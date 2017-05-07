package com.intendia.rxgwt.elemental2;

import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import elemental2.dom.KeyboardEvent;
import elemental2.dom.TouchEvent;
import jsinterop.base.Js;
import rx.Emitter;
import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public interface RxElemental2 {

    EType<KeyboardEvent> keydown = new EType<>("keydown");
    EType<KeyboardEvent> keyup = new EType<>("keyup");
    EType<TouchEvent> touchstart = new EType<>("touchstart");
    EType<TouchEvent> touchmove = new EType<>("touchmove");
    EType<TouchEvent> touchcancel = new EType<>("touchcancel");
    EType<TouchEvent> touchend = new EType<>("touchend");

    static Observable<Event> fromEvent(EventTarget element, String type) {
        return fromEvent(element, type, false);
    }

    static Observable<Event> fromEvent(EventTarget source, String type, boolean useCapture) {
        return Observable.create(s -> {
            EventListener listener = s::onNext;
            source.addEventListener(type, listener, useCapture);
            s.setSubscription(Subscriptions.create(() -> source.removeEventListener(type, listener, useCapture)));
        }, Emitter.BackpressureMode.LATEST);
    }

    static <T extends Event> Observable<T> fromEvent(EventTarget element, EType<T> type) {
        return fromEvent(element, type.name, false).map(Js::cast);
    }

    static <T extends Event, V> Observable<V> fromEvent(EventTarget element, EType<T> type, Func1<? super T, V> fn) {
        return fromEvent(element, type.name, false).map(Js::<T>cast).map(fn);
    }

    final class EType<T extends Event> {
        public final String name;
        public EType(String name) { this.name = name; }
    }
}
