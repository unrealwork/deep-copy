package org.ecwid.dev.copier;

public interface Copier {
    Object copy(Object obj) throws ObjectCopyException;
}
