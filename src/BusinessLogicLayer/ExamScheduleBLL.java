package BusinessLogicLayer;

import DataAccessLayer.ExamScheduleDAL;
import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;

import java.sql.Date;
import java.util.List;

public class ExamScheduleBLL {
    private ExamScheduleDAL examSchedule = new ExamScheduleDAL();

    public List<ExamScheduleDTO> getAllExamSchedule(){ return examSchedule.getAllExamSchedule(); }
    public int addNewEvent(ExamScheduleDTO event) { return examSchedule.addNewEvent(event); }
    public List<String> getEmptyRoomForSchedule(Date date, int shift){ return examSchedule.getEmptyRoomForSchedule(date, shift); }
    public int getExamScheduleId(ExamScheduleDTO event) { return examSchedule.getExamScheduleId(event); }

    public int deleteExamSchedule(ExamScheduleDTO event) { return examSchedule.deleteExamSchedule(event); }

    public List<ExamScheduleDTO> getAllExamScheduleByStudent(StudentDTO student){ return examSchedule.getAllExamScheduleByStudent(student); }

    public List<ExamScheduleDTO> getAllExamScheduleByTeacher(TeacherDTO teacher){ return examSchedule.getAllExamScheduleByTeacher(teacher); }
}
