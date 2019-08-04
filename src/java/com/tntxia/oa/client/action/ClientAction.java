package com.tntxia.oa.client.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTrans;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.jdbc.SQLExecutor;
import com.tntxia.oa.client.service.ClientService;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.mail.SendMail;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.ParamUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.DatasourceStore;

public class ClientAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();
	
	private ClientService service = new ClientService();
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		Map<String,String> paramMap = runtime.getParamMap();
		String number = NumberFactory.generateNumber("CL");
		paramMap.put("number", number);
		String username = this.getUsername(runtime);
		paramMap.put("username", username);
		String dept = this.getDept(runtime);
		paramMap.put("dept", dept);
		String deptjb = this.getDeptjb(runtime);
		paramMap.put("deptjb", deptjb);
		
		return HttpTrans.getMap("http://localhost:8080/OAService/services/client.add", paramMap);
		
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> listPotential(WebRuntime runtime)
			throws Exception {

		PageBean pageBean = runtime.getPageBean(20);

		String sqlWhere = "";
		String name = runtime.getParam("name");
		if (StringUtils.isNotEmpty(name)) {
			sqlWhere += " and coname like '%" + name + "%'";
		}

		String sql = "select top " + pageBean.getTop()
				+ " * from client where  cotypes='潜在客户'";
		String sqlCount = "select count(*) from client where  cotypes='潜在客户'";
		List list = dbManager.queryForList(sql + sqlWhere, true);
		int count = dbManager.queryForInt(sqlCount + sqlWhere);
		Map<String, Object> res = this.getPagingResult(list, pageBean, count);
		return res;

	}
	
	private boolean existConame(String coname){
		String sql = "select count(*) from client where coname=?";
		return dbManager.exist(sql, new Object[]{coname});
	}
	
	private Map<String,Object> getClientByName(String name) throws Exception{
		return dbManager.queryForMap("select * from client where coname=?",new Object[]{name}, true);
	}
	
	public Map<String,Object> checkConame(WebRuntime runtime) throws Exception{
		
		String coname = runtime.getParam("coname");
		
		Map<String,Object> res = new HashMap<String,Object>();
		boolean exist = this.existConame(coname);
		res.put("exist", exist);
		if(exist) {
			res.put("client", this.getClientByName(coname));
		}
		return res;
		
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> list(WebRuntime runtime) throws Exception {

		String sqlWhere = "";

		boolean xhview = this.existRight(runtime, "xhview");

		if (xhview) {
			String deptjb = this.getDeptjb(runtime);
			sqlWhere += " and deptjb  like '" + deptjb + "%' ";
		} else {
			String username = this.getUsername(runtime);
			sqlWhere += " and username  = '" + username + "' ";
		}

		PageBean pageBean = runtime.getPageBean();
		
		String name = runtime.getParam("coname");
		if (StringUtils.isNotEmpty(name)) {
			sqlWhere += " and coname like '%" + name + "%'";
		}
		
		String follower = runtime.getParam("follower");
		if (StringUtils.isNotEmpty(follower)) {
			sqlWhere += " and follower like '%" + follower + "%'";
		}

		String tel = runtime.getParam("tel");
		if (StringUtils.isNotEmpty(tel)) {
			sqlWhere += " and cotel like '%" + tel + "%'";
		}

		String addr = runtime.getParam("addr");
		if (StringUtils.isNotEmpty(addr)) {
			sqlWhere += " and coaddr like '%" + addr + "%'";
		}

		String sql = "select top " + pageBean.getTop()
				+ " * from client where  cotypes='现有客户'";

		String sqlCount = "select count(*) from client where  cotypes='现有客户'";

		System.out.println("search client,,,," + sql + sqlWhere);
		int totalAmount = dbManager.getCount(sqlCount + sqlWhere);
		List list = dbManager.queryForList(sql + sqlWhere, true);
		return this.getPagingResult(list, runtime, totalAmount);

	}

	@SuppressWarnings("rawtypes")
	public List listContact(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sql = "select  nameid,name,cotel,coname,waptel,email from linkman";
		return dbManager.queryForList(sql, true);

	}

	@SuppressWarnings("rawtypes")
	public List listClientQuest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sql = "select * from serverquest";
		return dbManager.queryForList(sql, true);

	}

	@SuppressWarnings("rawtypes")
	public List listComplainAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sql = "select * from appeal order by aid  desc";
		return dbManager.queryForList(sql, true);

	}

	/**
	 * 客户预约
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> changeName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Transaction trans = this.getTransaction();

		String cid = request.getParameter("id");

		Map<String, Object> client = service.getClient(cid);

		if (client == null) {
			this.errorMsg("客户信息不存在！");
		}

		String sp = (String) client.get("coname");

		String coname1 = ParamUtils.unescape(request, "name");

		try {
			String strSQL = "update client set coname='" + coname1
					+ "' where clientid='" + cid + "'";
			trans.executeUpdate(strSQL);

			if (sp != null) {
				String sql1 = "update linkman set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sql1);
				String sql8 = "update quote set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sql8);
				String sqlf = "update ddtransportation set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sqlf);
				String sqls = "update transportation set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sqls);
				String sqlg = "update gathering set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sqlg);
				String sqlsd = "update subscribe set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(sqlsd);
				String strSQLht = "update ht  set ht_customer='" + coname1
						+ "' where ht_customer='" + sp + "'";
				trans.executeUpdate(strSQLht);
				String strSQLsa = "update sample  set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(strSQLsa);
				String strSQLtt = "update th_table  set coname='" + coname1
						+ "' where coname='" + sp + "'";
				trans.executeUpdate(strSQLtt);
				String strSQLpt = "update payment_customer  set coname='"
						+ coname1 + "' where coname='" + sp + "'";
				trans.executeUpdate(strSQLpt);
				String strSQLgt = "update gathering_customer  set coname='"
						+ coname1 + "' where coname='" + sp + "'";
				trans.executeUpdate(strSQLgt);
				String strSQLct = "update credit_debit  set l_coname='"
						+ coname1 + "' where l_coname='" + sp + "'";
				trans.executeUpdate(strSQLct);
				String strSQLot = "update  outhouse  set pro_coname='"
						+ coname1 + "' where pro_coname='" + sp + "'";
				trans.executeUpdate(strSQLot);
				trans.commit();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			trans.rollback();
			return this.errorMsg(ex.toString());
		} finally {
			trans.close();
		}

		return this.success();

	}

	public Map<String, Object> export(WebRuntime runtime) throws Exception {

		String role = this.getRole(runtime);
		if (!role.equals("总经理")) {
			return this.errorMsg("你没有导出客户的权限！");
		}
		
		HttpTransfer ht = HttpTransferFactory.generate("ReportCenter");
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("template", "client_export");
		Map<String,Object> res = ht.getMap("report!generate", p);
		String uuid = (String) res.get("uuid");
		return this.success("uuid", uuid);

	}
	
	private Map<String,Object> getClientDetail(String id) throws Exception{
		return dbManager.queryForMap("select * from client where clientid = ?",new Object[]{id}, true);
	}
	
	public Map<String,Object> addContact(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("coId");
		
		Map<String,Object> client = getClientDetail(id);
		
		
		String name1 = runtime.getParam("name");
		String job1 = runtime.getParam("job");
		String mr1 = runtime.getParam("mr");
		String email1 = runtime.getParam("email");
		String tel1 = runtime.getParam("tel");
		String department1 = runtime.getParam("department");
		String borndt1 = runtime.getParam("borndt");
		String school1 = runtime.getParam("school");
		if(school1!=null){
			school1 = com.infoally.util.Replace.strReplace(school1, "'", "’");
			school1 = com.infoally.util.Replace.strReplace(school1, "\"", "”");
		}
		
		String degree1 = runtime.getParam("degree");
		String born1 = runtime.getParam("born");
		String co_number = (String)client.get("co_number");
		String coname1 = (String)client.get("coname");
		String coaddr1 = runtime.getParam("coaddr");
		String cotel1 = runtime.getParam("cotel");
		String cofax1 = runtime.getParam("cofax");
		String prorole1 = runtime.getParam("prorole");
		String evaluate1 = runtime.getParam("evaluate");
		if(evaluate1!=null){
			evaluate1 = com.infoally.util.Replace.strReplace(evaluate1, "'", "’");
			evaluate1 = com.infoally.util.Replace.strReplace(evaluate1, "\"", "”");
		}
		
		String artifice1 = runtime.getParam("artifice");
		String post1 = runtime.getParam("post");
		String city1 = runtime.getParam("city");
		String country1 = runtime.getParam("country");
		String province1 = runtime.getParam("province");
		String waptel1 = runtime.getParam("waptel");
		String interest1 = runtime.getParam("interest");
		interest1 = com.infoally.util.Replace.strReplace(interest1, "'", "’");
		interest1 = com.infoally.util.Replace.strReplace(interest1, "\"", "”");
		String username1 = runtime.getParam("username");
		String share1 = runtime.getParam("share");
		String beizhu1 = runtime.getParam("beizhu");
		beizhu1 = com.infoally.util.Replace.strReplace(beizhu1, "'", "’");
		beizhu1 = com.infoally.util.Replace.strReplace(beizhu1, "\"", "”");
		String modman = runtime.getParam("modman");
		String dept = runtime.getParam("dept");
		String deptjb = runtime.getParam("deptjb");
		String qq = runtime.getParam("qq");
		String msn = runtime.getParam("msn");
		String skype = runtime.getParam("skype");
		String strSQL = "insert into linkman(name,job,mr,email,tel,department,bornde,school,degree,born,co_number,coname,coaddr,cotel,cofax,prorole,evaluate,artifice,post,city,country,province,waptel,interest,username,rg_date,dept,deptjb,modman,mod_date,share,beizhu,qq,msn,skype) "
				+ "values(?,'"
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
				+ post1
				+ "','"
				+ city1
				+ "','"
				+ country1
				+ "','"
				+ province1
				+ "','"
				+ waptel1
				+ "','"
				+ interest1
				+ "','"
				+ username1
				+ "',?,'"
				+ dept
				+ "','"
				+ deptjb
				+ "','"
				+ modman
				+ "',?,'"
				+ share1
				+ "','"
				+ beizhu1
				+ "','"
				+ qq
				+ "','"
				+ msn
				+ "','"
				+ skype + "')";
		Date now = new Date(System.currentTimeMillis());

		dbManager.executeUpdate(strSQL, new Object[] { name1, now,
				now });
		
		

		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	private List getContactList(String co_number) throws Exception{
		String sql = "select * from linkman where co_number = ?";
		return dbManager.queryForList(sql, new Object[]{co_number},true);
	}
	
	public Map<String,Object> getContactList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String coId = request.getParameter("coId");
		
		Map<String,Object> client = getClientDetail(coId);
		
		String co_number = (String) client.get("co_number");
		
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", getContactList(co_number));
		
		return res;
	}
	
	public Map<String,Object> delContact(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "delete from linkman where nameid = ?";;
		dbManager.update(sql, new Object[]{id});
		return this.success();
	}
	
	public Map<String,Object> updateFollow(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String iftx = runtime.getParam("iftx");
		String sql = "update customer_gj set iftx = ? where id = ?";
		dbManager.update(sql, new Object[]{iftx,id});
		return this.success();
	}
	
	public Map<String,Object> getScore(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		Map<String,Object> client = service.getClient(id);
		String co_number = (String) client.get("co_number");
		
		String sql = "select avg(rfq) rfq,avg(gmjl) gmjl,avg(fk) fk,avg(th) th from pf_client where co_number = ?";
		return this.success("score", dbManager.queryForMap(sql,new Object[]{co_number}, true));
	}
	
	public Map<String,Object> doScore(WebRuntime runtime) throws Exception{
		String username = this.getUsername(runtime);   // 评分人
		String id = runtime.getParam("id");
		Map<String,Object> client = service.getClient(id);
		String co_number= (String) client.get("co_number");        // 客户编号
		String co_name=(String)client.get("co_name");              // 客户名称
		String rfq=runtime.getParam("rfq");        			       // 询价单(RFQs)回应速度
		String gmjl=runtime.getParam("gmjl");        		   	   // 购买机率
		String fk=runtime.getParam("fk");        			       // 付款情况
		String th=runtime.getParam("th");        			       // 退货速度
		String remark = runtime.getParam("remark");                // 评论

		String strSQL="insert into pf_client(co_number,username,rfq,gmjl,fk,th,remark,pf_datetime) "
		  	+"values('" + co_number + "','" + username +"'," 
		  			+ rfq + "," + gmjl + " ," + fk + "," + th + ",'"+remark+"',getdate())";


		dbManager.executeUpdate(strSQL);

		SendMail sendMail = new SendMail();

			sendMail.sendMail(username+"对潜在客户"+co_name+"进行了评分",
					"询价单(RFQs)回应速度:"+rfq+"分<br>"
					+"购买机率:"+rfq+"分<br>"
					+"付款情况:"+rfq+"分<br>"
					+"退货速度:"+rfq+"分<br>"
					+"评论："+remark,"Amy",username);
			
			return this.success();


	}
	
	public Map<String,Object> update(WebRuntime runtime) throws SQLException{
		
		String id1=runtime.getParam("id");
		
		 String coaddr1=runtime.getParam("coaddr");
		  coaddr1=com.infoally.util.Replace.strReplace(coaddr1,"'","’");
		  coaddr1=com.infoally.util.Replace.strReplace(coaddr1,"\"","”");
		 String copost1=runtime.getParam("copost");
		 String city1=runtime.getParam("city");
		 String province1=runtime.getParam("province");
		 String cofrdb1=runtime.getParam("cofrdb");
		 String cozzxs1=runtime.getParam("cozzxs");
		 String cozczb1=runtime.getParam("cozczb");
		 String coyyzz1=runtime.getParam("coyyzz");
		 String describee1=runtime.getParam("describee");
		  describee1=com.infoally.util.Replace.strReplace(describee1,"'","’");
		  describee1=com.infoally.util.Replace.strReplace(describee1,"\"","”");
		 String tradetypes1=runtime.getParam("tradetypes");
		 String cokhjb1=runtime.getParam("cokhjb");
		 String cokhyh1=runtime.getParam("cokhyh");
		 String coyhzh1=runtime.getParam("coyhzh");
		 String coclrq1=runtime.getParam("coclrq");
		 String cotel1=runtime.getParam("cotel");
		 String cofax1=runtime.getParam("cofax");
		 String codzyj1=runtime.getParam("codzyj");
		 String conet1=runtime.getParam("conet");
		 String cosyhb1=runtime.getParam("cosyhb");
		 String cojsfs1=runtime.getParam("cojsfs");
		 String number1=runtime.getParam("number");
		 String share1=runtime.getParam("share");
		 String yearearning1=runtime.getParam("yearearning");
		 String paydate1=runtime.getParam("paydate");
		 String limited_credit1=runtime.getParam("limited_credit");
		 String nsnumber1=runtime.getParam("nsnumber");
		 String product1=runtime.getParam("product");
		  product1=com.infoally.util.Replace.strReplace(product1,"'","’");
		  product1=com.infoally.util.Replace.strReplace(product1,"\"","”");
		 String mproduct1=runtime.getParam("mproduct");
		  mproduct1=com.infoally.util.Replace.strReplace(mproduct1,"'","’");
		  mproduct1=com.infoally.util.Replace.strReplace(mproduct1,"\"","”");
		 String co_number=runtime.getParam("co_number");
		 String company_management1=runtime.getParam("company_management");
		  company_management1=com.infoally.util.Replace.strReplace(company_management1,"'","’");
		  company_management1=com.infoally.util.Replace.strReplace(company_management1,"\"","”");
		 String username=runtime.getParam("username");
		   String link_zw1=runtime.getParam("link_zw1");
		 String link_wap1=runtime.getParam("link_wap1");
		 String link_mail1=runtime.getParam("link_mail1");
		 String linkman2=runtime.getParam("linkman2");
		 String link_zw2=runtime.getParam("link_zw2");
		 String link_tel2=runtime.getParam("link_tel2");
		 String link_wap2=runtime.getParam("link_wap2");
		 String link_mail2=runtime.getParam("link_mail2");
		 String linkman3=runtime.getParam("linkman3");
		 String link_zw3=runtime.getParam("link_zw3");
		 String link_tel3=runtime.getParam("link_tel3");
		 String link_wap3=runtime.getParam("link_wap3");
		 String link_mail3=runtime.getParam("link_mail3");
		 String linkman4=runtime.getParam("linkman4");
		 String link_zw4=runtime.getParam("link_zw4");
		 String link_tel4=runtime.getParam("link_tel4");
		 String link_wap4=runtime.getParam("link_wap4");
		 String link_mail4=runtime.getParam("link_mail4");
		 String linkman5=runtime.getParam("linkman5");
		 String link_zw5=runtime.getParam("link_zw5");
		 String link_tel5=runtime.getParam("link_tel5");
		 String link_wap5=runtime.getParam("link_wap5");
		 String link_mail5=runtime.getParam("link_mail5");
		 String co_code=runtime.getParam("co_code");
		 
		 String bank_name = runtime.getParam("bank_name");
		 String bank_addr = runtime.getParam("bank_addr");
		 String swift_code = runtime.getParam("swift_code");
		 String iban = runtime.getParam("iban");
		 String route = runtime.getParam("route");
		 String bic = runtime.getParam("bic");
		 String pay_deadline = runtime.getParam("pay_deadline");

		  String strSQL="update client set co_number='" + co_number + "',coaddr='" + coaddr1 +"',copost='" + copost1 + "',city='" + city1 + "',province='" + province1 + "',cofrdb='" + cofrdb1 + "',cozzxs='" + cozzxs1 + "',cozczb='" + cozczb1 + "',coyyzz='" + coyyzz1 + "',tradetypes='" + tradetypes1 + "',cokhjb='" + cokhjb1 + "',cokhyh='" + cokhyh1 + "',coyhzh='" + coyhzh1 + "',coclrq='" + coclrq1 + "',cotel='" + cotel1 + "',cofax='" + cofax1 + "',codzyj='" + codzyj1 + "',conet='" + conet1 + "',cosyhb='" + cosyhb1 + "',cojsfs='" + cojsfs1 + "',paydate='" + paydate1 + "',limited_credit='" + limited_credit1 + "',nsnumber='" + nsnumber1 + "',number='" + number1 + "',yearearning='" + yearearning1 + "',share='" + share1 + "',username='" + username + "',mproduct='" + mproduct1 + "',product='" + product1 + "',company_management='" + company_management1 + "',describee='" + describee1 + "',link_zw1='" + link_zw1 + "',link_wap1='" + link_wap1 + "',link_mail1='" + link_mail1 + "',linkman2='" + linkman2 + "',link_zw2='" + link_zw2+ "',link_tel2='" + link_tel2 + "',link_wap2='" + link_wap2 + "',link_mail2='" + link_mail2 + "',linkman3='" + linkman3 + "',link_zw3='" + link_zw3+ "',link_tel3='" + link_tel3 + "',link_wap3='" + link_wap3 + "',link_mail3='" + link_mail3 + "',linkman4='" + linkman4 + "',link_zw4='" + link_zw4+ "',link_tel4='" + link_tel4 + "',link_wap4='" + link_wap4 + "',link_mail4='" + link_mail4 + "',linkman5='" + linkman5+ "',link_zw5='" + link_zw5+ "',link_tel5='" + link_tel5 + "',link_wap5='" + link_wap5 + "',link_mail5='" + link_mail5 + "',co_code='" + co_code 
		  + "',bank_name='" + bank_name + "',bank_addr='" + bank_addr 
		  + "',swift_code='" + swift_code + "',iban='" + iban + "',route='" + route + "',bic='" + bic
		  + "',pay_deadline=?  where clientid='"+id1+"'";
		  dbManager.executeUpdate(strSQL,new Object[]{pay_deadline});
		  return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List listCustomerChat(WebRuntime runtime) throws Exception{
		String username = this.getUsername(runtime);
		String currentDate = DateUtil.getCurrentDateSimpleStr();
		SQLExecutor sqlExec = SQLExecutor.getInstance(DatasourceStore.getDatasource("default"));
		String sql = "select top 10 id,c_nr,co_number,c_name,coid,txtime,c_date,man from customer_gj  where man='"
				+ username
				+ "' and iftx='是' and txtime<='"
				+ currentDate
				+ "'  order by id desc";
		List res = sqlExec.queryForList(sql, true);
		sqlExec.close();
		return res;
	}

}
