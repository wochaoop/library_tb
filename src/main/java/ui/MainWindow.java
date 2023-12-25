package ui;

import service.DataOperation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends UniversalWindow {
    public MainWindow(String title, int w, int h){
        super(title,w,h);

        JPanel right=new JPanel();//右侧查询按钮面板
        right.setPreferredSize(new Dimension((int)(getWidth()*0.2),600));//设置右侧的大小
        add(right,BorderLayout.EAST);//放置到窗口的EAST

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        DataOperation dataOperation=new DataOperation();
        dataOperation.getResultCount("select count(*) as count from tb_bookinfo");
        dataOperation.initData(Const.tableBookTitle.length);
        String sqlbook0="select id,bookname,author,isbn,price,storage from tb_bookinfo";
        Object[][] data= (Object[][]) dataOperation.getData(sqlbook0);
        DefaultTableModel defaultTableModel0=new DefaultTableModel(data, Const.tableBookTitle);
        dataOperation.dbclose();
//        util.DBUtil dbUtil=new util.DBUtil();//创建数据库对象
//        dbUtil.getconn();//建立连接
//        dbUtil.exequerystmt("select count(*) as count from tb_bookinfo");//查询统计记录数
//        int rscount=0;//记录数
//        try {
//            if (dbUtil.rs.next())
//                rscount=dbUtil.rs.getInt("count");//把查询统计的结果放到记录数中
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        String[][] tableData =new String[rscount][ui.Const.tableBookTitle.length];//根据行数创建数组
//        try {
//            int i=0;
//            dbUtil.exequerystmt("select * from tb_bookinfo");//重新执行查询
//            while (dbUtil.rs.next()){//把记录集的数据写入到数组中
//                    tableData[i][0]=dbUtil.rs.getString("id");
//                    tableData[i][1]=dbUtil.rs.getString("bookname");
//                    tableData[i][2]=dbUtil.rs.getString("author");
//                    tableData[i][3]=dbUtil.rs.getString("isbn");
//                    tableData[i][4]=dbUtil.rs.getString("price");
//                    tableData[i][5]=dbUtil.rs.getString("storage");
//                    i++;
//                }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        DefaultTableModel defaultTableModel = new DefaultTableModel(tableData,ui.Const.tableBookTitle);//创建表格模型
        JTable jTable = new JTable(defaultTableModel0);//创建表格
        jTable.setRowHeight(35);//设置行高
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//设置列宽
        JScrollPane jScrollPane = new JScrollPane(jTable);//把表格放入到滚动面板中
        add(jScrollPane,BorderLayout.CENTER);//把滚动面板放置到左侧，如果这里先放一个面板JPane，再把表格放置到面板中，无法控制表格的大小。不知道为什么，有待研究
        JTextField[] jTextFields = {new JTextField(),new JTextField(),new JTextField()};
        JButton[] jButtons={new JButton("图书查询"),new JButton("借阅查询"),new JButton("读者查询")};
        for (int j=0;j<jTextFields.length;j++){
            right.add(jTextFields[j]).setBounds((int)(getWidth()*0.2-110)/2,30+j*120,110,30);
            right.add(jButtons[j]).setBounds((int)(getWidth()*0.2-100)/2,70+j*120,100,30);
        }
        right.setLayout(null);
        jButtons[0].addActionListener(new ActionListener() {//图书查询
            @Override
            public void actionPerformed(ActionEvent e) {
                String para = "%"+jTextFields[0].getText().trim()+"%";//作为数据库查询的参数
                String sqlbook1 = "select id,bookname,author,isbn,price,storage from tb_bookinfo where bookname like ? or author like ? or isbn like ?";
                DataOperation dataOperation=new DataOperation();
                dataOperation.getResultCount("select count(*) as count from tb_bookinfo");
                dataOperation.initData(Const.tableBookTitle.length);
                Object[][] data= (Object[][]) dataOperation.getData(sqlbook1,para,para,para);
                DefaultTableModel defaultTableModel1=new DefaultTableModel(data, Const.tableBookTitle);
                jTable.setModel(defaultTableModel1);//重新设置表格模型
//                while (defaultTableModel.getRowCount()>0)//删除原来表格的数据
//                    defaultTableModel.removeRow(0);
//                util.DBUtil dbUtil0=new util.DBUtil();//定义一个数据库对象
//                dbUtil0.getconn();
//                dbUtil0.exequeryppst(sqlbook0,para,para,para);
//                Object a[]=new Object[ui.Const.tableBookTitle.length];//定义一个临时数组来保存查询的数据
//                try {
//                    while (dbUtil0.rs.next()) {//把记录集的数据写入到数组中
//                        a[0]=dbUtil0.rs.getString("id");
//                        a[1]=dbUtil0.rs.getString("bookname");
//                        a[2]=dbUtil0.rs.getString("author");
//                        a[3]=dbUtil0.rs.getString("isbn");
//                        a[4]=dbUtil0.rs.getString("price");
//                        a[5]=dbUtil0.rs.getString("storage");
//                        defaultTableModel.addRow(a);//把数据添加到表格模型中
//                    }
//                    dbUtil0.close();
//                }catch (Exception e0){
//                    e0.printStackTrace();
//                }
//                defaultTableModel.fireTableDataChanged();//通知表格模型数据已经修改,这行代码有没有不影响执行效果
                jTable.repaint();//重新绘制表格
            }
        });
        jButtons[1].addActionListener(new ActionListener() {//借阅查询
            @Override
            public void actionPerformed(ActionEvent e) {
                String para = "%"+jTextFields[1].getText().trim()+"%";//作为数据库查询的参数
                String sqlbook1 = "select id,readername,bookname,borrowTime,backTime from tb_borrowview where id like ? or readername like ? or bookname like ?";
//                String[][] tableData1=new String[0][0];//重新定义保存数据的二维数组
//                util.DBUtil dbUtil1=new util.DBUtil();//定义一个数据库对象
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
//                    tableData1=new String[rscount][ui.Const.tableBorrowTitle.length];//根据内容重新定义二维数组
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
//                DefaultTableModel defaultTableModel1=new DefaultTableModel(tableData1,ui.Const.tableBorrowTitle);
                DataOperation dataOperation=new DataOperation();
                dataOperation.getResultCount("select count(*) as count from tb_borrowview");
                dataOperation.initData(Const.tableBorrowTitle.length);
                Object[][] data= (Object[][]) dataOperation.getData(sqlbook1,para,para,para);
                DefaultTableModel defaultTableModel1=new DefaultTableModel(data, Const.tableBorrowTitle);
                jTable.setModel(defaultTableModel1);//重新设置表格模型
                jTable.repaint();//重新绘制表格
            }
        });
        jButtons[2].addActionListener(new ActionListener() {//读者查询
            @Override
            public void actionPerformed(ActionEvent e) {
                String para = "%"+jTextFields[2].getText().trim()+"%";
                String sqlbook2 = "select id,name,sex,papertype,paperno,tel,email from tb_readerview where name  like ? or paperNO like ? or email like ?";
                DataOperation dataOperation=new DataOperation();
                dataOperation.getResultCount("select count(*) as count from tb_readerview");
                dataOperation.initData(Const.tableReaderTitle.length);
                Object[][] data= (Object[][]) dataOperation.getData(sqlbook2,para,para,para);
                DefaultTableModel defaultTableModel2=new DefaultTableModel(data, Const.tableReaderTitle);
                jTable.setModel(defaultTableModel2);//重新设置表格模型
                jTable.repaint();//重新绘制表格
                dataOperation.dbclose();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow("图书管理系统",800,600);
    }

}
