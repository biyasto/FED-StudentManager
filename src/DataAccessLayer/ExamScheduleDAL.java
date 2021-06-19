package DataAccessLayer;

import DataTransferObject.ExamScheduleDTO;
import DataTransferObject.StudentDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamScheduleDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

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
