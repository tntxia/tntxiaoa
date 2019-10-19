package com.tntxia.oa.knowledge.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.mvc.view.FileView;
import com.tntxia.web.util.UUIDUtils;

public class KnowledgeAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> listType(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from km_ty  order by  id desc";
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from km_ty";
		int count = dbManager.getCount(sql);
		return this.getPagingResult(list, pageBean, count);		
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception {
		String sql = "select * from km_ty where id = ?";
		String id = runtime.getParam("id");
		return this.success("detail", dbManager.queryForMap(sql,new Object[] {id}, true));
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {
		PageBean pageBean = runtime.getPageBean();
		String pid = runtime.getParam("pid");
		String sql = "select l.*, k.km_name from lawtable l left outer join km_ty k on l.km_types = k.id where km_types = ? order by id desc";
		List list = dbManager.queryForList(sql, new Object[] {pid}, true);
		sql = "select count(*) from lawtable where km_types = ?";
		int count = dbManager.getCount(sql, new Object[] {pid});
		return this.getPagingResult(list, pageBean, count);	
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		String username=this.getUsername(runtime);
		String km_types=runtime.getParam("km_types");
		String dept=this.getDept(runtime);
		String deptjb=this.getDeptjb(runtime);
		String titel1=runtime.getParam("title");
		
		String km_fa=runtime.getParam("km_fa");
		 String content1=runtime.getParam("content");
		
		 String fvdate1=DateUtil.getCurrentDateSimpleStr();

		 String strSQL="insert into lawtable(titel,km_types,content,km_fa,username,fvdate,dept,deptjb) values('" + titel1 + "','" + km_types +"','" + content1 +"','" + km_fa +"','" + username + "','" + fvdate1 + "','" + dept +"','" + deptjb +"')";
		 dbManager.update(strSQL);
		 return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		
		String id=runtime.getParam("id");
		 String strSQL="delete from lawtable where id = ?";
		 dbManager.update(strSQL,new Object[]{id});
		 return this.success();
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String titel = runtime.getParam("titel");
		String km_types = runtime.getParam("km_types");
		String content = runtime.getParam("content");
		String km_fa = runtime.getParam("km_fa");
		String sql = "update lawtable set titel=?,km_types=?,content=?,km_fa=? where id = ?";
		
		dbManager.update(sql,new Object[]{titel,km_types,content,km_fa,id});
		return this.success();
	}
	
	public Map<String,Object> uploadAttachment(
			@Param("id") String id,
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
		
		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String,Object> res = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
		
		if (res==null || !(boolean)res.get("success")) {
			return this.errorMsg("附件上传失败");
		}
		
		uuid = (String) res.get("uuid");
		
		String sql = "insert into knowledge_attachment(knowledge_id,name,file_path,create_time) values(?,?,?,now())";
		dbManager.update(sql, new Object[]{id,fileNameOriginal,uuid});
			
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	private List listAttachment(String id) throws Exception{
		String sql = "select * from knowledge_attachment where knowledge_id=?";
		return dbManager.queryForList(sql,new Object[]{id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	public List getAttachmentList(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		return this.listAttachment(id);
	}
	
	public Map<String,Object> delAttachment(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from knowledge_attachment where id=?";
		dbManager.update(sql, new Object[]{id});
		return this.success();
	}
	
	public FileView downloadAttachment(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "select * from knowledge_attachment where id=?";
		Map<String,Object> attachment = (Map<String,Object>) dbManager.queryForMap(sql,new Object[]{id}, true);
		String filePath = (String) attachment.get("file_path");
		FileView res = new FileView();
		res.setFilePath(filePath);
		return res;
	}
	
	public Map<String,Object> detail(@Param("id") String id) throws Exception {
		String sql = "select *, k.km_name  from lawtable l left outer join km_ty k on l.km_types = k.id where l.id = ?";
		Map<String, Object> res = dbManager.queryForMap(sql, new Object[] {id}, true);
		res.put("attachList", this.listAttachment(id));
		return res;
	}

}
