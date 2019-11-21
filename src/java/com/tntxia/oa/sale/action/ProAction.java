package com.tntxia.oa.sale.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.sale.form.ProParamBean;
import com.tntxia.oa.sale.form.SaveProParamBean;
import com.tntxia.oa.warehouse.service.WarehouseLightService;
import com.tntxia.web.mvc.WebRuntime;

public class ProAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();
	
	private WarehouseLightService warehouseService = new WarehouseLightService();

	public Map<String, Object> save(SaveProParamBean paramMap) throws Exception {
		
		String ddid = paramMap.getDdid();
		List<ProParamBean> pros = paramMap.getPros();
		
		for(ProParamBean pro : pros) {
			Integer id = pro.getId();
			String pro_name = pro.getCpro();
			String pro_model = pro.getEpro();

			String num = pro.getNum();
			String punit = pro.getPunit();
			String supplier = pro.getSupplier();

			String mpn = pro.getMpn();
			String salejg = pro.getSalejg();
			String pro_remark = pro.getRemark();
			String fypronum = pro.getFypronum();
			String s_tr_date = pro.getS_tr_date();

			if (id == null || id == 0) {
				String strSQL = "insert into ddpro(fypronum,supplier,epro,cpro,num,unit,salejg,s_tr_date,remark,mpn,ddid,PRICEHB) values(?,?,?,?,?,?,?,?,?,?,?,'CNY')";
				dbManager.executeUpdate(strSQL, new Object[] { fypronum, supplier, pro_model, pro_name, num, punit, salejg,
						s_tr_date, pro_remark, mpn, ddid });
			} else {
				String sql = "update ddpro set fypronum=?,supplier=?,epro=?,cpro=?,num=?,unit=?,salejg=?,s_tr_date=?,remark=?,mpn=? where  id=?";
				dbManager.executeUpdate(sql, new Object[] { fypronum, supplier, pro_model, pro_name, num, punit, salejg,
						s_tr_date, pro_remark, mpn, id });
			}
		}

		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception {
		String sql = "delete from ddpro where id = ?";
		String id = runtime.getParam("id");
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	/**
	 * 从仓库中导入
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> pushInFromWarehouse(Map<String,Object> paramMap) throws Exception {
		
		String ddid = (String) paramMap.get("ddid");
		
		Object data = paramMap.get("data");
		
		List<Integer> ids = new ArrayList<Integer>();
		
		if (data instanceof Map) {
			Map map = (Map) data;
			ids.add((Integer)map.get("id"));
		} else {
			List list = (List) data;
			for(int i=0;i<list.size();i++) {
				Map map = (Map) list.get(i);
				ids.add((Integer)map.get("id"));
			}
		}
		
		for(Integer wid : ids) {
			
			Map<String,Object> p = warehouseService.getDetail(wid);
			
			String pro_model = (String) p.get("pro_model");
			Integer pro_num = ((BigDecimal) p.get("pro_num")).intValue();
			String pro_number = (String) p.get("pro_number");
			
			String pro_name = (String) p.get("pro_name");
			
			String punit = (String) p.get("pro_unit");
			String pro_supplier = (String) p.get("pro_sup_number");
			String qhb = (String) p.get("price_hb");
			
			BigDecimal pro_price = (BigDecimal) p.get("pro_price");
			if(pro_price==null) {
				pro_price = BigDecimal.ZERO;
			}
			String pro_remark = (String) p.get("pro_remark");
			String pro_yyfw = (String) p.get("pro_yyfw");
			String pro_jz = (String) p.get("sjnum");
			String pro_mz=(String) p.get("yqnum");
			String pro_cc=(String) p.get("sale_min_price");
			String pro_cd=(String) p.get("sale_max_price");
			
			String sql="insert into ddpro(ddid,epro,num,fypronum,unit, cpro,rale_types,rale,supplier,pricehb,salejg,selljg,money,fyproall,wid,x_no,remark,s_num,s_c_num,s_tr_date,fy_states,p_check,money2,mpn,pro_jz,pro_mz,pro_cc,pro_cd,th_num,hl) "
			   		+" values(?,'" + pro_model + "','" + pro_num +"','"+pro_number+"','" + punit + "','" +pro_name+ "','','0','" 
			   + pro_supplier + "','" + qhb + "',?,?,'"+qhb+"','no','" + wid + "','',?,'0','0','','待发运','',?,'','"+pro_jz+"','"+pro_mz+"','"+pro_cc+"','"+pro_cd+"',0,1)";
			dbManager.update(sql,new Object[] {ddid,pro_price,pro_price,pro_yyfw, pro_remark});
		}
		
		return this.success();
		
	}

}
