package com.tntxia.oa.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tntxia.oa.system.dao.DepartmentDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.util.CommonAction;

/**
 * 
 * 部门管理 控制器
 * 
 * @author tntxia
 *
 */
public class DepartmentAction extends CommonAction {

	private DepartmentDao departmentDao;

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		List<Object> res = departmentDao.getDepartmentList();
		return getResult("system/department/list",res
				);
	}

	public ModelAndView add(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		
		String departname = request.getParameter("departname");
		String remark = request.getParameter("remark");
		String dept_types = request.getParameter("dept_types");
		String deptCodeFrom = request.getParameter("deptCodeFrom");
		String dept_code = request.getParameter("dept_code");
		String code = deptCodeFrom+dept_code;
		String leader = request.getParameter("leader");
		
		if(departmentDao.existCode(code)){
			return super.getErrorResult("部门编码已存在！");
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departname", departname);
		params.put("remark", remark);
		params.put("type", dept_types);
		params.put("code", code);
		params.put("leader", leader);
		departmentDao.add(params);
		
		return super.getSuccessResult();
	}
	
	public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		
		String id=request.getParameter("id");
		
		if(!departmentDao.exist(id)){
			return super.getErrorResult("部门不存在！");
		}
		
		Department dept = departmentDao.getDepartment(Integer.valueOf(id));
		
		return super.getResult("system/department/edit", dept);
	}
	
	public ModelAndView update(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		
		
		String id1=request.getParameter("id");
		 String departname1=request.getParameter("departname");
		 String remark1=request.getParameter("remark");
		 String leader = request.getParameter("leader");
		 
		 Department dept = new Department();
		 dept.setId(Integer.valueOf(id1));
		 dept.setName(departname1);
		 dept.setRemark(remark1);
		 dept.setLeader(leader);
		 
		departmentDao.update(dept);
		
		
		return super.getSuccessResult();
	}
	
	public ModelAndView del(HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		
		
		String id1=request.getParameter("id");
		
		departmentDao.del(Integer.parseInt(id1));
		
		
		return super.getSuccessResult();
	}

}
