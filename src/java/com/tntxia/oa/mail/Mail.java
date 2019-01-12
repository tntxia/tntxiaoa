package com.tntxia.oa.mail;

public class Mail {
	
	private int id;
	
	private String mail_to;
	
	private String mail_to2;
	
	private String mail_to3;
	
	private String mail_sub;
	
	private String mail_nr;
	
	private String mail_man;
	
	private String deptjb;
	
	private String dept;
	
	private String mail_datetime;
	
	private String getman;
	
	private String form_datetime;
	
	private String states;
	
	private int sid;
	
	private int type;                     // 邮件类型 1-收邮件  2-发邮件  3-待处理邮件

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}

	public String getMail_to2() {
		return mail_to2;
	}

	public void setMail_to2(String mail_to2) {
		this.mail_to2 = mail_to2;
	}

	public String getMail_to3() {
		return mail_to3;
	}

	public void setMail_to3(String mail_to3) {
		this.mail_to3 = mail_to3;
	}

	public String getMail_sub() {
		return mail_sub;
	}

	public void setMail_sub(String mail_sub) {
		this.mail_sub = mail_sub;
	}

	public String getMail_nr() {
		return mail_nr;
	}

	public void setMail_nr(String mail_nr) {
		this.mail_nr = mail_nr;
	}

	public String getMail_man() {
		return mail_man;
	}

	public void setMail_man(String mail_man) {
		this.mail_man = mail_man;
	}

	public String getDeptjb() {
		return deptjb;
	}

	public void setDeptjb(String deptjb) {
		this.deptjb = deptjb;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMail_datetime() {
		return mail_datetime;
	}

	public void setMail_datetime(String mail_datetime) {
		this.mail_datetime = mail_datetime;
	}

	public String getGetman() {
		return getman;
	}

	public void setGetman(String getman) {
		this.getman = getman;
	}

	public String getForm_datetime() {
		return form_datetime;
	}

	public void setForm_datetime(String form_datetime) {
		this.form_datetime = form_datetime;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	

}
