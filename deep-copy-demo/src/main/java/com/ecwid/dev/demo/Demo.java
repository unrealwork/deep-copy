package com.ecwid.dev.demo;

import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.copier.ObjectCopyException;
import com.ecwid.dev.util.CopyUtils;

import java.util.logging.Logger;

public final class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getCanonicalName());

    private Demo() {
    }

    public static void main(String[] args) throws ObjectCopyException {
        Copier copier = CopyUtils.deepCopy(Copier.create(Demo::logObjectClone));
        LOGGER.info(() -> "Deep copy is being started for object : " + copier);
        Copier copy = (Copier) copier.copy(copier);
        LOGGER.info(() -> "Deep copied object: " + copy);
        
    }

    private static void logObjectClone(Object src, Object clone) {
        LOGGER.info(() -> "Clone completed : " + shortObjectDescription(src) + "-> " + shortObjectDescription(clone));
    }

    private static String shortObjectDescription(Object obj) {
        return obj.getClass().getCanonicalName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
