package GUI.controllers.items;

import DataTransferObject.*;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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

    private String startTime = null;
    private ExamScheduleDTO examSchedule = null;
    private SubjectDTO subject = null;

    public static StackPane container = NavigationController.containerNav;

    public void setData(SubjectDTO subject, ExamScheduleDTO examSchedule) {
        this.subject = subject;
        this.examSchedule = examSchedule;

        if (examSchedule.getFlag() == 0) {
            lblTypeExam.setText("Mid-term Examination");
        } else if (examSchedule.getFlag() == 1) {
            lblTypeExam.setText("Final Examination");
        }
        lblSubjectName.setText(subject.getSubjectName());
        lblSubjectId.setText(subject.getSubjectID());
        lblFaculty.setText(subject.getFaculty());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        lblEventDate.setText(df.format(examSchedule.getExamDate()));

        int shift = examSchedule.getShift();
        lblShift.setText(String.valueOf(shift));

        lblStartTime.setText(loadStartTime(examSchedule.getShift()));

        lblEndTime.setText(loadEndTime(examSchedule.getShift(),examSchedule.getTotalTime()));
    }

    public String loadStartTime(int shift) {
        switch (shift) {
            case 1:
                startTime = "07:30:00";
                break;
            case 2:
                startTime = "09:30:00";
                break;
            case 3:
                startTime = "13:30:00";
                break;
            case 4:
                startTime = "15:30:00";
                break;
        }
        return startTime;
    }

    public String loadEndTime(int shift, Time total) {
        int start = timeStringToInt(startTime);
        int end = timeStringToInt(total.toString());
        int sumMinute = start + end;
        Time timeEnd = Time.valueOf(LocalTime.of(sumMinute/60, sumMinute%60, 0));
        return timeEnd.toString();
    }
    public static int timeStringToInt(String time)
    {
        String[]  convertStringArr = new String[3];
        convertStringArr = time.split(":");
        int sum =0;
        sum = Integer.parseInt(convertStringArr[0])*60 + Integer.parseInt(convertStringArr[1]) +Integer.parseInt(convertStringArr[2])/60;
        return sum;
    }

}