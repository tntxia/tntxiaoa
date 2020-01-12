package com.tntxia.oa.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tntxia.jdbc.SQLExecutor;
import com.tntxia.oa.config.SystemConfig;
import com.tntxia.oa.current.Current;
import com.tntxia.oa.system.SystemCache;
import com.tntxia.oa.system.dao.SystemDao;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.system.entity.SystemInfo;
import com.tntxia.oa.util.PropertiesUtils;
import com.tntxia.oa.warehouse.entity.WarehouseType;
import com.tntxia.web.util.DatasourceStore;

@WebListener
public class StartInitListener implements ServletContextListener {

	private static final Logger logger = Logger
			.getLogger(StartInitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("OA系统停止...");
	}

	@SuppressWarnings("rawtypes")
	private void initCurrent(SQLExecutor dbManager) {

		String sql = "select * from hltable";
		try {
			List list = dbManager.queryForList(sql, true);
			List<Current> currentList = new ArrayList<Current>();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String name = (String) map.get("currname");
				double rate = ((BigDecimal) map.get("currrate")).doubleValue();
				Current current = new Current();
				current.setCurrname(name);
				current.setCurrrate(rate);
				currentList.add(current);
			}
			SystemCache.currentList = currentList;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化品牌列表
	 * 
	 * @param dbManager
	 */
	@SuppressWarnings("rawtypes")
	private void initSuppliers(SQLExecutor dbManager) {
		String sql = "select * from profll";
		try {
			List list = dbManager.queryForList(sql, true);
			List<String> suppliers = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String cpro = (String) map.get("cpro");

				suppliers.add(cpro);
			}
			SystemCache.suppliers = suppliers;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 初始化品牌列表
	 * 
	 * @param dbManager
	 */
	@SuppressWarnings("rawtypes")
	private void initUnitList(SQLExecutor executor) {
		String sql = "select * from punit_type";
		try {
			List list = executor.queryForList(sql, true);
			List<String> unitList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String name = (String) map.get("punit_name");

				unitList.add(name);
			}
			SystemCache.unitList = unitList;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("rawtypes")
	private void initSqlmapping(File root){
		
		if(root==null || root.listFiles() == null){
			System.out.println("没有sqlmapping文件夹");
			return;
		}
		
		for (File f : root.listFiles()) {
			SAXReader saxReader = new SAXReader();
			try {
				Document document = saxReader.read(f);
				List sqlList = document.selectNodes("/sqlmapping/sql");
				for (int i = 0; i < sqlList.size(); i++) {
					Element node = (Element) sqlList.get(i);
					String id = node.attributeValue("id");
					String sql = node.getText().replaceAll("\n", " ");
					SystemCache.sqlmapping.put(id, sql);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		System.out.println("获取SQL语句 end");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();
		
		System.out.println("==== OA 系统 初始化开始       =======");

		System.out.println("==== 加载数据库配置       =======");
		String dbPath = context.getRealPath("/WEB-INF/config/db.xml");

		try {
			DatasourceStore.init(dbPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {


			String dbType = PropertiesUtils.getProperty("oa.db.type");

			System.out.println("获取SQL语句 ......");
			String sqlmappingPath = context.getRealPath("/WEB-INF/sqlmapping/"
					+ dbType);
			File root = new File(sqlmappingPath);
			initSqlmapping(root);

			SystemCache.status = 1;
			System.out.println("==== OA 系统 初始化结束       =======");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

}
