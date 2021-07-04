module QuanLyHocSInh {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires mysql.connector.java;
    requires itextpdf;
    requires java.desktop;
    requires jfreechart;
    requires jcommon;
    opens GUI;
    opens GUI.resources;
    opens GUI.controllers;
    opens GUI.resources.items;
    opens GUI.controllers.items;
    opens GUI.resources.Charts;
    opens GUI.controllers.Charts;
    opens Model;
    opens Launcher;
}