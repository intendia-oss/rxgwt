package com.intendia.rxgwt2.user;

import static com.intendia.rxgwt2.user.RxUser.register;

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
import com.google.gwt.event.logical.shared.CloseEvent;
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
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import io.reactivex.Observable;
import java.lang.SuppressWarnings;

@SuppressWarnings("unused")
public class RxHandlers {
    public static Observable<AttachEvent> attach(HasAttachHandlers source) {
        return Observable.create(s -> register(s, source.addAttachHandler(s::onNext)));
    }

    public static <T> Observable<BeforeSelectionEvent<T>> beforeSelection(HasBeforeSelectionHandlers<T> source) {
        return Observable.create(s -> register(s, source.addBeforeSelectionHandler(s::onNext)));
    }

    public static Observable<BlurEvent> blur(HasBlurHandlers source) {
        return Observable.create(s -> register(s, source.addBlurHandler(s::onNext)));
    }

    public static Observable<CanPlayThroughEvent> canPlayThrough(HasCanPlayThroughHandlers source) {
        return Observable.create(s -> register(s, source.addCanPlayThroughHandler(s::onNext)));
    }

    public static <T> Observable<CellPreviewEvent<T>> cellPreview(HasCellPreviewHandlers<T> source) {
        return Observable.create(s -> register(s, source.addCellPreviewHandler(s::onNext)));
    }

    public static Observable<ChangeEvent> change(HasChangeHandlers source) {
        return Observable.create(s -> register(s, source.addChangeHandler(s::onNext)));
    }

    public static Observable<ClickEvent> click(HasClickHandlers source) {
        return Observable.create(s -> register(s, source.addClickHandler(s::onNext)));
    }

    public static <T> Observable<CloseEvent<T>> close(HasCloseHandlers<T> source) {
        return Observable.create(s -> register(s, source.addCloseHandler(s::onNext)));
    }

    public static Observable<ContextMenuEvent> contextMenu(HasContextMenuHandlers source) {
        return Observable.create(s -> register(s, source.addContextMenuHandler(s::onNext)));
    }

    public static Observable<DoubleClickEvent> doubleClick(HasDoubleClickHandlers source) {
        return Observable.create(s -> register(s, source.addDoubleClickHandler(s::onNext)));
    }

    public static Observable<DragEvent> drag(HasDragHandlers source) {
        return Observable.create(s -> register(s, source.addDragHandler(s::onNext)));
    }

    public static Observable<DragEndEvent> dragEnd(HasDragEndHandlers source) {
        return Observable.create(s -> register(s, source.addDragEndHandler(s::onNext)));
    }

    public static Observable<DragEnterEvent> dragEnter(HasDragEnterHandlers source) {
        return Observable.create(s -> register(s, source.addDragEnterHandler(s::onNext)));
    }

    public static Observable<DragLeaveEvent> dragLeave(HasDragLeaveHandlers source) {
        return Observable.create(s -> register(s, source.addDragLeaveHandler(s::onNext)));
    }

    public static Observable<DragOverEvent> dragOver(HasDragOverHandlers source) {
        return Observable.create(s -> register(s, source.addDragOverHandler(s::onNext)));
    }

    public static Observable<DragStartEvent> dragStart(HasDragStartHandlers source) {
        return Observable.create(s -> register(s, source.addDragStartHandler(s::onNext)));
    }

    public static Observable<DropEvent> drop(HasDropHandlers source) {
        return Observable.create(s -> register(s, source.addDropHandler(s::onNext)));
    }

    public static Observable<EndedEvent> ended(HasEndedHandlers source) {
        return Observable.create(s -> register(s, source.addEndedHandler(s::onNext)));
    }

    public static Observable<ErrorEvent> error(HasErrorHandlers source) {
        return Observable.create(s -> register(s, source.addErrorHandler(s::onNext)));
    }

    public static Observable<FocusEvent> focus(HasFocusHandlers source) {
        return Observable.create(s -> register(s, source.addFocusHandler(s::onNext)));
    }

    public static Observable<GestureChangeEvent> gestureChange(HasGestureChangeHandlers source) {
        return Observable.create(s -> register(s, source.addGestureChangeHandler(s::onNext)));
    }

    public static Observable<GestureEndEvent> gestureEnd(HasGestureEndHandlers source) {
        return Observable.create(s -> register(s, source.addGestureEndHandler(s::onNext)));
    }

    public static Observable<GestureStartEvent> gestureStart(HasGestureStartHandlers source) {
        return Observable.create(s -> register(s, source.addGestureStartHandler(s::onNext)));
    }

    public static <V> Observable<HighlightEvent<V>> highlight(HasHighlightHandlers<V> source) {
        return Observable.create(s -> register(s, source.addHighlightHandler(s::onNext)));
    }

    public static Observable<InitializeEvent> initialize(HasInitializeHandlers source) {
        return Observable.create(s -> register(s, source.addInitializeHandler(s::onNext)));
    }

    public static Observable<KeyDownEvent> keyDown(HasKeyDownHandlers source) {
        return Observable.create(s -> register(s, source.addKeyDownHandler(s::onNext)));
    }

    public static Observable<KeyPressEvent> keyPress(HasKeyPressHandlers source) {
        return Observable.create(s -> register(s, source.addKeyPressHandler(s::onNext)));
    }

    public static Observable<KeyUpEvent> keyUp(HasKeyUpHandlers source) {
        return Observable.create(s -> register(s, source.addKeyUpHandler(s::onNext)));
    }

    public static Observable<LoadEvent> load(HasLoadHandlers source) {
        return Observable.create(s -> register(s, source.addLoadHandler(s::onNext)));
    }

    public static Observable<LoadedMetadataEvent> loadedMetadata(HasLoadedMetadataHandlers source) {
        return Observable.create(s -> register(s, source.addLoadedMetadataHandler(s::onNext)));
    }

    public static Observable<LoseCaptureEvent> loseCapture(HasLoseCaptureHandlers source) {
        return Observable.create(s -> register(s, source.addLoseCaptureHandler(s::onNext)));
    }

    public static Observable<MouseDownEvent> mouseDown(HasMouseDownHandlers source) {
        return Observable.create(s -> register(s, source.addMouseDownHandler(s::onNext)));
    }

    public static Observable<MouseMoveEvent> mouseMove(HasMouseMoveHandlers source) {
        return Observable.create(s -> register(s, source.addMouseMoveHandler(s::onNext)));
    }

    public static Observable<MouseOutEvent> mouseOut(HasMouseOutHandlers source) {
        return Observable.create(s -> register(s, source.addMouseOutHandler(s::onNext)));
    }

    public static Observable<MouseOverEvent> mouseOver(HasMouseOverHandlers source) {
        return Observable.create(s -> register(s, source.addMouseOverHandler(s::onNext)));
    }

    public static Observable<MouseUpEvent> mouseUp(HasMouseUpHandlers source) {
        return Observable.create(s -> register(s, source.addMouseUpHandler(s::onNext)));
    }

    public static Observable<MouseWheelEvent> mouseWheel(HasMouseWheelHandlers source) {
        return Observable.create(s -> register(s, source.addMouseWheelHandler(s::onNext)));
    }

    public static <T> Observable<OpenEvent<T>> open(HasOpenHandlers<T> source) {
        return Observable.create(s -> register(s, source.addOpenHandler(s::onNext)));
    }

    public static Observable<ProgressEvent> progress(HasProgressHandlers source) {
        return Observable.create(s -> register(s, source.addProgressHandler(s::onNext)));
    }

    public static Observable<RangeChangeEvent> rangeChange(HasRows source) {
        return Observable.create(s -> register(s, source.addRangeChangeHandler(s::onNext)));
    }

    public static Observable<ResizeEvent> resize(HasResizeHandlers source) {
        return Observable.create(s -> register(s, source.addResizeHandler(s::onNext)));
    }

    public static Observable<RowCountChangeEvent> rowCountChange(HasRows source) {
        return Observable.create(s -> register(s, source.addRowCountChangeHandler(s::onNext)));
    }

    public static Observable<ScrollEvent> scroll(HasScrollHandlers source) {
        return Observable.create(s -> register(s, source.addScrollHandler(s::onNext)));
    }

    public static <T> Observable<SelectionEvent<T>> selection(HasSelectionHandlers<T> source) {
        return Observable.create(s -> register(s, source.addSelectionHandler(s::onNext)));
    }

    public static Observable<SelectionChangeEvent> selectionChange(SelectionChangeEvent.HasSelectionChangedHandlers source) {
        return Observable.create(s -> register(s, source.addSelectionChangeHandler(s::onNext)));
    }

    public static Observable<SelectionChangeEvent> selectionChange(SelectionModel source) {
        return Observable.create(s -> register(s, source.addSelectionChangeHandler(s::onNext)));
    }

    public static <V> Observable<ShowRangeEvent<V>> showRange(HasShowRangeHandlers<V> source) {
        return Observable.create(s -> register(s, source.addShowRangeHandler(s::onNext)));
    }

    public static Observable<TouchCancelEvent> touchCancel(HasTouchCancelHandlers source) {
        return Observable.create(s -> register(s, source.addTouchCancelHandler(s::onNext)));
    }

    public static Observable<TouchEndEvent> touchEnd(HasTouchEndHandlers source) {
        return Observable.create(s -> register(s, source.addTouchEndHandler(s::onNext)));
    }

    public static Observable<TouchMoveEvent> touchMove(HasTouchMoveHandlers source) {
        return Observable.create(s -> register(s, source.addTouchMoveHandler(s::onNext)));
    }

    public static Observable<TouchStartEvent> touchStart(HasTouchStartHandlers source) {
        return Observable.create(s -> register(s, source.addTouchStartHandler(s::onNext)));
    }

    public static <T> Observable<ValueChangeEvent<T>> valueChange(HasValueChangeHandlers<T> source) {
        return Observable.create(s -> register(s, source.addValueChangeHandler(s::onNext)));
    }
}
