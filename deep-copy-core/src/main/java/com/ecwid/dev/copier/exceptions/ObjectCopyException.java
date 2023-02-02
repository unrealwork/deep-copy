package com.ecwid.dev.copier.exceptions;

import java.lang.reflect.Field;

public class ObjectCopyException extends Exception {
   public ObjectCopyException(Object obj, Throwable cause) {
        super("Unable to copy object " + obj + " of class: " + obj.getClass().getCanonicalName() + ".", cause);
    }

    public ObjectCopyException(Field field, Object obj, Throwable cause) {
        super("Unable to clone field " + field + " of object " + obj + " of class " + obj.getClass() + ".", cause);
    }

    ObjectCopyException(String message, Throwable cause) {
        super(message, cause);
    }
}
