package com.tntxia.oa.system.dao;

import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.util.DateUtil;

public class CompanyDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("rawtypes")
	public void add(Map param){
		
		String sql = SystemCache.sqlmapping.get("addCompany");
		
		String datetime = DateUtil.getNowString("yyyy-MM-dd");
		
		jdbcTemplate.update(sql, new Object[]{
			param.get("name"),param.get("name2"),param.get("addr"),param.get("addr2"),param.get("tel"),
			param.get("fax"),param.get("bank"),param.get("bank2"),param.get("bank_dm"),param.get("bank_dm2"),
			param.get("number"),param.get("number2"),param.get("bank_code"),param.get("bank_code2"),param.get("com_sy_code"),
			param.get("com_sy_code2"),param.get("com_sy_name"),param.get("com_sy_name2"),param.get("bankroll"),param.get("companyman"),
			param.get("companydm"),param.get("companysh"),param.get("companybm"),param.get("companylxr"),param.get("companyemail"),
			param.get("companynet"),param.get("username"),datetime,param.get("remark")
		});
		
	}

}
