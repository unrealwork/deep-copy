package com.ecwid.dev.factory;

/**
 * Describes type of abstract handler.
 * Able to verify applicability of the handler without creation it
 * via {@link HandlerType#canHandle(T req)} method.
 *
 * @param <T> type of object
 */
public interface HandlerType<T> {
    /**
     * Could handler of the type be applied to a request.
     *
     * @param req Request to check
     * @return true if applicable
     */
    boolean canHandle(T req);
}
