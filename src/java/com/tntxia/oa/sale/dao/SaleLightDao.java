package com.tntxia.oa.sale.dao;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.sale.entity.SaleFinanceInfo;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseDao;

public class SaleLightDao extends BaseDao{

	private DBManager dbManager = this.getDBManager();


	public Sale getSaleById(String id) throws Exception{
		Sale sale = (Sale) dbManager.queryForObject("select * from subscribe where id = ?", new Object[]{Integer.parseInt(id)}, Sale.class);
		return sale;
	}
	
	public Sale getSaleById(Transaction trans,String id) throws Exception{
		Sale sale = (Sale) trans.queryForObject("select * from subscribe where id = ?", new Object[]{Integer.parseInt(id)}, Sale.class);
		return sale;
	}
	
	
	public SalePro getSaleProById(int id) throws Exception{
		SalePro salePro = (SalePro) dbManager.queryForObject("select * from ddpro where id=?", new Object[]{id}, SalePro.class);
		return salePro;
	}
	
	public Map<String,Object> getSaleProById(Transaction trans,Integer id) throws Exception{
		return trans.queryForMap("select * from ddpro where id=?", new Object[]{id}, true);
		
	}
	

	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(String saleId) throws NumberFormatException, Exception {
		return getSaleProList(Integer.parseInt(saleId));
	}
	
	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(String saleId,SQLExecutor executor) throws NumberFormatException, Exception {
		return getSaleProList(Integer.parseInt(saleId),executor);
	}
	
	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(SQLExecutor trans,String saleId) throws NumberFormatException, Exception {
		return getSaleProList(trans,Integer.parseInt(saleId));
	}
	
	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(int saleId,SQLExecutor executor) throws Exception {

		String sql = "select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc";

		return executor.queryForList(sql, new Object[] { saleId },true);

	}
	
	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(int saleId) throws Exception {

		String sql = "select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc";

		return dbManager.queryForList(sql, new Object[] { saleId },true);

	}

	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getSaleProList(SQLExecutor trans,int saleId) throws Exception {

		String sql = "select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc";

		return trans.queryForList(sql, new Object[] { saleId },true);

	}

	/**
	 * 获取销售合同的总金额
	 * 
	 * @param ddid
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public double getTotalPrice(int ddid) throws Exception {

		BigDecimal totle = BigDecimal.ZERO;
		BigDecimal stotle = BigDecimal.ZERO;
		String sql = "select num,s_num,salejg,pricehb from ddpro where  ddid='"
				+ ddid + "'";
		
		List rows = dbManager.queryForList(sql, true);

		Iterator it = rows.iterator();

		try {
			while (it.hasNext()) {
				Map map = (Map) it.next();
				int num = (Integer) map.get("num");
				BigDecimal price = (BigDecimal) map.get("salejg");
				BigDecimal tprice = price.multiply(new BigDecimal(num));
				totle = totle.add(tprice);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return stotle.doubleValue();
	}

	@SuppressWarnings("rawtypes")
	public SaleFinanceInfo getSaleFinanceInfo(String number) throws Exception {

		String sql = "select  smoney,i_man,note from gathering where orderform='"
				+ number + "'";

		List rows = dbManager.queryForList(sql,true);

		Iterator it = rows.iterator();

		SaleFinanceInfo res = new SaleFinanceInfo();

		while (it.hasNext()) {
			Map map = (Map) it.next();
			BigDecimal sk = (BigDecimal) map.get("smoney");
			BigDecimal fp = (BigDecimal) map.get("i_man");
			String note = (String) map.get("note");
			res.setSmoney(sk);
			res.setiMan(fp);
			res.setNote(StringUtils.trimToEmpty(note));
		}

		return res;

	}
	
	public BigDecimal getRefundPrice(int id) throws Exception {

		String sql = "select salejg*num from th_pro where subscribe_id=? and ddid in (select id from th_table where state='已入库' )";
		return dbManager.queryForBigDecimal(sql,new Object[]{id});

	}
	
	@SuppressWarnings("rawtypes")
	public BigDecimal getRefundPrice(int id,SQLExecutor executor) throws Exception {

		String sql = "select salejg*num as total from th_pro where subscribe_id=? and ddid in (select id from th_table where state='已入库' )";
		Map map = executor.queryForMap(sql, new Object[]{id},true);
		if(map==null){
			return BigDecimal.ZERO;
		}else{
			return (BigDecimal) map.get("total");
		}
		

	}
	
	public BigDecimal getRefundPrice(SQLExecutor trans,int id) throws Exception {

		String sql = "select salejg*num from th_pro where subscribe_id=? and ddid in (select id from th_table where state='已入库' )";
		return trans.queryForBigDecimal(sql,new Object[]{id});

	}
	
	public BigDecimal getRefundPrice(String id) throws Exception {
		
		return this.getRefundPrice(Integer.valueOf(id));

	}
	
	public BigDecimal getRefundPrice(String id,SQLExecutor executor) throws Exception {
		
		return this.getRefundPrice(Integer.valueOf(id),executor);

	}
	
	public BigDecimal getRefundPrice(SQLExecutor trans,String id) throws Exception {
		
		return this.getRefundPrice(trans,Integer.valueOf(id));

	}
	@SuppressWarnings("rawtypes")
	public List getDhList() throws Exception{
		String sql = "select id,state,number,coname,sub,money,delivery_terms,man,datetime from sample_dh";
		return dbManager.queryForList(sql, true);
		
	}
	
	public Map<String,Object> getSale(String id) throws Exception{
		String sql = "select * from subscribe where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}

}
