package com.tntxia.oa.member.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.system.entity.User;
import com.tntxia.oa.util.PropertiesUtils;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.entity.MultipartForm;
import com.tntxia.web.mvc.view.FileView;

public class MemberAction extends CommonDoAction {

	private DBManager dbManager = this.getDBManager();
	
	private UserDao userDao = new UserDao();
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listExpense(WebRuntime runtime) throws Exception {
		
		 
		 Boolean fyview=this.existRight(runtime, "fyview");
		 String strSQL;
		 String sqlWhere;
		 String deptjb = this.getDeptjb(runtime);
		 String username1 = this.getUsername(runtime);
		 if(fyview){
			 sqlWhere = " where l_deptjb like '"+deptjb+"%'  or  l_spqk='待审批' and   l_spman='"+username1+"' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"+username1+"' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"+username1+"'";
		 }
		    else
		    	sqlWhere = " where l_man='"+username1+"' or  l_spqk='待审批' and   l_spman='"+username1+"' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"+username1+"' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"+username1+"' ";
		
		String sqlCount =  "select count(*) from lending "+sqlWhere;
		
		int totalAmount = dbManager.getCount(sqlCount);
		
		if(fyview){
		    strSQL = "select * from lending  where l_deptjb like '"+deptjb+"%'  or  l_spqk='待审批' and   l_spman='"+username1+"' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"+username1+"' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"+username1+"' order by id desc";
		}else
		    strSQL = "select *  from lending where l_man='"+username1+"' or  l_spqk='待审批' and   l_spman='"+username1+"' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"+username1+"' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"+username1+"'  order by id desc"; 

		// id,l_spman,l_menber,l_man,l_topic,l_date,l_sqje,l_spqk,l_fspman,l_firspman,l_coname
		List list = dbManager.queryForList(strSQL, true);
		return this.getPagingResult(list, runtime.getPageBean(20), totalAmount);
		

	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> listRewards(WebRuntime runtime) throws Exception {

		String sqlWhere = " where 1=1 ";
		String username = this.getUsername(runtime);

		if (this.existRight(runtime, "r_jcsq_view")) {
			sqlWhere += "and l_deptjb like '" + this.getDeptjb(runtime) + "%' ";
		} else {
			sqlWhere += "and l_man = '" + username + "'";
		}
		sqlWhere += "  or  l_spqk='待审批' and   l_spman='" + username
				+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
				+ username
				+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
				+ username + "'";

		String sqlCount = "select count(*) from m_jcgl " + sqlWhere;

		int count = dbManager.getCount(sqlCount);

		PageBean pageBean = runtime.getPageBean(30);

		String sqlList = "select top " + pageBean.getTop() + " * from m_jcgl "
				+ sqlWhere + " order by id desc";

		List list = dbManager.queryForList(sqlList, true);

		return this.getPagingResult(list, pageBean, count);

	}

	public Map<String, Object> addRewards(WebRuntime runtime) throws Exception {

		java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
				"yyMM");
		String number1 = simple1.format(new java.util.Date());

		String sqlq = "select  in_no  from  m_jcgl  where l_menber like 'J"
				+ number1 + "%' order by in_no desc";
		int in_no = dbManager.queryForInt(sqlq);
		in_no++;

		String sno = "";
		if (in_no < 10) {
			sno = "000";
		} else if ((10 <= in_no) & (in_no < 100)) {
			sno = "00";
		} else if ((100 <= in_no) & (in_no < 1000)) {
			sno = "0";
		} else
			sno = "";

		String l_name = runtime.getParam("l_name");
		String l_company = runtime.getParam("l_company");
		String l_role = runtime.getParam("l_role");
		String l_types = runtime.getParam("l_types");
		String l_jgyy = runtime.getParam("l_jcyy");

		String l_jcyj = runtime.getParam("l_jcyj");

		String l_dept = runtime.getParam("l_dept");
		String l_date = runtime.getParam("l_date");
		String remarks = runtime.getParam("remarks");
		String sqls = "select count(*) from m_jcgl where l_menber='J" + number1
				+ "" + sno + "" + in_no + "'";
		if (dbManager.getCount(sqls) > 0) {
			return this.errorMsg("奖惩编号重复!");
		}

		String deptjb = this.getDeptjb(runtime);

		String username1 = this.getUsername(runtime);
		String sqlddman = "select  * from  jcsp where dept='" + l_dept
				+ "' and role='" + l_role + "'";

		Map<String, Object> sp = dbManager.queryForMap(sqlddman, true);
		if (sp == null) {
			return this.errorMsg("未定义审批流程!");
		}

		String l_spman = (String) sp.get("dd_man");
		String fif = (String) sp.get("fif");
		String fspman = (String) sp.get("fspman");
		String firspif = (String) sp.get("firspif");
		String firspman = (String) sp.get("firspman");
		String strSQL = "insert into m_jcgl(l_menber,l_name,l_company,l_role,l_types,l_jcyj,l_man,l_dept,l_deptjb,l_date,l_jcyy,remarks,l_spqk,l_spyj,l_fqr,l_spman,l_fif,l_fspman,l_firspif,l_firspman,in_no) values('J"
				+ number1
				+ ""
				+ sno
				+ ""
				+ in_no
				+ "','"
				+ l_name
				+ "','"
				+ l_company
				+ "','"
				+ l_role
				+ "','"
				+ l_types
				+ "','"
				+ l_jcyj
				+ "','"
				+ username1
				+ "','"
				+ l_dept
				+ "','"
				+ deptjb
				+ "','"
				+ l_date
				+ "','"
				+ l_jgyy
				+ "','"
				+ remarks
				+ "','草拟','','','"
				+ l_spman
				+ "','"
				+ fif
				+ "','"
				+ fspman
				+ "','"
				+ firspif
				+ "','" + firspman + "','" + in_no + "')";
		dbManager.update(strSQL);

		// NoticeDAO dao = new NoticeDAO();
		// Notice notice = new Notice();
		// notice.setPro_man(l_spman);
		// notice.setContent(l_jgyy);
		// dao.add(notice);

		return this.success();

	}

	public Map<String, Object> delRewards(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "delete from m_jcgl where id = ?";
		dbManager.update(sql, new Object[] { id });
		return this.success();
	}

	private Map<String, Object> getRewards(String id) throws Exception {
		String sql = "select * from m_jcgl where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);
	}

	public Map<String, Object> auditRewards(WebRuntime runtime)
			throws Exception {
		String id = runtime.getParam("id");

		Map<String, Object> rewards = this.getRewards(id);
		String l_spqk = runtime.getParam("l_spqk");
		String l_spyj = runtime.getParam("l_spyj");

		String l_fif = (String) rewards.get("l_fif");

		String zt = "审批不通过";
		if (l_spqk.equals("审批通过")) {
			if (l_fif.equals("是")) {
				zt = "待二审";
			} else {
				zt = "审批通过";
			}
		}
		String strSQL = "update  m_jcgl set l_spqk=?,l_spyj=? where id=?";
		dbManager.update(strSQL, new Object[] { zt, l_spyj, id });

		return this.success();
	}
	
	public Map<String, Object> auditRewardsSecond(WebRuntime runtime)
			throws Exception {
		String id = runtime.getParam("id");

		Map<String, Object> rewards = this.getRewards(id);
		String l_spqk = runtime.getParam("l_spqk");
		String l_spyj = runtime.getParam("l_spyj");

		String fif=(String)rewards.get("l_firspif");

		String zt = "审批不通过";
		if (l_spqk.equals("审批通过")) {
			if (fif.trim().equals("是")) {
				zt = "待三审";
			} else {
				zt = "审批通过";
			}
		}
		String strSQL = "update  m_jcgl set l_spqk=?,l_spyj=? where id=?";
		dbManager.update(strSQL, new Object[] { zt, l_spyj, id });
		return this.success();
		
	}
	
	public Map<String, Object> auditRewardsThird(WebRuntime runtime)
			throws Exception {
		String id = runtime.getParam("id");

		String l_spqk = runtime.getParam("l_spqk");
		String l_spyj = runtime.getParam("l_spyj");

		String strSQL = "update  m_jcgl set l_spqk=?,l_spyj=? where id=?";
		dbManager.update(strSQL, new Object[] { l_spqk, l_spyj, id });
		return this.success();
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listMember(WebRuntime runtime) throws Exception{
		String sql = "select * from menber";
		List list = dbManager.queryForList(sql, true);
		String sqlCount = "select count(*) from menber";
		int count = dbManager.queryForInt(sqlCount);
		return this.getPagingResult(list, runtime.getPageBean(20),count);
		
	}
	
	public Map<String,Object> create(WebRuntime runtime) throws Exception{
		 String name1=runtime.getParam("name");
		 User user = userDao.getUser(name1);
		 
		 Department dept = SystemCache.getDepartmentById(user.getDepartmentId());
		 String deptName=dept.getName();
		 String deptjb=dept.getDepartCode();
		 
		 String business=runtime.getParam("business");
		 String jiondt=runtime.getParam("jiondt");
		 String borndt=runtime.getParam("borndt");
		 String sex=runtime.getParam("sex");
		 String man_zzmm=runtime.getParam("man_zzmm");
		 String nation=runtime.getParam("nation");
		 String man_h=runtime.getParam("man_h");
		 String idcard=runtime.getParam("idcard");
		 String man_tc=runtime.getParam("man_tc");
		 String nativeplace=runtime.getParam("nativeplace");
		 String xzaddr=runtime.getParam("xzaddr");
		 String born_addr=runtime.getParam("born_addr");
		 String hunif=runtime.getParam("hunif");
		 String man_tel=runtime.getParam("man_tel");
		 String man_school=runtime.getParam("man_school");
		 String school_date=runtime.getParam("school_date");
		 String school_zy=runtime.getParam("school_zy");
		 String school_year=runtime.getParam("school_year");
		 String man_english=runtime.getParam("man_english");
		 String man_computator=runtime.getParam("man_computator");
		 String man_jj_lxr=runtime.getParam("man_jj_lxr");
		 String man_jj_tel=runtime.getParam("man_jj_tel");
		 String man_jj_addr=runtime.getParam("man_jj_addr");
		 String family_name1=runtime.getParam("family_name1");
		 String family_gx1=runtime.getParam("family_gx1");
		 String family_work_name1=runtime.getParam("family_work_name1");
		 String family_zw1=runtime.getParam("family_zw1");
		 String family_tel1=runtime.getParam("family_tel1");
		 String family_name2=runtime.getParam("family_name2");
		 String family_gx2=runtime.getParam("family_gx2");
		 String family_work_name2=runtime.getParam("family_work_name2");
		 String family_zw2=runtime.getParam("family_zw2");
		 String family_tel2=runtime.getParam("family_tel2");
		 String family_name3=runtime.getParam("family_name3");
		 String family_gx3=runtime.getParam("family_gx3");
		 String family_work_name3=runtime.getParam("family_work_name3");
		 String family_zw3=runtime.getParam("family_zw3");
		 String family_tel3=runtime.getParam("family_tel3");
		 String work_sdate=runtime.getParam("work_sdate");
		 String work_edate=runtime.getParam("work_edate");
		 String work_company=runtime.getParam("work_company");
		 String work_zw=runtime.getParam("work_zw");
		 String work_tel=runtime.getParam("work_tel");
		 String work_sdate1=runtime.getParam("work_sdate1");
		 String work_edate1=runtime.getParam("work_edate1");
		 String work_company1=runtime.getParam("work_company1");
		 String work_zw1=runtime.getParam("work_zw1");
		 String work_tel1=runtime.getParam("work_tel1");
		 String work_sdate2=runtime.getParam("work_sdate2");
		 String work_edate2=runtime.getParam("work_edate2");
		 String work_company2=runtime.getParam("work_company2");
		 String work_zw2=runtime.getParam("work_zw");
		 String work_tel2=runtime.getParam("work_tel2");
		 String man_zz=runtime.getParam("man_zz");
		 String man_cb=runtime.getParam("man_cb");
		 String man_ht=runtime.getParam("man_ht");
		 String school_age=runtime.getParam("school_age");
		 String degree=runtime.getParam("degree");
		 String dimissiom_date=runtime.getParam("dimissiom_date");
		 String remark=runtime.getParam("remark");
		 String strSQL="insert into menber(pic_path,name,dept,deptjb,business,jiondt,borndt,sex,man_zzmm,nation,man_h,idcard,man_tc,nativeplace,xzaddr,born_addr,hunif,man_tel,man_school,school_date,school_zy,school_year,school_age,degree,dimissiom_date,man_english,man_computator,man_jj_lxr,man_jj_tel,man_jj_addr,family_name1,family_gx1,family_work_name1,family_zw1,family_tel1,family_name2,family_gx2,family_work_name2,family_zw2,family_tel2,family_name3,family_gx3,family_work_name3,family_zw3,family_tel3,work_sdate,work_edate,work_company,work_zw,work_tel,work_sdate1,work_edate1,work_company1,work_zw1,work_tel1,work_sdate2,work_edate2,work_company2,work_zw2,work_tel2,man_zz,man_cb,man_ht,remark) values('propic.gif','" + name1 + "','" + deptName +"','" + deptjb + "','" + business + "','" + jiondt + "','" + borndt + "','" + sex + "','" + man_zzmm + "','" + nation + "','" + man_h + "','" + idcard + "','" + man_tc + "','" + nativeplace + "','" + xzaddr + "','" + born_addr + "','" + hunif + "','" + man_tel + "','" + man_school + "','" + school_date + "','" + school_zy + "','" + school_year + "','" + school_age + "','" + degree + "','" + dimissiom_date + "','" + man_english + "','" + man_computator + "','" + man_jj_lxr + "','" + man_jj_tel + "','" + man_jj_addr + "','" + family_name1 + "','" + family_gx1 + "','" + family_work_name1 + "','" + family_zw1 + "','" + family_tel1 + "','" + family_name2 + "','" + family_gx2 + "','" + family_work_name2 + "','" + family_zw2+ "','" + family_tel2 + "','" + family_name3 + "','" + family_gx3 + "','" + family_work_name3 + "','" + family_zw3 + "','" + family_tel3 + "','" + work_sdate + "','" + work_edate + "','" + work_company + "','" + work_zw + "','" + work_tel + "','" + work_sdate1 + "','" + work_edate1 + "','" + work_company1 + "','" + work_zw1 + "','" + work_tel1 + "','" + work_sdate2 + "','" + work_edate2 + "','" + work_company2 + "','" + work_zw2 + "','" + work_tel2 + "','" + man_zz + "','" + man_cb + "','" + man_ht + "','" + remark + "')";
		 dbManager.update(strSQL);
		 return this.success();
	}
	
	
//	public Map<String,Object> uploadPic(WebRuntime runtime) throws Exception{
//		MultipartForm form = runtime.getMultipartForm();
//		String id = form.getString("id");
//		String uploadPath = PropertiesUtils.getProperty("upload_path");
//		List<String> saveRes = form.save(uploadPath);
//		String sql = "update menber set pic_path = ? where id = ?";
//		dbManager.update(sql, new Object[] {saveRes.get(0),id});
//		return this.success();
//		
//	}
//	
	public FileView getPic(WebRuntime runtime) throws Exception{
		String sql = "select pic_path from menber where id = ?";
		String id = runtime.getParam("id");
		String picPath = dbManager.getString(sql, new Object[] {id});
		FileView fileView = new FileView();
		fileView.setFilePath(picPath);
		return fileView;
	}
	
	

}
