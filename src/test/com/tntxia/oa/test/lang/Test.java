package com.tntxia.oa.test.lang;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DecimalFormat nf = new DecimalFormat("0000.0");
		System.out.println(nf.format(10000000));

	}

}
