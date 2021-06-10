module QuanLyHocSInh {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    opens GUI.CreateAccount;
    opens GUI.ForgotPassword_Confirm;
    opens GUI.ForgotPassword_FindAccount;
    opens GUI.Login;
    opens GUI.MainMenu;
    opens GUI.Schedule_Calendar;
    opens GUI.Schedule_Event;
    opens GUI.Search_Classes;
    opens GUI.Search_Users;
    opens GUI.StudentGrades;
    opens GUI.UserDetail;
    opens GUI;
}