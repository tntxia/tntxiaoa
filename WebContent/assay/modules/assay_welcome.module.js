(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('assay_welcome',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = webRoot+'/template/welcome.html';
exports.init = function(){
	
	$("#curr-module").text("决策分析");
	
	$.ajax({
		url:webRoot+"/logininfo.do"
	}).done(res=>{
		$("#online-num").text(res.loginList.length);
		$.each(res.loginList,function(i,d){
			$("#loginList").append(d+";")
		})
	})
	
};
return module.exports;});