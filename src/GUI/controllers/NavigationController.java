package GUI.controllers;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.application.Platform;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

    @FXML
    private ImageView btnClose;

    public static StudentDTO studentUser = LoginController.studentUser;
    public static TeacherDTO teacherUser = LoginController.teacherUser;

    //this is a reference of container above, use for pass data to another screen
    public static StackPane containerNav = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentUser != null) {

            CreateAccountButton.setVisible(false);
            SearchUsersButton.setVisible(false);
        }
        else if(teacherUser != null) {
            CreateAccountButton.setVisible(false);
        }
        else {

        }
        try {
            openInformationScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        containerNav = container;
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
        openInformationScreen();
    }

    @FXML
    void OpenScheduleScreen(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/Schedule_Calendar.fxml").toURI().toURL();
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

    void openInformationScreen() throws IOException {
        URL url = new File("src/GUI/resources/UserDetail.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }
    @FXML
    private void btnCloseAction(){
        Platform.exit();
    }

}
