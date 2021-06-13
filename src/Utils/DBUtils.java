package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    private Connection conn;
    public DBUtils() { }

    public Connection createConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //depend on different local MySql account, user and password below may wrong and lead to connection error
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement?useSSL=false&useUnicode=true&characterEncoding=UTF-8","root","123456");

            if(conn == null)
                System.out.println("connect failed");
            else
                System.out.println("connected successfully");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
