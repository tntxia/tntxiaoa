(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_in_manage',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	var warehousePath = webRoot+'/warehouse/warehouse.dispatch';
	
	var target = $("#container");
	
	var tabs = new JxiaUI({
		el:target,
		methods: {
			loadWait() {
				
				let target = $("#rkList");
				let url = webRoot+'/purchasing/purchasing!listWaitRk.do';
				let grid;
				grid = new BootstrapGrid({
					url:url,
					target:target,
					cols:[{
						label:'采购编号',
						field:'number',
						renderer:function(value,data){
							let href = "in/purchasingDetail.mvc?id="+data.id;
							var a = $("<a>",{
								text:value,
								href:href,
								target:'_blank'
							});
							return a;
						}
					},{
						label:'供应商名称',
						field:'coname'
					},{
						label:'入库仓库',
						field:'subck'
					},{
						label:'责任人',
						field:'man'
					},{
						label:'货期',
						field:'datetime'
					}]
				});
				grid.init();
				
				$("#form").buildform({
					actions: {
						"search": function() {
							let params = this.getParamMap();
							grid.load(params);
						}
					}
				})
			},
			loadInManage() {
				let grid;
				let url = webRoot+"/warehouse/warehouse!listIn.do";
				let target = $("#inListDatagrid");
				grid = new BootstrapGrid({
					url:url,
					target:target,
					cols:[{
						label:'入库编号',
						field:'number',
						renderer:function(value,data){
							let href = webRoot+"/warehouse/in/view.mvc?id="+data.id;
							var a = $("<a>",{
								text:value,
								href:href,
								target:'_blank'
							});
							return a;
						}
					},{
						label:'供应商名称',
						field:'supplier'
					},{
						label:'入库类别',
						field:'int_types'
					},{
						label:'责任人',
						field:'man'
					},{
						label:'入库日期',
						field:'int_date'
					}]
				});
				grid.init();
				
				var form = $("#searchTable").buildform({
					actions:{
						search:function(){
							var param = this.getParamMap(true);
							grid.load(param);
						},
						add:function() {
							window.open('in/new.mvc','_blank');
						}
					}
				});
				
				$("[name=startdate]").datepick();
				$("[name=enddate]").datepick({
					showNowDate:true
				});
			},
			loadPurchaseRefund() {
				let url = webRoot+'/warehouse/warehouse!listRefund.do';
				let target = $("#refundList");
				grid = new BootstrapGrid({
					url:url,
					target:target,
					cols:[{
						label:'退货编号',
						field:'number',
						renderer:function(value,data){
							let href = webRoot+"/warehouse/in/supplier-refund-view.mvc?id="+data.id;
							var a = $("<a>",{
								text:value,
								href:href,
								target:'_blank'
							});
							return a;
						}
					},{
						label:'供应商名称',
						field:'coname'
					},{
						label:'责任人',
						field:'man'
					},{
						label:'当前状态',
						field:'state'
					}]
				});
				grid.init();
			},
			loadWaitSample() {
				let url = webRoot+'/warehouse/warehouse!listSample.do';
				let target = $("#sampleList");
				grid = new BootstrapGrid({
					url:url,
					target:target,
					cols:[{
						label:'样品编号',
						field:'number',
						renderer:function(value,data){
							let href = webRoot+"/sale/ypgl/ryp-view.jsp?id="+data.id;
							var a = $("<a>",{
								text:value,
								href:href,
								target:'_blank'
							});
							return a;
						}
					},{
						label:'客户名称',
						field:'coname'
					},{
						label:'运输方式',
						field:'delivery_terms'
					},{
						label:'责任人',
						field:'man'
					},{
						label:'登记日期',
						field:'datetime'
					}]
				});
				grid.init();
			}
		}
	});
	
};
return module.exports;});