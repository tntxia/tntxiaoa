package com.tntxia.oa.warehouse.service;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.tntxia.date.DateUtil;
import com.tntxia.db.DBConnection;
import com.tntxia.dbmanager.DBManager;

import com.tntxia.oa.mail.SendMail;

import com.tntxia.oa.purchasing.dao.PurchasingLightDao;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.oa.sale.entity.Sale;
import com.tntxia.oa.util.CurrentUtils;
import com.tntxia.oa.util.StringUtils;
import com.tntxia.oa.warehouse.InWarehouse;
import com.tntxia.oa.warehouse.Rkhouse;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.dao.WarehouseLightDao;
import com.tntxia.oa.warehouse.entity.InOutLog;
import com.tntxia.oa.warehouse.entity.ProductNumChangeBean;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.service.CommonService;
import com.tntxia.web.util.DatasourceStore;

public class WarehouseLightService extends CommonService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private DBManager dbManager = this.getDBManager();
	
	private SaleLightDao saleDao = new SaleLightDao();
	
	private WarehouseLightDao dao = new WarehouseLightDao();
	
	private PurchasingLightDao purchasingDao = new PurchasingLightDao();
	
	public Map<String, Object> getDetail(String id) throws Exception {
		return dao.getDetail(id);
	}
	
	public Warehouse getProductById(String id) throws Exception {
		Warehouse res = new Warehouse();
		Map<String,Object> detail = (Map<String,Object>) this.getDetail(id);
		res.setId(id);
		res.setPro_model((String) detail.get("pro_model"));
		res.setPro_num(((BigDecimal) detail.get("pro_num")).intValue());
		return res;
	}
	
	private String getTrType(Transaction trans, String fynumber) {
		String sqltrdd = "select  top 1 tr_types from transportation where invoice=?  and sate='发运通知单'";
		return trans.getString(sqltrdd, new Object[] { fynumber }, true);
	}
	

	private int getTransportInNo(Transaction trans, String year)
			throws Exception {

		int tno = 1;

		String nnn = year + "%";
		String strSQLw = "select top 1 *  from transportation where tr_types like ?   order by in_no desc";
		Map<String, Object> transMap = trans.queryForMap(strSQLw,
				new Object[] { nnn }, true);

		if (transMap != null && transMap.size() > 0) {
			tno = (Integer) transMap.get("in_no") + 1;
		}

		return tno;

	}

	private String getTrType(String year, int tno) {

		String sno = "";
		if (tno < 10) {
			sno = "00";
		} else if ((10 <= tno) & (tno < 100)) {
			sno = "0";
		} else
			sno = "";

		return year + sno + tno;
	}
	
	/**
	 * 增加出入库记录
	 * 
	 * @param log
	 * @throws SQLException
	 */
	private void addInOutLog(Transaction trans, InOutLog log)
			throws SQLException {
		String id = String.valueOf(System.currentTimeMillis());
		String sql = "insert into warehouse_in_out_log(id,number,orgin_num,change_num,final_num,operateTime,operater,wid,memo) values(?,?,?,?,?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date(System.currentTimeMillis());
		trans.update(
				sql,
				new Object[] { id, log.getNumber(), log.getOrginNum(),
						log.getChangeNum(), log.getFinalNum(),
						sdf.format(now),
						log.getUsername(), log.getWid(),log.getRemark() });
	}
	
	/**
	 * 从仓库抽取产品
	 * @param trans
	 * @param num
	 * @throws Exception 
	 */
	public String updateProductNum(Transaction trans, ProductNumChangeBean changeBean,String model) throws Exception {
		
		String sql = "select * from warehouse where pro_model = ?";
		Map<String,Object> pro = trans.queryForMap(sql,new Object[] {model}, true);
		int pro_num = ((BigDecimal) pro.get("pro_num")).intValue();
		int num = changeBean.getNum();
		
		int finalNum = pro_num + num;
		if(finalNum<0) {
			throw new Exception("库存不足");
		}
		
		int id = (Integer) pro.get("id");
		
		sql = "update warehouse set pro_num = ? where id=?";
		trans.update(sql,new Object[] {finalNum,id});
		
		InOutLog log = new InOutLog();
		log.setOrginNum(pro_num);
		log.setChangeNum(num);
		log.setFinalNum(finalNum);
		log.setNumber(changeBean.getNumber());
		log.setUsername(changeBean.getUsername());
		log.setWid(id);
		log.setRemark(changeBean.getRemark());
		addInOutLog(trans, log);
		return null;
		
	}

	private void updateWarehosue(Transaction trans, InOutLog inOut)
			throws SQLException {
		String strSQLw = "update warehouse set pro_num=?  where  id=?";
		trans.update(strSQLw,
				new Object[] { inOut.getFinalNum(), inOut.getWid() });
		addInOutLog(trans, inOut);
	}
	
	public BigDecimal getWarehousePrice(String model) throws Exception{
		String sql = "select pro_price from warehouse where pro_model=?";
		return dbManager.queryForBigDecimal(sql, new Object[]{model});
	}

	/**
	 * 单个产品出库
	 * 
	 * @param username
	 * @param id
	 * @param ddid
	 * @return
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
	@SuppressWarnings("rawtypes")
	public String doProout(String username, String id, String ddid,
			String num1, String wid) throws NumberFormatException, SQLException {

		String currentDate = DateUtil.getCurrentDateSimpleStr();
		String msg = null;

		Transaction trans = this.getTransaction();

		try {

			Sale sale = saleDao.getSaleById(trans, ddid);

			String coname = sale.getConame();

			String fynumber = sale.getNumber();

			String co_number = sale.getCo_number();

			SimpleDateFormat simplew = new SimpleDateFormat("yyMMdd");
			// 获取当前时间
			String nn = simplew.format(new java.util.Date());

			String strSSql = "select * from ddpro where id=?";

			Map proMap = trans.queryForMap(strSSql, new Object[] { id }, true);

			Integer num = (Integer) proMap.get("num");
			Integer sNum = (Integer) proMap.get("s_num");

			// 需要出库的数量
			int n_num1 = Integer.parseInt(num1);

			if (num - sNum < n_num1) {
				msg = "出库数量不可以超过未出库数量！";
				return msg;
			}

			// 更新客户的出库时间
			String ddsqlst_sd = "update client set nsnumber='" + currentDate
					+ "'  where coname='" + coname + "'";
			trans.executeUpdate(ddsqlst_sd);

			String sqldd = "select  id,man,money,item,mode,source,trade,habitus,send_date,tr,yf_types,yf_money,tr_addr,tr_man,tr_tel,dept,deptjb,remarks from subscribe where id=?";
			Map saleMap = trans.queryForMap(sqldd, new Object[] { ddid }, true);

			if (saleMap == null || saleMap.size() == 0) {
				msg = "产品对应的销售合同不存在！";
				return msg;
			}

			String sales_man = (String) saleMap.get("man");
			String payment = (String) saleMap.get("mode");
			int datet = (Integer) saleMap.get("source");
			String dd_date = (String) saleMap.get("send_date");
			String tr = (String) saleMap.get("tr");
			String yf_types = (String) saleMap.get("yf_types");
			double yf_money = ((BigDecimal) saleMap.get("yf_money"))
					.doubleValue();
			String tr_addr = (String) saleMap.get("tr_addr");
			String tr_man = (String) saleMap.get("tr_man");
			String tr_tel = (String) saleMap.get("tr_tel");
			String dept = (String) saleMap.get("dept");
			String deptjb = (String) saleMap.get("deptjb");
			String remark = (String) saleMap.get("remarks");

			String strSQLpro = "select id,epro,cpro,fypronum,num,s_c_num,s_num,fy_states,unit,salejg,pricehb,supplier,wid,p_check,remark from ddpro where  id=?";
			Map mapPro = trans
					.queryForMap(strSQLpro, new Object[] { id }, true);
			int ddproid = (Integer) mapPro.get("id");
			String pro_model = ((String) mapPro.get("epro")).trim();
			String pro_name = (String) mapPro.get("cpro");
			int s_c_num = (Integer) mapPro.get("s_c_num");
			int s_num = (Integer) mapPro.get("s_num");
			int snum = n_num1 + 0;
			String fy_states = ((String) mapPro.get("fy_states")).trim();
			if (fy_states.equals("待发运")) {
				snum = snum + s_c_num;
			}
			String punit = (String) mapPro.get("unit");
			BigDecimal saleprice = (BigDecimal) mapPro.get("salejg");
			String salehb = (String) mapPro.get("pricehb");
			String supplier = (String) mapPro.get("supplier");
			String p_check = (String) mapPro.get("p_check");

			String sql = "select pro_num,pro_addr,pro_price,price_hb from warehouse where id = ?";

			Map warehouseMap = trans.queryForMap(sql, new Object[] { wid },
					true);

			if (warehouseMap == null || warehouseMap.size() == 0) {
				msg = "仓库暂无该产品:" + pro_model;
				return msg;
			}
			int pro_num = ((BigDecimal) warehouseMap.get("pro_num")).intValue();
			String proaddr = (String) warehouseMap.get("pro_addr");
			double pro_price = ((BigDecimal) warehouseMap.get("pro_price"))
					.doubleValue();
			String price_hb = (String) warehouseMap.get("price_hb");
			
			if (pro_num < n_num1) {
				msg = "库存不足";
				return msg;
			}

			int tno = 1;
			String tr_types = getTrType(trans, fynumber);
			if (tr_types == null) {

				tno = getTransportInNo(trans, nn);

				tr_types = getTrType(nn, tno);

				String strSQLfy = "insert into transportation(tr_types,ddid,fyid,invoice,subscribe,coname,aimport,transportation,mbdate,sjdate,linkman,linktel,mode,datet,sendcompany,slinkman,slinktel,sate,fy_num,fy_yf,fy_bf,fy_js,fy_sz,sale_man,sale_dept,deptjb,co_number,fy_date,yf_types,yf_money,remark,trans_com,in_no,tr_print) values(?,'"
						+ ddid
						+ "','"
						+ ddid
						+ "','"
						+ fynumber
						+ "','"
						+ fynumber
						+ "','"
						+ coname
						+ "','"
						+ tr_addr
						+ "','"
						+ tr
						+ "','"
						+ dd_date
						+ "','"
						+ dd_date
						+ "','"
						+ tr_man
						+ "','"
						+ tr_tel
						+ "','"
						+ payment
						+ "','"
						+ datet
						+ "','','"
						+ sales_man
						+ "','','发运通知单','','0','0','','','"
						+ sales_man
						+ "','"
						+ dept
						+ "','"
						+ deptjb
						+ "','"
						+ co_number
						+ "','"
						+ currentDate
						+ "','"
						+ yf_types
						+ "','"
						+ yf_money
						+ "','" + remark + "','','" + tno + "','')";
				trans.update(strSQLfy, new Object[] { tr_types });

				String ddsqls = "update subscribe set fy_number=?  where id='"
						+ ddid + "'";
				trans.update(ddsqls, new Object[] { tr_types });

			} else {
				tr_types = tr_types.trim();
			}

			String strSQLq = "insert into outhouse(pro_fynum,pro_coname,pro_model,pro_name,pro_num,pro_unit,pro_supplier,"
					+ "pro_datetime,slinkman,slinktel,states,ddid,remark,wid,pro_coname_num,pro_sales_price,"
					+ "pro_price_hb,pro_rate_types,pro_rate,pro_out_num,in_no) values('"
					+ fynumber
					+ "','"
					+ coname
					+ "','"
					+ pro_model
					+ "','"
					+ pro_name
					+ "','"
					+ n_num1
					+ "','"
					+ punit
					+ "','"
					+ supplier
					+ "','"
					+ currentDate
					+ "','"
					+ username
					+ "','"
					+ proaddr
					+ "','已出库','"
					+ ddid
					+ "','"
					+ p_check
					+ "','1','"
					+ co_number
					+ "','"
					+ saleprice
					+ "','"
					+ salehb
					+ "','"
					+ wid
					+ "','0','"
					+ tr_types + "','" + tno + "')";
			trans.update(strSQLq);

			ProductNumChangeBean source = new ProductNumChangeBean();
			source.setNumber(fynumber);
			source.setUsername(username);
			source.setRemark("仓库单个出库");
			source.setNum(-n_num1);
			String m = this.updateProductNum(trans, source, pro_model);
			if(org.apache.commons.lang.StringUtils.isNotEmpty(m)) {
				throw new Exception(m);
			}

			int nnum = s_num + n_num1;
			String strSQLwf = "update ddpro set selljg='" + pro_price
					+ "',money='" + price_hb + "',s_num='" + nnum
					+ "',s_c_num='" + snum + "',s_tr_date='" + currentDate
					+ "',fy_states='待发运'  where id='" + ddproid + "' ";
			trans.update(strSQLwf);
			trans.commit();
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			String errMsg = e.getMessage();
			return errMsg;
		} finally {
			trans.close();
		}

	}
	
	private List<String> getListFromStoken(String str) {
		// 产品编号的数组
		List<String> res = new ArrayList<String>();
		if (str.trim().length() > 0) {
			for (String wid : str.split(",")) {
				res.add(wid);
			}
		}
		return res;
	}
	
	/**
	 * 
	 * 全部销售产品出库
	 * 
	 * @param username
	 * @param ddid1
	 * @param coname
	 * @param fynumber
	 * @param co_number
	 * @return
	 * @throws SQLException
	 * 
	 **/
	public String doSout(String username, String ddid, String wids, String sids)
			throws Exception {
		
		

		String msg = null;

		// 产品编号的数组
		List<String> widList = getListFromStoken(wids);

		List<String> sidList = getListFromStoken(sids);

		if (widList.size() != sidList.size()) {
			msg = "出库的选择与销售产品不一致，请刷新页面后重试";
			return msg;
		}

		DataSource ds = DatasourceStore.getDatasource("default");
		Transaction trans = null;

		try {

			trans = Transaction.createTrans(ds);

			SimpleDateFormat simplew = new SimpleDateFormat("yyMMdd");
			String nn = simplew.format(new java.util.Date());
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = simple.format(new java.util.Date());

			
			String sqldd = "select * from subscribe where id=?";
			Map<String, Object> saleMap = trans.queryForMap(sqldd,
					new Object[] { ddid }, true);

			if (saleMap == null || saleMap.size() == 0) {
				msg = "销售订单不存在，请检查销售订单是否被删除！";
				return msg;
			}
			
			String fynumber = (String) saleMap.get("number");
			
			String coname = (String) saleMap.get("coname");
			String ddsqlst_sd = "update client set nsnumber='" + currentDate
					+ "'  where coname='" + coname + "'";
			trans.executeUpdate(ddsqlst_sd);
			String co_number = (String) saleMap.get("co_number");
			
			String sales_man = (String) saleMap.get("man");
			String items = ((String) saleMap.get("item")).trim();
			String payment = (String) saleMap.get("mode");
			Integer datet = (Integer) saleMap.get("source");
			String dd_date = (String) saleMap.get("send_date");
			String tr = (String) saleMap.get("tr");
			String yf_types = (String) saleMap.get("yf_types");
			BigDecimal yf_money = (BigDecimal) saleMap.get("yf_money");

			String tr_addr = (String) saleMap.get("tr_addr");
			String tr_man = (String) saleMap.get("tr_man");
			String tr_tel = (String) saleMap.get("tr_tel");

			String dept = (String) saleMap.get("dept");
			String deptjb = (String) saleMap.get("deptjb");
			String remark = (String) saleMap.get("remarks");

			for (int i = 0; i < sidList.size(); i++) {
				
				String sid = sidList.get(i);
				
				Map<String,Object> salePro = saleDao.getSaleProById(trans,sid);
				Integer num = (Integer) salePro.get("num");
				Integer s_num = (Integer) salePro.get("s_num");
				// 检查销售订单中是否有未出库产品
				// 有未出库产品，全部出库
				if (num > s_num) {
					
					Integer ddproid = (Integer)salePro.get("id");
					String selectedSwid = widList.get(i);
					
					String pro_model = (String)salePro.get("epro");
					String pro_name = (String)salePro.get("cpro");
					String pro_number = (String)salePro.get("fypronum");
					int n_num = num - s_num;

					String punit = (String) salePro.get("unit");
					BigDecimal saleprice = (BigDecimal) salePro.get("salejg");
					String salehb = (String)salePro.get("pricehb");
					String supplier = (String)salePro.get("supplier");
					String p_check = (String)salePro.get("p_check");

					String sql = "select id,pro_num,pro_addr,pro_price,price_hb from warehouse where id=?";

					Map<String, Object> warehouseMap = trans.queryForMap(sql,
							new Object[] { selectedSwid }, true);
					if (warehouseMap == null || warehouseMap.size() == 0) {
						msg = selectedSwid + "暂无该产品:";
						msg += "型号是:" + pro_model + "批号是:" + pro_name;
						return msg;
					}

					int wid = (Integer) warehouseMap.get("id");
					int pro_num = ((BigDecimal) warehouseMap.get("pro_num"))
							.intValue();
					String proaddr = (String) warehouseMap.get("pro_addr");
					BigDecimal pro_price = (BigDecimal) warehouseMap
							.get("pro_price");
					String price_hb = (String) warehouseMap.get("price_hb");
					int zpro_num = pro_num - n_num;
					if (zpro_num < 0) {
						msg = "库存不足";
						return msg;
					}

					int tno = 1;
					String tr_types = getTrType(trans, fynumber);
					if (tr_types == null) {

						tno = getTransportInNo(trans, nn);

						tr_types = getTrType(nn, tno);

						String strSQLfy = "insert into transportation(tr_types,ddid,fyid,invoice,subscribe,coname,aimport,transportation,mbdate,sjdate,linkman,linktel,mode,datet,sendcompany,slinkman,slinktel,sate,fy_num,fy_yf,fy_bf,fy_js,fy_sz,sale_man,sale_dept,deptjb,co_number,fy_date,yf_types,yf_money,remark,trans_com,in_no,tr_print) values(?,'"
								+ ddid
								+ "','"
								+ ddid
								+ "','"
								+ fynumber
								+ "','"
								+ fynumber
								+ "','"
								+ coname
								+ "','"
								+ tr_addr
								+ "','"
								+ tr
								+ "','"
								+ dd_date
								+ "','"
								+ dd_date
								+ "','"
								+ tr_man
								+ "','"
								+ tr_tel
								+ "','"
								+ payment
								+ "','"
								+ datet
								+ "','','"
								+ sales_man
								+ "','','发运通知单','','0','0','','','"
								+ sales_man
								+ "','"
								+ dept
								+ "','"
								+ deptjb
								+ "','"
								+ co_number
								+ "','"
								+ currentDate
								+ "','"
								+ yf_types
								+ "','"
								+ yf_money
								+ "','"
								+ remark
								+ "','','" + tno + "','')";
						trans.update(strSQLfy, new Object[] { tr_types });

						String ddsqls = "update subscribe set fy_number=?,send_date='"
								+ currentDate + "'  where id='" + ddid + "'";
						trans.update(ddsqls, new Object[] { tr_types });

					} else {
						tr_types = tr_types.trim();
					}

					String strSQLq = "insert into outhouse(pro_fynum,pro_coname,pro_model,pro_name,pro_num,pro_unit,pro_supplier,pro_datetime,pro_number,slinkman,slinktel,states,ddid,remark,wid,pro_coname_num,pro_sales_price,pro_price_hb,pro_rate_types,pro_rate,pro_out_num,in_no,salejg) values('"
							+ fynumber
							+ "','"
							+ coname
							+ "','"
							+ pro_model
							+ "','"
							+ pro_name
							+ "','"
							+ n_num
							+ "','"
							+ punit
							+ "','"
							+ supplier
							+ "','"
							+ currentDate
							+ "','"
							+ pro_number
							+ "','"
							+ username
							+ "','"
							+ proaddr
							+ "','已出库','"
							+ ddid
							+ "','"
							+ p_check
							+ "','"
							+ wid
							+ "','"
							+ co_number
							+ "','"
							+ saleprice
							+ "','"
							+ salehb
							+ "','"
							+ wid
							+ "','"
							+ items
							+ "','" + tr_types + "','" + tno + "',0)";
					trans.update(strSQLq);

					InOutLog log = new InOutLog();
					log.setOrginNum(pro_num);
					log.setChangeNum(-n_num);
					log.setFinalNum(zpro_num);
					log.setNumber(fynumber);
					log.setUsername(username);
					log.setWid(wid);
					log.setRemark("仓库全部出库操作");
					updateWarehosue(trans, log);

					String strSQLwf = "update ddpro set selljg='" + pro_price
							+ "',money='" + price_hb + "',s_num='" + num
							+ "',s_c_num='" + n_num + "',s_tr_date='"
							+ currentDate + "',fy_states='待发运'  where id='"
							+ ddproid + "' ";
					trans.executeUpdate(strSQLwf);

				}
			}
			String ddsqls1 = "update subscribe set state='已发运'  where id='"
					+ ddid + "'";
			trans.update(ddsqls1);
			trans.commit();
			return msg;
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if (trans != null) {
				trans.close();
			}
		}

	}
	
	/**
	 * 获取在途数
	 * @param sqlExcecutor
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int getCommingNum(SQLExecutor sqlExcecutor,String model) throws Exception {
		
		// 获取在途数
		String sqlComming = "select num,cgpro_num from cgview where  epro=? and (l_spqk='合同已确认' or l_spqk='待入库')";
		List list = sqlExcecutor.queryForList(sqlComming,new Object[] {model},true);
		int totalNum = 0;
		for(int i=0;i<list.size();i++) {
			Map res = (Map) list.get(i);
			Integer num = (Integer) res.get("num");
			if(num==null) {
				num = 0;
			}
			Integer cgproNum = (Integer) res.get("cgpro_num");
			if(cgproNum==null) {
				cgproNum = 0;
			}
			totalNum += num - cgproNum;
		}
		return totalNum;
		
	}
	
	/**
	 * 获取待出库数
	 * @param sqlExcecutor
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int getOutingNum(SQLExecutor sqlExcecutor,String model) throws Exception {
		String sql = "select num,s_num from subview where epro=? and (state='已备货' or state='待出库' or state='预收款') ";
		List list = sqlExcecutor.queryForList(sql,new Object[] {model} ,true);
		int totalNum = 0;
		for(int i=0;i<list.size();i++) {
			Map res = (Map) list.get(i);
			Integer num = (Integer) res.get("num");
			if(num==null) {
				num = 0;
			}
			Integer sNum = (Integer) res.get("s_num");
			if(sNum==null) {
				sNum = 0;
			}
			totalNum += num - sNum;
		}
		return totalNum;
	}
	

	@SuppressWarnings("rawtypes")
	public List search(String model) throws Exception {
		return dao.search(model);
	}

	@SuppressWarnings({ "rawtypes"})
	public Map listIn(Map<String,Object> param) throws Exception{
		
		PageBean pageBean = (PageBean) param.get("pageBean");
		
		Map<String,Object> res = new HashMap<String,Object>();
		int page = pageBean.getPage();
		int pageSize = pageBean.getPageSize();
		res.put("page", page);
		res.put("pageSize", pageSize);
		List list = dao.inList(param);
		List rows = this.getRows(list, pageBean);
		res.put("rows", rows);
		res.put("totalAmount", dao.inCount(param));
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public InWarehouse getInWarehouse(String id) throws Exception {

		InWarehouse res = dao.getInWarehouse(id);

		if (res == null) {
			return res;
		}

		List rkhouseList = dao.getRkhouseList(id);

		res.setRkhouseList(rkhouseList);

		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Rkhouse> getRkhouse(Transaction trans, String id)
			throws Exception {
		List<Rkhouse> res = new ArrayList<Rkhouse>();
		// 新建一个ＪＤＢＣ服务的对象
		String sql = "select * from rkhouse where pro_rk_num = ?";
		List list = trans.queryForList(sql, new Object[] { id }, true);
		for (int i = 0; i < list.size(); i++) {
			Rkhouse rk = new Rkhouse();
			Map map = (Map) list.get(i);
			Integer rkid = (Integer) map.get("id");
			rk.setId(rkid);
			Integer num = (Integer) map.get("pro_num");
			rk.setPro_num(num);
			String model = (String) map.get("pro_model");
			rk.setPro_model(model);
			String cgproId = (String) map.get("pro_rate");
			if(StringUtils.isEmpty(cgproId)){
				throw new Exception("采购产品ID为空！");
			}
			cgproId = cgproId.trim();
			rk.setCgproId(Integer.parseInt(cgproId));
			String pro_name = (String) map.get("pro_name");
			rk.setPro_name(pro_name);
			String pro_supplier = (String) map.get("pro_supplier");
			rk.setPro_supplier(pro_supplier);
			String pro_sup_number = (String) map.get("pro_sup_number");
			rk.setPro_sup_number(pro_sup_number);
			String pro_types = (String) map.get("pro_types");
			rk.setPro_types(pro_types);
			String remark = (String) map.get("remark");
			rk.setRemark(remark);
			String pro_addr = (String) map.get("pro_addr");
			rk.setPro_addr(pro_addr);
			String pro_number = (String) map.get("pro_number");
			rk.setPro_number(pro_number);
			String pro_cgy = (String) map.get("pro_cgy");
			rk.setPro_cgy(pro_cgy);
			String unit = (String) map.get("pro_unit");
			rk.setPro_unit(unit);
			BigDecimal price = (BigDecimal) map.get("pro_price");
			rk.setPro_price(price.doubleValue());
			String hb = (String) map.get("pro_hb");
			rk.setPro_hb(hb);
			Integer status = (Integer) map.get("status");
			rk.setStatus(status);
			
			res.add(rk);
		}

		return res;

	}
	
	/**
	 * 取消入库单
	 * 
	 * @param id
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void cancelInWarehouse(String id,String username) throws Exception {

		

		InWarehouse inWarehouse = getInWarehouse(id);
		
		String number = inWarehouse.getNumber();

		// 入库单状态
		String states = inWarehouse.getStates().trim();

		// 如果入库单不是已入库状态，不能取消入库
		if (states.equals("已删除")) {
			throw new Exception("入库单已经删除，请勿重复删除");
		}

		DataSource ds = DatasourceStore.getDatasource("default");
		Transaction trans = Transaction.createTrans(ds);

		try {
			
			String strSQL = "update in_warehouse set states='已删除' where id='" + id
					+ "'";
			trans.update(strSQL);

			List<Rkhouse> list = getRkhouse(trans, id);
			for (Rkhouse rkhouse : list) {
				int pro_num = rkhouse.getPro_num();
				int cgid = rkhouse.getCgproId();
				String pro_model = rkhouse.getPro_model();

				String strSQLpro1 = "select  *  from cgpro where id=? ";
				List cgproList = trans.queryForList(strSQLpro1,
						new Object[] { cgid }, true);

				for (int i = 0; i < cgproList.size(); i++) {
					Map map = (Map) cgproList.get(i);

					String ddid = (String) map.get("ddid");
					int inNum = purchasingDao.getRkNum(trans, cgid);

					String strSQLty = "update cgpro set cgpro_num=? where id=? ";
					trans.update(strSQLty, new Object[] { inNum, cgid });

					String strSQLty1 = "update procure set l_spqk='合同已确认'   where id='"
							+ ddid + "' ";
					trans.executeUpdate(strSQLty1);

				}

				// 如果入库单是已入库状态，需要把仓库的数量减掉
				if (states.equals("已入库") || states.equals("全部入库")) {
					
					ProductNumChangeBean changeBean = new ProductNumChangeBean();
					changeBean.setUsername(username);
					changeBean.setRemark("取消入库单");
					changeBean.setNumber(number);
					changeBean.setNum(-pro_num);
					
					this.updateProductNum(trans, changeBean, pro_model);

				}
			}
			trans.commit();

		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			if (trans != null) {
				trans.close();
			}
		}

	}
	
	/**
	 * 入库
	 * 
	 * @param id1
	 * @param username
	 * @param sp_number
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public String checkIn(String id, String username) throws Exception {

		if (username == null) {
			return "用户还没有登陆";
		}

		try {

			Date date = new Date(System.currentTimeMillis());
			System.out.println("rk : start time " + date.getTime());

			// 用来记录结果，结果为空时，表示成功入库
			// 否则，应该对入库结果进行处理
			String msg = null;

			DBConnection db = new DBConnection();
			Transaction trans = Transaction.createTrans(db
					.getConnectionObject());

			Rkhouse rkhouse = dao.getRkhouse(id);

			String parentId = rkhouse.getPro_rk_num();

			InWarehouse inWarehouse = dao.getInWarehouse(parentId);

			// 如果状态是已入库，则无需重复入库
			if (rkhouse.getStatus() == 1) {
				return null;
			}

			String pro_addr = rkhouse.getPro_addr();

			updateWarehouse(trans, rkhouse, pro_addr, username,
					inWarehouse.getNumber());
			updateRkhouseStatus(trans, rkhouse);

			trans.commit();
			db.close();

			Date end = new Date(System.currentTimeMillis());
			System.out.println("rk starttime is " + date.getTime()
					+ " end time is " + end.getTime());

			return msg;
		} catch (Exception e) {

			return e.getMessage();
		} finally {

		}

	}
	
	@SuppressWarnings("rawtypes")
	private List getPurchasingProductByParentId(Transaction trans, String ddid)
			throws Exception {
		String sql = "select * from cgpro where ddid = ?";
		return trans.queryForList(sql, new Object[] { ddid }, true);
	}
	
	/**
	 * 获取入库单信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public InWarehouse getInWarehouseById(Transaction trans, String id)
			throws Exception {
		InWarehouse res = new InWarehouse();
		String sql = "select * from  in_warehouse  where id=?";
		Map<String, Object> map = trans.queryForMap(sql, new Object[] { id },
				true);

		String number = (String) map.get("number");
		String states = (String) map.get("states");

		res.setNumber(number);
		res.setStates(states);

		return res;
	}
	
	/**
	 * 入库
	 * 
	 * @param id1
	 * @param username
	 * @param sp_number
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public String checkInAll(String id, String username, String sp_number)
			throws Exception {

		Transaction trans = this.getTransaction();

		try {

			// 用来记录结果，结果为空时，表示成功入库
			// 否则，应该对入库结果进行处理
			String msg = null;

			InWarehouse inWarehouse = getInWarehouseById(trans, id);

			String inWarehouseStatus = StringUtils.safeTrim(inWarehouse
					.getStates());

			if ("已入库".equals(inWarehouseStatus)
					|| "全部入库".equals(inWarehouseStatus)) {
				throw new Exception("入库单已经入库");
			}

			List<Rkhouse> rkhouseList = getRkhouse(trans, id);

			// 入库单编号
			String inWarehouseNumber = inWarehouse.getNumber();

			for (Rkhouse rkhouse : rkhouseList) { // 1

				// 如果状态是已入库，则无需重复入库
				if (rkhouse.getStatus() == 1) {
					continue;
				}

				String pro_model1 = rkhouse.getPro_model().trim();
				String pro_name = rkhouse.getPro_name().trim();
				String pro_supplier = rkhouse.getPro_supplier();
				String pro_sup_number = rkhouse.getPro_sup_number();
				String cgNumber = rkhouse.getPro_types();
				String pro_number = rkhouse.getPro_number();

				String cgname = rkhouse.getPro_cgy();

				int num1 = rkhouse.getPro_num();
				String pro_addr = rkhouse.getPro_addr();
				
				int cgproId = rkhouse.getCgproId();
				if(cgproId>0) {
					trans.update("update cgpro set cgpro_num = cgpro_num + ? where id = ?", new Object[] {num1,cgproId});
				}

				updateWarehouse(trans, rkhouse, pro_addr, username,
						inWarehouseNumber);

				updateRkhouseStatus(trans, rkhouse);

				SendMail sendmail = new SendMail();

				// 如果查不到负责人，则不会发邮件
				if (cgname != null && cgname.trim().length() != 0) {

					// 在内容里面加上采购订单号、供应商名称、型号、品牌、封装、年份、数量
					String content = "采购订单号为：{0},供应商为：{1},型号为：{2},，品牌为：{3}，封装：{4}，年份：{5}，数量:{6},<br>请及时为供应商评分，<a href=\"/supplier/pf.jsp?co_number={7}&co_name={8}\">供应商评分</a>"; // 邮件内容
					// 在邮件标题加上供应商、型号、数量
					String title = "{0},{1},{2}已入库";
					content = MessageFormat
							.format(content, cgNumber, pro_supplier,
									pro_model1, pro_sup_number, pro_number,
									pro_name, num1, sp_number, pro_supplier);
					title = MessageFormat.format(title, pro_supplier,
							pro_model1, num1);

					sendmail.sendMail(trans, title, content, cgname, username);
				}
			}// 1

			//
			String supsql = "update in_warehouse set states='全部入库'  where id='"
					+ id + "'";
			trans.update(supsql);
			// 更新采购订单信息
			updateProcure(trans, rkhouseList);
			trans.commit();
			return msg;
		} catch (Exception e) {
			logger.error("入库失败：", e);
			trans.rollback();
			return e.toString();
		} finally {
			trans.close();
		}

	}
	
	/**
	 * 重新入库
	 * 
	 * @param id1
	 * @param username
	 * @param sp_number
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public String recheckIn(String id, String username) throws Exception {

		if (username == null) {
			return "用户还没有登陆";
		}

		Transaction trans = Transaction.createTrans(DatasourceStore
				.getDatasource("default"));

		try {

			Date date = new Date(System.currentTimeMillis());

			// 用来记录结果，结果为空时，表示成功入库
			// 否则，应该对入库结果进行处理
			String msg = null;

			Rkhouse rkhouse = dao.getRkhouse(id);

			String parentId = rkhouse.getPro_rk_num();

			InWarehouse inWarehouse = this.getInWarehouseById(trans, parentId);

			String pro_addr = rkhouse.getPro_addr();
			String pro_model1 = rkhouse.getPro_model().trim();
			String pro_name = rkhouse.getPro_name().trim();
			String remark = rkhouse.getRemark();

			boolean isWarehouseExist = dao.exist(trans, pro_model1,
					pro_addr, pro_name, remark);
			if (!isWarehouseExist) {
				this.addWarehouseByRkhouse(trans, rkhouse, username, pro_addr,
						inWarehouse.getNumber());
			}

			Date end = new Date(System.currentTimeMillis());
			logger.info("rk starttime is " + date.getTime() + " end time is "
					+ end.getTime());
			trans.commit();
			return msg;
		} catch (Exception e) {
			trans.rollback();
			return e.getMessage();
		} finally {
			trans.close();
		}

	}
	

		private void updatePurchasingStatus(Transaction trans, int id, String status)
				throws SQLException {
			String sql = "update procure set l_spqk = ? where id = ?";
			trans.update(sql, new Object[] { status, id });
		}

		private void updateProcure(Transaction trans, List<Rkhouse> rkhouseList)
				throws Exception {

			List<String> purchasings = new ArrayList<String>();
			for (Rkhouse r : rkhouseList) {
				String purchasingNumber = r.getPro_types();
				if (!purchasings.contains(purchasingNumber)) {
					purchasings.add(purchasingNumber);
				}
			}

			for (String number : purchasings) {
				int purchasingId = purchasingDao.getPurchasingIdByNumber(trans,
						number);
				List<PurchasingProduct> productList = purchasingDao
						.getProductByPurchasingId(trans, purchasingId);

				boolean isAllIn = true;
				for (PurchasingProduct product : productList) {
					int purchaseProductId = product.getId();
					int inNum = purchasingDao.getRkNum(trans, purchaseProductId);
					if (inNum < product.getNum()) {
						isAllIn = false;
					}
				}
				if (isAllIn) {
					this.updatePurchasingStatus(trans, purchasingId, "已入库");
				}
			}

		}
		
		public List<PurchasingProduct> getPurchasingProducts(String id)
				throws NumberFormatException, Exception {
			return purchasingDao.getPurchasingProductListByParentId(id);
		}
		
		public void splitRkhouse(String id, String num1, String num2)
				throws Exception {
			dao.splitRkhouse(id, num1, num2);
		}
		
		@SuppressWarnings("rawtypes")
		public boolean addRkhouse(Transaction trans, Map product,
				Purchasing purchasing, String ddid1,Integer leftnum) throws Exception {

			int productId = (Integer) product.get("id");
			int purchasingNum = purchasingDao.getProductNum(trans,productId);
			int inNum = purchasingDao.getRkNum(trans,productId);
			int toInNum = purchasingNum - inNum;
			
			if(toInNum<leftnum) {
				return false;
			}
			
			if (toInNum > 0) {
				dao.addRkhouse(trans, product, purchasing, ddid1, leftnum);
				return true;
			}
			return false;
		}
		
		@SuppressWarnings("rawtypes")
		private void updatePurchasingStatus(Transaction trans, String id)
				throws Exception {

			List proList = this.getPurchasingProductByParentId(trans, id);
			boolean existIn = false;
			for (int i = 0; i < proList.size(); i++) {
				Map pro = (Map) proList.get(i);
				Integer proid = (Integer) pro.get("id");
				int inNum = purchasingDao.getRkNum(trans, proid);
				if (inNum > 0) {
					existIn = true;
					break;
				}
			}
			if (!existIn) {
				String sql = "update procure set l_spqk = ? where id = ?";
				trans.update(sql, new Object[] { "合同已确认", id });
			}

		}


		public Map<String, Object> getRkhouseById(Transaction trans, String id)
				throws Exception {
			String sql = "select * from rkhouse where id = ?";
			return trans.queryForMap(sql, new Object[] { id }, true);
		}

		/**
		 * 删除入库单产品
		 * 
		 * @param id
		 * @throws Exception
		 */
		public void delRkhouse(String id) throws Exception {

			Transaction trans = this.getTransaction();
			try {
				Map<String, Object> map = this.getRkhouseById(trans, id);
				String cgproId = (String) map.get("pro_rate");
				
				Map<String, Object> purchasingProduct = this.getPurchasingProduct(
						trans, Integer.parseInt(cgproId));
				dao.delRkhouse(trans, id);
				String ddid = (String) purchasingProduct.get("ddid");
				updatePurchasingStatus(trans, ddid);
				trans.commit();

			} catch (Exception ex) {
				trans.rollback();
				throw ex;
			} finally {
				if (trans != null) {
					trans.close();
				}
			}
		}
		

		private Map<String, Object> getPurchasingProduct(Transaction trans,
				Integer id) throws Exception {
			String sql = "select * from cgpro where id = ?";
			return trans.queryForMap(sql, new Object[] { id }, true);
		}


		public Purchasing getPurchasing(String id) throws SQLException {
			return purchasingDao.getPurchasingById(id);
		}

		public void purchasingProductIn(String fyid, String ddid, Integer productId, Integer leftnum
			) throws Exception {

			Transaction trans = this.getTransaction();

			try {
				Purchasing purchasing = getPurchasing(fyid);
				Map<String, Object> pro = getPurchasingProduct(trans, productId);
				addRkhouse(trans, pro, purchasing, ddid,leftnum);
				trans.commit();
			} catch (Exception ex) {
				trans.rollback();
				throw ex;
			} finally {
				trans.close();
			}

		}
		
		
		
		/**
		 * 根据入库的产品，来修改仓库的情况
		 * 
		 * @param rkhouse
		 * @throws Exception
		 */
		@SuppressWarnings("rawtypes")
		public String updateWarehouse(Transaction trans, Rkhouse rkhouse,
				String pro_addr, String username, String inWarehouseNumber)
				throws Exception {

			String msg = null;

			String pro_model1 = rkhouse.getPro_model().trim();

			int num1 = rkhouse.getPro_num();

			Map warehouseMap = dao.getWarehouseSingle(trans, pro_model1);

			// 如果产品已经入库的情况下，更新库信息
			if (warehouseMap != null && warehouseMap.size() > 0) {

				Integer wid = (Integer) warehouseMap.get("id");
				int pro_num = ((BigDecimal) warehouseMap.get("pro_num")).intValue();
				int finalNum = pro_num + num1;
				BigDecimal pro_price = (BigDecimal) warehouseMap.get("pro_num");

				InOutLog log = new InOutLog();
				log.setOrginNum(pro_num);
				log.setChangeNum(num1);
				log.setFinalNum(finalNum);
				log.setNumber(inWarehouseNumber);
				log.setUsername(username);
				log.setWid(wid);
				log.setRemark("仓库入库-更新库存");
				this.updateWarehosue(trans, log);

				if (pro_price.doubleValue() == 0) {

					double price = rkhouse.getPro_price();
					String hb = rkhouse.getPro_hb();
					BigDecimal rate = CurrentUtils.getCurrentRate(hb);
					if (rate != null && rate.doubleValue() > 0) {
						BigDecimal b = new BigDecimal(price);
						price = rate.multiply(b).doubleValue();
					}
					String upsql = "update warehouse set pro_price=" + price
							+ ",pro_yyfw='" + price + "' where id = ?";
					trans.update(upsql, new Object[] { wid });
				}

			}
			// 新增一条库信息
			else {
				addWarehouseByRkhouse(trans, rkhouse, username, pro_addr,
						inWarehouseNumber);
			}

			return msg;
		}

		public void updateRkhouseStatus(Transaction trans, Rkhouse rkhouse)
				throws Exception {
			dao.updateRkhouseStatus(trans, rkhouse.getId());
		}
		
		public void addWarehouseByRkhouse(Transaction trans, Rkhouse rkhouse,
				String username, String pro_addr, String number) throws Exception {

			String pro_number = rkhouse.getPro_number();
			String pro_supplier = rkhouse.getPro_supplier();
			String pro_sup_number = rkhouse.getPro_sup_number();
			String name1 = username;
			double price = rkhouse.getPro_price();
			String hb = rkhouse.getPro_hb();
			String pro_unit1 = rkhouse.getPro_unit();
			BigDecimal rate = CurrentUtils.getCurrentRate(hb);
			String pro_model1 = rkhouse.getPro_model().trim();
			String pro_name = rkhouse.getPro_name().trim();
			String remark = rkhouse.getRemark();
			if (rate != null && rate.doubleValue() > 0) {
				BigDecimal b = new BigDecimal(price);
				price = rate.multiply(b).doubleValue();
			}
			int num1 = rkhouse.getPro_num();

			String strSQLrw = "insert into warehouse(pro_model,pro_gg,pro_name,"
					+ "pro_number,pro_type,saleprice,price_hb,pro_s_num,pro_num,pro_unit, "
					+ "pro_price,pro_supplier,pro_addr,pro_remark,sjnum,yqnum,yqdate,pro_secid,"
					+ "pro_sup_number,pro_min_num,pro_max_num,sale_states,sale_min_price,"
					+ "sale_max_price,pro_date,pro_man,pro_ms,pro_jstx,pro_yyfw,pin,js_price,"
					+ "zk_price,th_number,pro_weight_unit,batch_no) values(?,?,?,'','IC','0','CNY','0',?,?,?,?,?,?,'0','0','','',?,'0','0','常销产品','0','0',?,?,'','',?,'1','0',"
					+ "'0',null,'','')";
			Object[] params = new Object[] { pro_model1, pro_number, pro_name,
					num1, pro_unit1, price, pro_supplier, pro_addr, remark,
					pro_sup_number, DateUtil.getCurrentDateSimpleStr(), name1,
					String.valueOf(price) };
			trans.update(strSQLrw, params);

			String sqlGetLastWarehouse = "select top 1 id from warehouse order by id desc";
			int wid = trans.queryForInt(sqlGetLastWarehouse);

			InOutLog log = new InOutLog();
			log.setOrginNum(0);
			log.setChangeNum(num1);
			log.setFinalNum(num1);
			log.setNumber(number);
			log.setUsername(username);
			log.setWid(wid);
			log.setRemark("仓库入库");
			this.updateWarehosue(trans, log);

		}
		

		public void changeInPro(String id, String num) throws Exception {
			String sql = "update rkhouse set pro_num = ? where id = ?";
			Transaction trans = this.getTransaction();
			try {
				Map<String, Object> map = this.getRkhouseById(trans, id);
				Integer status = (Integer) map.get("status");
				if (status == 1) {
					throw new Exception("产品已入库！不能修改产品信息");
				}

				String cgproId = (String) map.get("pro_rate");
				
				Map<String, Object> purchasingProduct = this.getPurchasingProduct(
						trans, Integer.parseInt(cgproId));

				Integer cgnum = (Integer) purchasingProduct.get("num");
				if (cgnum < Integer.valueOf(num)) {
					throw new Exception("入库产品不能大于采购产品的数量！");
				}

				trans.update(sql, new Object[] { num, id });
				trans.commit();
			} catch (Exception ex) {
				trans.rollback();
				throw ex;
			} finally {
				trans.close();
			}

		}
		

		

		public void updatePurchasingInStatus(String purchasingId, String status) throws NumberFormatException, Exception {
			purchasingDao.updateStatus(purchasingId, status);
		}

		public void editInNum(String id, String num) throws Exception {
			dao.editInNum(id, num);
		}
		
		public void delInPro(String id) throws Exception {
			dao.delInPro(id);
		}

}
