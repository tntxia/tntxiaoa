package com.tntxia.oa.finance.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.math.BigDecimalUtils;
import com.tntxia.oa.common.handler.OACommonHandler;
import com.tntxia.oa.finance.dao.FinanceLightDao;
import com.tntxia.oa.sale.dao.SaleLightDao;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 跳转到增加往来帐目凭证的页面
 * 
 * @param request
 * @param arg1
 * @return
 * @throws Exception
 */
public class AddCreditHandler extends OACommonHandler {

	private FinanceLightDao financeDao = new FinanceLightDao();
	
	private SaleLightDao saleDao = new SaleLightDao();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void init(WebRuntime runtime) throws Exception {
		
		Map<String, Object> resultMap = this.getRoot();

		int id = runtime.getInt("id");

		String type = runtime.getParam("type");
		Map<String,Object> gathering = null;

		if (StringUtils.isEmpty(type)) {
			gathering = financeDao.getGathering(id);
		} else {
			gathering = financeDao.getGatheringRefund(id);
		}

		BigDecimal je = BigDecimal.ZERO;

		if (runtime.getParam("je") == null) {
			String saleId = ((String) gathering.get("fyid")).trim();
			List saleProList = saleDao.getSaleProList(saleId);
			for(int i=0;i<saleProList.size();i++) {
				Map<String,Object> map = (Map<String,Object>) saleProList.get(i);
				Integer num = (Integer)map.get("num");
				BigDecimal salejg = (BigDecimal) map.get("salejg");
				je = je.add(BigDecimalUtils.multiply(salejg, num));
			}
			
			BigDecimal bank = (BigDecimal) gathering.get("bank");
			BigDecimal moneyGathered = (BigDecimal) gathering.get("smoney");
			// 应收款数为销售总额-银行存款-已收款
			je = je.add(bank).subtract(moneyGathered);
		} else {
			je = BigDecimal.valueOf(Double.parseDouble(runtime.getParam("je").replaceAll(",",
					"")));
		}

		resultMap.put("gathering", gathering);

		resultMap.put("je", je);

		

	}

}
