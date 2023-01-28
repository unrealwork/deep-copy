package org.ecwid.dev.factory;

public interface Factory<S, T> {
    T get(S request);

    final class Builders {
        private Builders() {
        }

        public static <R, S extends HandlerType<R>, T> FlyweightHandlerFactory.Builder<R, S, T> flyweight() {
            return FlyweightHandlerFactory.builder();
        }
    }
}
