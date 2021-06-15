package DataAccessLayer;

import DataTransferObject.SubjectClassDTO;
import DataTransferObject.SubjectDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectClassDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<SubjectClassDTO> getAllSubjectClass(){
        List<SubjectClassDTO> list = new ArrayList<>();
        String sql = "select * from SubjectClass";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();

            while (rs.next()) {
                SubjectClassDTO s = new SubjectClassDTO();
                s.setClassId(rs.getString("classId"));
                s.setHeadMaster(rs.getString("headMaster"));
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));
                s.setFaculty(rs.getString("faculty"));

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

    public List<SubjectClassDTO> getClassesByStudentId(String id){
        List<SubjectClassDTO> list = new ArrayList<>();
        String sql = "select s1.classId, s1.headMaster, s1.subjectId, s1.schoolYear, s1.semester from subjectclass s1, studentclass s2 where s1.classId = s2.classId and s2.studentId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            rs = pres.executeQuery();

            while (rs.next()) {
                SubjectClassDTO s = new SubjectClassDTO();
                s.setClassId(rs.getString("classId"));
                s.setHeadMaster(rs.getString("headMaster"));
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));
                s.setFaculty(rs.getString("faculty"));

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
