package GUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    @FXML
    private Button EventBtn;

    @FXML
    private Button ClassBtn;

    @FXML
    private Button AccountBtn;

    @FXML
    private Button SubjectBtn;

    @FXML
    private StackPane createContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            openDefault();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void OpenAccount(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/CreateAccount.fxml").toURI().toURL();
        Parent createScreen = FXMLLoader.load(url);
        createContainer.getChildren().removeAll();
        createContainer.getChildren().setAll(createScreen);
    }

    @FXML
    void OpenClass(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/CreateClass.fxml").toURI().toURL();
        Parent createScreen = FXMLLoader.load(url);
        createContainer.getChildren().removeAll();
        createContainer.getChildren().setAll(createScreen);
    }

    @FXML
    void OpenSubject(MouseEvent event) {

    }

    @FXML
    void openEvent(MouseEvent event) throws IOException {
        openDefault();
    }

    private void openDefault() throws IOException {
        URL url = new File("src/GUI/resources/AddExamEvent.fxml").toURI().toURL();
        Parent createScreen = FXMLLoader.load(url);
        createContainer.getChildren().removeAll();
        createContainer.getChildren().setAll(createScreen);
    }

}
