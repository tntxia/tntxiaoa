package com.tntxia.oa.login.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tntxia.oa.util.PropertiesUtils;

/**
 * 登录过滤
 * 
 * @author tntxia
 * @date 2015-2-24
 * @lastUpdate 2017-6-24
 */
@WebFilter(filterName="SessionFilter",urlPatterns="/*")
public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");

		// 不过滤的uri
		String[] notFilter = new String[] {"registerApp.do","registerApp.mvc","logout.dispatch","manageslogin.jsp",
				"login.dispatch","login.do", "login",".css", ".gif",".png",".htm",".asp",".jpg","login.jsp" };

		// 请求的uri
		String uri = request.getRequestURI();
		
		String basePath = request.getContextPath();

		// 是否过滤
		boolean notFilterFlag = false;
		for (String s : notFilter) {
			if (uri.toLowerCase().endsWith(s.toLowerCase())) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				notFilterFlag = true;
				break;
			}
		}
		
		if (uri.toLowerCase().endsWith(".js")) {
			if (!uri.toLowerCase().endsWith(".module.js")) {
				notFilterFlag = true;
			}
		}
		
		if (notFilterFlag) {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
			return;
		}
			
		HttpSession session = request.getSession();
		
		// 执行过滤
		// 从session中获取登录者实体
		String username = (String)session.getAttribute("username");
		if (null == username) {
			
			String requestWith = request.getHeader("x-requested-with");
			
			String redirectPath;
			if ("XMLHttpRequest".equals(requestWith)) {
				redirectPath = "/json/noLogin.json";
			} else {
				if(isMobileUserAgent(request)) {
					redirectPath = "/login_mobile.mvc";
				}else {
					redirectPath = "/login.mvc";
				}
			}
			
			session.setAttribute("lastUrl", uri);
			request.getRequestDispatcher(redirectPath).forward(request, response);
			return;
		}
		
		String adminlogin = PropertiesUtils.getProperty("adminlogin");
		
		if(StringUtils.equals(adminlogin, "true")){
			if(uri.indexOf("/system")!=-1){
				String admin = (String)request.getSession().getAttribute("admin");
				if(admin==null){
					response.sendRedirect(basePath+"/system/manageslogin.jsp");
					return;
				}
					
			}
		}
		
		// 如果session中存在登录者实体，则继续
		filterChain.doFilter(request, response);

	}
	
	private boolean isMobileUserAgent(HttpServletRequest request) {
		String agentStr = request.getHeader("User-Agent");
		return agentStr.indexOf("Mobile")!=-1;
	}

}