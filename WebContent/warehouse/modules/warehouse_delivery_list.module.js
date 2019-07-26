(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_delivery_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	new Vue({
		el: '#app',
		data: {
			form: {
				number: null,
				saleNumber: null
			},
			dataset: {
				url: webRoot + '/warehouse/send!transportList.do',
				method: 'post'
			}
		},
		methods: {
			toAdd() {
				let vm = this;
				let saleChooseDialog = dialogVue.getDialog("saleApprovedChooseDialog");
				saleChooseDialog.setCallbackFun(row=> {
					console.log("show me the sale,,,", row);
					let addDialog = dialogVue.getDialog("warehouseSendBillAddDialog");
					addDialog.setSale(row);
					addDialog.setCallbackFunc(()=> {
						vm.reload();
					})
					addDialog.show();
				});
				saleChooseDialog.show();
			},
			reload() {
				let datagrid = this.$refs["datagrid"];
				datagrid.query();
			},
			query() {
				
			},
			view(row) {
				let dialog = dialogVue.getDialog("warehouseSendBillViewDialog");
				dialog.show(row.id);
			},
			del(row) {
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
		}
	});
};
return module.exports;});