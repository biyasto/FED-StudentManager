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

    public static void main(String[] args) {
        StudentDAL dal = new StudentDAL();
        List<StudentDTO> list = dal.GetALlStudent();
        list.forEach(s -> {
            System.out.println("Id>>" + s.getId());
            System.out.println("Name>>" + s.getName());
            System.out.println("Email>>" + s.getEmail());
            System.out.println("Gender>>" + s.getGender());
            System.out.println("Type>>" + s.getType());
            System.out.println("Birthday>>" + s.getBirthDay());
            System.out.println("Faculty>>" + s.getFaculty());
            System.out.println();
            System.out.println();
        });
    }

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
}
