package com.tntxia.oa.warehouse.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.company.service.CompanyService;
import com.tntxia.oa.sale.service.SaleLightService;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseSendAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	private CompanyService companyService = new CompanyService();
	
	private SaleLightService saleService = new SaleLightService();

	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> transportList(WebRuntime runtime) throws Exception {
		
		String username = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);
		boolean trview = this.existRight(runtime, "trview");
		
		String sqlWhere = " where ";
		
		String sql = "select count(*) from transportation";
		
		if (trview) {
			sqlWhere += "sate='发运通知单' or sate='已受理' ";
		} else
			sqlWhere += "sale_man='"
					+ username
					+ "'  and  sate='发运通知单' and  deptjb like '"
					+ deptjb
					+ "%' or sale_man='"
					+ username
					+ "'  and  sate='已受理' ";
		
		int count = dbManager.getCount(sql+sqlWhere);
		
		sql = "select  id,tr_types,sate,invoice,subscribe,coname,transportation,linkman,mbdate,tr_print from transportation";
		
		List list = dbManager.queryForList(sql, true);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	@SuppressWarnings("rawtypes")
	public String getCompanyName(List companyList,Integer companyId) {
		for(int i=0;i<companyList.size();i++) {
			
			Map map = (Map) companyList.get(i);
			Integer id = (Integer) map.get("id");
			if(id.equals(companyId)) {
				return (String) map.get("companyname");
			}
		}
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getSendBillList(WebRuntime runtime) throws Exception{
		
		List companyList = companyService.getList();
		
		String sql = "select *　from send_bill";
		String sqlWhere = " where 1=1";
		
		List list = dbManagerBack.queryForList(sql+sqlWhere+" order by create_time desc", true);
		
		PageBean pageBean = runtime.getPageBean();
		List row = this.getRows(list, pageBean);
		for(int i=0;i<row.size();i++) {
			Map map = (Map) row.get(i);
			Integer companyId = (Integer) map.get("company_id");
			map.put("companyName", getCompanyName(companyList,companyId));
		}
		
		sql = "select count(*)　from send_bill";
		int totalAmount = dbManagerBack.getCount(sql+sqlWhere);
		return this.getPagingResult(list, runtime, totalAmount);
		
	}
	
	public Map<String,Object> createSendBill(WebRuntime runtime) throws Exception{
		String company = runtime.getParam("company");
		String number = NumberFactory.generateNumber("SB");
		String username = this.getUsername(runtime);
		
		String saleId = runtime.getParam("saleId");
		Map<String,Object> saleDetail = saleService.getDetail(saleId);
		String saleNumber = (String) saleDetail.get("number");
		String coname = (String) saleDetail.get("coname");
		String tr_tel = (String) saleDetail.get("tr_tel");
		
		String sql = "insert into send_bill(number,man,create_time,company_id,sale_number,coname,co_tel) values(?,?,now(),?,?,?,?)";
		dbManagerBack.executeUpdate(sql, new Object[] {number,username,company,saleNumber,coname,tr_tel});
		return this.success();
		
	}
	
	public Map<String,Object> delSendBill(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		
		String sql = "delete from send_bill  where id = ?";
		dbManagerBack.executeUpdate(sql, new Object[] {id});
		return this.success();
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getSendBillDetail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "select * from send_bill where id = ?";
		Map<String,Object> detail = dbManagerBack.queryForMap(sql, new Object[] {id}, true);
		
		Integer companyId = (Integer) detail.get("company_id");
		
		Map<String,Object> company = companyService.getDetail(companyId);
		
		detail.put("company", company);
		
		sql = "select * from send_bill_pro where pid = ?";
		
		List list = dbManagerBack.queryForList(sql, new Object[] {id}, true);
		detail.put("list", list);
		return detail;
	}
	
	/**
	 * 更新送货单的销售单号
	 * @param runtime
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> updateSendBillSale(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String saleId = runtime.getParam("saleId");
		Map<String,Object> saleDetail = saleService.getDetail(saleId);
		String number = (String) saleDetail.get("number");
		String coname = (String) saleDetail.get("coname");
		String tr_tel = (String) saleDetail.get("tr_tel");
		String sql = "update send_bill set sale_number=?,coname=?,co_tel=? where id = ?";
		dbManagerBack.update(sql,new Object[] {number,coname,tr_tel,id});
		return this.success();
	}
	
	public Map<String,Object> addSendBillPro(WebRuntime runtime) throws Exception{
		String pid = runtime.getParam("pid");
		String epro = runtime.getParam("epro");
		String cpro = runtime.getParam("cpro");
		String supplier = runtime.getParam("supplier");
		String num = runtime.getParam("num");
		String salejg = runtime.getParam("salejg");
		String remark = runtime.getParam("remark");
		String sql = "insert into send_bill_pro(model,brand,batch,num,price,remark,pid) values(?,?,?,?,?,?,?)";
		dbManagerBack.update(sql,new Object[] {epro,supplier,cpro,num,salejg,remark,pid});
		return this.success();
	}
	
	public Map<String,Object> delSendBillPro(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from send_bill_pro where id = ?";
		dbManagerBack.update(sql,new Object[] {id});
		return this.success();
	}


}
