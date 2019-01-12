package com.tntxia.oa.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.tntxia.db.DBConnection;

public class CurrentUtils {
	
	private static Map<String,BigDecimal> currentRateMapping = new HashMap<String,BigDecimal>();
	
	private static boolean isInit = false;
	
	public static void init() throws SQLException{
		
		DBConnection db = new DBConnection();
		String sql = "select * from hltable";
		ResultSet rs = db.executeQuery(sql);
		while(rs.next()){
			currentRateMapping.put(rs.getString("currname"), rs.getBigDecimal("currrate"));
		}
		isInit = true;
	}
	
	public static BigDecimal getCurrentRate(String hb) throws SQLException{
		
		if(!isInit)
			init();
		
		return currentRateMapping.get(hb);
		
	}

}
