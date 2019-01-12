package com.tntxia.oa.servlet;

import infocrmdb.infocrmdb;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.json.simple.JSONObject;

import com.tntxia.oa.warehouse.Warehouse;
import com.tntxia.oa.warehouse.WarehouseManager;
import com.tntxia.oa.inquiry.Inquiry;
import com.tntxia.oa.inquiry.InquiryManager;
import com.tntxia.oa.model.SaleOrder;
import com.tntxia.oa.model.SalePro;
import com.tntxia.oa.procure.CgPro;
import com.tntxia.oa.procure.Procure;
import com.tntxia.oa.procure.ProcureManager;
import com.tntxia.oa.sale.SaleManager;

public class ActionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2188159147082679822L;

	/**
	 * Constructor of the object.
	 */
	public ActionServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	private void getProfitInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		ProcureManager procureManager = new ProcureManager();
		Procure procure = procureManager.getProcure(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", procure.getNumber());
		map.put("sub", procure.getSub());
		ArrayList<Map<String, String>> pros = new ArrayList<Map<String, String>>();
		if (procure.getPros() != null) {
			for (int i = 0; i < procure.getPros().size(); i++) {
				CgPro p = procure.getPros().get(i);
				Map<String, String> pro = new HashMap<String, String>();
				pro.put("epro", p.getEpro());
				pro.put("profit", String.valueOf(p.getProfit()));
				pros.add(pro);
			}
		}

		map.put("pros", pros);
		String result = JSONObject.toJSONString(map);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getInquiry(HttpServletRequest request,
			HttpServletResponse response) {
		String pro_model = request.getParameter("pro_model");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lastDate = new Date(System.currentTimeMillis() - 5 * 24 * 3600
				* 1000);
		ArrayList<Inquiry> inquiryList = InquiryManager.getInquiry(
				sdf.format(lastDate), pro_model);
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (Inquiry i : inquiryList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(i.getId()));
			map.put("coname", i.getConame());
			map.put("man", i.getMan());
			map.put("quotedatetime", i.getQuotedatetime());
			result.add(map);
		}

		Map<String, ArrayList<Map<String, String>>> r = new HashMap<String, ArrayList<Map<String, String>>>();
		r.put("result", result);
		String res = JSONObject.toJSONString(r);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void addSalePro2SupDirect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		

		HttpSession session = request.getSession();
		String getLoginmessage = (String) session.getAttribute("loginSign");
		if (getLoginmessage != "OK") {
			response.sendRedirect("../refuse.jsp");
			return;
		}

		PrintWriter out = response.getWriter();

		infocrmdb einfodb = new infocrmdb();

		
		String number1 = request.getParameter("number");
		
		String ddid1 = request.getParameter("ddid");
		String hb = request.getParameter("hb");
		
		SaleManager saleManager = new SaleManager();
		
		SaleOrder saleOrder = saleManager.getSaleOrder(number1);
		
		String t[] = request.getParameterValues("checkpro");
		
		

		// 如果没有选中销售产品，直接返回
		if (t == null || t.length == 0) {
			return;
		}

		List<SalePro> saleProList = new ArrayList<SalePro>();

		for (int i = 0; i < t.length; i++) {
			SalePro salePro = saleManager.getSalePro(t[i]);
			if (salePro != null) {
				saleProList.add(salePro);
			}
		}

		for (SalePro salePro : saleProList) {

			String strSQLsub = "insert into cgpro(ddid,epro,cpro,pro_number,num,unit,selljg,money,cgpro_ydatetime," +
					"cgpro_num,cgpro_sdatetime,remark,supplier,rate,wid,sale_supplier,sale_remark," +
					"sale_rate,sale_finance,sale_pro_id) values('"
					+ ddid1 + "','"
					+ salePro.getEpro() + "','" + salePro.getCpro() + "','"
					+ salePro.getFypronum() + "','" + salePro.getNum() + "','"
					+ salePro.getUnit() + "','" + salePro.getPrice() + "','"
					+ hb + "','" + salePro.getS_tr_date() + "','0','','"
					+ salePro.getRemark() + "','" + salePro.getSupplier()
					+ "','0','" + salePro.getWid() + "','','','" + saleOrder.getRate() + "','','"
					+ salePro.getId() + "')";

			boolean tq = einfodb.executeUpdate(strSQLsub);
			if (!tq) {
				out.println("添加失败,你所输入的内容超出系统范围或输入类型不符!");
				return;
			}

		}

		String strSQL = "update procure set sub='" + number1 + "' where id='"
				+ ddid1 + "'";
		boolean tp = einfodb.executeUpdate(strSQL);
		if (!tp) {
			out.println("添加失败,你所输入的内容超出系统范围或输入类型不符!");
			return;
		}
		
		einfodb.close();
		
		response.sendRedirect("ddgl/dd-view.jsp?id="+ddid1);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void addSalePro2Sup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");

		PrintWriter out = response.getWriter();

		infocrmdb einfodb = new infocrmdb();

		
		String number = request.getParameter("number");
		
		String ddid1 = request.getParameter("ddid");
		String hb = request.getParameter("hb");
		
		String t[] = request.getParameterValues("checkpro");
		String sub=request.getParameter("sub");
		
		SaleManager saleManager = new SaleManager();
		
		SaleOrder saleOrder = saleManager.getSaleOrder(number);
		String rate = saleOrder.getRate();

		// 如果没有选中销售产品，直接返回
		if (t == null || t.length == 0) {
			return;
		}

		List<SalePro> saleProList = new ArrayList<SalePro>();
		
		List<Warehouse> existWarehouseList = new ArrayList<Warehouse>();

		for (int i = 0; i < t.length; i++) {
			
			SalePro salePro = saleManager.getSalePro(t[i]);
			if (salePro != null) {
				List<Warehouse> warehouseList = new WarehouseManager()
				.getWarehouse(salePro.getEpro());
				existWarehouseList.addAll(warehouseList);
				saleProList.add(salePro);
			}
		}
		
		if(existWarehouseList.size()>0){
			
			VelocityUtil.init(this.getServletContext().getRealPath("/"));
			
			Template template = Velocity.getTemplate("template/confirmAddCgPro.vm");
			VelocityContext context = new VelocityContext();
            context.put("ddid", ddid1);
            context.put("sub", sub);
            context.put("rate", rate);
            context.put("number", number);
            context.put("hb", hb);
            context.put("wList", existWarehouseList);
            context.put("proIds", t);
            template.merge(context, out);
			
			return;
		}

		for (SalePro salePro : saleProList) {

			String strSQLsub = "insert into cgpro(ddid,epro,cpro,pro_number,num,unit," 
			        + "selljg,money,cgpro_ydatetime,cgpro_num,cgpro_sdatetime,remark,supplier,rate,wid,sale_supplier," 
				    + "sale_remark,sale_rate,sale_finance,sale_pro_id) values('" + ddid1 + "','"
					+ salePro.getEpro() + "','" + salePro.getCpro() + "','"
					+ salePro.getFypronum() + "','" + salePro.getNum() + "','"
					+ salePro.getUnit() + "','" + salePro.getPrice() + "','"
					+ hb + "','" + salePro.getS_tr_date() + "','0','','"
					+ salePro.getRemark() + "','" + salePro.getSupplier()
					+ "','0','" + salePro.getWid() + "','','','" + rate + "','"
					+ salePro.getId() + "',"+salePro.getId()+")";

			boolean tq = einfodb.executeUpdate(strSQLsub);
			if (!tq) {
				out.println("添加失败,你所输入的内容超出系统范围或输入类型不符!");
				return;
			}
		}

		String strSQL = "update procure set sub='" + number + "' where id='"
				+ ddid1 + "'";
		boolean tp = einfodb.executeUpdate(strSQL);
		if (!tp) {
			out.println("添加失败,你所输入的内容超出系统范围或输入类型不符!");
			return;
		}
		
		einfodb.close();
		
		response.sendRedirect("ddgl/dd-view.jsp?id="+ddid1);

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String actionType = request.getParameter("actionType");
		if (actionType.equals("profitInfo")) {
			getProfitInfo(request, response);
		} else if (actionType.equals("inquiryList")) {
			getInquiry(request, response);
		} else if (actionType.equals("addSalePro2Sup")) {
			try {
				addSalePro2Sup(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (actionType.equals("addSalePro2SupDirect")) {
			try {
				addSalePro2SupDirect(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
