package GUI.controllers;

import BusinessLogicLayer.SubjectBLL;
import DataTransferObject.SubjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateClassController implements Initializable {

    @FXML
    private AnchorPane CreateClassPane;

    @FXML
    private TextField ClassID;

    @FXML
    private ChoiceBox<String> SubjectChoiceBox;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblError;

    @FXML
    private Text lblWrongPass;

    @FXML
    private ChoiceBox<String> FacultyChoiceBox;

    @FXML
    private TextField TeacherID;

    @FXML
    private Button btnCreate;

    @FXML
    private ChoiceBox<String> SchoolYearChoiceBox;

    @FXML
    private ChoiceBox<String> SemesterChoiceBox;

    @FXML
    private Button btnBack;

    private final StackPane container = NavigationController.containerNav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SubjectBLL subjectBLL = new SubjectBLL();
        List<SubjectDTO> subjectList = subjectBLL.GetAllSubject();

        List<String> facultyList = new ArrayList<>();
        for(SubjectDTO subject: subjectList) {
            if(!facultyList.contains(subject.getFaculty())) {
                facultyList.add(subject.getFaculty());
            }
        }

        //data for faculty
        ObservableList<String> dataFaculty = FXCollections.observableArrayList();
        dataFaculty.setAll(facultyList);
        FacultyChoiceBox.setItems(dataFaculty);

        FacultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //whenever user change faculty, subject choice box will be updated
            List<String> subjectListChoice = new ArrayList<>();
            for(SubjectDTO subject: subjectList) {
                if(subject.getFaculty().equals(newValue)) {
                    subjectListChoice.add(subject.getSubjectName());
                }
            }

            ObservableList<String> dataSubject = FXCollections.observableArrayList();
            dataSubject.setAll(subjectListChoice);
            SubjectChoiceBox.setItems(dataSubject);
        });

        //data for school year
        String[] optionSchoolYear = {"2019", "2020", "2021","2022","2023","2024","2025"};
        ObservableList<String> dataSchoolYear = FXCollections.observableArrayList();
        dataSchoolYear.setAll(optionSchoolYear);
        SchoolYearChoiceBox.setItems(dataSchoolYear);

        //data for semester
        String[] optionSemester = {
                "1",
                "2"
        };
        ObservableList<String> dataSemester = FXCollections.observableArrayList();
        dataSemester.setAll(optionSemester);
        SemesterChoiceBox.setItems(dataSemester);
    }

    @FXML
    void createClass(MouseEvent event) {

    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(CreateClassPane);
    }

}
