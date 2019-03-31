(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_report_weekly_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_report_weekly_add.html';
exports.init = function(){
	
	var username = $("#username").val();
	
	var dept = $("#dept").val();
	
	var form = $("#form").buildform({
		actions:{
			add:function(){
				var params = this.getParamMap();
				$.ajax({
					url:webRoot+'/sale/report!addWorkReportWeekly.do',
					type:'post',
					data:params
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_report_weekly");
					}else{
						alert("操作失败");
					}
				})
			}
		}
	});
	form.setValue("man",username);
	form.setValue("dept",dept);
	
};
return module.exports;});