package com.ecwid.dev.copier.fieldcloner;

import com.ecwid.dev.copier.ObjectCopyException;
import com.ecwid.dev.copier.Copier;
import com.ecwid.dev.factory.Factory;

import java.lang.reflect.Field;

class CommonFieldCloner implements FieldCloner {
    private final Factory<Field, FieldCloner> fieldClonerFactory;
    private final Copier copier;

    CommonFieldCloner(Copier copier) {
        fieldClonerFactory = Factory.Builders.<Field, FieldClonerType, FieldCloner>flyweight()
                .addSupplier(FieldClonerType.FINAL, this::finalFieldCloner)
                .addSupplier(FieldClonerType.PRIMITIVE, PrimitiveFieldCloner::get)
                .addSupplier(FieldClonerType.COMMON, this::commonCopyCloner)
                .addSupplier(FieldClonerType.REF_COPY, this::refCopyCloner)
                .handleOrder(FieldClonerType.FINAL, FieldClonerType.PRIMITIVE, FieldClonerType.REF_COPY, FieldClonerType.COMMON)
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
