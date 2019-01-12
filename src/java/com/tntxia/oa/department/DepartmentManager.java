package com.tntxia.oa.department;

import java.sql.ResultSet;

import infocrmdb.infocrmdb;

public class DepartmentManager {
	
	public String getDepartmentLeaderByName(String name){
		infocrmdb db = new infocrmdb();
		String sql = "select leader from department where departname ='"+name+"'";
		
		String leader = null;
		ResultSet rs = null;
		try{
			rs = db.executeQuery(sql);
			if(rs.next()){
				leader = rs.getString("leader");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close();
		}
		return leader;
	}

}
