package com.tntxia.oa.bbs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class bbsuser {
	infocrmdb.infocrmdb dbconn = new infocrmdb.infocrmdb();
	ResultSet rs = null;
	String strSQL;
	int i;
	boolean isUser;
	int userid = 0;

	int userclass = 0;

	String username = null;
	String userpassword = null;
	String useremail = null;
	String userhomepage = null;
	String hpname = null;
	String usersex = null;
	String comefrom = null;
	String usersign = null;

	Date userbirthday = new Date();
	Date dateandtime = new Date();

	public void changeUserClass(String paramString, int paramInt) {
		this.strSQL = ("update bbs_user set userclass=userclass+" + paramInt
				+ " where username='" + paramString + "'");
		this.dbconn.executeUpdate(this.strSQL);
	}

	public void changeUserPassword(String paramString1, String paramString2) {
		this.strSQL = ("update bbs_user set userpassword='" + paramString2
				+ "' where username='" + paramString1 + "'");
		this.dbconn.executeUpdate(this.strSQL);
	}

	public boolean checkUserPassword(String paramString1, String paramString2) {
		this.rs = null;
		this.strSQL = ("select * from bbs_user where username='" + paramString1
				+ "' and userpassword='" + paramString2 + "'");
		try {
			this.rs = this.dbconn.executeQuery(this.strSQL);
			if (this.rs.next()) {
				this.isUser = true;
				getUserInfo(paramString1);
			} else {
				this.isUser = false;
			}
			this.rs.close();
		} catch (SQLException localSQLException) {
			System.err.println("aq.executeQuery: "
					+ localSQLException.getMessage());
			System.err.println("checkuserpassword.STRSQL:: " + this.strSQL);
		}
		return this.isUser;
	}

	public int countUser() {
		this.strSQL = "SELECT COUNT(*) AS aa FROM bbs_user";
		this.i = 0;
		try {
			this.rs = this.dbconn.executeQuery(this.strSQL);

			if (this.rs.next())
				this.i = this.rs.getInt("aa");
			this.rs.close();
		} catch (SQLException localSQLException) {
			System.err.println("aq.executeQuery: "
					+ localSQLException.getMessage());
			System.err.println("countUser.STRSQL:: " + this.strSQL);
		}
		return this.i;
	}

	public void delUser(String paramString) {
		this.strSQL = ("delete from bbs_user where username='" + paramString + "'");
		this.dbconn.executeUpdate(this.strSQL);
	}

	public Date getUserBirthday() {
		return this.userbirthday;
	}

	public int getUserClass() {
		return this.userclass;
	}

	public String getUserComefrom() {
		return this.comefrom;
	}

	public Date getUserDateandtime() {
		return this.dateandtime;
	}

	public String getUserEmail() {
		return this.useremail;
	}

	public String getUserHomepage() {
		return this.userhomepage;
	}

	public String getUserHomepagename() {
		return this.hpname;
	}

	public int getUserId() {
		return this.userid;
	}

	public boolean getUserInfo(String paramString) {
		this.rs = null;
		this.strSQL = ("select * from bbs_user where username='" + paramString + "'");
		try {
			this.rs = this.dbconn.executeQuery(this.strSQL);
			if (this.rs.next()) {
				this.isUser = true;
				this.userid = this.rs.getInt("userid");
				this.username = this.rs.getString("username");
				this.userpassword = this.rs.getString("userpassword");
				this.useremail = this.rs.getString("useremail");
				this.userhomepage = this.rs.getString("userhomepage");
				this.hpname = this.rs.getString("hpname");
				this.userbirthday = this.rs.getDate("userbirthday");
				this.usersex = this.rs.getString("usersex");
				this.comefrom = this.rs.getString("comefrom");
				this.userclass = this.rs.getInt("userclass");
				this.usersign = this.rs.getString("usersign");
				this.dateandtime = this.rs.getDate("dateandtime");
			} else {
				this.isUser = false;
			}
			this.rs.close();
		} catch (SQLException localSQLException) {
			System.err.println("aq.executeQuery: "
					+ localSQLException.getMessage());
			System.err.println("aq.STRSQL:: " + this.strSQL);
		}
		return this.isUser;
	}

	public String getUserName() {
		return this.username;
	}

	public String getUserPassword() {
		return this.userpassword;
	}

	public String getUserSex() {
		return this.usersex;
	}

	public String getUserSign() {
		return this.usersign;
	}

	public ResultSet listUser(int paramInt) {
		this.rs = null;
		switch (paramInt) {
		case 1:
			this.strSQL = "select * from bbs_user order by userid desc";
			break;
		case 2:
			this.strSQL = "select * from bbs_user order by username desc";
			break;
		case 3:
			this.strSQL = "select * from bbs_user order by userclass desc";
			break;
		default:
			this.strSQL = "select * from bbs_user order by userid desc";
		}
		this.rs = this.dbconn.executeQuery(this.strSQL);
		return this.rs;
	}

	public void registryNewUser() {
		this.strSQL = ("INSERT INTO bbs_USER(username,userpassword,useremail,userhomepage,hpname,usersex,comefrom,userclass,usersign) values('"
				+ this.username
				+ "','"
				+ this.userpassword
				+ "','"
				+ this.useremail
				+ "','"
				+ this.userhomepage
				+ "','"
				+ this.hpname
				+ "','"
				+ this.usersex
				+ "','"
				+ this.comefrom
				+ "','" + this.userclass + "','" + this.usersign + "')");
		this.dbconn.executeUpdate(this.strSQL);
	}

	public void setUserBirthday(Date paramDate) {
		this.userbirthday = paramDate;
	}

	public void setUserClass(int paramInt) {
		this.userclass = paramInt;
	}

	public void setUserComefrom(String paramString) {
		this.comefrom = paramString;
	}

	public void setUserDateandtime(Date paramDate) {
		this.dateandtime = paramDate;
	}

	public void setUserEmail(String paramString) {
		this.useremail = paramString;
	}

	public void setUserHomepage(String paramString) {
		this.userhomepage = paramString;
	}

	public void setUserHomepagename(String paramString) {
		this.hpname = paramString;
	}

	public void setUserName(String paramString) {
		this.username = paramString;
	}

	public void setUserPassword(String paramString) {
		this.userpassword = paramString;
	}

	public void setUserSex(String paramString) {
		this.usersex = paramString;
	}

	public void setUserSign(String paramString) {
		this.usersign = paramString;
	}

	public void updateUserInfo(String paramString) {
		this.strSQL = ("update bbs_user set useremail='" + this.useremail
				+ "',userhomepage='" + this.userhomepage + "',hpname='"
				+ this.hpname + "',usersex='" + this.usersex + "',comefrom='"
				+ this.comefrom + "',usersign='" + this.usersign
				+ "' where username='" + paramString + "'");
		this.dbconn.executeUpdate(this.strSQL);
	}
}