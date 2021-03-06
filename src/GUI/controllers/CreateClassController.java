package GUI.controllers;

import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
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
    private Text lblCoefficient;

    @FXML
    private ChoiceBox<String> FacultyChoiceBox;

    @FXML
    private ChoiceBox<String> TeacherChoiceBox;

    @FXML
    private Button btnCreate;

    @FXML
    private ChoiceBox<String> SchoolYearChoiceBox;

    @FXML
    private ChoiceBox<String> SemesterChoiceBox;

    @FXML
    private ChoiceBox<String> AttendanceChoiceBox;

    @FXML
    private ChoiceBox<String> QuizChoiceBox;

    @FXML
    private ChoiceBox<String> PracticeChoiceBox;

    @FXML
    private ChoiceBox<String> FinalChoiceBox;

    @FXML
    private Button btnBack;

    private final StackPane container = NavigationController.containerNav;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SubjectBLL subjectBLL = new SubjectBLL();
        List<SubjectDTO> subjectList = subjectBLL.GetAllSubject();

        TeacherBLL teacherBLL = new TeacherBLL();
        List<TeacherDTO> teacherList = teacherBLL.GetALlTeacher();

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
            //Data for subject choice box, whenever user change faculty, subject choice box will be updated automatically
            List<String> subjectListChoice = new ArrayList<>();
            for(SubjectDTO subject: subjectList) {
                if(subject.getFaculty() != null) {
                    if (subject.getFaculty().equals(newValue)){
                        subjectListChoice.add(subject.getSubjectID() + "-" + subject.getSubjectName());
                    }
                }
            }
            ObservableList<String> dataSubject = FXCollections.observableArrayList();
            dataSubject.setAll(subjectListChoice);
            SubjectChoiceBox.setItems(dataSubject);

            //Data for teacher choice box, whenever user change faculty, teacher choice box will be updated automatically
            List<String> teacherListChoice = new ArrayList<>();
            for(TeacherDTO teacher: teacherList) {
                if(teacher.getFaculty().equals(newValue)) {
                    teacherListChoice.add(teacher.getId() + "-" + teacher.getName());
                }
            }
            ObservableList<String> dataTeacher = FXCollections.observableArrayList();
            dataTeacher.setAll(teacherListChoice);
            TeacherChoiceBox.setItems(dataTeacher);
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

        //data for grade's coefficient
        String[] optionCoefficient = {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
        };
        ObservableList<String> dataCoefficient = FXCollections.observableArrayList();
        dataCoefficient.setAll(optionCoefficient);

        AttendanceChoiceBox.setItems(dataCoefficient);
        AttendanceChoiceBox.setValue("0");

        QuizChoiceBox.setItems(dataCoefficient);
        QuizChoiceBox.setValue("0");

        PracticeChoiceBox.setItems(dataCoefficient);
        PracticeChoiceBox.setValue("0");

        FinalChoiceBox.setItems(dataCoefficient);
        FinalChoiceBox.setValue("0");
    }

    @FXML
    void createClass(MouseEvent event) {
        String faculty = FacultyChoiceBox.getValue();
        String schoolYear = SchoolYearChoiceBox.getValue();
        String semester = SemesterChoiceBox.getValue();
        String classId = ClassID.getText();

        int Attendance = Integer.parseInt(AttendanceChoiceBox.getValue());
        int Quiz = Integer.parseInt(QuizChoiceBox.getValue());
        int Practice = Integer.parseInt(PracticeChoiceBox.getValue());
        int Final = Integer.parseInt(FinalChoiceBox.getValue());

        String subjectChoice = SubjectChoiceBox.getValue();
        String teacherChoice = TeacherChoiceBox.getValue();

        if(faculty == null
        || schoolYear == null
        || semester == null
        || classId.isEmpty()
        || subjectChoice == null
        || teacherChoice == null) {
            lblEmpty.setVisible(true);
            lblError.setVisible(false);
            lblSuccess.setVisible(false);
            lblCoefficient.setVisible(false);
        }
        else if((Attendance + Quiz + Practice + Final) != 10) {
            lblEmpty.setVisible(false);
            lblError.setVisible(false);
            lblSuccess.setVisible(false);
            lblCoefficient.setVisible(true);
        }
        else {
            String[] strSubject = subjectChoice.split("-");
            String subjectId = strSubject[0];

            String[] strTeacher = teacherChoice.split("-");
            String teacherId = strTeacher[0];

            SubjectClassDTO subjectClassDTO = new SubjectClassDTO(
                    classId,
                    teacherId,
                    subjectId,
                    Integer.parseInt(schoolYear),
                    Integer.parseInt(semester),
                    Attendance,
                    Quiz,
                    Practice,
                    Final
            );

            SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
            int result = subjectClassBLL.InsertSubjectClass(subjectClassDTO);

            if(result != -1) {
                lblEmpty.setVisible(false);
                lblError.setVisible(false);
                lblSuccess.setVisible(true);
                lblCoefficient.setVisible(false);
            }
            else {
                lblEmpty.setVisible(false);
                lblError.setVisible(true);
                lblSuccess.setVisible(false);
                lblCoefficient.setVisible(false);
            }
        }
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(CreateClassPane);
    }

}
