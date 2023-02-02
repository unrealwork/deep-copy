package com.ecwid.dev.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

    private final Map<K, V> map;
    private final boolean immutable;

    private MapBuilder(boolean immutable) {
        this.immutable = immutable;
        this.map = new HashMap<>();
    }

    public static <K, V> MapBuilder<K, V> immutable() {
        return new MapBuilder<>(true);
    }

    public static <K, V> MapBuilder<K, V> mutable() {
        return new MapBuilder<>(false);
    }

    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        if (immutable) {
            return Collections.unmodifiableMap(map);
        }

        return map;
    }
}
