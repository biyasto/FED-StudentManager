package GUI.controllers.Charts;

import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import GUI.controllers.NavigationController;
import com.itextpdf.text.pdf.PdfPTable;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentScoresBarChart implements Initializable {
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
    private AnchorPane InfoBox;

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
    private Label lblCredit;

    @FXML
    private Label lblGPA;

    private final StackPane container = NavigationController.containerNav;
    private StudentDTO studentUser = NavigationController.studentUser;
    private List<StudentCLassDTO> studentCLassList = new ArrayList<>();
    private String yearFilter = "";
    private String semesterFilter = "";
    private int credit = 0;

    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    List<XYChart.Data<String, Number>> data = new ArrayList<>();


    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(chartPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        series1.setName("Subject");
    }

    public void setData(String chartTitle,
                        String xAxis,
                        String yAxis,
                        StudentDTO studentUser,
                        List<StudentCLassDTO> studentCLassList,
                        String yearFilter ,
                        String semesterFilter,
                        int credit) {

        this.studentUser = studentUser;
        this.studentCLassList = studentCLassList;
        this.yearFilter = yearFilter;
        this.semesterFilter = semesterFilter;
        this.credit = credit;

        //Class label
        NameLabel.setText(studentUser.getName());
        IDLabel.setText(studentUser.getId());
        FacultyLabel.setText(studentUser.getFaculty());
        EmailLabel.setText(studentUser.getEmail());
        PositionLabel.setText("Student");

        //Chart infor
        barChart.setTitle(chartTitle);
        barChart.setStyle("-fx-font-size: " + 15 + "px;");
        barChart.getXAxis().setLabel(xAxis);
        barChart.getYAxis().setLabel(yAxis);

        createDataset(studentCLassList);
        for (int i = 0; i < studentCLassList.size(); i++) {
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
        barChart.getData().setAll(series1);
    }

    private void createDataset(List<StudentCLassDTO> studentCLassList) {
        for (StudentCLassDTO studentCLass : studentCLassList) {
            try {
                SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
                SubjectBLL subjectBLL = new SubjectBLL();
                TranscriptBLL transcriptBLL = new TranscriptBLL();

                //get classId and className to set data for grade item
                SubjectClassDTO subjectClass = subjectClassBLL.getClassById(studentCLass.getClassId());
                SubjectDTO subject = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
                TranscriptDTO transcript = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), studentUser.getId());

                if (yearFilter.equals("All") && semesterFilter.equals("All")) {
                    data.add(new XYChart.Data<>(subject.getSubjectName(),transcriptBLL.calGPAByTranscriptId(transcript.getTranscriptId())));
                    credit += subject.getCredits();
                } else if (yearFilter.equals("All") && String.valueOf(subjectClass.getSemester()).equals(semesterFilter)) {
                    data.add(new XYChart.Data<>(subject.getSubjectName(),transcriptBLL.calGPAByTranscriptId(transcript.getTranscriptId())));
                    credit += subject.getCredits();
                } else if (semesterFilter.equals("All") && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                    data.add(new XYChart.Data<>(subject.getSubjectName(), transcriptBLL.calGPAByTranscriptId(transcript.getTranscriptId())));
                    credit += subject.getCredits();
                } else {
                    if (String.valueOf(subjectClass.getSemester()).equals(semesterFilter)
                            && String.valueOf(subjectClass.getSchoolYear()).equals(yearFilter)) {
                        data.add(new XYChart.Data<>(subject.getSubjectName(),transcriptBLL.calGPAByTranscriptId(transcript.getTranscriptId())));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = ((Double) data.getYValue() != 0) ? new Text(data.getYValue() + "") : new Text("");
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

}
