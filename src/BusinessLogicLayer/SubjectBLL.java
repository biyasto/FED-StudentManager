package BusinessLogicLayer;

import DataAccessLayer.SubjectDAL;
import DataTransferObject.SubjectDTO;

public class SubjectBLL {
    SubjectDAL dal = new SubjectDAL();
    public int InsertSubject(SubjectDTO s) {
        return InsertSubject(s);
    }
    public String GetSubjectNameById(String id) {
        return GetSubjectNameById(id);
    }
}
