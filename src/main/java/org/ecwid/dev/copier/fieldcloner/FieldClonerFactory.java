package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.factory.Factory;

import java.lang.reflect.Field;

import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.COMMON;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.FINAL;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.PRIMITIVE;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.REF_COPY;

public final class FieldClonerFactory implements Factory<Field, FieldCloner> {
    private final Copier copier;

    private final Factory<Field, FieldCloner> delegate;

    private FieldClonerFactory(Copier copier) {
        this.delegate = Factory.Builders.<Field, FieldClonerType, FieldCloner>flyweight()
                .addSupplier(FINAL, this::finalFieldCloner)
                .addSupplier(PRIMITIVE, PrimitiveFieldCloner::get)
                .addSupplier(COMMON, this::commonCopyCloner)
                .addSupplier(REF_COPY, this::refCopyCloner)
                .handleOrder(FINAL, PRIMITIVE, REF_COPY, COMMON)
                .build();
        this.copier = copier;
    }

    public static FieldClonerFactory withCopier(Copier copier) {
        return new FieldClonerFactory(copier);
    }

    @Override
    public FieldCloner get(Field field) {
        return delegate.get(field);
    }

    private FieldCloner refCopyCloner() {
        return new RefCopyFieldCloner();
    }

    private FieldCloner commonCopyCloner() {
        return new CommonFieldCloner(copier);
    }

    private FieldCloner finalFieldCloner() {
        return new FinalFieldCloner(this::get);
    }
}
