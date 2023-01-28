package org.ecwid.dev.factory;

public interface HandlerFactory<R, S extends HandlerType<R>, T> extends Factory<R, T> {
    T getByType(S type);
}
