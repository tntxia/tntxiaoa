package com.tntxia.oa.test.xml;

import org.apache.log4j.Logger;

public class TestLog4j {
	
	private static Logger logger = Logger.getLogger(Test.class);
	
	public static void main(String[] args){
		logger.error("error");
		logger.debug("debug22");
		logger.info("info");
	}

}
