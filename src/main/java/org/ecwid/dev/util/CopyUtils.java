package org.ecwid.dev.util;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.copier.ObjectCopyException;

import java.util.function.BiConsumer;

public final class CopyUtils {
    private static final Copier DEFAULT_COPIER = Copier.create();

    private CopyUtils() {
    }

    public static <T> T deepCopy(T obj) throws ObjectCopyException {
        return (T) DEFAULT_COPIER.copy(obj);
    }

    public static <T> T deepCopy(T obj, BiConsumer<Object, Object> cloneCallback) throws ObjectCopyException {
        return (T) Copier.create(cloneCallback).copy(obj);
    }
}
