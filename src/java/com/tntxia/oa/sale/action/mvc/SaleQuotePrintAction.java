package com.tntxia.oa.sale.action.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.common.action.OAMVCAction;
import com.tntxia.oa.sale.dao.QuoteDao;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.util.Dom4jUtil;

public class SaleQuotePrintAction extends OAMVCAction {
	
	private DBManager dbManager = this.getDBManager();
	
	private QuoteDao quoteDao = new QuoteDao();
	
	private Map<String,Object> getCompany(String id) throws Exception{
		String sql = "select * from company where id = ?";
		return dbManager.queryForMap(sql, new Object[]{id},true);
	}
	
	private String getStamp(HttpServletRequest request) throws DocumentException{
		
		String stampDesignPath = request.getServletContext().getRealPath("/WEB-INF/config/design/stamp.xml");
		
		Document doc = Dom4jUtil.getDoc(stampDesignPath);
		
		String stamp = Dom4jUtil.getText(doc, "header/img");
		return stamp;
	}
	
	@Override
	public void init(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		this.put("title","打印报价单");
		
		String id = request.getParameter("id");
		String cid = request.getParameter("cid");
		
		PageBean pageBean = this.getPageBean(request);
		
		this.setToPage("sale/quote/cprint.ftl");
		
		this.putAll(quoteDao.getDetail(id));
		this.put("page", pageBean.getPage());
		this.put("pageCount", 1);
		this.put("company", this.getCompany(id));
		this.put("list", quoteDao.getList(id,pageBean));
		this.put("company", this.getCompany(cid));
		this.put("unitList", SystemCache.unitList);
		this.put("currentList", SystemCache.currentList);
		this.put("stamp", this.getStamp(request));
		this.put("totalPrice", quoteDao.getTotalPrice(id));
		
	}

}
