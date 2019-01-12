package com.tntxia.oa.system.action.mvc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.entity.Restrain;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 权限组列表
 * @param request
 * @param response
 * @return
 */
public class RestrainListHandler extends OACommonHandler {
	
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

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setRootValue("list", getRestrainList());

	}

}
