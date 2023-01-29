package org.ecwid.dev.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseEventEmitter<T> {

    private final Map<EventType, List<EventObserver<T>>> observerLists;

    protected BaseEventEmitter() {
        observerLists = new HashMap<>();
    }

    public final void registerObserver(EventObserver<T> obs, EventType type) {
        observerLists.computeIfAbsent(type, t -> new ArrayList<>())
                .add(obs);
    }

    protected void notifyObservers(Event<T> e) {
        observerLists.getOrDefault(e.type(), Collections.emptyList())
                .forEach(obs -> obs.onEvent(e));
    }
}
