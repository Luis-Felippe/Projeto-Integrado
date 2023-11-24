package bookify.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabasePostgreSQL {

  DatabasePostgreSQL() {
    super();

    hostName = "localhost";
    userName = "postgres";
    password = "1234";
    jdbcDriver = "org.postgresql.Driver";
    dataBaseName = "postgres";
    dataBasePrefix = "jdbc:postgresql://";
    dabaBasePort = "5432";

    url = dataBasePrefix + hostName + ":" + dabaBasePort + "/" + dataBaseName;
  }
  
  public Connection getConnection() {
    try {
      if (con == null) {
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(url, userName, password);
      } else if (con.isClosed()) {
        con = null;
        return getConnection();
      }
    } catch (ClassNotFoundException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    return con;
  }

  private Connection con = null;

  private String hostName = null;
  private String userName = null;
  private String password = null;
  private String url = null;
  private String jdbcDriver = null;
  private String dataBaseName = null;
  private String dataBasePrefix = null;
  private String dabaBasePort = null;

  public void closeConnection() {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}