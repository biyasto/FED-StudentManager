package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class UserInfor_admin_Controller {

    @FXML
    private AnchorPane inforPane;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private ImageView UserAvatar;

    @FXML
    private Label IDLabel;

    @FXML
    private Button btnGetGrade;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnGradeStatistic;

    @FXML
    private Button btnChangePassword;

    private StudentDTO student = null;
    private StackPane container = NavigationController.containerNav;

    public void setData(StudentDTO student) {
        this.student = student;

        NameLabel.setText(student.getName());
        IDLabel.setText(student.getId());
        FacultyLabel.setText(student.getFaculty());
        EmailLabel.setText(student.getEmail());
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(inforPane);
    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void openStudentGrade(MouseEvent event) {

    }

    @FXML
    void openStudentStatistic(MouseEvent event) {

    }
}
