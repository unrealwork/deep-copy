package com.ecwid.dev.event;

/**
 * Describes classes observing some events
 *
 * @param <T> event data type
 */
public interface EventObserver<T> {
    /**
     * Handling point
     *
     * @param evt occurred event
     */
    void onEvent(Event<T> evt);
}
