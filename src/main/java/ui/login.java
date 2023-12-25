package ui;

import util.DBUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Stack;
public class login {
    public static void main(String[] args) {
        Stack<UniversalWindow> stack=new Stack<>();
        UniversalWindow universalWindow=new UniversalWindow("登录界面",400,240);
        universalWindow.setWinStack(universalWindow,stack);
        universalWindow.addWindowListener(new WindowOp());
        Font font=new Font("宋体", Font.BOLD,20);
        JLabel jLabel1=new JLabel("账  号:");
        jLabel1.setFont(font);
        JLabel jLabel2=new JLabel("密  码:");
        jLabel2.setFont(font);
        JTextField username=new JTextField();
        username.setFont(font);
        JPasswordField password=new JPasswordField();
        password.setFont(font);
        JButton jButton1=new JButton("登 录");
        jButton1.setFont(font);
        JButton jButton2=new JButton("重 置");
        jButton2.setFont(font);
        JButton jButton3=new JButton("注 册");
        jButton3.setFont(font);
        universalWindow.add(jLabel1).setBounds(80,30,80,30);
        universalWindow.add(username).setBounds(180,30,140,30);
        universalWindow.add(jLabel2).setBounds(80,80,80,30);
        universalWindow.add(password).setBounds(180,80,140,30);
        universalWindow.add(jButton1).setBounds(30,140,100,35);
        universalWindow.add(jButton2).setBounds(140,140,100,35);
        universalWindow.add(jButton3).setBounds(250,140,100,35);
        universalWindow.setVisible(true);
        jButton2.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });
        jButton1.addActionListener(e -> {
            String yhm=username.getText().trim();
            String mm=new String(password.getPassword()).trim();
            String sql="select * from tb_manager where name=?";
            DBUtil dbUtil=new DBUtil();
            dbUtil.getconn();
            dbUtil.exequeryppst(sql,yhm);
            try {
                if (dbUtil.rs.next())
                    if (dbUtil.rs.getString("pwd").equals(mm)){
                        MainWindow mainWindow= new MainWindow("图书管理系统",800,600);
                        stack.peek().setVisible(false);
                        mainWindow.setWinStack(mainWindow,stack);
                        mainWindow.addWindowListener(new WindowOp());

                    }
                    else
                        JOptionPane.showMessageDialog(null,"密码错误成功");
                else
                    JOptionPane.showMessageDialog(null,"查无此用户");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
