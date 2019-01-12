package com.tntxia.oa.bbs;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bbs
{
	infocrmdb.infocrmdb dbconn = new infocrmdb.infocrmdb();
  bbsuser bbsuser = new bbsuser();
  ResultSet rs = null;
  String strSQL;
  boolean yesorno;
  int i;
  int bbsid = 0;
  int parentid = 0;
  int boardid = 0;
  int child = 0;

  int bbshits = 0;
  int bbslength = 0;

  String username = "";
  String useremail = "";
  String userip = "";
  String expression = "";
  String usersign = "";
  String bbstopic = "";
  String bbscontent = "";

  String bbshot = "";

  public void addNewBbs()
  {
    this.strSQL = 
      ("INSERT INTO bbs(parentid,boardid,child,username,useremail,userip,expression,usersign,bbstopic,bbscontent,bbshits,length,dateandtime) VALUES(" + 
      this.parentid + "," + this.boardid + "," + this.child + ",'" + 
      this.username + "','" + this.useremail + "','" + this.userip + "','" + 
      this.expression + "','" + this.usersign + "','" + this.bbstopic + "','" + 
      this.bbscontent + "'," + this.bbshits + "," + this.bbslength + ",getdate())");
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void changeBbsChild(int paramInt1, int paramInt2)
  {
    this.strSQL = ("update bbs set child=child+" + paramInt2 + " where bbsid=" + paramInt1);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void changeBbsHits(int paramInt1, int paramInt2)
  {
    this.strSQL = ("update bbs set bbshits=bbshits+" + paramInt2 + " where bbsid=" + paramInt1);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void delBbs(int paramInt)
  {
    this.rs = null;
    if (getBbsInfo(paramInt))
    {
      if (this.parentid == 0) {
        this.strSQL = ("delete from bbs where parentid=" + this.bbsid);
        this.dbconn.executeUpdate(this.strSQL);
        this.strSQL = ("update board set boardtopics=boardtopics-1 where boardid=" + this.boardid);
        this.dbconn.executeUpdate(this.strSQL);
      }
      else {
        this.strSQL = ("update bbs set child=child-1 where bbsid=" + this.parentid);
        this.dbconn.executeUpdate(this.strSQL);
      }

      this.bbsuser.changeUserClass(this.username, -3);

      this.strSQL = ("delete from bbs where bbsid=" + this.bbsid);
      this.dbconn.executeUpdate(this.strSQL);
    }
  }

  public int getBbsBoardid()
  {
    return this.boardid;
  }

  public int getBbsChild() {
    return this.child;
  }

  public String getBbsContent()
  {
    return this.bbscontent;
  }

  public String getBbsExpression()
  {
    return this.expression;
  }

  public int getBbsHits()
  {
    return this.bbshits;
  }

  public String getBbsHot()
  {
    return this.bbshot;
  }

  public boolean getBbsInfo(int paramInt)
  {
    this.rs = null;
    this.strSQL = ("select * from bbs where bbsid=" + paramInt);
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) {
        this.yesorno = true;
        this.bbsid = this.rs.getInt("bbsid");
        this.parentid = this.rs.getInt("parentid");
        this.boardid = this.rs.getInt("boardid");
        this.child = this.rs.getInt("child");
        this.username = this.rs.getString("username");
        this.useremail = this.rs.getString("useremail");
        this.userip = this.rs.getString("userip");
        this.expression = this.rs.getString("expression");
        this.usersign = this.rs.getString("usersign");
        this.bbstopic = this.rs.getString("bbstopic");
        this.bbscontent = this.rs.getString("bbscontent");

        this.bbshits = this.rs.getInt("bbshits");
        this.bbslength = this.rs.getInt("length");
        this.bbshot = this.rs.getString("bbshot");
      } else {
        this.yesorno = false;
      }
      this.rs.close();
    }
    catch (SQLException localSQLException)
    {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("aq.STRSQL:: " + this.strSQL);
    }
    return this.yesorno;
  }

  public int getBbsLength()
  {
    return this.bbslength;
  }

  public int getBbsParentid()
  {
    return this.parentid;
  }

  public String getBbsTopic()
  {
    return this.bbstopic;
  }

  public String getBbsUseremail()
  {
    return this.useremail;
  }

  public String getBbsUserip() {
    return this.userip;
  }

  public String getBbsUsername()
  {
    return this.username;
  }

  public String getBbsUsersign()
  {
    return this.usersign;
  }

  public int getRecordsCount(int paramInt1, int paramInt2, String paramString)
  {
    this.rs = null;
    this.i = 0;
    switch (paramInt1) { case 0:
      this.strSQL = ("SELECT COUNT(*) AS aa FROM bbs WHERE parentid=0 and boardid=" + paramInt2);
      break;
    case 8:
      this.strSQL = ("SELECT COUNT(*) AS aa FROM bbs WHERE parentid=0 and boardid=" + paramInt2);
      break;
    case 1:
      this.strSQL = 
        ("SELECT COUNT(*) AS aa FROM bbs where parentid=0 and boardid=" + paramInt2 + 
        " and (bbstopic like '%" + paramString + "%')");
      break;
    case 2:
      this.strSQL = 
        ("SELECT COUNT(*) AS aa FROM bbs where boardid=" + paramInt2 + 
        " and (username like '%" + paramString + "%')");
      break;
    case 3:
      this.strSQL = 
        ("SELECT COUNT(*) AS aa FROM bbs where boardid=" + paramInt2 + 
        " and (dateandtime >=#" + paramString + "#)");
      break;
    case 4:
      this.strSQL = ("SELECT COUNT(*) AS aa FROM bbs where bbshot='ok' and boardid=" + paramInt2);
      break;
    case 5:
    case 6:
    case 7:
    default:
      this.strSQL = ("SELECT COUNT(*) AS aa FROM bbs WHERE parentid=0 and boardid=" + paramInt2);
    }
    try
    {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) this.i = this.rs.getInt("aa"); 
    }
    catch (SQLException localSQLException)
    {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("aq.STRSQL:!#: " + this.strSQL);
    }

    return this.i;
  }

  public int getRecordsStart(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString)
  {
    this.rs = null;
    this.i = 0;
    int j = 0;
    if (paramInt3 == 0) j = 0; else {
      j = (paramInt3 - 1) * paramInt2 + 1;
    }
    switch (paramInt1) { case 8:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " ORDER BY dateandtime DESC");
      break;
    case 0:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " ORDER BY dateandtime DESC");
      break;
    case 1:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " and (bbstopic like '%" + paramString + "%') ORDER BY dateandtime DESC");
      break;
    case 2:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs where boardid=" + paramInt4 + 
        " and (username like '%" + paramString + "%') order by dateandtime desc");
      break;
    case 3:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs where boardid=" + paramInt4 + 
        " and (dateandtime >=#" + paramString + "#) order by dateandtime desc");
      break;
    case 4:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs where bbshot='ok' and boardid=" + 
        paramInt4 + " order by dateandtime desc");
      break;
    case 5:
    case 6:
    case 7:
    default:
      this.strSQL = 
        ("SELECT TOP " + j + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " ORDER BY dateandtime DESC");
    }
    try
    {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      while (this.rs.next()) {
        this.i = this.rs.getInt("bbsid");
      }
      this.rs.close();
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("aq.STRSQL:!#$$: " + this.strSQL);
    }
    return this.i;
  }

  public ResultSet listBbs(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {
    this.rs = null;
    this.i = getRecordsStart(paramInt1, paramInt2, paramInt3, paramInt4, paramString);
    switch (paramInt1) { case 0:
      this.strSQL = 
        ("SELECT TOP " + paramInt2 + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " and bbsid<=" + this.i + " ORDER BY dateandtime DESC");
      break;
    case 1:
      this.strSQL = 
        ("SELECT TOP " + paramInt2 + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " and (bbstopic like '%" + paramString + "%') and bbsid<=" + 
        this.i + " ORDER BY dateandtime DESC");
      break;
    case 8:
      this.strSQL = 
        ("SELECT TOP " + paramInt2 + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " and bbsid<=" + this.i + " ORDER BY dateandtime DESC");
      break;
    case 2:
      this.strSQL = 
        ("SELECT TOP " + paramInt2 + " * FROM bbs where boardid=" + paramInt4 + 
        " and (username like '%" + paramString + "%') and bbsid<=" + 
        this.i + " order by dateandtime desc");
      break;
    case 3:
      this.strSQL = 
        ("SELECT * FROM bbs where boardid=" + paramInt4 + 
        " and (dateandtime >=#" + paramString + "#) and bbsid<=" + 
        this.i + " order by dateandtime desc");
      break;
    case 4:
      this.strSQL = 
        ("SELECT * FROM bbs where bbshot='ok' and boardid=" + paramInt4 + 
        " and bbsid<=" + this.i + " order by dateandtime desc");
      break;
    case 5:
    case 6:
    case 7:
    default:
      this.strSQL = 
        ("SELECT TOP " + paramInt2 + " * FROM bbs WHERE parentid=0 and boardid=" + 
        paramInt4 + " and bbsid<=" + this.i + " ORDER BY dateandtime DESC");
    }

    this.rs = this.dbconn.executeQuery(this.strSQL);

    return this.rs;
  }

  public ResultSet listBbsRe(int paramInt)
  {
    this.rs = null;
    this.strSQL = ("select * from bbs where parentid=" + paramInt);
    this.rs = this.dbconn.executeQuery(this.strSQL);
    return this.rs;
  }

  public void setBbsBoardid(int paramInt)
  {
    this.boardid = paramInt;
  }

  public void setBbsChild(int paramInt) {
    this.child = paramInt;
  }

  public void setBbsContent(String paramString)
  {
    this.bbscontent = paramString;
  }

  public void setBbsExpression(String paramString)
  {
    this.expression = paramString;
  }

  public void setBbsHits(int paramInt)
  {
    this.bbshits = paramInt;
  }

  public void setBbsHot(int paramInt)
  {
    this.strSQL = ("update bbs set bbshot='ok' where bbsid=" + paramInt);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void setBbsHot(String paramString)
  {
    this.bbshot = paramString;
  }

  public void setBbsLength(int paramInt)
  {
    this.bbslength = paramInt;
  }

  public void setBbsNohot(int paramInt)
  {
    this.strSQL = ("update bbs set bbshot='no' where bbsid=" + paramInt);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void setBbsParentid(int paramInt)
  {
    this.parentid = paramInt;
  }

  public void setBbsTopic(String paramString)
  {
    this.bbstopic = paramString;
  }

  public void setBbsUseremail(String paramString)
  {
    this.useremail = paramString;
  }

  public void setBbsUserip(String paramString) {
    this.userip = paramString;
  }

  public void setBbsUsername(String paramString)
  {
    this.username = paramString;
  }

  public void setBbsUsersign(String paramString)
  {
    this.usersign = paramString;
  }
}