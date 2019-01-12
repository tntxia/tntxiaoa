package com.tntxia.oa.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.oa.current.Current;
import com.tntxia.oa.system.entity.Department;
import com.tntxia.oa.system.entity.Menu;
import com.tntxia.oa.warehouse.entity.WarehouseType;

@SuppressWarnings("rawtypes")
public class SystemCache {
	
	// 系统状态  0 表示未初始化 1表示初始化成功
	public static int status = 0;
	
	// 当前应用的ID
	public static Map<String,Object> app;
	
	public static Map<String,String> sqlmapping = new HashMap<String,String>();
	
	public static Map<String,List<Menu>> menuMap = null;
	
	public static Map<String,Object> oaProperties = new HashMap<String,Object>();
	
	public static List<Object> departmentList = null;
	
	public static List<Object> warehouseTypeList = null;
	
	public static List<Current> currentList = null;
	
	public static List<Object> userList = null;
	
	public static List<Object> saleUserList = null;
	
	public static List<String> suppliers = new ArrayList<String>();
	
	public static List<String> unitList = new ArrayList<String>();
	
	public static Map<String,List> leftbarMap = new HashMap<String,List>();
	
	public static String headerImg;
	
	public static Map<String,Object> defaultJSON;
	
	public static String getDepartmentName(int id){
		if(departmentList!=null){
			for(Object o : departmentList){
				Department dept = (Department) o;
				if(id==dept.getId()){
					return dept.getName();
				}
			}
		}
		return null;
	}
	
	public static String getDepartmentName(String id){
		if(id ==null){
			return null;
		}
		return getDepartmentName(Integer.parseInt(id));
	}
	
	public static Department getDepartmentById(int id){
		Department dept = null;
		if(departmentList!=null){
			for(Object o : departmentList){
				Department temp = (Department) o;
				if(id==temp.getId()){
					dept = temp;
					break;
				}
			}
		}
		return dept;
	}
	
	public static String getWarehouseTypeName(String id){
		if(warehouseTypeList!=null){
			for(Object o : warehouseTypeList){
				WarehouseType warehouse = (WarehouseType) o;
				if(Integer.parseInt(id)==warehouse.getId()){
					return warehouse.getName();
				}
			}
		}
		return null;
	}
	
	public static double getCurrentRate(String name){
		
		if(currentList!=null){
			for(Current curr : currentList){
				if(curr.getCurrname().equals(name)){
					return curr.getCurrrate();
				}
			}
		}
		
		return 1;
	}

}
