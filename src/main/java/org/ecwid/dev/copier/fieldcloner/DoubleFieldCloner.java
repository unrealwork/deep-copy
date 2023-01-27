package org.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class DoubleFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setDouble(to, field.getDouble(from));
    }
}
