package com.ecwid.dev.util;

import java.util.Objects;

public class Cat extends Animal {
    private final String sound;

    public Cat() {
        super("cat");
        this.sound = "meow";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Cat cat = (Cat) o;
        return Objects.equals(sound, cat.sound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sound);
    }
}
