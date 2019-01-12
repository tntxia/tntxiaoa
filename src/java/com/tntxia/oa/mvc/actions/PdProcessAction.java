package com.tntxia.oa.mvc.actions;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tntxia.oa.warehouse.PdConsole;
import com.tntxia.oa.warehouse.PdRun;

public class PdProcessAction implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");

		String pro_addr = request.getParameter("pro_addr");
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(5);
		
		JSONObject json = new JSONObject();
		json.put("process", nf.format(PdConsole.process));
		response.setContentType("text/json");
		response.getWriter().write(json.toJSONString());
		
		return null;
	}

}
