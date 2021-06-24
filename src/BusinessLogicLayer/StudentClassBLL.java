package BusinessLogicLayer;

import DataAccessLayer.StudentCLassDAL;
import DataTransferObject.StudentCLassDTO;
import DataTransferObject.StudentDTO;

import java.util.List;

public class StudentClassBLL {
    StudentCLassDAL studentClass = new StudentCLassDAL();

    public List<StudentCLassDTO> getAllStudentClass(){return studentClass.getAllStudentClass();}

    public List<StudentCLassDTO> getAllClassOfStudent(String id){
        return studentClass.getAllClassOfStudent(id);
    }

    public int InsertStudent(String studentID, String classID, int transcriptID) {
        return studentClass.InsertStudent(studentID, classID, transcriptID);
    }
}
