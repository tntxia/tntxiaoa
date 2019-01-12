package com.tntxia.oa.login.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.consts.JSONReturnConst;
import com.tntxia.oa.login.service.LoginService;
import com.tntxia.oa.restrain.service.RestrainService;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.dao.DepartmentDao;
import com.tntxia.oa.system.dao.RightLightDao;
import com.tntxia.oa.system.dao.SystemDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.system.entity.Restrain;
import com.tntxia.oa.system.entity.RestrainGP;
import com.tntxia.oa.system.entity.SystemInfo;
import com.tntxia.oa.system.entity.User;
import com.tntxia.oa.util.DateUtil;
import com.tntxia.web.mvc.WebRuntime;

public class LoginAction extends CommonDoAction {
	
	private LoginService loginService = new LoginService();

	private SystemDao systemDao = new SystemDao();

	private DepartmentDao departmentDao = new DepartmentDao();

	private RightLightDao rightDao = new RightLightDao();
	
	private RestrainService restrainService = new RestrainService();
	
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	
	/**
	 * 普通登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> execute(WebRuntime runtime) throws Exception {
		
		if(SystemCache.status!=1){
			return this.errorMsg("系统未初始化成功，请重新启动系统！");
		}

		if (runtime.getParam("user_id") == null) {
			return this.errorMsg("请输入登录名！");
		}
		
		
		String username = runtime.unescape("user_id");
		HttpSession session = runtime.getSession();
		
		String currentDate = DateUtil.getNowString("yyyy-MM-dd HH:mm");

		SystemInfo systemInfo = systemDao.getSystemInfo();
		String q_company = systemInfo.getCompanyName();

		String pwd1 = runtime.unescape("password");
		String ip = runtime.getIP();
		
		Map<String,Object> checkResult = loginService.checkLogin(username, pwd1, ip);
		
		if(!(Boolean)checkResult.get(JSONReturnConst.SUCCESS_FLAG)){
			return checkResult;
		}
		
		List<String> userRightList = new ArrayList<String>();


		User user = (User)checkResult.get("user");
		Integer userId = user.getId();
		String ename = user.getNameEn();
		String role = user.getPosition();
		int restrainId = user.getRestrainId();

		Restrain restrain = rightDao.getRestrain(restrainId);
		
		String restrain_name = restrain.getName();

		
		int departId = user.getDepartmentId();
		Department depart = departmentDao.getDepartment(departId);

		session.setAttribute("userId", String.valueOf(userId));
		session.setAttribute("ecompany", q_company);
		session.setAttribute("Remotehost", ip);
		session.setAttribute("currentDate", currentDate);
		session.setAttribute("ename", ename);
		session.setAttribute("role", role);
		
		session.setAttribute("restrainId", restrainId);
		session.setAttribute("restrain_name", restrain_name);
		
		if(depart!=null){
			session.setAttribute("dept", depart.getName());
			String deptjb = depart.getDepartCode();
			session.setAttribute("deptjb", deptjb);
			
		}
		
		for(String right : rightDao.getRestrainRightList(restrainId)){
			userRightList.add(right);
		}
		
		List<Integer> restrains = restrainService.listIds(String.valueOf(userId));
		for(Integer id : restrains) {
			
			List<String> rightList = rightDao.getRestrainRightList(id);
			
			for(String right : rightList){
				userRightList.add(right);
			}
		}
		
		List<RestrainGP> restrainGPList = rightDao.getRestrainGP(restrain_name);
		
		session.setAttribute("restrainGPList", restrainGPList);
		
		List<String> restrainList = rightDao.getRestrainListById(restrainId);
		if(restrainList!=null){
			userRightList.addAll(restrainList);
		}
		
		session.setAttribute("loginSign", "OK");
		session.setAttribute("username", username);
		session.setAttribute("userRightList", userRightList);
		
		return this.success("lastUrl",session.getAttribute("lastUrl"));
	}
	
}
