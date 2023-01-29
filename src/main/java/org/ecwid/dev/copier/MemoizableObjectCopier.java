package org.ecwid.dev.copier;

import org.ecwid.dev.copier.fieldcloner.FieldCloner;
import org.ecwid.dev.event.BaseEventEmitter;
import org.ecwid.dev.event.Event;
import org.ecwid.dev.event.EventObserver;
import org.ecwid.dev.factory.Factory;

import java.util.IdentityHashMap;
import java.util.Map;

import static org.ecwid.dev.copier.CopierType.ARRAY;
import static org.ecwid.dev.copier.CopierType.NO_OP;
import static org.ecwid.dev.copier.CopierType.OBJECT;

final class MemoizableObjectCopier extends BaseEventEmitter<Object> implements Copier, EventObserver<Object> {

    private final Map<Object, Object> memo;
    private final FieldCloner fieldCloner;
    private final Factory<Object, Copier> copierFactory;

    MemoizableObjectCopier() {
        memo = new IdentityHashMap<>();
        this.fieldCloner = FieldCloner.withCopier(this);
        this.copierFactory = Factory.Builders.<Object, CopierType, Copier>flyweight()
                .addSupplier(ARRAY, this::arrayCopier)
                .addSupplier(NO_OP, NoOpCopier::new)
                .addSupplier(OBJECT, this::objectCopier)
                .handleOrder(NO_OP, ARRAY, OBJECT)
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
