package com.tntxia.oa.finance.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.tntxia.dbmanager.DBManager;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 跳转到增加往来帐目凭证的页面
 * 
 * @param request
 * @param arg1
 * @return
 * @throws Exception
 */
public class NeedGatherHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		 

		
		java.lang.String strSQL;
		
		
		PageBean pageBean = runtime.getPageBean(500);

		
			double g_je=0;
		    String tmpsalejg1=runtime.getParam("g_je");//待对帐
		    if(tmpsalejg1!=null)
		    g_je=Double.parseDouble(tmpsalejg1);
		String g_sje=runtime.getParam("g_sje");//已对帐
		String gid=runtime.getParam("id");
		String g_date=runtime.getParam("g_date");
		String coname=runtime.getParam("coname").trim();
		String number = runtime.getParam("number");


		String hb=runtime.getParam("hb").trim();

		strSQL = "select count(*) from gathering where states='待收款' and coname like  '%"+coname+"%' and invoice = '"+number+"'  or  states='预收款'  and coname like  '%"+coname+"%' and invoice = '"+number+"'";

		int intRowCount = dbManager.getCount(strSQL);
		
		pageBean.setTotalAmount(intRowCount);
		
		Transaction trans = this.getTransaction();
		
		strSQL = "select id,fyid,ymoney,bank,smoney,invoice,sub,coname,sjdate,skdate,sale_man,states from gatherview  where states='待收款'  and coname like  '%"+coname+"%' and invoice = '"+number+"'  or  states='预收款'  and coname like  '%"+coname+"%' and invoice = '"+number+"' order by sjdate asc";

		List list = trans.queryForList(strSQL, true);
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			
			String  fyid=(String)map.get("fyid");
		   
		    BigDecimal s=(BigDecimal)map.get("smoney");
	        
			BigDecimal totle=BigDecimal.ZERO;  
		   
			String strSQLpro = "select num,salejg,pricehb from ddpro where  ddid='"+fyid+"'";
		    List proList=trans.queryForList(strSQLpro, true);
	    
			for(int j=0;j<proList.size();j++){ 
				Map pro = (Map)proList.get(j);
				Integer num=(Integer)pro.get("num");
	        
				BigDecimal price= (BigDecimal)pro.get("salejg");
				BigDecimal tprice= BigDecimalUtils.multiply(num, price);
				totle=totle.add(tprice);
			}
			map.put("totle", totle);
			BigDecimal sub1=totle.subtract(s);//剩余金额
			map.put("sub1", sub1);
			
		}
		
		this.setRootValue("list", list);
		this.setRootValue("g_je", g_je);
		this.setRootValue("gid", gid);
		this.setRootValue("g_sje", g_sje);
		this.setRootValue("coname", coname);
		this.setRootValue("g_date", g_date);
		this.setRootValue("hb", hb);
	}

}
