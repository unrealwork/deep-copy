package org.ecwid.dev.factory;

public interface HandlerType<T> {
    boolean canHandle(T req);
}
