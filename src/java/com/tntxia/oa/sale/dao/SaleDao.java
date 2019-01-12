package com.tntxia.oa.sale.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.jdbc.Transaction;
import com.tntxia.oa.sale.entity.Dh;
import com.tntxia.oa.sale.entity.RefundPro;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.sale.entity.SalePro;

public class SaleDao {

	private JdbcTemplate jdbcTemplate;
	
	private DBManager dbManager;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setDbManager(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	public Sale getSaleById(String id) throws Exception{
		Sale sale = (Sale) dbManager.queryForObject("select * from subscribe where id = ?", new Object[]{Integer.parseInt(id)}, Sale.class);
		return sale;
	}
	
	public Sale getSaleById(Transaction trans,String id) throws Exception{
		Sale sale = (Sale) trans.queryForObject("select * from subscribe where id = ?", new Object[]{Integer.parseInt(id)}, Sale.class);
		return sale;
	}
	
	
	
	public SalePro getSaleProById(int id) throws Exception{
		SalePro salePro = (SalePro) dbManager.queryForObject("select * from ddpro where id=?", new Object[]{id}, SalePro.class);
		return salePro;
	}
	
	public Map<String,Object> getSaleProById(Transaction trans,String id) throws Exception{
		return trans.queryForMap("select * from ddpro where id=?", new Object[]{id}, true);
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map getRefundById(String id){
		
		List list = jdbcTemplate.queryForList("select * from th_table where id = ?", new Object[]{Integer.parseInt(id)});
		
		return list.size()==0?null:(Map)list.get(0);
	}

	@SuppressWarnings("rawtypes")
	public List<Sale> getSaleList(Sale query, int page, int pageSize) {

		int top = page * pageSize;

		int start = (page - 1) * pageSize;

		String strSQL = "select top "
				+ top
				+ " id,number,item,coname,epro,fypronum,cpro,"
				+ "yj,num,s_num,salejg,pricehb,supplier,man from subview  where 1=1";

		if (StringUtils.isNotEmpty(query.getState())) {
			strSQL += " and state='" + query.getState() + "'";
		}

		if (StringUtils.isNotEmpty(query.getSupplier())) {
			strSQL += " and coname like '%" + query.getSupplier() + "%'";
		}

		strSQL += " order by number desc";

		List<Sale> res = new ArrayList<Sale>();

		List rows = jdbcTemplate.queryForList(strSQL);

		Iterator it = rows.iterator();

		int i = 0;

		while (it.hasNext()) {

			Map saleMap = (Map) it.next();

			if (i >= start) {
				Sale sale = new Sale();

				sale.setId((Integer) saleMap.get("id"));
				sale.setNumber((String) saleMap.get("number"));
				sale.setModel((String) saleMap.get("epro"));

				res.add(sale);
			}
			i++;
		}

		return res;

	}

	public long getSaleListCount(Sale query) {

		String strSQL = "select count(*) from subview  where 1=1";

		if (StringUtils.isNotEmpty(query.getState())) {
			strSQL += " and state='" + query.getState() + "'";
		}

		if (StringUtils.isNotEmpty(query.getSupplier())) {
			strSQL += " and coname like '%" + query.getSupplier() + "%'";
		}

		return jdbcTemplate.queryForInt(strSQL);

	}

	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public List<SalePro> getSaleProList(String saleId) throws NumberFormatException, Exception {
		return getSaleProList(Integer.parseInt(saleId));
	}

	/**
	 * 查看销售产品的列表
	 * 
	 * @param number
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List<SalePro> getSaleProList(int saleId) throws Exception {

		String sql = "select id,epro,cpro,num,s_num,unit,salejg,pricehb from ddpro where  ddid=? order by id asc";

		List rows = dbManager.queryForList(sql, new Object[] { saleId },true);

		Iterator it = rows.iterator();

		List<SalePro> saleProList = new ArrayList<SalePro>();

		while (it.hasNext()) {
			SalePro pro = new SalePro();
			Map map = (Map) it.next();
			pro.setId((Integer) map.get("id"));
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			pro.setSalejg(salejg);
			pro.setHb((String) map.get("pricehb"));
			pro.setNum((Integer) map.get("num"));
			pro.setNumOut((Integer) map.get("s_num"));
			saleProList.add(pro);
		}

		return saleProList;

	}

	/**
	 * 通过销售订单号获取采购订单号
	 * 
	 * @param number
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getPurchasingNumber(String number) {

		String sql = "select number from procure where sub='" + number + "'";

		List rows = (List) jdbcTemplate.queryForList(sql);

		Iterator it = rows.iterator();

		String sdnumber = "";

		while (it.hasNext()) {
			Map map = (Map) it.next();
			sdnumber = (String) map.get("number");
		}

		return sdnumber;
	}

	/**
	 * 更新退货订单的状态
	 * 
	 * @param status
	 */
	public void updateRefundStatus(String number, String status) {
		String sql = "update th_table set state=? where number=?";
		jdbcTemplate.update(sql, new Object[] { status, number });
	}

	public void addRefundLog(String number, String type, String remark,
			String user) {
		String sql = "insert into oa_sale_refund_log(number,remark,created_time,operateType,operator) values(?,?,getdate(),?,?)";
		jdbcTemplate.update(sql, new Object[] { number, remark, type, user });
	}

	@SuppressWarnings("rawtypes")
	public List<RefundPro> getRefundProList(int ddid) throws Exception {
		String sql = "select id,epro,cpro,fypronum,supplier,wid,s_num,num,unit,salejg,pricehb,rale_types,rale,fy_states from th_pro where ddid=?";
		List list = dbManager.queryForList(sql, new Object[] { ddid },true);
		List<RefundPro> result = new ArrayList<RefundPro>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			RefundPro pro = new RefundPro();
			pro.setId((Integer) map.get("id"));
			pro.setModel((String) map.get("epro"));
			pro.setProductYear((String) map.get("cpro"));
			pro.setNum((Integer) map.get("num"));
			pro.setUnit((String) map.get("unit"));
			pro.setSeal((String) map.get("fypronum"));
			pro.setSupplier((String) map.get("supplier"));
			pro.setSalejg((BigDecimal) map.get("salejg"));
			pro.setSalehb((String)map.get("pricehb"));
			result.add(pro);
		}
		return result;
	}
	
	public List<RefundPro> getRefundProList(String ddid) throws NumberFormatException, Exception {
		return getRefundProList(Integer.parseInt(ddid.trim()) );
	}
	
	
	
	public Dh getDhById(int id) throws Exception{
		String sql = "select * from sample_dh where id = ?";
		Dh res = (Dh) dbManager.queryForObject(sql, new Object[]{id}, Dh.class);
		return res;
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public List getAllBank() throws Exception {
		String sql = "select * from sales_bank";
		return dbManager.queryForList(sql, true);
	}

}
