package org.ecwid.dev.copier;

public class NoOpCopier implements Copier {
    @Override
    public Object copy(Object obj) {
        return obj;
    }
}
