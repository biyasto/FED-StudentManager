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
    private  Button Minimizebtn;

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
    private Button LogoutButton;

    @FXML
    private StackPane container;

    @FXML
    private Button btnClose;

    @FXML
    private Button GradesButton;

    public static StudentDTO studentUser = LoginController.studentUser;
    public static TeacherDTO teacherUser = LoginController.teacherUser;

    //this is a reference of container above, use for pass data to another screen
    public static StackPane containerNav = null;

    public NavigationController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentUser != null) {
            CreateAccountButton.setVisible(false);
            SearchUsersButton.setVisible(false);
        }
        else if(teacherUser != null) {
            GradesButton.setVisible(false);
            CreateAccountButton.setVisible(false);
        }
        else {
            GradesButton.setVisible(false);
        }
        try {
            openInformationScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        containerNav = container;
        SetStyle();
    }



    @FXML
    void OpenCreateAccountScreen(MouseEvent event) throws IOException {

        URL url = new File("src/GUI/resources/Create.fxml").toURI().toURL();
        Parent createScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createScreen);

    }

    @FXML
    void OpenInformationScreen(MouseEvent event) throws IOException {
        openInformationScreen();

    }

    @FXML
    void OpenScheduleScreen(MouseEvent event) throws IOException {

        URL url = new File("src/GUI/resources/Schedule_Calendar.fxml").toURI().toURL();
        Parent createScheduleScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createScheduleScreen);

    }

    @FXML
    void OpenSearchClassesScreen(MouseEvent event) throws IOException {

        URL url = new File("src/GUI/resources/Search_Classes.fxml").toURI().toURL();
        Parent createSearchClassesScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createSearchClassesScreen);

    }

    @FXML
    void OpenSearchUserScreen(MouseEvent event) throws IOException {

        URL url = new File("src/GUI/resources/Search_Users.fxml").toURI().toURL();
        Parent createSearchUserScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createSearchUserScreen);


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
    void btnCloseAction(MouseEvent event) {
        System.out.println("close");
        System.exit(0);
    }
    @FXML
    public void btnMinimizeAction(MouseEvent mouseEvent) {

    }

    @FXML
    void Logout(MouseEvent event) {

    }
    void SetStyle()
    {
        InfoButton.setStyle("");
        ScheduleButton.setStyle("");
        SearchClassButton.setStyle("");
        SearchUsersButton.setStyle("");
        CreateAccountButton.setStyle("");
    }


    public void OpenGradesScreen(MouseEvent mouseEvent) throws IOException {
        URL url = new File("src/GUI/resources/MyGrade.fxml").toURI().toURL();
        Parent myGradeScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(myGradeScreen);
    }
}
