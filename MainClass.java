import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainClass {

  private static final String EMPLOYEE_TABLE = "create table MyEmployees3 ( "
      + "   id INT PRIMARY KEY, firstName VARCHAR(20), lastName VARCHAR(20), "
      + "   title VARCHAR(20), salary INT " + ")";

  public static Connection getConnection() throws ClassNotFoundException, SQLException {
    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:database";
    String username = "tanujjoshi";
    String password = "pass12345";
    Class.forName(driver);
    Connection conn = DriverManager.getConnection(url, username, password);
    return conn;
  }

  public static void main(String args[]) {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(EMPLOYEE_TABLE);
      stmt.executeUpdate("insert into MyEmployees3(id, firstName) values(100, 'A')");
      stmt.executeUpdate("insert into MyEmployees3(id, firstName) values(200, 'B')");
      System.out.println("CreateEmployeeTableOracle: main(): table created.");
    } catch (ClassNotFoundException e) {
      System.out.println("error: failed to load Oracle driver.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("error: failed to create a connection object.");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("other error:");
      e.printStackTrace();
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (Exception e) {
      }
    }
  }
}
           