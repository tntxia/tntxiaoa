package com.tntxia.oa.purchasing.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.procure.ProcureManager;
import com.tntxia.oa.purchasing.dao.PurchasingDao;
import com.tntxia.oa.purchasing.dao.PurchasingProductDao;
import com.tntxia.oa.purchasing.dao.UnitDao;
import com.tntxia.oa.purchasing.entity.Purchasing;
import com.tntxia.oa.purchasing.entity.PurchasingProduct;
import com.tntxia.oa.purchasing.entity.Unit;
import com.tntxia.oa.right.Restrain;
import com.tntxia.oa.right.RightManager;

public class PurchasingViewAction implements Controller {
	
	private PurchasingDao purchasingDao;
	
	private PurchasingProductDao purchasingProductDao;
	
	private UnitDao unitDao;
	
	public void setPurchasingDao(PurchasingDao purchasingDao) {
		this.purchasingDao = purchasingDao;
	}
	
	public void setPurchasingProductDao(PurchasingProductDao purchasingProductDao) {
		this.purchasingProductDao = purchasingProductDao;
	}
	
	public void setUnitDao(UnitDao unitDao) {
		this.unitDao = unitDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse arg1) throws Exception {
		
		String id = req.getParameter("id");
		
		HttpSession session = req.getSession();
		
		String restrain_name = (String) session.getAttribute("restrain_name");
		
		RightManager rightManager = new RightManager();
		
		Restrain restrain = rightManager.getRestrain(restrain_name);
		
		String cgmod=restrain.getCgmod();
		String cgdel=restrain.getCgdel();
		
		Purchasing purchasing = purchasingDao.getPurchasingById(id);
		
		if(purchasing.getNumber()==null){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("success", false);
			result.put("message", "订单不存在!!!");
			return new ModelAndView("common/result", result);
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String sub = purchasing.getSaleNumber();
		
		ProcureManager procureManager = new ProcureManager();
		
		String saleMan = procureManager.getSaleMan(sub);
		
		result.put("purchasing", purchasing);
		
		result.put("saleMan", saleMan);
		
		List<PurchasingProduct> productList = purchasingProductDao.getProductByPurchasingId(id);
		
		result.put("proList", productList);
		
		if("有".equals(cgmod)){
			result.put("cgmod", true);
		}else{
			result.put("cgmod", false);
		}
		
		if("有".equals(cgdel)){
			result.put("cgdel", true);
		}else{
			result.put("cgdel", false);
		}
		
		List<Unit> unitList = unitDao.getUnitList();
		
		result.put("unitList", unitList);
		
		return new ModelAndView("purchasing/purchasingView", result);
	}

}
