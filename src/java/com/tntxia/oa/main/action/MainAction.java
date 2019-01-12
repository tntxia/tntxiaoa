package com.tntxia.oa.main.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.web.servlet.ModelAndView;

import com.tntxia.db.DBConnection;
import com.tntxia.oa.main.entity.CustomerGJ;
import com.tntxia.oa.purchasing.dao.UnitDao;
import com.tntxia.oa.system.dao.CountryDao;
import com.tntxia.oa.system.dao.DepartmentDao;
import com.tntxia.oa.system.dao.NoticeDao;
import com.tntxia.oa.system.dao.ProvinceDao;
import com.tntxia.oa.system.dao.UserDao;
import com.tntxia.oa.system.entity.User;
import com.tntxia.oa.util.CommonAction;

public class MainAction extends CommonAction {

	private NoticeDao noticeDao;
	
	private DepartmentDao departmentDao;
	
	private UserDao userDao;
	
	private CountryDao countryDao;
	
	private ProvinceDao provinceDao;
	
	private UnitDao unitDao;

	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}
	
	public void setProvinceDao(ProvinceDao provinceDao) {
		this.provinceDao = provinceDao;
	}

	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}

	/**
	 * 显示首页下方的页面
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView toUserFace(HttpServletRequest request,
			HttpServletResponse arg1) throws Exception {

		DBConnection db = new DBConnection();

		HttpSession session = request.getSession();

		String name1 = (String) session.getAttribute("username");

		java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		String currentDate = simple.format(new java.util.Date());
		
		List<CustomerGJ> customerGJList = new ArrayList<CustomerGJ>();
		
		java.sql.ResultSet sqlRst;
		java.lang.String strSQL;
		int intPageSize=0;
		int intPage = 0;
		int intRowCount=0;
		int intPageCount=0;
		java.lang.String strPage;
		int i, j;
		intPageSize = 10;
		strPage = request.getParameter("page");
		int znum = 0;

		try{
			
			java.sql.ResultSet sqlRst1;
			String strSQL1 = "select count(*) from quote where spman='" + name1
					+ "' and states='待审批'";
			sqlRst1 = db.executeQuery(strSQL1);
			sqlRst1.next();
			int num1 = sqlRst1.getInt(1);
			sqlRst1.close();
			java.sql.ResultSet sqlRst2;
			String strSQL2 = "select count(*) from ht where states='待审批' and spman='"
					+ name1 + "' or states='待复审' and fspman='" + name1 + "'";
			sqlRst2 = db.executeQuery(strSQL2);
			sqlRst2.next();
			int num2 = sqlRst2.getInt(1);
			sqlRst2.close();
			java.sql.ResultSet sqlRst3;
			String strSQL3 = "select count(*) from sample where state='待审批' and spman='"
					+ name1
					+ "' or state='待复审' and cwman='"
					+ name1
					+ "' and fif='是'";
			sqlRst3 = db.executeQuery(strSQL3);
			sqlRst3.next();
			int num3 = sqlRst3.getInt(1);
			sqlRst3.close();
			java.sql.ResultSet sqlRst4;
			String strSQL4 = "select count(*) from sample inner join sam_pro on sample.id = sam_pro.ddid where state='已发运' and  man='"
					+ name1
					+ "'  and num!=pro_snum and pro_r_date<='"
					+ currentDate + "'";
			sqlRst4 = db.executeQuery(strSQL4);
			sqlRst4.next();
			int num4 = sqlRst4.getInt(1);
			sqlRst4.close();
			java.sql.ResultSet sqlRst5;
			String strSQL5 = "select count(*) from subscribe where state='待审批' and spman='"
					+ name1
					+ "' or state='待复审' and cwman='"
					+ name1
					+ "' and fif='是'";
			sqlRst5 = db.executeQuery(strSQL5);
			sqlRst5.next();
			int num5 = sqlRst5.getInt(1);
			sqlRst5.close();
			java.sql.ResultSet sqlRst6;
			String strSQL6 = "select count(*) from workreport where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst6 = db.executeQuery(strSQL6);
			sqlRst6.next();
			int num6 = sqlRst6.getInt(1);
			sqlRst6.close();
			java.sql.ResultSet sqlRst7;
			String strSQL7 = "select count(*) from work_report_rd where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst7 = db.executeQuery(strSQL7);
			sqlRst7.next();
			int num7 = sqlRst7.getInt(1);
			sqlRst7.close();
			java.sql.ResultSet sqlRst8;
			String strSQL8 = "select count(*) from work_report_r where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst8 = db.executeQuery(strSQL8);
			sqlRst8.next();
			int num8 = sqlRst8.getInt(1);
			sqlRst8.close();
			java.sql.ResultSet sqlRst9;
			String strSQL9 = "select count(*) from workreport_r where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst9 = db.executeQuery(strSQL9);
			sqlRst9.next();
			int num9 = sqlRst9.getInt(1);
			sqlRst9.close();
			java.sql.ResultSet sqlRst10;
			String strSQL10 = "select count(*) from procure  where   l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待复审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst10 = db.executeQuery(strSQL10);
			sqlRst10.next();
			int num10 = sqlRst10.getInt(1);
			sqlRst10.close();
			java.sql.ResultSet sqlRst11;
			String strSQL11 = "select count(*) from lending  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst11 = db.executeQuery(strSQL11);
			sqlRst11.next();
			int num11 = sqlRst11.getInt(1);
			sqlRst11.close();
			java.sql.ResultSet sqlRst12;
			String strSQL12 = "select count(*) from lending_m  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst12 = db.executeQuery(strSQL12);
			sqlRst12.next();
			int num12 = sqlRst12.getInt(1);
			sqlRst12.close();
			java.sql.ResultSet sqlRst13;
			String strSQL13 = "select count(*) from payment_sp  where  p_states='待审批' and   p_spman='"
					+ name1
					+ "' or   p_states='待二审' and fif='是' and   fspman='"
					+ name1
					+ "' or  firspif='是'  and   p_states='待三审' and   firspman='"
					+ name1
					+ "'  or  fourspif='是'  and   p_states='待四审' and   fourspman='"
					+ name1 + "'";
			sqlRst13 = db.executeQuery(strSQL13);
			sqlRst13.next();
			int num13 = sqlRst13.getInt(1);
			sqlRst13.close();
			java.sql.ResultSet sqlRst14;
			String strSQL14 = "select count(*) from workm where  rwzman='" + name1
					+ "'  and states='待完成任务'   or rwman='" + name1
					+ "'  and states='待完成任务' ";
			sqlRst14 = db.executeQuery(strSQL14);
			sqlRst14.next();
			int num14 = sqlRst14.getInt(1);
			sqlRst14.close();
			java.sql.ResultSet sqlRst15;
			String strSQL15 = "select count(*) from work_report where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst15 = db.executeQuery(strSQL15);
			sqlRst15.next();
			int num15 = sqlRst15.getInt(1);
			sqlRst15.close();
			java.sql.ResultSet sqlRst16;
			String strSQL16 = "select count(*) from work_report_rdm where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst16 = db.executeQuery(strSQL16);
			sqlRst16.next();
			int num16 = sqlRst16.getInt(1);
			sqlRst16.close();
			java.sql.ResultSet sqlRst17;
			String strSQL17 = "select count(*) from work_report_rm where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst17 = db.executeQuery(strSQL17);
			sqlRst17.next();
			int num17 = sqlRst17.getInt(1);
			sqlRst17.close();
			java.sql.ResultSet sqlRst18;
			String strSQL18 = "select count(*) from month_report_rdm where w_states='待审批' and w_spman='"
					+ name1 + "'";
			sqlRst18 = db.executeQuery(strSQL18);
			sqlRst18.next();
			int num18 = sqlRst18.getInt(1);
			sqlRst18.close();
			java.sql.ResultSet sqlRst19;
			String strSQL19 = "select count(*) from m_zzgl  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst19 = db.executeQuery(strSQL19);
			sqlRst19.next();
			int num19 = sqlRst19.getInt(1);
			sqlRst19.close();
			java.sql.ResultSet sqlRst20;
			String strSQL20 = "select count(*) from m_jcgl  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst20 = db.executeQuery(strSQL20);
			sqlRst20.next();
			int num20 = sqlRst20.getInt(1);
			sqlRst20.close();
			java.sql.ResultSet sqlRst21;
			String strSQL21 = "select count(*) from m_stipend  where l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst21 = db.executeQuery(strSQL21);
			sqlRst21.next();
			int num21 = sqlRst21.getInt(1);
			sqlRst21.close();
			java.sql.ResultSet sqlRst22;
			String strSQL22 = "select count(*) from m_evection  where l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			sqlRst22 = db.executeQuery(strSQL22);
			sqlRst22.next();
			int num22 = sqlRst22.getInt(1);
			sqlRst22.close();
			String strSQL23 = "select count(*) from procure_of  where l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待复审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			java.sql.ResultSet sqlRst23 = db.executeQuery(strSQL23);
			sqlRst23.next();
			int num23 = sqlRst23.getInt(1);
			sqlRst23.close();
			String strSQL25 = "select count(*) from m_document  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			java.sql.ResultSet sqlRst25 = db.executeQuery(strSQL25);
			sqlRst25.next();
			int num25 = sqlRst25.getInt(1);
			sqlRst25.close();
			String strSQL26 = "select count(*) from m_commision  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			java.sql.ResultSet sqlRst26 = db.executeQuery(strSQL26);
			sqlRst26.next();
			int num26 = sqlRst26.getInt(1);
			sqlRst26.close();
			String strSQL27 = "select count(*) from m_spbm  where l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审' and l_fif='是' and   l_fspman='"
					+ name1
					+ "' or  l_firspif='是'  and   l_spqk='待三审' and   l_firspman='"
					+ name1 + "'";
			java.sql.ResultSet sqlRst27 = db.executeQuery(strSQL27);
			sqlRst27.next();
			int num27 = sqlRst27.getInt(1);
			sqlRst27.close();
			String strSQL28 = "select count(*) from th_table where  state='待审批' and spman='"
					+ name1
					+ "' or state='待复审' and cwman='"
					+ name1
					+ "' and fif='是'   or state='待三审' and  firspman='"
					+ name1
					+ "' and  firspif='是'";
			java.sql.ResultSet sqlRst28 = db.executeQuery(strSQL28);
			sqlRst28.next();
			int num28 = sqlRst28.getInt(1);
			sqlRst28.close();
			String strSQL29 = "select count(*) from hh_goods  where  l_spqk='待审批' and   l_spman='"
					+ name1
					+ "' or   l_spqk='待二审'  and   l_fspman='"
					+ name1
					+ "' or    l_spqk='待三审' and   l_firspman='"
					+ name1
					+ "'  or    l_spqk='待四审' and   hh_m_man='" + name1 + "'";
			java.sql.ResultSet sqlRst29 = db.executeQuery(strSQL29);
			sqlRst29.next();
			int num29 = sqlRst29.getInt(1);
			sqlRst29.close();
			String strSQL30 = "select count(*) from appeal where state='办理中' and  clfaman='"
					+ name1 + "'";
			java.sql.ResultSet sqlRst30 = db.executeQuery(strSQL30);
			sqlRst30.next();
			int num30 = sqlRst30.getInt(1);
			sqlRst30.next();
			znum = num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9
					+ num10 + num11 + num12 + num13 + num14 + num15 + num16 + num17
					+ num18 + num19 + num20 + num21 + num22 + num23 + num25 + num26
					+ num27 + num28 + num29 + num30;

			if (strPage == null) {
				intPage = 1;
			} else {
				intPage = java.lang.Integer.parseInt(strPage);
				if (intPage < 1)
					intPage = 1;
			}

			strSQL = "select count(*) from customer_gj where man='" + name1
					+ "' and iftx='是' and txtime<='" + currentDate + "'";
			sqlRst = db.executeQuery(strSQL);
			sqlRst.next();
			intRowCount = sqlRst.getInt(1);
			sqlRst.close();
			intPageCount = (intRowCount + intPageSize - 1) / intPageSize;
			if (intPage > intPageCount)
				intPage = intPageCount;
			strSQL = "select  id,c_nr,co_number,c_name,coid,txtime,c_date,man from customer_gj  where man='"
					+ name1
					+ "' and iftx='是' and txtime<='"
					+ currentDate
					+ "'  order by id desc";
			sqlRst = db.executeQuery(strSQL);
			i = (intPage - 1) * intPageSize;
			for (j = 0; j < i; j++)
				sqlRst.next();

			i = 0;

			while (i < intPageSize && sqlRst.next()) {

				CustomerGJ customerGJ = new CustomerGJ();

				int id = sqlRst.getInt(1);
				customerGJ.setId(id);
				String c_nr = sqlRst.getString("c_nr");
				customerGJ.setContent(c_nr);
				String co_number = sqlRst.getString("co_number");
				customerGJ.setCo_number(co_number);
				String s3 = sqlRst.getString("c_name");
				customerGJ.setName(s3);
				String coid = sqlRst.getString("coid");
				if (coid != null) {
					customerGJ.setCo_id(Integer.parseInt(coid.trim()));
				}

				String cotel = "";
				String sql = "select  * from client where co_number='" + co_number
						+ "'";
				ResultSet rs = db.executeQuery(sql);
				if (rs.next()) {
					cotel = rs.getString("cotel");
				}
				rs.close();

				customerGJ.setCo_tel(cotel);

				customerGJList.add(customerGJ);
			}

			

			
		}catch(Exception e){
			e.printStackTrace();
		}
		String dept = (String) session.getAttribute("dept");
		List userNoticeList = noticeDao.getUserNotice(dept, name1);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("customerGJList", customerGJList);
		result.put("intPage", intPage);
		result.put("intPageCount", intPageCount);
		result.put("znum", znum);
		result.put("userNoticeList", userNoticeList);

		return new ModelAndView("cwface", result);
	}
	
	/**
	 * 获取部门列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView getDepartmentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		List<Object> departList = departmentDao.getDepartmentList();
		
		return exportJSONObject(response, departList);
	}
	
	/**
	 * 获取国家列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView getCountryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return exportJSONObject(response, countryDao.list());
	}
	
	/**
	 * 获取省市列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView getProvinceList(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return exportJSONObject(response, provinceDao.list());
	}
	
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView getLeftBar(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String type = request.getParameter("type");
		
		String leftbarConf = this.getServletContext().getRealPath("/WEB-INF/config/leftbar/"+type+".xml");
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(leftbarConf);
        
        Element root = document.getRootElement();
        
        Node barIndexNode = root.selectSingleNode("currentBarIndex");
        
        int currentBarIndex = 0;
        
        if(barIndexNode!=null && barIndexNode.getText()!=null){
        	currentBarIndex = Integer.parseInt(barIndexNode.getText());
        }
        
        
        List list = root.selectNodes("bars/bar");
        
        Map<String,Object> res = new HashMap<String,Object>();
        
        List<Map<String,Object>> bars = new ArrayList<Map<String,Object>>();
        
        for(int i=0;i<list.size();i++){
        	Map<String,Object> map = new HashMap<String,Object>();
        	Element element = (Element) list.get(i);
        	map.put("text", element.attributeValue("name"));
        	
        	List<Map<String,Object>> buttons = new ArrayList<Map<String,Object>>();
        	List buttonList = element.selectNodes("button");
        	for(int j=0;j<buttonList.size();j++){
        		Element el = (Element) buttonList.get(j);
        		Map<String,Object> buttonMap = new HashMap<String,Object>();
        		
        		String ico = el.attributeValue("ico");
        		if(ico!=null){
        			buttonMap.put("ico", request.getContextPath()+"/"+ico);
        		}
        		
        		
        		String url = el.attributeValue("url");
        		
        		// 以#开头是hash链接，不要在前面加basePath
        		if(!url.startsWith("#")){
        			url = request.getContextPath()+"/"+url;
        		}
        		buttonMap.put("url", url);
        		buttonMap.put("text", el.getText());
        		String target = "mainn";
        		if(StringUtils.isNotEmpty(el.attributeValue("target"))){
        			target  = el.attributeValue("target");
        		}
        		buttonMap.put("target", target);
        		buttons.add(buttonMap);
        	}
        	
        	map.put("buttons", buttons);
        	
        	bars.add(map);
        	
        	
        }
        
        res.put("bars", bars);
        res.put("currentBarIndex", currentBarIndex);
		
		return super.exportJSONObject(response, res);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView toTop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String leftbarConf = this.getServletContext().getRealPath("/WEB-INF/config/top/top.xml");
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(leftbarConf);
        
        List list = document.selectNodes("//top/bar/item");
        
        List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        
        for(int i=0;i<list.size();i++){
        	Map<String,String> map = new HashMap<String,String>();
        	Element element = (Element) list.get(i);
        	String right = element.attributeValue("restrain");
        	
        	if(StringUtils.isNotEmpty(right)){
        		boolean existRightFlag = super.existRight(request, right);
        		if(!existRightFlag){
        			continue;
        		}
        	}
        	
        	map.put("url", element.attributeValue("url"));
        	map.put("label", element.attributeValue("label"));
        	
        	res.add(map);
        }
		
        return super.getResult("top", res);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getUserRight(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, super.getUserRight(request));
	}
	
	private String getString(HttpSession session,String key){
		return (String)session.getAttribute(key);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes" })
	public ModelAndView getUserInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String username = getString(session,"username");
		String role = getString(session,"role");
		String dept = getString(session,"dept");
		String currentDate = getString(session,"currentDate");
		Map result = new HashMap();
		result.put("username", username);
		result.put("role", role);
		result.put("dept", dept);
		result.put("currentDate", currentDate);
		result.put("rights", session.getAttribute("userRightList"));
		return this.exportJSONObject(response, result);
	}
	
	@SuppressWarnings("rawtypes")
	public ModelAndView getLoginList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List)request.getSession().getServletContext().getAttribute("userList");
		return super.exportJSONObject(response, list);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public ModelAndView getUserInfoAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		Map userEmailInfo = userDao.getUserEmailInfo(username);
		return this.exportJSONObject(response, userEmailInfo);
	}
	
	public ModelAndView changePass(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String name = (String) session.getAttribute("username");
		String password = request.getParameter("password");
		String mail_user = request.getParameter("mail_user");
		String mail_password = request.getParameter("mail_password");
		String mail_smtp = request.getParameter("mail_smtp");
		String email = request.getParameter("email");

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setEmailUser(mail_user);
		user.setEmailPassword(mail_password);
		user.setEmailStmp(mail_smtp);
		userDao.updateUser(user);

		return this.exportSuccessJSON(response);

	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView export(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String filename="warehouse.xls";
		String uid = request.getParameter("uid");
		
		response.setContentType("application/octet-stream");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(filename.getBytes(), "UTF-8") + "\"");
			ServletOutputStream out = response.getOutputStream();

			File file = new File("D:\\oa_export_temp\\"+uid+".xls");

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			out.write(buffer);
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public ModelAndView getUnitList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.exportJSONObject(response, unitDao.getUnitList());
	}
	
	public ModelAndView toIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer restrainId = (Integer)session.getAttribute("restrainId");
		request.getRequestDispatcher("/index_"+restrainId+".jsp").forward(request, response);
		return null;
	}
	
	
	
}
