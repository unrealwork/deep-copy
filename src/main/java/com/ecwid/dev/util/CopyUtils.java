package com.ecwid.dev.util;

import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.copier.ObjectCopyException;

import java.util.function.BiConsumer;

public final class CopyUtils {
    private static final Copier DEFAULT_COPIER = Copier.create();

    private CopyUtils() {
    }

    /**
     * Make deep copy of object
     *
     * @param obj instance of object of type T
     * @param <T> type of object.
     * @return copied object
     * @throws ObjectCopyException in case of error during copy
     */
    public static <T> T deepCopy(T obj) throws ObjectCopyException {
        return (T) DEFAULT_COPIER.copy(obj);
    }

    public static <T> T deepCopy(T obj, BiConsumer<Object, Object> cloneCallback) throws ObjectCopyException {
        return (T) Copier.create(cloneCallback).copy(obj);
    }
}
