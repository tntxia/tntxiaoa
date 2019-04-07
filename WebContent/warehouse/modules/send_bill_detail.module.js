(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('send_bill_detail',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sendBill/detail.html';
exports.init = function(){
	
	var target = $("#vue");
	
	var id = router.getParams().id;
	
	let vm = new Vue({
		data:{
			detail:{
				company:{},
				list:[]	
			}
			
		},
		mounted:function(){
			this.fetchData();
		},
		methods:{
			getUrl(uuid){
				if(uuid){
					return "/file_center/file!showPic.do?uuid="+uuid;
				}
			},
			back(){
				router.goRoute("send_bill_list");
			},
			fetchData(){
				let vm = this;
				$.ajax({
					url:'send!getSendBillDetail.do',
					data:{
						id:id
					}
				}).done(res=>{
					vm.detail = res;
				})
			},
			addPro(){
				let detail = vm.detail;
				OACommonUse.openSaleProChooseDialog({
					saleNumber:detail.sale_number,
					callback(data){
						data.pid = id;
						$.ajax({
							url:'send!addSendBillPro.do',
							type:'post',
							data:data
						}).done(res=>{
							if(res.success){
								alert("操作成功");
								window.location.reload();
							}else{
								alert("操作失败");
							}
						})
					}
				})
			},
			delPro(id){
				$.ajax({
					url:'send!delSendBillPro.do',
					type:'post',
					data:{
						id:id
					}
				}).done(res=>{
					if(res.success){
						alert("操作成功");
						window.location.reload();
					}else{
						alert("操作失败");
					}
				})
			},
			print(){
				window.open("send-bill-print.mvc?id="+id);
			},
			toEdit(){
				router.goRoute("send_bill_edit",{id:id});
			}
		}
	});
	
	vm.$mount(target.get(0));
	
	
};
return module.exports;});