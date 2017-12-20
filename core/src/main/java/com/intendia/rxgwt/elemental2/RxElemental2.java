package com.intendia.rxgwt.elemental2;

import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import elemental2.promise.IThenable;
import rx.Emitter;
import rx.Observable;
import rx.Single;
import rx.subscriptions.Subscriptions;

/**
 * This is a experimental (breaking changes between versions) API to add elemental2 typed events.
 *
 * https://developer.mozilla.org/en-US/docs/Web/Events
 */
public interface RxElemental2 {

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

    static <T> Single<T> fromPromise(IThenable<T> promise) {
        return Single.create(em -> promise.then(success -> {
                    em.onSuccess(success);
                    return null;
                },
                failure -> {
                    em.onError(new PromiseRejectedException(failure));
                    return null;
                }));
    }

    class PromiseRejectedException extends RuntimeException {
        private final transient Object reject;
        public PromiseRejectedException() { reject = null; }
        public PromiseRejectedException(Object reject) { this.reject = reject; }
        public Object getReject() { return reject; }
    }
}
