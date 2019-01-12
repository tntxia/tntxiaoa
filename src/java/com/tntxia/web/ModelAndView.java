package com.tntxia.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ModelAndView {
	
	private String view;
	
	private Map<String,Object> model;

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}
	
	public abstract void render(HttpServletRequest request,HttpServletResponse response); 
	
	

}
