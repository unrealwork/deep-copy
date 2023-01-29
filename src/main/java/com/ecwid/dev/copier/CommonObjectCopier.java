package com.ecwid.dev.copier;

import com.ecwid.dev.event.BaseEventEmitter;
import com.ecwid.dev.event.Event;
import com.ecwid.dev.event.EventObserver;
import com.ecwid.dev.event.EventType;

final class CommonObjectCopier extends BaseEventEmitter<Object> implements Copier, EventObserver<Object> {
    private final MemoizableObjectCopier delegate;

    CommonObjectCopier() {
        delegate = new MemoizableObjectCopier();
    }


    @Override
    public Object copy(Object obj) throws ObjectCopyException {
        Object copy = delegate.copy(obj);
        delegate.clearMemo();
        return copy;
    }

    @Override
    public void onEvent(Event<Object> evt) {
        delegate.onEvent(evt);
    }

    @Override
    public void registerObserver(EventObserver<Object> obs, EventType type) {
        delegate.registerObserver(obs, type);
    }
}
