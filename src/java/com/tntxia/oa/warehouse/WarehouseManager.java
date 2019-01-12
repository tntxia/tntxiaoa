package com.tntxia.oa.warehouse;

import infocrmdb.infocrmdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.mail.SendMail;
import com.tntxia.oa.model.SalePro;
import com.tntxia.oa.sale.SaleManager;
import com.tntxia.oa.util.StringUtils;

public class WarehouseManager {

	private static HashMap<String, String> restrainMap = new HashMap<String, String>();

	private static List<String> productInList = new ArrayList<String>();

	/**
	 * 增加锁，防止重复提交
	 * 
	 * @param number
	 */
	private void addLock(String number) throws Exception{
		if (productInList.contains(number)) {
			throw new Exception("入库编码已经存在");
		}
		productInList.add(number);
	}

	/**
	 * 检查是否有锁
	 * 
	 * @param number
	 * @return
	 */
	private boolean checkLock(String number) {
		return productInList.contains(number);
	}

	/**
	 * 移除锁
	 * 
	 * @param number
	 */
	private void removeLock(String number) {
		productInList.remove(number);
	}

	// 对于入库信息的记录
	public void logWarehouseIn(String pro_model, String operateMan,
			String remark) {

		String sql = "insert into rk_info(pro_model,remark,operateMan,operateTime)"
				+ " values( '"
				+ pro_model
				+ "','"
				+ remark
				+ "','"
				+ operateMan + "',getdate())";
		infocrmdb db = new infocrmdb();
		db.executeUpdate(sql);
		db.close();
	}

	// 通过型号查询出产品在仓库中的数量
	public Warehouse getWarehouseSingle(String pro_model) throws SQLException {
		infocrmdb einfodb = new infocrmdb();
		String strSQL = "select top 1 id,pro_number,pro_model,pro_gg,pro_name,pro_sup_number,yqdate,pro_secid,pro_supplier,pro_num,pro_unit,pro_min_num,pro_price,price_hb,saleprice,pro_yyfw,pro_addr,pro_remark from warehouse where pro_model='"
				+ pro_model + "'";
		ResultSet sqlRst = einfodb.executeQuery(strSQL);
		Warehouse warehouse = new Warehouse();
		int dqnum = 0;
		String id = null;
		if (sqlRst.next()) {
			warehouse.setPro_model(sqlRst.getString("pro_model"));
			id = sqlRst.getString("id");
			dqnum = sqlRst.getInt("pro_num");
		}
		sqlRst.close();
		einfodb.close();
		warehouse.setId(id);
		warehouse.setQnum(dqnum);
		return warehouse;
	}

	// 通过型号查询出产品在仓库中的数量
	public Warehouse getWarehouseExistSingle(String pro_model)
			throws SQLException {
		infocrmdb einfodb = new infocrmdb();
		String strSQL = "select top 1 id,pro_number,pro_model,pro_gg,pro_name,pro_sup_number,yqdate,pro_secid,pro_supplier,pro_num,pro_unit,pro_min_num,pro_price,price_hb,saleprice,pro_yyfw,pro_addr,pro_remark from warehouse where pro_model='"
				+ pro_model + "' and pro_num > 0 ";
		ResultSet sqlRst = einfodb.executeQuery(strSQL);
		Warehouse warehouse = new Warehouse();
		int dqnum = 0;
		String id = null;
		if (sqlRst.next()) {
			warehouse.setPro_model(sqlRst.getString("pro_model"));
			warehouse.setPro_name(sqlRst.getString("pro_name"));
			id = sqlRst.getString("id");
			dqnum = sqlRst.getInt("pro_num");
		}
		sqlRst.close();
		einfodb.close();
		warehouse.setId(id);
		warehouse.setQnum(dqnum);
		return warehouse;
	}

	// 通过型号查询出产品在仓库中的数量
	public Warehouse getWarehouse(String pro_model, String filter)
			throws SQLException {
		infocrmdb einfodb = new infocrmdb();
		String strSQL = "select id,pro_number,pro_model,pro_gg,pro_name,pro_sup_number,yqdate,pro_secid,pro_supplier,pro_num,pro_unit,pro_min_num,pro_price,price_hb,saleprice,pro_yyfw,pro_addr,pro_remark from warehouse where pro_model='"
				+ pro_model + "' " + filter;
		ResultSet sqlRst = einfodb.executeQuery(strSQL);
		int dqnum = 0;
		String id = null;
		if (sqlRst.next()) {
			id = sqlRst.getString("id");
			dqnum = sqlRst.getInt("pro_num");
		}

		sqlRst.close();
		einfodb.close();
		Warehouse warehouse = new Warehouse();
		warehouse.setId(id);
		warehouse.setQnum(dqnum);
		return warehouse;
	}

	public ArrayList<HDCompanyAttachment> getHDCompanyAttachment(String id) {

		ArrayList<HDCompanyAttachment> result = new ArrayList<HDCompanyAttachment>();
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,filename,filepath,remark from hdcompany_attachment where hdcompanyid = '"
				+ id + "'";
		ResultSet rs = null;

		try {
			rs = einfodb.executeQuery(sql);
			while (rs.next()) {
				HDCompanyAttachment att = new HDCompanyAttachment();
				att.setId(rs.getString("id"));
				att.setFilename(rs.getString("filename"));
				att.setFilepath(rs.getString("filepath"));
				att.setRemark(rs.getString("remark"));
				result.add(att);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}

	public String getSaleNumberFromReturnNumber(String returnNumber) {
		String result = "";
		infocrmdb einfodb = new infocrmdb();
		String sql = "select sub from th_table where number = '" + returnNumber
				+ "'";
		ResultSet rs = null;

		try {
			rs = einfodb.executeQuery(sql);
			while (rs.next()) {

				result = rs.getString("sub");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}

	public Warehouse getWarehouseSingle(Warehouse warehouse) {
		String sql = "select * from warehouse where 1=1 ";
		if (StringUtils.isEmpty(warehouse.getId())) {
			sql += " and id =" + warehouse.getId();
		}
		if (warehouse.getPro_addr() != null
				&& warehouse.getPro_addr().trim().length() > 0) {
			sql += " and pro_addr ='" + warehouse.getPro_addr() + "'";
		}
		if (warehouse.getPro_model() != null
				&& warehouse.getPro_model().trim().length() > 0) {
			sql += " and pro_model ='" + warehouse.getPro_model() + "'";
		}
		if (warehouse.getPro_name() != null
				&& warehouse.getPro_name().trim().length() > 0) {
			sql += " and pro_name ='" + warehouse.getPro_name() + "'";
		}
		if (warehouse.getPro_remark() != null
				&& warehouse.getPro_remark().trim().length() > 0) {
			sql += " and pro_remark ='" + warehouse.getPro_remark() + "'";
		}

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return warehouse;
	}

	/**
	 * 通过仓库产品的ID来查询产品信息
	 * 
	 * @param id
	 * @return
	 */
	public Warehouse getWarehouseById(String id) {
		String sql = "select * from warehouse where id= " + id;
		DBConnection db = new DBConnection();
		ResultSet rs = db.executeQuery(sql);
		Warehouse warehouse = new Warehouse();
		try {
			if (rs.next()) {
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			db.close();
		}

		return warehouse;
	}

	public Warehouse getWarehouseSingle(Warehouse warehouse, boolean isReturn) {
		String sql = "select * from warehouse where 1=1 ";
		if (warehouse.getPro_addr() != null
				&& warehouse.getPro_addr().trim().length() > 0) {
			sql += " and pro_addr ='" + warehouse.getPro_addr() + "'";
		}
		if (warehouse.getPro_model() != null
				&& warehouse.getPro_model().trim().length() > 0) {
			sql += " and pro_model ='" + warehouse.getPro_model() + "'";
		}
		if (warehouse.getPro_name() != null
				&& warehouse.getPro_name().trim().length() > 0) {
			sql += " and pro_name ='" + warehouse.getPro_name() + "'";
		}
		if (warehouse.getPro_remark() != null
				&& warehouse.getPro_remark().trim().length() > 0) {
			sql += " and pro_remark ='" + warehouse.getPro_remark() + "'";
		}
		if (isReturn) {
			sql += " and th_number is not null ";
		} else {
			sql += " and th_number is null ";
		}

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return warehouse;
	}

	private boolean ignore(boolean ignoreBlank, String str) {
		if (str == null) {
			return true;
		} else {
			if (ignoreBlank) {
				if (str.trim().length() == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public List<Warehouse> getWarehouse(String productName) {

		List<Warehouse> result = new ArrayList<Warehouse>();

		String sql = "select top 8 id,pro_addr,pro_model,pro_sup_number,pro_gg,pro_num,pro_remark from warehouse where pro_model like '"
				+ productName
				+ "' and pro_remark not like '退货:%' and pro_num > 0";
		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);

		try {
			while (rs.next()) {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				result.add(warehouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}

	public Warehouse getWarehouseSingle(Warehouse warehouse, boolean isReturn,
			boolean ignoreBlank, boolean ignoreReturn) {
		String sql = "select * from warehouse where 1=1 ";

		if (!ignore(ignoreBlank, warehouse.getPro_addr())) {
			sql += " and pro_addr ='" + warehouse.getPro_addr() + "'";
		}

		if (!ignore(ignoreBlank, warehouse.getPro_model())) {
			sql += " and pro_model ='" + warehouse.getPro_model() + "'";
		}
		if (!ignore(ignoreBlank, warehouse.getPro_name())) {
			sql += " and pro_name ='" + warehouse.getPro_name() + "'";
		}
		if (!ignore(ignoreBlank, warehouse.getPro_remark())) {
			sql += " and pro_remark ='" + warehouse.getPro_remark() + "'";
		}
		if (!ignoreReturn) {
			if (isReturn) {
				sql += " and th_number is not null ";
			} else {
				sql += " and th_number is null ";
			}
		}

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return warehouse;
	}

	public Warehouse getWarehouseSingle(Map<String, String> filter) {

		Warehouse warehouse = new Warehouse();

		String sql = "select * from warehouse where 1=1 ";

		for (Map.Entry<String, String> entity : filter.entrySet()) {
			sql += " and " + entity.getKey() + " " + entity.getValue();
		}

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return warehouse;
	}

	/**
	 * 获取仓库产品列表
	 * 
	 * @param filter
	 * @return
	 */
	public List<Warehouse> getWarehouseList(Map<String, String> filter) {

		List<Warehouse> res = new ArrayList<Warehouse>();

		String sql = "select * from warehouse where 1=1 ";

		for (Map.Entry<String, String> entity : filter.entrySet()) {
			sql += " and " + entity.getKey() + " = '" + entity.getValue() + "'";
		}

		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			while (rs.next()) {
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));
				res.add(warehouse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return res;
	}

	/**
	 * 增加一个仓库产品
	 * 
	 * @param warehouse
	 */
	public void add(Warehouse warehouse) {
		infocrmdb einfodb = new infocrmdb();
		String sqlHead = "insert into warehouse(";
		String sqlTail = " values(";

		sqlHead += "pro_model,";
		sqlTail += "'" + warehouse.getPro_model() + "',";

		sqlHead += "pro_gg,";
		sqlTail += "'" + warehouse.getPro_gg() + "',";

		sqlHead += "pro_name,";
		sqlTail += "'" + warehouse.getPro_name() + "',";

		sqlHead += "pro_number,";
		sqlTail += "'" + warehouse.getPro_number() + "',";

		sqlHead += "pro_type,";
		sqlTail += "'" + warehouse.getPro_type() + "',";

		sqlHead += "saleprice,";
		sqlTail += warehouse.getSaleprice() + ",";

		sqlHead += "price_hb,";
		sqlTail += "'" + warehouse.getPrice_hb() + "',";

		sqlHead += "pro_s_num,";
		sqlTail += "'" + warehouse.getPro_s_num() + "',";

		sqlHead += "pro_num,";
		sqlTail += warehouse.getPro_num() + ",";

		sqlHead += "pro_unit,";
		sqlTail += "'" + warehouse.getPro_unit() + "',";

		sqlHead += "pro_price,";
		sqlTail += warehouse.getPro_price() + ",";

		sqlHead += "pro_addr,";
		sqlTail += "'" + warehouse.getPro_addr() + "',";

		sqlHead += "pro_supplier,";
		sqlTail += "'" + warehouse.getPro_supplier() + "',";

		sqlHead += "pro_remark,";
		sqlTail += "'" + warehouse.getPro_remark() + "',";

		sqlHead += "sjnum,";
		sqlTail += "'" + warehouse.getSjnum() + "',";

		sqlHead += "yqnum,";
		sqlTail += "'" + warehouse.getYqnum() + "',";

		sqlHead += "yqdate,";
		sqlTail += "'" + warehouse.getYqdate() + "',";

		sqlHead += "pro_secid,";
		sqlTail += "'" + warehouse.getPro_secid() + "',";

		sqlHead += "pro_sup_number,";
		sqlTail += "'" + warehouse.getPro_sup_number() + "',";

		sqlHead += "pro_min_num,";
		sqlTail += "'" + warehouse.getPro_min_num() + "',";

		sqlHead += "pro_max_num,";
		sqlTail += "'" + warehouse.getPro_max_num() + "',";

		sqlHead += "sale_states,";
		sqlTail += "'" + warehouse.getSale_states() + "',";

		sqlHead += "sale_min_price,";
		sqlTail += "'" + warehouse.getSale_min_price() + "',";

		sqlHead += "sale_max_price,";
		sqlTail += "'" + warehouse.getSale_max_price() + "',";

		sqlHead += "pro_date,";
		sqlTail += "'" + warehouse.getPro_date() + "',";

		sqlHead += "pro_man,";
		sqlTail += "'" + warehouse.getPro_man() + "',";

		sqlHead += "pro_ms,";
		sqlTail += "'" + warehouse.getPro_ms() + "',";

		sqlHead += "pro_jstx,";
		sqlTail += "'" + warehouse.getPro_jstx() + "',";

		sqlHead += "pro_yyfw,";
		sqlTail += "'" + warehouse.getPro_yyfw() + "',";

		sqlHead += "pin,";
		sqlTail += "'" + warehouse.getPin() + "',";

		sqlHead += "js_price,";
		sqlTail += "'" + warehouse.getJs_price() + "',";

		sqlHead += "th_number,";
		if (warehouse.getTh_number() == null) {
			sqlTail += "null,";
		} else {
			sqlTail += "'" + warehouse.getTh_number() + "',";
		}

		sqlHead += "zk_price)";
		sqlTail += "'" + warehouse.getZk_price() + "')";

		String sql = sqlHead + sqlTail;
		try {
			einfodb.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			einfodb.close();
		}
	}

	public int getRkCount(String epro) {
		String sql = "select count(id) as c from rkhouse where pro_model ='"
				+ epro + "'";
		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		int result = 0;
		try {
			if (rs.next()) {
				result = rs.getInt("c");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}

	public int genKey(String keyName) {
		String sql = "select keyValue from KeyGen where keyName ='" + keyName
				+ "'";
		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = einfodb.executeQuery(sql);
		int key = 0;
		try {
			if (rs.next()) {
				key = rs.getInt("keyValue");
				key++;
				String update_sql = "update KeyGen  set keyValue=" + key
						+ " where keyName='" + keyName + "'";
				boolean flag = einfodb.executeUpdate(update_sql);
				if (!flag) {
					key = 0;
				}
			} else {
				String update_sql = "insert into KeyGen(keyValue,keyName) values(1,'"
						+ keyName + "')";
				boolean flag = einfodb.executeUpdate(update_sql);
				if (!flag) {
					key = 0;
				}
				key = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return key;
	}

	public String getPid() {

		String pid = "";
		int in_no = genKey("pid");
		SimpleDateFormat simple1 = new SimpleDateFormat("yy");
		String number1 = simple1.format(new java.util.Date());

		String sno = "";
		if (in_no < 100) {
			sno = "0000";
		} else if ((100 <= in_no) & (in_no < 1000)) {
			sno = "000";
		} else if ((1000 <= in_no) & (in_no < 10000)) {
			sno = "00";
		} else
			sno = "";

		pid = "PL" + number1 + sno + in_no;

		return pid;
	}

	/**
	 * 
	 * 检查是否有仓库的权限
	 * 
	 * @param restrainName
	 *            权限名称
	 * @param warehouseName
	 *            仓库名称
	 * @return
	 */
	public String checkRestrain(String restrainName, String warehouseName)
			throws Exception {
		if (restrainMap.get(restrainName) != null) {
			return restrainMap.get(restrainName);
		} else {
			infocrmdb einfodb = new infocrmdb();
			String modsqlc = "select * from restrain_gp where restrain_name='"
					+ restrainName + "' and pro_ck='" + warehouseName + "'";
			ResultSet rsmodc = einfodb.executeQuery(modsqlc);
			String proview_price = "";
			if (rsmodc != null && rsmodc.next()) {
				proview_price = rsmodc.getString("proview_price").trim();
			}
			if (rsmodc != null)
				rsmodc.close();
			einfodb.close();
			restrainMap.put(warehouseName, proview_price);
			return proview_price;
		}
	}

	/**
	 * 取消入库单
	 * 
	 * @param id
	 */
	public void cancelWarehouseIn(int id) {

	}

	/**
	 * 获取入库单信息
	 * 
	 * @param id
	 * @return
	 */
	public InWarehouse getInWarehouseById(String id) {
		InWarehouse res = new InWarehouse();
		String sqltrdd = "select * from  in_warehouse  where id='" + id + "'";
		DBConnection db = new DBConnection();
		ResultSet rstrr = db.executeQuery(sqltrdd);

		try {
			if (rstrr.next()) {
				res.setNumber(rstrr.getString("number"));
				res.setStates(rstrr.getString("states"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rstrr != null) {
				try {
					rstrr.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		db.close();
		return res;
	}

	/**
	 * 
	 * 给出入库的信息增加日志
	 * 
	 */
	public void addWarehouseInOutLog(int orginNum, int changeNum,
			String number, String username, int wid) {

		String logSql = "insert into warehouse_in_out_log([number],[orgin_num],[change_num],[final_num],[operateTime],[operater],[wid],[rk_id]) values(?,?,?,?,getdate(),?,?,?)";

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		List<Object> params = new ArrayList<Object>();
		params.add(number);
		params.add(orginNum);
		params.add(changeNum);
		params.add(orginNum + changeNum);
		params.add(username);
		params.add(wid);
		params.add(0);
		db.executeUpdate(logSql, params);
		db.close();
	}

	/**
	 * 入库
	 * 
	 * @param id1
	 * @param username
	 * @param sp_number
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public String rk(String id1, String username, String sp_number)
			throws Exception {
		
		try{
			
			if(username==null){
				return  "用户还没有登陆";
			}
			
			Date date = new Date(System.currentTimeMillis());
			
			System.out.println("rk : start time "+date.getTime());

			

			// 当前时间
			java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			String pro_datetime1 = simple1.format(new java.util.Date());

			// 用来记录结果，结果为空时，表示成功入库
			// 否则，应该对入库结果进行处理
			String msg = "";
			
			if (checkLock(id1)) {
				msg = "该入库单正在入库中，请勿重复提交";
				System.out.println(msg);
				return msg;
			}
			
			addLock(id1);
			
			// 新建一个ＪＤＢＣ服务的对象
			DBConnection db = new DBConnection();
			
			
			InWarehouse inWarehouse = getInWarehouseById(id1);
			if (inWarehouse.getStates().trim().equals("已入库")) {
				msg = "对不起你已经提交了入库确认，请不要重复提交，或查看是否设置了IE缓存，谢谢！";
				System.out.println(msg);
				return msg;
			}

			List<Rkhouse> rkhouseList = getRkhouse(id1);

			// 入库单编号
			String inWarehouseNumber = inWarehouse.getNumber();

			for(Rkhouse rkhouse : rkhouseList) { // 1
				double zprice = 0.00;
				String pro_model1 = rkhouse.getPro_model().trim();
				String pro_name = rkhouse.getPro_name().trim();
				String pro_supplier = rkhouse.getPro_supplier();
				String pro_sup_number = rkhouse.getPro_sup_number();
				String cgNumber = rkhouse.getPro_types();
				String remark = rkhouse.getRemark();
				String pro_number = rkhouse.getPro_number();
				
				String cgname = rkhouse.getPro_cgy();

				int num1 = rkhouse.getPro_num();
				String pro_unit1 = rkhouse.getPro_unit();
				double new_price1 = rkhouse.getPro_price();
				String hb = rkhouse.getPro_hb();
				String pro_addr = rkhouse.getPro_addr();
				double ss = 1;
				String sqlhl = "select currrate from hltable where currname='" + hb
						+ "'";
				ResultSet rshl = db.executeQuery(sqlhl);
				if (rshl.next()) {
					String currrate1 = rshl.getString("currrate");
					if (currrate1 != null)
						ss = Double.parseDouble(currrate1);
					rshl.close();
				}
				double new_price = new_price1;

				String sql = "select id,pro_num,pro_price,price_hb,pro_man,pro_gg,yqdate from warehouse where pro_model='"
						+ pro_model1
						+ "' and pro_addr='"
						+ pro_addr
						+ "' and pro_name='"
						+ pro_name
						+ "' and pro_remark='"
						+ remark + "'  ";

				ResultSet rs = db.executeQuery(sql);

				// 如果产品已经入库的情况下，更新库信息
				if (rs.next()) {// 2
					int wid = rs.getInt("id");
					double pro_num = rs.getDouble("pro_num");

					double old_price = rs.getDouble("pro_price");
					String w_hb = rs.getString("price_hb");

					double w_hb_hl = 1;
					String sqlhl_w = "select currrate from hltable where currname='"
							+ w_hb + "'";
					ResultSet rshl_w = db.executeQuery(sqlhl_w);
					if (rshl_w.next()) { // 2
						w_hb_hl = rshl_w.getDouble("currrate");
					}// 2
					rshl_w.close();
					new_price = new_price * ss / w_hb_hl;
					double total_num = pro_num + num1;
					if (total_num > 0) {
						zprice = (num1 * new_price + pro_num * old_price)
								/ total_num;
					}
					if (new_price == 0) {
						zprice = old_price;
					}
					if (old_price == 0) {
						zprice = new_price;
					}
					rs.close();
					String upsql = "update warehouse set pro_num=pro_num+" + num1
							+ ",pro_price='" + zprice + "',pro_unit='" + pro_unit1
							+ "',pro_remark='" + remark + "'  where pro_model='"
							+ pro_model1 + "' and pro_addr='" + pro_addr
							+ "' and pro_name='" + pro_name + "' and pro_remark='"
							+ remark + "' ";
					boolean up = db.executeUpdate(upsql);
					if (!up) {
						msg = "<font size='2' color='#FF0000'>更新库存失败,你所输入的内容超出系统范围或输入类型不符!1111111111111</font>";
						return msg;
					}
					addWarehouseInOutLog((int) pro_num, num1, inWarehouseNumber,
							username, wid);

				}
				// 新增一条库信息
				else {
					String name1 = username;
					String strSQLrw = "insert into warehouse(pro_model,pro_gg,pro_name,"
							+ "pro_number,pro_type,saleprice,price_hb,pro_s_num,pro_num,pro_unit, "
							+ "pro_price,pro_supplier,pro_addr,pro_remark,sjnum,yqnum,yqdate,pro_secid,"
							+ "pro_sup_number,pro_min_num,pro_max_num,sale_states,sale_min_price,"
							+ "sale_max_price,pro_date,pro_man,pro_ms,pro_jstx,pro_yyfw,pin,js_price,"
							+ "zk_price,th_number,pro_weight_unit,batch_no) "
							+ " values('"
							+ pro_model1
							+ "','"
							+ pro_number
							+ "','"
							+ pro_name
							+ "','','IC','0','"
							+ hb
							+ "','0','"
							+ num1
							+ "','"
							+ pro_unit1
							+ "','"
							+ new_price1
							+ "','"
							+ pro_supplier
							+ "','"
							+ pro_addr
							+ "','"
							+ remark
							+ "','0','0','','','"
							+ pro_sup_number
							+ "','0','0','常销产品','0','0','"
							+ pro_datetime1
							+ "','"
							+ name1 + "','','','','1','0','0',null,'','')";
					boolean trw = db.executeUpdate(strSQLrw);
					if (!trw) {
						msg = "插入仓库失败";
						return msg;
					}
					int wid = db.getIdenty("warehouse");
					addWarehouseInOutLog(0, num1, inWarehouseNumber, username, wid);

				}

				SendMail sendmail = new SendMail();

				// 如果查不到负责人，则不会发邮件
				if (cgname != null && cgname.trim().length() != 0) {
					String content = ""; // 邮件内容

					String title = ""; // 邮件标题
					// 在内容里面加上采购订单号、供应商名称、型号、品牌、封装、年份、数量
					content += "采购订单号为："
							+ cgNumber
							+ ",供应商为："
							+ pro_supplier
							+ ",型号为："
							+ pro_model1
							+ "，品牌为："
							+ pro_sup_number
							+ "，封装："
							+ pro_number
							+ "，年份："
							+ pro_name
							+ "，数量:"
							+ num1
							+ ",<br>请及时为供应商评分，<a href=\"/supplier/pf.jsp?co_number="
							+ sp_number + "&co_name=" + pro_supplier
							+ "\">供应商评分</a>";

					title += pro_supplier + "," + pro_model1 + "," + num1 + "已入库"; // 在邮件标题加上供应商、型号、数量

					sendmail.sendMail(title, content, cgname, username);

				}

				logWarehouseIn(pro_model1, username, remark);

			}// 1

			//
			String supsql = "update in_warehouse set states='已入库'  where id='"
					+ id1 + "'";
			boolean sups = db.executeUpdate(supsql);
			if (!sups) {
				msg = "<font size='2' color='#FF0000'>更新入库单失败,你所输入的内容超出系统范围或输入类型不符!</font>";
				return msg;
			}

			db.close();

			
			
			Date end = new Date(System.currentTimeMillis());
			System.out.println("rk starttime is "+date.getTime()+" end time is "+end.getTime());

			return msg;
		}catch(Exception e){
			
			return e.getMessage();
		}finally{
			removeLock(id1);
		}
		
		

	}

	private int pauseInt(String str) {
		int res = 0;
		try {
			res = Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private List<String> getListFromStoken(String str) {
		// 产品编号的数组
		List<String> res = new ArrayList<String>();
		if (str.trim().length() > 0) {
			for (String wid : str.split(",")) {
				res.add(wid);
			}
		}
		return res;
	}

	/**
	 * 
	 * 全部销售产品出库
	 * 
	 * @param username
	 * @param ddid1
	 * @param coname
	 * @param fynumber
	 * @param co_number
	 * @return
	 * 
	 **/
	public String doSout(String username, String ddid, String coname,
			String fynumber, String co_number, String wids, String sids) {

		String msg = null;

		/**
		 * 检查是否有重复操作
		 */
		if (checkLock(ddid)) {
			msg = ddid + "正在做全部出库操作，请勿重复操作";
			return msg;
		}
		
		try{
			// 产品编号的数组
			List<String> widList = getListFromStoken(wids);

			List<String> sidList = getListFromStoken(sids);

			SaleManager saleManager = new SaleManager();

			if (widList.size() != sidList.size()) {
				msg = "出库的选择与销售产品不一致，请刷新页面后重试";
				return msg;
			}

			addLock(ddid);

			// 新建一个ＪＤＢＣ服务的对象
			DBConnection db = new DBConnection();

			SimpleDateFormat simplew = new SimpleDateFormat("yyMMdd");
			String nn = simplew.format(new java.util.Date());
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = simple.format(new java.util.Date());

			String ddsqlst_sd = "update client set nsnumber='" + currentDate
					+ "'  where coname='" + coname + "'";
			db.executeUpdate(ddsqlst_sd);
			String sqldd = "select  id,man,money,item,item,mode,source,trade,habitus,send_date,tr,yf_types,yf_money,tr_addr,tr_man,tr_tel,dept,deptjb,remarks from subscribe where id='"
					+ ddid + "'";
			ResultSet rsdd = db.executeQuery(sqldd);
			try {
				if (!rsdd.next()) {
					msg = "销售订单不存在，请检查销售订单是否被删除！";
					removeLock(ddid);
					return msg;
				}
				String sales_man = rsdd.getString("man");
				String items = rsdd.getString("item").trim();
				String payment = rsdd.getString("mode");
				int datet = rsdd.getInt("source");
				String dd_date = rsdd.getString("send_date");
				String tr = rsdd.getString("tr");
				String yf_types = rsdd.getString("yf_types");
				double yf_money = rsdd.getDouble("yf_money");

				String tr_addr = rsdd.getString("tr_addr");
				String tr_man = rsdd.getString("tr_man");
				String tr_tel = rsdd.getString("tr_tel");

				String dept = rsdd.getString("dept");
				String deptjb = rsdd.getString("deptjb");
				String remark = rsdd.getString("remarks");
				rsdd.close();

				for (int i = 0; i < sidList.size(); i++) {
					String sid = sidList.get(i);
					SalePro salePro = saleManager.getSalePro(sid);
					// 检查销售订单中是否有未出库产品
					// 有未出库产品，全部出库
					if (salePro.getNum() != salePro.getS_num()) {

						int ddproid = salePro.getId();
						int selectedSwid = pauseInt(widList.get(i));

						String pro_model = salePro.getEpro();
						String pro_name = salePro.getCpro();
						String pro_number = salePro.getFypronum().trim();
						int num = salePro.getNum();
						int s_c_num = salePro.getS_c_num();
						int s_num = salePro.getS_num();
						int n_num1 = num - s_num;
						int n_num = n_num1 + 0;
						String fy_states = "";
						if (salePro.getFy_states() != null) {
							fy_states = salePro.getFy_states().trim();
						}

						if (fy_states.equals("待发运")) {
							n_num = n_num + s_c_num;
						}
						String punit = salePro.getUnit();
						double saleprice = salePro.getSalejg();
						String salehb = salePro.getPricehb();
						String supplier = salePro.getSupplier();
						String p_check = salePro.getP_check();

						String sql = "select id,pro_num,pro_addr,pro_price,price_hb from warehouse where id="
								+ selectedSwid;

						ResultSet rs = db.executeQuery(sql);
						if (!rs.next()) {
							msg = selectedSwid + "暂无该产品:";
							msg += "型号是:" + pro_model + "批号是:" + pro_name;
							removeLock(ddid);
							return msg;
						}
						int wid = rs.getInt("id");
						int pro_num = rs.getInt("pro_num");
						String proaddr = rs.getString("pro_addr");
						String pro_price = rs.getString("pro_price");
						String price_hb = rs.getString("price_hb");
						int zpro_num = pro_num - n_num1;
						if (zpro_num < 0) {
							msg = "库存不足";
							removeLock(ddid);
							return msg;
						}
						rs.close();

						int tno = 1;
						String sno = "";
						String tr_types = "";

						String sqltrdd = "select  * from transportation where invoice='"
								+ fynumber + "'  and sate='发运通知单'";
						ResultSet rstrr = db.executeQuery(sqltrdd);
						if (!rstrr.next()) {
							String strSQLw = "select  *  from transportation where tr_types like '"
									+ nn + "%'   order by in_no desc";
							java.sql.ResultSet sqlRsttr = db.executeQuery(strSQLw);
							if (sqlRsttr.next()) {
								tno = sqlRsttr.getInt("in_no") + 1;
							}
							sqlRsttr.close();
							if (tno < 10) {
								sno = "00";
							} else if ((10 <= tno) & (tno < 100)) {
								sno = "0";
							} else
								sno = "";
							String strSQLfy = "insert into transportation(tr_types,ddid,fyid,invoice,subscribe,coname,aimport,transportation,mbdate,sjdate,linkman,linktel,mode,datet,sendcompany,slinkman,slinktel,sate,fy_num,fy_yf,fy_bf,fy_js,fy_sz,sale_man,sale_dept,deptjb,co_number,fy_date,yf_types,yf_money,remark,trans_com,in_no,tr_print) values('"
									+ nn
									+ ""
									+ sno
									+ ""
									+ tno
									+ "','"
									+ ddid
									+ "','"
									+ ddid
									+ "','"
									+ fynumber
									+ "','"
									+ fynumber
									+ "','"
									+ coname
									+ "','"
									+ tr_addr
									+ "','"
									+ tr
									+ "','"
									+ dd_date
									+ "','"
									+ dd_date
									+ "','"
									+ tr_man
									+ "','"
									+ tr_tel
									+ "','"
									+ payment
									+ "','"
									+ datet
									+ "','','"
									+ sales_man
									+ "','','发运通知单','','0','0','','','"
									+ sales_man
									+ "','"
									+ dept
									+ "','"
									+ deptjb
									+ "','"
									+ co_number
									+ "','"
									+ currentDate
									+ "','"
									+ yf_types
									+ "','"
									+ yf_money
									+ "','"
									+ remark + "','','" + tno + "','')";
							boolean tfy = db.executeUpdate(strSQLfy);
							if (!tfy) {
								msg = "发运失败,你所输入的内容超出系统范围或输入类型不符!";
								removeLock(ddid);
								return msg;
							}
							String ddsqls = "update subscribe set fy_number='" + nn
									+ "" + sno + "" + tno + "',send_date='"
									+ currentDate + "'  where id='" + ddid + "'";
							boolean tssu = db.executeUpdate(ddsqls);

							if (!tssu) {
								msg = "更新失败,你所输入的内容超出系统范围或输入类型不符!";
								removeLock(ddid);
								return msg;
							}
							tr_types = nn + sno + tno;
						} else {
							tr_types = rstrr.getString("tr_types").trim();
						}
						rstrr.close();
						/*
						 * String strSQL1 =
						 * "select  *  from outhouse where pro_out_num like '"
						 * +number+"%'   order by in_no desc"; sqlRsto =
						 * einfodb.executeQuery(strSQL1); if(sqlRsto.next()) {
						 * no=sqlRsto.getInt("in_no")+1; } String sno1="";
						 * if(no<10){ sno1="000"; } else if((10<=no)&(no<100)){
						 * sno1="00"; } else if((100<=no)&(no<1000)){ sno1="0"; }
						 * else sno1=""; sqlRsto.close();
						 */
						Connection conn = db.getConnectionObject();
						if (conn == null) {
							removeLock(ddid);
							return "数据库连接无法获取";
						}

						try {
							conn.setAutoCommit(false);
							String strSQLq = "insert into outhouse values('"
									+ fynumber + "','" + coname + "','" + pro_model
									+ "','" + pro_name + "','" + n_num1 + "','"
									+ punit + "','" + supplier + "','"
									+ currentDate + "','" + pro_number + "','"
									+ username + "','" + proaddr + "','已出库','"
									+ ddid + "','" + p_check + "','"+wid+"','"
									+ co_number + "','" + saleprice + "','"
									+ salehb + "','" + wid + "','" + items + "','"
									+ tr_types + "','" + tno + "',0)";
							boolean tq = db.executeUpdate(strSQLq);
							if (!tq) {
								msg = "添加出库单失败,你所输入的内容超出系统范围或输入类型不符!";
								removeLock(ddid);
								return msg;
							}
							String strSQLw = "update warehouse set pro_num='"
									+ zpro_num + "'  where  id=" + selectedSwid;
							boolean tw = db.executeUpdate(strSQLw);
							if (!tw) {
								msg = "更新库存失败,你所输入的内容超出系统范围或输入类型不符!";
								removeLock(ddid);
								return msg;
							}
							String strSQLwf = "update ddpro set selljg='"
									+ pro_price + "',money='" + price_hb
									+ "',s_num='" + num + "',s_c_num='" + n_num1
									+ "',s_tr_date='" + currentDate
									+ "',fy_states='待发运'  where id='" + ddproid
									+ "' ";
							boolean twf = db.executeUpdate(strSQLwf);
							if (!twf) {
								msg = "更新失败,你所输入的内容超出系统范围或输入类型不符!";
								return msg;
							}
							conn.commit();
							conn.setAutoCommit(true);

							addWarehouseInOutLog(pro_num, -n_num1, fynumber,
									username, selectedSwid);
						} catch (Exception e) {
							conn.rollback();
							e.printStackTrace();
							removeLock(ddid);
							return "操作异常";
						}

					}
				}
				String ddsqls1 = "update subscribe set state='已发运'  where id='"
						+ ddid + "'";
				boolean tssu1 = db.executeUpdate(ddsqls1);

				if (!tssu1) {
					msg = "更新失败,你所输入的内容超出系统范围或输入类型不符!";
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				msg = "数据库操作异常";
			}
			removeLock(ddid);
			db.close();
		}catch(Exception e){
			return e.getMessage();
		}finally{
			removeLock(ddid);
		}

		
		return msg;

	}

	/**
	 * 单个产品出库
	 * 
	 * @param username
	 * @param id
	 * @param ddid
	 * @return
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	public String doProout(String username, String id, String ddid,
			String num1, String currentDate, String coname, String fynumber,
			String co_number, int wid) throws NumberFormatException,
			SQLException {

		String msg = null;

		/**
		 * 检查是否有重复操作
		 */
		if (checkLock(id)) {
			msg = id + "正在做单个出库操作，请勿重复操作";
			return msg;
		}
		
		try{
			addLock(id);

			// 新建一个ＪＤＢＣ服务的对象
			DBConnection db = new DBConnection();
			SimpleDateFormat simplew = new SimpleDateFormat("yyMMdd");
			// 获取当前时间
			String nn = simplew.format(new java.util.Date());

			String strSSql = "select * from ddpro where (num-s_num)>='" + num1
					+ "' and id='" + id + "'";
			ResultSet rsp = db.executeQuery(strSSql);
			if (!rsp.next()) {
				msg = "本次出库数量不符，请重新输入!";
				removeLock(id);
				return msg;
			} else {

				// 需要出库的数量
				int n_num1 = Integer.parseInt(num1);

				// 更新客户的出库时间
				String ddsqlst_sd = "update client set nsnumber='" + currentDate
						+ "'  where coname='" + coname + "'";
				db.executeUpdate(ddsqlst_sd);

				String sqldd = "select  id,man,money,item,mode,source,trade,habitus,send_date,tr,yf_types,yf_money,tr_addr,tr_man,tr_tel,dept,deptjb,remarks from subscribe where id='"
						+ ddid + "'";
				ResultSet rsdd = db.executeQuery(sqldd);
				if (!rsdd.next()) {
					msg = "not have record";
					removeLock(id);
					return msg;
				}

				String sales_man = rsdd.getString("man");
				String payment = rsdd.getString("mode");
				int datet = rsdd.getInt("source");
				String dd_date = rsdd.getString("send_date");
				String tr = rsdd.getString("tr");
				String yf_types = rsdd.getString("yf_types");
				double yf_money = rsdd.getDouble("yf_money");
				String tr_addr = rsdd.getString("tr_addr");
				String tr_man = rsdd.getString("tr_man");
				String tr_tel = rsdd.getString("tr_tel");
				String dept = rsdd.getString("dept");
				String deptjb = rsdd.getString("deptjb");
				String remark = rsdd.getString("remarks");
				rsdd.close();

				String strSQLpro = "select id,epro,cpro,fypronum,num,s_c_num,s_num,fy_states,unit,salejg,pricehb,supplier,wid,p_check,remark from ddpro where  id='"
						+ id + "'";
				ResultSet prs = db.executeQuery(strSQLpro);
				while (prs.next()) {
					int ddproid = prs.getInt("id");
					String pro_model = prs.getString("epro").trim();
					String pro_name = prs.getString("cpro");
					String pro_number = prs.getString("fypronum").trim();
					int s_c_num = prs.getInt("s_c_num");
					int s_num = prs.getInt("s_num");
					int snum = n_num1 + 0;
					String fy_states = prs.getString("fy_states").trim();
					if (fy_states.equals("待发运")) {
						snum = snum + s_c_num;
					}
					String punit = prs.getString("unit");
					String saleprice = prs.getString("salejg");
					String salehb = prs.getString("pricehb");
					String supplier = prs.getString("supplier");
					String p_check = prs.getString("p_check");

					String sql = "select pro_num,pro_addr,pro_price,price_hb from warehouse where id ="
							+ wid;
					System.out.println("w sql is " + sql);

					ResultSet rs = db.executeQuery(sql);
					if (!rs.next()) {
						msg = "仓库暂无该产品:" + pro_model;
						removeLock(id);
						return msg;
					}
					int pro_num = rs.getInt("pro_num");
					String proaddr = rs.getString("pro_addr");
					String pro_price = rs.getString("pro_price");
					String price_hb = rs.getString("price_hb");
					int zpro_num = pro_num - n_num1;
					if (pro_num < n_num1) {
						msg = "库存不足";
						removeLock(id);
						return msg;
					}
					rs.close();

					String sno = "";
					int tno = 1;
					String tr_types = "";
					String sqltrdd = "select  * from transportation where invoice='"
							+ fynumber + "' and sate='发运通知单'";
					ResultSet rstrr = db.executeQuery(sqltrdd);
					if (!rstrr.next()) {

						String strSQLw = "select * from transportation where tr_types like '"
								+ nn + "%'   order by in_no desc";
						java.sql.ResultSet sqlRsttr = db.executeQuery(strSQLw);
						if (sqlRsttr.next()) {
							tno = sqlRsttr.getInt("in_no") + 1;
						}
						sqlRsttr.close();

						if (tno < 10) {
							sno = "00";
						} else if ((10 <= tno) & (tno < 100)) {
							sno = "0";
						} else
							sno = "";
						String strSQLfy = "insert into transportation(tr_types,ddid,fyid,invoice,subscribe,coname,aimport,transportation,mbdate,sjdate,linkman,linktel,mode,datet,sendcompany,slinkman,slinktel,sate,fy_num,fy_yf,fy_bf,fy_js,fy_sz,sale_man,sale_dept,deptjb,co_number,fy_date,yf_types,yf_money,remark,trans_com,in_no,tr_print) values('"
								+ nn
								+ ""
								+ sno
								+ ""
								+ tno
								+ "','"
								+ ddid
								+ "','"
								+ ddid
								+ "','"
								+ fynumber
								+ "','"
								+ fynumber
								+ "','"
								+ coname
								+ "','"
								+ tr_addr
								+ "','"
								+ tr
								+ "','"
								+ dd_date
								+ "','"
								+ dd_date
								+ "','"
								+ tr_man
								+ "','"
								+ tr_tel
								+ "','"
								+ payment
								+ "','"
								+ datet
								+ "','','"
								+ sales_man
								+ "','','发运通知单','','0','0','','','"
								+ sales_man
								+ "','"
								+ dept
								+ "','"
								+ deptjb
								+ "','"
								+ co_number
								+ "','"
								+ currentDate
								+ "','"
								+ yf_types
								+ "','"
								+ yf_money
								+ "','"
								+ remark
								+ "','','"
								+ tno
								+ "','')";
						boolean tfy = db.executeUpdate(strSQLfy);
						if (!tfy) {
							msg = "发运失败,你所输入的内容超出系统范围或输入类型不符!";
							removeLock(id);
							return msg;
						}
						String ddsqls = "update subscribe set fy_number='" + nn
								+ "" + sno + "" + tno + "'  where id='" + ddid
								+ "'";
						boolean tssu = db.executeUpdate(ddsqls);
						if (!tssu) {
							msg = "更新失败,你所输入的内容超出系统范围或输入类型不符!";
							removeLock(id);
							return msg;
						}
						tr_types = nn + sno + tno;
					} else {
						tr_types = rstrr.getString("tr_types").trim();
					}
					rstrr.close();
					/*
					 * String strSQL1 =
					 * "select  *  from outhouse where pro_out_num like '"
					 * +number+"%'   order by in_no desc"; sqlRsto =
					 * einfodb.executeQuery(strSQL1); if(sqlRsto.next()) {
					 * no=sqlRsto.getInt("in_no")+1; } sqlRsto.close(); String
					 * sno1=""; if(no<10){ sno1="000"; } else if((10<=no)&(no<100)){
					 * sno1="00"; } else if((100<=no)&(no<1000)){ sno1="0"; } else
					 * sno1="";
					 */
					Connection conn = db.getConnectionObject();
					if (conn == null) {
						removeLock(id);
						return "数据库连接获取不到";
					}

					try {
						conn.setAutoCommit(false);
						String strSQLq = "insert into outhouse([pro_fynum],[pro_coname],[pro_model],[pro_name],[pro_num],[pro_unit],[pro_supplier],"
								+ "[pro_datetime],[pro_number],[slinkman],[slinktel],[states],[ddid],[remark],[wid],[pro_coname_num],[pro_sales_price],"
								+ "[pro_price_hb],[pro_rate_types],[pro_rate],[pro_out_num],[in_no]) values('" + fynumber
								+ "','" + coname + "','" + pro_model + "','"
								+ pro_name + "','" + n_num1 + "','" + punit + "','"
								+ supplier + "','" + currentDate + "','"
								+ pro_number + "','" + username + "','" + proaddr
								+ "','已出库','" + ddid + "','" + p_check + "','1','"
								+ co_number + "','" + saleprice + "','" + salehb
								+ "','" + wid + "','0','" + tr_types + "','" + tno
								+ "')";
						boolean tq = db.executeUpdate(strSQLq);
						if (!tq) {
							msg = "添加出库单失败,你所输入的内容超出系统范围或输入类型不符!";
							removeLock(id);
							return msg;
						}

						String strSQLw = "update warehouse set pro_num='"
								+ zpro_num + "'  where id= " + wid;
						boolean tw = db.executeUpdate(strSQLw);
						if (!tw) {
							msg = "更新库存失败,你所输入的内容超出系统范围或输入类型不符!";
							removeLock(id);
							return msg;
						}
						int nnum = s_num + n_num1;
						String strSQLwf = "update ddpro set selljg='" + pro_price
								+ "',money='" + price_hb + "',s_num='" + nnum
								+ "',s_c_num='" + snum + "',s_tr_date='"
								+ currentDate + "',fy_states='待发运'  where id='"
								+ ddproid + "' ";
						boolean twf = db.executeUpdate(strSQLwf);
						if (!twf) {
							msg = "更新失败,你所输入的内容超出系统范围或输入类型不符!";
							removeLock(id);
							return msg;
						}
						conn.commit();
						conn.setAutoCommit(true);
						addWarehouseInOutLog(pro_num, -n_num1, fynumber, username,
								wid);

					} catch (Exception e) {
						try {
							conn.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
						removeLock(id);
						return "数据库操作异常";
					}

				}

			}
			removeLock(id);
			return msg;
		}catch(Exception e){
			
			e.printStackTrace();
			return e.getMessage();
		}finally{
			removeLock(ddid);
		}

		
	}

	/**
	 * 通过产品的型号和用户权限来查询产品信息
	 * 
	 * @return
	 */
	public List<Warehouse> getWarehouseByModelAndRestain(
			String pro_model) {

		Map<String, String> warehouseFilterMap = new HashMap<String, String>();
		warehouseFilterMap.put("pro_model", pro_model);
		List<Warehouse> warehouseList = getWarehouseList(warehouseFilterMap);
		return warehouseList;
	}

	/**
	 * 通过产品型号的数量 这里的型号是唯一的
	 * 
	 * @return
	 */
	public int getProModelCount() {

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();

		String sql = "select count(distinct pro_model) from warehouse";

		java.sql.ResultSet rs = db.executeQuery(sql);

		int res = 0;

		try {
			if (rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return res;
	}

	public int getInWarehouseCount(String id) {
		Warehouse w = getWarehouseById(id);
		String pro_model = w.getPro_model();
		String pro_name = w.getPro_name();
		String remark = w.getPro_remark();
		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		String sql = "select sum(rkhouse.pro_num) from rkhouse,in_warehouse where in_warehouse.id = rkhouse.pro_rk_num and in_warehouse.[states]='已入库' and rkhouse.pro_model='"
				+ pro_model
				+ "' and pro_name = '"
				+ pro_name
				+ "' and rkhouse.remark='" + remark + "'";
		ResultSet rs = db.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next())
				res = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public int getOutWarehouseCount(String id) {
		Warehouse w = getWarehouseById(id);
		String pro_model = w.getPro_model();
		String pro_name = w.getPro_name();
		String remark = w.getPro_remark();

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		String sql = "select sum(pro_num) from outhouse where pro_model='"
				+ pro_model + "' and pro_name = '" + pro_name
				+ "' and remark='" + remark + "'";
		ResultSet rs = db.executeQuery(sql);
		int res = 0;
		try {
			if (rs.next())
				res = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	/**
	 * 获取入库单列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Rkhouse> getInWarehouseList(String id) {

		Warehouse w = getWarehouseById(id);
		String pro_model = w.getPro_model();
		String pro_name = w.getPro_name();
		String remark = w.getPro_remark();

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();

		List<Rkhouse> res = new ArrayList<Rkhouse>();

		String sql = "select rkhouse.*,in_warehouse.number,in_warehouse.man from rkhouse,in_warehouse where in_warehouse.id = rkhouse.pro_rk_num and in_warehouse.[states]='已入库' and rkhouse.pro_model='"
				+ pro_model
				+ "' and pro_name = '"
				+ pro_name
				+ "' and rkhouse.remark='" + remark + "'";
		ResultSet rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				Rkhouse rkhouse = new Rkhouse();
				rkhouse.setId(rs.getInt("id"));
				rkhouse.setNumber(rs.getString("number"));
				rkhouse.setPro_model(rs.getString("pro_model"));
				rkhouse.setPro_name(rs.getString("pro_name"));
				rkhouse.setRemark(rs.getString("remark"));
				rkhouse.setPro_num(rs.getInt("pro_num"));
				rkhouse.setMan(rs.getString("man"));
				res.add(rkhouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return res;

	}

	/**
	 * 显示出库记录列表
	 * 
	 * @param id
	 * @return
	 */
	public List<Outhouse> getOutWarehouseList(String id) {

		Warehouse w = getWarehouseById(id);
		String pro_model = w.getPro_model();
		String pro_name = w.getPro_name();
		String remark = w.getPro_remark();

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();

		List<Outhouse> res = new ArrayList<Outhouse>();

		String sql = "select * from outhouse where pro_model='" + pro_model
				+ "' and pro_name = '" + pro_name + "' and remark='" + remark
				+ "'";
		ResultSet rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				Outhouse rkhouse = new Outhouse();
				rkhouse.setId(rs.getInt("id"));
				rkhouse.setNumber(rs.getString("pro_fynum"));
				rkhouse.setPro_model(rs.getString("pro_model"));
				rkhouse.setPro_name(rs.getString("pro_name"));
				rkhouse.setRemark(rs.getString("remark"));
				rkhouse.setPro_num(rs.getInt("pro_num"));
				rkhouse.setMan(rs.getString("slinkman"));
				res.add(rkhouse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return res;

	}

	/**
	 * 
	 * 调整仓库数量
	 * 
	 * @param id
	 * 
	 */
	public void adaptProNum(String id, String pro_num, String username) {
		Warehouse warehouse = getWarehouseById(id);
		// 原始的库存
		int orginProNum = warehouse.getPro_num();
		int pro_num_int = 0;
		try {
			pro_num_int = Integer.parseInt(pro_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int change_num = pro_num_int - orginProNum;

		String sqlUpdate = "update warehouse set pro_num = " + pro_num
				+ " where id=" + id;
		String sqlLog = "INSERT INTO [warehouse_in_out_log]([number],[orgin_num],[change_num],[final_num],"
				+ "[operateTime],[operater],wid,rk_id)"
				+ " VALUES ('',"
				+ orginProNum
				+ ","
				+ change_num
				+ ","
				+ pro_num_int
				+ ",getdate(),'" + username + "'," + id + ",0)";

		List<String> sqlList = new ArrayList<String>();
		sqlList.add(sqlUpdate);
		sqlList.add(sqlLog);

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		db.execBatch(sqlList);
		db.close();

	}

	/**
	 * 
	 * 调整仓库数量
	 * 
	 * @param id
	 * 
	 */
	public void finishStoking(String id, String username) {

		String sql = "update warehouse set finishStoking = 1" + " where id="
				+ id;

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		db.executeUpdate(sql);
		db.close();

	}

	public List<Rkhouse> getRkhouse(String id) {
		List<Rkhouse> res = new ArrayList<Rkhouse>();
		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		String sql = "select * from rkhouse where pro_rk_num = " + id;
		ResultSet rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				Rkhouse rk = new Rkhouse();
				rk.setId(rs.getInt("id"));
				rk.setPro_num(rs.getInt("pro_num"));
				rk.setPro_model(rs.getString("pro_model"));
				rk.setCgproId(rs.getInt("pro_rate"));
				rk.setPro_name(rs.getString("pro_name"));
				rk.setPro_supplier(rs.getString("pro_supplier"));
				rk.setPro_sup_number(rs.getString("pro_sup_number"));
				rk.setPro_types(rs.getString("pro_types"));
				rk.setRemark(rs.getString("remark"));
				rk.setPro_addr(rs.getString("pro_addr"));
				rk.setPro_number(rs.getString("pro_number"));
				rk.setPro_cgy(rs.getString("pro_cgy"));
				rk.setPro_unit(rs.getString("pro_unit"));
				rk.setPro_price(rs.getDouble("pro_price"));
				rk.setPro_hb(rs.getString("pro_hb"));
				res.add(rk);
			}
		} catch (SQLException e) {
			db.closeRs(rs);
			e.printStackTrace();
		}finally{
			db.close();
		}
		return res;

	}

	public InWarehouse getInWarehouse(String id) {

		InWarehouse res = new InWarehouse();
		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();
		String sql = "select * from in_warehouse where id = " + id;
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				res.setNumber(rs.getString("number"));
				res.setStates(rs.getString("states"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.closeRs(rs);
		}
		return res;
	}
	
	private List<Warehouse> getWarehouseList(ResultSet rs){
		List<Warehouse> res = new ArrayList<Warehouse>();
		try {
			while(rs.next()){
				
				Warehouse warehouse = new Warehouse();
				warehouse.setId(rs.getString("id"));
				warehouse.setPro_model(rs.getString("pro_model"));
				warehouse.setPro_gg(rs.getString("pro_gg"));
				warehouse.setPro_name(rs.getString("pro_name"));
				warehouse.setPro_number(rs.getString("pro_number"));
				warehouse.setPro_type(rs.getString("pro_type"));
				warehouse.setSaleprice(rs.getDouble("saleprice"));
				warehouse.setPrice_hb(rs.getString("price_hb"));
				warehouse.setPro_s_num(rs.getInt("pro_s_num"));
				warehouse.setPro_num(rs.getInt("pro_num"));
				warehouse.setPro_unit(rs.getString("pro_unit"));
				warehouse.setPro_price(rs.getDouble("pro_price"));
				warehouse.setPro_supplier(rs.getString("pro_supplier"));
				warehouse.setPro_addr(rs.getString("pro_addr"));
				warehouse.setPro_remark(rs.getString("pro_remark"));
				warehouse.setSjnum(rs.getString("sjnum"));
				warehouse.setYqnum(rs.getString("yqnum"));
				warehouse.setYqdate(rs.getString("yqdate"));
				warehouse.setPro_secid(rs.getString("pro_secid"));
				warehouse.setPro_sup_number(rs.getString("pro_sup_number"));
				warehouse.setPro_min_num(rs.getInt("pro_min_num"));
				warehouse.setPro_max_num(rs.getInt("pro_max_num"));
				warehouse.setSale_states(rs.getString("sale_states"));
				warehouse.setSale_min_price(rs.getString("sale_min_price"));
				warehouse.setSale_max_price(rs.getString("sale_max_price"));
				warehouse.setPro_date(rs.getString("pro_date"));
				warehouse.setPro_man(rs.getString("pro_man"));
				warehouse.setPro_ms(rs.getString("pro_ms"));
				warehouse.setPro_jstx(rs.getString("pro_jstx"));
				warehouse.setPro_yyfw(rs.getString("pro_yyfw"));
				warehouse.setPin(rs.getInt("pin"));
				warehouse.setJs_price(rs.getDouble("js_price"));
				warehouse.setZk_price(rs.getDouble("zk_price"));
				res.add(warehouse);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Warehouse> getWarehouseNoPic(String pro_addr){
		
		DBConnection db = new DBConnection();
		String sql = "select * from warehouse where pro_num <= 0 and id not in (select oid from o_pic) and pro_addr='"+pro_addr+"' order by pro_model";
		
		ResultSet rs = db.executeQuery(sql);
		return getWarehouseList(rs);
	}
	
	/**
	 * 删除时，备份删除的库存信息
	 * @param id
	 */
	public void deleteWarehouse(int id){
		
		String backSQL = "insert into warehouse_del select * from warehouse where id="+id;
		String strSQL="delete from warehouse where id='" + id + "'";
		
		List<String> batchDeleteSQLList = new ArrayList<String>();
		batchDeleteSQLList.add(backSQL);
		batchDeleteSQLList.add(strSQL);
		DBConnection db = new DBConnection();
		db.execBatch(batchDeleteSQLList);
	}
	
	public void restoreWarehouse(int id){
		String sql = "insert into warehouse(pro_model,pro_gg,pro_name,pro_number,[pro_type],[saleprice],[price_hb],[pro_s_num],[pro_num],[pro_unit],[pro_price],[pro_supplier],[pro_addr],[pro_remark],[sjnum],[yqnum],[yqdate],[pro_secid],[pro_sup_number],[pro_min_num],[pro_max_num],[sale_states],[sale_min_price],[sale_max_price],[pro_date],[pro_man],[pro_ms],[pro_jstx],[pro_yyfw],[pin],[js_price],[zk_price],[th_number],[pro_weight_unit],[batch_no])"+ 

		"select pro_model,pro_gg,pro_name,pro_number,[pro_type],[saleprice],[price_hb],"+
		"[pro_s_num],[pro_num],[pro_unit],[pro_price],[pro_supplier],[pro_addr],"+
		"[pro_remark],[sjnum],[yqnum],[yqdate],[pro_secid],[pro_sup_number],[pro_min_num],[pro_max_num],"+
		"[sale_states],[sale_min_price],[sale_max_price],[pro_date],[pro_man],[pro_ms],[pro_jstx],[pro_yyfw],"+
		"[pin],[js_price],[zk_price],[th_number],[pro_weight_unit],[batch_no] from warehouse_del where id="+id;

		String sqlDel = "delete from warehouse_del where id="+id;
		List<String> batchDeleteSQLList = new ArrayList<String>();
		batchDeleteSQLList.add(sql);
		batchDeleteSQLList.add(sqlDel);
		DBConnection db = new DBConnection();
		db.execBatch(batchDeleteSQLList);
	}
	
	/**
	 * 删除时，备份删除的库存信息
	 * @param id
	 */
	public void deleteWarehouse(String id){
		deleteWarehouse(Integer.valueOf(id));
	}
	
	public int getModelCount(String pro_model,String addr){
		String sql = "select count(pro_model) from warehouse where pro_model='"+pro_model
				+"' and  (pro_num > 0 or id in (select oid from o_pic)) and pro_addr='"+addr+"'";
		DBConnection db = new DBConnection();
		return db.getCount(sql);
		
	}
	
	public int deleteNoUseWarehouse(String addr){

		List<Warehouse> warehouseList = getWarehouseNoPic(addr);
		List<String> toDeleteIds = new ArrayList<String>();
		
		List<String> temp = new ArrayList<String>();
		
		for(Warehouse w : warehouseList){
			String pro_model = w.getPro_model();
			// 如果这个型号没有一个数量不为零，或者图片有上传的型号，增加这个型号
			if(getModelCount(pro_model, addr)==0){
				if(temp.contains(pro_model)){
					toDeleteIds.add(w.getId());
				}else{
					temp.add(pro_model);
				}
			}else{
				toDeleteIds.add(w.getId());
			}
		}
		
		for(String id : toDeleteIds){
			deleteWarehouse(id);
		}
		return toDeleteIds.size();
		
	}

	public static void main(String args[]) {
		
	}

}
