module com.exercise1.brunadonatoni_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens com.exercise1.brunadonatoni_comp228lab5 to javafx.fxml;
    exports com.exercise1.brunadonatoni_comp228lab5;
}