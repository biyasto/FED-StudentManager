package GUI.controllers;

import BusinessLogicLayer.SubjectBLL;
import DataTransferObject.SubjectDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateSubjectController implements Initializable {

    @FXML
    private AnchorPane CreateSubjectPane;

    @FXML
    private TextField SubjectName;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblError;

    @FXML
    private ChoiceBox<String> FacultyChoiceBox;

    @FXML
    private Button btnCreate;

    @FXML
    private ChoiceBox<String> creditChoiceBox;

    private String faculty = "";
    private String subjectName = "";
    private int credit = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SubjectBLL subjectBLL = new SubjectBLL();
        List<SubjectDTO> subjectList = subjectBLL.GetAllSubject();

        List<String> facultyList = new ArrayList<>();
        for(SubjectDTO subject: subjectList) {
            if(!facultyList.contains(subject.getFaculty())) {
                facultyList.add(subject.getFaculty());
            }
        }

        //data for faculty
        ObservableList<String> dataFaculty = FXCollections.observableArrayList();
        dataFaculty.setAll(facultyList);
        FacultyChoiceBox.setItems(dataFaculty);
        FacultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { faculty = newValue; });

        //data for credit
        String[] optionCredit = {
                "2",
                "3",
                "4"
        };
        ObservableList<String> dataCredit = FXCollections.observableArrayList();
        dataCredit.setAll(optionCredit);
        creditChoiceBox.setItems(dataCredit);
        creditChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { credit = Integer.parseInt(newValue); });
    }

    @FXML
    void createSubject(MouseEvent event) {
        subjectName = SubjectName.getText();
        if(faculty.isEmpty() || subjectName.isEmpty() || credit == 0) {
            lblEmpty.setVisible(true);
            lblSuccess.setVisible(false);
            lblError.setVisible(false);
        }
        else {
            SubjectBLL subjectBLL = new SubjectBLL();
            int id = subjectBLL.countSubject() + 1;
            String strID = String.valueOf(id);

            if(strID.length() == 1)
                strID = "000" + strID;
            else if(strID.length() == 2)
                strID = "00" + strID;
            else if(strID.length() == 3)
                strID = "0" + strID;

            SubjectDTO subject = new SubjectDTO(
                    strID,
                    subjectName,
                    credit,
                    faculty
            );

            int result = subjectBLL.InsertSubject(subject);
            if(result != -1) {
                lblEmpty.setVisible(false);
                lblSuccess.setVisible(true);
                lblError.setVisible(false);
            }
            else {
                lblEmpty.setVisible(false);
                lblSuccess.setVisible(false);
                lblError.setVisible(true);
            }
        }
    }
}
