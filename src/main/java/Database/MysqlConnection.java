package Database;

import java.sql.*;

public class MysqlConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    java.sql.Connection conn = null;
    Statement stmt = null;
    private static String DBUser = "";
    private static String DBPassword = "";
    private static String DBURL = "";

    public static void setDBPassword(String DBPassword) {
        MysqlConnection.DBPassword = DBPassword;
    }

    public static void setDBURL(String DBURL) {
        MysqlConnection.DBURL = DBURL;
    }

    public static void setDBUser(String DBUser) {
        MysqlConnection.DBUser = DBUser;
    }

    public void connect() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动异常" + e.toString());
        }
        try {
            conn = DriverManager.getConnection(DBURL, DBUser, DBPassword);
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.toString());

        }
    }

    public void getAll() {
        connect();
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM teachers";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.print("ID: " + id);
                System.out.print(", 名字: " + name);
                System.out.print("\n");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.print(se.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
}
