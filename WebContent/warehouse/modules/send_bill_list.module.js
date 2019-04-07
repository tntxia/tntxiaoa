(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('send_bill_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sendBill/list.html';
exports.init = function(){
	
	let form = $("#form").buildform({
		actions:{
			"search":function(){
				
			},
			"toAdd":function(){
				router.goRoute("send_bill_add");
			}
		}
	});
	
	
	var grid = new BootstrapGrid({
		target:$("#datagrid"),
		url:'send!getSendBillList.do',
		cols:[{
			label:'编号',
			field:'number',
			renderer:function(value,row){
				let a = $("<a>",{
					text:value,
					href:'#send_bill_detail?id='+row.id
				});
				return a;
			}
		},{
			label:'创建人',
			field:'man'
		},{
			label:'创建时间',
			field:'create_time'
		},{
			label:'公司名',
			field:'companyName'
		},{
			label:'销售单号',
			field:'sale_number'
		},{
			label:'操作',
			renderer:function(value,row){
				let button = $("<button>",{
					text:'删除',
					click:function(){
						let row = $(this).data("row");
						let id = row.id;
						$.ajax({
							url:'send!delSendBill.do',
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
				});
				button.data("row",row);
				return button;
			}
		}]
	});
	
	grid.init();
};
return module.exports;});