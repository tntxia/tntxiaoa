package com.tntxia.oa.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tntxia.oa.system.entity.UserInfo;
import com.tntxia.web.mvc.WebRuntime;

public class WebUtils {

	public static boolean isLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		return username != null;

	}

	public static boolean isNoLogin(HttpServletRequest request) {
		return !isLogin(request);
	}

	public static UserInfo getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String dept = (String) session.getAttribute("dept");
		String username = (String) session.getAttribute("username");
		String restrain_name = (String) session.getAttribute("restrain_name");
		Integer restrainId = (Integer) session.getAttribute("restrainId");
		String deptjb = (String) session.getAttribute("deptjb");

		UserInfo userInfo = new UserInfo();
		userInfo.setDept(dept);
		userInfo.setDeptjb(deptjb);
		userInfo.setUsername(username);
		userInfo.setRestrain_name(restrain_name);
		if (restrainId != null)
			userInfo.setRestrainId(restrainId);
		return userInfo;
	}

	public static boolean isAdminLogin(HttpServletRequest request) {

		HttpSession session = request.getSession();

		String adminname = (String) session.getAttribute("adminname");
		String username = (String) session.getAttribute("username");
		if ("admin".equals(username)) {
			adminname = username;
			session.setAttribute("adminname", username);
			return true;
		}
		return adminname != null && adminname.equals("admin");

	}

	public static boolean isNoAdminLogin(HttpServletRequest request) {
		return !isAdminLogin(request);
	}

	public static void writeJson(HttpServletResponse response,
			Map<String, Object> result) throws IOException{
		String json = JSON.toJSONString(result);
		response.setContentType("text/json");
		response.getWriter().print(json);
	}
	
	public static void exportSuccessJson(HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", true);
		exportJSON(response,resultMap);
	}
	
	public static void exportErrorJSON(HttpServletResponse response,String errMsg){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", false);
		resultMap.put("msg", errMsg);
		exportJSON(response,resultMap);
	}
	
	public static void exportJSON(HttpServletResponse response,Object o){
		response.setContentType("text/json; charset=gb2312");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(o,SerializerFeature.WriteDateUseDateFormat));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	}
	
	public static ModelAndView getResult(String view,Object obj){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", obj);
		return new ModelAndView(view, result);
	}
	
	public static String getUsername(WebRuntime runtime) {
		return runtime.getSessionStr("username");
	}

}
