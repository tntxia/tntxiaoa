package com.tntxia.oa.sale.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.tntxia.date.DateUtil;
import com.tntxia.db.InsertSegmentCombine;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.current.CurrentManager;
import com.tntxia.oa.sale.dao.QuoteDao;
import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.user.service.UserService;
import com.tntxia.oa.util.BigDecimalUtils;
import com.tntxia.oa.util.ListUtils;
import com.tntxia.oa.util.MapUtils;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.mvc.view.FileView;
import com.tntxia.web.util.DatasourceStore;
import com.tntxia.web.util.Dom4jUtil;

import infocrmdb.DealString;

public class SaleDoAction extends CommonDoAction {
	
	private Logger logger = Logger.getLogger(SaleDoAction.class);

	private DBManager dbManager = this.getDBManager();

	private SaleLightDao saleDao = new SaleLightDao();
	
	private QuoteDao quoteDao = new QuoteDao();
	
	private SaleLightService saleService = new SaleLightService();
	
	private UserService userService = new UserService();
	
	/**
	 * 新增订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> list(WebRuntime runtime) throws Exception {

		String numberQ = runtime.getParam("number");

		String model = runtime.getParam("model");

		String conameQ = runtime.getParam("coname");

		String productNumber = runtime.getParam("productNumber");
		String deptId = runtime.getParam("dept");
		String man = runtime.getParam("man");

		String startdate = runtime.getParam("startdate");
		String enddate = runtime.getParam("enddate");

		String strSQL;
		int intRowCount;

		PageBean pageBean = runtime.getPageBean(50);
		
		String username1 = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);

		// 是否拥有查看本部门订单的权限
		boolean hasSubviewRight = this.existRight(runtime, "subview");

		String sqlWhere = " where (state='未提交' or  state='未批准') ";

		strSQL = "select count(*) from subscribe";

		if (hasSubviewRight) {
			sqlWhere += " and deptjb like '" + deptjb + "%'";
		} else {
			sqlWhere += " and man='" + username1 + "'";
		}

		if (StringUtils.isNotEmpty(numberQ)) {
			sqlWhere += " and number like '%" + numberQ + "%'";
		}

		if (StringUtils.isNotEmpty(model)) {
			sqlWhere += " and id in (select ddid from ddpro where epro like '%"
					+ model + "%')";
		}

		if (StringUtils.isNotEmpty(conameQ)) {
			sqlWhere += " and coname like '%" + conameQ + "%'";
		}

		if (StringUtils.isNotEmpty(deptId)) {
			String departmentName = SystemCache.getDepartmentName(deptId);
			sqlWhere += " and dept like '%" + departmentName + "%'";
		}

		if (StringUtils.isNotEmpty(productNumber)) {
			sqlWhere += " and fypronum like '%" + productNumber + "%'";
		}

		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man like '%" + man + "%'";
		}

		if (StringUtils.isNotEmpty(startdate)) {
			sqlWhere += " and datetime >= '" + startdate + "'";
		}

		if (StringUtils.isNotEmpty(enddate)) {
			sqlWhere += " and datetime <= '" + enddate + "'";
		}
		
		intRowCount = dbManager.getCount(strSQL + sqlWhere);

		strSQL = "select * from subscribe "
				+ sqlWhere + "  order by id desc ";
		

		List list = dbManager.queryForList(strSQL, true);

		return this.getPagingResult(list, pageBean, intRowCount);
		

	}
	
	/**
	 * 已删订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listDeleted(WebRuntime runtime) throws Exception {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		String strSQL;

		String number = runtime.getParam("number");
		String sqlWhere = "";

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		String man = runtime.getParam("man");
		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man like '%" + man + "%' ";
		}

		String coname = runtime.getParam("coname");
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}

		String startdate = runtime.getParam("startdate");
		if (StringUtils.isNotEmpty(startdate))
			sqlWhere += " and send_date >= '" + startdate + "'";
		String enddate = runtime.getParam("enddate");

		if (StringUtils.isNotEmpty(enddate))
			sqlWhere += " and send_date <= '" + enddate + "'";

		String model = runtime.getParam("model");
		if (StringUtils.isNotEmpty(model)) {
			sqlWhere += " and id in  (select ddid from ddpro where epro like '%"
					+ model + "%')";
		}

		strSQL = "select count(*) from subscribe where state='已删除'";

		
		int intRowCount = dbManager.getCount(strSQL+sqlWhere);
		
	
		strSQL = "select id,fy_number,number,custno,sub,yj,yf_money,coname,item,mode,send_date,man,spman,state,del_remark from subscribe where state='已删除'";

		List list = dbManager.queryForList(strSQL+sqlWhere+" order by number desc", true);
		
		return this.getPagingResult(list, runtime, intRowCount);

	}
	
	/**
	 * 删除销售订单
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> del(WebRuntime runtime) throws Exception {
		
		Transaction trans = this.getTransaction();
		
		try {
			String id1 = runtime.getParam("id");

			Sale sale = saleDao.getSaleById(id1);
			
			String username = this.getUsername(runtime);
			if(! (username.equals(sale.getMan()) || this.existRight(runtime, "subdel")) ) {
				return this.errorMsg("你没有删除此订单的权限");
			}
			
			String number = (String) sale.getNumber();

			String remark = runtime.getParam("remark");
			String strDelSale = "update subscribe set state='已删除',del_remark='"
					+ remark + "' where id = '" + id1 + "'";
			
			trans.update(strDelSale);
			
			String strDelGathering = "update gathering set states = '已删除' where orderform='"
					+ number + "'";
			trans.update(strDelGathering);
			trans.commit();
			
		}catch(Exception ex) {
			trans.rollback();
		}finally {
			trans.close();
		}
		return this.success();

	}

	public Map<String, Object> stockUp(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "update subscribe set state='已备货' where id=?";
		dbManager.update(sql, new Object[] { id });
		return this.success();
	}

	private Map<String, Object> getDetail(String id) throws Exception {
		String sql = "select * from subscribe where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}
	
	private Map<String, Object> getDetail(Integer id) throws Exception {
		return this.getDetail(String.valueOf(id));
	}

	private String getFinanceNote(String number) {
		String sql = "select note from gathering where orderform=?";
		return dbManager.getString(sql, new Object[] { number });
	}

	public Map<String, Object> returnOrder(WebRuntime runtime) throws Exception {

		String ddid = runtime.getParam("id");

		Map<String, Object> detail = this.getDetail(ddid);

		String state = (String) detail.get("state");

		if (state == null) {
			return this.errorMsg("已审合同状态为空");
		}

		if (state.equals("已发运") || state.equals("全部退货") || state.equals("部分退货")) {
			return this.errorMsg("不能申请返回，订单状态：" + state);
		}

		String number = (String) detail.get("number");

		String note = this.getFinanceNote(number);
		if (note != null && note.trim().length() > 0) {
			return this.errorMsg("不能申请返回，财务收款状态：" + note);
		}

		String return_remark = runtime.getParam("remark").trim();

		String man = (String) detail.get("man");

		Transaction trans = this.getTransaction();

		try {

			String strSQL = "update  subscribe  set state='待审批',return_remark='"
					+ return_remark + "'   where number='" + number + "'";

			trans.executeUpdate(strSQL);

			String strSQLp = "delete from gathering where orderform='" + number
					+ "'";

			trans.executeUpdate(strSQLp);

			java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");

			String currentDate = simple.format(new java.util.Date());
			String usernmae = this.getUsername(runtime);
			String dept = this.getDept(runtime);
			String deptjb = this.getDeptjb(runtime);

			String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
					+ man
					+ "','','','合同退回:编号为:"
					+ number
					+ "合同','退回合同原因:"
					+ return_remark
					+ "','"
					+ usernmae
					+ "','"
					+ deptjb
					+ "','"
					+ dept + "','" + currentDate + "','已发送','','','')";

			trans.executeUpdate(strSQLs);
			trans.commit();
			return this.success();
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
			return this.errorMsg(e.toString());
		} finally {
			trans.close();
		}

	}

	/**
	 * 已审批订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> approvedList(WebRuntime runtime)
			throws Exception {
		
		PageBean pageBean = runtime.getPageBean(50);
		String username = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);
		
		Map<String,String> param = runtime.getParamMap();
		
		boolean subview = this.existRight(runtime, "subview");
		if(subview) {
			param.put("deptjb", deptjb);
		}else {
			param.put("username", username);
		}
		return saleService.listApproved(param, pageBean);

		
	}

	/**
	 * 
	 * 已审订单查询，与已审订单表查询的sql不同，所以独立一个方法来获取
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> approvedSearchList(WebRuntime runtime)
			throws Exception {

		String coname = runtime.unescape("coname");

		String departmentName = runtime.unescape("depts");

		String man = runtime.unescape("man");
		String epro = runtime.unescape("epro");
		String startdate = runtime.unescape("startdate");
		String enddate = runtime.unescape("enddate");

		// 财务收款情况
		String gatherState = runtime.unescape("gatherState");
		String statexs = runtime.unescape("statexs");
		// 合同编号
		String numberQuery = runtime.unescape("number");

		String sub = runtime.getParam("sub");

		String pp = runtime.getParam("pp");

		if (pp == null)
			pp = "";

		String currentDate = DateUtil.getCurrentDateSimpleStr();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		int intRowCount = 0;

		PageBean pageBean = runtime.getPageBean(50);

		String username1 = this.getUsername(runtime);

		String deptjb = this.getDeptjb(runtime);

		boolean subview = this.existRight(runtime, "subview");

		String strSQL;

		String sqlWhere = " where (state='预收款' or state='已发运' or state='待出库' or state='待收款' or state='已备货' or state='全部退货' or state='部分退货') and habitus='订单执行中'";

		if (subview || username1.equals("admin")) {
			sqlWhere += " and deptjb like '" + deptjb + "%'";
		} else {
			sqlWhere += " and  man='" + username1 + "' ";
		}

		if (StringUtils.isNotEmpty(numberQuery)) {
			sqlWhere += " and number like '%" + numberQuery + "%'";
		}

		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}

		if (StringUtils.isNotEmpty(departmentName)) {
			sqlWhere += " and dept like '%" + departmentName + "%'";
		}

		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man like '%" + man + "%'";
		}

		if (StringUtils.isNotEmpty(epro)) {
			sqlWhere += " and epro like '%" + epro + "%'";
		}

		if (StringUtils.isNotEmpty(startdate)) {
			sqlWhere += "  and datetime>='" + startdate + "' ";
		}

		if (StringUtils.isNotEmpty(enddate)) {
			sqlWhere += "  and datetime<='" + enddate + "' ";
		}

		if (StringUtils.isNotEmpty(sub)) {
			sqlWhere += "  and custno like '%" + sub + "%' ";
		}

		if (StringUtils.isNotEmpty(gatherState)) {
			if ("1".equals(gatherState)) {
				sqlWhere += " and number not in (select orderform from gathering where note = '已收款' )";
			} else if ("2".equals(gatherState)) {
				sqlWhere += " and number in (select orderform from gathering where note = '已收款' )";
			}

		}

		if (StringUtils.isNotEmpty(statexs)) {
			sqlWhere += "  and state = '" + statexs + "' ";
		}

		if (StringUtils.isNotEmpty(pp)) {
			sqlWhere += "  and supplier = '" + pp + "' ";
		}

		strSQL = "select count(*) from subview2 " + sqlWhere;

		intRowCount = dbManager.queryForInt(strSQL);

		int top = pageBean.getTop();

		strSQL = "select top " + top + " * from subview2 " + sqlWhere
				+ " order by id desc";

		List list = dbManager.queryForList(strSQL, true);
		
		logger.debug("search order sql : "+strSQL);

		List rows = this.getRows(list, pageBean);

		CurrentManager currManager = new CurrentManager();

		Map<String, BigDecimal> hbAmountTotalMap = new HashMap<String, BigDecimal>();
		hbAmountTotalMap.put("CNY", BigDecimal.ZERO);

		for (int i = 0; i < rows.size(); i++) {
			Map map = ListUtils.getMap(rows, i);
			int num = MapUtils.getInt(map, "num");

			if (num == 0) {
				continue;
			}
			BigDecimal salejg = MapUtils.getBigDecimal(map, "salejg");
			if (salejg == null) {
				continue;
			}

			String hb = MapUtils.getString(map, "pricehb");

			// 如果货币类型为空或为CNY
			if (hb == null || hb.trim().equals("CNY")) {
				hb = "CNY";
			} else {
				hb = hb.trim();
			}

			// 如果汇率计算出来小于等于0，默认汇率为1
			double hl = currManager.getCurrrate(hb);
			if (hl <= 0.0) {
				hl = 1.0;
			}

			BigDecimal total = BigDecimal.valueOf(num).multiply(salejg);

			// 全部加到一个Map里面
			BigDecimal totalAll = hbAmountTotalMap.get(hb);
			BigDecimal totalAllCNY = hbAmountTotalMap.get("CNY");
			if (totalAll == null) {
				totalAll = BigDecimal.ZERO;
			}

			if (total.doubleValue() > 0) {
				if (hb.equals("CNY")) {
					hbAmountTotalMap.put(hb, totalAll.add(total));
				} else {
					hbAmountTotalMap.put("CNY", totalAllCNY.add(total
							.multiply(BigDecimal.valueOf(hl))));
					hbAmountTotalMap.put(hb, totalAll.add(total));
				}
			}

		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", rows);
		result.put("currentDate", currentDate);
		result.put("totalAmount", intRowCount);
		result.put("page", pageBean.getPage());
		result.put("pageSize", pageBean.getPageSize());
		result.put("hbAmountTotalMap", hbAmountTotalMap);

		return result;

	}

	public Map<String, Object> updateOrder(WebRuntime runtime) throws SQLException {

		String id1 = runtime.unescape("id");
		String fy_number = runtime.unescape("fy_number");
		
		String sub1 = runtime.unescape("sub");
		String coname1 = runtime.unescape("coname");
		String item1 = runtime.unescape("item");
		String mode1 = runtime.unescape("mode");
		String senddate1 = runtime.unescape("senddate");
		String source1 = runtime.unescape("source");
		String money = runtime.unescape("money");
		String yj1 = runtime.unescape("yj");
		String tbyq1 = runtime.unescape("tbyq");
		String remarks1 = runtime.unescape("remarks");
		remarks1 = DealString.htmlEncode(remarks1);
		remarks1 = com.infoally.util.Replace.strReplace(remarks1, "'", "''");
		String trade = runtime.unescape("trade");
		String datetime1 = runtime.unescape("datetime");
		String tr = runtime.unescape("tr");
		String tr_addr = runtime.unescape("tr_addr");
		tr_addr = DealString.htmlEncode(tr_addr);
		tr_addr = com.infoally.util.Replace.strReplace(tr_addr, "'", "''");
		String tr_man = runtime.unescape("tr_man");
		String tr_tel = runtime.unescape("tr_tel");
		String yf_types = runtime.unescape("yf_types");
		String yf_money = runtime.unescape("yf_money");
		String send_date = runtime.unescape("send_date");
		String custno = runtime.unescape("custno");
		String strSQL = "update subscribe set custno='" + custno + "',fy_number='" + fy_number + "',sub='" + sub1
				+ "',coname='" + coname1 + "',yj='" + yj1 + "',item='" + item1
				+ "',mode='" + mode1 + "',senddate='" + senddate1
				+ "',source='" + source1 + "',money='" + money + "',tbyq='"
				+ tbyq1 + "',trade='" + trade + "',datetime='" + datetime1
				+ "',tr='" + tr + "',tr_addr='" + tr_addr + "',tr_man='"
				+ tr_man + "',tr_tel='" + tr_tel + "',remarks='" + remarks1
				+ "',send_date='" + send_date + "',yf_types='" + yf_types
				+ "',yf_money='" + yf_money + "' where id='" + id1 + "'";
		dbManager.executeUpdate(strSQL);

		String strSQL1 = "update ddpro set pricehb='" + money
				+ "' where ddid='" + id1 + "'";
		dbManager.executeUpdate(strSQL1);
		return this.success();

	}

	public Map<String, Object> getItems(WebRuntime runtime) throws Exception {
		String sql = "select tbyq from subscribe where id=?";
		return dbManager.queryForMap(sql,
				new Object[] { runtime.getParam("id") }, true);
	}

	public Map<String, Object> updateItems(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String items = runtime.unescape("items");

		dbManager.update("update subscribe set tbyq = ? where id = ?",
				new Object[] { items, id });
		return this.success();
	}
	
	/**
	 * 新增订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listSample(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);
		
		String username1 = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean r_sam_view = this.existRight(runtime, "r_sam_view");
		
		String strSQL;
		
		if(r_sam_view){
			strSQL = "select count(*) from sample where state='未提交' and deptjb like '"+deptjb+"%' or state='样品未批准' and deptjb like '"+deptjb+"%'";
		}else
			strSQL = "select count(*) from sample where state='未提交' and man='"+username1+"' or state='样品未批准' and man='"+username1+"'";

		int count = dbManager.getCount(strSQL);
		
		
		 if(r_sam_view){
		strSQL = "select id,money,number,coname,delivery_terms,man,state,datetime from sample where state='未提交' and deptjb like '"+deptjb+"%' or  state='样品未批准' and deptjb like '"+deptjb+"%' order by id desc ";
		}else
		strSQL = "select id,money,number,coname,delivery_terms,man,state,datetime from sample where state='未提交' and man='"+username1+"' or state='样品未批准' and man='"+username1+"' order by id desc ";
		
		List list = dbManager.queryForList(strSQL, true);
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	/**
	 * 待审订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listSampleAudited(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);
		
		String username = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean r_sam_view = this.existRight(runtime, "r_sam_view");
		
		String strSQL="select count(*) from sample";
		
		String sqlWhere;
		
		if(r_sam_view){
			sqlWhere = " where state='样品已批准' and deptjb like '"+deptjb+"%'";
		}else
			sqlWhere = " where state='样品已批准' and  man='"+username+"'";

		int count = dbManager.getCount(strSQL+sqlWhere);
		
		strSQL = "select * from sample";
		List list = dbManager.queryForList(strSQL+sqlWhere, true);
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	/**
	 * 待审订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listSampleAll(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);
		
		String strSQL="select count(*) from sample";
		
		String sqlWhere = " where 1=1 ";
		
		String number = runtime.getParam("number");
		if(StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%"+number+"%'";
		}
		
		String coname = runtime.getParam("coname");
		if(StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%"+coname+"%'";
		}

		int count = dbManager.getCount(strSQL+sqlWhere);
		
		strSQL = "select * from sample";
		List list = dbManager.queryForList(strSQL+sqlWhere, true);
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	/**
	 * 待归还订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listSampleReturn(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);
		
		String username = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean r_sam_view = this.existRight(runtime, "r_sam_view");
		
		String strSQL="select count(*) from sample";
		
		String sqlWhere;
		
		if(r_sam_view){
			sqlWhere = " where (state='已入库') and deptjb like '"+deptjb+"%'";
		}else
			sqlWhere = " where (state='已入库') and  man='"+username+"'";

		int count = dbManager.getCount(strSQL+sqlWhere);
		
		strSQL = "select * from sample";
		List list = dbManager.queryForList(strSQL+sqlWhere, true);
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	private Map<String,Object> getSampleById(String id) throws Exception{
		String sql = "select * from sample where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	/**
	 * 新增样品单产品
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	
	public Map<String,Object> addSamplePro(WebRuntime runtime) throws Exception {
		
		 String ddid1=runtime.getParam("ddid");
		 
		 Map<String,Object> sample = this.getSampleById(ddid1);
		 
		 String epro1=runtime.getParam("epro");
		 int num1=0;
		 String cgpro_num=runtime.getParam("num");
		 if(cgpro_num!=null)
		 num1=Integer.parseInt(cgpro_num);
		 String unit1=runtime.getParam("unit");
		 String cpro1=runtime.getParam("cpro");
		 
		 String cpro2=runtime.getParam("cpro2");
		 
		 String supplier1=(String)sample.get("supplier");
		 String pricehb1=(String)sample.get("pricehb");
		 String salejg1=runtime.getParam("salejg");
		
		 String money1=(String)sample.get("money");
		 String wid=(String)sample.get("wid");
		 String pro_r_date=runtime.getParam("pro_r_date");
		 String remark=runtime.getParam("remark");

		 String strSQL="insert into sam_pro(ddid,epro,num,fypronum,unit,cpro,cpro2,rale_types,rale,supplier,pricehb,salejg,money,fyproall,wid,pro_r_date,pro_sr_date,remark,pro_snum,pro_sc_num,xj_date) values('" + ddid1 + "','" + epro1 + "','" + num1 +"','0','" + unit1 + "','" + cpro1 + "','" + cpro2 + "','','0','" + supplier1 + "','" + pricehb1 + "','" + salejg1 + "','" + money1 + "','no','" + wid + "','" + pro_r_date + "','','" + remark + "',0,0,'')";
		 dbManager.update(strSQL);
		 return this.success();
	}
	
	/**
	 * 将样品单提交审批
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	public Map<String,Object> sampleAudit(WebRuntime runtime) throws Exception {
		
		String id=runtime.getParam("id");
		
	    String state = runtime.getParam("state");
	    String spyj = runtime.getParam("spyj");
	    
	   String strSQL="update sample set state='" + state + "',spdate='" + DateUtil.getCurrentDateStr() + "',spyj='" + spyj + "' where id='" + id + "'";
	 dbManager.update(strSQL);
	 return this.success();
	}
	
	/**
	 * 将样品单提交审批
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	public Map<String,Object> sampleBackToAudit(WebRuntime runtime) throws Exception {
		
		String id1=runtime.getParam("id");
		String strSQL="update  sample set state='样品未批准'    where id='" + id1 + "'";
		dbManager.update(strSQL);
		return this.success();
	}
	
	/**
	 * 
	 * 显示印章的图片
	 * @throws DocumentException 
	 * 
	 */
	public FileView getStampImg(WebRuntime runtime) throws DocumentException{
		FileView view = new FileView();
		
		Document doc = Dom4jUtil.getDoc(runtime.getRealPath("/WEB-INF/config/design/stamp.xml"));
		String img = Dom4jUtil.getProp(doc.getRootElement(), "img");
		view.setFilePath(runtime.getRealPath("/"+img));
		return view;
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listProject(WebRuntime runtime) throws Exception {
		
		String coname = runtime.unescape("coname");
		
		PageBean pageBean = runtime.getPageBean(25);
		
		String sqlWhere = " where states='执行中' ";
		
		List<Object> params = new ArrayList<Object>();
		
		boolean xmview = this.existRight(runtime, "xmview");
		
		if(StringUtils.isNotEmpty(coname)){
			sqlWhere += " and prog_coname like ?";
			params.add("%"+coname+"%");
		}
		
		String man = runtime.getParam("man");
		if(StringUtils.isNotEmpty(man)){
			sqlWhere += " and application = ?";
			params.add(man);
		}
		
		if(xmview){
			sqlWhere += " and deptjb like '"+this.getDeptjb(runtime)+"%'";
		}else{
			sqlWhere += " and application = '"+this.getUsername(runtime)+"'";
		}
		
		String sql = "select top "+pageBean.getTop()+" * from programer "+sqlWhere+" order by id desc";
		List list = dbManager.queryForList(sql,params, true);
		sql = "select count(*) from programer "+sqlWhere;
		int count = dbManager.queryForInt(sql,params.toArray());
		
		return this.getPagingResult(list, pageBean, count);
	}
	
	public Map<String,Object> updateProject(WebRuntime runtime) throws SQLException{
		
		String id=runtime.getParam("id");
		 String programername1=runtime.getParam("programername");
		 String starttime1=runtime.getParam("startdt");
		 String prog_je=runtime.getParam("prog_je");
		 String prog_coname=runtime.getParam("prog_coname");
		 String prog_coman=runtime.getParam("prog_coman");
		 String prog_cotel=runtime.getParam("prog_cotel");
		 String prog_nr=runtime.getParam("prog_nr");
		 if(StringUtils.isEmpty(prog_nr)) {
			 return this.errorMsg("项目简介不能为空");
		 }
		 
		 String situation1=runtime.getParam("situation");
		 String progress1=runtime.getParam("progress");
		 
		 String prog_jg=runtime.getParam("prog_jg");
		
		 String spman1=runtime.getParam("spman");
		 String remark1=runtime.getParam("remark");
		 
		 String xm_types=runtime.getParam("xm_types");
		 

		 String strSQL="update  programer  set  starttime='" + starttime1 +"',spman='" + spman1 + "',programername='"
		 + programername1 + "',prog_je='" + prog_je +"',xm_types='"+xm_types+"',prog_coname='" + prog_coname +"',prog_coman='" 
		 + prog_coman +"',prog_cotel='" + prog_cotel +"',prog_nr='" 
		 + prog_nr +"',situation='" + situation1 + "',progress='" + progress1 + "',prog_jg='" + prog_jg + "',remark='"+remark1+"',last_update_time=?  where  id=?";
		 dbManager.executeUpdate(strSQL,new Object[] {DateUtil.getCurrentDateSimpleStr(),id});
		 return this.success();
	}

	@SuppressWarnings("rawtypes")
	public Map<String,Object> listProjectFinish(WebRuntime runtime) throws Exception {
		String coname = runtime.unescape("coname");
		PageBean pageBean = runtime.getPageBean(25);
		
		String sqlWhere = " where states='项目完成' ";
		
		List<Object> params = new ArrayList<Object>();
		
		boolean xmview = this.existRight(runtime, "xmview");
		
		if(StringUtils.isNotEmpty(coname)){
			sqlWhere += " and prog_coname like ?";
			params.add("%"+coname+"%");
		}
		
		String man = runtime.getParam("man");
		if(StringUtils.isNotEmpty(man)){
			sqlWhere += " and application = ?";
			params.add(man);
		}
		
		if(xmview){
			sqlWhere += " and deptjb like '"+this.getDeptjb(runtime)+"%'";
		}else{
			sqlWhere += " and application = '"+this.getUsername(runtime)+"'";
		}
		
		String sql = "select top "+pageBean.getTop()+" * from programer "+sqlWhere+" order by id desc";
		List list = dbManager.queryForList(sql,params, true);
		sql = "select count(*) from programer "+sqlWhere;
		int count = dbManager.queryForInt(sql,params.toArray());
		
		return this.getPagingResult(list, pageBean, count);
	}
	
	private BigDecimal getSaleTotal(Transaction trans, String id) throws Exception {
		String sql = "select sum(num * salejg) from ddpro where ddid = ?";
		BigDecimal total = trans.queryForBigDecimal(sql, new Object[]{ id });
		return total;
	}

	/**
	 * 合同审批
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> audit(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		Transaction trans = this.getTransaction();
		
		try {
			String sql = "select  * from subscribe where id=?";
			Map<String,Object> subscribe = trans.queryForMap(sql, new Object[]{id},true);
			if(subscribe==null){
				return this.errorMsg("合同ID不存在");
			}
			BigDecimal total = this.getSaleTotal(trans, id);
			
			String ddnumber = (String) subscribe.get("number");
			String spman1 = (String) subscribe.get("spman");
			String spdate1 = DateUtil.getCurrentDateSimpleStr();
			String state1 = runtime.getParam("state").trim();
			String spyj1 = runtime.getParam("spyj");
			String ddyf = (String) subscribe.get("ddyf");
			java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			String currentDate = simple.format(new java.util.Date());
			String name1 = this.getUsername(runtime);
			String dept = this.getDept(runtime);
			String deptjb = this.getDeptjb(runtime);
			String saleMan = "";
			
			// 是否需要复审
			String fif = (String) subscribe.get("fif");
			
			String state;
			
			if(state1.equals("1")){
				// 如果无需审批的情况下
				if("否".equals(fif.trim())){
					state = "待出库";
					String coname1 = (String) subscribe.get("coname");
					BigDecimal yj1 = (BigDecimal) subscribe.get("yj");
					String money1 = (String) subscribe.get("money");
					String item = (String) subscribe.get("item");
					String mode = (String) subscribe.get("mode");
					Integer datet = (Integer) subscribe.get("source");
					String co_number = (String) subscribe.get("senddate");
					String dept1 = (String) subscribe.get("dept");
					String deptjb1 = (String) subscribe.get("deptjb");
					BigDecimal yf = (BigDecimal) subscribe.get("yf_money");
					String ware_man = (String) subscribe.get("ware_man");
					String datetime1 = (String) subscribe.get("send_date");
					saleMan= (String) subscribe.get("man");
					String strSQLcl = "update client set cotypes='现有客户' where coname=?";
					trans.update(strSQLcl,new Object[]{coname1});
					
					String strSQL8 = "delete from gathering where orderform='"
							+ ddnumber + "'";
					trans.executeUpdate(strSQL8);
					
					// 预收款
					String gatheringStatus;
					if ("是".equals(ddyf)) {// yy
						gatheringStatus = "预收款";
					} else {
						gatheringStatus = "待收款";
					}
					String strSQLsk = "insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,rate,i_man,sendcompany,remark,note, total) values('"
							+ id
							+ "','"
							+ ddnumber
							+ "','"
							+ ddnumber
							+ "','"
							+ coname1
							+ "','"
							+ datetime1
							+ "','"
							+ datetime1
							+ "','"
							+ yj1
							+ "',?,'"
							+ mode
							+ "','"
							+ datet
							+ "','"
							+ money1
							+ "','0','"
							+ yf
							+ "','待开发票','"
							+ saleMan
							+ "','"
							+ dept1
							+ "','"
							+ deptjb1
							+ "','"
							+ co_number
							+ "','" + item + "','0','','','', ?)";
					trans.update(strSQLsk, new Object[] {gatheringStatus, total });
					String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
							+ ware_man
							+ "','','','新增:编号为:"
							+ ddnumber
							+ "合同','审批内容:"
							+ spyj1
							+ "','"
							+ saleMan
							+ "','"
							+ deptjb1
							+ "','"
							+ dept1
							+ "','"
							+ currentDate
							+ "','已发送','','','')";
					trans.executeUpdate(strSQLs);
					
				}else{
					state = "待复审";
				}
			}else{
				state = "未批准";
			}
			
			String strSQL = "update subscribe set state='" + state + "',spman='"
					+ spman1 + "',spdate='" + spdate1 + "',spyj='" + spyj1
					+ "' where id='" + id + "'";
			trans.executeUpdate(strSQL);
			
			String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
					+ saleMan
					+ "','','','合同审批结果:编号为:"
					+ ddnumber
					+ "当前状态:"
					+ state
					+ "','审批内容:"
					+ spyj1
					+ "','"
					+ name1
					+ "','"
					+ deptjb
					+ "','"
					+ dept + "','" + currentDate + "','已发送','','','')";
			trans.executeUpdate(strSQLs);
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		return this.success();

	}
	
	public Map<String,Object> updateSub(WebRuntime runtime) throws Exception {
		
		String sub = runtime.getParam("sub");
		String id = runtime.getParam("id");
		
		String strSQL="update  subscribe set sub=?    where id=?";
		dbManager.executeUpdate(strSQL,new Object[]{sub,id});
		return this.success();
	}
	
	/**
	 * 合同审批
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> auditSecond(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		Transaction trans = this.getTransaction();
		
		try {
			String sql = "select  * from subscribe where id=?";
			Map<String,Object> subscribe = trans.queryForMap(sql, new Object[]{id},true);
			if(subscribe==null){
				return this.errorMsg("合同ID不存在");
			}
			BigDecimal total = this.getSaleTotal(trans, id);
			String ddnumber = (String) subscribe.get("number");
			String spman1 = (String) subscribe.get("spman");
			String spdate1 = DateUtil.getCurrentDateSimpleStr();
			String state1 = runtime.getParam("state").trim();
			String spyj1 = runtime.getParam("spyj");
			String ddyf = (String) subscribe.get("ddyf");
			java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			String currentDate = simple.format(new java.util.Date());
			String name1 = this.getUsername(runtime);
			String dept = this.getDept(runtime);
			String deptjb = this.getDeptjb(runtime);
			String saleMan = "";
			
			String state;
			
			if(state1.equals("1")){
				
				state = "待出库";
				String coname1 = (String) subscribe.get("coname");
				BigDecimal yj1 = (BigDecimal) subscribe.get("yj");
				String money1 = (String) subscribe.get("money");
				String item = (String) subscribe.get("item");
				String mode = (String) subscribe.get("mode");
				Integer datet = (Integer) subscribe.get("source");
				String co_number = (String) subscribe.get("senddate");
				String dept1 = (String) subscribe.get("dept");
				String deptjb1 = (String) subscribe.get("deptjb");
				BigDecimal yf = (BigDecimal) subscribe.get("yf_money");
				String ware_man = (String) subscribe.get("ware_man");
				String datetime1 = (String) subscribe.get("send_date");
				saleMan= (String) subscribe.get("man");
				String strSQLcl = "update client set cotypes='现有客户' where coname=?";
				trans.update(strSQLcl,new Object[]{coname1});
				
				String strSQL8 = "delete from gathering where orderform='"
						+ ddnumber + "'";
				dbManager.executeUpdate(strSQL8);
				
				String gatherStatus;
				
				// 预收款
				if ("是".equals(ddyf)) {// yy
					gatherStatus = "预收款";
				} else {
					gatherStatus = "待收款";
				}
				String strSQLsk = "insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,rate,i_man,sendcompany,remark,note) values('"
						+ id
						+ "','"
						+ ddnumber
						+ "','"
						+ ddnumber
						+ "','"
						+ coname1
						+ "','"
						+ datetime1
						+ "','"
						+ datetime1
						+ "','"
						+ yj1
						+ "',?,'"
						+ mode
						+ "','"
						+ datet
						+ "','"
						+ money1
						+ "','0','"
						+ yf
						+ "','待开发票','"
						+ saleMan
						+ "','"
						+ dept1
						+ "','"
						+ deptjb1
						+ "','"
						+ co_number
						+ "','" + item + "','0','','','')";
				trans.update(strSQLsk, new Object[] {gatherStatus, total});
				
				String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
						+ ware_man
						+ "','','','新增:编号为:"
						+ ddnumber
						+ "合同','审批内容:"
						+ spyj1
						+ "','"
						+ saleMan
						+ "','"
						+ deptjb1
						+ "','"
						+ dept1
						+ "','"
						+ currentDate
						+ "','已发送','','','')";
				dbManager.executeUpdate(strSQLs);
				
			}else{
				state = "未批准";
			}
			
			String strSQL = "update subscribe set state='" + state + "',spman='"
					+ spman1 + "',spdate='" + spdate1 + "',spyj='" + spyj1
					+ "' where id='" + id + "'";
			dbManager.executeUpdate(strSQL);
			
			String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
					+ saleMan
					+ "','','','合同审批结果:编号为:"
					+ ddnumber
					+ "当前状态:"
					+ state
					+ "','审批内容:"
					+ spyj1
					+ "','"
					+ name1
					+ "','"
					+ deptjb
					+ "','"
					+ dept + "','" + currentDate + "','已发送','','','')";
			dbManager.executeUpdate(strSQLs);
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}

		return this.success();

	}
	
	/**
	 * 申请出库
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sampleToCheckOut(WebRuntime runtime) throws Exception {
		
		 String id = runtime.getParam("id");
		 
		 String sql = "update sample set state='待出库' where id = ?";
		 
		 dbManager.update(sql, new Object[]{id});
		 
		 return this.success();
		 
		
	}
	
	/**
	 * 申请入库
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> sampleToCheckIn(WebRuntime runtime) throws Exception {
		
		 String id = runtime.getParam("id");
		 
		 String sql = "update sample set state='待入库' where id = ?";
		 
		 dbManager.update(sql, new Object[]{id});
		 
		 return this.success();
		 
		
	}
	
	@SuppressWarnings("rawtypes")
	public List listPartner(WebRuntime runtime) throws Exception {
		String sql = "select * from client where  cotypes='合作伙伴'";
		return 
				dbManager.queryForList(sql, true);
	}
	
	public Map<String,Object> delPartner(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "delete from client where clientid = ?";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	public Map<String,Object> addPartner(WebRuntime runtime) throws Exception {

		String coname1 = runtime.getParam("coname");
		String coaddr1 = runtime.getParam("coaddr");
		String copost1 = runtime.getParam("copost");
		String city1 = runtime.getParam("city");
		String province1 = runtime.getParam("province");
		String cofrdb1 = runtime.getParam("cofrdb");
		String cozzxs1 = runtime.getParam("cozzxs");
		String cozczb1 = runtime.getParam("cozczb");
		String coyyzz1 = runtime.getParam("coyyzz");
		

		String tradetypes1 = runtime.getParam("tradetypes");
		String cokhjb1 = runtime.getParam("cokhjb");
		
		String coyhzh1 = runtime.getParam("coyhzh");
		String coclrq1 = runtime.getParam("coclrq");
		String cotel1 = runtime.getParam("cotel");
		String cofax1 = runtime.getParam("cofax");
		String codzyj1 = runtime.getParam("codzyj");
		String conet1 = runtime.getParam("conet");
		String cosyhb1 = runtime.getParam("cosyhb");
		String cojsfs1 = runtime.getParam("cojsfs");
		
		String paydate1 = runtime.getParam("paydate");
		
		String sqls = "select count(*) from client where coname='" + coname1
				+ "'";
		
		int count = dbManager.getCount(sqls);
		if (count > 0) {
			return this.errorMsg("合作伙伴名称重复!");
		} else {
			
			String sql = "select max(clientid)  from client";
			
			int ddid = dbManager.queryForInt(sql);
			InsertSegmentCombine insertSegmentCombine = new InsertSegmentCombine();
			insertSegmentCombine.setTable("client");
			insertSegmentCombine.addSegment("co_number", String.valueOf(ddid));
			insertSegmentCombine.addSegment("coname", coname1);
			insertSegmentCombine.addSegment("coaddr", coaddr1);
			insertSegmentCombine.addSegment("copost", copost1);
			insertSegmentCombine.addSegment("city", city1);
			insertSegmentCombine.addSegment("province", province1);
			insertSegmentCombine.addSegment("cofrdb", cofrdb1);
			insertSegmentCombine.addSegment("cozzxs", cozzxs1);
			insertSegmentCombine.addSegment("cozczb", cozczb1);
			insertSegmentCombine.addSegment("coyyzz", coyyzz1);
			insertSegmentCombine.addSegment("cotypes", "合作伙伴");
			insertSegmentCombine.addSegment("tradetypes", tradetypes1);
			insertSegmentCombine.addSegment("cokhjb", cokhjb1);
			insertSegmentCombine.addSegment("cokhyh", coyhzh1);
			insertSegmentCombine.addSegment("coclrq", coclrq1);
			insertSegmentCombine.addSegment("cotel", cotel1);
			insertSegmentCombine.addSegment("cofax", cofax1);
			insertSegmentCombine.addSegment("codzyj", codzyj1);
			insertSegmentCombine.addSegment("conet", conet1);
			insertSegmentCombine.addSegment("cosyhb", cosyhb1);
			insertSegmentCombine.addSegment("cojsfs", cojsfs1);
			insertSegmentCombine.addSegment("paydate", paydate1);

			String strSQL = insertSegmentCombine.getInsertSql();
			
			dbManager.update(strSQL);

		}
		
		return this.success();
	}
	
	/**
	 * 查看调货详细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getDhList(WebRuntime runtime) throws Exception {
		return saleDao.getDhList();
	}
	
	/**
	 * 提交审批
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> toAudit(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		
		try{
			saleService.toAudit(runtime.getBasePath(), id, super.getUserinfo(runtime));
		}catch(Exception e){
			return this.errorMsg(e.toString());
			
		}
		
		return this.success();
	}
	
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> approvingList(WebRuntime runtime) throws Exception {
		
		String name1 = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean subview = this.existRight(runtime, "subview");
		
		String coname = runtime.getParam("coname");
	
		String sqlWhere = " where  (state='待审批' or state='待复审') ";
		
		if (subview) {
			sqlWhere += " and deptjb like '"
					+ deptjb
					+ "%'";
		} else
			sqlWhere += " and (cwman='"
					+ name1
					+ "' or man='"
					+ name1
					+ " or spman='"
					+ name1
					+ "')";
		
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}
		
		System.out.println("approving list,,," + sqlWhere);

		int count = dbManager.getCount("select count(*) from subscribe " + sqlWhere);
		List list = dbManager.queryForList("select * from subscribe " + sqlWhere + " order by number desc", true);
		
		return this.getPagingResult(list, runtime, count);

	}
	
	
	@SuppressWarnings("rawtypes")
	public List listProByNumber(WebRuntime runtime) throws Exception {
		
		String number = runtime.getParam("number");
		String sql = "select p.* from ddpro p left join subscribe s on p.ddid = s.id where s.number = ?";
		return dbManager.queryForList(sql, new Object[] {number},true);
		
	}
	
	/**
	 * 
	 * 已审订单查询，与已审订单表查询的sql不同，所以独立一个方法来获取
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listInquiry(WebRuntime runtime) throws Exception {

		String strSQL;
		String username1 = this.getUsername(runtime);

		String deptjb = this.getDeptjb(runtime);

		boolean quoteview = this.existRight(runtime, "r_cus_xj_view");

		String sqlWhere = "";

		strSQL = "select count(*) from ixjview ";

		// 判断是否有查看所有询价单的权限
		if (quoteview) {
			sqlWhere += " where  deptjb  like '" + deptjb + "%' ";
		} else {
			sqlWhere += " where  man='" + username1 + "' ";
		}

		String pro_model = runtime.getParam("pro_model");
		if (StringUtils.isNotEmpty(pro_model)) {
			sqlWhere += " and  product like '%" + pro_model + "%' ";
		}

		int count = dbManager.getCount(strSQL + sqlWhere);

		PageBean pageBean = runtime.getPageBean();
		int top = pageBean.getTop();

		strSQL = "select  top "
				+ top
				+ " id,states,coname,cotel,product,cpro,pro_p_year,supplier,quantity,price,price_hb,p_price,p_hb,q_price,pro_report,xj_date,man from ixjview ";

		List list = dbManager.queryForList(strSQL + sqlWhere
				+ "  order by quotedatetime desc", true);
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	/**
	 * 
	 * 报价单列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listQuote(WebRuntime runtime) throws Exception {
		
		String strSQL;
		
		PageBean pageBean = runtime.getPageBean();
		String username1 = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);
		
		boolean quoteview = super.existRight(runtime, "quoteview");

		String sqlWhere = "";

		String pro_model = runtime.getParam("model");
		if (StringUtils.isNotEmpty(pro_model)) {
			sqlWhere += " and id in (select quoteid from quoteproduct where product like '%"
					+ pro_model + "%')";
		}

		String number = runtime.getParam("number");
		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		String coname = runtime.getParam("coname");
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}
		
		String status = runtime.getParam("status");
		if (StringUtils.isNotEmpty(status)) {
			if (status.equals("approving")) {
				status = "待审批";
			}
			sqlWhere += " and states = '" + status + "'";
		}

		if (quoteview) {
			strSQL = "select count(*) from quote  where deptjb  like '"
					+ deptjb + "%' ";
		} else
			strSQL = "select count(*) from quote where man='" + username1 + "'";
		
		int intRowCount = dbManager.queryForInt(strSQL);
		
		if (quoteview) {
			strSQL = "select top "+pageBean.getTop()+" id,spman, number,states,linkman,coname,quotedatetime,man from quote  where deptjb  like '"
					+ deptjb + "%'  " + sqlWhere + " order by id desc";
		} else
			strSQL = "select top "+pageBean.getTop()+" id,spman, number,states,linkman,coname,quotedatetime,man from quote  where man='"
					+ username1 + "'  " + sqlWhere + "  order by id desc";
		
		List list = dbManager.queryForList(strSQL, true);
		
		pageBean.setTotalAmount(intRowCount);
		List rows = this.getRows(list, pageBean);

		return this.getPagingResult(rows, pageBean);
	}
	
	public Map<String,Object> listRefund(WebRuntime runtime) throws Exception {
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageBean", runtime.getPageBean());
		
		try{
			Map<String,Object> res = saleService.listRefund(param);
			return res;
		}catch(Exception ex){
			logger.error("获取退货列表失败", ex);
			return this.errorMsg(ex.toString());
		}
		
		
	}

	public Map<String,Object> listRefundApproving(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageBean", this.getPageBean(request, 20));
		
		try{
			Map<String,Object> res = saleService.listRefundApproving(param);
			return res;
		}catch(Exception ex){
			logger.error("获取退货列表失败", ex);
			return this.errorMsg(ex.toString());
		}
	}

	public Map<String,Object> listRefundApproved(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageBean", this.getPageBean(request, 20));
		
		try{
			Map<String,Object> res = saleService.listRefundApproved(param);
			return res;
		}catch(Exception ex){
			logger.error("获取退货列表失败", ex);
			return this.errorMsg(ex.toString());
		}
		
	}
	
	public Map<String,Object> listRefundDeleted(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageBean", this.getPageBean(request, 20));
		
		try{
			Map<String,Object> res = saleService.listRefundApproved(param);
			return res;
		}catch(Exception ex){
			logger.error("获取退货列表失败", ex);
			return this.errorMsg(ex.toString());
		}
		
	}
	
	/**
	 * 退货审批
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> approveRefund(WebRuntime runtime) throws Exception {

		Transaction trans = Transaction.createTrans(DatasourceStore.getDatasource("default"));

		String id1 = runtime.getParam("id");
		String username = this.getUsername(runtime);
		String spdate1 = DateUtil.getCurrentDateSimpleStr();
		String state1 = runtime.getParam("state");
		String spyj1 = runtime.getParam("spyj");
		
		try{
			String strSQL = "update th_table set state='" + state1 + "',spman='"
					+ username + "',spdate='" + spdate1 + "',spyj='" + spyj1
					+ "' where id='" + id1 + "'";
			trans.executeUpdate(strSQL);
			

			if (state1.equals("已批准")) {

				String th_number = "";
				String coname = "";
				Date dd_date = null;
				String money1 = "";
				String sales_man = "";
				String co_number = "";
				String strSQLprost = "select number,sub,coname,datetime,money,man,senddate from th_table where id=?";
				Map<String,Object> map = trans.queryForMap(strSQLprost,new Object[] {id1},true);
				if (map!=null) {
					th_number = (String)map.get("number");
					coname = (String)map.get("coname");
					dd_date = (Date)map.get("datetime");
					money1 = (String)map.get("money");
					sales_man = (String)map.get("man");
					co_number = (String)map.get("senddate");
				}
				
				String dept = this.getDept(runtime);
				String deptjb = this.getDeptjb(runtime);

				String strSQLsk = "insert into gathering_refund(fyid,invoice,orderform,coname,yjskdate,sjdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,i_man) values('"
						+ id1
						+ "','"
						+ th_number
						+ "','"
						+ th_number
						+ "','"
						+ coname
						+ "','"
						+ dd_date
						+ "','"
						+ dd_date
						+ "','"
						+ dd_date
						+ "','0','已批准','退货','0','"
						+ money1
						+ "','0','0','','"
						+ sales_man
						+ "','"
						+ dept
						+ "','"
						+ deptjb + "','" + co_number + "',0)";
				trans.executeUpdate(strSQLsk);
			}
			trans.commit();

		}catch(Exception ex){
			trans.rollback();
		}finally{
			trans.close();
		}
		return this.success();
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception {
		
		String number = NumberFactory.generateNumber("ONK-");

		String coname = runtime.getParam("coname");
		String sub = runtime.getParam("sub");
		String custno = runtime.getParam("custno");
		String item = runtime.getParam("item");
		String mode = runtime.getParam("mode");
		String source1 = runtime.getParam("source");
		String trade1 = runtime.getParam("trade");
		String man = this.getUsername(runtime);
		String yj = runtime.getParam("yj");
		
		String senddate1 = runtime.getParam("senddate");
		String tbyq = runtime.getParam("tbyq");
		
		String remarks = runtime.getParam("remarks");
		String tr = runtime.getParam("tr");
		String tr_addr = runtime.getParam("tr_addr");
		String tr_man = runtime.getParam("contact");
		String tr_tel = runtime.getParam("tr_tel");
		String yf_types = runtime.getParam("yf_types");
		String yf_money = runtime.getParam("yf_money");
		String send_date = runtime.getParam("send_date");
		String fy_number = runtime.getParam("fy_number");
		
		String other_fy = runtime.getParam("other_fy");

		int ddid = 1;
		String role = this.getRole(runtime);
		String dept = this.getDept(runtime);
		String deptjb = this.getDeptjb(runtime);
		

		String sqlddman = "select  * from ddsp where dept='" + dept
				+ "' and role='" + role + "' or dept='全部' or dept='" + dept
				+ "' and role='全部'";
		Map<String,Object> sp = dbManager.queryForMap(sqlddman, true);
		
		if(sp==null) {
			return this.errorMsg("未定义合同审批流程!");
		}

		String dd_man = (String)sp.get("dd_man");
		String fif = (String)sp.get("fif");
		String fspman = (String)sp.get("fspman");

		String strSQL = "insert into subscribe(number,man,sub,coname,yj,money,item,mode,source,trade,habitus,datetime,senddate,tbyq,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,dept,"
				+ "deptjb,tr,tr_addr,tr_man,tr_tel,yf_types,yf_money,fy_number,ware_remark,p_states,sub_time,s_check,cg_man,send_date,other_fy,custno) "
				+ "values(?,?,?,?,?,'CNY',?,?,?,?,'订单执行中',getdate(),?,?,?,'未提交',?,'','',?,?,'','',?,?,?,?,?,?,?,?,?,'','','','无','共享',?,?,?)";
		dbManager.executeUpdate(strSQL,new Object[] {number,man, sub,coname, yj, item, mode,source1,trade1, senddate1, tbyq,remarks,dd_man,
				fif,fspman,dept,deptjb,tr,tr_addr,tr_man,tr_tel,yf_types,yf_money,fy_number,send_date,other_fy, custno});
		
		String sql = "select max(id)  from subscribe";
		ddid = dbManager.queryForInt(sql);

		return this.success("ddid",ddid);
		
	}
	
	/**
	 * 通过报价票生成销售订单
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> createSaleByQuote(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		Map<String,Object> quote = dbManager.queryForMap("select * from quote where id = ?",new Object[] {id}, true);
		
		String coname = (String) quote.get("coname");
		String money = (String)quote.get("hb");
		String mode = (String) quote.get("mode");
		
		Userinfo userinfo = this.getUserinfo(runtime);
		String address = (String) quote.get("coaddr");
		String deliveryLinkMan = (String) quote.get("linkman");
		String deliveryTel = (String) quote.get("cotel");
		
		List list = quoteDao.getList(id);
		
		Sale sale = new Sale();
		sale.setConame(coname);
		sale.setSubck("");
		sale.setSub("");
		sale.setCurrentType(money);
		sale.setItem("");
		sale.setMode(mode);
		sale.setTrade("");
		sale.setCustno("");
		sale.setSendDate("");
		sale.setTerms("");
		sale.setRemark("");
		sale.setTr("");
		sale.setAddress(address);
		sale.setDeliveryLinkMan(deliveryLinkMan);
		sale.setDeliveryTel(deliveryTel);
		sale.setDeliveryFeeType("");
		sale.setFy_number("");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			SalePro pro = new SalePro();
			String product = (String) map.get("product");
			pro.setEpro(product);
			Integer num = (Integer) map.get("quantity");
			pro.setNum(num);
		
			BigDecimal salejg = (BigDecimal) map.get("price");
			pro.setSalejg(salejg);
			BigDecimal selljg = (BigDecimal) map.get("selljg");
			pro.setSelljg(selljg);
			String supplier = (String) map.get("supplier");
			pro.setSupplier(supplier);
			
			String moneyPro = (String) map.get("pricehb");
			pro.setHb(moneyPro);
			String unit = (String) map.get("unit");
			pro.setUnit(unit);
			sale.addPro(pro);
			
		}
		int ddid = saleService.create(sale,userinfo);

		return this.success("ddid",ddid);
		
	}
	
	public Map<String,Object> savePro(WebRuntime runtime) throws Exception {
		int quoteid=runtime.getInt("ddid");
		
		Map<String,Object> sale = this.getDetail(quoteid);
		String money = (String) sale.get("money");
		int t=0;
		 String i1=runtime.getParam("i");
		 if(i1!=null)
		  t=Integer.parseInt(i1);
		    for(int i=1;i<=t;i++)
		    {
		 String id=runtime.getParam("id"+i);
		      if(id!=null){
		 String pro_name=runtime.getParam("pro_name"+i);
		    if(pro_name!=null){
		   pro_name=com.infoally.util.Replace.strReplace(pro_name,"'","’");
		   pro_name=com.infoally.util.Replace.strReplace(pro_name,"\"","”");
		    }
		 String  pro_model=runtime.getParam("pro_model"+i);
		    if(pro_model!=null){
		   pro_model=com.infoally.util.Replace.strReplace(pro_model,"'","’");
		   pro_model=com.infoally.util.Replace.strReplace(pro_model,"\"","”");
		    }
		
		 String  num=runtime.getParam("num"+i);
		  String punit=runtime.getParam("punit"+i);
	
		  String qprice=runtime.getParam("qprice"+i);
		  
		  if(qprice!=null){
			  qprice.replaceAll(",", "");
		  }
		  String supplier=runtime.getParam("supplier"+i);
		  String pro_tr=runtime.getParam("pro_tr"+i);
		  String fz=runtime.getParam("fz"+i);
		  String selljg=runtime.getParam("selljgs"+i);
		  String hl = runtime.getParam("hl"+i);
		 
		  
		  String money2=runtime.getParam("money2"+i);
		 
		  String mpn=runtime.getParam("mpn"+i);
		  
		  String pro_remark=runtime.getParam("pro_remark"+i);
		    if(pro_remark!=null){
		   pro_remark=com.infoally.util.Replace.strReplace(pro_remark,"'","’");
		   pro_remark=com.infoally.util.Replace.strReplace(pro_remark,"\"","”");
		    }

		    qprice = qprice.replaceAll(",", "");
		    
		   
		 String strSQL="update ddpro set fypronum='" + fz + "',supplier='" + supplier + "',epro='" + pro_model +"',cpro='" + pro_name +"',num='" + num + "',unit='" + punit + "',salejg='"+qprice+"',selljg='"+selljg+"',s_tr_date='"+pro_tr+"',remark='" + pro_remark + "',money2='"+money2+"',mpn='"+mpn+"',hl="+hl+"  where  id='"+id+"'";
		 
		 
		   dbManager.executeUpdate(strSQL);
		   
		   }}
		    
		    String  pro_model=runtime.getParam("2pro_model").trim();
		   
		   	if(StringUtils.isNotEmpty(pro_model)){ 
		   		
		   		SalePro pro = new SalePro();
		   		pro.setEpro(pro_model);
		   		pro.setDdid(quoteid);
		   		int  num=runtime.getInt("2num");
		   		pro.setNum(num);
		   		String punit=runtime.getParam("2punit");
			  	pro.setUnit(punit);
			  	String qprice=runtime.getParam("2qprice");
			  	pro.setSalejg(BigDecimalUtils.toBigDecimal(qprice));
				  String supplier=runtime.getParam("2supplier");
				  pro.setMoney(money);
				  pro.setSupplier(supplier);
				  String pro_tr=runtime.getParam("2pro_tr");
				  pro.setPro_tr(pro_tr);
				  String mpn=runtime.getParam("2mpn");
				  pro.setMpn(mpn);
				  
				  String pro_remark=runtime.getParam("2pro_remark");
				  pro.setRemark(pro_remark);
			  
		   		saleService.addPro(pro);
		   	}
		   	
		   	return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List getSaleUserList() throws Exception {
		return userService.getSaleUserList();
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		Map<String,Object> res = this.getDetail(id);
		return this.success("data", res);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listProduct(@Param("id") String id, PageBean pageBean) throws Exception {
		String sqlWhere = " where ddid = ?";
		String sql = "select * from ddpro ";
		List list = dbManager.queryForList(sql + sqlWhere, new Object[] {id}, true);
		sql = "select count(*) from ddpro ";
		int count = dbManager.queryForInt(sql + sqlWhere, new Object[] {id});
		return this.getPagingResult(list, pageBean, count);
	}

}
