package DataAccessLayer;

import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamScheduleDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;
    private final List<String> rooms = Arrays.asList(
            "A1.1", "A1.2", "A1.3", "A1.4", "A2.1", "A2.2", "A2.3", "A2.4",
            "B1.1", "B1.2", "B1.3", "B1.4", "B2.1", "B2.2", "B2.3", "B2.4",
            "C1.1", "C1.2", "C1.3", "C1.4", "C2.1", "C2.2", "C2.3", "C2.4",
            "E1.1", "E1.2", "E1.3", "E1.4", "E2.1", "E2.2", "E2.3", "E2.4"
    );
    public List<ExamScheduleDTO> getAllExamSchedule(){
        List<ExamScheduleDTO> list = new ArrayList<>();
        String sql = "select * from ExamSchedule";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next()) {
                ExamScheduleDTO s = new ExamScheduleDTO();
                s.setClassId(rs.getString("classId"));
                s.setExamDate(rs.getDate("examdate"));
                s.setFlag(rs.getInt("flag"));
                s.setShift(rs.getInt("shift"));
                s.setTotalTime(rs.getTime("totalTime"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                pres.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getEmptyRoomForSchedule(Date date, int shift){
        List<String> list = new ArrayList<>();
        String sql = " select e2.room from examschedule e1, examroom e2 where e1.id = e2.examId and e1.examDate = ? and shift = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            pres.setDate(1, date);
            pres.setInt(2, shift);
            rs = pres.executeQuery();

            while (rs.next()) {
                String s = rs.getString("room");
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                pres.close();
                rs.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public int addNewEvent(ExamScheduleDTO event) {
        String sqlInsert = "insert into examschedule values (?,?,?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sqlInsert);

            pres.setString(1, event.getClassId());
            pres.setDate(2, event.getExamDate());
            pres.setInt(3, event.getFlag());
            pres.setInt(4, event.getShift());
            pres.setTime(5, event.getTotalTime());

            result = pres.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                pres.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
