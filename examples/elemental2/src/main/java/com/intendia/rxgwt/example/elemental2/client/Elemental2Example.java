package com.intendia.rxgwt.example.elemental2.client;

import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.EventType.mousemove;
import static org.jboss.gwt.elemento.core.EventType.touchmove;

import com.google.gwt.core.client.EntryPoint;
import com.intendia.rxgwt.elemento.RxElemento;
import elemental2.dom.Element;
import rx.Observable;

public class Elemental2Example implements EntryPoint {

    public void onModuleLoad() {
        Element info = document.createElement("span"); document.body.appendChild(info);

        Observable<double[]> touch$ = RxElemento.fromEvent(document, touchmove)
                .map(ev -> ev.touches.item(0))
                .map(touch -> new double[] { touch.clientX, touch.clientY });
        Observable<double[]> mouse$ = RxElemento.fromEvent(document, mousemove)
                .map(ev -> new double[] { ev.clientX, ev.clientY });
        Observable.merge(touch$, mouse$)
                .map(p -> "position (" + p[0] + "," + p[1] + ")")
                .forEach(n -> info.textContent = n);
    }
}
