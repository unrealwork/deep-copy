package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.copier.ObjectCopyException;

import java.lang.reflect.Field;

/**
 * Describe classes that clone field from one obejct to another
 */
public interface FieldCloner {
    /**
     * Create Field cloner that uses passed {@link Copier} under the hood.
     * @param copier Object copier
     * @return common field cloner.
     */
    static FieldCloner withCopier(Copier copier) {
        return new CommonFieldCloner(copier);
    }

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
