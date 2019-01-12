package com.tntxia.oa.warehouse.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.warehouse.InWarehouse;
import com.tntxia.oa.warehouse.entity.WarehouseInProduct;
import com.tntxia.db.DBConnection;
import com.tntxia.oa.right.Restrain;
import com.tntxia.oa.right.RightManager;
import com.tntxia.oa.system.dao.RightDao;
import com.tntxia.oa.system.entity.RestrainGP;
import com.tntxia.oa.system.entity.UserInfo;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.oa.util.WebUtils;
import com.tntxia.web.ParamUtils;

public class ViewRkAction implements Controller {

	private RightDao rightDao;

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}

	private ModelAndView getRkList(HttpServletRequest request) throws Exception {

		String startdate = ParamUtils.getString(request, "startdate");
		String enddate = ParamUtils.getString(request,"enddate");
		String int_types =ParamUtils.getString(request,"int_types");
		String pro_model = ParamUtils.getString(request,"pro_model");
		String g_man = ParamUtils.getString(request,"g_man");
		String coname = ParamUtils.getString(request,"coname");
		String ddnum = ParamUtils.getString(request,"ddnum");
		String orderNumber = ParamUtils.getString(request,"orderNumber");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ddnum", ddnum);
		model.put("coname", coname);
		model.put("pro_model", pro_model);
		model.put("startdate", startdate);
		model.put("enddate", enddate);
		model.put("int_types", int_types);
		model.put("g_man", g_man);

		String sqlWhere = " where states<>'已删除'";

		if (!StringUtils.isEmpty(orderNumber)) {

			sqlWhere += " and id in (select pro_rk_num from rkhouse where pro_types like '%"
					+ orderNumber + "%')";

		}

		if (!StringUtils.isEmpty(coname)) {
			sqlWhere += " and supplier like '%" + coname + "%'";
		}

		if (!StringUtils.isEmpty(ddnum)) {
			sqlWhere += " and number like '%" + ddnum + "%'";
		}

		if (!StringUtils.isEmpty(pro_model)) {

			sqlWhere += " and id in (select pro_rk_num from rkhouse where pro_model like '%"
					+ pro_model + "%')";

		}

		if (!StringUtils.isEmpty(g_man)) {
			sqlWhere += " and g_man like '%" + g_man + "%'";
		}

		if (!StringUtils.isEmpty(startdate)) {
			sqlWhere += " and int_date >= '" + startdate + "'";
		}

		if (!StringUtils.isEmpty(enddate)) {
			sqlWhere += " and int_date <= '" + enddate + "'";
		}

		HttpSession session = request.getSession();
		DBConnection einfodb = new DBConnection();

		ResultSet sqlRst;
		java.lang.String strSQL;
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
			intPage = Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}

		String username = (String) session.getAttribute("username");
		String restrain_name = (String) session.getAttribute("restrain_name");
		String deptjb = (String) session.getAttribute("deptjb");

		RightManager rightManager = new RightManager();
		Restrain restrain = rightManager.getRestrain(restrain_name);

		if (restrain.getIntview().equals("有")) {
			sqlWhere += " and deptjb like '" + deptjb + "%'";

		} else {
			sqlWhere += " and man='" + username + "'";
		}

		strSQL = "select count(*) from in_warehouse " + sqlWhere;

		sqlRst = einfodb.executeQuery(strSQL);
		sqlRst.next();
		intRowCount = sqlRst.getInt(1);

		sqlRst.close();
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		if (intPage > intPageCount)
			intPage = intPageCount;

		model.put("intPage", intPage);
		model.put("intRowCount", intRowCount);
		model.put("intPageCount", intPageCount);
		
		int top = intPage * intPageSize;

		strSQL = "select  top "+top+" id,states,number,supplier,int_types,man,int_date from in_warehouse "
				+ sqlWhere + " order  by number desc";

		sqlRst = einfodb.executeQuery(strSQL);
		i = (intPage - 1) * intPageSize;
		for (j = 0; j < i; j++)
			sqlRst.next();

		List<InWarehouse> result = new ArrayList<InWarehouse>();
		i = 0;
		while (i < intPageSize && sqlRst.next()) {
			InWarehouse inWarehouse = new InWarehouse();
			inWarehouse.setId(sqlRst.getInt("id"));
			inWarehouse.setStates(sqlRst.getString("states"));
			inWarehouse.setNumber(sqlRst.getString("number"));
			inWarehouse.setSupplier(sqlRst.getString("supplier"));
			inWarehouse.setInt_types(sqlRst.getString("int_types"));
			inWarehouse.setMan(sqlRst.getString("man"));
			inWarehouse.setInt_date(sqlRst.getTimestamp("int_date"));
			result.add(inWarehouse);
		}

		sqlRst.close();
		einfodb.close();

		model.put("result", result);

		return new ModelAndView("warehouse/in/viewRk", model);
	}

	private ModelAndView getRkListQuery(HttpServletRequest request,String restrainName)
			throws Exception {

		String startdate = StringUtils.safeTrim(request
				.getParameter("startdate"));
		String enddate = StringUtils.safeTrim(request.getParameter("enddate"));
		String int_types = StringUtils.safeTrim(request
				.getParameter("int_types"));
		String pro_model = StringUtils.safeTrim(request
				.getParameter("pro_model"));
		String g_man = StringUtils.safeTrim(request.getParameter("g_man"));
		String coname = StringUtils.safeTrim(request.getParameter("coname"));
		String ddnum = StringUtils.safeTrim(request.getParameter("ddnum"));
		String orderNumber = request.getParameter("orderNumber");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ddnum", ddnum);
		model.put("coname", coname);
		model.put("pro_model", pro_model);
		model.put("startdate", startdate);
		model.put("enddate", enddate);
		model.put("int_types", int_types);
		model.put("g_man", g_man);

		String sqlWhere = " where states<>'已删除'";

		if (!StringUtils.isEmpty(orderNumber)) {

			sqlWhere += " and  pro_types like '%" + orderNumber + "%'";

		}

		if (!StringUtils.isEmpty(coname)) {
			sqlWhere += " and supplier like '%" + coname + "%'";
		}

		if (!StringUtils.isEmpty(ddnum)) {
			sqlWhere += " and number like '%" + ddnum + "%'";
		}

		if (!StringUtils.isEmpty(pro_model)) {

			sqlWhere += " and pro_model like '%" + pro_model + "%'";

		}

		if (!StringUtils.isEmpty(g_man)) {
			sqlWhere += " and g_man like '%" + g_man + "%'";
		}

		if (!StringUtils.isEmpty(startdate)) {
			sqlWhere += " and int_date >= '" + startdate + "'";
		}

		if (!StringUtils.isEmpty(enddate)) {
			sqlWhere += " and int_date <= '" + enddate + "'";
		}

		HttpSession session = request.getSession();
		DBConnection einfodb = new DBConnection();

		ResultSet sqlRst;
		java.lang.String strSQL;
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
			intPage = Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}

		String username = (String) session.getAttribute("username");
		String restrain_name = (String) session.getAttribute("restrain_name");
		String deptjb = (String) session.getAttribute("deptjb");

		RightManager rightManager = new RightManager();
		Restrain restrain = rightManager.getRestrain(restrain_name);

		if (restrain.getIntview().equals("有")) {
			sqlWhere += " and deptjb like '" + deptjb + "%'";

		} else {
			sqlWhere += " and man='" + username + "'";
		}

		strSQL = "select count(*) from rkview " + sqlWhere;

		sqlRst = einfodb.executeQuery(strSQL);
		sqlRst.next();
		intRowCount = sqlRst.getInt(1);

		sqlRst.close();
		intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
		if (intPage > intPageCount)
			intPage = intPageCount;

		model.put("intPage", intPage);
		model.put("intRowCount", intRowCount);
		model.put("intPageCount", intPageCount);

		strSQL = "select id,states,number,supplier,pro_model,pro_name,pro_num,pro_hb,pro_price,pro_addr,man,int_date,pro_types from rkview"
				+ sqlWhere + " order  by number desc";
		
		System.out.println(strSQL);

		sqlRst = einfodb.executeQuery(strSQL);
		i = (intPage - 1) * intPageSize;
		for (j = 0; j < i; j++)
			sqlRst.next();

		List<WarehouseInProduct> result = new ArrayList<WarehouseInProduct>();
		i = 0;
		while (i < intPageSize && sqlRst.next()) {
			WarehouseInProduct inWarehouse = new WarehouseInProduct();
			inWarehouse.setId(sqlRst.getInt("id"));
			inWarehouse.setStates(sqlRst.getString("states"));
			inWarehouse.setModel(sqlRst.getString("pro_model"));
			inWarehouse.setProductYear(sqlRst.getString("pro_name"));
			
			inWarehouse.setHb(sqlRst.getString("pro_hb"));
			inWarehouse.setNum(sqlRst.getInt("pro_num"));
			inWarehouse.setPrice(sqlRst.getDouble("pro_price"));
			inWarehouse.setInNumber(sqlRst.getString("number"));
			inWarehouse.setSupplier(sqlRst.getString("supplier"));
			inWarehouse.setPurchaseNumber(sqlRst.getString("pro_types"));
			inWarehouse.setMan(sqlRst.getString("man"));
			inWarehouse.setInt_date((Date) sqlRst.getTimestamp("int_date"));
			
			// 入库仓库
			String addr = sqlRst.getString("pro_addr");
			inWarehouse.setAddr(addr);
			
			// 查看当前用户是否有仓库查看价格的权限，如果有的话，插入价格，
			// 如果没有则显示0
			RestrainGP restrainGP = rightDao.getRestrainGP(restrainName, addr);
			if(restrainGP.isPrice()){
				inWarehouse.setPrice(sqlRst.getDouble("pro_price"));
			}else{
				inWarehouse.setPrice(0);
			}
			
			result.add(inWarehouse);
		}

		sqlRst.close();
		einfodb.close();

		model.put("result", result);

		return new ModelAndView("warehouse/in/viewRkQuery", model);
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		if (WebUtils.isNoLogin(request)) {
			Map<String, Object> model = new HashMap<String, Object>();
			return new ModelAndView("common/refuse", model);
		}
		
		// 用户信息
		UserInfo userInfo = WebUtils.getUserInfo(request);
		String restrainName = userInfo.getRestrain_name();
		String query = request.getParameter("query");

		if ("true".equals(query)) {
			return getRkListQuery(request,restrainName);
		} else {
			return getRkList(request);
		}

	}
}
