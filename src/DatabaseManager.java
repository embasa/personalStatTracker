import java.sql.*;

/**
 * Created by bruno on 10/19/15.
 */
public class DatabaseManager
{
  public static void main(String[] args)
  {
    Connection conn = null;
    Statement stmt = null;
    try
    {
      //STEP 2: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      //sql = "SELECT id, first, last, age FROM Employees";
      //sql = "SELECT DATABASE()";
      //sql = "CREATE DATABASE bruno";
      //stmt.executeUpdate(sql);

      sql = "CREATE TABLE IF NOT EXISTS friends(" +
          "PersonID int," +
          "LastName VARCHAR(255)," +
          "FirstName VARCHAR(255)," +
          "Adress VARCHAR(255)," +
          "City VARCHAR(255)" +
          ")";
      stmt.executeUpdate(sql);
      sql = "SELECT database()";
      ResultSet rs = stmt.executeQuery(sql);
      //sql = "CREATE DATABASE bruno";

      //rs = stmt.executeQuery(sql);
      //STEP 5: Extract data from result set
      while (rs.next())
      {
        //Retrieve by column name
        System.out.println(rs.getString(1));
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException se)
    {
      //Handle errors for JDBC
      se.printStackTrace();
    } catch (Exception e)
    {
      //Handle errors for Class.forName
      e.printStackTrace();
    } finally
    {
      try
      {
        if (stmt != null)
          stmt.close();
      } catch (SQLException se2)
      {
      }// nothing we can do
      try
      {
        if (conn != null)
          conn.close();
      } catch (SQLException se)
      {
        se.printStackTrace();
      }//end finally try
    }//end try
    System.out.println("Goodbye!");
  }

  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  static final String DB_URL = "jdbc:mysql:/52.91.228.44:3306/";

  //  Database credentials
  static final String USER = "root";
  static final String PASS = "pass";
}
