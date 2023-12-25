package util;

public class DBUtilTest {
    public static void main(String[] args) {
        // Create an instance of DBUtil
        DBUtil dbUtil = new DBUtil();

        try {
            // Establish the database connection
            dbUtil.getconn();
            System.out.println("Connected to the database!");

            // Execute a sample query (replace "your_table_name" with an actual table name)
            String query = "SELECT * FROM tb_bookcase";
            dbUtil.exequerystmt(query);
            System.out.println("Query executed successfully!");

            // Process the query results (replace this part with your specific processing logic)
            if (dbUtil.rs != null) {
                while (dbUtil.rs.next()) {
                    // Print sample data (replace this with your actual data processing logic)
                    System.out.println("Column 1: " + dbUtil.rs.getString(1));
                    System.out.println("Column 2: " + dbUtil.rs.getString(2));
                    // Add more columns as needed
                }
            } else {
                System.out.println("Result set is null. Check your query or database connection.");
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        } finally {
            // Close the resources
            dbUtil.close();
            System.out.println("Connection closed.");
        }
    }
}
