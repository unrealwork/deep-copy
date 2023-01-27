package org.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

final class LongFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setLong(to, field.getLong(from));
    }
}
