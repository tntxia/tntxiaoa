package com.tntxia.oa.purchasing.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.purchasing.entity.Unit;

public class UnitDao {

	/**
	 * 
	 * 获取货币类型的列表
	 * 
	 * @param purchasingId
	 * @return
	 * @throws SQLException
	 */
	public List<Unit> getUnitList() throws SQLException {

		DBConnection db = new DBConnection();

		List<Unit> res = new ArrayList<Unit>();

		try {
			ResultSet hlrs = db.executeQuery("select * from punit_type");
			while (hlrs.next()) {
				int id = hlrs.getInt("id");
				String name = hlrs.getString(2);
				Unit unit = new Unit();
				unit.setId(id);
				unit.setName(name);
				res.add(unit);
			}
			hlrs.close();
		} catch (Exception e) {
		}
		return res;
	}

}
