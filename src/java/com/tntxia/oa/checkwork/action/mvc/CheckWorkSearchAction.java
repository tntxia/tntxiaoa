package com.tntxia.oa.checkwork.action.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tntxia.date.DateUtil;
import com.tntxia.oa.checkwork.service.CheckworkService;
import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.web.mvc.WebRuntime;

public class CheckWorkSearchAction extends HandlerWithHeaderAndLeftbar {
	
	private CheckworkService service = new CheckworkService();

	@Override
	public void init(WebRuntime runtime) throws Exception {
		this.setLeftbar("member", 5, 2);
		String username = this.getUsername(runtime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todayStart = DateUtil.getTodayStart();
		Date tomorrowStart = DateUtil.getTomorrowStart();
		this.setRootValue("records", service.searchCheckworkRecord(username,sdf.format(todayStart),sdf.format(tomorrowStart)));
	}

}
