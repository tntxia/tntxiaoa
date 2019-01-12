package com.tntxia.oa.util;

import java.util.ArrayList;
import java.util.List;

public class Pager {
	
	private int pageNo;
	
	private int pageSize;
	
	private int totalPage;
	
	private int totalCount;
	
	public Pager(int pageNo, int pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@SuppressWarnings("rawtypes")
	public List paging(List list){
		List<Object> res = new ArrayList<Object>();
		for(int i=(pageNo-1)*pageSize;i<pageNo*pageSize&&i<list.size();i++){
			res.add(list.get(i));
		}
		this.setTotalCount(list.size());
		this.setTotalPage(list.size()/pageSize+(list.size()%pageSize==0?0:1));
		return res;
	}

}
