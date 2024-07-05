package Database;

import Teacher.Teacher;
import com.heuluck.teachermanagesys.Context;

import java.sql.*;
import java.util.ArrayList;

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

    public boolean isExist() {
        connect();
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SHOW TABLES LIKE '%teachers%';";
            ResultSet rs = stmt.executeQuery(sql);
            boolean isExist = false;
            while (rs.next()) {
                String tableName = rs.getString("Tables_in_teachermanage (%teachers%)");
                if (tableName.equals("teachers"))
                    isExist = true;
                System.out.println(tableName);
            }
            rs.close();
            stmt.close();
            conn.close();
            return isExist;
        } catch (SQLException se) {
            System.out.println("数据库错误：" + se.toString());
            return false;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean createTable() {
        connect();
        try {
            stmt = conn.createStatement();
            String sql;
            sql = """
                    create table if not exists teachers
                    (
                        id                varchar(50)  not null
                            primary key,
                        name              varchar(100) not null,
                        sex               varchar(20)  not null,
                        `rank`            varchar(100) not null,
                        lessons           text         not null,
                        classes           text         not null,
                        theoryClassLength int          not null,
                        labClassLength    int          not null
                    );
                    """;
            boolean rs = stmt.execute(sql);
            int updateCount = stmt.getUpdateCount();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException se) {
            System.out.println("数据库错误：" + se.toString());
            return false;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public void getAll() {
        connect();
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM teachers";
            ResultSet rs = stmt.executeQuery(sql);
            Context.SQLTeachers = new ArrayList<Teacher>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                String rank = rs.getString("rank");
                String lessons = rs.getString("lessons");
                String classes = rs.getString("classes");
                int theoryLength = rs.getInt("theoryClassLength");
                int labLength = rs.getInt("labClassLength");
                Teacher teacher = new Teacher(id, name, sex, rank, lessons, classes, theoryLength, labLength);
                Context.SQLTeachers.add(teacher);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void InsertAll() {
        connect();
        String sql = """
                      INSERT INTO teachers(id,name,sex,`rank`,lessons,classes,theoryClassLength,labClassLength)
                      VALUES(?,?,?,?,?,?,?,?)
                      ON DUPLICATE KEY UPDATE
                      name = VALUES(name),
                      sex = VALUES(sex),
                      `rank` = VALUES(`rank`),
                      lessons = VALUES(lessons),
                      classes = VALUES(classes),
                      theoryClassLength = VALUES(theoryClassLength),
                      labClassLength = VALUES(labClassLength);
                """;
        Context.allTeachers.forEach(teacher -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, teacher.getId());
                ps.setString(2, teacher.getName());
                ps.setString(3, teacher.getSex());
                ps.setString(4, teacher.getRank());
                ps.setString(5, String.join(";", teacher.getLessons()));
                ps.setString(6, String.join(";", teacher.getClasses()));
                ps.setInt(7, teacher.getTheoryClassLength());
                ps.setInt(8, teacher.getLabClassLength());
                ps.executeUpdate();
            } catch (SQLException se) {
                System.out.println("数据库错误：" + se.toString());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        });
        try {
            conn.close();
        } catch (SQLException se) {
            System.out.println("关闭数据库时出现数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.println("关闭数据库时出现错误：" + e.toString());
        }
    }

    public void insert(Teacher teacher) {
        connect();
        String sql = """
                      INSERT INTO teachers(id,name,sex,`rank`,lessons,classes,theoryClassLength,labClassLength)
                      VALUES(?,?,?,?,?,?,?,?)
                      ON DUPLICATE KEY UPDATE
                      name = VALUES(name),
                      sex = VALUES(sex),
                      `rank` = VALUES(`rank`),
                      lessons = VALUES(lessons),
                      classes = VALUES(classes),
                      theoryClassLength = VALUES(theoryClassLength),
                      labClassLength = VALUES(labClassLength);
                """;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getId());
            ps.setString(2, teacher.getName());
            ps.setString(3, teacher.getSex());
            ps.setString(4, teacher.getRank());
            ps.setString(5, String.join(";", teacher.getLessons()));
            ps.setString(6, String.join(";", teacher.getClasses()));
            ps.setInt(7, teacher.getTheoryClassLength());
            ps.setInt(8, teacher.getLabClassLength());
            ps.executeUpdate();
        } catch (SQLException se) {
            System.out.println("数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            conn.close();
        } catch (SQLException se) {
            System.out.println("关闭数据库时出现数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.println("关闭数据库时出现错误：" + e.toString());
        }
    }

    public void delete(Teacher teacher) {
        connect();
        String sql = "DELETE FROM teachers WHERE id = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, teacher.getId());
            ps.executeUpdate();
        } catch (SQLException se) {
            System.out.println("数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            conn.close();
        } catch (SQLException se) {
            System.out.println("关闭数据库时出现数据库错误：" + se.toString());
        } catch (Exception e) {
            System.out.println("关闭数据库时出现错误：" + e.toString());
        }
    }
}