package com.tntxia.oa.inquiry.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.inquiry.service.InquiryService;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 利用询价单生成报价单
 * 
 * @author tntxia
 *
 */
public class InquiryToQuoteAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	private InquiryService service = new InquiryService();
	
	@SuppressWarnings("rawtypes")
	private List getPaymentList() throws Exception{
		String sql = "select * from payment_fs";
		return dbManager.queryForList(sql, true);
	}

	@Override
	public void init(WebRuntime runtime)
			throws Exception {
		
		String id = runtime.getParam("id");

		Map<String,Object> inquiry = service.getInquiry(id);
		
		this.putAllRootValue(inquiry);
		
		this.setRootValue("paymentList", getPaymentList());
		this.setRootValue("currentList", SystemCache.currentList);
		this.setRootValue("userList", SystemCache.userList);
		
	}

}
