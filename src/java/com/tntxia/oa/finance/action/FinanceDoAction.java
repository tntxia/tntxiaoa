package com.tntxia.oa.finance.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.excel.ExcelData;
import com.tntxia.excel.ExcelRow;
import com.tntxia.excel.ExcelUtils;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.finance.dao.FinanceLightDao;
import com.tntxia.oa.finance.service.FinanceAccountService;
import com.tntxia.oa.finance.service.FinanceLightService;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.oa.system.entity.FinanceAccountDetail;
import com.tntxia.oa.util.DateUtil;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.DatasourceStore;

import infocrmdb.DealString;

public class FinanceDoAction extends CommonDoAction {

	public static Logger logger = Logger.getLogger(FinanceDoAction.class);

	private DBManager dbManager = this.getDBManager();

	private FinanceLightService financeService = new FinanceLightService();
	
	private FinanceAccountService financeAccountService = new FinanceAccountService();
	
	private SaleLightService saleService = new SaleLightService();
	
	private FinanceLightDao financeDao = new FinanceLightDao();

	@SuppressWarnings("rawtypes")
	private List getList(PageBean pageBean, String sqlWhere) throws Exception {
		
		String top="";
		
		if(pageBean!=null){
			top = "top " + pageBean.getTop();
		}
		
		String sql = "select "+top+" s.id,s.number,s.custno,s.coname,item from subscribe s left join gathering g on s.id = g.fyid where (g.states is null or g.states <> '已收全部款') "
				+ sqlWhere + " order by s.id desc";
		return dbManager.queryForList(sql, true);
		
	}

	private int getCount(String sqlWhere) throws Exception {
		String sql = "select count(*) from subscribe s where 1= 1 " + sqlWhere;
		return dbManager.queryForInt(sql);
	}

	/**
	 * 未收款订单列表
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getNoGatherList(WebRuntime runtime)
			throws Exception {
		
		PageBean pageBean = runtime.getPageBean(20);
		String sqlWhere = " and (s.state='预收款' or s.state='已发运' or s.state='待出库' or s.state='待收款' or s.state='已备货' or s.state='部分退货')";
		
		String number = runtime.getParam("number");
		if(StringUtils.isNotEmpty(number)) {
			sqlWhere += " and s.number like '%"+number+"%'";
		}
	
		List list = this.getList(pageBean, sqlWhere);
		List rows = this.getRows(list, pageBean);
		
		for(int i=0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			saleService.setTotal(map);
		}
		
		int count = this.getCount(sqlWhere);
		pageBean.setTotalAmount(count);
		return this.getPagingResult(rows, pageBean);
		
	}
	
	/**
	 * 未收款订单列表   统计信息
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getNoGatherListTotal(WebRuntime runtime)
			throws Exception {
		
		
		String sqlWhere = " and (state='预收款' or state='已发运' or state='待出库' or state='待收款' or state='已备货' or state='部分退货') and habitus='订单执行中' and (gather_status is null or gather_status <> 1)";
		
		String man = runtime.getParam("man");
		
		if(StringUtils.isNotEmpty(man)){
			sqlWhere += " and man='"+man+"'";
		}
		
		List list = this.getList(null, sqlWhere);
		
		saleService.fillAllSaleTotal(list);
		
		BigDecimal totalAll = BigDecimal.ZERO;
		BigDecimal totalLeft = BigDecimal.ZERO;
		BigDecimal totalOut = BigDecimal.ZERO;
		
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			
			BigDecimal left = (BigDecimal) map.get("left");
			BigDecimal stotal = (BigDecimal) map.get("stotal");
			BigDecimal total = (BigDecimal) map.get("total");
			if(left!=null)
				totalLeft = totalLeft.add(left);
			if(stotal!=null)
				totalOut = totalOut.add(stotal);
			if(total!=null)
				totalAll = totalAll.add(total);
		}
		
		Map<String,Object> total = new HashMap<String,Object>();
		total.put("totalLeft", totalLeft);
		total.put("totalOut", totalOut);
		total.put("total", totalAll);
		return total;
		
	}
	
	

	/**
	 * 修改收款状态
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 * 
	 */
	public Map<String, Object> changeGatherStatus(WebRuntime runtime)
			throws Exception {

		String id = runtime.getParam("id");

		String sql = "update subscribe set gather_status=1 where id = ?";

		dbManager.executeUpdate(sql, new Object[] { id });

		return this.success();

	}
	
	/**
	 * 欠款信息列表 统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String, Object> listToGather(WebRuntime runtime)
			throws SQLException {

		try {

			Map<String, Object> param = new HashMap<String, Object>();
			PageBean pageBean = runtime.getPageBean(50);
			param.put("pageBean", pageBean);

			String depts = runtime.unescape("depts");
			String sendcompany = runtime.unescape("sendcompany");
			String coname = runtime.unescape("coname");
			String fpnum = runtime.unescape("fpnum");
			String sdate = runtime.unescape("sdate");
			String edate = runtime.unescape("edate");

			param.put("depts", depts);
			param.put("sendcompany", sendcompany);
			param.put("coname", coname);

			param.put("fpnum", fpnum);
			param.put("sdate", sdate);
			param.put("edate", edate);

			Map<String, Object> res = financeService.listToGather(param);
			return res;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());

		}

	}
	
	/**
	 * 欠款信息列表 统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String, Object> listTaxWait(WebRuntime runtime)
			throws SQLException {

		try {

			Map<String, Object> param = new HashMap<String, Object>();
			PageBean pageBean = runtime.getPageBean(50);

			Map<String, Object> res = financeService.listTaxWait(param,pageBean);
			return res;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());

		}

	}
	
	/**
	 * 欠款信息列表 统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String, Object> listTaxFinish(WebRuntime runtime)
			throws SQLException {

		try {

			Map<String, Object> param = new HashMap<String, Object>();
			PageBean pageBean = runtime.getPageBean(50);

			Map<String, Object> res = financeService.listTaxFinish(param,pageBean);
			return res;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());

		}

	}

	/**
	 * 欠款信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String, Object> gatherStatist(WebRuntime runtime)
			throws SQLException {

		try {

			Map<String, Object> param = new HashMap<String, Object>();
			PageBean pageBean = runtime.getPageBean(50);
			param.put("pageBean", pageBean);

			String depts = runtime.unescape("depts");
			String sendcompany = runtime.getParam("sendcompany");
			String coname = runtime.getParam("coname");
			String fpnum = runtime.getParam("fpnum");
			String sdate = runtime.getParam("sdate");
			String edate = runtime.getParam("edate");

			param.put("depts", depts);
			param.put("sendcompany", sendcompany);
			param.put("coname", coname);

			param.put("fpnum", fpnum);
			param.put("sdate", sdate);
			param.put("edate", edate);

			Map<String, Object> res = financeService.gatherStatist(param);
			return res;

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());

		}

	}

	/**
	 * 欠款信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> exportToGather(WebRuntime runtime)
			throws Exception {
		
		if(!this.existRight(runtime, "gathering_export")) {
			return this.errorMsg("你没有导出欠款的权限");
		}
		
		HttpTransfer ht = HttpTransferFactory.generate("ReportCenter");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("template", "gathering_export");
		Map<String,Object> res = ht.getMap("report!generate", p);
		String uuid = (String) res.get("uuid");
		return this.success("uuid", uuid);

	}

	private Map<String, Object> getSaleDetail(String id) throws Exception {
		String sql = "select * from subscribe where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

	/**
	 * 增加欠款信息列表
	 * 
	 * 处理销售订单没有欠款信息的问题
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> addGather(WebRuntime runtime) throws Exception {

		String id1 = runtime.getParam("id");
		Map sale = this.getSaleDetail(id1);

		if (sale == null) {
			return this.errorMsg("订单不存在！");
		}


		String ddyf = (String) sale.get("trade");
		
		String saleMan = "";

		String coname1 = (String) sale.get("coname");
		String number = (String) sale.get("number");
		BigDecimal yj1 = (BigDecimal) sale.get("yj");
		String money1 = (String) sale.get("money");
		String item = (String) sale.get("item");
		String mode = (String) sale.get("mode");
		Integer datet = (Integer) sale.get("source");
		String co_number = (String) sale.get("senddate");
		String dept1 = (String) sale.get("dept");
		String deptjb1 = (String) sale.get("deptjb");
		BigDecimal yf = (BigDecimal) sale.get("yf_money");
		String datetime1 = (String) sale.get("send_date");
		saleMan = (String) sale.get("man");

		String strSQLsk;

		// 预收款
		if (ddyf.equals("是")) {// yy

			strSQLsk = "insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,rate,i_man,sendcompany,remark,note) values('"
					+ id1
					+ "','"
					+ number
					+ "','"
					+ number
					+ "','"
					+ coname1
					+ "','"
					+ datetime1
					+ "','"
					+ datetime1
					+ "','"
					+ yj1
					+ "','预收款','"
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
					+ "','"
					+ item
					+ "','0','','','')";

		} else {

			strSQLsk = "insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,rate,i_man,sendcompany,remark,note) values('"
					+ id1
					+ "','"
					+ number
					+ "','"
					+ number
					+ "','"
					+ coname1
					+ "','"
					+ datetime1
					+ "','"
					+ datetime1
					+ "','"
					+ yj1
					+ "','待收款','"
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
					+ "','"
					+ item
					+ "','0','','','')";

		}

		dbManager.update(strSQLsk);

		return success();

	}
	
	@SuppressWarnings({ "rawtypes" })
	private void fillTotalAmoumt(SQLExecutor sqlExecutor,List list) throws Exception{
		for (int i = 0; i < list.size(); i++) {
			
			Map map = (Map) list.get(i);
			String orderform = (String) map.get("orderform");

			String strSQLpro = "select sum(cgpro_num*selljg) inWarehouseTotalPrice,sum(num*selljg) totalPrice from cgpro where  ddid='"
					+ orderform + "'";
			Map<String, Object> priceMap = sqlExecutor.queryForMap(strSQLpro,true);

			map.put("totle", priceMap.get("totalprice"));
			map.put("stotle", priceMap.get("inwarehousetotalprice"));

		}
	}

	/**
	 * 待付款列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String, Object> listToPay(WebRuntime runtime) throws Exception {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		String strSQL;
		int intRowCount;

		PageBean pageBean = runtime.getPageBean();
		int intPage = pageBean.getPage();

		String depts = runtime.unescape("depts");
		String user = runtime.unescape("user");
		String coname = runtime.unescape("coname");

		String cg_man = runtime.getParam("cg_man");
		if (cg_man == null)
			cg_man = "";
		String pros = runtime.getParam("pros");
		if (pros == null)
			pros = "";

		String sdate = runtime.getParam("sdate");
		if (sdate == null)
			sdate = "";
		String edate = runtime.getParam("edate");
		if (edate == null)
			edate = "";

		String co_number = runtime.getParam("co_number");
		if (co_number == null)
			co_number = "";

		String sqlWhere = "";

		if (StringUtils.isNotBlank(edate)) {
			sqlWhere = " and payment.sjfkdate<='" + edate + "' ";
		}

		String contract = runtime.getParam("number");
		if (StringUtils.isNotEmpty(contract)) {
			sqlWhere = " and payment.contract like '%" + contract + "%' ";
		}

		if (StringUtils.isNotEmpty(depts)) {
			sqlWhere = " and payment.contract in (select number from procure where L_DEPT='"
					+ depts + "')";
		}

		if (StringUtils.isNotEmpty(user)) {
			sqlWhere = " and payment.contract in (select number from procure where man='"
					+ user + "')";
		}

		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere = " and payment.supplier like '%" + coname + "%'";
		}

		Map<String, Object> res = new HashMap<String, Object>();
		strSQL = "select count(*) from payment where states='待付款' and contract not like 'TT%'";

		intRowCount = dbManager.queryForInt(strSQL + sqlWhere);
		
		pageBean.setTotalAmount(intRowCount);

		strSQL = "select top "
				+ pageBean.getTop()
				+ "  * from payment where states='待付款' and contract not like 'TT%' "
				+ sqlWhere + "  order by id desc";

		List list = dbManager.queryForList(strSQL, true);

		List rows = this.getRows(list, pageBean);
		
		SQLExecutorSingleConn sqlExecutor=null;
		
		try{
			sqlExecutor = this.getSQLExecutorSingleConn();
			this.fillTotalAmoumt(sqlExecutor,rows);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sqlExecutor!=null){
				sqlExecutor.close();
			}
		}
		
		res.put("rows", rows);

		res.put("page", intPage);
		res.put("totalPage", pageBean.getTotalPage());
		res.put("totalAmount", pageBean.getTotalAmount());
		res.put("pageSize", pageBean.getPageSize());

		return res;
	}
	
	/**
	 * 创建待付款报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String, Object> createToPayReport(WebRuntime runtime) throws Exception {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		String strSQL;
	
		String depts = runtime.unescape("depts");
		String user = runtime.unescape("user");
		String coname = runtime.unescape("coname");

		String cg_man = runtime.getParam("cg_man");
		if (cg_man == null)
			cg_man = "";
		String pros = runtime.getParam("pros");
		if (pros == null)
			pros = "";

		String sdate = runtime.getParam("sdate");
		if (sdate == null)
			sdate = "";
		String edate = runtime.getParam("edate");
		if (edate == null)
			edate = "";

		String co_number = runtime.getParam("co_number");
		if (co_number == null)
			co_number = "";

		String sqlWhere = "";

		if (StringUtils.isNotBlank(edate)) {
			sqlWhere = " and payment.sjfkdate<='" + edate + "' ";
		}

		String contract = runtime.getParam("number");
		if (StringUtils.isNotEmpty(contract)) {
			sqlWhere = " and payment.contract like '%" + contract + "%' ";
		}

		if (StringUtils.isNotEmpty(depts)) {
			sqlWhere = " and payment.contract in (select number from procure where L_DEPT='"
					+ depts + "')";
		}

		if (StringUtils.isNotEmpty(user)) {
			sqlWhere = " and payment.contract in (select number from procure where man='"
					+ user + "')";
		}

		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere = " and payment.supplier like '%" + coname + "%'";
		}
		
		strSQL = "select * from payment where states='待付款' and contract not like 'TT%' "
				+ sqlWhere + "  order by id desc";
		
		

		List list = dbManager.queryForList(strSQL, true);
		
		SQLExecutorSingleConn sqlExecutor=null;
		
		try{
			sqlExecutor = this.getSQLExecutorSingleConn();
			this.fillTotalAmoumt(sqlExecutor,list);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(sqlExecutor!=null){
				sqlExecutor.close();
			}
		}
		
		String[] cols = new String[] { "采购编号", "供应商", "采购员", "订单金额", "入库金额",
				"实付金额", "付款日期", "当前状态" };

		ExcelData data = new ExcelData();
		
		for(int i=0;i<list.size();i++){
			
			Map map = (Map) list.get(i);
			
			ExcelRow row = new ExcelRow();
			row.add(map.get("contract"));
			row.add(map.get("supplier"));
			row.add(map.get("remark"));
			row.add(map.get("totle"));
			row.add(map.get("stotle"));
			row.add(map.get("htmoney"));
			row.add(map.get("sjfkdate"));
			row.add(map.get("states"));
			data.add(row);
			
		}
		
		String uuid = ExcelUtils.makeCommonExcel("付款信息", cols, data);
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("uuid", uuid);
		return res;
		
	}
	
//	private void finishPayment(Transaction trans,Integer id,BigDecimal totalPrice) throws Exception{
//		String sql = "update payment set states='已付全款',htmoney=? where id = ?";
//		trans.update(sql, new Object[]{totalPrice,id});
//	}
	
//	@SuppressWarnings("rawtypes")
//	public Map<String, Object> quickGather(WebRuntime runtime)
//			throws Exception {
//		
//		String toPayIds = runtime.getParam("toPayIds");
//		String[] toPayIdArr = toPayIds.split(",");
//		
//		for(String toPayId : toPayIdArr){
//			
//			Map<String,Object> payment = financeService.getPayment(toPayId);
//			
//			System.out.println("doing payment,"+toPayId);
//			
//			System.out.println("payment,,,,,,,,,,,,,,,,,,,"+payment);
//			
//			Transaction trans = this.getTransaction();
//			
//			try {
//			
//				Integer id = (Integer) payment.get("id");
//				
//				Integer payAcountId = (Integer) SystemCache.defaultJSON.get("pay_account");
//				Map<String,Object> params = new HashMap<String,Object>();
//				params.put("accountId", payAcountId);
//				params.put("l_man", this.getUsername(runtime));
//				params.put("reference", "");
//				params.put("l_dept", this.getDept(runtime));
//				params.put("deptjb", this.getDeptjb(runtime));
//				params.put("c_d", "借方");
//				params.put("l_date", com.tntxia.date.DateUtil.getCurrentDateSimpleStr());
//				String l_coname = (String) payment.get("supplier");
//				params.put("l_coname", l_coname);
//				
//				params.put("co_number", "");
//				
//				String orderform = (String) payment.get("orderform");
//				
//				List proList = purchasingService.getPurchasingProductList(trans,orderform);
//				
//				BigDecimal stotal = BigDecimal.ZERO;
//				
//				for(int i=0;i<proList.size();i++){
//					
//					Map map = (Map) proList.get(i);
//					Integer num = (Integer) map.get("num");
//					BigDecimal selljg = (BigDecimal) map.get("selljg");
//					BigDecimal proPriceTotal = selljg.multiply(new BigDecimal(num));
//					stotal = stotal.add(proPriceTotal);
//					
//				}
//				
//				String l_hb = (String) payment.get("moneyty");
//				
//				params.put("l_sqje", stotal);
//				params.put("l_hb", l_hb);
//				params.put("compendium", "");
//				params.put("g_bank", "");
//				params.put("g_banknum", "");
//				params.put("remarks", "快速完成付款");
//				
//				params.put("sy", "supplier");
//				params.put("xsdh", "");
//				params.put("cgdh", "");
//				
//				financeService.addCredit(trans,params);
//				this.finishPayment(trans,id,stotal);
//				trans.commit();
//			}catch(Exception ex) {
//				trans.rollback();
//				ex.printStackTrace();
//				return this.errorMsg(ex.toString());
//			}finally {
//				trans.close();
//			}
//		}
//			
//		
//		
//		
//		return this.success();
//	}
	
	public Map<String,Object> getGatheringInfoBySaleId(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		return financeService.getGatheringBySaleId(id);
	}
	
	/**
	 * 查看科目明细
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<FinanceAccountDetail> getFinanceDetailList() throws Exception{
		
		List<FinanceAccountDetail> res = new ArrayList<FinanceAccountDetail>();
		String sql = "select id,km,km_mx,kmId from  km_mx order by km asc";
		
		List kmList = dbManager.queryForList(sql, true);
		
		for(int i=0;i<kmList.size();i++){
			Map<String,Object> map = (Map<String,Object>) kmList.get(i);
			FinanceAccountDetail detail = new FinanceAccountDetail();
			detail.setId((Integer)map.get("id"));
			detail.setKm((String)map.get("km"));
			detail.setName((String)map.get("km_mx"));
			if(map.get("kmId")!=null) {
				detail.setKmId((Integer)map.get("kmId"));
			}
			
			res.add(detail);
		}
		
		return res;
	}
	
	/**
	 * 获取科目列表
	 * @return
	 */
	public List<FinanceAccountDetail> getFinanceAccountList(WebRuntime runtime)  throws Exception{
		
		List<FinanceAccountDetail> financeAccountList = 
				getFinanceDetailList();
		return financeAccountList;
	}
	
	/**
	 * 增加凭证
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> addCredit(WebRuntime runtime)  throws Exception{
		
		String number = NumberFactory.generateNumber("M");
		
		 String sy=runtime.getParam("sy").trim();
		 String l_topic=runtime.getParam("l_topic");
		 
		 String l_man=this.getUsername(runtime);
		 String l_date=runtime.getParam("l_date");
		 String xsdh=runtime.getParam("xsdh"); 
		 String l_sqje=runtime.getParam("l_sqje");
		 if(l_sqje!=null){
		 	l_sqje=l_sqje.trim();
		 }else{
		 	l_sqje="0";
		 }
		 l_sqje = l_sqje.replaceAll(",", "");

		 
		 String l_coname=runtime.getParam("l_coname").trim();
		 String co_number=runtime.getParam("co_number");
		 String cgdh=runtime.getParam("cgdh");

		 String l_dept = this.getDept(runtime);
		 String deptjb = this.getDeptjb(runtime);
		 String l_hb=runtime.getParam("l_hb");
		 String c_d=runtime.getParam("c_d");
		 String reference=runtime.getParam("reference");
		 String compendium =runtime.getParam("compendium ");
		 compendium = DealString.htmlEncode(compendium);
		 compendium=com.infoally.util.Replace.strReplace(compendium,"'","''");
		 String g_bank=runtime.getParam("g_bank");
		 String g_banknum=runtime.getParam("g_banknum");
		 String remarks=runtime.getParam("remarks");
		 remarks = DealString.htmlEncode(remarks);
		 remarks=com.infoally.util.Replace.strReplace(remarks,"'","''");
		 String km=financeAccountService.getKmName(l_topic);
		 
		 String strSQL="insert into credit_debit(l_menber,l_man,reference,l_topic,l_dept,l_deptjb,l_c_d,l_date,l_coname,co_number,l_sqje,l_sje,l_hb,compendium,g_bank,g_banknum,remarks,st,sy,km,kms,rale,states,spman,spdate,spyj,fif,fspman,fspdate,fspyj,xsdh,cgdh) values(?,'" +l_man + "','" + reference+"','" + l_topic+"','" + l_dept+"','" + deptjb+"','" + c_d+"','" + l_date+"','"+l_coname+"','"+co_number+"','" + l_sqje + "','0','" + l_hb + "','" + compendium+ "','" + g_bank+ "','" + g_banknum+ "','" + remarks+ "','3','"+sy+"','"+km+"','','1','','','','','','','','','"+xsdh+"','"+cgdh+"')";
		 dbManager.executeUpdate(strSQL,new Object[] {number});
		 
		 String sql="select max(id)  from credit_debit";
		int  ddid=dbManager.queryForInt(sql);
		return this.success("ddid", ddid);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getGatheringNeedFlushList(WebRuntime runtime) throws Exception {
		
		String coname=runtime.getParam("coname").trim();
		String number = runtime.getParam("number");
		
		SQLExecutorSingleConn sqlExecutor = getSQLExecutorSingleConn();
		
		List list = new ArrayList();
		
		try {
			String strSQL = "select id,fyid,ymoney,bank,smoney,invoice,sub,coname,sjdate,skdate,sale_man,states from gatherview  where (states='待收款' or states='预收款')  and coname like  '%"+coname+"%' and invoice = '"+number+"' order by sjdate asc";

			list = sqlExecutor.queryForList(strSQL, true);
			
			for(int i=0;i<list.size();i++) {
				
				Map map = (Map) list.get(i);
				
				String  fyid=(String)map.get("fyid");
				fyid = fyid.trim();
			   
			    BigDecimal s=(BigDecimal)map.get("smoney");
		        
				BigDecimal totle=saleService.getOrderTotalPrice(fyid,sqlExecutor);
				map.put("totle", totle);
				
				BigDecimal sub1=totle.subtract(s);//剩余金额
				map.put("sub1", sub1);
				
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			sqlExecutor.close();
		}
		
		
		return list;
	}
	
	/**
	 * 收款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException 
	 */
	public Map<String,Object> gather(WebRuntime runtime) throws SQLException {
		
		DataSource dataSource = DatasourceStore.getDatasource("default");
		Connection conn = dataSource.getConnection();
		com.tntxia.sqlexecutor.Transaction trans = com.tntxia.sqlexecutor.Transaction.createTrans(conn);
		
		

		try {
			String name1 = this.getUsername(runtime);

			String skdate = DateUtil.getNowString("yyyyMMddHHmmss");
			String chooseIds = runtime.getParam("chooseIds");
			String[] t = chooseIds.split(",");
			String gid = runtime.getParam("gid");
			
			String g_date = runtime.getParam("g_date");
			String hb = runtime.getParam("hb");// 往来帐货币
			
			
			// 
			BigDecimal g_je = BigDecimalUtils.parse(runtime.getParam("g_je"));
			
			System.out.println("收款金额："+g_je);
			
			if (t != null) {
				for (int i = 0; i < t.length; i++) {
					
					String sqlss = "select fyid,invoice,smoney,ymoney,bank,note,coname from gathering where id='"
							+ t[i] + "'";
					Map<String,Object> gathering = dbManager.queryForMap(sqlss, true);
					if(gathering==null) {
						return this.errorMsg("收款信息不存在 ！");
					}
					String coname = (String) gathering.get("coname");
		
					// 订单ID
					String fyid = (String) gathering.get("fyid");
					fyid = fyid.trim();
					String invoice = (String)gathering.get("invoice");
					BigDecimal smoney = (BigDecimal) gathering.get("smoney");// 已收款金额
					
					String salesman = (String)gathering.get("note");
					
					BigDecimal totle = saleService.getOrderTotalPrice(fyid, trans);
					
					
					// 还没有收款的金额
					BigDecimal ts = totle.subtract(smoney);
					
					System.out.println("未收金额："+g_je);
					
					int compare = ts.compareTo(g_je);
					
					// 如果冲帐的金额，刚好是待收的金额，则记账为已收款
					if (compare==0) {

						String strSQLmx = "insert into gather_mx_mx(g_m_id,g_m_types,g_m_number,g_m_coname,g_m_smoney,g_m_hb,g_m_salesman,g_m_date,g_m_man) values('"
								+ gid + "','2','" + invoice + "','" + coname
								+ "','" + ts + "','" + hb + "','','" + g_date + "','" + name1 + "')";
						
						System.out.println("收款明细："+strSQLmx);

						trans.executeUpdate(strSQLmx);

						String strpros = "update gathering set  imoney='" + gid
								+ "',smoney='" + smoney.add(g_je)
								+ "',states='已收全部款',note='已收款',skdate='"
								+ skdate + "'  where id='" + t[i] + "'";
						trans.executeUpdate(strpros);
						
						String sql = "update subscribe set gather_status=2 where id = ?";
						trans.update(sql, new Object[] {fyid});
						
					// 如果冲帐的金额，小于待收的金额，则记账为部分收款
					} else if(compare==1){
						
						String strSQLmx = "insert into gather_mx_mx(g_m_id,g_m_types,g_m_number,g_m_coname,g_m_smoney,g_m_hb,g_m_salesman,g_m_date,g_m_man) values('"
								+ gid + "','2','" + invoice + "','" + coname
								+ "','" + g_je + "','" + hb + "','" + salesman
								+ "','" + g_date + "','" + name1 + "')";
						trans.executeUpdate(strSQLmx);
						
						
						BigDecimal tsn = smoney.add(g_je);
						String strpros = "update gathering set  imoney='" + gid
								+ "',smoney='" + tsn + "',note='部分收款',skdate='"
								+ skdate + "' where id='" + t[i] + "'";
						trans.executeUpdate(strpros);
						
						String sql = "update subscribe set gather_status=1 where id = ?";
						trans.update(sql, new Object[] {fyid});
						
					}
				}
			}
			
			String strpross = "update credit_debit set  l_sje='" + g_je
					+ "'  where id='" + gid + "'";
			trans.executeUpdate(strpross);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			this.errorMsg("操作失败");
		}finally {
			trans.close();
		}

		return this.success();

	}
	
	@SuppressWarnings("rawtypes")
	public List getBankList(WebRuntime runtime) throws Exception {
		
		String deptjb = this.getDeptjb(runtime);
		
		String strSQL="select * from bank where deptjb like '"+deptjb+"%' and hiddens='null' order by bank,com_bank";
		
		return dbManager.queryForList(strSQL, true);
		
	}
	
	private Map<String,Object> getGathering(String id) throws Exception{
		return dbManager.queryForMap("select  * from gatherview where id=?",new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getProList(String id) throws Exception{
		return dbManager.queryForList("select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc",new Object[]{id}, true);
	}

	@SuppressWarnings("rawtypes")
	private Map<String,BigDecimal> getPriceTotal(List list){
		
		BigDecimal orderTotal = BigDecimal.ZERO;
		BigDecimal outTotal = BigDecimal.ZERO;
		
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			Integer num = (Integer) map.get("num");
			Integer s_num = (Integer) map.get("s_num");
			orderTotal = orderTotal.add(salejg.multiply(BigDecimal.valueOf(num)));
			outTotal = outTotal.add(salejg.multiply(BigDecimal.valueOf(s_num)));
		}
		
		Map<String,BigDecimal> res = new HashMap<String,BigDecimal>();
		res.put("orderTotal", orderTotal);
		res.put("outTotal", outTotal);
		return res;
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getGatheringDataById(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> gathering = this.getGathering(id);
		String fyid = (String) gathering.get("fyid");
		List proList = this.getProList(fyid);
	
		
		gathering.put("proList", proList);
		gathering.put("priceTotal",this.getPriceTotal(proList));
		return gathering;
		
	}
	
	public Map<String,Object> updateGathering(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		 String remark1=runtime.getParam("remark");
		 String i_man=runtime.getParam("i_man");
		 String note=runtime.getParam("note");
		 String sendcompany=runtime.getParam("sendcompany");
		 String bankaccounts=runtime.getParam("bankaccounts");
		 String rate=runtime.getParam("rate");
		 String sjskdate=runtime.getParam("sjskdate");
		 String strSQL="update gathering set sjskdate='" + sjskdate + "',note='" + note + "',rate='" + rate + "',i_man='" + i_man + "',remark='" + remark1 + "',sendcompany='"+sendcompany+"',bankaccounts='"+bankaccounts+"' where id=?";
		 
		 dbManager.update(strSQL,new Object[] {id});
		 return this.success();
		 
	}
	
	/**
	 * 评论
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> comment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String note = request.getParameter("noteValue");
		String id = request.getParameter("id");

		financeDao.comment(id, note);
		return this.success();

	}
	
	/**
	 * 查询所有的费用科目
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List listExpenseAccount() throws Exception{
		String sql = "select * from km_f ";
		return dbManager.queryForList(sql, true);
	}
	
	/**
	 * 来往帐目信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String,Object> listExchange(WebRuntime runtime ) throws SQLException {
		
		Map<String,String> param = runtime.getParamMap();

		try{
			
			PageBean pageBean = runtime.getPageBean(50);
			Map<String,Object> res = financeService.listExchange(param,pageBean);
			return res;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());
		}
		
	}
	

	/**
	 * 收款信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Map<String,Object> listGathered(WebRuntime runtime) throws SQLException {
		
		Map<String,String> params = runtime.getParamMap();

		try{
			
			PageBean pageBean = runtime.getPageBean(50);
			Map<String,Object> res = financeService.listGathered(params,pageBean);
			return res;
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("获取欠款信息失败", ex);
			return this.errorMsg(ex.toString());
		}
		
	}
	
	/**
	 * 获取未收款退货列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> getGatheringRefundList(WebRuntime runtime) throws SQLException {

		Map<String, Object> res = new HashMap<String, Object>();

		try {

			PageBean pageBean = runtime.getPageBean();

			String coname = runtime.getParam("coname");
			String dept = runtime.getParam("dept");

			String number = runtime.getParam("fpnum");

			boolean gathered = runtime.getBoolean("gathered");
			
			Map<String,String> param = runtime.getParamMap();

			String sqlWhere = "";
			if (StringUtils.isNotEmpty(coname)) {
				sqlWhere += " and coname like '%" + coname + "%'";
			}
			if (StringUtils.isNotEmpty(dept)) {
				sqlWhere += " and sale_dept like '%" + dept + "%'";
			}
			if (StringUtils.isNotEmpty(number)) {
				sqlWhere += " and orderform like '%" + number + "%'";
			}

			int count = financeDao.getGatheringRefundCount(param, sqlWhere,
					gathered);
			List list;
			
			int pageNo = pageBean.getPage();

			list = financeDao.getGatheringRefund(param, sqlWhere,pageNo ,
					gathered);

			List rows = this.getRows(list, pageBean);
			for (int i = 0; i < rows.size(); i++) {
				Map map = (Map) rows.get(i);
				String fyid = (String) map.get("fyid");
				String rNumber = (String) map.get("orderform");
				map.put("totalAmount", financeDao.getSumAmount(fyid));
				map.put("subNumber", financeDao.getSubNumber(rNumber));
			}

			return this.getPagingResult(list, runtime, count);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return res;
	}
	
	public Map<String,Object> delGathering(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "update gathering set states='已删除' where id = ? ";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	public Map<String,Object> generateGathering(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String sql = "select  * from subscribe where id=?";
		Map<String,Object> subscribe = dbManager.queryForMap(sql, new Object[]{id},true);
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
		String datetime1 = (String) subscribe.get("send_date");
		String saleMan= (String) subscribe.get("man");
		String ddnumber = (String) subscribe.get("number");
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
				+ "','预收款','"
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
		dbManager.executeUpdate(strSQLsk);
		
		return this.success();
		
	}
	
	public Map<String,Object> markPaymentFinish(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String strSQL="update payment set states='已付全款' where id=?";
		dbManager.update(strSQL, new Object[] {id});
		return this.success();
	}
	
	public Map<String,Object> markGatheringFinish(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		financeService.finishGatherng(id);
		return this.success();
	}
	

}
