package org.ecwid.dev.copier;

public final class CloneData {
    private final Object object;
    private final Object copy;

    private CloneData(Object object, Object copy) {
        this.object = object;
        this.copy = copy;
    }

    public static CloneData of(Object object, Object copy) {
        return new CloneData(object, copy);
    }

    public Object getObject() {
        return object;
    }

    public Object getCopy() {
        return copy;
    }
}
