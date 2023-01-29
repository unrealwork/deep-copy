package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Function;

final class FinalFieldCloner extends BaseFieldCloner {
    private final Function<Field, FieldCloner> delegateProvider;

    FinalFieldCloner(Function<Field, FieldCloner> delegeate) {
        this.delegateProvider = delegeate;
    }

    private static Field modifiersField() {
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.trySetAccessible();
            return modifiersField;
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException("Unbale to provide access to final field", e);
        }
    }

    @Override
    protected void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException {
        final int modifiers = field.getModifiers();
        final int nonFinalModifiers = modifiers & ~Modifier.FINAL;
        Field modifiersField = modifiersField();
        try {
            modifiersField.setInt(field, nonFinalModifiers);
            field.trySetAccessible();
            FieldCloner delegate = delegateProvider.apply(field);
            if (delegate == this) {
                throw new IllegalAccessException();
            }
            delegate.clone(field, from, to);
        } finally {
            modifiersField.setInt(field, modifiers);
            modifiersField.setAccessible(false);
        }
    }
}
