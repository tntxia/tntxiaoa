package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.KnowledgeType;

public class KnowledgeDao {

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	public List<KnowledgeType> listTypes() {

		DBConnection einfodb = new DBConnection();

		ResultSet rs = einfodb.executeQuery("select * from km_ty");

		List<KnowledgeType> res = new ArrayList<KnowledgeType>();

		try {
			while (rs.next()) {
				KnowledgeType type = new KnowledgeType();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("km_name"));
				type.setRemark(rs.getString("remark"));
				type.setDate(rs.getTimestamp("km_date"));
				res.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

	public boolean addType(KnowledgeType type) {

		DBConnection einfodb = new DBConnection();

		String strSQL = "insert into km_ty(km_name,remark,km_date) values(?,?,getdate())";

		List<Object> params = new ArrayList<Object>();
		params.add(type.getName());
		params.add(type.getRemark());

		boolean t = einfodb.executeUpdate(strSQL, params);
		return t;
	}
	
	public boolean delTypeById(int id) {

		DBConnection einfodb = new DBConnection();

		String strSQL = "delete from km_ty where id = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(id);
		

		boolean t = einfodb.executeUpdate(strSQL, params);
		return t;
	}

}
