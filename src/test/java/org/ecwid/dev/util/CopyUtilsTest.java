package org.ecwid.dev.util;

import org.ecwid.dev.examples.classes.Man;
import org.ecwid.dev.examples.classes.Primitives;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CopyUtilsTest {

    public static Stream<Arguments> deepCopyTestCases() {
        Man man = new Man("test", 20, List.of("Lord of the Rings"));
        man.addFriend(man);
        return Stream.of(
                arguments(man),
                arguments("Test"),
                arguments(List.of(1, 2, 3)),
                arguments(1),
                arguments(Boolean.TRUE),
                arguments('a'),
                arguments(ParameterizedTest.class),
                arguments(new Integer[] {1, 2, 3})
        );
    }

    @ParameterizedTest(name = "Deep copy of {0} should be correct")
    @MethodSource("deepCopyTestCases")
    @DisplayName("Deep copy of an object should create different objects for all underlying fields of the object.")
    void deepObjectCopy(Object src) {
        // Validate that all objects cloned during deep copy have different references.
        Object copy = CopyUtils.deepCopy(src, Assertions::assertNotSame);
        assertEquals(src, copy);
    }


    @Test
    @DisplayName("Deep copy of int array should ret")
    void deepIntArrayCopy() {
        int[] src = {1, 2, 3};
        int[] copy = CopyUtils.deepCopy(src);
        assertNotSame(src, copy);
        assertArrayEquals(src, copy);
    }

    @Test
    void copyPrimitiveFields() {
        Primitives primitives = Primitives.builder()
                .setB(Byte.MAX_VALUE)
                .setC('a')
                .setI(1)
                .setL(1L)
                .setS(Short.MAX_VALUE)
                .setD(1)
                .setIs(Boolean.TRUE)
                .setF(1)
                .build();
        Primitives copy = CopyUtils.deepCopy(primitives);
        assertEquals(primitives, copy);
    }
}
