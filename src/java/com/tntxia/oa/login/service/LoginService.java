package com.tntxia.oa.login.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tntxia.oa.admin.dao.AdminDao;
import com.tntxia.oa.consts.JSONReturnConst;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.system.entity.User;

public class LoginService {
	
	private UserDao userDao = new UserDao();
	
	private AdminDao adminDao = new AdminDao();

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	private Map<String,Object> returnErrorMap(String msg){

		Map<String,Object> res = new HashMap<String,Object>();
		res.put(JSONReturnConst.SUCCESS_FLAG, false);
		res.put(JSONReturnConst.ERROR_MSG, "用户不存在");
		return  res;
	}
	
	public Map<String,Object> checkLogin(String name,String password,String ip) throws Exception{
		
		Map<String,Object> res = new HashMap<String,Object>();
		
		// 检查管理员登陆
		if(StringUtils.equals("admin", name)){
			boolean userExist = adminDao.checkUserExist(name);
			if(!userExist){
				return returnErrorMap("用户不存在");
			}
		}else{
			boolean userExist = userDao.checkUserExist(name);
			if(!userExist){
				return returnErrorMap("用户不存在");
			}
			User user = userDao.getUser(name);
			if (user.getErrCount() >= 3) {
				return returnErrorMap("该用户被锁定(三次错误输入密码,将被锁定),请与系统管理员联系!");
			}
			int nameid = user.getId();
			boolean ipbd = user.isIpBind();
			if (ipbd) {
				if (!ip.equals(user.getIp())) {
					return returnErrorMap("该用户被锁定是IP绑定用户，当前登陆的IP与绑定IP不符,请与系统管理员联系!");
				}
			}
			

			if (user.getPassword().equals(password)) {
				// 如果是IP绑定的用户，先检查用户登陆的IP是否正确
				if (ipbd) {
					if (!ip.equals(user.getIp())) {
						return returnErrorMap("用户登陆的IP检验失败，请使用办公电脑登陆！");
					}
				}
				res.put("user", user);
				// 重置登陆错误数
				userDao.resetUserErrCount(nameid);
			} else {
				
				// 将用户的登陆错误次数+1
				userDao.addUserErrorCount(nameid);
				return returnErrorMap("用户登陆的密码错误");
				
			}
			
		}
		
		res.put(JSONReturnConst.SUCCESS_FLAG, true);
		return  res;
		
	}
	
	public boolean existUser(String name,String password) throws Exception{
		if(StringUtils.equals("admin", name)){
			return adminDao.checkUserExist(name, password);
		}else{
			return userDao.checkUserExist(name, password);
		}
	}

}
