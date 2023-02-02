package com.ecwid.dev.util;

import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.copier.exceptions.ObjectCopyException;

import java.util.function.BiConsumer;

public final class CopyUtils {
    private static final Copier DEFAULT_COPIER = Copier.create();

    private CopyUtils() {
    }

    /**
     * Make deep copy of an object
     *
     * @param obj instance of object of type T
     * @param <T> type of object.
     * @return copied object
     * @throws ObjectCopyException in case of error during copy
     */
    public static <T> T deepCopy(T obj) throws ObjectCopyException {
        return (T) DEFAULT_COPIER.copy(obj);
    }

    /**
     * Make deep copy of an object with callback on each clone completed during the process
     *
     * @param obj           object to clone
     * @param cloneCallback - callback for completed clone. First param - source object, second - cloned.
     * @param <T>           type of object
     * @return cloned object
     * @throws ObjectCopyException in case of error related to object copying process
     */
    public static <T> T deepCopy(T obj, BiConsumer<Object, Object> cloneCallback) throws ObjectCopyException {
        return (T) Copier.create(cloneCallback).copy(obj);
    }
}
