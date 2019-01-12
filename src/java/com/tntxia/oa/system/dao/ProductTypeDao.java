package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.entity.ProductType;

public class ProductTypeDao {

	/**
	 * 货币列表
	 * 
	 * @return
	 */
	public List<ProductType> getProductTypeList() {

		DBConnection einfodb = new DBConnection();

		ResultSet rs=einfodb.executeQuery("select * from secprofl");
		
		List<ProductType> res = new ArrayList<ProductType>();
		
		try {
			while(rs.next()){
				ProductType type = new ProductType();
				type.setName(rs.getString("proflname"));
				
				res.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		einfodb.close();

		return res;
	}

}
