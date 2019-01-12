package com.tntxia.oa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	public static Properties getOAProperties(){
		Properties prop = new Properties();
		InputStream in = PropertiesUtils.class.getResourceAsStream("/oa.properties");
		try {
			if(in!=null){
				prop.load(in);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	
	public static String getProperty(String key){
		Properties prop = getOAProperties();
		return prop.getProperty(key);
	}

}
