package org.ecwid.dev.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

final class FlyweightHandlerFactory<R, S extends HandlerType<R>, T> implements HandlerFactory<R, S, T> {
    private final Map<S, Supplier<T>> supplierMap;
    private final List<S> handleOrder;

    private final Map<S, T> storage;

    private FlyweightHandlerFactory(Map<S, Supplier<T>> supplierMap, List<S> handleOrder) {
        this.supplierMap = supplierMap;
        this.handleOrder = handleOrder;
        this.storage = new HashMap<>();
    }

    public static <R, S extends HandlerType<R>, T> Builder<R, S, T> builder() {
        return new Builder<>();
    }

    @Override

    public T get(R request) {
        for (S type : handleOrder) {
            if (type.canHandle(request)) {
                return getByType(type);
            }
        }
        throw new UnsupportedOperationException("Unsupported request to handle by factory: " + request);
    }


    @Override
    public T getByType(S type) {
        return storage.computeIfAbsent(type, this::supplyHandler);
    }

    private T supplyHandler(S t) {
        Supplier<T> supplier = supplierMap.get(t);
        if (supplier == null) {
            throw new IllegalStateException("Unable to find supplier for handler type " + t);
        }
        return supplier.get();
    }

    public static final class Builder<R, S extends HandlerType<R>, T> {
        private final Map<S, Supplier<T>> supplierMap;
        private List<S> handleOrder;

        private Builder() {
            this.supplierMap = new HashMap<>();
        }

        public Builder<R, S, T> addSupplier(S type, Supplier<T> supplier) {
            Builder.this.supplierMap.put(type, supplier);
            return this;
        }

        @SafeVarargs
        public final Builder<R, S, T> handleOrder(S... handleOrder) {
            Builder.this.handleOrder = List.of(handleOrder);
            return this;
        }

        public Factory<R, T> build() {
            return new FlyweightHandlerFactory<>(supplierMap, handleOrder);
        }
    }
}
