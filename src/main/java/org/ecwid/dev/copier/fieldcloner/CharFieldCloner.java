package org.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class CharFieldCloner extends BaseFieldCloner {
    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setChar(to, field.getChar(from));
    }
}
