package com.tntxia.oa.user.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.oa.common.action.CommonDoAction;
import com.tntxia.oa.system.entity.User;
import com.tntxia.oa.user.service.UserService;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.util.UUIDUtils;

public class UserAction extends CommonDoAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private UserService userService = new UserService();
	
	@SuppressWarnings("rawtypes")
	public List list(WebRuntime runtime) throws Exception {
		return dbManager.queryForList("select * from username", true);
	}
	
	public Map<String,Object> changePass(WebRuntime runtime) throws Exception{
		
		String passwordOld = runtime.getParam("passwordOld");
		String username = this.getUsername(runtime);
		String password = runtime.getParam("password");
		
		User user = userService.getUser(username);
		String pass = user.getPassword();
		if (!passwordOld.equals(pass)) {
			return this.errorMsg("原密码错误");
		}
		
		String sql = "update username set password = ? where name = ?";
		dbManager.update(sql, new Object[]{password,username});
		return this.success();
	}
	
	/**
	 * 获取用户的头像ID
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> picId(WebRuntime runtime) throws Exception{
		String username = this.getUsername(runtime);
		String sql = "select pic_id from username where name = ?";
		String picId = dbManager.getString(sql, new Object[] {username});
		return this.success("picId", picId);
	}
	
	/**
	 * 用户图片上传
	 * @param pic
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> uploadPic(@Param("pic") FileItem pic, WebRuntime runtime) throws Exception{
		
		String filename = pic.getName();
		String ext = FilenameUtils.getExtension(filename);
		String tempDirPath = runtime.getRealPath("/WEB-INF/temp");
		File tempDir = new File(tempDirPath);
		tempDir.mkdirs();
		String uuid = UUIDUtils.getUUID();
		String tempPath = tempDir.getAbsolutePath() + File.separatorChar + uuid + "." + ext;
		File file = new File(tempPath);
		pic.write(file);
		
		
		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String, Object> transResult = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
		uuid = (String) transResult.get("uuid");
		
		
		String sql = "update username set pic_id = ?  where name = ?";
		String username = this.getUsername(runtime);
		dbManager.update(sql, new Object[] {uuid, username});
		return this.success("uuid", uuid);
		
	}

}
