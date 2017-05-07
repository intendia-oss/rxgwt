package com.intendia.rxgwt.user;

import static com.intendia.rxgwt.client.RxGwt.uiBackpressureMode;
import static com.intendia.rxgwt.user.RxUser.register;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.CanPlayThroughEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.EndedEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.GestureChangeEvent;
import com.google.gwt.event.dom.client.GestureEndEvent;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadedMetadataEvent;
import com.google.gwt.event.dom.client.LoseCaptureEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.ProgressEvent;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HighlightEvent;
import com.google.gwt.event.logical.shared.HighlightHandler;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.RowHoverEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import java.lang.SuppressWarnings;
import rx.Observable;

@SuppressWarnings("unused")
public class RxEvents {
    public static Observable<AttachEvent> attach(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, AttachEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<BeforeSelectionEvent<T>> beforeSelection(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((BeforeSelectionHandler<T>) s::onNext, BeforeSelectionEvent.getType())), uiBackpressureMode());
    }

    public static Observable<BlurEvent> blur(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, BlurEvent.getType())), uiBackpressureMode());
    }

    public static Observable<CanPlayThroughEvent> canPlayThrough(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, CanPlayThroughEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<CellPreviewEvent<T>> cellPreview(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((CellPreviewEvent.Handler<T>) s::onNext, CellPreviewEvent.getType())), uiBackpressureMode());
    }

    public static Observable<ChangeEvent> change(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ChangeEvent.getType())), uiBackpressureMode());
    }

    public static Observable<ClickEvent> click(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ClickEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<CloseEvent<T>> close(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((CloseHandler<T>) s::onNext, CloseEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.user.client.Window$ClosingEvent do not have a public getType!")
    private void closing() {
    }

    public static Observable<ColumnSortEvent> columnSort(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, ColumnSortEvent.getType())), uiBackpressureMode());
    }

    public static Observable<ContextMenuEvent> contextMenu(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ContextMenuEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.user.datepicker.client.DateChangeEvent do not have a public getType!")
    private void dateChange() {
    }

    @GwtIncompatible("class com.google.gwt.user.datepicker.client.DatePicker$DateHighlightEvent do not have a public getType!")
    private void dateHighlight() {
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.DomEvent do not have a public getType!")
    private void dom() {
    }

    public static Observable<DoubleClickEvent> doubleClick(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DoubleClickEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DragEvent> drag(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.DragDropEventBase do not have a public getType!")
    private void dragDropEven() {
    }

    public static Observable<DragEndEvent> dragEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEndEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DragEnterEvent> dragEnter(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEnterEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DragLeaveEvent> dragLeave(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragLeaveEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DragOverEvent> dragOver(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragOverEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DragStartEvent> dragStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragStartEvent.getType())), uiBackpressureMode());
    }

    public static Observable<DropEvent> drop(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DropEvent.getType())), uiBackpressureMode());
    }

    public static Observable<EndedEvent> ended(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, EndedEvent.getType())), uiBackpressureMode());
    }

    public static Observable<ErrorEvent> error(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ErrorEvent.getType())), uiBackpressureMode());
    }

    public static Observable<FocusEvent> focus(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, FocusEvent.getType())), uiBackpressureMode());
    }

    public static Observable<GestureChangeEvent> gestureChange(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureChangeEvent.getType())), uiBackpressureMode());
    }

    public static Observable<GestureEndEvent> gestureEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureEndEvent.getType())), uiBackpressureMode());
    }

    public static Observable<GestureStartEvent> gestureStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureStartEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.shared.GwtEvent do not have a public getType!")
    private void gwt() {
    }

    public static <V> Observable<HighlightEvent<V>> highlight(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((HighlightHandler<V>) s::onNext, HighlightEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.HumanInputEvent do not have a public getType!")
    private void humanInput() {
    }

    public static Observable<InitializeEvent> initialize(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, InitializeEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.KeyEvent do not have a public getType!")
    private void key() {
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.KeyCodeEvent do not have a public getType!")
    private void keyCode() {
    }

    public static Observable<KeyDownEvent> keyDown(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyDownEvent.getType())), uiBackpressureMode());
    }

    public static Observable<KeyPressEvent> keyPress(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyPressEvent.getType())), uiBackpressureMode());
    }

    public static Observable<KeyUpEvent> keyUp(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyUpEvent.getType())), uiBackpressureMode());
    }

    public static Observable<LoadEvent> load(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoadEvent.getType())), uiBackpressureMode());
    }

    public static Observable<LoadedMetadataEvent> loadedMetadata(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoadedMetadataEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.user.cellview.client.LoadingStateChangeEvent do not have a public getType!")
    private void loadingStateChange() {
    }

    public static Observable<LoseCaptureEvent> loseCapture(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoseCaptureEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.MouseEvent do not have a public getType!")
    private void mouse() {
    }

    public static Observable<MouseDownEvent> mouseDown(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseDownEvent.getType())), uiBackpressureMode());
    }

    public static Observable<MouseMoveEvent> mouseMove(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseMoveEvent.getType())), uiBackpressureMode());
    }

    public static Observable<MouseOutEvent> mouseOut(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseOutEvent.getType())), uiBackpressureMode());
    }

    public static Observable<MouseOverEvent> mouseOver(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseOverEvent.getType())), uiBackpressureMode());
    }

    public static Observable<MouseUpEvent> mouseUp(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseUpEvent.getType())), uiBackpressureMode());
    }

    public static Observable<MouseWheelEvent> mouseWheel(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseWheelEvent.getType())), uiBackpressureMode());
    }

    public static Observable<Event.NativePreviewEvent> nativePreview(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, Event.NativePreviewEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<OpenEvent<T>> open(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((OpenHandler<T>) s::onNext, OpenEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.place.shared.PlaceChangeEvent do not have a public getType!")
    private void placeChange() {
    }

    @GwtIncompatible("class com.google.gwt.place.shared.PlaceChangeRequestEvent do not have a public getType!")
    private void placeChangeRequest() {
    }

    public static Observable<ProgressEvent> progress(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ProgressEvent.getType())), uiBackpressureMode());
    }

    public static Observable<RangeChangeEvent> rangeChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RangeChangeEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.user.cellview.client.AbstractHasData$RedrawEvent do not have a public getType!")
    private void redraw() {
    }

    public static Observable<ResizeEvent> resize(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, ResizeEvent.getType())), uiBackpressureMode());
    }

    public static Observable<RowCountChangeEvent> rowCountChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RowCountChangeEvent.getType())), uiBackpressureMode());
    }

    public static Observable<RowHoverEvent> rowHover(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RowHoverEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.user.client.Window$ScrollEvent do not have a public getType!")
    private void scroll() {
    }

    public static Observable<ScrollEvent> scroll(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ScrollEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<SelectionEvent<T>> selection(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((SelectionHandler<T>) s::onNext, SelectionEvent.getType())), uiBackpressureMode());
    }

    public static Observable<SelectionChangeEvent> selectionChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, SelectionChangeEvent.getType())), uiBackpressureMode());
    }

    public static <V> Observable<ShowRangeEvent<V>> showRange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((ShowRangeHandler<V>) s::onNext, ShowRangeEvent.getType())), uiBackpressureMode());
    }

    public static Observable<FormPanel.SubmitEvent> submit(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, FormPanel.SubmitEvent.getType())), uiBackpressureMode());
    }

    public static Observable<FormPanel.SubmitCompleteEvent> submitComplete(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, FormPanel.SubmitCompleteEvent.getType())), uiBackpressureMode());
    }

    @GwtIncompatible("class com.google.gwt.event.dom.client.TouchEvent do not have a public getType!")
    private void touch() {
    }

    public static Observable<TouchCancelEvent> touchCancel(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchCancelEvent.getType())), uiBackpressureMode());
    }

    public static Observable<TouchEndEvent> touchEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchEndEvent.getType())), uiBackpressureMode());
    }

    public static Observable<TouchMoveEvent> touchMove(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchMoveEvent.getType())), uiBackpressureMode());
    }

    public static Observable<TouchStartEvent> touchStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchStartEvent.getType())), uiBackpressureMode());
    }

    public static <T> Observable<ValueChangeEvent<T>> valueChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((ValueChangeHandler<T>) s::onNext, ValueChangeEvent.getType())), uiBackpressureMode());
    }
}
