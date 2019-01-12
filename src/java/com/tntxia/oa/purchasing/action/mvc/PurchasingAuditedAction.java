package com.tntxia.oa.purchasing.action.mvc;

import java.util.Map;

import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.purchasing.dao.TrademarkDao;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.web.mvc.WebRuntime;

public class PurchasingAuditedAction extends HandlerWithHeaderAndLeftbar {
	
	private TrademarkDao trademarkDao = new TrademarkDao();
	
	private UserDao userDao = new UserDao();

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		Map<String,Object> root = this.getRoot();
		
		this.setLeftbar("purchasing", 1, 2);
		
		root.put("trademarks", trademarkDao.trademarkList());
		root.put("purchasingManList", userDao.getPurchasingUserList());
		
		root.put("view_top_record", this.existRight(runtime, "view_top_record"));
	}

}
