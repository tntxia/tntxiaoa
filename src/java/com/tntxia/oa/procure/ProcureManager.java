package com.tntxia.oa.procure;

import infocrmdb.infocrmdb;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.tntxia.oa.current.CurrentManager;

public class ProcureManager {
	
	public String getRate(String number){
		String result = "";
		infocrmdb einfodb = new infocrmdb();
		String sql = "select rate from procure where number = '"+number+"'";
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				if(rs.getString("rate")!=null)
					result =rs.getString("rate");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		
		
		return result;
	}
	
	/**
	 * 查询采购订单的总价
	 * @param number
	 * @return
	 */
	public double getTotalBuyPrice(String number){
		double result = 0;
		infocrmdb einfodb = new infocrmdb();
		String sql = "select selljg from cgpro where ddid in (select id from procure number = '"+number+"')";
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				result +=rs.getDouble("selljg");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 查询采购订单的总价
	 * @param number
	 * @return
	 */
	public double getTotalBuyPrice(int id){
		double result = 0;
		infocrmdb einfodb = new infocrmdb();
		String sql = "select selljg from cgpro where ddid ="+id;
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				result +=rs.getDouble("selljg");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 查询销售订单的总价
	 * @param number
	 * @return
	 */
	public double getTotalSalePrice(String number){
		double result = 0;
		infocrmdb einfodb = new infocrmdb();
		String sql = "select selljg from ddpro where ddid in (select id from subscribe number = '"+number+"')";
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				result +=rs.getDouble("selljg");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	public Subscribe getSubscribe(String sub){
		Subscribe subscribe = new Subscribe();
		String sql = "select number,money from subscribe where number='"+sub+"'";
		infocrmdb einfodb = new infocrmdb();
		ResultSet rs = null;
		try{
			rs = einfodb.executeQuery(sql);
			if(rs.next()){
				subscribe.setNumber(rs.getString("number"));
				subscribe.setMoney(rs.getString("money"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ArrayList<DdPro> ddPros = getDdPro(sub);
		subscribe.setPros(ddPros);
		return subscribe;
	}
	
	/**
	 * 通过采购订单ID查询采购订单信息
	 * @param id
	 * @return
	 */
	public Procure getProcure(String id){
		
		Procure result = new Procure();
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,number,sub,l_spyj,money,man from procure where id = "+id;
		ResultSet rs = null;
		
		CurrentManager currManager = new CurrentManager();
		currManager.init();
		
		try{
			rs = einfodb.executeQuery(sql);
			if(rs.next()){
				result.setId(rs.getInt("id"));
				result.setNumber(rs.getString("number"));
				result.setMan(rs.getString("man"));
				result.setSub(rs.getString("sub"));
				result.setL_spyj(rs.getString("l_spyj"));
				result.setMoney(rs.getString("money"));
			}
			double cgHl = currManager.getCurrrate(result.getMoney());
			Subscribe subscribe = this.getSubscribe(result.getSub());
			double xsHl = currManager.getCurrrate(subscribe.getMoney());
			ArrayList<CgPro> cgPros = getCgPro(id);
			ArrayList<DdPro> ddPros = subscribe.getPros();
			for(int i=0;i<cgPros.size();i++){
				CgPro cgPro = cgPros.get(i);
				
				// 如果产品的采购价格为零，或者不是根据销售订单来采购的话，把采购产品的利润定为100%
				if(cgPro.getSelljg()==0 || ddPros.size()==0){
					cgPro.setProfit(1.0);
					continue;
				}
				
				for(int j=0;j<ddPros.size();j++){
					DdPro ddpro = ddPros.get(j);
					if(ddpro.getEpro().equals(cgPro.getEpro())){
						if(cgHl==0){
							cgHl = 1.0;
						}
						if(xsHl==0)
							xsHl = 1.0;
						System.out.println("ddpro "+ddpro.getEpro()+" selljg:"+ddpro.getSelljg()+";cgPro:"+cgPro.getSelljg()+";cgHl:"+cgHl+";xsHl:"+xsHl+";");
						System.out.println("profit:"+ddpro.getSelljg()*xsHl);
						System.out.println(cgPro.getSelljg()*xsHl-1);
						cgPro.setProfit((ddpro.getSelljg()*xsHl)/(cgPro.getSelljg()*cgHl)-1);
						break;
					}
				}
			}
			result.setPros(cgPros);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 通过采购订单ID查询采购订单信息
	 * @param id
	 * @return
	 */
	public Procure getProcure(int id){
		
		Procure result = new Procure();
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,number,sub,l_spyj,money,coname,jydd,man from procure where id = "+id;
		ResultSet rs = null;
		
		CurrentManager currManager = new CurrentManager();
		currManager.init();
		
		try{
			rs = einfodb.executeQuery(sql);
			if(rs.next()){
				result.setId(rs.getInt("id"));
				result.setNumber(rs.getString("number"));
				result.setSub(rs.getString("sub"));
				result.setL_spyj(rs.getString("l_spyj"));
				result.setMoney(rs.getString("money"));
				result.setCo_name(rs.getString("coname"));
				result.setJydd(rs.getString("jydd"));
				result.setMan(rs.getString("man"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 通过采购订单号查询采购订单
	 * @param id
	 * @return
	 */
	public ArrayList<CgPro> getCgPro(String ddid){
		
		ArrayList<CgPro> result = new ArrayList<CgPro>();
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,selljg,epro,cpro,num from cgpro where ddid = "+ddid;
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				CgPro pro = new CgPro();
				pro.setId(rs.getInt("id"));
				pro.setSelljg(rs.getDouble("selljg"));
				pro.setEpro(rs.getString("epro"));
				pro.setCpro(rs.getString("cpro"));
				pro.setNum(rs.getInt("num"));
				result.add(pro);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 通过采购订单号查询采购订单
	 * @param id
	 * @return
	 */
	private ArrayList<DdPro> getDdPro(String sub){
		
		ArrayList<DdPro> result = new ArrayList<DdPro>();
		infocrmdb einfodb = new infocrmdb();
		String sql = "select id,salejg,epro,cpro,hl from ddpro where ddid in (select id from subscribe where number='"+sub+"')";
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				DdPro pro = new DdPro();
				pro.setId(rs.getInt("id"));
				pro.setSelljg(rs.getDouble("salejg"));
				pro.setHl(rs.getDouble("hl"));
				pro.setEpro(rs.getString("epro"));
				pro.setCpro(rs.getString("cpro"));
				result.add(pro);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 通过采购订单号查询采购订单
	 * @param id
	 * @return
	 */
	public String getSaleMan(String sub){
		
		String result = null;
		infocrmdb einfodb = new infocrmdb();
		String sql = "select man from subscribe where number='"+sub+"'";
		ResultSet rs = null;
		
		try{
			rs = einfodb.executeQuery(sql);
			while(rs.next()){
				result = rs.getString("man");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try{
					rs.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			einfodb.close();
		}
		return result;
	}
	
	/**
	 * 删除退货单
	 * @param id
	 */
	public void deleteTh(String id){
		String sql="delete from th_pro_supplier where id='" + id + "'";
		infocrmdb einfodb = new infocrmdb();
		einfodb.executeUpdate(sql);
		einfodb.close();
	}

}
