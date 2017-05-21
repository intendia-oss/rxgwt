package com.intendia.rxgwt.elemental2;

import elemental2.dom.ClipboardEvent;
import elemental2.dom.Document;
import elemental2.dom.DragEvent;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.EventTarget;
import elemental2.dom.FocusEvent;
import elemental2.dom.HashChangeEvent;
import elemental2.dom.InputEvent;
import elemental2.dom.KeyboardEvent;
import elemental2.dom.MouseEvent;
import elemental2.dom.PageTransitionEvent;
import elemental2.dom.PopStateEvent;
import elemental2.dom.TouchEvent;
import elemental2.dom.UIEvent;
import elemental2.dom.WheelEvent;
import elemental2.dom.Window;
import jsinterop.base.Js;
import rx.Emitter;
import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * This is a experimental (breaking changes between versions) API to add elemental2 typed events.
 *
 * https://developer.mozilla.org/en-US/docs/Web/Events
 */
public interface RxElemental2 {

    // Network Events
    EType<Event, Window> online = new EType<>("online");
    EType<Event, Window> offline = new EType<>("offline");

    // Focus event
    EType<FocusEvent, Element> focus = new EType<>("focus");
    EType<FocusEvent, Element> blur = new EType<>("blur");

    // Session History Events
    EType<PageTransitionEvent, Document> pagehide = new EType<>("pagehide");
    EType<PageTransitionEvent, Document> pageshow = new EType<>("pageshow");
    EType<PopStateEvent, Window> popstate = new EType<>("popstate");

    // Form Events
    EType<Event, Element> reset = new EType<>("reset");
    EType<Event, Element> submit = new EType<>("submit");

    // Printing Events
    EType<Event, Window> beforeprint = new EType<>("beforeprint");
    EType<Event, Window> afterprint = new EType<>("afterprint");

    // Text Composition Events
    EType<TouchEvent, Element> compositionstart = new EType<>("compositionstart");
    EType<TouchEvent, Element> compositionupdate = new EType<>("compositionupdate");
    EType<TouchEvent, Element> compositionend = new EType<>("compositionend");

    // View Events
    EType<Event, Document> fullscreenchange = new EType<>("fullscreenchange");
    EType<Event, Document> fullscreenerror = new EType<>("fullscreenerror");
    EType<UIEvent, Window> resize = new EType<>("resize");
    EType<UIEvent, EventTarget> scroll = new EType<>("scroll");

    // Clipboard Events
    EType<ClipboardEvent, EventTarget> cut = new EType<>("cut");
    EType<ClipboardEvent, EventTarget> copy = new EType<>("copy");
    EType<ClipboardEvent, EventTarget> paste = new EType<>("paste");

    // Keyboard Events
    EType<KeyboardEvent, EventTarget> keydown = new EType<>("keydown");
    EType<KeyboardEvent, EventTarget> keypress = new EType<>("keypress");
    EType<KeyboardEvent, EventTarget> keyup = new EType<>("keyup");

    // Mouse Events
    EType<MouseEvent, EventTarget> mouseenter = new EType<>("mouseenter");
    EType<MouseEvent, EventTarget> mouseover = new EType<>("mouseover");
    EType<MouseEvent, EventTarget> mousemove = new EType<>("mousemove");
    EType<MouseEvent, EventTarget> mousedown = new EType<>("mousedown");
    EType<MouseEvent, EventTarget> mouseup = new EType<>("mouseup");
    EType<MouseEvent, Element> auxclick = new EType<>("auxclick");
    EType<MouseEvent, Element> click = new EType<>("click");
    EType<MouseEvent, Element> dblclick = new EType<>("dblclick");
    EType<MouseEvent, Element> contextmenu = new EType<>("contextmenu");
    EType<WheelEvent, EventTarget> wheel = new EType<>("wheel");
    EType<MouseEvent, Element> mouseleave = new EType<>("mouseleave");
    EType<MouseEvent, Element> mouseout = new EType<>("mouseout");
    EType<Event, Document> pointerlockchange = new EType<>("pointerlockchange");
    EType<Event, Document> pointerlockerror = new EType<>("pointerlockerror");

    // Drag & Drop Events
    EType<DragEvent, EventTarget> dragstart = new EType<>("dragstart");
    EType<DragEvent, EventTarget> drag = new EType<>("drag");
    EType<DragEvent, EventTarget> dragend = new EType<>("dragend");
    EType<DragEvent, EventTarget> dragenter = new EType<>("dragenter");
    EType<DragEvent, EventTarget> dragover = new EType<>("dragover");
    EType<DragEvent, EventTarget> dragleave = new EType<>("dragleave");
    EType<DragEvent, EventTarget> drop = new EType<>("drop");

    // Touch Events
    EType<TouchEvent, Element> touchcancel = new EType<>("touchcancel");
    EType<TouchEvent, Element> touchend = new EType<>("touchend");
    EType<TouchEvent, Element> touchmove = new EType<>("touchmove");
    EType<TouchEvent, Element> touchstart = new EType<>("touchstart");

    // Value Change Events
    EType<HashChangeEvent, Window> hashchange = new EType<>("hashchange");
    EType<InputEvent, Element> input = new EType<>("input");
    EType<Event, Document> readystatechange = new EType<>("readystatechange");
    EType<InputEvent, Element> change = new EType<>("change");

    // Uncategorized Events
    EType<Event, Element> invalid = new EType<>("invalid");
    EType<Event, Element> show = new EType<>("show");

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

    static <T extends Event> Observable<T> fromEvent(EventTarget target, EType<T, ?> type) {
        return fromEvent(target, type.name, false).map(Js::cast);
    }

    static <T extends Event, V> Observable<V> fromEvent(EventTarget target, EType<T, ?> type, Func1<? super T, V> fn) {
        return fromEvent(target, type.name, false).map(Js::<T>cast).map(fn);
    }

    final class EType<T extends Event, V extends EventTarget> {
        public final String name;
        public EType(String name) { this.name = name; }
    }
}
