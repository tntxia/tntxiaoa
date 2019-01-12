package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.entity.Restrain;
import com.tntxia.oa.system.entity.RestrainGP;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.web.mvc.BaseDao;

public class RightLightDao extends BaseDao{

	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	private DBManager dbManager = this.getDBManager();

	/**
	 * 权限列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<String> getRestrainRightList(int restrainId) throws Exception {

		String strSQL = "select r.name from user_right r,role_right_rel rrr where r.id = rrr.rightId and roleId = ?";
		List list = dbManagerBack.queryForList(strSQL, new Object[] {restrainId},true);
		List<String> res = new ArrayList<String>();
		
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>) list.get(i);
			String name = (String) map.get("name");
			res.add(name);
		}
		return res;
	}

	/**
	 * 权限列表
	 * 
	 * @return
	 */
	public List<Restrain> getRestrainList() {

		DBConnection einfodb = new DBConnection();

		String strSQL = "select * from restrain";

		ResultSet sqlRst = einfodb.executeQuery(strSQL);

		List<Restrain> res = new ArrayList<Restrain>();

		try {
			while (sqlRst.next()) {
				Restrain restrain = new Restrain();
				restrain.setId(sqlRst.getInt("id"));
				restrain.setName(sqlRst.getString("restrain_name"));

				res.add(restrain);
			}

			sqlRst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

	/**
	 * 职位权限列表
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Restrain getRestrain(int id) throws Exception {

		String sql = "select * from restrain where id = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(id);
		
		List list = dbManager.queryForList(sql, new Object[]{id},true);

		Restrain res = new Restrain();
		
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String restrainName = (String)map.get("restrain_name");
			res.setId(id);
			res.setName(restrainName);
		}

		return res;
	}

	public Restrain getRestrain(String name) {

		String sql = "select * from restrain where restrain_name='" + name
				+ "'";

		DBConnection einfodb = new DBConnection();

		Restrain res = new Restrain();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {

				String intadd = StringUtils.safeTrim(rs.getString("intadd"));
				String intview = StringUtils.safeTrim(rs.getString("intview"));
				String cgmod = StringUtils.safeTrim(rs.getString("cgmod"));
				String cgdel = StringUtils.safeTrim(rs.getString("cgdel"));

				res.setIntadd(intadd);
				res.setIntview(intview);
				res.setCgmod(cgmod);
				res.setCgdel(cgdel);
				res.setSubadd(StringUtils.safeTrim(rs.getString("subadd")));
				res.setSubview(StringUtils.safeTrim(rs.getString("subview")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			einfodb.closeRs(rs);
			einfodb.close();
		}
		return res;
	}

	public RestrainGP getRestrainGP(String name, String addr) {

		String sql = "select * from restrain_gp where restrain_name='" + name
				+ "' and pro_ck = '" + addr + "'";

		DBConnection einfodb = new DBConnection();

		RestrainGP res = new RestrainGP();
		ResultSet rs = einfodb.executeQuery(sql);
		try {
			if (rs.next()) {

				String proview_price = StringUtils.safeTrim(rs
						.getString("proview_price"));
				if ("有".equals(proview_price.trim())) {
					res.setPrice(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			einfodb.closeRs(rs);
			einfodb.close();
		}
		return res;
	}
	
	public void updateWarehouseRestrain(String warehouseId,String restrainId,String right) throws Exception{
		int id = dbManager.queryForInt("select id from warehouse_right where warehouse_id = ? and restrain_id = ?", new Object[]{warehouseId,restrainId});
		
		if(id>0){
			dbManager.update("update warehouse_right set restrain_warehouse_right=? where id = ?",new Object[]{right,id});
		}else{
			dbManager.update("insert into warehouse_right(warehouse_id,restrain_id,restrain_warehouse_right) values(?,?,?)",
					new Object[]{warehouseId,restrainId,right});
		}
	}

	@SuppressWarnings("rawtypes")
	public List<RestrainGP> getRestrainGP(String name) throws Exception {

		String sql = "select * from restrain_gp where restrain_name=?";

		List list = dbManager.queryForList(sql, new Object[]{name},true);

		List<RestrainGP> res = new ArrayList<RestrainGP>();
		
		
			for (int i=0;i<list.size();i++) {
				RestrainGP restrain = new RestrainGP();
				Map map = (Map) list.get(i);
				restrain.setWarehouse((String)map.get("pro_ck"));
				String pro_ck = ((String)map.get("pro_ck")).trim();
				restrain.setName(pro_ck);
				String pro_view = ((String)map.get("pro_view")).trim();
				if (pro_view.equals("有")) {
					restrain.setView(true);
					restrain.getRightList().add("pro_view");
				}
				String pro_add = StringUtils.safeTrim((String)map.get("pro_add"), "");
				if(pro_add.equals("有")){
					restrain.setAdd(true);
				}
				String pro_zt = StringUtils.safeTrim((String)map.get("pro_zt"), "");
				if(pro_zt.equals("有")){
					restrain.setUse(true);
					restrain.getRightList().add("pro_zt");
				}
				String proview_price = StringUtils.safeTrim((String)map.get("proview_price"), "");
				if(proview_price.equals("有")){
					restrain.setPrice(true);
					restrain.getRightList().add("price");
				}
				String pro_mod = StringUtils.safeTrim((String)map.get("pro_mod"), "");
				if(pro_mod.equals("有")){
					restrain.setMod(true);
					restrain.getRightList().add("mod");
				}
				String pro_del = StringUtils.safeTrim((String)map.get("pro_del"), "");
				if(pro_del.equals("有")){
					restrain.setDel(true);
					restrain.getRightList().add("del");
				}
				res.add(restrain);
			}
		
		return res;
	}
	
	public List<RestrainGP> getWarehouseRight(String id) throws NumberFormatException, Exception {
		return getWarehouseRight(Integer.parseInt(id));
	}
	
	public List<RestrainGP> getWarehouseRight(int id) throws Exception {

		String sql = "select w.proflname warehouse,warehouse_id warehouseId,restrain_warehouse_right rightNum from warehouse_right r left join profl w on r.warehouse_id = w.id  where restrain_id=?";
		List<Object> res = dbManager.queryForList(sql, new Object[]{id}, RestrainGP.class);
		List<RestrainGP> result = new ArrayList<RestrainGP>();
		for(Object o : res){
			RestrainGP gp = (RestrainGP) o;
			int right = gp.getRightNum();
			if(right%2==1){
				gp.setUse(true);
			}
			if(right%4>=2){
				gp.setAdd(true);
			}
			if(right%8>=4){
				gp.setMod(true);
			}
			if(right%16>=8){
				gp.setDel(true);
			}
			if(right%32>=16){
				gp.setView(true);
			}
			if(right%64>=32){
				gp.setPrice(true);
			}
			result.add(gp);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public List<String> getRestrainListById(int id) throws Exception {

		String strSQL = "select * from restrain where id = ?";

		List list = dbManager.queryForList(strSQL, new Object[] { id },true);
		
		if(list==null || list.size()==0){
			return null;
		}
		
		Map map = (Map)list.get(0);

		List<String> checkList = this.getRestrainMeta();

		List<String> result = new ArrayList<String>();

		if (map != null) {
			for (String check : checkList) {
				String ch = (String) map.get(check);
				if (ch != null && ch.trim().equals("有")) {
					result.add(check);
				}
			}
		}
		return result;
	}
	
	public Map<String,Object> getRestrainMap(Integer id) throws Exception{
		
		String sql = "select * from restrain where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
		
	}
	
	public Map<String,Object> getRestrainMap(String id) throws Exception{
		return getRestrainMap(Integer.parseInt(id));
	}
	
	public List<String> getRestrainMeta() {
		
		String sql = "SELECT TOP 1 * from restrain";
		
		return dbManager.getMetaList(sql, true);
	}
	
	public void delete(int id) throws Exception{
		String sql="delete from restrain where id=?";
		dbManager.update(sql,new Object[]{id});
	}

}
