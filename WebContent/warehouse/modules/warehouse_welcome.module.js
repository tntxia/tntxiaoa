(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_welcome',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = webRoot+'/template/welcome.html';
exports.init = function(){
	
	$("#curr-module").text("仓库管理模块");
	
};
return module.exports;});