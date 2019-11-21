package com.tntxia.oa.purchasing.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.common.CommonOpen;
import com.tntxia.oa.purchasing.dao.PurchasingDao;

import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.oa.purchasing.entity.PurchasingRefundProduct;
import com.tntxia.oa.sale.dao.SaleDao;
import com.tntxia.oa.supplier.Supplier;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.util.CommonAction;
import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.WarehouseManager;

public class PurchasingAction extends CommonAction {

	private SaleDao saleDao;

	private PurchasingDao purchasingDao;

	private UserDao userDao;

	public SaleDao getSaleDao() {
		return saleDao;
	}

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;
	}

	public void setPurchasingDao(PurchasingDao purchasingDao) {
		this.purchasingDao = purchasingDao;
	}

	/**
	 * 采购退货出库 单个
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView purchasingRefundFromWarehouseSingle(
			HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		DBConnection einfodb = new DBConnection();

		int id = ServletRequestUtils.getIntParameter(request, "id");

		PurchasingRefundProduct refundProduct = purchasingDao
				.getPurchasingRefundProductById(id);

		String purchasingNumber = refundProduct.getPurchasingNumber();

		String th_num = request.getParameter("th_num");
		String wid = request.getParameter("wid");

		WarehouseManager warehouseManager = new WarehouseManager();

		Warehouse query = new Warehouse();
		query.setId(wid);
		Warehouse warehouse = warehouseManager.getWarehouseSingle(query);

		if (warehouse.getPro_num() < Integer.valueOf(th_num)) {
			result.put("success", false);
			result.put("message", "仓库数量少于退货数量！！");
			return new ModelAndView("common/result", result);

		}

		String strSQLwf = "update  th_pro_supplier set s_num=s_num+" + th_num
				+ " where id='" + id + "' ";
		boolean twf = einfodb.executeUpdate(strSQLwf);
		if (!twf) {
			result.put("success", false);
			result.put("message", "更新退货产品失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);

		}

		String strSQLw = "update warehouse set pro_num=pro_num-" + th_num
				+ " where id=" + wid;
		boolean tw = einfodb.executeUpdate(strSQLw);
		if (!tw) {
			result.put("success", false);
			result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);

		}

		String sql = "update procure set l_spqk = '部分退货' where number = '"
				+ purchasingNumber + "'";
		tw = einfodb.executeUpdate(sql);
		if (!tw) {
			result.put("success", false);
			result.put("msg", "更新采购订单状态失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);
		}

		einfodb.close();

		result.put("success", true);
		result.put("msg", "退货成功");

		return new ModelAndView("common/result", result);
	}

	/**
	 * 采购退货出库 全部
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public ModelAndView purchasingRefundFromWarehouseAll(
			HttpServletRequest request, HttpServletResponse arg1)
			throws Exception {

		DBConnection einfodb = new DBConnection();

		Map<String, Object> result = new HashMap<String, Object>();

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String ddid1 = request.getParameter("ddid");
		String sqldd = "select  number,coname,co_number,man,money,datetime,dept,deptjb,sub from th_table_supplier where id='"
				+ ddid1 + "'";
		ResultSet rsdd = einfodb.executeQuery(sqldd);
		if (!rsdd.next()) {
			result.put("success", false);
			result.put("msg", "退货出库单不存在");
			return new ModelAndView("common/result", result);

		}

		String refundState = "全部退货";

		String thnumber = rsdd.getString("number");
		String coname = rsdd.getString("coname");
		String co_number = rsdd.getString("co_number");
		String sales_man = rsdd.getString("man");
		String allPro_model = "";
		String allPro_name = "";
		String allNum = "";
		String allSupplier = "";
		String sub = rsdd.getString("sub");

		List<String> purchasingNumberList = new ArrayList<String>();

		String strSQLpro = "select id,epro,cpro,fypronum,s_c_num,s_num,num,unit,salejg,pricehb,supplier,wid,fy_states,cg_number,th_number,remark from th_pro_supplier where  ddid='"
				+ ddid1 + "'";
		ResultSet prs = einfodb.executeQuery(strSQLpro);

		while (prs.next()) {
			int ddproid = prs.getInt("id");
			String pro_model = prs.getString("epro").trim();
			allPro_model += pro_model + ",";
			String pro_name = prs.getString("cpro");
			allPro_name += pro_name + ",";
			String pro_number = "0";
			String fypronum = prs.getString("fypronum");
			if (fypronum != null) {
				pro_number = fypronum.trim();
			}

			int s_num = prs.getInt("s_num");
			int num = prs.getInt("num");
			int num1 = num - s_num;

			String punit = prs.getString("unit");
			String saleprice = prs.getString("salejg");
			String salehb = prs.getString("pricehb");
			String supplier = prs.getString("supplier");
			allSupplier += supplier + ",";
			String wid = prs.getString("wid");
			String fy_states = prs.getString("fy_states");
			String cg_number = prs.getString("cg_number");
			purchasingNumberList.add(cg_number);
			String th_number = prs.getString("th_number");
			String pro_remark = prs.getString("remark");

			String sql = null;

			if (th_number != null) {
				sql = "select pro_num,pro_addr from warehouse where pro_addr='"
						+ wid + "' and pro_model='" + pro_model
						+ "'  and  pro_name='" + pro_name
						+ "' and  th_number='" + th_number + "'";
			} else {
				sql = "select pro_num,pro_addr from warehouse where pro_addr='"
						+ wid + "' and pro_model='" + pro_model
						+ "'  and  pro_name='" + pro_name + "'";
			}

			int warehouseid = Integer.valueOf(request.getParameter("wid"
					+ ddproid));

			sql = "select pro_num,pro_addr from warehouse where id="
					+ warehouseid + " ";

			ResultSet rs = einfodb.executeQuery(sql);
			if (!rs.next()) {
				result.put("success", false);
				result.put("message", "仓库暂无该产品或批号不正确:" + "产品ID：" + warehouseid
						+ ";" + pro_model + ";批号:" + pro_name + ";备注:"
						+ pro_remark);
				return new ModelAndView("common/result", result);

			}

			int pro_num = rs.getInt("pro_num");
			String proaddr = rs.getString("pro_addr");
			int zpro_num = pro_num - num1;

			allNum += zpro_num + ",";

			if (warehouseid == 0) {
				result.put("success", false);
				result.put("message", "请选择退货产品");
				return new ModelAndView("common/result", result);

			} else {
				if (zpro_num < 0) {
					result.put("success", false);
					result.put("message", "库存不足:" + zpro_num);
					return new ModelAndView("common/result", result);

				}
				String strSQLw = "update warehouse set pro_num='" + zpro_num
						+ "' where id=" + warehouseid;
				boolean tw = einfodb.executeUpdate(strSQLw);
				if (!tw) {
					result.put("success", false);
					result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
					return new ModelAndView("common/result", result);

				}
			}

			int no = 1;
			java.sql.ResultSet sqlRsto;
			java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
					"yyMM");
			String number = simple1.format(new java.util.Date());
			String strSQL1 = "select  *  from outhouse where pro_out_num like '"
					+ number + "%'   order by in_no desc";
			sqlRsto = einfodb.executeQuery(strSQL1);
			if (sqlRsto.next()) {
				no = sqlRsto.getInt("in_no") + 1;
			}
			String sno1 = "";
			if (no < 10) {
				sno1 = "000";
			} else if ((10 <= no) & (no < 100)) {
				sno1 = "00";
			} else if ((100 <= no) & (no < 1000)) {
				sno1 = "0";
			} else
				sno1 = "";
			sqlRsto.close();

			String strSQLq = "insert into outhouse([pro_fynum],[pro_coname],[pro_model],[pro_name],[pro_num],[pro_unit],[pro_supplier]"
					+ ",[pro_datetime],[pro_number],[slinkman],[slinktel],[states],[ddid],[remark],[wid],[pro_coname_num],[pro_sales_price]"
					+ ",[pro_price_hb],[pro_rate_types],[pro_rate],[pro_out_num],[in_no]) values('"
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
					+ "','-"
					+ pro_number
					+ "','','"
					+ proaddr
					+ "','已出库','"
					+ ddid1
					+ "','"
					+ fy_states
					+ "','2','"
					+ co_number
					+ "','"
					+ saleprice
					+ "','"
					+ salehb
					+ "','"
					+ wid
					+ "','0','"
					+ number
					+ ""
					+ sno1 + "" + no + "','" + no + "')";
			boolean tq = einfodb.executeUpdate(strSQLq);
			if (!tq) {
				result.put("success", false);
				result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
				return new ModelAndView("common/result", result);
			}
			String strSQLwf = "update  th_pro_supplier set s_num='" + num
					+ "',s_c_num='" + num + "',s_tr_date='" + currentDate
					+ "',fy_states='待发运'  where id='" + ddproid + "' ";
			boolean twf = einfodb.executeUpdate(strSQLwf);
			if (!twf) {
				result.put("success", false);
				result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
				return new ModelAndView("common/result", result);
			}

			try {
				PurchasingProduct purchasingProduct = purchasingDao
						.getPurchasingProductListByNumberAndModel(cg_number,
								pro_model);

				if (purchasingProduct.getNum() > num) {
					refundState = "部分退货";
				}
			} catch (Exception e) {
				refundState = "全部退货";
			}

		}

		String ddsqls = "update th_table_supplier set state='已出库' where id='"
				+ ddid1 + "'";
		boolean tssu = einfodb.executeUpdate(ddsqls);
		if (!tssu) {
			result.put("success", false);
			result.put("message", "更新库存失败,你所输入的内容超出系统范围或输入类型不符!");
			return new ModelAndView("common/result", result);
		}

		// 更新采购订单的状态
		for (String pNumber : purchasingNumberList) {
			try {
				purchasingDao.updatePurchasingStatus(refundState, pNumber);
			} catch (Exception e) {

			}

		}

		einfodb.close();

		result.put("success", true);
		result.put("close", false);
		result.put("message", "退货成功");

		List<CommonOpen> openList = new ArrayList<CommonOpen>();

		CommonOpen open1 = new CommonOpen();
		open1.setUrl("/warehouse/main.jsp");
		open1.setTarget("rtop");
		open1.setWinOpt("height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");
		openList.add(open1);

		CommonOpen open2 = new CommonOpen();
		open2.setUrl("/warehouse/sendmail_th2.jsp?id=" + ddid1 + "&coname="
				+ coname + "&ddnumber=" + thnumber + "&co_number=" + co_number
				+ "&sendto=" + sales_man + "&pro_model=" + allPro_model
				+ "&num=" + allNum + "&sub=" + sub + "&supplier=" + allSupplier
				+ "&pro_name=" + allPro_name);
		open2.setTarget("_blank");
		open2.setWinOpt("height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");
		openList.add(open2);
		result.put("openList", openList);

		return new ModelAndView("common/result", result);
	}

	/**
	 * 增加供应商退货单的产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addPurchasingRefundPro(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		String fyid = request.getParameter("fyid");

		String money = request.getParameter("money");

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		String coname = request.getParameter("coname");

		String cgnumber = request.getParameter("cgnumber");

		String t[] = request.getParameterValues("checkpro");

		if (t != null) {
			for (int i = 0; i < t.length; i++) {
				String strSQLpro = "select id,wid,epro,cpro,pro_number,num,unit,selljg,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,rate,supplier,remark from cgpro where id='"
						+ t[i] + "' ";

				ResultSet prs = db.executeQuery(strSQLpro);

				while (prs.next()) {
					int id = prs.getInt(1);
					String wid = prs.getString("wid").trim();
					String pro_model1 = prs.getString("epro").trim();
					String pro_name1 = prs.getString("cpro");
					String pro_name2 = prs.getString("pro_number");

					String in_num = request.getParameter("in_num" + t[i]);
					String pro_unit1 = prs.getString("unit");

					double selljg = prs.getDouble("selljg");

					String supplier = prs.getString("supplier");
					String remark = prs.getString("remark");

					String strSQLi = "insert into th_pro_supplier(ddid,epro,cpro,num,fypronum,unit,pricehb,fy_states,wid,money,selljg,salejg,rale,rale_types,supplier,fyproall,s_num,s_c_num,remark,cg_number) "
							+ "values('"
							+ fyid
							+ "','"
							+ pro_model1
							+ "','"
							+ pro_name1
							+ "','"
							+ in_num
							+ "','"
							+ pro_name2
							+ "','"
							+ pro_unit1
							+ "','"
							+ money
							+ "','"
							+ coname
							+ "','"
							+ wid
							+ "','"
							+ money
							+ "',"
							+ selljg
							+ ","
							+ selljg
							+ ",0,'','"
							+ supplier
							+ "','no',0,0,'"
							+ remark + "','" + cgnumber + "')";

					boolean ttt = db.executeUpdate(strSQLi);
					if (!ttt) {
						String message = "添加入库产品失败,你所输入的内容超出系统范围或输入类型不符!";
						return getErrorResult(message);
					}

					String strSQLty = "update cgpro set cgpro_num='" + in_num
							+ "',cgpro_sdatetime='" + currentDate
							+ "' where id='" + id + "' ";
					boolean ttt1 = db.executeUpdate(strSQLty);
					if (!ttt1) {
						String message = "修改采购产品信息失败!";
						return getErrorResult(message);

					}
				}
				prs.close();
			}
		}

		boolean ttt1 = db.executeUpdate("update th_table_supplier set coname='"
				+ coname + "',money='" + money + "' where id='" + fyid + "'");

		if (!ttt1) {
			String message = "修改退货单 供应商信息失败!";
			return getErrorResult(message);
		}

		response.sendRedirect("refund/dd-view.jsp?id=" + fyid);

		return null;

	}

	/**
	 * 采购产品信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView productView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		PurchasingProduct product = purchasingDao.getProductById(id);
		return this.getResult("purchasing/proview", product);
	}

	/**
	 * 跳转到增加联系人的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAddContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String number = request.getParameter("co_number");

		Supplier supplier = purchasingDao.getSupplierByNumber(number);

		return this.getResult("supplier/addContact", supplier);
	}

	/**
	 * 跳转到增加联系人的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DBConnection db = new DBConnection();

		String name1 = request.getParameter("name");
		String job1 = request.getParameter("job");
		String mr1 = request.getParameter("mr");
		String email1 = request.getParameter("email");
		String tel1 = request.getParameter("tel");
		String department1 = request.getParameter("department");
		String borndt1 = request.getParameter("borndt");
		String school1 = request.getParameter("school");
		String degree1 = request.getParameter("degree");
		String born1 = request.getParameter("born");
		String co_number = request.getParameter("co_number");
		String coname1 = request.getParameter("coname");
		String coaddr1 = request.getParameter("coaddr");
		String cotel1 = request.getParameter("cotel");
		String cofax1 = request.getParameter("cofax");
		String prorole1 = request.getParameter("prorole");
		String evaluate1 = request.getParameter("evaluate");
		String artifice1 = request.getParameter("artifice");
		String myaddr = request.getParameter("myaddr");
		String waptel1 = request.getParameter("waptel");
		String interest1 = request.getParameter("interest");
		String username1 = request.getParameter("username");
		String rg_date = request.getParameter("rg_date");
		String dept = request.getParameter("dept");
		String deptjb = request.getParameter("deptjb");
		String modman = request.getParameter("modman");
		String mod_date = request.getParameter("mod_date");
		String share1 = request.getParameter("share");
		String beizhu1 = request.getParameter("beizhu");

		String qq = request.getParameter("qq");
		String msn = request.getParameter("msn");
		String skype = request.getParameter("skype");

		String strSQL = "insert into qlinkman(name,job,mr,email,tel,department,bornde,school,degree,born,co_number,coname,coaddr,cotel,cofax,"
				+ "prorole,evaluate,artifice,waptel,myaddr,interest,username,rg_date,dept,deptjb,share,modman,"
				+ "mod_date,beizhu,qq,msn,skype) values('"
				+ name1
				+ "','"
				+ job1
				+ "','"
				+ mr1
				+ "','"
				+ email1
				+ "','"
				+ tel1
				+ "','"
				+ department1
				+ "','"
				+ borndt1
				+ "','"
				+ school1
				+ "','"
				+ degree1
				+ "','"
				+ born1
				+ "','"
				+ co_number
				+ "','"
				+ coname1
				+ "','"
				+ coaddr1
				+ "','"
				+ cotel1
				+ "','"
				+ cofax1
				+ "','"
				+ prorole1
				+ "','"
				+ evaluate1
				+ "','"
				+ artifice1
				+ "','"
				+ waptel1
				+ "','"
				+ myaddr
				+ "','"
				+ interest1
				+ "','"
				+ username1
				+ "','"
				+ rg_date
				+ "','"
				+ dept
				+ "','"
				+ deptjb
				+ "','"
				+ share1
				+ "','"
				+ modman
				+ "','"
				+ mod_date
				+ "','"
				+ beizhu1
				+ "','"
				+ qq
				+ "','"
				+ msn
				+ "','" + skype + "')";
		boolean t = db.executeUpdate(strSQL);
		db.close();
		if (!t) {

			return this.getErrorResult("添加失败,你所输入的内容超出系统范围或输入类型不符!");

		}

		return this.getSuccessResult();
	}

	/**
	 * 获取联系人的列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getContactList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String number = request.getParameter("number");
		return this.exportJSONObject(response,
				purchasingDao.getContactList(number));
	}

	public ModelAndView getPurchasingManList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, userDao.getPurchasingUserList());
	}

}
