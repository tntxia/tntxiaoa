package com.tntxia.oa.knowledge.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.tntxia.date.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.entity.MultipartForm;
import com.tntxia.web.mvc.view.FileView;

public class KnowledgeAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
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
	
	public Map<String,Object> uploadAttachment(WebRuntime runtime) throws Exception{
		
		MultipartForm multipartForm = runtime.getMultipartForm();
		String knowledgeId = multipartForm.getString("knowledgeId");
		String name = multipartForm.getString("name");
		List<FileItem> fileItemList = multipartForm.getFileItemList();
		for(FileItem fileItem : fileItemList){
			
			String uploadFileRoot = "D:\\oa_upload";
			File rootPath = new File(uploadFileRoot);
			rootPath.mkdirs();
			
			// ��ȡitem�е��ϴ��ļ���������
			InputStream in = fileItem.getInputStream();
			
			String ext = FilenameUtils.getExtension(fileItem.getName());
			String newFileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+ext;

			String uploadPath = uploadFileRoot+File.separator+newFileName;

			// ����һ���ļ������
			FileOutputStream out = new FileOutputStream(uploadPath);
			// ����һ��������
			byte buffer[] = new byte[1024];
			// �ж��������е������Ƿ��Ѿ�����ı�ʶ
			int len = 0;
			// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
			while ((len = in.read(buffer)) > 0) {
				// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" +
				// filename)����
				out.write(buffer, 0, len);
			}
			
			// �ر�������
			in.close();
			// �ر������
			out.close();
			// ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
			fileItem.delete();
			
			String sql = "insert into knowledge_attachment(knowledge_id,name,file_path,create_time) values(?,?,?,now())";
			dbManager.update(sql, new Object[]{knowledgeId,name,uploadPath});
			
		}
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List getAttachmentList(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "select * from knowledge_attachment where knowledge_id=?";
		return dbManager.queryForList(sql,new Object[]{id}, true);
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

}
