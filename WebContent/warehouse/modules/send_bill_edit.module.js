(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('send_bill_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sendBill/edit.html';
exports.init = function(){
	
	var target = $("#vue");
	
	var id = router.getParams().id;
	
	let vm = new Vue({
		data:{
			companyList:[],
			detail:{
				id:id,
				company_id:null,
				company:{},
				list:[]	
			}
			
		},
		mounted:function(){
			this.fillCompanyList();
			this.fetchData();
		},
		methods:{
			getUrl(uuid){
				if(uuid){
					return "/file_center/file!showPic.do?uuid="+uuid;
				}
			},
			back(){
				router.goRoute("send_bill_detail",{id:id});
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
				});
			},
			fillCompanyList:function(){
				let vm = this;
				$.ajax({
					url:webRoot+'/company!listAll.do'
				}).done(res=>{
					vm.companyList = res;
				});
			},
			update(){
				let vm = this;
				$.ajax({
					url:'send!updateSendBill.do',
					data:vm.detail
				}).done(res=>{
					if(res.success){
						router.goRoute("send_bill_detail",{id:id});
					}
					vm.detail = res;
				})
			},
			changeCompany(){
			
				let companyList = this.companyList;
				for(let i=0;i<companyList.length;i++){
					let company = companyList[i];
					if(company.id==this.detail.company_id){
						this.detail.company = company;
						break;
					}
				}
			},
			chooseSale(){
				OACommonUse.openSaleChooseDialog(function(data){
					
					var saleId = data.id;
					
					$.ajax({
						url:'send!updateSendBillSale.do',
						data:{
							id:id,
							saleId,saleId
						}
					}).done(function(res){
						if(res.success){
							alert("操作成功");
							window.location.reload();
						}else{
							alert("操作失败："+res.msg);
						}
					})
					console.log(data);
				},{
					url:webRoot + '/sale/sale!approvedList.do'
				});
			}
		}
	});
	
	vm.$mount(target.get(0));
	
	
};
return module.exports;});