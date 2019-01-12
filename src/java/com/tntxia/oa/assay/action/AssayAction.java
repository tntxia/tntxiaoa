package com.tntxia.oa.assay.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class AssayAction extends CommonDoAction{
	
	private DBManager dbManager = this.getDBManager();
	
	
	/**
	 * 统计入库信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> statistWarehouse(WebRuntime runtime)
			throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		
		String sql = "select top "+pageBean.getTop()+" id,pro_number,pro_model,pro_num,pro_price,pro_num * pro_price as total from warehouse where pro_num>0 ";
		
		
		String sqlCount = "select count(*) from warehouse where pro_num>0 ";
		
		List list = dbManager.queryForList(sql, true);
		
		int count = dbManager.getCount(sqlCount);
		
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	/**
	 * 统计入库信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> statistInWarehouse(WebRuntime runtime)
			throws Exception{
		
		PageBean pageBean = runtime.getPageBean(20);
		
		String sql = "select top "+pageBean.getTop()+" i.number,i.supplier,i.man,i.states,r.pro_model,r.pro_supplier,i.int_date,r.pro_num,r.pro_supplier from in_warehouse i inner join rkhouse r on r.pro_rk_num = i.id ";
		String sqlWhere = "";
		
		String sqlCount = "select count(*) from in_warehouse i inner join rkhouse r on r.pro_rk_num = i.id ";
		
		String model = runtime.getParam("model");
		if(StringUtils.isNotEmpty(model)){
			sqlWhere += " where r.pro_model like '%"+model+"%'";
		}
		
		List list = dbManager.queryForList(sql+sqlWhere, true);
		
		int count = dbManager.getCount(sqlCount+sqlWhere);
		
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List getProductSaleStatist(WebRuntime runtime) throws Exception {
		
		String startdate = runtime.getParam("startdate");
		String enddate = runtime.getParam("enddate");
		
		String sql = "select * from subview where (state='待收款' or state='归档' or state='待发运'  or state='待出库' or state='已发运' or state='订单已批准')  and datetime>='" 
			+ startdate + "' and '" + enddate+"'>=datetime order by epro";
		
		List list = dbManager.queryForList(sql, true);
		
		List res = new ArrayList();
		
		Map<String,Integer> mapping = new HashMap<String,Integer>();
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			String epro = (String) map.get("epro");
			if(epro==null) {
				continue;
			}
			Integer num = (Integer)map.get("num");
			if(mapping.get(epro)==null) {
				mapping.put(epro, num);
			}else {
				Integer oNum = mapping.get(epro);
				mapping.put(epro, oNum+num);
			}
		}
		
		for(String key : mapping.keySet()) {
			Map item = new HashMap();
			item.put("epro", key);
			item.put("num", mapping.get(key));
			res.add(item);
		}
		
		return res;
		
		
	}
	
	/**
	 * 统计销售月度走势图
	 * @param runtime
	 * @return
	 * @throws Exception 
	 */
	public List<BigDecimal> statistSaleMonthTrend(WebRuntime runtime) throws Exception {
		String year =runtime.getParam("year");
		
		List<BigDecimal> res = new ArrayList<BigDecimal>();
		
		for(int i=1;i<=12;i++) {
			String month;
			
			String nextMonth;
			
			if(i==12) {
				month = ""+i;
				nextMonth = "01";
				year = (Integer.parseInt(year)+1)+"";
						
			}else {
				if(i<10) {
					month = "0"+i;
				}else {
					month = ""+i;
				}
				if(i+1<10) {
					nextMonth = "0"+(i+1);
				}else {
					nextMonth = ""+(i+1);
				}
			}
			
			String sql = "select sum(salejg) from subview where datetime>='"+year+"-"+month+"-01' and datetime<'"+year+"-"+nextMonth+"-01'";
			
			BigDecimal total = dbManager.queryForBigDecimal(sql,null);
			if(total==null) {
				total = BigDecimal.ZERO;
			}
			res.add(total);
			
		}
		
		return res;
		
	}

}
