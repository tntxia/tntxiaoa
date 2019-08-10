package com.tntxia.oa.finance.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseDao;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.SqlCache;

public class FinanceLightDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public List getGatheringList(Map<String,Object> param) throws Exception{
		String sql = SqlCache.get("gathering", param);
		return dbManager.queryForList(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllGatheringList(Transaction trans) throws Exception{
		
		String sql = SqlCache.get("gatheringAll");
		return trans.queryForList(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getAllGatheringList(Map<String,Object> params) throws Exception{
		
		String sql = SqlCache.get("gatheringAll",params);
		return dbManager.queryForList(sql, true);
	}
	
	public Map<String,Object> getAllGatheringStatist(Map<String,Object> param) throws Exception{
		
		String sql = SqlCache.get("gatheringStatist",param);
		
		return dbManager.queryForMap(sql, true);
	}
	
	
	
	public int getGatheringCount(Map<String,Object> param) throws Exception{
		
		String sql = SqlCache.get("gatheringCount", param);
		
		return dbManager.queryForInt(sql);
	}
	
	
	
	public Map<String,Object> getGathering(int id) throws Exception{
		
		String sql="select  * from gathering where id=?";
		Map<String,Object> map = dbManager.queryForMap(sql, new Object[]{id}, true);
		
		return map;
		
	}
	
	public Map<String,Object> getGatheringBySaleId(int id) throws Exception{
		
		String sql="select  * from gathering where fyid=?";
		Map<String,Object> map = dbManager.queryForMap(sql, new Object[]{id}, true);
		return map;
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getTaxWaitList(Map<String,Object> param,PageBean pageBean) throws Exception {
		
		String sql = "select top "+pageBean.getTop()+" * from gathering where  (bankaccounts='待开发票' and rate<>'不含税')";
		return dbManager.queryForList(sql, true);
		
	}
	
	public int getTaxWaitCount(Map<String,Object> param) throws Exception {
		
		String sql = "select count(*) from gathering where  (bankaccounts='待开发票' and rate!='不含税')";
		return dbManager.queryForInt(sql);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getTaxFinishList(Map<String,Object> param,PageBean pageBean) throws Exception {
		String sql = "select top "+pageBean.getTop()+" * from gathering where  (bankaccounts='已开发票' and rate<>'不含税')";
		return dbManager.queryForList(sql, true);
	}
	
	public int getTaxFinishCount(Map<String,Object> param) throws Exception {
		
		String sql = "select count(*) from gathering where  (bankaccounts='已开发票' and rate<>'不含税')";
		return dbManager.queryForInt(sql);
		
	}
	
	public void comment(String id,String note) throws Exception{
		String sql = "update gathering set note = ? where id=?";
		dbManager.update(sql, new Object[]{note,id});
	}
	
	public void updatePaymentStatus(String orderId,String status) throws Exception{
		String sql = "update payment set states=? where orderform=?";
		dbManager.update(sql, new Object[]{status,orderId});
	}
	
	public int getExchangeCount(Map<String,String> param) throws Exception{
		
		String sqlWhere = "";
		
		String coname = param.get("coname");
		if(StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and l_coname like '%"+coname+"%'";
		}
		String xsdh = param.get("xsdh");
		if(StringUtils.isNotEmpty(xsdh)) {
			sqlWhere += " and xsdh like '%"+xsdh+"%'";
		}
		
		String sql = "select count(*) from  credit_debit where 1=1 "+sqlWhere;
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public List getExchangeList(PageBean pageBean,Map<String,String> param) throws Exception{
		
		String sqlWhere = "";
		
		String coname = param.get("coname");
		if(StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and l_coname like '%"+coname+"%'";
		}
		
		String xsdh = param.get("xsdh");
		if(StringUtils.isNotEmpty(xsdh)) {
			sqlWhere += " and xsdh like '%"+xsdh+"%'";
		}
		
		int top = pageBean.getTop();
		String sql = "select top "+top+" * from credit_debit where 1=1 "+sqlWhere+" order by l_date desc";
		return dbManager.queryForList(sql, true);
	}
	

	public int getGatheredCount(Map<String,String> param) throws Exception{
		
		String sqlWhere = "";
		String fpnum = param.get("coname");
		if(StringUtils.isNotEmpty(fpnum)) {
			sqlWhere += " and coname like '%"+fpnum+"%'";
		}
		
		String sql = "select count(*) from gathering  where (states='订单完成' or states='已收全部款')"+sqlWhere;
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public List getGatheredList(PageBean pageBean,Map<String,String> param) throws Exception{
		
		String sqlWhere = "";
		String fpnum = param.get("coname");
		if(StringUtils.isNotEmpty(fpnum)) {
			sqlWhere += " and coname like '%"+fpnum+"%'";
		}
		
		int top = pageBean.getTop();
		String sql = "select top "+top+" * from gathering  where (states='订单完成' or states='已收全部款')"+sqlWhere+" order by id desc";
		return dbManager.queryForList(sql, true);
	}
	
	/**
	 * 查询未冲账列表总数
	 * @param pageNo
	 * @return
	 * @throws Exception 
	 */
	public int getGatheringRefundCount(Map<String,String> params,String sqlWhere,boolean gathered) throws Exception{
		
		String eq = "<>";
		if(gathered){
			eq = "=";
		}
		
		String sql = "select count(*) from gathering_refund  where states"+eq+"'已冲帐'"+sqlWhere;
		return dbManager.getCount(sql);
		
	}
	
	public Map<String,Object> getGatheringRefund(int id) throws Exception{
		
		String sql="select  * from gathering_refund where id=?";
		
		return dbManager.queryForMap(sql, new Object[] {id},true);
		
	}
	
	/**
	 * 查询未冲账列表
	 * @param pageNo
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getGatheringRefund(Map<String,String> params,String sqlWhere,int pageNo,boolean gathered) throws Exception{
		
		String eq = "<>";
		if(gathered){
			eq = "=";
		}
		
		int top = pageNo *20;
		String sql = "select top "+top+" * from gathering_refund  where states"+eq+"'已冲帐' "+sqlWhere+" order by  yjskdate desc";
		return dbManager.queryForList(sql, true);
	}
	
	public double getSumAmount(String ddid){
		String sql = "select num*salejg from  th_pro where  ddid=?";
		return dbManager.getDouble(sql, new Object[]{ddid});
	}
	
	public String getSubNumber(String rNumber){
		String sql = "select sub from th_table where number = ?";
		return dbManager.getString(sql,new Object[]{rNumber});
	}
	
	

}
