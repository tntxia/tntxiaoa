package com.tntxia.oa.user.action;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class UserAlertCountAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager();
	
	private int getPurchaseRefundToAuditCount(String deptjb,String username) throws Exception{
		String sql = "select count(*) from th_table_supplier where  state='待审批' and  deptjb  like  '"+deptjb+"%'   or  state='待复审' and  deptjb  like  '"+deptjb+"%'    or  state='待三审' and  deptjb  like  '"+deptjb+"%'     or    state='待审批' and spman='"+username+"' or state='待复审' and cwman='"+username+"' and fif='是'   or state='待三审' and  firspman='"+username+"' and  firspif='是' or man='"+username+"' and state='待审批' or man='"+username+"' and state='待复审'  or man='"+username+"' and state='待三审'";
		return dbManager.queryForInt(sql);
	}
	
	private int getSaleToAuditCount(String deptjb,String username) throws Exception {
		String strSQL = "select count(*) from subscribe where state='待审批' and spman='"
				+ username
				+ "' or state='待复审' and cwman='"
				+ username
				+ "' and fif='是'";
		
		return dbManager.queryForInt(strSQL);
	}
	
	private int getPublicToAuditCount(String username) throws Exception{
		String sql = "select count(*) from pub_table where states='待审批' and spman=?";
		return dbManager.queryForInt(sql,new Object[]{username});
	}
	
	private int getRefundToAuditCount(String username) throws Exception{
		String sql = "select count(*) from th_table where state='待审批' and spman=? or state='待复审' and cwman=? and fif='是'  or state='待三审' and firspman=?";
		return dbManager.queryForInt(sql, new Object[]{username,username,username});
	}
	
	private int getMailCount(String username) throws Exception{
		String sql = "select count(*) from sendmail where mail_to like ?" +
				"  and  form_to  not like ?  or  mail_to2 like ?  and  " +
				"form_to2  not  like ?  or  mail_to3 like ?  and  form_to3 not  like ?";
		String u = "%"+username+"%";
		return dbManager.queryForInt(sql,new Object[]{u,u,u,u,u,u});
	}
	
	private int getQuoteCount(String username) throws Exception{
		String sql = "select count(*) from quote where states='待审批' and spman=? ";
		return dbManager.queryForInt(sql,new Object[]{username});
	}
	
	private int getCreditDebitCount(String username) throws Exception{
		String sql = "select count(*) from credit_debit   where    spman='"
				+ username
				+ "'  and states='待审批' or  fspman='"
				+ username
				+ "'  and states='待复审'";
		return dbManager.queryForInt(sql);
	}
	
	private int getPurchaseCount(String username) throws Exception{
		String sql = "select count(*) from  procure  where   l_spqk='待审批' and   l_spman='"
				+ username
				+ "' or   l_spqk='待复审' and l_fif='是' and   l_fspman='"
				+ username
				+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
				+ username + "'";
		return dbManager.queryForInt(sql);
	}
	
	/**
	 * 跟进客户数量
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	private int getCustomerFollowCount(String username) throws Exception{
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		String cDate=simple.format(new java.util.Date());
		String  sql = "select count(*) from customer_gj where man=? and iftx='是' and txtime<='"+cDate+"'";
		int num = dbManager.getCount(sql,new String[]{username});
		return num;
	}
	
	private int getSampleToAuditCount(String username) throws Exception{
		String strSQL3 = "select count(*) from sample where state='待审批' and spman=? or state='待复审' and cwman=? and fif='是'";
		return dbManager.queryForInt(strSQL3, new Object[]{username,username});
		
	}
	
	private int getSampleToReturnCount(String username) throws Exception {
		String strSQL3 = "select count(*) from samview where state='已发运' and  man=? and num!=pro_snum and pro_r_date<='"+DateUtil.getCurrentDateSimpleStr()+"'";
		return dbManager.queryForInt(strSQL3, new Object[]{username});
		
	}
	
	public int execute(WebRuntime runtime) throws Exception{
		
		Map<String,Object> res = new HashMap<String,Object>();
		
		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);
		res.put("username", this.getUsername(runtime));
		
		return this.getPublicToAuditCount(username) + this.getMailCount(username) + this.getCustomerFollowCount(username) + this.getSaleToAuditCount(deptjb, username)
			+ this.getRefundToAuditCount(username) + this.getQuoteCount(username) + this.getCreditDebitCount(username) + this.getSampleToAuditCount(username)
			+ this.getSampleToReturnCount(username) + this.getPurchaseCount(username) + this.getPurchaseRefundToAuditCount(deptjb, username);

	}

}
