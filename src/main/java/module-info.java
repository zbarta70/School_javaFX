module com.phonebook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.school to javafx.fxml;
    exports com.school;
    requires itextpdf;
}
