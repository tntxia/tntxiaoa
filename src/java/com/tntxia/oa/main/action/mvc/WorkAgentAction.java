package com.tntxia.oa.main.action.mvc;

import java.util.List;

import com.tntxia.oa.common.handler.HandlerWithHeader;
import com.tntxia.oa.system.dao.ApprovalProcessDao;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.web.mvc.WebRuntime;

public class WorkAgentAction extends HandlerWithHeader{
	
	private ApprovalProcessDao approvalProcessDao = new ApprovalProcessDao();
	
	private UserDao userDao = new UserDao();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String name = this.getUsername(runtime);
		
		boolean isPurchasingApprovalMan = approvalProcessDao.isPurchasingApprovalMan(name);
		
		boolean isPurchasingApprovalManFinal = approvalProcessDao.isPurchasingApprovalManFinal(name);
		
		List<Object> userList = userDao.getUserList();
		
		this.setRootValue("isPurchasingApprovalMan", isPurchasingApprovalMan);
		this.setRootValue("isPurchasingApprovalManFinal", isPurchasingApprovalManFinal);
		this.setRootValue("isContactApprovalMan", approvalProcessDao.isContactApprovalMan(name));
		this.setRootValue("isContactApprovalManSec", approvalProcessDao.isContactApprovalManSec(name));
		this.setRootValue("isContactApprovalManFinal", approvalProcessDao.isContactApprovalManFinal(name));
		
		this.setRootValue("isSampleApprovalMan", approvalProcessDao.isSampleApprovalMan(name));
		this.setRootValue("isSampleApprovalManFinal", approvalProcessDao.isSampleApprovalManFinal(name));
		
		this.setRootValue("isArrangeApprovalMan", approvalProcessDao.isArrangeApprovalMan(name));
		this.setRootValue("isArrangeApprovalManFinal", approvalProcessDao.isArrangeApprovalManFinal(name));
		
		this.setRootValue("isOrderApprovalMan", approvalProcessDao.isOrderApprovalMan(name));
		this.setRootValue("isOrderApprovalManFinal", approvalProcessDao.isOrderApprovalManFinal(name));
		
		this.setRootValue("userList", userList);
		
	}

}
