package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import DataTransferObject.StudentDTO;
import GUI.controllers.items.StudentItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchUsersController implements Initializable {
    @FXML
    private TextField SearchIDTextfield;

    @FXML
    private Button FindButton;

    @FXML
    private ChoiceBox<String> PositionChoiceBox;

    @FXML
    private TextField SearchNameTextfield;

    @FXML
    private VBox studentScrollPane;

    private List<StudentDTO> studentList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //data for Position
        String[] optionPosition = {
                "Teacher",
                "Student"
        };
        ObservableList<String> dataPosition = FXCollections.observableArrayList();
        dataPosition.setAll(optionPosition);
        PositionChoiceBox.setItems(dataPosition);
        //PositionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> position = newValue);

        //get all current students from database
        StudentBLL studentBLL = new StudentBLL();
        studentList = studentBLL.GetALlStudent();
        for(StudentDTO student: studentList) {
            try {
                URL urlLayout = new File("src/GUI/resources/items/StudentItem.fxml").toURI().toURL();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(urlLayout);
                Node item = fxmlLoader.load();

                StudentItemController studentItemController = fxmlLoader.getController();
                studentItemController.setData(student);

                studentScrollPane.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
