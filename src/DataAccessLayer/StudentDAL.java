package DataAccessLayer;

import DataTransferObject.StudentDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public List<StudentDTO> GetALlStudent() {
        List<StudentDTO> list = new ArrayList<>();
        String sql = "select * from student";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();

            while (rs.next()) {
                StudentDTO s = new StudentDTO();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getBoolean("gender"));
                s.setEmail(rs.getString("email"));
                s.setFaculty(rs.getString("faculty"));
                s.setBirthDay(rs.getString("birthday"));
                s.setPass(rs.getString("pass"));
                s.setType(rs.getInt("userType"));

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

    public int InsertStudent(StudentDTO s) {
        String sql = "insert into Student values (?,?,?,?,?,?,?,?);";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);

            pres.setString(1, s.getId());
            pres.setString(2, s.getName());
            pres.setBoolean(3, s.isGender());
            pres.setString(4, s.getBirthDay());
            pres.setString(5, s.getEmail());
            pres.setString(6, s.getPass());
            pres.setInt(7, s.getType());
            pres.setString(8, s.getFaculty());

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

    public int UpdatePassword(String id, String password) {
        String sql = "update Student set pass = ? where id = ?";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, password);
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

    public StudentDTO GetStudent(String id, String password) {
        String sql = "select * from student where id =? and pass=?";
        StudentDTO s = null;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            pres.setString(2, password);

            rs = pres.executeQuery();
            if (rs.next()) {
                s = new StudentDTO();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getBoolean("gender"));
                s.setEmail(rs.getString("email"));
                s.setFaculty(rs.getString("faculty"));
                s.setBirthDay(rs.getString("birthday"));
                s.setPass(rs.getString("pass"));
                s.setType(rs.getInt("userType"));
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
        return s;
    }
    public StudentDTO GetStudentByID(String id) {
        String sql = "select * from student where id =?";
        StudentDTO s = null;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);

            rs = pres.executeQuery();
            if (rs.next()) {
                s = new StudentDTO();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getBoolean("gender"));
                s.setEmail(rs.getString("email"));
                s.setFaculty(rs.getString("faculty"));
                s.setBirthDay(rs.getString("birthday"));
                s.setPass(rs.getString("pass"));
                s.setType(rs.getInt("userType"));
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
        return s;
    }
//
//    public StudentDTO getStudentBySubjectAndSchoolYear(String subjectId, int schoolYear, int semester) {
//        String sql = "select s2.studentId from subjectclass s1, studentclass s2 where s1.subjectId = ? and s1.classId = s2.classId and s1.schoolYear = ? and semester = ?";
//        StudentDTO s = null;
//        try {
//            DBU = new DatabaseUtils();
//            conn = DBU.createConnection();
//            pres = conn.prepareStatement(sql);
//            pres.setString(1, subjectId);
//            pres.setInt(2, schoolYear);
//            pres.setInt(3, semester);
//
//            rs = pres.executeQuery();
//            if (rs.next()) {
//                s = new StudentDTO();
//                s.setId(rs.getString("id"));
//                s.setName(rs.getString("name"));
//                s.setGender(rs.getBoolean("gender"));
//                s.setEmail(rs.getString("email"));
//                s.setFaculty(rs.getString("faculty"));
//                s.setBirthDay(rs.getString("birthday"));
//                s.setPass(rs.getString("pass"));
//                s.setType(rs.getInt("userType"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                conn.close();
//                pres.close();
//                rs.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return s;
//    }
    public int countStudents() {
        String sql = "select count(*) from Student;";
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

    public List<StudentDTO> getStudentsByClassId(String classID){
        List<StudentDTO> list = new ArrayList<>();
        String sql = "select s3.* from subjectclass s1, studentclass s2, student s3 where s1.classId = ? and s1.classId = s2.classId and s2.studentId = s3.id;";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, classID);
            rs = pres.executeQuery();

            while (rs.next()) {
                StudentDTO s = new StudentDTO();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("name"));
                s.setGender(rs.getBoolean("gender"));
                s.setEmail(rs.getString("email"));
                s.setFaculty(rs.getString("faculty"));
                s.setBirthDay(rs.getString("birthday"));
                s.setPass(rs.getString("pass"));
                s.setType(rs.getInt("userType"));

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

    public boolean checkExits(String id) {
        String sql = "select * from Student where id =?";
        boolean result = false;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);

            rs = pres.executeQuery();
            if(rs.next())
                result = true;

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
        return result;
    }
}
