package GUI.controllers.Charts;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.NavigationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ClassScoresBarChartController implements Initializable{

    @FXML
    private AnchorPane chartPane;

    @FXML
    private BarChart<String, Number> barChart;

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

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    List<XYChart.Data<String, Number>> data = new ArrayList<>();

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

        series1.setName("Number Of Student");
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
        barChart.setStyle("-fx-font-size: " + 15 + "px;");
        barChart.getXAxis().setLabel(xAxis);
        barChart.getYAxis().setLabel(yAxis);

        createDataset(studentList);
        for (int i = 0; i < 10; i++) {
            final XYChart.Data<String, Number> columnData = data.get(i);
            columnData.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observableValue, Node oldNode, Node node) {
                    if (node != null){
//                        setNodeStyle(columnData);
                        displayLabelForData(columnData);
                    }
                }
            });
            series1.getData().add(columnData);
        }
//        series1.getData().setAll(data);
        barChart.getData().setAll(series1);
    }

    public void createDataset(List<StudentDTO> studentList){

        int maxCount = 0;
        for (StudentDTO student : studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

            double finalScore = transcriptOfOneStudent.getTotalScore() / 4;
            int roundedScore = (int)finalScore;

            //count number of score range
            if (roundedScore >= 0 && roundedScore <= 10){
                data.get(roundedScore).setYValue((int) data.get(roundedScore).getYValue() + 1);
            }
        }
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = ((int) data.getYValue() != 0) ? new Text(data.getYValue() + "") : new Text("");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }

    /** Change color of bar if value of i is <5 then red, if >5 then green if i>8 then blue */
    private void setNodeStyle(XYChart.Data<String, Number> data) {
        Node node = data.getNode();
        if (data.getYValue().intValue() > 8) {
            node.setStyle("-fx-bar-fill: -fx-exceeded;");
        } else if (data.getYValue().intValue() > 5) {
            node.setStyle("-fx-bar-fill: -fx-achieved;");
        } else {
            node.setStyle("-fx-bar-fill: -fx-not-achieved;");
        }
    }

    public void addEventForChartColumns(){
        for (XYChart.Series<String, Number> serie: barChart.getData()){
            for (XYChart.Data<String, Number> item: serie.getData()){
                item.getNode().setOnMousePressed((MouseEvent event) -> {
                    System.out.println("you clicked " + item.toString() + serie.toString());
                });
            }
        }
    }
}
