package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;

import java.lang.reflect.Field;

final class NoopFieldCloner extends BaseFieldCloner {


    NoopFieldCloner() {
        super();
    }

    @Override
    protected void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException {
        // NOOP
    }
}
