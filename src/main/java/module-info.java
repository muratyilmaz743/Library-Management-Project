module org.Library {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql;

    opens org.Library to javafx.fxml;
    exports org.Library;
}