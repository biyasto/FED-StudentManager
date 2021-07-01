package GUI.controllers;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.concurrent.TimeUnit;

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
    public static boolean isAdmin = LoginController.isAdmin;

    //this is a reference of container above, use for pass data to another screen
    public static StackPane containerNav = null;

    public NavigationController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerNav = container;
        if(studentUser != null) {
            CreateAccountButton.setVisible(false);
            SearchUsersButton.setVisible(false);
            try {
                openInformationStudentScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(teacherUser != null) {
            CreateAccountButton.setVisible(false);
            try {
                openInformationScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //admin cannot change password
            SettingButton.setVisible(false);
            try {
                openInformationScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        if(studentUser != null) {
            openInformationStudentScreen();
        }
        else {
            openInformationScreen();
        }
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
    void OpenSettingScreen(MouseEvent event) throws IOException {
        //change password
        URL urlLayout = new File("src/GUI/resources/ChangePassword.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        ChangePasswordController ChangePasswordController = fxmlLoader.getController();
        if(studentUser != null) {
            ChangePasswordController.setData(studentUser, null);
        }
        else if(teacherUser != null) {
            ChangePasswordController.setData(null, teacherUser);
        }

        container.getChildren().removeAll();
        container.getChildren().setAll(item);
    }

    void openInformationScreen() throws IOException {
        URL url = new File("src/GUI/resources/UserDetail.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().setAll(createAccountScreen);
    }

    void openInformationStudentScreen() throws IOException {
        URL url = new File("src/GUI/resources/MyGrade.fxml").toURI().toURL();
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
        Stage stage = (Stage) Minimizebtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void Logout(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            //set delay for smooth animation
            TimeUnit.SECONDS.sleep(1);
            stage.close();

            URL url = new File("src/GUI/resources/Login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void SetStyle()
    {
        InfoButton.setStyle("");
        ScheduleButton.setStyle("");
        SearchClassButton.setStyle("");
        SearchUsersButton.setStyle("");
        CreateAccountButton.setStyle("");
    }

}
