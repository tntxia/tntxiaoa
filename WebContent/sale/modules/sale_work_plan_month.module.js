(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_plan_month',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_plan_month.html';
exports.init = function(){
	
	$("#toolbar").buildform({
		actions:{
			add:function(){
				router.goRoute("sale_work_plan_month_add");
				// window.open(webRoot+'/swork/nhlxx.jsp');
			}
		}
	})
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/sale/report!listWorkPlanMonth.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'月计划编号',
			field:'number',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					href:'#sale_work_plan_month_detail?id='+data.id
				});
				return a;
			}
		},{
			label:'预计销售额(万元) ',
			field:'total'
		},{
			label:'完成度',
			field:'process',
			renderer:function(value){
				if(!value){
					value = 0;
				}
				return value + "%";
			}
		},{
			label:'计划员工 ',
			field:'man'
		},{
			label:'计划日期',
			renderer:function(val,row){
				return row.year+"年"+row.month+"月"
			}
		},{
			label:'创建日期',
			field:'create_time'
		}]
	});
	grid.init();
	
};
return module.exports;});