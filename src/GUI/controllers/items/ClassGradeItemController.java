package GUI.controllers.items;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TranscriptDTO;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.List;

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

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnBack;


    private StudentDTO student = null;
    private List<TranscriptDTO> transcriptList = null;


    public void setData(StudentDTO student, List<TranscriptDTO> transcriptList) {
        this.student = student;
        this.transcriptList = transcriptList;

        StudentName.setText(student.getName());
        StudentID.setText(student.getId());

        int sum = 0;
        if(!transcriptList.isEmpty()) {
            for(TranscriptDTO item: transcriptList) {
                if(item.getFlag() == 1) {
                    Grade1Textfield.setText(String.valueOf(item.getMarks()));
                    sum+=item.getMarks();
                }
                else if(item.getFlag() == 2) {
                    Grade2Textfield.setText(String.valueOf(item.getMarks()));
                    sum+=item.getMarks();
                }
                else if(item.getFlag() == 3) {
                    Grade3Textfield.setText(String.valueOf(item.getMarks()));
                    sum+=item.getMarks();
                }
                else if(item.getFlag() == 4) {
                    Grade4Textfield.setText(String.valueOf(item.getMarks()));
                    sum+=item.getMarks();
                }
            }
        }
        SumGradeLabel.setText(String.valueOf(sum));
    }

    @FXML
    void enableEdit(MouseEvent event) {
        Grade1Textfield.setEditable(true);
        Grade2Textfield.setEditable(true);
        Grade3Textfield.setEditable(true);
        Grade4Textfield.setEditable(true);
        btnOK.setVisible(true);
    }

    @FXML
    void updateGrade(MouseEvent event) {
        TranscriptBLL transcriptBLL = new TranscriptBLL();
        int result = -1;
        for(TranscriptDTO transcript: transcriptList) {
            if(transcript.getFlag() == 1) {
                transcript.setMarks(Double.parseDouble(Grade1Textfield.getText()));
                result = transcriptBLL.UpdateTranscript(transcript.getMarks(), transcript.getTranscriptId());
                if(result == -1) {
                    //update failed, handle here
                    break;
                }
            }
            else if(transcript.getFlag() == 2) {
                transcript.setMarks(Double.parseDouble(Grade2Textfield.getText()));
                result = transcriptBLL.UpdateTranscript(transcript.getMarks(), transcript.getTranscriptId());
                if(result == -1) {
                    //update failed, handle here
                    break;
                }
            }
            else if(transcript.getFlag() == 3) {
                transcript.setMarks(Double.parseDouble(Grade3Textfield.getText()));
                result = transcriptBLL.UpdateTranscript(transcript.getMarks(), transcript.getTranscriptId());
                if(result == -1) {
                    //update failed, handle here
                    break;
                }
            }
            else if(transcript.getFlag() == 4) {
                transcript.setMarks(Double.parseDouble(Grade4Textfield.getText()));
                result = transcriptBLL.UpdateTranscript(transcript.getMarks(), transcript.getTranscriptId());
                if(result == -1) {
                    //update failed, handle here
                    break;
                }
            }
        }
        if(result != -1) {
            btnOK.setVisible(false);
            Grade1Textfield.setEditable(false);
            Grade2Textfield.setEditable(false);
            Grade3Textfield.setEditable(false);
            Grade4Textfield.setEditable(false);
        }
    }

}
