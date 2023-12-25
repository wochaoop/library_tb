package ui;

import util.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserUpdate extends UniversalWindow {
    public  UserUpdate(String title,int w,int h){
        super(title,w,h);
        JLabel[] jLabels={new JLabel("用 户 名"),new JLabel("新 密 码"),new JLabel("密码确认")};
        JTextField jTextField=new JTextField();
        JPasswordField[] jPasswordFields={new JPasswordField(),new JPasswordField()};
        JButton[] jButtons={new JButton("确认"),new JButton("重置")};
        for (JLabel jLabel : jLabels) jLabel.setFont(Const.font);
        jTextField.setFont(Const.font);
        jPasswordFields[0].setFont(Const.font);
        jPasswordFields[1].setFont(Const.font);
        jButtons[0].setFont(Const.font);
        jButtons[1].setFont(Const.font);
        add(jLabels[0]).setBounds(150,20,100,40);
        add(jLabels[1]).setBounds(150,110,100,40);
        add(jLabels[2]).setBounds(150,200,100,40);
        add(jTextField).setBounds(120,60,160,40);
        add(jPasswordFields[0]).setBounds(120,150,160,40);
        add(jPasswordFields[1]).setBounds(120,240,160,40);
        add(jButtons[0]).setBounds(90,310,100,40);
        add(jButtons[1]).setBounds(210,310,100,40);
        jButtons[1].addActionListener(e -> {
            jTextField.setText("");
            jPasswordFields[0].setText("");
            jPasswordFields[1].setText("");
        });
        jButtons[0].addActionListener(e -> {
            DBUtil dbUtil=new DBUtil();
            dbUtil.getconn();
            String yhm=jTextField.getText().trim();
            String mm1=new String(jPasswordFields[0].getPassword()).trim();
            String mm2=new String(jPasswordFields[0].getPassword()).trim();
            String sql="select * from tb_manager where name=?";
            dbUtil.exequeryppst(sql,yhm);
            try {
                if (dbUtil.rs.next())
                    JOptionPane.showMessageDialog(null, "该用户名已经被使用");
                else if (mm1.isEmpty() || mm2.isEmpty())
                    JOptionPane.showMessageDialog(null, "密码不能为空");
                else if (!mm1.equals(mm2))
                    JOptionPane.showMessageDialog(null, "密码必须一致");
                else {
                    sql = "insert into tb_manager(name,pwd) values(?,?)";
                    dbUtil.exeupdateppst(sql, yhm, mm1);
                    JOptionPane.showMessageDialog(null, "注册成功");
                    MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
                    if (!winStack.isEmpty())
                        winStack.peek().setVisible(false);
                    dispose();
                    mainWindow.setWinStack(mainWindow, winStack);
                    mainWindow.addWindowListener(new WindowOp());
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new UserUpdate("",400,440);
    }
}
