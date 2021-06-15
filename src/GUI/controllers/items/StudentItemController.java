package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    private StudentDTO studentDTO;

    public void setData(StudentDTO student) {
        studentDTO = student;
        System.out.println(studentDTO.getName());
        //NameLabel.setText(studentDTO.getName());
        //IDLabel.setText("ID: " + studentDTO.getId());
       // FacultyLabel.setText("Faculty: " + studentDTO.getFaculty());
    }

}
