package GUI.controllers;

import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.StudentGradeItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    @FXML
    private ImageView UserAvatar;

    @FXML
    private AnchorPane InfoBox;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private AnchorPane panelGradeStatistic;

    @FXML
    private VBox gradeScrollPane;

    @FXML
    private ChoiceBox<Integer> SemesterChoiceBox;

    @FXML
    private ChoiceBox<Integer> SchoolYearChoiceBox;

    public static StudentDTO studentUser = NavigationController.studentUser;
    public static TeacherDTO teacherUser = NavigationController.teacherUser;

    private List<StudentCLassDTO> studentCLassList = new ArrayList<>();
    private final List<Integer> schoolList = new ArrayList<>();
    private final List<Integer> semesterList = new ArrayList<>();

    private int yearFilter = 0;
    private int semesterFilter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(studentUser != null) {
            //load data
            NameLabel.setText(studentUser.getName());
            IDLabel.setText(studentUser.getId());
            FacultyLabel.setText(studentUser.getFaculty());
            EmailLabel.setText(studentUser.getEmail());
            PositionLabel.setText("Student");

            //disable feature

            //load grade statistic
            loadGradeFirstTime(studentUser);

            //init combobox's data
            ObservableList<Integer> dataSchoolYear = FXCollections.observableArrayList();
            dataSchoolYear.setAll(schoolList);
            SchoolYearChoiceBox.setItems(dataSchoolYear);
            SchoolYearChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    yearFilter = newValue;
                    if(semesterFilter != 0) {
                        loadGradeFilter();
                    }
            });

            ObservableList<Integer> dataSemester = FXCollections.observableArrayList();
            dataSemester.setAll(semesterList);
            SemesterChoiceBox.setItems(dataSemester);
            SemesterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                semesterFilter = newValue;
                if(yearFilter != 0) {
                    loadGradeFilter();
                }
            });
        }
        else if(teacherUser != null) {
            //load data
            NameLabel.setText(teacherUser.getName());
            IDLabel.setText(teacherUser.getId());
            FacultyLabel.setText(teacherUser.getFaculty());
            EmailLabel.setText(teacherUser.getEmail());
            PositionLabel.setText("Teacher");
        }
        else {
            //load data
            NameLabel.setText("ADMIN");
            IDLabel.setText("ADMIN");
            FacultyLabel.setText("ADMIN");
            EmailLabel.setText("ADMIN");
            PositionLabel.setText("ADMIN");
        }
    }

    void loadGradeFirstTime(StudentDTO student) {
        StudentClassBLL studentClassBLL = new StudentClassBLL();
        studentCLassList = studentClassBLL.getAllClassOfStudent(student.getId());
        if(!studentCLassList.isEmpty()) {
            for(StudentCLassDTO studentCLass: studentCLassList) {
                try {
                    SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                    SubjectBLL subjectBLL = new SubjectBLL();
                    TranscriptBLL transcriptBLL = new TranscriptBLL();

                    //get classId and className to set data for grade item
                    SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                    SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                    TranscriptDTO transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

                    if(!schoolList.contains(subjectClass.getSchoolYear()))
                        schoolList.add(subjectClass.getSchoolYear());

                    if(!semesterList.contains(subjectClass.getSemester()))
                    semesterList.add(subjectClass.getSemester());

                    bindData(subjectClass, subject, transcriptOfStudent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void loadGradeFilter() {
        gradeScrollPane.getChildren().clear();
        if(!studentCLassList.isEmpty()) {
            for(StudentCLassDTO studentCLass: studentCLassList) {
                try {
                    SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                    SubjectBLL subjectBLL = new SubjectBLL();
                    TranscriptBLL transcriptBLL = new TranscriptBLL();

                    //get classId and className to set data for grade item
                    SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                    SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                    TranscriptDTO transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), studentUser.getId());

                    if(subjectClass.getSchoolYear() == yearFilter && subjectClass.getSemester() == semesterFilter) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void bindData(SubjectClassDTO subjectClass, SubjectDTO subject, TranscriptDTO transcriptOfStudent) throws IOException {
        URL urlLayout = new File("src/GUI/resources/items/StudentGradeItem.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        StudentGradeItemController studentGradeItemController = fxmlLoader.getController();
        studentGradeItemController.setData(subjectClass, subject, transcriptOfStudent);

        gradeScrollPane.getChildren().addAll(item);
    }

    public void Refresh(MouseEvent mouseEvent) {
    }
}
