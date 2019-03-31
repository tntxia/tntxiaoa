(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_plan_month_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_plan_month_add.html';
exports.init = function(){
	
	var username = $("#username").val();
	$("#plan_man").text(username);
	var currentDate = $("#current").val();
	var year = parseInt(currentDate.split("-")[0]);
	var month = parseInt(currentDate.split("-")[1]);
	
	let grid;
	let targetGrid;
	
	$("[name=year]").append("<option>"+(year-1)+"</option>");
	$("[name=year]").append("<option selected='selected'>"+year+"</option>");
	$("[name=year]").append("<option>"+(year+1)+"</option>");
	
	for(var i=1;i<=12;i++){
		var option = $("<option>",{
			text:i
		});
		if(i==month){
			option.prop("selected",true);
		}
		$("[name=month]").append(option);
	}
	$("#plan_date").text(currentDate);
	
	var dateSelect = $("<select>");
	for(let i=0;i<31;i++){
		let option = $("<option>",{
			text:i
		});
		dateSelect.append(option);
	}
	
	$("#form").buildform({
		actions:{
			addPlan(){
				grid.addRow();
			},
			addTarget(){
				targetGrid.addRow();
			},
			add:function(){
				
				var params = this.getParamMap();
				params.planList = grid.getRows();
				let targetList = targetGrid.getRows();
				
				params.targetList = targetList;
				
				$.ajax({
					url:webRoot+'/sale/report!addWorkPlanMonth.do',
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
			back:function(){
				router.goRoute("sale_work_plan_month");
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
	grid.addRow();
	
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
	targetGrid.addRow();
	
	
};
return module.exports;});