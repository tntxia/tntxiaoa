package com.tntxia.oa.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.system.entity.RestrainGP;
import com.tntxia.web.mvc.PageBean;

public class CommonAction extends MultiActionController {

	protected ModelAndView getSuccessResult(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return new ModelAndView("common/result", result);
	}
	
	protected ModelAndView getErrorResult(String message){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		result.put("message", "<font size='2' color='#FF0000'>"+message+"</font>");
		return new ModelAndView("common/result", result);
	}
	
	protected ModelAndView getResult(String view,Object obj){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", obj);
		return new ModelAndView(view, result);
	}
	
	protected ModelAndView getResult(String view){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", null);
		return new ModelAndView(view, result);
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> beanToMap(Object object){
		String json = JSON.toJSONString(object);
		return (Map<String,Object>)JSON.parse(json);
	}
	
	
	
	protected ModelAndView exportJSON(HttpServletResponse response,Map<String,Object> resultMap){
		resultMap.put("success", true);
		response.setContentType("text/json; charset=gbk");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(resultMap));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	    return null;
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
	
	protected ModelAndView exportErrorJSON(HttpServletResponse response,String errMsg){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", false);
		resultMap.put("msg", errMsg);
		response.setContentType("text/json; charset=gbk");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(resultMap));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	    return null;
	}
	
	protected ModelAndView exportSuccessJSON(HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", true);
		response.setContentType("text/json; charset=gbk");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(resultMap));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	    return null;
	}
	
	protected ModelAndView exportSuccessJSON(HttpServletResponse response,String key,Object value){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", true);
		resultMap.put(key, value);
		response.setContentType("text/json; charset=gbk");
		PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSON.toJSONString(resultMap));  
	        
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	    return null;
	}
	
	protected Map<String,Object> getSuccessJSON(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", true);
	    return resultMap;
	}
	
	protected ModelAndView exportCommon(Boolean success,Map<String,Object> model){
		model.put("success", success);
		return new ModelAndView("common/result",model);
	}
	
	@SuppressWarnings("unchecked")
	protected List<String> getRightList(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (List<String>) session.getAttribute("userRightList");
	}
	
	protected boolean existRight(HttpServletRequest request,String right){
		if(right==null){
			return true;
		}
		List<String> userRightList = getUserRight(request);
		return userRightList.contains(right);
	}
	
	protected String getUsername(HttpServletRequest request){
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		return username;
	}
	
	protected Userinfo getUserinfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		Userinfo userinfo = new Userinfo();
		String username = (String) session.getAttribute("username");
		String dept = (String) session.getAttribute("dept");
		String deptjb = (String) session.getAttribute("deptjb");
		String role = (String) session.getAttribute("role");
		userinfo.setDept(dept);
		userinfo.setRole(role);
		userinfo.setDeptjb(deptjb);
		userinfo.setUsername(username);
		
		return userinfo;
	}
	
	@SuppressWarnings({ "unchecked"})
	protected List<String> getUserRight(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<String> userRightList = (List<String>) session.getAttribute("userRightList");
		return userRightList;
	}
	
	/**
	 * 是否存在仓库的权限
	 * @param request
	 * @param right
	 * @param warehouse
	 * @return
	 */
	protected boolean existGPRight(HttpServletRequest request,String right,String warehouse){
		
		if(right==null){
			return true;
		}
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<RestrainGP> restrainGPList = (List<RestrainGP>) session.getAttribute("restrainGPList");
		
		for(RestrainGP r : restrainGPList){
			
			if(StringUtils.equals(warehouse, r.getWarehouse()) 
					&& r.getRightList().contains(right)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否存在仓库的权限
	 * @param request
	 * @param right
	 * @param warehouse
	 * @return
	 */
	protected boolean existGPRight(HttpServletRequest request,String right){
		
		if(right==null){
			return true;
		}
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<RestrainGP> restrainGPList = (List<RestrainGP>) session.getAttribute("restrainGPList");
		for(RestrainGP r : restrainGPList){
			
			if(r.getRightList().contains(right)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected List<RestrainGP> getGPRight(HttpServletRequest request){
		HttpSession session = request.getSession();
		List<RestrainGP> restrainGPList = (List<RestrainGP>) session.getAttribute("restrainGPList");
		return restrainGPList;
	}
	
	
	
	public PageBean getPageBean(HttpServletRequest request,int defaultPageSize){
		int page = 1;
		if(StringUtils.isNotEmpty(request.getParameter("page"))){
			page = Integer.valueOf(request.getParameter("page"));
			if(page<1){
				page = 1;
			}
		}
		int pageSize = defaultPageSize;
		if(StringUtils.isNotEmpty(request.getParameter("pageSize"))){
			pageSize = Integer.valueOf(request.getParameter("pageSize"));
			if(pageSize<1){
				pageSize = defaultPageSize;
			}
		}
		PageBean pageBean = new PageBean();
		pageBean.setPage(page);
		pageBean.setPageSize(pageSize);
		return pageBean;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getRows(List list,PageBean pageBean){
		List rows = new ArrayList();
		int page = pageBean.getPage();
		int pageSize = pageBean.getPageSize();
		for(int i=(page-1)*pageSize;i<page*pageSize&&i<list.size();i++){
			rows.add(list.get(i));
		}
		return rows;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getCrudResult(List list, PageBean pageBean,int totalAmount){
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", this.getRows(list, pageBean));
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());
		res.put("totalAmount", totalAmount);
		return res;
	}

}
