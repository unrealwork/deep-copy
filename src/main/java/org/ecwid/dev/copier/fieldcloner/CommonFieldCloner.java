package org.ecwid.dev.copier.fieldcloner;

import org.ecwid.dev.copier.Copier;
import org.ecwid.dev.copier.ObjectCopyException;
import org.ecwid.dev.factory.Factory;

import java.lang.reflect.Field;

import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.COMMON;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.FINAL;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.PRIMITIVE;
import static org.ecwid.dev.copier.fieldcloner.FieldClonerType.REF_COPY;

class CommonFieldCloner implements FieldCloner {
    private final Factory<Field, FieldCloner> fieldClonerFactory;
    private final Copier copier;

    CommonFieldCloner(Copier copier) {
        fieldClonerFactory = Factory.Builders.<Field, FieldClonerType, FieldCloner>flyweight()
                .addSupplier(FINAL, this::finalFieldCloner)
                .addSupplier(PRIMITIVE, PrimitiveFieldCloner::get)
                .addSupplier(COMMON, this::commonCopyCloner)
                .addSupplier(REF_COPY, this::refCopyCloner)
                .handleOrder(FINAL, PRIMITIVE, REF_COPY, COMMON)
                .build();
        this.copier = copier;
    }

    @Override
    public void clone(Field field, Object src, Object dest) throws ObjectCopyException {
        fieldClonerFactory.get(field).clone(field, src, dest);
    }

    private FieldCloner refCopyCloner() {
        return new RefCopyFieldCloner();
    }

    private FieldCloner commonCopyCloner() {
        return new ObjectFieldCloner(copier);
    }

    private FieldCloner finalFieldCloner() {
        return new FinalFieldCloner(fieldClonerFactory::get);
    }
}
