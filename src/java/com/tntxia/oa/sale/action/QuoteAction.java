package com.tntxia.oa.sale.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tntxia.dbmanager.DBManager;

import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.sale.dao.QuoteDao;
import com.tntxia.oa.warehouse.dao.WarehouseLightDao;
import com.tntxia.sqlexecutor.SQLExecutor;
import com.tntxia.sqlexecutor.SQLExecutorSingleConn;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.WebRuntime;

public class QuoteAction extends CommonDoAction {
	
	private QuoteDao quoteDao = new QuoteDao();
	
	private WarehouseLightDao warehouseDao = new WarehouseLightDao();
	
	private DBManager dbManager = this.getDBManager();

	private int getInNo(SQLExecutor db, String pre, String number1)
			throws Exception {

		int in_no = 1;
		String sqlq = "select  top 1 in_no  from quote  where number like '"
				+ pre + number1 + "%' order by in_no desc";
		in_no = db.queryForInt(sqlq) + 1;
		return in_no;

	}

	public Map<String, Object> add(WebRuntime runtime) {

		SQLExecutorSingleConn executor = null;

		String pre = "ONKSZ-QU-";

		try {
			executor = this.getSQLExecutorSingleConn();
			java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
					"yy");
			String number1 = simple1.format(new java.util.Date());
			int in_no = this.getInNo(executor, pre, number1);

			String sno = "";
			if (in_no < 10) {
				sno = "00000";
			} else if ((10 <= in_no) & (in_no < 100)) {
				sno = "0000";
			} else if ((100 <= in_no) & (in_no < 1000)) {
				sno = "000";
			} else if ((1000 <= in_no) & (in_no < 10000)) {
				sno = "00";
			} else
				sno = "";
			String quotedatetime1 = runtime.unescape("quotedatetime");
			String in_number = runtime.unescape("in_number");
			String co_number = runtime.unescape("co_number");
			String coname1 = runtime.unescape("coname");
			String cotel1 = runtime.unescape("cotel");
			String coaddr1 = runtime.unescape("coaddr");
			String cofax1 = runtime.unescape("cofax");
			String linkman1 = runtime.unescape("linkman");
			String linktel1 = runtime.unescape("linktel");
			String linkwap1 = runtime.unescape("linkwap");
			String linkemail1 = runtime.unescape("linkemail");
			String airport = runtime.unescape("airport");

			String tr_types = runtime.unescape("tr_types");
			String q_tr_date = runtime.unescape("q_tr_date");
			String payment = runtime.unescape("payment");
			String content1 = runtime.unescape("content");
			String man1 = runtime.unescape("man");
			String spman = runtime.unescape("spman");
			String money = runtime.unescape("money");
			String fveight = runtime.unescape("fveight");
			String insurance = runtime.unescape("insurance");
			String commission = runtime.unescape("commission");
			String discount = runtime.unescape("discount");
			String dept = this.getDept(runtime);
			String deptjb = this.getDeptjb(runtime);
			String acct = runtime.unescape("acct");

			String strSQL = "insert into quote(number,quotedatetime,in_number,co_number,coname,cotel,coaddr,cofax,linkman,linktel,linkwap,linkemail,content,man,airport,tr_types,q_tr_date,payment,spman,states,spyj,hb,fveight,insurance,commission,discount,in_no,dept,deptjb,acct) values('"
					+ pre
					+ number1
					+ ""
					+ sno
					+ ""
					+ in_no
					+ "','"
					+ quotedatetime1
					+ "','"
					+ in_number
					+ "','"
					+ co_number
					+ "','"
					+ coname1
					+ "','"
					+ cotel1
					+ "','"
					+ coaddr1
					+ "','"
					+ cofax1
					+ "','"
					+ linkman1
					+ "','"
					+ linktel1
					+ "','"
					+ linkwap1
					+ "','"
					+ linkemail1
					+ "','"
					+ content1
					+ "','"
					+ man1
					+ "','"
					+ airport
					+ "','"
					+ tr_types
					+ "','"
					+ q_tr_date
					+ "','"
					+ payment
					+ "','"
					+ spman
					+ "','未提交','','"
					+ money
					+ "','"
					+ fveight
					+ "','"
					+ insurance
					+ "','"
					+ commission
					+ "','"
					+ discount
					+ "','"
					+ in_no
					+ "','"
					+ dept
					+ "','"
					+ deptjb
					+ "','"
					+ acct
					+ "')";

			executor.update(strSQL);

			String sql = "select max(id)  from quote";
			int max = executor.queryForInt(sql);
			return this.success("quoteId", max);
		} catch (Exception ex) {
			return this.errorMsg(ex.toString());
		} finally {
			if (executor != null)
				executor.close();
		}
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		
		Transaction trans = this.getTransaction();
		try{
			String id1=runtime.getParam("id");
			  String strSQL="delete from quote where id=?";
			  trans.update(strSQL,new Object[]{id1});  
			  String strSQL1="delete from quoteproduct where quoteid=?";
			  trans.update(strSQL1,new Object[]{id1});
			  trans.commit();
		}catch(Exception e){
			throw e;
		}finally{
			trans.close();
		}
		return this.success();
	}

	/**
	 * 修改报价单的产品信息
	 * @param runtime
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> updateProduct(WebRuntime runtime) throws Exception {

		SQLExecutorSingleConn sqlExecutor = this.getSQLExecutorSingleConn();
		
		try {
			boolean canViewPurchasingPrice = this.canViewPrice(runtime);

			
			String quoteid = runtime.getParam("ddid");
			int t = 0;
			String i1 = runtime.getParam("i");
			if (i1 != null)
				t = Integer.parseInt(i1);
			for (int i = 1; i < t; i++) {
				String id = runtime.getParam("id" + i);
				if (id != null) {
					String pro_name = runtime.getParam("pro_name" + i);
					if (pro_name != null) {
						pro_name = com.infoally.util.Replace.strReplace(pro_name,
								"'", "’");
						pro_name = com.infoally.util.Replace.strReplace(pro_name,
								"\"", "”");
					}
					String pro_model = runtime.unescape("pro_model" + i);
					if (pro_model != null) {
						pro_model = com.infoally.util.Replace.strReplace(pro_model,
								"'", "’");
						pro_model = com.infoally.util.Replace.strReplace(pro_model,
								"\"", "”");
					}
					String num = runtime.getParam("num" + i);
					String punit = runtime.unescape("punit" + i);
					String qprice = runtime.unescape("qprice" + i);
					String supplier = runtime.unescape("supplier" + i);
					String pro_tr = runtime.unescape("pro_tr" + i);
					String fz = runtime.unescape("fz" + i);
					String selljg = runtime.unescape("selljg" + i);
					String money1 = runtime.unescape("money" + i);
					String pro_remark = runtime.unescape("pro_remark" + i);
					String moq = runtime.unescape("moq" + i);
					String mpq = runtime.unescape("mpq" + i);
					if (pro_remark != null) {
						pro_remark = com.infoally.util.Replace.strReplace(
								pro_remark, "'", "’");
						pro_remark = com.infoally.util.Replace.strReplace(
								pro_remark, "\"", "”");
					}
					String rate = runtime.unescape("rate" + i);
					String pricehb = runtime.unescape("pricehb" + i);

					String strSQL = "update quoteproduct set  pro_gg='" + fz
							+ "',supplier='" + supplier + "',product='" + pro_model
							+ "',cpro='" + pro_name + "',quantity='" + num
							+ "',unit='" + punit + "',price='" + qprice
							+ "',rate='" + rate + "',pricehb='" + pricehb
							+ "'";
					
					
					if(canViewPurchasingPrice){
						strSQL += ",selljg='" + selljg + "',money='" + money1 + "'";
					}
					
					strSQL += ",cpro2='" + pro_tr + "',remark='" + pro_remark
							+ "',moq='" + moq + "',mpq='" + mpq + "'  where  id='"
							+ id + "'";
					sqlExecutor.update(strSQL);

				}
			}
			String pro_name = runtime.unescape("2pro_name");
			if (pro_name != null) {
				pro_name = com.infoally.util.Replace.strReplace(pro_name, "'", "’");
				pro_name = com.infoally.util.Replace
						.strReplace(pro_name, "\"", "”");
			}
			String pro_model = runtime.unescape("2pro_model").trim();
			if (pro_model != null) {
				pro_model = com.infoally.util.Replace.strReplace(pro_model, "'",
						"’");
				pro_model = com.infoally.util.Replace.strReplace(pro_model, "\"",
						"”");
			}
			String num = runtime.unescape("2num");
			String punit = runtime.unescape("2punit");
			String qprice = runtime.unescape("2qprice");
			String supplier = runtime.unescape("2supplier");
			String pro_tr = runtime.unescape("2pro_tr");
			String fz = runtime.unescape("2fz");
			String money = runtime.unescape("2money");
			String selljg = runtime.unescape("2selljg");
			String rate = runtime.unescape("2rate");
			String pricehb = runtime.unescape("2pricehb");
			String pro_remark = runtime.unescape("2pro_remark");
			String moq = runtime.unescape("2moq");
			String mpq = runtime.unescape("2mpq");
			if (pro_remark != null) {
				pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "'",
						"’");
				pro_remark = com.infoally.util.Replace.strReplace(pro_remark, "\"",
						"”");
			}
			if (pro_model.equals("")) {
			} else {
				String strSQL = "insert into quoteproduct(quoteid,product,cpro,cpro2,pro_gg,quantity,unit,price,pricehb";
				
				// 只有有权限的用户才能增加采购价格和更新采购价格
				if(canViewPurchasingPrice){
					strSQL += ",selljg,money";
				}
				
				strSQL += ",supplier,wid,rate_types,rate,remark,moq,mpq) values('"
						+ quoteid
						+ "','"
						+ pro_model
						+ "','"
						+ pro_name
						+ "','"
						+ pro_tr
						+ "','"
						+ fz
						+ "','"
						+ num
						+ "','"
						+ punit
						+ "','"
						+ qprice
						+ "','"
						+ pricehb
						+ "'";
				
				if(canViewPurchasingPrice){
					strSQL += ",'"+ selljg
						+ "','"
						+ money
						+ "'";
				}
					
				strSQL += 
					",'"
					+ supplier
					+ "','','含税','"
					+ rate
					+ "','"
					+ pro_remark + "','" + moq + "','" + mpq + "')";
				sqlExecutor.update(strSQL);

			}
		}catch(Exception ex) {
			throw ex;
		}finally {
			sqlExecutor.close();
		}
		
		
		
		
		
		return this.success();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> addProduct(WebRuntime runtime) throws Exception{
		
		String selectedRows = runtime.getParam("selectedRows");
		List rows = (List)JSON.parse(selectedRows);
		
		String qid=runtime.getParam("qid");
		
		Map<String,Object> quote = quoteDao.getDetail(qid);
		
		String hb = (String) quote.get("hb");
		
		
		    for(int i=0;i<rows.size();i++)
		    {
		    	Map<String,Object> r = (Map<String,Object>) rows.get(i);
		    	Integer productId = (Integer) r.get("id");
		    	String pro_tr = (String) r.get("pro_tr");
		    	
		    	String rate = (String) r.get("rate");
		    	Map<String,Object> product = warehouseDao.getDetail(productId);
		    	String pro_gg = (String) product.get("pro_gg");
		    	String pro_model = (String) product.get("pro_model");
		    	String num = (String) r.get("num");
		    	String pro_number = (String) product.get("pro_number");
		    	String pro_unit = (String) product.get("pro_unit");
		    	
		    	String pro_supplier = (String) product.get("pro_sup_number");
		
		 
		  String pro_price=(String) r.get("pro_price");
		  
		  String money="CNY";
		   

		    String strSQL="insert into quoteproduct(quoteid,product,cpro,cpro2,pro_gg,quantity,unit,price,pricehb,selljg,mone" +
		    		"y,supplier,wid,rate_types,rate,remark,moq,mpq) values('" + qid + "','" + pro_model +"','"
		    		+ pro_number +"',?,'" + pro_gg +"','" + num + "','" + pro_unit + "','" + pro_price + "','" 
		    		+ hb + "','0','" + money + "','" + pro_supplier + "','" + productId + "','含税','" + rate + "','','','')";
		    
		    dbManager.executeUpdate(strSQL,new Object[]{pro_tr});
		   
		    
		}
		
		return this.success();
		
	}
	
	public Map<String,Object> delProduct(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql="delete from quoteproduct where id=?";
		dbManager.update(sql, new Object[]{id});
		return this.success();
	}
	
	public Map<String,Object> toAudit(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "update quote set states='待审批'  where id=?";
		dbManager.update(sql, new Object[]{id});
		return this.success();
	}

}
