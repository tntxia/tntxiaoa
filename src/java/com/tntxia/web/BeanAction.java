package com.tntxia.web;

import java.util.Map;

import org.springframework.context.ApplicationContext;

public class BeanAction extends Action {
	
	private ApplicationContext context;
	
	private String refBean;
	
	

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public String getRefBean() {
		return refBean;
	}

	public void setRefBean(String refBean) {
		this.refBean = refBean;
	}

	@Override
	public void execute(WebInput input, Map runtime) throws Exception {
		ActionBeanInterface actionBean = (ActionBeanInterface) context.getBean(refBean);
		actionBean.execute(input, runtime);
	}
	
}
