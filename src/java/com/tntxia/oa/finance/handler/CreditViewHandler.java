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
 * ��ת������������Ŀƾ֤��ҳ��
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
