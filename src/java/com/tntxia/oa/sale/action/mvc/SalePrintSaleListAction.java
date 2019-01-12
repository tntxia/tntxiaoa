package com.tntxia.oa.sale.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.currency.Rmb;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class SalePrintSaleListAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String cid=runtime.getParam("cid");
		 String strSQLq="select companyname,companyaddr,companytel,companyfax,companybank,companynumber,companyemail,companynet,picpath from company where id='"+cid+"' ";
		 Map<String,Object> company = dbManager.queryForMap(strSQLq, true);
		 this.putAllRootValue(company);

		String id=runtime.getParam("id");
		
		this.setRootValue("cid", cid);
		this.setRootValue("id", id);
		String sql="select  id,number,man,subck,custno,coname,senddate,yj,money,item,mode,source,trade,fy_number,other_fy,datetime,tr,send_date,yf_types,yf_money,tr_addr,tr_man,tr_tel,tbyq,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,ware_remark from subscribe where id='"+id+"'";
		Map<String,Object> order = dbManager.queryForMap(sql, true);
		this.putAllRootValue(order);
		  Integer ddid1=(Integer)order.get("id");
		 
		
		String strSQL = "select count(*) from ddpro where ddid ='"+ddid1+"'";
		
		int intRowCount = dbManager.getCount(strSQL);
		
		PageBean pageBean = runtime.getPageBean(20);
		
		pageBean.setTotalAmount(intRowCount);
		
		String  strSQLpro = "select " +pageBean.getTop()+" ddid,epro,mpn,supplier,num,unit,cpro,salejg,remark from ddpro where ddid ='"+ddid1+"'";
		List list = dbManager.queryForList(strSQLpro, true);
		
		List rows = this.getRows(list, pageBean);
		
		this.setRootValue("rows", rows);
		
		String sqlTotal = "select sum(salejg*num) as saletl from ddpro where ddid =?";
		
		BigDecimal saletl = dbManager.queryForBigDecimal(sqlTotal, new Object[]{ddid1});
		
		this.setRootValue("saletl", saletl);
		
		this.setRootValue("saletlBig", Rmb.lowerToUpper(saletl));
		this.setRootValue("page", pageBean.getPage());
		this.setRootValue("totalPage", pageBean.getTotalPage());

	}

}
