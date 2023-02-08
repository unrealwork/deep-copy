package com.ecwid.dev.copier;

import com.ecwid.dev.factory.HandlerType;

import java.util.function.Predicate;

import static java.util.function.Predicate.not;

/**
 * Types of copier that could handle copying process
 */
enum CopierType implements HandlerType<Object> {
    /**
     * Handles arrays copying
     */
    ARRAY(Class::isArray),
    /**
     * Generic handler for object
     */
    NO_OP(clz -> clz.isSynthetic() || clz.isEnum() || clz.isRecord() || clz.isAssignableFrom(Class.class)),
    /**
     * No operation copy, just copy ref of an object.
     */
    OBJECT(clz -> not(ARRAY.handlePredicate)
            .and(not(NO_OP.handlePredicate))
            .test(clz));
    private final Predicate<Class<?>> handlePredicate;

    CopierType(Predicate<Class<?>> handlePredicate) {
        this.handlePredicate = handlePredicate;
    }

    @Override
    public boolean canHandle(Object obj) {
        return handlePredicate.test(obj.getClass());
    }
}
