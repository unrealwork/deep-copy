package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

abstract class BaseFieldCloner implements FieldCloner {
    @Override
    public void clone(Field field, Object from, Object to) throws ObjectCopyException {
        try {
            field.trySetAccessible();
            final int modifiers = field.getModifiers();
            if ((modifiers & Modifier.FINAL) != 0) {
                setFinalField(field, from, to, modifiers);
            } else {
                doClone(field, from, to);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new ObjectCopyException(field, from, e);
        }
    }

    private void setFinalField(Field field, Object from, Object to, int modifiers) throws NoSuchFieldException, IllegalAccessException, ObjectCopyException {
        final int nonFinalModifiers = modifiers & ~Modifier.FINAL;
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        try {
            if (modifiersField.trySetAccessible()) {
                modifiersField.setInt(field, nonFinalModifiers);
                if ((modifiers & Modifier.STATIC) == 0) {
                    doClone(field, from, to);
                } 
            }
        } finally {
            modifiersField.setInt(field, modifiers);
            modifiersField.setAccessible(false);
        }
    }


    abstract void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException;
}
