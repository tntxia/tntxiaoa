package com.tntxia.oa.purchasing.mapping;

import java.util.HashMap;
import java.util.Map;

public class StatusMapping {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	static {
		map.put("0", "����");
		map.put("2", "����ͨ��");
		map.put("3", "��ͬ��ȷ��");
		map.put("4", "�����");
		map.put("6", "�����");
		map.put("7", "ȫ�����");
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
