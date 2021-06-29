package GUI.controllers;

import BusinessLogicLayer.*;
import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentDTO;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddExamEventController implements Initializable {


    @FXML
    private AnchorPane AddExamEventPane;

    @FXML
    private ChoiceBox<String> choiceBoxFlag;

    @FXML
    private ChoiceBox<String> choiceBoxShift;

    @FXML
    private ChoiceBox<Integer> choiceBoxTotalTime;

    @FXML
    private ChoiceBox<SubjectDTO> choiceBoxSubject;

    @FXML
    private ChoiceBox<String> choiceBoxFaculty;

    @FXML
    private ChoiceBox<Integer> choiceBoxSemester;

    @FXML
    private ChoiceBox<Integer> choiceBoxYear;

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

    private final List<String> rooms = Arrays.asList(
            "A1.1", "A1.2", "A1.3", "A1.4", "A2.1", "A2.2", "A2.3", "A2.4",
            "B1.1", "B1.2", "B1.3", "B1.4", "B2.1", "B2.2", "B2.3", "B2.4",
            "C1.1", "C1.2", "C1.3", "C1.4", "C2.1", "C2.2", "C2.3", "C2.4",
            "E1.1", "E1.2", "E1.3", "E1.4", "E2.1", "E2.2", "E2.3", "E2.4"
    );
    private int numberOfStudentInOneRoom = 20;

    private final StackPane container = NavigationController.containerNav;
    List<SubjectDTO> subjects;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    void back(MouseEvent event) {
        container.getChildren().remove(AddExamEventPane);
    }

    @FXML
    void createEvent(MouseEvent event) {
        if (choiceBoxFaculty.getSelectionModel().getSelectedItem() == null ||
                choiceBoxSubject.getSelectionModel().getSelectedItem() == null ||
                choiceBoxYear.getSelectionModel().getSelectedItem() == null ||
                choiceBoxSemester.getSelectionModel().getSelectedItem() == null ||
                choiceBoxFlag.getSelectionModel().getSelectedItem() == null ||
                choiceBoxShift.getSelectionModel().getSelectedItem() == null ||
                choiceBoxTotalTime.getSelectionModel().getSelectedItem() == null ||
                examDate.getValue() == null){
            showLblEmpty();
        }else{
            ExamScheduleDTO exam = new ExamScheduleDTO();
            int selectedIndex;

            exam.setSubjectId(choiceBoxSubject.getValue().getSubjectID());
            exam.setSchoolYear(choiceBoxYear.getValue());
            exam.setSemester(choiceBoxSemester.getValue());

            selectedIndex = choiceBoxFlag.getSelectionModel().getSelectedIndex();
            exam.setFlag(selectedIndex);

            selectedIndex = choiceBoxShift.getSelectionModel().getSelectedIndex();
            exam.setShift(selectedIndex + 1);

            exam.setExamDate(Date.valueOf(examDate.getValue()));

            int minutes = choiceBoxTotalTime.getValue();
            exam.setTotalTime(Time.valueOf(LocalTime.of(minutes/60, minutes%60, 0)));
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
        loadChoiceBoxSchoolYear();
        loadChoiceBoxSemester();
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
    }

    boolean arrangeStudentIntoExamRoom(ExamScheduleDTO examScheduleDTO){
        List<SubjectClassDTO> subjectClasses = new SubjectClassBLL().findClassesForExam(
                examScheduleDTO.getSubjectId(),
                examScheduleDTO.getSchoolYear(),
                examScheduleDTO.getSemester()
        );
        List<StudentDTO> students = new ArrayList<>();

        //Find student in this exam
        for (SubjectClassDTO subjectClass : subjectClasses) {
            students.addAll(new StudentBLL().getStudentsByClassId(subjectClass.getClassId()));
        }

        //Find empty room
        List<String> emptyRooms = new ExamRoomBLL().getEmptyRoomForExam(examScheduleDTO);

        //Check if not enough rooms for exam
        int numberOfExamRooms = (students.size() / numberOfStudentInOneRoom + 1);

        if (numberOfExamRooms <= emptyRooms.size()){
            ExamRoomBLL examRoomBLL = new ExamRoomBLL();
            int examScheduleId = new ExamScheduleBLL().getExamScheduleId(examScheduleDTO);

            for (int i = 0; i < numberOfExamRooms; i++) {
                examRoomBLL.addExamRoom(examScheduleId, emptyRooms.get(i));
            }
            return true;
        }else{
            return false;
        }
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

    void loadChoiceBoxSchoolYear(){
        ObservableList<Integer> values = FXCollections.observableArrayList();
        int curentYear = LocalDate.now().getYear();
        for (int i = curentYear; i < curentYear + 5; i++) {
            values.add(i);
        }
        choiceBoxYear.setItems(values);
    }

    void loadChoiceBoxSemester(){
        ObservableList<Integer> values = FXCollections.observableArrayList(1, 2);
        choiceBoxSemester.setItems(values);
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
        //format Date picker to display
        StringConverter<LocalDate> converter = new StringConverter<>() {
            final DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        examDate.setConverter(converter);
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
