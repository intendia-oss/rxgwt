package com.intendia.rxgwt.client;

import static com.intendia.rxgwt.client.RxGwt.register;

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
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasCanPlayThroughHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasContextMenuHandlers;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.HasDragEndHandlers;
import com.google.gwt.event.dom.client.HasDragEnterHandlers;
import com.google.gwt.event.dom.client.HasDragHandlers;
import com.google.gwt.event.dom.client.HasDragLeaveHandlers;
import com.google.gwt.event.dom.client.HasDragOverHandlers;
import com.google.gwt.event.dom.client.HasDragStartHandlers;
import com.google.gwt.event.dom.client.HasDropHandlers;
import com.google.gwt.event.dom.client.HasEndedHandlers;
import com.google.gwt.event.dom.client.HasErrorHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasGestureChangeHandlers;
import com.google.gwt.event.dom.client.HasGestureEndHandlers;
import com.google.gwt.event.dom.client.HasGestureStartHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.HasLoadHandlers;
import com.google.gwt.event.dom.client.HasLoadedMetadataHandlers;
import com.google.gwt.event.dom.client.HasLoseCaptureHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.HasMouseUpHandlers;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.event.dom.client.HasProgressHandlers;
import com.google.gwt.event.dom.client.HasScrollHandlers;
import com.google.gwt.event.dom.client.HasTouchCancelHandlers;
import com.google.gwt.event.dom.client.HasTouchEndHandlers;
import com.google.gwt.event.dom.client.HasTouchMoveHandlers;
import com.google.gwt.event.dom.client.HasTouchStartHandlers;
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
import com.google.gwt.event.logical.shared.HasAttachHandlers;
import com.google.gwt.event.logical.shared.HasBeforeSelectionHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasHighlightHandlers;
import com.google.gwt.event.logical.shared.HasInitializeHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.HasShowRangeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
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
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import java.lang.SuppressWarnings;
import rx.Observable;

@SuppressWarnings("unused")
public class RxEvents {
    public static Observable<AttachEvent> attach(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, AttachEvent.getType())));
    }

    public static Observable<AttachEvent> attach(HasAttachHandlers source) {
        return Observable.create(s -> register(s, source.addAttachHandler(s::onNext)));
    }

    public static <T> Observable<BeforeSelectionEvent<T>> beforeSelection(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((BeforeSelectionHandler<T>) s::onNext, BeforeSelectionEvent.getType())));
    }

    public static <T> Observable<BeforeSelectionEvent<T>> beforeSelection(HasBeforeSelectionHandlers<T> source) {
        return Observable.create(s -> register(s, source.addBeforeSelectionHandler(s::onNext)));
    }

    public static Observable<BlurEvent> blur(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, BlurEvent.getType())));
    }

    public static Observable<BlurEvent> blur(HasBlurHandlers source) {
        return Observable.create(s -> register(s, source.addBlurHandler(s::onNext)));
    }

    public static Observable<CanPlayThroughEvent> canPlayThrough(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, CanPlayThroughEvent.getType())));
    }

    public static Observable<CanPlayThroughEvent> canPlayThrough(HasCanPlayThroughHandlers source) {
        return Observable.create(s -> register(s, source.addCanPlayThroughHandler(s::onNext)));
    }

    public static <T> Observable<CellPreviewEvent<T>> cellPreview(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((CellPreviewEvent.Handler<T>) s::onNext, CellPreviewEvent.getType())));
    }

    public static <T> Observable<CellPreviewEvent<T>> cellPreview(HasCellPreviewHandlers<T> source) {
        return Observable.create(s -> register(s, source.addCellPreviewHandler(s::onNext)));
    }

    public static Observable<ChangeEvent> change(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ChangeEvent.getType())));
    }

    public static Observable<ChangeEvent> change(HasChangeHandlers source) {
        return Observable.create(s -> register(s, source.addChangeHandler(s::onNext)));
    }

    public static Observable<ClickEvent> click(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ClickEvent.getType())));
    }

    public static Observable<ClickEvent> click(HasClickHandlers source) {
        return Observable.create(s -> register(s, source.addClickHandler(s::onNext)));
    }

    public static <T> Observable<CloseEvent<T>> close(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((CloseHandler<T>) s::onNext, CloseEvent.getType())));
    }

    public static <T> Observable<CloseEvent<T>> close(HasCloseHandlers<T> source) {
        return Observable.create(s -> register(s, source.addCloseHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void closing() {
    }

    public static Observable<ColumnSortEvent> columnSort(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, ColumnSortEvent.getType())));
    }

    public static Observable<ContextMenuEvent> contextMenu(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ContextMenuEvent.getType())));
    }

    public static Observable<ContextMenuEvent> contextMenu(HasContextMenuHandlers source) {
        return Observable.create(s -> register(s, source.addContextMenuHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void dateChange() {
    }

    @GwtIncompatible("Private event type!")
    private void dateHighlight() {
    }

    @GwtIncompatible("Private event type!")
    private void dom() {
    }

    public static Observable<DoubleClickEvent> doubleClick(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DoubleClickEvent.getType())));
    }

    public static Observable<DoubleClickEvent> doubleClick(HasDoubleClickHandlers source) {
        return Observable.create(s -> register(s, source.addDoubleClickHandler(s::onNext)));
    }

    public static Observable<DragEvent> drag(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEvent.getType())));
    }

    public static Observable<DragEvent> drag(HasDragHandlers source) {
        return Observable.create(s -> register(s, source.addDragHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void dragDropEven() {
    }

    public static Observable<DragEndEvent> dragEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEndEvent.getType())));
    }

    public static Observable<DragEndEvent> dragEnd(HasDragEndHandlers source) {
        return Observable.create(s -> register(s, source.addDragEndHandler(s::onNext)));
    }

    public static Observable<DragEnterEvent> dragEnter(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragEnterEvent.getType())));
    }

    public static Observable<DragEnterEvent> dragEnter(HasDragEnterHandlers source) {
        return Observable.create(s -> register(s, source.addDragEnterHandler(s::onNext)));
    }

    public static Observable<DragLeaveEvent> dragLeave(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragLeaveEvent.getType())));
    }

    public static Observable<DragLeaveEvent> dragLeave(HasDragLeaveHandlers source) {
        return Observable.create(s -> register(s, source.addDragLeaveHandler(s::onNext)));
    }

    public static Observable<DragOverEvent> dragOver(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragOverEvent.getType())));
    }

    public static Observable<DragOverEvent> dragOver(HasDragOverHandlers source) {
        return Observable.create(s -> register(s, source.addDragOverHandler(s::onNext)));
    }

    public static Observable<DragStartEvent> dragStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DragStartEvent.getType())));
    }

    public static Observable<DragStartEvent> dragStart(HasDragStartHandlers source) {
        return Observable.create(s -> register(s, source.addDragStartHandler(s::onNext)));
    }

    public static Observable<DropEvent> drop(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, DropEvent.getType())));
    }

    public static Observable<DropEvent> drop(HasDropHandlers source) {
        return Observable.create(s -> register(s, source.addDropHandler(s::onNext)));
    }

    public static Observable<EndedEvent> ended(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, EndedEvent.getType())));
    }

    public static Observable<EndedEvent> ended(HasEndedHandlers source) {
        return Observable.create(s -> register(s, source.addEndedHandler(s::onNext)));
    }

    public static Observable<ErrorEvent> error(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ErrorEvent.getType())));
    }

    public static Observable<ErrorEvent> error(HasErrorHandlers source) {
        return Observable.create(s -> register(s, source.addErrorHandler(s::onNext)));
    }

    public static Observable<FocusEvent> focus(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, FocusEvent.getType())));
    }

    public static Observable<FocusEvent> focus(HasFocusHandlers source) {
        return Observable.create(s -> register(s, source.addFocusHandler(s::onNext)));
    }

    public static Observable<GestureChangeEvent> gestureChange(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureChangeEvent.getType())));
    }

    public static Observable<GestureChangeEvent> gestureChange(HasGestureChangeHandlers source) {
        return Observable.create(s -> register(s, source.addGestureChangeHandler(s::onNext)));
    }

    public static Observable<GestureEndEvent> gestureEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureEndEvent.getType())));
    }

    public static Observable<GestureEndEvent> gestureEnd(HasGestureEndHandlers source) {
        return Observable.create(s -> register(s, source.addGestureEndHandler(s::onNext)));
    }

    public static Observable<GestureStartEvent> gestureStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, GestureStartEvent.getType())));
    }

    public static Observable<GestureStartEvent> gestureStart(HasGestureStartHandlers source) {
        return Observable.create(s -> register(s, source.addGestureStartHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void gwt() {
    }

    public static <V> Observable<HighlightEvent<V>> highlight(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((HighlightHandler<V>) s::onNext, HighlightEvent.getType())));
    }

    public static <V> Observable<HighlightEvent<V>> highlight(HasHighlightHandlers<V> source) {
        return Observable.create(s -> register(s, source.addHighlightHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void humanInput() {
    }

    public static Observable<InitializeEvent> initialize(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, InitializeEvent.getType())));
    }

    public static Observable<InitializeEvent> initialize(HasInitializeHandlers source) {
        return Observable.create(s -> register(s, source.addInitializeHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void key() {
    }

    @GwtIncompatible("Private event type!")
    private void keyCode() {
    }

    public static Observable<KeyDownEvent> keyDown(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyDownEvent.getType())));
    }

    public static Observable<KeyDownEvent> keyDown(HasKeyDownHandlers source) {
        return Observable.create(s -> register(s, source.addKeyDownHandler(s::onNext)));
    }

    public static Observable<KeyPressEvent> keyPress(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyPressEvent.getType())));
    }

    public static Observable<KeyPressEvent> keyPress(HasKeyPressHandlers source) {
        return Observable.create(s -> register(s, source.addKeyPressHandler(s::onNext)));
    }

    public static Observable<KeyUpEvent> keyUp(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyUpEvent.getType())));
    }

    public static Observable<KeyUpEvent> keyUp(HasKeyUpHandlers source) {
        return Observable.create(s -> register(s, source.addKeyUpHandler(s::onNext)));
    }

    public static Observable<LoadEvent> load(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoadEvent.getType())));
    }

    public static Observable<LoadEvent> load(HasLoadHandlers source) {
        return Observable.create(s -> register(s, source.addLoadHandler(s::onNext)));
    }

    public static Observable<LoadedMetadataEvent> loadedMetadata(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoadedMetadataEvent.getType())));
    }

    public static Observable<LoadedMetadataEvent> loadedMetadata(HasLoadedMetadataHandlers source) {
        return Observable.create(s -> register(s, source.addLoadedMetadataHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void loadingStateChange() {
    }

    public static Observable<LoseCaptureEvent> loseCapture(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, LoseCaptureEvent.getType())));
    }

    public static Observable<LoseCaptureEvent> loseCapture(HasLoseCaptureHandlers source) {
        return Observable.create(s -> register(s, source.addLoseCaptureHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void mouse() {
    }

    public static Observable<MouseDownEvent> mouseDown(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseDownEvent.getType())));
    }

    public static Observable<MouseDownEvent> mouseDown(HasMouseDownHandlers source) {
        return Observable.create(s -> register(s, source.addMouseDownHandler(s::onNext)));
    }

    public static Observable<MouseMoveEvent> mouseMove(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseMoveEvent.getType())));
    }

    public static Observable<MouseMoveEvent> mouseMove(HasMouseMoveHandlers source) {
        return Observable.create(s -> register(s, source.addMouseMoveHandler(s::onNext)));
    }

    public static Observable<MouseOutEvent> mouseOut(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseOutEvent.getType())));
    }

    public static Observable<MouseOutEvent> mouseOut(HasMouseOutHandlers source) {
        return Observable.create(s -> register(s, source.addMouseOutHandler(s::onNext)));
    }

    public static Observable<MouseOverEvent> mouseOver(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseOverEvent.getType())));
    }

    public static Observable<MouseOverEvent> mouseOver(HasMouseOverHandlers source) {
        return Observable.create(s -> register(s, source.addMouseOverHandler(s::onNext)));
    }

    public static Observable<MouseUpEvent> mouseUp(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseUpEvent.getType())));
    }

    public static Observable<MouseUpEvent> mouseUp(HasMouseUpHandlers source) {
        return Observable.create(s -> register(s, source.addMouseUpHandler(s::onNext)));
    }

    public static Observable<MouseWheelEvent> mouseWheel(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, MouseWheelEvent.getType())));
    }

    public static Observable<MouseWheelEvent> mouseWheel(HasMouseWheelHandlers source) {
        return Observable.create(s -> register(s, source.addMouseWheelHandler(s::onNext)));
    }

    public static Observable<Event.NativePreviewEvent> nativePreview(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, Event.NativePreviewEvent.getType())));
    }

    public static <T> Observable<OpenEvent<T>> open(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((OpenHandler<T>) s::onNext, OpenEvent.getType())));
    }

    public static <T> Observable<OpenEvent<T>> open(HasOpenHandlers<T> source) {
        return Observable.create(s -> register(s, source.addOpenHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void placeChange() {
    }

    @GwtIncompatible("Private event type!")
    private void placeChangeRequest() {
    }

    public static Observable<ProgressEvent> progress(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ProgressEvent.getType())));
    }

    public static Observable<ProgressEvent> progress(HasProgressHandlers source) {
        return Observable.create(s -> register(s, source.addProgressHandler(s::onNext)));
    }

    public static Observable<RangeChangeEvent> rangeChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RangeChangeEvent.getType())));
    }

    public static Observable<RangeChangeEvent> rangeChange(HasRows source) {
        return Observable.create(s -> register(s, source.addRangeChangeHandler(s::onNext)));
    }

    @GwtIncompatible("Private event type!")
    private void redraw() {
    }

    public static Observable<ResizeEvent> resize(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, ResizeEvent.getType())));
    }

    public static Observable<ResizeEvent> resize(HasResizeHandlers source) {
        return Observable.create(s -> register(s, source.addResizeHandler(s::onNext)));
    }

    public static Observable<RowCountChangeEvent> rowCountChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RowCountChangeEvent.getType())));
    }

    public static Observable<RowCountChangeEvent> rowCountChange(HasRows source) {
        return Observable.create(s -> register(s, source.addRowCountChangeHandler(s::onNext)));
    }

    public static Observable<RowHoverEvent> rowHover(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, RowHoverEvent.getType())));
    }

    @GwtIncompatible("Private event type!")
    private void scroll() {
    }

    public static Observable<ScrollEvent> scroll(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, ScrollEvent.getType())));
    }

    public static Observable<ScrollEvent> scroll(HasScrollHandlers source) {
        return Observable.create(s -> register(s, source.addScrollHandler(s::onNext)));
    }

    public static <T> Observable<SelectionEvent<T>> selection(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((SelectionHandler<T>) s::onNext, SelectionEvent.getType())));
    }

    public static <T> Observable<SelectionEvent<T>> selection(HasSelectionHandlers<T> source) {
        return Observable.create(s -> register(s, source.addSelectionHandler(s::onNext)));
    }

    public static Observable<SelectionChangeEvent> selectionChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, SelectionChangeEvent.getType())));
    }

    public static Observable<SelectionChangeEvent> selectionChange(SelectionChangeEvent.HasSelectionChangedHandlers source) {
        return Observable.create(s -> register(s, source.addSelectionChangeHandler(s::onNext)));
    }

    public static Observable<SelectionChangeEvent> selectionChange(SelectionModel source) {
        return Observable.create(s -> register(s, source.addSelectionChangeHandler(s::onNext)));
    }

    public static <V> Observable<ShowRangeEvent<V>> showRange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((ShowRangeHandler<V>) s::onNext, ShowRangeEvent.getType())));
    }

    public static <V> Observable<ShowRangeEvent<V>> showRange(HasShowRangeHandlers<V> source) {
        return Observable.create(s -> register(s, source.addShowRangeHandler(s::onNext)));
    }

    public static Observable<FormPanel.SubmitEvent> submit(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, FormPanel.SubmitEvent.getType())));
    }

    public static Observable<FormPanel.SubmitCompleteEvent> submitComplete(Widget source) {
        return Observable.create(s -> register(s, source.addHandler(s::onNext, FormPanel.SubmitCompleteEvent.getType())));
    }

    @GwtIncompatible("Private event type!")
    private void touch() {
    }

    public static Observable<TouchCancelEvent> touchCancel(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchCancelEvent.getType())));
    }

    public static Observable<TouchCancelEvent> touchCancel(HasTouchCancelHandlers source) {
        return Observable.create(s -> register(s, source.addTouchCancelHandler(s::onNext)));
    }

    public static Observable<TouchEndEvent> touchEnd(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchEndEvent.getType())));
    }

    public static Observable<TouchEndEvent> touchEnd(HasTouchEndHandlers source) {
        return Observable.create(s -> register(s, source.addTouchEndHandler(s::onNext)));
    }

    public static Observable<TouchMoveEvent> touchMove(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchMoveEvent.getType())));
    }

    public static Observable<TouchMoveEvent> touchMove(HasTouchMoveHandlers source) {
        return Observable.create(s -> register(s, source.addTouchMoveHandler(s::onNext)));
    }

    public static Observable<TouchStartEvent> touchStart(Widget source) {
        return Observable.create(s -> register(s, source.addDomHandler(s::onNext, TouchStartEvent.getType())));
    }

    public static Observable<TouchStartEvent> touchStart(HasTouchStartHandlers source) {
        return Observable.create(s -> register(s, source.addTouchStartHandler(s::onNext)));
    }

    public static <T> Observable<ValueChangeEvent<T>> valueChange(Widget source) {
        return Observable.create(s -> register(s, source.addHandler((ValueChangeHandler<T>) s::onNext, ValueChangeEvent.getType())));
    }

    public static <T> Observable<ValueChangeEvent<T>> valueChange(HasValueChangeHandlers<T> source) {
        return Observable.create(s -> register(s, source.addValueChangeHandler(s::onNext)));
    }
}
