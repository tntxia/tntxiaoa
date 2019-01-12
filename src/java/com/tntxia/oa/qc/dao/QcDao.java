package com.tntxia.oa.qc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.db.DBUtil;
import com.tntxia.oa.qc.entity.QcCheckItemResult;
import com.tntxia.oa.qc.entity.QcItem;
import com.tntxia.oa.system.entity.QcCheckItem;

/**
 * 
 * 品管Dao
 * 
 * @author tntxia
 *
 */
public class QcDao {
	
	/**
	 * 获取品管信息列表
	 * @return
	 */
	public List<QcItem> getQcList(){
		
		DBConnection einfodb = new DBConnection();
		
		List<QcItem> res = new ArrayList<QcItem>();
		
		String sql = "select qc.id,qc.qc_date,qc.number,qc.model," +
				"qc.result,qc.remark,c.id checkId," +
				"c.name,cr.result checkResult " +
				"from qc_item qc,qc_check_item c,qc_check_result cr " +
				"where qc.id = cr.qc_item_id and c.id = cr.qc_check_item_id order by qc.id";
		
		ResultSet rs = einfodb.executeQuery(sql);
		
		int tempId = 0;
		
		QcItem item = null;
		
		try {
			while(rs.next()){
				
				int id = rs.getInt("id");
				
				if(id!=tempId){
					item = new QcItem();
					item.setId(id);
					item.setDate(rs.getDate("qc_date"));
					item.setNumber(rs.getString("number"));
					item.setResult(rs.getString("result"));
					item.setRemark(rs.getString("remark"));
					item.setModel(rs.getString("model"));
					res.add(item);
					tempId = id;
				}
				
				QcCheckItemResult r = new QcCheckItemResult();
				r.setQcId(id);
				r.setCheckItemId(rs.getInt("checkId"));
				r.setCheckItemName(rs.getString("name"));
				r.setResult(rs.getString("checkResult"));
				item.getCheckItemResultList().add(r);
			}
		} catch (SQLException e) {
			DBUtil.closeResultSet(rs);
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	/**
	 * 获取品管信息列表
	 * @return
	 */
	public QcItem getQcById(int id){
		
		DBConnection einfodb = new DBConnection();
		
		List<QcItem> res = new ArrayList<QcItem>();
		
		String sql = "select qc.id,qc.qc_date,qc.number,qc.model," +
				"qc.result,qc.remark,c.id checkId," +
				"c.name,cr.result checkResult " +
				"from qc_item qc,qc_check_item c,qc_check_result cr " +
				"where qc.id = cr.qc_item_id and c.id = cr.qc_check_item_id "
				+ " and qc.id = ?";
				
		
		ResultSet rs = einfodb.executeQuery(sql,id);
		
		int tempId = 0;
		
		QcItem item = null;
		
		try {
			while(rs.next()){
				
				if(id!=tempId){
					item = new QcItem();
					item.setId(id);
					item.setDate(rs.getDate("qc_date"));
					item.setNumber(rs.getString("number"));
					item.setResult(rs.getString("result"));
					item.setRemark(rs.getString("remark"));
					item.setModel(rs.getString("model"));
					res.add(item);
					tempId = id;
				}
				
				QcCheckItemResult r = new QcCheckItemResult();
				r.setQcId(id);
				r.setCheckItemId(rs.getInt("checkId"));
				r.setCheckItemName(rs.getString("name"));
				r.setResult(rs.getString("checkResult"));
				item.getCheckItemResultList().add(r);
			}
		} catch (SQLException e) {
			DBUtil.closeResultSet(rs);
			e.printStackTrace();
		}
		
		return item;
		
	}
	
	/**
	 * 获取检查项列表
	 * @return
	 */
	public List<QcCheckItem> getCheckItemList(){
		DBConnection einfodb = new DBConnection();
		
		List<QcCheckItem> res = new ArrayList<QcCheckItem>();
		
		String sql = "select * from qc_check_item ";
		
		ResultSet rs = einfodb.executeQuery(sql);
		
		try {
			while(rs.next()){
				QcCheckItem item = new QcCheckItem();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String display = rs.getString("display");
				item.setId(id);
				item.setName(name);
				item.setDisplay(display);
				res.add(item);
			}
		} catch (SQLException e) {
			DBUtil.closeResultSet(rs);
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 增加检查项
	 * @param name
	 */
	public void addCheckItemList(QcCheckItem checkItem){
		
		DBConnection db = new DBConnection();
		String sql = "insert into qc_check_item(name,display) values(?,?) ";
		List<Object> params = new ArrayList<Object>();
		params.add(checkItem.getName());
		params.add(checkItem.getDisplay());
		db.executeUpdate(sql, params);
		db.close();
		
	}
	
	/**
	 * 增加一个品管信息
	 * @param item
	 */
	public void add(QcItem item){
		DBConnection db = new DBConnection();
		String sql = "insert into qc_item(qc_date,number,result,remark,model) values(getdate(),?,?,?,?)";
		
		List<Object> params = new ArrayList<Object>();
		params.add(item.getNumber());
		params.add(item.getResult());
		params.add(item.getRemark());
		params.add(item.getModel());
		
		db.executeUpdate(sql, params);
	}
	
	/**
	 * 增加一个品管信息
	 * @param item
	 */
	public void update(QcItem item){
		DBConnection db = new DBConnection();
		String sql = "update qc_item set number = ?,model=?,qc_date = getdate(),result=?,remark=? where id = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(item.getNumber());
		params.add(item.getModel());
		
		params.add(item.getResult());
		params.add(item.getRemark());
		params.add(item.getId());
		
		db.executeUpdate(sql, params);
		db.close();
	}
	
	/**
	 * 增加一个品管信息
	 * @param item
	 */
	public void delete(int id){
		DBConnection db = new DBConnection();
		String sql = "delete from qc_item where id = ?";
		
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		
		db.executeUpdate(sql, params);
		db.close();
	}
	
	/**
	 * 清除以前检查项的结果
	 * @param id
	 */
	public void clearQcCheckResult(int id){
		DBConnection db = new DBConnection();
		String sql = "delete from qc_check_result where qc_item_id = ?";
		db.executeUpdate(sql, id);
		db.close();
	}
	
	/**
	 * 返回自增的大小 
	 * @return
	 */
	public int getIdent(){
		
		DBConnection db = new DBConnection();
		int ident = db.getIdenty("qc_item");
		db.close();
		return ident;
		
	}
	
	/**
	 * 增加检查项的结果
	 * @param qcItemId
	 * @param checkItemId
	 * @param result
	 */
	public void addQcCheckResult(int qcItemId,int checkItemId,String result){
		DBConnection db = new DBConnection();
		String sql = "insert into qc_check_result(qc_item_id,qc_check_item_id,result) values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(qcItemId);
		params.add(checkItemId);
		params.add(result);
		db.executeUpdate(sql, params);
		db.close();
	}
	
	/**
	 * 增加检查项的结果
	 * @param qcItemId
	 * @param checkItemId
	 * @param result
	 */
	public void updateQcCheckResult(int qcItemId,int checkItemId,String result){
		DBConnection db = new DBConnection();
		String sql = "update qc_check_result set result=? where qc_item_id = ? and qc_check_item_id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(result);
		params.add(qcItemId);
		params.add(checkItemId);
		db.executeUpdate(sql, params);
		db.close();
	}

}
