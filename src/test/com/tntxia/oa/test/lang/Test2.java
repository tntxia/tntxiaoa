package com.tntxia.oa.test.lang;

public class Test2 {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "select * from test where username=#username# and datetime=#currentDate# and user=#username# and test=#currentDate#";
		String a = "0001";
		System.out.println(Integer.parseInt(a));
	}

}
