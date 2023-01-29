package com.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class ShortFieldCloner extends BaseFieldCloner {
    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setShort(to, field.getShort(from));
    }
}
