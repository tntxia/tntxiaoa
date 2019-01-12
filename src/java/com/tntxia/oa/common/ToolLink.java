package com.tntxia.oa.common;

public class ToolLink {
	
	private String label;
	
	private String action;
	
	

	public ToolLink(String label, String action) {
		super();
		this.label = label;
		this.action = action;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
