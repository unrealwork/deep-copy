package org.ecwid.dev.copier;

import org.ecwid.dev.event.Event;

import java.util.function.BiConsumer;

/**
 * Describes class creating copy of the object
 */
public interface Copier {
    /**
     * Copy source object.
     *
     * @param obj source object.
     * @return copied object.
     * @throws ObjectCopyException in case of errors during copy creation
     */
    Object copy(Object obj) throws ObjectCopyException;

    static Copier deep() {
        return deep(null);
    }

    static Copier deep(BiConsumer<Object, Object> cloneCallback) {
        DeepObjectCopier deepObjectCopier = new DeepObjectCopier();
        if (cloneCallback != null) {
            deepObjectCopier.registerObserver(
                    e -> onCloneCompletedEvent(e, cloneCallback),
                    CopierEventType.CLONE_COMPLETED
            );
        }
        return deepObjectCopier;
    }

    private static void onCloneCompletedEvent(Event<Object> event, BiConsumer<Object, Object> cloneCallback) {
        CloneData data = (CloneData) event.data();
        cloneCallback.accept(data.getObject(), data.getCopy());
    }
}
