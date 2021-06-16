package DataAccessLayer;

import DataTransferObject.ExamScheduleDTO;
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
                s.setExamType(rs.getInt("flag"));

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
}
