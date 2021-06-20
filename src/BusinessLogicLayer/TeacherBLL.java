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
        return dal.InsertTeacher(s);
    }

    public int UpdatePassword(TeacherDTO s) {
        return dal.UpdatePassword(s);
    }

    public TeacherDTO GetTeacher(String id, String password) {
        return dal.GetTeacher(id, password);
    }

    public TeacherDTO GetTeacherByID(String id) {
        return dal.GetTeacherByID(id);
    }

    public int countTeachers() {
        return dal.countTeachers();
    }
}
