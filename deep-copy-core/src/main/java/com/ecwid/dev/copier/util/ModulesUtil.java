package com.ecwid.dev.copier.util;

import com.ecwid.dev.copier.fieldcloner.FieldCloner;

public class ModulesUtil {
    public static final Module MODULE = FieldCloner.class.getModule();

    private ModulesUtil() {
    }

    public static boolean isOpen(Class<?> clz) {
        Module module = clz.getModule();
        return module.canRead(MODULE);
    }
}
