package com.tntxia.oa.test.calendar;

import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		int year2 = 2018;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year2);
		calendar.set(Calendar.MONTH,2);
		calendar.set(Calendar.DATE,1);
		calendar.add(Calendar.DATE, -1);
		
		int r = calendar.get(Calendar.DATE);
		System.out.println(r);
	}

}
