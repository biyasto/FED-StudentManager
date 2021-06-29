package BusinessLogicLayer;

import DataAccessLayer.ExamRoomDAL;
import DataAccessLayer.ExamScheduleDAL;
import DataTransferObject.ExamScheduleDTO;

import java.util.List;

public class ExamRoomBLL {
    private ExamRoomDAL dal = new ExamRoomDAL();

    public int addExamRoom(int examId, String room, String classId){ return dal.addExamRoom(examId, room, classId); }

    public List<String> getEmptyRoomForExam(ExamScheduleDTO examScheduleDTO){ return dal.getEmptyRoomForExam(examScheduleDTO); }


}
