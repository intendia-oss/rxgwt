package com.intendia.rxgwt.example.client;

import static com.intendia.rxgwt.client.RxHandlers.change;
import static com.intendia.rxgwt.client.RxHandlers.click;
import static com.intendia.rxgwt.client.RxHandlers.valueChange;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.intendia.rxgwt.client.RxWidget;
import java.util.function.Consumer;
import rx.Observable;

class MyWidget extends Composite {
    @UiField Button simpleButton;
    @UiField ToggleButton toggleButton;
    @UiField ListBox listButton;
    @UiField Label result;

    public MyWidget(Consumer<String> notify) {
        initWidget(Ui.binder.createAndBindUi(this));
        RxWidget rx = new RxWidget(this);

        rx.registerAttachObservable(Observable.never()
                .doOnSubscribe(() -> notify.accept("subscribed"))
                .doOnUnsubscribe(() -> notify.accept("unsubscribed")));

        rx.registerAttachObservable(click(simpleButton)
                .doOnNext(n -> result.setText("clicked!")));

        rx.registerAttachObservable(valueChange(toggleButton)
                .map(n -> n.getValue() ? "down" : "up")
                .doOnNext(n -> result.setText("toggle '" + n + "'!")));

        rx.registerAttachObservable(change(listButton)
                .map(n -> listButton.getSelectedValue())
                .doOnNext(n -> result.setText("select '" + n + "'!")));
    }

    interface Ui extends UiBinder<HTMLPanel, MyWidget> {
        Ui binder = GWT.create(Ui.class);
    }
}
