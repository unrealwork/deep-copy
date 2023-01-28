package org.ecwid.dev.copier;

import org.ecwid.dev.event.Event;
import org.ecwid.dev.event.EventType;

public class CopierEvent implements Event<Object> {
    private final Object data;
    private final CopierEventType type;

    private CopierEvent(CopierEventType type, Object data) {
        this.data = data;
        this.type = type;
    }

    public static Event<Object> cloneCompleted(Object obj, Object copy) {
        return new CopierEvent(CopierEventType.CLONE_COMPLETED, CloneData.of(obj, copy));
    }

    @Override
    public EventType type() {
        return type;
    }

    @Override
    public Object data() {
        return data;
    }

    static CopierEvent objectCreated(Object obj, Object clone) {
        return new CopierEvent(CopierEventType.OBJECT_CREATED, CloneData.of(obj, clone));
    }
}
