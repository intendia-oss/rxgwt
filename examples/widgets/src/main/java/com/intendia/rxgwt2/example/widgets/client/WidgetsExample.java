package com.intendia.rxgwt2.example.widgets.client;

import static com.google.gwt.dom.client.Style.WhiteSpace.NOWRAP;
import static com.intendia.rxgwt2.user.RxHandlers.selection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.intendia.rxgwt2.user.RxWidget;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;

public class WidgetsExample implements EntryPoint {

    public void onModuleLoad() {
        NotificationMole mole = new NotificationMole();
        mole.setAnimationDuration(100); mole.getElement().getStyle().setWhiteSpace(NOWRAP);

        RootPanel.get().add(mole);
        RootPanel.get().add(new Layout(mole));
    }

    static class Layout extends Composite {
        public Layout(NotificationMole mole) {
            RxWidget rx = new RxWidget(this);
            PublishSubject<String> notifications = PublishSubject.create();

            TabBar tabs = new TabBar();
            tabs.addTab("First");
            tabs.addTab("Second");
            tabs.addTab("Error!");

            SimplePanel deck = new SimplePanel();
            MyWidget first = new MyWidget(msg -> notifications.onNext("first: " + msg));
            MyWidget second = new MyWidget(msg -> notifications.onNext("second: " + msg));

            FlowPanel container = new FlowPanel();
            container.add(tabs);
            container.add(deck);
            initWidget(container);

            rx.onAttach(selection(tabs)
                    .map(n -> {
                        switch (n.getSelectedItem()) {
                            case 0: return first;
                            case 1: return second;
                        }
                        throw new IllegalArgumentException("ups… invalid tab!");
                    })
                    .doOnNext(deck::setWidget));

            rx.onAttach(notifications
                    .toFlowable(BackpressureStrategy.BUFFER)
                    .flatMapCompletable(msg -> Observable.just(msg)
                            .doOnNext(mole::show)
                            .delay(2, TimeUnit.SECONDS)
                            .doOnComplete(mole::hide)
                            .ignoreElements()));
        }
    }
}
