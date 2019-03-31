(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('client_contact',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/client/contact.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/client/client!listContact.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'联系人',
			field:'name',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					click:function(){
						window.open(webRoot+"/xclient/c-main-1v.jsp?id="+data.nameid);
					}
				});
				return a;
			}
		},{
			label:'电话',
			field:'cotel',
			renderer:function(value,data){
				var a = $("<a>",{
					text:data.cotel,
					click:function(){
						window.open("c-main-1v.jsp?id="+data.id);
					}
				});
				return a;
			}
		},{
			label:'公司名称',
			field:'coname',
			renderer:function(value,data){
				var a = $("<a>",{
					text:data.coname,
					click:function(){
						window.open("c-main-1v.jsp?id="+data.id);
					}
				});
				return a;
			}
		},{
			label:'电子邮件',
			field:'email'
		}]
	});
	grid.init();
	
};
return module.exports;});