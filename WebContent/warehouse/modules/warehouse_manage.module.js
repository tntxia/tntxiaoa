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
	
	new Vue({
		el: '#app',
		data: {
			form: {
				model: null,
				
			},
			dataset: {
				url: webRoot+"/warehouse/warehouse!warehouseList.do",
				method: 'post'
			}
		},
		methods: {
			add() {
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
			query() {
				let datagrid = this.$refs["datagrid"];
				datagrid.setParams(this.form);
				datagrid.query();
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
			},
			getUrl(row){
				return webRoot
					+ "/sale/productView.mvc?id="+row.id;
			},
			getProNumClass(row) {
				let val = row.pro_num;
				var pro_min_num = row.pro_min_num;
				var pro_max_num = row.pro_max_num;
				var color;
				if(pro_min_num){
					if(val<pro_min_num){
						return 'not-enough-num';
					}
				}
				if(pro_max_num){
					if(val>pro_max_num){
						return 'too-much-num';
					}
				}
			},
			// 查看待出库
			viewToOut(row) {
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
								model:row.pro_model
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
				
			},
			viewComming(row) {
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
								model:escape(row.pro_model)
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
			}
		}
	});
	return;
};
return module.exports;});