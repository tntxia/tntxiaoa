package com.tntxia.oa.member.handler;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.web.mvc.WebRuntime;

public class MemberViewHandler extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private List getAssessmentList(String memberId) throws Exception {
		String strSQLsub = "select * from man_kh where menberid=? order  by id desc";
		return dbManager.queryForList(strSQLsub, new Object[] {memberId},true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAwardList(String memberId) throws Exception {
		String strSQLsub = "select * from man_jf where menberid=? order  by id desc";
		return dbManager.queryForList(strSQLsub, new Object[] {memberId},true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getChangeList(String memberId) throws Exception {
		String strSQLsub = "select * from man_dd where menberid=? order  by id desc";
		return dbManager.queryForList(strSQLsub, new Object[] {memberId},true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getSalaryChangeList(String memberId) throws Exception {
		String strSQLsub = "select * from man_xz where menberid=? order  by id desc";
		return dbManager.queryForList(strSQLsub, new Object[] {memberId},true);
	}

	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id2=runtime.getParam("id");
		String sql="select  * from menber where id=?";
		
		this.putAllRootValue(dbManager.queryForMap(sql, new Object[] {id2},true));
		this.setRootValue("assessmentList", this.getAssessmentList(id2));
		this.setRootValue("awardList", this.getAwardList(id2));
		this.setRootValue("changeList", this.getChangeList(id2));
		this.setRootValue("salaryChangeList", this.getSalaryChangeList(id2));
		
		this.setRootValue("manmod", this.existRight(runtime, "manmod"));
		this.setRootValue("mandel", this.existRight(runtime, "mandel"));
		
	}

}
