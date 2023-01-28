package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.factory.HandlerType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

public enum FieldClonerType implements HandlerType<Field> {
    FINAL(f -> Modifier.isFinal(f.getModifiers())),
    PRIMITIVE(f -> f.getType().isPrimitive()),
    REF_COPY(f -> Modifier.isStatic(f.getModifiers()) || f.isSynthetic() || f.isEnumConstant()),
    COMMON(f -> true);

    private final Predicate<Field> canHandlePredicate;

    FieldClonerType(Predicate<Field> canHandlePredicate) {
        this.canHandlePredicate = canHandlePredicate;
    }

    public boolean canHandle(Field field) {
        return canHandlePredicate.test(field);
    }
}
