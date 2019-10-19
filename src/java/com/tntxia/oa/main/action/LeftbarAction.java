package com.tntxia.oa.main.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tntxia.oa.util.CommonAction;
import com.tntxia.web.mvc.WebRuntime;

public class LeftbarAction extends CommonAction  {
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> execute(WebRuntime runtime) throws Exception {
		ServletContext context = runtime.getServletContext();
		
		Map<String,Object> res = new HashMap<String,Object>();
		
		File leftbarDir = new File(context.getRealPath("/WEB-INF/config/leftbar"));
		File[] leftFiles = leftbarDir.listFiles();
		for(File leftbarFile : leftFiles) {
			
			String fileName = leftbarFile.getName();
			String type = FilenameUtils.getBaseName(fileName);
			SAXReader saxReader = new SAXReader();
	        Document document = saxReader.read(leftbarFile);
	        
	        Element root = document.getRootElement();
	        
	        List<Map<String,Object>> bars = new ArrayList<Map<String,Object>>();
	        List list = root.selectNodes("bars/bar");
	        
	        for(int i=0;i<list.size();i++){
	        	Map<String,Object> map = new HashMap<String,Object>();
	        	Element element = (Element) list.get(i);
	        	map.put("text", element.attributeValue("name"));
	        	
	        	List<Map<String,Object>> buttons = new ArrayList<Map<String,Object>>();
	        	List buttonList = element.selectNodes("button");
	        	for(int j=0;j<buttonList.size();j++){
	        		Element el = (Element) buttonList.get(j);
	        		Map<String,Object> buttonMap = new HashMap<String,Object>();
	        		String url = el.attributeValue("url");
	        		String target = el.attributeValue("target");
	        		buttonMap.put("url", url);
	        		buttonMap.put("text", el.getText());
	        		buttonMap.put("target", target);
	        		
	        		buttons.add(buttonMap);
	        	}
	        	map.put("buttons", buttons);
	        	
	        	bars.add(map);
	        }
	        res.put(type, bars);
		}
		
		return res;
	}

}
