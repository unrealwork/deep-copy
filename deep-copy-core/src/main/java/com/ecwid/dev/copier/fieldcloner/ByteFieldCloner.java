package com.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class ByteFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setByte(to, field.getByte(from));
    }
}
