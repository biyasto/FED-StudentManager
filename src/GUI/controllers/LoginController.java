package GUI.controllers;

import BusinessLogicLayer.LoginBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

public class LoginController {

    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField UserTextField;

    @FXML
    private Button LoginButton;

    @FXML
    private Hyperlink ForgetPasswordLink;

    @FXML
    private Label lblSuccessful;

    @FXML
    private Label lblFail;

    @FXML
    private Label lblEmpty;

    @FXML
    void login(MouseEvent event) throws IOException {
        String userName = UserTextField.getText();
        String password = PasswordField.getText();

        if(userName.isEmpty() || password.isEmpty()) {
            //empty information
            lblSuccessful.setVisible(false);
            lblFail.setVisible(false);
            lblEmpty.setVisible(true);
        }
        else {
            if(userName.equals("admin") && password.equals("admin")) {
                //call main menu of admin account
            }
            else {
                //connect to database and execute query
                LoginBLL loginBLL = new LoginBLL();
                String resultStudent = loginBLL.checkStudentLogin(userName, password);
                String resultTeacher = loginBLL.checkTeacherLogin(userName, password);

                String rsId = "";
                String rsUserType = "";

                if(resultStudent.isEmpty() && resultTeacher.isEmpty()) {
                    //wrong userName or Password
                    lblSuccessful.setVisible(false);
                    lblFail.setVisible(true);
                    lblEmpty.setVisible(false);
                }
                else {

                    //problem: cannot show label Successful
                    //TAO THỀ LÀ TAO ĐÉO HIỂU TẠI SAO LUÔN ???
                    lblFail.setVisible(false);
                    lblEmpty.setVisible(false);
                    lblSuccessful.setVisible(true);

                    if(!resultStudent.isEmpty()) {
                        //student account
                        String[] rsStudent = resultStudent.split("/");
                        rsId = rsStudent[0];
                        rsUserType = rsStudent[1];
                    }
                    else {
                        //teacher account
                        String[] rsTeacher = resultTeacher.split("/");
                        rsId = rsTeacher[0];
                        rsUserType = rsTeacher[1];
                    }

                    try {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();

                        //set delay for smooth animation
                        TimeUnit.SECONDS.sleep(2);
                        stage.close();

                        URL url = new File("src/GUI/resources/MainMenu.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    void callForm(int type) {

    }


}
