package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.UnitType;

public class UnitTypeDao {

	/**
	 * 货币列表
	 * 
	 * @return
	 */
	public List<UnitType> getUnitTypeList() {

		DBConnection einfodb = new DBConnection();

		ResultSet rs=einfodb.executeQuery("select * from punit_type");
		
		List<UnitType> res = new ArrayList<UnitType>();
		
		try {
			while(rs.next()){
				UnitType type = new UnitType();
				type.setId(rs.getInt("id"));
				type.setName(rs.getString("punit_name"));
				res.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

}
