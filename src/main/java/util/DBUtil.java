package util;

import java.sql.*;

public class DBUtil {
    private Connection conn = null;
    private Statement stmt = null;
    public ResultSet rs = null;
    private PreparedStatement ppst = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载失败");
            e.printStackTrace();
        }
    }

    public void getconn() {
        try {
            String url = "jdbc:mysql://localhost:3306/library?serverTimezone=GMT%2B8";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
    }

    public void exequerystmt(String sql) {
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exeupdateppst(String sql, String... x) {
        try {
            ppst = conn.prepareStatement(sql);
            for (int i = 0; i < x.length; i++) {
                ppst.setString(i + 1, x[i]);
            }
            ppst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void exequeryppst(String sql, String... x) {
        try {
            ppst = conn.prepareStatement(sql);
            for (int i = 0; i < x.length; i++) {
                ppst.setString(i + 1, x[i]);
            }
            rs = ppst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exeupdate(String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    public void close() {
        closeResources();
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (ppst != null) ppst.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
