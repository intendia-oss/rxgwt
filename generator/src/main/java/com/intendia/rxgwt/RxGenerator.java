package com.intendia.rxgwt;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;

import com.google.gwt.core.shared.GwtIncompatible;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;
import org.reflections.Reflections;
import rx.Observable;
import rx.functions.Func1;

public class RxGenerator {
    private static final String INDENT = "    ";
    // Extends {com.google.bindery.event.shared.HandlerRegistration} for legacy compatibility.
    // com.google.gwt.event.sharedHandlerRegistration extends com.google.web.bindery.event.shared.HandlerRegistration
    private static final Class<HandlerRegistration> HR_TYPE = HandlerRegistration.class;

    public static void main(String[] args) throws IOException {
        Reflections gwt = new Reflections("com.google.gwt");
        Path out = Paths.get("core/src/main/java");
        AnnotationSpec unused = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unused").build();

        Set<Class<? extends HasHandlers>> hasHandlers = gwt.getSubTypesOf(HasHandlers.class);
        Set<Class<? extends Event>> events = gwt.getSubTypesOf(Event.class);
        Map<Class<?>, Class<?>> eventsWithGenericsToHandlerMap = gwt.getSubTypesOf(EventHandler.class).stream()
                .filter(Class::isInterface)
                .flatMap(h -> stream(h.getDeclaredMethods())
                        .filter(m -> m.getName().startsWith("on") && m.getReturnType().equals(void.class))
                        .filter(m -> m.getParameterTypes().length == 1).map(m -> m.getParameterTypes()[0])
                        .filter(e -> Event.class.isAssignableFrom(e) && e.getTypeParameters().length > 0)
                        .map(e -> new Class<?>[] { e, h }))
                .collect(Collectors.toMap(o -> o[0], o -> o[1]));

        ClassName rxGwt = ClassName.bestGuess("com.intendia.rxgwt.client.RxGwt");
        String packageName = rxGwt.packageName();

        JavaFile.builder(packageName, classBuilder("RxEvents")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(unused)
                .addMethods(() -> sortByName(events(events, eventsWithGenericsToHandlerMap)))
                .build()
        ).addStaticImport(rxGwt, "register").indent(INDENT).build().writeTo(out);

        JavaFile.builder(packageName, classBuilder("RxHandlers")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(unused)
                .addMethods(() -> sortByName(handlers(hasHandlers)))
                .build()
        ).addStaticImport(rxGwt, "register").indent(INDENT).build().writeTo(out);

    }

    private static Iterator<MethodSpec> sortByName(Stream<MethodSpec> methods) {
        return methods.sorted(comparing(m -> m.name)).iterator();
    }

    private static Stream<MethodSpec> handlers(Set<Class<? extends HasHandlers>> handlers) {
        // Find handlers like...
        // public interface HasTouchEndHandlers extends HasHandlers {
        //     /** Adds a {@link TouchEndEvent} handler. */
        //     HandlerRegistration addTouchEndHandler(TouchEndHandler handler);
        // ...to generate methods like...
        // public static Observable<ClickEvent> click(HasClickHandlers source) {
        //     return Observable.create(s -> register(s, source.addClickHandler(s::onNext)));
        return handlers.stream()
                .filter(Class::isInterface)
                .flatMap(hasHandler -> stream(hasHandler.getDeclaredMethods())
                        .filter(m -> HR_TYPE.isAssignableFrom(m.getReturnType()))
                        .filter(m -> m.getName().startsWith("add") && m.getName().endsWith("Handler")))
                .map(addHandler -> {
                    Func1<Method, String> eventName = m -> m.getName().substring(3, m.getName().length() - 7);
                    String name = UPPER_CAMEL.to(LOWER_CAMEL, eventName.call(addHandler));
                    Class<?> event = addHandler.getParameterTypes()[0].getMethods()[0].getParameterTypes()[0];
                    TypeVariable<? extends Class<?>>[] generics = event.getTypeParameters();
                    return MethodSpec.methodBuilder(name).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .addTypeVariables(() -> stream(generics).map(TypeVariableName::get).iterator())
                            .returns(observableOf(event))
                            .addParameter(typeName(addHandler.getDeclaringClass(), generics), "source")
                            .addStatement("return $T.create(s -> register(s, source.$L(s::onNext)))",
                                    Observable.class, addHandler.getName())
                            .build();
                });
    }

    private static Stream<MethodSpec> events(Set<Class<? extends Event>> events,
            Map<Class<?>, Class<?>> eventToHandlerIndex) {
        // Find gwt events to generate methods like...
        // public static Observable<KeyUpEvent> keyUp(Widget source) {
        //     return Observable.create(s -> register(s, source.addDomHandler(s::onNext, KeyUpEvent.getType())));
        return events.stream()
                .filter(c -> !c.isAnonymousClass())
                .map(event -> {
                    String name = event.getSimpleName().substring(0, event.getSimpleName().length() - 5);
                    name = UPPER_CAMEL.to(LOWER_CAMEL, name);
                    Optional<Method> type = stream(event.getDeclaredMethods())
                            .filter(m -> m.getName().equals("getType") && isPublic(m.getModifiers()))
                            .findFirst();
                    if (!type.isPresent()) {
                        return MethodSpec.methodBuilder(name).addModifiers(Modifier.PRIVATE)
                                .addAnnotation(AnnotationSpec.builder(GwtIncompatible.class)
                                        .addMember("value", "$S", event + " do not have a public getType!").build())
                                .build();
                    }
                    boolean isDom = DomEvent.class.isAssignableFrom(event);
                    TypeVariable<? extends Class<? extends Event>>[] generics = event.getTypeParameters();
                    Class<?> handler = eventToHandlerIndex.get(event); // only non null for events with generics
                    return MethodSpec.methodBuilder(name)
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .addTypeVariables(() -> stream(generics).map(TypeVariableName::get).iterator())
                            .returns(observableOf(event))
                            .addParameter(Widget.class, "source")
                            .addCode(block("$[return $T.create(s -> register(s, source.$L(",
                                    Observable.class, isDom ? "addDomHandler" : "addHandler"))
                            .addCode(generics.length == 0 ? block("s::onNext") : block("($T) s::onNext",
                                    ParameterizedTypeName.get(handler, generics[0])))
                            .addCode(block(", $T.getType())));\n$]", event))
                            .build();
                });
    }

    private static CodeBlock block(String format, Object... args) {
        return CodeBlock.of(format, args);
    }

    private static ParameterizedTypeName observableOf(Class<?> e) {
        return ParameterizedTypeName.get(ClassName.get(Observable.class), typeName(e, e.getTypeParameters()));
    }

    private static TypeName typeName(Class<?> e, TypeVariable<? extends Class<?>>[] typeParameters) {
        return typeParameters.length == 0 ? ClassName.get(e) : ParameterizedTypeName.get(e, (Type[]) typeParameters);
    }
}
