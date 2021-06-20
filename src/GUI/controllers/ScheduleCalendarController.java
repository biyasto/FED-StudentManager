package GUI.controllers;

import BusinessLogicLayer.*;
import DataTransferObject.*;
import GUI.controllers.items.ClassItemController;
import GUI.controllers.items.EventItemController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleCalendarController implements Initializable {

    @FXML
    public ChoiceBox<Integer> MonthChoiceBox;
    @FXML
    public ChoiceBox<Integer> YearChoiceBox;
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

        loadChoiceBoxMonth();
        loadChoiceBoxYear();

        MonthChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (MonthChoiceBox.getItems().get((Integer) t1) != null && YearChoiceBox.getSelectionModel().getSelectedItem() != null){
                    int newYear  = YearChoiceBox.getSelectionModel().getSelectedItem();
                    int newMonth = MonthChoiceBox.getItems().get((Integer) t1);
                    loadExamEventWithMonthAndYear(newMonth, newYear);
                }
            }
        });
        YearChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (MonthChoiceBox.getSelectionModel().getSelectedItem() != null && YearChoiceBox.getItems().get((Integer) t1) != null){
                    int newYear  = YearChoiceBox.getItems().get((Integer) t1);
                    int newMonth = MonthChoiceBox.getSelectionModel().getSelectedItem();
                    loadExamEventWithMonthAndYear(newMonth, newYear);
                }
            }
        });
    }

    @FXML
    void addExamEvent(MouseEvent event) throws IOException {
        URL url = new File("src/GUI/resources/AddExamEvent.fxml").toURI().toURL();
        Parent createExamEvent = FXMLLoader.load(url);
        container.getChildren().removeAll();
        container.getChildren().add(createExamEvent);
    }

    void loadExamEventWithMonthAndYear(int month, int year){
        eventScrollPane.getChildren().clear();
        System.out.println("choose date: "+month + "/" + year);
        Calendar cal = Calendar.getInstance();

        for(ExamScheduleDTO event: eventList){
            cal.setTime(event.getExamDate());
            int eventMonth = cal.get(Calendar.MONTH) + 1;
            int eventYear = cal.get(Calendar.YEAR);
            System.out.println("Event date: " + eventMonth + "/" + eventYear);
            if (eventMonth == month && eventYear == year){
                loadDataIntoTable(event);
            }
        }
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

    void loadChoiceBoxMonth(){
        ObservableList<Integer> values = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        MonthChoiceBox.setItems(values);
    }

    void loadChoiceBoxYear(){
        List<Integer> years = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= 2006; i--) {
            years.add(i);
        }
        ObservableList<Integer> values = FXCollections.observableArrayList(years);
        YearChoiceBox.setItems(values);
    }
}
