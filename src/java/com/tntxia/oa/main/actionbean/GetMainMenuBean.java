package com.tntxia.oa.main.actionbean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.dbmanager.datasource.DefaultDataSource;
import com.tntxia.web.ActionBeanInterface;
import com.tntxia.web.WebInput;

public class GetMainMenuBean implements ActionBeanInterface {
	
	protected boolean existRight(WebInput input,String right){
		
		HttpSession session = input.getSession();
		
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		
		if(right==null){
			return true;
		}
		
		return userRightList.contains(right);
	}
	
	private DBManager getSystemDBManager(String configFile) throws DocumentException, ClassNotFoundException{
		
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(configFile);
        Element el = (Element) document.selectSingleNode("config/systemdb");
        String systemdbPath = el.getTextTrim();
        DefaultDataSource datasource = new DefaultDataSource();
        datasource.setDriverClassName("org.h2.Driver");
        datasource.setUsername("sa");
        datasource.setPassword("sa");
        datasource.setUrl("jdbc:h2:"+systemdbPath);
        return new DBManager(datasource);
        
	}

	@Override
	public void execute(WebInput input, Map runtime) throws Exception {
		
		ServletContext servletContext = input.getServletContext();
		String configFile = servletContext.getRealPath("/WEB-INF/config/config.xml");
		
		Integer restrainId = (Integer) input.getSession().getAttribute("restrainId");
		
		DBManager dbManager = this.getSystemDBManager(configFile);
		
		String sql = "select *¡¡from menu where id in (select menu_id from restrain_menu where restrain_id = ?) order by id ";
		
		List list = dbManager.queryForList(sql, new Object[]{restrainId},true);
        
        runtime.put("result", list);

	}

}
