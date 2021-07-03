package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.StudentClassBLL;
import BusinessLogicLayer.TranscriptBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class AddStudentController {

    @FXML
    private AnchorPane addStudentPane;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnAdd;

    @FXML
    private TextArea Textfield;

    @FXML
    private Button btnDelete;

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
    private Label lblSuccess;

    @FXML
    private Label lblFailed;

    @FXML
    private Label lblExist;

    private String classID = "";
    private final List<String> studentIDList = new ArrayList<>();
    //private List<StudentDTO> studentList = null;
    //public static List<StudentDTO> studentDTOList = new ArrayList<>();

    private final StackPane container = NavigationController.containerNav;

    public void setData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        ClassID.setText(subjectClass.getClassId());
        SubjectName.setText(subject.getSubjectName());
        facultyName.setText("Faculty: " + subject.getFaculty());
        teacherName.setText("Teacher: " + teacher.getName());
        attendantNum.setText("Attendants: " + studentList.size());
        //this.studentList = studentList;
        //studentDTOList = studentList;

        classID = subjectClass.getClassId();
        for(StudentDTO studentDTO: studentList) {
            studentIDList.add(studentDTO.getId());
        }
    }

    @FXML
    void addStudents(MouseEvent event) {
        String[] allStudent = Textfield.getText().split("\n");
        for (String studentId : allStudent) {
            //ignore this line if its empty
            if(studentId.isEmpty())
                continue;

            StudentBLL studentBLL = new StudentBLL();
            //check if this studentID is valid or not
            boolean result = studentBLL.checkExits(studentId);
            if(result) {
                if(!studentIDList.contains(studentId)) {
                    try {
                        TranscriptBLL transcriptBLL = new TranscriptBLL();
                        int newTranscriptID = transcriptBLL.countTranscripts() + 1;
                        transcriptBLL.InsertTranscript(newTranscriptID);

                        StudentClassBLL studentClassBLL = new StudentClassBLL();
                        studentClassBLL.InsertStudent(studentId, classID, newTranscriptID);

                        //added successfully
                        lblSuccess.setVisible(true);
                        lblFailed.setVisible(false);
                        lblExist.setVisible(false);
                        Textfield.setText(removeAdded(Textfield.getText(), studentId));

                        //add new student to list in order to update layout
                        StudentDTO newStudent = getStudentByID(studentId);
                        if(newStudent != null) {
                            //studentList.add(newStudent);
                            //studentDTOList = studentList;
                            ClassGradesController.addedStudents.add(newStudent);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                else {
                    //this student has already existed in this class
                    lblSuccess.setVisible(false);
                    lblFailed.setVisible(false);
                    lblExist.setVisible(true);
                    break;
                }
            }
            else {
                //invalid studentID
                lblSuccess.setVisible(false);
                lblFailed.setVisible(true);
                lblExist.setVisible(false);
                break;
            }
        }
    }

    String removeAdded(String textField, String id) {
        String[] str = textField.split("\n");
        String result = "";
        for (String s : str) {
            if (!s.equals(id))
                result += s + "\n";
        }
        return result;
    }

    StudentDTO getStudentByID(String id) {
        StudentBLL studentBLL = new StudentBLL();
        return studentBLL.GetStudentByID(id);
    }

    @FXML
    void back(MouseEvent event) {
        container.getChildren().removeAll(addStudentPane);
    }

}
