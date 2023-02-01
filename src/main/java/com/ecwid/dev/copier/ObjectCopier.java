package com.ecwid.dev.copier;

import com.ecwid.dev.copier.fieldcloner.FieldCloner;
import com.ecwid.dev.event.BaseEventEmitter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ObjectCopier extends BaseEventEmitter<Object> implements Copier {

    private static final Unsafe UNSAFE = getUnsafe();
    private final FieldCloner fieldCloner;

    ObjectCopier(FieldCloner fieldClonerFactory) {
        super();
        this.fieldCloner = fieldClonerFactory;
    }

    static ObjectCopier withFieldCloner(FieldCloner fieldCloner) {
        return new ObjectCopier(fieldCloner);
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
            List<Field> fields = collectFields(aClass);
            for (Field field : fields) {
                fieldCloner.clone(field, obj, copy);
            }
            notifyObservers(CopierEvent.cloneCompleted(obj, copy));
            return copy;
        } catch (InstantiationException e) {
            throw new ObjectCopyException(obj, e);
        }
    }

    private List<Field> collectFields(Class<?> clz) {
        List<Field> res = new ArrayList<>();
        Class<?> curClass = clz;
        do {
            Collections.addAll(res, curClass.getDeclaredFields());
            curClass = curClass.getSuperclass();
        } while (curClass != null);
        return res;
    }
}
