package GUI.controllers.items;

import BusinessLogicLayer.*;
import DataTransferObject.*;
import GUI.controllers.MyGradeController;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UserInfor_admin_Controller {

    @FXML
    private AnchorPane inforPane;

    @FXML
    private AnchorPane panelMenu;

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

    //component of change password panel

    @FXML
    private AnchorPane panelChangePassword;

    @FXML
    private Button btnBack_pnChangePass;

    @FXML
    private Label lblCurPass;

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtConfirmPass;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblError;

    private StudentDTO student = null;
    private TeacherDTO teacher = null;
    private StackPane container = NavigationController.containerNav;

    public void setData(StudentDTO student, TeacherDTO teacher) {
        if(student != null) {
            //set data for main menu of student's information
            this.student = student;
            NameLabel.setText(student.getName());
            IDLabel.setText(student.getId());
            FacultyLabel.setText(student.getFaculty());
            EmailLabel.setText(student.getEmail());
            lblCurPass.setText(student.getPass());
            PositionLabel.setText("Student");
            if(!student.isGender()) {
                File file = new File("src/GUI/asset/Picture/UserAvatar2.png");
                Image image = new Image(file.toURI().toString());
                UserAvatar.setImage(image);
            }
        }
        else if( teacher != null) {
            //set data for main menu of student's information
            this.teacher = teacher;
            NameLabel.setText(teacher.getName());
            IDLabel.setText(teacher.getId());
            FacultyLabel.setText(teacher.getFaculty());
            EmailLabel.setText(teacher.getEmail());
            lblCurPass.setText(teacher.getPass());
            PositionLabel.setText("Teacher");
            btnGetGrade.setVisible(false);
            if(!teacher.isGender()) {
                File file = new File("src/GUI/asset/Picture/UserAvatar2.png");
                Image image = new Image(file.toURI().toString());
                UserAvatar.setImage(image);
            }
        }
    }

    @FXML
    void openStudentGrade(MouseEvent event) throws IOException {
        URL urlLayout = new File("src/GUI/resources/MyGrade.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        MyGradeController myGradeController = fxmlLoader.getController();
        myGradeController.setData(student);

        container.getChildren().addAll(item);
    }

    @FXML
    void openChangePasswordPane(MouseEvent event) {
        panelMenu.setVisible(false);
        panelChangePassword.setVisible(true);
    }

    @FXML
    void changePassword(MouseEvent event) {
        String password = txtPass.getText();
        String confirmPass = txtConfirmPass.getText();
        if(password.isEmpty() || confirmPass.isEmpty()) {
            lblEmpty.setVisible(true);
            lblError.setVisible(false);
            lblSuccess.setVisible(false);
        }
        else if(!password.equals(confirmPass)) {
            lblEmpty.setVisible(false);
            lblError.setVisible(true);
            lblSuccess.setVisible(false);
        }
        else {
            int result = -1;

            if(student != null) {
                StudentBLL studentBLL = new StudentBLL();
                result = studentBLL.UpdatePassword(IDLabel.getText(), password);
            }
            else if(teacher != null) {
                TeacherDTO teacherTemp = teacher;
                TeacherBLL teacherBLL = new TeacherBLL();
                teacherTemp.setPass(password);
                result = teacherBLL.UpdatePassword(teacherTemp);
            }

            if(result != -1) {
                lblEmpty.setVisible(false);
                lblError.setVisible(false);
                lblSuccess.setVisible(true);
                afterUpdateSuccess(password);
            }
            else {
                lblEmpty.setVisible(false);
                lblError.setVisible(true);
                lblSuccess.setVisible(false);
            }
        }
    }

    private void afterUpdateSuccess(String newPassword) {
        lblCurPass.setText(newPassword);
        txtPass.clear();
        txtConfirmPass.clear();

        if(student != null)
            student.setPass(newPassword);
        else if(teacher != null)
            teacher.setPass(newPassword);
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(inforPane);
    }

    @FXML
    void backToMenu(MouseEvent event) {
        panelMenu.setVisible(true);
        panelChangePassword.setVisible(false);
    }
}
