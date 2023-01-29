package org.ecwid.dev.copier;

import org.ecwid.dev.event.BaseEventEmitter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

final class ObjectCopier extends BaseEventEmitter<Object> implements Copier {

    private static final Unsafe UNSAFE = getUnsafe();
    private final DeepObjectCopier copier;

    private ObjectCopier(DeepObjectCopier copier) {
        this.copier = copier;
    }

    static ObjectCopier withObjectCopier(DeepObjectCopier copier) {
        return new ObjectCopier(copier);
    }

    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.trySetAccessible();
            return (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public Object copy(Object obj) throws ObjectCopyException {
        try {
            final Class<?> aClass = obj.getClass();
            Object copy = UNSAFE.allocateInstance(aClass);
            notifyObservers(CopierEvent.objectCreated(obj, copy));
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                copier.getFieldClonerFactory().get(field).clone(field, obj, copy);
            }
            notifyObservers(CopierEvent.cloneCompleted(obj, copy));
            return copy;
        } catch (InstantiationException e) {
            throw new ObjectCopyException(obj, e);
        }

    }


}
