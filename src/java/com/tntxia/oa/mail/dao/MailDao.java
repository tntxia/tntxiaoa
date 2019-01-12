package com.tntxia.oa.mail.dao;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;

import com.tntxia.date.DateUtil;
import com.tntxia.oa.mail.entity.Mail;

/**
 * 
 * 审批流程
 * 
 * @author tntxia
 * 
 */
public class MailDao {
	
	public int sendMail(Mail mail){
		String curDate = DateUtil.getCurrentDateStr();
		infocrmdb db = null;
		ResultSet rsSid = null;
		int sid = 1;
		try{
			db = new infocrmdb();
			rsSid = db.executeQuery("select max(sid) from getmail");
			if(rsSid.next()){
				sid = rsSid.getInt(1)+1;
			}
			String sql = "insert into sendmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,form_to,mail_to2,mail_to3)"
				+" values('"+mail.getTo()+"','"+mail.getTitle()+"','"+mail.getContent()+"','"+mail.getFrom()+"','"+curDate+"','','','')";
			db.executeUpdate(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rsSid!=null){
				try{
					rsSid.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			db.closeStmt();
			db.close();
		}
		
		return sid;
	}

	
}
