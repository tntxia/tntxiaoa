package com.tntxia.oa.finance.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.tntxia.dbmanager.DBManager;
import com.tntxia.jdbc.Transaction;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.OACommonHandler;

import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 跳转到增加往来帐目凭证的页面
 * 
 * @param request
 * @param arg1
 * @return
 * @throws Exception
 */
public class CreditViewHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		 
	}

}
