package BusinessLogicLayer;

import DataAccessLayer.StudentDAL;
import DataTransferObject.StudentDTO;

import java.util.List;

public class StudentBLL {
    private StudentDAL dal = new StudentDAL();

    public List<StudentDTO> GetALlStudent() {
        return dal.GetALlStudent();
    }
    public int InsertStudent(StudentDTO s) {
        return dal.InsertStudent(s);
    }
    public int UpdatePassword(StudentDTO s) {
        return UpdatePassword(s);
    }
    public StudentDTO GetStudent(String id, String password) {
        return dal.GetStudent(id, password);
    }
    public int countStudents() {
        return dal.countStudents();
    }
}
