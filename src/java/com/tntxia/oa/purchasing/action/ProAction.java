package com.tntxia.oa.purchasing.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.purchasing.form.ProParamBean;
import com.tntxia.oa.purchasing.form.SaveProParamBean;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;

public class ProAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	/**
	 * 获取采购合同的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getProList(@Param("ddid") String ddid, PageBean pageBean) throws Exception {
		
		String sqlWhere = " where ddid = ?";
		String sql = "select top "+pageBean.getTop()+" * from cgpro ";
		List list = dbManager.queryForList(sql + sqlWhere + " order by id",
				new Object[] { ddid }, true);
		sql = "select count(*) from cgpro ";
		int count = dbManager.queryForInt(sql + sqlWhere,
				new Object[] { ddid });
		
		return this.getPagingResult(list, pageBean, count);

	}
	
	/**
	 * 获取采购合同的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> save(SaveProParamBean paramMap) throws Exception {
		String ddid = paramMap.getDdid();
		List<ProParamBean> pros = paramMap.getPros();
		for(ProParamBean pro : pros) {
			Integer id = pro.getId();
			String epro = pro.getEpro();
			String cpro = pro.getCpro();
			String pro_number = pro.getPro_number();
			String num = pro.getNum();
			String unit = pro.getUnit();
			String selljg = pro.getSelljg();
			String money = pro.getMoney();
			String cgpro_ydatetime = pro.getCgpro_ydatetime();
			String remark = pro.getRemark();
			String supplier = pro.getSupplier();
			String rate = pro.getRate();
			
			if (id == null || id == 0) {
				String strSQL1 = "insert into cgpro(ddid,epro,cpro,pro_number,num,unit,selljg,money,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,remark,supplier,rate,sale_supplier,sale_remark,sale_rate,sale_finance) "
						+ "values(?,?,?,?,?,?,?,?,?,'0','',?,?,?,'','','','')";
				dbManager.update(strSQL1, new Object[] {ddid, epro, cpro, pro_number,num,unit,selljg,money,cgpro_ydatetime,remark,supplier,rate});
			} else {
				String strSQL1 = "update cgpro set epro=?,cpro=?,pro_number=?,num=?,unit=?,selljg=?,money=?,cgpro_ydatetime=?,"
						+ "remark=?,supplier=?,rate=? where id = ?";
				dbManager.update(strSQL1, new Object[] {epro, cpro, pro_number, num,unit, selljg, money,cgpro_ydatetime, remark,supplier,rate, id});
			}
		}
		
		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception {
		String sql = "delete from cgpro where id = ?";
		String id = runtime.getParam("id");
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}

}
