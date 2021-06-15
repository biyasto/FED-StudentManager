package DataAccessLayer;

import DataTransferObject.StudentCLassDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentCLassDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<StudentCLassDTO> getAllStudentClass(){
        List<StudentCLassDTO> list = new ArrayList<>();
        String sql = "select * from StudentClass";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();

            while (rs.next()) {
                StudentCLassDTO s = new StudentCLassDTO();
                s.setClassId(rs.getString("classId"));
                s.setStudentId(rs.getString("studentId"));
                s.setTranScriptId(rs.getString("transcriptId"));

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
