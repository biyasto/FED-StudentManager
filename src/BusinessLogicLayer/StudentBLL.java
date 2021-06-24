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

    public StudentDTO GetStudentByID(String id) {return dal.GetStudentByID(id);};

    public int UpdatePassword(String id, String password) {
        return dal.UpdatePassword(id, password);
    }

    public StudentDTO GetStudent(String id, String password) {
        return dal.GetStudent(id, password);
    }

    public int countStudents() {
        return dal.countStudents();
    }

    public List<StudentDTO> getStudentsByClassId(String classID) {
        return dal.getStudentsByClassId(classID);
    }

    public boolean checkExits(String id) {
        return dal.checkExits(id);
    }
}
