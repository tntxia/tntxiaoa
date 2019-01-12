package com.tntxia.oa.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserLoginListener implements HttpSessionListener,
		HttpSessionAttributeListener {

	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String name = event.getName();
		HttpSession session = event.getSession();
		
		if ("username".equals(name)) {
			List<String> userList = (List<String>) session.getServletContext()
					.getAttribute("userList");
			if (userList == null) {
				userList = new ArrayList<String>();
				session.getServletContext().setAttribute("userList", userList);
			}
			String username = (String) session.getAttribute("username");
			System.out.println("用户" + username + "登陆");
			if (!userList.contains(username) && username != null) {
				userList.add(username);
			}
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("Session 信息被移除");

	}

	
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		String name = event.getName();
		HttpSession session = event.getSession();
		if ("username".equals(name)) {
			List<String> userList = (List<String>) session.getServletContext()
					.getAttribute("userList");
			if (userList == null) {
				userList = new ArrayList<String>();
				session.getServletContext().setAttribute("userList", userList);
			}
			String username = (String) session.getAttribute("username");
			System.out.println("用户" + username + "登陆");
			if (!userList.contains(username) && username != null) {
				userList.add(username);
			}
		}

	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Session创建：" + event.getSession().getId());
	}

	
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		HttpSession session = event.getSession();
		// 时间: 2008-3-25 上午09:41:11
		String username = (String) session.getAttribute("username");
		System.out.println("有人登出：" + username);

		List<String> userList = (List<String>) session.getServletContext()
				.getAttribute("userList");

		if (userList == null) {
			userList = new ArrayList<String>();
			session.getServletContext().setAttribute("userList", userList);
		}

		if (userList.contains(username)) {
			userList.remove(username);
		}
	}

}
