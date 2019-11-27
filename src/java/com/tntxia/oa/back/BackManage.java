package com.tntxia.oa.back;

import java.sql.ResultSet;

import infocrmdb.infocrmdb;

public class BackManage {

	public static int getBindWidByDdid(int ddid) {
		infocrmdb einfodb = new infocrmdb();
		String sqldd = "select wid from th_supplier_out_bind where ddid='" + ddid
				+ "'";
		ResultSet rs = einfodb.executeQuery(sqldd);
		int result = 0;
		try {
			if (rs.next()) {
				result = rs.getInt("wid");
			}
			einfodb.close();
		} catch (Exception e) {
			e.printStackTrace();
			einfodb.close();
		} finally {
			einfodb.close();
		}
		return result;
	}
}
