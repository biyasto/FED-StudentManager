package GUI.controllers;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BarChartController implements Initializable{

    @FXML
    private AnchorPane chartPane;

    @FXML
    private BarChart<Integer, Integer> barChart;

    @FXML
    private Button btnBack;

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

    private final StackPane container = NavigationController.containerNav;
    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    XYChart.Series series1 = new XYChart.Series();
    List<XYChart.Data> data = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        data.add(new XYChart.Data<>("0 - 1", 0));
        data.add(new XYChart.Data<>("1 - 2", 0));
        data.add(new XYChart.Data<>("2 - 3", 0));
        data.add(new XYChart.Data<>("3 - 4", 0));
        data.add(new XYChart.Data<>("4 - 5", 0));
        data.add(new XYChart.Data<>("5 - 6", 0));
        data.add(new XYChart.Data<>("6 - 7", 0));
        data.add(new XYChart.Data<>("7 - 8", 0));
        data.add(new XYChart.Data<>("8 - 9", 0));
        data.add(new XYChart.Data<>("9 - 10", 0));
        series1.getData().addAll(data);
        barChart.getData().addAll(series1);
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(chartPane);
    }

    public void setData(String chartTitle, String xAxis, String yAxis, List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.studentList = studentList;
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        //Class label
        ClassID.setText(subjectClass.getClassId());
        SubjectName.setText(subject.getSubjectName());
        facultyName.setText("Faculty: " + subject.getFaculty());
        teacherName.setText("Teacher: " + teacher.getName());
        attendantNum.setText("Attendants: " + studentList.size());

        //Chart infor
        barChart.setTitle(chartTitle);
        barChart.getXAxis().setLabel(xAxis);
        barChart.getYAxis().setLabel(yAxis);

        createDataset(studentList);
//        addEventForChartColumns();
    }

    public void createDataset(List<StudentDTO> studentList){
        data.add(new XYChart.Data<>("0 - 1", 0));
        data.add(new XYChart.Data<>("1 - 2", 0));
        data.add(new XYChart.Data<>("2 - 3", 0));
        data.add(new XYChart.Data<>("3 - 4", 0));
        data.add(new XYChart.Data<>("4 - 5", 0));
        data.add(new XYChart.Data<>("5 - 6", 0));
        data.add(new XYChart.Data<>("6 - 7", 0));
        data.add(new XYChart.Data<>("7 - 8", 0));
        data.add(new XYChart.Data<>("8 - 9", 0));
        data.add(new XYChart.Data<>("9 - 10", 0));
        for (StudentDTO student : studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());
            double finalScore = transcriptOfOneStudent.getTotalScore() / 4;
            int roundedScore = (int)finalScore;

            switch (roundedScore){
                case 0:
                    data.get(0).setYValue((int)data.get(0).getYValue() + 1);
                    break;
                case 1:
                    data.get(1).setYValue((int)data.get(1).getYValue() + 1);
                    break;
                case 2:
                    data.get(2).setYValue((int)data.get(2).getYValue() + 1);
                    break;
                case 3:
                    data.get(3).setYValue((int)data.get(3).getYValue() + 1);
                    break;
                case 4:
                    data.get(4).setYValue((int)data.get(4).getYValue() + 1);
                    break;
                case 5:
                    data.get(5).setYValue((int)data.get(5).getYValue() + 1);
                    break;
                case 6:
                    data.get(6).setYValue((int)data.get(6).getYValue() + 1);
                    break;
                case 7:
                    data.get(7).setYValue((int)data.get(7).getYValue() + 1);
                    break;
                case 8:
                    data.get(8).setYValue((int)data.get(8).getYValue() + 1);
                    break;
                case 9:
                    data.get(9).setYValue((int)data.get(9).getYValue() + 1);
                    break;
            }
        }
//        barChart.getData().removeAll();
//        barChart.getData().addAll(series1);
    }

    public void addEventForChartColumns(){
        for (XYChart.Series<Integer, Integer> serie: barChart.getData()){
            for (XYChart.Data<Integer, Integer> item: serie.getData()){
                item.getNode().setOnMousePressed((MouseEvent event) -> {
                    System.out.println("you clicked " + item.toString() + serie.toString());
                });
            }
        }
    }
}
