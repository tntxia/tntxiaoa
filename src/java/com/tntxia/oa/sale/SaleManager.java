package com.tntxia.oa.sale;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.oa.model.SaleOrder;
import com.tntxia.oa.model.SalePro;

public class SaleManager {
	
	
	/**
	 * 通过订单号来获取订单信息
	 * @param number
	 * @return
	 */
	public SaleOrder getSaleOrder(String number){
		
		SaleOrder result = new SaleOrder();
		infocrmdb einfodb = new infocrmdb();
		String strSQL = "select item from subscribe where number='" + number + "' ";
		
		ResultSet rs = einfodb.executeQuery(strSQL);
		
		try {
			if (rs.next()) {
				result.setRate(rs.getString("item"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			einfodb.close();
		}
		
		return result;
	}
	
	public SalePro getSalePro(String id){
		
		infocrmdb einfodb = new infocrmdb();
		
		String strSQL = "select * from ddpro where id='"
				+ id + "' ";

		ResultSet sqlRst = einfodb.executeQuery(strSQL);
		
		SalePro salePro = new SalePro();
		
		try {
			if (!sqlRst.next()) {
				return null;
			}
			salePro.setId(sqlRst.getInt("id"));
			salePro.setEpro(sqlRst.getString("epro").trim());
			salePro.setCpro(sqlRst.getString("cpro"));
			salePro.setNum(sqlRst.getInt("num"));
			salePro.setS_num(sqlRst.getInt("s_num"));
			salePro.setS_c_num(sqlRst.getInt("s_c_num"));
			salePro.setUnit(sqlRst.getString("unit"));
			salePro.setWid(sqlRst.getString("wid").trim());
			salePro.setFypronum(sqlRst.getString("fypronum"));
			salePro.setFy_states(sqlRst.getString("fy_states"));
			salePro.setSupplier(sqlRst.getString("supplier"));
			salePro.setRemark(sqlRst.getString("remark"));
			salePro.setS_tr_date(sqlRst.getString("s_tr_date"));
			salePro.setPrice(sqlRst.getString("selljg"));
			salePro.setPricehb(sqlRst.getString("pricehb"));
			salePro.setP_check(sqlRst.getString("p_check"));
			sqlRst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			einfodb.close();
		}
		
		return salePro;
	}
	
	/**
	 * 获得销售产品信息
	 * @param ddid
	 * @return
	 */
	public List<SalePro> getSalePros(String ddid){
		infocrmdb einfodb = new infocrmdb();
		
		String sql = "select * from ddpro where ddid='"
				+ ddid + "' ";

		ResultSet sqlRst = einfodb.executeQuery(sql);
		
		List<SalePro> res = new ArrayList<SalePro>();
		
		try {
			while (sqlRst.next()) {
				SalePro salePro = new SalePro();
				salePro.setId(sqlRst.getInt("id"));
				salePro.setEpro(sqlRst.getString("epro").trim());
				salePro.setCpro(sqlRst.getString("cpro"));
				salePro.setNum(sqlRst.getInt("num"));
				salePro.setS_num(sqlRst.getInt("s_num"));
				salePro.setS_c_num(sqlRst.getInt("s_c_num"));
				salePro.setUnit(sqlRst.getString("unit"));
				salePro.setWid(sqlRst.getString("wid").trim());
				salePro.setFypronum(sqlRst.getString("fypronum"));
				salePro.setSupplier(sqlRst.getString("supplier"));
				salePro.setRemark(sqlRst.getString("remark"));
				salePro.setS_tr_date(sqlRst.getString("s_tr_date"));
				salePro.setPrice(sqlRst.getString("selljg"));
				salePro.setPricehb(sqlRst.getString("pricehb"));
				salePro.setP_check(sqlRst.getString("p_check"));
				salePro.setSelljg(sqlRst.getString("selljg"));
				salePro.setSalejg(sqlRst.getDouble("salejg"));
				res.add(salePro);
			}
			
			sqlRst.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally{
			einfodb.close();
		}
		
		return res;
	}

}
