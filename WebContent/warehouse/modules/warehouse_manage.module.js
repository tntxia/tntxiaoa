(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_manage',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let grid;
	var form = $("#toolbar").buildform({
		
		actions:{
			add:function(){
				
				BootstrapUtils.createDialog({
					id:'addProductModal',
					title:'增加仓库产品',
					template:webRoot+"/warehouse/template/add.mvc",
					onFinish:function(){
						var sel = this.find("[name=unit]");
						OACommonSelects.fillUnitSelect({
							sel:sel
						});
					},
					onConfirm:function(){
						
						var paramMap = this.getParamMap();
						$.ajax({
							url:webRoot+'/warehouse/warehouse!add.do',
							data:paramMap
						}).done(function(data){
							if(data.success){
								alert("操作成功");
								vm.fetchData();
								$("#addProductModal").modal('hide');
								
							}else{
								if(data.msg){
									alert(data.msg);
								}else{
									alert("操作失败");
								}
								
							}
						}).fail(function(e){
							alert("操作异常");
						});
					}
				});
				$("#addProductModal").modal('show');
			},
			search:function(){
				var params = this.getParamMap();
				grid.load(params);
			},
			exports:function(){
				$.ajax({
					url:webRoot+'/warehouse/warehouse.do',
					data:{
						method:'export'
					},
					type:'post',
					success:function(data){
						if(data.success){
							window.open("/ReportCenter/view.mvc?id="+data.uuid);
						}else{
							alert("操作失败："+data.msg);
						}
					},
					dataType:"json"
				}).fail(e=>{
					alert("操作异常");
				});
			}
		}
	});
	
	let target = $("#warehouseList");
	
	grid = new BootstrapGrid({
		url:webRoot+"/warehouse/warehouse!warehouseList.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'产品型号',
			field:'promodel',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					href:"productView.mvc?id="+data.id,
					target:'_blank'
				});
				return a;
			}
		},{
			label:'品牌',
			field:'pro_sup_number'
		},{
			label:'货期',
			field:'yqdate'
		},{
			label:'当前库存',
			field:'pro_num',
			renderer:function(val,data){
				var pro_min_num = data.pro_min_num;
				var pro_max_num = data.pro_max_num;
				var color;
				if(pro_min_num){
					if(val<pro_min_num){
						color = "green"; 
					}
				}
				if(pro_max_num){
					if(val>pro_max_num){
						color ="red";
					}
				}
				if(color){
					var span = $("<span>",{
						text:val
					});
					span.css("color",color);
					return span;
				}else{
					return val;
				}
			}
		},{
			label:'最小库存',
			field:'pro_min_num'
		},{
			label:'最大库存',
			field:'pro_max_num'
		},{
			label:'待出库数',
			field:'ttnum',
			renderer:function(val,data){
				
				var a = $("<a>",{
					href:'javascript:void(0)',
					text:val
				});
				a.data("data",data);
				a.click(function(){
					
					var data = $(this).data("data");
					BootstrapUtils.createDialog({
						id:'showOutingModal',
						title:'查看待出库信息',
						template:webRoot+'/warehouse/template/viewComming.html',
						init:function(){
							
							var target  = this.find(".datagrid");
							BootstrapUtils.createGrid({
								
								target:target,
								url:webRoot+"/warehouse/warehouse!viewToOut.do",
								paramMap:{
									model:data.pro_model
								},
								cols:[{
									label:'销售订单编号',
									field:'number',
									renderer:function(data){
										return "<a target='_blank' href='"+ webRoot+"/sale/ddgl/detailAudited.mvc?id="+data.id+"'>"+data.number+"</a>";
									}
								},{
									label:'供应商',
									field:'coname'
								},{
									label:'交付时间',
									field:'send_date'
								},{
									label:'待出库数量',
									field:'numLeft'
								}]
							})
							
						}
					});
					$("#showOutingModal").modal('show');
					
				});
				
				return a;
				
				
			}
		},{
			label:'在途数',
			field:'zint_num',
			renderer:function(val,data){
				
				var a = $("<a>",{
					href:'javascript:void(0)',
					text:val
				});
				a.data("data",data);
				a.click(function(){
					var data = $(this).data("data");
					BootstrapUtils.createDialog({
						id:'showCommingModal',
						title:'查看在途信息',
						template:webRoot+'/warehouse/template/viewComming.html',
						init:function(){
							
							var target  = this.find(".datagrid");
							
							BootstrapUtils.createGrid({
								
								target:target,
								url:webRoot+"/warehouse/warehouse!viewComing.do",
								paramMap:{
									model:escape(data.pro_model)
								},
								cols:[{
									label:'采购订单编号',
									field:'number',
									renderer:function(data){
										return "<a target='_blank' href='"+ webRoot+"/purchasing/detail/audited.mvc?id="+data.id+"'>"+data.number+"</a>";
									}
								},{
									label:'供应商',
									field:'coname'
								},{
									label:'交付时间',
									field:'pay_if'
								},{
									label:'在途数量',
									field:'numLeft'
								}]
								
								
							})
							
						}
						
					});
					$("#showCommingModal").modal('show');
				});
				return a;
			}
			
		},{
			label:'产品状态',
			field:'pro_secid'
		},{
			label:'备注',
			field:'pro_remark'
		}]
	});
	grid.init();
	
};
return module.exports;});