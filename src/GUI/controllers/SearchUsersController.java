package GUI.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class SearchUsersController {
    public TextField SearchTextfield;
    public ChoiceBox PositionChoiceBox; // chọn giáo viên hay học sinh
    public Button FindButton;

    public AnchorPane StudentInfoBox;
    //thuộc studentINFOBOX
    public Label PositionLabel;
    public Label FacultyLabel;
    public Label NameLabel;
    public Label ActiveLabel;
    public Label IDLabel;
    public Circle StatusCircle;
    //
    public Button BackButton;
}
