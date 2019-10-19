package com.tntxia.oa.purchasing.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.purchasing.dao.SupplierLightDao;
import com.tntxia.oa.system.dao.DepartmentDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.user.service.UserService;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.mvc.view.FileView;
import com.tntxia.web.util.UUIDUtils;

public class SupplierDoAction extends CommonDoAction {

	private SupplierLightDao supplierDao = new SupplierLightDao();
	
	private DBManager dbManager = this.getDBManager();
	
	private DepartmentDao departmentDao = new DepartmentDao();
	
	private UserService userService = new UserService();

	/**
	 * 获取供应商列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		
		String coname = runtime.getParam("coname");
		Map<String,Object> param = new HashMap<String,Object>();
		PageBean pageBean = runtime.getPageBean(20);
		param.put("pageBean", pageBean);
		param.put("coname", coname);
		param.put("country", runtime.getParam("country"));
		param.put("province", runtime.getParam("province"));
		param.put("tradetypes", runtime.getParam("tradetypes"));
		param.put("cojsfs", runtime.getParam("cojsfs"));
		param.put("scale", runtime.getParam("scale"));
		
		List list = supplierDao.list(param);
		List rows = this.getRows(list, pageBean);
		int totalAmount = supplierDao.getTotal(param);
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("rows", rows);
		res.put("totalAmount", totalAmount);
		res.put("page", pageBean.getPage());
		res.put("pageSize", pageBean.getPageSize());

		return this.getPagingResult(rows, pageBean);
	}
	
	/**
	 * 获取联系人的列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getContactList(WebRuntime runtime) throws Exception {
		String number = runtime.getParam("number");
		return 
				supplierDao.getContactList(number);
	}
	
	public Map<String,Object> create(WebRuntime runtime) throws Exception {
		
		String coname1=runtime.getParam("coname");
		String sqls="select count(*) from supplier where coname='" + coname1 + "'";
		 
		 if(dbManager.exist(sqls)) {
			 return this.errorMsg("公司名称重复!");
		 }
		 
		 String sqlq="select in_no  from  supplier   order by in_no desc";
		 int in_no = dbManager.queryForInt(sqlq);
		 in_no++;
		
		 String sno="";
		if(in_no<10){
			sno="000";
		}
		else if((10<=in_no)&(in_no<100)){
			sno="00";
		}
		else if((100<=in_no)&(in_no<1000)){
			sno="0";
		}
		else
			sno="";
		
		
		  coname1=com.infoally.util.Replace.strReplace(coname1,"'","’");
		  coname1=com.infoally.util.Replace.strReplace(coname1,"\"","”");
		 String coaddr1=runtime.getParam("coaddr");
		  coaddr1=com.infoally.util.Replace.strReplace(coaddr1,"'","’");
		  coaddr1=com.infoally.util.Replace.strReplace(coaddr1,"\"","”");
		 String copost1=runtime.getParam("copost");
		 String city1=runtime.getParam("city");
		 String country1=runtime.getParam("country");
		 String province1=runtime.getParam("province");
		 String cofrdb1=runtime.getParam("cofrdb");
		 String cozzxs1=runtime.getParam("cozzxs");
		 String cozczb1=runtime.getParam("cozczb");
		 String coyyzz1=runtime.getParam("coyyzz");
		 String cotypes1=runtime.getParam("cotypes");
		 String describee1=runtime.getParam("describee");
		  describee1=com.infoally.util.Replace.strReplace(describee1,"'","’");
		  describee1=com.infoally.util.Replace.strReplace(describee1,"\"","”");
		 String tradetypes1=runtime.getParam("tradetypes");
		 String cokhjb1=runtime.getParam("cokhjb");
		 String cokhyh1=runtime.getParam("cokhyh");
		 String coyhzh1=runtime.getParam("coyhzh");
		 String coclrq1=runtime.getParam("coclrq");
		 String ifjckq1=runtime.getParam("ifjckq");
		 String cotel1=runtime.getParam("cotel");
		 String cofax1=runtime.getParam("cofax");
		 String codzyj1=runtime.getParam("codzyj");
		 String conet1=runtime.getParam("conet");
		 String cosyhb1=runtime.getParam("cosyhb");
		 String cojsfs1=runtime.getParam("cojsfs");
		 String nshm1=runtime.getParam("nshm");
		 String number=runtime.getParam("number");
		 String username1=this.getUsername(runtime);
		 String dept=this.getDept(runtime);
		 String deptjb=this.getDeptjb(runtime);
		 String modman=runtime.getParam("modman");
		 String mod_date=DateUtil.getCurrentDateSimpleStr();
		 String share1=runtime.getParam("share");
		 String yearearning1=runtime.getParam("yearearning");
		  String valueco1=runtime.getParam("valueco");
		  String rg_date1=DateUtil.getCurrentDateSimpleStr();
		  String annual_sales1=runtime.getParam("annual_sales");
		  String sales_ability1=runtime.getParam("sales_ability");
		  String qlty_control1=runtime.getParam("qlty_control");
		  String companymt1=runtime.getParam("companymt");
		  companymt1=com.infoally.util.Replace.strReplace(companymt1,"'","’");
		  companymt1=com.infoally.util.Replace.strReplace(companymt1,"\"","”");
		  String scale1=runtime.getParam("scale");
		  String warehouse=runtime.getParam("warehouse");
		  
		  String certification = runtime.getParam("certification");
		  String bank_addr = runtime.getParam("bank_addr");
		  String swift_code = runtime.getParam("swift_code");
		  String iban = runtime.getParam("iban");
		  String route = runtime.getParam("route");
		  String bic = runtime.getParam("bic");
		  String receiver = runtime.getParam("receiver");
		  String receiver_tel = runtime.getParam("receiver_tel");
		  String receiver_addr = runtime.getParam("receiver_addr");
		  String freight = runtime.getParam("freight");
		  String express_company = runtime.getParam("express_company");
		  String acct = runtime.getParam("acct");
		  String service_type = runtime.getParam("service_type");
		  String pay_type = runtime.getParam("pay_type");
		  
		 String strSQL="insert into supplier(co_number,coname,coaddr,copost,city,country,province,"+
		 "cofrdb,cozzxs,cozczb,coyyzz,cotypes,tradetypes,cokhjb,cokhyh,coyhzh,coclrq,ifjckq,cotel,cofax,"+
		 "codzyj,conet,cosyhb,cojsfs,nshm,number,username,dept,deptjb,modman,mod_date,share,yearearning,"+
		 "valueco,rg_date,annual_sales,sales_ability,qlty_control,companymt,scale,warehouse,describee,in_no,"
		 +"certification,bank_addr,swift_code,iban,route,bic,receiver,receiver_tel,receiver_addr,freight,express_company,"
		 +"acct,service_type,pay_type,pf) values('"+sno+""+in_no+"','" + coname1 + "','" 
		 + coaddr1 +"','" + copost1 + "','" + city1 + "','" + country1 + "','" + province1 + "','" 
		 + cofrdb1 + "','" + cozzxs1 + "','" + cozczb1 + "','" + coyyzz1 + "','" + cotypes1 + "','" 
		 + tradetypes1 + "','" + cokhjb1 + "','" + cokhyh1 + "','" + coyhzh1 + "','" + coclrq1 + "','" 
		 + ifjckq1 + "','" + cotel1 + "','" + cofax1 + "','" + codzyj1 + "','" + conet1 + "','" + cosyhb1 
		 + "','" + cojsfs1 + "','" + nshm1 + "','" + number + "','" + username1 + "','" + dept + "','" 
		 + deptjb + "','" + modman + "','" + mod_date + "','" + share1 + "','" + yearearning1 + "','" 
		 + valueco1 + "','" + rg_date1 + "','" + annual_sales1 + "','" + sales_ability1 + "','" 
		 + qlty_control1 + "','" + companymt1 + "','" + scale1 + "','" + warehouse + "','" 
		 + describee1 + "','" + in_no + "','"+certification+"','"+bank_addr+"','"+swift_code+"','"
		 +iban+"','"+route+"','"+bic+"','"+receiver+"','"+receiver_tel+"','"+receiver_addr+"','"+freight+"','"
		 +express_company+"','"+acct+"','"+service_type+"','"+pay_type+"',0)";
		 dbManager.executeUpdate(strSQL);
		 
         if(StringUtils.isNotEmpty(cojsfs1)){
        	 String strSQLyy="insert into qlinkman values('" + cojsfs1 + "','','','" + codzyj1 + "','" + cotel1 + "','','','','','','"+sno+""+in_no+"','" + coname1 + "','" + coaddr1 + "','" + cotel1 + "','" + cofax1 + "','','','','" + nshm1 + "','','','" + username1 + "','" + rg_date1 + "','" + dept + "','" + deptjb + "','否','" + modman + "','" + mod_date + "','')";

		     dbManager.executeUpdate(strSQLyy);
	     }
         
         return this.success();
		   
	}
	
	@SuppressWarnings("rawtypes")
	public List getAttachList(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		String sql = "select id,filename,filepath,remark from supplier_attachment where supplierId = ?";
		return dbManager.queryForList(sql, new Object[] {id}, true);
		
	}
	
	public Map<String,Object> uploadAttach(
			@Param("id") String id, 
			@Param("remark") String remark,
			@Param("attachment") FileItem attachment,
			HttpServletRequest request
			) throws Exception{
		
		String fileNameOriginal = attachment.getName();
		String ext = FilenameUtils.getExtension(fileNameOriginal);
		
		String uploadPath = request.getServletContext().getRealPath("/uploadFile");
		File uploadDir = new File(uploadPath);
		uploadDir.mkdirs();
		String uuid = UUIDUtils.getUUID();
		
		String tempPath = uploadDir.getAbsolutePath() + File.separatorChar + uuid + "." + ext;
		File tempFile = new File(tempPath);
		System.out.println("文件写入" + tempFile.getAbsolutePath());
		attachment.write(tempFile);
		
		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String,Object> res = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
		
		if (res==null || !(boolean)res.get("success")) {
			return this.errorMsg("附件上传失败");
		}
		
		uuid = (String) res.get("uuid");
		
		dbManager.executeUpdate("insert into supplier_attachment(supplierid,file_id,remark)"
				+" values('"+id+"',?,'"+remark+"')", new Object[] {uuid});
		return this.success();
	}
	
	public FileView viewAttach(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		
		String sql = "select id,filename,filepath,remark from supplier_attachment where id = ?";
		
		Map<String,Object> attach = dbManager.queryForMap(sql, new Object[] {id},true);

		String filePath = (String) attach.get("filepath");
		FileView fileView = new FileView();
		fileView.setFilePath(filePath);
		return fileView;
	}
	
	public Map<String,Object> delAttach(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String strSQL="delete from supplier_attachment where id = '" + id + "'";
		dbManager.update(strSQL);
		return this.success(); 
	}
	
	private String getString(HSSFRow row,int i) {
		HSSFCell cell=row.getCell(i);
		if(cell==null) {
			return null;
		}
		return cell.getStringCellValue();
	}
	
	/**
	 * 供应商导入
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> importSupplier(
			@Param("attachment") FileItem attachment,
			HttpServletRequest request) throws Exception{
		
		
		String fileNameOriginal = attachment.getName();
		String ext = FilenameUtils.getExtension(fileNameOriginal);
		
		String uploadPath = request.getServletContext().getRealPath("/uploadFile");
		File uploadDir = new File(uploadPath);
		uploadDir.mkdirs();
		String uuid = UUIDUtils.getUUID();
		
		String tempPath = uploadDir.getAbsolutePath() + File.separatorChar + uuid + "." + ext;
		File tempFile = new File(tempPath);
		System.out.println("文件写入" + tempFile.getAbsolutePath());
		attachment.write(tempFile);
		 
		Transaction trans = this.getTransaction();
		HSSFWorkbook workbook = null;
		
		int repeatAmount=0;
		int userErrorAmount = 0;
		
		try {
			
			workbook=new HSSFWorkbook(new FileInputStream(tempFile));
			HSSFSheet sheet=workbook.getSheetAt(0);
			int amount=sheet.getLastRowNum();
			
			for (int r=1;r<=amount;r++){
				
				HSSFRow row=sheet.getRow(r);
				
				String coname=this.getString(row, 0);
				if(StringUtils.isEmpty(coname)){
					continue;
				}

				boolean nameExist = trans.exist("select count(*) from supplier where coname = ?", new Object[] {coname});
				if(nameExist) {
					repeatAmount++;
					continue;
				}
				
	            String coaddr=this.getString(row, 1);
	             
	            String copost=this.getString(row, 2);
	            String city=this.getString(row, 3);
				String p=this.getString(row, 4);
				String c=this.getString(row, 5);
				String hy=this.getString(row, 6);
				String zzxs=this.getString(row, 7);
				String cokhjb=this.getString(row,8);
				String username=this.getString(row,9);
				
				if(StringUtils.isEmpty(username)) {
					userErrorAmount ++;
				}
				
				String sql_name="select count(*) from username where name=? ";
				boolean userExist = trans.exist(sql_name, new Object[] {username});
				if(!userExist){
					userErrorAmount ++;
				}
				
				Map<String,Object> user = trans.queryForMap("select * from username where name=?", new Object[] {username},true);

				Integer departmentId = (Integer)user.get("department_id");
				Department depart = departmentDao.getDepartment(departmentId);
				String dept=depart.getName();
				String deptjb=depart.getDepartCode();

	           String cotel=this.getString(row,10);
	           String cofax=this.getString(row,11);
	           String email= this.getString(row,12);
	           String conet= this.getString(row,13);
	           String linkman=this.getString(row,14);
	           String linkman_wap=this.getString(row,15);
	           String remark=this.getString(row,16);
				
	           String currentDate=DateUtil.getCurrentDateSimpleStr();
	           
	           String maxNumber = trans.getString("select max(co_number) from supplier");
	           int max = Integer.parseInt(maxNumber);
	           int inNo = max+1;
	           String co_number = inNo<10?"000"+inNo:inNo<100?"00"+inNo:inNo<1000?"0"+inNo:""+inNo;
	           
	           String strSQLup="insert into supplier(co_number,coname,coaddr,copost,city,country,province,cofrdb,cozzxs,cozczb,coyyzz,cotypes,tradetypes,cokhjb,cokhyh,coyhzh,coclrq,ifjckq,cotel,cofax,codzyj,conet,cosyhb,cojsfs,nshm,number,username,dept,deptjb,modman,mod_date,share,yearearning,valueco,rg_date,annual_sales,sales_ability,qlty_control,companymt,scale,warehouse,describee,in_no) values('"+co_number+"','" + coname + "','" + coaddr +"','" + copost + "','" + city + "','" + c + "','" + p + "','','" + zzxs + "','','','','" + hy + "','" + cokhjb+ "','','','','','" + cotel + "','" + cofax+ "','" + email + "','" + conet + "','RMB','"+linkman+"','"+linkman_wap+"','','" + username + "','" + dept + "','" + deptjb + "','" + username + "','" + currentDate + "','否','','','" + currentDate + "','','','','','','','" + remark + "','0')";

	           trans.executeUpdate(strSQLup);
	           
	           
			}
			trans.commit();
			Map<String,Object> res = new HashMap<String,Object>();
	        res.put("success", true);
	        res.put("amount", amount);
	        res.put("repeatAmount", repeatAmount);
	        res.put("userErrorAmount", userErrorAmount);
	        return res;
		}catch(Exception ex) {
			trans.rollback();
			return this.errorMsg("导入异常");
			
		}finally {
			trans.close();
			if(workbook!=null) {
				workbook.close();
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Map<String,Object> listContact(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean();
		String username = this.getUsername(runtime);
		String deptjb = this.getDeptjb(runtime);
		boolean hasView = this.existRight(runtime, "supview");
		String sqlWhere = " where ";
		if (hasView) {
			sqlWhere += "deptjb  like '" + deptjb + "%'";
		} else {
			sqlWhere += "(share='是' or username='" + username + "')";
		}
		
		String name = runtime.getParam("name");
		if (StringUtils.isNotEmpty(name)) {
			sqlWhere += " and name like '%" + name + "%' ";
		}
		String sql = "select count(*) from qlinkman";
		int intRowCount = dbManager.getCount(sql + sqlWhere);
		sql = "select top " + pageBean.getTop() + " * from qlinkman";
		List list = dbManager.queryForList(sql + sqlWhere, true);
		return this.getPagingResult(list, pageBean, intRowCount);
	}
	
	/**
	 * 获取供应商所属的用户
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getSupplierByName(WebRuntime runtime) throws Exception {
		String coname = runtime.getParam("coname");
		String sql = "select * from supplier where coname = ?";
		Map<String,Object> supplier = dbManager.queryForMap(sql, new Object[] {coname}, true);
		return this.success("supplier", supplier);
	}
	
	public Map<String,Object> shift(WebRuntime runtime) throws Exception {
		String man1 = runtime.getParam("man1");
		String man2 = runtime.getParam("man2");
		String share=runtime.getParam("share");
		String coname=runtime.getParam("coname");
		boolean man2Exist = userService.existUser(man2);
		if (!man2Exist) {
			return this.errorMsg("目标用户不存在");
		}

		Map<String,Object> dept = userService.getUserDept(man2);
		String dept2=(String) dept.get("deptname");
		 String deptjb2=(String) dept.get("deptjb");

		 String strSQLc = "select * from supplier where coname = ?";
		 Map<String,Object> supplier = dbManager.queryForMap(strSQLc, new Object[] {coname}, true);
		 
		 if (supplier==null) {
			 return this.errorMsg("供应商不存在");
		 }
		 
		 Transaction trans = this.getTransaction();
		 try {
			 String strSQLman="update qlinkman set username='"+man2+"',dept='"+dept2+"',deptjb='"+deptjb2+"'  where  coname='"+coname+"' and username='"+man1+"' ";
			 trans.update(strSQLman);
			 String strSQLop="update procure_xj set man='"+man2+"'  where  coname='"+coname+"' and man='"+man1+"' ";
			 trans.update(strSQLop);
			 String strSQLact="update procure set man='"+man2+"',l_dept='"+dept2+"',l_deptjb='"+deptjb2+"'  where  coname='"+coname+"' and man='"+man1+"' ";
			 trans.update(strSQLact);
			 String strSQLq="update payment set remark='"+man2+"',wtfk='"+deptjb2+"'  where  supplier='"+coname+"' and remark='"+man1+"' ";
			 trans.update(strSQLq);
			 String strSQLsub="update gathering_customer set pname='"+man2+"',dept='"+dept2+"',deptjb='"+deptjb2+"'  where  coname='"+coname+"' and pname='"+man1+"' ";
			 trans.update(strSQLsub);
			 String strSQL="update supplier set username='" + man2  + "',share='" + share + "',dept='"+dept2+"',deptjb='"+deptjb2+"' where  username='" + man1 + "' and  coname like '%" + coname + "%'";
			 trans.update(strSQL);
			 trans.commit();
		 }catch(Exception e) {
			 trans.rollback();
			 return this.errorMsg("转移出现异常");
		 } finally {
			 trans.close();
		 }
		 return this.success();
	}

}
