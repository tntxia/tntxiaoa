package com.tntxia.oa.back;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;

public class ThTablePro {
	
	private int id;
	
	private int ddid;
	
	private int num;
	
	private int s_num;
	
	private ThTable thTable;
	
	

	public ThTable getThTable() {
		
		if(thTable!=null)
			return thTable;
		
		infocrmdb einfodb = new infocrmdb();
		String sqldd="select id,number,coname,senddate,man,money,datetime,dept,deptjb,sub from th_table where id='"+ddid+"'";
		ResultSet rs=einfodb.executeQuery(sqldd);
		ThTable thTable = new ThTable();
		try{
			if(rs.next()){
				thTable.setId(rs.getInt("id"));
				thTable.setNumber(rs.getString("number"));
				thTable.setConame(rs.getString("coname"));
				thTable.setCo_number(rs.getString("senddate"));
				thTable.setMan(rs.getString("man"));
				thTable.setMoney(rs.getString("money"));
				thTable.setDatetime(rs.getDate("datetime"));
				thTable.setDept(rs.getString("dept"));
				thTable.setDeptjb(rs.getString("deptjb"));
				thTable.setSub(rs.getString("sub"));
			}
			einfodb.close();
		}catch(Exception e){
			e.printStackTrace();
			einfodb.close();
		}finally{
			einfodb.close();
		}
		
		
		return thTable;
	}

	public void setThTable(ThTable thTable) {
		this.thTable = thTable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getDdid() {
		return ddid;
	}

	public void setDdid(int ddid) {
		this.ddid = ddid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getS_num() {
		return s_num;
	}

	public void setS_num(int s_num) {
		this.s_num = s_num;
	}

	
	
}
