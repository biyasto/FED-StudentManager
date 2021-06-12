module QuanLyHocSInh {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    opens GUI;
    opens GUI.resources;
    opens GUI.controllers;
}