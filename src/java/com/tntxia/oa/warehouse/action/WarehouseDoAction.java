package com.tntxia.oa.warehouse.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tntxia.date.DateUtil;
import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.oa.common.CommonToolbarItem;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.mail.SendMail;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.service.PurchasingLightService;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.oa.system.dao.ProductTypeDao;
import com.tntxia.oa.system.entity.ProductType;
import com.tntxia.oa.warehouse.InWarehouse;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.dao.WarehouseLightDao;
import com.tntxia.oa.warehouse.entity.InOutLog;
import com.tntxia.oa.warehouse.entity.ProductNumChangeBean;
import com.tntxia.oa.warehouse.service.WarehouseLightService;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.string.EscapeUnescape;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Session;
import com.tntxia.web.util.DatasourceStore;

public class WarehouseDoAction extends CommonDoAction {

	private static Logger logger = Logger.getLogger(WarehouseAction.class);

	private DBManager dbManager = this.getDBManager();

	private WarehouseLightDao warehouseDao = new WarehouseLightDao();

	private WarehouseLightService service = new WarehouseLightService();

	private SaleLightService saleService = new SaleLightService();
	
	private PurchasingLightService purchasingService = new PurchasingLightService();
	
	private ProductTypeDao productTypeDao = new ProductTypeDao();

	private boolean hasRefund(Transaction trans, String id) throws Exception {
		String sql = "select s_num from th_pro where id=?";
		return trans.queryForInt(sql, new Object[] { id }) > 0;
	}

	private Map<String, Object> getPro(Transaction trans, String id) throws Exception {
		String sql = "select * from th_pro where id=?";
		return trans.queryForMap(sql, new Object[] { id }, true);
	}

	private Map<String, Object> getOrder(Transaction trans, String id) throws Exception {
		String sql = "select * from th_table where id=?";

		return trans.queryForMap(sql, new Object[] { id }, true);
	}

	private Map<String, Object> getWarehouse(Transaction trans, String epro, String cpro) throws Exception {
		String sql = "select * from warehouse where pro_model=? and pro_name = ?";
		return trans.queryForMap(sql, new Object[] { epro, cpro }, true);
	}

	private void addWarehouseNum(Transaction trans, int warehouseId, int refundNum) throws SQLException {
		String sql = "update warehouse set pro_num = pro_num + ? where id = ?";
		trans.update(sql, new Object[] { refundNum, warehouseId });
	}

	// 对于退货信息的记录
	private void logWarehouseReturn(Transaction trans, String pro_model, String operateMan, String remark)
			throws SQLException {

		String sql = "insert into th_info(pro_model,remark,operateMan,operateTime)" + " values( '" + pro_model + "','"
				+ remark + "','" + operateMan + "',getdate())";
		trans.update(sql);

	}

	@SuppressWarnings("rawtypes")
	private List getProList(Transaction trans, Integer ddid) throws Exception {
		String sql = "select id,ddid,num,s_num from th_pro where ddid=?";
		return trans.queryForList(sql, new Object[] { ddid }, true);
	}

	@SuppressWarnings("rawtypes")
	private void updateThTableThStatus(Transaction trans, Integer id) throws Exception {

		List pros = this.getProList(trans, id);

		int in_num = 0;

		for (int i = 0; i < pros.size(); i++) {
			Map map = (Map) pros.get(i);
			Integer num = (Integer) map.get("num");
			Integer sNum = (Integer) map.get("s_num");
			if (sNum > 0 && num == sNum) {
				in_num++;
			}
		}

		if (in_num == pros.size()) {

			String sql = "update th_table set state='已入库' where id=" + id;
			trans.update(sql);
		}
	}

	public Map<String, Object> doSingleRefund(WebRuntime runtime) throws SQLException {

		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String id = runtime.getParam("id");

		DataSource dataSource = DatasourceStore.getDatasource("default");
		Connection conn = dataSource.getConnection();
		Transaction trans = Transaction.createTrans(conn);
		try {

			if (this.hasRefund(trans, id)) {
				throw new Exception("产品已经做了退货！");
			}

			Map<String, Object> pro = this.getPro(trans, id);
			String ddid = (String) pro.get("ddid");
			Map<String, Object> order = this.getOrder(trans, ddid);

			String thnumber = (String) order.get("number");
			String coname = (String) order.get("coname");
			String co_number = (String) order.get("senddate");
			String sales_man = (String) order.get("man");
			String money1 = (String) order.get("money");
			Date dd_date = (Date) order.get("datetime");
			String dept = (String) order.get("dept");
			String deptjb = (String) order.get("deptjb");

			String remarks = "";

			String pro_model = ((String) pro.get("epro")).trim();
			String pro_name = (String) pro.get("cpro");

			String pro_number = ((String) pro.get("fypronum")).trim();

			int s_num = (Integer) pro.get("s_num");
			int num = (Integer) pro.get("num");
			int num1 = num - s_num;
			num1 = -1 * num1;

			String punit = (String) pro.get("unit");
			BigDecimal saleprice = (BigDecimal) pro.get("salejg");
			String salehb = (String) pro.get("pricehb");
			String supplier = (String) pro.get("supplier");

			String wid = (String) pro.get("wid");
			String fy_states = (String) pro.get("fy_states");

			String username = this.getUsername(runtime);

			Map<String, Object> warehouse = this.getWarehouse(trans, pro_model, pro_name);

			if (warehouse == null) {
				throw new Exception("仓库暂无该产品或批号不正确:" + pro_model + "批号:" + pro_name);
			}
			

			int warehouseId = (Integer) warehouse.get("id");

			addWarehouseNum(trans, warehouseId, num);

			SimpleDateFormat simple1 = new SimpleDateFormat("yyMM");
			String number = simple1.format(new java.util.Date());
			String strSQL1 = "select in_no from outhouse where pro_out_num like '" + number
					+ "%'   order by in_no desc";

			int no = trans.queryForInt(strSQL1) + 1;

			String sno1 = "";
			if (no < 10) {
				sno1 = "000";
			} else if ((10 <= no) & (no < 100)) {
				sno1 = "00";
			} else if ((100 <= no) & (no < 1000)) {
				sno1 = "0";
			} else
				sno1 = "";

			int orderId = (Integer) order.get("id");

			String strSQLq = "insert into outhouse(pro_fynum,pro_coname,pro_model,"
					+ "pro_name,pro_num,pro_unit,pro_supplier,pro_datetime,pro_number,slinkman,slinktel,states,ddid,remark,wid,pro_coname_num,pro_sales_price,pro_price_hb,pro_rate_types,pro_rate,pro_out_num,in_no) values(?,'"
					+ coname + "','" + pro_model + "','" + pro_name + "','" + num1 + "','" + punit + "','" + supplier
					+ "','" + currentDate + "','" + pro_number + "','','" + wid + "','已出库','" + orderId + "','"
					+ fy_states + "','2','" + co_number + "','" + saleprice + "','" + salehb + "','" + wid + "','0','"
					+ number + "" + sno1 + "" + no + "','" + no + "')";

			trans.update(strSQLq, new Object[] { thnumber });
			logWarehouseReturn(trans, pro_model, username, remarks);

			String strSQLwf = "update  th_pro set s_num='" + num + "',s_c_num='" + num + "',s_tr_date='" + currentDate
					+ "',fy_states='待发运'  where id='" + id + "' ";

			trans.executeUpdate(strSQLwf);

			String strSQLthg = "delete from gathering where orderform='" + thnumber + "'";
			trans.executeUpdate(strSQLthg);
			String strSQLsk = "insert into gathering(fyid,invoice,orderform,coname,yjskdate,sjdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,i_man) values('"
					+ orderId + "','" + thnumber + "','" + thnumber + "','" + coname + "','" + dd_date + "','" + dd_date
					+ "','" + dd_date + "','0','退货','退货','0','" + money1 + "','0','0','','" + sales_man + "','" + dept
					+ "','" + deptjb + "','" + co_number + "','0')";
			trans.executeUpdate(strSQLsk);

			updateThTableThStatus(trans, orderId);
			trans.commit();
			return this.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			trans.rollback();
			return this.errorMsg(ex.toString());
		} finally {
			trans.close();
		}

	}

	private String getWarehouseListSqlWhere(WebRuntime runtime) {

		String sqlWhere = " where 1=1 ";

		String model = runtime.getParam("model");
		if (StringUtils.isNotEmpty(model)) {
			sqlWhere += " and pro_model like '%" + model + "%'";
		}
		String supplier = StringUtils.trim(runtime.getParam("supplier"));
		if (StringUtils.isNotEmpty(supplier)) {
			sqlWhere += " and pro_sup_number like '%" + supplier + "%'";
		}
		return sqlWhere;
	}
	
	

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	private List getWarehouseList(String sqlWhere, Object[] params,WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean(25);

		SQLExecutorSingleConn db = new SQLExecutorSingleConn(DatasourceStore.getDatasource("default"));
		
		List list = null;
		
		boolean view_purchasing_price = this.existRight(runtime, "view_purchasing_price");
		
		try {

			String sql = "select top " + pageBean.getTop() + " * from warehouse " + sqlWhere + " order by id desc";
			list = db.queryForList(sql, params,true);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);

				String promodel = ((String) map.get("pro_model")).trim();
				map.put("promodel", promodel);

				String pro_yyfw = (String) map.get("pro_yyfw");

				String pro_remark = (String) map.get("pro_remark");
				String th_number = (String) map.get("th_number");

				int zint_num = 0;
				int ttnum = 0;

				if (th_number != null) {
					ttnum = 0;
					zint_num = 0;
					pro_remark = "<font color='red'>退货:</font>" + pro_remark;
				} else {
					zint_num = service.getCommingNum(db,promodel);
					ttnum = service.getOutingNum(db,promodel);
				}

				map.put("ttnum", ttnum);
				map.put("zint_num", zint_num);
				map.put("isRefund", StringUtils.isNotEmpty(th_number));

				// 如果用户有权限才会显示采购价格
				String cgprice = "";
				if (view_purchasing_price) {
					cgprice = pro_yyfw;
				}
				map.put("cgprice", cgprice);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return list;

	}

	private String getSqlWhere(Map<String, String> paramMap) {
		String sql = "";
		String supplier = paramMap.get("supplier");
		if (StringUtils.isNotEmpty(supplier)) {
			sql += " and pro_sup_number ='" + supplier + "'";
		}
		String model = paramMap.get("model");
		if (StringUtils.isNotEmpty(model)) {
			sql += " and pro_model like '%" + model + "%'";
		}
		return sql;
	}

	private int getProductCount(Map<String, String> paramMap) throws Exception {
		String sql = "select count(*) from warehouse where 1=1 ";
		String sqlWhere = this.getSqlWhere(paramMap);
		return dbManager.getCount(sql + sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	private List getProductList(PageBean pageBean, Map<String, String> paramMap) throws Exception {
		String sql = "select top " + pageBean.getTop() + " * from warehouse where 1=1 ";
		String sqlWhere = this.getSqlWhere(paramMap);
		return dbManager.queryForList(sql + sqlWhere, true);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> list(WebRuntime runtime) throws Exception {

		Map<String, String> paramMap = runtime.getParamMap();

		int count = this.getProductCount(paramMap);
		PageBean pageBean = runtime.getPageBean(200);
		List list = this.getProductList(pageBean, paramMap);
		return this.getPagingResult(list, pageBean, count);

	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> warehouseList(WebRuntime runtime) throws Exception {

		SQLExecutorSingleConn db = new SQLExecutorSingleConn(DatasourceStore.getDatasource("default"));

		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			nf.setMinimumFractionDigits(4);

			java.lang.String strSQL;

			int intRowCount;
			String prodel = "";

			strSQL = "select count(*) from warehouse ";

			String sqlWhere = this.getWarehouseListSqlWhere(runtime);

			intRowCount = db.getCount(strSQL + sqlWhere);

			PageBean pageBean = runtime.getPageBean(25);

			List list = this.getWarehouseList(sqlWhere, null,runtime);

			if ("有".equals(prodel)) {
				resultMap.put("prodel", true);
			}

			String totalMessage = "";

			resultMap.put("totalMessage", totalMessage);
			
			return this.getPagingResult(list, pageBean,intRowCount);


		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorMsg(ex.toString());

		} finally {
			db.close();
		}

	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getSameModelList(WebRuntime runtime) throws Exception {

		
		SQLExecutorSingleConn db = new SQLExecutorSingleConn(DatasourceStore.getDatasource("default"));

		try {

			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(4);
			nf.setMinimumFractionDigits(4);

			int intRowCount;
			
			String model = runtime.getParam("model");
			String id = runtime.getParam("id");

			String strSQL = "select count(*) from warehouse ";

			String sqlWhere = " where pro_model= ? and id<>?";
			
			Object[] params = new Object[] {model,id};

			intRowCount = db.getCount(strSQL + sqlWhere,params);

			PageBean pageBean = runtime.getPageBean(25);

			List list = this.getWarehouseList(sqlWhere, params,runtime);
			
			return this.getPagingResult(list, pageBean,intRowCount);


		} catch (Exception ex) {
			ex.printStackTrace();
			return this.errorMsg(ex.toString());

		} finally {
			db.close();
		}

	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> getHdCompanyList(WebRuntime runtime) throws Exception {

		String strSQL;
		boolean hdview = this.existRight(runtime, "hdview");

		String companyname = runtime.getParam("companyname");

		if (hdview) {
			strSQL = "select count(*) from hdcompany where 1=1";
		} else
			strSQL = "select count(*) from hdcompany  where companyname='789'";

		String sqlWhere = "";

		if (StringUtils.isNotEmpty(companyname)) {
			sqlWhere += " and companyname like '%" + companyname + "%'";
		}

		int count = dbManager.queryForInt(strSQL + sqlWhere);

		strSQL = "select * from hdcompany where 1=1 ";

		List list = dbManager.queryForList(strSQL + sqlWhere, true);
		return this.getPagingResult(list, runtime.getPageBean(20), count);

	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> getOutList(WebRuntime runtime) throws Exception {

		int intPageSize = 50;
		PageBean pageBean = runtime.getPageBean(intPageSize);

		boolean proview = this.existRight(runtime, "outview");
		boolean prock = this.existRight(runtime, "profl");

		String sqlWhere = "";

		String coname = StringUtils.trim(runtime.getParam("coname"));
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and pro_coname like '%" + coname + "%'";
		}

		String model = StringUtils.trim(runtime.getParam("model"));

		if (StringUtils.isNotEmpty(model)) {
			sqlWhere += " and pro_model like '%" + model + "%'";
		}

		String number = StringUtils.trim(runtime.getParam("number"));

		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and pro_fynum like '%" + number + "%'";
		}

		String strSQL;
		if (proview) {
			strSQL = "select count(*) from ckview where states='已出库'";
		} else
			strSQL = "select count(*) from ckview where states='已出库'  and  slinktel='" + prock + "'";

		int intRowCount = dbManager.queryForInt(strSQL + sqlWhere);

		if (proview) {
			strSQL = "select top " + pageBean.getTop() + " * from ckview where states='已出库' " + sqlWhere
					+ " order by id desc";

		} else
			strSQL = "select top " + pageBean.getTop() + " * from ckview where states='已出库' and  slinktel='" + prock
					+ "' " + sqlWhere + " order by id desc";

		List list = dbManager.queryForList(strSQL, true);

		return this.getPagingResult(list, pageBean, intRowCount);

	}

	@SuppressWarnings("rawtypes")
	public List viewComing(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String model = ParamUtils.unescape(request, "model");
		List list = warehouseDao.getComingList(model);
		return list;

	}

	@SuppressWarnings("rawtypes")
	public List getWaitOut(Map<String, Object> param) throws Exception {

		PageBean pageBean = (PageBean) param.get("pageBean");

		String sql = "select top " + pageBean.getTop()
				+ " s.*,g.note from subscribe s left outer join gathering g on g.orderform = s.number where (state='待出库'  or  state='预收款' or state='已备货') ";

		String epro = (String) param.get("epro");
		if (StringUtils.isNotEmpty(epro)) {
			sql += " and s.id in (select ddid from ddpro where epro like '%" + epro + "%')";
		}
		String coname = (String) param.get("coname");
		if (StringUtils.isNotEmpty(coname)) {
			sql += " and s.coname like '%" + coname + "%'";
		}
		String man = (String) param.get("man");
		if (StringUtils.isNotEmpty(man)) {
			sql += " and s.man like '%" + man + "%'";
		}
		String sdate = (String) param.get("sdate");
		if (StringUtils.isNotEmpty(sdate)) {
			sql += " and s.send_date >= '" + sdate + "'";
		}
		String edate = (String) param.get("edate");
		if (StringUtils.isNotEmpty(edate)) {
			sql += " and s.send_date <= '" + edate + "'";
		}
		String number = (String) param.get("number");
		if (StringUtils.isNotEmpty(number)) {
			sql += " and s.number like '%" + number + "%'";
		}
		String supplier = (String) param.get("supplier");
		if (StringUtils.isNotEmpty(supplier)) {
			sql += " and s.id in ( select ddid from ddpro where supplier like '%" + supplier + "%')";
		}
		sql += " order by s.id desc";
		return dbManager.queryForList(sql, true);
	}

	/**
	 * 待出库列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int getWaitOutCount(Map<String, Object> param) throws Exception {
		String sql = "select count(*) from subscribe s left outer join gathering g on g.orderform = s.number where (state='待出库'  or  state='预收款' or state='已备货') ";
		String epro = (String) param.get("epro");
		if (StringUtils.isNotEmpty(epro)) {
			sql += " and s.id in (select ddid from ddpro where epro like '%" + epro + "%')";
		}
		String coname = (String) param.get("coname");
		if (StringUtils.isNotEmpty(coname)) {
			sql += " and s.coname like '%" + coname + "%'";
		}
		String man = (String) param.get("man");
		if (StringUtils.isNotEmpty(man)) {
			sql += " and s.man like '%" + man + "%'";
		}
		String sdate = (String) param.get("sdate");
		if (StringUtils.isNotEmpty(sdate)) {
			sql += " and s.send_date >= '" + sdate + "'";
		}
		String edate = (String) param.get("edate");
		if (StringUtils.isNotEmpty(edate)) {
			sql += " and s.send_date <= '" + edate + "'";
		}
		String number = (String) param.get("number");
		if (StringUtils.isNotEmpty(number)) {
			sql += " and s.number like '%" + number + "%'";
		}
		String supplier = (String) param.get("supplier");
		if (StringUtils.isNotEmpty(supplier)) {
			sql += " and s.id in ( select ddid from ddpro where supplier like '%" + supplier + "%')";
		}
		return dbManager.getCount(sql);

	}

	/**
	 * 待出库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> waitOutList(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean(100);

		String epro = runtime.getParam("epro");
		String coname = runtime.getParam("coname");
		String man = runtime.getParam("man");
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		String number = runtime.getParam("number");
		String supplier = runtime.getParam("supplier");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("epro", epro);
		param.put("coname", coname);
		param.put("man", man);
		param.put("sdate", sdate);
		param.put("edate", edate);
		param.put("number", number);
		param.put("supplier", supplier);
		param.put("pageBean", pageBean);

		List list = this.getWaitOut(param);

		int count = getWaitOutCount(param);

		return this.getPagingResult(list, pageBean, count);

	}

	@SuppressWarnings("rawtypes")
	private List getWaitOutSampleList(PageBean pageBean) throws Exception {
		String sql = "select top " + pageBean.getTop() + " * from sample where  state='待出库'   order by id desc";
		return dbManager.queryForList(sql, true);
	}

	private int getWaitOutSampleCount() throws Exception {

		String sql = "select count(*) from sample where  state='待出库'";
		return dbManager.queryForInt(sql);
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> waitOutSampleList(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(100);

		List list = this.getWaitOutSampleList(pageBean);

		int count = this.getWaitOutSampleCount();

		return this.getPagingResult(list, pageBean, count);
	}

	@SuppressWarnings("rawtypes")
	private List getWaitOutDhList(PageBean pageBean) throws Exception {
		String sql = "select top " + pageBean.getTop() + " * from sample_dh where  state='待出库'   order by id desc";
		return dbManager.queryForList(sql, true);
	}

	private int getWaitOutDhCount() throws Exception {

		String sql = "select count(*) from sample_dh where  state='待出库'";
		return dbManager.queryForInt(sql);
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> waitOutDhList(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(100);

		List list = this.getWaitOutDhList(pageBean);

		int count = this.getWaitOutDhCount();

		return this.getPagingResult(list, pageBean, count);
	}

	@SuppressWarnings("rawtypes")
	private List getRefundList(PageBean pageBean) throws Exception {
		String sql = "select top " + pageBean.getTop() + " * from th_table where  state='已批准'   order by number desc";
		return dbManager.queryForList(sql, true);
	}

	private int getRefundCount() throws Exception {

		String sql = "select count(*) from th_table where  state='已批准'";
		return dbManager.queryForInt(sql);
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> waitRefundList(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(100);

		List list = this.getRefundList(pageBean);

		int count = this.getRefundCount();

		return this.getPagingResult(list, pageBean, count);
	}

	
	/**
	 * 出库
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> out(WebRuntime runtime) throws Exception {

		String wid = runtime.getParam("wid");

		if (StringUtils.isEmpty(wid)) {
			return this.errorMsg("仓库ID不能为空");
		}

		String username = super.getUsername(runtime);
		String id = runtime.getParam("id");
		String ddid = runtime.getParam("ddid");

		String num1 = runtime.getParam("num");

		try {
			String msg = service.doProout(username, id, ddid, num1, wid);
			if (msg == null) {
				return this.success();
			} else {
				return this.errorMsg(msg);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("单个出库失败", ex);
			return this.errorMsg(ex.toString());

		} finally {

		}

	}

	/**
	 * 增加出入库记录
	 * 
	 * @param log
	 * @throws SQLException
	 */
	private void addInOutLog(Transaction trans, InOutLog log) throws SQLException {

		String id = String.valueOf(System.currentTimeMillis());
		String sql = "insert into warehouse_in_out_log(id,number,orgin_num,change_num,final_num,operateTime,operater,wid,memo) values(?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date(System.currentTimeMillis());

		trans.update(sql, new Object[] { id, log.getNumber(), log.getOrginNum(), log.getChangeNum(), log.getFinalNum(),
				sdf.format(now), log.getUsername(), log.getWid(), log.getRemark() });
	}
	
	@SuppressWarnings("rawtypes")
	private void changeProductNum(Transaction trans, String number,String username, String model, int num,String remark) throws Exception {
		int pro_num = 0;
		String sql = "select id,pro_num from warehouse where pro_model=? ";

		Map warehouseMap = trans.queryForMap(sql, new Object[] { model }, true);
		
		if(warehouseMap==null) {
			throw new Exception("产品："+model+"在仓库中查询不到！");
		}
		
		int wid = (Integer) warehouseMap.get("id");
		pro_num = ((BigDecimal) warehouseMap.get("pro_num")).intValue();
		int zpro_num = pro_num + num;
		
		if(zpro_num<0) {
			throw new Exception("产品："+model+"库存不足！");
		}
		
		String strSQLw = "update warehouse set pro_num=?  where id=? ";
		trans.update(strSQLw,new Object[] {zpro_num,wid});

		InOutLog log = new InOutLog();
		log.setOrginNum(pro_num);
		log.setChangeNum(num);
		log.setFinalNum(zpro_num);
		log.setNumber(number);
		log.setUsername(username);
		log.setWid(wid);
		log.setRemark(remark);
		addInOutLog(trans, log);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> outReturn(WebRuntime runtime) throws Exception {

		String ware_remark = runtime.getParam("ware_remark");

		String p_states = runtime.getParam("p_states");
		String ddid = runtime.getParam("ddid");
		String username = this.getUsername(runtime);

		Transaction trans = this.getTransaction();

		Map<String, Object> sale = saleService.getDetail(ddid);
		String ddnumber1 = (String) sale.get("number");
		String man = (String) sale.get("man");

		try {
			String strSQLprog = "select  count(*) from gather_mx_mx where  g_m_number='" + ddnumber1 + "'";
			if (trans.exist(strSQLprog)) {
				return this.errorMsg("请删除合同" + ddnumber1 + "的往来帐目冲帐信息,再返回!");

			}

			String strSQLpro = "select  id,pro_model,pro_name,pro_num,slinktel from outhouse where  ddid=?";

			List outhouseList = trans.queryForList(strSQLpro, new Object[] { ddid }, true);

			for (int i = 0; i < outhouseList.size(); i++) {
				Map map = (Map) outhouseList.get(i);
				String pro_model = ((String) map.get("pro_model")).trim();
				int num = (Integer) map.get("pro_num");
				
				changeProductNum(trans, ddnumber1,username, pro_model, num,"返回出库单");

			}

			String strSQL = "update  subscribe  set  p_states='" + p_states + "',state='待审批',ware_remark='"
					+ ware_remark + "'   where number='" + ddnumber1 + "'";
			String strSQL8 = "delete from gathering where orderform='" + ddnumber1 + "'";
			String strSQL6 = "update  ddpro set s_num=0,s_c_num='0',fy_states='待发运' where ddid='" + ddid + "'";
			String strSQL9 = "delete from transportation where subscribe='" + ddnumber1 + "'";
			trans.update(strSQL9);
			trans.update(strSQL8);
			trans.update(strSQL6);
			trans.update(strSQL);

			SendMail sendmail = new SendMail();
			sendmail.sendMail("合同退回:编号为:" + ddnumber1 + "合同", "退回合同原因:" + ware_remark, man, username);

			String strSQLout = "delete from outhouse where pro_fynum='" + ddnumber1 + "'";
			trans.update(strSQLout);
			trans.commit();
		} catch (Exception ex) {
			trans.rollback();
			return this.errorMsg(ex.toString());
		} finally {
			trans.close();
		}

		return this.success();
	}

	/**
	 * 全部产品出库
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> doOutSale(Map<String, Object> paramMap, @Session("username") String username) throws Exception {

		String id = (String) paramMap.get("id");
		List rows = (List) paramMap.get("rows");

		try {
			String msg = service.doSout(username, id, rows);
			if (msg != null) {
				return this.errorMsg(msg);
			}
			return this.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("产品全部入库失败", ex);
			return this.errorMsg(ex.toString());
		}

	}
	
	private Map<String,Object> getSampleDetail(String id) throws Exception {
		String sqldd = "select * from sample where id=?";
		Map<String, Object> detail = dbManager.queryForMap(sqldd, new Object[] { id }, true);

		return detail;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> doOutSample(WebRuntime runtime) throws Exception {
		
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		
		String id1 = runtime.getParam("id");

		Map<String, Object> detail = this.getSampleDetail(id1);

		if (detail == null) {
			return this.errorMsg("样品ID不存在！");
		}
		
		String username = this.getUsername(runtime);
		
		Transaction trans = this.getTransaction();
		try {
			String coname = (String)detail.get("coname");
			String fynumber = (String)detail.get("number");
			String co_number = runtime.getParam("co_number");

			String strSQLpro = "select id,epro,cpro,num,unit,wid,salejg,pricehb from sam_pro where ddid=? order by id asc";
			List proList = trans.queryForList(strSQLpro, new Object[] { id1 }, true);

			for (int i = 0; i < proList.size(); i++) {
				Map<String, Object> map = (Map<String, Object>) proList.get(i);
				String pro_model = ((String) map.get("epro")).trim();
				String pro_name = (String) map.get("cpro");
				int num = (Integer) map.get("num");
				String punit = (String) map.get("unit");
				BigDecimal saleprice = (BigDecimal) map.get("salejg");
				String salehb = (String) map.get("pricehb");

				String sql = "select pro_num,pro_addr from warehouse where pro_model='" + pro_model + "'";
				Map<String, Object> productDetail = trans.queryForMap(sql, true);

				if (productDetail == null) {
					throw new Exception(pro_model + "在仓库中没有找到！");
				}
				
				Integer wid = (Integer)productDetail.get("id");

				java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat("yyMM");
				String number = simple1.format(new java.util.Date());
				int no = 1;
				String strSQL1 = "select in_no  from outhouse where pro_out_num like '" + number
						+ "%'   order by in_no desc";
				no = trans.queryForInt(strSQL1);
				no++;

				String sno1 = "";
				if (no < 10) {
					sno1 = "000";
				} else if ((10 <= no) & (no < 100)) {
					sno1 = "00";
				} else if ((100 <= no) & (no < 1000)) {
					sno1 = "0";
				} else
					sno1 = "";

				String strSQLq = "insert into outhouse(pro_fynum,pro_coname,pro_model,pro_name,pro_num,pro_unit,pro_supplier,pro_datetime,pro_number,slinkman,states,ddid,remark,wid,pro_coname_num,pro_sales_price,pro_price_hb,pro_rate_types,pro_rate,pro_out_num,in_no,salejg) values('"
						+ fynumber + "','" + coname + "','" + pro_model + "','" + pro_name + "','" + num + "','" + punit
						+ "','','" + currentDate + "','','','已出库','" + id1 + "','','3','" + co_number
						+ "','" + saleprice + "','" + salehb + "','" + wid + "','0','" + number + "" + sno1 + "" + no
						+ "','" + no + "',0)";
				trans.executeUpdate(strSQLq);
				
				this.changeProductNum(trans, number, username, pro_model, - num, "样品出库");

			}
			String ddsqls = "update sample set state='已发运',habitus='样品完成' where id='" + id1 + "'";
			trans.executeUpdate(ddsqls);
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
			return this.errorMsg("出库异常，"+ex.toString());
		}finally {
			trans.close();
		}
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> doInSample(WebRuntime runtime) throws Exception {
		
		java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
		String currentDate=simple.format(new java.util.Date());
		 String id1=runtime.getParam("id");

			Map<String, Object> detail = this.getSampleDetail(id1);

			if (detail == null) {
				return this.errorMsg("样品ID不存在！");
			}
			String fynumber = (String)detail.get("number");
		 String username = this.getUsername(runtime);
		 String state = (String) detail.get("state");
		 if(state.equals("已入库")) {
			 return this.errorMsg("样品已入库");
		 }
		 Transaction trans = this.getTransaction();
		 try {
			 String strSQLpro = "select id,epro,cpro,num,pro_snum,pro_sc_num,unit,wid,salejg,pricehb from sam_pro where  ddid='"+id1+"'";
			List proList=trans.queryForList(strSQLpro, true);
			
			for(int i=0;i<proList.size();i++) {
				 Map map = (Map) proList.get(i);
				 Integer  pid=(Integer) map.get("id");
				String pro_model=((String) map.get("epro")).trim();
				
				 int num=(Integer)  map.get("num");
				 int pro_snum=(Integer)  map.get("pro_snum");
				 int pro_sc_num=(Integer)  map.get("pro_sc_num");
				  pro_snum=pro_snum+pro_sc_num;
				  pro_sc_num=pro_sc_num*(-1);

				 String sql="select count(*) from warehouse where pro_model='"+pro_model+"'";
				 int count = trans.getCount(sql);
				 if(count==0) {
					 return this.errorMsg("仓库暂无该产品");
				 }
				
				 this.changeProductNum(trans, fynumber, username, pro_model, num, "样品归还");
		
				  String strSQLw1="update sam_pro set pro_sc_num='0',pro_snum='"+pro_snum+"',pro_sr_date='"+currentDate+"'  where id='" + pid + "'";
				  trans.executeUpdate(strSQLw1);
				  
				  
			}
			
			      
			    String ddsqls="update sample set state='已入库'  where id='"+id1+"'";
			    trans.executeUpdate(ddsqls);
			    trans.commit();
		 }catch(Exception e) {
			 e.printStackTrace();
			 trans.rollback();
			 return this.errorMsg(e.getMessage());
		 }finally {
			 trans.close();
		 }
		 
		 return this.success();    
		
	}
	
	public List<ProductType> listProductType(WebRuntime runtime) throws Exception {
		List<ProductType> productTypeList = productTypeDao.getProductTypeList();
		return productTypeList;
	}
	
	/**
	 * 
	 * 修改仓库内的产品信息
	 * 
	 */
	public Map<String,Object> update(WebRuntime runtime) throws Exception {
		
		String id2 = runtime.getParam("id");
		String price_hb = runtime.getParam("price_hb");
		String pro_number = runtime.getParam("pro_number").trim();
		
		String pro_supplier = runtime.getParam("pro_supplier");
		
		int pro_s_num = 0;
		String tepnum1 = runtime.getParam("pro_s_num");
		if (tepnum1 != null)
			pro_s_num = Integer.parseInt(tepnum1);
		
		String pro_unit = runtime.getParam("pro_unit");
		String pro_sup_number = runtime.getParam("pro_sup_number").trim();
		String pro_min_num = runtime.getParam("pro_min_num");
		String pro_max_num = runtime.getParam("pro_max_num");
		String sale_states = runtime.getParam("sale_states");
		String sale_min_price = runtime.getParam("sale_min_price");
		String sale_max_price = runtime.getParam("sale_max_price");
		String proms = runtime.getParam("pro_ms");

		String pro_jstx = runtime.getParam("pro_jstx");
		String pro_yyfw = runtime.getParam("pro_yyfw");

		double proyyfw=0;
		if(StringUtils.isNotEmpty(pro_yyfw)) {
			proyyfw = Double.parseDouble(pro_yyfw); 
		}

		String pro_remark1 = runtime.getParam("pro_remark");
		String pro_man = runtime.getParam("pro_man").trim();
		
		String pro_type1 = runtime.getParam("pro_type");
		if(pro_type1==null) pro_type1 = "";
		pro_type1 = pro_type1.trim();
		String pro_date = DateUtil.getCurrentDateSimpleStr();
		String pro_secid = runtime.getParam("pro_secid");
		String yqdate = runtime.getParam("yqdate");
		String pro_weight_unit = runtime.getParam("pro_weight_unit");

		
		String strSQL = "update warehouse set pro_weight_unit='"
				+ pro_weight_unit + "', pro_date='" + pro_date
				+ "',pro_number='" + pro_number
				+ "',price_hb='"+price_hb
				+ "',saleprice='" + proms
				+ "',pro_s_num='" + pro_s_num
				+ "',pro_unit='" + pro_unit + "',pro_supplier='" + pro_supplier
				+ "',pro_remark=?,pro_sup_number='"
				+ pro_sup_number + "',pro_type='" + pro_type1
				+ "',pro_min_num='" + pro_min_num + "',pro_max_num='"
				+ pro_max_num + "',sale_states='" + sale_states
				+ "',sale_min_price='" + sale_min_price + "',sale_max_price='"
				+ sale_max_price + "',pro_ms='" + proms + "',pro_jstx='"
				+ pro_jstx + "',pro_yyfw='" + proyyfw + "',pro_man='" + pro_man
				+ "',pro_secid='" + pro_secid + "',yqdate='" + yqdate
				+ "' where id='" + id2 + "'";
		
		dbManager.update(strSQL,new Object[] {pro_remark1});
		
		return this.success();
		
	}
	
	public Map<String,Object> viewChangeLog(WebRuntime runtime) throws Exception {
		
		PageBean pageBean = runtime.getPageBean(20);
		String id = runtime.getParam("id");
		String sql = "select top "+pageBean.getTop()+" * from warehouse_in_out_log where wid = '"+id+ "' order by operateTime desc";
		String sqlCount =  "select count(*) from warehouse_in_out_log where wid = '"+id+ "' ";
		int totalAmount = dbManager.getCount(sqlCount);
		return this.getPagingResult(dbManager.queryForList(sql, true), pageBean, totalAmount);
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> viewRkhouse(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> warehouse = warehouseDao.getDetail(id);
		
		String pro_model = (String) warehouse.get("pro_model");
		
		PageBean pageBean = runtime.getPageBean(20);

		String sql = "select top "+pageBean.getTop()+" * from rkview where  pro_model='"+pro_model+"' and (states='已入库' or states='全部入库') order by pro_datetime desc";
		String sqlCount =  "select count(*) from rkview where  pro_model='"+pro_model+"' and (states='已入库' or states='全部入库') ";
		int totalAmount = dbManager.getCount(sqlCount);
		
		List list = dbManager.queryForList(sql, true);
		
		pageBean.setTotalAmount(totalAmount);
		List rows = this.getRows(list, pageBean);
		
		for(int i=0;i<rows.size();i++) {
			Map map = (Map) rows.get(i);
			// 如果不是经理，不让看价格
			if(!this.isManager(runtime)) {
				map.put("pro_price", "");
			}
			
		}
	
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> viewOuthouse(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> warehouse = warehouseDao.getDetail(id);
		
		String pro_model = (String) warehouse.get("pro_model");
		
		PageBean pageBean = runtime.getPageBean(20);

		String sql = "select top "+pageBean.getTop()+" * from outhouse where  pro_model='"+pro_model+"' order by pro_datetime desc";
		String sqlCount =  "select count(*) from outhouse where  pro_model='"+pro_model+"' ";
		int totalAmount = dbManager.getCount(sqlCount);
		List list = dbManager.queryForList(sql, true);
		
		pageBean.setTotalAmount(totalAmount);
		List rows = this.getRows(list, pageBean);
		for(int i=0;i<rows.size();i++) {
			Map map = (Map) rows.get(i);
			// 如果不是经理，不让看价格
			if(!this.isManager(runtime)) {
				map.put("pro_sales_price", "");
			}
			
		}
		
		return this.getPagingResult(list, pageBean, totalAmount);
		
		
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception {
		String ids = runtime.getParam("ids");
		String sql = "delete from warehouse where id in ("+ids+")";
		dbManager.update(sql);
		return this.success();
				
	}
	
	
	
	
	/**
	 * 增加产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String model = request.getParameter("promodel");
		DataSource dataSource = DatasourceStore.getDatasource("default");
		Connection conn = dataSource.getConnection();
		Transaction trans = Transaction.createTrans(conn);
		
		try {
			if(warehouseDao.exist(trans, model)) {
				
				trans.rollback();
				return this.errorMsg("产品型号已经存在！");
			}
			
			String pro_gg = request.getParameter("pro_gg");
			String pro_name = request.getParameter("pro_name");
			String pro_sup_number = request.getParameter("pro_sup_number");
			String dqnum = request.getParameter("num");
			String pro_addr1 = request.getParameter("pro_addr1");
			String pro_remark = request.getParameter("pro_remark");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("model", model);
			params.put("pro_gg", pro_gg);
			params.put("pro_name", pro_name);
			params.put("pro_sup_number", pro_sup_number);
			params.put("dqnum", dqnum);
			params.put("pro_addr1", pro_addr1);
			params.put("pro_remark", pro_remark);

			warehouseDao.add(params);

			return this.success();
		}catch(Exception ex) {
			
			ex.printStackTrace();
			return this.errorMsg("增加产品失败");
		}finally {
			trans.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public List viewToOut(WebRuntime runtime) throws Exception {
		String model = runtime.getParam("model");
		List list = warehouseDao.getToOutList(model);
		List res = new ArrayList();
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer num = (Integer) map.get("num");
			
			Integer s_num = (Integer) map.get("s_num");
			Integer numLeft;
			if(s_num!=null) {
				numLeft = num - s_num;
			}else {
				numLeft = num;
			}
			if(numLeft>0) {
				map.put("numLeft", numLeft);
				res.add(map);
			}
			
		}
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> pushProductIntoPurchasingOrder(Map<String,Object> paramMap) throws Exception {
		
		String ddid = (String) paramMap.get("ddid");
		
		Object data = paramMap.get("data");
		
		List<Integer> ids = new ArrayList<Integer>();
		
		if (data instanceof Map) {
			Map map = (Map) data;
			ids.add((Integer)map.get("id"));
		} else {
			List list = (List) data;
			for(int i=0;i<list.size();i++) {
				Map map = (Map) list.get(i);
				ids.add((Integer)map.get("id"));
			}
		}
		
		for(Integer wid : ids) {
			
			Map<String,Object> p = service.getDetail(wid);
			
			String pro_model = (String) p.get("pro_model");
			Integer pro_num = ((BigDecimal) p.get("pro_num")).intValue();
			
			String punit = (String) p.get("pro_unit");
			String pro_supplier = (String) p.get("pro_sup_number");
			String qhb = (String) p.get("price_hb");
			
			BigDecimal pro_price = (BigDecimal) p.get("pro_price");
			if(pro_price==null) {
				pro_price = BigDecimal.ZERO;
			}
			String pro_remark = (String) p.get("pro_remark");
			
			String currentDate = DateUtil.getCurrentDateSimpleStr();
			
			String sql="insert into cgpro(ddid,epro,cpro,pro_number,num,unit,selljg,money,cgpro_ydatetime,cgpro_num,"+
				    "cgpro_sdatetime,remark,supplier ,rate,wid,sale_supplier,sale_remark,sale_rate,"+
				    "sale_finance) values(?,?,'0','',?,'" + punit + "','" + pro_price + "','"+qhb+"','"+currentDate+"','0','',?,'"+pro_supplier+"','0',?,'','','','')";
			dbManager.update(sql,new Object[] {ddid,pro_model,pro_num,pro_remark,wid});
		}
		
		return this.success();
		
	}
	
	/**
	 * 获取等待入库的列表
	 * @param runtime
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getWaitingRkList(WebRuntime runtime) throws Exception {
		// 获取在途数
		String sqlComming = "select number,num,cgpro_num from cgview where l_spqk='已入库' and num-cgpro_num>0";
		List list = dbManager.queryForList(sqlComming,true);
		
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>)list.get(i);
			String number = (String) map.get("number");
			String sql = "update procure set l_spqk='待入库' where number = ?";
			dbManager.update(sql, new Object[] {number});
		}
		return this.success();
		
	}
	
	@SuppressWarnings("rawtypes")
	public List listWarehouseTypeList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return 
				warehouseDao.getWarehouseTypeList();
	}
	
	/**
	 * 搜索仓库产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List search(WebRuntime runtime) throws Exception {
		String model = runtime.getParam("model");
		return service.search(model);
	}
	
	public Map<String,Object> listIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String startdate = ParamUtils.unescape(request, "startdate");
		String enddate = ParamUtils.unescape(request,"enddate");
		String int_types =ParamUtils.unescape(request,"int_types");
		String pro_model = ParamUtils.unescape(request,"pro_model");
		String g_man = ParamUtils.unescape(request,"g_man");
		String coname = ParamUtils.unescape(request,"coname");
		String ddnum = ParamUtils.unescape(request,"ddnum");
		String orderNumber = ParamUtils.unescape(request,"orderNumber");
		
		PageBean pageBean = this.getPageBean(request, 50);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("pageBean", pageBean);
		param.put("startdate", startdate);
		param.put("enddate", enddate);
		param.put("int_types", int_types);
		param.put("pro_model", pro_model);
		param.put("g_man", g_man);
		param.put("coname", coname);
		param.put("ddnum", ddnum);
		param.put("orderNumber", orderNumber);
		
		return service.listIn(param);
	}
	
	private int addInWarehouse(InWarehouse in,Userinfo uesrinfo) throws Exception{
		
		String man = uesrinfo.getUsername();
		String dept = uesrinfo.getDept();
		String deptjb = uesrinfo.getDeptjb();
		
		java.sql.ResultSet sqlRst;
		java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
				"yyMM");
		String number1 = simple1.format(new java.util.Date());
		int in_no = 1;
		DBConnection db = new DBConnection();
		String sqlq = "select  *  from in_warehouse  where number like 'PO"
				+ number1 + "%' order by in_no desc";
		ResultSet rs = db.executeQuery(sqlq);
		if (rs.next()) {
			in_no = rs.getInt("in_no") + 1;
		}
		String sno = "";
		if (in_no < 10) {
			sno = "00";
		} else if ((10 <= in_no) & (in_no < 100)) {
			sno = "0";
		} else
			sno = "";
		rs.close();
		
		String int_date = DateUtil.getCurrentDateSimpleStr();

		String strSQL = "insert into in_warehouse(number,cg_number,supplier,sp_number,int_types,money,int_date,g_man,"
				+ "man,dept,deptjb,remark,in_no,states,purchasing_id)"
				+ " values('PO"
				+ number1
				+ "-"
				+ sno
				+ ""
				+ in_no
				+ "','','"
				+ in.getSupplier()
				+ "','"
				+ in.getSp_number()
				+ "','"
				+ in.getInt_types()
				+ "','"
				+ in.getMoney()
				+ "','"
				+ int_date
				+ "','"
				+ in.getG_man()
				+ "','"
				+ man
				+ "','"
				+ dept
				+ "','"
				+ deptjb + "','" + in.getRemark() + "','" + in_no + "','待入库',?)";
		logger.info("execute in warehouse :" + strSQL);
		dbManager.executeUpdate(strSQL,new Object[] {in.getPurchasingId()});
		
		String sql = "select max(id)  from in_warehouse";
		sqlRst = db.executeQuery(sql);
		sqlRst.next();
		int ddid = sqlRst.getInt(1);
		
		return ddid;
	}
	

	public Map<String,Object> addInWarehouse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String supplier = EscapeUnescape.unescape(request, "supplier");

		String sp_number = request.getParameter("sp_number");
		String int_types = EscapeUnescape.unescape(request, "int_types");
		String money = request.getParameter("money");
		String g_man = EscapeUnescape.unescape(request, "g_man");
		
		String remark = EscapeUnescape.unescape(request, "remark");
		
		Userinfo uesrinfo = this.getUserinfo(request);
		InWarehouse in = new InWarehouse();
		in.setSupplier(supplier);
		in.setSp_number(sp_number);
		in.setInt_types(int_types);
		in.setMoney(money);
		in.setG_man(g_man);
		in.setRemark(remark);
		
		int ddid = this.addInWarehouse(in,uesrinfo);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		res.put("id", ddid);
		return res;
		
	}
	
	/**
	 * 入库时，查找采购单产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> purchasingSearch(WebRuntime runtime) throws Exception {

		String ddid = runtime.getParam("ddid");
		InWarehouse inWarehouse = service.getInWarehouse(ddid);
		String sp_number = inWarehouse.getSp_number();
		String strSQL;
		
		PageBean pageBean = runtime.getPageBean(20);
		boolean proview = super.existRight(runtime, "proview");

		String sqlWhere = " where co_number='" + sp_number
				+ "' and (l_spqk='合同已确认' or l_spqk='待入库')";
		
		String number = runtime.getParam("number");
		if(StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%"+number+"%'";
		}
		
		String coname = runtime.getParam("coname");
		if(StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%"+coname+"%'";
		}

		if (proview) {
			strSQL = "select count(*) from  procure" + sqlWhere;
		} else
			strSQL = "select count(*) from  procure" + sqlWhere;
		
		int intRowCount = dbManager.getCount(strSQL);
		pageBean.setTotalAmount(intRowCount);

		if (proview) {
			strSQL = "select id,number,coname,datetime,man,subck from  procure  "
					+ sqlWhere + "     order by id desc";
		} else
			strSQL = "select id,number,coname,datetime,man,subck from  procure "
					+ sqlWhere + "    order by id desc";

		System.out.println(strSQL);

		List list  = dbManager.queryForList(strSQL, true);
		return this.getPagingResult(list, pageBean, intRowCount);

	}
	
	/**
	 * 入库时，查找采购单产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public List purchasingProductSearch(WebRuntime runtime) throws Exception {

		String ddid = runtime.getParam("ddid");
		String strSQL;

		strSQL = "select c.*,(num-cgpro_num) numLeft  from  cgpro c where ddid=? and num>cgpro_num";

		List list  = dbManager.queryForList(strSQL, new Object[] {ddid},true);
		return list;

	}
	
	/**
	 * 
	 * 取消入库单
	 * 
	 * 需要做的相关操作 1. 恢复采购订单为供应商确认 2. 将已入库的产品从仓库中提出，如果仓库的产品不足，提示库存不足不能取消
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> cancelInWarehouse(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id").trim();
		String username = this.getUsername(runtime);

		try {
			service.cancelInWarehouse(id,username);
		} catch (Exception ex) {
			return this.errorMsg(ex.toString());
		} finally {
			
		}

		return this.success();

	}
	
	public Map<String,Object> reCheckin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		service.recheckIn(id, username);
		return this.success();
	}
	
	public Map<String,Object> delRkhouse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		service.delRkhouse(id);
		return this.success();
		
	}
	
	public Map<String,Object> checkIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id1 = request.getParameter("id");
		String sp_number = request.getParameter("sp_number");
		String username = super.getUsername(request);

		if (username == null) {
			return this.errorMsg("用户未登陆");
		}
		
		String msg = service.checkInAll(id1, username, sp_number);
		if (msg == null) {
			return this.success();
		} else {
			return this.errorMsg(msg);
		}


	}
	
	public Map<String,Object> checkInSingle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id1 = request.getParameter("id");
		String username = super.getUsername(request);
		String msg = service.checkIn(id1, username);
		if (msg == null) {
			return this.success();
			
		} else {
			return this.errorMsg(msg);
		}

	}
	
	/**
	 * 将采购产品放入入库单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> purchasingProductIn(WebRuntime runtime) throws Exception {

		String refundId = runtime.getParam("refundId");
		JSONArray products = JSONObject.parseArray(runtime.getParam("products"));
		String ddid = runtime.getParam("ddid");
		for(int i=0;i<products.size();i++) {
			JSONObject json = (JSONObject) products.get(i);
			Integer productId = json.getInteger("id");
			Integer leftnum = json.getInteger("numleft");
			service.purchasingProductIn(ddid,refundId,  productId,leftnum);
			
		}
		
		return this.success();

	}
	
	public Map<String,Object> editInNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		service.editInNum(id, num);
		return this.success();
	}
	
	public Map<String,Object> delInPro(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		service.delInPro(id);
		return this.success();
	}
	
	/**
	 * 供应商退货待入库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List listRefund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return warehouseDao.getRefundList();
	}
	
	private void updateRefundStatus(Transaction trans,int id) throws Exception {
		String sql = "select sum(num) total_num,sum(s_num) total_out_num from th_pro_supplier where ddid=?";
		Map<String,Object> res = trans.queryForMap(sql, new Object[] {id}, true);
		Integer totalNum = (Integer)res.get("total_num");
		Integer totalOutNum = (Integer)res.get("total_out_num");
		if(totalNum==0 || totalOutNum==0) {
			return;
		}
		String status;
		if(totalNum.equals(totalOutNum)) {
			status = "已出库";
		}else {
			status = "部分出库";
		}
		
		sql = "update th_table_supplier set state=? where id = ?";
		trans.update(sql, new Object[] {status,id});
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> supplierRefundAll(WebRuntime runtime) throws Exception{
		
		DataSource dataSource = DatasourceStore.getDatasource("default");
		Connection conn = dataSource.getConnection();
		Transaction trans = Transaction.createTrans(conn);
		String id = runtime.getParam("id");
		String sql = "select * from th_table_supplier where id = '"+ id +"'";
		Map<String,Object> refund = dbManager.queryForMap(sql, true);
		String number = (String) refund.get("number");
		sql = "select * from th_pro_supplier where ddid = '"+id+"'";
		System.out.println(sql);
		List list = dbManager.queryForList(sql, true);
		
		ProductNumChangeBean source = new ProductNumChangeBean();
		source.setNumber(number);
		source.setUsername(this.getUsername(runtime));
		source.setRemark("供应商全部退货");
		
		
		try {
			for(int i=0;i<list.size();i++) {
				Map map = (Map) list.get(i);
				Integer proid = (Integer) map.get("id");
				String epro = (String) map.get("epro");
				int num = (Integer) map.get("num");
				source.setNum(-num);
				service.updateProductNum(trans, source, epro);
				sql = "update th_pro_supplier set s_num=? where id = ?";
				trans.update(sql, new Object[] {num,proid});
			}
			updateRefundStatus(trans,Integer.parseInt(id));
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally{
			trans.close();
		}
		
		return this.success();
		
	}
	
	public Map<String,Object> supplierRefundSingle(WebRuntime runtime) throws Exception{
		
		DataSource dataSource = DatasourceStore.getDatasource("default");
		Connection conn = dataSource.getConnection();
		Transaction trans = Transaction.createTrans(conn);
		String id = runtime.getParam("id");
		String sql = "select *　from th_pro_supplier where id = ?";
		Map<String,Object> refundPro = dbManager.queryForMap(sql, new Object[] {id}, true);
		Integer ddid = (Integer) refundPro.get("ddid");
		
		 sql = "select * from th_table_supplier where id = ?";
		Map<String,Object> refund = dbManager.queryForMap(sql, new Object[] {ddid}, true);
		String number = (String) refund.get("number");
		
		ProductNumChangeBean source = new ProductNumChangeBean();
		source.setNumber(number);
		source.setUsername(this.getUsername(runtime));
		source.setRemark("供应商全部退货");
		
		try {
			
			String epro = (String) refundPro.get("epro");
			int num = (Integer) refundPro.get("num");
			source.setNum(-num);
			service.updateProductNum(trans, source, epro);
			sql = "update th_pro_supplier set s_num=? where id = ?";
			trans.update(sql, new Object[] {num,id});
			
			updateRefundStatus(trans,ddid);
			trans.commit();
		}catch(Exception ex) {
			trans.rollback();
		}finally{
			trans.close();
		}
		
		return this.success();
		
	}
	
	@SuppressWarnings("rawtypes")
	public List listSupplierRefundPro(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from th_pro_supplier where ddid = '"+id+"'";
		
		System.out.println(sql);
		
		List list = dbManager.queryForList(sql, true);
		return list;
	}
	
	public Map<String,Object> createHDCompany(WebRuntime runtime) throws Exception {
		String companyname1=runtime.getParam("companyname");
		 String companytel1=runtime.getParam("companytel");
		 String companyaddr1=runtime.getParam("companyaddr");
		 String companyfax1=runtime.getParam("companyfax");
		 String companynet1=runtime.getParam("companynet");
		 String companyemail1=runtime.getParam("companyemail");
		 String linkman1=runtime.getParam("linkman");
		 String linkmantel1=runtime.getParam("linkmantel");
		 String remark1=runtime.getParam("remark");
		 String strSQL="insert into hdcompany(companyname,companytel,companyaddr,companyfax,companynet,companyemail,linkman,linkmantel,remark) values('" + companyname1 + "','" + companytel1 +"','" + companyaddr1 + "','" + companyfax1 + "','" + companynet1 + "','" + companyemail1 + "','" + linkman1 + "','" + linkmantel1 + "','" + remark1 + "')";
		 dbManager.executeUpdate(strSQL);
		 return this.success();
	}
	
	/**
	 * 合并重复的仓库产品
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> combineProduct(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String targetId = runtime.getParam("targetId");
		if(id.equals(targetId)) {
			return this.errorMsg("不能合并相同产品");
		}
		Warehouse product = service.getProductById(id);
		Warehouse productTarget = service.getProductById(targetId);
		
		if(!product.getPro_model().equals(productTarget.getPro_model())) {
			return this.errorMsg("两个产品的型号不相同 ");
		}
		
		String sql = "update warehouse set pro_num = pro_num + ? where id = ?";
		dbManager.update(sql, new Object[] {productTarget.getPro_num(),id});
		sql = "delete from warehouse where id = ?";
		dbManager.update(sql, new Object[] {targetId});
		return this.success();
	}
	
	/**
	 * 根据采购单创建入库单
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> generateInWarehouse(WebRuntime runtime) throws Exception {
		
		int purchasingId = runtime.getInt("purchasingId");
		Purchasing purchasing = purchasingService.getPurchasingById(String.valueOf(purchasingId));
		Userinfo uesrinfo = this.getUserinfo(runtime);
		InWarehouse in = new InWarehouse();
		in.setPurchasingId(purchasingId);
		in.setSupplier(purchasing.getSupplier());
		in.setSp_number(purchasing.getSupplierNumber());
		in.setInt_types("采购入库");
		in.setMoney(purchasing.getMoney());
		in.setG_man("");
		in.setRemark("");
		
		int ddid = this.addInWarehouse(in,uesrinfo);

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		res.put("id", ddid);
		return res;
		
	}
	
	/**
	 * 根据采购单创建入库单
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> markCheckIn(WebRuntime runtime) throws Exception {
		
		int purchasingId = runtime.getInt("purchasingId");
		String sql = "update procure set l_spqk='已入库' where id = ?";
		dbManager.update(sql,new Object[] {purchasingId});
		return this.success();
		
	}
	
	public Map<String,Object> delLogisticsCompany(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql="delete from hdcompany where id=?";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	public Map<String,Object> updatePurchasingPrice(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String selljg = runtime.getParam("price");
		String current = runtime.getParam("current");
		String sql = "update warehouse set pro_price=?,price_hb=? where id = ?";
		dbManager.update(sql, new Object[] {selljg,current,id});
		return this.success();
	}
	
	public List<CommonToolbarItem> getToolbarItemList(WebRuntime runtime) throws Exception {
		boolean warehouseViewPurchasingPrice = this.existRight(runtime, "warehouse_view_purchasing_price");
		List<CommonToolbarItem> res = new ArrayList<CommonToolbarItem>();
		if(warehouseViewPurchasingPrice) {
			res.add(new CommonToolbarItem("采购价管理","editPurchasing"));
		}
		
		return res;
	}
	
	/**
	 * 导出仓库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> export(WebRuntime runtime) throws Exception {
		
		HttpTransfer ht = HttpTransferFactory.generate("ReportCenter");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("template", "warehouse_product_export");
		Map<String,Object> res = ht.getMap("report!generate", p);
		String uuid = (String) res.get("uuid");
		return this.success("uuid", uuid);
		
	}
	
	/**
	 * 已审批订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> listSale(WebRuntime runtime)
			throws Exception {
		
		PageBean pageBean = runtime.getPageBean(50);
		Map<String,String> param = runtime.getParamMap();
		return saleService.listWarehouseSaleList(param, pageBean);
		
	}
	
	/**
	 * 已审批订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listSaleApproving(WebRuntime runtime)
			throws Exception {
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select count(*) from subscribe ";
		String sqlWhere = " where (state='待审批' or state='待复审')";
		int count = dbManager.getCount(sql + sqlWhere);
		sql = "select top "+pageBean.getTop()+" * from subscribe ";
		String sqlOrderBy = " order by number desc";
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, true);
		return this.getPagingResult(list, runtime, count);
		
	}
	
	/**
	 * 已审批订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> listSaleOuted(WebRuntime runtime)
			throws Exception {
		
		PageBean pageBean = runtime.getPageBean(50);
		Map<String,String> param = runtime.getParamMap();
		return saleService.listWarehouseSaleList(param, pageBean);
		
	}
	
	/**
	 *通过型号查询 产品列表
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listByModel(WebRuntime runtime) throws Exception {
		String model = runtime.getParam("model");
		if (model!=null) {
			model = model.trim();
		}
		String sql = "select * from warehouse where pro_model = ?";
		List list = dbManager.queryForList(sql, new Object[] {model}, true);
		return this.success("data", list);
	}


}
