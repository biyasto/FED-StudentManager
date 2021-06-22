package GUI.controllers;

import BusinessLogicLayer.ExamScheduleBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AddExamEventController implements Initializable {


    @FXML
    private AnchorPane AddExamEventPane;

    @FXML
    private ChoiceBox<String> choiceBoxFlag;

    @FXML
    private ChoiceBox<SubjectClassDTO> choiceBoxClassId;

    @FXML
    private ChoiceBox<String> choiceBoxShift;

    @FXML
    private ChoiceBox<Integer> choiceBoxTotalTime;

    @FXML
    private ChoiceBox<SubjectDTO> choiceBoxSubject;

    @FXML
    private ChoiceBox<String> choiceBoxFaculty;

    @FXML
    private DatePicker examDate;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBack;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblError;

    private final StackPane container = NavigationController.containerNav;
    List<SubjectDTO> subjects;

    @FXML
    void back(MouseEvent event) {
        container.getChildren().remove(AddExamEventPane);
    }

    @FXML
    void createEvent(MouseEvent event) {
        if (choiceBoxFaculty.getSelectionModel().getSelectedItem() == null ||
                choiceBoxSubject.getSelectionModel().getSelectedItem() == null ||
                choiceBoxClassId.getSelectionModel().getSelectedItem() == null ||
                choiceBoxFlag.getSelectionModel().getSelectedItem() == null ||
                choiceBoxShift.getSelectionModel().getSelectedItem() == null ||
                choiceBoxTotalTime.getSelectionModel().getSelectedItem() == null || examDate.getValue() == null){
            showLblEmpty();
        }else{
            ExamScheduleDTO exam = new ExamScheduleDTO();
            int selectedIndex;

            exam.setClassId(choiceBoxClassId.getValue().getClassId());

            selectedIndex = choiceBoxFlag.getSelectionModel().getSelectedIndex();
            exam.setFlag(selectedIndex);

            selectedIndex = choiceBoxShift.getSelectionModel().getSelectedIndex();
            exam.setShift(selectedIndex + 1);

            int min = choiceBoxTotalTime.getValue();
            exam.setTotalTime(Time.valueOf(LocalTime.of(min/60, min%60, 0)));
            exam.setExamDate(java.sql.Date.valueOf(examDate.getValue()));

            int result = new ExamScheduleBLL().addNewEvent(exam);
            if (result >= 0){
                showLblSuccess();
                System.out.println("success "+result);

            }else{
                showLblError();
                System.out.println("error "+result);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxFaculty();
        loadChoiceBoxType();
        loadChoiceBoxShift();
        loadDefaultExamDate();
        loadChoiceBoxTotalTime();

        choiceBoxFaculty.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                String faculty = choiceBoxFaculty.getItems().get((Integer) t1);
                loadChoiceBoxSubject(faculty);
            }
        });
        choiceBoxSubject.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if ((Integer) t1 != -1){
                    SubjectDTO s = choiceBoxSubject.getItems().get((Integer) t1);
                    loadChoiceBoxClassId(s.getSubjectID());
                }
            }
        });
    }

    void loadChoiceBoxFaculty(){
        subjects = new SubjectBLL().GetAllSubject();
        List<String> faculties = new ArrayList<>();
        for (SubjectDTO s : subjects) {
            if (!faculties.contains(s.getFaculty()))
                faculties.add(s.getFaculty());
        }
        ObservableList<String> facultyList = FXCollections.observableArrayList(faculties);
        choiceBoxFaculty.setItems(facultyList);
    }

    void loadChoiceBoxSubject(String faculty){
        subjects = new SubjectBLL().GetAllSubject();
        List<SubjectDTO> subjectsWithFaculty = new ArrayList<>();
        for (SubjectDTO s : subjects) {
            if (s.getFaculty() != null){
                if (s.getFaculty().equals(faculty))
                    subjectsWithFaculty.add(s);
            }
        }
        ObservableList<SubjectDTO> subjectList = FXCollections.observableArrayList(subjectsWithFaculty);
        choiceBoxSubject.setItems(subjectList);
    }
    void loadChoiceBoxClassId(String subjectId){
        List<SubjectClassDTO> subjectClasses = new SubjectClassBLL().getAllSubjectClass();
        List<SubjectClassDTO> subjectClassWithSubjectId = new ArrayList<>();
        for (SubjectClassDTO s: subjectClasses) {
            if (s.getSubjectId().equals(subjectId)){
                subjectClassWithSubjectId.add(s);
            }
        }
        ObservableList<SubjectClassDTO> classes = FXCollections.observableArrayList(subjectClassWithSubjectId);
        choiceBoxClassId.setItems(classes);
    }

    void loadChoiceBoxType(){
        ObservableList<String> values = FXCollections.observableArrayList("Mid-term","Final");
        choiceBoxFlag.setItems(values);
    }

    void loadChoiceBoxShift(){
        ObservableList<String> values = FXCollections.observableArrayList(
        "Shift 1: start at 7h30",
            "Shift 2: start at 9h30",
            "Shift 3: start at 13h30",
            "Shift 4: start at 15h30"
        );
        choiceBoxShift.setItems(values);
    }

    void loadChoiceBoxTotalTime(){
        ObservableList<Integer> values = FXCollections.observableArrayList(45, 60, 90);
        choiceBoxTotalTime.setItems(values);
    }

    void loadDefaultExamDate(){
        Date date = new Date();
        examDate.setValue(LocalDate.now());
    }

    void showLblEmpty(){
        lblEmpty.setVisible(true);
        lblError.setVisible(false);
        lblSuccess.setVisible(false);
    }

    void showLblError(){
        lblError.setVisible(true);
        lblEmpty.setVisible(false);
        lblSuccess.setVisible(false);
    }

    void showLblSuccess(){
        lblSuccess.setVisible(true);
        lblEmpty.setVisible(false);
        lblError.setVisible(false);
    }
}
