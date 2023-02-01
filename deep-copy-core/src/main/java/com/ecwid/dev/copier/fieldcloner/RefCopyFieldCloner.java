package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

final class RefCopyFieldCloner extends BaseFieldCloner {
    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException {
        field.set(to, field.get(from));
    }
}
