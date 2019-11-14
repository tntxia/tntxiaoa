package com.tntxia.oa.purchasing.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.jdbc.SQLExecutorInterface;
import com.tntxia.oa.common.service.CommonService;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.web.util.DatasourceStore;

public class PurchasingLightService extends CommonService{
	
	private DBManager dbManager = new DBManager(DatasourceStore.getDatasource("default"));
	
	@SuppressWarnings("rawtypes")
	public List getPurchasingProductList(SQLExecutorInterface sqlExecutor,String orderform) throws Exception{
		String sql = "select id,epro,num,cgpro_num,unit,money,selljg,cgpro_sdatetime from cgpro where ddid=?";
		return sqlExecutor.queryForList(sql, new Object[]{orderform},true);
	}
	
	public Purchasing getPurchasingById(String id) throws Exception{
		
		String sql="select * from procure where id='"+id+"'";
		Map<String,Object> rs = dbManager.queryForMap(sql, true);
		Purchasing res = new Purchasing();
		if(rs!=null)
		{
			res.setId((Integer)rs.get("id"));
			res.setNumber((String)rs.get("number"));
			res.setMoney((String)rs.get("money"));
			res.setMan((String)rs.get("man"));
			String sub = (String)rs.get("sub");
			res.setSaleNumber(sub);
			res.setWarehouse((String)rs.get(""));
			res.setSupplierNumber((String)rs.get("co_number"));
			res.setSupplier((String)rs.get("coname"));
			res.setPurchasePlace((String)rs.get("senddate"));
			res.setDeliverDate((String)rs.get("pay_if"));
			res.setTransportationExpense(((BigDecimal)rs.get("pay_je")).toString());
			res.setPurchaseDate((String)rs.get("datetime"));
			res.setPurchaseMoneyType((String)rs.get("money"));
			res.setContractItem((String)rs.get("tbyq"));
			res.setRemark((String)rs.get("remarks"));
			String spqk = (String)rs.get("l_spqk");
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
			
			res.setFirstApprover((String)rs.get("l_spman"));
			String l_fif = (String)rs.get("l_fif");
			if(l_fif.trim().equals("是")){
				res.setToSencondApprove(true);
			}else{
				res.setToSencondApprove(false);
			}
			res.setSecondApprover((String)rs.get("l_fspman"));
			String l_firspif = (String)rs.get("l_firspif");
			if(l_firspif.trim().equals("是")){
				res.setFirstApproved(true);
			}else{
				res.setFirstApproved(false);
			}
			res.setApproveOpinion((String)rs.get("l_spyj"));
		    res.setDealPlace((String)rs.get("ponum"));
		    res.setContactMan((String)rs.get("lxr"));
		    res.setReceiver((String)rs.get("receiver"));
		    res.setReceiverTel((String)rs.get("receiver_tel"));
		    res.setReceiverAddress((String)rs.get("receiver_addr"));
		    res.setFreight((String)rs.get("freight"));
		    res.setExpressCompany((String)rs.get("express_company"));
		    res.setAccountNo((String)rs.get("acct"));
		    res.setServiceType((String)rs.get("service_type"));
		    res.setPayway((String)rs.get("pay_type"));
		    res.setRate((String)rs.get("rate"));
		    res.setTransportationExpenseMoneyType((String)rs.get("yfmoney"));
		    res.setExchangePlace((String)rs.get("jydd"));
		    Integer self_carry = (Integer)rs.get("self_carry");
		    if(self_carry==0){
		    	res.setSelfPickup(true);
		    }else{
		    	res.setSelfPickup(false);
		    }
		    
		    res.setFirspif((String)rs.get("l_firspif"));
		    res.setFirspman((String)rs.get("l_firspman"));
		    res.setL_spyj((String)rs.get("l_spyj"));
		    
		    
		    
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

}
