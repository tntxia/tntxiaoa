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

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;

public class LeftbarAction extends CommonDoAction  {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	private String getParentId(String type) throws Exception {
		String sql = "select * from menu where key_name = ?";
		Map<String,Object> menu = dbManager.queryForMap(sql, new Object[] {type}, true);
		return (String) menu.get("id");
	}
	
	@SuppressWarnings("rawtypes")
	private List getChildrenMenus(String parentId) throws Exception {
		String sql = "select * from menu where pid = ? order by order_no";
		return dbManager.queryForList(sql, new Object[] {parentId}, true);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		
		String[] types = new String[] {"warehouse", "purchasing"};
		for(String type: types) {
			String parentId = this.getParentId(type);
			List menuListL1 = this.getChildrenMenus(parentId);
			for(int i=0;i<menuListL1.size();i++) {
				Map<String,Object> menu = (Map<String,Object>) menuListL1.get(i);
				String menuId = (String) menu.get("id");
				List subMenus = this.getChildrenMenus(menuId);
				menu.put("buttons", subMenus);
			}
			res.put(type, menuListL1);
		}
		
		return res;
	}

}
