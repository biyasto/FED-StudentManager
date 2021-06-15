package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import GUI.controllers.items.StudentItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchUsersController implements Initializable {
    @FXML
    private TextField SearchIDTextfield;

    @FXML
    private Button FindButton;

    @FXML
    private ChoiceBox<String> PositionChoiceBox;

    @FXML
    private TextField SearchNameTextfield;

    @FXML
    private VBox studentScrollPane;

    @FXML
    private Label lblNotFound;

    @FXML
    private Label lblEmpty;

    private List<StudentDTO> studentList = new ArrayList<>();
    private List<TeacherDTO> teacherList = new ArrayList<>();

    private String position;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get all current students from database
        StudentBLL studentBLL = new StudentBLL();
        studentList = studentBLL.GetALlStudent();

        //get all current teachers from database
        TeacherBLL teacherBLL = new TeacherBLL();
        teacherList = teacherBLL.GetALlTeacher();

        //data for Position
        String[] optionPosition = {
                "Teacher",
                "Student"
        };
        ObservableList<String> dataPosition = FXCollections.observableArrayList();
        dataPosition.setAll(optionPosition);
        PositionChoiceBox.setItems(dataPosition);

        //set default value
        PositionChoiceBox.setValue("Student");
        loadStudents();

        //handler on data change action
        PositionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            position = newValue;
            if(newValue.equals("Student"))
                loadStudents();
            else
                loadTeachers();
        });
    }

    void loadStudents() {
        //reset layout
        studentScrollPane.getChildren().clear();

        for(StudentDTO student: studentList) {
            bindStudentData(student);
        }
    }

    void loadTeachers() {
        //reset layout
        studentScrollPane.getChildren().clear();

        for(TeacherDTO teacher: teacherList) {
            bindTeacherData(teacher);
        }
    }

    @FXML
    void findUser(MouseEvent event) {
        String findID = SearchIDTextfield.getText();
        String findName = SearchNameTextfield.getText();

        if(!findID.isEmpty()) {
            findByID(findID);
        }
        else if(!findName.isEmpty()) {
            findByName(findName);
        }
        else {
            lblEmpty.setVisible(true);
        }

        SearchIDTextfield.clear();
        SearchNameTextfield.clear();
    }

    private void findByID(String findID) {
        StudentDTO studentFound = null;
        for(StudentDTO student: studentList) {
            if(student.getId().equals(findID)) {
                studentFound = student;
            }
        }

        TeacherDTO teacherFound = null;
        for(TeacherDTO teacher: teacherList) {
            if(teacher.getId().equals(findID)) {
                teacherFound = teacher;
            }
        }

        //display result
        if(studentFound != null) {
            studentScrollPane.getChildren().clear();
            bindStudentData(studentFound);
        }
        else if(teacherFound != null) {
            studentScrollPane.getChildren().clear();
            bindTeacherData(teacherFound);
        }
        else {
            lblEmpty.setVisible(false);
            lblNotFound.setVisible(true);
        }
    }

    private void findByName(String findName) {
        List<StudentDTO> studentFound = new ArrayList<>();
        for(StudentDTO student: studentList) {
            if(student.getName().equals(findName)) {
                studentFound.add(student);
            }
        }

        List<TeacherDTO> teacherFound = new ArrayList<>();
        for(TeacherDTO teacher: teacherList) {
            if(teacher.getName().equals(findName)) {
                teacherFound.add(teacher);
            }
        }

        studentScrollPane.getChildren().clear();
        if(!studentFound.isEmpty()) {
            for(StudentDTO student: studentFound)
                bindStudentData(student);
        }
        if(!teacherFound.isEmpty()) {
            for(TeacherDTO teacher: teacherFound)
                bindTeacherData(teacher);
        }

        if(studentFound.isEmpty() && teacherFound.isEmpty()) {
            lblNotFound.setVisible(true);
            lblEmpty.setVisible(false);
        }
    }

    private void bindStudentData(StudentDTO student) {
        lblEmpty.setVisible(false);
        lblNotFound.setVisible(false);
        try {
            URL urlLayout = new File("src/GUI/resources/items/StudentItem.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            StudentItemController studentItemController = fxmlLoader.getController();
            studentItemController.setDataStudent(student);

            studentScrollPane.getChildren().add(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindTeacherData(TeacherDTO teacher) {
        lblEmpty.setVisible(false);
        lblNotFound.setVisible(false);
        try {
            URL urlLayout = new File("src/GUI/resources/items/StudentItem.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            StudentItemController studentItemController = fxmlLoader.getController();
            studentItemController.setDataTeacher(teacher);

            studentScrollPane.getChildren().add(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
