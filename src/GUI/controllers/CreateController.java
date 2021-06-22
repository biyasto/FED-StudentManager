package GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateController {

    @FXML
    private AnchorPane panelGradeStatistic;

    @FXML
    private Button EventBtn;

    @FXML
    private Button ClassBtn;

    @FXML
    private Button AccountBtn;


    @FXML
    void OpenAccount(MouseEvent event) {

    }

    @FXML
    void OpenClass(MouseEvent event) {

    }

    @FXML
    void openEvent(MouseEvent event) {

    }
    void SetStyle()
    { // Thêm hàm này vô Init để set style cho button
        EventBtn.setStyle("");
        ClassBtn.setStyle("");
        AccountBtn.setStyle("");
    }

}
