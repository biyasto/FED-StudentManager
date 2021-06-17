package GUI.controllers.items;

import DataTransferObject.StudentDTO;
import DataTransferObject.TranscriptDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClassGradeItemController {

    @FXML
    private AnchorPane StudentInfoBox;

    @FXML
    private AnchorPane GradeBox;

    @FXML
    private Label Grade1Label;

    @FXML
    private TextField Grade1Textfield;

    @FXML
    private Label Grade2Label;

    @FXML
    private TextField Grade2Textfield;

    @FXML
    private Label Grade3Label;

    @FXML
    private TextField Grade3Textfield;

    @FXML
    private Label Grade4Label;

    @FXML
    private TextField Grade4Textfield;

    @FXML
    private Label SumGradeLabel;

    @FXML
    private Label StudentName;

    @FXML
    private Label StudentID;

    private StudentDTO student = null;
    private List<TranscriptDTO> transcriptList = null;


    public void setData(StudentDTO student, List<TranscriptDTO> transcriptList) {
        this.student = student;
        this.transcriptList = transcriptList;

        StudentName.setText(student.getName());
        StudentID.setText(student.getId());

        if(!transcriptList.isEmpty()) {
            for(TranscriptDTO item: transcriptList) {
                if(item.getFlag() == 1) {
                    Grade1Textfield.setText(String.valueOf(item.getMarks()));
                }
                else if(item.getFlag() == 2) {
                    Grade2Textfield.setText(String.valueOf(item.getMarks()));
                }
                else if(item.getFlag() == 3) {
                    Grade3Textfield.setText(String.valueOf(item.getMarks()));
                }
                else if(item.getFlag() == 4) {
                    Grade4Textfield.setText(String.valueOf(item.getMarks()));
                }
            }
        }
    }
}
