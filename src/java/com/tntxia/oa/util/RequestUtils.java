package com.tntxia.oa.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tntxia.web.mvc.WebRuntime;

public class RequestUtils {
	
	public static String getString(HttpServletRequest request,String key){
		String value = org.apache.commons.lang.StringUtils.trim(request.getParameter(key));
		if(value==null){
			value = "";
		}
		return value;
	}
	
	public static Integer getRestrainId(WebRuntime runtime){
		HttpSession session = runtime.getSession();
		Integer restrainId = (Integer)session.getAttribute("restrainId");
		return restrainId;
	}
	
	
	private static List<String> getUserRight(WebRuntime runtime){
		HttpSession session = runtime.getSession();
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		return userRightList;
	}
	
	public static boolean existRight(WebRuntime runtime,String right){
		if(right==null){
			return true;
		}
		List<String> userRightList = getUserRight(runtime);
		return userRightList.contains(right);
	}

}
