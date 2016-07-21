# RxGWT: Reactive Extensions for GWT [![Build Status](https://travis-ci.org/intendia-oss/rxgwt.svg)](https://travis-ci.org/intendia-oss/rxgwt) [![Join the chat at https://gitter.im/intendia-oss/rxgwt](https://badges.gitter.im/intendia-oss/rxgwt.svg)](https://gitter.im/intendia-oss/rxgwt?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

GWT specific bindings for [RxJava](http://github.com/ReactiveX/RxJava).

This module adds the minimum classes to RxJava that make writing reactive components in GWT applications easy and 
hassle-free. More specifically, it provides adapters for all available events and various operators like bufferedFinally,
debounceFinally, etc.

## Download

Snapshots of the development version are available in [Sonatype's `snapshots` repository](https://oss.sonatype.org/content/repositories/snapshots/).


## Example

```java
Observable<List<double[]>> mouseDiff$ = mouseMove(canvas)
        .map(e -> canvasPosition(canvas, e))
        .buffer(3, 1);

Observable<List<double[]>> mouseDrag$ = mouseDown(canvas).compose(log("mouse down"))
        .flatMap(e -> mouseDiff$.takeUntil(mouseUp(canvas).compose(log("mouse up"))));

Observable<List<double[]>> touchDiff$ = touchMove(canvas)
        .map(e -> e.getTouches().get(0))
        .map(e -> canvasPosition(canvas, e))
        .buffer(2, 1);

Observable<List<double[]>> touchDrag$ = touchStart(canvas).compose(log("touch down"))
        .flatMap(e -> touchDiff$.takeUntil(touchEnd(canvas).compose(log("touch up"))));

Observable<Object> down$ = merge(mouseDown(canvas), touchStart(canvas));
Observable<List<double[]>> drag$ = merge(mouseDrag$, touchDrag$);

Observable<String> paint$ = keyPress(canvas, '1').map(e -> "paint").startWith("default");
Observable<String> erase$ = keyPress(canvas, '2').map(e -> "erase");
```

All the `mouseMove`, `mouseDown`, `tochMove`, `touchStart`, `touchEnd` and `keyPress` static methods are exposed by the 
RxGWT API. To find all of them just explore [RxGwt](https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/client/RxGwt.java), 
or [RxWidget](https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/client/RxWidget.java) 
and [RxEvents](https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/client/RxEvents.java).
RxWidgets and RxEvents are [auto-generated](https://github.com/intendia-oss/rxgwt/blob/master/generator/src/main/java/com/intendia/rxgwt/RxGenerator.java) 
using all classes in `gwt-user` extending from [Events](https://github.com/gwtproject/gwt/blob/master/user/src/com/google/web/bindery/event/shared/Event.java)
and [HasValue](https://github.com/gwtproject/gwt/blob/master/user/src/com/google/gwt/event/shared/HasHandlers.java) respectively.
 
 You can see this complete code example here [RxCanvas](https://github.com/ibaca/rxcanvas-gwt/blob/master/src/main/java/rxcanvas/client/RxCanvas.java),
 or other interesting one in [RxSnake](https://github.com/ibaca/rxsnake-gwt).


