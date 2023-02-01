package com.ecwid.dev.copier;

import com.ecwid.dev.factory.HandlerType;

import java.util.function.Predicate;

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
    OBJECT(clz -> true),
    /**
     * No operation copy, just copy ref of an object.
     */
    NO_OP(clz -> clz.isSynthetic() || clz.isEnum() || clz.isAssignableFrom(Class.class));
    private final Predicate<Class<?>> handlePredicate;

    CopierType(Predicate<Class<?>> handlePredicate) {
        this.handlePredicate = handlePredicate;
    }

    @Override
    public boolean canHandle(Object obj) {
        return handlePredicate.test(obj.getClass());
    }
}
