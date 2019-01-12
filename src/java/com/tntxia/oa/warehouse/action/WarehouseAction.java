package com.tntxia.oa.warehouse.action;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.jdbc.Transaction;
import com.tntxia.oa.mail.entity.Mail;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.warehouse.PdItem;
import com.tntxia.oa.warehouse.WarehouseStokingManager;
import com.tntxia.oa.warehouse.dao.WarehouseDao;
import com.tntxia.oa.warehouse.entity.InOutLog;
import com.tntxia.oa.warehouse.entity.WarehouseType;
import com.tntxia.oa.warehouse.service.WarehouseService;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.util.DatasourceStore;

public class WarehouseAction extends CommonAction {
	
	private WarehouseDao warehouseDao;

	private WarehouseService warehouseService;

	public void setWarehouseService(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public void setWarehouseDao(WarehouseDao warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	private Map<String,Object> getWarehouse(Transaction trans,String model) throws Exception{
		String sql = "select * from warehouse where pro_model = ?";
		return trans.queryForMap(sql, new Object[]{model}, true);
	}
	
	private void addWarehouseNum(Transaction trans,Integer proId,Integer num) throws SQLException{
		String sql = "update warehouse set pro_num = pro_num + ? where id = ?";
		trans.update(sql, new Object[]{num,proId});
	}

	/**
	 * 退货的产品入库
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView refundIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Transaction trans = null;
		
		try{
			
			DataSource ds = DatasourceStore.getDatasource("default");
			trans = Transaction.createTrans(ds);
			Map<String, Object> resultMap = new HashMap<String, Object>();

			HttpSession session = request.getSession();

			String name1 = (String) session.getAttribute("username");

			java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			String currentDate = simple.format(new java.util.Date());

			int ddid1 = ServletRequestUtils.getIntParameter(request, "ddid", 0);
			String sqldd = "select  number,coname,senddate,man,money,datetime,dept,deptjb,sub from th_table where id=? ";
			Map thTable = trans.queryForMap(sqldd, new Object[]{ddid1},true);
			if (thTable==null) {
				throw new Exception("退货合同不存在，请与管理员联系");
			}

			String thnumber = (String) thTable.get("number");
			String coname = (String) thTable.get("coname");
			String co_number = (String) thTable.get("senddate");
			String sales_man = (String) thTable.get("man");
			String money1 = (String) thTable.get("money");
			Date dd_date = (Date) thTable.get("datetime");
			String dept = (String) thTable.get("dept");
			String deptjb = (String)thTable.get("deptjb");
			String sub =  (String) thTable.get("sub");

			
			
			StringBuffer mailContent = new StringBuffer();
			mailContent.append("您好！ \n");
			mailContent.append("退货单号：" + thnumber + " 销售单号：" + sub);

			String strSQLpro = "select * from th_pro where  ddid=?  and  num!=s_num";
			List proList = trans.queryForList(strSQLpro,new Object[]{ddid1} ,true);

			for (int i=0;i<proList.size();i++) {
				Map map = (Map) proList.get(i);
				int ddproid = (Integer) map.get("id");
				String pro_model = ((String) map.get("epro")).trim();

				String pro_name = (String)map.get("cpro");

				String pro_number =((String)map.get("fypronum")).trim();
				
				String supplier = (String) map.get("supplier");
				
				int num = (Integer) map.get("num");
				
				mailContent.append("产品型号：" + pro_model + " 厂家： "
						+ supplier + " 封装： " + pro_name + " 批号："
						+ pro_number + " 数量：" + num + "\n");

				int s_num = (Integer)map.get("s_num");
				
				int num1 = num - s_num;

				String punit =(String) map.get("unit");
				BigDecimal saleprice = (BigDecimal) map.get("salejg");
				String salehb = (String)map.get("pricehb");
				

				String wid = (String)map.get("wid");
				String fy_states = (String)map.get("fy_states");

				String username = (String) session.getAttribute("username");

				Map warehouse = this.getWarehouse(trans, pro_model);

				if (warehouse == null) {
					throw new Exception("仓库暂无该产品或批号不正确:"+pro_model);
				}

				Integer proId = (Integer) warehouse.get("id");
				this.addWarehouseNum(trans, proId, num1);
				
				int orginNum = ((BigDecimal) warehouse.get("pro_num")).intValue();
				InOutLog log = new InOutLog();
				log.setOrginNum(orginNum);
				log.setChangeNum(num1);
				log.setFinalNum(orginNum+num1);
				log.setNumber(thnumber);
				log.setUsername(username);
				log.setRemark("销售退货");
				addInOutLog(trans, log);

				int no = 1;
				
				SimpleDateFormat simple1 = new SimpleDateFormat(
						"yyMM");
				String number = simple1.format(new java.util.Date());
				String strSQL1 = "select max(in_no)  from outhouse where pro_out_num like ? ";
				int in_no = trans.queryForInt(strSQL1, new Object[]{number + "%"});
				no = in_no + 1;
				
				String sno1 = "";
				if (no < 10) {
					sno1 = "000";
				} else if ((10 <= no) & (no < 100)) {
					sno1 = "00";
				} else if ((100 <= no) & (no < 1000)) {
					sno1 = "0";
				} else
					sno1 = "";
				

				String strSQLq = "insert into outhouse(pro_fynum,pro_coname,pro_model,pro_name,pro_num,pro_unit,pro_supplier,pro_datetime,"
						+ "pro_number,slinkman,slinktel,states,ddid,remark,wid,pro_coname_num,pro_sales_price,pro_price_hb,pro_rate_types,pro_rate,pro_out_num,in_no) values('"
						+ thnumber
						+ "','"
						+ coname
						+ "','"
						+ pro_model
						+ "','"
						+ pro_name
						+ "','"
						+ num1
						+ "','"
						+ punit
						+ "','"
						+ supplier
						+ "','"
						+ currentDate
						+ "','"
						+ pro_number
						+ "','','"
						+ wid
						+ "','已出库','"
						+ ddid1
						+ "','"
						+ fy_states
						+ "','2','"
						+ co_number
						+ "','"
						+ saleprice.toString()
						+ "','"
						+ salehb
						+ "','"
						+ wid
						+ "','0','"
						+ number
						+ ""
						+ sno1
						+ "" + no + "','" + no + "')";
				
				trans.update(strSQLq);
				
				String strSQLwf = "update  th_pro set s_num='" + num
						+ "',s_c_num='" + num + "',s_tr_date='" + currentDate
						+ "',fy_states='待发运'  where id='" + ddproid + "' ";
				trans.update(strSQLwf);
				

			}
			String strSQLthg = "delete from gathering_refund where orderform='"
					+ thnumber + "'";
			trans.update(strSQLthg);
			
			String strSQLsk = "insert into gathering_refund(fyid,invoice,orderform,coname,yjskdate,sjdate,sjskdate,ymoney,states,mode,datet,moneytypes,smoney,bank,bankaccounts,sale_man,sale_dept,deptjb,co_number,i_man) values('"
					+ ddid1
					+ "','"
					+ thnumber
					+ "','"
					+ thnumber
					+ "','"
					+ coname
					+ "','"
					+ dd_date
					+ "','"
					+ dd_date
					+ "','"
					+ dd_date
					+ "','0','已入库','退货','0','"
					+ money1
					+ "','0','0','','"
					+ sales_man
					+ "','"
					+ dept
					+ "','"
					+ deptjb
					+ "','"
					+ co_number
					+ "','0')";
			trans.update(strSQLsk);
			
			String ddsqls = "update th_table set state='已入库' where id='" + ddid1
					+ "'";
			trans.update(ddsqls);

			resultMap.put("success", true);
			resultMap.put("close", true);

			Mail mail = new Mail();
			mail.setTitle("退货单" + thnumber + "已入库");
			mail.setTo(sales_man);

			mailContent.append(" 致 礼！ " + name1 + currentDate);

			String t = String.valueOf(System.currentTimeMillis());

			session.setAttribute(t, mail);

			mail.setContent(mailContent.toString());

			trans.commit();
			return this.exportSuccessJSON(response);
		}catch(Exception ex){
			trans.rollback();
			ex.printStackTrace();
			return this.exportErrorJSON(response, ex.toString());
		}finally{
			if(trans!=null){
				trans.close();
			}
		}
		
	}

	/**
	 * 返回退货待入库
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView returnRefundIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		String ddnumber1 = request.getParameter("number").trim();
		String ware_remark = request.getParameter("ware_remark");
		String man = request.getParameter("man");
		String ddid = request.getParameter("ddid");
		String strSQLpro = "select  * from outhouse where  pro_fynum='"
				+ ddnumber1 + "'";
		ResultSet prs = db.executeQuery(strSQLpro);

		while (prs.next()) {
			int ddproid = prs.getInt("id");
			int num = prs.getInt("pro_num");
			String wid = prs.getString("pro_rate_types").trim();
			int pro_num = 0;
			String sql = "select pro_num from warehouse where id='" + wid + "'";
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				pro_num = rs.getInt("pro_num");
			}
			int zpro_num = pro_num - num;
			String strSQLw = "update warehouse set pro_num='" + zpro_num
					+ "'  where id='" + wid + "' ";
			boolean tw = db.executeUpdate(strSQLw);
			if (!tw) {
				resultMap.put("success", false);
				resultMap.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
				return new ModelAndView("common/result", resultMap);

			}
			String strSQLout = "delete from outhouse where id='" + ddproid
					+ "'  and   pro_fynum='" + ddnumber1 + "'";
			db.executeUpdate(strSQLout);

		}

		String strSQLwf = "update th_pro set s_num='0',s_c_num='0',s_tr_date='',fy_states='待发运'  where ddid='"
				+ ddid + "' ";
		boolean twf = db.executeUpdate(strSQLwf);
		if (!twf) {
			resultMap.put("success", false);
			resultMap.put("message", "更新失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", resultMap);

		}

		String strSQL = "update  th_table  set state='未批准',w_remark='"
				+ ware_remark + "'   where number='" + ddnumber1 + "'";
		String strSQL8 = "delete from gathering_refund where orderform='"
				+ ddnumber1 + "'   and  states='退货'";
		db.executeUpdate(strSQL8);
		db.executeUpdate(strSQL);
		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String name1 = (String) session.getAttribute("username");
		String dept = (String) session.getAttribute("dept");
		String deptjb = (String) session.getAttribute("deptjb");
		String strSQLs = "insert into sendmail values('" + man
				+ "','','','仓库退回:编号为:" + ddnumber1 + "退货单','退回原因:"
				+ ware_remark + "','" + name1 + "','" + deptjb + "','" + dept
				+ "','" + currentDate + "','已发送','','','')";
		boolean t1 = db.executeUpdate(strSQLs);
		if (!t1) {
			resultMap.put("success", false);
			resultMap.put("message", "邮件通知发送失败!");
			return new ModelAndView("common/result", resultMap);

		}

		resultMap.put("success", true);
		resultMap.put("openerReload", true);
		resultMap.put("close", true);

		return new ModelAndView("common/result", resultMap);

	}

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int intPage;
		java.lang.String strPage;
		strPage = request.getParameter("page");

		if (strPage == null) {
			intPage = 1;
		} else {
			intPage = java.lang.Integer.parseInt(strPage);
			if (intPage < 1)
				intPage = 1;
		}
		

		List<WarehouseType> res = new ArrayList<WarehouseType>();

		

		return this.exportJSONObject(response, res);

	}

	/**
	 * 调货入库列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView listSampleDh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.exportJSONObject(response, warehouseDao.getSampleDhList());

	}
	
	

	

	/**
	 * 入库时，查找采购单产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView purchasingProductSearch(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		String id = request.getParameter("id");
//		List<PurchasingProduct> proList = warehouseService
//				.getPurchasingProducts(id);
//
//		Map<String, Object> res = new HashMap<String, Object>();
//		res.put("proList", proList);
//		res.put("gp", this.getGPRight(request));
//
//		return super.getResult("warehouse/in/purchasingProductSearch", res);
//
//	}

	

//	public ModelAndView splitInNum(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String id = request.getParameter("id");
//		String num1 = request.getParameter("num1");
//		String num2 = request.getParameter("num2");
//		warehouseService.splitRkhouse(id, num1, num2);
//		return this.exportSuccessJSON(response);
//	}

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
						log.getUsername(), log.getWid(), log.getRemark() });
	}

	public ModelAndView viewPd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		String status = request.getParameter("status");
		String pro_model = request.getParameter("pro_model");

		WarehouseStokingManager warehouseManager = new WarehouseStokingManager();

		List<PdItem> list = warehouseManager.getPdItemList(
				Integer.parseInt(status), pro_model);

		int pageNo = 1;
		int pageSize = 50;

		if (request.getParameter("page") != null) {
			String strPage = request.getParameter("page");
			pageNo = Integer.parseInt(strPage);
		}

		int total = list.size();
		int pageTotal = total % pageSize == 0 ? (total / pageSize) : (total
				/ pageSize + 1);

		model.put("pageNo", pageNo);
		model.put("status", status);
		model.put("pageTotal", pageTotal);

		return this.exportJSONObject(response, list);
	}

	/**
	 * 
	 * 修改入库单产品信息
	 * 
	 * 需要检查是否已经入库，如果已经入库的产品，不允许修改入库的信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView changeInPro(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		String id = request.getParameter("id");
//		String num = request.getParameter("num");
//		try {
//			warehouseService.changeInPro(id, num);
//		} catch (Exception e) {
//			return this.exportErrorJSON(response, e.toString());
//		}
//		return this.exportSuccessJSON(response);
//
//	}

	

	/**
	 * 搜索仓库产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView getInWarehouseInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Map map = warehouseService.getInWarehouseInfo(id);
		return this.exportJSONObject(response, map);
	}

	public ModelAndView getRkList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		return this.exportJSONObject(response, warehouseService.getRkList(id));
	}
	
	/**
	 * 查询待入库订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView listWaitRk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String model = ParamUtils.unescape(request, "model");
		String coname = ParamUtils.unescape(request, "coname");
		String number = ParamUtils.unescape(request, "number");
		String supplier =  ParamUtils.unescape(request, "supplier");

		PageBean pageBean = this.getPageBean(request, 50);
		
		Map<String,Object> params = new HashMap<String,Object>();
		
		params.put("model", model);
		params.put("coname", coname);
		params.put("number", number);
		params.put("supplier", supplier);
		
		params.put("pageBean", pageBean);
		
		params.put("method", "listWaitRk");
		
		HttpTransfer httpTrans = HttpTransferFactory.generate("purchasingcenter");
		
		return this.exportJSONObject(response, httpTrans.getMap("purchasing", params));
		
		
	}

}
