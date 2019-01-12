package com.tntxia.oa.util;

import java.math.BigDecimal;

public class BigDecimalUtils {
	
	public static BigDecimal toBigDecimal(String val) {
		if(val==null) {
			return null;
		}
		Double d = Double.valueOf(val);
		return BigDecimal.valueOf(d);
	}

}
