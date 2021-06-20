package DataAccessLayer;

import DataTransferObject.SubjectDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<SubjectDTO> GetAllSubject() {
        List<SubjectDTO> list = new ArrayList<>();
        String sql = "select * from subject";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            rs = pres.executeQuery();
            while (rs.next()) {
                SubjectDTO subjectDTO = new SubjectDTO();
                subjectDTO.setSubjectID(rs.getString("subjectId"));
                subjectDTO.setSubjectName(rs.getString("subjectName"));
                subjectDTO.setCredits(rs.getInt("credit"));
                subjectDTO.setFaculty(rs.getString("faculty"));

                list.add(subjectDTO);
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

    public int InsertSubject(SubjectDTO s) {
        String sql = "insert into subject values (?,?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, s.getSubjectID());
            pres.setString(2, s.getSubjectName());
            pres.setInt(3, s.getCredits());
            pres.setString(4, s.getFaculty());

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


    public SubjectDTO GetSubjectById(String id) {
        SubjectDTO subjectDTO = null;
        String sql = "select * from subject where subjectid =?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);

            rs = pres.executeQuery();
            if (rs.next()) {
                subjectDTO = new SubjectDTO();
                subjectDTO.setSubjectID(rs.getString("subjectId"));
                subjectDTO.setSubjectName(rs.getString("subjectName"));
                subjectDTO.setCredits(rs.getInt("credit"));
                subjectDTO.setFaculty(rs.getString("faculty"));
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
        return subjectDTO;
    }

    public List<SubjectDTO> getSubjectsByStudentId(String id){
        List<SubjectDTO> list = new ArrayList<>();
        String sql = "select s2.subjectId, s2.subjectName, s2.credit, s2.faculty " +
                "from StudentClass s1, Subject s2, SubjectClass s3 " +
                "where s1.classId = s3.classId and " +
                "s2.subjectId = s3.subjectId and " +
                "s1.studentId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            rs = pres.executeQuery();

            while (rs.next()) {
                SubjectDTO s = new SubjectDTO();
                s.setSubjectID(rs.getString("subjectId"));
                s.setSubjectName(rs.getString("subjectName"));
                s.setCredits(rs.getInt("credit"));
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
