package com.ecwid.dev.copier.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import static com.ecwid.dev.copier.util.ModulesUtil.MODULE;

public class MessagesUtil {

    private MessagesUtil() {
    }

    private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("Messages");

    public static String restrictedModuleAccess(Class<?> clz) {
        final String moduleName = clz.getModule().getName();
        String messageTemplate = MESSAGES_BUNDLE.getString("restricted_module_access");
        final String libModuleName;
        if (MODULE.isNamed()) {
            libModuleName = MODULE.getName();
        } else {
            libModuleName = "ALL-UNNAMED";
        }
        return MessageFormat.format(messageTemplate,
                clz.getPackageName(), moduleName, libModuleName, clz.getCanonicalName());
    }

}
