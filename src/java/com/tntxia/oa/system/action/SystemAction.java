package com.tntxia.oa.system.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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

import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.system.dao.RightDao;
import com.tntxia.oa.system.dao.RightLightDao;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class SystemAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	private RightLightDao rightDao  = new RightLightDao();
	
	private String getValue(Element el,String prop){
		Node node = el.selectSingleNode(prop);
		if(node==null){
			return "";
		}
		return node.getText();
	}
	
	
	@SuppressWarnings({"rawtypes" })
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes" })
	public List getManagerItems(WebRuntime runtime) throws Exception {
		
		String config = runtime.getRealPath("/WEB-INF/config/manage/manage.xml");
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(config);
       
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
        
		return res;
	}
	
	/**
	 * 权限组列表
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String,Object> listRestrain(WebRuntime runtime){
		
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rightDao.getRestrainList());
		
		return res;
		
	}
	
	public Map<String,Object> addRestrain(WebRuntime runtime) throws SQLException,
			UnsupportedEncodingException {

		List<String> meta = rightDao.getRestrainMeta();
		
		String r = runtime.getParam("restrain_name");
		
		if(r!=null){
			r = r.trim();
		}

		StringBuffer sqlHead = new StringBuffer("insert into restrain(restrain_name");
		
		StringBuffer sqlValues = new StringBuffer(") values(?");
		
		List<Object> params = new ArrayList<Object>();
		params.add(r);

		for (String type : meta) {
			
			sqlHead.append(","+type);
			sqlValues.append(",?");
			
			String value = runtime.getParam(type);
			if (value != null) {
				value = "有";
			} else {
				value = "无";
			}
			params.add(value);
			
		}

		String sql = sqlHead.append(sqlValues.toString()).append(")").toString();
		dbManager.executeUpdate(sql,params);
		
		return this.success();

	}

}
