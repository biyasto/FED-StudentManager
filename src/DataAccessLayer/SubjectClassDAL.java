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
        String sql = "select s1.* from subjectclass s1, studentclass s2 where s1.classId = s2.classId and s2.studentId = ?";
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

    public SubjectClassDTO getClassById(String id){
        SubjectClassDTO subjectClass = null;
        String sql = "select * from subjectclass where classId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            rs = pres.executeQuery();

            while (rs.next()) {
                subjectClass = new SubjectClassDTO();
                subjectClass.setClassId(rs.getString("classId"));
                subjectClass.setHeadMaster(rs.getString("headMaster"));
                subjectClass.setSubjectId(rs.getString("subjectId"));
                subjectClass.setSchoolYear(rs.getInt("schoolYear"));
                subjectClass.setSemester(rs.getInt("semester"));
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
        return subjectClass;
    }

    public List<SubjectClassDTO> getClassesBySubjectName(String name){
        List<SubjectClassDTO> list = new ArrayList<>();
        String sql = "select s1.* from subjectclass s1, subject s2 where s2.subjectname = ? and s1.subjectId = s2.subjectId";

        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            rs = pres.executeQuery();

            while (rs.next()) {
                SubjectClassDTO s = new SubjectClassDTO();
                s.setClassId(rs.getString("classId"));
                s.setHeadMaster(rs.getString("headMaster"));
                s.setSubjectId(rs.getString("subjectId"));
                s.setSchoolYear(rs.getInt("schoolYear"));
                s.setSemester(rs.getInt("semester"));


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

    public int InsertSubjectClass(SubjectClassDTO s) {
        String sql = "insert into subjectclass values (?,?,?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, s.getClassId());
            pres.setString(2, s.getHeadMaster());
            pres.setString(3, s.getSubjectId());
            pres.setInt(4, s.getSchoolYear());
            pres.setInt(5, s.getSemester());

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
