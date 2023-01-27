package org.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class IntFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setInt(to, field.getInt(from));
    }
}
