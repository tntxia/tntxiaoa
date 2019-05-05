(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_out_manage',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	var target = $("#container");
	
	var tabs = new JxiaUI({
		el:target,
		methods: {
			loadWait() {
				listWaitCheckout();
			},
			loadWaitSample() {
				listWaitOutSample();
			},
			loadWaitRefund() {
				listWaitRefund();
			},
			loadOuted() {
				new Vue({
					el:'#outed',
					data:{
						coname:null,
						model:null,
						sub:null,
						number:null,
						totalAmount:0,
						page:1,
						totalPage:0,
						rows:[]
					},
					created:function(){
						this.fetchData();
					},
					methods:{
						fetchData:function(){
							
							var vm = this;
							
							var coname = vm.coname;
							var model = vm.model;
							var number = vm.number;
							
							$.ajax({
								url:webRoot+'/warehouse/warehouse!getOutList.do',
								type:'post',
								data:{
									coname:coname,
									model:model,
									number:number,
									page:vm.page,
									pageSize:50,
									totalAmount:0,
									totalPage:0
								},
								success:function(data){
									vm.page = data.page;
									vm.totalAmount = data.totalAmount;
									vm.rows = [];
									$.each(data.rows,function(i,r){
										vm.rows.push(r);
									});
									
									vm.totalPage = data.totalPage;
								}
							});
						},
						nextPage:function(){
							this.page++;
							this.fetchData();
						},
						prevPage:function(){
							this.page--;
							this.fetchData();
						},
						getUrl:function(ddid){
							return "out/operate.mvc?id="+ddid;
						}
					}
				});
			}
		}
	});
	
	function listWaitCheckout(){
		let target = $("#waitCheckoutList");
		let grid = new BootstrapGrid({
			url:webRoot+'/warehouse/warehouse!waitOutList.do',
			target:target,
			cols:[{
				label:'合同编号',
				field:'number',
				renderer:function(value,data){
					var a = $("<a>",{
						text:data.number,
						target:'_blank',
						href:webRoot+'/warehouse/out/operate.mvc?id='+data.id
					});
					return a;
				}
			},{
				label:'公司合同号',
				field:'sub'
			},{
				label:'客户名称',
				field:'coname'
			},{
				label:'申请人',
				field:'man'
			},{
				label:'发货日期',
				field:'send_date'
			}]
		});
		grid.init();
		
		$("#waitOutListSearchForm").buildform({
			actions: {
				search() {
					let paramMap = this.getParamMap();
					grid.load(paramMap);
				}
			}
		});
	}
	
	function listWaitRefund(){
		
		let target = $("#waitRefundList");
		
		let grid = new BootstrapGrid({
			url:webRoot+'/warehouse/warehouse!waitRefundList.do',
			target:target,
			cols:[{
				label:'退货编号',
				field:'number',
				renderer:function(value,data){
					var a = $("<a>",{
						text:data.number,
						target:'_blank',
						href:webRoot+'/server/thgl/th-view.jsp?t=111&id='+data.id
					});
					return a;
				}
			},{
				field:'coname',
				label:'客户名称'
			},{
				field:'man',
				label:'申请人'
			},{
				field:'state',
				label:'当前状态'
			}]
		});
		grid.init();
		
	}
	
	function listWaitOutSample(){
		
		let target = $("#waitOutSampleList");
		let grid = new BootstrapGrid({
			url:webRoot+'/warehouse/warehouse!waitOutSampleList.do',
			target:target,
			cols:[{
				label:'样品编号',
				field:'number',
				renderer:function(value,data){
					var a = $("<a>",{
						text:data.number,
						target:'_blank',
						href:webRoot+'/sale/ypgl/yp-view.jsp?id='+data.id
					});
					return a;
				}
			},{
				field:'coname',
				label:'客户名称'
			},{
				field:'delivery_terms',
				label:'运输方式'
			},{
				field:'man',
				label:'申请人'
			},{
				field:'datetime',
				label:'起运日期'
			}]
		});
		grid.init();
		
	}
	
};
return module.exports;});