package com.tntxia.oa.sale.action.mvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.WebRuntime;

public class SaleDetailDraftAction extends OACommonHandler {
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail(String id) throws Exception{
		String sql="select * from subscribe where id=?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getList(String id) throws Exception{
		String sql = "select * from ddpro where ddid = ? order by id ";
		return dbManager.queryForList(sql, new Object[]{id},true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getUnitList() throws Exception{
		String sql = "select * from punit_type";
		return dbManager.queryForList(sql,true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getAuditFlows(String dept,String role) throws Exception{
		String sql = "select  * from ddsp  where  dept=? and role=?";
		return dbManager.queryForList(sql,new Object[]{dept,role}, true	);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		
		String id = runtime.getParam("id");
		Map<String,Object> root = this.getRoot();
		Map<String,Object> detail = this.getDetail(id);
		root.put("detail", detail);
		String man = (String) detail.get("man");
		
		String dept = this.getDept(runtime);
		String role = this.getRole(runtime);
		
		List list = this.getList(id);
		BigDecimal zprice=BigDecimal.ZERO;
		for(int i=0;i<list.size();i++){
			Map map = (Map) list.get(i);
			Integer num = (Integer) map.get("num");
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			zprice=zprice.add(salejg.multiply(BigDecimal.valueOf(num)));
		}
		
		root.put("proList", this.getList(id));
		root.put("unitList", this.getUnitList());
		root.put("zprice", zprice);
		root.put("viewPurchasingPrice", this.existRight(runtime, "viewPurchasingPrice"));
		root.put("submod", super.existRight(runtime, "submod"));
		
		boolean canDel = false;
		String username = this.getUsername(runtime);
		if(username.equals(man) || super.existRight(runtime, "subdel") ) {
			canDel = true;
		}
		root.put("subdel", canDel);
		root.put("proListSize", list.size());
		root.put("canPrint", "总经办".equals(dept));
		root.put("auditFlows", this.getAuditFlows(dept, role));
		
		root.put("suppliers", SystemCache.suppliers);
		
	}

}
