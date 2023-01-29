package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

/**
 * Describe classes that clone field from one obejct to another
 */
public interface FieldCloner {
    /**
     * Clone field src one object dest another
     *
     * @param field descriptor of field
     * @param src   source object
     * @param dest  destination object
     * @throws ObjectCopyException in case of error during copying process
     */
    void clone(Field field, Object src, Object dest) throws ObjectCopyException;
}
