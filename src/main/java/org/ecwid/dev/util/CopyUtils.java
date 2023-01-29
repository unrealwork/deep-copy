package org.ecwid.dev.util;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.copier.ObjectCopyException;

import java.util.function.BiConsumer;

public final class CopyUtils {
    private CopyUtils() {
    }

    public static <T> T deepCopy(T obj) throws ObjectCopyException {
        return deepCopy(obj, null);
    }

    public static <T> T deepCopy(T obj, BiConsumer<Object, Object> cloneCallback) throws ObjectCopyException {
        return (T) Copier.deep(cloneCallback).copy(obj);
    }


}
