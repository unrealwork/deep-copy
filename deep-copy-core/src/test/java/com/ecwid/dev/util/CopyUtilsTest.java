package com.ecwid.dev.util;

import com.ecwid.dev.copier.ObjectCopyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

final class CopyUtilsTest {

    private static final int PARALLEL_TASK_COUNT = 1000;

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
                arguments(new Integer[] {1, 2, 3}));
    }

    @ParameterizedTest(name = "Deep copy of {0} should be correct")
    @MethodSource("deepCopyTestCases")
    @DisplayName("Deep copy of an object should create different objects for all underlying fields of the object.")
    void deepObjectCopy(Object src) throws ObjectCopyException {
        // Validate that all objects cloned during deep copy have different references.
        Object copy = CopyUtils.deepCopy(src, Assertions::assertNotSame);
        assertEquals(src, copy);
    }


    @Test
    @DisplayName("Deep copy of int array should return equal array at with diff ref")
    void deepIntArrayCopy() throws ObjectCopyException {
        int[] src = {1, 2, 3};
        int[] copy = CopyUtils.deepCopy(src);
        assertNotSame(src, copy);
        assertArrayEquals(src, copy);
    }

    @Test
    @DisplayName("Deep copy of an object of class with different types of primitives should set correct values of fields in cloned object")
    void copyPrimitiveFields() throws ObjectCopyException {
        Primitives primitives = Primitives.builder().setB(Byte.MAX_VALUE).setC('a').setI(1).setL(1L).setS(Short.MAX_VALUE).setD(1).setIs(Boolean.TRUE).setF(1).build();
        Primitives copy = CopyUtils.deepCopy(primitives);
        assertEquals(primitives, copy);
    }

    @Test
    @DisplayName("Deep copy of the null object")
    void copyNull() throws ObjectCopyException {
        Man copy = CopyUtils.deepCopy(null);
        assertNull(copy);
    }

    @Test
    @DisplayName("Copying method generates new object for each call")
    void copyMultipleTimes() throws ObjectCopyException {
        Integer src = 1;
        Integer firstCopy = CopyUtils.deepCopy(src);
        Integer secondCopy = CopyUtils.deepCopy(src);
        assertNotSame(firstCopy, secondCopy);
    }

    @Test
    @DisplayName("Copying object in parallel should create new object for each thread")
    void copyInParallel() {
        Integer src = 1;
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        IntStream.range(0, PARALLEL_TASK_COUNT).parallel().forEach(i -> {
            try {
                Integer copy = CopyUtils.deepCopy(src);
                map.merge(System.identityHashCode(copy), 1, Integer::sum);
            } catch (ObjectCopyException e) {
                throw new RuntimeException(e);
            }
        });
        Assertions.assertEquals(PARALLEL_TASK_COUNT, map.size());
    }
}
