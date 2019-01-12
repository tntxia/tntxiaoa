package com.tntxia.oa.right;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.Right;
import com.tntxia.oa.util.StringUtils;

import infocrmdb.infocrmdb;

public class RightManager {

	public boolean hasRole(String role, String right_name, String right_type) {

		String sql = "select " + right_type
				+ " from role_right where role_name='" + role
				+ "' and right_name='" + right_name + "'";
		boolean result = false;
		infocrmdb db = new infocrmdb();
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				int n = rs.getInt(1);
				if (n == 1)
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeStmt();
			db.close();
		}

		return result;

	}

	public RoleRight getRoleRight(String role_id, String right_name) {

		String sql = "select right_add,right_update,right_delete,right_enable from role_right where role_id='"
				+ role_id + "' and right_name='" + right_name + "'";
		infocrmdb db = new infocrmdb();
		ResultSet rs = db.executeQuery(sql);
		RoleRight roleRight = new RoleRight();
		try {
			if (rs.next()) {

				int right_add = rs.getInt("right_add");
				int right_update = rs.getInt("right_update");
				int right_delete = rs.getInt("right_delete");
				int right_enable = rs.getInt("right_enable");

				roleRight.setRight_add(right_add);
				roleRight.setRight_update(right_update);
				roleRight.setRight_delete(right_delete);
				roleRight.setRight_enable(right_enable);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeStmt();
			db.close();
		}

		return roleRight;

	}

	public boolean updateRoleRight(String role_id, String role_name,
			String right_name, int right_add, int right_update,
			int right_delete, int right_enable) {
		String sql = "select id from role_right where role_id=" + role_id
				+ " and right_name='" + right_name + "'";

		boolean result = false;
		infocrmdb db = new infocrmdb();
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String updateSql = "update role_right set right_add =  "
						+ right_add + ", right_update = " + right_update
						+ ", right_delete = " + right_delete
						+ ", right_enable = " + right_enable
						+ " where role_id=" + role_id + " and right_name='"
						+ right_name + "'";
				db.executeUpdate(updateSql);
			} else {
				String updateSql = "insert into role_right(role_id,role_name,right_name,right_add,right_update,right_delete,right_enable) "
						+ "values("
						+ role_id
						+ ",'"
						+ role_name
						+ "','"
						+ right_name
						+ "',"
						+ right_add
						+ ","
						+ right_update
						+ ", " + right_delete + "," + right_enable + ")";
				db.executeUpdate(updateSql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeStmt();
			db.close();
		}

		return result;
	}

	// 获取权限列表
	public ArrayList<Right> getRightList() {
		String sql = "select * from right_list";

		ArrayList<Right> result = new ArrayList<Right>();
		infocrmdb db = new infocrmdb();
		ResultSet rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				Right right = new Right();
				right.setId(rs.getInt("id"));
				right.setRightName(rs.getString("rightName"));
				right.setRightDisplay(rs.getString("rightDisplay"));
				result.add(right);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeStmt();
			db.close();
		}

		return result;
	}
	
	/**
	 * 获取仓库权限
	 * @param position 职位
	 * @return
	 */
	public List<String> getWarehouses(String position){
		List<String> warehouses = new ArrayList<String>();
		String sql = "select * from restrain_gp where restrain_name='"+position+"' and pro_view = '有'";
		infocrmdb db = new infocrmdb();
		ResultSet rs = db.executeQuery(sql);
		try {
			while(rs.next()){
				warehouses.add(rs.getString("pro_ck"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
		return warehouses;
	}
	
	
	public Restrain getRestrain(String name){
		
		String sql = "select * from restrain where restrain_name='"
				+ name + "'";
		
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			einfodb.closeRs(rs);
			einfodb.close();
		}
		return res;
	}

}