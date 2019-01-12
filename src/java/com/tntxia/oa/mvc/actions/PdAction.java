package com.tntxia.oa.mvc.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.warehouse.PdRun;
import com.tntxia.string.EscapeUnescape;

public class PdAction  extends CommonAction  {
	
	private DBManager dbManager;
	
	public DBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public ModelAndView pd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Thread run = new Thread(new PdRun());
		run.start();
		
		return super.exportSuccessJSON(response);
	}
	
	public ModelAndView getNotIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sql = "select distinct pro_model from rkhouse where pro_rk_num in (select id from in_warehouse where states<>'已删除' and (states='已入库' or states='全部入库')  ) and pro_model not in (select distinct pro_model from warehouse)";
		return super.exportJSONObject(response, dbManager.queryForList(sql, true));
	}
	
	public ModelAndView getInWarehouseByModel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String model = EscapeUnescape.unescape(request,"model");
		String sql = "select * from in_warehouse where id in (select pro_rk_num from rkhouse where pro_model=?) and states<>'已删除'";
		return super.exportJSONObject(response, dbManager.queryForList(sql,new Object[]{model}, true));
	}

}
