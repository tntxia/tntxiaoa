package com.tntxia.oa.sale.action.mvc;


import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.web.mvc.RedirectViewHandler;
import com.tntxia.web.mvc.WebRuntime;

public class SaleApprovingViewAction extends RedirectViewHandler {
	
	private SaleLightDao saleDao = new SaleLightDao();
	
	@Override
	public String init(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		Sale sale = saleDao.getSaleById(id);
		String states = sale.getState();
		String man = sale.getSpman();
		String fspman = sale.getCwman();
		String username = runtime.getSessionStr("username");

		String path;
		
		if (states.equals("待审批")) {
			System.out.println("审批人：" + man + ":用户名：" + username);
			if (man.trim().equals(username)) {
				path = "ddd";
			} else {
				path = "sd";
			}
		} else if (states.equals("待复审")) {
			if (fspman.equals(username)) {
				path = "fddd";
			} else {
				path = "dddman";
			}
		} else {
			path = "dddman";
		}
		
		return path;
		
	}

}
