package com.tntxia.oa.bbs;

import java.sql.ResultSet;
import java.sql.SQLException;

public class bbsboard
{
	infocrmdb.infocrmdb dbconn = new infocrmdb.infocrmdb();
  ResultSet rs = null;
  String strSQL;
  int i;
  boolean yesorno;
  String boardname = "";
  String boardmaster = "";
  String masterpwd = "";
  String masterword = "";
  String masteremail = "";
  int boardid = 0;
  int boardhits = 0;
  int boardtopics = 0;

  public boolean checkBoardMaster(int paramInt, String paramString1, String paramString2)
  {
    this.rs = null;
    this.strSQL = 
      ("select * from board where boardmaster='" + paramString1 + "' and masterpwd='" + paramString2 + 
      "' and boardid=" + paramInt);
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) {
        this.yesorno = true;
        getBoardInfo(paramInt);
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

  public boolean checkBoardName(String paramString)
  {
    this.rs = null;
    this.yesorno = true;
    this.strSQL = ("select * from board where boardname='" + paramString + "'");
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) {
        this.yesorno = false;
      }
      this.rs.close();
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("countUser.STRSQL:: " + this.strSQL);
    }
    return this.yesorno;
  }

  public int countBoard()
  {
    this.strSQL = "SELECT COUNT(*) AS aa FROM board";
    this.i = 0;
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);

      if (this.rs.next()) this.i = this.rs.getInt("aa");
      this.rs.close();
    }
    catch (SQLException localSQLException) {
      System.err.println("aq.executeQuery: " + localSQLException.getMessage());
      System.err.println("countUser.STRSQL:: " + this.strSQL);
    }
    return this.i;
  }

  public void delBoard(int paramInt)
  {
    this.strSQL = ("delete from board where boardid=" + paramInt);
    this.dbconn.executeUpdate(this.strSQL);
    this.strSQL = ("delete from bbs where boardid=" + this.boardid);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public int getBoardHits()
  {
    return this.boardhits;
  }

  public int getBoardId()
  {
    return this.boardid;
  }

  public boolean getBoardInfo(int paramInt)
  {
    this.rs = null;
    this.strSQL = ("select * from board where boardid=" + paramInt);
    try {
      this.rs = this.dbconn.executeQuery(this.strSQL);
      if (this.rs.next()) {
        this.yesorno = true;
        this.boardid = this.rs.getInt("boardid");
        this.boardname = this.rs.getString("boardname");
        this.boardmaster = this.rs.getString("boardmaster");
        this.masterpwd = this.rs.getString("masterpwd");
        this.masterword = this.rs.getString("masterword");
        this.masteremail = this.rs.getString("masteremail");
        this.boardhits = this.rs.getInt("boardhits");
        this.boardtopics = this.rs.getInt("boardtopics");
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

  public String getBoardMaster()
  {
    return this.boardmaster;
  }

  public String getBoardMasteremail()
  {
    return this.masteremail;
  }

  public String getBoardMasterpwd()
  {
    return this.masterpwd;
  }
  public String getBoardMasterword() {
    return this.masterword;
  }

  public String getBoardName()
  {
    return this.boardname;
  }

  public int getBoardTopics()
  {
    return this.boardtopics;
  }

  public ResultSet listBoard(int paramInt)
  {
    this.rs = null;
    switch (paramInt) { case 1:
      this.strSQL = "select * from board order by boardid desc"; break;
    case 2:
      this.strSQL = "select * from board order by boardhits desc"; break;
    case 3:
      this.strSQL = "select * from board order by boardtopics desc"; break;
    default:
      this.strSQL = "select * from board";
    }
    this.rs = this.dbconn.executeQuery(this.strSQL);
    return this.rs;
  }

  public void newBoard()
  {
    this.strSQL = 
      ("insert into board(boardname,boardmaster,masterpwd,masteremail,masterword,boardhits,boardtopics) values('" + 
      this.boardname + "','" + this.boardmaster + "','" + 
      this.masterpwd + "','" + this.masteremail + "','" + 
      this.masterword + "'," + this.boardhits + "," + this.boardtopics + ")");
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void setBoardHits(int paramInt)
  {
    this.boardhits = paramInt;
  }

  public void setBoardMaster(String paramString)
  {
    this.boardmaster = paramString;
  }

  public void setBoardMasteremail(String paramString)
  {
    this.masteremail = paramString;
  }

  public void setBoardMasterpwd(String paramString)
  {
    this.masterpwd = paramString;
  }
  public void setBoardMasterword(String paramString) {
    this.masterword = paramString;
  }

  public void setBoardName(String paramString)
  {
    this.boardname = paramString;
  }

  public void setBoardTopics(int paramInt)
  {
    this.boardtopics = paramInt;
  }

  public void updateBoard(int paramInt)
  {
    this.strSQL = 
      ("update board set boardmaster='" + this.boardmaster + 
      "',masterpwd='" + this.masterpwd + "',masteremail='" + this.masteremail + 
      "',masterword='" + this.masterword + "' where boardid=" + paramInt);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void updateBoardHits(int paramInt1, int paramInt2)
  {
    this.strSQL = ("update board set boardhits=boardhits+" + paramInt2 + " where boardid=" + paramInt1);
    this.dbconn.executeUpdate(this.strSQL);
  }

  public void updateBoardTopics(int paramInt1, int paramInt2)
  {
    this.strSQL = ("update board set boardtopics=boardtopics+" + paramInt2 + " where boardid=" + paramInt1);
    this.dbconn.executeUpdate(this.strSQL);
  }
}