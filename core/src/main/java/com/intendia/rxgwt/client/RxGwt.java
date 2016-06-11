package com.intendia.rxgwt.client;

import static rx.Observable.defer;
import static rx.Observable.just;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.view.client.SetSelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.subscriptions.Subscriptions;

public class RxGwt {

    // filters & binders

    public static Observable<KeyPressEvent> keyPress(HasKeyPressHandlers source, char filter) {
        return RxEvents.keyPress(source).filter(e -> filter == e.getCharCode());
    }

    public static <T> Observable<Set<T>> bindSetSelectionChange(SetSelectionModel<T> source) {
        return RxEvents.selectionChange(source).map(e -> source.getSelectedSet())
                .startWith(defer(() -> just(source.getSelectedSet())));
    }

    public static <T> Observable<T> bindSingleSelectionChange(SingleSelectionModel<T> source) {
        return RxEvents.selectionChange(source).map(e -> source.getSelectedObject())
                .startWith(defer(() -> just(source.getSelectedObject())));
    }

    /** An observable that start with the source value and notify source value changes. */
    public static <T> Observable<T> bindValueChange(HasValue<T> source) {
        return RxEvents.valueChange(source).map(ValueChangeEvent::getValue)
                .startWith(defer(() -> just(source.getValue())));
    }

    // operators

    public static <T> Observable.Operator<T, T> debounce(long windowDuration, TimeUnit unit) {
        return new OperatorDebounceTimer<>(windowDuration, unit);
    }

    private static class OperatorDebounceTimer<T> implements Observable.Operator<T, T> {
        private final int timeInMilliseconds;

        private OperatorDebounceTimer(long windowDuration, TimeUnit unit) {
            this.timeInMilliseconds = (int) unit.toMillis(windowDuration);
        }

        @Override public Subscriber<? super T> call(final Subscriber<? super T> child) {
            return new Subscriber<T>(child) {
                private double lastOnNext = 0;
                private @Nullable T last = null;
                private Timer timer = new Timer() {
                    @Override public void run() {
                        if (last != null) {
                            child.onNext(last);
                            last = null;
                        }
                    }
                };

                @Override public void onNext(T v) {
                    double now = JsDate.now();
                    if (lastOnNext == 0 || now - lastOnNext >= timeInMilliseconds) {
                        lastOnNext = now;
                        last = null;
                        child.onNext(v);
                    } else {
                        if (last == null) {
                            // wait till timer fires, this is faster than re-schedule on each element
                            timer.schedule(timeInMilliseconds);
                        }
                        last = v;
                    }
                }

                @Override public void onCompleted() {
                    if (last != null) {
                        child.onNext(last);
                    }
                    child.onCompleted();
                }

                @Override public void onError(Throwable e) { child.onError(e); }
            };
        }
    }

    public static <T> Observable.Operator<T, T> debounceFinally() {
        return new OperatorDebounceFinally<>(Scheduler.get());
    }

    public static <T> Observable.Operator<T, T> debounceFinally(Scheduler scheduler) {
        return new OperatorDebounceFinally<>(scheduler);
    }

    private static class OperatorDebounceFinally<T> implements Observable.Operator<T, T> {
        private final Scheduler scheduler;

        private OperatorDebounceFinally(Scheduler scheduler) { this.scheduler = scheduler; }

        @Override public Subscriber<? super T> call(Subscriber<? super T> child) {
            return new Subscriber<T>(child) {
                private boolean hasNext = false;
                private boolean hasScheduled = false;
                private boolean backpressure = false;
                private long pending = 0L;
                private @Nullable T next = null;

                @Override public void onStart() {
                    setProducer(n -> {
                        if (!(n > 0)) throw new IllegalArgumentException("expected n > 0 but was " + n);
                        backpressure = n != Long.MAX_VALUE;
                        if (backpressure) pending += n;
                        tick();
                    });
                }

                private boolean hasRequested() { return !backpressure || pending > 0; }

                @Override public void onNext(T v) { next = v; hasNext = true; tick(); }

                @Override public void onCompleted() { emit(); child.onCompleted(); }

                @Override public void onError(Throwable e) { child.onError(e); }
                private void tick() {
                    if (!hasScheduled && hasNext && hasRequested()) {
                        hasScheduled = true;
                        scheduler.scheduleFinally(() -> {
                            if (isUnsubscribed()) return;
                            hasScheduled = false;
                            emit();
                        });
                    }
                }

                private void emit() {
                    if (hasNext && hasRequested()) {
                        T next = this.next;
                        this.next = null;
                        hasNext = false;
                        if (backpressure) pending--;
                        child.onNext(next);
                    }
                }
            };
        }
    }

    public static <T> Observable.Operator<List<T>, T> bufferFinally() {
        return bufferFinally(Scheduler.get());
    }

    public static <T> Observable.Operator<List<T>, T> bufferFinally(Scheduler scheduler) {
        return new OperatorBufferFinally<>(scheduler);
    }

    /**
     * This operation takes values from the specified {@link Observable} source and stores them in a buffer.
     * On "finally" (before GWT-generated code returns control to the browser's event loop) the buffer is emitted and
     * replaced with a new buffer. When the source {@link Observable} completes or produces an error, the current
     * buffer is emitted, and the event is propagated to all subscribed {@link Subscriber}s.
     *
     * @param <T> the buffered value type
     * @see Scheduler#scheduleFinally(Scheduler.RepeatingCommand)
     */
    private static class OperatorBufferFinally<T> implements Observable.Operator<List<T>, T> {
        private final Scheduler scheduler;

        private OperatorBufferFinally(Scheduler scheduler) { this.scheduler = scheduler; }

        @Override public Subscriber<? super T> call(final Subscriber<? super List<T>> child) {
            return new Subscriber<T>(child) {
                private @Nullable List<T> buffer = null;

                @Override public void onNext(T v) {
                    if (buffer == null) {
                        buffer = new ArrayList<>();
                        scheduler.scheduleFinally(() -> {
                            if (!isUnsubscribed()) child.onNext(buffer);
                            buffer = null;
                            return false;
                        });
                    }
                    buffer.add(v);
                }

                @Override public void onCompleted() {
                    if (buffer != null) {
                        child.onNext(buffer);
                        buffer = null;
                    }
                    child.onCompleted();
                }

                @Override public void onError(Throwable e) { child.onError(e); }
            };
        }
    }

    // utils

    public static void register(Subscriber s, HandlerRegistration handlerRegistration) {
        s.add(Subscriptions.create(handlerRegistration::removeHandler));
    }

    public static <T extends Event> Action1<T> consume(Action1<T> n) {
        return e -> {
            e.preventDefault(); e.stopPropagation(); n.call(e);
        };
    }

}
