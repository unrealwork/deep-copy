package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.factory.HandlerType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

enum FieldClonerType implements HandlerType<Field> {
    /**
     * For handler of final fields
     */
    NO_OP(f -> Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())),
    /**
     * For handler of primitive fields
     */
    PRIMITIVE(f -> f.getType().isPrimitive()),
    /**
     * For handler that should not be cloned
     */
    REF_COPY(f -> Modifier.isStatic(f.getModifiers()) || f.isSynthetic() || f.isEnumConstant()),
    /**
     * Common object cloner for object.
     */
    COMMON(f -> true);

    private final Predicate<Field> canHandlePredicate;

    FieldClonerType(Predicate<Field> canHandlePredicate) {
        this.canHandlePredicate = canHandlePredicate;
    }

    @Override
    public final boolean canHandle(Field field) {
        return canHandlePredicate.test(field);
    }
}
