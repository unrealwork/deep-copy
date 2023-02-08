package com.ecwid.dev.deep.copy.demo;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;
import com.ecwid.dev.deep.copy.demo.classes.Man;
import com.ecwid.dev.util.CopyUtils;

import java.util.List;
import java.util.logging.Logger;

public final class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getCanonicalName());

    private Demo() {
    }

    public static void main(String[] args) throws ObjectCopyException {
        Man man = new Man("test", 20, List.of("Lord of the Rings"));
        man.addFriend(man);
        LOGGER.info(() -> "Deep copy is being started for object : " + man);
        Man copy = CopyUtils.deepCopy(man, Demo::logObjectClone);
        LOGGER.info(() -> "Deep copied object: " + copy);
    }

    private static void logObjectClone(Object src, Object clone) {
        LOGGER.info(() -> "Clone completed : " + shortObjectDescription(src) + "-> " + shortObjectDescription(clone));
    }

    private static String shortObjectDescription(Object obj) {
        return obj.getClass().getCanonicalName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
