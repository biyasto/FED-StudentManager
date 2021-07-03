package GUI.controllers;

import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.StudentGradeItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    @FXML
    private ImageView UserAvatar;

    @FXML
    private AnchorPane InfoBox;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label EmailLabel;

    public TeacherDTO teacherUser = NavigationController.teacherUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherUser = NavigationController.teacherUser;
        if(teacherUser != null) {
            //load data
            NameLabel.setText(teacherUser.getName());
            IDLabel.setText(teacherUser.getId());
            FacultyLabel.setText(teacherUser.getFaculty());
            EmailLabel.setText(teacherUser.getEmail());
            PositionLabel.setText("Teacher");
            teacherUser = null;
        }
        else {
            //load data
            NameLabel.setText("ADMIN");
            IDLabel.setText("ADMIN");
            FacultyLabel.setText("ADMIN");
            EmailLabel.setText("ADMIN");
            PositionLabel.setText("ADMIN");
        }
    }
}
