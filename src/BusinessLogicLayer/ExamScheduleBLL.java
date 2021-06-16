package BusinessLogicLayer;

import DataAccessLayer.ExamScheduleDAL;
import DataTransferObject.ExamScheduleDTO;

import java.util.List;

public class ExamScheduleBLL {
    private ExamScheduleDAL examSchedule = new ExamScheduleDAL();

    private List<ExamScheduleDTO> getAllExamSchedule(){return examSchedule.getAllExamSchedule();}
}
