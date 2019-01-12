package com.tntxia.db;

import java.util.ArrayList;
import java.util.List;

public class InsertSegmentCombine {
	
	private String table;
	
	private List<InsertSegment> list = new ArrayList<InsertSegment>();
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addSegment(String key,String value){
		InsertSegment segment = new InsertSegment();
		segment.setKey(key);
		segment.setValue(value);
		list.add(segment);
	}
	
	public String getInsertSql(){
		String sqlHeader = "insert into "+table+"(";
		String sqlValues = " values(";
		for(int i=0;i<list.size();i++){
			InsertSegment seg = list.get(i);
			if(i==0){
				sqlHeader += seg.getKey();
				sqlValues += "'"+seg.getValue()+"'";
			}else{
				sqlHeader += ","+seg.getKey();
				sqlValues += ",'"+seg.getValue()+"'";
			}
		}
		sqlHeader+=")";
		sqlValues+=")";
		return sqlHeader+sqlValues;
	}

}
