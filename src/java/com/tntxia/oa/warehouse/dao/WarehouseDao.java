package com.tntxia.oa.warehouse.dao;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.entity.WarehouseInProduct;

public class WarehouseDao {
	
	public static Logger logger = Logger.getLogger(DBConnection.class);

	private JdbcTemplate jdbcTemplate;

	private DBManager dbManager;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public String getWarehouseTypeById(String id){
		String sql = "select cpro from profl where id = ?";
		return dbManager.getString(sql, new Object[]{id});
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
				String addr = rs.getString("pro_addr");
				if(addr!=null){
					addr = addr.trim();
				}
				warehouse.setPro_addr(addr);
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
				warehouse.setPro_weight_unit(rs.getString("pro_weight_unit"));
			} else {
				return null;
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

	public List<WarehouseInProduct> getInWarehouseListByModel(String model) {
		DBConnection db = new DBConnection();
		String sql = "select p.id,rk.id inId,p.pro_model model,pro_name py,p.remark ,rk.number,p.pro_rk_num,p.pro_num from in_warehouse rk,rkhouse p where rk.id = p.pro_rk_num and rk.states='已入库' and p.pro_model = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(model);
		ResultSet rs = db.executeQuery(sql, params);

		List<WarehouseInProduct> res = new ArrayList<WarehouseInProduct>();
		try {
			while (rs.next()) {
				WarehouseInProduct p = new WarehouseInProduct();
				p.setId(rs.getInt("id"));
				p.setInId(rs.getInt("inId"));
				p.setModel(rs.getString("model"));
				p.setInNumber(rs.getString("number"));
				p.setProductYear(rs.getString("py"));
				p.setRemark(rs.getString("remark"));
				p.setNum(rs.getInt("pro_num"));
				res.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public WarehouseInProduct getWarehouseInProduct(int id) {
		DBConnection db = new DBConnection();
		String sql = "select p.id,rk.id inId,p.pro_model model,pro_name py,p.remark ,rk.number,p.pro_rk_num,p.pro_num from in_warehouse rk,rkhouse p where rk.id = p.pro_rk_num and p.id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		ResultSet rs = db.executeQuery(sql, params);

		WarehouseInProduct res = new WarehouseInProduct();

		try {
			if (rs.next()) {
				res.setId(rs.getInt("id"));
				res.setInId(rs.getInt("inId"));
				res.setModel(rs.getString("model"));
				res.setInNumber(rs.getString("number"));
				res.setProductYear(rs.getString("py"));
				res.setRemark(rs.getString("remark"));
				res.setNum(rs.getInt("pro_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	

	public boolean isAllProductCancel(int id) {

		String sql = "select count(*) from rkhouse where pro_rk_num = ? and status is null";

		DBConnection db = new DBConnection();

		List<Object> params = new ArrayList<Object>();

		params.add(id);

		ResultSet rs = db.executeQuery(sql, params);

		try {
			if (rs.next()) {
				if (rs.getInt(1) == 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		return false;

	}

	public void updateInWarehouseCancel(int id) {
		DBConnection db = new DBConnection();
		String sql = "update in_warehouse set states='已删除' where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		db.executeUpdate(sql, params);
		db.close();
	}

	/**
	 * 把入库产品的状态改为取消
	 * 
	 * @param id
	 */
	public void updateInWarehouseProductCancel(int id, int inId) {

		DBConnection db = new DBConnection();
		String sql = "update rkhouse set status=1 where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		db.executeUpdate(sql, params);

		if (isAllProductCancel(inId)) {
			updateInWarehouseCancel(inId);
		}

		db.close();
	}

	public void updateWarehouse(String id, int num) {
		DBConnection db = new DBConnection();
		String sql = "update warehouse set pro_num=? where id = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		params.add(id);
		db.executeUpdate(sql, params);
		db.close();
	}

	

	/**
	 * 删除仓库产品
	 * 
	 * @param id
	 */
	public void del(int id) {
		DBConnection db = new DBConnection();
		String sql = "delete from warehouse where id=?";
		db.executeUpdate(sql, id);
		db.close();

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
		sqlTail += "0,";

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

	public List<Map<String, Object>> getProflList() throws SQLException {

		DBConnection db = new DBConnection();

		String strSQLpro1 = "select * from profl  order  by id asc";
		ResultSet prs1 = db.executeQuery(strSQLpro1);

		List<Map<String, Object>> warehouseList = new ArrayList<Map<String, Object>>();
		while (prs1.next()) {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("name", prs1.getString("cpro"));
			map.put("id", prs1.getInt("id"));

			warehouseList.add(map);
		}

		prs1.close();
		db.close();
		return warehouseList;
	}


	/**
	 * 
	 * 给出入库的信息增加日志
	 * 
	 */
	public void addWarehouseInOutLog(int orginNum, int changeNum,
			String number, String username, String wid, int pro_id) {
		String sql = "insert into warehouse_in_out_log(number,orgin_num,change_num,final_num,operateTime,operater,wid,rk_id) values(?,?,?,?,getdate(),?,?,?)";
		jdbcTemplate.update(sql, new Object[] { number, orginNum, changeNum,
				orginNum + changeNum, username, wid, pro_id });
	}

	
	/**
	 * 计算在途数
	 * @param model
	 * @param wid
	 * @return
	 */
	public int getComingNum(String model){
		String sql = "select sum(num) as total from cgview where  epro=? and (l_spqk='合同已确认' or l_spqk='待入库')";
		return jdbcTemplate.queryForInt(sql,new Object[]{model});
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getSampleDhList() throws Exception{
		String sql = "select id,spman,number,coname,sub,money,man,datetime from sample_dh where state='待入库'  order by id  Asc";
		return dbManager.queryForList(sql, true);
	}
	
	
	@SuppressWarnings("rawtypes")
	public Map getInWarehouseInfo(String id) throws Exception{
		String sql = "select * from in_warehouse where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getRkList(String id) throws Exception{
		String sql = "select * from rkhouse where pro_rk_num = ? ";
		return dbManager.queryForList(sql, new Object[]{id}, true);
	}
	

}
