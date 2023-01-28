package org.ecwid.dev.event;

public interface Event<T> {
    EventType type();

    T data();
}
