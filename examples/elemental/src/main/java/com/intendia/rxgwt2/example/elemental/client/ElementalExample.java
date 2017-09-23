package com.intendia.rxgwt2.example.elemental.client;

import static elemental.events.Event.MOUSEMOVE;
import static elemental.events.Event.TOUCHMOVE;

import com.google.gwt.core.client.EntryPoint;
import com.intendia.rxgwt2.elemental.RxElemental;
import elemental.client.Browser;
import elemental.dom.Document;
import elemental.events.MouseEvent;
import elemental.events.TouchEvent;
import elemental.html.SpanElement;
import io.reactivex.Observable;

public class ElementalExample implements EntryPoint {

    public void onModuleLoad() {
        Document doc = Browser.getDocument();
        SpanElement info = doc.createSpanElement(); doc.getBody().appendChild(info);

        Observable<double[]> touch$ = RxElemental.observe(doc, TOUCHMOVE)
                .map(e -> (TouchEvent) e).map(e -> e.getTouches().item(0))
                .map(e -> new double[] { e.getClientX(), e.getClientY() });
        Observable<double[]> mouse$ = RxElemental.observe(doc, MOUSEMOVE)
                .map(e -> (MouseEvent) e)
                .map(e -> new double[] { e.getClientX(), e.getClientY() });
        Observable.merge(touch$, mouse$)
                .map(p -> "position (" + p[0] + "," + p[1] + ")")
                .forEach(info::setInnerText);
    }
}
