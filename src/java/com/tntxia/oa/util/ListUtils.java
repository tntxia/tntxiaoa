package com.tntxia.oa.util;

import java.util.List;
import java.util.Map;

public class ListUtils {
	
	@SuppressWarnings("rawtypes")
	public static Map getMap(List list,int i){
		return (Map) list.get(i);
	}

}
