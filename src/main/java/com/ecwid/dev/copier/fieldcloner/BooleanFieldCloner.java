package com.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class BooleanFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setBoolean(to, field.getBoolean(from));
    }
}
