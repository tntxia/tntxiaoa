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
	
	/**
	 * 初始化配置的JS
	 * @param context
	 * @throws IOException 
	 */
	private void initConfigJs(ServletContext context){
		String path = context.getRealPath("/js/config.js");
		File file = new File(path);
		if(file.exists()){
			return;
		}
		
		FileWriter fileWritter = null;
		try{
			fileWritter = new FileWriter(path,false);
			fileWritter.write("var local = window.location;\n\r");
			fileWritter.write("var contextPath = local.pathname.split(\"/\")[1];\n\r");
			String contextPath = context.getContextPath();
			
			fileWritter.write("var basePath = local.protocol+\"//\"+local.host+\""+contextPath+"/\";");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("写入js配置文件失败！", ex);
			
		}finally{
			if(fileWritter!=null){
				try {
					fileWritter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void contextInitialized(ServletContextEvent event) {

		ServletContext context = event.getServletContext();
		
		initConfigJs(context);
		
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

		DataSource dataSource = DatasourceStore.getDatasource("default");
		SQLExecutor dbManager = null;

		try {
			dbManager = SQLExecutor.getInstance(dataSource);
			System.out.println("==== 加载数据库初始化成功       =======");

			System.out.println("获取系统表信息 ......");
			SystemDao systemDao = new SystemDao();

			SystemInfo systemInfo = systemDao.getSystemInfo();
			if (systemInfo == null) {
				logger.error("获取系统信息失败！！！！！");
				return;
			}
			SystemConfig.put("companyName", systemInfo.getCompanyName());
			System.out.println("获取系统表信息 end 公司名称："
					+ systemInfo.getCompanyName());

			String dbType = PropertiesUtils.getProperty("oa.db.type");

			System.out.println("获取SQL语句 ......");
			String sqlmappingPath = context.getRealPath("/WEB-INF/sqlmapping/"
					+ dbType);
			File root = new File(sqlmappingPath);
			initSqlmapping(root);

			String sql = "select  id,number,cpro name from profl  order by cpro desc";
			List<Object> departmentList = null;
			try {
				departmentList = dbManager.queryForList(
						SystemCache.sqlmapping.get("getAllDepartment"),
						Department.class);
				SystemCache.warehouseTypeList = dbManager.queryForList(sql,
						WarehouseType.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			SystemCache.departmentList = departmentList;

			try {
				List userList = dbManager.queryForList(
						"select * from username", true);
				SystemCache.userList = userList;
				List saleUserList = new ArrayList();
				for (int i = 0; i < userList.size(); i++) {

					Map user = (Map) userList.get(i);
					Integer deptId = (Integer) user.get("department_id");
					// 忽略部门编号为空的用户
					if (deptId == null) {
						continue;
					}

					String deptName = SystemCache.getDepartmentName(deptId);
					if ("销售部".equals(deptName) || "销售二部".equals(deptName)) {
						saleUserList.add(user);
					}
				}
				SystemCache.saleUserList = saleUserList;

			} catch (Exception e) {
				System.out.println("初始化用户列表失败");
				e.printStackTrace();
			}

			initCurrent(dbManager);
			initSuppliers(dbManager);
			initUnitList(dbManager);

			SystemCache.status = 1;
			System.out.println("==== OA 系统 初始化结束       =======");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbManager != null) {
				dbManager.close();
			}
		}

	}

}
