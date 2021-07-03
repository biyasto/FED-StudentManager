package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
import GUI.controllers.items.ClassItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.*;

public class SearchClassesController implements Initializable {
    @FXML
    private TextField ClassIDTextfield;

    @FXML
    private Button FindButton;

    @FXML
    private Button NewClassButton;

    @FXML
    private Button btnRefresh;

    @FXML
    private TextField SubjectNameTextfield;

    @FXML
    private VBox classScrollPane;

    @FXML
    private Label lblNotFound;

    @FXML
    private Label lblEmpty;

    public StudentDTO student = NavigationController.studentUser;
    public TeacherDTO teacher = NavigationController.teacherUser;
    private SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
    private List<SubjectClassDTO> classList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDataFirstTime();
    }

    public void loadDataFirstTime() {
        if (student != null) {
            classList = subjectClassBLL.getClassesByStudentId(student.getId());
        } else if (teacher != null) {
            classList = subjectClassBLL.getClassesByTeacherId(teacher.getId());
        } else {
            classList = subjectClassBLL.getAllSubjectClass();
        }

        //load class into table
        classScrollPane.getChildren().clear();
        for (SubjectClassDTO subjectClass : classList) {
            loadDataIntoTable(subjectClass);
        }
    }

    void loadDataIntoTable(SubjectClassDTO subjectClass) {
        SubjectBLL subjectBLL = new SubjectBLL();
        TeacherBLL teacherBLL = new TeacherBLL();
        StudentBLL studentBLL = new StudentBLL();

        SubjectDTO subjectDTO = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
        TeacherDTO teacherDTO = teacherBLL.GetTeacherByID(subjectClass.getHeadMaster());
        List<StudentDTO> studentList = studentBLL.getStudentsByClassId(subjectClass.getClassId());


        bindData(studentList, teacherDTO, subjectDTO, subjectClass);

    }

    void bindData(List<StudentDTO> studentList, TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        try {
            URL urlLayout = new File("src/GUI/resources/items/ClassItem.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            ClassItemController classItemController = fxmlLoader.getController();
            classItemController.setData(studentList, teacher, subject, subjectClass);

            classScrollPane.getChildren().add(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void findClass(MouseEvent event) {
        String findID = ClassIDTextfield.getText();
        String findName = SubjectNameTextfield.getText();
        boolean isFound = false;

        ClassIDTextfield.clear();
        SubjectNameTextfield.clear();

        if (findID.isEmpty() && findName.isEmpty()) {
            lblNotFound.setVisible(false);
            lblEmpty.setVisible(true);
        } else {
            if (!findID.isEmpty()) {
                classScrollPane.getChildren().clear();
                for (SubjectClassDTO subjectClass : classList) {
                    if (subjectClass.getClassId().contains(findID.toUpperCase())) {
                        loadDataIntoTable(subjectClass);
                        isFound = true;
                        break;
                    }
                }
            } else {
                classScrollPane.getChildren().clear();
                for (SubjectClassDTO subjectClass : classList) {
                    //find subject base on class
                    SubjectBLL subjectBLL = new SubjectBLL();
                    SubjectDTO subjectDTO = subjectBLL.GetSubjectById(subjectClass.getSubjectId());

                    if (subjectDTO.getSubjectName().toUpperCase().contains(findName.toUpperCase())) {
                        loadDataIntoTable(subjectClass);
                        isFound = true;
                    }
                }
            }

            if (isFound) {
                lblNotFound.setVisible(false);
                lblEmpty.setVisible(false);
            } else {
                lblNotFound.setVisible(true);
                lblEmpty.setVisible(false);
            }
        }
    }

    @FXML
    void refresh(MouseEvent event) {
        loadDataFirstTime();
        lblNotFound.setVisible(false);
        lblEmpty.setVisible(false);
    }

}
