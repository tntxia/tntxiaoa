package com.tntxia.oa.sale.handler;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tntxia.date.DateUtil;
import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.HandlerWithHeaderAndLeftbar;
import com.tntxia.oa.sale.dao.SampleDao;


import com.tntxia.web.mvc.WebRuntime;

public class SampleDetailViewDDDHandler extends HandlerWithHeaderAndLeftbar {
	
	private SampleDao sampleDao = new SampleDao();

	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		String id=runtime.getParam("id");
		
		Map<String,Object> detail = sampleDao.getDetail(id);
		
		String fif = (String) detail.get("fif");
		
		if("是".equals(fif)){
			detail.put("needSecondAudit", true);
		}else{
			detail.put("needSecondAudit", false);
		}
		
		List list = sampleDao.getList(id);
		BigDecimal total = BigDecimal.ZERO;
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>) list.get(i);
			Integer num = (Integer) map.get("num");
			BigDecimal salejg = (BigDecimal) map.get("salejg");
			total = total.add(BigDecimalUtils.multiply(salejg, num));
		}
		
		this.setRootValue("detail", detail);
		this.setRootValue("list", list);
		this.setRootValue("total", total);
		this.setRootValue("r_sam_add", this.existRight(runtime, "r_sam_add"));
		this.setRootValue("currentDate", DateUtil.getCurrentDateSimpleStr());
		this.setRootValue("id", id);
		
	}

}
