package com.tntxia.oa.util;

import java.math.BigDecimal;
import java.util.Map;

public class MapUtils {
	
	/**
	 * 把Map里面所有值为空的，都置为空的字符串
	 * @param map
	 */
	public static void escapeNull(Map<String,Object> map){
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(map.get(entry.getKey())==null){
				map.put(entry.getKey(), "");
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static int getInt(Map map,String key){
		Object i = map.get(key);
		if(i==null){
			return 0;
		}
		return (Integer)i;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getString(Map map,String key){
		
		Object obj = map.get(key);
		
		String res;
		
		if(obj instanceof String){
			res = (String)map.get(key);
		}else{
			res = String.valueOf(obj);
		}
		
		if(res!=null){
			res = res.trim();
		}
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public static BigDecimal getBigDecimal(Map map,String key){
		return (BigDecimal)map.get(key);
	}

}
