package com.tntxia.oa.purchasing.actionbean;

import java.util.List;
import java.util.Map;

import com.tntxia.oa.purchasing.dao.PurchasingDao;
import com.tntxia.oa.purchasing.dao.PurchasingProductDao;
import com.tntxia.web.ActionBeanInterface;
import com.tntxia.web.WebInput;

public class GetCgProListBean implements ActionBeanInterface {
	
	private PurchasingProductDao purchasingProductDao;
	
	private PurchasingDao purchasingDao;
	
	public void setPurchasingProductDao(
			PurchasingProductDao purchasingProductDao) {
		this.purchasingProductDao = purchasingProductDao;
	}
	
	
	public PurchasingDao getPurchasingDao() {
		return purchasingDao;
	}


	public void setPurchasingDao(PurchasingDao purchasingDao) {
		this.purchasingDao = purchasingDao;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(WebInput input, Map runtime) throws Exception {
		
		String id = input.getParam("id");
		List proList = purchasingProductDao.getProductByPurchasingId(id);
		for(Object pro:proList){
			Map map = (Map) pro;
			Integer purchaseProductId = (Integer)map.get("id");
//			int rknum = purchasingDao.getRkNum(purchaseProductId);
//			map.put("rknum",rknum);
		}
		runtime.put("result", proList);
		

	}

}
