package org.ecwid.dev.copier;

import org.ecwid.dev.copier.fieldcloner.FieldClonerFactory;
import org.ecwid.dev.event.Event;
import org.ecwid.dev.event.EventEmitter;
import org.ecwid.dev.event.EventObserver;
import org.ecwid.dev.util.MapBuilder;

import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.ecwid.dev.copier.CopierType.ARRAY;
import static org.ecwid.dev.copier.CopierType.NO_OP;
import static org.ecwid.dev.copier.CopierType.OBJECT;

public final class DeepObjectCopier extends EventEmitter<Object> implements Copier, EventObserver<Object> {

    private final Map<Object, Object> memo;
    private final FieldClonerFactory fieldClonerFactory;

    private final Map<CopierType, Supplier<Copier>> copiersSuppliers;
    private final Map<CopierType, Copier> copierMap;
    private final List<CopierType> handlerOrder;

    private DeepObjectCopier() {
        memo = new IdentityHashMap<>();
        fieldClonerFactory = FieldClonerFactory.withCopier(this);
        this.copiersSuppliers = MapBuilder.<CopierType, Supplier<Copier>>immutable()
                .put(ARRAY, this::arrayCopier)
                .put(NO_OP, NoOpCopier::new)
                .put(OBJECT, this::objectCopier)
                .build();
        copierMap = new EnumMap<>(CopierType.class);
        this.handlerOrder = List.of(NO_OP, ARRAY, OBJECT);
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
        Copier copier = getCopier(obj);
        try {
            return copier.copy(obj);
        } catch (ObjectCopyException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private Copier getCopier(Object obj) {
        for (CopierType type : handlerOrder) {
            if (type.canHandle(obj)) {
                return getCopierByType(type);
            }
        }
        throw new UnsupportedOperationException();
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

    @Override
    public void onEvent(Event<Object> e) {
        if (e.type() == CopierEventType.OBJECT_CREATED) {
            CloneData data = (CloneData) e.data();
            saveRef(data.getObject(), data.getCopy());
        }
        if (e.type() == CopierEventType.CLONE_COMPLETED) {
            notifyObservers(e);
        }
    }

    private ObjectCopier objectCopier() {
        ObjectCopier objectCopier = ObjectCopier.withObjectCopier(this);
        objectCopier.registerObserver(this, CopierEventType.OBJECT_CREATED);
        objectCopier.registerObserver(this, CopierEventType.CLONE_COMPLETED);
        return objectCopier;
    }

    private ArrayCopier arrayCopier() {
        ArrayCopier arrayCopier = ArrayCopier.withObjectCopier(this);
        arrayCopier.registerObserver(this, CopierEventType.OBJECT_CREATED);
        arrayCopier.registerObserver(this, CopierEventType.CLONE_COMPLETED);
        return arrayCopier;
    }
}
