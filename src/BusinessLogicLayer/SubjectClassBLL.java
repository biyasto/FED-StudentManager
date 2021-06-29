package BusinessLogicLayer;

import DataAccessLayer.SubjectClassDAL;
import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentCLassDTO;
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

    public SubjectClassDTO getClassById(String id){return dal.getClassById(id);}

    public List<SubjectClassDTO> getClassesByTeacherId(String id){return dal.getClassesByTeacherId(id);};

    public List<SubjectClassDTO> getClassesBySubjectName(String name){ return dal.getClassesBySubjectName(name); }

    public int InsertSubjectClass(SubjectClassDTO s) {
        return dal.InsertSubjectClass(s);
    }

    public List<SubjectClassDTO> findClassesForExam(String subjectId, int schoolYear, int semester){ return dal.findClassesForExam(subjectId, schoolYear, semester); }

    public List<SubjectClassDTO> getClassesForExamSchedule(ExamScheduleDTO examScheduleDTO){ return dal.getClassesForExamSchedule(examScheduleDTO); }

}
