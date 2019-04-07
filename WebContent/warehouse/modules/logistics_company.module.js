(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('logistics_company',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	$("#addBtn").click(function(){
		window.open(webRoot+'/dzzz/ysgl/hdgst.mvc');
	});
	
	var grid = new BootstrapGrid({
		target:$("#datagrid"),
		url:webRoot+'/warehouse/warehouse!getHdCompanyList.do',
		cols:[{
			label:'公司名称',
			field:'companyname',
			renderer:function(value,row){
				let a = $("<a>",{
					text:value,
					href:webRoot+'/dzzz/ysgl/hdview.mvc?id='+row.id,
					target:'_blank'
				});
				return a;
			}
		},{
			label:'公司电话',
			field:'companytel'
		},{
			label:'公司传真',
			field:'companyfax'
		},{
			label:'联系人',
			field:'linkman'
		},{
			label:'操作',
			renderer:function(value,row){
				let button = $("<button>",{
					text:'删除',
					click:function(){
						
						if (confirm("确实要删除吗？")) {
							let row = $(this).data("row");
							let id = row.id;
							$.ajax({
								url:webRoot+'/warehouse/warehouse!delLogisticsCompany.do',
								type:'post',
								data:{
									id
								}
							}).done(function(res){
								if(res.success){
									alert("操作成功");
									window.location.reload();
								}else{
									alert("操作失败："+res.msg);
								}
							})
						}
						
					}
				});
				button.data("row",row);
				return button;
			}
		}]
	});
	
	grid.init();
	
};
return module.exports;});