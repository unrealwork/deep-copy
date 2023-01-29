package org.ecwid.dev.copier;

import org.ecwid.dev.event.BaseEventEmitter;
import org.ecwid.dev.event.Event;
import org.ecwid.dev.event.EventObserver;
import org.ecwid.dev.event.EventType;

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
