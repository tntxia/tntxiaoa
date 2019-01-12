package com.tntxia.oa.util;

public class StringUtils {

	/**
	 * 防止去掉空格的时候，出现空指针的异常
	 * 
	 * @param str
	 * @return
	 */
	public static String safeTrim(String str) {
		if (str != null)
			return str.trim();
		return str;
	}

	/**
	 * 防止去掉空格的时候，出现空指针的异常
	 * 
	 * @param str
	 * @return
	 */
	public static String safeTrim(String str, String defaultValue) {
		if (str != null)
			return str.trim();
		else
			return defaultValue;
	}

	/**
	 * 判断字符串是否为空或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断是否为字符串的“有”，是的话返回checked;
	 * 
	 * @param str
	 * @return
	 */
	public static String haveToChecked(String str) {
		if (str == null) {
			return "";
		}
		String result = "";
		String workadd = str.trim();
		if (workadd.equals("有")) {
			result = "checked";
		}
		return result;
	}

}
