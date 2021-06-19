package GUI.controllers.items;

import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserInfor_admin_Controller {

    @FXML
    private AnchorPane inforPane;

    @FXML
    private AnchorPane panelMenu;

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
    private Button btnGetGrade;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnGradeStatistic;

    @FXML
    private Button btnChangePassword;

    //component of grade panel

    @FXML
    private AnchorPane panelGrade;

    @FXML
    private VBox gradeScrollPane;

    @FXML
    private Button btnBack_pnGrade;

    private StudentDTO student = null;
    private StackPane container = NavigationController.containerNav;

    private List<StudentCLassDTO> studentCLassList = new ArrayList<>();

    public void setData(StudentDTO student) {
        this.student = student;

        //set data for main menu of student's information
        NameLabel.setText(student.getName());
        IDLabel.setText(student.getId());
        FacultyLabel.setText(student.getFaculty());
        EmailLabel.setText(student.getEmail());

        setDataForGradePane(student);
    }

    private void setDataForGradePane(StudentDTO student) {
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
                    List<TranscriptDTO> transcriptOfStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

                    bindData(subjectClass, subject, transcriptOfStudent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void bindData(SubjectClassDTO subjectClass, SubjectDTO subject, List<TranscriptDTO> transcriptOfStudent) throws IOException {
        URL urlLayout = new File("src/GUI/resources/items/StudentGradeItem.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        StudentGradeItemController studentGradeItemController = fxmlLoader.getController();
        studentGradeItemController.setData(subjectClass, subject, transcriptOfStudent);

        gradeScrollPane.getChildren().addAll(item);
    }

    @FXML
    void openStudentGrade(MouseEvent event) {
        panelGrade.setVisible(true);
    }

    @FXML
    void openStudentStatistic(MouseEvent event) {

    }

    @FXML
    void changePassword(MouseEvent event) {

    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(inforPane);
    }

    @FXML
    void backToPrevious(MouseEvent event) {
        panelGrade.setVisible(false);
    }
}
