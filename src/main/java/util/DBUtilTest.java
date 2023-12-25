package util;

public class DBUtilTest {
    public static void main(String[] args) {

        DBUtil dbUtil = new DBUtil();

        try {

            dbUtil.getconn();
            System.out.println("Connected to the database!");


            String query = "SELECT * FROM tb_bookcase";
            dbUtil.exequerystmt(query);
            System.out.println("Query executed successfully!");


            if (dbUtil.rs != null) {
                while (dbUtil.rs.next()) {

                    System.out.println("Column 1: " + dbUtil.rs.getString(1));
                    System.out.println("Column 2: " + dbUtil.rs.getString(2));

                }
            } else {
                System.out.println("Result set is null. Check your query or database connection.");
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            dbUtil.close();
            System.out.println("Connection closed.");
        }
    }
}
