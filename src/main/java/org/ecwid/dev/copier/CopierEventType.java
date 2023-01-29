package org.ecwid.dev.copier;

import org.ecwid.dev.event.EventType;

/**
 * Describes events occurring during object's copying.
 */
enum CopierEventType implements EventType {
    /**
     * New instance of object created.
     */
    INSTANCE_CREATED,
    /**
     * Full clone of object with all fields completed.
     */
    CLONE_COMPLETED;
}
