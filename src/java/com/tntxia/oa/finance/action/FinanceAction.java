package com.tntxia.oa.finance.action;

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

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.finance.dao.FinanceDao;
import com.tntxia.oa.finance.entity.CreditDebit;
import com.tntxia.oa.sale.dao.SaleDao;
import com.tntxia.oa.sale.entity.RefundPro;
import com.tntxia.oa.system.dao.CurrentTypeDao;
import com.tntxia.oa.system.entity.CurrentType;
import com.tntxia.oa.util.CommonAction;

public class FinanceAction extends CommonAction {

	public static Logger logger = Logger.getLogger(FinanceAction.class);

	private FinanceDao financeDao;

	private SaleDao saleDao;

	private CurrentTypeDao currentTypeDao;

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}

	public void setCurrentTypeDao(CurrentTypeDao currentTypeDao) {
		this.currentTypeDao = currentTypeDao;
	}


	/**
	 * 跳转到退货冲帐的界面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toRefundFlush(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		java.sql.ResultSet sqlRst;
		
		java.lang.String strSQL;
		int intPageSize;
		int intRowCount;
		int intPageCount;
		int intPage;
		String strPage;
		int i, j;
		intPageSize = 500;
		strPage = request.getParameter("page");

		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}
		double g_je = 0;
		String tmpsalejg1 = request.getParameter("g_je");// 待对帐
		if (tmpsalejg1 != null)
			g_je = Double.parseDouble(tmpsalejg1);

		// 重新查一遍凭证信息，防止中文的乱码
		int gid = ServletRequestUtils.getIntParameter(request, "id", 0);
		CreditDebit creditDebit = financeDao.getCreditDebitById(gid);

		String coname = creditDebit.getConame();
		resultMap.put("coname", coname);
		String hb = request.getParameter("hb").trim();
		strSQL = "select count(*) from gathering_refund where states='退货' and coname='"
				+ coname + "' ";
		sqlRst = einfodb.executeQuery(strSQL);
		sqlRst.next();
		intRowCount = sqlRst.getInt(1);
		sqlRst.close();
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		if (intPage > intPageCount)
			intPage = intPageCount;
		strSQL = "select id,fyid,ymoney,smoney,invoice,coname,sjdate,skdate,sale_man,states from gathering_refund  where coname='"
				+ coname + "'   order by sjdate asc";

		sqlRst = einfodb.executeQuery(strSQL);
		i = (intPage - 1) * intPageSize;
		for (j = 0; j < i; j++)
			sqlRst.next();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		i = 0;
		while (i < intPageSize && sqlRst.next()) {

			Map<String, Object> item = new HashMap<String, Object>();

			String thnumber = sqlRst.getString("invoice");
			item.put("thnumber", thnumber);

			int id = sqlRst.getInt("id");
			item.put("id", id);
			String fyid = sqlRst.getString("fyid");
			double s = 0;
			String tmps = sqlRst.getString("smoney");
			if (tmps != null)
				s = Double.parseDouble(tmps);// 实收金额
			double totle = 0;
			String sale_man = sqlRst.getString("sale_man");
			resultMap.put("sale_man", sale_man);
			
			String strSQLpro = "select num,salejg,pricehb from th_pro where  ddid='"
					+ fyid + "'";
			ResultSet prs = einfodb.executeQuery(strSQLpro);
			
			while (prs.next()) {
				double num = 0;
				String tmpnum = prs.getString("num");
				if (tmpnum != null)
					num = Double.parseDouble(tmpnum);
				double price = 0;
				String tmpprice = prs.getString("salejg");
				if (tmpprice != null)
					price = Double.parseDouble(tmpprice);
				String shb = prs.getString("pricehb");

				CurrentType currentType = currentTypeDao.getByName(hb);

				if (currentType == null) {

					resultMap.put("success", false);
					resultMap.put("message", "货币类型不存在" + hb);
					return new ModelAndView("common/result", resultMap);
				}
				double chl = currentType.getRate();

				currentType = currentTypeDao.getByName(shb);

				if (currentType == null) {
					resultMap.put("success", false);
					resultMap.put("message", "货币类型不存在" + shb);
					return new ModelAndView("common/result", resultMap);
				}
				double shl = currentType.getRate();

				double tprice = num * price * shl / chl * (-1);
				totle = totle + tprice;
				
				
			}
			double sub1 = totle - s;// 剩余金额
			String se = "";
			double ss_je = 0.00;
			if (g_je >= sub1) {
				ss_je = sub1 + 0;
				g_je = g_je - sub1;
				se = "checked";
			} else {
				ss_je = g_je;
				g_je = 0;
				se = "";
			}
			item.put("s", s);
			item.put("sub1", sub1);
			item.put("ss_je", ss_je);
			item.put("se", se);
			result.add(item);

		}

		resultMap.put("g_je", g_je);
		resultMap.put("result", result);

		resultMap.put("intPageSize", intPageSize);
		resultMap.put("intRowCount", intRowCount);
		resultMap.put("intPageCount", intPageCount);
		resultMap.put("intPage", intPage);

		return new ModelAndView("finance/bank/thxxm", resultMap);

	}

	/**
	 * 跳转到退货冲帐的界面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toGatheringRefundView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		String totle = request.getParameter("totle");
		String hb = request.getParameter("hb");
		String id1 = request.getParameter("id");
		
		String restrain_name = (String) session.getAttribute("restrain_name");
		
		String modsql = "select * from restrain where restrain_name='"
				+ restrain_name + "'";
		ResultSet rsmod = einfodb.executeQuery(modsql);

		String t2 = null;

		double t7 = 0;

		if (rsmod.next()) {
			
			String sql = "select gathering.*,th_table.sub,th_table.remarks,th_table.payway from gathering_refund gathering left outer join th_table on gathering.orderform = th_table.number where gathering.id='"
					+ id1 + "'";
			ResultSet rs = einfodb.executeQuery(sql);
			if (!rs.next()) {

				exportErrorJSON(response, "not have record");

			}
			t2 = rs.getString(2);
			
			String contact = rs.getString("orderform");
			
			t7 = rs.getDouble(7);
			String coname = rs.getString(8);
			double bankFee = rs.getDouble(9); // 银行费用
			String t10 = rs.getString(10);
			String t11 = rs.getString(11);
			String t12 = rs.getString(12);
			java.sql.Date t13 = rs.getDate(13);
			String t14 = rs.getString(14);
			// String t15=rs.getString(15).trim();
			String i_man = rs.getString("i_man");
			String sale_man = rs.getString("sale_man");
			String sale_dept = rs.getString("sale_dept");
			String co_number = rs.getString("co_number");

			String sub = rs.getString("sub");
			String remark = rs.getString("remarks");
			String payway = rs.getString("payway");

			resultMap.put("co_number", co_number);
			resultMap.put("coname", coname);
			resultMap.put("totle", totle);
			resultMap.put("hb", hb);
			resultMap.put("contact", contact);
			resultMap.put("sub", sub);

			resultMap.put("t7", t7);
			resultMap.put("payway", payway);
			resultMap.put("t11", t11);

			resultMap.put("sale_man", sale_man);
			resultMap.put("sale_dept", sale_dept);
			resultMap.put("remark", remark);

		}

		List<RefundPro> refundProList = saleDao.getRefundProList(t2);

		if (t2 != null) {
			resultMap.put("refundProList", refundProList);
		}

		BigDecimal saletl = BigDecimal.ZERO;

		for (RefundPro pro : refundProList) {
			saletl = saletl.add(pro.getSalehj());
		}

		resultMap.put("saletl", saletl);
		double notPayAmount = saletl.subtract(BigDecimal.valueOf(t7))
				.doubleValue();
		resultMap.put("notPayAmount", nf.format(notPayAmount));
		resultMap.put("t7f", nf.format(t7));
		rsmod.close();
		einfodb.closeStmt();
		einfodb.closeConn();

		return super.getResult("finance/refund/view", resultMap);

	}

	/**
	 * 获取要调整的财务项
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView getAdaptItems(HttpServletRequest request,
			HttpServletResponse response) {

		String number = request.getParameter("number");
		String type = request.getParameter("type");
		if (type.equals("1")) {

			Map<String, Object> resultMap = new HashMap<String, Object>();
			List list = financeDao.getRefundGatherList(number);
			resultMap.put("list", list);
			super.exportJSON(response, resultMap);
		}
		return null;
	}

	public ModelAndView delRefundGather(HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		financeDao.deleteRefundGather(id);

		super.exportSuccessJSON(response);

		return null;
	}

}
