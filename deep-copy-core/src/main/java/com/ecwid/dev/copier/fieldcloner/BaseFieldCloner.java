package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;

import java.lang.reflect.Field;

abstract class BaseFieldCloner implements FieldCloner {
    protected BaseFieldCloner() {
    }

    @Override
    public void clone(Field field, Object src, Object dest) throws ObjectCopyException {
        try {
            field.trySetAccessible();
            doClone(field, src, dest);
        } catch (IllegalAccessException e) {
            throw new ObjectCopyException(field, src, e);
        }
    }

    /**
     * Do clone of the field
     *
     * @param field field descriptor
     * @param from  from the object
     * @param to    to the object
     * @throws IllegalAccessException in case of illegal access via reflect API.
     * @throws ObjectCopyException    in case of any copying related error.
     */
    abstract void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException;
}
