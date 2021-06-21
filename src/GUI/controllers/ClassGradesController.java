package GUI.controllers;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.ClassGradeItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.List;

public class ClassGradesController {

    @FXML
    private AnchorPane classPane;

    @FXML
    private Label SubjectName;

    @FXML
    private Label ClassID;

    @FXML
    private Label teacherName;

    @FXML
    private Label attendantNum;

    @FXML
    private Label facultyName;

    @FXML
    private Button SaveButton;

    @FXML
    private Button UpdateButton;

    @FXML
    private ScrollPane GradesPanel;

    @FXML
    private VBox studentGrades;

    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    private StackPane container = NavigationController.containerNav;

    public void setData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.studentList = studentList;
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        ClassID.setText(subjectClass.getClassId());
        SubjectName.setText(subject.getSubjectName());
        facultyName.setText("Faculty: " + subject.getFaculty());
        teacherName.setText("Teacher: " + teacher.getName());
        attendantNum.setText("Attendants: " + studentList.size());

        showClass();
    }

    void showClass() {
        for(StudentDTO student: studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

            try {
                URL urlLayout = new File("src/GUI/resources/items/ClassGradeItem.fxml").toURI().toURL();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(urlLayout);
                Node item = fxmlLoader.load();

                ClassGradeItemController classGradeItemController = fxmlLoader.getController();
                classGradeItemController.setData(student, transcriptOfOneStudent);

                studentGrades.getChildren().addAll(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(classPane);
    }
}
