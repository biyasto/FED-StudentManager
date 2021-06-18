package DataAccessLayer;

import DataTransferObject.TranscriptDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TranscriptDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<TranscriptDTO> GetALlTranscript() {
        List<TranscriptDTO> list = new ArrayList<>();
        String sql = "select * from transcript";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();

            while (rs.next()) {
                TranscriptDTO s = new TranscriptDTO();
                s.setTranscriptId(rs.getString("transcriptId"));
                s.setSubjectId(rs.getString("subjectId"));
                s.setMarks(rs.getDouble("marks"));
                s.setFlag(rs.getInt("flag"));

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

    public List<TranscriptDTO> GetTranscriptOfClass(String classID, String studentID) {
        List<TranscriptDTO> list = new ArrayList<>();
        String sql = "select t.* from transcript t, studentclass s where s.studentId = ? and s.classId = ? and s.transcriptId = t.transcriptId;";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, studentID);
            pres.setString(2, classID);

            rs = pres.executeQuery();

            while (rs.next()) {
                TranscriptDTO s = new TranscriptDTO();
                s.setTranscriptId(rs.getString("transcriptId"));
                s.setSubjectId(rs.getString("subjectId"));
                s.setMarks(rs.getDouble("marks"));
                s.setFlag(rs.getInt("flag"));

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

    public int UpdateTranscript(Double marks, String id) {
        String sql = "update transcript set marks = ? where transcriptid = ?;";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setDouble(1, marks);
            pres.setString(2, id);

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
