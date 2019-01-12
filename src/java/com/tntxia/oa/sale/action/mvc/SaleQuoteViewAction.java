package com.tntxia.oa.sale.action.mvc;

import java.util.List;

import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.sale.dao.QuoteDao;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class SaleQuoteViewAction extends OACommonHandler {
		
	private QuoteDao quoteDao = new QuoteDao();

	@SuppressWarnings({ "rawtypes"})
	@Override
	public void init(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		
		List list = quoteDao.getList(id);
		this.getRoot().putAll(quoteDao.getDetail(id));
		this.getRoot().put("list", list);
		
		this.getRoot().put("totalPrice", quoteDao.getTotalPrice(id));
		
		boolean canViewPurchasingPrice = this.canViewPrice(runtime);
		this.getRoot().put("canViewPurchasingPrice", canViewPurchasingPrice);
		
		this.getRoot().put("quotemod", this.existRight(runtime, "quotemod"));
		this.getRoot().put("quotedel", this.existRight(runtime, "quotedel"));
		
		this.getRoot().put("unitList", SystemCache.unitList);
		this.getRoot().put("currentList", SystemCache.currentList);
		this.getRoot().put("productSize", list.size()	);
		
		
	}

}
