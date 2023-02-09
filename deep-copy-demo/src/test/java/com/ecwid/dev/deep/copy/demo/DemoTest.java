package com.ecwid.dev.deep.copy.demo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class DemoTest {
    @Test
    @DisplayName("Test that main method")
    void testMain() {
        Assertions.assertDoesNotThrow(() -> Demo.main(new String[] {}));
    }
}
