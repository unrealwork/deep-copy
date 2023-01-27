package org.ecwid.dev.util;

import org.ecwid.dev.examples.classes.Man;
import org.ecwid.dev.examples.classes.Primitives;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class CopyUtilsTest {

    @Test
    void deepCopy() {
        Man man = new Man("test", 20, List.of("Lord of the Rings"));
        man.addFriend(man);
        Man copy = CopyUtils.deepCopy(man);
        assertNotSame(copy, man);
        assertNotSame(copy.getName(), man.getName());
        assertNotSame(copy.getFavoriteBooks(), man.getFavoriteBooks());
        assertNotSame(copy.getFriends(), man.getFriends());
        assertEquals(man, copy);
    }
    
    @Test
    void copyPrimitives() {
        Primitives primitives = Primitives.builder()
                .setB(Byte.MAX_VALUE)
                .setC('a')
                .setI(1)
                .setL(1L)
                .setS(Short.MAX_VALUE)
                .setD(1)
                .setIs(true)
                .setF(1)
                .build();
        Primitives copy = CopyUtils.deepCopy(primitives);
        assertEquals(primitives, copy);
    }
}
