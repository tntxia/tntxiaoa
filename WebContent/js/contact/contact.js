require.config({
	baseUrl:basePath+'js',
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
		'oalib-leftbar':'lib/oa/leftbar',
		'oalib-util':'lib/oa/util'
			
	},
	shim:{
		'backbone':{
			deps:['underscore'],
			exports:'Backbone'
		}
	},
	urlArgs: "bust=" + (new Date()).getTime()// 定义每次加载 js 带上参数， 防止 cache 
});

function viewTop50(){
	window.open('top50.jsp','_blank', 'height=450, width=930, top=0, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
	
}

require(['tntxiaui-datagrid','tntxiaui-getParamMap'],function(){
	$(function(){
		
		$("#searchBut").click(function(){
			var param = $(".main_sec").getParamMap(true);
			$("#datagrid").datagrid('load',param);
		});
		
		// 增加按钮
		$("#addBut").click(function(){
			window.open('c-main-1.jsp');
		});
		
		$("#datagrid").datagrid({
			url:basePath+"client/client.dispatch",
			data:{
				method:'listContact'
			},
			cols:[{
				label:'序号',
				type:'index'
			},{
				label:'联系人',
				field:'name',
				renderer:function(data){
					var a = $("<a>",{
						text:data.name,
						click:function(){
							window.open("c-main-1v.jsp?id="+data.nameid);
						}
					});
					return a;
				}
			},{
				label:'电话',
				field:'tel',
				renderer:function(data){
					var a = $("<a>",{
						text:data.cotel,
						click:function(){
							window.open("c-main-1v.jsp?id="+data.id);
						}
					});
					return a;
				}
			},{
				label:'公司名称',
				field:'coname',
				renderer:function(data){
					var a = $("<a>",{
						text:data.coname,
						click:function(){
							window.open("c-main-1v.jsp?id="+data.id);
						}
					});
					return a;
				}
			},{
				label:'电子邮件',
				field:'email'
			}]
		});
	});
});