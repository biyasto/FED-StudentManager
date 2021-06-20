package GUI.controllers;

import BusinessLogicLayer.ExamScheduleBLL;
import BusinessLogicLayer.SubjectClassBLL;
import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.SubjectClassDTO;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

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
    private DatePicker examDate;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnBack;

    private final StackPane container = NavigationController.containerNav;

    @FXML
    void back(MouseEvent event) {
        container.getChildren().remove(AddExamEventPane);
    }

    @FXML
    void createEvent(MouseEvent event) {
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
            //success
            System.out.println(result);

        }else{
            // loi
            System.out.println(result);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChoiceBoxClassId();
        loadChoiceBoxType();
        loadChoiceBoxShift();
        loadDefaultExamDate();
        loadChoiceBoxTotalTime();
    }

    void loadChoiceBoxClassId(){
        List<SubjectClassDTO> subjectClasses = new SubjectClassBLL().getAllSubjectClass();
        ObservableList<SubjectClassDTO> classes = FXCollections.observableArrayList(subjectClasses);
        choiceBoxClassId.setItems(classes);
    }

    void loadChoiceBoxType(){
        ObservableList<String> values = FXCollections.observableArrayList("Mid-term","Final");
        choiceBoxFlag.setItems(values);
    }

    void loadChoiceBoxShift(){
        ObservableList<String> values = FXCollections.observableArrayList(
        "Ca 1: bắt đầu 7h30",
            "Ca 2: bắt đầu 9h30",
            "Ca 3: bắt đầu 13h30",
            "Ca 4: bắt đầu 15h30"
        );
        choiceBoxShift.setItems(values);
    }

    void loadChoiceBoxTotalTime(){
        ObservableList<Integer> values = FXCollections.observableArrayList(45, 60, 90);
        choiceBoxTotalTime.setItems(values);
    }

    void loadDefaultExamDate(){
        Date date = new Date();
        examDate.setValue(LocalDate.of(2021,10,20));
    }
}
