package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
import GUI.controllers.ClassGradesController;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ClassItemController {
    @FXML
    private AnchorPane StudentInfoBox;

    @FXML
    private AnchorPane ClassBox;

    @FXML
    private Label SubjectLabel;

    @FXML
    private Label ClassIDLabel;

    @FXML
    private Label TeacherLabel;

    @FXML
    private Label FacultyLabel;

    @FXML
    private Label AttendantsLabel;

    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    public static StackPane container = NavigationController.containerNav;

    public void setData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.studentList = studentList;
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        ClassIDLabel.setText(subjectClass.getClassId());
        SubjectLabel.setText(subject.getSubjectName());
        FacultyLabel.setText("Faculty: " + subject.getFaculty());
        TeacherLabel.setText("Teacher: " + teacher.getName());
        AttendantsLabel.setText("Attendants: " + studentList.size());
    }

    @FXML
    void showClass(MouseEvent event) throws IOException {
        URL urlLayout = new File("src/GUI/resources/ClassGrades.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(urlLayout);
        Node item = fxmlLoader.load();

        ClassGradesController classGradesController = fxmlLoader.getController();
        classGradesController.setData(studentList, teacher, subject, subjectClass);

        container.getChildren().addAll(item);
    }
}
