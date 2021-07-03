package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
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

    private StudentDTO student;
    public static StackPane container = NavigationController.containerNav;

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

    public void openUserInfo(MouseEvent mouseEvent) throws IOException {
        URL urlLayout = new File("src/GUI/resources/items/UserInfor_admin.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        UserInfor_admin_Controller userInfor_admin_controller = fxmlLoader.getController();
        userInfor_admin_controller.setData(studentDTO);

        container.getChildren().addAll(item);
    }

}
