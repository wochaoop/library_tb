package com.library.service;

import com.library.util.DBUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

import static com.library.service.BookAdd.getStrings;

public class BookModify extends UniversalWindow { // 修改图书类

    private int bookId; // 存储图书ID

    public BookModify(String title, int w, int h) {
        super(title, w, h);
        JLabel jLabel = new JLabel("请输入要修改的图书ID：");
        JTextField jTextField = new JTextField();
        JButton jButton = new JButton("确定");

        jLabel.setFont(Const.font);
        jTextField.setFont(Const.font);
        jButton.setFont(Const.font);

        add(jLabel).setBounds(50, 50, 280, 40);
        add(jTextField).setBounds(280, 50, 200, 40);
        add(jButton).setBounds(180, 130, 100, 40);

        jButton.addActionListener(e -> {
            String bookIdStr = jTextField.getText().trim();
            if (bookIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "请输入图书ID");
                invalidate();
                validate();
                repaint();
                return;
            }

            try {
                bookId = Integer.parseInt(bookIdStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请输入数字");
                invalidate();
                validate();
                repaint();
                return;
            }

            String[] bookInfo = getBookInfoFromDB(bookId);
            if (bookInfo[0] == null) {
                JOptionPane.showMessageDialog(null, "该图书不存在");
                invalidate();
                validate();
                repaint();
                return;
            }

            dispose();
            modifyBook(bookInfo);
        });

        setVisible(true);
    }

    private void modifyBook(String[] bookInfo) {
        getContentPane().removeAll();
        JLabel[] jLabels = {new JLabel("图书名称："), new JLabel("图书类型："), new JLabel("作者：")};
        JTextField[] jTextFields = {new JTextField(), new JTextField(), new JTextField()};
        JButton[] jButtons = {new JButton("修改"), new JButton("取消")};
        JComboBox<String> typeComboBox = new JComboBox<>();

        // 获取图书类型列表
        List<String> typeList = getTypeListFromDB();
        for (String type : typeList) {
            typeComboBox.addItem(type);
        }

        for (int i = 0; i < 3; i++) {
            jLabels[i].setFont(Const.font);
            jTextFields[i].setFont(Const.font);
            add(jLabels[i]).setBounds(80, 50 + 50 * i, 150, 40);
            if (i == 1) {
                add(typeComboBox).setBounds(200, 50 + 50 * i, 200, 40);
            } else {
                jTextFields[0].setText(bookInfo[0]); // 填充图书信息
                jTextFields[2].setText(bookInfo[2]); // 填充图书信息
                add(jTextFields[i]).setBounds(200, 50 + 50 * i, 200, 40);
            }
        }
        for (int i = 0; i < 2; i++) {
            jButtons[i].setFont(Const.font);
            add(jButtons[i]).setBounds(80 + 120 * i, 250, 100, 40);
        }
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
            String bookName = jTextFields[0].getText().trim();
            String author = jTextFields[2].getText().trim();
            int typeId = typeComboBox.getSelectedIndex(); // 获取选择的图书类型ID

            if (bookName.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "图书信息不能为空");
                return;
            }

            DBUtil dbUtil = new DBUtil();
            dbUtil.getconn();
            dbUtil.updateBook(bookName, author, typeId,bookId);
            JOptionPane.showMessageDialog(null, "修改成功");
            dispose();
            MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
            if (!winStack.isEmpty()) {
                winStack.peek().setVisible(false);
            }
            dispose();
            mainWindow.setWinStack(mainWindow, winStack);
            mainWindow.addWindowListener(new WindowOp());
        });

        setVisible(true);
    }

    private String[] getBookInfoFromDB(int bookId) {
        String[] bookInfo = new String[3];

        DBUtil dbUtil = new DBUtil();
        try {
            dbUtil.getconn();
            String sql = "select bookname,  author from tb_bookinfo where tb_bookinfo.id = " + bookId;
            dbUtil.exequerystmt(sql);
            if (dbUtil.rs.next()) {
                bookInfo[0] = dbUtil.rs.getString("bookname");
                bookInfo[2] = dbUtil.rs.getString("author");
            }
        } catch (SQLException e) {
            System.out.println("查询图书信息失败");
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }

        return bookInfo;
    }

    private List<String> getTypeListFromDB() {
        return getStrings();
    }

    public static void main(String[] args) {
        new BookModify("", 400, 300);
    }
}
