package org.ecwid.dev.copier;

import java.util.function.Predicate;

enum CopierType {
    ARRAY(Class::isArray), OBJECT(clz -> true), NO_OP(clz -> clz.isSynthetic() || clz.isEnum() || clz.isAssignableFrom(Class.class));
    private final Predicate<Class<?>> handlePredicate;

    CopierType(Predicate<Class<?>> handlePredicate) {
        this.handlePredicate = handlePredicate;
    }

    boolean canHandle(Object obj) {
        return handlePredicate.test(obj.getClass());
    }
}
