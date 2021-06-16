package BusinessLogicLayer;

import DataAccessLayer.StudentCLassDAL;
import DataTransferObject.StudentCLassDTO;

import java.util.List;

public class StudentClassBLL {
    StudentCLassDAL studentClass = new StudentCLassDAL();

    public List<StudentCLassDTO> getAllStudentClass(){return studentClass.getAllStudentClass();}
}
