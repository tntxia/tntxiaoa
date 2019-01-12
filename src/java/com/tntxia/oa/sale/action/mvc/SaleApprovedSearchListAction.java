package com.tntxia.oa.sale.action.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.web.mvc.WebRuntime;

public class SaleApprovedSearchListAction extends HandlerWithHeaderAndLeftbar {
	
	private boolean isStringTheSame(String str1,String str2){
		if(str1==null && str2 != null || str1!=null && str2 == null){
			return false;
		}
		
		if(str1==null && str2==null){
			return true;
		}
		
		return str1.trim().equals(str2.trim());
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		Map<String,Object> root = this.getRoot();

		Map<String,String> param = runtime.getParamMap();
		// 项目路径
		root.put("param", param);
		this.setLeftbar("sale",0,2);
		
		
		List depts = new ArrayList();
		
		for(int i=0;i<SystemCache.departmentList.size();i++){
			Map map = new HashMap();
			Department dept = (Department)SystemCache.departmentList.get(i);
			map.put("name", dept.getName());
			if(dept.getName().equals((String)param.get("depts"))){
				map.put("selected", "selected");
			}
			depts.add(map);
		}
		
		List users = new ArrayList();
		for(int i=0;i<SystemCache.saleUserList.size();i++){
			Map map = new HashMap();
			Map user = (Map)SystemCache.saleUserList.get(i);;
			String name = (String) user.get("name");
			map.put("name", name);
			if(isStringTheSame(name,(String)param.get("man"))){
				map.put("selected", "selected");
			}
			users.add(map);
		}
		
		String[] statusArr = new String[]{"已发运","待出库","已备货","部分退货","全部退货"};
		List statusList = new ArrayList();
		for(String s : statusArr){
			Map map = new HashMap();
			map.put("name", s);
			if(isStringTheSame(s,(String)param.get("statexs"))){
				map.put("selected", "selected");
			}
			statusList.add(map);
		}
		
		List gatherStatusList = new ArrayList();
		String gs = (String)param.get("gatherState");
		Map gsMap1 = new HashMap();
		gsMap1.put("name", "未收款");
		if("1".equals(gs)){
			gsMap1.put("selected", "selected");
		}
		gatherStatusList.add(gsMap1);
		Map gsMap2 = new HashMap();
		gsMap2.put("name", "已收款");
		if("2".equals(gs)){
			gsMap2.put("selected", "selected");
		}
		gatherStatusList.add(gsMap2);
		
		root.put("depts", depts);
		root.put("users", users);
		root.put("statusList", statusList);
		root.put("gatherStatusList", gatherStatusList);
		root.put("hasViewTopRight", existRight(runtime, "sale_view_top_50"));

		
		
	}

}
