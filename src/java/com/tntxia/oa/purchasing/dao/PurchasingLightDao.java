package com.tntxia.oa.purchasing.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseDao;

public class PurchasingLightDao extends BaseDao{
	
	private DBManager dbManager = this.getDBManager();
	
	public void updatePurchasingStatus2(String status,String status2,String id) throws Exception{
		String sql = "update procure set l_spqk=?,l_spyj=? where id=?";
		dbManager.update(sql, new Object[]{status,status2,id});
	}
	
	public Purchasing getPurchasingById(String id) throws SQLException{
		
		DBConnection db = new DBConnection();
		
		String sql="select * from procure where id='"+id+"'";
		ResultSet rs=db.executeQuery(sql);
		Purchasing res = new Purchasing();
		if(rs.next())
		{
			res.setId(rs.getInt("id"));
			res.setNumber(rs.getString("number"));
			res.setMan(rs.getString(3));
			res.setSaleNumber(rs.getString("sub").trim());
			res.setWarehouse(rs.getString(5));
			res.setSupplierNumber(rs.getString("co_number"));
			res.setSupplier(rs.getString("coname"));
			res.setPurchasePlace(rs.getString("senddate"));
			res.setDeliverDate(rs.getString("pay_if"));
			res.setTransportationExpense(rs.getString("pay_je"));
			res.setPurchaseDate(rs.getString("datetime"));
			res.setPurchaseMoneyType(rs.getString("money"));
			res.setContractItem(rs.getString("tbyq"));
			res.setRemark(rs.getString("remarks"));
			String spqk = rs.getString("l_spqk");
			res.setStatusOrign(spqk);
			if(spqk!=null){
				spqk = spqk.trim();
			}
			if("已入库".equals(spqk) || "全部入库".equals(spqk) ||
					"全部入库".equals(spqk) || "待入库".equals(spqk) ||
					"全部退货".equals(spqk) || "部分退货".equals(spqk)){
				res.setStatus(5);
			}else if("合同已确认".equals(spqk)){
				res.setStatus(4);
			}else if("审批通过".equals(spqk)){
				res.setStatus(3);
			}else if("待审批".equals(spqk)){
				res.setStatus(1);
			}else if("待复审".equals(spqk)){
				res.setStatus(2);
			}else if("草拟".equals(spqk)){
				res.setStatus(0);
			}
			
			res.setFirstApprover(rs.getString("l_spman"));
			if(rs.getString("l_fif").trim().equals("是")){
				res.setToSencondApprove(true);
			}else{
				res.setToSencondApprove(false);
			}
			res.setSecondApprover(rs.getString("l_fspman"));
			if(rs.getString("l_firspif").trim().equals("是")){
				res.setFirstApproved(true);
			}else{
				res.setFirstApproved(false);
			}
			res.setApproveOpinion(rs.getString("l_spyj"));
		    res.setDealPlace(rs.getString("ponum"));
		    res.setContactMan(rs.getString("lxr"));
		    res.setReceiver(rs.getString("receiver"));
		    res.setReceiverTel(rs.getString("receiver_tel"));
		    res.setReceiverAddress(rs.getString("receiver_addr"));
		    res.setFreight(rs.getString("freight"));
		    res.setExpressCompany(rs.getString("express_company"));
		    res.setAccountNo(rs.getString("acct"));
		    res.setServiceType(rs.getString("service_type"));
		    res.setPayway(rs.getString("pay_type"));
		    res.setRate(rs.getString("rate"));
		    res.setTransportationExpenseMoneyType(rs.getString("yfmoney"));
		    res.setExchangePlace(rs.getString("jydd"));
		    if(rs.getInt("self_carry")==0){
		    	res.setSelfPickup(true);
		    }else{
		    	res.setSelfPickup(false);
		    }
		    
		    res.setFirspif(rs.getString("l_firspif"));
		    res.setFirspman(rs.getString("l_firspman"));
		    res.setL_spyj(rs.getString("l_spyj"));
		    
		    
		    
		}
		return res;
	}
	
	/**
	 * 获取采购产品的入库数量
	 * @param purchaseProductId
	 * @return
	 */
	public int getRkNum(int purchaseProductId) throws Exception{
		String sql = "select sum(pro_num) from rkhouse where pro_rate = ? and pro_rk_num not in (select id from in_warehouse where states='已删除')";
		return dbManager.queryForInt(sql, new Object[]{purchaseProductId});
	}
	
	public List<PurchasingProduct> getPurchasingProductListByParentId(String  ddid) throws NumberFormatException, Exception{
		return getPurchasingProductListByParentId(Integer.parseInt(ddid));
	}
	
	@SuppressWarnings({"rawtypes" })
	public List<PurchasingProduct> getPurchasingProductListByParentId(int ddid) throws Exception{
		
		String sql = "select * from cgpro where ddid = ?";
		
		List result = dbManager.queryForList(sql,new Object[]{String.valueOf(ddid)},PurchasingProduct.class);
		for(int i=0;i<result.size();i++){
			PurchasingProduct product = (PurchasingProduct) result.get(i);
			int purchaseProductId = product.getId();
			product.setCgpro_num(this.getRkNum(purchaseProductId));
		}
		return result;
	}
	
	/**
	 * 获取采购产品的入库数量
	 * @param purchaseProductId
	 * @return
	 */
	public int getRkNum(Transaction trans,int purchaseProductId) throws Exception {
		String sql = "select sum(pro_num) from rkhouse where pro_rate = ? and pro_rk_num not in (select id from in_warehouse where states='已删除')";
		return trans.queryForInt(sql, new Object[]{purchaseProductId});
	}
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<PurchasingProduct> getProductByPurchasingId(String purchasingId) throws Exception{
		
		String sql="select id,epro,cpro,pro_number,num,cgpro_num,unit,money,selljg,rate,supplier,cgpro_ydatetime,remark,sale_rate,sale_finance from cgpro where ddid=? order by id asc";
		List<Object> params = new ArrayList<Object>();
		params.add(purchasingId);
		
		List list = dbManager.queryForList(sql, new Object[]{purchasingId}, true);
		
		List<PurchasingProduct> res = new ArrayList<PurchasingProduct>();
		for( int i=0;i<list.size();i++)
		{
			PurchasingProduct pro = new PurchasingProduct();
			Map map = (Map) list.get(i);
			pro.setId((Integer)map.get("id"));
			pro.setEpro((String)map.get("epro"));
			pro.setCpro((String)map.get("cpro"));
			pro.setSupplier((String)map.get("supplier"));
			pro.setPro_number((String)map.get("pro_number"));
			pro.setNum((Integer)map.get("num"));
			pro.setCgpro_num((Integer)map.get("cgpro_num"));
			pro.setUnit((String)map.get("unit"));
			pro.setPurchasingPrice((BigDecimal)map.get("selljg"));
			pro.setRate((Integer)map.get("rate"));
			pro.setDeliverDate((String)map.get("cgpro_ydatetime"));
			pro.setTradeMark((String)map.get("supplier"));
			pro.setRemark((String)map.get("remark"));
			pro.setSelljg((BigDecimal)map.get("selljg"));
			res.add(pro);
		    
		}
		
		return res;
	}
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<PurchasingProduct> getProductByPurchasingId(Transaction trans,String purchasingId) throws Exception{
		
		
		String sql="select id,epro,cpro,pro_number,num,cgpro_num,unit,money,selljg,rate,supplier,cgpro_ydatetime,remark,sale_rate,sale_finance from cgpro where ddid=? order by id asc";
		List list = trans.queryForList(sql, new Object[]{purchasingId},true);
		List<PurchasingProduct> res = new ArrayList<PurchasingProduct>();
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			PurchasingProduct pro = new PurchasingProduct();
			Integer id = (Integer) map.get("id");
			pro.setId(id);
			String epro = (String)map.get("epro");
			pro.setEpro(epro);
			String cpro = (String)map.get("cpro");
			pro.setCpro(cpro);
			String supplier = (String) map.get("supplier");
			pro.setSupplier(supplier);
			String pro_number = (String) map.get("pro_number");
			pro.setPro_number(pro_number);
			Integer num = (Integer) map.get("num");
			pro.setNum(num);
			String unit = (String) map.get("unit");
			pro.setUnit(unit);
			Integer rate = (Integer)map.get("rate");
			pro.setRate(rate);
			String deliveryDate = (String)map.get("cgpro_ydatetime");
			pro.setDeliverDate(deliveryDate);
			pro.setTradeMark(supplier);
			String remark = (String) map.get("remark");
			
			pro.setRemark(remark);
			BigDecimal selljg = (BigDecimal) map.get("selljg");
			pro.setSelljg(selljg);
			res.add(pro);
		}
		
		return res;
	}
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 */
	public List<PurchasingProduct> getProductByPurchasingId(int purchasingId) throws Exception{
		
		return this.getProductByPurchasingId(String.valueOf(purchasingId));
	}
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 */
	public List<PurchasingProduct> getProductByPurchasingId(Transaction trans,int purchasingId) throws Exception{
		
		return this.getProductByPurchasingId(trans,String.valueOf(purchasingId));
	}
	
	public void updateRkNum(String id,String rknum) throws Exception{
		
		String sqlUpdateRkhouse = "update rkhouse set pro_num = ? where id = ?";
		dbManager.update(sqlUpdateRkhouse, new Object[]{rknum,id});
		
	}
	
	public void updateProRkNum(String proId,String rknum) throws Exception{
		String sql = "update cgpro set cgpro_num = ? where id = ?";
		dbManager.update(sql, new Object[]{rknum,proId});
	}
	

	public int getPurchasingIdByNumber(Transaction trans,String number) throws Exception{
		return trans.queryForInt("select id from procure where number = ?", new Object[]{number});
	}
	
	public int getProductNum(int id) throws Exception{
		String sql = "select num from cgpro where id = ?";
		return dbManager.queryForInt(sql,new Object[]{id});
	}
	
	public int getProductNum(Transaction trans,int id) throws Exception{
		String sql = "select num from cgpro where id = ?";
		return trans.queryForInt(sql,new Object[]{id});
	}
	

	public void updateStatus(int id,String status) throws Exception{
		String sql = "update procure set l_spqk = ? where id = ?";
		dbManager.update(sql, new Object[]{status,id});
	}
	
	public void updateStatus(String id,String status) throws NumberFormatException, Exception{
		this.updateStatus(Integer.parseInt(id), status);
	}

}
