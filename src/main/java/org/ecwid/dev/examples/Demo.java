package org.ecwid.dev.examples;

import org.ecwid.dev.examples.classes.Man;
import org.ecwid.dev.util.CopyUtils;

import java.util.List;
import java.util.logging.Logger;

public final class Demo {
    private Demo() {
    }

    private static final Logger LOGGER = Logger.getLogger(Demo.class.getCanonicalName());

    public static void main(String[] args) {
        Man man = new Man("test", 20, List.of("Lord of the Rings"));
        man.addFriend(man);
        LOGGER.info(() -> "Source object: " + man);
        Man copy = CopyUtils.deepCopy(man);
        LOGGER.info(() -> "Copied object: " + copy);
    }
}
