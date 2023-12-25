package service;

import util.DBUtil;

import java.sql.SQLException;
public class DataOperation {
    private final DBUtil dbUtil;
    private Object[][] data = null;
    private int rscount = 0;
    private int colCount = 0;

    public DataOperation() {
        dbUtil=new DBUtil();
        dbUtil.getconn();
    }

    public int getColCount() {
        return colCount;
    }

    public int getRscount() {
        return rscount;
    }

    public void getResultCount(String sql) {
        dbUtil.exequerystmt(sql);
        try {
            if (dbUtil.rs.next())
                rscount=dbUtil.rs.getInt("count");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initData(int colCount) {
        this.colCount=colCount;
        data=new Object[rscount][colCount];
        System.out.println(data.length+","+data[0].length);
    }

    public Object getData(String sql, String... para) {
        dbUtil.exequeryppst(sql,para);
        int i=0;
        try {
            while (dbUtil.rs.next()) {
                for (int j = 1; j <= colCount; j++) {
                    data[i][j-1] = dbUtil.rs.getString(j);
                }
                i++;
            }
        }catch (Exception e1) {
            e1.printStackTrace();
        }
        return data;
    }

    public void dbclose() {
        dbUtil.close();
    }
}
