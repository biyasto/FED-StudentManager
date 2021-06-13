package GUI.controllers;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {

    @FXML
    private Button SearchClassButton;

    @FXML
    private Button InfoButton;

    @FXML
    private Button ScheduleButton;

    @FXML
    private Button SearchUsersButton;

    @FXML
    private ImageView Avatar;

    @FXML
    private Label NameLabel;

    @FXML
    private Button CreateAccountButton;

    @FXML
    private Button SettingButton;

    @FXML
    private ImageView LogoutButton;

    @FXML
    private StackPane container;

    public static StudentDTO studentUser = LoginController.studentUser;
    public static TeacherDTO teacherUser = LoginController.teacherUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentUser != null) {
            NameLabel.setText(studentUser.getName());
            CreateAccountButton.setVisible(false);
            SearchUsersButton.setVisible(false);
        }
        else if(teacherUser != null) {
            NameLabel.setText(teacherUser.getName());
            CreateAccountButton.setVisible(false);
        }
        else {
            NameLabel.setText("ADMIN");
        }
    }

    @FXML
    void Logout(MouseEvent event) {

    }

    @FXML
    void OpenCreateAccountScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/CreateAccount.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    @FXML
    void OpenInformationScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/UserDetail.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    @FXML
    void OpenScheduleScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/Schedule_Event.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    @FXML
    void OpenSearchClassesScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/Search_Classes.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    @FXML
    void OpenSearchUserScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/Search_Users.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    @FXML
    void OpenSettingScreen(MouseEvent event) {

    }

}
