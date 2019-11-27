package com.tntxia.currency;

import java.math.BigDecimal;

public class Rmb {

	public static String lowerToUpper(BigDecimal s) throws Exception {

		if (s == null) {
			s = BigDecimal.ZERO;
		}
		return lowerToUpper(String.valueOf(s.doubleValue()));
	}

	public static String lowerToUpper(String s) throws Exception {
		if (s.indexOf(".") != -1 && s.indexOf(".") < s.length() - 3)
			throw new Exception("只能转换小数点后有两位以内的数！");
		String s1 = s;
		if (s1.indexOf(".") != -1)
			s1 = s1.substring(0, s1.indexOf("."));
		if (s1.length() > 12)
			throw new Exception("不能能转换大于千亿级的数额");
		String s2 = "";
		int i = 0;
		boolean flag = false;

		String s3 = "";
		String s4 = "";

		boolean flag2 = true;
		String as[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String s9 = "分角元拾佰仟万拾佰仟亿拾佰仟";
		s3 = s;
		s3 = s3.replaceAll(",", "");

		i = s3.length();
		for (int j = i - 1; j >= 0; j--) {

			if (!s3.substring(j, j + 1).equals("."))
				continue;

			switch (i - j - 1) {
			case 1: // '\001'
				s3 = s3.replaceAll("\\.", "") + "0";
				break;

			case 2: // '\002'
				s3 = s3.replaceAll("\\.", "");
				break;
			}
			flag2 = false;
			break;
		}

		if (flag2)
			s3 = s3 + "00";
		i = s3.length();
		for (int k = 0; k < i; k++)
			s4 = s3.substring(k, k + 1) + s4;

		s2 = "";

		for (int l = 0; l < i; l++) {
			String s8 = as[Integer.parseInt(s4.substring(l, l + 1))];
			String s6 = s9.substring(l, l + 1);

			if (!s8.equals("零")) {
				s2 = s8 + s6 + s2;
				flag = true;
			} else {
				if (l == 2)
					s2 = s6 + s2;
				if (l == 6)
					s2 = s6 + s2;
				if (l == 10)
					s2 = s6 + s2;
				if (flag)
					s2 = "零" + s2;
				flag = false;
			}
		}

		s2 = s2 + "整";
		s2 = s2.replaceAll("零万", "万");
		s2 = s2.replaceAll("零元", "元");
		return s2;
	}

	public static void main(String args[]) throws Exception {
		String res = Rmb.lowerToUpper("10.01");
		System.out.println(res);
	}
}
