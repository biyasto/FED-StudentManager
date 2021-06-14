package DataAccessLayer;

import DataTransferObject.StudentDTO;
import DataTransferObject.TeacherDTO;
import Utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAL {
    private DatabaseUtils DBU = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

//    public static void main(String[] args) {
//        TeacherDAL dal = new TeacherDAL();
//
//        TeacherDTO t1 = new TeacherDTO("01", "Bảo Nhạc", true, "2001-07-23", "@mgail", "999999999999", 1, "CNPM");
//        TeacherDTO t2 = new TeacherDTO("02", "Đỗ Thiếu Phủ", true, "2001-07-23", "@mgail", "8888888888", 1, "CNPM");
//        TeacherDTO t3 = new TeacherDTO("03", "Tế Đỗ", true, "2001-07-23", "@mgail", "999999999999", 1, "CNPM");
//
//
//        dal.UpdatePassword(t2);
//        List<TeacherDTO> list = dal.GetALlTeacher();
//            //dal.UpdatePassword(st);
//        //list.forEach(s -> {
//            TeacherDTO s = dal.GetById("02");
//            System.out.println("Id>>      " + s.getId());
//            System.out.println("Name>>    " + s.getName());
//            System.out.println("Email>>   " + s.getEmail());
//            System.out.println("Gender>>  " + s.isGender());
//            System.out.println("Type>>    " + s.getType());
//            System.out.println("Birthday>>" + s.getBirthDay());
//            System.out.println("Password>>" + s.getPass());
//            System.out.println("Faculty>> " + s.getFaculty());
//            System.out.println();
//            System.out.println();
//        //});
//
//    }

    public List<TeacherDTO> GetALlTeacher() {
        List<TeacherDTO> list = new ArrayList<>();
        String sql = "select * from teacher";
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            rs = pres.executeQuery();

            while (rs.next()) {
                TeacherDTO s = new TeacherDTO();
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

    public int InsertTeacher(TeacherDTO s) {
        String sql = "insert into Teacher values (?,?,?,?,?,?,?,?);";
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

    public int UpdatePassword(TeacherDTO s) {
        String sql = "update Teacher set pass = ? where id = ?";
        int result = -1;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, s.getPass());
            pres.setString(2, s.getId());

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

    public TeacherDTO GetTeacher(String id, String password) {
        String sql = "select * from Teacher where id =? and pass=?";
        TeacherDTO s = null;
        try {
            DBU = new DatabaseUtils();
            conn = DBU.createConnection();
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            pres.setString(2, password);

            rs = pres.executeQuery();
            if (rs.next()) {
                s = new TeacherDTO();
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

    public int countTeachers() {
        String sql = "select count(*) from Teacher;";
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

}
