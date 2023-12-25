package com.library.service;

import com.library.login;
import com.library.util.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Stack;

public class MainWindow extends UniversalWindow {
    Stack<UniversalWindow> stack = new Stack<>();

    public MainWindow(String title, int w, int h) {
        super(title, w, h);
        setTitle(title);
        JMenuBar menuBar = new JMenuBar();
        JMenu[] menu = new JMenu[Const.menuTitle.length];
        for (int i = 0; i < menu.length; i++) {
            menu[i] = new JMenu(Const.menuTitle[i]);
            menu[i].setFont(Const.font);
            menuBar.add(menu[i]);
        }
        JMenuItem[] jMenuItems1 = new JMenuItem[Const.menuItemsTitle.length];

        //系统设置
        for (int i = 0; i < 3; i++) {
            jMenuItems1[i] = new JMenuItem(Const.menuItemsTitle[i]);
            jMenuItems1[i].setFont(Const.font);
            menu[0].add(jMenuItems1[i]);
        }
        //系统设置_刷新
        jMenuItems1[0].addActionListener(e -> {
            MainWindow mainWindow = new MainWindow("图书管理系统", 800, 600);
            if (!winStack.isEmpty()) {
                winStack.peek().setVisible(false);
            }
            dispose();
            mainWindow.setWinStack(mainWindow, winStack);
            mainWindow.addWindowListener(new WindowOp());
        });
        //系统设置_重启
        jMenuItems1[1].addActionListener(e -> {
            login login = new login("登录界面",400,240);
            dispose();
            login.setWinStack(login, winStack);
            login.addWindowListener(new WindowOp());
        });
        //系统设置_查看版本
        jMenuItems1[2].addActionListener(e -> {
                // 创建一个用于显示版本信息的面板
                JPanel versionPanel = new JPanel();
                versionPanel.setLayout(new FlowLayout());

                // 创建版本信息标签
                JLabel versionLabel = new JLabel("当前版本：1.0");
                versionLabel.setFont(Const.font);
                versionPanel.add(versionLabel);

                // 创建对话框，并将版本信息面板添加到对话框中
                JDialog versionDialog = new JDialog();
                versionDialog.setTitle("版本信息");
                versionDialog.setSize(300, 100);
                versionDialog.setLocationRelativeTo(null); // 居中显示
                versionDialog.setContentPane(versionPanel);
                versionDialog.setVisible(true);
            });
        //读者管理
        for (int i = 3; i < 6; i++) {
            jMenuItems1[i] = new JMenuItem(Const.menuItemsTitle[i]);
            jMenuItems1[i].setFont(Const.font);
            menu[1].add(jMenuItems1[i]);
        }
        //图书管理
        for (int i = 6; i < 9; i++) {
            jMenuItems1[i] = new JMenuItem(Const.menuItemsTitle[i]);
            jMenuItems1[i].setFont(Const.font);
            menu[2].add(jMenuItems1[i]);
        }
        //图书管理_添加图书
        jMenuItems1[6].addActionListener(e -> {
            BookAdd bookAdd = new BookAdd("添加图书",600,400);
            winStack.peek().setVisible(false);
            bookAdd.setWinStack(bookAdd,winStack);
            bookAdd.addWindowListener(new WindowOp());
        });
        jMenuItems1[7].addActionListener(e -> {
            BookModify bookModify = new BookModify("修改图书",600,400);
            winStack.peek().setVisible(false);
            bookModify.setWinStack(bookModify,winStack);
            bookModify.addWindowListener(new WindowOp());
        });

        //图书管理_删除图书
        jMenuItems1[8].addActionListener(e -> {
            BookDelete bookDelete = new BookDelete("删除图书",400,300);
            winStack.peek().setVisible(false);
            bookDelete.setWinStack(bookDelete, winStack);
            bookDelete.addWindowListener(new WindowOp());
        });
        //借阅管理
        for (int i = 9; i < 12; i++) {
            jMenuItems1[i] = new JMenuItem(Const.menuItemsTitle[i]);
            jMenuItems1[i].setFont(Const.font);
            menu[3].add(jMenuItems1[i]);
        }
        //用户管理
        for (int i = 12; i < 15; i++) {
            jMenuItems1[i] = new JMenuItem(Const.menuItemsTitle[i]);
            jMenuItems1[i].setFont(Const.font);
            menu[4].add(jMenuItems1[i]);
        }

        //用户管理_用户注册
        jMenuItems1[12].addActionListener(e -> {
            UserRegister userRegister = new UserRegister("用户注册界面", 400, 440);
            winStack.peek().setVisible(false);
            userRegister.setWinStack(userRegister, winStack);
            userRegister.addWindowListener(new WindowOp());
        });

        //用户管理_用户删除
        jMenuItems1[13].addActionListener(e -> {
            UserDelete userDelete = new UserDelete("删除用户",400,300);
            dispose();
            userDelete.setWinStack(userDelete,winStack);
            userDelete.addWindowListener(new WindowOp());
        });

        //用户管理_用户登出
        jMenuItems1[14].addActionListener(e -> {
            login login = new login("登录界面",400,240);
            dispose();
            login.setWinStack(login, winStack);
            login.addWindowListener(new WindowOp());
        });
        setLayout(new BorderLayout());
        JPanel top = new JPanel();//顶部菜单栏
        top.setPreferredSize(new Dimension(getWidth(), 40));//设置宽和高
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);//设置为左对齐
        top.setLayout(flowLayout);//设置布局
        top.add(menuBar);//添加菜单栏
        add(top, BorderLayout.NORTH);//放置到窗口的NORTH
        JPanel right = new JPanel();//右侧查询按钮面板
        right.setPreferredSize(new Dimension((int) (getWidth() * 0.2), 600));//设置右侧的大小
        add(right, BorderLayout.EAST);//放置到窗口的EAST

        JPanel bottom = new JPanel();//底部版权信息
        JLabel bottomText = new JLabel("LibrayManagementSystem version 1.0");
        bottom.add(bottomText);
        add(bottom, BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        DBUtil dbUtil = new DBUtil();//创建数据库对象
        dbUtil.getconn();//建立连接

        dbUtil.exequerystmt("select count(*) as count from tb_bookinfo");//查询统计记录数
        int rscount = 0;//记录数
        try {
            if (dbUtil.rs.next())
                rscount = dbUtil.rs.getInt("count");//把查询统计的结果放到记录数中
        } catch (SQLException ex) {
            System.out.println("查询统计记录数失败");
            ex.printStackTrace();
        }
        String[][] tableData = new String[rscount][Const.tableBookTitle.length];//根据行数创建数组
        try {
            int i = 0;
            dbUtil.exequerystmt("select * from tb_bookinfo");//重新执行查询
            while (dbUtil.rs.next()) {//把记录集的数据写入到数组中
                tableData[i][0] = dbUtil.rs.getString("id");
                tableData[i][1] = dbUtil.rs.getString("bookname");
                tableData[i][2] = dbUtil.rs.getString("author");
                tableData[i][3] = dbUtil.rs.getString("isbn");
                tableData[i][4] = dbUtil.rs.getString("price");
                tableData[i][5] = dbUtil.rs.getString("storage");
                i++;
            }
        } catch (SQLException e) {
            System.out.println("查询图书信息失败");
            e.printStackTrace();
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(tableData, Const.tableBookTitle);//创建表格模型
        JTable jTable = new JTable(defaultTableModel);//创建表格
        jTable.setRowHeight(35);//设置行高
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//设置列宽
        JScrollPane jScrollPane = new JScrollPane(jTable);//把表格放入到滚动面板中
        add(jScrollPane, BorderLayout.CENTER);//把滚动面板放置到左侧，如果这里先放一个面板JPane，再把表格放置到面板中，无法控制表格的大小。不知道为什么，有待研究
        dbUtil.close();
        JTextField[] jTextFields = {new JTextField(), new JTextField(), new JTextField()};
        JButton[] jButtons = {new JButton("图书查询"), new JButton("借阅查询"), new JButton("读者查询")};
        for (int j = 0; j < jTextFields.length; j++) {
            right.add(jTextFields[j]).setBounds((int) (getWidth() * 0.2 - 110) / 2, 30 + j * 120, 110, 30);
            right.add(jButtons[j]).setBounds((int) (getWidth() * 0.2 - 100) / 2, 70 + j * 120, 100, 30);
        }
        right.setLayout(null);
        jButtons[0].addActionListener(new ActionListener() {//图书查询
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel defaultTableModel0 = getDefaultTableModel();
                jTable.setModel(defaultTableModel0);//重新设置表格模型
                jTable.repaint();//重新绘制表格
//                //重新绘制表格
//                while (defaultTableModel.getRowCount() > 0)//删除原来表格的数据
//                    defaultTableModel.removeRow(0);
//                DBUtil dbUtil0 = new DBUtil();//定义一个数据库对象
//                dbUtil0.getconn();
//                dbUtil0.exequeryppst(sqlbook0, para, para, para);
//                Object a[] = new Object[Const.tableBookTitle.length];//定义一个临时数组来保存查询的数据
//                try {
//                    while (dbUtil0.rs.next()) {//把记录集的数据写入到数组中
//                        a[0] = dbUtil0.rs.getString("id");
//                        a[1] = dbUtil0.rs.getString("bookname");
//                        a[2] = dbUtil0.rs.getString("author");
//                        a[3] = dbUtil0.rs.getString("isbn");
//                        a[4] = dbUtil0.rs.getString("price");
//                        a[5] = dbUtil0.rs.getString("storage");
//                        defaultTableModel.addRow(a);//把数据添加到表格模型中
//                    }
//                    dbUtil0.close();
//                } catch (Exception e0) {
//                    e0.printStackTrace();
//                }
//                defaultTableModel.fireTableDataChanged();//通知表格模型数据已经修改,这行代码有没有不影响执行效果
//                jTable.repaint();//重新绘制表格
            }

            private DefaultTableModel getDefaultTableModel() {
                String para = "%" + jTextFields[0].getText().trim() + "%";//作为数据库查询的参数
                String sqlbook0 = "select id, bookname, author, ISBN, price,storage from tb_bookinfo where bookname like ? or author like ? or isbn like ?";
                DataOperation dataOperation = new DataOperation();
                dataOperation.getResultCount("select count(*) as count from tb_bookinfo");
                dataOperation.initData(Const.tableBookTitle.length);
                Object[][] data = (Object[][]) dataOperation.getData(sqlbook0, para, para, para);
                return new DefaultTableModel(data, Const.tableBookTitle);
            }
        });
        //借阅查询
        jButtons[1].addActionListener(e -> {
            String para = "%" + jTextFields[1].getText().trim() + "%";//作为数据库查询的参数
            String sqlbook1 = "select id,readername,bookname,borrowTime,backTime from tb_borrowview where id like ? or readername like ? or bookname like ?";
//                String[][] tableData1=new String[0][0];//重新定义保存数据的二维数组
//                DBUtil dbUtil1=new DBUtil();//定义一个数据库对象
//                dbUtil1.getconn();
//                String sqltemp="select count(*) as count from tb_borrowview ";//查询统计记录
//                dbUtil1.exequeryppst(sqltemp);
//                int rscount=0;//记录数
//                try {
//                    if (dbUtil1.rs.next())
//                        rscount=dbUtil1.rs.getInt("count");//把查询统计的结果放到记录数中
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//                dbUtil1.exequeryppst(sqlbook1,para,para,para);
//                try {
//                    tableData1=new String[rscount][Const.tableBorrowTitle.length];//根据内容重新定义二维数组
//                    int i=0;
//                    while (dbUtil1.rs.next()) {//把记录集的数据写入到数组中
//                        tableData1[i][0] = dbUtil1.rs.getString("id");
//                        tableData1[i][1] = dbUtil1.rs.getString("readername");
//                        tableData1[i][2] = dbUtil1.rs.getString("bookname");
//                        tableData1[i][3] = dbUtil1.rs.getString("borrowTime");
//                        tableData1[i][4] = dbUtil1.rs.getString("backTime");
//                        i++;
//                    }
//                    dbUtil1.close();
//                }catch (Exception e1){
//                    e1.printStackTrace();
//                }
//                DefaultTableModel defaultTableModel1=new DefaultTableModel(tableData1,Const.tableBorrowTitle);
            DataOperation dataOperation = new DataOperation();
            dataOperation.getResultCount("select count(*) as count from tb_borrowview");
            dataOperation.initData(Const.tableBorrowTitle.length);
            Object[][] data = (Object[][]) dataOperation.getData(sqlbook1, para, para, para);
            DefaultTableModel defaultTableModel1 = new DefaultTableModel(data, Const.tableBorrowTitle);
            jTable.setModel(defaultTableModel1);//重新设置表格模型
            jTable.repaint();//重新绘制表格
        });
        //读者查询
        jButtons[2].addActionListener(e -> {
            String para = "%" + jTextFields[2].getText().trim() + "%";
            String sqlbook2 = "select id,name,sex,papertype,paperno,tel,email from tb_readerview where name  like ? or paperNO like ? or email like ?";
            DataOperation dataOperation = new DataOperation();
            dataOperation.getResultCount("select count(*) as count from tb_readerview");
            dataOperation.initData(Const.tableReaderTitle.length);
            Object[][] data = (Object[][]) dataOperation.getData(sqlbook2, para, para, para);
            DefaultTableModel defaultTableModel2 = new DefaultTableModel(data, Const.tableReaderTitle);
            jTable.setModel(defaultTableModel2);//重新设置表格模型
            jTable.repaint();//重新绘制表格
            dataOperation.dbclose();
        });
        dbUtil.close();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow("图书管理系统",800,600);
    }

}