(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_report_monthly',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_report_monthly.html';
exports.init = function(){
	
	$("#toolbar").buildform({
		actions:{
			add:function(){
				router.goRoute("sale_work_report_monthly_add");
			}
		}
	})
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/sale/report!listWorkReportMonthly.do",
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
					href:'#sale_work_report_monthly_detail?id='+data.id
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
			renderer:function(val,row){
				return row.report_date_start+"至"+row.report_date_end
			}
		},{
			label:'本月销售额',
			field:'total'
		},{
			label:'创建日期',
			field:'create_time'
		}]
	});
	grid.init();
	
};
return module.exports;});