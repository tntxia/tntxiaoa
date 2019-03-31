(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_report_monthly_detail',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_report_monthly_detail.html';
exports.init = function(){
	
	var id = router.getParam('id');
	
	$("#form").buildform({
		url:webRoot+'/sale/report!workReportMonthlyDetail.do',
		params:{
			id:id
		},
		actions:{
			update:function(){
				var params = this.getParamMap();
				$.ajax({
					url:webRoot+'/sale/report!updateWorkReportMonthly.do',
					type:'post',
					data:params
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_report_monthly");
					}else{
						alert("操作失败");
					}
				})
			},
			del:function(){
				if(!confirm("删除后无法恢复，是否确认删除日报？")){
					return;
				}
				$.ajax({
					url:webRoot+'/sale/report!delWorkReportMonthly.do',
					type:'post',
					data:{
						id:id
					}
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_report_monthly");
					}else{
						alert("操作失败");
					}
				})
			},
			back:function(){
				router.goRoute("sale_work_report_monthly");
			}
		}
	});
	
};
return module.exports;});