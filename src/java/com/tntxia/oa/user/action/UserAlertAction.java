package com.tntxia.oa.user.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class UserAlertAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager();
	
	private int getPurchaseRefundToAuditCount(String deptjb,String username) throws Exception{
		String sql = "select count(*) from th_table_supplier where  state='待审批' and  deptjb  like  '"+deptjb+"%'   or  state='待复审' and  deptjb  like  '"+deptjb+"%'    or  state='待三审' and  deptjb  like  '"+deptjb+"%'     or    state='待审批' and spman='"+username+"' or state='待复审' and cwman='"+username+"' and fif='是'   or state='待三审' and  firspman='"+username+"' and  firspif='是' or man='"+username+"' and state='待审批' or man='"+username+"' and state='待复审'  or man='"+username+"' and state='待三审'";
		return dbManager.queryForInt(sql);
	}
	
	private int getSaleToAuditCount(String deptjb,String username) throws Exception{
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
	
	public Map<String,Object> execute(WebRuntime runtime) throws Exception{
		
		Map<String,Object> res = new HashMap<String,Object>();
		
		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);
		res.put("username", this.getUsername(runtime));
		
		List<Item> items = new ArrayList<Item>();
		
		items.add(new Item("待审批公告","#",this.getPublicToAuditCount(username)));
		
		items.add(new Item("未读邮件",runtime.getBasePath()+"/mail.mvc",this.getMailCount(username)));
		
		items.add(new Item("待跟进客户数量","#",this.getCustomerFollowCount(username)));
		items.add(new Item("销售待审批数量",runtime.getBasePath()+"/sale/ddgl/approvingList.mvc",this.getSaleToAuditCount(deptjb, username)));
		items.add(new Item("待审批退货","#",this.getRefundToAuditCount(username)));
		items.add(new Item("待审报价单数量","#",this.getQuoteCount(username)));
		items.add(new Item("待审凭证数量","#",this.getCreditDebitCount(username)));
		items.add(new Item("待审批样品","#",this.getSampleToAuditCount(username)));
		
		items.add(new Item("待审采购订单数量",runtime.getBasePath()+"/purchasing/toAudit.mvc",this.getPurchaseCount(username)));
		items.add(new Item("采购退货待审批数量",runtime.getBasePath()+"/purchasing/refund/approving.mvc",this.getPurchaseRefundToAuditCount(deptjb, username)));
		
		res.put("items", items);
		
		return res;
	}
	
	private static class Item{
		
		private String label;
		
		private String url;
		
		private int count;
		
		public Item(String label,String url,int count) {
			this.label = label;
			this.count = count;
			this.url = url;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
		
		

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
		
		
	}


}
