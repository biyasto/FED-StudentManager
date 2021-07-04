package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ChangePasswordController {
    @FXML
    private AnchorPane changePassPane;

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
    private Button btnConfimChangePass;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblError;

    private StudentDTO studentDTO = null;
    private TeacherDTO teacherDTO = null;

    public void setData(StudentDTO studentDTO, TeacherDTO teacherDTO) {
        if(studentDTO != null) {
            this.studentDTO = studentDTO;
            lblCurPass.setText(studentDTO.getPass());
        }
        else {
            this.teacherDTO = teacherDTO;
            lblCurPass.setText(teacherDTO.getPass());
        }
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
            if(studentDTO != null) {
                StudentBLL studentBLL = new StudentBLL();
                result = studentBLL.UpdatePassword(studentDTO.getId(), password);
            }
            else if(teacherDTO != null) {
                TeacherDTO temp = teacherDTO;
                temp.setPass(password);
                TeacherBLL teacherBLL = new TeacherBLL();
                result = teacherBLL.UpdatePassword(temp);
            }
            if(result != -1) {
                lblEmpty.setVisible(false);
                lblError.setVisible(false);
                lblSuccess.setVisible(true);
                lblCurPass.setText(password);
            }
            else {
                lblEmpty.setVisible(false);
                lblError.setVisible(true);
                lblSuccess.setVisible(false);
            }
        }
    }

}
