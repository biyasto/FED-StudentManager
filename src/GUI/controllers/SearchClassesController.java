package GUI.controllers;

import BusinessLogicLayer.SubjectBLL;
import BusinessLogicLayer.SubjectClassBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import DataTransferObject.TeacherDTO;
import GUI.controllers.items.ClassItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchClassesController implements Initializable {
    @FXML
    private TextField ClassIDTextfield;

    @FXML
    private Button FindButton;

    @FXML
    private TextField SubjectNameTextfield;

    @FXML
    private VBox classScrollPane;

    private List<SubjectClassDTO> classList = new ArrayList<>();

    @FXML
    void findClassByClassIdOrSubjectName(MouseEvent event) {
        findClassByClassId(ClassIDTextfield.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
        classList = subjectClassBLL.getAllSubjectClass();

        for(SubjectClassDTO subjectClass: classList) {
            SubjectBLL subjectBLL = new SubjectBLL();
            TeacherBLL teacherBLL = new TeacherBLL();

            SubjectDTO subjectDTO = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
            TeacherDTO teacherDTO = teacherBLL.GetTeacherByID(subjectClass.getHeadMaster());

            bindData(teacherDTO, subjectDTO, subjectClass);
        }
    }

    void bindData(TeacherDTO teacher, SubjectDTO subject, SubjectClassDTO subjectClass) {
        try {
            URL urlLayout = new File("src/GUI/resources/items/ClassItem.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            ClassItemController classItemController = fxmlLoader.getController();
            classItemController.setData(teacher, subject, subjectClass);

            classScrollPane.getChildren().add(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void findClassByClassId(String id){
        classScrollPane.getChildren().clear();
        SubjectClassBLL subjectClassBLL = new SubjectClassBLL();
        classList = subjectClassBLL.getClassesByClassId(id);

        for(SubjectClassDTO subjectClass: classList) {
            SubjectBLL subjectBLL = new SubjectBLL();
            TeacherBLL teacherBLL = new TeacherBLL();

            SubjectDTO subjectDTO = subjectBLL.GetSubjectById(subjectClass.getSubjectId());
            TeacherDTO teacherDTO = teacherBLL.GetTeacherByID(subjectClass.getHeadMaster());

            bindData(teacherDTO, subjectDTO, subjectClass);
        }
    }

    void findClassBySubjectName(String name){

    }
}
