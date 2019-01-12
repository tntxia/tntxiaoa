package com.tntxia.oa.config;

import java.util.HashMap;
import java.util.Map;

public class SystemConfig{
	
	
	private static Map<String, Object> map = new  HashMap<String, Object>();
	
	public static void put(String key,Object value){
		map.put(key, value);
	}

	public static String getString(String key){
		return (String)map.get(key);
	}

}
