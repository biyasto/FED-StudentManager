package GUI.controllers;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.items.ClassGradeItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
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
    private Button btnAddStudent;

    @FXML
    private VBox studentGrades;

    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    private final StackPane container = NavigationController.containerNav;

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
    void inputGrades(MouseEvent event) {
        try {
            URL urlLayout = new File("src/GUI/resources/InputGrades.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            InputGradesController inputGradesController = fxmlLoader.getController();
            inputGradesController.setData(studentList, teacher, subject, subjectClass);

            container.getChildren().addAll(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addStudents(MouseEvent event) throws IOException {
        //open add students form
        URL urlLayout = new File("src/GUI/resources/AddStudent.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        AddStudentController addStudentController = fxmlLoader.getController();
        addStudentController.setData(studentList, teacher, subject, subjectClass);

        container.getChildren().add(item);
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(classPane);
    }
}
