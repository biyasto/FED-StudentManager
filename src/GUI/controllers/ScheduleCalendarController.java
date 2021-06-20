package GUI.controllers;

import BusinessLogicLayer.*;
import DataTransferObject.*;
import GUI.controllers.items.ClassItemController;
import GUI.controllers.items.EventItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleCalendarController implements Initializable {

    @FXML
    public ChoiceBox MonthChoiceBox;
    @FXML
    public ChoiceBox YearChoiceBox;
    @FXML
    public Button BackButton;
    @FXML
    public Button btnAddEvent;
    @FXML
    public Button PrintButton;
    @FXML
    public Button InfoButton;
    @FXML
    public Button SaveButton;
    @FXML
    public VBox eventScrollPane;

    private StackPane container = NavigationController.containerNav;
    private List<ExamScheduleDTO> eventList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventList = new ExamScheduleBLL().getAllExamSchedule();

        //load all class into table
        for(ExamScheduleDTO event: eventList)
            loadDataIntoTable(event);
    }

    @FXML
    void addExamEvent(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/AddExamEvent.fxml").toURI().toURL();
        Parent createAccountScreen = FXMLLoader.load(url);
        container.getChildren().add(createAccountScreen);
    }

    void loadDataIntoTable(ExamScheduleDTO examSchedule) {
        SubjectClassDTO subjectClass = new SubjectClassBLL().getClassById(examSchedule.getClassId());
        SubjectDTO subjectDTO = new SubjectBLL().GetSubjectById(subjectClass.getSubjectId());

        bindData(subjectDTO, examSchedule);
    }

    void bindData(SubjectDTO subject, ExamScheduleDTO examSchedule) {
        try {
            URL urlLayout = new File("src/GUI/resources/items/EventItem.fxml").toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlLayout);
            Node item = fxmlLoader.load();

            EventItemController eventController = fxmlLoader.getController();
            eventController.setData(subject, examSchedule);

            eventScrollPane.getChildren().add(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
