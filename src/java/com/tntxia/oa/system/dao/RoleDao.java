package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.entity.Role;

public class RoleDao {

	/**
	 * 权限列表
	 * 
	 * @return
	 */
	public List<Role> getRoleList() {

		DBConnection einfodb = new DBConnection();

		String sql = SystemCache.sqlmapping.get("getRoleList");

		ResultSet sqlRst = einfodb.executeQuery(sql);

		List<Role> res = new ArrayList<Role>();

		try {
			while (sqlRst.next()) {
				Role role = new Role();
				role.setId(sqlRst.getInt("id"));
				role.setName(sqlRst.getString("role_name"));
				res.add(role);
			}
			sqlRst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

}
