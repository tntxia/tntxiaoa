package com.tntxia.oa.sale.action;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class InquiryAction extends BaseAction {

	private int getInNo(SQLExecutor db, String pre, String number1)
			throws Exception {

		int in_no = 1;
		String sqlq = "select  top 1 in_no  from quote  where number like '"
				+ pre + number1 + "%' order by in_no desc";
		in_no = db.queryForInt(sqlq) + 1;
		return in_no;

	}

	public Map<String, Object> add(HttpServletRequest request,
			HttpServletResponse arg1) {

		SQLExecutorSingleConn executor = null;

		String pre = "ONKSZ-QU-";

		try {
			executor = this.getSQLExecutorSingleConn();
			java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
					"yy");
			String number1 = simple1.format(new java.util.Date());
			int in_no = this.getInNo(executor, pre, number1);

			String sno = "";
			if (in_no < 10) {
				sno = "00000";
			} else if ((10 <= in_no) & (in_no < 100)) {
				sno = "0000";
			} else if ((100 <= in_no) & (in_no < 1000)) {
				sno = "000";
			} else if ((1000 <= in_no) & (in_no < 10000)) {
				sno = "00";
			} else
				sno = "";
			String quotedatetime1 = ParamUtils.unescape(request,
					"quotedatetime");
			String in_number = ParamUtils.unescape(request, "in_number");
			String co_number = ParamUtils.unescape(request, "co_number");
			String coname1 = ParamUtils.unescape(request, "coname");
			String cotel1 = ParamUtils.unescape(request, "cotel");
			String coaddr1 = ParamUtils.unescape(request, "coaddr");
			String cofax1 = ParamUtils.unescape(request, "cofax");
			String linkman1 = ParamUtils.unescape(request, "linkman");
			String linktel1 = ParamUtils.unescape(request, "linktel");
			String linkwap1 = ParamUtils.unescape(request, "linkwap");
			String linkemail1 = ParamUtils.unescape(request, "linkemail");
			String airport = ParamUtils.unescape(request, "airport");

			String tr_types = ParamUtils.unescape(request, "tr_types");
			String q_tr_date = ParamUtils.unescape(request, "q_tr_date");
			String payment = ParamUtils.unescape(request, "payment");
			String content1 = ParamUtils.unescape(request, "content");
			String man1 = ParamUtils.unescape(request, "man");
			String spman = ParamUtils.unescape(request, "spman");
			String money = ParamUtils.unescape(request, "money");
			String fveight = ParamUtils.unescape(request, "fveight");
			String insurance = ParamUtils.unescape(request, "insurance");
			String commission = ParamUtils.unescape(request, "commission");
			String discount = ParamUtils.unescape(request, "discount");
			String dept = ParamUtils.unescape(request, "dept");
			String deptjb = ParamUtils.unescape(request, "deptjb");
			String acct = ParamUtils.unescape(request, "acct");

			String strSQL = "insert into quote(number,quotedatetime,in_number,co_number,coname,cotel,coaddr,cofax,linkman,linktel,linkwap,linkemail,content,man,airport,tr_types,q_tr_date,payment,spman,states,spyj,hb,fveight,insurance,commission,discount,in_no,dept,deptjb,acct) values('"
					+ pre
					+ number1
					+ ""
					+ sno
					+ ""
					+ in_no
					+ "','"
					+ quotedatetime1
					+ "','"
					+ in_number
					+ "','"
					+ co_number
					+ "','"
					+ coname1
					+ "','"
					+ cotel1
					+ "','"
					+ coaddr1
					+ "','"
					+ cofax1
					+ "','"
					+ linkman1
					+ "','"
					+ linktel1
					+ "','"
					+ linkwap1
					+ "','"
					+ linkemail1
					+ "','"
					+ content1
					+ "','"
					+ man1
					+ "','"
					+ airport
					+ "','"
					+ tr_types
					+ "','"
					+ q_tr_date
					+ "','"
					+ payment
					+ "','"
					+ spman
					+ "','未提交','','"
					+ money
					+ "','"
					+ fveight
					+ "','"
					+ insurance
					+ "','"
					+ commission
					+ "','"
					+ discount
					+ "','"
					+ in_no
					+ "','"
					+ dept
					+ "','"
					+ deptjb
					+ "','"
					+ acct
					+ "')";

			executor.update(strSQL);

			String sql = "select max(id)  from quote";
			int max = executor.queryForInt(sql);
			return this.success("quoteId", max);
		} catch (Exception ex) {
			return this.errorMsg(ex.toString());
		} finally {
			if (executor != null)
				executor.close();
		}
	}

	/**
	 * 修改报价单的产品信息
	 * @param runtime
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> updateProduct(WebRuntime runtime) throws SQLException {

		SQLExecutorSingleConn sqlExecutor = this.getSQLExecutorSingleConn();

		String qhb = runtime.getParam("qhb");
		String quoteid = runtime.getParam("ddid");
		int t = 0;
		String i1 = runtime.getParam("i");
		if (i1 != null)
			t = Integer.parseInt(i1);
		for (int i = 1; i < t; i++) {
			String id = runtime.getParam("id" + i);
			if (id != null) {
				String pro_name = runtime.getParam("pro_name" + i);
				if (pro_name != null) {
					pro_name = com.infoally.util.Replace.strReplace(pro_name,
							"'", "’");
					pro_name = com.infoally.util.Replace.strReplace(pro_name,
							"\"", "”");
				}
				String pro_model = runtime.unescape("pro_model" + i);
				if (pro_model != null) {
					pro_model = com.infoally.util.Replace.strReplace(pro_model,
							"'", "’");
					pro_model = com.infoally.util.Replace.strReplace(pro_model,
							"\"", "”");
				}
				String xh = runtime.getParam("i" + i);
				String num = runtime.getParam("num" + i);
				String punit = runtime.unescape("punit" + i);
				String price = runtime.unescape("price" + i);
				String qprice = runtime.unescape("qprice" + i);
				String supplier = runtime.unescape("supplier" + i);
				String pro_tr = runtime.unescape("pro_tr" + i);
				String fz = runtime.unescape("fz" + i);
				String selljg = runtime.unescape("selljg" + i);
				String money1 = runtime.unescape("money" + i);
				String pro_remark = runtime.unescape("pro_remark" + i);
				String moq = runtime.unescape("moq" + i);
				String mpq = runtime.unescape("mpq" + i);
				if (pro_remark != null) {
					pro_remark = com.infoally.util.Replace.strReplace(
							pro_remark, "'", "’");
					pro_remark = com.infoally.util.Replace.strReplace(
							pro_remark, "\"", "”");
				}
				String rate = runtime.unescape("rate" + i);
				String pricehb = runtime.unescape("pricehb" + i);

				String strSQL = "update quoteproduct set  pro_gg='" + fz
						+ "',supplier='" + supplier + "',product='" + pro_model
						+ "',cpro='" + pro_name + "',quantity='" + num
						+ "',unit='" + punit + "',price='" + qprice
						+ "',rate='" + rate + "',pricehb='" + pricehb
						+ "',money='" + money1 + "',selljg='" + selljg
						+ "',cpro2='" + pro_tr + "',remark='" + pro_remark
						+ "',moq='" + moq + "',mpq='" + mpq + "'  where  id='"
						+ id + "'";
				sqlExecutor.update(strSQL);

			}
		}
		String pro_name = runtime.unescape("2pro_name");
		if (pro_name != null) {
			pro_name = com.infoally.util.Replace.strReplace(pro_name, "'", "’");
			pro_name = com.infoally.util.Replace
					.strReplace(pro_name, "\"", "”");
		}
		String pro_model = runtime.unescape("2pro_model").trim();
		if (pro_model != null) {
			pro_model = com.infoally.util.Replace.strReplace(pro_model, "'",
					"’");
			pro_model = com.infoally.util.Replace.strReplace(pro_model, "\"",
					"”");
		}
		String num = runtime.unescape("2num");
		String punit = runtime.unescape("2punit");
		String price = runtime.unescape("2price");
		String qprice = runtime.unescape("2qprice");
		String supplier = runtime.unescape("2supplier");
		String pro_tr = runtime.unescape("2pro_tr");
		String fz = runtime.unescape("2fz");
		String money = runtime.unescape("2money");
		String selljg = runtime.unescape("2selljg");
		String rate = runtime.unescape("2rate");
		String pricehb = runtime.unescape("2pricehb");
		String pro_remark = runtime.unescape("2pro_remark");
		String moq = runtime.unescape("2moq");
		String mpq = runtime.unescape("2mpq");
		if (pro_remark != null) {
			pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "'",
					"’");
			pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "\"",
					"”");
		}
		if (pro_model.equals("")) {
		} else {
			String strSQL = "insert into quoteproduct(quoteid,product,cpro,cpro2,pro_gg,quantity,unit,price,pricehb,selljg,money,supplier,wid,rate_types,rate,remark,moq,mpq) values('"
					+ quoteid
					+ "','"
					+ pro_model
					+ "','"
					+ pro_name
					+ "','"
					+ pro_tr
					+ "','"
					+ fz
					+ "','"
					+ num
					+ "','"
					+ punit
					+ "','"
					+ qprice
					+ "','"
					+ pricehb
					+ "','"
					+ selljg
					+ "','"
					+ money
					+ "','"
					+ supplier
					+ "','','含税','"
					+ rate
					+ "','"
					+ pro_remark + "','" + moq + "','" + mpq + "')";
			sqlExecutor.update(strSQL);

		}
		
		return this.success();
	}

}
