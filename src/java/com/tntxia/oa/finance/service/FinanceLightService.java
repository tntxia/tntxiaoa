package com.tntxia.oa.finance.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.Userinfo;
import com.tntxia.oa.finance.dao.FinanceLightDao;
import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.util.ListUtils;
import com.tntxia.oa.util.MapUtils;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.service.CommonService;
import com.tntxia.web.util.DatasourceStore;

public class FinanceLightService extends CommonService{
	
	private Logger logger = Logger.getLogger(FinanceLightService.class);
	
	private DBManager dbManager = new DBManager(DatasourceStore.getDatasource("default"));
	
	private FinanceLightDao financeDao = new FinanceLightDao();
	
	private SaleLightDao saleDao = new SaleLightDao();
	
	@SuppressWarnings({ "rawtypes"})
	private void setTotal(SQLExecutor sqlExecutor,Map map) throws Exception{
		
		String fyid = MapUtils.getString(map, "fyid");
		BigDecimal smoney = (BigDecimal) map.get("smoney");
		List proList = saleDao.getSaleProList(sqlExecutor,fyid);
		BigDecimal total = BigDecimal.ZERO;     // 合同金额
		BigDecimal stotal = BigDecimal.ZERO;	// 出库金额
		BigDecimal rTotal = BigDecimal.ZERO;	// 退货金额
		for(int i=0;i<proList.size();i++){
			Map mapPro = ListUtils.getMap(proList, i);
			int num = MapUtils.getInt(mapPro, "num");
			int s_num = MapUtils.getInt(mapPro, "s_num");
			BigDecimal salejg = MapUtils.getBigDecimal(mapPro, "salejg");
			total = total.add(BigDecimal.valueOf(num).multiply(salejg));
			stotal = stotal.add(BigDecimal.valueOf(s_num).multiply(salejg));
			rTotal = rTotal.add(saleDao.getRefundPrice(sqlExecutor,fyid));
		}
		map.put("total", total);
		map.put("stotal", stotal);
		map.put("rTotal", rTotal);
		map.put("left", stotal.subtract(smoney).subtract(rTotal));
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void setTotal(Map map) throws Exception{
		
		String fyid = MapUtils.getString(map, "fyid");
		BigDecimal smoney = (BigDecimal) map.get("smoney");
		
		List proList = saleDao.getSaleProList(fyid);
		Map<String,BigDecimal> totalMap = this.getProListTotal(proList);
		
		BigDecimal total = totalMap.get("total");
		BigDecimal stotal = totalMap.get("stotal");
		
		
		String sql = "update gathering set total=?,total_out_house = ? where fyid=?";
		dbManager.update(sql,new Object[] {total,stotal,fyid});
		
		BigDecimal rTotal = saleDao.getRefundPrice(fyid);    // 退货金额
		map.put("total", total);
		map.put("stotal", stotal);
		map.put("rTotal", rTotal);
		map.put("left", stotal.subtract(smoney).subtract(rTotal));
		
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String,BigDecimal> getProListTotal(List proList) {
		BigDecimal total = BigDecimal.ZERO;     // 合同金额
		BigDecimal stotal = BigDecimal.ZERO;	// 出库金额
		
		for(int i=0;i<proList.size();i++){
			Map mapPro = ListUtils.getMap(proList, i);
			int num = MapUtils.getInt(mapPro, "num");
			int s_num = MapUtils.getInt(mapPro, "s_num");
			BigDecimal salejg = MapUtils.getBigDecimal(mapPro, "salejg");
			total = total.add(BigDecimal.valueOf(num).multiply(salejg));
			stotal = stotal.add(BigDecimal.valueOf(s_num).multiply(salejg));
		}
		
		Map<String,BigDecimal> res =new HashMap<String,BigDecimal>();
		res.put("total", total);
		res.put("stotal", stotal);
		return res;
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listToGather(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		List list = financeDao.getGatheringList(param);
		List rows = this.getRows(list, pageBean);
		for(int i=0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			String fyid = MapUtils.getString(map, "fyid");
			Sale sale = saleDao.getSaleById(fyid);
			if(sale!=null) {
				map.put("rate", sale.getRate());
				setTotal(map);
			}
		}
		int totalAmount = financeDao.getGatheringCount(param);
		pageBean.setTotalAmount(totalAmount);
		return this.getPagingResult(list, pageBean, totalAmount);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listTaxWait(Map<String,Object> param,PageBean pageBean) throws Exception{
		List list = financeDao.getTaxWaitList(param,pageBean);
		List rows = this.getRows(list, pageBean);
		for(int i=0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			String fyid = MapUtils.getString(map, "fyid");
			Sale sale = saleDao.getSaleById(fyid);
			if (sale!=null ) {
				setTotal(map);
			}
		}
		int totalAmount = financeDao.getTaxWaitCount(param);
		pageBean.setTotalAmount(totalAmount);
		
		return this.getPagingResult(list, pageBean, totalAmount);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listTaxFinish(Map<String,Object> param,PageBean pageBean) throws Exception{
		
		List list = financeDao.getTaxFinishList(param,pageBean);
		List rows = this.getRows(list, pageBean);
		for(int i=0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			String fyid = MapUtils.getString(map, "fyid");
			Sale sale = saleDao.getSaleById(fyid);
			if (sale!=null) {
				map.put("rate", sale.getRate());
				setTotal(map);
			}
		}
		int totalAmount = financeDao.getTaxFinishCount(param);
		return this.getPagingResult(list, pageBean, totalAmount);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> gatherStatist(Map<String,Object> param) throws Exception{
		
		List list = financeDao.getAllGatheringList(param);
		
		BigDecimal totalAll = BigDecimal.ZERO;
		BigDecimal stotalAll = BigDecimal.ZERO;
		BigDecimal rTotalAll = BigDecimal.ZERO;
		BigDecimal gatheredAll = BigDecimal.ZERO;
		BigDecimal leftAll = BigDecimal.ZERO;
		
		SQLExecutorSingleConn sqlExecutor = this.getSqlExecutorSingleConn();
		
		Map res = new HashMap();
		
		try {
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				setTotal(sqlExecutor,map);
				BigDecimal total = (BigDecimal) map.get("total");
				totalAll = totalAll.add(total);
				BigDecimal stotal = (BigDecimal) map.get("stotal");
				stotalAll = stotalAll.add(stotal);
				BigDecimal rTotal = (BigDecimal) map.get("rTotal");
				rTotalAll = rTotalAll.add(rTotal);
				BigDecimal smoney = (BigDecimal) map.get("smoney");
				gatheredAll = gatheredAll.add(smoney);
				BigDecimal left = (BigDecimal) map.get("left");
				leftAll = leftAll.add(left);
			}
			
			res.put("totalAll", totalAll);
			res.put("stotalAll", stotalAll);
			res.put("rTotalAll", rTotalAll);
			res.put("gatheredAll", gatheredAll);
			res.put("leftAll", leftAll);
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			sqlExecutor.close();
		}
		
		
		return res;
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List exportToGather() throws Exception{
		
		Transaction trans = this.getTransaction();
		List list = null;
		try{
			list = financeDao.getAllGatheringList(trans);
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String fyid = MapUtils.getString(map, "fyid");
				Sale sale = saleDao.getSaleById(trans,fyid);
				map.put("rate", sale.getRate());
				setTotal(trans,map);
			}
		}catch(Exception ex){
			logger.error("导出欠款异常！");
			throw new Exception("导出欠款异常！");
		}finally{
			trans.close();
		}
		
		return list;
	}
	
	private boolean checkNumberExist(String number){
		String sql ="select count(*) from credit_debit where l_menber=? ";
		return dbManager.exist(sql, new Object[]{number});
	}

	public Map<String,Object> getPayment(String id) throws Exception{
		String sql="select  * from payment where id=?";
		return dbManager.queryForMap(sql, new Object[]{id}, true);
	}
	
	private int getMax(SQLExecutor db) throws Exception {
		// st=1为销售，2为采购，3为其他
		String sql = "select max(id)  from credit_debit";
		return db.queryForInt(sql);
	}
	
	public int addCredit(Transaction trans, Map<String,Object> params) throws Exception{
		
		int max=0;
		
		String number = NumberFactory.generateNumber("M");
		String l_man = (String) params.get("l_man");
		String reference = (String) params.get("reference");
		
		String accountName = (String)params.get("subject");
		
		String l_dept = (String) params.get("l_dept");
		String deptjb = (String) params.get("deptjb");
		String c_d = (String) params.get("c_d");
		
		String l_coname = (String) params.get("l_coname");
		String co_number = (String) params.get("co_number");
		BigDecimal l_sqje = (BigDecimal) params.get("l_sqje");
		String l_hb = (String) params.get("l_hb");
		
		String compendium = (String) params.get("compendium");
		String g_bank = (String) params.get("g_bank");
		String g_banknum = (String) params.get("g_banknum");
		String remarks = (String) params.get("remarks");
		
		String sy = (String) params.get("sy");
		
		String km = "";
		
		String xsdh = (String) params.get("xsdh");
		String cgdh = (String) params.get("cgdh");
		
		
		String strSQL = "insert into credit_debit(l_menber,l_man,reference,l_topic,l_dept," +
				"l_deptjb,l_c_d,l_date,l_coname,co_number,l_sqje,l_sje,l_hb,compendium,g_bank,g_banknum," +
				"remarks,st,sy,km,kms,rale,states,spman,spdate,spyj,fif,fspman,fspdate,fspyj,xsdh,cgdh)" +
				" values(?,?,?,?,'"
				+ l_dept
				+ "','"
				+ deptjb
				+ "','"
				+ c_d
				+ "',?,'"
				+ l_coname
				+ "','"
				+ co_number
				+ "',?,'0','"
				+ l_hb
				+ "','"
				+ compendium
				+ "','"
				+ g_bank
				+ "','"
				+ g_banknum
				+ "','"
				+ remarks
				+ "','3','"
				+ sy
				+ "','"
				+ km
				+ "','','1','','','','','','','','','"
				+ xsdh
				+ "','"
				+ cgdh + "')";
		trans.update(strSQL,new Object[]{number,l_man,reference,accountName, new Date(System.currentTimeMillis()),l_sqje});
		max = this.getMax(trans);
		return max;
	}
	
	
	
	public Map<String,Object> getGatheringBySaleId(String saleId) throws Exception{
		
		String sql= "select * from gathering where fyid=?";
		return dbManager.queryForMap(sql, new Object[]{saleId},true);
	}
	
	@SuppressWarnings({ "rawtypes"})
	public Map<String,Object> listExchange(Map<String,String> param,PageBean pageBean) throws Exception{
	
		List list = financeDao.getExchangeList(pageBean,param);
		int total = financeDao.getExchangeCount(param);
		return this.getPagingResult(list, pageBean, total);
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listGathered(Map<String,String> param,PageBean pageBean) throws Exception{
		List list = financeDao.getGatheredList(pageBean,param);
		List rows = this.getRows(list, pageBean);
		for(int i=0;i<rows.size();i++){
			Map map = (Map) rows.get(i);
			String fyid = (String) map.get("fyid");
			if(fyid!=null){
				fyid = fyid.trim();
			}
			BigDecimal smoney = (BigDecimal) map.get("smoney");
			Sale sale = saleDao.getSaleById(fyid);
			if (sale== null) {
				continue;
			}
			map.put("rate", sale.getRate());
			
			List proList = saleDao.getSaleProList(fyid);
			Map<String,BigDecimal> totalMap = this.getProListTotal(proList);
			
			BigDecimal total = totalMap.get("total");
			BigDecimal stotal = totalMap.get("stotal");
			
			map.put("total", total);
			map.put("stotal", stotal);
			map.put("left", total.subtract(smoney));
		}
		return this.getPagingResult(list, pageBean, financeDao.getGatheredCount(param));
	}
	
	public Map<String,Object> getCreditDebit(String id) throws Exception{
		String sql="select * from credit_debit where id=?";
		return dbManager.queryForMap(sql, new Object[] {id},true);
	}
	
	public Map<String,Object> getPaymentByOrderNumber(String number) throws Exception{
		String sql = "select  * from payment where contract=?";
		return dbManager.queryForMap(sql, new Object[]{number}, true);
	}
	
	public void finishGatherng(String gatheringId) throws Exception {
		
		Transaction trans = null;
		
		try {
			trans = this.getTransaction();
			String currentDate = DateUtil.getCurrentDateSimpleStr();
			
			String strpros = "update gathering set states='已收全部款',note='已收款',skdate=?  where id=?";
			trans.update(strpros, new Object[] {currentDate, gatheringId});
			
			Map<String,Object> gathering = trans.queryForMap("select * from gathering where id = ?",new Object[] {gatheringId}, true);
			String fyid = (String) gathering.get("fyid");
			
			String sql = "update subscribe set gather_status=2 where id = ?";
			trans.update(sql, new Object[] {fyid});
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			if(trans!=null) {
				trans.rollback();
			}
		}finally {
			trans.close();
		}
	}
	
	public Map<String,Object> getGathering(Transaction trans, Integer id) throws Exception {
		String sql = "select * from gathering where id = ?";
		return trans.queryForMap(sql, new Object[] {id}, true);
	}
	
	public void updateGatheringRemark(Transaction trans,Integer id, String remark) throws SQLException {
		String sql = "update gathering set remark = ? where id = ?";
		trans.update(sql, new Object[] {remark, id});
	}
	
	private void doGather(Transaction trans,Integer id,String username,BigDecimal gatherTotal) throws Exception{
		
		Map<String,Object> gathering = getGathering(trans, id);
		
		String  fyid = (String) gathering.get("fyid");
		BigDecimal total = (BigDecimal) gathering.get("total");
		BigDecimal smoney = (BigDecimal) gathering.get("smoney");
		String orderform = (String) gathering.get("orderform");
		String coname = (String) gathering.get("coname");
		
		// 还没有收款的金额
		BigDecimal left = total.subtract(smoney);
		// 最后已收款金额
		BigDecimal finalGatheredTotal = smoney.add(gatherTotal);
		
		System.out.println("未收金额："+left);
		
		int compare = left.compareTo(gatherTotal);
		String date = com.tntxia.date.DateUtil.getCurrentDateSimpleStr();
		
		// 如果冲帐的金额，刚好是待收的金额，则记账为已收款
		if (compare==0) {

			String strSQLmx = "insert into gather_mx_mx(g_m_id,g_m_types,g_m_number,g_m_coname,g_m_smoney,g_m_hb,g_m_date,g_m_man,G_M_SALESMAN) values('"
					+ id + "','2','" + orderform + "','" + coname
					+ "','" + gatherTotal + "','CNY','" + date + "','" + username + "','')";
			
			System.out.println("收款明细："+strSQLmx);

			trans.executeUpdate(strSQLmx);

			String strpros = "update gathering set  imoney='" + id
					+ "',smoney='" + finalGatheredTotal
					+ "',states='已收全部款',note='已收款',skdate='"
					+ date + "'  where id='" + id + "'";
			trans.executeUpdate(strpros);
			
			String sql = "update subscribe set gather_status=2 where id = ?";
			trans.update(sql, new Object[] {fyid});
			
		// 如果冲帐的金额，小于待收的金额，则记账为部分收款
		} else if(compare==1){
			
			String strSQLmx = "insert into gather_mx_mx(g_m_id,g_m_types,g_m_number,g_m_coname,g_m_smoney,g_m_hb,g_m_date,g_m_man,G_M_SALESMAN) values('"
					+ id + "','2','" + orderform + "','" + coname
					+ "','" + gatherTotal + "','CNY','" + date + "','" + username + "','')";
			trans.executeUpdate(strSQLmx);

			String strpros = "update gathering set smoney='" + finalGatheredTotal + "',note='部分收款',skdate='"
					+ date + "' where id='" + id + "'";
			trans.executeUpdate(strpros);
			
			String sql = "update subscribe set gather_status=1 where id = ?";
			trans.update(sql, new Object[] {fyid});
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> gather(Userinfo user, List list) throws SQLException {
		Transaction trans = this.getTransaction();
		String username = user.getUsername();
		try {
			for(int i=0;i<list.size();i++){
				
				Map map = (Map) list.get(i);
				Integer id = (Integer) map.get("id");
				String subject = (String) map.get("subject");
				BigDecimal toGatherTotal = BigDecimalUtils.parse((String) map.get("toGather"));
				String remark = (String) map.get("remark");
				Map<String,Object> gathering = getGathering(trans, id);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("subject", subject);
				params.put("l_man", username);
				params.put("reference", "");
				params.put("l_dept", user.getDept());
				params.put("deptjb", user.getDeptjb());
				params.put("c_d", "贷方");
				params.put("l_date", com.tntxia.date.DateUtil.getCurrentDateSimpleStr());
				String l_coname = (String) gathering.get("coname");
				params.put("l_coname", l_coname);
				
				params.put("co_number", "");
				
				String orderform = (String) gathering.get("orderform");
				params.put("orderform", orderform);
				params.put("l_sqje", toGatherTotal);
				params.put("l_hb", "CNY");
				params.put("compendium", "");
				params.put("g_bank", "");
				params.put("g_banknum", "");
				params.put("remarks", "");
				
				params.put("sy", "supplier");
				params.put("xsdh", orderform);
				params.put("cgdh", "");
				
				this.updateGatheringRemark(trans, id, remark);
				addCredit(trans,params);
				this.doGather(trans,id,username,toGatherTotal);
				trans.commit();
				
			}
		}catch(Exception ex) {
			trans.rollback();
			ex.printStackTrace();
			return this.errorMsg(ex.toString());
		}finally {
			trans.close();
		}
		
		return this.success();
	}

}
