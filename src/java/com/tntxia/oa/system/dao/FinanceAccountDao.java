package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.FinanceAccount;
import com.tntxia.oa.system.entity.FinanceAccountDetail;

public class FinanceAccountDao {
	
	public FinanceAccount getById(int id){
		DBConnection db = new DBConnection();
		FinanceAccount res = new FinanceAccount();
		String sql = "select * from km";
		ResultSet rs = db.executeQuery(sql);
		try {
			if(rs.next()){
				res.setId(rs.getInt("id"));
				res.setName(rs.getString("coun_name"));
				res.setNameEn(rs.getString("coun_ename"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 查看科目明细
	 * @param id
	 * @return
	 */
	public List<FinanceAccountDetail> getFinanceDetailList(int id){
		DBConnection db = new DBConnection();
		List<FinanceAccountDetail> res = new ArrayList<FinanceAccountDetail>();
		String sql = "select id,km,km_mx,kmId from  km_mx  where  kmId=? order by km asc";
		
		ResultSet rs = db.executeQuery(sql, id);
		
		try {
			while(rs.next()){
				FinanceAccountDetail detail = new FinanceAccountDetail();
				detail.setId(rs.getInt("id"));
				detail.setKm(rs.getString("km"));
				detail.setName(rs.getString("km_mx"));
				detail.setKmId(rs.getInt("kmId"));
				res.add(detail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
		
		return res;
	}
	
	/**
	 * 查看科目明细
	 * @param id
	 * @return
	 */
	public List<FinanceAccountDetail> getFinanceDetailList(){
		DBConnection db = new DBConnection();
		List<FinanceAccountDetail> res = new ArrayList<FinanceAccountDetail>();
		String sql = "select id,km,km_mx,kmId from  km_mx order by km asc";
		
		ResultSet rs = db.executeQuery(sql);
		
		try {
			while(rs.next()){
				FinanceAccountDetail detail = new FinanceAccountDetail();
				detail.setId(rs.getInt("id"));
				detail.setKm(rs.getString("km"));
				detail.setName(rs.getString("km_mx"));
				detail.setKmId(rs.getInt("kmId"));
				res.add(detail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.close();
		
		return res;
	}
	
	/**
	 * 查看科目明细
	 * @param id
	 * @return
	 */
	public void addDetail(int accountId,String name){
		
		FinanceAccount account = getById(accountId);
		
		DBConnection db = new DBConnection();
		List<FinanceAccountDetail> res = new ArrayList<FinanceAccountDetail>();
		String sql = "insert into km_mx(kmId,km,km_mx) values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(accountId);
		params.add(account.getName());
		params.add(name);
		db.executeUpdate(sql, params);
		
		
		db.close();
		
	}

}
