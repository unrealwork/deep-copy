package com.ecwid.dev.copier;

import com.ecwid.dev.event.BaseEventEmitter;

import java.lang.reflect.Array;

final class ArrayCopier extends BaseEventEmitter<Object> implements Copier {
    private final Copier objectCopier;

    private ArrayCopier(Copier objectCopier) {
        super();
        this.objectCopier = objectCopier;
    }

    static ArrayCopier withObjectCopier(Copier copier) {
        return new ArrayCopier(copier);
    }

    @Override
    public Object copy(Object obj) throws ObjectCopyException {
        Class<?> aClass = obj.getClass();
        int length = Array.getLength(obj);
        Class<?> componentType = aClass.getComponentType();
        Object copy = Array.newInstance(componentType, length);
        notifyObservers(CopierEvent.objectCreated(obj, copy));
        if (componentType.isPrimitive()) {
            System.arraycopy(obj, 0, copy, 0, length);
        } else {
            for (int i = 0; i < length; i++) {
                Object el = Array.get(obj, i);
                Array.set(copy, i, objectCopier.copy(el));
            }
        }
        notifyObservers(CopierEvent.cloneCompleted(obj, copy));
        return copy;
    }
}
