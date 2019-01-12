package com.tntxia.web;

import org.dom4j.Element;
import org.springframework.context.ApplicationContext;

public class ActionFactory {
	
	private static final String ACTION_TYPE_BEAN = "bean";
	
	public static Action createAction(Element el,ApplicationContext context){
		String name = el.selectSingleNode("name").getText();
		String type = el.selectSingleNode("type").getText();
		
		if(ACTION_TYPE_BEAN.equals(type)){
			BeanAction action = new BeanAction();
			action.setName(name);
			action.setType(type);
			String refBean =  el.selectSingleNode("refBean").getText();
			action.setContext(context);
			action.setRefBean(refBean);
			return action;
		}
		
		return null;
	}

}
