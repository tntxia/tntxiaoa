package com.tntxia.oa.finance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.finance.entity.CreditDebit;

public class FinanceDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private DBManager dbManager;
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@SuppressWarnings("rawtypes")
	public CreditDebit getCreditDebitById(int id) throws Exception{
		String sql="select l_coname from credit_debit where id=?";
		Map map = dbManager.queryForMap(sql,new Object[]{id},true);
		if(map==null){
			return null;
		}
		CreditDebit result = new CreditDebit();
		result.setConame((String)map.get("l_coname"));
		return result;
	}
	
	/**
	 * 查看订单是否冲帐
	 * @param number
	 * @return
	 */
	public boolean isRefundFlush(String number){
		String sql = "select count(id) from gathering_refund where orderform = ? and states = '已冲帐'";
		int count = jdbcTemplate.queryForInt(sql,new Object[]{number});
		return count>0;
	}
	
	@SuppressWarnings("rawtypes")
	public List getRefundGatherList(String number){
		String sql = "select * from gathering_refund where orderform = ?";
		return jdbcTemplate.queryForList(sql,new Object[]{number});
	}
	
	public void deleteRefundGather(String id ){
		String sql = "update gathering_refund set states='已删除' where id = ?";
		jdbcTemplate.update(sql, new Object[]{id});
	}

}
