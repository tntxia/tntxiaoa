package com.tntxia.oa.hr.action;

import java.util.List;
import java.util.Map;

import com.tntxia.common.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.NumberFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.department.service.DepartmentService;
import com.tntxia.oa.user.service.UserService;
import com.tntxia.web.mvc.WebRuntime;

public class PaymentAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private UserService userService = new UserService();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from payment_sp order by id desc";
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from payment_sp";
		int count = dbManager.getCount(sql);
		
		return this.getPagingResult(list, runtime, count);
		
	}
	
	/**
	 * 增加费用
	 * 
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> add(WebRuntime runtime) throws Exception {

		String payNumber = NumberFactory.generateNumber("PAY");

		String l_date = DateUtil.getCurrentDateSimpleStr();
		String p_je = runtime.getParam("p_je");
		String p_hb = runtime.getParam("p_hb");

		String p_company = runtime.getParam("p_company");
		String p_banknumber = runtime.getParam("p_banknumber");
		String p_bankname = runtime.getParam("p_bankname");
		String p_payment = runtime.getParam("p_payment");
		
		String man = runtime.getParam("p_man");
		
		String p_sm = runtime.getParam("p_sm");

		String strSQL = "insert into payment_sp(p_menber,p_datetime,p_man,p_company,p_banknumber,p_bankname,p_payment,p_je,p_hb,p_states,p_sm)"
				+ " values(?,?,?,?,?,?,?,?,'草拟',?,?)";
		dbManager.update(strSQL, new Object[] { payNumber, l_date,man, p_company,p_banknumber,p_bankname,p_payment,p_je,
				p_hb, p_sm });
		
		

		return this.success();

	}

}
