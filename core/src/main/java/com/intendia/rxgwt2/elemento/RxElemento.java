package com.intendia.rxgwt2.elemento;

import com.intendia.rxgwt2.elemental2.RxElemental2;
import elemental2.dom.Event;
import elemental2.dom.EventTarget;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import jsinterop.base.Js;
import org.jboss.gwt.elemento.core.EventType;

public interface RxElemento {

    static <T extends Event> Observable<T> fromEvent(EventTarget src, EventType<T, ?> type) {
        return RxElemental2.fromEvent(src, type.getName()).map(Js::cast);
    }

    static <T extends Event> Observable<T> fromEvent(EventTarget src, EventType<T, ?> type, boolean useCapture) {
        return RxElemental2.fromEvent(src, type.getName(), useCapture).map(Js::cast);
    }

    static <T extends Event, V> Observable<V> fromEvent(EventTarget src, EventType<T, ?> type, Function<? super T, V> fn) {
        return RxElemental2.fromEvent(src, type.getName(), false).map(Js::<T>cast).map(fn);
    }
}
