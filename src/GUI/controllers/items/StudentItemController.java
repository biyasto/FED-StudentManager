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

    private StudentDTO studentDTO = null;
    private TeacherDTO teacherDTO = null;

    public void setDataStudent(StudentDTO student) {
        studentDTO = student;
        PositionLabel.setText("Student");
        NameLabel.setText(studentDTO.getName());
        IDLabel.setText("ID: " + studentDTO.getId());
        FacultyLabel.setText("Faculty: " + studentDTO.getFaculty());
    }

    public void setDataTeacher(TeacherDTO teacher) {
        teacherDTO = teacher;
        PositionLabel.setText("Teacher");
        NameLabel.setText(teacherDTO.getName());
        IDLabel.setText("ID: " + teacherDTO.getId());
        FacultyLabel.setText("Faculty: " + teacherDTO.getFaculty());
    }

    public void openUserInfo(MouseEvent mouseEvent) {
        System.out.println(studentDTO.getName());
    }
}
