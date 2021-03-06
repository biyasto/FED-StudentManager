package DataAccessLayer;

import DataTransferObject.StudentCLassDTO;
import DataTransferObject.TranscriptDTO;
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

    public List<StudentCLassDTO> getAllStudentClass() {
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

    public List<StudentCLassDTO> getAllClassOfStudent(String id) {
        List<StudentCLassDTO> list = new ArrayList<>();
        String sql = "select * from StudentClass where studentId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
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

    public int InsertStudent(String studentID, String classID, int transcriptID) {
        String sql = "insert into StudentClass values (?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, studentID);
            pres.setString(2, classID);
            pres.setInt(3, transcriptID);

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

    public int DeleteStudent(String studentID, String classID, int transcriptID) {
        String sql = "delete from studentclass where studentid = ? and classid = ? and transcriptId = ?;";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, studentID);
            pres.setString(2, classID);
            pres.setInt(3, transcriptID);

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
