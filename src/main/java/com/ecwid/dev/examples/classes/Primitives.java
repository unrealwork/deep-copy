package com.ecwid.dev.examples.classes;

import java.util.Objects;

public final class Primitives {
    private final long l;
    private final boolean is;
    private final byte b;
    private final short s;
    private final float f;
    private final double d;
    private final int i;
    private final char c;

    private Primitives(long l, boolean is, byte b, short s, float f, double d, int i, char c) {
        this.l = l;
        this.is = is;
        this.b = b;
        this.s = s;
        this.f = f;
        this.d = d;
        this.i = i;
        this.c = c;
    }

    public static PrimitivesBuilder builder() {
        return new PrimitivesBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Primitives that = (Primitives) o;
        return l == that.l && is == that.is && b == that.b && s == that.s && Float.compare(that.f, f) == 0 && Double.compare(that.d, d) == 0 && i == that.i && c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(l, is, b, s, f, d, i, c);
    }

    public static class PrimitivesBuilder {
        private long l;
        private boolean is;
        private byte b;
        private short s;
        private float f;
        private double d;
        private int i;
        private char c;

        public PrimitivesBuilder setL(long l) {
            this.l = l;
            return this;
        }

        public PrimitivesBuilder setIs(boolean is) {
            this.is = is;
            return this;
        }

        public PrimitivesBuilder setB(byte b) {
            this.b = b;
            return this;
        }

        public PrimitivesBuilder setS(short s) {
            this.s = s;
            return this;
        }

        public PrimitivesBuilder setF(float f) {
            this.f = f;
            return this;
        }

        public PrimitivesBuilder setD(double d) {
            this.d = d;
            return this;
        }

        public PrimitivesBuilder setI(int i) {
            this.i = i;
            return this;
        }

        public PrimitivesBuilder setC(char c) {
            this.c = c;
            return this;
        }

        public Primitives build() {
            return new Primitives(l, is, b, s, f, d, i, c);
        }
    }
}
