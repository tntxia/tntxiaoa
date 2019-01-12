package com.tntxia.oa.admin.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.web.mvc.BaseDao;

public class AdminDao extends BaseDao{
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public boolean checkUserExist(String name){
		String sql = "select count(*) from admin where adminname = ?";
		Integer res = jdbcTemplate.queryForInt(sql,new Object[]{name});
		return res!=0;
	}
	
	public boolean checkUserExist(String name,String password){
		String sql = "select count(*) from admin where adminname = ? and password = ? ";
		Integer res = jdbcTemplate.queryForInt(sql,new Object[]{name,password});
		return res!=0;
	}

}
