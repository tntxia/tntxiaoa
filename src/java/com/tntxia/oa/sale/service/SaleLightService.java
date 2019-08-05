package com.tntxia.oa.sale.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tntxia.date.DateUtil;
import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.finance.dao.FinanceLightDao;
import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.sale.entity.SalePro;
import com.tntxia.oa.util.ListUtils;
import com.tntxia.oa.util.MapUtils;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.service.CommonService;


public class SaleLightService extends CommonService{
	
	private static Logger logger = Logger.getLogger(SaleLightService.class);

	private SaleLightDao saleDao = new SaleLightDao();
	
	private FinanceLightDao financeDao = new FinanceLightDao();
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> getDetail(String id) throws Exception{
		String sql="select * from subscribe where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	@SuppressWarnings("rawtypes")
	public List<SalePro> getProductList(String id) throws Exception{
		
		String sql = "select * from ddpro where ddid = ? order by id ";
		
		List list = dbManager.queryForList(sql, new Object[]{id},true);
		
		List<SalePro> res = new ArrayList<SalePro>();
		
		for(int i=0;i<list.size();i++){
			
			Map map = (Map) list.get(i);
			Integer proid = (Integer)map.get("id");
			
			String money=(String) map.get("money");
			String epro = (String) map.get("epro");
			Integer num = (Integer) map.get("num");
			Integer s_num = (Integer) map.get("s_num");
			String supplier = (String) map.get("supplier");
			String cpro = (String) map.get("cpro");
			String fypronum = (String) map.get("fypronum");
			String remark = (String) map.get("remark");
			String unit = (String) map.get("unit");
			String deliveryDate = (String) map.get("s_tr_date");
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			BigDecimal selljg = (BigDecimal)map.get("selljg");
			
			SalePro pro = new SalePro();
			pro.setId(proid);
			pro.setEpro(epro);
			pro.setCpro(cpro);
			pro.setSupplier(supplier);
			pro.setFypronum(fypronum);
			pro.setRemark(remark);
			pro.setUnit(unit);
			pro.setMoney(money);
			pro.setNum(num);
			pro.setNumOut(s_num);
			pro.setSalejg(salejg);
			pro.setSelljg(selljg);
			pro.setDeliveryDate(deliveryDate);
			res.add(pro);
		}
		
		return res;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void fillAllSaleTotal(List saleList) throws Exception{
		
		SQLExecutorSingleConn executor = this.getSqlExecutorSingleConn();

		try{
			for(int i=0;i<saleList.size();i++){
				Map map = (Map) saleList.get(i);
				Integer id = (Integer) map.get("id");
				Map<String,Object> totalInfo = getTotalInfo(id,executor);
				BigDecimal left = (BigDecimal) totalInfo.get("left");
				BigDecimal stotal = (BigDecimal) totalInfo.get("stotal");
				BigDecimal total = (BigDecimal) totalInfo.get("total");
				map.put("left", left);
				map.put("stotal", stotal);
				map.put("total", total);
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			executor.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getTotalInfo(Integer id,SQLExecutor executor) throws Exception{
		
		BigDecimal smoney = BigDecimal.ZERO;
		
		Map<String,Object> gathering = financeDao.getGatheringBySaleId(id);
		
		if(gathering!=null){
			smoney = (BigDecimal) gathering.get("smoney");
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		List proList = saleDao.getSaleProList(String.valueOf(id));
		BigDecimal total = BigDecimal.ZERO;     
		BigDecimal stotal = BigDecimal.ZERO;	
		BigDecimal rTotal = BigDecimal.ZERO;	
		for(int i=0;i<proList.size();i++){
			Map mapPro = ListUtils.getMap(proList, i);
			int num = MapUtils.getInt(mapPro, "num");
			int s_num = MapUtils.getInt(mapPro, "s_num");
			BigDecimal salejg = MapUtils.getBigDecimal(mapPro, "salejg");
			total = total.add(BigDecimal.valueOf(num).multiply(salejg));
			stotal = stotal.add(BigDecimal.valueOf(s_num).multiply(salejg));
			rTotal = rTotal.add(saleDao.getRefundPrice(String.valueOf(id),executor));
		}
		map.put("total", total);
		map.put("stotal", stotal);
		map.put("rTotal", rTotal);
		map.put("smoney", smoney);
		map.put("left", stotal.subtract(smoney).subtract(rTotal));
		return map;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void setTotal(Map map) throws Exception{
		
		Integer id = (Integer) map.get("id");
		SQLExecutorSingleConn executor = this.getSqlExecutorSingleConn();
		try{
			map.putAll(getTotalInfo(id,executor));
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			executor.close();
		}
		
	}
	
	public BigDecimal getOrderTotalPrice(String id,SQLExecutor executor) throws Exception{
		return this.getOrderTotalPrice(Integer.parseInt(id), executor);
	}
	
	@SuppressWarnings("rawtypes")
	public BigDecimal getOrderTotalPrice(Integer id,SQLExecutor executor) throws Exception{
		
		List proList = saleDao.getSaleProList(id,executor);
		
		BigDecimal total = BigDecimal.ZERO;
		for(int i=0;i<proList.size();i++){
			Map mapPro = ListUtils.getMap(proList, i);
			int num = MapUtils.getInt(mapPro, "num");
			BigDecimal salejg = MapUtils.getBigDecimal(mapPro, "salejg");
			total = total.add(BigDecimalUtils.multiply(num, salejg));
			
		}
		
		return total;
		
	}
	
	@SuppressWarnings("rawtypes")
	private List getProList(String id) throws Exception {
		String sql = "select * from ddpro where ddid = ?";
		return dbManager.queryForList(sql, new Object[] { id }, true);
	}
	


	/**
	 * 获取审批流程
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getAuditFlow(double total, String dept,
			String role) throws Exception {
		String sql = "select  * from ddsp  where  dept=? and role=? and price_min<=? and  price_max>=? ";
		return dbManager.queryForMap(sql, new Object[] { dept, role, total,
				total }, true);
	}
	
	/**
	 * 销售合同，提交审批
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void toAudit(String basePath, String id, Userinfo userinfo) throws Exception {

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		String currentDate = simple.format(new java.util.Date());

		String dept = userinfo.getDept();
		String role = userinfo.getRole();
		double total = 0;
		double shl = 1;
		
		Map sale = saleDao.getSale(id);
		
		if(sale==null){
			throw new Exception("销售订单不存在！！");
		}
		
		String ddnumber = (String) sale.get("number");
		String username = userinfo.getUsername();

		DBConnection einfodb = new DBConnection();
		DBConnection einfodb2 = new DBConnection();

		try {

			List proList = this.getProList(id);
			for (int i = 0; i < proList.size(); i++) {
				Map map = (Map) proList.get(i);
				int num = (Integer) map.get("num");
				BigDecimal sprice = (BigDecimal) map.get("salejg");
				String hb = (String) map.get("pricehb");
				String sql = "select currrate from hltable where currname='"
						+ hb + "'";
				ResultSet rsshb = einfodb.executeQuery(sql);
				if (rsshb.next()) {
					String tmpshb = rsshb.getString("currrate");
					if (tmpshb != null)
						shl = Double.parseDouble(tmpshb);
				}
				double tprice = num * sprice.doubleValue() * shl;
				total = total + tprice;// 金额

			}

			Map<String, Object> auditFlow = this
					.getAuditFlow(total, dept, role);

			String fif = "";
			String if_sp = "";
			String fspman = "";
			String zt = "待审批";

			if (auditFlow == null) {
				throw new Exception("未定义审批流程");
			}

			if_sp = ((String) auditFlow.get("if_sp")).trim();
			String dd_man = (String) auditFlow.get("dd_man");
			fif = (String) auditFlow.get("fif");
			fspman = (String) auditFlow.get("fspman");
			if (if_sp.equals("否")) {
				zt = "待出库";
				String strSQL8="delete from gathering where orderform='" + ddnumber + "'";
			    dbManager.executeUpdate(strSQL8);
			    String coname = (String) sale.get("coname");
			    String senddate = (String)sale.get("send_date");
			    BigDecimal yj = (BigDecimal)sale.get("yj");   // 银行费用 		
			    String mode = (String) sale.get("mode");	
			    Integer source = (Integer) sale.get("source");
			    String money = (String) sale.get("money");
			    String man = (String) sale.get("man");
			    String sale_dept = (String) sale.get("dept");
			    String deptjb = (String) sale.get("deptjb");
			    String co_number = (String) sale.get("co_number");
			    String item = (String) sale.get("item");
				String strSQLsk="insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,rate,i_man,sendcompany,remark,note) values('" + id + "','" + ddnumber +"','" + ddnumber +"','" + coname + "','" + senddate +"','" + senddate +"',?,'待收款','" + mode +"','"+source+"','" + money +"','0',?,'待开发票','"+man+"','"+sale_dept+"','"+deptjb+"','" + co_number + "','" + item + "','0','','','')";
				dbManager.update(strSQLsk, new Object[]{yj.doubleValue(),yj.doubleValue()});
			} else {
				String approvingPath = basePath + "/#sale_list_approving";
				String mailContent = "点击<a href='"+approvingPath+"'>这里</a>查看待审批销售订单";
				String strSQLs = "insert into sendmail(mail_to,mail_to2,mail_to3,mail_sub,mail_nr,mail_man,deptjb,dept,mail_datetime,states,form_to,form_to2,form_to3) values('"
						+ dd_man
						+ "','','','编号为:"
						+ ddnumber
						+ "合同,需要你进行审批',?,'"
						+ username
						+ "','"
						+ userinfo.getDept()
						+ "','"
						+ userinfo.getDeptjb()
						+ "','"
						+ currentDate
						+ "','已发送','','','')";
				dbManager.executeUpdate(strSQLs, new Object[] {mailContent});
			}

			String sql = "update subscribe set state=?,spman=?,fif=?,cwman=?,sub_time=? where id=?";
			dbManager.update(sql, new Object[]{zt,dd_man,fif,fspman,currentDate,id});

		} catch (Exception ex) {
			logger.error("提交销售订单审批失败！", ex);
			throw ex;
		} finally {
			einfodb.close();
			einfodb2.close();
		}

	}
	
	public int listRefundCount(Map<String,Object> param) throws Exception{
		String sql = "select count(*) from th_table where (state='未提交' or  state='未批准')";
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listRefund(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
		String sql = "select top "+pageBean.getTop()+" * from th_table where (state='未提交' or  state='未批准')";
		List list = dbManager.queryForList(sql, true);
		List rows = this.getRows(list, pageBean);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", this.listRefundCount(param));
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		return res;
		
	}
	
	
	public int listRefundApprovingCount(Map<String,Object> param) throws Exception{
		String sql = "select count(*) from th_table where  (state='待审批' or  state='待复审')";
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listRefundApproving(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
		String sql = "select top "+pageBean.getTop()+" * from th_table where  (state='待审批' or  state='待复审')";
		List list = dbManager.queryForList(sql, true);
		List rows = this.getRows(list, pageBean);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", this.listRefundCount(param));
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		return res;
		
		
	}
	
	public int listRefundApprovedCount(Map<String,Object> param) throws Exception{
		String sql = "select count(*) from th_table where (state='已批准' or state='待退货'  or  state='已入库')";
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listRefundApproved(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
		String sql = "select top "+pageBean.getTop()+" * from th_table where (state='已批准' or state='待退货'  or  state='已入库') order by id desc";
		List list = dbManager.queryForList(sql, true);
		List rows = this.getRows(list, pageBean);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", this.listRefundCount(param));
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		return res;
		
		
	}
	
	public int listRefundDeletedCount(Map<String,Object> param) throws Exception{
		String sql = "select count(*) from th_table where (state='删除')";
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listRefundDeleted(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
		String sql = "select top "+pageBean.getTop()+" * from th_table where (state='删除')";
		List list = dbManager.queryForList(sql, true);
		List rows = this.getRows(list, pageBean);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", this.listRefundCount(param));
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		return res;
		
	}
	
	
	private String combineSQLWhere(String sqlWhere, Map<String,String> param) {
		
		String deptjb = param.get("deptjb");
		if(StringUtils.isNotEmpty(deptjb)) {
			sqlWhere += " and deptjb like '" + deptjb + "%'";
		}
		
		String username = param.get("username");
		if (StringUtils.isNotEmpty(username)) {
			sqlWhere += " and  man='" + username + "' ";
		}

		String numberR = param.get("number");
		if (StringUtils.isNotEmpty(numberR)) {
			sqlWhere += " and  number like '%" + numberR + "%' ";
		}

		String coname = param.get("coname");
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%' ";
		}

		String man = param.get("man");
		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man = '" + man + "' ";
		}

		String sub = param.get("sub");
		if (StringUtils.isNotEmpty(sub)) {
			sqlWhere += " and custno like '%" + sub + "%' ";
		}

		String depts = param.get("depts");
		if (StringUtils.isNotEmpty(depts)) {
			sqlWhere += " and dept = '" + depts + "' ";
		}

		String statexs = param.get("statexs");
		if (StringUtils.isNotEmpty(statexs)) {
			sqlWhere += " and state = '" + statexs + "' ";
		}

		String epro = param.get("epro");

		if (StringUtils.isNotEmpty(epro)) {
			sqlWhere += " and epro like '%" + epro + "%'";
		}

		String startdate = param.get("startdate");
		if (StringUtils.isNotEmpty(startdate)) {
			sqlWhere += "  and datetime>='" + startdate + "' ";
		}

		String enddate = param.get("enddate");
		if (StringUtils.isNotEmpty(enddate)) {
			sqlWhere += "  and datetime<='" + enddate + "' ";
		}

		String gatherState = param.get("gatherState");

		if (StringUtils.isNotEmpty(gatherState)) {
			if ("1".equals(gatherState)) {
				sqlWhere += " and number not in (select orderform from gathering where note = '已收款' )";
			} else if ("2".equals(gatherState)) {
				sqlWhere += " and number in (select orderform from gathering where note = '已收款' )";
			}

		}

		String pp = param.get("pp");
		if (StringUtils.isNotEmpty(pp)) {
			sqlWhere += "  and supplier = '" + pp + "' ";
		}

		return sqlWhere;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listApproved(Map<String,String> param,PageBean pageBean) throws Exception{
		
		int intRowCount = 0;
		String strSQL;
		String sqlWhere = " where (state='预收款' or state='已发运' or state='待出库' or state='待收款' or state='已备货' or state='全部退货' or state='部分退货') and habitus='订单执行中'";

		// 根据参数来拼装SQL语句
		sqlWhere = this.combineSQLWhere(sqlWhere, param);
		strSQL = "select count(*) from subview " + sqlWhere;
		intRowCount = dbManager.queryForInt(strSQL);

		String top = " top " + pageBean.getTop();

		strSQL = "select " + top + " * from subview "
				+ sqlWhere + " order by id desc";

		List list = dbManager.queryForList(strSQL, true);

		List rows = this.getRows(list, pageBean);

		for (int i = 0; i < rows.size(); i++) {

			Map map = (Map) rows.get(i);
			int id = (Integer) map.get("id");
			String number = (String) map.get("number");

			map.put("totalPrice", saleDao.getTotalPrice(id));
			map.put("financeInfo", saleDao.getSaleFinanceInfo(number));
			map.put("refundPrice", saleDao.getRefundPrice(id));

		}

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("rows", rows);

		result.put("totalAmount", intRowCount);
		result.put("page", pageBean.getPage());
		result.put("pageSize", pageBean.getPageSize());

		return result;
	}
	
	public Map<String,Object> listWarehouseSaleList(Map<String,String> param,PageBean pageBean) throws Exception{
		
		int intRowCount = 0;
		String strSQL;
		
		String sqlWhere = " where (state='预收款' or state='已发运' or state='待出库' or state='待收款' or state='已备货' or state='全部退货' or state='部分退货') and habitus='订单执行中'";
		
		// 根据参数来拼装SQL语句
		sqlWhere = this.combineSQLWhere(sqlWhere, param);

		strSQL = "select count(*) from subscribe " + sqlWhere;
		intRowCount = dbManager.queryForInt(strSQL);

		String top = " top " + pageBean.getTop();
		String selecteItems = "id,fy_number,number,mode,sub,yj,yf_money,coname,item,send_date,man,spman,state,custno";

		strSQL = "select " + top + " " + selecteItems + " from subscribe "
				+ sqlWhere + " order by id desc";

		List<Object> list = dbManager.queryForList(strSQL, true);
		
		return this.getPagingResult(list, pageBean, intRowCount);
	}
	
	public Map<String,Object> listWarehouseSaleOutedList(Map<String,String> param,PageBean pageBean) throws Exception{
		
		int intRowCount = 0;
		String strSQL;
		
		String sqlWhere = " where state='已发运'";
		
		// 根据参数来拼装SQL语句
		sqlWhere = this.combineSQLWhere(sqlWhere, param);

		strSQL = "select count(*) from subscribe " + sqlWhere;
		intRowCount = dbManager.queryForInt(strSQL);

		String top = " top " + pageBean.getTop();
		String selecteItems = "id,fy_number,number,mode,sub,yj,yf_money,coname,item,send_date,man,spman,state,custno";

		strSQL = "select " + top + " " + selecteItems + " from subscribe "
				+ sqlWhere + " order by id desc";

		List<Object> list = dbManager.queryForList(strSQL, true);
		
		return this.getPagingResult(list, pageBean, intRowCount);
	}
	
	public int create(Sale sale,Userinfo userinfo) throws Exception {
		
		String man = userinfo.getUsername();
		String subck = sale.getSubck();
		String number = sale.generateNumber();
		String coname = sale.getConame();
		String money = sale.getCurrentType();
		String sub = sale.getSub();
		String item = sale.getItem();
		String mode = sale.getMode();
		String trade1 = sale.getTrade();
		String datetime1 = DateUtil.getCurrentDateSimpleStr();
		String sendDate = sale.getSendDate();
		String terms = sale.getTerms();
		String remark = sale.getRemark();
		String tr = sale.getTr();
		String dept = userinfo.getDept();
		String deptjb = userinfo.getDeptjb();
		String address = sale.getAddress();
		String deliveryLinkMan = sale.getDeliveryLinkMan();
		String deliveryTel = sale.getDeliveryTel();
		String deliveryFeeType = sale.getDeliveryFeeType();
		String fy_number = sale.getFy_number();
		String custno = sale.getCustno();
		
		String strSQL = "insert into subscribe(number,man,subck,sub,coname,yj,money,item,mode,source,trade,habitus,datetime,"
				+ "senddate,tbyq,remarks,state,dept,deptjb,tr,tr_addr,tr_man,tr_tel,yf_types,yf_money,"
				+ "fy_number,ware_remark,p_states,sub_time,s_check,ware_man,cg_man,send_date,other_fy,custno) values(?,?,'"
				+ subck
				+ "','"
				+ sub
				+ "','"
				+ coname
				+ "',?,'"
				+ money
				+ "','"
				+ item
				+ "','"
				+ mode
				+ "',?,'"
				+ trade1
				+ "','订单执行中','"
				+ datetime1
				+ "','"
				+ sendDate
				+ "','"
				+ terms
				+ "','"
				+ remark
				+ "','未提交','"
				+ dept
				+ "','"
				+ deptjb
				+ "','"
				+ tr
				+ "','"
				+ address
				+ "','"
				+ deliveryLinkMan
				+ "','"
				+ deliveryTel
				+ "','"
				+ deliveryFeeType
				+ "',?,'"
				+ fy_number
				+ "','','','','无','','共享','"
				+ sendDate + "',?,'" + custno + "')";
		dbManager.executeUpdate(strSQL,new Object[] {number,man,0,0,0,0});
		
		String sql = "select max(id)  from subscribe";
		int ddid = dbManager.queryForInt(sql);
		
		List<SalePro> proList = sale.getProList();
		
		if(proList!=null) {
			for(SalePro pro : proList) {
				pro.setDdid(ddid);
				this.addPro(pro);
			}
		}
		
		return ddid;
		
	}
	
	public void addPro(SalePro pro) throws SQLException {
		
		int ddid = pro.getDdid();
		String epro = pro.getEpro();
		int num = pro.getNum();
		String unit = pro.getUnit();
		String supplier = pro.getSupplier();
		String price = pro.getSalejg().toString();
		int wid = pro.getWid();
		String remark = pro.getRemark();
		String pro_tr = pro.getPro_tr();
		String mpn = pro.getMpn();
		String money = pro.getMoney();
		
		String strSQL="insert into ddpro(ddid,epro,num,unit,rale_types,rale,supplier,salejg,fyproall,wid,x_no,remark,s_num,s_c_num,s_tr_date,fy_states,p_check,mpn,pro_jz,pro_mz,pro_cc,pro_cd,th_num,PRICEHB,money) "
		   		+"values(?,?,?,?,'价外税','0',?,?,'no',?,'',?,'0','0','"+pro_tr+"','待发运','',?,'','','','',0,'',?)";
		   	dbManager.executeUpdate(strSQL,new Object[] {ddid,epro,num,unit,supplier,price,wid,remark,mpn,money});
	}
	
	
	
	

}
