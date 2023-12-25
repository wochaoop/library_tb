package service;

import util.DBUtil;
import java.sql.SQLException;
public class DataOperation {
    /*说明：
    数据库数据操作类
    主要用来查询数据库数据，然后把他放到数组当中，方便在界面中使用表格模型生成表格
     */
    private Object[][] data=null;//二维数组来保存查询的数据
    private int rscount=0;//行数，也是记录数
    private int colCount=0;//列数
    private final DBUtil dbUtil;//数据库对象
    public DataOperation(){//构造方法主要用来产生一个数据库对象并建立连接
        dbUtil=new DBUtil();
        dbUtil.getconn();
    }
    public int getColCount() {//返回数组的列数
        return colCount;
    }
    public int getRscount() {//返回数组的行数
        return rscount;
    }
    public void getResultCount(String sql){//输入查询语句获得记录数
        dbUtil.exequerystmt(sql);
        try {
            if (dbUtil.rs.next())
                rscount=dbUtil.rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void initData(int colCount){//初始化二维数组
        this.colCount=colCount;
        data=new Object[rscount][colCount];
        System.out.println(data.length+","+data[0].length);
    }
    public Object getData(String sql,String...para){//把数据存放在二维数组中
        dbUtil.exequeryppst(sql,para);
        int i=0;
        try {
            while (dbUtil.rs.next()) {//把记录集的数据写入到数组中
                for (int j = 1; j <=colCount; j++) {//使用索引获得字段值，索引是从1开始，不是从0开始
                    data[i][j-1] = dbUtil.rs.getString(j);
                }
                i++;
            }
        }catch (Exception e1) {
            e1.printStackTrace();
        }
        return data;
    }
    public void dbclose(){//释放数据库对象
        dbUtil.close();
    }
}
