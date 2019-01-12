package com.tntxia.oa.dao;

import com.tntxia.db.DBConnection;

public class PdDao {
	
	public void deleteAll(){
		DBConnection einfodb = new DBConnection();
		String sql = "delete from pd";
		einfodb.executeUpdate(sql);
		einfodb.close();
	}

}
