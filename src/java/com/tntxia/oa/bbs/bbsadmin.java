package com.tntxia.oa.bbs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bbsadmin
{
	infocrmdb.infocrmdb dbconn = new infocrmdb.infocrmdb();
  ResultSet rs = null;
  String strSQL;
  boolean yesorno;
  int id = 0;
  String adminname = "";
  String adminpwd = "";

  public boolean checkAdminPassword(String paramString1, String paramString2)
  {
    this.rs = null;
    this.strSQL = ("select * from admin where adminname='" + paramString1 + "' and password='" + paramString2 + "'");
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) {
        this.yesorno = true;
        this.adminname = this.rs.getString("adminname");
        this.adminpwd = this.rs.getString("password");
      } else {
        this.yesorno = false;
      }
      this.rs.close();
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("checkuserpassword.STRSQL:: " + this.strSQL);
    }
    return this.yesorno;
  }

  public String getAdminName()
  {
    return this.adminname;
  }
  public String getAdminPassword() {
    return this.adminpwd;
  }
}