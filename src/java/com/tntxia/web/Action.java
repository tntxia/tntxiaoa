package com.tntxia.web;

import java.util.Map;

import org.springframework.context.ApplicationContext;

public abstract class Action {
	
	private String name;
	
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public abstract void execute(WebInput input,Map runtime) throws Exception;

}
