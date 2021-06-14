package GUI.controllers;

import BusinessLogicLayer.StudentBLL;
import BusinessLogicLayer.TeacherBLL;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {
    @FXML
    private TextField NameTextField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private PasswordField CPasswordField;

    @FXML
    private ChoiceBox<String> FacultyChoiceBox;

    @FXML
    private ChoiceBox<String> PositionChoiceBox;

    @FXML
    private ChoiceBox<String> GenderChoiceBox;

    @FXML
    private DatePicker DoBPicker;

    @FXML
    private Button CreateButton;

    @FXML
    private Text lblSuccess;

    @FXML
    private Text lblEmpty;

    @FXML
    private Text lblError;

    @FXML
    private Text lblWrongPass;

    String id;
    String email;
    String fullName;
    String dateOfBirth;
    String faculty;
    String password;
    String confirmPassword;
    String gender;
    String position;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //data for Faculty
        String[] optionFaculty = {
                "Software Engineering",
                "Information System",
                "Computer Science",
                "Computer Engineering",
                "Information Technology",
                "Data Science",
                "Electronic Commerce",
                "Computer Network"
        };
        ObservableList<String> dataFaculty = FXCollections.observableArrayList();
        dataFaculty.setAll(optionFaculty);
        FacultyChoiceBox.setItems(dataFaculty);
        FacultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> faculty = newValue);

        //data for Gender
        String[] optionGender = {
                "Male",
                "Female"
        };
        ObservableList<String> dataGender = FXCollections.observableArrayList();
        dataGender.setAll(optionGender);
        GenderChoiceBox.setItems(dataGender);
        GenderChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> gender = newValue);

        //data for Position
        String[] optionPosition = {
                "Teacher",
                "Student"
        };
        ObservableList<String> dataPosition = FXCollections.observableArrayList();
        dataPosition.setAll(optionPosition);
        PositionChoiceBox.setItems(dataPosition);
        PositionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> position = newValue);

        //format Date picker to display
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        DoBPicker.setConverter(converter);
        DoBPicker.setPromptText("dd/mm/yyyy");
    }

    @FXML
    void createAccount(MouseEvent event) {
        fullName = NameTextField.getText();
        password = PasswordField.getText();
        confirmPassword = CPasswordField.getText();

        //format chosen date of birth to store in the database
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = DoBPicker.getValue();
            dateOfBirth = formatter.format(date);
        }
        catch (Exception e) {
            lblEmpty.setVisible(false);
            lblWrongPass.setVisible(false);
            lblError.setVisible(true);
            lblSuccess.setVisible(false);
            e.printStackTrace();
        }

        if(position.equals("Student"))
            createID(1);
        else
            createID(2);

        if(fullName.isEmpty()
           || dateOfBirth.isEmpty()
           || faculty.isEmpty()
           || password.isEmpty()
           || confirmPassword.isEmpty()
           || gender.isEmpty()
           || position.isEmpty()) {
            lblEmpty.setVisible(true);
            lblError.setVisible(false);
            lblSuccess.setVisible(false);
        }
        else if(!password.equals(confirmPassword)) {
            lblEmpty.setVisible(false);
            lblWrongPass.setVisible(true);
            lblError.setVisible(false);
            lblSuccess.setVisible(false);
        }
        else if(id.isEmpty() || email.isEmpty()) {
            lblEmpty.setVisible(false);
            lblWrongPass.setVisible(false);
            lblError.setVisible(true);
            lblSuccess.setVisible(false);
        }
        else {
            boolean gder = true;
            if(gender.equals("Female"))
                gder = false;

            if(position.equals("Student")) {
                StudentDTO studentDTO = new StudentDTO(
                        id,
                        fullName,
                        gder,
                        dateOfBirth,
                        email,
                        password,
                        1,
                        faculty
                );

                StudentBLL studentBLL = new StudentBLL();
                int result = studentBLL.InsertStudent(studentDTO);
                if(result == -1) {
                    lblEmpty.setVisible(false);
                    lblWrongPass.setVisible(false);
                    lblError.setVisible(true);
                    lblSuccess.setVisible(false);
                }
                else {
                    lblEmpty.setVisible(false);
                    lblWrongPass.setVisible(false);
                    lblError.setVisible(false);
                    lblSuccess.setVisible(true);
                }
            }
            else {
                TeacherDTO teacherDTO = new TeacherDTO(
                        id,
                        fullName,
                        gder,
                        dateOfBirth,
                        email,
                        password,
                        2,
                        faculty
                );

                TeacherBLL teacherBLL = new TeacherBLL();
                int result = teacherBLL.InsertTeacher(teacherDTO);
                if(result == -1) {
                    lblEmpty.setVisible(false);
                    lblWrongPass.setVisible(false);
                    lblError.setVisible(true);
                    lblSuccess.setVisible(false);
                }
                else {
                    lblEmpty.setVisible(false);
                    lblWrongPass.setVisible(false);
                    lblError.setVisible(false);
                    lblSuccess.setVisible(true);
                }
            }
        }
    }

    void createID(int userType) {
        if(userType == 1) {
            StudentBLL studentBLL = new StudentBLL();
            String curID = String.valueOf(studentBLL.countStudents() + 1);

            if(curID.length() == 1)
                curID = "000" + curID;
            else if(curID.length() == 2)
                curID = "00" + curID;
            else if(curID.length() == 3)
                curID = "0" + curID;

            id = "1952" + curID;
            email = createEmail(1);
        }
        else {
            TeacherBLL teacherBLLBLL = new TeacherBLL();
            String curID = String.valueOf(teacherBLLBLL.countTeachers() + 1);

            if(curID.length() == 1)
                curID = "000" + curID;
            else if(curID.length() == 2)
                curID = "00" + curID;
            else if(curID.length() == 3)
                curID = "0" + curID;

            id = curID;
            email = createEmail(2);
        }
    }

    String createEmail(int userType) {
        if(userType == 1) {
            return id + "@gm.uit.edu.vn";
        }
        else {
            String curName = VNCharacterUtils.removeAccent(fullName.toLowerCase());
            String[] split = curName.split(" ");
            String email = "";
            for(int i = 0; i < split.length - 1; i++) {
                email += split[i].charAt(0);
            }
            email = split[split.length - 1] + email;
            return email + "@gm.uit.edu.vn";
        }
    }

    //this class use for remove vietnamese accent in a String
    public static class VNCharacterUtils {

        private static final char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
                'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
                'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
                'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
                'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
                'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
                'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
                'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
                'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
                'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
                'ữ', 'Ự', 'ự',};

        private static final char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
                'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
                'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
                'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
                'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
                'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
                'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
                'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
                'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
                'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
                'U', 'u', 'U', 'u',};

        public static char removeAccent(char ch) {
            int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
            if (index >= 0) {
                ch = DESTINATION_CHARACTERS[index];
            }
            return ch;
        }

        public static String removeAccent(String str) {
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < sb.length(); i++) {
                sb.setCharAt(i, removeAccent(sb.charAt(i)));
            }
            return sb.toString();
        }
    }
}
