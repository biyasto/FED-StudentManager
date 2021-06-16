package GUI.controllers.items;

import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    public void setData(TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        ClassIDLabel.setText(subjectClass.getClassId());
        SubjectLabel.setText(subject.getSubjectName());
        FacultyLabel.setText(subject.getFaculty());
        TeacherLabel.setText(teacher.getName());
    }

}
