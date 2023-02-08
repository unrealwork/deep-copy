package com.ecwid.dev.deep.copy.demo;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;
import com.ecwid.dev.util.CopyUtils;

import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Demo {
    private static final Logger LOGGER = Logger.getLogger(Demo.class.getCanonicalName());

    private Demo() {
    }

    public static void main(String[] args) throws ObjectCopyException {
        var src = IntStream.range(0, 3)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
//        LOGGER.info(() -> "Deep copy is being started for object : " + src);
        for (Integer integer : src) {
            System.out.println(integer);
        }
        var copy = CopyUtils.deepCopy(src, Demo::logObjectClone);
        assert copy.equals(src);
        for (Integer integer : copy) {
            System.out.println(integer);
        }
//        LOGGER.info(() -> "Deep copied object: " + copy);
    }

    private static void logObjectClone(Object src, Object clone) {
        LOGGER.info(() -> "Clone completed : " + shortObjectDescription(src) + "-> " + shortObjectDescription(clone));
    }

    private static String shortObjectDescription(Object obj) {
        return obj.getClass().getCanonicalName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }
}
