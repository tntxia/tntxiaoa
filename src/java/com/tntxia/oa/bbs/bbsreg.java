package com.tntxia.oa.bbs;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bbsreg
{
  String sDBDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
  String sConnStr = "jdbc:odbc:bbs";
  Connection conn = null;
  ResultSet rs = null;

  public bbsreg() {
    try {
      Class.forName(this.sDBDriver);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
      System.err.println("bbsreg(): " + localClassNotFoundException.getMessage());
    }
  }

  public ResultSet executeQuery(String paramString) {
    this.rs = null;
    try {
      this.conn = DriverManager.getConnection(this.sConnStr);
      Statement localStatement = this.conn.createStatement();
      this.rs = localStatement.executeQuery(paramString);
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("aq.executeQuerystrSQL: " + paramString);
    }
    return this.rs;
  }

  public void executeUpdate(String paramString)
  {
    try {
      this.conn = DriverManager.getConnection(this.sConnStr);
      Statement localStatement = this.conn.createStatement();
      localStatement.executeUpdate(paramString);
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeUpdate: " + localSQLException.getMessage());
      System.err.println("aq.executeUpadatestrSQL: " + paramString);
    }
  }
}