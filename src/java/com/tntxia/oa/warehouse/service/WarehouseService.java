package com.tntxia.oa.warehouse.service;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.tntxia.db.DBConnection;
import com.tntxia.jdbc.Transaction;
import com.tntxia.oa.common.service.CommonService;

import com.tntxia.oa.purchasing.dao.PurchasingDao;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.oa.warehouse.HDCompanyAttachment;
import com.tntxia.oa.warehouse.Outhouse;
import com.tntxia.oa.warehouse.Rkhouse;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.dao.WarehouseDao;
import com.tntxia.oa.warehouse.entity.InOutLog;

@Transactional
public class WarehouseService extends CommonService{

	

	private static HashMap<String, String> restrainMap = new HashMap<String, String>();

	private WarehouseDao warehouseDao;

	private PurchasingDao purchasingDao;

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public void setPurchasingDao(PurchasingDao purchasingDao) {
		this.purchasingDao = purchasingDao;
	}

	// 对于退货信息的记录
	public void logWarehouseReturn(String pro_model, String operateMan,
			String remark) {

		String sql = "insert into th_info(pro_model,remark,operateMan,operateTime)"
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
		if (!StringUtils.isEmpty(warehouse.getId())) {
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
	 * 增加出入库记录
	 * 
	 * @param log
	 * @throws SQLException
	 */
	private void addInOutLog(Transaction trans, InOutLog log)
			throws SQLException {
		String id = String.valueOf(System.currentTimeMillis());
		String sql = "insert into warehouse_in_out_log(id,number,orgin_num,change_num,final_num,operateTime,operater,wid,memo) values(?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date(System.currentTimeMillis());
		trans.update(
				sql,
				new Object[] { id, log.getNumber(), log.getOrginNum(),
						log.getChangeNum(), log.getFinalNum(),
						sdf.format(now),
						log.getUsername(), log.getWid(),log.getRemark() });
	}

	private void updateWarehosue(Transaction trans, InOutLog inOut)
			throws SQLException {
		String strSQLw = "update warehouse set pro_num=?  where  id=?";
		trans.update(strSQLw,
				new Object[] { inOut.getFinalNum(), inOut.getWid() });
		addInOutLog(trans, inOut);
	}

	

	/**
	 * 通过产品的型号和用户权限来查询产品信息
	 * 
	 * @return
	 */
	public List<Warehouse> getWarehouseByModelAndRestain(String restrain_name,
			String pro_model) {

		// 新建一个ＪＤＢＣ服务的对象
		DBConnection db = new DBConnection();

		String strSQLh = "select * from restrain_gp where restrain_name='"
				+ restrain_name + "'";
		java.sql.ResultSet sqlRsth = db.executeQuery(strSQLh);

		List<Warehouse> res = new ArrayList<Warehouse>();

		try {
			while (sqlRsth.next()) {

				String pro_ck = sqlRsth.getString("pro_ck").trim();
				String pro_zt = sqlRsth.getString("pro_view").trim();
				if (pro_zt.equals("有")) {
					Map<String, String> warehouseFilterMap = new HashMap<String, String>();
					warehouseFilterMap.put("pro_addr", pro_ck);
					warehouseFilterMap.put("pro_model", pro_model);
					List<Warehouse> warehouseList = getWarehouseList(warehouseFilterMap);
					res.addAll(warehouseList);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (sqlRsth != null) {
				try {
					sqlRsth.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return res;
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
		String sqlLog = "INSERT INTO warehouse_in_out_log(number,orgin_num,change_num,final_num,"
				+ "operateTime,operater,wid,rk_id)"
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

	private List<Warehouse> getWarehouseList(ResultSet rs) {
		List<Warehouse> res = new ArrayList<Warehouse>();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Warehouse> getWarehouseNoPic(String pro_addr) {

		DBConnection db = new DBConnection();
		String sql = "select * from warehouse where pro_num <= 0 and id not in (select oid from o_pic) and pro_addr='"
				+ pro_addr + "' order by pro_model";

		ResultSet rs = db.executeQuery(sql);
		return getWarehouseList(rs);
	}

	/**
	 * 删除时，备份删除的库存信息
	 * 
	 * @param id
	 */
	public void deleteWarehouse(int id) {

		String backSQL = "insert into warehouse_del select * from warehouse where id="
				+ id;
		String strSQL = "delete from warehouse where id='" + id + "'";

		List<String> batchDeleteSQLList = new ArrayList<String>();
		batchDeleteSQLList.add(backSQL);
		batchDeleteSQLList.add(strSQL);
		DBConnection db = new DBConnection();
		db.execBatch(batchDeleteSQLList);
	}

	public void restoreWarehouse(int id) {
		String sql = "insert into warehouse(pro_model,pro_gg,pro_name,pro_number,[pro_type],[saleprice],[price_hb],[pro_s_num],[pro_num],[pro_unit],[pro_price],[pro_supplier],[pro_addr],[pro_remark],[sjnum],[yqnum],[yqdate],[pro_secid],[pro_sup_number],[pro_min_num],[pro_max_num],[sale_states],[sale_min_price],[sale_max_price],[pro_date],[pro_man],[pro_ms],[pro_jstx],[pro_yyfw],[pin],[js_price],[zk_price],[th_number],[pro_weight_unit],[batch_no])"
				+

				"select pro_model,pro_gg,pro_name,pro_number,[pro_type],[saleprice],[price_hb],"
				+ "[pro_s_num],[pro_num],[pro_unit],[pro_price],[pro_supplier],[pro_addr],"
				+ "[pro_remark],[sjnum],[yqnum],[yqdate],[pro_secid],[pro_sup_number],[pro_min_num],[pro_max_num],"
				+ "[sale_states],[sale_min_price],[sale_max_price],[pro_date],[pro_man],[pro_ms],[pro_jstx],[pro_yyfw],"
				+ "[pin],[js_price],[zk_price],[th_number],[pro_weight_unit],[batch_no] from warehouse_del where id="
				+ id;

		String sqlDel = "delete from warehouse_del where id=" + id;
		List<String> batchDeleteSQLList = new ArrayList<String>();
		batchDeleteSQLList.add(sql);
		batchDeleteSQLList.add(sqlDel);
		DBConnection db = new DBConnection();
		db.execBatch(batchDeleteSQLList);
	}

	/**
	 * 删除时，备份删除的库存信息
	 * 
	 * @param id
	 */
	public void deleteWarehouse(String id) {
		deleteWarehouse(Integer.valueOf(id));
	}

	public int getModelCount(String pro_model, String addr) {
		String sql = "select count(pro_model) from warehouse where pro_model='"
				+ pro_model
				+ "' and  (pro_num > 0 or id in (select oid from o_pic)) and pro_addr='"
				+ addr + "'";
		DBConnection db = new DBConnection();
		return db.getCount(sql);

	}

	public int deleteNoUseWarehouse(String addr) {

		List<Warehouse> warehouseList = getWarehouseNoPic(addr);
		List<String> toDeleteIds = new ArrayList<String>();

		List<String> temp = new ArrayList<String>();

		for (Warehouse w : warehouseList) {
			String pro_model = w.getPro_model();
			// 如果这个型号没有一个数量不为零，或者图片有上传的型号，增加这个型号
			if (getModelCount(pro_model, addr) == 0) {
				if (temp.contains(pro_model)) {
					toDeleteIds.add(w.getId());
				} else {
					temp.add(pro_model);
				}
			} else {
				toDeleteIds.add(w.getId());
			}
		}

		for (String id : toDeleteIds) {
			deleteWarehouse(id);
		}
		return toDeleteIds.size();

	}

	public int getCgProNum(String id) {
		return purchasingDao.getPurchasingProductNum(id);
	}
	

	/**
	 * 获取在途数
	 * 
	 * @param model
	 * @param wid
	 * @return
	 */
	public int getComingNum(String model) {
		return warehouseDao.getComingNum(model);
	}

	

	
	@SuppressWarnings("rawtypes")
	public Map getInWarehouseInfo(String id) throws Exception {
		return warehouseDao.getInWarehouseInfo(id);
	}
	
	@SuppressWarnings("rawtypes")
	public List getRkList(String id) throws Exception {
		return warehouseDao.getRkList(id);
	}

}
