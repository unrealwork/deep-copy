package com.ecwid.dev.factory;

/**
 * Factory that allows to get provide handler by special key of {@link HandlerType}
 *
 * @param <R> Type of object that should be handled
 * @param <S> Handler type
 * @param <T> Provided type
 */
interface HandlerFactory<R, S extends HandlerType<R>, T> extends Factory<R, T> {
    /**
     * Provide handler by type
     *
     * @param type Type of handler
     * @return Handler instance
     */
    T getByType(S type);
}
