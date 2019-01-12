package com.tntxia.velocity;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	
	private static boolean initialized = false;
	
	/**
	 * 
	 * Velocity初始化
	 * @param templatePath
	 * 
	 */
	public static void init(String templatePath){
		
		if(!initialized){
			Properties p = new Properties();
			p.setProperty(Velocity.ENCODING_DEFAULT, "GBK");
	        p.setProperty(Velocity.INPUT_ENCODING, "GBK");
	        p.setProperty(Velocity.OUTPUT_ENCODING, "GBK");
			p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templatePath);
			Velocity.init(p);
			initialized = true;
		}
		
	}

}
