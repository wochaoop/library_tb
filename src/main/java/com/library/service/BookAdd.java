package com.library.service;

import com.library.util.DBUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookAdd extends UniversalWindow {// 添加图书类

    private int typeId; // 存储图书类型ID

    public BookAdd(String title, int w, int h) {
        super(title, w, h);
        JLabel[] jLabels = {new JLabel("图书名称："), new JLabel("图书类型："), new JLabel("作者：")};
        JTextField[] jTextFields = {new JTextField(), new JTextField(), new JTextField(), new JTextField()};
        JButton[] jButtons = {new JButton("添加"), new JButton("取消")};
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
            if (bookName.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "图书信息不能为空");
                return;
            }

            DBUtil dbUtil = new DBUtil();
            dbUtil.getconn();
            dbUtil.addBook(bookName,author, typeId);
            JOptionPane.showMessageDialog(null, "添加成功");
            dispose();
            MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
            if (!winStack.isEmpty()) {
                winStack.peek().setVisible(false);
            }
            dispose();
            mainWindow.setWinStack(mainWindow, winStack);
            mainWindow.addWindowListener(new WindowOp());
        });

        // 获取图书类型ID列表
        List<Integer> typeIdList = getTypeIdListFromDB();

        // 添加下拉框监听器
        typeComboBox.addActionListener(e -> {
            int selectedIndex = typeComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < typeIdList.size()) {
                typeId = typeIdList.get(selectedIndex);
            }
        });

        setVisible(true);
    }

    private List<String> getTypeListFromDB() {
        return getStrings();
    }

    static List<String> getStrings() {
        List<String> typeList = new ArrayList<>();

        DBUtil dbUtil = new DBUtil();
        try {
            dbUtil.getconn();
            String sql = "select typename from tb_booktype";
            dbUtil.exequery(sql);
            while (dbUtil.rs.next()) {
                String typeName = dbUtil.rs.getString("typename");
                typeList.add(typeName);
            }
        } catch (SQLException e) {
            System.out.println("获取图书类型列表失败");
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }

        return typeList;
    }

    private List<Integer> getTypeIdListFromDB() {
        List<Integer> typeIdList = new ArrayList<>();

        DBUtil dbUtil = new DBUtil();
        try {
            dbUtil.getconn();
            String sql = "select id from tb_booktype";
            dbUtil. exequery(sql);
            while (dbUtil.rs.next()) {
                int typeId = dbUtil.rs.getInt("id");
                typeIdList.add(typeId);
            }
        } catch (SQLException e) {
            System.out.println("获取图书类型ID列表失败");
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }

        return typeIdList;
    }

    public static void main(String[] args) {
        new BookAdd("", 400, 300);
    }
}
