package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;

import java.lang.reflect.Field;
import java.util.logging.Logger;

abstract class BaseFieldCloner implements FieldCloner {
    private final Logger logger;

    protected BaseFieldCloner() {
        logger = Logger.getLogger(getClass().getCanonicalName());
    }

    @Override
    public void clone(Field field, Object src, Object dest) throws ObjectCopyException, IllegalAccessException {
        if (field.trySetAccessible()) {
            doClone(field, src, dest);
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
