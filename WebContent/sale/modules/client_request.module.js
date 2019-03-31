(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('client_request',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/client/request.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/client/client!listClientQuest.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'编号',
			field:'record',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					click:function(){
						window.open(webRoot+"/server/khts/ckhts-view.jsp?id="+data.aid);
					}
				});
				return a;
			}
		},{
			label:'客户名称',
			field:'coname',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					click:function(){
						window.open(webRoot+"/server/khts/ckhts-view.jsp?id="+data.aid);
					}
				});
				return a;
			}
		},{
			label:'优先级',
			field:'import'
		}]
	});
	grid.init();
	
};
return module.exports;});