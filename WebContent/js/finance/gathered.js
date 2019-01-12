
$(function() {
	
	var grid = new BootstrapGrid({
		title : '收款信息',
		target: $("#crud"),
		url : webRoot + "/finance/finance!listGathered.do",
		cols : [ {
			label:'序号',
			type:'index'
		},{
			label : '合同编号',
			field : 'orderform',
			renderer : function(value,data) {
				var a = $("<a>", {
					text : value,
					click : function() {
						
						var dataId = $(this).data("dataId");
						console.log("dataId",dataId);
						
						BootstrapUtils.createDialog({
							id:'gatherModal',
							width:800,
							template:'gather/gathering/gathering-view.ftl',
							init:function(){
								var target = this.find("#vue");
								var vm = new Vue({
									
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
										fetchData:function(){
											
											var vm = this;
											
											$.ajax({
												url:webRoot + "/finance/finance!getGatheringDataById.do",
												data:{
													id:dataId
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
											window.open("editTax.mvc?id="+dataId);
										},
										addCredit:function(){
											window.open("addCredit.mvc?id="+dataId);
											
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
													id:dataId,
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
								vm.$mount(target.get(0));
								
							}
						});
						$("#gatherModal").modal('show');
						
						
					}
				});
				a.data("dataId",data.id);
				return a;
			}
		}, {
			label : '票据号',
			field : 'remark'
		}, {
			label : '是否开发票',
			field : 'bankaccounts'
		}, {
			label : '客户名称',
			field : 'coname'
		}, {
			label : '合同金额',
			field : 'total'
		}, {
			label : '出货金额',
			field : 'stotal'
		}, {
			label : '欠款金额',
			field : 'left'
		}, {
			label : '财务审批',
			field : 'note'
		}, {
			label : '预计收款日期',
			field : 'sjskdate'
		}, {
			label : '部门',
			field : 'sale_dept'
		}, {
			label : '当前状态',
			field : 'states'
		} ]
	});
	
	grid.init();
	
	$("#searchBtn").click(function(){
		var params = $("#searchForm").getParamMap();
		grid.load(params)
	});

});

