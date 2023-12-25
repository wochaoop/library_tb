import util.DBUtil;

import util.DBUtil;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserRegister extends UniversalWindow {//用户注册类

    public UserRegister(String title, int w, int h) {
        super(title, w, h);
        JLabel[] jLabels = {new JLabel("用 户 名"), new JLabel("密    码"), new JLabel("密码确认")};
        JTextField jTextField = new JTextField();
        JPasswordField[] jPasswordFields = {new JPasswordField(), new JPasswordField()};
        JButton[] jButtons = {new JButton("注册"), new JButton("重置")};
        for (int i = 0; i < jLabels.length; i++)
            jLabels[i].setFont(Const.font);
        jTextField.setFont(Const.font);
        jPasswordFields[0].setFont(Const.font);
        jPasswordFields[1].setFont(Const.font);
        jButtons[0].setFont(Const.font);
        jButtons[1].setFont(Const.font);
        add(jLabels[0]).setBounds(150, 20, 100, 40);
        add(jLabels[1]).setBounds(150, 110, 100, 40);
        add(jLabels[2]).setBounds(150, 200, 100, 40);
        add(jTextField).setBounds(120, 60, 160, 40);
        add(jPasswordFields[0]).setBounds(120, 150, 160, 40);
        add(jPasswordFields[1]).setBounds(120, 240, 160, 40);
        add(jButtons[0]).setBounds(90, 310, 100, 40);
        add(jButtons[1]).setBounds(210, 310, 100, 40);
        jButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextField.setText("");
                jPasswordFields[0].setText("");
                jPasswordFields[1].setText("");
            }
        });
        jButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBUtil dbUtil = new DBUtil();
                dbUtil.getconn();
                String yhm = jTextField.getText().trim();
                String mm1 = new String(jPasswordFields[0].getPassword()).trim();
                String mm2 = new String(jPasswordFields[0].getPassword()).trim();
                String sql = "select * from tb_manager where name=?";
                dbUtil.exequeryppst(sql, yhm);
                try {
                    if (dbUtil.rs.next())
                        JOptionPane.showMessageDialog(null, "该用户名已经被使用");
                    else if (mm1.equals(null) || mm1.equals("") || mm2.equals(null) || mm2.equals(""))
                        JOptionPane.showMessageDialog(null, "密码不能为空");
                    else if (!mm1.equals(mm2))
                        JOptionPane.showMessageDialog(null, "密码必须一致");
                    else {
                        sql = "insert into tb_manager(name,pwd) values(?,?)";
                        dbUtil.exeupdateppst(sql, yhm, mm1);
                        JOptionPane.showMessageDialog(null, "注册成功");
                        MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
                        if (winStack.size() > 0)
                            winStack.peek().setVisible(false);
                        dispose();
                        mainWindow.setWinStack(mainWindow, winStack);
                        mainWindow.addWindowListener(new WindowOp());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    //
    public static void main(String[] args) {
        new UserRegister("", 400, 440);
    }
}