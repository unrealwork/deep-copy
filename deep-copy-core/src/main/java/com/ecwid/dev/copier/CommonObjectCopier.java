package com.ecwid.dev.copier;

import com.ecwid.dev.copier.exceptions.ObjectCopyException;
import com.ecwid.dev.event.BaseEventEmitter;
import com.ecwid.dev.event.Event;
import com.ecwid.dev.event.EventObserver;
import com.ecwid.dev.event.EventType;

final class CommonObjectCopier extends BaseEventEmitter<Object> implements Copier, EventObserver<Object> {
    private final ThreadLocal<MemoizableObjectCopier> delegate;

    CommonObjectCopier() {
        super();
        delegate = ThreadLocal.withInitial(MemoizableObjectCopier::new);
    }


    @Override
    public Object copy(Object obj) throws ObjectCopyException {
        MemoizableObjectCopier copier = delegate.get();
        Object copy = copier.copy(obj);
        copier.clearMemo();
        return copy;
    }

    @Override
    public void onEvent(Event<Object> evt) {
        delegate.get().onEvent(evt);
    }

    @Override
    public void registerObserver(EventObserver<Object> obs, EventType type) {
        delegate.get().registerObserver(obs, type);
    }
}
