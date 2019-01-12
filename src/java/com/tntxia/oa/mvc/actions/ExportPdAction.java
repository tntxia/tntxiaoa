package com.tntxia.oa.mvc.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.warehouse.WarehouseStokingManager;

public class ExportPdAction implements Controller {
	
	private final static String PD = "pd";
	
	private final static String STATUS_NORMAL = "normal";
	
	private final static String STATUS_EXCEPTION = "exception";

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String wid = request.getParameter("wid");
		String pro_addr = SystemCache.getWarehouseTypeName(wid);
		int status = Integer.parseInt(request.getParameter("status"));
		Boolean isAll = Boolean.parseBoolean(request.getParameter("isAll"));
		String path = request.getRealPath("/");

		WarehouseStokingManager warehouseManager = new WarehouseStokingManager();

		int pageNo = 1;
		int pageSize = 50;
		int startIndex = 0;
		int endIndex = pageSize - 1;

		if (isAll) {
			startIndex = 0;
			endIndex = 0;
		} else {
			if (request.getParameter("page") != null) {
				String strPage = request.getParameter("page");
				pageNo = Integer.parseInt(strPage);
			}
			startIndex = (pageNo - 1) * pageSize;
			endIndex = pageNo * pageSize;
		}

		warehouseManager.exportPd(pro_addr, startIndex, endIndex, path, status);

		String filePath;
		String filename;

		if (status == 1) {
			filePath = path + "\\pd_normal.xls";
			filename = PD+"_"+STATUS_NORMAL;
			if(isAll){
				filename+= "_all";
			}else{
				filename+= "_page_"+pageNo;
			}
			filename+= ".xls";
		} else {
			filePath = path + "\\pd_exception.xls";
			filename = PD+"_"+STATUS_EXCEPTION;
			if(isAll){
				filename+= "_all";
			}else{
				filename+= "_page_"+pageNo;
			}
			filename+= ".xls";
		}

		response.setContentType("application/octet-stream");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(filename.getBytes(), "UTF-8") + "\"");
			ServletOutputStream out = response.getOutputStream();

			File file = new File(filePath);

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

	

}
