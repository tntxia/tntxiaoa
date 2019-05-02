(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('assay_in_warehouse',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/assay_in_warehouse.html';
exports.init = function(){
	
	$("#toolbar").buildform({
		actions:{
			search:function(){
				
				var target = $("#datagrid");
				
				let grid = new BootstrapGrid({
					url:webRoot+"/assay/assay!statistInWarehouse.do",
					target:target,
					cols:[{
						label:'入库单号',
						field:'number'
					},{
						label:'负责人',
						field:'man'
					},{
						label:'入库时间',
						field:'int_date'
					},{
						label:'产品型号',
						field:'pro_model'
					},{
						label:'品牌 ',
						field:'pro_supplier'
					},{
						label:'入库数量',
						field:'pro_num'
					}]
				});
				grid.init();
			}
		}
	});
	
};
return module.exports;});