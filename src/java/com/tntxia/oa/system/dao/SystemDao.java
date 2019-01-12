package com.tntxia.oa.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.SystemInfo;
import com.tntxia.web.mvc.BaseDao;

public class SystemDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();

	@SuppressWarnings("rawtypes")
	public SystemInfo getSystemInfo(){
		
		String sql = "select top 1 * from system_info";

		SystemInfo res = new SystemInfo();
		
		String companyName = "";
		String website = "";
		
		try {
			Map map = dbManager.queryForMap(sql, true);
			companyName = (String)map.get("companyname");
			website = (String)map.get("website");
			
			res.setCompanyName(companyName);
			res.setWebsite(website);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
		return res;
	}
	
	public void updateSystemInfo(SystemInfo info){
		DBConnection einfodb = new DBConnection();
		
		String sql = "update system_info set companyName = ?, website = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(info.getCompanyName());
		params.add(info.getWebsite());

		einfodb.executeUpdate(sql, params);
		
		einfodb.close();
		
	}
	
	public boolean existAdmin(String username,String password){
		
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(password);
		String sql = "select count(*) from admin where adminname = ? and password = ?";
		return dbManager.exist(sql,new String[]{username,password});
	}

}
