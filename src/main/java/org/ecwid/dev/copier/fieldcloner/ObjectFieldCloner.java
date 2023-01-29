package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

final class ObjectFieldCloner extends BaseFieldCloner {
    private final Copier copier;


    ObjectFieldCloner(Copier copier) {
        this.copier = copier;
    }

    @Override
    void doClone(Field field, Object from, Object to) throws ObjectCopyException, IllegalAccessException {
        field.set(to, copier.copy(field.get(from)));
    }
}
