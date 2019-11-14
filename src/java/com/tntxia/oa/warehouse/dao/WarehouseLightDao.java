package com.tntxia.oa.warehouse.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;

import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.oa.warehouse.InWarehouse;
import com.tntxia.oa.warehouse.Rkhouse;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.entity.PicInfo;
import com.tntxia.oa.warehouse.entity.SupplierPrice;
import com.tntxia.oa.warehouse.entity.WarehouseType;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseDao;
import com.tntxia.web.mvc.PageBean;

import infocrmdb.infocrmdb;

public class WarehouseLightDao extends BaseDao {
	
	public static Logger logger = Logger.getLogger(WarehouseLightDao.class);

	private DBManager dbManager = this.getDBManager();
	
	public Map<String, Object> getDetail(String id) throws Exception {
		return this.getDetail(Integer.valueOf(id));
	}

	public Map<String, Object> getDetail(Integer id) throws Exception {
		String sql = "select * from warehouse where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

	/**
	 * 计算在途数
	 * 
	 * @param model
	 * @param wid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getComingList(String model) throws Exception {
		
		String sql = "select id,number ,coname,pay_if,num,cgpro_num from cgview where  epro=? and (l_spqk='合同已确认' or l_spqk='待入库')";
		List list = dbManager.queryForList(sql, new Object[] { model }, true);
		List res = new ArrayList();
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer num = (Integer) map.get("num");
			Integer cgpro_num = (Integer) map.get("cgpro_num");
			Integer numLeft = num;
			if(cgpro_num!=null) {
				numLeft = num - cgpro_num;
			}
			
			map.put("numLeft", numLeft);
			if(numLeft>0) {
				res.add(map);
			}
		}
		return res;
		
	}

	public double getSellPrice(String model) {
		String sql = "select pro_yyfw from warehouse where pro_model='" + model
				+ "'";
		String sellprice = dbManager.getString(sql);
		Double selljg = null;
		if (sellprice != null) {
			try {
				selljg = Double.parseDouble(sellprice);
			} catch (Exception e) {

			}
		}
		return selljg;

	}
	
	public List<PicInfo> getPicIfo(String id) {
		DBConnection db = new DBConnection();
		String strSQLpro = "select id,pic_sm,pic_path from o_pic where oid='"
				+ id + "'";
		ResultSet prs = db.executeQuery(strSQLpro);
		List<PicInfo> res = new ArrayList<PicInfo>();
		try {
			if (prs.next()) {
				PicInfo picInfo = new PicInfo();
				picInfo.setName(prs.getString("pic_sm"));
				picInfo.setPath(prs.getString("pic_path"));
				res.add(picInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return res;
	}
	
	public List<SupplierPrice> getSupplierPriceList(String model) {
		DBConnection db = new DBConnection();
		List<SupplierPrice> res = new ArrayList<SupplierPrice>();
		String strSQLfy = "select id,pro_sup_id,pro_sup_name,pro_sup_price,pro_sup_hb,pro_sup_rate,pro_sup_date from pro_sup where pro_id='"
				+ model + "'";
		ResultSet rs = db.executeQuery(strSQLfy);

		try {
			while (rs.next()) {
				SupplierPrice price = new SupplierPrice();
				price.setId(rs.getInt("id"));
				price.setPro_sup_name(rs.getString("pro_sup_name"));
				price.setPro_sup_id(rs.getInt("pro_sup_id"));
				price.setPro_sup_price(rs.getDouble("pro_sup_price"));
				price.setPro_sup_hb(rs.getString("pro_sup_hb"));
				price.setPro_sup_rate(rs.getString("pro_sup_rate"));
				price.setPro_sup_date(rs.getString("pro_sup_date"));
				res.add(price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 增加一个仓库产品
	 * 
	 * @param warehouse
	 * @throws Exception 
	 */
	public void add(Map<String,Object> params) throws Exception {
		String model = (String)params.get("model");
		String pro_gg = (String)params.get("pro_gg");
		String pro_name = (String)params.get("pro_name");
		String pro_sup_number = (String)params.get("pro_sup_number");
		String dqnum = (String)params.get("dqnum");
		String addr = "现货仓库";
		String pro_remark = (String)params.get("pro_remark");
		
		String sql = "insert into warehouse(pro_model,pro_gg," +
				"pro_name,pro_sup_number,pro_num,pro_addr,pro_remark)" +
				" values(?,?,?,?,?,?,?)";
		
		dbManager.update(sql, new Object[]{model,pro_gg,pro_name,pro_sup_number,
				dqnum,addr,pro_remark});
		
	}
	
	/**
	 * 检查产品是否存在
	 * 
	 * @param pro_model
	 * @param addr
	 * @param pro_name
	 * @param remark
	 * @return
	 */
	public boolean exist(Transaction trans , String pro_model) throws Exception{
		String sql = "select count(*) from warehouse"
				+ " where pro_model= ?  ";

		return trans.exist(sql, new Object[] { pro_model}) ;
	}
	
	/**
	 * 计算待出库
	 * @param model
	 * @param wid
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List getToOutList(String model) throws Exception{
		String sql = "select id,number,coname,send_date,num,s_num from subview where epro=? and (state='已备货' or state='待出库' or state='预收款') ";
		logger.info(sql+":"+model);
		return dbManager.queryForList(sql,new Object[]{model},true);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getSampleList() throws Exception{
		String sql = "select id,spman,number,coname,delivery_terms,man,datetime from sample where state='待入库'  order by id  Asc";
		return dbManager.queryForList(sql, true);
	}
	

	public List<Object> getWarehouseTypeList() throws Exception{
		String sql =  "select  id,number,cpro name from profl  order by cpro desc";
		return dbManager.queryForList(sql,WarehouseType.class);
	}
	
	@SuppressWarnings("rawtypes")
	public List search(String model) throws Exception{
		String sql = "select * from warehouse where pro_model = ?";
		return dbManager.queryForList(sql, new Object[]{model}, true);
	}
	

	/**
	 * 入库单数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int inCount(Map param) throws Exception{

		String orderNumber = (String) param.get("orderNumber");
		String startdate = (String) param.get("startdate");
		String enddate = (String) param.get("enddate");

		String pro_model = (String) param.get("pro_model");
		String g_man = (String) param.get("g_man");
		String coname = (String) param.get("coname");
		String ddnum = (String) param.get("ddnum");
		
		
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
		
		String sql = "select count(*) from in_warehouse "
				+ sqlWhere;
		
		return dbManager.queryForInt(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public List inList(Map param) throws Exception{

		String orderNumber = (String) param.get("orderNumber");
		String startdate = (String) param.get("startdate");
		String enddate = (String) param.get("enddate");
		
		String pro_model = (String) param.get("pro_model");
		String g_man = (String) param.get("g_man");
		String coname = (String) param.get("coname");
		String ddnum = (String) param.get("ddnum");
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
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
		
		String sql = "select  top "+pageBean.getTop()+" id,states,number,supplier,int_types,man,int_date from in_warehouse "
				+ sqlWhere + " order  by id desc";
		
		return dbManager.queryForList(sql, true);
	}
	
	public InWarehouse getInWarehouse(String id) throws Exception {
		String sql = "select  * from in_warehouse where id=?";
		return (InWarehouse) dbManager.queryForObject(sql, new Object[] { id },
				InWarehouse.class);

	}
	
	public Rkhouse getRkhouse(String id) throws Exception {
		String sql = "select * from rkhouse where id = ? ";
		return (Rkhouse) dbManager.queryForObject(sql, new Object[] { id },
				Rkhouse.class);
	}
	@SuppressWarnings("rawtypes")
	public List getRkhouseList(String inWarehouseId) throws Exception {
		String sql = "select * from rkhouse where pro_rk_num = ? ";
		return dbManager.queryForList(sql, new Object[] { inWarehouseId },
				Rkhouse.class);
	}
	

	public void splitRkhouse(String id, String num1,
			String num2) throws Exception {
		
		Rkhouse rkhouse = this.getRkhouse(id);
		String sql = "update rkhouse set pro_num = ? where id = ?";
		
		dbManager.update(sql, new Object[] { Integer.parseInt(num1),id });
		
		sql = "insert into rkhouse(pro_model,pro_name,pro_number,pro_num,pro_unit,pro_price,pro_hb,pro_supplier,pro_addr,pro_id" +
				",pro_datetime,remark,pro_types,pro_sup_number,pro_rate,pro_rk_num,pro_cgy,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		dbManager.update(sql, new Object[]{
				rkhouse.getPro_model(),rkhouse.getPro_name(),rkhouse.getPro_number(),num2,
				rkhouse.getPro_unit(),rkhouse.getPro_price(),rkhouse.getPro_hb(),rkhouse.getPro_supplier(),
				rkhouse.getPro_addr(),rkhouse.getPro_id(),rkhouse.getPro_datetime(),rkhouse.getRemark(),rkhouse.getPro_types(),
				rkhouse.getPro_sup_number(),rkhouse.getPro_rate(),rkhouse.getPro_rk_num(),rkhouse.getPro_cgy(),rkhouse.getStatus()
		});
		
	}
	
	/**
	 * 增加入库产品
	 * @param pro
	 * @param purchasing
	 * @param ddid1
	 * @throws SQLException 
	 */
	@SuppressWarnings("rawtypes")
	public void addRkhouse(Transaction trans,Map pro, Purchasing purchasing,
			String ddid1,int snum) throws SQLException {
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		int id = (Integer)pro.get("id");
		String pro_model1 = (String)pro.get("epro");
		String pro_name1 = (String)pro.get("cpro");
		String pro_name2 = (String)pro.get("pro_number");
		String cgnames = purchasing.getMan();
		String pro_unit1 = (String)pro.get("unit");
		String hb = purchasing.getPurchaseMoneyType();
		double selljg = ((BigDecimal)pro.get("selljg")).doubleValue();

		String supplier = (String)pro.get("supplier");
		String remark = (String)pro.get("remark");
		String pro_addr = (String)pro.get("addr");
		String sql = "insert into rkhouse(pro_model,pro_name,pro_number,"
				+ "pro_num,pro_unit,pro_price,pro_hb,pro_supplier,pro_addr,pro_id,"
				+ "pro_datetime,remark,pro_types,pro_sup_number,pro_rate,pro_rk_num,pro_cgy,status) "
				+ "values('"
				+ pro_model1
				+ "','"
				+ pro_name1
				+ "','"
				+ pro_name2
				+ "','"
				+ snum
				+ "','"
				+ pro_unit1
				+ "','"
				+ selljg
				+ "','"
				+ hb
				+ "','"
				+ purchasing.getSupplier()
				+ "','"
				+ pro_addr
				+ "','"
				+ id
				+ "','"
				+ currentDate
				+ "','"
				+ remark
				+ "','"
				+ purchasing.getNumber()
				+ "','"
				+ supplier
				+ "','"
				+ id
				+ "','" + ddid1 + "','" + cgnames + "',0)";

		trans.update(sql);

	}

	/**
	 * 删除入库单产品
	 * 
	 * @param id
	 */
	public void delRkhouse(Transaction trans,String id) throws Exception {
		
		String sql = "delete from rkhouse where id=?";
		trans.update(sql, new Object[]{id});
		

	}
	
	/**
	 * 检查产品是否存在
	 * 
	 * @param pro_model
	 * @param addr
	 * @param pro_name
	 * @param remark
	 * @return
	 */
	public boolean exist(Transaction trans , String pro_model, String addr, String pro_name,
			String remark) throws Exception{
		String sql = "select count(*) from warehouse"
				+ " where pro_model= ? and pro_addr=?  and pro_name=? and pro_remark=?  ";

		return trans.queryForInt(sql, new Object[] { pro_model, addr,
				pro_name, remark }) > 0;
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
			einfodb.close();
		}

		return warehouse;
	}
	
	/**
	 * 查询仓库产品的信息
	 * 
	 * @param pro_model
	 * @param addr
	 * @param pro_name
	 * @param remark
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map getWarehouseSingle(Transaction trans,String pro_model) throws Exception {
		String sql = "select * from warehouse where pro_model = ?";
		List list = trans.queryForList(sql,new Object[]{
				pro_model
		}, true);
		
		if(list==null || list.size()==0) {
			return null;
		}
		
		return (Map) list.get(0);

	}


	public void updateRkhouseStatus(Transaction trans,int id) throws Exception  {
		String sql = "update rkhouse set status = 1 where id = ?";
		trans.update(sql, new Object[] { id });
	}


	
	public void editInNum(String id, String num) throws Exception{
		String sql = "update rkhouse set pro_num = ? where id = ?";
		dbManager.update(sql, new Object[]{num,id});
	}
	
	public void delInPro(String id) throws Exception{
		
		String sql = "delete from rkhouse where id = ?";
		dbManager.update(sql, new Object[]{id});
	}
	

	@SuppressWarnings("rawtypes")
	public List getRefundList() throws Exception{
		String sql = "select id,number,coname,sub,man,spman,state from th_table_supplier where  state='已批准' or state='部分出库'   order by number desc";
		return dbManager.queryForList(sql, true);
	}

}
