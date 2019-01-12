package com.tntxia.oa.util;

public class StringUtils {

	/**
	 * ��ֹȥ���ո��ʱ�򣬳��ֿ�ָ����쳣
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
	 * ��ֹȥ���ո��ʱ�򣬳��ֿ�ָ����쳣
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
	 * �ж��ַ����Ƿ�Ϊ�ջ���ַ���
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * �ж��Ƿ�Ϊ�ַ����ġ��С����ǵĻ�����checked;
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
		if (workadd.equals("��")) {
			result = "checked";
		}
		return result;
	}

}
