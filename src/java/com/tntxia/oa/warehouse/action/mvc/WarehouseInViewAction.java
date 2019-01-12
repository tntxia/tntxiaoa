package com.tntxia.oa.warehouse.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseInViewAction extends OACommonHandler {

	private DBManager dbManager = this.getDBManager("default");

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getDetail(String id) throws Exception {
		String sql = "select  * from in_warehouse where id=?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

	@SuppressWarnings("rawtypes")
	private List proList(String id) throws Exception {
		String sql = "select * from rkhouse where pro_rk_num = ? ";
		return dbManager.queryForList(sql, new Object[] { id }, true);
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public void init(WebRuntime runtime)
			throws Exception {

		String id = runtime.getParam("id");
		this.putAllRootValue(this.getDetail(id));

		List proList = this.proList(id);

		String role = this.getRole(runtime);

		BigDecimal allTotalPrice = BigDecimal.ZERO;
		for (int i = 0; i < proList.size(); i++) {
			Map pro = (Map) proList.get(i);
			BigDecimal price = (BigDecimal) pro.get("pro_price");
			Integer num = (Integer) pro.get("pro_num");

			if (role.equals("总经理")) {
				
				BigDecimal totalPrice = price.multiply(BigDecimal
						.valueOf(num));
				allTotalPrice = allTotalPrice.add(totalPrice);
				pro.put("totalPrice", totalPrice);
			} else {
				pro.put("pro_price", "");
			}

		}
		if (role.equals("总经理")) {
			this.setRootValue("allTotalPrice", allTotalPrice);
		}

		this.setRootValue("proList", proList);

	}

}
