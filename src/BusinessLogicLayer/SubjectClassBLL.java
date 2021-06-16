package BusinessLogicLayer;

import DataAccessLayer.SubjectClassDAL;
import DataTransferObject.SubjectClassDTO;

import java.util.List;

public class SubjectClassBLL {
    SubjectClassDAL dal = new SubjectClassDAL();

    public List<SubjectClassDTO> getAllSubjectClass() {
        return dal.getAllSubjectClass();
    }

    public List<SubjectClassDTO> getClassesByStudentId(String id) {
        return dal.getClassesByStudentId(id);
    }
}
