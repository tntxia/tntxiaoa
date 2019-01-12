package com.tntxia.oa.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tntxia.db.DBConnection;
import com.tntxia.db.DBUtil;

/**
 * 
 * ��������
 * 
 * @author tntxia
 * 
 */
public class ApprovalProcessDao {

	/**
	 * �鿴�û��Ƿ�ɹ�������
	 * 
	 * @param name
	 * @return
	 */
	public boolean isPurchasingApprovalMan(String name) {

		DBConnection db = new DBConnection();

		String sqlr = "select  * from cgsp where dd_man='" + name + "'";
		ResultSet rsr = db.executeQuery(sqlr);
		boolean res = false;
		try {
			if (rsr.next()) {
				res = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rsr);
			db.close();
		}
		return res;
	}

	/**
	 * �鿴�û��Ƿ�ɹ���������
	 * 
	 * @param name
	 * @return
	 */
	public boolean isPurchasingApprovalManFinal(String name) {

		String sqlr = "select  * from cgsp where fif='��'   and  fspman='"
				+ name + "' ";

		DBConnection db = new DBConnection();
		ResultSet rsr = db.executeQuery(sqlr);
		boolean res = false;
		try {
			if (rsr.next()) {
				res = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rsr);
			db.close();
		}
		return res;

	}
	
	public boolean isContactApprovalMan(String name){
		
		DBConnection db = new DBConnection();
		String sqlht="select  * from shtsp where  ifsp='��'   and  dd_man='"+name+"' ";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
		
	}
	
	public boolean isContactApprovalManSec(String name){
		
		DBConnection db = new DBConnection();
		String sqlht="select  * from shtsp where    iffsp='��'   and  fsp_man='"+name+"' ";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
		
	}
	
	public boolean isContactApprovalManFinal(String name){
		
		DBConnection db = new DBConnection();
		String sqlht="select  * from shtsp where    firsp='��'   and  fir_man='"+name+"' ";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
		
	}
	
	public boolean isSampleApprovalMan(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from sam_sp where    dd_man='"+name+"'";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}
	
	public boolean isSampleApprovalManFinal(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from sam_sp where    fif='��'   and  fspman='"+name+"'";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}
	
	public boolean isArrangeApprovalMan(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from dhsp where    dd_man='"+name+"'  ";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}
	
	public boolean isArrangeApprovalManFinal(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from dhsp where      fif='��'   and  fspman='"+name+"'";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}
	
	public boolean isOrderApprovalMan(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from ddsp where    dd_man='"+name+"' ";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}
	
	public boolean isOrderApprovalManFinal(String name){
		DBConnection db = new DBConnection();
		String sqlht="select  * from ddsp where    fif='��'   and  fspman='"+name+"'";
		ResultSet rsht=db.executeQuery(sqlht);
		boolean res = false;
		try {
			if(rsht.next())
			  {
				res = true;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeResultSet(rsht);
			db.close();
		}
		return res;
	}

}
