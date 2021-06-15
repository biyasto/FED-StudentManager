package DataAccessLayer;

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
}
