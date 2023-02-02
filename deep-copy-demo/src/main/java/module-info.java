module demo {
    requires com.ecwid.dev.deep.copy;
    requires java.logging;
    
    opens com.ecwid.dev.deep.copy.demo.classes to com.ecwid.dev.deep.copy;
}
