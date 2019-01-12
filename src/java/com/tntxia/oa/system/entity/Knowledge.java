package com.tntxia.oa.system.entity;

public class Knowledge {
	
	private int id;
	
	private String name;
	
	// 英文名
	private String nameEn;
	
	private String sex;
	
	private String password;
	
	private int departmentId;
	
	private String position;
	
	private String email;
	
	private String emailUser;
	
	private String emailPassword;
	
	private String emailStmp;
	
	private String tel;
	
	private String telHome;
	
	private String ip;
	
	private boolean ipBind;
	
	private double rate;
	
	private int restrainId;
	
	private int errCount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailStmp() {
		return emailStmp;
	}

	public void setEmailStmp(String emailStmp) {
		this.emailStmp = emailStmp;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	

	public String getTelHome() {
		return telHome;
	}

	public void setTelHome(String telHome) {
		this.telHome = telHome;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isIpBind() {
		return ipBind;
	}

	public void setIpBind(boolean ipBind) {
		this.ipBind = ipBind;
	}

	public int getRestrainId() {
		return restrainId;
	}

	public void setRestrainId(int restrainId) {
		this.restrainId = restrainId;
	}

	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}
	
	

}
