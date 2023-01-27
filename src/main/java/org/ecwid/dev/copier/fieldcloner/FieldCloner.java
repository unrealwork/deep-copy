package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

public interface FieldCloner {
    void clone(Field field, Object from, Object to) throws ObjectCopyException;
}
