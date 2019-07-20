package com.tntxia.oa.system.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.entity.User;
import com.tntxia.oa.util.BeanUtil;
import com.tntxia.web.mvc.BaseDao;
import com.tntxia.web.util.DBManagerUtil;

public class UserDao extends BaseDao{
	
	private DBManager dbManager = DBManagerUtil.getDBManager();

	/**
	 * 
	 * 增加用户 
	 * @param user
	 * @throws SQLException 
	 * 
	 */
	public void addUser(User user) throws SQLException{
		
		String name = user.getName();
		String sex = user.getSex();
		String password = user.getPassword();
		int departmentId = user.getDepartmentId();
		String position = user.getPosition();
		String email = user.getEmail();
		String tel = user.getTel();
		String telHome = user.getTelHome();
		
		String sql = SystemCache.sqlmapping.get("addUser");
		
		List<Object> params = new ArrayList<Object>();
		params.add(name);
		params.add(user.getNameEn());
		params.add(sex);
		params.add(password);
		params.add(departmentId);
		params.add(position);
		params.add(email);
		params.add(tel);
		params.add(telHome);
		params.add(user.getIp());
		params.add(user.isIpBind());
		params.add(user.getEmailUser());
		params.add(user.getEmailPassword());
		params.add(user.getEmailStmp());
		params.add(user.getRestrainId());
		
		dbManager.executeUpdate(sql, params);
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	private User getUserFromMap(Map map) throws SQLException{
		
		User res = new User();
		String ipbd = (String) map.get("ipbd");
		BeanUtil.transMap2Bean(map, res);
		if (ipbd.equals("Y")) {
			res.setIpBind(true);
		}
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public List getList() throws Exception{
		String sql = "select u.nameid,u.name,d.departname,r.restrain_name from username u left outer join department d on u.department_id = d.id left outer join restrain r on u.restrain_id = r.id order by department_id";
		return dbManager.queryForList(sql,true);
	}

	/**
	 * 获取用户列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List<Object> getUserList() throws Exception {
		
		String sql = "select nameid id,name,password,department_id departmentId,restrain_id restrainId from username";

		return dbManager.queryForList(sql, User.class);

	}
	
	/**
	 * 获取销售部门用户列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<User> getSaleUserList(String departmentName) throws Exception {

		String sql = "select * from username where department_id in (select id from department where departname in ('销售部','销售二部','总经办'))";
		
		if(departmentName!=null){
			sql += " and yjxs='"+departmentName+"'";
		}

		List list = dbManager.queryForList(sql,true);
		
		List<User> res = new ArrayList<User>();
		
		try {
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				User user = getUserFromMap(map);
				res.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	/**
	 * 获取销售部门用户列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<User> getPurchasingUserList() throws Exception {

		String sql = "select * from username where department_id in (select id from department where departname in ('采购部','采购二部','总经办'))";
		
		List list = dbManager.queryForList(sql,true);
		
		List<User> res = new ArrayList<User>();
		
		try {
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				User user = getUserFromMap(map);
				res.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public boolean checkUserExist(String name) throws Exception{
		String sql = "select count(*) from username where name = ?";
		Integer res = dbManager.queryForInt(sql,new Object[]{name});
		return res!=0;
	}
	
	public boolean checkUserExist(String name,String password) throws Exception{
		String sql = "select count(*) from username where name = ? and password = ?";
		Integer res = dbManager.queryForInt(sql,new Object[]{name,password});
		return res!=0;
	}
	
	/**
	 * 获取用户信息
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public User getUser(String name) throws Exception{
		
		String sql = SystemCache.sqlmapping.get("getUserByName");
		
		System.out.println(sql);
		
		List list = dbManager.queryForList(sql,new Object[]{name},true);
		
		if(list.size()==0){
			return null;
		}
		
		Map map = (Map)list.get(0);
		
		User res = getUserFromMap(map);

		return res;
	}
	
	/**
	 * 获取用户信息
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public User getUserById(int id) throws Exception{
		
		String sql = SystemCache.sqlmapping.get("getUserById");
		
		Map map = dbManager.queryForMap(sql,new Object[]{id},true);
		
		if(map==null){
			return null;
		}
		
		User res = getUserFromMap(map);

		return res;
		
	}
	
	
	
	/**
	 * 获取用户信息
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public void resetUserErrCount(int id) throws SQLException{
		
		String sql = "update username set err = 0 where nameid = ?";
		dbManager.executeUpdate(sql, new Object[]{id});


	}
	
	/**
	 * 用戶信息更新
	 * @param user
	 */
	public void updateUser(User user){
		
		DBConnection db = new DBConnection();
		
		String sql = "update username set password = ?,email=?,mail_user=?,mail_password=?,mail_smtp=? where name = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getPassword());
		params.add(user.getEmail());
		params.add(user.getEmailUser());
		params.add(user.getEmailPassword());
		params.add(user.getEmailStmp());
		params.add(user.getName());
		db.executeUpdate(sql, params);
		
	}
	
	/**
	 * 用戶信息删除
	 * @param user
	 */
	public void deleteUser(int id){
		
		DBConnection db = new DBConnection();
		
		String sql = "delete from username where nameid = ?";
		db.executeUpdate(sql, id);
		
	}
	
	/**
	 * 增加用户的错误次数
	 * @throws Exception 
	 */
	public void addUserErrorCount(int id) throws Exception{
		String sql = "update username set err = err +1 where nameid = ?";
		dbManager.update(sql, new Object[]{id});
	}
	
	/**
	 * 获取用户信息
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map getUserEmailInfo(String name) throws Exception{
		
		String sql = "select * from username where name=?";
		
		Map map = dbManager.queryForMap(sql,new Object[]{name},true);
		
		return map;
		
	}

}
