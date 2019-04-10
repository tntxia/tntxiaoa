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
	
	var target = $(".main_sec");
	
	var tabs = new BootstrapTabs({
		target:target
	});
	
	tabs.addTab({
		label:'待入库采购订单',
		template:'template_wait.mvc',
		handler:function(){
			
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
						let href = "purchasingDetail.mvc?id="+data.id;
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
			
			$("#rkList").datagrid({
				title:'待入库列表 ',
				pageSize:20,
				form:{
					inputs:[{
						label:"采购编号：",
						name:"cgnumber"
					},{
						label:"供 应 商：",
						name:"coname"
					},{
						label:"型号：",
						name:"epro"
					},{
						label:'品牌',
						name:'supplier'
					}],
					operations:[{
						text:'查询',
						click:function(){
							var model = $("[name=epro]").val();
							var coname = $("[name=coname]").val();
							var number = $("[name=cgnumber]").val();
							var supplier = $("[name=supplier]").val();
							$("#rkList").datagrid('reload',{
								model:escape(model),
								coname:escape(coname),
								supplier:escape(supplier),
								number:number
							});
						}
					}]
				},
				url:webRoot+'/purchasing/purchasing!listWaitRk.do',
				cols:[{
					label:'采购编号',
					field:'number',
					renderer:function(data,i){
						
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
		}
	});
	
	tabs.addTab({
		label:'入库单管理',
		template:'template_list.mvc',
		handler:function(){
			
			var form = $("#searchTable").buildform({
				actions:{
					search:function(){
						var param = this.getParamMap(true);
						$("#inListDatagrid").datagrid('load',param);
					}
				}
			});
			
			$("[name=startdate]").datepick();
			$("[name=enddate]").datepick({
				showNowDate:true
			});
			
			$("#inListDatagrid").datagrid({
				title:'入库单列表',
				url:webRoot+"/warehouse/warehouse!listIn.do",
				pageSize:20,
				cols:[{
					label:'入库编号',
					field:'number',
					renderer:function(data){
						var a = $("<a>",{
							text:data.number,
							click:function(){
								window.open(webRoot+"/warehouse/in/view.mvc?id="+data.id);
							}
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
					label:'登记者',
					field:'man'
				},{
					label:'入库日期',
					field:'int_date'
				},{
					label:'当前状态',
					field:'states'
				}]
			});
		}
	});
	
	tabs.addTab({
		label:'待入库供应商退货订单',
		template:'template_wait_refund.mvc',
		handler:function(){
			$("#refundList").datagrid({
				title:'供应商退货列表: ',
				url:webRoot+'/warehouse/warehouse!listRefund.do',
				cols:[{
					label:'退货编号',
					field:'number',
					renderer:function(data,i){
						var purchasingDetailPath = webRoot+"/warehouse/in/supplier-refund-view.mvc?id="+data.id;
						var a = $("<a>",{
							href:purchasingDetailPath,
							target:'_blank',
							text:data.number
						})
						return a;
					}
				},{
					label:'供应商名称',
					field:'coname'
				},{
					label:'申请人',
					field:'man'
				},{
					label:'当前状态',
					field:'state'
				}]
			});
		}
	});
	
	tabs.addTab({
		label:'调货待入库',
		template:'template_wait_dh.mvc',
		handler:function(){
			$("#dhList").datagrid({
				title:'调货待入库: ',
				url:warehousePath,
				data:{
					method:'listSampleDh'
				},
				cols:[{
					label:'调货编号',
					field:'number',
					renderer:function(data,i){
						var purchasingDetailPath = webRoot+"/sale/cypgl/ryp-view.jsp?id="+data.id;
						return "<a href='"+purchasingDetailPath+"' target='_blank'>"+data.number+"</a>";
					}
				},{
					label:'出库仓库',
					field:'sub'
				},{
					label:'入库仓库',
					field:'money'
				},{
					label:'申请人',
					field:'man'
				},{
					label:'日期',
					field:'datetime'
				}]
			});
		}
	});
	
	tabs.addTab({
		label:'样品待入库',
		template:'template_wait_sample.mvc',
		handler:function(){
			$("#sampleList").datagrid({
				title:'样品待入库: ',
				url:webRoot+'/warehouse/warehouse!listSample.do',
				cols:[{
					label:'样品编号',
					field:'number',
					renderer:function(data,i){
						var purchasingDetailPath = webRoot+"/sale/ypgl/ryp-view.jsp?id="+data.id;
						return "<a href='"+purchasingDetailPath+"' target='_blank'>"+data.number+"</a>";
					}
				},{
					label:'客户名称',
					field:'coname'
				},{
					label:'运输方式',
					field:'delivery_terms'
				},{
					label:'申请人',
					field:'man'
				},{
					label:'登记日期',
					field:'datetime'
				}]
			});
		}
	});
	
	tabs.init();
	
};
return module.exports;});