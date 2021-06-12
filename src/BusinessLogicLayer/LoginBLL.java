package BusinessLogicLayer;

import DataAccessLayer.LoginDAL;

public class LoginBLL {
    LoginDAL loginDAL = new LoginDAL();

    public String checkStudentLogin(String user, String password) {
        return loginDAL.checkStudentLogin(user, password);
    }

    public String checkTeacherLogin(String user, String password) {
        return loginDAL.checkTeacherLogin(user, password);
    }
}
