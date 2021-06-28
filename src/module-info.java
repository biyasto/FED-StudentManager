module QuanLyHocSInh {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires itextpdf;
    requires jfreechart;
    opens GUI;
    opens GUI.resources;
    opens GUI.controllers;
    opens GUI.resources.items;
    opens GUI.controllers.items;
    opens Model;
}