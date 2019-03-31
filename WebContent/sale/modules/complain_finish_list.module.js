(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('complain_finish_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/complain/finish.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/appeal!listFinish.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'投诉编号',
			field:'numeration',
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
			label:'投诉分类',
			field:'appealfl'
		},{
			label:'星级',
			field:'appealdj'
		}]
	});
	grid.init();
	
};
return module.exports;});