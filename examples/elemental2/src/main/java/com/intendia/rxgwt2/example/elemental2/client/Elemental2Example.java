package com.intendia.rxgwt2.example.elemental2.client;

import static elemental2.dom.DomGlobal.console;
import static elemental2.dom.DomGlobal.document;
import static org.jboss.gwt.elemento.core.EventType.mousemove;
import static org.jboss.gwt.elemento.core.EventType.touchmove;

import com.google.gwt.core.client.EntryPoint;
import com.intendia.rxgwt2.elemental2.RxElemental2;
import com.intendia.rxgwt2.elemento.RxElemento;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Response;
import elemental2.promise.Promise;
import io.reactivex.Observable;
import io.reactivex.Single;

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

        String resource = "http://nominatim.openstreetmap.org/search?q=malaga&format=json";
        Promise<Response> fetchPromise = DomGlobal.fetch(resource);// this start the request operation
        Single<Response> fetchRxAlreadyStarted = RxElemental2.fromPromise(fetchPromise); // so this just
        fetchRxAlreadyStarted.subscribe(n -> console.log("1.each subscription gets the same response", n));
        fetchRxAlreadyStarted.subscribe(n -> console.log("2.each subscription gets the same response", n));
        // wraps the already started request as a Single type, this is perfectly fine, but…

        // you can also make it reusable, just defer the DomGlobal.fetch call using... 'defer'
        Single<Response> fetchRxNotStarted = Single.defer(() -> {
            return RxElemental2.fromPromise(DomGlobal.fetch(resource));
        });
        // nice, so now each time you subscribe to this RX type, the fetch is actually started
        fetchRxNotStarted.subscribe(n -> console.log("1.each subscription fire a new request", n));
        fetchRxNotStarted.subscribe(n -> console.log("2.each subscription fire a new request", n));
    }
}
