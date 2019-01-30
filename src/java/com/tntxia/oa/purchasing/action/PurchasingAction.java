package com.tntxia.oa.purchasing.action;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.CommonOpen;
import com.tntxia.oa.purchasing.dao.PurchasingDao;

import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.oa.purchasing.entity.PurchasingRefundProduct;
import com.tntxia.oa.sale.dao.SaleDao;
import com.tntxia.oa.supplier.Supplier;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.WarehouseManager;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.util.DBManagerUtil;

public class PurchasingAction extends CommonAction {

	private SaleDao saleDao;

	private PurchasingDao purchasingDao;

	private UserDao userDao;

	private DBManager dbManager = DBManagerUtil.getDBManager();

	public SaleDao getSaleDao() {
		return saleDao;
	}

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}

	public void setPurchasingDao(PurchasingDao purchasingDao) {
		this.purchasingDao = purchasingDao;
	}

	@SuppressWarnings("rawtypes")
	private BigDecimal getTotalPrice(Integer id) throws Exception {
		
		String strSQLpro = "select num,selljg from cgpro where  ddid='" + id
				+ "'";
		List list = dbManager.queryForList(strSQLpro, true);
		BigDecimal totalPrice = BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			Integer num = (Integer) m.get("num");
			BigDecimal selljg = (BigDecimal) m.get("selljg");
			totalPrice = totalPrice
					.add(selljg.multiply(BigDecimal.valueOf(num)));
		}
		return totalPrice;
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
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView listApproved(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String deptjb = (String) session.getAttribute("deptjb");

		PageBean pageBean = this.getPageBean(request, 50);

		int top = pageBean.getTop();
		String countSql = "select count(*) from procure";

		String sql = "select top "
				+ top
				+ " id,number,coname,l_spqk,l_spman,pay_je,sub,man,l_fspman,l_firspman,senddate,money,datetime from procure";
		String sqlWhere = " where ";
		String sqlOrderBy = " order by number desc ";

		if (this.existRight(request, "cgview")) {
			sqlWhere += " l_deptjb like '" + deptjb + "%'";
		} else {
			sqlWhere += " man='" + username + "'";
		}

		sqlWhere += " and (l_spqk='审批通过' or l_spqk='合同已确认' or  l_spqk='已入库' or l_spqk='待入库' or  l_spqk='全部退货' or  l_spqk='部分退货') ";

		String pro_model = ParamUtils.unescape(request, "pro_model");
		if (StringUtils.isNotEmpty(pro_model)) {
			sqlWhere += " and id in (select ddid from cgpro where epro like '%"
					+ pro_model + "%')";
		}
		
		String pro_pp = ParamUtils.unescape(request, "pro_pp");
		if(StringUtils.isNotEmpty(pro_pp)){
			sqlWhere += " and id in (select ddid from cgpro where supplier like '%"
					+ pro_pp + "%')";
		}

		String coname = ParamUtils.unescape(request, "supplier");
		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%'";
		}

		String number = ParamUtils.unescape(request, "number");
		if (StringUtils.isNotEmpty(number)) {
			sqlWhere += " and number like '%" + number + "%'";
		}

		String startDate = request.getParameter("startdate");
		if (StringUtils.isNotEmpty(startDate)) {
			sqlWhere += " and datetime >= '" + startDate + "'";
		}

		String endDate = request.getParameter("enddate");
		if (StringUtils.isNotEmpty(endDate)) {
			sqlWhere += " and datetime <= '" + endDate + "'";
		}

		String man = ParamUtils.unescape(request, "man");
		if (StringUtils.isNotEmpty(man)) {
			sqlWhere += " and man = '" + man + "'";
		}

		String status = request.getParameter("status");
		if (StringUtils.isNotEmpty(status)) {
			sqlWhere += " and l_spqk = '" + getStatus(status) + "'";
		}

		int intRowCount = dbManager.getCount(countSql + sqlWhere);
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, true);
		List rows = this.getRows(list, pageBean);

		for (int i = 0; i < rows.size(); i++) {
			Map m = (Map) rows.get(i);
			Integer id = (Integer) m.get("id");
			m.put("totalPrice", this.getTotalPrice(id));
		}
		
		Map res = new HashMap();
		res.put("totalAmount", intRowCount);
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		res.put("rows", rows);

		return this.exportJSONObject(response, res);

	}

	/**
	 * 已审订单查询
	 * 
	 * @param request
	 * @param arg1
	 */
	public ModelAndView listApprovedSearch(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		List<String> purchasingManList = new ArrayList<String>();

		List<String> hbList = new ArrayList<String>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		int intRowCount = 0;

		int intPageCount = 0;
		int intPage;

		int numCount = 0;

		HttpSession session = request.getSession();

		String number = request.getParameter("number").trim();

		String man = request.getParameter("man").trim();
		String startdate = request.getParameter("startdate").trim();
		String enddate = request.getParameter("enddate").trim();

		String pro_model = request.getParameter("pro_model").trim();

		String pro_pp = request.getParameter("pro_pp").trim();

		String coname = request.getParameter("supplier").trim();
		String sub1 = request.getParameter("sub").trim();
		String senddate = request.getParameter("senddate").trim();
		String zt = request.getParameter("zt").trim();

		java.sql.ResultSet sqlRst;

		java.lang.String strSQL;
		int intPageSize;

		java.lang.String strPage;
		int i, j;
		intPageSize = 50;
		strPage = request.getParameter("page");
		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		DBConnection einfodb = new DBConnection();

		String username1 = (String) session.getAttribute("username");
		String restrain_name = (String) session.getAttribute("restrain_name");
		String deptjb = (String) session.getAttribute("deptjb");
		String modsql = "select * from restrain where restrain_name='"
				+ restrain_name + "'";
		ResultSet rsmod = einfodb.executeQuery(modsql);
		if (rsmod.next()) {

			String cgview = rsmod.getString("cgview").trim();
			if (cgview.equals("有")) {

				strSQL = "select count(*) from cgview  where  senddate like '%"
						+ senddate + "%'  and number like '%" + number
						+ "%'  and  coname like  '%" + coname
						+ "%'  and  l_spqk like  '%" + zt
						+ "%'  and  man  like '%" + man
						+ "%'  and  sub  like '%" + sub1
						+ "%'   and  epro  like '%" + pro_model
						+ "%'  and  supplier  like '%" + pro_pp
						+ "%'   and datetime>='" + startdate + "' and '"
						+ enddate + "'>=datetime  and   l_deptjb like '"
						+ deptjb + "%' and l_spqk='审批通过' or senddate like '%"
						+ senddate + "%'  and number like '%" + number
						+ "%'  and  coname like  '%" + coname
						+ "%'   and  l_spqk like  '%" + zt
						+ "%'  and  man  like '%" + man
						+ "%'  and  sub  like '%" + sub1
						+ "%'   and  epro  like '%" + pro_model
						+ "%' and supplier  like '%" + pro_pp
						+ "%'  and datetime>='" + startdate + "' and '"
						+ enddate + "'>=datetime  and   l_deptjb like '"
						+ deptjb + "%' and l_spqk='已入库' or senddate like '%"
						+ senddate + "%'  and number like '%" + number
						+ "%'  and  coname like  '%" + coname
						+ "%' and  l_spqk like  '%" + zt
						+ "%'  and  man  like '%" + man
						+ "%'  and  sub  like '%" + sub1
						+ "%'   and  epro  like '%" + pro_model
						+ "%' and supplier  like '%" + pro_pp
						+ "%'   and datetime>='" + startdate + "' and '"
						+ enddate + "'>=datetime  and   l_deptjb like '"
						+ deptjb + "%' and l_spqk='合同已确认' or senddate like '%"
						+ senddate + "%'  and number like '%" + number
						+ "%'  and  coname like  '%" + coname
						+ "%' and  l_spqk like  '%" + zt
						+ "%'  and  man  like '%" + man
						+ "%'  and  sub  like '%" + sub1
						+ "%'   and  epro  like '%" + pro_model
						+ "%' and supplier  like '%" + pro_pp
						+ "%'   and datetime>='" + startdate + "' and '"
						+ enddate + "'>=datetime  and   l_deptjb like '"
						+ deptjb + "%' and l_spqk='全部退货' or senddate like '%"
						+ senddate + "%'  and number like '%" + number
						+ "%'  and  coname like  '%" + coname
						+ "%' and  l_spqk like  '%" + zt
						+ "%'  and  man  like '%" + man
						+ "%'  and  sub  like '%" + sub1
						+ "%'   and  epro  like '%" + pro_model
						+ "%' and supplier  like '%" + pro_pp
						+ "%'   and datetime>='" + startdate + "' and '"
						+ enddate + "'>=datetime  and   l_deptjb like '"
						+ deptjb + "%' and l_spqk='部分退货'";

			} else
				strSQL = "select count(*) from  cgview  where  senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'   and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='审批通过' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%'  and coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and  supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='已入库' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='合同已确认' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='全部退货' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='部分退货'";

			sqlRst = einfodb.executeQuery(strSQL);
			sqlRst.next();
			intRowCount = sqlRst.getInt(1);
			sqlRst.close();
			intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
			if (intPage > intPageCount)
				intPage = intPageCount;

			String sq = "select money,sum(num*selljg) je from cgview where senddate like '%"
					+ senddate
					+ "%'  and  man='"
					+ username1
					+ "'  and  number like '%"
					+ number
					+ "%' and  coname like  '%"
					+ coname
					+ "%'  and  man  like '%"
					+ man
					+ "%'   and datetime>='"
					+ startdate
					+ "' and '"
					+ enddate
					+ "'>=datetime  and  epro  like '%"
					+ pro_model
					+ "%' and supplier  like '%"
					+ pro_pp
					+ "%' and  sub  like '%"
					+ sub1
					+ "%'  and  l_spqk like  '%"
					+ zt
					+ "%' and l_spqk='审批通过' or senddate like '%"
					+ senddate
					+ "%'  and  man='"
					+ username1
					+ "'  and  number like '%"
					+ number
					+ "%' and  coname like  '%"
					+ coname
					+ "%'  and  man  like '%"
					+ man
					+ "%'   and datetime>='"
					+ startdate
					+ "' and '"
					+ enddate
					+ "'>=datetime  and  epro  like '%"
					+ pro_model
					+ "%' and supplier  like '%"
					+ pro_pp
					+ "%' and  sub  like '%"
					+ sub1
					+ "%'  and  l_spqk like  '%"
					+ zt
					+ "%' and l_spqk='已入库' or senddate like '%"
					+ senddate
					+ "%'  and  man='"
					+ username1
					+ "'  and  number like '%"
					+ number
					+ "%' and  coname like  '%"
					+ coname
					+ "%'  and  man  like '%"
					+ man
					+ "%'   and datetime>='"
					+ startdate
					+ "' and '"
					+ enddate
					+ "'>=datetime  and  epro  like '%"
					+ pro_model
					+ "%' and supplier  like '%"
					+ pro_pp
					+ "%' and  sub  like '%"
					+ sub1
					+ "%'  and  l_spqk like  '%"
					+ zt
					+ "%' and l_spqk='合同已确认' or senddate like '%"
					+ senddate
					+ "%'  and  man='"
					+ username1
					+ "'  and  number like '%"
					+ number
					+ "%' and  coname like  '%"
					+ coname
					+ "%'  and  man  like '%"
					+ man
					+ "%'   and datetime>='"
					+ startdate
					+ "' and '"
					+ enddate
					+ "'>=datetime  and  epro  like '%"
					+ pro_model
					+ "%' and supplier  like '%"
					+ pro_pp
					+ "%' and  sub  like '%"
					+ sub1
					+ "%'  and  l_spqk like  '%"
					+ zt
					+ "%' and l_spqk='全部退货' or senddate like '%"
					+ senddate
					+ "%'  and  man='"
					+ username1
					+ "'  and  number like '%"
					+ number
					+ "%' and  coname like  '%"
					+ coname
					+ "%'  and  man  like '%"
					+ man
					+ "%'   and datetime>='"
					+ startdate
					+ "' and '"
					+ enddate
					+ "'>=datetime  and  epro  like '%"
					+ pro_model
					+ "%' and supplier  like '%"
					+ pro_pp
					+ "%' and  sub  like '%"
					+ sub1
					+ "%'  and  l_spqk like  '%"
					+ zt
					+ "%' and l_spqk='部分退货' group by money order by 2 desc";

			if (cgview.equals("有")) {

				sq = "select money,sum(num*selljg) je from cgview where  senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='审批通过' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='已入库' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%' and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='合同已确认' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%' and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='全部退货' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%' and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='部分退货' group by money order by 2 desc";

				strSQL = "select  id,number,coname,epro,num,cgpro_num,selljg,pay_je,money,rate,l_spqk,man,cgpro_sdatetime from  cgview where  senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and  supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='审批通过' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='已入库' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='合同已确认' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='全部退货' or senddate like '%"
						+ senddate
						+ "%'  and number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%' and  l_spqk like  '%"
						+ zt
						+ "%'  and  man  like '%"
						+ man
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'   and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and   l_deptjb like '"
						+ deptjb
						+ "%' and l_spqk='部分退货' order  by  number  desc ";

			} else {
				strSQL = "select  id,number,coname,epro,num,cgpro_num,selljg,money,rate,l_spqk,man,cgpro_sdatetime from  cgview  where  senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='审批通过' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%' and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='已入库' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='合同已确认' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='全部退货' or senddate like '%"
						+ senddate
						+ "%'  and  man='"
						+ username1
						+ "'  and  number like '%"
						+ number
						+ "%'  and  coname like  '%"
						+ coname
						+ "%'  and  man  like '%"
						+ man
						+ "%'   and datetime>='"
						+ startdate
						+ "' and '"
						+ enddate
						+ "'>=datetime  and  epro  like '%"
						+ pro_model
						+ "%' and supplier  like '%"
						+ pro_pp
						+ "%'  and  sub  like '%"
						+ sub1
						+ "%'  and  l_spqk like  '%"
						+ zt
						+ "%' and l_spqk='部分退货'";

			}

			sqlRst = einfodb.executeQuery(strSQL);
			i = (intPage - 1) * intPageSize;
			for (j = 0; j < i; j++)
				sqlRst.next();

			i = 0;

			while (i < intPageSize && sqlRst.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int id = sqlRst.getInt(1);
				map.put("id", id);
				map.put("number", sqlRst.getString("number"));
				map.put("coname", sqlRst.getString("coname"));
				map.put("epro", sqlRst.getString("epro"));
				int num = sqlRst.getInt("num");
				map.put("num", num);
				double salejg1 = 0;
				String tmpsalejg1 = sqlRst.getString("selljg");
				if (tmpsalejg1 != null)
					salejg1 = Double.parseDouble(tmpsalejg1);
				double salehj = num * salejg1;
				map.put("salejg", salejg1);
				map.put("salehj", salehj);
				map.put("money", sqlRst.getString("money"));
				map.put("rate", sqlRst.getString("rate"));
				map.put("money", sqlRst.getString("money"));
				map.put("man", sqlRst.getString("man"));
				map.put("rate", sqlRst.getString("rate"));
				map.put("l_spqk", sqlRst.getString("l_spqk"));
				map.put("cgpro_sdatetime", sqlRst.getString("cgpro_sdatetime"));

				resultList.add(map);
			}
			try {
				ResultSet rs = einfodb
						.executeQuery("select name from username where yjxs='采购部' or yjxs='采购二部'");
				while (rs.next()) {
					String name = rs.getString("name");
					purchasingManList.add(name);

				}
				rs.close();
			} catch (Exception e) {
			}

			ResultSet prst = einfodb.executeQuery(sq);

			while (prst.next()) {
				String l_hbs = prst.getString("money").trim();

				double sprice_je = prst.getDouble("je");
				hbList.add(l_hbs + ":" + nf.format(sprice_je) + "；");

			}
			prst.close();

			sqlRst.close();

			sq = "select sum(num) c from cgview where  senddate like '%"
					+ senddate + "%'  and number like '%" + number
					+ "%'  and  coname like  '%" + coname
					+ "%' and  l_spqk like  '%" + zt + "%'  and  man  like '%"
					+ man + "%'  and  sub  like '%" + sub1
					+ "%'   and  epro  like '%" + pro_model
					+ "%' and  supplier  like '%" + pro_pp
					+ "%'  and datetime>='" + startdate + "' and '" + enddate
					+ "'>=datetime  and   l_deptjb like '" + deptjb
					+ "%' and l_spqk='审批通过' or senddate like '%" + senddate
					+ "%'  and number like '%" + number
					+ "%'  and  coname like  '%" + coname
					+ "%' and  l_spqk like  '%" + zt + "%'  and  man  like '%"
					+ man + "%'  and  sub  like '%" + sub1
					+ "%'   and  epro  like '%" + pro_model
					+ "%' and supplier  like '%" + pro_pp
					+ "%'  and datetime>='" + startdate + "' and '" + enddate
					+ "'>=datetime  and   l_deptjb like '" + deptjb
					+ "%' and l_spqk='已入库' or senddate like '%" + senddate
					+ "%'  and number like '%" + number
					+ "%'  and  coname like  '%" + coname
					+ "%' and  l_spqk like  '%" + zt + "%'  and  man  like '%"
					+ man + "%'  and  sub  like '%" + sub1
					+ "%'   and  epro  like '%" + pro_model
					+ "%' and supplier  like '%" + pro_pp
					+ "%'  and datetime>='" + startdate + "' and '" + enddate
					+ "'>=datetime  and   l_deptjb like '" + deptjb
					+ "%' and l_spqk='合同已确认' or senddate like '%" + senddate
					+ "%'  and number like '%" + number
					+ "%'  and  coname like  '%" + coname
					+ "%' and  l_spqk like  '%" + zt + "%'  and  man  like '%"
					+ man + "%'  and  sub  like '%" + sub1
					+ "%'   and  epro  like '%" + pro_model
					+ "%' and supplier  like '%" + pro_pp
					+ "%'  and datetime>='" + startdate + "' and '" + enddate
					+ "'>=datetime  and   l_deptjb like '" + deptjb
					+ "%' and l_spqk='全部退货' or senddate like '%" + senddate
					+ "%'  and number like '%" + number
					+ "%'  and  coname like  '%" + coname
					+ "%' and  l_spqk like  '%" + zt + "%'  and  man  like '%"
					+ man + "%'  and  sub  like '%" + sub1
					+ "%'   and  epro  like '%" + pro_model
					+ "%' and supplier  like '%" + pro_pp
					+ "%'  and datetime>='" + startdate + "' and '" + enddate
					+ "'>=datetime  and   l_deptjb like '" + deptjb
					+ "%' and l_spqk='部分退货'";
			prst = einfodb.executeQuery(sq);

			if (prst.next()) {
				numCount = prst.getInt("c");

			}
			prst.close();

		}

		rsmod.close();

		einfodb.closeStmt();
		einfodb.closeConn();

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("purchasingManList", purchasingManList);
		result.put("hbList", hbList);
		result.put("resultList", resultList);

		result.put("intRowCount", intRowCount);
		result.put("intPageCount", intPageCount);
		result.put("intPage", intPage);
		result.put("numCount", numCount);

		return new ModelAndView("ddgl/ymain-search", result);

	}

	/**
	 * 待审批订单列表
	 * 
	 * @param request
	 * @param arg1
	 */
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView listDeleted(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		

		DBConnection einfodb = new DBConnection();
		HttpSession session = request.getSession();


		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		java.sql.ResultSet sqlRst;
		java.lang.String strSQL;
		int intPageSize;
		int intRowCount=0;
		int intPageCount;
		int intPage;
		java.lang.String strPage;
		int i, j;
		intPageSize = 50;
		strPage = request.getParameter("page");

		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}
		
		Map res = new HashMap();
		
		String restrain_name = (String) session.getAttribute("restrain_name");
		String modsql = "select * from restrain where restrain_name='"
				+ restrain_name + "'";
		ResultSet rsmod = einfodb.executeQuery(modsql);
		if (rsmod.next()) {

			String cgview = rsmod.getString("cgview").trim();

			String number = request.getParameter("number");

			String sub = request.getParameter("sub");

			String sqlWhere = "";

			if (sub != null) {

				sqlWhere += " and sub like '%" + sub + "%'";

			}

			if (number != null) {

				sqlWhere += " and number like '%" + number + "%'";

			}

			String manP = request.getParameter("man");
			if (manP != null) {
				sqlWhere += " and man like '%" + manP + "%' ";
			}
			String conameP = request.getParameter("coname");
			if (conameP != null) {
				sqlWhere += " and coname like '%" + conameP + "%'";
			}

			String startdate = request.getParameter("startdate");
			if (startdate != null)
				sqlWhere += " and datetime >= '" + startdate + "'";
			String enddate = request.getParameter("enddate");
			if (enddate != null)
				sqlWhere += " and datetime <= '" + enddate + "'";

			String brand = request.getParameter("brand"); // 品牌查询

			if (brand != null && brand.trim().length() > 0) {
				sqlWhere += " and id in (select ddid from cgpro where supplier like '%"
						+ brand + "%')";

			}

			String epro = request.getParameter("epro"); // 型号查询
			if (epro == null)
				epro = "";
			if (epro != null && epro.trim().length() > 0) {
				sqlWhere += " and id in (select ddid from cgpro where epro like '%"
						+ epro + "%') ";
			}

			if (cgview.equals("有")) {
				strSQL = "select count(*) from procure  where l_spqk='已删除' "
						+ sqlWhere;
			} else
				strSQL = "select count(*) from procure  where and l_spqk='已删除'"
						+ sqlWhere;
			sqlRst = einfodb.executeQuery(strSQL);
			sqlRst.next();
			intRowCount = sqlRst.getInt(1);

			sqlRst.close();
			intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
			if (intPage > intPageCount)
				intPage = intPageCount;

			if (cgview.equals("有")) {
				strSQL = "select id,number,coname,l_spqk,l_spman,pay_je,sub,man,l_fspman,l_firspman,senddate,money,datetime from procure  where l_spqk='已删除'"
						+ sqlWhere;
			} else
				strSQL = "select id,number,coname,l_spqk,l_spman,pay_je,sub,man,l_fspman,l_firspman,senddate,money,datetime from procure where l_spqk='已删除'"
						+ sqlWhere;

			sqlWhere += " order by datetime desc";
			strSQL += sqlWhere;

			sqlRst = einfodb.executeQuery(strSQL);
			i = (intPage - 1) * intPageSize;
			for (j = 0; j < i; j++)
				sqlRst.next();
			
			i = 0;
			List rows = new ArrayList();
			   while(i<intPageSize && sqlRst.next()){
				   
				   int id=sqlRst.getInt("id");
				    
				    String coname=sqlRst.getString(3);
				    String l_spqk=sqlRst.getString(4).trim();
				    String spman=sqlRst.getString(5).trim();
				    String  pay_je=sqlRst.getString(6).trim();
				    String  pay=sqlRst.getString(7).trim();
				    String man=sqlRst.getString(8).trim();
				    String fspman=sqlRst.getString(9).trim();
				    String firspman=sqlRst.getString(10).trim();
				    String senddate=sqlRst.getString("senddate").trim();
				    String money1=sqlRst.getString("money").trim();
				    
				    String remark = "";                            // 删除原因
				    ResultSet rsLog = einfodb.executeQuery("select remark from dd_del_log where ddid = '"+id+"'");
				    if(rsLog.next())
				    	remark = rsLog.getString("remark");
				    rsLog.close();
				    
				    Map map = new HashMap();
				    map.put("id", id);
				    map.put("number", sqlRst.getString("number"));
				    map.put("coname", coname);
				    map.put("l_spqk", l_spqk);
				    map.put("spman", spman);
				    map.put("pay_je", pay_je);
				    map.put("pay", pay);
				    map.put("man", man);
				    map.put("fspman", fspman);
				    map.put("firspman", firspman);
				    map.put("senddate", senddate);
				    map.put("money", money1);
				    map.put("remark", remark);
				    rows.add(map);
				   i++;
			   }
			   
			   res.put("rows", rows);
		}
		
		res.put("page", intPage);
		res.put("pageSize", intPageSize);
		res.put("totalAmount", intRowCount);
		return this.exportJSONObject(response, res);
	}

	/**
	 * 采购退货出库 单个
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView purchasingRefundFromWarehouseSingle(
			HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		DBConnection einfodb = new DBConnection();

		int id = ServletRequestUtils.getIntParameter(request, "id");

		PurchasingRefundProduct refundProduct = purchasingDao
				.getPurchasingRefundProductById(id);

		String purchasingNumber = refundProduct.getPurchasingNumber();

		String th_num = request.getParameter("th_num");
		String wid = request.getParameter("wid");

		WarehouseManager warehouseManager = new WarehouseManager();

		Warehouse query = new Warehouse();
		query.setId(wid);
		Warehouse warehouse = warehouseManager.getWarehouseSingle(query);

		if (warehouse.getPro_num() < Integer.valueOf(th_num)) {
			result.put("success", false);
			result.put("message", "仓库数量少于退货数量！！");
			return new ModelAndView("common/result", result);

		}

		String strSQLwf = "update  th_pro_supplier set s_num=s_num+" + th_num
				+ " where id='" + id + "' ";
		boolean twf = einfodb.executeUpdate(strSQLwf);
		if (!twf) {
			result.put("success", false);
			result.put("message", "更新退货产品失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);

		}

		String strSQLw = "update warehouse set pro_num=pro_num-" + th_num
				+ " where id=" + wid;
		boolean tw = einfodb.executeUpdate(strSQLw);
		if (!tw) {
			result.put("success", false);
			result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);

		}

		String sql = "update procure set l_spqk = '部分退货' where number = '"
				+ purchasingNumber + "'";
		tw = einfodb.executeUpdate(sql);
		if (!tw) {
			result.put("success", false);
			result.put("msg", "更新采购订单状态失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);
		}

		einfodb.close();

		result.put("success", true);
		result.put("msg", "退货成功");

		return new ModelAndView("common/result", result);
	}

	/**
	 * 采购退货出库 全部
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView purchasingRefundFromWarehouseAll(
			HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> result = new HashMap<String, Object>();

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String ddid1 = request.getParameter("ddid");
		String sqldd = "select  number,coname,co_number,man,money,datetime,dept,deptjb,sub from th_table_supplier where id='"
				+ ddid1 + "'";
		ResultSet rsdd = einfodb.executeQuery(sqldd);
		if (!rsdd.next()) {
			result.put("success", false);
			result.put("msg", "退货出库单不存在");
			return new ModelAndView("common/result", result);

		}

		String refundState = "全部退货";

		String thnumber = rsdd.getString("number");
		String coname = rsdd.getString("coname");
		String co_number = rsdd.getString("co_number");
		String sales_man = rsdd.getString("man");
		String allPro_model = "";
		String allPro_name = "";
		String allNum = "";
		String allSupplier = "";
		String sub = rsdd.getString("sub");

		List<String> purchasingNumberList = new ArrayList<String>();

		String strSQLpro = "select id,epro,cpro,fypronum,s_c_num,s_num,num,unit,salejg,pricehb,supplier,wid,fy_states,cg_number,th_number,remark from th_pro_supplier where  ddid='"
				+ ddid1 + "'";
		ResultSet prs = einfodb.executeQuery(strSQLpro);

		while (prs.next()) {
			int ddproid = prs.getInt("id");
			String pro_model = prs.getString("epro").trim();
			allPro_model += pro_model + ",";
			String pro_name = prs.getString("cpro");
			allPro_name += pro_name + ",";
			String pro_number = "0";
			String fypronum = prs.getString("fypronum");
			if (fypronum != null) {
				pro_number = fypronum.trim();
			}

			int s_num = prs.getInt("s_num");
			int num = prs.getInt("num");
			int num1 = num - s_num;

			String punit = prs.getString("unit");
			String saleprice = prs.getString("salejg");
			String salehb = prs.getString("pricehb");
			String supplier = prs.getString("supplier");
			allSupplier += supplier + ",";
			String wid = prs.getString("wid");
			String fy_states = prs.getString("fy_states");
			String cg_number = prs.getString("cg_number");
			purchasingNumberList.add(cg_number);
			String th_number = prs.getString("th_number");
			String pro_remark = prs.getString("remark");

			String sql = null;

			if (th_number != null) {
				sql = "select pro_num,pro_addr from warehouse where pro_addr='"
						+ wid + "' and pro_model='" + pro_model
						+ "'  and  pro_name='" + pro_name
						+ "' and  th_number='" + th_number + "'";
			} else {
				sql = "select pro_num,pro_addr from warehouse where pro_addr='"
						+ wid + "' and pro_model='" + pro_model
						+ "'  and  pro_name='" + pro_name + "'";
			}

			int warehouseid = Integer.valueOf(request.getParameter("wid"
					+ ddproid));

			sql = "select pro_num,pro_addr from warehouse where id="
					+ warehouseid + " ";

			ResultSet rs = einfodb.executeQuery(sql);
			if (!rs.next()) {
				result.put("success", false);
				result.put("message", "仓库暂无该产品或批号不正确:" + "产品ID：" + warehouseid
						+ ";" + pro_model + ";批号:" + pro_name + ";备注:"
						+ pro_remark);
				return new ModelAndView("common/result", result);

			}

			int pro_num = rs.getInt("pro_num");
			String proaddr = rs.getString("pro_addr");
			int zpro_num = pro_num - num1;

			allNum += zpro_num + ",";

			if (warehouseid == 0) {
				result.put("success", false);
				result.put("message", "请选择退货产品");
				return new ModelAndView("common/result", result);

			} else {
				if (zpro_num < 0) {
					result.put("success", false);
					result.put("message", "库存不足:" + zpro_num);
					return new ModelAndView("common/result", result);

				}
				String strSQLw = "update warehouse set pro_num='" + zpro_num
						+ "' where id=" + warehouseid;
				boolean tw = einfodb.executeUpdate(strSQLw);
				if (!tw) {
					result.put("success", false);
					result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
					return new ModelAndView("common/result", result);

				}
			}

			int no = 1;
			java.sql.ResultSet sqlRsto;
			java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
					"yyMM");
			String number = simple1.format(new java.util.Date());
			String strSQL1 = "select  *  from outhouse where pro_out_num like '"
					+ number + "%'   order by in_no desc";
			sqlRsto = einfodb.executeQuery(strSQL1);
			if (sqlRsto.next()) {
				no = sqlRsto.getInt("in_no") + 1;
			}
			String sno1 = "";
			if (no < 10) {
				sno1 = "000";
			} else if ((10 <= no) & (no < 100)) {
				sno1 = "00";
			} else if ((100 <= no) & (no < 1000)) {
				sno1 = "0";
			} else
				sno1 = "";
			sqlRsto.close();

			String strSQLq = "insert into outhouse([pro_fynum],[pro_coname],[pro_model],[pro_name],[pro_num],[pro_unit],[pro_supplier]"
					+ ",[pro_datetime],[pro_number],[slinkman],[slinktel],[states],[ddid],[remark],[wid],[pro_coname_num],[pro_sales_price]"
					+ ",[pro_price_hb],[pro_rate_types],[pro_rate],[pro_out_num],[in_no]) values('"
					+ thnumber
					+ "','"
					+ coname
					+ "','"
					+ pro_model
					+ "','"
					+ pro_name
					+ "','"
					+ num1
					+ "','"
					+ punit
					+ "','"
					+ supplier
					+ "','"
					+ currentDate
					+ "','-"
					+ pro_number
					+ "','','"
					+ proaddr
					+ "','已出库','"
					+ ddid1
					+ "','"
					+ fy_states
					+ "','2','"
					+ co_number
					+ "','"
					+ saleprice
					+ "','"
					+ salehb
					+ "','"
					+ wid
					+ "','0','"
					+ number
					+ ""
					+ sno1 + "" + no + "','" + no + "')";
			boolean tq = einfodb.executeUpdate(strSQLq);
			if (!tq) {
				result.put("success", false);
				result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
				return new ModelAndView("common/result", result);
			}
			String strSQLwf = "update  th_pro_supplier set s_num='" + num
					+ "',s_c_num='" + num + "',s_tr_date='" + currentDate
					+ "',fy_states='待发运'  where id='" + ddproid + "' ";
			boolean twf = einfodb.executeUpdate(strSQLwf);
			if (!twf) {
				result.put("success", false);
				result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
				return new ModelAndView("common/result", result);
			}

			try {
				PurchasingProduct purchasingProduct = purchasingDao
						.getPurchasingProductListByNumberAndModel(cg_number,
								pro_model);

				if (purchasingProduct.getNum() > num) {
					refundState = "部分退货";
				}
			} catch (Exception e) {
				refundState = "全部退货";
			}

		}

		String ddsqls = "update th_table_supplier set state='已出库' where id='"
				+ ddid1 + "'";
		boolean tssu = einfodb.executeUpdate(ddsqls);
		if (!tssu) {
			result.put("success", false);
			result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);
		}

		// 更新采购订单的状态
		for (String pNumber : purchasingNumberList) {
			try {
				purchasingDao.updatePurchasingStatus(refundState, pNumber);
			} catch (Exception e) {

			}

		}

		einfodb.close();

		result.put("success", true);
		result.put("close", false);
		result.put("message", "退货成功");

		List<CommonOpen> openList = new ArrayList<CommonOpen>();

		CommonOpen open1 = new CommonOpen();
		open1.setUrl("/warehouse/main.jsp");
		open1.setTarget("rtop");
		open1.setWinOpt("height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");
		openList.add(open1);

		CommonOpen open2 = new CommonOpen();
		open2.setUrl("/warehouse/sendmail_th2.jsp?id=" + ddid1 + "&coname="
				+ coname + "&ddnumber=" + thnumber + "&co_number=" + co_number
				+ "&sendto=" + sales_man + "&pro_model=" + allPro_model
				+ "&num=" + allNum + "&sub=" + sub + "&supplier=" + allSupplier
				+ "&pro_name=" + allPro_name);
		open2.setTarget("_blank");
		open2.setWinOpt("height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");
		openList.add(open2);
		result.put("openList", openList);

		return new ModelAndView("common/result", result);
	}

//	/**
//	 * 查看已审核订单
//	 * 
//	 * @param request
//	 * @param arg1
//	 * @return
//	 * @throws Exception
//	 */
//	public ModelAndView viewApproved(HttpServletRequest request,
//			HttpServletResponse arg1) throws Exception {
//
//		int id = ServletRequestUtils.getIntParameter(request, "id");
//
//		DBConnection einfodb = new DBConnection();
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		HttpSession session = request.getSession();
//
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		nf.setMaximumFractionDigits(4);
//		nf.setMinimumFractionDigits(4);
//
//		NumberFormat nf1 = NumberFormat.getNumberInstance();
//		nf1.setMaximumFractionDigits(6);
//		nf1.setMinimumFractionDigits(6);
//
//		String restrain_name = (String) session.getAttribute("restrain_name");
//		String modsql = "select * from restrain where restrain_name='"
//				+ restrain_name + "'";
//		ResultSet rsmod = einfodb.executeQuery(modsql);
//
//		String cgmod = "";
//		String cgview_yes = "";
//
//		if (rsmod.next()) {
//			cgmod = rsmod.getString("cgmod").trim();
//			cgview_yes = rsmod.getString("cgview_yes").trim();
//
//		}
//
//		String sql = "select number,man,sub,subck,coname,co_number,senddate,pay_if,pay_je,datetime,money,tbyq,remarks,l_spqk,l_spman,l_fif,l_fspman,l_firspif,l_firspman,l_spyj,receiver,receiver_tel,receiver_addr,freight,express_company,acct,service_type,pay_type,rate,yfmoney,jydd from procure where id='"
//				+ id + "'";
//		ResultSet rs = einfodb.executeQuery(sql);
//		if (!rs.next()) {
//			result.put("success", false);
//			result.put("message", "采购订单不存在 !");
//			return new ModelAndView("common/result", result);
//
//		}
//
//		result.put("cgmod", cgmod);
//		result.put("cgview_yes", cgview_yes);
//
//		String ddnumber = rs.getString("number");
//		String man = rs.getString("man"); // 负责人
//		String sub = rs.getString("sub");
//		String subck = rs.getString("subck");
//		String coname1 = rs.getString("coname");
//		String co_number = rs.getString("co_number");
//
//		result.put("ddnumber", ddnumber);
//		result.put("man", man);
//		result.put("sub", sub);
//		result.put("subck", subck);
//		result.put("coname1", coname1);
//		result.put("co_number", co_number);
//
//		// 通过供应商名称获取供应商的一些信息
//		String sqls = "select cojsfs,coaddr,cotel,cofax from supplier where coname='"
//				+ coname1 + "'";
//		ResultSet rs1 = einfodb.executeQuery(sqls);
//		String coaddrs = "";
//		String cojsfss = "";
//		String cotels = "";
//		if (rs1.next()) {
//			coaddrs = rs1.getString("coaddr");
//			cojsfss = rs1.getString("cojsfs");
//			cotels = rs1.getString("cotel");
//
//		}
//		result.put("coaddrs", coaddrs);
//		result.put("cojsfss", cojsfss);
//		result.put("cotels", cotels);
//		rs1.close();
//
//		String senddate = rs.getString("senddate");
//		String pay_if = rs.getString("pay_if");
//		String pay_je = rs.getString("pay_je");
//		String datetime = rs.getString("datetime");
//		String money = rs.getString("money");
//		String tbyq = rs.getString("tbyq");
//		String remark = rs.getString("remarks");
//		String jydd = rs.getString("jydd");
//		String l_spqk = rs.getString("l_spqk").trim();
//		String l_spman = rs.getString("l_spman");
//		String fif = rs.getString("l_fif").trim();
//		String l_fspman = rs.getString("l_fspman");
//		String firspif = rs.getString("l_firspif").trim();
//		String firspman = rs.getString("l_firspman");
//		String l_spyj = rs.getString("l_spyj");
//
//		String receiver = rs.getString("receiver");
//		String receiver_tel = rs.getString("receiver_tel");
//		String receiver_addr = rs.getString("receiver_addr");
//		String freight = rs.getString("freight");
//		String express_company = rs.getString("express_company");
//		String acct = rs.getString("acct");
//		String service_type = rs.getString("service_type");
//		String pay_type = rs.getString("pay_type");
//		String yfmoney = rs.getString("yfmoney");
//		String dd_rate = rs.getString("rate");
//
//		result.put("senddate", senddate);
//		result.put("pay_if", pay_if);
//		result.put("pay_je", pay_je);
//		result.put("datetime", datetime);
//		result.put("money", money);
//		result.put("tbyq", tbyq);
//		result.put("remark", remark);
//		result.put("l_spqk", l_spqk);
//		result.put("l_spman", l_spman);
//		result.put("l_fspman", l_fspman);
//		result.put("firspif", firspif);
//		result.put("firspman", firspman);
//		result.put("l_spyj", l_spyj);
//		result.put("receiver", receiver);
//		result.put("receiver_tel", receiver_tel);
//		result.put("receiver_addr", receiver_addr);
//		result.put("freight", freight);
//		result.put("express_company", express_company);
//		result.put("acct", acct);
//		result.put("service_type", service_type);
//		result.put("pay_type", pay_type);
//		result.put("yfmoney", yfmoney);
//		result.put("dd_rate", dd_rate);
//		result.put("fif", fif);
//		result.put("jydd", jydd);
//
//		ProcureManager procureManage = new ProcureManager();
//
//		String subMan = procureManage.getSaleMan(sub);
//		if (subMan == null)
//			subMan = "";
//
//		result.put("subMan", subMan);
//
//		// 计算供应商评分平均值
//		String sqlPf = "select avg(rfq) rfq,avg(bj) bj,avg(ch) ch,avg(fh) fh,avg(th) th from pf_supplier where co_number = '"
//				+ co_number + "'";
//		ResultSet rs_pf = einfodb.executeQuery(sqlPf);
//		double pf_rfq = 0;
//		double pf_bj = 0;
//		double pf_ch = 0;
//		double pf_th = 0;
//		double pf_fh = 0;
//		if (rs_pf.next()) {
//			pf_rfq = rs_pf.getDouble("rfq");
//			pf_bj = rs_pf.getDouble("bj");
//			pf_ch = rs_pf.getDouble("ch");
//			pf_th = rs_pf.getDouble("th");
//			pf_fh = rs_pf.getDouble("fh");
//		}
//		rs_pf.close();
//
//		result.put("pf_rfq", pf_rfq);
//		result.put("pf_bj", pf_bj);
//		result.put("pf_ch", pf_ch);
//		result.put("pf_th", pf_th);
//		result.put("pf_fh", pf_fh);
//
//		java.sql.ResultSet sqlRst;
//		java.lang.String strSQL;
//		strSQL = "select states from payment where orderform='" + id + "'";
//		sqlRst = einfodb.executeQuery(strSQL);
//		String finance_state = "";
//		if (sqlRst.next()) {
//			finance_state = sqlRst.getString("states");
//		}
//
//		result.put("finance_state", finance_state);
//
//		List<PurchasingProduct> purchasingProductList = purchasingDao
//				.getPurchasingProductListByParentId(id);
//
//		result.put("purchasingProductList", purchasingProductList);
//
//		rsmod.close();
//		einfodb.closeStmt();
//		einfodb.closeConn();
//
//		return new ModelAndView("purchasing/approvedView", result);
//	}

	/**
	 * 用订单的ID查询，订单的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView getPurchasingProByPid(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		DBConnection db = new DBConnection();
//
//		HttpSession session = request.getSession();
//
//		String rddid = request.getParameter("rddid");
//		String id = request.getParameter("ddid");
//
//		Purchasing purchasing = purchasingDao.getPurchasingById(id);
//
//		ResultSet sqlRst;
//		String strSQL;
//		int intPageSize;
//		int intRowCount;
//		int intPageCount;
//		int intPage;
//		java.lang.String strPage;
//
//		intPageSize = 2000;
//
//		String lxr = request.getParameter("lxr");
//		String coaddr = request.getParameter("coaddr");
//		String cotel = request.getParameter("cotel");
//		String cofax = request.getParameter("cofax");
//
//		String money = request.getParameter("money");
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		strPage = request.getParameter("page");
//		if (strPage == null) {
//			intPage = 1;
//		} else {
//			intPage = java.lang.Integer.parseInt(strPage);
//			if (intPage < 1)
//				intPage = 1;
//		}
//
//		result.put("intPage", intPage);
//
//		strSQL = "select count(*) from cgpro where  ddid='" + id + "' ";
//		sqlRst = db.executeQuery(strSQL);
//		sqlRst.next();
//		intRowCount = sqlRst.getInt(1);
//		sqlRst.close();
//		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
//		if (intPage > intPageCount)
//			intPage = intPageCount;
//
//		result.put("intPageCount", intPageCount);
//
//		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
//
//		WarehouseManager warehouseManager = new WarehouseManager();
//
//		for (PurchasingProduct pro : purchasingDao
//				.getPurchasingProductListByParentId(id)) {
//
//			Map<String, Object> item = beanToMap(pro);
//
//			String cpro = pro.getCpro();
//			String epro = pro.getEpro();
//			String remark = pro.getRemark();
//			Warehouse query = new Warehouse();
//			query.setPro_model(epro);
//			query.setPro_name(cpro);
//			query.setPro_remark(remark);
//			Warehouse warehouse = warehouseManager.getWarehouseSingle(query,
//					false);
//			int pro_num = warehouse.getPro_num();
//			item.put("warehouuseNum", pro_num);
//
//			String restrain_name = (String) session
//					.getAttribute("restrain_name");
//
//			// 查询出用户所有的仓库权限
//			List<RestrainGP> restrainList = rightDao
//					.getRestrainGP(restrain_name);
//
//			String warehouseSel = "";
//
//			for (RestrainGP r : restrainList) {
//
//				if (r.isView()) {
//					warehouseSel += "<option value='" + r.getName() + "'>"
//							+ r.getName() + " " + pro_num + "</option>";
//
//				}
//
//			}
//
//			item.put("warehouseSel", warehouseSel);
//
//			itemList.add(item);
//		}
//
//		result.put("purchasing", purchasing);
//		result.put("itemList", itemList);
//		result.put("lxr", lxr);
//		result.put("coaddr", coaddr);
//		result.put("cotel", cotel);
//		result.put("cofax", cofax);
//		result.put("money", money);
//		result.put("rddid", rddid);
//		result.put("itemList", itemList);
//
//		return new ModelAndView("purchasing/purchasingProSearchResult", result);
//
//	}

	/**
	 * 增加供应商退货单的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addPurchasingRefundPro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		String fyid = request.getParameter("fyid");

		String money = request.getParameter("money");

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String coname = request.getParameter("coname");

		String cgnumber = request.getParameter("cgnumber");

		String t[] = request.getParameterValues("checkpro");

		if (t != null) {
			for (int i = 0; i < t.length; i++) {
				String strSQLpro = "select id,wid,epro,cpro,pro_number,num,unit,selljg,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,rate,supplier,remark from cgpro where id='"
						+ t[i] + "' ";

				ResultSet prs = db.executeQuery(strSQLpro);

				while (prs.next()) {
					int id = prs.getInt(1);
					String wid = prs.getString("wid").trim();
					String pro_model1 = prs.getString("epro").trim();
					String pro_name1 = prs.getString("cpro");
					String pro_name2 = prs.getString("pro_number");

					String in_num = request.getParameter("in_num" + t[i]);
					String pro_unit1 = prs.getString("unit");

					double selljg = prs.getDouble("selljg");

					String supplier = prs.getString("supplier");
					String remark = prs.getString("remark");

					String strSQLi = "insert into th_pro_supplier(ddid,epro,cpro,num,fypronum,unit,pricehb,fy_states,wid,money,selljg,salejg,rale,rale_types,supplier,fyproall,s_num,s_c_num,remark,cg_number) "
							+ "values('"
							+ fyid
							+ "','"
							+ pro_model1
							+ "','"
							+ pro_name1
							+ "','"
							+ in_num
							+ "','"
							+ pro_name2
							+ "','"
							+ pro_unit1
							+ "','"
							+ money
							+ "','"
							+ coname
							+ "','"
							+ wid
							+ "','"
							+ money
							+ "',"
							+ selljg
							+ ","
							+ selljg
							+ ",0,'','"
							+ supplier
							+ "','no',0,0,'"
							+ remark + "','" + cgnumber + "')";

					boolean ttt = db.executeUpdate(strSQLi);
					if (!ttt) {
						String message = "添加入库产品失败,你所输入的内容超出系统范围或输入类型不符!";
						return getErrorResult(message);
					}

					String strSQLty = "update cgpro set cgpro_num='" + in_num
							+ "',cgpro_sdatetime='" + currentDate
							+ "' where id='" + id + "' ";
					boolean ttt1 = db.executeUpdate(strSQLty);
					if (!ttt1) {
						String message = "修改采购产品信息失败!";
						return getErrorResult(message);

					}
				}
				prs.close();
			}
		}

		boolean ttt1 = db.executeUpdate("update th_table_supplier set coname='"
				+ coname + "',money='" + money + "' where id='" + fyid + "'");

		if (!ttt1) {
			String message = "修改退货单 供应商信息失败!";
			return getErrorResult(message);
		}

		response.sendRedirect("refund/dd-view.jsp?id=" + fyid);

		return null;

	}

	/**
	 * 采购订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView detailView(HttpServletRequest request,
//			HttpServletResponse arg1) throws Exception {
//
//		String id = request.getParameter("id");
//
//		List<String> rightList = this.getRightList(request);
//
//		Purchasing purchasing = purchasingDao.getPurchasingById(id);
//
//		if (purchasing.getNumber() == null) {
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("success", false);
//			result.put("message", "订单不存在!!!");
//			return new ModelAndView("common/result", result);
//		}
//
//		Map<String, Object> result = new HashMap<String, Object>();
//
//		String sub = purchasing.getSaleNumber();
//
//		ProcureManager procureManager = new ProcureManager();
//
//		String saleMan = procureManager.getSaleMan(sub);
//
//		result.put("purchasing", purchasing);
//
//		result.put("saleMan", saleMan);
//
//		List<PurchasingProduct> productList = purchasingDao
//				.getProductByPurchasingId(id);
//		
//		BigDecimal totalPrice = BigDecimal.ZERO;
//		for(PurchasingProduct p : productList){
//			
//			totalPrice = totalPrice.add(p.getSelljg().multiply(BigDecimal.valueOf(p.getNum())));
//			
//		}
//
//		result.put("proList", productList);
//		result.put("totalPrice", totalPrice);
//
//		if (rightList != null) {
//			result.put("cgmod", rightList.contains("cgmod"));
//			result.put("cgdel", rightList.contains("cgdel"));
//		}
//
//		List<Unit> unitList = unitDao.getUnitList();
//
//		result.put("unitList", unitList);
//		result.put("rightList", rightList);
//
//		
//
//		return new ModelAndView("purchasing/purchasingView", result);
//
//	}

	/**
	 * 采购订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView updateRkNum(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		String id = request.getParameter("id");
//		String proId = request.getParameter("proId");
//
//		String rknum = request.getParameter("rknum");
//
//		purchasingDao.updateRkNum(id, rknum,proId);
//
//		return this.exportSuccessJSON(response);
//
//	}

	/**
	 * 采购订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView updateStatus(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		String id = request.getParameter("id");
//
//		String status = StatusMapping.getLabel(request.getParameter("status"));
//
//		purchasingDao.updateStatus(Integer.valueOf(id), status);
//
//		return this.exportSuccessJSON(response);
//
//	}

	/**
	 * 采购产品信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView productView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		PurchasingProduct product = purchasingDao.getProductById(id);
		return this.getResult("purchasing/proview", product);
	}

	/**
	 * 跳转到增加联系人的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAddContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String number = request.getParameter("co_number");

		Supplier supplier = purchasingDao.getSupplierByNumber(number);

		return this.getResult("supplier/addContact", supplier);
	}

	/**
	 * 跳转到增加联系人的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		String name1 = request.getParameter("name");
		String job1 = request.getParameter("job");
		String mr1 = request.getParameter("mr");
		String email1 = request.getParameter("email");
		String tel1 = request.getParameter("tel");
		String department1 = request.getParameter("department");
		String borndt1 = request.getParameter("borndt");
		String school1 = request.getParameter("school");
		String degree1 = request.getParameter("degree");
		String born1 = request.getParameter("born");
		String co_number = request.getParameter("co_number");
		String coname1 = request.getParameter("coname");
		String coaddr1 = request.getParameter("coaddr");
		String cotel1 = request.getParameter("cotel");
		String cofax1 = request.getParameter("cofax");
		String prorole1 = request.getParameter("prorole");
		String evaluate1 = request.getParameter("evaluate");
		String artifice1 = request.getParameter("artifice");
		String myaddr = request.getParameter("myaddr");
		String waptel1 = request.getParameter("waptel");
		String interest1 = request.getParameter("interest");
		String username1 = request.getParameter("username");
		String rg_date = request.getParameter("rg_date");
		String dept = request.getParameter("dept");
		String deptjb = request.getParameter("deptjb");
		String modman = request.getParameter("modman");
		String mod_date = request.getParameter("mod_date");
		String share1 = request.getParameter("share");
		String beizhu1 = request.getParameter("beizhu");

		String qq = request.getParameter("qq");
		String msn = request.getParameter("msn");
		String skype = request.getParameter("skype");

		String strSQL = "insert into qlinkman(name,job,mr,email,tel,department,bornde,school,degree,born,co_number,coname,coaddr,cotel,cofax,"
				+ "prorole,evaluate,artifice,waptel,myaddr,interest,username,rg_date,dept,deptjb,share,modman,"
				+ "mod_date,beizhu,qq,msn,skype) values('"
				+ name1
				+ "','"
				+ job1
				+ "','"
				+ mr1
				+ "','"
				+ email1
				+ "','"
				+ tel1
				+ "','"
				+ department1
				+ "','"
				+ borndt1
				+ "','"
				+ school1
				+ "','"
				+ degree1
				+ "','"
				+ born1
				+ "','"
				+ co_number
				+ "','"
				+ coname1
				+ "','"
				+ coaddr1
				+ "','"
				+ cotel1
				+ "','"
				+ cofax1
				+ "','"
				+ prorole1
				+ "','"
				+ evaluate1
				+ "','"
				+ artifice1
				+ "','"
				+ waptel1
				+ "','"
				+ myaddr
				+ "','"
				+ interest1
				+ "','"
				+ username1
				+ "','"
				+ rg_date
				+ "','"
				+ dept
				+ "','"
				+ deptjb
				+ "','"
				+ share1
				+ "','"
				+ modman
				+ "','"
				+ mod_date
				+ "','"
				+ beizhu1
				+ "','"
				+ qq
				+ "','"
				+ msn
				+ "','" + skype + "')";
		boolean t = db.executeUpdate(strSQL);
		db.close();
		if (!t) {

			return this.getErrorResult("添加失败,你所输入的内容超出系统范围或输入类型不符!");

		}

		return this.getSuccessResult();
	}

	/**
	 * 获取联系人的列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getContactList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String number = request.getParameter("number");
		return this.exportJSONObject(response,
				purchasingDao.getContactList(number));
	}

	public ModelAndView getPurchasingManList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, userDao.getPurchasingUserList());
	}

}
