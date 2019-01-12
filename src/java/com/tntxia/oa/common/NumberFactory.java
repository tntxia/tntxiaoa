package com.tntxia.oa.common;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import com.tntxia.jdbc.Transaction;
import com.tntxia.web.util.DatasourceStore;

public class NumberFactory {
	
	private static void addGenerateNumber(Transaction trans, String year,
			String keyType) throws SQLException {
		String sql = "insert into key_generate(year,in_no,key_type) values(?,1,?)";
		trans.update(sql, new Object[] { year, keyType });
	}
	
	private static void updateGenerateNumber(Transaction trans, String year,
			String keyType,int in_no) throws SQLException {
		String sql = "update key_generate set in_no = ? where year=? and key_type = ? ";
		trans.update(sql, new Object[] { in_no,year, keyType });
	}
	
	public static String generateNumber(String keyType) throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		String sql = "select top 1 in_no from key_generate where year = ? and key_type = ?";
		String result = null;

		synchronized (NumberFactory.class){
			String year = sdf.format(new Date(System.currentTimeMillis()));
			
			DataSource datasource = DatasourceStore.getDatasource("default");
			Transaction trans = Transaction.createTrans(datasource.getConnection());
			
			int in_no = trans.queryForInt(sql, new Object[] { year, keyType });
			in_no += 1;
			if (in_no == 1) {
				addGenerateNumber(trans, year, keyType);
			}else {
				updateGenerateNumber(trans, year, keyType, in_no);
			}
			
			int numberLength = 6;
			StringBuffer sb = new StringBuffer();
			sb.append(in_no + "");
			while (sb.length() < numberLength) {
				sb.insert(0, "0");
			}
			result = keyType + year +sb.toString();
			trans.commit();
			trans.close();
		}
		return result;
	}
	

}
