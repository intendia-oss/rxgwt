# RxGWT: Reactive Extensions for GWT 

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.intendia.gwt.rxgwt2/rxgwt-parent/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.intendia.gwt.rxgwt2/rxgwt-parent)
[![Build Status](https://travis-ci.org/intendia-oss/rxgwt.svg)](https://travis-ci.org/intendia-oss/rxgwt) 
[![Join the chat at https://gitter.im/intendia-oss/rxgwt](https://badges.gitter.im/intendia-oss/rxgwt.svg)](https://gitter.im/intendia-oss/rxgwt?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

GWT specific bindings for [RxJava](http://github.com/ReactiveX/RxJava) (requires [RxJava GWT](https://github.com/intendia-oss/rxjava-gwt)). 

This module adds the minimum classes to RxJava that make writing reactive components in GWT applications easy and 
hassle-free. More specifically, it provides adapters for all available events and various operators like bufferedFinally,
debounceFinally, etc.

GWT module system requires all classes used by a module to be included as dependant modules. So to not to
force to load all optional modules various independent small modules are exposed by RxGWT.
* RxGWT.gwt.xml - includes common utils, usually not included explicit as it is included by all other modules
* RxUser.gwt.xml - includes GWT user dependant utils like [RxHandlers][RxHandlers] and [RxEvents][RxEvents]
* RxElemental.gwt.xml - legacy GWT elemental utils
* RxElemental2.gwt.xml - new generation GWT [elemental2](https://github.com/google/elemental2) utils
* RxElemento.gwt.xml - [elemento](https://github.com/hal/elemento) typed events on top of new generation GWT elemental2 utils 

## Download

Releases are deployed to [the Central Repository](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.intendia.gwt.rxgwt2%22).

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

All the `mouseMove`, `mouseDown`, `touchMove`, `touchStart`, `touchEnd` and `keyPress` static methods are exposed by the 
RxGWT API. To find all of them just explore [RxGwt](https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/client/RxGwt.java), 
or [RxHandlers][RxHandlers] and [RxEvents][RxEvents].
RxEvents and RxHandlers are [auto-generated](https://github.com/intendia-oss/rxgwt/blob/master/generator/src/main/java/com/intendia/rxgwt/RxGenerator.java) 
using all classes in `gwt-user` extending from [Events](https://github.com/gwtproject/gwt/blob/master/user/src/com/google/web/bindery/event/shared/Event.java)
and [HasValue](https://github.com/gwtproject/gwt/blob/master/user/src/com/google/gwt/event/shared/HasHandlers.java) respectively.
 
You can see this complete code example here [RxCanvas](https://github.com/ibaca/rxcanvas-gwt/blob/master/src/main/java/rxcanvas/client/RxCanvas.java),
or other interesting one in [RxSnake](https://github.com/ibaca/rxsnake-gwt).

[RxEvents]: https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/user/RxEvents.java
[RxHandlers]: https://github.com/intendia-oss/rxgwt/blob/master/core/src/main/java/com/intendia/rxgwt/user/RxHandlers.java
