
require.config({
	baseUrl:'../js',
	paths:{
		
		// 公用js
		jquery:'lib/jquery',
		underscore:'lib/underscore',
		backbone:'lib/backbone',
		
		// tntxiaui js
		'tntxiaui-valid':'lib/tntxiaui/valid',
		'tntxiaui-getParamMap':'lib/tntxiaui/getParamMap',
		'tntxiaui-reset':'lib/tntxiaui/reset',
		'tntxiaui-dropdown':'lib/tntxiaui/dropdown',
		'tntxiaui-panel':'lib/tntxiaui/panel',
		'tntxiaui-mainlayout':'lib/tntxiaui/mainLayout',
		'tntxiaui-combox':'lib/tntxiaui/combox',
		'tntxiaui-loadPage':'lib/tntxiaui/loadPage',
		'tntxiaui-dialog':'lib/tntxiaui/dialog',
		'tntxiaui-datepick':'lib/tntxiaui/datepick',
		'tntxiaui-datagrid':'lib/tntxiaui/datagrid',
		'tntxiaui-accordion':'lib/tntxiaui/accordion',
		'tntxiaui-crud':'lib/tntxiaui/crud',
		
		// oa 公用js
		'oalib-top':'lib/oa/top',
		'oalib-leftbar':'lib/oa/leftbar'
			
	},
	shim:{
		'backbone':{
			deps:['underscore'],
			exports:'Backbone'
		}
	},
	urlArgs: "bust=" + (new Date()).getTime()// 定义每次加载 js 带上参数， 防止 cache 
});


$(function(){
	
	getDepartments({
		target:"depts",
		label:"name",
		value:"id",
		changeCallback:getSaleUserList
	});
	getSaleUserList({
		target:"sendcompany",
		defaultValue:'',
		defaultLabel:'销售员'
		
	});
});

function pic1_onclick() {
	var nowStr = getCurrentDate();
    window.open(basePath+"common/waitcal.jsp?m=startdate&time="+nowStr,'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
	var nowStr = getCurrentDate();
    window.open("waitcal.jsp?m=enddate&time="+nowStr,'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}

function getCurrentDate(){
	var nowDate = new Date();
	return nowDate.getFullYear()+"-"+(nowDate.getMonth()+1)+"-"+nowDate.getDate();
}

/**
 * 
 * @param page
 */
function goPage(page){
	if(page)
		$("#page").val(page);
	else
		$("#page").val($("#pageInput").val());
	
	$("#searchForm").submit();
	
}

/**
 * 
 */
function search_ymain(){
	$("#method").val("listApprovedSearch");
	$("#searchForm").submit();
}