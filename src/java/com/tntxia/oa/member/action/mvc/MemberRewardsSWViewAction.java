package com.tntxia.oa.member.action.mvc;

import java.util.Map;

import com.tntxia.web.mvc.WebRuntime;

public class MemberRewardsSWViewAction extends MemberRewardsCommonViewAction{

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> detail = this.getDetail(id);
		this.getRoot().putAll(detail);
		
		String fif = (String)detail.get("l_fif");
		String firspif = (String)detail.get("l_firspif");
		
		this.setRootValue("fif", fif.equals("ÊÇ"));
		this.setRootValue("firspif", firspif.equals("ÊÇ"));
		
		this.setRootValue("fydel", this.existRight(runtime, "r_jcsq_del"));
		this.setRootValue("fymod", this.existRight(runtime, "r_jcsq_mod"));
		
		this.setRootValue("firspif", firspif.equals("ÊÇ"));
	
		
	}

}
