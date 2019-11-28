package com.tntxia.oa.purchasing.action;

import infocrmdb.DealString;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tntxia.common.date.DateUtil;
import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.finance.dao.FinanceLightDao;
import com.tntxia.oa.mail.SendMail;
import com.tntxia.oa.model.SalePro;
import com.tntxia.oa.procure.CgPro;
import com.tntxia.oa.procure.Procure;
import com.tntxia.oa.procure.ProcureManager;
import com.tntxia.oa.purchasing.dao.PurchasingLightDao;
import com.tntxia.oa.purchasing.dao.TrademarkDao;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingAuditLog;
import com.tntxia.oa.purchasing.form.AuditForm;
import com.tntxia.oa.sale.SaleManager;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.string.EscapeUnescape;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.mvc.annotation.Session;

public class PurchasingDoAction extends CommonDoAction {

	private static Logger logger = Logger.getLogger(PurchasingDoAction.class);

	private DBManager dbManager = this.getDBManager();
	

	private TrademarkDao trademarkDao = new TrademarkDao();
	
	private PurchasingLightDao purchasingDao = new PurchasingLightDao();
	
	private FinanceLightDao financeDao = new FinanceLightDao();

	private Map<String, Object> getPurchasing(SQLExecutor sqlExecutor, String id) throws Exception {
		String sql = "select * from procure where id =?";
		return sqlExecutor.queryForMap(sql, new Object[] { id }, true);
	}

	/**
	 * 创建采购订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> create(WebRuntime runtime, @Session("username") String username) throws Exception {

		String keyType = "T";
		String jump = runtime.getParam("jump");
		if (jump == null) {
			jump = "";
		}

		String number = NumberFactory.generateNumber(keyType);
		String coname1 = runtime.getParam("coname").trim();

		String co_number = runtime.getParam("co_number");
		String sub1 = runtime.getParam("sub");
		String subck = runtime.getParam("subck");

		String pay_if = runtime.getParam("pay_if");
		String pay_je = runtime.getParam("pay_je");
		String tbyq1 = runtime.getParam("tbyq");
		String ponum = runtime.getParam("ponum");
		String lxr = runtime.getParam("lxr");
		String coaddr1 = runtime.getParam("supplier_addr");
		String cotel1 = runtime.getParam("tel");
		String cofax1 = runtime.getParam("fax");
		String receiver = runtime.getParam("receiver");
		String receiver_tel = runtime.getParam("receiver_tel");
		String receiver_addr = runtime.getParam("receiver_addr");
		String freight = runtime.getParam("freight");
		String express_company = runtime.getParam("express_company");
		String acct = runtime.getParam("acct");
		String service_type = runtime.getParam("service_type");
		String pay_type = runtime.getParam("payment");
		String rate = runtime.getParam("rate");
		String self_carry = runtime.getParam("self_carry");
		String yfmoney = runtime.getParam("yfmoney");
		String datetime1 = DateUtil.getCurrentDateSimpleStr();
		String money = runtime.getParam("money");

		String remarks1 = runtime.getParam("remarks");

		System.out.println(self_carry);
		if (self_carry != null) {
			self_carry = "1";
		} else {
			self_carry = "0";
		}

		Transaction trans = this.getTransaction();

		try {
			String l_spman = runtime.getParam("spman");

			HttpSession session = runtime.getSession();

			session.setAttribute("cgnumber_message", "");
			String dept = (String) session.getAttribute("dept");
			String deptjb = (String) session.getAttribute("deptjb");
			String role = (String) session.getAttribute("role");
			String sqlddman = "select  * from cgsp where dept=? and role=?";
			System.out.print(sqlddman + " " + dept + " " + role);
			Map<String, Object> sp = trans.queryForMap(sqlddman, new Object[] { dept, role }, true);

			if (sp == null) {
				return this.errorMsg("未定义采购审批流程!");
			}

			String fif = (String) sp.get("fif");
			String fspman = (String) sp.get("fspman");
			String strSQL = "insert into procure(number,man,sub,subck,co_number,coname,pay_if,pay_je,datetime,money,tbyq,remarks,l_spqk,l_spman,l_fif,l_fspman,l_firspif,l_firspman,l_spyj,l_dept,l_deptjb,ponum,lxr,receiver,receiver_tel,receiver_addr,freight,express_company,acct,service_type,pay_type,coaddr,cotel,cofax,rate,self_carry,yfmoney) "
					+ "values(?,?,?,?,?,?,?,?,getdate(),'CNY',?,?,'草拟','" + l_spman + "','" + fif + "','" + fspman
					+ "','否','','','" + dept + "','" + deptjb + "','" + ponum + "','" + lxr + "','" + receiver + "','"
					+ receiver_tel + "','" + receiver_addr + "','" + freight + "','" + express_company + "','" + acct
					+ "','" + service_type + "','" + pay_type + "','" + coaddr1 + "','" + cotel1 + "','" + cofax1
					+ "','" + rate + "'," + self_carry + ",'" + yfmoney + "')";

			trans.update(strSQL, new Object[] {number, username, sub1, subck,co_number, coname1, pay_if, pay_je, tbyq1,remarks1 });

			String sql = "select id  from procure where  number='" + number + "'";

			Integer ddid = trans.queryForInt(sql);

			if (ddid == null || ddid == 0) {
				throw new Exception("订单没有正常生成，订单ID不存在！");
			}

			String strSQLp = "insert into payment(contract,orderform,sup_number,supplier,pay_je,yjfkdate,sjfkdate,moneyty,moneytypes,htmoney,bank,bankaccounts,paynr,note,states,remark,wtfk) values('T"
					+ number + "','" + ddid + "','" + co_number + "','" + coname1 + "','" + pay_je + "','" + datetime1
					+ "','" + datetime1 + "','" + money + "','银行转帐','0','','0','','','草拟','" + username + "','" + deptjb
					+ "')";

			trans.update(strSQLp);

			String sqlsp = "select * from gathering_customer where coname=?";
			Map<String, Object> rssp = trans.queryForMap(sqlsp, new Object[] { coname1 },true);
			if (rssp != null) {

				String sqlpp = "select  id,co_number,coname,coaddr,cotel,cofax,codzyj,conet,copost,city,country,province,cofrdb,cozzxs,cozczb,coyyzz,cotypes,tradetypes,cokhjb,cosyhb,cojsfs,nshm,number,yearearning,cokhyh,coyhzh,coclrq,ifjckq,username,dept,modman,mod_date,share,valueco,rg_date,annual_sales,sales_ability,qlty_control,companymt,scale,warehouse,describee from supplier where coname=?";
				String coaddr = "";
				String cotel = "";
				String cofax = "";
				String cokhyh = "";
				String coyhzh = "";
				Map<String, Object> rsq = trans.queryForMap(sqlpp, new Object[] { coname1 },true);
				if (rsq != null) {
					coaddr = (String) rsq.get("coaddr");
					cotel = (String) rsq.get("cotel");
					cofax = (String) rsq.get("cofax");
					cokhyh = (String) rsq.get("cokhyh");
					coyhzh = (String) rsq.get("coyhzh");
				}

				String strSQLp1 = "insert into gathering_customer(coname,coaddr,cofrdb,cotel,cofax,bank,payment_je,payment_sje,money,remark,payment_date,co_number,dept,deptjb,pname) values('"
						+ coname1 + "','" + coaddr + "','','" + cotel + "','" + cofax + "','" + cokhyh + ":" + coyhzh
						+ "','0','0','" + money + "','','" + datetime1 + "','" + co_number + "','" + dept + "','"
						+ deptjb + "','" + username + "')";
				trans.executeUpdate(strSQLp1);

			}

			Map<String, Object> successMap = this.success();
			successMap.put("ddid", ddid);
			trans.commit();
			return successMap;

		} catch (Exception ex) {
			logger.error(ex);
			return this.errorMsg(ex.toString());

		} finally {
			trans.close();
		}

	}

	public Map<String, Object> importToRefund(WebRuntime runtime) throws Exception {

		String money = runtime.getParam("money");

		String co_number = runtime.getParam("co_number");

		SQLExecutorSingleConn sqlExecutor = this.getSQLExecutorSingleConn();

		// 采购订单ID
		String fyid = runtime.getParam("fyid");

		Map<String, Object> purchasing = this.getPurchasing(sqlExecutor, fyid);

		String coname = (String) purchasing.get("coname");

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat("yy-MM-dd");
		String currentDate = simple.format(new java.util.Date());

		String lxr = runtime.getParam("lxr");
		String coaddr = runtime.getParam("coaddr");
		String cotel = runtime.getParam("cotel");
		String cofax = runtime.getParam("cofax");
		String cgnumber = runtime.getParam("cgnumber");
		String ddid = runtime.getParam("ddid");

		String t[] = runtime.getParam("checkpro").split(",");

		if (t != null) {
			for (int i = 0; i < t.length; i++) {
				String strSQLpro = "select id,wid,epro,cpro,pro_number,num,unit,selljg,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,rate,supplier,remark from cgpro where id='"
						+ t[i] + "' ";

				Map<String, Object> pro = sqlExecutor.queryForMap(strSQLpro, true);

				int id = (Integer) pro.get("id");
				String wid = ((String) pro.get("wid")).trim();
				String pro_model1 = ((String) pro.get("epro")).trim();
				String pro_name1 = (String) pro.get("cpro");
				String pro_name2 = (String) pro.get("pro_number");

				String in_num = runtime.getParam("in_num" + t[i]);
				String pro_unit1 = (String) pro.get("unit");

				BigDecimal selljg = (BigDecimal) pro.get("selljg");

				String supplier = (String) pro.get("supplier");
				String remark = (String) pro.get("remark");

				String strSQLi = "insert into th_pro_supplier(ddid,epro,cpro,num,fypronum,unit,pricehb,fy_states,wid,money,selljg,salejg,rale,rale_types,supplier,fyproall,s_num,s_c_num,remark) "
						+ "values('" + ddid + "','" + pro_model1 + "','" + pro_name1 + "','" + in_num + "','"
						+ pro_name2 + "','" + pro_unit1 + "','" + money + "','" + coname + "','" + wid + "','" + money
						+ "'," + selljg + "," + selljg + ",0,'','" + supplier + "','no',0,0,'" + remark + "')";

				System.out.println(strSQLi);

				sqlExecutor.update(strSQLi);

				String strSQLty = "update cgpro set cgpro_num='" + in_num + "',cgpro_sdatetime='" + currentDate
						+ "' where id='" + id + "' ";
				sqlExecutor.update(strSQLty);

			}
		}

		sqlExecutor.update("update th_table_supplier set sub='" + cgnumber + "',co_number='" + co_number + "',cotel='"
				+ cotel + "',colxr='" + lxr + "',coname='" + coname + "',coaddr='" + coaddr + "',cofax='" + cofax
				+ "' where id='" + ddid + "'");

		sqlExecutor.close();

		return this.success();

	}

	private double getTotalPrice(Integer ddid) {
		String sql = "select sum(num*selljg) from cgpro where ddid = ?";
		return dbManager.getDouble(sql, new Object[] { ddid });
	}

	/**
	 * 新增订单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> list(WebRuntime runtime) throws Exception {

		String epro = runtime.unescape("epro");
		String supplier = runtime.unescape("supplier");

		PageBean pageBean = runtime.getPageBean(20);

		int top = pageBean.getTop();
		String sql = "select top " + top + " * from procure";
		String sqlCount = "select count(*) from procure";
		String sqlWhere = " where (l_spqk='草拟' or l_spqk='审批不通过')";
		String sqlOrderBy = " order by number desc";
		String number = runtime.getParam("number");

		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		boolean cgview = this.existRight(runtime, "cgview");

		String deptjbLike = deptjb + "%";

		if (cgview) {
			sqlWhere += " and l_deptjb like ?";
			params.add(deptjbLike);
		} else {
			sqlWhere += " and man = ?";
			params.add(username);
		}

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number = ?";
			params.add(number);
		}

		if (StringUtils.isNotEmpty(epro)) {
			sqlWhere += " and id in (select ddid from cgpro where epro like ?)";
			params.add("%" + epro + "%");
		}

		if (StringUtils.isNotEmpty(supplier)) {
			sqlWhere += " and id in (select ddid from cgpro where supplier like ?)";
			params.add("%" + supplier + "%");
		}

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);
		List rows = this.getRows(list, pageBean);
		for (int i = 0; i < rows.size(); i++) {
			Map map = (Map) rows.get(i);
			Integer ddid = (Integer) map.get("id");
			map.put("totalPrice", this.getTotalPrice(ddid));
		}
		return this.getPagingResult(list, pageBean, count);

	}

	/**
	 * 待审批订单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listToAudit(WebRuntime runtime) throws Exception {

		String epro = runtime.unescape("epro");
		String supplier = runtime.unescape("supplier");

		PageBean pageBean = runtime.getPageBean(20);

		int top = pageBean.getTop();
		String sql = "select top " + top + " * from procure";
		String sqlCount = "select count(*) from procure";
		String sqlWhere = " where 1=1";
		String sqlOrderBy = " order by number desc";
		String number = runtime.getParam("number");

		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		boolean cgview = this.existRight(runtime, "cgview");

		String deptjbLike = deptjb + "%";

		if (cgview) {
			sqlWhere += " and (l_spqk='待审批' or l_spqk='待复审' or l_spqk='待三审')";
			sqlWhere += " and l_deptjb like ?";
			params.add(deptjbLike);
		} else {
			sqlWhere += " and (" + "l_spqk='待审批' and (l_spman = ? or man = ?) "
					+ " or l_spqk='待复审' and (l_fspman= ? or man = ?) "
					+ " or l_spqk='待三审' and (l_firspman= ? or man = ?)" + ")";
			params.add(username);
			params.add(username);
			params.add(username);
			params.add(username);
			params.add(username);
			params.add(username);
		}

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number = ?";
			params.add(number);
		}

		if (StringUtils.isNotEmpty(epro)) {
			sqlWhere += " and id in (select ddid from cgpro where epro like ?)";
			params.add("%" + epro + "%");
		}

		if (StringUtils.isNotEmpty(supplier)) {
			sqlWhere += " and id in (select ddid from cgpro where supplier like ?)";
			params.add("%" + supplier + "%");
		}

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);
		List rows = this.getRows(list, pageBean);
		for (int i = 0; i < rows.size(); i++) {
			Map map = (Map) rows.get(i);
			Integer ddid = (Integer) map.get("id");
			map.put("totalPrice", this.getTotalPrice(ddid));
		}
		return this.getPagingResult(list, pageBean, count);

	}

	/**
	 * 待入库订单查询
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listWaitRk(WebRuntime runtime) throws Exception {

		String model = runtime.getParam("model");
		String coname = runtime.getParam("coname");
		String number = runtime.getParam("number");
		String supplier = runtime.getParam("supplier");

		PageBean pageBean = runtime.getPageBean(50);

		int top = pageBean.getTop();
		String countSql = "select count(*) from procure";

		String sql = "select top " + top
				+ " id,number,coname,l_spqk,l_spman,pay_je,sub,man,l_fspman,l_firspman,senddate,money,datetime,subck from procure";
		String sqlWhere = " where  (l_spqk='合同已确认' or l_spqk='待入库') ";

		if (StringUtils.isNotEmpty(model)) {
			sqlWhere += " and id in (select ddid from cgpro where epro like '%" + model + "%')";
		}

		if (StringUtils.isNotEmpty(supplier)) {
			sqlWhere += " and id in (select ddid from cgpro where supplier like '%" + supplier + "%')";
		}

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}
		String sqlOrderBy = " order by number desc ";

		int intRowCount = dbManager.getCount(countSql + sqlWhere);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, true);

		return this.getPagingResult(list, pageBean, intRowCount);

	}

	/**
	 * 待回复的询价单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listInquiryToReply(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);

		String xjnumber = runtime.getParam("number");
		String product = runtime.getParam("product");

		String supplier = runtime.getParam("supplier");

		String man = runtime.getParam("man");

		String cgman = runtime.getParam("cgman");

		String startdate = runtime.getParam("startdate");
		String enddate = runtime.getParam("enddate");
		String timeCondi = "";

		if (enddate == null)
			enddate = "";

		if (startdate == null)
			startdate = "";

		if (startdate != null && startdate.trim().length() > 0) {
			timeCondi += " and xj_date >='" + startdate + "'";
		}

		if (enddate != null && enddate.trim().length() > 0) {
			timeCondi += " and xj_date <='" + enddate + "'";
		}

		boolean supxjview = this.existRight(runtime, "r_sup_xj_view");

		boolean cgxjxx = this.existRight(runtime, "cgxjxx");

		String sql_where = "";

		if (xjnumber != null && xjnumber.trim().length() > 0) {
			sql_where += "and number like '%" + xjnumber + "%' ";
		}

		if (product != null && product.trim().length() > 0) {
			sql_where += "and product like '%" + product + "%' ";
		}

		if (supplier != null && supplier.trim().length() > 0) {
			sql_where += "and supplier like '%" + supplier + "%' ";
		}

		if (man != null && man.trim().length() > 0) {
			sql_where += "and man like '%" + man + "%' ";
		}

		if (cgman != null && cgman.trim().length() > 0) {
			sql_where += "and cgman like '%" + cgman + "%' ";
		}

		String username = this.getUsername(runtime);

		String sqlWhere;

		if (supxjview) {
			sqlWhere = "  where pro_states='已通知采购' " + sql_where + timeCondi;
		} else
			sqlWhere = " where pro_states='已通知采购'" + sql_where + timeCondi + " and cgman='" + username + "'";

		String strSQL = "select count(*) from ixjview " + sqlWhere;

		int count = dbManager.queryForInt(strSQL);

		List list = dbManager.queryForList("select * from ixjview " + sqlWhere + " order by id desc", true);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String saleMan = "";
			if (cgxjxx) {
				saleMan = (String) map.get("man");

			}
			saleMan += "(" + map.get("cgman") + ")";
			map.put("saleMan", saleMan);
		}

		return this.getPagingResult(list, pageBean, count);

	}

	/**
	 * 待回复的询价单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listInquiry(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);

		boolean supxjview = this.existRight(runtime, "r_sup_xj_view");

		boolean cgxjxx = this.existRight(runtime, "cgxjxx");

		String username = this.getUsername(runtime);

		String sqlWhere;

		if (supxjview) {
			sqlWhere = "";
		} else
			sqlWhere = " where pro_states!='草拟' and man='" + username + "'";

		String strSQL = "select count(*) from procure_xj " + sqlWhere;

		int count = dbManager.queryForInt(strSQL);

		List list = dbManager.queryForList("select * from procure_xj " + sqlWhere + " order by id desc", true);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			String saleMan = "";
			if (cgxjxx) {
				saleMan = (String) map.get("man");

			}
			saleMan += "(" + map.get("cgman") + ")";
			map.put("saleMan", saleMan);
		}

		return this.getPagingResult(list, pageBean, count);

	}

	public Map<String, Object> addInquiry(WebRuntime runtime) throws Exception {

		String number = NumberFactory.generateNumber("PE");

		String coname1 = runtime.getParam("coname");
		String co_number = runtime.getParam("co_number");
		String username = this.getUsername(runtime);
		String senddate1 = runtime.getParam("senddate");
		String tbyq1 = runtime.getParam("tbyq");
		tbyq1 = DealString.htmlEncode(tbyq1);
		tbyq1 = com.infoally.util.Replace.strReplace(tbyq1, "'", "''");
		String datetime1 = runtime.getParam("datetime");
		String money = runtime.getParam("money");
		String remarks1 = runtime.getParam("remarks");
		remarks1 = DealString.htmlEncode(remarks1);
		remarks1 = com.infoally.util.Replace.strReplace(remarks1, "'", "''");
		String linkman = runtime.getParam("linkman");
		String sup_tel = runtime.getParam("sup_tel");
		String sup_fax = runtime.getParam("sup_fax");

		String strSQL = "insert into procure_xj(number,man,linkman,co_number,coname,sup_tel,sup_fax,datetime,money,senddate,tbyq,remarks,state,sp_man,spyj,in_number) values(?,?,'"
				+ linkman + "','" + co_number + "','" + coname1 + "','" + sup_tel + "','" + sup_fax + "','" + datetime1
				+ "','" + money + "','" + senddate1 + "','" + tbyq1 + "','" + remarks1 + "','草拟','','','')";

		dbManager.executeUpdate(strSQL, new Object[] { number, username });
		return this.success();
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> getSaleList(WebRuntime runtime) throws Exception {

		String sqlWhere = "";

		String number = runtime.getParam("number");

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		int count = dbManager
				.getCount("select count(*) from subscribe where ( state='待出库'  or  state='预收款') " + sqlWhere);
		List list = dbManager.queryForList(
				"select * from subscribe where ( state='待出库'  or  state='预收款') "
						+ sqlWhere + " order  by id desc",
				true);

		return this.getPagingResult(list, runtime.getPageBean(20), count);

	}

	public Map<String, Object> pushSalePro(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");

		SaleManager saleManager = new SaleManager();
		SalePro salePro = saleManager.getSalePro(id);

		String ddid = runtime.getParam("ddid");
		String hb = runtime.getParam("hb");
		String rate = runtime.getParam("rate");

		String sql = "insert into cgpro(ddid,epro,cpro,pro_number,num,unit,"
				+ "selljg,money,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,remark,supplier,rate,wid,sale_supplier,"
				+ "sale_remark,sale_rate,sale_finance,sale_pro_id) values('" + ddid + "','" + salePro.getEpro() + "','"
				+ salePro.getCpro() + "','" + salePro.getFypronum() + "','" + salePro.getNum() + "','"
				+ salePro.getUnit() + "','" + salePro.getPrice() + "','" + hb + "','" + salePro.getS_tr_date()
				+ "','0','','" + salePro.getRemark() + "','" + salePro.getSupplier() + "','0','" + salePro.getWid()
				+ "','','','" + rate + "','" + salePro.getId() + "'," + salePro.getId() + ")";
		dbManager.update(sql);
		return this.success();

	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listRefundDraft(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);
		int top = pageBean.getTop();
		String sql = "select top " + top + " * from th_table_supplier where state='未提交'  or  state='未批准'";
		String sqlCount = "select count(*) from th_table_supplier where state='未提交'  or  state='未批准' ";
		String sqlWhere = "";
		String sqlOrderBy = " order by number desc";

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);

		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listRefundApproving(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);
		int top = pageBean.getTop();
		String sql = "select top " + top + " * from th_table_supplier where state='待审批'  or  state='待复审'";
		String sqlCount = "select count(*) from th_table_supplier where state='待审批'  or  state='待审批' ";
		String sqlWhere = "";
		String sqlOrderBy = " order by number desc";

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);

		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listRefundApproved(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);
		int top = pageBean.getTop();
		String sqlWhere = " where (state='已批准' or state='待退货'  or  state='已入库' or state='已出库' or state='部分出库')";
		String sql = "select top " + top + " * from th_table_supplier ";
		String sqlCount = "select count(*) from th_table_supplier ";
		
		String sqlOrderBy = " order by number desc";

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);

		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> listRefundDeleted(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(20);
		int top = pageBean.getTop();
		String sqlWhere = " where state='已删除' ";
		String sql = "select top " + top + " * from th_table_supplier ";
		String sqlCount = "select count(*) from th_table_supplier ";
		
		String sqlOrderBy = " order by number desc";

		// 发送到接口的参数
		List<Object> params = new ArrayList<Object>();

		int count = dbManager.getCount(sqlCount + sqlWhere, params);

		System.out.println(sql + sqlWhere + sqlOrderBy);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);

		return this.getPagingResult(list, pageBean, count);
	}

	public Map<String, Object> createRefund(WebRuntime runtime) throws Exception {

		String number1 = NumberFactory.generateNumber("RS");

		String coname1 = runtime.getParam("coname");
		String sub1 = runtime.getParam("sub");
		
		String money1 = runtime.getParam("money");
		String co_number = runtime.getParam("co_number");
		String datetime1 = DateUtil.getCurrentDateSimpleStr();
		String remarks1 = runtime.getParam("remarks");
		String deptjb = this.getDeptjb(runtime);
		
		String skfs = runtime.getParam("skfs");

		String sqls = "select count(*) from th_table_supplier where number=?";
		if (dbManager.exist(sqls,new Object[] {number1})) {
			return this.errorMsg("订单号重复");
		}

		String dept = this.getDept(runtime);
		String role = this.getRole(runtime);
		String username1 = this.getUsername(runtime);
		String sqlddman = "select  * from thsp_supplier where dept='" + dept + "' and role='" + role + "'";
		System.out.println(sqlddman);
		Map<String, Object> rsddman = dbManager.queryForMap(sqlddman, true);
		if (rsddman == null) {
			return this.errorMsg("未定义退货审批流程!");
		}
		String dd_man = (String) rsddman.get("dd_man");
		String fif = (String) rsddman.get("fif");
		String fspman = (String) rsddman.get("fspman");
		String firspif = (String) rsddman.get("firspif");
		String firspman = (String) rsddman.get("firspman");

		String strSQL = "insert into th_table_supplier(number,man,sub,coname,co_number,money,habitus,datetime,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,firspif,firspman,fspyj,dept,deptjb,w_remark,skfs) values('"
				+ number1 + "','" + username1 + "','" + sub1 + "','" + coname1 + "','" + co_number
				+ "','" + money1 + "','订单执行中','" + datetime1 + "','" + remarks1 + "','未提交','" + dd_man + "','  ',' ','"
				+ fif + "','" + fspman + "','','','" + firspif + "','" + firspman + "','','" + dept + "','" + deptjb
				+ "','','" + skfs + "')";
		dbManager.executeUpdate(strSQL);
		return this.success();

	}
	
	private String getStatus(String code) {
		Integer codeInt = Integer.valueOf(code);
		String res = null;
		switch (codeInt) {
		case 2:
			res = "审批通过";
			break;
		case 3:
			res = "合同已确认";
			break;

		case 4:
			res = "待入库";
			break;
		case 6:
			res = "已入库";
			break;
		case 7:
			res = "全部退货";
			break;
		case 8:
			res = "部分退货";
			break;

		}
		return res;
	}
	
	/**
	 * 已审订单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({"rawtypes" })
	public Map<String,Object> listApproved(WebRuntime runtime) throws Exception {

		
		String username = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);

		PageBean pageBean = runtime.getPageBean(50);

		int top = pageBean.getTop();
		String countSql = "select count(*) from procure";

		String sql = "select top "
				+ top
				+ " id,number,coname,l_spqk,l_spman,pay_je,sub,man,l_fspman,l_firspman,senddate,money,datetime from procure";
		String sqlWhere = " where ";
		String sqlOrderBy = " order by number desc ";

		if (this.existRight(runtime, "cgview")) {
			sqlWhere += " l_deptjb like '" + deptjb + "%'";
		} else {
			sqlWhere += " man='" + username + "'";
		}

		sqlWhere += " and (l_spqk='审批通过' or l_spqk='合同已确认' or  l_spqk='已入库' or l_spqk='待入库' or  l_spqk='全部退货' or  l_spqk='部分退货') ";

		String pro_model = runtime.getParam("pro_model");
		if (StringUtils.isNotEmpty(pro_model)) {
			sqlWhere += " and id in (select ddid from cgpro where epro like '%"
					+ pro_model + "%')";
		}
		
		String pro_pp = runtime.getParam("pro_pp");
		if(StringUtils.isNotEmpty(pro_pp)){
			sqlWhere += " and id in (select ddid from cgpro where supplier like '%"
					+ pro_pp + "%')";
		}

		String coname = runtime.getParam("supplier");
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}

		String number = runtime.getParam("number");
		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		String startDate = runtime.getParam("startdate");
		if (StringUtils.isNotEmpty(startDate)) {
			sqlWhere += " and datetime >= '" + startDate + "'";
		}

		String endDate = runtime.getParam("enddate");
		if (StringUtils.isNotEmpty(endDate)) {
			sqlWhere += " and datetime <= '" + endDate + "'";
		}

		String man = runtime.getParam("man");
		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man = '" + man + "'";
		}

		String status = runtime.getParam("status");
		if (StringUtils.isNotEmpty(status)) {
			sqlWhere += " and l_spqk = '" + getStatus(status) + "'";
		}

		int intRowCount = dbManager.getCount(countSql + sqlWhere);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, true);
		pageBean.setTotalAmount(intRowCount);
		List rows = this.getRows(list, pageBean);

		for (int i = 0; i < rows.size(); i++) {
			Map m = (Map) rows.get(i);
			Integer id = (Integer) m.get("id");
			m.put("totalPrice", this.getTotalPrice(id));
		}
		
		return this.getPagingResult(rows, pageBean);

	}
	
	public Map<String,Object> addPrintLog(WebRuntime runtime) throws Exception {
		String username = this.getUsername(runtime);
		String number = runtime.getParam("number");
		String sql = "insert into print_log(operator,number,created_time) values(?,?,getdate())";
		dbManager.update(sql,new Object[] {username,number});
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listPrintLog(WebRuntime runtime) throws Exception {
		String sql = "select * from print_log";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from print_log";
		int count = dbManager.getCount(sql);
		return this.getPagingResult(list, runtime.getPageBean(20), count);
	}
	
	/**
	 * 获取所有的品牌信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List trademarkList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return trademarkDao.trademarkList();
	}
	
	/**
	 * 
	 * 保存采购订单的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	public Map<String,Object> savePro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String subck = EscapeUnescape.unescape(request, "subck");
		String quoteid = request.getParameter("ddid");

		int t = 0;
		String i1 = request.getParameter("i");

		if (i1 != null)
			t = Integer.parseInt(i1);

		for (int i = 1; i <= t; i++) {

			String id = request.getParameter("id" + i);
			if (id != null) {

				String pro_name = "0";
				String pro_model = EscapeUnescape.unescape(request, "pro_model"
						+ i);

				String num = request.getParameter("num" + i);
				String punit = EscapeUnescape.unescape(request, "punit" + i);
				String supplier = EscapeUnescape.unescape(request, "supplier"
						+ i);
				String pro_tr = EscapeUnescape.unescape(request, "pro_tr" + i);
				String fz = EscapeUnescape.unescape(request, "fz" + i);
				String selljg = request.getParameter("selljg" + i);
				String pro_remark = EscapeUnescape.unescape(request,
						"pro_remark" + i);

				String rate = request.getParameter("rate" + i);
				String strSQL = "update cgpro set epro=?,cpro=?,pro_number='"
						+ fz + "',num='" + num + "',unit='" + punit
						+ "',selljg='" + selljg + "',cgpro_ydatetime='"
						+ pro_tr + "',remark=?,supplier='" + supplier
						+ "',rate='" + rate + "' where  id='" + id + "'";

				dbManager.update(strSQL, new Object[] { pro_model, pro_name,
						pro_remark });

			}
		}

		String pro_name = "0";

		String pro_model = EscapeUnescape.unescape(request, "pro_model_new")
				.trim();

		if (pro_model != null) {
			pro_model = com.infoally.util.Replace.strReplace(pro_model, "'",
					"’");
			pro_model = com.infoally.util.Replace.strReplace(pro_model, "\"",
					"”");
		}
		String num = request.getParameter("2num");
		String punit = EscapeUnescape.unescape(request, "2punit");
		String supplier = EscapeUnescape.unescape(request, "2supplier");
		String pro_tr = EscapeUnescape.unescape(request, "2pro_tr");
		String fz = EscapeUnescape.unescape(request, "2fz");
		String money = request.getParameter("2money");
		String selljg = request.getParameter("2selljg");
		String rate = request.getParameter("2rate");

		String pro_remark = EscapeUnescape.unescape(request, "pro_remark_new");
		if (pro_remark != null) {
			pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "'",
					"’");
			pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "\"",
					"”");
		}
		
		System.out.println("pro_model:"+pro_model);
		
		if (StringUtils.isNotEmpty(pro_model)) {
			
			String strSQL1 = "insert into cgpro(ddid,epro,cpro,pro_number,num,unit,selljg,money,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,remark,supplier,rate,wid,sale_supplier,sale_remark,sale_rate,sale_finance) values('"
					+ quoteid
					+ "','"
					+ pro_model
					+ "','"
					+ pro_name
					+ "','"
					+ fz
					+ "','"
					+ num
					+ "','"
					+ punit
					+ "','"
					+ selljg
					+ "','"
					+ money
					+ "','"
					+ pro_tr
					+ "','0','','"
					+ pro_remark
					+ "','"
					+ supplier
					+ "','"
					+ rate
					+ "','"
					+ subck
					+ "','','','','')";
			dbManager.update(strSQL1);
			
		}

		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> toAudit(@Param("id") String id, @Session("username") String username,
			@Session("deptjb") String deptjb, @Session("dept") String dept, @Session("role") String role) throws Exception {
		
		String currentDate = DateUtil.getCurrentDateSimpleStr();

		Transaction trans = null;

		try {
			trans = this.getTransaction();

			Map<String, Object> procure = trans.queryForMap(
					"select * from procure where id = ?", new Object[] { id },
					true);
			String co_number = (String) procure.get("co_number");
			String coname = (String)  procure.get("coname");
			if (coname == null) {
				throw new Exception("订单没有供应商信息，不允许提交审批！");
			}

			String man1 = (String) procure.get("man");
			String number = (String) procure.get("number");

			number = number.trim();

			if (coname != null) {
				coname = coname.trim();
			}
			if (man1 != null) {
				man1 = man1.trim();
			}

			String hb = (String) procure.get("money");
			String coaddr = "";
			String cotel = "";
			String cofax = "";
			String cokhyh = "";
			String coyhzh = "";
			
			if(StringUtils.isEmpty(co_number)){
				throw new Exception("供应商编号不能为空");
			}
			
			String sqlpp = "select  id,co_number,coname,coaddr,cotel,cofax,codzyj,conet,copost,city,country,province,cofrdb,cozzxs,cozczb,coyyzz,cotypes,tradetypes,cokhjb,cosyhb,cojsfs,nshm,number,yearearning,cokhyh,coyhzh,coclrq,ifjckq,username,dept,modman,mod_date,share,valueco,rg_date,annual_sales,sales_ability,qlty_control,companymt,scale,warehouse,describee from supplier where co_number=?";
			Map supplier = trans.queryForMap(sqlpp, new Object[] { co_number },
					true);
			
			if(supplier==null){
				throw new Exception("供应商不存在！");
			}
			
			coaddr = (String) supplier.get("coaddr");
			cotel = (String) supplier.get("cotel");
			cofax = (String) supplier.get("cofax");
			cokhyh = (String) supplier.get("cokhyh");
			coyhzh = (String) supplier.get("coyhzh");

			String sqlsp = "select count(*) from gathering_customer where coname=?";
			int gatheringCount = trans.getCount(sqlsp, new Object[] { coname });

			if (gatheringCount == 0) {
				String strSQLp1 = "insert into gathering_customer(coname,coaddr,cofrdb,cotel,cofax,bank,payment_je,payment_sje,money,remark,payment_date,co_number,dept,deptjb,pname) values('"
						+ coname
						+ "','"
						+ coaddr
						+ "','','"
						+ cotel
						+ "','"
						+ cofax
						+ "','"
						+ cokhyh
						+ ":"
						+ coyhzh
						+ "','0','0','"
						+ hb
						+ "','','"
						+ currentDate
						+ "','"
						+ co_number
						+ "','" + dept + "','" + deptjb + "','" + man1 + "')";
				trans.update(strSQLp1);
			}
			double shl = SystemCache.getCurrentRate(hb);
			double totle = 0;
			String strSQLpro = "select num,selljg from cgpro where ddid='" + id
					+ "'";

			List cgproList = trans.queryForList(strSQLpro, true);

			for (int i = 0; i < cgproList.size(); i++) {

				Map map = (Map) cgproList.get(i);
				int num = (Integer) map.get("num");

				double price = ((BigDecimal) map.get("selljg")).doubleValue();
				double tprice = num * price;
				totle = totle + tprice;// 金额

			}

			totle = totle * shl;
			
			String strSQL = "update procure set l_spqk='待审批' where id=?";

			trans.update(strSQL, new Object[] {id});

			String sqlspg = "select count(*) from payment where contract='"
					+ number + "'";
			int paymentCount = trans.getCount(sqlspg);
			if (paymentCount == 0) {
				String strSQLp = "insert into payment(contract,orderform,sup_number,supplier,pay_je,yjfkdate,sjfkdate,moneyty,moneytypes,htmoney,bank,bankaccounts,paynr,note,states,remark,wtfk) values('"
						+ number
						+ "','"
						+ id
						+ "','"
						+ co_number
						+ "','"
						+ coname
						+ "','0','"
						+ currentDate
						+ "','"
						+ currentDate
						+ "','"
						+ hb
						+ "','银行转帐','0','','0','','','待付款','"
						+ man1
						+ "','" + deptjb + "')";
				trans.update(strSQLp);
			}
			trans.commit();
			return this.success();
		} catch (Exception ex) {
			if (trans != null) {
				trans.rollback();
			}
			logger.error("采购订单提交审批失败！", ex);
			return this.errorMsg(ex.toString());
		} finally {
			if (trans != null) {
				trans.close();
			}

		}
	}

	public Map<String,Object> getProfitInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ProcureManager procureManager = new ProcureManager();
		Procure procure = procureManager.getProcure(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", procure.getNumber());
		map.put("sub", procure.getSub());
		ArrayList<Map<String, String>> pros = new ArrayList<Map<String, String>>();
		if (procure.getPros() != null) {
			for (int i = 0; i < procure.getPros().size(); i++) {
				CgPro p = procure.getPros().get(i);
				Map<String, String> pro = new HashMap<String, String>();
				pro.put("epro", p.getEpro());
				pro.put("profit", String.valueOf(p.getProfit()));
				pros.add(pro);
			}
		}

		map.put("pros", pros);
		return map;
	}
	
	private Map<String,Object> reject(Transaction trans, Purchasing purchasing, String username, String opinion) throws Exception {
		Integer id = purchasing.getId();

		String zt = "审批不通过";
		String pzt = "草拟";

		// 发送审批的邮件
		String number = purchasing.getNumber();
		String mail_to = purchasing.getMan();
		String title = "你的订单" + number + "没有通过审批";
		String content = "审批意见：" + opinion;
		String mail_from = "system";

		SendMail sendmail = new SendMail();
		sendmail.sendMail(title, content, mail_to, mail_from);

		financeDao.updatePaymentStatus(trans, id, pzt);
		purchasingDao.updatePurchasingStatus(trans, zt, opinion, id);
		
		PurchasingAuditLog log = new PurchasingAuditLog();
		log.setOrderId(id);
		log.setOperator(username);
		log.setStatusFrom(purchasing.getStatusOrign());
		log.setStatusTo(zt);
		purchasingDao.addAuditLog(trans, log);
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String,Object> pass(Transaction trans, Purchasing purchasing, String username, String opinion) throws Exception {
		Integer id = purchasing.getId();
		double totle = 0;
		String strSQLpro = "select num,selljg from cgpro where ddid=?";
		
		List cgproList = trans.queryForList(strSQLpro, new Object[] {id}, true);

		for (int i = 0; i < cgproList.size(); i++) {
			Map map = (Map) cgproList.get(i);
			int num = (Integer) map.get("num");
			double price = ((BigDecimal) map.get("selljg")).doubleValue();		
			double tprice = num * price;
			totle = totle + tprice;												// 金额
		}

		String dept = purchasing.getDept();
		String sqlddman = "select  * from cgsp  where  ?>=price_min  and  price_max>=?  and dept=?";

		Map<String, Object> spMap = trans.queryForMap(sqlddman,
				new Object[] { totle, totle, dept }, true);

		if (spMap == null || spMap.size() == 0) {
			return this.errorMsg("未定义审批流程!");
		}

		boolean fif = purchasing.isFirstApproved();
		String zt = "审批不通过";
		String pzt = "草拟";

		// 发送审批的邮件
		String number = purchasing.getNumber();
		String mail_to = purchasing.getMan();
		String title = "你的订单" + number + "已经通过审批";
		String content = "审批意见：" + opinion;
		String mail_from = "system";

		SendMail sendmail = new SendMail();
		sendmail.sendMail(title, content, mail_to, mail_from);

		if (fif) {
			zt = "待复审";
		} else {
			zt = "审批通过";
			pzt = "待付款";
		}

		financeDao.updatePaymentStatus(trans, id, pzt);
		purchasingDao.updatePurchasingStatus(trans, zt, opinion, id);
		
		PurchasingAuditLog log = new PurchasingAuditLog();
		log.setOrderId(id);
		log.setOperator(username);
		log.setStatusFrom(purchasing.getStatusOrign());
		log.setStatusTo(zt);
		purchasingDao.addAuditLog(trans, log);
		return this.success();
	}
	
	/**
	 * 审批订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> audit(AuditForm auditForm, @Session("username") String username) throws Exception {

		String id = auditForm.getId();
		Purchasing purchasing = purchasingDao.getPurchasingById(id);
		Transaction trans = this.getTransaction();
		try {
			String l_spqk = auditForm.getL_spqk();
			String opinion = auditForm.getL_spyj();
			boolean pass = l_spqk.equals("审批通过");
			Map<String,Object> res;
			if (!pass) {
				res = reject(trans, purchasing, username, opinion);
			} else {
				res = pass(trans, purchasing, username, opinion);
			}
			trans.commit();
			return res;
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
		return this.success();
	}
	
	/**
	 * 审批订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> auditSecond(WebRuntime runtime) throws Exception {
		
		 String id1=runtime.getParam("id");
		 Purchasing procure = purchasingDao.getPurchasingById(id1);
		 
		  String l_spqk=runtime.getParam("status").trim();
		  String l_spyj=runtime.getParam("remark");
		  String spyj=runtime.getParam("spyj");
		 
		  String zt="审批不通过";
		  String pzt="草拟";
		  
		  
		  SendMail sendmail = new SendMail();
		  // 发送审批的邮件
		String number = procure.getNumber();
		String mail_to = procure.getMan();
		String title = "你的订单"+number+l_spqk;
		String content = "审批意见："+l_spyj;
		String mail_from = this.getUsername(runtime);
		sendmail.sendMail(title,content,mail_to,mail_from);
		  
		  if(l_spqk.equals("审批通过")){
			  	zt="审批通过";pzt="待付款";
			}
		   String strSQLpay="update payment set states='" + pzt + "' where orderform='" + id1 + "'";
		   dbManager.executeUpdate(strSQLpay);
		   
		 String strSQL="update procure set l_spqk='" + zt + "',l_spyj='"+spyj+";"+l_spyj+"' where id='" + id1 + "'";
		   dbManager.executeUpdate(strSQL);
		   
		   return this.success();
   
	}
	

	public Map<String,Object> confirmContact(WebRuntime runtime) throws Exception {

		DBConnection einfodb = new DBConnection();

		String id1 = runtime.getParam("id");
		String strSQL = "update procure set  l_spqk='合同已确认' where id='" + id1
				+ "'";

		boolean t = einfodb.executeUpdate(strSQL);
		if (!t) {
			return this.errorMsg("供应商确认失败");
		}

		return this.success();

	}
	
	public Map<String,Object> editInNum(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		String num = runtime.getParam("num");
		
		purchasingDao.updateRkNum(id, num);
		
		return this.success();
	}
	
	public Map<String,Object> updaeProRkNum(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		String num = runtime.getParam("num");
		
		purchasingDao.updateProRkNum(id, num);
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List listRkPro(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select i.number,r.* from rkhouse r inner join in_warehouse i on r.pro_rk_num = i.id where pro_rate = ? ";
		return dbManager.queryForList(sql, new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getProList(String id) throws Exception{
		String sql = "select * from cgpro where ddid = ?";
		return dbManager.queryForList(sql, new Object[]{id},true);
	}
	
	
	@SuppressWarnings("rawtypes")
	private List<String> checkRk(String id) throws Exception{
		List proList = this.getProList(id);
		List<String> res = new ArrayList<String>();
		for(int i=0;i<proList.size();i++){
			Map map = (Map) proList.get(i);
			Integer proId = (Integer) map.get("id");
			String checkResult = checkRkPro(proId);
			if(checkResult!=null){
				if(!res.contains(checkResult))
					res.add(checkResult);
			}
		}
		return res;
	}
	
	/**
	 * 返回合同
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> returnPurchasing(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		List<String> checkResult = checkRk(id);
		if(checkResult.size()>0){
			String msg =  "采购订单已经做了入库单，请先取消入库单，再返回：入库单号：";
			for(int i=0;i<checkResult.size();i++){
				msg += ","+checkResult.get(i);
			}
			return this.errorMsg(msg);
		}
		
		String update1 = "update payment set states='草拟' where orderform=?";
		String update2 = "update procure set l_spqk='待审批',thremark='已审合同返回' where  id=?";
		dbManager.update(update1, new Object[] { id });
		dbManager.update(update2, new Object[] { id });
		return this.success();
	}

	public Map<String,Object> delPro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		dbManager.update("delete from cgpro where id = ?", new Object[] { id });
		return this.success();
	}

	public Map<String,Object> del(@Param("id") String id) throws Exception {
		purchasingDao.del(id);
		return this.success();
	}
	
	private String checkRkPro(Integer proId) throws Exception{
		String sql = "select pro_rk_num from rkhouse where pro_rate = ?";
		Integer inWarehouseId = dbManager.queryForInt(sql,new Object[]{proId});
		if(inWarehouseId>0){
			return getInWarehouseNumber(inWarehouseId);
		}
		return null;
	}
	
	
	private String getInWarehouseNumber(Integer id){
		String sql = "select number from in_warehouse where id = ? and states<>'已删除'";
		return dbManager.getString(sql, new Object[]{id});
	}
	
	/**
	 * 待审批订单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listDeleted(WebRuntime runtime) throws Exception {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);
		
		PageBean pageBean = runtime.getPageBean();
		
		boolean cgview = this.existRight(runtime, "cgview");
		String number = runtime.getParam("number");
		String sub = runtime.getParam("sub");

		String sqlWhere = "";

		if (sub != null) {
			sqlWhere += " and sub like '%" + sub + "%'";
		}

		if (number != null) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		String manP = runtime.getParam("man");
		if (manP != null) {
			sqlWhere += " and man like '%" + manP + "%' ";
		}
		String conameP = runtime.getParam("coname");
		if (conameP != null) {
			sqlWhere += " and coname like '%" + conameP + "%'";
		}

		String startdate = runtime.getParam("startdate");
		if (startdate != null)
			sqlWhere += " and datetime >= '" + startdate + "'";
		String enddate = runtime.getParam("enddate");
		if (enddate != null)
			sqlWhere += " and datetime <= '" + enddate + "'";

		String brand = runtime.getParam("brand"); // 品牌查询

		if (brand != null && brand.trim().length() > 0) {
			sqlWhere += " and id in (select ddid from cgpro where supplier like '%"
					+ brand + "%')";

		}

		String epro = runtime.getParam("epro"); // 型号查询
		if (epro == null)
			epro = "";
		if (epro != null && epro.trim().length() > 0) {
			sqlWhere += " and id in (select ddid from cgpro where epro like '%"
					+ epro + "%') ";
		}

		String strSQL = "select count(*) from procure  where l_spqk='已删除'"
				+ sqlWhere;
		
		int count = dbManager.getCount(strSQL);

		if (cgview) {
			strSQL = "select top " + pageBean.getTop() + " * from procure  where l_spqk='已删除'"
					+ sqlWhere;
		} else
			strSQL = "select top " + pageBean.getTop() + " * from procure where l_spqk='已删除'"
					+ sqlWhere;

		sqlWhere += " order by datetime desc";
		strSQL += sqlWhere;

		List list = dbManager.queryForList(strSQL, true);
		
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public List listAllPurchaseMan(WebRuntime runtime) throws Exception {
		String sql = "select name from username where department_name='采购部'";
		return dbManager.queryForList(sql, true);
	}
	
	public Map<String,Object> detail(@Param("id") String id) throws Exception {
		String sql = "select * from procure where id = ?";
		Map<String, Object> res = dbManager.queryForMap(sql, new Object[] {id}, true);
		return this.success("data", res);
	}

}