(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('client_shift',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/client/shift.html';
exports.init = function(){
	
	$("#submitBut").click(function(){
		var param = $(".main_sec").getParamMap();
		$.ajax({
			url:basePath+"purchasing/supplier.dispatch",
			data:param,
			success:function(data){
				if(data.success){
					alert("操作成功");
				}else{
					alert("操作失败！"+data.msg);
				}
			},
			error:function(){
				alert("请求后台服务失败");
			}
		});
	});
	
};
return module.exports;});