package com.tntxia.oa.finance;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.tntxia.db.DBUtil;
import com.tntxia.oa.entity.Payment;

public class FinanceManager {
	
	private static Logger logger = Logger.getLogger(FinanceManager.class);

	/**
	 * 通过订单号来查询订单的收款信息
	 * 
	 * @param dd_number
	 *            订单号
	 * @return
	 */
	public String getPaymentStatus(String dd_number) {
		String result = "";
		infocrmdb einfodb = new infocrmdb();
		String sql = "select states from gathering where orderform  = '"
				+ dd_number + "'";
		ResultSet rs = null;

		try {
			rs = einfodb.executeQuery(sql);
			while (rs.next()) {

				result = rs.getString("states");

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

	public void addPayment(Payment payment) {
		infocrmdb einfodb = new infocrmdb();
		String sqlspg = "select * from payment where contract='" + payment.getContact()
				+ "'";
		ResultSet rsspg = einfodb.executeQuery(sqlspg);
		java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
		String currentDate=simple.format(new java.util.Date());
		
		boolean isPaymentExists = false;
		
		try{
			isPaymentExists = rsspg.next();
		}catch(Exception e){
			logger.error(e);
		}finally{
			DBUtil.closeResultSet(rsspg);
		}
		
		if (!isPaymentExists) {
			
			String strSQLp = "insert into payment(contract,orderform,sup_number,supplier,pay_je,yjfkdate,sjfkdate,moneyty,moneytypes,htmoney,bank,bankaccounts,paynr,note,states,remark,wtfk) values('"
					+ payment.getContact()
					+ "','"
					+ payment.getOrderform()
					+ "','"
					+ payment.getSup_number()
					+ "','"
					+ payment.getSupplier()
					+ "','0','"
					+ currentDate
					+ "','"
					+ currentDate
					+ "','"
					+ payment.getHb()
					+ "','银行转帐','0','','0','','','待付款','"
					+ payment.getMan()
					+ "','" + payment.getDeptjb() + "')";
			einfodb.executeUpdate(strSQLp);
			
		}
		einfodb.close();
	}

}
