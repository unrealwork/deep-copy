package org.ecwid.dev.copier;

class NoOpCopier implements Copier {
    @Override
    public Object copy(Object obj) {
        return obj;
    }
}
