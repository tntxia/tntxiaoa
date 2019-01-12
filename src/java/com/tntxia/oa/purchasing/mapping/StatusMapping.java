package com.tntxia.oa.purchasing.mapping;

import java.util.HashMap;
import java.util.Map;

public class StatusMapping {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("0", "草拟");
		map.put("2", "审批通过");
		map.put("3", "合同已确认");
		map.put("4", "待入库");
		map.put("6", "已入库");
		map.put("7", "全部入库");
	}
	
	public static String getLabel(String key){
		return map.get(key);
	}
	
	public static int getKey(String label){
		
		for(Map.Entry<String, String> entry:map.entrySet()){
			if(entry.getValue().equals(label)){
				return Integer.parseInt(entry.getKey());
			}
		}
		return -1;
	}

}
