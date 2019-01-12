package com.tntxia.oa.finance.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.date.DateUtil;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.finance.service.FinanceLightService;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 付款管理
 * 
 * @author tntxia
 * 
 */
public class FinancePaymentAction extends CommonDoAction {
	
	private FinanceLightService financeService = new FinanceLightService();

	public Map<String, Object> addCredit(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {


		int max = 0;

			String sy = ParamUtils.unescape(request, "sy");
			String l_topic = ParamUtils.unescape(request, "l_topic");
			Userinfo userinfo = this.getUserinfo(request);
			String l_man = userinfo.getUsername();
			String l_date = ParamUtils.unescape(request, "l_date");
			String xsdh = ParamUtils.unescape(request, "xsdh");
			String l_sqje = ParamUtils.unescape(request, "l_sqje");
			if (l_sqje != null) {
				l_sqje = l_sqje.trim();
			} else {
				l_sqje = "0";
			}
			l_sqje = l_sqje.replaceAll(",", "");

			String l_coname = ParamUtils.unescape(request, "l_coname").trim();
			String co_number = ParamUtils.unescape(request, "co_number");
			String cgdh = ParamUtils.unescape(request, "cgdh");

			String l_dept = userinfo.getDept();
			String deptjb = userinfo.getDeptjb();
			String l_hb = ParamUtils.unescape(request, "l_hb");
			String c_d = ParamUtils.unescape(request, "c_d");
			String reference = ParamUtils.unescape(request, "reference");
			String compendium = ParamUtils.unescape(request, "compendium");

			compendium = com.infoally.util.Replace.strReplace(compendium, "'",
					"''");
			String g_bank = ParamUtils.unescape(request, "g_bank");
			String g_banknum = ParamUtils.unescape(request, "g_banknum");
			String remarks = ParamUtils.unescape(request, "remarks");
			remarks = com.infoally.util.Replace.strReplace(remarks, "'", "''");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("l_man", l_man);
			params.put("reference", reference);
			params.put("accountId", Integer.valueOf(l_topic));
			params.put("l_dept", l_dept);
			params.put("deptjb", deptjb);
			params.put("c_d", c_d);
			params.put("l_date", l_date);
			params.put("l_coname", l_coname);
			
			params.put("co_number", co_number);
			params.put("l_sqje",new BigDecimal(l_sqje));
			params.put("l_hb", l_hb);
			params.put("compendium", compendium);
			params.put("g_bank", g_bank);
			params.put("g_banknum", g_banknum);
			params.put("remarks", remarks);
			
			params.put("sy", sy);
			params.put("xsdh", xsdh);
			params.put("cgdh", cgdh);
			
			max = financeService.addCredit(params);
			
		return this.success("max", max);
	}

	private int getFlushListCount(SQLExecutor db,
			String purchasingNumber) throws Exception {
		String sql = "select count(*) from payment where  contract=?  and  states='待付款'";
		return db.queryForInt(sql, new Object[] { purchasingNumber });
	}

	@SuppressWarnings("rawtypes")
	private List getFlushList(SQLExecutor db, String number,
			PageBean pageBean) throws Exception {
		int top = pageBean.getTop();
		String sql = "select top "
				+ top
				+ " id,orderform,contract,ponum,supplier,moneyty,htmoney,yjfkdate,paynr from payview  where contract=?   and  states='待付款'  order by yjfkdate asc";
		List list = db.queryForList(sql, new Object[] { number }, true);
		return list;
	}

	/**
	 * 获取冲帐列表
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getFlushList(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		SQLExecutorSingleConn db = this.getSQLExecutorSingleConn();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		PageBean pageBean = this.getPageBean(request);

		String number = ParamUtils.unescape(request, "number");

		int totalAmount = this.getFlushListCount(db, number);

		List list = this.getFlushList(db, number, pageBean);

		return this.getPagingResult(list, pageBean, totalAmount);

	}
	
	@SuppressWarnings("rawtypes")
	private List getProList(SQLExecutor db, String id) throws Exception{
		String sql = "select  num,selljg,money from cgpro where  ddid='"
				+ id + "'";
		return db.queryForList(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	private BigDecimal getTotalPrice(List proList){
		BigDecimal totle = BigDecimal.ZERO;
		for (int i=0;i<proList.size();i++) {
			Map map = (Map)proList.get(i);
			int num = (Integer)map.get("num");
			BigDecimal price = (BigDecimal)map.get("selljg");
			
			BigDecimal j_m = price.multiply(BigDecimal.valueOf(num));
			totle = totle.add(j_m) ;
			
		}
		return totle;
	}

	/**
	 * 付款冲帐
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> flush(WebRuntime runtime) throws SQLException {
		
		SQLExecutorSingleConn db = this.getSQLExecutorSingleConn();
		
		try{
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			nf.setMinimumFractionDigits(4);

			String skdate = DateUtil.getCurrentDateSimpleStr();

			String name1 = this.getUsername(runtime);
			String gid = runtime.getParam("id");        // 往来账目的ID
			BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(runtime.getParam("amount"))); // 对账金额
			
			Map<String,Object> detail = financeService.getCreditDebit(gid);
			
			String coname = (String) detail.get("l_coname");
			Date g_date = (Date) detail.get("l_date");
			String hb = (String) detail.get("l_hb");
			
			String orderNumber = (String) detail.get("cgdh");
			
			Map<String,Object> payment = financeService.getPaymentByOrderNumber(orderNumber);
			if(payment==null || payment.size()==0){
				return this.errorMsg("你选择的付款信息不存在！");
			}
			
			Integer paymentId = (Integer) payment.get("id");
			String invoice = (String)payment.get("contract");
			String fyid = (String)payment.get("orderform");
			BigDecimal smoney = (BigDecimal)payment.get("htmoney");// 已收款金额
			
			List proList = this.getProList(db, fyid);
			BigDecimal totle = this.getTotalPrice(proList);
			
			if(smoney.add(amount).compareTo(totle)>0) {
				return this.errorMsg("需要对账的金额加上已对账的金额大于总金额！");
			}
			
			String strSQLmx = "insert into gather_mx_mx(" +
					"g_m_id,g_m_types,g_m_number,g_m_coname,g_m_smoney," +
					"g_m_hb,g_m_salesman,g_m_date,g_m_man) values('"
					+ gid
					+ "','2','"
					+ invoice
					+ "','"
					+ coname
					+ "',?,'"
					+ hb
					+ "','','"
					+ g_date
					+ "','"
					+ name1 + "')";

			db.update(strSQLmx,new Object[] {amount});
			
			if (smoney.add(amount).compareTo(totle)==0) {
				String strpros = "update payment set  htmoney=?,states='已付全款',sjfkdate='" + skdate
						+ "'  where id=?";
				db.update(strpros,new Object[] {totle,paymentId});
			} else {
				String strpros = "update payment set  htmoney= htmoney + ?  where id=?";
				db.update(strpros,new Object[] {amount,paymentId});
			}
		
			String strpross = "update credit_debit set  l_sje = l_sje + ?  where id= ?";
			db.update(strpross,new Object[] {amount,gid});
		}catch(Exception ex){
			return this.errorMsg(ex.toString());
		}finally{
			if(db!=null){
				db.close();
			}
		}
		
		return this.success();

	}

}
