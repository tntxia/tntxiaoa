package com.tntxia.oa.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.util.WebUtils;


public class ToManageAction implements Controller {
	
	private String getValue(Element el,String prop){
		Node node = el.selectSingleNode(prop);
		return node.getText();
	}
	
	private void getItems(Map<String,Object> map,List items){
		
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		map.put("items", res);
		for(int i=0;i<items.size();i++){
			Map<String,String> itemMap = new HashMap<String,String>();
			Element element = (Element) items.get(i);
			itemMap.put("name", this.getValue(element, "name"));
			itemMap.put("url", this.getValue(element, "url"));
			itemMap.put("target", this.getValue(element, "target"));
			itemMap.put("option", this.getValue(element, "option"));
			res.add(itemMap);
		}
		
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String config = request.getRealPath("/WEB-INF/config/manage/manage.xml");
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(config);
        @SuppressWarnings("rawtypes")
		List list = document.selectNodes("//manage/*");
        
        List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
        
        for(int i=0;i<list.size();i++){
        	Map<String,Object> map = new HashMap<String,Object>();
        	Element element = (Element) list.get(i);
        	
        	if("row".equalsIgnoreCase(element.getName())){
        		List items = element.selectNodes("item");
            	getItems(map,items);
            	map.put("type", "row");
        	}else if("hr".equalsIgnoreCase(element.getName())){
        		map.put("type", "hr");
        	}
        	
        	res.add(map);
        }
        
		return WebUtils.getResult("system/manageIndex", res);
	}

}
