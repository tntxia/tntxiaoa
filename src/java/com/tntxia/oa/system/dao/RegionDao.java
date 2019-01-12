package com.tntxia.oa.system.dao;

import java.sql.SQLException;
import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.City;
import com.tntxia.oa.system.entity.Province;

public class RegionDao {
	
	private DBManager dbManager;
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	@SuppressWarnings("rawtypes")
	public List getProvinceList() throws Exception{
		String sql = "select id,province_name name from province";
		return dbManager.queryForList(sql, Province.class);
	}

	public Province getProvinceById(int id) throws Exception{
		String sql = "select id,province_name name from province where id = ?";
		return (Province)dbManager.queryForObject(sql, new Object[]{id}, Province.class);
	}
	
	@SuppressWarnings("rawtypes")
	public List getCityListByProvinceId(int id) throws Exception{
		String sql = "select id,city_name name from city where provinceId = ?";
		return dbManager.queryForList(sql, new Object[]{id}, City.class);
	}
	
	public void addCity(String city,int provinceId) throws SQLException{
		String sql = "insert into city(city_name,provinceId) values(?,?)";
		dbManager.executeUpdate(sql, new Object[]{city,provinceId});
	}

}
