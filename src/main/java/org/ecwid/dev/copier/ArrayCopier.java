package org.ecwid.dev.copier;

import java.lang.reflect.Array;

final class ArrayCopier implements Copier {
    private final DeepObjectCopier objectCopier;

    public ArrayCopier(DeepObjectCopier objectCopier) {
        this.objectCopier = objectCopier;
    }

    @Override
    public Object copy(Object obj) {
        Class<?> aClass = obj.getClass();
        int length = Array.getLength(obj);
        Class<?> componentType = aClass.getComponentType();
        Object copy = Array.newInstance(componentType, length);
        objectCopier.saveRef(obj, copy);
        if (componentType.isPrimitive()) {
            System.arraycopy(obj, 0, copy, 0, length);
        } else {
            for (int i = 0; i < length; i++) {
                Object el = Array.get(obj, i);
                Array.set(copy, i, objectCopier.copy(el));
            }
        }
        return copy;
    }
}
