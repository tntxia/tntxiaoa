package com.tntxia.oa.system.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.system.dao.RightDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.util.MapUtils;
import com.tntxia.oa.warehouse.dao.WarehouseDao;

/**
 * 
 * 权限管理 控制器
 * 
 * @author tntxia
 *
 */
public class RestrainAction extends CommonAction {

	private RightDao rightDao;
	
	private WarehouseDao warehouseDao;

	public RightDao getRightDao() {
		return rightDao;
	}

	public void setRightDao(RightDao rightDao) {
		this.rightDao = rightDao;
	}
	
	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	public ModelAndView editRestrainById(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		String id1 = request.getParameter("id");
		
		Map<String,Object> restrainMap = rightDao.getRestrainMap(id1);

		if (restrainMap == null) {
			return this.getErrorResult("权限不存在！");
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resultMap", restrainMap);


		return new ModelAndView("system/restrain/editnpunit", result);

	}

	public ModelAndView updateRestrainById(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			UnsupportedEncodingException {

		List<String> meta = rightDao.getRestrainMeta();

		DBConnection db = new DBConnection();

		String id1 = request.getParameter("id");
		
		String r = request.getParameter("restrain_name");
		
		if(r!=null){
			r = r.trim();
		}

		String strSQL = "update restrain set restrain_name = '"+r+"'";

		for (String type : meta) {

			String value = request.getParameter(type);
			if (value != null) {
				value = "有";
			} else {
				value = "无";
			}
			strSQL += "," + type + "='" + value + "'";
		}

		strSQL += " where id='" + id1 + "'";
		boolean t = db.executeUpdate(strSQL);
		if (!t) {
			return exportErrorJSON(response, "更新权限权限表失败");
		}
		
		List<Map<String,Object>> warehouseList = warehouseDao.getProflList();
		for(Map<String,Object> map : warehouseList){
			Integer id = (Integer)map.get("id");
			String name = (String) map.get("name");
			String gpid = request.getParameter("gpid"+id);
			String prozt = request.getParameter("prozt"+id);
			String proadd = request.getParameter("proadd"+id);
			String promod = request.getParameter("promod"+id);
			String prodel = request.getParameter("prodel"+id);
			String proview = request.getParameter("proview"+id);
			String proviewprice = request.getParameter("proviewprice"+id);
			String sql = null;
			List<Object> params = new ArrayList<Object>();
			if(StringUtils.isNullOrEmpty(gpid) || !StringUtils.isNumber(gpid)){
				sql = "insert into restrain_gp(restrain_name,pro_ck,pro_zt,pro_add,"
						+ "pro_mod,pro_del,pro_view,proview_price) "
						+ " values(?,?,?,?,?,?,?,?)";
				
				params.add(r);
				params.add(name);
				if("Y".equals(prozt)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proadd)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(promod)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(prodel)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proview)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proviewprice)){
					params.add("有");
				}else{
					params.add("无");
				}
			}else{
				sql = "update restrain_gp set restrain_name='"+r+"', pro_zt=?,[pro_add]=?,"
						+ "[pro_mod]=?,[pro_del]=?,[pro_view]=?,[proview_price]=? where id=? ";
				
				
				if("Y".equals(prozt)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proadd)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(promod)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(prodel)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proview)){
					params.add("有");
				}else{
					params.add("无");
				}
				if("Y".equals(proviewprice)){
					params.add("有");
				}else{
					params.add("无");
				}
				params.add(gpid);
			}
			db.executeUpdate(sql, params);
		}
		

		return exportCommon(true, new HashMap<String,Object>());

	}
	
	/**
	 * 删除权限组
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public ModelAndView del(HttpServletRequest request,
			HttpServletResponse response) throws NumberFormatException, Exception{
		String id = request.getParameter("id");
		rightDao.delete(Integer.parseInt(id));
		return super.getResult("system/restrain/punit", rightDao.getRestrainList()) ;
	}

}
