package BusinessLogicLayer;

import DataAccessLayer.ExamScheduleDAL;
import DataTransferObject.ExamScheduleDTO;

import java.util.List;

public class ExamScheduleBLL {
    private ExamScheduleDAL examSchedule = new ExamScheduleDAL();

    public List<ExamScheduleDTO> getAllExamSchedule(){ return examSchedule.getAllExamSchedule(); }
    public int addNewEvent(ExamScheduleDTO event) { return examSchedule.addNewEvent(event); }
}
