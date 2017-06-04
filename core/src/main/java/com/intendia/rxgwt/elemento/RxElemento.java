package com.intendia.rxgwt.elemento;

import com.intendia.rxgwt.elemental2.RxElemental2;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import jsinterop.base.Js;
import org.jboss.gwt.elemento.core.EventType;
import rx.Emitter;
import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

public interface RxElemento {

    static <T extends Event> Observable<T> fromEvent(EventTarget src, EventType<T, ?> type) {
        return RxElemental2.fromEvent(src, type.name, false).map(Js::cast);
    }

    static <T extends Event, V> Observable<V> fromEvent(EventTarget src, EventType<T, ?> type, Func1<? super T, V> fn) {
        return RxElemental2.fromEvent(src, type.name, false).map(Js::<T>cast).map(fn);
    }
}
