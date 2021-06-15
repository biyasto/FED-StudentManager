package BusinessLogicLayer;

import DataAccessLayer.SubjectDAL;
import DataTransferObject.SubjectDTO;

public class SubjectBLL {
    SubjectDAL dal = new SubjectDAL();

    public int InsertSubject(SubjectDTO s) {
        return dal.InsertSubject(s);
    }

    public SubjectDTO GetSubjectById(String id) {
        return dal.GetSubjectById(id);
    }
}
