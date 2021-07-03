package GUI.controllers;

import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.*;
import Model.StudentGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;

import java.util.List;

public class InputGradesController {

    @FXML
    private AnchorPane panelGrade;

    @FXML
    private TableView<StudentGrade> table;

    @FXML
    private TableColumn<StudentGrade, Integer> IDColumn;

    @FXML
    private TableColumn<StudentGrade, String> nameColumn;

    @FXML
    private TableColumn<StudentGrade, Double> attendanceColumn;

    @FXML
    private TableColumn<StudentGrade, Double> practiceColumn;

    @FXML
    private TableColumn<StudentGrade, Double> projectColumn;

    @FXML
    private TableColumn<StudentGrade, Double> finalColumn;

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

    @FXML
    private Button btnBack;

    private List<StudentDTO> studentList = null;
    private TeacherDTO teacher = null;
    private SubjectDTO subject = null;
    private SubjectClassDTO subjectClass = null;

    private StackPane container = NavigationController.containerNav;

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(panelGrade);
    }

    public void setData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        this.studentList = studentList;
        this.teacher = teacher;
        this.subject = subject;
        this.subjectClass = subjectClass;

        ClassID.setText(subjectClass.getClassId());
        SubjectName.setText(subject.getSubjectName());
        facultyName.setText("Faculty: " + subject.getFaculty());
        teacherName.setText("Teacher: " + teacher.getName());
        attendantNum.setText("Attendants: " + studentList.size());

        loadTable();
    }

    private void loadTable() {
        ObservableList<StudentGrade> list = FXCollections.observableArrayList();
        for (StudentDTO student : studentList) {
            TranscriptBLL transcriptBLL = new TranscriptBLL();
            TranscriptDTO transcriptOfOneStudent = transcriptBLL.GetTranscriptOfClass(subjectClass.getClassId(), student.getId());

            Double mark1 = null;
            Double mark2 = null;
            Double mark3 = null;
            Double mark4 = null;

            if (transcriptOfOneStudent.getMark1() != -1)
                mark1 = transcriptOfOneStudent.getMark1();
            if (transcriptOfOneStudent.getMark2() != -1)
                mark2 = transcriptOfOneStudent.getMark2();
            if (transcriptOfOneStudent.getMark3() != -1)
                mark3 = transcriptOfOneStudent.getMark3();
            if (transcriptOfOneStudent.getMark4() != -1)
                mark4 = transcriptOfOneStudent.getMark4();

            StudentGrade studentGrade = new StudentGrade(
                    student.getId(),
                    student.getName(),
                    transcriptOfOneStudent.getTranscriptId(),
                    mark1,
                    mark2,
                    mark3,
                    mark4
            );

            list.add(studentGrade);
        }

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("mark1"));
        practiceColumn.setCellValueFactory(new PropertyValueFactory<>("mark2"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("mark3"));
        finalColumn.setCellValueFactory(new PropertyValueFactory<>("mark4"));

        //set editable for all columns
        attendanceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        practiceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        projectColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        finalColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        table.setItems(list);
    }

    private void updateGrade(StudentGrade studentGrade) {
        Double mark1 = studentGrade.getMark1();
        Double mark2 = studentGrade.getMark2();
        Double mark3 = studentGrade.getMark3();
        Double mark4 = studentGrade.getMark4();

        if (mark1 == null)
            mark1 = -1.0;
        if (mark2 == null)
            mark2 = -1.0;
        if (mark3 == null)
            mark3 = -1.0;
        if (mark4 == null)
            mark4 = -1.0;

        TranscriptDTO transcript = new TranscriptDTO(
                studentGrade.getTranscriptId(),
                mark1,
                mark2,
                mark3,
                mark4
        );
        TranscriptBLL transcriptBLL = new TranscriptBLL();
        transcriptBLL.UpdateTranscript(transcript);
    }

    @FXML
    void onMarkOneChanged(TableColumn.CellEditEvent<StudentGrade, Double> studentGradeStringCellEditEvent) {
        StudentGrade studentGrade = table.getSelectionModel().getSelectedItem();
        studentGrade.setMark1(studentGradeStringCellEditEvent.getNewValue());
        updateGrade(studentGrade);
    }

    @FXML
    void onMarkTwoChanged(TableColumn.CellEditEvent<StudentGrade, Double> studentGradeStringCellEditEvent) {
        StudentGrade studentGrade = table.getSelectionModel().getSelectedItem();
        studentGrade.setMark2(studentGradeStringCellEditEvent.getNewValue());
        updateGrade(studentGrade);
    }

    @FXML
    void onMarkThreeChanged(TableColumn.CellEditEvent<StudentGrade, Double> studentGradeStringCellEditEvent) {
        StudentGrade studentGrade = table.getSelectionModel().getSelectedItem();
        studentGrade.setMark3(studentGradeStringCellEditEvent.getNewValue());
        updateGrade(studentGrade);
    }

    @FXML
    void onMarkFourChanged(TableColumn.CellEditEvent<StudentGrade, Double> studentGradeStringCellEditEvent) {
        StudentGrade studentGrade = table.getSelectionModel().getSelectedItem();
        studentGrade.setMark4(studentGradeStringCellEditEvent.getNewValue());
        updateGrade(studentGrade);
    }

}
