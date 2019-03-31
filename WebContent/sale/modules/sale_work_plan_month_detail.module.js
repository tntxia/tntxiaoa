(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_plan_month_detail',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_plan_month_detail.html';
exports.init = function(){
	
	var id = router.getParam('id');
	
	let grid;
	let targetGrid;
	
	var dateSelect = $("<select>");
	for(let i=0;i<31;i++){
		let option = $("<option>",{
			text:i
		});
		dateSelect.append(option);
	}
	
	$("#form").buildform({
		url:webRoot+'/sale/report!workPlanMonthDetail.do',
		params:{
			id:id
		},
		actions:{
			update:function(){
				var params = this.getParamMap();
				params.planList = grid.getRows();
				let targetList = targetGrid.getRows();
				params.targetList = targetList;
				
				$.ajax({
					url:webRoot+'/sale/report!updateWorkPlanMonth.do',
					type:'post',
					dataType:'json',
					data:JSON.stringify(params)
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_plan_month");
					}else{
						alert("操作失败");
					}
				})
			},
			del:function(){
				if(!confirm("删除后无法恢复，是否确认删除月工作计划？")){
					return;
				}
				$.ajax({
					url:webRoot+'/sale/report!delWorkPlanMonth.do',
					type:'post',
					data:{
						id:id
					}
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_plan_month");
					}else{
						alert("操作失败");
					}
				})
			}
		},
		onFinish:function(data){
			
			console.log(data)
			
			let planItemList = data.planItemList;
			if(planItemList && planItemList.length){
				$.each(planItemList,(i,d)=>{
					debugger;
					grid.addRow(d);
				})
			}
			
			let targetItemList = data.targetItemList;
			if(targetItemList && targetItemList.length){
				$.each(targetItemList,(i,d)=>{
					targetGrid.addRow(d);
				})
			}
			
		}
	});
	
	let target = $("#main_plan_table");
	grid = new BootstrapGrid({
		target:target,
		
		cols:[{
			label:'日期',
			renderer:function(value,data){
				
				let div = $("<div>");
				
				let selectStart = dateSelect.clone();
				selectStart.attr("field", "date_start");
				div.append(selectStart);
				
				div.append(" 至 ");
				
				let selectEnd = dateSelect.clone();
				selectEnd.attr("field", "date_end");
				div.append(selectEnd);
				
				return div;
			}
		},{
			label:'工作重点',
			field: 'key_point',
			editable: true
		},{
			label:'备注',
			field: 'remark',
			editable: true
		}]
	});
	grid.init();
	
	target = $("#main_target_table");
	targetGrid = new BootstrapGrid({
		target:target,
		cols:[{
			label:'目标',
			field:'target',
			editable: true
		},{
			label:'完成期限',
			renderer(val,row){
				let div = $("<div>");
				let selectEnd = dateSelect.clone();
				selectEnd.attr("field", "date_before");
				div.append(selectEnd);
				div.append("日前");
				return div;
			}
		},{
			label:'结果确认',
			renderer(val,row){
				return "未完成";
			}
		}]
	});
	targetGrid.init();
	
};
return module.exports;});