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
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.DatasourceStore;

public class ClientAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();
	
	private ClientService service = new ClientService();
	
	@SuppressWarnings("unchecked")
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

		String sqlWhere = " where   cotypes='现有客户'  and (share='是' ";

		boolean xhview = this.existRight(runtime, "xhview");
		if (xhview) {
			String deptjb = this.getDeptjb(runtime);
			sqlWhere += " or deptjb  like '" + deptjb + "%' ";
		} else {
			String username = this.getUsername(runtime);
			sqlWhere += " or username  = '" + username + "' ";
		}
		
		sqlWhere += ")";

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
				+ " * from client";
		String sqlCount = "select count(*) from client";
		
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
		
		String id=runtime.getParam("clientid");
		String share = runtime.getParam("share");

		  String strSQL="update client set share=?  where clientid=?";
		  dbManager.executeUpdate(strSQL,new Object[]{share, id});
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
	
	/**
	  *      删除客户
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from client where clientid=?";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	/**
	 * 是否客户的拥有者
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	private boolean isClientOwner(String id , String username) throws Exception {
		String sql = "select count(*) from client where clientid = ? and username = ?";
		return dbManager.getCount(sql, new Object[] {id, username})>0;
	}
	
	/**
	 * 客户详细
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		return service.getClient(id);
	}

}
