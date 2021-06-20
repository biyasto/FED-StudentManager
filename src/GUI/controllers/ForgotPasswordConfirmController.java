package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ForgotPasswordConfirmController implements Initializable {
    public Label NameLabel;
    public Label PositionLabel;
    public Label IDLabel;
    public Label FacilityLabel;
    public Label EmailLabel;
    public Button ContinueButton;
    public Button ForwardButton;
    public Text StatusText; //text để thông báo tìm password thành công

    public static StudentDTO student = ForgotPasswordFindAccountController.student;
    public static TeacherDTO teacher = ForgotPasswordFindAccountController.teacher;
    private static StudentBLL studentBll = new StudentBLL();
    private static TeacherBLL teacherBll = new TeacherBLL();

    @FXML
    void ResetPassword(ActionEvent event) {
        if (student != null) {
            int r = studentBll.UpdatePassword(student.getId(), student.getId());
            StatusText.setVisible(true);
            ForwardButton.setVisible(true);
        } else if (teacher != null) {
            teacher.setPass(teacher.getId());
            int r = teacherBll.UpdatePassword(teacher);
            StatusText.setVisible(true);
            ForwardButton.setVisible(true);
        }

    }

    @FXML
    void ForwardToLogin(ActionEvent event) throws InterruptedException, IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //set delay for smooth animation
        TimeUnit.SECONDS.sleep(1);
        stage.close();

        URL url = new File("src/GUI/resources/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (student != null) {
            PositionLabel.setText("Student");
            IDLabel.setText(student.getId());
            EmailLabel.setText(student.getEmail());
            NameLabel.setText(student.getName());
            FacilityLabel.setText(student.getFaculty());
        } else if (teacher != null) {
            PositionLabel.setText("Teacher");
            IDLabel.setText(teacher.getId());
            EmailLabel.setText(teacher.getEmail());
            NameLabel.setText(teacher.getName());
            FacilityLabel.setText(teacher.getFaculty());
        }
    }
}
