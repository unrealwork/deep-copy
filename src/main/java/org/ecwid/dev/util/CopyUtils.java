package org.ecwid.dev.util;

import org.ecwid.dev.copier.DeepObjectCopier;

public final class CopyUtils {
    private CopyUtils() {
    }
    
    public static <T> T deepCopy(T obj) {
        return (T) DeepObjectCopier.get().copy(obj);
    }
}
