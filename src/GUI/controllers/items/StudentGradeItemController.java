package GUI.controllers.items;

import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TranscriptDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class StudentGradeItemController {

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
    private Label lblAvgGrade;

    @FXML
    private Label lblClassID;

    @FXML
    private Label lblClassName;

    public void setData(SubjectClassDTO subjectClass, SubjectDTO subject, List<TranscriptDTO> transcriptOfStudent) {
        lblClassID.setText(subjectClass.getClassId());
        lblClassName.setText(subject.getSubjectName());

        double avg = 0.0;
        for(TranscriptDTO transcript: transcriptOfStudent) {
            if(transcript.getFlag() == 1) {
                Grade1Textfield.setText(String.valueOf(transcript.getMarks()));
                avg += transcript.getMarks();
            }
            else if(transcript.getFlag() == 2) {
                Grade2Textfield.setText(String.valueOf(transcript.getMarks()));
                avg += transcript.getMarks();
            }
            else if(transcript.getFlag() == 3) {
                Grade3Textfield.setText(String.valueOf(transcript.getMarks()));
                avg += transcript.getMarks();
            }
            else if(transcript.getFlag() == 4) {
                Grade4Textfield.setText(String.valueOf(transcript.getMarks()));
                avg += transcript.getMarks();
            }
        }

        lblAvgGrade.setText(String.valueOf(avg));
    }
}
