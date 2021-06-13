package BusinessLogicLayer;

import DataAccessLayer.TeacherDAL;
import DataTransferObject.TeacherDTO;

import java.util.List;

public class TeacherBLL {
    private TeacherDAL dal = new TeacherDAL();
    public List<TeacherDTO> GetALlTeacher() {
        return dal.GetALlTeacher();
    }
    public int InsertTeacher(TeacherDTO s) {
        return InsertTeacher(s);
    }
    public int UpdatePassword(TeacherDTO s) {
        return UpdatePassword(s);
    }
    public TeacherDTO GetTeacher(String id, String password) {
        return dal.GetTeacher(id, password);
    }
}
