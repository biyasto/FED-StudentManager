package BusinessLogicLayer;

import DataAccessLayer.SubjectDAL;
import DataTransferObject.SubjectDTO;

import java.util.List;

public class SubjectBLL {
    SubjectDAL dal = new SubjectDAL();

    public List<SubjectDTO> GetAllSubject() {
        return dal.GetAllSubject();
    }

    public int InsertSubject(SubjectDTO s) {
        return dal.InsertSubject(s);
    }

    public SubjectDTO GetSubjectById(String id) {
        return dal.GetSubjectById(id);
    }

    public List<SubjectDTO> getSubjectsByStudentId(String id){return dal.getSubjectsByStudentId(id);}

    public SubjectDTO getSubjectByTranscriptId(String id){ return dal.getSubjectByTranscriptId(id); }

    public int countSubject() {
        return dal.countSubject();
    }
}
