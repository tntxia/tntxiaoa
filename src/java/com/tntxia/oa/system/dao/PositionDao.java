package com.tntxia.oa.system.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.Position;

public class PositionDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private DBManager dbManager;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * 获取职位列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List list() throws Exception {
		
		String sql = "select id,role_name name,role_date roleDate from role";
		
		List result = dbManager.queryForList(sql, true);
		
		return result;
	}
	
	public Position getById(int id){
		String sql = "select id,role_name name,remark,role_date from role where id = ?";
		Map map = jdbcTemplate.queryForMap(sql,new Integer[]{id});
		Position res = new Position();
		res.setId((Integer)map.get("id"));
		res.setName((String)map.get("name"));
		res.setRemark((String)map.get("remark"));
		res.setDate((Date)map.get("role_date"));
		return res;
	}
	
	public void del(int id){
		String sql="delete from role where id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}
	
	public void update(Position pos){
		 String sql="update role set remark=?,role_date=getdate() where id=?";
		jdbcTemplate.update(sql,new Object[]{pos.getRemark(),pos.getId()});
	}
	
	public void add(Position pos){
		String sql="insert into role(role_name,remark,role_date) values(?,?,getdate())";
		
		jdbcTemplate.update(sql,new Object[]{pos.getName(),pos.getRemark()});
	}

}
