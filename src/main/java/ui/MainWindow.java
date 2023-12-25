package ui;

import service.DataOperation;
import util.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainWindow extends UniversalWindow {

    private DefaultTableModel defaultTableModel;
    private JTable jTable;

    MainWindow(String title, int w, int h) {
        super(title, w, h);
        initializeUI();
        setupListeners();
        loadBookInfoData();
        setVisible(true);
    }

    private void initializeUI() {
        createMenu();
        createTopPanel();
        createRightPanel();
        createBottomPanel();
        createTable();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        for (int i = 0; i < Const.menuTitle.length; i++) {
            JMenuItem[] menuItems = getMenuItems(i);
            for (JMenuItem menuItem : menuItems) {
                menuItem.addActionListener(e -> handleMenuItemClick(menuItem.getText()));
            }
        }
    }

    private void createTopPanel() {
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(getWidth(), 40));
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.add(new JMenuBar());
        add(top, BorderLayout.NORTH);
    }

    private void createRightPanel() {
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension((int) (getWidth() * 0.2), 600));
        add(right, BorderLayout.EAST);
        JTextField[] jTextFields = {new JTextField(), new JTextField(), new JTextField()};
        JButton[] jButtons = {new JButton("图书查询"), new JButton("借阅查询"), new JButton("读者查询")};
        for (int j = 0; j < jTextFields.length; j++) {
            right.add(jTextFields[j]).setBounds((int) (getWidth() * 0.2 - 110) / 2, 30 + j * 120, 110, 30);
            right.add(jButtons[j]).setBounds((int) (getWidth() * 0.2 - 100) / 2, 70 + j * 120, 100, 30);
        }
        right.setLayout(null);
        jButtons[0].addActionListener(e -> searchBooks(jTextFields[0].getText().trim()));
        jButtons[1].addActionListener(e -> searchBorrow(jTextFields[1].getText().trim()));
        jButtons[2].addActionListener(e -> searchReaders(jTextFields[2].getText().trim()));
    }

    private void searchBooks(String para) {
        String sql = "select * from tb_bookinfo where bookname like ? or author like ? or isbn like ?";
        String[] tableTitle = Const.tableBookTitle;
        performSearch(sql, tableTitle, para);
    }

    private void searchBorrow(String para) {
        String sql = "select id, readername, bookname, borrowTime, backTime from tb_borrowview " +
                "where id like ? or readername like ? or bookname like ?";
        String[] tableTitle = Const.tableBorrowTitle;
        performSearch(sql, tableTitle, para);
    }

    private void searchReaders(String para) {
        String sql = "select id, name, sex, papertype, paperno, tel, email from tb_readerview " +
                "where name like ? or paperNO like ? or email like ?";
        String[] tableTitle = Const.tableReaderTitle;
        performSearch(sql, tableTitle, para);
    }

    private void performSearch(String sql, String[] tableTitle, String para) {
        DBUtil dbUtil = new DBUtil();
        dbUtil.getconn();

        try {
            // Use a prepared statement for the count query
            String countQuery = "SELECT COUNT(*) AS count FROM (" + sql + ") AS result";
            dbUtil.exequeryppst(countQuery, "%" + para + "%", "%" + para + "%", "%" + para + "%");

            int rscount = 0;

            // Get the count of records
            if (dbUtil.rs.next()) {
                rscount = dbUtil.rs.getInt("count");
            }

            String[][] tableData = new String[rscount][tableTitle.length];

            // Reset the result set and execute the main query
            dbUtil.rs = null;
            dbUtil.exequeryppst(sql, "%" + para + "%", "%" + para + "%", "%" + para + "%");

            int i = 0;
            while (dbUtil.rs.next()) {
                for (int j = 0; j < tableTitle.length; j++) {
                    tableData[i][j] = dbUtil.rs.getString(tableTitle[j]);
                }
                i++;
            }

            DefaultTableModel defaultTableModel = new DefaultTableModel(tableData, tableTitle);
            jTable.setModel(defaultTableModel);
            jTable.repaint();

        } catch (SQLException e) {
            System.out.println("查询数据出错");
            e.printStackTrace();
        } finally {
            dbUtil.close();
        }
    }



    private void createBottomPanel() {
        JPanel bottom = new JPanel();
        JLabel bottomText = new JLabel("LibraryManagementSystem version 1.0  CopyRight:23983873@qq.com");
        bottom.add(bottomText);
        add(bottom, BorderLayout.SOUTH);
    }

    private void createTable() {
        defaultTableModel = new DefaultTableModel(new String[0][0], Const.tableBookTitle);
        jTable = new JTable(defaultTableModel);
        jTable.setRowHeight(35);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        add(jScrollPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void loadBookInfoData() {
        loadTableData("tb_bookinfo", Const.tableBookTitle);
    }

    private void loadTableData(String tableName, String[] tableTitle) {
        DBUtil dbUtil = new DBUtil();
        dbUtil.getconn();

        String countQuery = "select count(*) as count from " + tableName;

        dbUtil.exequerystmt(countQuery);
        int rscount = 0;

        try {
            if (dbUtil.rs.next()) {
                rscount = dbUtil.rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.out.println("查询统计记录数出错");
            ex.printStackTrace();
        }

        String[][] tableData = new String[rscount][tableTitle.length];

        String selectQuery = "select * from " + tableName;

        try {
            int i = 0;
            dbUtil.exequerystmt(selectQuery);
            while (dbUtil.rs.next()) {
                for (int j = 0; j < tableTitle.length; j++) {
                    tableData[i][j] = dbUtil.rs.getString(tableTitle[j]);
                }
                i++;
            }
        } catch (SQLException e) {
            System.out.println("查询数据出错");
            e.printStackTrace();
        }

        dbUtil.close();
        defaultTableModel.setDataVector(tableData, tableTitle);
    }

    private JMenuItem[] getMenuItems(int index) {
        switch (index) {
            case 0: // 系统信息
                return createMenuItems(Const.menuItemsSystem);
            case 1: // 读者管理
                return createMenuItems(Const.menuItemsReader);
            case 2: // 图书管理
                return createMenuItems(Const.menuItemsBook);
            case 3: // 借阅管理
                return createMenuItems(Const.menuItemsBorrow);
            case 4: // 用户管理
                return createMenuItems(Const.menuItemsUser);
            default:
                return new JMenuItem[0];
        }
    }

    private JMenuItem[] createMenuItems(String[] itemNames) {
        JMenuItem[] menuItems = new JMenuItem[itemNames.length];
        for (int i = 0; i < itemNames.length; i++) {
            menuItems[i] = new JMenuItem(itemNames[i]);
            menuItems[i].setFont(Const.font);
        }
        return menuItems;
    }

    private void handleMenuItemClick(String menuItemText) {
        switch (menuItemText) {
            case "系统信息":
                showTable("tb_library");
                break;
            case "读者管理":
                showTable("tb_readerview");
                break;
            case "图书管理":
                showTable("tb_bookinfo");
                break;
            case "借阅管理":
                showTable("tb_borrowview");
                break;
            case "用户管理":
                showTable("tb_manager");
                break;
            // Add more cases for other menu items if needed
        }
    }


    private void showTable(String tableName) {
        String[] tableTitle = getTableTitle(tableName);
        loadTableData(tableName, tableTitle);
    }

    private String[] getTableTitle(String tableName) {
        // You may need to implement logic to get the table title based on the table name.
        // For now, assuming you have a constant in Const class.
        switch (tableName) {
            case "tb_library":
                return Const.menuTitle;
            case "tb_readerview":
                return Const.tableReaderTitle;
            case "tb_bookinfo":
                return Const.tableBookTitle;
            case "tb_borrowview":
                return Const.tableBorrowTitle;
            case "tb_manager":
                return Const.tablemanagerTitle;
            default:
                return new String[0];
        }
    }


    public static void main(String[] args) {
        new MainWindow("图书管理系统",800,600);
    }

}