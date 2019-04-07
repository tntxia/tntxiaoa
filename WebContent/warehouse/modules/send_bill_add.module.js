(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('send_bill_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sendBill/add.html';
exports.init = function(){
	

	let vm = new Vue({
		data:{
			company_id:null,
			sale_number:null,
			companyList:[],
			company:{
				
			}
		},
		mounted(){
			this.fetchData();
		},
		methods:{
			changeCompany(){
				
				let companyList = this.companyList;
				for(let i=0;i<companyList.length;i++){
					let company = companyList[i];
					if(company.id==this.company_id){
						this.company = company;
						break;
					}
				}
			},
			getUrl(picId){
				if(picId)
					return "/file_center/file!showPic.do?uuid="+picId
			},
			fetchData(){
				let vm = this;
				$.ajax({
					url:webRoot+'/company!listAll.do'
				}).done(function(data){
					vm.companyList = data;
					
				}).fail(function(){
					
				})
			},
			chooseSale(){
				let vm = this;
				OACommonUse.openSaleChooseDialog(function(data){
					vm.sale_number = data.number;
					vm.saleId = data.id;
				},{
					url:webRoot + '/sale/sale!approvedList.do'
				});
			},
			add(){
				let vm = this;
				let company = vm.company_id;
				if(!company){
					alert("请选择送货单的公司");
					return;
				}
				let saleId = vm.saleId;
				if(!saleId){
					alert("请选择销售订单");
					return;
				}
				$.ajax({
					url:'send!createSendBill.do',
					type:'post',
					data:{
						company,
						saleId
					}
				}).done(function(res){
					if(res.success){
						alert("操作成功");
						router.goRoute("send_bill_list");
					}else{
						alert("操作失败："+res.msg);
					}
				})
			}
		
		}
	});
	vm.$mount($("#vue").get(0));

	
};
return module.exports;});