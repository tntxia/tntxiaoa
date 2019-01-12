package com.tntxia.oa.purchasing.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.purchasing.dao.SupplierDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;

public class SupplierAction extends CommonAction {

	private SupplierDao supplierDao;

	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/**
	 * 获取供应商列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String coname = ParamUtils.unescape(request, "coname");
		Map<String,Object> param = new HashMap<String,Object>();
		PageBean pageBean = this.getPageBean(request, 20);
		param.put("pageBean", pageBean);
		param.put("coname", coname);
		param.put("country", ParamUtils.unescape(request, "country"));
		param.put("province", ParamUtils.unescape(request, "province"));
		param.put("tradetypes", ParamUtils.unescape(request, "tradetypes"));
		param.put("cojsfs", ParamUtils.unescape(request, "cojsfs"));
		param.put("scale", ParamUtils.unescape(request, "scale"));
		
		List list = supplierDao.list(param);
		List rows = this.getRows(list, pageBean);
		int totalAmount = supplierDao.getTotal(param);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", totalAmount);
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());

		return exportJSONObject(response, res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView listContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		java.sql.ResultSet sqlRst;
		java.lang.String strSQL;
		int intPageSize;
		int intRowCount;
		int intPageCount;
		int intPage;
		java.lang.String strPage;
		int i, j, k;
		intPageSize = 50;
		strPage = request.getParameter("page");
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		String currentDate = simple.format(new java.util.Date());
		HttpSession session = request.getSession();
		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}
		String dept = (String) session.getValue("dept");
		String username = (String) session.getValue("username");
		String restrain_name = (String) session.getValue("restrain_name");
		String deptjb = (String) session.getValue("deptjb");
		Map res = new HashMap();
		
		String modsql = "select * from restrain where restrain_name='"
				+ restrain_name + "'";
		DBConnection einfodb = new DBConnection();

		ResultSet rsmod = einfodb.executeQuery(modsql);
		if (rsmod.next()) {
			String supadd = rsmod.getString("supadd").trim();
			String supview = rsmod.getString("supview").trim();
			String modsqlt = "select * from restrain_name where r_name='"
					+ username + "'";
			ResultSet rsmodt = einfodb.executeQuery(modsqlt);
			int tmpigp = 0;
			String res_dept = "";
			while (rsmodt.next()) {
				String deptjb_t = rsmodt.getString("deptjb").trim();
				String manview_t = rsmodt.getString("supview").trim();
				if (manview_t.equals("有")) {
					String res_dept1 = "  or  deptjb='" + deptjb_t + "'";
					res_dept += res_dept1;
				}
				tmpigp++;
			}
			if (supview.equals("有")) {
				strSQL = "select count(*) from qlinkman   where   deptjb  like '"
						+ deptjb + "%'   " + res_dept + "";
			} else
				strSQL = "select count(*) from qlinkman where share='是' or username='"
						+ username + "'   " + res_dept + "";
			sqlRst = einfodb.executeQuery(strSQL);
			sqlRst.next();
			intRowCount = sqlRst.getInt(1);
			sqlRst.close();
			intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
			if (intPage > intPageCount)
				intPage = intPageCount;
			if (supview.equals("有")) {
				strSQL = "select  nameid,name,cotel,cofax,waptel,email,coname from qlinkman   where   deptjb  like '"
						+ deptjb + "%'   " + res_dept + " order by coname asc";
			} else
				strSQL = "select nameid,name,cotel,cofax,waptel,email,coname from qlinkman   where share='是' or username='"
						+ username
						+ "'    "
						+ res_dept
						+ "  order by coname asc";
			sqlRst = einfodb.executeQuery(strSQL);
			i = (intPage - 1) * intPageSize;
			for (j = 0; j < i; j++)
				sqlRst.next();
			
			i = 0;
			
			List list = new ArrayList();
		    while(i<intPageSize && sqlRst.next()){
		    	
		    	Map map = new HashMap();
		    	map.put("id", sqlRst.getInt("nameid"));
		    	map.put("name", sqlRst.getString("name"));
		    	map.put("cotel", sqlRst.getString("cotel"));
		    	map.put("cofax", sqlRst.getString("cofax"));
		    	map.put("waptel", sqlRst.getString("waptel"));
		    	map.put("email", sqlRst.getString("email"));
		    	map.put("coname", sqlRst.getString("coname"));
		    	list.add(map);
		    	i++;
		    }
		    res.put("rows", list);
		    res.put("totalAmount", intRowCount);
		}
		res.put("page", intPage);
		res.put("pageSize", intPageSize);
		
		return this.exportJSONObject(response, res);
		
	}

	public ModelAndView getPf(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String number = request.getParameter("number");
		return exportJSONObject(response, supplierDao.getPf(number));
	}
	
	public ModelAndView shift(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String man1=request.getParameter("man1").trim();
		 String man2=request.getParameter("man2").trim();
		 String share=request.getParameter("share").trim();
		 String coname1=request.getParameter("coname").trim();
		  String sqlusername2="select * from username where name='" + man2 + "'";
		  DBConnection einfodb = new DBConnection();
		  DBConnection einfodb2 = new DBConnection();
		 ResultSet rsusername2 = einfodb.executeQuery(sqlusername2);
		 if(!rsusername2.next()) {
			 rsusername2.close();
			 return this.exportErrorJSON(response, "系统暂无该用户!");
		 }
		 String dept2=rsusername2.getString("yjxs").trim();
		 String deptjb2=rsusername2.getString("hometel").trim();
		 rsusername2.close();

		 String strSQLc = "select * from supplier where username='" + man1 + "' and coname like '%" + coname1 + "%'";
			ResultSet prs=einfodb.executeQuery(strSQLc);
		    int tmpi=0;
		 while(prs.next()){ 
		  int cid=prs.getInt(1);
		  String coname=prs.getString("coname").trim();
		  String strSQLman="update qlinkman set username='"+man2+"',dept='"+dept2+"',deptjb='"+deptjb2+"'  where  coname='"+coname+"' and username='"+man1+"' ";
		       boolean tman= einfodb2.executeUpdate(strSQLman);
		   if(!tman)
		   {
			   return this.exportErrorJSON(response, "<font size='2' color='#FF0000'>转移联系人失败!</font>");
		    
		   }
		   String strSQLop="update procure_xj set man='"+man2+"'  where  coname='"+coname+"' and man='"+man1+"' ";
		       boolean top= einfodb2.executeUpdate(strSQLop);
		   if(!top)
		   {
			   return this.exportErrorJSON(response, "<font size='2' color='#FF0000'>转移询价失败!</font>");
		   }
		   String strSQLact="update procure set man='"+man2+"',l_dept='"+dept2+"',l_deptjb='"+deptjb2+"'  where  coname='"+coname+"' and man='"+man1+"' ";
		       boolean tact= einfodb2.executeUpdate(strSQLact);
		   if(!tact)
		   {
			   return this.exportErrorJSON(response, "<font size='2' color='#FF0000'>转移采购转移失败!</font>");
		     
		   }
		   String strSQLq="update payment set remark='"+man2+"',wtfk='"+deptjb2+"'  where  supplier='"+coname+"' and remark='"+man1+"' ";
		       boolean tq= einfodb2.executeUpdate(strSQLq);
		   if(!tq)
		   {
			   return this.exportErrorJSON(response, "<font size='2' color='#FF0000'>转移付款信息失败!</font>");
		     
		   }

		   String strSQLsub="update gathering_customer set pname='"+man2+"',dept='"+dept2+"',deptjb='"+deptjb2+"'  where  coname='"+coname+"' and pname='"+man1+"' ";
		       boolean tsub= einfodb2.executeUpdate(strSQLsub);
		   if(!tsub)
		   {
			   return this.exportErrorJSON(response, "转移付款汇总失败!");
		     
		   }
		tmpi++;}
		  prs.close();
		 String strSQL="update supplier set username='" + man2  + "',share='" + share + "',dept='"+dept2+"',deptjb='"+deptjb2+"' where  username='" + man1 + "' and  coname like '%" + coname1 + "%'";
		     boolean t= einfodb2.executeUpdate(strSQL);
		   if(!t)
		   {
			   return this.exportErrorJSON(response, "转移客户资料失败,!");
		     
		   }
		 einfodb.closeStmt();
		 einfodb.closeConn();
		 einfodb2.closeStmt();
		 einfodb2.closeConn();
		 return this.exportSuccessJSON(response);
	}
	
	

}
