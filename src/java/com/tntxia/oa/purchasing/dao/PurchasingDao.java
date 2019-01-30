package com.tntxia.oa.purchasing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.dbmanager.rowmapper.BeanRowMapper;
import com.tntxia.oa.purchasing.entity.Contact;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.oa.purchasing.entity.PurchasingRefundProduct;
import com.tntxia.oa.supplier.Supplier;

public class PurchasingDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private DBManager dbManager;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PurchasingProduct> getPurchasingProductListByNumber(String number){
		String sql = "select * from procure where number = ?";
		Map purchasing = jdbcTemplate.queryForMap(sql, new String[]{number});
		int id = (Integer)purchasing.get("id");
		
		sql = "select * from cgpro where ddid = ?";
		
		List<Map> purchasingProductList = jdbcTemplate.queryForList(sql,new Object[]{id});
		List<PurchasingProduct> result = new ArrayList<PurchasingProduct>();
		
		for(Map item : purchasingProductList){
			PurchasingProduct product = new PurchasingProduct();
			product.setEpro((String)item.get("epro"));
			product.setNum((Integer)item.get("num"));
			product.setSupplier((String) item.get("supplier"));
			
			result.add(product);
		}
		
		
		return result;
	}
	
	
	
	/**
	 * 通过采购订单号和型号，获取采购产品的信息
	 * @param number
	 * @param model
	 * @return
	 */
	public PurchasingProduct getPurchasingProductListByNumberAndModel(String number,String model){
		List<PurchasingProduct> purchasingProductList = getPurchasingProductListByNumber(number);
		PurchasingProduct result = null;
		for(PurchasingProduct product : purchasingProductList){
			if(product.getEpro().equals(model)){
				result = product;
				break;
			}
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public PurchasingRefundProduct getPurchasingRefundProductById(int id){
		String sql = "select * from th_pro_supplier where id = ?";
		Map purchasingRefundProduct = jdbcTemplate.queryForMap(sql, new Object[]{id});
		PurchasingRefundProduct result = new PurchasingRefundProduct();
		result.setEpro((String)purchasingRefundProduct.get("epro"));
		result.setNum((Integer)purchasingRefundProduct.get("num"));
		result.setPurchasingNumber((String)purchasingRefundProduct.get("cg_number"));
		return result;
	}
	
	public void updatePurchasingStatus(String status,String number){
		String sql = "update procure set l_spqk = ? where number = ?";
		jdbcTemplate.update(sql, new Object[]{status,number});
	}
	
	
	
	public void addPrintLog(String username,String number){
		String sql = "insert into print_log(operator,number,created_time) values(?,?,getdate())";
		jdbcTemplate.update(sql, new Object[]{username,number});
	}
	
	public Supplier getSupplier(String name) throws Exception{
		//通过供应商名称获取供应商的一些信息
		String sql="select cojsfs,coaddr,cotel,cofax,co_number from supplier where coname=?";
		Supplier res = (Supplier) dbManager.queryForObject(sql, new Object[]{name}, Supplier.class);
		if(res==null){
			return null;
		}
		
		sql = "select avg(rfq) from pf_supplier where co_number = '"+res.getCo_number()+"'";
		Double rfq = (Double)jdbcTemplate.queryForObject(sql, Double.class);
		if(rfq!=null)
			res.setRfq(rfq);
		
		sql = "select avg(bj) from pf_supplier where co_number = '"+res.getCo_number()+"'";
		Double bj = (Double)jdbcTemplate.queryForObject(sql, Double.class);
		if(bj!=null)
		res.setBj(bj);
		
		sql = "select avg(ch) from pf_supplier where co_number = '"+res.getCo_number()+"'";
		Double ch = (Double)jdbcTemplate.queryForObject(sql, Double.class);
		if(ch!=null)
		res.setCh(ch);
		
		sql = "select avg(fh) from pf_supplier where co_number = '"+res.getCo_number()+"'";
		
		Double fh =  (Double)jdbcTemplate.queryForObject(sql, Double.class);
		if(fh!=null)
		res.setFh(fh);
		
		sql = "select avg(th) from pf_supplier where co_number = '"+res.getCo_number()+"'";
		
		Double th = (Double)jdbcTemplate.queryForObject(sql, Double.class);
		if(th!=null)
		res.setTh(th);
		
		
		return res;
	}
	
	public Supplier getSupplierByNumber(String number){
		//通过供应商名称获取供应商的一些信息
		String sql="select coname,coaddr,cojsfs,cotel,co_number from supplier where co_number='"+number+"'";
		Supplier res = (Supplier) jdbcTemplate.queryForObject(sql, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Supplier res = new Supplier();
				res.setCo_name(rs.getString("coname"));
				res.setCoaddr(rs.getString("coaddr"));
				res.setCojsfs(rs.getString("cojsfs"));
				res.setColtel(rs.getString("cotel"));
				res.setCo_number(rs.getString("co_number"));
				return res;
			}
		});
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public String getFinanceInfo(String orderNo){
		
		String sql = "select states from payment where orderform='"+orderNo+"'";
		List list = jdbcTemplate.queryForList(sql);
		
		String states = "";
		if(list!=null && list.size()>0){
			Map map = (Map)list.get(0);
			states = (String)map.get(1);
		}
		return states;
		
	}
	
	public int getPurchasingProductNum(String parentId){
		String sql = "select count(*) from cgpro where  ddid='"+parentId+"' ";
		return jdbcTemplate.queryForInt(sql);
	}
	
	
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 * @throws SQLException
	 */
	public PurchasingProduct getProductByNumber(String number) throws Exception{
		
		String sql = "select * from cgpro where id = ?";
		
		PurchasingProduct product = (PurchasingProduct) dbManager.queryForObject(sql, new Object[]{number}, new BeanRowMapper(PurchasingProduct.class));
		
		return product;
	}
	
	
	
	/**
	 * 
	 * 获取采购产品
	 * 
	 * @param purchasingId
	 * @return
	 * @throws Exception 
	 * @throws SQLException
	 */
	public PurchasingProduct getProductById(String id) throws Exception{
		
		String sql = "select * from cgpro where id = ?";
		
		PurchasingProduct product = (PurchasingProduct) dbManager.queryForObject(sql, new Object[]{id}, new BeanRowMapper(PurchasingProduct.class));
		
		return product;
	}
	
	@SuppressWarnings("rawtypes")
	public List getContactList(String number) throws Exception{
		String sql = "select nameid id , name from qlinkman where co_number = ?";
		return dbManager.queryForList(sql, new Object[]{number}, new BeanRowMapper(Contact.class));
		
	}
	
	public int getProNum(int id) throws Exception{
		String sql = "select sum(num) from cgpro where ddid=?";
		return dbManager.getCount(sql,new Object[]{id});
	}

}
