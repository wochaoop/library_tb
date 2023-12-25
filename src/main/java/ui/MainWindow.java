package ui;

import service.DataOperation;
import util.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
public class MainWindow extends UniversalWindow {
    MainWindow(String title,int w,int h){
        super(title,w,h);
        setTitle(title);
        JMenuBar  menuBar=new JMenuBar ();
        JMenu[] menu =new JMenu[Const.menuTitle.length];
        for (int i=0;i<menu.length;i++) {
            menu[i] = new JMenu(Const.menuTitle[i]);
            menu[i].setFont(Const.font);
            menuBar.add(menu[i]);
        }
        setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(getWidth(), 40));
        FlowLayout flowLayout=new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        top.setLayout(flowLayout);
        top.add(menuBar);
        add(top, BorderLayout.NORTH);
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension((int) (getWidth() * 0.2), 600));
        add(right, BorderLayout.EAST);

        JPanel bottom = new JPanel();
        JLabel bottomText=new JLabel("LibrayManagementSystem version 1.0  CopyRight:23983873@qq.com");
        bottom.add(bottomText);
        add(bottom,BorderLayout.SOUTH);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        DBUtil dbUtil = new DBUtil();
        dbUtil.getconn();
        dbUtil.exequerystmt("select count(*) as count from tb_bookinfo");
        int rscount = 0;
        try {
            if (dbUtil.rs.next())
                rscount = dbUtil.rs.getInt("count");
        } catch (SQLException ex) {
            System.out.println("查询统计记录数出错");
            ex.printStackTrace();
        }
        String[][] tableData = new String[rscount][Const.tableBookTitle.length];
        try {
            int i=0;
            dbUtil.exequerystmt("select * from tb_bookinfo");
            while (dbUtil.rs.next()) {
                tableData[i][0]=dbUtil.rs.getString("id");
                tableData[i][1]=dbUtil.rs.getString("bookname");
                tableData[i][2]=dbUtil.rs.getString("author");
                tableData[i][3]=dbUtil.rs.getString("isbn");
                tableData[i][4]=dbUtil.rs.getString("price");
                tableData[i][5]=dbUtil.rs.getString("storage");
                i++;
            }
        } catch (SQLException e) {
            System.out.println("查询数据出错");
            e.printStackTrace();
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(tableData, Const.tableBookTitle);
        JTable jTable = new JTable(defaultTableModel);
        jTable.setRowHeight(35);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        add(jScrollPane, BorderLayout.CENTER);
        dbUtil.close();
        JTextField[] jTextFields = {new JTextField(),new JTextField(),new JTextField()};
        JButton[] jButtons={new JButton("图书查询"),new JButton("借阅查询"),new JButton("读者查询")};
        for (int j=0;j<jTextFields.length;j++){
            right.add(jTextFields[j]).setBounds((int)(getWidth()*0.2-110)/2,30+j*120,110,30);
            right.add(jButtons[j]).setBounds((int)(getWidth()*0.2-100)/2,70+j*120,100,30);
        }
        right.setLayout(null);

        jButtons[0].addActionListener(e -> {
            String para = "%" + jTextFields[0].getText().trim() + "%";
            String sqlbook0 = "select * from tb_bookinfo where bookname like ? or author like ? or isbn like ?";
            while (defaultTableModel.getRowCount() > 0)
                defaultTableModel.removeRow(0);
            DBUtil dbUtil0 = new DBUtil();
            dbUtil0.getconn();
            dbUtil0.exequeryppst(sqlbook0,para,para,para);
            Object[] a = new Object[Const.tableBookTitle.length];
            try {
                while (dbUtil0.rs.next()) {
                    a[0]=dbUtil0.rs.getString("id");
                    a[1]=dbUtil0.rs.getString("bookname");
                    a[2]=dbUtil0.rs.getString("author");
                    a[3]=dbUtil0.rs.getString("isbn");
                    a[4]=dbUtil0.rs.getString("price");
                    a[5]=dbUtil0.rs.getString("storage");
                    defaultTableModel.addRow(a);
                }
                dbUtil0.close();
            }catch (Exception e0){
                System.out.println("查询数据出错");
                e0.printStackTrace();
            }
            defaultTableModel.fireTableDataChanged();
            jTable.repaint();
        });

        jButtons[1].addActionListener(e -> {
            String para = "%" + jTextFields[1].getText().trim() + "%";
            String sqlbook1 = "select id,readername,bookname,borrowTime,backTime from tb_borrowview where id like ? or readername like ? or bookname like ?";


            DataOperation dataOperation=new DataOperation();
            dataOperation.getResultCount("select count(*) as count from tb_borrowview");
            dataOperation.initData(Const.tableBorrowTitle.length);
            Object[][] data= (Object[][]) dataOperation.getData(sqlbook1,para,para,para);
            DefaultTableModel defaultTableModel1=new DefaultTableModel(data,Const.tableBorrowTitle);
            jTable.setModel(defaultTableModel1);
            jTable.repaint();
        });

        jButtons[2].addActionListener(e -> {
            String para = "%"+jTextFields[2].getText().trim()+"%";
            String sqlbook2 = "select id,name,sex,papertype,paperno,tel,email from tb_readerview where name  like ? or paperNO like ? or email like ?";
            DataOperation dataOperation=new DataOperation();
            dataOperation.getResultCount("select count(*) as count from tb_readerview");
            dataOperation.initData(Const.tableReaderTitle.length);
            Object[][] data= (Object[][]) dataOperation.getData(sqlbook2,para,para,para);
            DefaultTableModel defaultTableModel2=new DefaultTableModel(data,Const.tableReaderTitle);
            jTable.setModel(defaultTableModel2);
            jTable.repaint();
            dataOperation.dbclose();
        });
        dbUtil.close();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow("图书管理系统", 800, 600);
    }

}