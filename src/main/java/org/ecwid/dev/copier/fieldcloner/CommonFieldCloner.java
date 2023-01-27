package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

final class CommonFieldCloner extends BaseFieldCloner {
    private final Copier copier;

    CommonFieldCloner(Copier copier) {
        this.copier = copier;
    }

    @Override
    void doClone(Field field, Object from, Object to) throws ObjectCopyException {
        try {
            Object srcObj = field.get(from);
            field.trySetAccessible();
            if (field.isSynthetic() || field.isEnumConstant()) {
                field.set(to, field.get(from));
            } else {
                field.set(to, copier.copy(srcObj));
            }
        } catch (IllegalAccessException e) {
            throw new ObjectCopyException(field, from, e);
        }
    }
}
