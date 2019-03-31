(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_report_daily',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_report_daily.html';
exports.init = function(){
	
	$("#toolbar").buildform({
		actions:{
			add:function(){
				router.goRoute("sale_work_report_daily_add");
			}
		}
	})
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/sale/report!listWorkReportDaily.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'报告编号',
			field:'number',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					href:'#sale_work_report_daily_detail?id='+data.id
				});
				return a;
			}
		},{
			label:'部门',
			field:'dept'
		},{
			label:'报告人',
			field:'man'
		},{
			label:'报告日期 ',
			field:'report_date'
		},{
			label:'创建日期',
			field:'create_time'
		}]
	});
	grid.init();
	
};
return module.exports;});