package com.tntxia.oa.assay.action;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	 * 获取该型号产品的采购价
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	private BigDecimal getSellPrice(String model) throws Exception{
		String sql = "select pro_price from warehouse where pro_model = ?";
		return dbManager.queryForBigDecimal(sql, new Object[]{model});
		
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
	
	@SuppressWarnings("rawtypes")
	private Map<String,BigDecimal> getPriceMap(Integer id) throws Exception {
		
		Map<String,BigDecimal> res = new HashMap<String,BigDecimal>();
		
		List proList = dbManager.queryForList("select num,salejg,epro from ddpro where ddid=?",new Object[]{id}, true);
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalPurchasePrice = BigDecimal.ZERO;
		BigDecimal totalSendPrice = BigDecimal.ZERO;
		BigDecimal totalSendPurchasePrice = BigDecimal.ZERO;
		
		for(int i=0;i<proList.size();i++){
			Map map = (Map) proList.get(i);
			Integer num = (Integer) map.get("num");
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			String model = (String) map.get("epro");
			BigDecimal purchasePrice = this.getSellPrice(model);
			if(num!=null && num>0){
				
				totalPrice = totalPrice.add(BigDecimal.valueOf(num).multiply(salejg));
				if(purchasePrice!=null){
					totalPurchasePrice = totalPurchasePrice.add(purchasePrice.multiply(BigDecimal.valueOf(num)));
				}
			}
			Integer sendNum = (Integer) map.get("s_num");
			if(sendNum!=null && sendNum>0){
				totalSendPrice = totalSendPrice.add(BigDecimal.valueOf(sendNum).multiply(salejg));
				totalSendPurchasePrice = totalSendPurchasePrice.add(BigDecimal.valueOf(sendNum).multiply(purchasePrice));
			}
		}
		
		res.put("totalPrice", totalPrice);
		res.put("totalPurchasePrice", totalPurchasePrice);
		res.put("totalSendPrice", totalSendPrice);
		res.put("totalSendPurchasePrice", totalSendPurchasePrice);
		return res;
	}
	
	private Map<String,Object> getGatherMap(String number) throws Exception{
		String sql="select sum(smoney) as sk,sum(i_man) as iman from gathering  where orderform=?";
		return dbManager.queryForMap(sql, new Object[]{number},true);
		
	}
	
	/**
	 * 销售订单统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getStatistic(WebRuntime runtime)
			throws Exception {

		PageBean pageBean = runtime.getPageBean(50);

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);
		nf.setMinimumFractionDigits(4);

		String fpnum = runtime.getParam("fpnum");
		String coname = runtime.getParam("coname");
		String model = runtime.getParam("model");
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		String man = runtime.getParam("man");
		String supplier = runtime.getParam("supplier");

		boolean subviewRight = this.existRight(runtime, "subview");
		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);

		String sqlWhere = "";

		if (supplier != null && supplier.trim().length() > 0) {
			sqlWhere += " and id in (select ddid from ddpro where supplier like '%"
					+ supplier + "%')";
		}
		if (sdate != null && sdate.trim().length() > 0) {
			sqlWhere += " and datetime>='" + sdate + "'";
		}
		if (edate != null && edate.trim().length() > 0) {
			sqlWhere += " and '" + edate + "'>=datetime ";
		}

		if (man != null && man.trim().length() > 0) {
			sqlWhere += " and man='" + man + "' ";
		}

		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%' ";
		}

		if (StringUtils.isNotEmpty(fpnum)) {
			sqlWhere += " and number like '%" + fpnum + "%' ";
		}
		
		if(StringUtils.isNotEmpty(model)){
			sqlWhere += " and id in (select ddid from ddpro where epro like '%"
					+ model + "%')";
		}

		String sql = "";
		if (subviewRight) {
			sql = "select count(*) from subscribe where (state='预收款' or state='已发运' or state='待出库' or state='订单已批准') and deptjb like '"
					+ deptjb + "%'";

		} else
			sql = "select count(*) from subscribe where (state='预收款' or state='已发运' or state='待出库' or state='订单已批准') and deptjb like '"
					+ deptjb + "%' and  man='" + username + "'";

		int count = dbManager.queryForInt(sql + sqlWhere);

		if (subviewRight) {
			
			sql = "select top "
					+ pageBean.getTop()
					+ " id,number,coname,man,send_date,datetime from subscribe  where (state='预收款' or state='已发运' or state='待出库' or state='订单已批准')   and deptjb like '"
					+ deptjb + "%' " + sqlWhere + "  order  by  number desc";
		} else {
			sql = "select top "
					+ pageBean.getTop()
					+ "  id,number,coname,man,send_date,datetime from subscribe where (state='预收款' or state='已发运' or state='待出库' or state='订单已批准')  and  man='"
					+ username + "' " + sqlWhere + " order  by  number desc";
		}
		List list = dbManager.queryForList(sql, true);

		List rows = this.getRows(list, pageBean);

		for (int i = 0; i < rows.size(); i++) {

			Map map = (Map) rows.get(i);
			Integer id = (Integer) map.get("id");
			Map<String,BigDecimal> priceMap = getPriceMap(id);
			map.putAll(priceMap);
			String number = (String) map.get("number");
			Map<String,Object> gatherMap = getGatherMap(number);
			map.putAll(gatherMap);
			
		}
		
		pageBean.setTotalAmount(count);

		return this.getPagingResult(rows, pageBean);

	}
	
	/**
	 * 销售订单统计
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getStatisticTotal(WebRuntime runtime)
			throws Exception {
		boolean subviewRight = this.existRight(runtime, "subview");
		String deptjb = this.getDeptjb(runtime);
		String username = this.getUsername(runtime);
		String sql = "";
		
		String sqlWhere = "";
		
		String fpnum = runtime.getParam("fpnum");
		String coname = runtime.getParam("coname");
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		String man = runtime.getParam("man");
		String supplier = runtime.getParam("supplier");
		
		String model = runtime.getParam("model");

		if (supplier != null && supplier.trim().length() > 0) {
			sqlWhere += " and id in (select ddid from ddpro where supplier like '%"
					+ supplier + "%')";
		}
		if (sdate != null && sdate.trim().length() > 0) {
			sqlWhere += " and datetime>='" + sdate + "'";
		}
		if (edate != null && edate.trim().length() > 0) {
			sqlWhere += " and '" + edate + "'>=datetime ";
		}

		if (man != null && man.trim().length() > 0) {
			sqlWhere += " and man='" + man + "' ";
		}

		if (StringUtils.isNotEmpty(coname)) {
			sqlWhere += " and coname like '%" + coname + "%' ";
		}

		if (StringUtils.isNotEmpty(fpnum)) {
			sqlWhere += " and number like '%" + fpnum + "%' ";
		}
		
		if(StringUtils.isNotEmpty(model)){
			sqlWhere += " and id in (select ddid from ddpro where epro like '%"
					+ model + "%')";
		}
		
		if(subviewRight){
			sql = "select money,sum(num*salejg) ys from subview where (state='预收款' or state='已发运' or state='待出库' or state='订单已批准')   and deptjb like '"
					+ deptjb
					+ "%'"
					+ sqlWhere
					+ "  group by money order by 2 desc";
		}else{
			sql = "select money,sum(num*salejg) ys from subview where  man='"
					+ username
					+ "' and (state='预收款' or state='已发运' or state='待出库' or state='订单已批准') "
					+ sqlWhere
					+ "  group by money order by 2 desc";
		}
	
		return dbManager.queryForList(sql, true);
		
	}

}
