package com.tntxia.oa.inquiry.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.inquiry.service.InquiryService;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class InquiryAction extends CommonDoAction {
	
	private InquiryService service = new InquiryService();
	

	/**
	 * 通知销售
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception
	 */
	public Map<String, Object> noticeSale(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String id = request.getParameter("id");

		Userinfo userinfo = this.getUserinfo(request);
		try{
			service.noticeSale(id, userinfo);
		}catch(Exception ex){
			return this.errorMsg(ex.toString());
		}
		return this.success();
	}
	
	
	public Map<String,Object> getClientInquiry(WebRuntime runtime) throws Exception{
		String coId = runtime.getParam("coId");
		boolean quoteview = this.existRight(runtime, "r_cus_xj_view");
		String username = this.getUsername(runtime);
		PageBean pageBean = runtime.getPageBean(20);
		return service.getClientInquiry(pageBean,coId,quoteview,username);
	}

}
