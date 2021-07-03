package GUI.controllers.items;

import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.TranscriptDTO;
import GUI.controllers.ClassGradesController;
import GUI.controllers.LoginController;
import GUI.controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClassGradeItemController implements Initializable {

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

    @FXML
    private Button btnDelete;


    public StudentDTO studentUser;
    private StudentDTO student = null;
    private TranscriptDTO transcript = null;
    private SubjectClassDTO subjectClass = null;

    private final VBox container = ClassGradesController.studentGrade;

    public void setData(SubjectClassDTO subjectClass, StudentDTO student, TranscriptDTO transcriptDTO) {
        this.student = student;
        this.transcript = transcriptDTO;
        this.subjectClass = subjectClass;

        StudentName.setText(student.getName());
        StudentID.setText(student.getId());

        double avg = 0;
        if (transcript != null) {
            if (transcript.getMark1() != -1) {
                Grade1Textfield.setText(String.valueOf(transcript.getMark1()));
                avg += transcript.getMark1() * 0.1;
            }
            if (transcript.getMark2() != -1) {
                Grade2Textfield.setText(String.valueOf(transcript.getMark2()));
                avg += transcript.getMark2() * 0.2;
            }
            if (transcript.getMark3() != -1) {
                Grade3Textfield.setText(String.valueOf(transcript.getMark3()));
                avg += transcript.getMark3() * 0.2;
            }
            if (transcript.getMark4() != -1) {
                Grade4Textfield.setText(String.valueOf(transcript.getMark4()));
                avg += transcript.getMark4() * 0.5;
            }

        }
        DecimalFormat df = new DecimalFormat("#.#");
        SumGradeLabel.setText(df.format(avg));
    }

    @FXML
    void enableEdit(MouseEvent event) {
        Grade1Textfield.setEditable(true);
        Grade2Textfield.setEditable(true);
        Grade3Textfield.setEditable(true);
        Grade4Textfield.setEditable(true);
        btnOK.setVisible(true);
    }

    void disableEdit() {
        Grade1Textfield.setEditable(false);
        Grade2Textfield.setEditable(false);
        Grade3Textfield.setEditable(false);
        Grade4Textfield.setEditable(false);
        btnOK.setVisible(false);
    }

    @FXML
    void updateGrade(MouseEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Edit Student's Grades");
        alertConfirm.setHeaderText("Are you sure want to edit grades of this student?");
        Optional<ButtonType> option = alertConfirm.showAndWait();

        if (option.get() == ButtonType.OK) {
            try {
                String str1 = Grade1Textfield.getText();
                String str2 = Grade2Textfield.getText();
                String str3 = Grade3Textfield.getText();
                String str4 = Grade4Textfield.getText();

                double mark1 = -1;
                double mark2 = -1;
                double mark3 = -1;
                double mark4 = -1;

                if (!str1.isEmpty())
                    mark1 = Double.parseDouble(str1);
                if (!str2.isEmpty())
                    mark2 = Double.parseDouble(str2);
                if (!str3.isEmpty())
                    mark3 = Double.parseDouble(str3);
                if (!str4.isEmpty())
                    mark4 = Double.parseDouble(str4);

                if (mark1 < -1 || mark1 > 10
                        || mark2 < -1 || mark2 > 10
                        || mark3 < -1 || mark3 > 10
                        || mark4 < -1 || mark4 > 10) {
                    //invalid input, handle here
                    Alert alertInvalid = new Alert(Alert.AlertType.INFORMATION);
                    alertInvalid.setTitle("Invalid input");
                    alertInvalid.setHeaderText(null);
                    alertInvalid.setContentText("Invalid grade's input, please check again!");
                    alertInvalid.showAndWait();
                } else {
                    TranscriptBLL transcriptBLL = new TranscriptBLL();
                    int result = -1;

                    transcript.setMark1(mark1);
                    transcript.setMark2(mark2);
                    transcript.setMark3(mark3);
                    transcript.setMark4(mark4);

                    result = transcriptBLL.UpdateTranscript(transcript);

                    if (result != -1) {
                        //update successfully
                        btnOK.setVisible(false);
                        Grade1Textfield.setEditable(false);
                        Grade2Textfield.setEditable(false);
                        Grade3Textfield.setEditable(false);
                        Grade4Textfield.setEditable(false);

                        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                        alertSuccess.setTitle("Update Student's Grades");
                        alertSuccess.setHeaderText(null);
                        alertSuccess.setContentText("Update successfully!");
                        alertSuccess.showAndWait();
                        disableEdit();
                        setData(subjectClass, student, transcript);
                    } else {
                        Alert alertFail = new Alert(Alert.AlertType.INFORMATION);
                        alertFail.setTitle("Update Student's Grades");
                        alertFail.setHeaderText(null);
                        alertFail.setContentText("Update failed!");
                        alertFail.showAndWait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            disableEdit();
        }
    }

    @FXML
    void deleteStudent(MouseEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Remove Student");
        alertConfirm.setHeaderText("Are you sure want to remove this student?");
        Optional<ButtonType> option = alertConfirm.showAndWait();

        if (option.get() == ButtonType.OK) {
            StudentClassBLL studentClassBLL = new StudentClassBLL();
            int result = studentClassBLL.DeleteStudent(student.getId(), subjectClass.getClassId(), transcript.getTranscriptId());

            if (result != -1) {
                //success, handle here
                container.getChildren().removeAll(StudentInfoBox);
                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Result");
                alertSuccess.setHeaderText(null);
                alertSuccess.setContentText("Removed successfully!");
                alertSuccess.showAndWait();
                ClassGradesController.deletedStudents.add(student);
            } else {
                //fail
                Alert alertFail = new Alert(Alert.AlertType.INFORMATION);
                alertFail.setTitle("Result");
                alertFail.setHeaderText(null);
                alertFail.setContentText("Remove failed, please try again!");
                alertFail.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentUser = NavigationController.studentUser;
        if (studentUser != null) {
            btnDelete.setVisible(false);
            btnEdit.setVisible(false);
            btnDelete.setVisible(false);
        }
    }
}
