package DataAccessLayer;

import Utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAL {
    private DBUtils dbu = null;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private ResultSet rs = null;

    public String checkStudentLogin(String user, String password) {
        String result = "";
        String sql = "select id, userType from Student where id=? and pass=?";

        try {
            dbu = new DBUtils();
            conn = dbu.createConn();
            pres = conn.prepareStatement(sql);

            pres.setString(1, user);
            pres.setString(2, password);

            rs = pres.executeQuery();

            while(rs.next()) {
                result = rs.getString("id") + "/" + rs.getString("userType");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
                pres.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public String checkTeacherLogin(String user, String password) {
        String result = "";
        String sql = "select id, userType from Teacher where id=? and pass=?";

        try {
            dbu = new DBUtils();
            conn = dbu.createConn();
            pres = conn.prepareStatement(sql);

            pres.setString(1, user);
            pres.setString(2, password);

            rs = pres.executeQuery();

            while(rs.next()) {
                result = rs.getString("id") + "/" + rs.getString("userType");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
                pres.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
