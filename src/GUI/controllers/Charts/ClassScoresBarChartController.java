package GUI.controllers.Charts;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.NavigationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassScoresBarChartController implements Initializable{

    @FXML
    private AnchorPane chartPane;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

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
    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    XYChart.Series<String, Number> series3 = new XYChart.Series<>();
    List<XYChart.Data<String, Number>> data1 = new ArrayList<>();
    List<XYChart.Data<String, Number>> data2 = new ArrayList<>();
    List<XYChart.Data<String, Number>> data3 = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data1.add(new XYChart.Data<>("0 - 1", 0));
        data1.add(new XYChart.Data<>("1 - 2", 0));
        data1.add(new XYChart.Data<>("2 - 3", 0));
        data1.add(new XYChart.Data<>("3 - 4", 0));
        data1.add(new XYChart.Data<>("4 - 5", 0));
        data2.add(new XYChart.Data<>("5 - 6", 0));
        data2.add(new XYChart.Data<>("6 - 7", 0));
        data2.add(new XYChart.Data<>("7 - 8", 0));
        data3.add(new XYChart.Data<>("8 - 9", 0));
        data3.add(new XYChart.Data<>("9 - 10", 0));

        series1.setName("0 - 5");
        series2.setName("5 - 8");
        series3.setName("8 - 10");
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

        for (int i = 0; i < 5; i++) {
            final XYChart.Data<String, Number> columnData = data1.get(i);
            columnData.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observableValue, Node oldNode, Node node) {
                    if (node != null){
                        setNodeStyle(columnData);
                        displayLabelForData(columnData);
                    }
                }
            });
            series1.getData().add(columnData);
        }
        for (int i = 5; i < 8; i++) {
            final XYChart.Data<String, Number> columnData = data2.get(i - 5);
            columnData.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observableValue, Node oldNode, Node node) {
                    if (node != null){
                        setNodeStyle(columnData);
                        displayLabelForData(columnData);
                    }
                }
            });
            series2.getData().add(columnData);
        }
        for (int i = 8; i < 10; i++) {
            final XYChart.Data<String, Number> columnData = data3.get(i - 8);
            columnData.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observableValue, Node oldNode, Node node) {
                    if (node != null){
                        setNodeStyle(columnData);
                        displayLabelForData(columnData);
                    }
                }
            });
            series3.getData().add(columnData);
        }
        barChart.getData().setAll(series1, series2, series3);

    }

    public void createDataset(List<StudentDTO> studentList){

        int maxCount = 0;
        for (StudentDTO student : studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

            double avg = new TranscriptBLL().calGPAByTranscriptId(transcriptOfOneStudent.getTranscriptId());

            if (avg >= 0 && avg <= 1){
                data1.get(0).setYValue((int) data1.get(0).getYValue() + 1);
                if (maxCount < data1.get(0).getYValue().intValue()){
                    maxCount = data1.get(0).getYValue().intValue();
                }
            }else if (avg <= 2){
                data1.get(1).setYValue((int) data1.get(1).getYValue() + 1);
                if (maxCount < data1.get(1).getYValue().intValue()){
                    maxCount = data1.get(1).getYValue().intValue();
                }
            }else if (avg <= 3){
                data1.get(2).setYValue((int) data1.get(2).getYValue() + 1);
                if (maxCount < data1.get(2).getYValue().intValue()){
                    maxCount = data1.get(2).getYValue().intValue();
                }
            }else if (avg <= 4){
                data1.get(3).setYValue((int) data1.get(3).getYValue() + 1);
                if (maxCount < data1.get(3).getYValue().intValue()){
                    maxCount = data1.get(3).getYValue().intValue();
                }
            }else if (avg <= 5){
                data1.get(4).setYValue((int) data1.get(4).getYValue() + 1);
                if (maxCount < data1.get(4).getYValue().intValue()){
                    maxCount = data1.get(4).getYValue().intValue();
                }
            }else if (avg <= 6){
                data2.get(0).setYValue((int) data2.get(0).getYValue() + 1);
                if (maxCount < data2.get(0).getYValue().intValue()){
                    maxCount = data2.get(0).getYValue().intValue();
                }
            }else if (avg <= 7){
                data2.get(1).setYValue((int) data2.get(1).getYValue() + 1);
                if (maxCount < data2.get(1).getYValue().intValue()){
                    maxCount = data2.get(1).getYValue().intValue();
                }
            }else if (avg <= 8){
                data2.get(2).setYValue((int) data2.get(2).getYValue() + 1);
                if (maxCount < data2.get(2).getYValue().intValue()){
                    maxCount = data2.get(2).getYValue().intValue();
                }
            }else if (avg <= 9){
                data3.get(0).setYValue((int) data3.get(0).getYValue() + 1);
                if (maxCount < data3.get(0).getYValue().intValue()){
                    maxCount = data3.get(0).getYValue().intValue();
                }
            }else if (avg <= 10){
                data3.get(1).setYValue((int) data3.get(1).getYValue() + 1);
                if (maxCount < data3.get(1).getYValue().intValue()){
                    maxCount = data3.get(1).getYValue().intValue();
                }
            }
        }

        //change bar width
        xAxis.categorySpacingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                recalculateBarPositions();
            }
        });
//        yAxis.setAutoRanging(true);
        yAxis.setUpperBound(maxCount + 2);
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        DecimalFormat df = new DecimalFormat("#.#");

        final Text dataText = ((int) data.getYValue() != 0) ? new Text(df.format(data.getYValue())) : new Text("");
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
        if (Integer.parseInt(String.valueOf(data.getXValue().charAt(0))) >= 8) {
            node.setStyle("-fx-bar-fill: green;");
        } else if (Integer.parseInt(String.valueOf(data.getXValue().charAt(0))) >= 5) {
            node.setStyle("-fx-bar-fill: orange;");
        } else {
            node.setStyle("-fx-bar-fill: red;");
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

    private void recalculateBarPositions() {
        final int seriesCount = barChart.getData().size();
        final double categoryHeight = xAxis.getCategorySpacing() - barChart.getCategoryGap();
        final double originalBarHeight = categoryHeight / seriesCount;
        final double barTranslateX = originalBarHeight * (seriesCount - 1) / 2;

        //find the (bar) node associated with each series data and adjust its size and position
        barChart.getData().forEach(numberStringSeries ->
                numberStringSeries.getData().forEach(numberStringData -> {
                    Node node = numberStringData.getNode();

                    node.setScaleX(3);
                    //translate the node vertically to center it around the category label
                    node.setTranslateX(16);
                })
        );
    }
}
