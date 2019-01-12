package com.tntxia.oa.inquiry;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InquiryManager {

	public boolean exist(String epro) {
		
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id from xjpro where epro  like '%" + epro + "%'";
		ResultSet rs = null;

		boolean result = false;

		try {
			rs = einfodb.executeQuery(sql);
			
			if (rs.next()) {
				result = true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}
	
	public static boolean exist(String pro_model,int id){
		infocrmdb einfodb = new infocrmdb();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date lastDate = new Date(System.currentTimeMillis()-4*24*3600*1000);
		String sql = "select top 1 id from  ixjview where quotedatetime >= '" + sdf.format(lastDate) + "' and product='"+pro_model+"' and id!="+id;
		ResultSet rs = null;

		boolean result = false;

		try {
			rs = einfodb.executeQuery(sql);
			
			while (rs.next()) {
				int c = rs.getInt("id");
				if(c>0){
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}
	
	public static ArrayList<Inquiry> getInquiry(String beginDate,String pro_model){
		
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,coname,man,quotedatetime from ixjview where quotedatetime >= '" + beginDate + "' and product='"+pro_model+"'";
		ResultSet rs = null;

		ArrayList<Inquiry> result = new ArrayList<Inquiry>();

		try {
			rs = einfodb.executeQuery(sql);
			
			while (rs.next()) {
				Inquiry in = new Inquiry();
				in.setId(rs.getInt("id"));
				in.setConame(rs.getString("coname"));
				in.setMan(rs.getString("man"));
				in.setQuotedatetime(rs.getString("quotedatetime"));
				result.add(in);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			einfodb.close();
		}

		return result;
	}

}
