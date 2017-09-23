package com.intendia.rxgwt2.example.widgets.client;

import static com.intendia.rxgwt2.user.RxHandlers.change;
import static com.intendia.rxgwt2.user.RxHandlers.click;
import static com.intendia.rxgwt2.user.RxHandlers.valueChange;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.intendia.rxgwt2.user.RxWidget;
import io.reactivex.Observable;
import java.util.function.Consumer;

class MyWidget extends Composite {
    @UiField Button simpleButton;
    @UiField ToggleButton toggleButton;
    @UiField ListBox listButton;
    @UiField Label result;

    public MyWidget(Consumer<String> notify) {
        initWidget(Ui.binder.createAndBindUi(this));
        RxWidget rx = new RxWidget(this);

        rx.onAttach(Observable.never()
                .doOnSubscribe(s -> notify.accept("subscribed"))
                .doOnDispose(() -> notify.accept("unsubscribed")));

        rx.onAttach(click(simpleButton)
                .doOnNext(n -> result.setText("clicked!")));

        rx.onAttach(valueChange(toggleButton)
                .map(n -> n.getValue() ? "down" : "up")
                .doOnNext(n -> result.setText("toggle '" + n + "'!")));

        rx.onAttach(change(listButton)
                .map(n -> listButton.getSelectedValue())
                .doOnNext(n -> result.setText("select '" + n + "'!")));
    }

    interface Ui extends UiBinder<HTMLPanel, MyWidget> {
        Ui binder = GWT.create(Ui.class);
    }
}
