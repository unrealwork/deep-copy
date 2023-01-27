package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.util.MapBuilder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class FieldClonerFactory {
    private final Copier copier;
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

    private FieldClonerFactory(Copier copier) {
        this.copier = copier;
    }

    public static FieldClonerFactory withCopier(Copier copier) {
        return new FieldClonerFactory(copier);
    }

    public FieldCloner get(Field field) {
        Class<?> type = field.getType();
        return storage.computeIfAbsent(type, k -> PRIMITIVE_FIELD_CLONER.getOrDefault(k,
                () -> new CommonFieldCloner(copier)).get());
    }
}
