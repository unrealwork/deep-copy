package com.ecwid.dev.factory;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

final class FlyweightHandlerFactory<R, S extends Enum<S> & HandlerType<R>, T> implements HandlerFactory<R, S, T> {
    private final Map<S, Supplier<T>> supplierMap;
    private final List<S> handleOrder;

    private final Map<S, T> storage;

    private FlyweightHandlerFactory(Class<S> clz, Map<S, Supplier<T>> supplierMap, List<S> handleOrder) {
        this.supplierMap = supplierMap;
        this.handleOrder = handleOrder;
        this.storage = new EnumMap<>(clz);
    }

    public static <R, S extends Enum<S> & HandlerType<R>, T> Builder<R, S, T> builder(Class<S> clz) {
        return new Builder<>(clz);
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

    public static final class Builder<R, S extends Enum<S> & HandlerType<R>, T> {
        private final EnumMap<S, Supplier<T>> supplierMap;
        private final Class<S> clz;
        private List<S> handleOrder;

        private Builder(Class<S> clz) {
            this.clz = clz;
            this.supplierMap = new EnumMap<>(clz);
        }

        public Builder<R, S, T> addSupplier(S type, Supplier<T> supplier) {
            this.supplierMap.put(type, supplier);
            return this;
        }

        @SafeVarargs
        public final Builder<R, S, T> handleOrder(S... handleOrder) {
            this.handleOrder = List.of(handleOrder);
            return this;
        }

        public Factory<R, T> build() {
            return new FlyweightHandlerFactory<>(clz, supplierMap, handleOrder);
        }
    }
}
