package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentItemController {
    @FXML
    private AnchorPane StudentInfoBox;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private ImageView Avatar;

    public void setDataStudent(StudentDTO student) {
        PositionLabel.setText("Student");
        NameLabel.setText(student.getName());
        IDLabel.setText("ID: " + student.getId());
        FacultyLabel.setText("Faculty: " + student.getFaculty());
    }

    public void setDataTeacher(TeacherDTO teacher) {
        PositionLabel.setText("Teacher");
        NameLabel.setText(teacher.getName());
        IDLabel.setText("ID: " + teacher.getId());
        FacultyLabel.setText("Faculty: " + teacher.getFaculty());
    }

    public void openUserInfo(MouseEvent mouseEvent) {
        System.out.println("abc");
    }
}
