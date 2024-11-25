module org.gosqo.hellojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.gosqo.hellojavafx to javafx.fxml;
    exports org.gosqo.hellojavafx;
}