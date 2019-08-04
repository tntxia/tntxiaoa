package com.tntxia.oa.mail;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.tntxia.date.DateUtil;
import com.tntxia.db.DBConnection;
import com.tntxia.sqlexecutor.Transaction;

import infocrmdb.infocrmdb;

/**
 * 发内部邮箱
 * @author tntxia
 *
 */
public class SendMail {
	
	public int sendMail(Transaction trans,String title,String content
			,String mail_to,String mail_from) throws Exception{
		String curDate = DateUtil.getCurrentDateStr();
		String sql = "insert into sendmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,form_to,mail_to2,mail_to3)"
				+" values('"+mail_to+"','"+title+"','"+content+"','"+mail_from+"','"+curDate+"','','','')";
		trans.update(sql);
		int sid = trans.queryForInt("select max(sid) from getmail");
		return sid;
	}
	
	public int sendMail(String title,String content
			,String mail_to,String mail_from) throws Exception{
		DBConnection db = new DBConnection();
		Transaction trans = Transaction.createTrans(db.getConnectionObject());
		String curDate = DateUtil.getCurrentDateStr();
		String sql = "insert into sendmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,form_to,mail_to2,mail_to3)"
				+" values('"+mail_to+"','"+title+"','"+content+"','"+mail_from+"','"+curDate+"','','','')";
		trans.update(sql);
		int sid = trans.queryForInt("select max(sid) from getmail");
		trans.commit();
		trans.close();
		return sid;
	}
	
	public void addMail(Mail mail,int type){

		infocrmdb db = null;
		ResultSet rsSid = null;
		
		try{
			db = new infocrmdb();
			String sql = "insert into sendmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,form_to,mail_to2,mail_to3)"
				+" values('"+mail.getMail_to()+"','"+mail.getMail_sub()+"','"+mail.getMail_nr()+"','"+mail.getMail_man()+"','"+mail.getMail_datetime()+"','','','')";
			if(type ==1) 
				sql = "insert into getmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,mail_to2,mail_to3,getman,form_datetime,states,sid)"
						+" values('"+mail.getMail_to()+"','"+mail.getMail_sub()+"','"+mail.getMail_nr()+"','"+mail.getMail_man()+"','"+mail.getMail_datetime()+"','','','"+mail.getGetman()+"','"+mail.getForm_datetime()+"','"+mail.getStates()+"','"+mail.getSid()+"')";
			else if(type==2)
				sql = "insert into sendmail(mail_to,mail_sub,mail_nr,mail_man,mail_datetime,form_to,mail_to2,mail_to3)"
						+" values('"+mail.getMail_to()+"','"+mail.getMail_sub()+"','"+mail.getMail_nr()+"','"+mail.getMail_man()+"','"+mail.getMail_datetime()+"','','','')";
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
	}
	
	public void deleteMail(Mail mail,int type){

		infocrmdb db = null;
		ResultSet rsSid = null;
		
		try{
			db = new infocrmdb();
			
			String sql = "delete from  sendmail where id="+mail.getId();
			if(type==1)
				sql = "delete from  getmail where id="+mail.getId();
			else if(type==3)
				sql = "delete from  handlingmail where id="+mail.getId();
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
	}
	
	public void addMails(ArrayList<Mail> mails,int type){
		for(Mail mail : mails){
			addMail(mail,type);
		}
	}
	
	public void deleteMail(ArrayList<Mail> mails,int type){
		for(Mail mail : mails){
			deleteMail(mail,type);
		}
	}
	
	public Mail getMailById(int id,int type){
		Mail mail = new Mail();
		
		infocrmdb einfodb = new infocrmdb();
		String sql = "select * from getmail where id="+id;
		if(type==2)
			sql = "select * from sendmail where id="+id;
		else if(type==3)
			sql = "select * from handlingmail where id="+id;
		
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				mail.setId(id);
				mail.setDept(rs.getString("dept"));
				mail.setDeptjb(rs.getString("deptjb"));
				if(type==1 || type==3)
					mail.setForm_datetime(rs.getString("form_datetime"));
				if(type==1 || type==3)
				mail.setGetman(rs.getString("getman"));
				mail.setMail_datetime(rs.getString("mail_datetime"));
				mail.setMail_man(rs.getString("mail_man"));
				mail.setMail_nr(rs.getString("mail_nr"));
				mail.setMail_sub(rs.getString("mail_sub"));
				mail.setMail_to(rs.getString("mail_to"));
				mail.setMail_to2(rs.getString("mail_to2"));
				mail.setMail_to3(rs.getString("mail_to3"));
				if(type==1 || type==3)
					mail.setSid(rs.getInt("sid"));
				mail.setStates(rs.getString("states"));
				if(type==3)
					mail.setType(rs.getInt("type"));
				else
					mail.setType(type);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return mail;
	}

}
