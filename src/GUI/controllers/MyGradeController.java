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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyGradeController implements Initializable {

    @FXML
    private AnchorPane inforPane;

    @FXML
    private AnchorPane panelGradeStatistic;

    @FXML
    private VBox gradeScrollPane;

    @FXML
    private ChoiceBox<String> SemesterChoiceBox;

    @FXML
    private ChoiceBox<String> SchoolYearChoiceBox;

    @FXML
    private Label lblSemesterGPA;

    @FXML
    private AnchorPane InfoBox;

    @FXML
    private Label NameLabel;

    @FXML
    private Label PositionLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label EmailLabel;

    @FXML
    private ImageView UserAvatar;

    @FXML
    private Label IDLabel;

    @FXML
    private Label lblCredit;

    @FXML
    private Label lblGPA;

    @FXML
    private Button RefeshButton;

    @FXML
    private Button btnPrintPDF;

    private StudentDTO studentUser = NavigationController.studentUser;

    private List<StudentCLassDTO> studentCLassList = new ArrayList<>();
    private final List<String> schoolList = new ArrayList<>();
    private final List<String> semesterList = new ArrayList<>();

    private String yearFilter = "All";
    private String semesterFilter = "All";
    private final DecimalFormat df = new DecimalFormat("##.#");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //load data for label
        NameLabel.setText(studentUser.getName());
        IDLabel.setText(studentUser.getId());
        FacultyLabel.setText(studentUser.getFaculty());
        EmailLabel.setText(studentUser.getEmail());
        PositionLabel.setText("Student");

        //load grade statistic
        loadGradeFirstTime(studentUser);

        //init combobox's data
        ObservableList<String> dataSchoolYear = FXCollections.observableArrayList();
        dataSchoolYear.setAll(schoolList);
        SchoolYearChoiceBox.setItems(dataSchoolYear);
        SchoolYearChoiceBox.setValue("All");          //set default value
        SchoolYearChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            yearFilter = newValue;
            loadGradeFilter();
        });

        ObservableList<String> dataSemester = FXCollections.observableArrayList();
        dataSemester.setAll(semesterList);
        SemesterChoiceBox.setItems(dataSemester);
        SemesterChoiceBox.setValue("All");          //set default value
        SemesterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            semesterFilter = newValue;
            loadGradeFilter();
        });
    }

    void loadGradeFirstTime(StudentDTO student) {
        schoolList.add("All");
        semesterList.add("All");

        double sumGPA = 0.0;
        int num = 0;
        int credit = 0;

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

                    if(!schoolList.contains(String.valueOf(subjectClass.getSchoolYear())))
                        schoolList.add(String.valueOf(subjectClass.getSchoolYear()));

                    if(!semesterList.contains(String.valueOf(subjectClass.getSemester())))
                        semesterList.add(String.valueOf(subjectClass.getSemester()));

                    sumGPA += calculateAvg(transcriptOfStudent);
                    num++;
                    credit += subject.getCredits();

                    bindData(subjectClass, subject, transcriptOfStudent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            lblCredit.setText("Accumulated credits: " + credit);
            if(num != 0) {
                double semesterGPA = sumGPA/num;
                lblGPA.setText("GPA: " + df.format(semesterGPA));
            }
        }
    }

    void loadGradeFilter() {
        double sumGPA = 0.0;
        int num = 0;
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

                    if(yearFilter.equals("All") && semesterFilter.equals("All")) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                    }
                    else if(yearFilter.equals("All") && String.valueOf(subjectClass.getSemester()).equals(semesterFilter)) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                        sumGPA += calculateAvg(transcriptOfStudent);
                        num++;
                    }
                    else if(semesterFilter.equals("All") && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                        bindData(subjectClass, subject, transcriptOfStudent);
                        sumGPA += calculateAvg(transcriptOfStudent);
                        num++;
                    }
                    else {
                        if(String.valueOf(subjectClass.getSemester()).equals(semesterFilter)
                        && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                            bindData(subjectClass, subject, transcriptOfStudent);
                            sumGPA += calculateAvg(transcriptOfStudent);
                            num++;
                        }
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(num != 0) {
                double semesterGPA = sumGPA/num;
                lblSemesterGPA.setText("semester GPA: " + df.format(semesterGPA));
            }
        }
    }

    double calculateAvg(TranscriptDTO transcript) {
        return transcript.getMark1() * 0.1
             + transcript.getMark2() * 0.2
             + transcript.getMark3() * 0.2
             + transcript.getMark4() * 0.5;
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

    @FXML
    void printPDF(MouseEvent event) {

    }

}
