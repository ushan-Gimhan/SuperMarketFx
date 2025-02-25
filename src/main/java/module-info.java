module com.service.Project {
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires com.jfoenix;
    requires net.sf.jasperreports.core;
    requires java.mail;
    requires java.desktop;
    requires javafx.controls;

    opens com.service.Project.model.tm to javafx.base;
    opens com.service.Project.controller to javafx.fxml;
    exports com.service.Project;
}