package com.ecwid.dev.copier;

import com.ecwid.dev.copier.fieldcloner.FieldCloner;
import com.ecwid.dev.event.BaseEventEmitter;
import com.ecwid.dev.event.Event;
import com.ecwid.dev.event.EventObserver;
import com.ecwid.dev.factory.Factory;

import java.util.IdentityHashMap;
import java.util.Map;

final class MemoizableObjectCopier extends BaseEventEmitter<Object> implements Copier, EventObserver<Object> {

    private final Map<Object, Object> memo;
    private final FieldCloner fieldCloner;
    private final Factory<Object, Copier> copierFactory;

    MemoizableObjectCopier() {
        super();
        memo = new IdentityHashMap<>();
        this.fieldCloner = FieldCloner.withCopier(this);
        this.copierFactory = Factory.Builders.<Object, CopierType, Copier>flyweight(CopierType.class)
                .addSupplier(CopierType.ARRAY, this::arrayCopier)
                .addSupplier(CopierType.NO_OP, NoOpCopier::create)
                .addSupplier(CopierType.OBJECT, this::objectCopier)
                .handleOrder(CopierType.NO_OP, CopierType.ARRAY, CopierType.OBJECT)
                .build();
    }

    private void saveRef(Object src, Object copy) {
        memo.putIfAbsent(src, copy);
    }

    private Object copyRec(Object obj) {
        if (obj == null) {
            return null;
        }
        Copier copier = copierFactory.get(obj);
        try {
            return copier.copy(obj);
        } catch (ObjectCopyException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public Object copy(Object obj) {
        return memo.computeIfAbsent(obj, this::copyRec);
    }

    @Override
    public void onEvent(Event<Object> evt) {
        if (evt.type() == CopierEventType.INSTANCE_CREATED) {
            CloneData data = (CloneData) evt.data();
            saveRef(data.getObject(), data.getCopy());
        }
        if (evt.type() == CopierEventType.CLONE_COMPLETED) {
            notifyObservers(evt);
        }
    }

    void clearMemo() {
        this.memo.clear();
    }

    private ObjectCopier objectCopier() {
        ObjectCopier objectCopier = ObjectCopier.withFieldCloner(fieldCloner);
        objectCopier.registerObserver(this, CopierEventType.INSTANCE_CREATED);
        objectCopier.registerObserver(this, CopierEventType.CLONE_COMPLETED);
        return objectCopier;
    }

    private ArrayCopier arrayCopier() {
        ArrayCopier arrayCopier = ArrayCopier.withObjectCopier(this);
        arrayCopier.registerObserver(this, CopierEventType.INSTANCE_CREATED);
        arrayCopier.registerObserver(this, CopierEventType.CLONE_COMPLETED);
        return arrayCopier;
    }
}
