package com.tntxia.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings({"rawtypes"})
public class DispatcherServlet extends FrameworkServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String,Service> serviceMap = new HashMap<String,Service>();
	
	private ApplicationContext context;

	protected void onRefresh(ApplicationContext context) throws BeansException {

		this.context = context;

		String serviceSettingPath = this.getInitParameter("serviceSetting");
		String path = this.getServletContext().getRealPath(serviceSettingPath);
		initServiceMap(path);
	}
	
	private List<Action> getActions(Element parent){
		List elList = parent.elements();
		List<Action> result = new ArrayList<Action>();
		for(int i=0;i<elList.size();i++){
			Element el = (Element)elList.get(i);
			result.add(ActionFactory.createAction(el,context));
		}
		return result;
	}

	private void initServiceMap(String settingPath) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(settingPath));
			Element root = document.getRootElement();
			List serviceEl = root.selectNodes("service");
			for(int i=0;i<serviceEl.size();i++){
				Element el = (Element) serviceEl.get(i);
				String serviceName = el.selectSingleNode("name").getText().trim();
				Service service = new Service();
				Element actionList = (Element) el.selectSingleNode("actionList");
				service.setActionList(getActions(actionList));
				serviceMap.put(serviceName, service);
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected ModelAndView exportJSONObject(HttpServletResponse response,Object obj){
		
		response.setContentType("text/json; charset=gbk");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	    return null;
	}

	@Override
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String requestUri = (new UrlPathHelper()).getRequestUri(request);
		String basePath = request.getContextPath();
		String serviceName = requestUri.substring(basePath.length()+9,
				requestUri.length());
		WebInput input = new WebInput();
		Map paramMap = request.getParameterMap();
		input.setParamMap(paramMap);
		HttpSession session = request.getSession();
		input.setSession(session);
		String ip = request.getRemoteAddr();
		input.setIp(ip);
		ServletContext servletContext = this.getServletContext();
		input.setServletContext(servletContext);
		Map runtime = new HashMap();
		
		Service service = (Service) serviceMap.get(serviceName);
		
		for(Action action : service.getActionList()){
			action.execute(input, runtime);
		}
		
		Object result =  runtime.get("result");
		this.exportJSONObject(response, result);

	}
}
