package BusinessLogicLayer;

import DataAccessLayer.SubjectClassDAL;
import DataTransferObject.SubjectClassDTO;

import java.util.List;

public class SubjectClassBLL {
    private SubjectClassDAL subjectClassDTO = new SubjectClassDAL();

    public List<SubjectClassDTO> getAllSubjectClass(){return subjectClassDTO.getAllSubjectClass();}
}
