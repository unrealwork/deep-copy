package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

final class ObjectFieldCloner extends BaseFieldCloner {
    private final Copier copier;


    ObjectFieldCloner(Copier copier) {
        super();
        this.copier = copier;
    }

    @Override
    void doClone(Field field, Object from, Object to) throws ObjectCopyException, IllegalAccessException {
        field.set(to, copier.copy(field.get(from)));
    }
}
