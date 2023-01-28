package org.ecwid.dev.event;

public interface EventObserver<T> {
    void onEvent(Event<T> e);
}
