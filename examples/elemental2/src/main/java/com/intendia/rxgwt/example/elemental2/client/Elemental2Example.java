package com.intendia.rxgwt.example.elemental2.client;

import static com.intendia.rxgwt.elemental2.RxElemental2.mousemove;
import static com.intendia.rxgwt.elemental2.RxElemental2.touchmove;

import com.google.gwt.core.client.EntryPoint;
import com.intendia.rxgwt.elemental2.RxElemental2;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLDocument;
import rx.Observable;

public class Elemental2Example implements EntryPoint {

    public void onModuleLoad() {
        HTMLDocument doc = DomGlobal.document;
        Element info = doc.createElement("span"); doc.body.appendChild(info);

        Observable<double[]> touch$ = RxElemental2.fromEvent(doc, touchmove)
                .map(e -> e.touches.item(0))
                .map(e -> new double[] { e.clientX, e.clientY });
        Observable<double[]> mouse$ = RxElemental2.fromEvent(doc, mousemove)
                .map(e -> new double[] { e.clientX, e.clientY });
        Observable.merge(touch$, mouse$)
                .map(p -> "position (" + p[0] + "," + p[1] + ")")
                .forEach(n -> info.textContent = n);
    }
}
