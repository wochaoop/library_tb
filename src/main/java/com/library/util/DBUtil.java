package com.library.util;

import cn.hutool.core.util.RandomUtil;

import java.sql.*;

public class DBUtil {
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;
    public PreparedStatement ppst = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e1) {
            System.out.println("数据库驱动加载失败");
        }
    }

    public void getconn() {//建立连接
        try {
            String url = "jdbc:mysql://localhost:3306/library?serverTimezone=GMT%2B8";
            String username = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (Exception e2) {
            System.out.println("数据库连接失败");
        }
    }

    public void addBook(String bookName, String author, int typeid) {
        try {
            String sql = "INSERT INTO tb_bookinfo (bookname, author,typeid,ISBN,price,storage) VALUES (?, ?, ?,?,?,?)";
            ppst = conn.prepareStatement(sql);
            ppst.setString(1, bookName);
            ppst.setString(2, author);
            ppst.setInt(3, typeid);
            ppst.setString(4, "7-121");
            ppst.setFloat(5, RandomUtil.randomFloat(0, 100));
            ppst.setInt(6, Integer.parseInt(RandomUtil.randomNumbers(3)));
            ppst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("添加图书失败");
            e.printStackTrace();
        }
    }

    public void updateBook(String bookName, String author, int typeid,int id) {
        try {
            conn.setAutoCommit(false);
            String sql = "update tb_bookinfo set bookname = ?, author = ?, typeid = ? where id = ?";
            ppst = conn.prepareStatement(sql);
            ppst.setString(1, bookName);
            ppst.setString(2, author);
            ppst.setInt(3, typeid);
            ppst.setInt(4, id);

            ppst.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("更新图书失败");
                    ex.printStackTrace();
                }
            }
        } finally {
            close();
        }
    }

    public void exequerystmt(String sql) {//执行查询语句
        try {
            rs = stmt.executeQuery(sql);
        } catch (Exception e3) {
            System.out.println("查询失败");
        }
    }

    public ResultSet exequery(String sql) {
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("查询失败");
            e.printStackTrace();
        }
        return null;
    }

    public void exequeryppst(String sql, String... x) {
        try {
            ppst = conn.prepareStatement(sql);
            int i = 1;
            for (String p : x) {
                ppst.setString(i++, p);
            }
            rs = ppst.executeQuery();
        } catch (Exception e) {
            System.out.println("查询失败");
        }
    }

    public void exeupdate(String sql) {//执行增删改语句
        try {
            stmt.executeUpdate(sql);
        } catch (Exception e3) {
            System.out.println("更新失败");
        }
    }

    public void close() {//关闭释放资源
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception e4) {
            System.out.println("关闭失败");
        }

    }

    public void exeupdateppst(String sql, String... x) {
        try {
            ppst = conn.prepareStatement(sql);
            int i = 1;
            for (String p : x) {
                ppst.setString(i++, p);
            }
            ppst.executeUpdate();
        } catch (Exception e) {
            System.out.println("更新失败");
            e.printStackTrace();
        }
    }

}
