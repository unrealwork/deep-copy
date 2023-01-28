package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

abstract class BaseFieldCloner implements FieldCloner {
    @Override
    public void clone(Field field, Object from, Object to) throws ObjectCopyException {
        try {
            field.trySetAccessible();
            doClone(field, from, to);
        } catch (IllegalAccessException e) {
            throw new ObjectCopyException(field, from, e);
        }
    }


    abstract void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException;
}
