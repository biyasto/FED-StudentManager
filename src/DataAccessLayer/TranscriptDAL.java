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
                s.setTranscriptId(rs.getInt("transcriptId"));
                s.setMark1(rs.getDouble("mark1"));
                s.setMark2(rs.getDouble("mark2"));
                s.setMark3(rs.getDouble("mark3"));
                s.setMark4(rs.getDouble("mark4"));

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

    public TranscriptDTO GetTranscriptOfClass(String classID, String studentID) {
        TranscriptDTO transcriptDTO = null;
        String sql = "select t.* from transcript t, studentclass s where s.studentId = ? and s.classId = ? and s.transcriptId = t.transcriptId;";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, studentID);
            pres.setString(2, classID);

            rs = pres.executeQuery();

            while (rs.next()) {
                transcriptDTO = new TranscriptDTO();
                transcriptDTO.setTranscriptId(rs.getInt("transcriptId"));
                transcriptDTO.setMark1(rs.getDouble("mark1"));
                transcriptDTO.setMark2(rs.getDouble("mark2"));
                transcriptDTO.setMark3(rs.getDouble("mark3"));
                transcriptDTO.setMark4(rs.getDouble("mark4"));
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
        return transcriptDTO;
    }

    public List<TranscriptDTO> getTranscriptsByStudentId(String studentID) {
        List<TranscriptDTO> transcripts = null;
        String sql = "select t.* from studentclass s, transcript t where s.transcriptId = t.transcriptId and s.studentId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, studentID);

            rs = pres.executeQuery();

            while (rs.next()) {
                TranscriptDTO transcriptDTO = new TranscriptDTO();
                transcriptDTO.setTranscriptId(rs.getInt("transcriptId"));
                transcriptDTO.setMark1(rs.getDouble("mark1"));
                transcriptDTO.setMark2(rs.getDouble("mark2"));
                transcriptDTO.setMark3(rs.getDouble("mark3"));
                transcriptDTO.setMark4(rs.getDouble("mark4"));

                transcripts.add(transcriptDTO);
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
        return transcripts;
    }

    public int UpdateTranscript(TranscriptDTO transcript) {
        String sql = "update transcript set mark1=?, mark2=?, mark3=?, mark4=? where transcriptid = ?;";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setDouble(1, transcript.getMark1());
            pres.setDouble(2, transcript.getMark2());
            pres.setDouble(3, transcript.getMark3());
            pres.setDouble(4, transcript.getMark4());
            pres.setInt(5, transcript.getTranscriptId());

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

    public int countTranscripts() {
        String sql = "select count(*) from Transcript;";
        int count = 0;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            rs = pres.executeQuery();
            if(rs.next())
                count = rs.getInt("count(*)");

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
        return count;
    }

    public int InsertTranscript(int transcriptID) {
        String sql = "insert into Transcript values (?,?,?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setInt(1, transcriptID);
            pres.setDouble(2, -1);
            pres.setDouble(3, -1);
            pres.setDouble(4, -1);
            pres.setDouble(5, -1);

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

    public double calGPAByTranscriptId(int id){
        double GPA = 0;
        String sql = "select * from transcript t, studentclass s1, subjectclass s2 where s1.classId = s2.classId and s1.transcriptId = t.transcriptId and t.transcriptId = ?";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            rs = pres.executeQuery();


            while (rs.next()) {
                double mark1 = rs.getDouble("mark1");
                double mark2 = rs.getDouble("mark2");
                double mark3 = rs.getDouble("mark3");
                double mark4 = rs.getDouble("mark4");
                double attendancePercent = rs.getInt("attendance");
                double quizPercent = rs.getInt("quiz");
                double practicePercent = rs.getInt("practice");
                double finalPercent = rs.getInt("final");

                if(mark1 != -1)
                    GPA += mark1 * attendancePercent * 0.1;
                if(mark2 != -1)
                    GPA += mark2 * quizPercent * 0.1;
                if(mark3 != -1)
                    GPA += mark3 * practicePercent * 0.1;
                if(mark4 != -1)
                    GPA += mark4 * finalPercent * 0.1;
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
        return GPA;
    }
}
