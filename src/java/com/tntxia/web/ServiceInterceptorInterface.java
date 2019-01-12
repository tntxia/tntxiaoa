package com.tntxia.web;

import java.util.Map;

public interface ServiceInterceptorInterface {
	
	public void before(Map input);
	
	public void after(Map runtime);

}
