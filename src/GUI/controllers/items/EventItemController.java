package GUI.controllers.items;

import DataTransferObject.*;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class EventItemController {

    @FXML
    private AnchorPane StudentInfoBox;

    @FXML
    private AnchorPane EventBox;

    @FXML
    private Label lblSubjectId;

    @FXML
    private Label lblFaculty;

    @FXML
    private Label lblEventDate;

    @FXML
    private Label lblShift;

    @FXML
    private Label lblStartTime;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblSubjectName;

    @FXML
    private Label lblTypeExam;

    private ExamScheduleDTO examSchedule = null;
    private SubjectDTO subject = null;

    public static StackPane container = NavigationController.containerNav;

    public void setData(SubjectDTO subject, ExamScheduleDTO examSchedule) {
        this.subject = subject;
        this.examSchedule = examSchedule;

        if (examSchedule.getFlag() == 0){
            lblTypeExam.setText("Mid-term Examination");
        }else if (examSchedule.getFlag() == 1){
            lblTypeExam.setText("Final Examination");
        }
        lblSubjectName.setText(subject.getSubjectName());
        lblSubjectId.setText(subject.getSubjectID());
        lblFaculty.setText(subject.getFaculty());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        lblEventDate.setText(df.format(examSchedule.getExamDate()));

        int shift = examSchedule.getShift();
        lblShift.setText(String.valueOf(shift));
    }
}