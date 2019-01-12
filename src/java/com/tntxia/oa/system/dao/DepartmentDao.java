package com.tntxia.oa.system.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.web.mvc.BaseDao;

public class DepartmentDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();
	

	/**
	 * 获取所有的部门列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List<Object> getDepartmentList() throws Exception {

		String sql = SystemCache.sqlmapping.get("getAllDepartment");
		return dbManager.queryForList(sql, Department.class);
	}
	
	/**
	 * 通过ID寻找部门
	 * @param id
	 * @return
	 * @throws Exception 
	 * @throws SQLException 
	 */
	public Department getDepartment(int id) throws Exception
	{
		
		String sql = "select id, departname name,dept_code departCode,dept_types type,remark,leader from department where id= ? ";
		return (Department)dbManager.queryForObject(sql, new Object[]{id}, Department.class);

	}
	
	public boolean existCode(String code) throws Exception{
		String sql="select count(*) from department where dept_code='" + code + "'";
		return dbManager.queryForInt(sql)>0;
		
	}
	
	public boolean exist(String id) throws Exception{
		String sql="select  count(*) from department where id=?";
		
		return dbManager.queryForInt(sql,new Object[]{id})>0;
		
	}
	
	public void add(Map<String,Object> params) throws Exception{
		String sql="insert into department(departname,dept_types,dept_code,remark,leader) "
				+ "values(?,?,?,?,?)";
		dbManager.update(sql,new Object[]{params.get("departname"),params.get("type"),
				params.get("code"),params.get("remark"),params.get("leader")});
		 
	}
	
	public void update(Department dept) throws Exception{
		String sql="update department set remark=?,leader=?  where id=?";
		dbManager.update(sql,new Object[]{dept.getRemark(),
				dept.getLeader(),dept.getId()});
		 
	}
	
	public void del(int id) throws Exception{
		String sql="delete from department where id=?";
		dbManager.update(sql,new Object[]{id});
		 
	}
}
