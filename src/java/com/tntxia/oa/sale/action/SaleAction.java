package com.tntxia.oa.sale.action;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.finance.dao.FinanceDao;
import com.tntxia.oa.sale.dao.SaleDao;
import com.tntxia.oa.sale.entity.Dh;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.util.WebUtils;
import com.tntxia.web.ParamUtils;

public class SaleAction extends CommonAction {
	
	private SaleDao saleDao;

	private FinanceDao financeDao;


	public SaleDao getSaleDao() {
		return saleDao;
	}

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}
	
	/**
	 * 新增订单查询
	 * 
	 * @param request
	 * @param arg1
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView listSearch(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		HttpSession session = request.getSession();

		List<String> userRightList = (List<String>) session
				.getAttribute("userRightList");

		DBConnection db = new DBConnection();

		ResultSet sqlRst;
		String strSQL;
		int intPageSize;
		int intRowCount;
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
		String dept = (String) session.getAttribute("dept");
		String username1 = (String) session.getAttribute("username");
		String deptjb = (String) session.getAttribute("deptjb");

		// 是否拥有查看本部门订单的权限
		boolean hasSubviewRight = userRightList.contains("subview");

		if (hasSubviewRight) {
			strSQL = "select count(*) from subscribe where state='未提交'  and deptjb like '"
					+ deptjb
					+ "%' or state='未批准'  and deptjb like '"
					+ deptjb
					+ "%'   or  dept='"
					+ dept
					+ "' and state='未提交'  or  dept='"
					+ dept
					+ "' and state='未批准' ";
		} else
			strSQL = "select count(*) from subscribe where state='未提交' and man='"
					+ username1
					+ "' or state='未批准' and man='"
					+ username1
					+ "'";
		sqlRst = db.executeQuery(strSQL);
		sqlRst.next();
		intRowCount = sqlRst.getInt(1);
		sqlRst.close();
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		if (intPage > intPageCount)
			intPage = intPageCount;
		if (hasSubviewRight) {
			strSQL = "select id,money,number,coname,mode,p_states,man,state from subscribe  where state='未提交'  and deptjb like '"
					+ deptjb
					+ "%' or state='未批准'  and deptjb like '"
					+ deptjb
					+ "%'   or  dept='"
					+ dept
					+ "' and state='未提交'  or  dept='"
					+ dept
					+ "' and state='未批准'  order by number desc ";
		} else
			strSQL = "select id,money,number,coname,mode,p_states,man,state from subscribe where state='未提交' and man='"
					+ username1
					+ "' or state='未批准' and man='"
					+ username1
					+ "' order by number desc ";
		sqlRst = db.executeQuery(strSQL);
		i = (intPage - 1) * intPageSize;
		for (j = 0; j < i; j++)
			sqlRst.next();

		i = 0;

		Map res = new HashMap();

		List rows = new ArrayList();

		while (i < intPageSize && sqlRst.next()) {
			Map map = new HashMap();
			int id = sqlRst.getInt(1);
			String hb = sqlRst.getString("money");
			String number = sqlRst.getString("number");
			String coname = sqlRst.getString("coname");
			map.put("id", id);
			map.put("hb", hb);
			map.put("number", number);
			map.put("coname", coname);
			map.put("mode", sqlRst.getString("mode"));
			map.put("pstates", sqlRst.getString("p_states"));
			map.put("state", sqlRst.getString("state"));
			map.put("man", sqlRst.getString("man"));
			rows.add(map);

		}
		db.close();
		res.put("totalAmount", intRowCount);
		res.put("currentPage", intPage);
		res.put("pageSize", intPageSize);
		res.put("rows", rows);

		return this.exportJSONObject(response, res);

	}

	/**
	 * 跳转到已审退货的页面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toRefundReturn(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String ddnumber1 = request.getParameter("number").trim();

		Map<String, Object> result = new HashMap<String, Object>();

		// 检查财务信息
		if (financeDao.isRefundFlush(ddnumber1)) {
			result.put("success", false);
			result.put("message", "不能申请返回，财务已经收款冲帐，请联系财务修改");
			return new ModelAndView("common/result", result);
		}

		return new ModelAndView("sale/thgl/refundReturn", result);
	}

	/**
	 * 返回已审退货
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView refundReturn(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection db = new DBConnection();

		HttpSession session = request.getSession();

		Map<String, Object> result = new HashMap<String, Object>();

		String return_remark = request.getParameter("return_remark").trim();

		String ddnumber1 = request.getParameter("number").trim();

		String man = request.getParameter("man");

		// 检查财务信息
		if (financeDao.isRefundFlush(ddnumber1)) {
			result.put("success", false);
			result.put("message", "不能申请返回，财务已经收款冲帐，请联系财务修改");
			return new ModelAndView("common/result", result);
		}

		// 将退货订单的状态改成待审批
		saleDao.updateRefundStatus(ddnumber1, "待审批");
		// 增加返回的日志
		saleDao.addRefundLog(ddnumber1, "refund_return", return_remark,
				WebUtils.getUserInfo(request).getUsername());

		String strSQLp = "delete from gathering_refund where orderform='"
				+ ddnumber1 + "'";

		boolean t6 = db.executeUpdate(strSQLp);

		if (!t6) {
			result.put("success", false);
			result.put("message", "删除财务信息失败");
			return new ModelAndView("common/result", result);

		}

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		String currentDate = simple.format(new java.util.Date());
		String name1 = (String) session.getAttribute("username");
		String dept = (String) session.getAttribute("dept");
		String deptjb = (String) session.getAttribute("deptjb");

		String strSQLs = "insert into sendmail values('" + man
				+ "','','','合同退回:编号为:" + ddnumber1 + "合同','退回合同原因:"
				+ return_remark + "','" + name1 + "','" + deptjb + "','" + dept
				+ "','" + currentDate + "','已发送','','','')";

		boolean t1 = db.executeUpdate(strSQLs);
		db.close();

		if (!t1) {
			result.put("success", false);
			result.put("message", "邮件通知发送失败!");
			return new ModelAndView("common/result", result);
		}

		result.put("success", true);
		return new ModelAndView("common/result", result);

	}

	/**
	 * 查看销售前50名的产品
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView viewTop50SalePro(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection db = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String strSQL = "select top 50 epro,count(id) c from ddpro where epro is not null and epro!='' group by epro order by count(id) desc  ";
		ResultSet sqlRst = db.executeQuery(strSQL);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		while (sqlRst.next()) {
			String product = sqlRst.getString("epro");
			int count = sqlRst.getInt("c");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("epro", product);
			map.put("count", count);
			list.add(map);
		}
		db.close();

		resultMap.put("list", list);

		return getResult("sale/top50", resultMap);
	}

	/**
	 * 删除销售订单
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView delRefund(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection db = new DBConnection();

		String id = request.getParameter("id");

		String sql = "update th_table set state='已删除' where id='" + id + "'";

		Map refund = saleDao.getRefundById(id);

		String number = "";

		if (refund != null) {
			number = (String) refund.get("number");
		}

		String strDelGathering = "update gathering_refund set states = '已删除' where orderform='"
				+ number + "'";
		List<String> sqlList = new ArrayList<String>();
		sqlList.add(sql);
		sqlList.add(strDelGathering);
		db.execBatch(sqlList);
		db.close();

		Map<String, Object> model = new HashMap<String, Object>();

		return super.exportCommon(true, model);

	}

	/**
	 * 合同审批
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView getTemplateList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();
		

		
		java.sql.ResultSet sqlRst;
		java.lang.String strSQL;
		int intPageSize;
		int intRowCount;
		int intPageCount;
		int intPage;
		java.lang.String strPage;
		int i, j;
		intPageSize = 20;
		strPage = request.getParameter("page");
		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}

		strSQL = "select count(*) from ht_mb";
		sqlRst = db.executeQuery(strSQL);
		sqlRst.next();
		intRowCount = sqlRst.getInt(1);
		sqlRst.close();
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		if (intPage > intPageCount)
			intPage = intPageCount;

		strSQL = "select id,q_name name ,q_company coname,q_date createDate from ht_mb";

		sqlRst = db.executeQuery(strSQL);
		i = (intPage - 1) * intPageSize;
		for (j = 0; j < i; j++)
			sqlRst.next();

		List result = new ArrayList();
		while (sqlRst.next()) {
			Map map = new HashMap();
			map.put("id", sqlRst.getInt("id"));
			map.put("name", sqlRst.getString("name"));
			map.put("coname", sqlRst.getString("coname"));
			map.put("createDate", sqlRst.getString("createDate"));
			result.add(map);
		}
		db.close();
		return this.exportJSONObject(response, result);

	}

	/**
	 * 查看调货详细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView dhDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int id = ParamUtils.getInt(request, "id");
		Dh dh = saleDao.getDhById(id);
		String prev = "sale/cypgl/";
		String viewName = "";
		if ("已入库".equals(dh.getState())) {
			viewName = "fddds-view";
		} else if ("已出库".equals(dh.getState())) {
			viewName = "fdd-view";
		} else if ("待发运".equals(dh.getState())) {
			viewName = "fdd-view";
		} else if ("待出库".equals(dh.getState())) {
			viewName = "fdd-view";
		} else if ("待入库".equals(dh.getState())) {
			viewName = "fdd-view";
		} else if ("待审批".equals(dh.getState())) {
			viewName = "spdd-view";
		} else {
			viewName = "dd-view";
		}
		return this.getResult(prev + viewName);
	}

	public ModelAndView getAllBank(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, saleDao.getAllBank());
	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView createRefund(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DBConnection einfodb = new DBConnection();
		
		try{
			java.sql.ResultSet sqlRst;
			java.text.SimpleDateFormat simple1=new java.text.SimpleDateFormat("yyMM");
			String number1=simple1.format(new java.util.Date());
			int in_no=1;
			 String sqlq="select * from th_table  where number like 'R"+number1+"%' order by in_no desc";
			ResultSet rs=einfodb.executeQuery(sqlq);
			if(rs.next())
			  {
			    in_no=rs.getInt("in_no")+1;
			  }
			 String sno="";
			if(in_no<10){
				sno="000";
			}
			else if((10<=in_no)&(in_no<100)){
				sno="00";
			}
			else if((100<=in_no)&(in_no<1000)){
				sno="0";
			}
			else
				sno="";
			 String coname1=ParamUtils.unescape(request, "coname");
			 String sub1=request.getParameter("sub");
			 String man1=super.getUsername(request);
			
			 String senddate1=ParamUtils.unescape(request,"senddate");
			 
			 String datetime1=request.getParameter("datetime");
			 String remarks1=ParamUtils.unescape(request,"remarks");
			 String deptjb=request.getParameter("deptjb");
			 
			 String payway = request.getParameter("payway");
			 
			 String sqls="select * from th_table where number='R"+number1+""+sno+""+in_no+"'";
			 if(einfodb.exist(sqls)){
				 throw new Exception("退货号重复!");
			 }
			  
			
				
				HttpSession session = request.getSession();
				
			   	int ddid=1;
			   	String dept = (String) session.getAttribute("dept");
			   	String role = (String) session.getAttribute("role");
			   	
			  	String sqlddman="select  * from thsp where dept='"+dept+"' and role='"+role+"'";
			    ResultSet rsddman=einfodb.executeQuery(sqlddman);
			  	if(rsddman.next()){
			   		String dd_man=rsddman.getString("dd_man");
			   		String fif=rsddman.getString("fif");
			   		String fspman=rsddman.getString("fspman");
			   		String firspif=rsddman.getString("firspif");
			   		String firspman=rsddman.getString("firspman");

			 		String strSQL="insert into th_table(number,man,sub,coname,senddate,money,habitus,datetime,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,firspif,firspman,fspyj,dept,deptjb,in_no,w_remark,payway) values('R"+number1+""+sno+""+in_no+"','" + man1 +"','" + sub1 + "','" + coname1 + "','" + senddate1 + "','','订单执行中','" + datetime1 + "','" + remarks1 + "','未提交','"+dd_man+"','  ',' ','"+fif+"','"+fspman+"','','','" + firspif + "','" + firspman + "','','" + dept + "','" + deptjb + "','" + in_no + "','','"+payway+"')";
			   		boolean t= einfodb.executeUpdate(strSQL);
			   		if(!t){
			   			throw new Exception("添加失败,你所输入的内容超出系统范围或输入类型不符!");
			   		
			   		}
			 		String sql="select max(id)  from th_table";
			 		sqlRst = einfodb.executeQuery(sql);
			 		sqlRst.next();
			 		ddid=sqlRst.getInt(1);
			 		return this.exportSuccessJSON(response,"id",ddid);
			 	}else {
			 		throw new Exception("未定义退货审批流程!");
			 	}
			  	
			  	
		}catch(Exception ex){
			logger.error("增加销售退货单失败", ex);
			return this.exportErrorJSON(response, ex.toString());
		}finally{
			einfodb.close();
		}
		
	}
	
	
	

}
