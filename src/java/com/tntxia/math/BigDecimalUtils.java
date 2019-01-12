package com.tntxia.math;

import java.math.BigDecimal;

public class BigDecimalUtils {
	
	public static BigDecimal getBigDecimal(Number number) {
		
		if(number instanceof BigDecimal) {
			return (BigDecimal)number;
		}
		
		if(number instanceof Integer) {
			Integer integerNumber = (Integer) number;
			return BigDecimal.valueOf(integerNumber);
		}
		return null;
	}
	
	public static BigDecimal multiply(Number number1,Number number2) {
		BigDecimal b1 = getBigDecimal(number1);
		BigDecimal b2 = getBigDecimal(number2);
		return b1.multiply(b2);
	}
	
	public static BigDecimal parse(String str) {
		Double d = Double.parseDouble(str);
		return BigDecimal.valueOf(d);
	}
	
	

}
