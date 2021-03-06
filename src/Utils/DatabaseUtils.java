package Utils;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseUtils {

    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/StudentManagement?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String pass = "123456";

    public DatabaseUtils() {
    }

    public Connection createConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            if (conn == null) {
                System.out.println("Kết nối không thành công");
            } else {
                System.out.println("Kết nối thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }

}
