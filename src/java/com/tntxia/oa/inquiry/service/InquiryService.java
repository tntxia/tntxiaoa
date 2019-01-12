package com.tntxia.oa.inquiry.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.jdbc.SQLExecutorInterface;
import com.tntxia.jdbc.Transaction;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.common.service.CommonService;
import com.tntxia.web.mvc.PageBean;

public class InquiryService extends CommonService{
	
	private static Logger logger = Logger.getLogger(InquiryService.class);
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String, Object> getInquiry(SQLExecutorInterface trans, String id)
			throws Exception {
		String sql = "select * from Inquiry where id = ?";
		return trans.queryForMap(sql, new Object[] { id }, true);
	}

	private void updateInquiry(Transaction trans, String id) throws Exception {
		String sql = "update Inquiry_product set pro_states='已完成询价'  where  quoteid=?";
		trans.update(sql, new Object[] { id });
	}

	private void updateInquiryProduct(Transaction trans, String id)
			throws Exception {
		java.text.SimpleDateFormat simplec = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		String cd = simplec.format(new Date());
		String sql = "update Inquiry set states='已完成询价',hf_date=?  where id=?";
		trans.update(sql, new Object[] { cd, id });
	}

	private void sendMail(Transaction trans, String id, Userinfo userinfo)
			throws Exception {

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());

		Map<String, Object> inquiry = this.getInquiry(trans, id);
		String qnumber = (String) inquiry.get("number");
		String salesman = (String) inquiry.get("man");

		String name1 = userinfo.getUsername();
		
		String title = "采购已回复编号为" + qnumber + "询价单," + name1
				+ "最新批示,请查阅客户询价栏目！";

		String sql = "insert into sendmail(mail_to,mail_sub,mail_nr,states,mail_datetime) "
				+ "values(?,?,'','已发送',?)";
		trans.update(sql, new Object[] { salesman, title, currentDate});
	}
	
	public Map<String, Object> getInquiry(String id)
			throws Exception {
		DBManager dbManager = this.getDBManager();

		try {
			return this.getInquiry(dbManager,id);
		}catch(Exception ex){
			logger.error("获取询价单信息失败", ex);
			throw ex;
		}
	}
	
	/**
	 * 通知销售采购完成
	 * @param id
	 * @param userinfo
	 * @throws Exception
	 */
	public void noticeSale(String id,Userinfo userinfo) throws Exception{
		Transaction trans = this.getTransaction();

		try {
			updateInquiry(trans, id);
			updateInquiryProduct(trans, id);
			sendMail(trans, id, userinfo);
			trans.commit();
		} catch (Exception ex) {
			trans.rollback();
			logger.error("询价采购通知销售", ex);
			throw ex;
		} finally {
			trans.close();
		}
	}
	
	private Map<String,Object> getClientDetail(String coId) throws Exception{
		String sql = "select * from client where clientId = ?";
		return dbManager.queryForMap(sql, new Object[]{coId},true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getClientInquiryList(String coname,boolean quoteview,String username) throws Exception{
		String sql;
		if(quoteview){
			sql = "select * from ixjview    where  coname like '%"+ coname+"%'";
		}else
			sql = "select * from ixjview where  man='"+username+"'and  coname like '%"+ coname+"%' ";
		
		return dbManager.queryForList(sql, true);
	}
	
	private int getClientInquiryCount(String coname,boolean quoteview,String username) throws Exception{
		String sql;
		if(quoteview){
			sql = "select count(*) from ixjview    where  coname like '%"+ coname+"%'";
		}else
			sql = "select count(*) from ixjview where  man='"+username+"'and  coname like '%"+ coname+"%' ";
		
		return dbManager.queryForInt(sql);
	}
 	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getClientInquiry(PageBean pageBean,String coId,boolean quoteview,String username) throws Exception{
		
		Map<String,Object> client = this.getClientDetail(coId);
		
		String coname = (String) client.get("coname");
		
		List list = this.getClientInquiryList(coname, quoteview, username);
		int count = this.getClientInquiryCount(coname, quoteview, username);
		return this.getPagingResult(list, pageBean, count);
		
		
		
	}

}
