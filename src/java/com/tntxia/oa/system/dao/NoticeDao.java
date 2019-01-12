package com.tntxia.oa.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.date.DateUtil;

/**
 * 
 * 公告管理的Dao
 * 
 * @author tntxia
 * 
 */
public class NoticeDao {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("rawtypes")
	public List list() {
		String sql = "select id,titel,username,fvdate,dept,states from pub_table  order by fvdate desc";
		List result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	public void delete(int id){
		String sql = "delete from pub_table where id = ?";
		jdbcTemplate.update(sql,new Object[]{id});
	}
	
	/**
	 * 获取当前用户的公告
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getUserNotice(String dept,String username){
		
		String currentDate = DateUtil.getCurrentDateSimpleStr();
		String sql = "select id,titel,username,fvdate,dept from pub_table where dept like '%"
				+ dept
				+ "%'  and  view_date>='"
				+ currentDate
				+ "'   and  states='审批通过'   or dept='全体员工'  and  view_date>='"
				+ currentDate
				+ "'  and  states='审批通过'   or   username='"
				+ username
				+ "'   and  view_date>='"
				+ currentDate
				+ "'   and  states='审批通过'     order by fvdate desc";
		List result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Map getById(int id){
		String sql = "select * from htsp where id = ?";
		return jdbcTemplate.queryForMap(sql,new Object[]{id});
	}

}
