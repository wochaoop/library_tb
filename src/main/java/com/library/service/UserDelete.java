package com.library.service;


import com.library.util.DBUtil;
import javax.swing.*;

public class UserDelete extends UniversalWindow {//用户删除类

    public UserDelete(String title, int w, int h) {
        super(title, w, h);
        JLabel jLabel = new JLabel("请输入要删除的用户名：");
        JTextField jTextField = new JTextField();
        JButton[] jButtons = {new JButton("删除"), new JButton("取消")};
        jLabel.setFont(Const.font);
        jTextField.setFont(Const.font);
        jButtons[0].setFont(Const.font);
        jButtons[1].setFont(Const.font);
        add(jLabel).setBounds(80, 50, 400, 40);
        add(jTextField).setBounds(80, 100, 200, 40);
        add(jButtons[0]).setBounds(80, 170, 100, 40);
        add(jButtons[1]).setBounds(180, 170, 100, 40);
        jButtons[1].addActionListener(e -> {
            dispose();
            MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
            if (!winStack.isEmpty()) {
                winStack.peek().setVisible(false);
            }
            dispose();
            mainWindow.setWinStack(mainWindow, winStack);
            mainWindow.addWindowListener(new WindowOp());
        });
        jButtons[0].addActionListener(e -> {
            String yhm = jTextField.getText().trim();
            if (yhm.isEmpty()) {
                JOptionPane.showMessageDialog(null, "用户名不能为空");
                return;
            }
            DBUtil dbUtil = new DBUtil();
            dbUtil.getconn();
            String sql = "select * from tb_manager where name=?";
            dbUtil.exequeryppst(sql, yhm);
            try {
                if (!dbUtil.rs.next()) {
                    JOptionPane.showMessageDialog(null, "该用户名不存在");
                    return;
                }
                int option = JOptionPane.showConfirmDialog(null, "确定要删除该用户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    sql = "delete from tb_manager where name=?";
                    dbUtil.exeupdateppst(sql, yhm);
                    JOptionPane.showMessageDialog(null, "删除成功");
                    dispose();
                    MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
                    if (!winStack.isEmpty()) {
                        winStack.peek().setVisible(false);
                    }
                    dispose();
                    mainWindow.setWinStack(mainWindow, winStack);
                    mainWindow.addWindowListener(new WindowOp());
                }
            } catch (Exception e1) {
                System.out.println("删除用户失败");
                e1.printStackTrace();
            }
        });
        setVisible(true);
    }


    public static void main(String[] args) {
        new UserDelete("", 400, 300);
    }

}
