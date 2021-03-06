package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginController {

    public static StudentDTO studentUser = null;
    public static TeacherDTO teacherUser = null;
    public static boolean isAdmin = false;
    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField UserTextField;

    @FXML
    private Button LoginButton;

    @FXML
    private Hyperlink ForgetPasswordLink;

    @FXML
    private Button hideButton;

    @FXML
    private Button CloseButton;

    @FXML
    private Label lblSuccessful;

    @FXML
    private Label lblFail;

    @FXML
    private Label lblEmpty;

    @FXML
    void login(ActionEvent event) {
        String userName = UserTextField.getText();
        String password = PasswordField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            //empty information
            lblSuccessful.setVisible(false);
            lblFail.setVisible(false);
            lblEmpty.setVisible(true);
        } else {
            if (userName.equals("admin") && password.equals("admin")) {
                isAdmin = true;
                //call main menu of admin account
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();

                    //set delay for smooth animation
                    TimeUnit.SECONDS.sleep(1);
                    stage.close();

                    URL url = new File("src/GUI/resources/Navigation.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //connect to database and execute query
                TeacherBLL teacherBLL = new TeacherBLL();
                StudentBLL studentBLL = new StudentBLL();

                studentUser = studentBLL.GetStudent(userName, password);
                teacherUser = teacherBLL.GetTeacher(userName, password);

                if (studentUser == null && teacherUser == null) {
                    //wrong userName or Password
                    lblSuccessful.setVisible(false);
                    lblFail.setVisible(true);
                    lblEmpty.setVisible(false);
                } else {
                    lblFail.setVisible(false);
                    lblEmpty.setVisible(false);
                    lblSuccessful.setVisible(true);

                    try {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();

                        //set delay for smooth animation
                        TimeUnit.SECONDS.sleep(1);
                        stage.close();

                        URL urlLayout = new File("src/GUI/resources/Navigation.fxml").toURI().toURL();
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(urlLayout);
                        Parent item = fxmlLoader.load();

                        NavigationController navigationController = fxmlLoader.getController();
                        if (studentUser != null)
                            navigationController.setData(studentUser, null);
                        else if (teacherUser != null)
                            navigationController.setData(null, teacherUser);

                        stage.setScene(new Scene(item));
                        stage.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @FXML
    public void HyperLinkAction(ActionEvent event) throws InterruptedException, IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //set delay for smooth animation
        TimeUnit.SECONDS.sleep(1);
        stage.close();

        URL url = new File("src/GUI/resources/ForgotPassword_FindAccount.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void hideButtonAction() {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

}
