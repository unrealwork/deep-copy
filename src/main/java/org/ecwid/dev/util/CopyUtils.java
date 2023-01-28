package org.ecwid.dev.util;

import org.ecwid.dev.copier.CloneData;
import org.ecwid.dev.copier.CopierEventType;
import org.ecwid.dev.copier.DeepObjectCopier;
import org.ecwid.dev.event.Event;

import java.util.function.BiConsumer;

public final class CopyUtils {
    private CopyUtils() {
    }

    public static <T> T deepCopy(T obj) {
        return deepCopy(obj, null);
    }

    public static <T> T deepCopy(T obj, BiConsumer<Object, Object> cloneCallback) {
        DeepObjectCopier deepObjectCopier = DeepObjectCopier.get();
        if (cloneCallback != null) {
            deepObjectCopier.registerObserver(
                    e -> onCloneCompletedEvent(e, cloneCallback),
                    CopierEventType.CLONE_COMPLETED
            );
        }
        return (T) deepObjectCopier.copy(obj);
    }

    private static void onCloneCompletedEvent(Event<Object> event, BiConsumer<Object, Object> cloneCallback) {
        CloneData data = (CloneData) event.data();
        cloneCallback.accept(data.getObject(), data.getCopy());
    }
}
