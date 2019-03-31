(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('complain_approved_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/complain/approved.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/appeal!listApproved.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'投诉编号',
			field:'numeration',
			renderer:function(data){
				var a = $("<a>",{
					text:data.numeration,
					click:function(){
						window.open("spkhts-view.jsp?id="+data.aid);
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