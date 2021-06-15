package DataAccessLayer;

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
}
