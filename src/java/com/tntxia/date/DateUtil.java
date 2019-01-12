package com.tntxia.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getCurrentDateStr(){
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(now);
	}
	
	public static String getCurrentDateSimpleStr(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(date);
	}
	
	public static String getNextDateStr(){
		Date nextDate = new Date(System.currentTimeMillis()+(24*3600*1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(nextDate);
	}
	
	public static String changeDate(String date,String fmtOld,String fmtNew){
		SimpleDateFormat sdf = new SimpleDateFormat(fmtOld);
		String result = "";
		try{
			Date d = sdf.parse(date);
			SimpleDateFormat sdf2 = new SimpleDateFormat(fmtNew);
			result = sdf2.format(d);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static Date getCurrentDate(){
		return new Date(System.currentTimeMillis());
	}
	
	
	
	public static Date getTodayStart(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date(System.currentTimeMillis()));
		Date result = null;
		try{
			result = sdf.parse(dateStr);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public static Date getTomorrowStart(){
		Date result = new Date(getTodayStart().getTime()+24*3600*1000);
		return result;
	}
	
	public static String getNextDate(String date) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, 1);
		return sdf.format(cal.getTime()) ;
	}
	
	public static void main(String args[]) throws ParseException{
		System.out.println(DateUtil.getNextDate("2018-05-31"));
	}

}
