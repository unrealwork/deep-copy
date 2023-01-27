package org.ecwid.dev.copier;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

final class ObjectCopier implements Copier {

    private static final Unsafe UNSAFE = getUnsafe();
    private final DeepObjectCopier copier;

    ObjectCopier(DeepObjectCopier copier) {
        this.copier = copier;
    }


    @Override
    public Object copy(Object obj) throws ObjectCopyException {
        try {
            final Class<?> aClass = obj.getClass();
            Object copy = UNSAFE.allocateInstance(aClass);
            copier.saveRef(obj, copy);
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                copier.getFieldClonerFactory().get(field).clone(field, obj, copy);
            }
            return copy;
        } catch (InstantiationException e) {
            throw new ObjectCopyException(obj, e);
        }

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


}
