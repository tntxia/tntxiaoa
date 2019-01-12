package com.tntxia.oa.test.xml;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Test {
	
	

	public static void main(String[] args) throws DocumentException{
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("WebContent/WEB-INF/config/leftbar/finance.xml");
        Element root = document.getRootElement();
        Node node = root.selectSingleNode("currentBarIndex");
        System.out.println(node.getText());
	}

}
