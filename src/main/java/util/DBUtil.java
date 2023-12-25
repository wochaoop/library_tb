package util;

import java.sql.*;
public class DBUtil {
    public Connection conn=null;
    public Statement stmt=null;
    public ResultSet rs=null;
    public PreparedStatement ppst=null;
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e1){}
    }
    private String url="jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private String username="root";
    private String password="123456";
    public  void  getconn(){//建立连接
        try {
            conn= DriverManager.getConnection(url,username,password);
            stmt=conn.createStatement();
        }catch (Exception e2){
            System.out.println("数据库连接失败");
            System.out.println(e2.getMessage());
        }
    }
    public void exequerystmt(String sql){//执行查询语句
        try {
            rs = stmt.executeQuery(sql);
        }catch (Exception e3){
        }
    }

    public void exequeryppst(String sql,String...x){
        try {
            ppst=conn.prepareStatement(sql);
            int i=1;
            if (x.length>0)
                for (String p:x) {
                    ppst.setString(i++,p);
                }
            rs=ppst.executeQuery();
        }catch (Exception e){
        }
    }

    public void exeupdateppst(String sql,String...x){
        try {
            ppst=conn.prepareStatement(sql);
            int i=1;
            if (x.length>0)
                for (String p:x) {
                    ppst.setString(i++,p);
                };
            ppst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exeupdate(String sql){//执行增删改语句
        try {
            stmt.executeUpdate(sql);
        }catch (Exception e3){
        }
    }
    public  void  close(){//关闭释放资源
        try {
            if (rs!=null)     rs.close();
            if (stmt!=null)   stmt.close();
            if (conn!=null)   conn.close();
        }catch (Exception e4){

        }

    }
}