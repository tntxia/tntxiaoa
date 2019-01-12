package com.tntxia.oa.qc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.tntxia.oa.qc.dao.QcDao;
import com.tntxia.oa.qc.entity.QcItem;
import com.tntxia.oa.system.entity.QcCheckItem;

/**
 * 品质管理
 * @author tntxia
 *
 */
public class QualityControlAction extends MultiActionController{
	
	private QcDao qcDao;
	
	public void setQcDao(QcDao qcDao) {
		this.qcDao = qcDao;
	}

	/**
	 * QC 列表
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<QcItem> qcList = qcDao.getQcList();
		result.put("qcList", qcList);
		
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		result.put("checkList", checkList);
		
		
		
		return new ModelAndView("/qc/list", result);
	}
	
	/**
	 * 检查项 列表
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkItemList(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		result.put("checkList", checkList);
		
		
		return new ModelAndView("/qc/listCheckItem", result);
	}
	
	/**
	 * 增加检查项
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkItemAdd(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String name = request.getParameter("name");
		String display = request.getParameter("display");
		QcCheckItem item = new QcCheckItem();
		item.setName(name);
		item.setDisplay(display);
		Map<String, Object> result = new HashMap<String, Object>();
		
		qcDao.addCheckItemList(item);
		
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		
		result.put("checkList", checkList);
		
		return new ModelAndView("/qc/listCheckItem", result);
	}
	
	/**
	 * 跳转到新增品管信息的页面 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toNew(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		result.put("checkList", checkList);
		result.put("tid", System.currentTimeMillis());
		return new ModelAndView("/qc/new", result);
	}
	
	/**
	 * 跳转到新增品管信息的页面 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		String number = request.getParameter("number");
		String model = request.getParameter("model");
		String result = request.getParameter("result");
		String remark = request.getParameter("remark");

		QcItem item = new QcItem();
		item.setNumber(number);
		item.setModel(model);
		item.setResult(result);
		item.setRemark(remark);
		qcDao.add(item);
		int ident = qcDao.getIdent();
		
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		for(QcCheckItem checkItem : checkList){
			String checkName = checkItem.getName();
			String checkResult = request.getParameter(checkName);
			qcDao.addQcCheckResult(ident, checkItem.getId(), checkResult);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("success", true);
		return new ModelAndView("common/result", resultMap);
	}
	
	/**
	 * 跳转到修改品管信息的页面 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toEdit(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		int id = ServletRequestUtils.getIntParameter(request, "id", 0);
		
		Map<String, Object> result = new HashMap<String, Object>();
		QcItem qcItem = qcDao.getQcById(id);
		result.put("qcItem", qcItem);
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		result.put("checkList", checkList);
		
		return new ModelAndView("/qc/toEdit", result);
	}
	
	/**
	 * 修改品管信息 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		int id = ServletRequestUtils.getIntParameter(request, "id", 0);
		QcItem qcItem = new QcItem();
		qcItem.setId(id);
		qcItem.setNumber(request.getParameter("number"));
		qcItem.setModel(request.getParameter("model"));
		qcItem.setDate(request.getParameter("currentDate"));
		
		List<QcCheckItem> checkList = qcDao.getCheckItemList();
		for(QcCheckItem checkItem : checkList){
			String checkName = checkItem.getName();
			String checkResult = request.getParameter(checkName);
			qcDao.updateQcCheckResult(id, checkItem.getId(), checkResult);
		}
		
		qcItem.setResult(request.getParameter("result"));
		qcItem.setRemark(request.getParameter("remark"));
		qcDao.update(qcItem);
		
		return this.list(request, arg1);
	}
	
	/**
	 * 修改品管信息 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {
		
		int id = ServletRequestUtils.getIntParameter(request, "id", 0);
		
		qcDao.delete(id);
		
		return this.list(request, arg1);
	}

}
