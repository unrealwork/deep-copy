package com.ecwid.dev.copier;

class NoOpCopier implements Copier {
    
    private NoOpCopier() {
        
    }

    static NoOpCopier create() {
        return new NoOpCopier();
    }

    @Override
    public Object copy(Object obj) {
        return obj;
    }
}
