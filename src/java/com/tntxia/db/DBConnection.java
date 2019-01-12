package com.tntxia.db;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class DBConnection {
	
	public static Logger logger = Logger.getLogger(DBConnection.class);
	
	private String sDBDriver = null;
	private String sConnStr = null;
	private String username = null;
	private String password = null;

	private Connection conn = null;
	private Statement stmt = null;
	
	public DBConnection() {
		
		try {
			ResourceBundle rb = ResourceBundle.getBundle("jdbc");
			if(rb.containsKey("filePath")){
				String filePath = rb.getString("filePath");
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				Properties p = new Properties();
				p.load(in);
				sConnStr = p.getProperty("url");
				username = p.getProperty("username");
				password = p.getProperty("password");
				sDBDriver = p.getProperty("driver");
			}else{
				sConnStr = rb.getString("url");
				username = rb.getString("username");
				password = rb.getString("password");
				sDBDriver = rb.getString("driver");
				
			}
			Class.forName(sDBDriver);
		} catch (java.lang.ClassNotFoundException e) {
			logger.error(" 数据库初始化错误", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean getConnection() {

		try {
			if(conn!=null && !conn.isClosed()){
				return true;
			}
			conn = DriverManager.getConnection(sConnStr, username, password);
			stmt = conn.createStatement();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(" 获取数据库错误", ex);
			return false;
		}
	}

	public Connection getConnectionObject() {
		if (conn != null)
			return conn;
		else {
			if (getConnection()) {
				return conn;
			} else {
				return null;
			}
		}
	}

	public ResultSet executeQuery(String sql) {
		
		if (getConnection() == false)
			return null;
		ResultSet rs = null;
		try {
			Statement stmt = this.conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(" 执行查询错误", ex);
		}
		return rs;
	}
	
	public ResultSet executeQuery(String sql,List<Object> params) {
		
		if (getConnection() == false)
			return null;
		ResultSet rs = null;
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			for(int i=0;i<params.size();i++){
				ps.setObject(i+1, params.get(i));
			}
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(" 执行查询错误", ex);
		}
		return rs;
	}
	
	public ResultSet executeQuery(String sql,Object[] params) throws Exception{
		
		if (getConnection() == false)
			return null;
		ResultSet rs = null;
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(" 执行查询错误", ex);
		}
		return rs;
	}
	
	public ResultSet executeQuery(String sql,Object param){
		
		if (getConnection() == false)
			return null;
		ResultSet rs = null;
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setObject(1, param);
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error(" 执行查询错误", ex);
		}
		return rs;
	}
	
	public boolean exist(String sql) throws Exception{
		return this.exist(sql, null);
	}
	
	public boolean exist(String sql,Object[] params) throws Exception{
		ResultSet rs = this.executeQuery(sql, params);
		boolean res = false;
		if(rs.next()){
			res = true;
		}
		rs.close();
		return res;
	}

	public boolean executeUpdate(String sql) {
		
		if (conn == null) {
			if (getConnection() == false)
				return false;
		}
		
		Statement stat = null;
		boolean returnValue = true;
		try {
			if (conn.isClosed()) {
				close();
				getConnection();
			}
			stat = conn.createStatement();
			stat.executeUpdate(sql);
		} catch (SQLException ex) {
			logger.error(" 执行查询错误", ex);
			
			returnValue = false;
		}finally{
			closeStat(stat);
		}
		return returnValue;
	}
	
	public boolean executeUpdate(String sql,Object[] params) {
		List<Object> paramsList = new ArrayList<Object>();
		for(Object p : params){
			paramsList.add(p);
		}
		return executeUpdate(sql,paramsList);
		
	}
	
	public boolean executeUpdate(String sql,List<Object> params) {
		
		if (conn == null) {
			if (getConnection() == false)
				return false;
		}

		boolean returnValue = true;
		PreparedStatement ps = null;
		try {
			if (conn.isClosed()) {
				close();
				getConnection();
			}
			ps = conn.prepareStatement(sql);
			for(int i=0;i<params.size();i++){
				Object o = params.get(i);
				ps.setObject(i+1, o);
			}
			ps.executeUpdate();
			
		} catch (SQLException ex) {
			logger.error(" 执行更新错误", ex);
			returnValue = false;
		}finally{
			closePs(ps);
		}
		return returnValue;
	}
	
	public boolean executeUpdate(String sql,Integer param) {
		
		List<Object> params = new ArrayList<Object>();
		params.add(param);
		return executeUpdate(sql,params);
	}
	
	public boolean executeUpdate(String sql,String param) {
		
		List<Object> params = new ArrayList<Object>();
		params.add(param);
		return executeUpdate(sql,params);
	}
	
	public boolean executeUpdate(String sql,Double param) {
		
		List<Object> params = new ArrayList<Object>();
		params.add(param);
		return executeUpdate(sql,params);
	}
	

	
	
	public String execBatch(List<String> sqlList){
		
		boolean flag = getConnection();
		
		if(!flag){
			return "cannot get connection from db";
		}
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			for(String sql:sqlList){
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();	
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			closePs(ps);
			closeConn();
		}
		return "ok";
		
	}
	
	public int getIdenty(String tableName){
		String sql = "SELECT IDENT_CURRENT('"+tableName+"')";
		ResultSet rs = executeQuery(sql);
		int res = 0;
		try {
			if(rs.next()){
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(" getIdenty()错误", e);
		}finally{
			closeRs(rs);
		}
		return res;
	}
	
	public int getCount(String sql){
		ResultSet rs = executeQuery(sql);
		int res = 0;
		try {
			if(rs.next()){
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(" getCount()错误", e);
		}finally{
			closeRs(rs);
		}
		return res;
	}
	
	
	public void closeRs(ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("结果集关闭错误", e);
			}
		}
	}
	
	public void closeStat(Statement stat){
		if(stat!=null){
			try {
				stat.close();
			} catch (SQLException e) {
				logger.error(" Statement关闭错误", e);
			}
		}
	}
	
	public void closeStmt() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			logger.error(" Statement关闭错误", e);
		}
	}
	
	public void closePs(PreparedStatement ps){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error(" PreparedStatement关闭错误", e);
			}
		}
	}

	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			logger.error("数据关闭错误", e);
		}
	}
	
	public void close() {
		closeStmt();
		closeConn();
	}

	public static void main(String args[]) {
		
		DBConnection db = new DBConnection();
		db.executeQuery("select noname from");

	}
}
