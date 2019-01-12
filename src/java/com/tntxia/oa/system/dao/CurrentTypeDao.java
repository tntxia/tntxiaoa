package com.tntxia.oa.system.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.CurrentType;

public class CurrentTypeDao {
	
	private DBManager dbManager;

	
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public CurrentType getByName(String name) throws Exception{
		
		String sql = "select currname,currrate from hltable where currname=?";
		List list = dbManager.queryForList(sql, new Object[]{name}, true);
		
		
		Map result = null;
		
		if(list!=null && list.size()>0){
			result = (Map) list.get(0);
		}
		
		if(result==null){
			return null;
		}
		CurrentType curr = new CurrentType();
		curr.setName((String)result.get("currname"));
		curr.setRate(((BigDecimal)result.get("currrate")).doubleValue());
		return curr;
	}

	/**
	 * 货币列表
	 * 
	 * @return
	 */
	public List<CurrentType> getCurrentTypeList() {

		DBConnection einfodb = new DBConnection();

		ResultSet rs=einfodb.executeQuery("select * from hltable");
		
		List<CurrentType> res = new ArrayList<CurrentType>();
		
		try {
			while(rs.next()){
				CurrentType type = new CurrentType();
				type.setName(rs.getString("currname"));
				type.setRate(rs.getDouble("currrate"));
				res.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

}
