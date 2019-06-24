(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_gathering_view',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/finance_gathering_view.html';
exports.init = function(){
	
	let id = router.getParam("id");
	
	var vm = new Vue({
		el: '#vue',
		data:{
			showNoteFlag:false,
			noteValue:'',
			orderform:null,
			custno:null,
			total:null,
			ymoney:null,
			smoney:null,
			bank:null,
			coname:null,
			co_number:null,
			yjskdate:null,
			sjdate:null,
			sjskdate:null,
			mode:null,
			rate:null,
			i_man:null,
			sendcompany:null,
			remark:null,
			saleman:null,
			sale_dept:null,
			priceTotal:{},
			note:null,
			yjskdate:null,
			sjdate:null,
			sjskdate:null,
			rate:null,
			i_man:null,
			mode:null,
			bankaccounts:null,
			remark:null,
			proList:[]
		},
		
		created:function(){
			this.fetchData();
		},
		methods:{
			back() {
				router.goRoute("finance_gathering");
			},
			fetchData:function(){
				
				var vm = this;
				
				$.ajax({
					url:webRoot + "/finance/finance!getGatheringDataById.do",
					data:{
						id
					}
				}).done(function(data){
					vm.orderform = data.orderform;
					vm.custno = data.custno;
					vm.smoney = data.smoney;
					vm.total = data.total;
					vm.ymoney = data.ymoney;
					vm.bank = data.bank;
					vm.coname = data.coname;
					vm.co_number = data.co_number;
					vm.note = data.note;
					vm.yjskdate = data.yjskdate;
					vm.sjdate = data.sjdate;
					vm.sjskdate = data.sjskdate;
					vm.rate = data.rate;
					vm.i_man = data.i_man;
					vm.sendcompany = data.sendcompany;
					vm.mode = data.mode;
					vm.bankaccounts = data.bankaccounts;
					vm.remark = data.remark;
					vm.proList = data.proList;
					vm.priceTotal = data.priceTotal;
				})
				console.log("fetchData");
			},
			editInvoiceBtn:function(){
				window.open("editTax.mvc?id="+id);
			},
			finishGathering() {
				$.ajax({
					url:webRoot + "/finance/finance!markGatheringFinish.do",
					type:'post',
					data:{
						id:id
					}
				}).done(function(data){
					if(data.success){
						window.location.reload();
					}else{
						alert("操作失败");
					}
				}).fail(function(){
					alert("操作异常");
				})
				
			},
			del(){
				if(confirm("是否确认将这个收款信息删除")){
					$.ajax({
						url:webRoot + "/finance/finance!delGathering.do",
						type:'post',
						data:{
							id:id
						}
					}).done(function(data){
						if(data.success){
							window.location.reload();
						}else{
							alert("操作失败");
						}
					}).fail(function(){
						alert("操作异常");
					})
				}
				
			},
			addCredit:function(){
				window.open("addCredit.mvc?id="+id);
			},
			toNoteIt:function(){
				this.showNoteFlag = true;
			},
			cancelNote:function(){
				this.showNoteFlag = false;
			},
			noteIt:function(){
				var noteValue = this.noteValue;
				$.ajax({
					url:webRoot + "/finance/finance!comment.do",
					type:'post',
					data:{
						id:id,
						noteValue:noteValue
					}
				}).done(function(data){
					if(data.success){
						window.location.reload();
					}else{
						alert("操作失败");
					}
				}).fail(function(){
					alert("操作异常");
				})
			}
			
		}
		
	});
	
};
return module.exports;});