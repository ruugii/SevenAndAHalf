module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;

    opens application to javafx.fxml;
    opens application.controller to javafx.fxml;

    exports application.controller;
    exports application;
}