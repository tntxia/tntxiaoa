package com.tntxia.js;

public class Stringfy {
	
	public String legal(String str){
		return str.replaceAll("\\n", "%20").replaceAll("\\t", "%20").replaceAll("\\s", "%20");
	}

}
