package org.ecwid.dev.copier;

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

    /**
     * Create copier for objects that performs deep copy.
     *
     * @return Copier instance
     */
    static Copier create() {
        return create(null);
    }


    /**
     * Create copier for objects that performs deep copy and allows to call back on each object's cloning that happens during deep copy of object
     *
     * @param cloneCallback callback for each object copy is completed during deep copy of the object. First param of callback is source object and second cloned object.
     * @return Copier that allows to call back on each object's cloning that happens during deep copy of object.
     */
    static Copier create(BiConsumer<Object, Object> cloneCallback) {
        CommonObjectCopier commonObjectCopier = new CommonObjectCopier();
        if (cloneCallback != null) {
            commonObjectCopier.registerObserver(
                    e -> {
                        CloneData data = (CloneData) e.data();
                        cloneCallback.accept(data.getObject(), data.getCopy());
                    },
                    CopierEventType.CLONE_COMPLETED
            );
        }
        return commonObjectCopier;
    }
}
