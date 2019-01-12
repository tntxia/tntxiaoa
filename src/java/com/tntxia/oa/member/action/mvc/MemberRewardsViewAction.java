package com.tntxia.oa.member.action.mvc;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class MemberRewardsViewAction extends OACommonHandler{
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql="select   *  from m_jcgl where id=?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		Map<String,Object> detail = this.getDetail(id);
		
		String l_spqk = ((String)detail.get("l_spqk")).trim();
		
		String l_spman = ((String)detail.get("l_spman")).trim();
		String l_fspman = ((String)detail.get("l_fspman")).trim();
		String l_firspman = ((String)detail.get("l_firspman")).trim();
		
		String username = this.getUsername(runtime);
		String forward;
		if(l_spqk.equals("待审批")){
			if(username.equals(l_spman)){
				forward = "dw";
			}else{
				forward = "sw";
			}
		} else if(l_spqk.equals("待二审")){
			if(username.equals(l_fspman)){
				forward = "dtw";
			}else{
				forward = "sw";
			}
		} else if(l_spqk.equals("待三审")){
			if(username.equals(l_firspman)){
				forward = "dfw";
			}else{
				forward = "sw";
			}
		} else if(l_spqk.equals("草拟") || l_spqk.equals("审批不通过")){
			forward = "w";
		}else{
			forward = "sw";
		}
		this.setRootValue("forward", forward);
		
	}

}
