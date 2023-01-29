package org.ecwid.dev.copier;

final class CloneData {
    private final Object object;
    private final Object copy;

    private CloneData(Object object, Object copy) {
        this.object = object;
        this.copy = copy;
    }

    static CloneData create(Object object, Object copy) {
        return new CloneData(object, copy);
    }

    Object getObject() {
        return object;
    }

     Object getCopy() {
        return copy;
    }
}
