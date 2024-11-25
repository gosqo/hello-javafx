module org.gosqo.hellojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires org.glassfish.tyrus.client;
    requires org.glassfish.tyrus.server;
    requires org.glassfish.tyrus.core;
    requires org.glassfish.tyrus.spi;

    requires org.glassfish.tyrus.container.grizzly.server;
    requires org.glassfish.tyrus.container.grizzly.client;



    opens org.gosqo.hellojavafx to javafx.fxml;
    exports org.gosqo.hellojavafx;
}
