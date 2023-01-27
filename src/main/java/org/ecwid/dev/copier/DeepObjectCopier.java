package org.ecwid.dev.copier;

import org.ecwid.dev.copier.fieldcloner.FieldClonerFactory;
import org.ecwid.dev.util.MapBuilder;

import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class DeepObjectCopier implements Copier {

    private final Map<Object, Object> memo;
    private final FieldClonerFactory fieldClonerFactory;

    private final Map<CopierType, Supplier<Copier>> copiersSuppliers;
    private final Map<CopierType, Copier> copierMap;

    private DeepObjectCopier() {
        memo = new IdentityHashMap<>();
        fieldClonerFactory = FieldClonerFactory.withCopier(this);
        this.copiersSuppliers = MapBuilder.<CopierType, Supplier<Copier>>immutable()
                .put(CopierType.ARRAY, () -> new ArrayCopier(this))
                .put(CopierType.NO_OP, NoOpCopier::new)
                .put(CopierType.OBJECT, () -> new ObjectCopier(this))
                .build();
        copierMap = new EnumMap<>(CopierType.class);
    }

    public static DeepObjectCopier get() {
        return new DeepObjectCopier();
    }

    void saveRef(Object src, Object copy) {
        memo.putIfAbsent(src, copy);
    }

    private Object copyRec(Object obj) {
        if (obj == null) {
            return null;
        }
        Copier copier = getCopier(obj.getClass());
        try {
            return copier.copy(obj);
        } catch (ObjectCopyException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private Copier getCopier(Class<?> clazz) {
        if (clazz.isSynthetic() || clazz.isEnum()) {
            return getCopierByType(CopierType.NO_OP);
        }
        if (clazz.isArray()) {
            return getCopierByType(CopierType.ARRAY);
        }
        return getCopierByType(CopierType.OBJECT);
    }

    private Copier getCopierByType(CopierType copierType) {
        return copierMap.computeIfAbsent(copierType, type -> copiersSuppliers.get(type).get());
    }

    @Override
    public Object copy(Object obj) {
        return memo.computeIfAbsent(obj, this::copyRec);
    }

    FieldClonerFactory getFieldClonerFactory() {
        return fieldClonerFactory;
    }

    enum CopierType {
        ARRAY, OBJECT, NO_OP
    }
}
