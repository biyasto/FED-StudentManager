package GUI.controllers.items;

import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TranscriptDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormat;
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

    public void setData(SubjectClassDTO subjectClass, SubjectDTO subject, TranscriptDTO transcriptOfStudent) {
        lblClassID.setText(subjectClass.getClassId());
        lblClassName.setText(subject.getSubjectName());

        double avg = 0.0;
        if(transcriptOfStudent != null) {
            Grade1Textfield.setText(String.valueOf(transcriptOfStudent.getMark1()));
            Grade2Textfield.setText(String.valueOf(transcriptOfStudent.getMark2()));
            Grade3Textfield.setText(String.valueOf(transcriptOfStudent.getMark3()));
            Grade4Textfield.setText(String.valueOf(transcriptOfStudent.getMark4()));

            avg = transcriptOfStudent.getMark1() * 0.1
                + transcriptOfStudent.getMark2() * 0.2
                + transcriptOfStudent.getMark3() * 0.2
                + transcriptOfStudent.getMark4() * 0.5;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        lblAvgGrade.setText(df.format(avg));
    }
}
