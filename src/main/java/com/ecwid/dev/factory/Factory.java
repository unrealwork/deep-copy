package com.ecwid.dev.factory;

/**
 * General factory interface that provide an object from family of objects
 *
 * @param <S> key for retrieving an object
 * @param <T> type of objects that can be provided by the factory
 */
public interface Factory<S, T> {
    /**
     * Retrieve object by some key.
     *
     * @param request key to decide which instance should factory provide.
     * @return provided instance
     */
    T get(S request);

    /**
     * Different builders for factories.
     */
    final class Builders {
        private Builders() {
        }

        /**
         * Flyweight factory that caches provided objects by some type.
         *
         * @param <R> type of key to retrieve object
         * @param <S> type of caching key for generated
         * @param <T> type of retrieved object
         * @return instance of provided object
         */
        public static <R, S extends HandlerType<R>, T> FlyweightHandlerFactory.Builder<R, S, T> flyweight() {
            return FlyweightHandlerFactory.builder();
        }
    }
}
