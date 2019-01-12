package com.tntxia.oa.supplier;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;

public class SupplierManager {
	
	public Supplier getSupplier(String co_number){
		infocrmdb db = new infocrmdb();
		String sql = "select id,receiver,receiver_tel,receiver_addr,freight,express_company,receiver_tel,receiver_addr,freight from supplier where co_number ='"+co_number+"'";
		Supplier supplier = null;
		ResultSet rs = null;
		try{
			rs = db.executeQuery(sql);
			if(rs.next()){
				supplier = new Supplier();
				supplier.setId(rs.getInt("id"));
				supplier.setReceiver(rs.getString("receiver"));
				supplier.setReceiver_tel(rs.getString("receiver_tel"));
				supplier.setReceiver_addr(rs.getString("receiver_addr"));
				supplier.setFreight(rs.getString("freight"));
				supplier.setExpress_company(rs.getString("express_company"));
				supplier.setAcct(rs.getString("acct"));
				supplier.setService_type(rs.getString("service_type"));
				supplier.setPay_type(rs.getString("pay_type"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close();
		}
		return supplier;
	}
	
	public String getSupplierPf(String co_number){
		infocrmdb db = new infocrmdb();
		String sql = "select avg(rfq),avg(bj),avg(ch),avg(fh),avg(th) from pf_supplier where co_number = '"+co_number+"'";
      	String result = "";
      	ResultSet rs = null;
		try{
			
			rs = db.executeQuery(sql);
			if(rs.next()){
				result = rs.getDouble(1)+","+rs.getDouble(2)+","+rs.getDouble(3)+","+rs.getDouble(4)+","+rs.getDouble(5);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close();
		}
		return result;
	}
	
	// 查询供应商的开发票情况
	public String getSupplierTaxBill(String co_number){
		infocrmdb db = new infocrmdb();
		String sql = "select cotypes from supplier where co_number ='"+co_number+"'";
		Supplier supplier = null;
		ResultSet rs = null;
		String cotypes = "";
		try{
			rs = db.executeQuery(sql);
			if(rs.next()){
				cotypes = rs.getString("cotypes");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close();
		}
		return cotypes;
	}
	
	// 查询供应商的开发票情况
	public String getSupplierTaxBillName(String co_number){
		String cotypes = this.getSupplierTaxBill(co_number);
		if(cotypes.equals("0")){
			return "0个点";
		}else if(cotypes.equals("3")){
			return "3个点";
		}else if(cotypes.equals("17")){
			return "17个点";
		}else{
			return "无开票";
		}
	}

}
