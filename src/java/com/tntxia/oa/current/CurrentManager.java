package com.tntxia.oa.current;

import java.sql.ResultSet;
import java.util.ArrayList;

import infocrmdb.infocrmdb;

public class CurrentManager {
	
	public ArrayList currs = null;
	
	public void init(){
		
		if(currs==null){
			currs = new ArrayList();
			String sql = "select * from hltable";
			infocrmdb db = new infocrmdb();
			ResultSet rs = db.executeQuery(sql);
			
			try{
				while(rs.next()){
					Current curr = new Current();
					String currname = rs.getString("currname");
					curr.setCurrname(currname);
					double currrate = rs.getDouble("currrate");
					curr.setCurrrate(currrate);
					currs.add(curr);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close();
		}
	}
	
	public double getCurrrate(String currname){
		if(currs==null){
			init();
		}
		if(currname==null)
			currname = "";
		currname = currname.trim();
		if(currs!=null){
			for(int i=0;i<currs.size();i++){
				Current curr = (Current)currs.get(i);
				if(curr.getCurrname().equals(currname)){
					return curr.getCurrrate();
				}
			}
		}
		return 1.0;
	}
	
	

}
