package org.ecwid.dev.copier;

import org.ecwid.dev.event.Event;
import org.ecwid.dev.event.EventType;

final class CopierEvent implements Event<Object> {
    private final Object data;
    private final CopierEventType type;

    private CopierEvent(CopierEventType type, Object data) {
        this.data = data;
        this.type = type;
    }

    public static Event<Object> cloneCompleted(Object obj, Object copy) {
        return new CopierEvent(CopierEventType.CLONE_COMPLETED, CloneData.create(obj, copy));
    }

    static CopierEvent objectCreated(Object obj, Object clone) {
        return new CopierEvent(CopierEventType.INSTANCE_CREATED, CloneData.create(obj, clone));
    }

    @Override
    public EventType type() {
        return type;
    }

    @Override
    public Object data() {
        return data;
    }
}
