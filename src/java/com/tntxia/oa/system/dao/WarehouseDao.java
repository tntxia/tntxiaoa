package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.db.DBConnection;
import com.tntxia.db.DBUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.dbmanager.rowmapper.BeanRowMapper;
import com.tntxia.oa.warehouse.Warehouse;

public class WarehouseDao {
	
	private JdbcTemplate jdbcTemplate;
	
	private DBManager dbManager;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * 
	 * 列出所有的仓库
	 * @param user
	 * @throws Exception 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public List list() throws Exception{
		
		String sql =  "select  id,number,cpro from profl  order by cpro desc";
		
		return dbManager.queryForList(sql,true);
		
	}
	
	public void delWarehouseProduct(String warehouse){
		String sql="delete from warehouse where pro_addr=?";
		jdbcTemplate.update(sql,new Object[]{warehouse});
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
		
		List<Warehouse> res = getWarehouseList(rs);
		
		DBUtil.closeResultSet(rs);
		
		db.close();
		
		return res;
	}
	
	public int getModelCount(String pro_model,String addr){
		String sql = "select count(pro_model) from warehouse where pro_model='"+pro_model
				+"' and  (pro_num > 0 or id in (select oid from o_pic)) and pro_addr='"+addr+"'";
		DBConnection db = new DBConnection();
		return db.getCount(sql);
		
	}
	
	/**
	 * 删除时，备份删除的库存信息
	 * @param id
	 */
	public void deleteWarehouse(String id){
		
		String backSQL = "insert into warehouse_del select * from warehouse where id="+id;
		String strSQL="delete from warehouse where id='" + id + "'";
		
		List<String> batchDeleteSQLList = new ArrayList<String>();
		batchDeleteSQLList.add(backSQL);
		batchDeleteSQLList.add(strSQL);
		DBConnection db = new DBConnection();
		db.execBatch(batchDeleteSQLList);
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
	
	public boolean exist(String name){
		
		String sql="select count(*) from profl where cpro=?";
		return jdbcTemplate.queryForInt(sql,new Object[]{name})>0;
		
	}
	
	public void add(Map<String,String> params){
		
		String sql="insert into profl(number,proflname,cpro,remark) values(?,?,?,?)";
		jdbcTemplate.update(sql,new Object[]{params.get("number"),params.get("place"),params.get("name"),params.get("remark")});
		
	}
	
	public void update(Map<String,String> params){
		
		String sql="update profl set number=?,proflname=?,cpro=?,remark=? where id=?";
		jdbcTemplate.update(sql,new Object[]{params.get("number"),params.get("place"),params.get("name"),params.get("remark"),params.get("id")});
		
	}
	
	public void delete(int id){
		
		String sql="delete from profl where id=?";
		jdbcTemplate.update(sql,new Object[]{id});
		
	}
	
	public Warehouse getById(String id) throws Exception{
		String sql = "select * from warehouse where id=?";
		return (Warehouse) dbManager.queryForObject(sql,new Object[]{id}, new BeanRowMapper(Warehouse.class));
	}
	
}
