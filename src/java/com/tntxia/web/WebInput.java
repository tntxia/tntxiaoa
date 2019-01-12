package com.tntxia.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@SuppressWarnings("rawtypes")
public class WebInput {
	
	private String ip;
	
	private Map paramMap;
	
	private HttpSession session;
	
	private ServletContext servletContext;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String getParam(String key){
		String[] params = (String[])paramMap.get(key);
		if(params==null || params.length==0){
			return null;
		}
		return params[0];
	}

}
