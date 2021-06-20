package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataAccessLayer.StudentDAL;
import DataAccessLayer.TeacherDAL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class ForgotPasswordFindAccountController {
    public TextField IDCodeTextfield;
    public Button NextButton;
    public TextField IDTextfield;
    public RadioButton TeacherRadiobutton;
    public RadioButton StudentRadiobutton;
    public Button BackButton;
    public Label AnnounceLabel;


    public static StudentDTO student;
    public static TeacherDTO teacher;
    private StudentBLL studentBll = new StudentBLL();
    private TeacherBLL teacherBll = new TeacherBLL();


    public static String IDENTIFICATION_CODE = "thaibinhdeptrai";

    @FXML
    void nextButtonEvent(ActionEvent event) throws InterruptedException, IOException {
        student = null;
        teacher = null;
        String ID = IDTextfield.getText();
        String IDCode = IDCodeTextfield.getText();
        student = studentBll.GetStudentByID(ID);
        teacher = teacherBll.GetTeacherByID(ID);
        if (TeacherRadiobutton.isSelected()) {
            if (ID.equals("")) {
                AnnounceLabel.setText("Please fill ID!");
                AnnounceLabel.setVisible(true);
            } else if (!IDCode.equals(IDENTIFICATION_CODE)) {
                AnnounceLabel.setText("Wrong identification code!");
                AnnounceLabel.setVisible(true);
            } else if (teacher == null) {
                AnnounceLabel.setText("Wrong ID!");
                AnnounceLabel.setVisible(true);
            } else {
                AnnounceLabel.setVisible(false);
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                //set delay for smooth animation
                TimeUnit.SECONDS.sleep(1);
                stage.close();

                URL url = new File("src/GUI/resources/ForgotPassword_Confirm.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                stage.setScene(new Scene(root));
                stage.show();
            }

        } else if (StudentRadiobutton.isSelected()) {
            if (ID.equals("")) {
                AnnounceLabel.setText("Please fill ID!");
                AnnounceLabel.setVisible(true);
            } else if (!IDCode.equals(IDENTIFICATION_CODE)) {
                AnnounceLabel.setText("Wrong identification code!");
                AnnounceLabel.setVisible(true);
            } else if (student == null) {
                AnnounceLabel.setText("Wrong ID!");
                AnnounceLabel.setVisible(true);
            } else {
                AnnounceLabel.setVisible(false);
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                //set delay for smooth animation
                TimeUnit.SECONDS.sleep(1);
                stage.close();

                URL url = new File("src/GUI/resources/ForgotPassword_Confirm.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                stage.setScene(new Scene(root));
                stage.show();
            }

        } else {
            AnnounceLabel.setText("Please choose gender!");
            AnnounceLabel.setVisible(true);
        }
    }
    @FXML
    void BackAction(ActionEvent event) throws InterruptedException, IOException {
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
}
