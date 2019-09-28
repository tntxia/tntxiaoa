package com.tntxia.oa.sale.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.client.service.ClientService;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class SaleSampleAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();

	private ClientService clientService = new ClientService();

	@SuppressWarnings("rawtypes")
	public Map<String, Object> listToReturn(WebRuntime runtime) throws Exception {

		PageBean pageBean = runtime.getPageBean(50);

		String username = this.getUsername(runtime);

		String deptjb = this.getDeptjb(runtime);

		boolean r_sam_view = this.existRight(runtime, "r_sam_view");

		String strSQL = "select count(*) from sample";

		String sqlWhere;

		if (r_sam_view) {
			sqlWhere = " where (state='部分入库' or   state='已发运'  or   state='已出库' or   state='待入库') and deptjb like '"
					+ deptjb + "%'";
		} else
			sqlWhere = " where (state='部分入库' or   state='已发运'  or   state='已出库' or   state='待入库') and  man='" + username
					+ "'";

		int count = dbManager.getCount(strSQL + sqlWhere);

		strSQL = "select top " + pageBean.getTop() + " * from sample";
		List list = dbManager.queryForList(strSQL + sqlWhere, true);

		return this.getPagingResult(list, pageBean, count);
	}

	/**
	 * 增加样品
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> addSample(WebRuntime runtime) throws Exception {

		String coId = runtime.getParam("clientId");
		Map<String, Object> client = clientService.getClient(coId);
		String coname = (String) client.get("coname");
		String man = this.getUsername(runtime);
		String yj1 = runtime.getParam("contact_tel");
		String money1 = "CNY";
		String senddate1 = (String) client.get("co_number");
		String tbyq1 = runtime.getParam("tbyq");
		String datetime1 = DateUtil.getCurrentDateSimpleStr();
		String remarks1 = runtime.getParam("remarks");
		String delivery_terms = runtime.getParam("delivery_terms");
		String delivery_date = runtime.getParam("delivery_date");
		String airport = runtime.getParam("coaddr");
		String linkman = runtime.getParam("contact");
		String fveight = runtime.getParam("fveight");
		String insurance = runtime.getParam("insurance");

		String role = this.getRole(runtime);
		String dept = this.getDept(runtime);
		String deptjb = this.getDeptjb(runtime);

		String sqlddman = "select  * from  sam_sp where dept='" + dept + "' and role='" + role + "'";
		Map<String, Object> sp = dbManager.queryForMap(sqlddman, true);
		if (sp == null) {
			return this.errorMsg("未定义样品审批流程!");
		}

		String dd_man = (String) sp.get("dd_man");
		String fif = (String) sp.get("fif");
		String fspman = (String) sp.get("fspman");
		String number = NumberFactory.generateNumber("SA");
		String strSQL = "insert into sample(number,man,coname,yj,money,habitus,datetime,senddate,tbyq,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,dept,deptjb,delivery_terms,delivery_date,airport,linkman,fveight,insurance,syyj) values(?,?,?,?,?,'样品执行中',?,?,?,?,'未提交',?,'',' ',?,?,'','',?,?,?,?,?,?,?,?,'')";
		dbManager.executeUpdate(strSQL,
				new Object[] { number, man, coname, yj1, money1, datetime1, senddate1, tbyq1, remarks1, dd_man, fif,
						fspman, dept, deptjb, delivery_terms, delivery_date, airport, linkman, fveight, insurance });

		String sql = "select max(id)  from sample";
		int maxId = dbManager.queryForInt(sql);

		return this.success("id", maxId);

	}

	/**
	 * 客户详细
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> detail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from sample where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

	@SuppressWarnings("rawtypes")
	private List getSampleProList(String id) throws Exception {
		String strSQLpro = "select num,unit,salejg,pricehb from sam_pro where ddid='" + id + "'";
		return dbManager.queryForList(strSQLpro, true);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> listPro(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		PageBean pageBean = runtime.getPageBean();

		String sql = "select top " + pageBean.getTop() + " * from sam_pro where ddid = ?";
		List list = dbManager.queryForList(sql, new Object[] { id }, true);
		int count = dbManager.getCount("select count(*) from sam_pro where ddid = ?", new Object[] { id });
		return this.getPagingResult(list, pageBean, count);
	}

	public Map<String, Object> delPro(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "delete from sam_pro where id = ?";
		dbManager.update(sql, new Object[] { id });
		return this.success();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> getSampleAuditFlow(String dept, String role, String id) throws Exception {
		double totle = 0;
		List proList = getSampleProList(id);

		for (int i = 0; i < proList.size(); i++) {

			Map<String, Object> pro = (Map<String, Object>) proList.get(i);
			int num = (Integer) pro.get("num");
			BigDecimal price = (BigDecimal) pro.get("salejg");
			String hb = (String) pro.get("pricehb");
			double rate = SystemCache.getCurrentRate(hb);

			double tprice = num * price.doubleValue() * rate;
			totle = totle + tprice;// 金额

		}

		int zt = (int) totle;

		String sqlddman = "select  * from sam_sp  where  '" + zt + "'>=price_min  and  price_max>='" + zt
				+ "'  and dept='" + dept + "' and role='" + role + "'";

		Map<String, Object> flow = dbManager.queryForMap(sqlddman, true);
		return flow;
	}

	/**
	 * 将样品单提交审批
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> toAudit(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");
		String dept = this.getDept(runtime);
		String role = this.getRole(runtime);

		Map<String, Object> flow = getSampleAuditFlow(dept, role, id);

		if (flow == null) {
			return this.errorMsg("未定义审批流程!");
		}
		String dd_man = (String) flow.get("dd_man");
		String fif = (String) flow.get("fif");
		String fspman = (String) flow.get("fspman");

		String strSQL = "update sample set state='待审批',spman='" + dd_man + "',fif='" + fif + "',cwman='" + fspman
				+ "'   where id='" + id + "'";
		dbManager.update(strSQL);
		return this.success();
	}
	
	/**
	 * 待审订单列表
	 * 
	 * @param request
	 * @param arg1
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listAuditing(WebRuntime runtime) throws Exception {
		
		String basePath = runtime.getBasePath();

		PageBean pageBean = runtime.getPageBean(50);
		
		String username = this.getUsername(runtime);
		
		String deptjb = this.getDeptjb(runtime);
		
		boolean r_sam_view = this.existRight(runtime, "r_sam_view");
		
		String strSQL;
		
		if(r_sam_view){
			strSQL = "select count(*) from sample where state='待审批' and deptjb like '"+deptjb+"%'  or state='待复审' and deptjb like '"+deptjb+"%'";
		}else
			strSQL = "select count(*) from sample where state='待审批' and spman='"+username+"' or state='待复审' and cwman='"+username+"' and fif='是' or man='"+username+"' and state='待审批' or man='"+username+"' and state='待复审'";

		int count = dbManager.getCount(strSQL);
		
		
		 if(r_sam_view){
		strSQL = "select * from sample where state='待审批' and deptjb like '"+deptjb+"%'  or state='待复审' and deptjb like '"+deptjb+"%' order by id desc ";
		}else
		strSQL = "select * from sample where state='待审批' and spman='"+username+"' or state='待复审' and cwman='"+username+"' and fif='是' or man='"+username+"' and state='待审批' or man='"+username+"' and state='待复审' order by id desc ";
		
		List list = dbManager.queryForList(strSQL, true);
		
		List rows = this.getRows(list, pageBean);
		
		for(int i=0;i<rows.size();i++){
			
			Map r = (Map) rows.get(i);
			String state = (String) r.get("state");
			String url="";
			if("待审批".equals(state)){
				String spman = (String) r.get("spman");
				spman = StringUtils.trim(spman);
				if(username.equals(spman)){
					url = "detailView.mvc";
				}else{
					url = "dddman-view.jsp";
				}
				
			}else if("待复审".equals(state)){
				String cwman = (String) r.get("cwman");
				cwman = StringUtils.trim(cwman);
				if(username.equals(cwman)){
					url = "fddd-view.jsp";
				}else{
					url = "dddman-view.jsp";
				}
			}
			r.put("url", basePath+"/sale/ypgl/"+url+"?id="+r.get("id"));
			
			
		}
		
		return this.getPagingResult(list, pageBean, count);

	}

}
