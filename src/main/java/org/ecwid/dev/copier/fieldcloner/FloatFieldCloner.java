package org.ecwid.dev.copier.fieldcloner;

import java.lang.reflect.Field;

public class FloatFieldCloner extends BaseFieldCloner {

    @Override
    void doClone(Field field, Object from, Object to) throws IllegalAccessException {
        field.setFloat(to, field.getFloat(from));
    }
}
