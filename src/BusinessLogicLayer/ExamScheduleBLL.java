package BusinessLogicLayer;

import DataAccessLayer.ExamScheduleDAL;
import DataTransferObject.ExamScheduleDTO;

import java.sql.Date;
import java.util.List;

public class ExamScheduleBLL {
    private ExamScheduleDAL examSchedule = new ExamScheduleDAL();

    public List<ExamScheduleDTO> getAllExamSchedule(){ return examSchedule.getAllExamSchedule(); }
    public int addNewEvent(ExamScheduleDTO event) { return examSchedule.addNewEvent(event); }
    public List<String> getEmptyRoomForSchedule(Date date, int shift){ return examSchedule.getEmptyRoomForSchedule(date, shift); }
    public int getExamScheduleId(ExamScheduleDTO event) { return examSchedule.getExamScheduleId(event); }
}
