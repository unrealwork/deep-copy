package com.ecwid.dev.event;

/**
 * Describes some event that could happen
 *
 * @param <T> event data type
 */
public interface Event<T> {
    /**
     * Type of the event.
     *
     * @return EventType instance
     */
    EventType type();

    /**
     * Some data related to the event
     *
     * @return T instance
     */
    T data();
}
