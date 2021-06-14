package GUI.controllers;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.net.URL;
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

    public static StudentDTO studentUser = NavigationController.studentUser;
    public static TeacherDTO teacherUser = NavigationController.teacherUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentUser != null) {
            //load data
            NameLabel.setText(studentUser.getName());
            IDLabel.setText(studentUser.getId());
            FacultyLabel.setText(studentUser.getFaculty());
            EmailLabel.setText(studentUser.getEmail());
            PositionLabel.setText("Student");

            //disable feature

        }
        else if(teacherUser != null) {
            //load data
            NameLabel.setText(teacherUser.getName());
            IDLabel.setText(teacherUser.getId());
            FacultyLabel.setText(teacherUser.getFaculty());
            EmailLabel.setText(teacherUser.getEmail());
            PositionLabel.setText("Teacher");
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
