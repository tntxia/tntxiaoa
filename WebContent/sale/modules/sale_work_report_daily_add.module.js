(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_work_report_daily_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_work_report_daily_add.html';
exports.init = function(){
	
	var username = $("#username").val();
	
	var dept = $("#dept").val();
	
	var form = $("#form").buildform({
		actions:{
			add:function(){
				var params = this.getParamMap();
				$.ajax({
					url:webRoot+'/sale/report!addWorkReportDaily.do',
					type:'post',
					data:params
				}).done(function(data){
					if(data.success){
						alert("操作成功");
						router.goRoute("sale_work_report_daily");
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