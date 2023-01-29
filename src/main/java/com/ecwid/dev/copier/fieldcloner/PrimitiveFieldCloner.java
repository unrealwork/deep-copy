package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.ObjectCopyException;
import com.ecwid.dev.util.MapBuilder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

final class PrimitiveFieldCloner extends BaseFieldCloner {
    private static final Map<Class<?>, Supplier<FieldCloner>> PRIMITIVE_FIELD_CLONER =
            MapBuilder.<Class<?>, Supplier<FieldCloner>>immutable()
                    .put(int.class, IntFieldCloner::new)
                    .put(byte.class, ByteFieldCloner::new)
                    .put(long.class, LongFieldCloner::new)
                    .put(short.class, ShortFieldCloner::new)
                    .put(boolean.class, BooleanFieldCloner::new)
                    .put(char.class, CharFieldCloner::new)
                    .put(float.class, FloatFieldCloner::new)
                    .put(double.class, DoubleFieldCloner::new)
                    .build();
    private final Map<Class<?>, FieldCloner> storage = new HashMap<>();

    private PrimitiveFieldCloner() {
        super();
    }

    static PrimitiveFieldCloner get() {
        return new PrimitiveFieldCloner();
    }

    @Override
    protected void doClone(Field field, Object from, Object to) throws IllegalAccessException, ObjectCopyException {
        storage.computeIfAbsent(field.getType(), this::primitiveClonerByClass)
                .clone(field, from, to);
    }

    private FieldCloner primitiveClonerByClass(Class<?> clz) {
        return PRIMITIVE_FIELD_CLONER.get(clz).get();
    }
}
