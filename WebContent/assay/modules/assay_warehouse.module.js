(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('assay_warehouse',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/assay_warehouse.html';
exports.init = function(){
	
	var target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/assay/assay!statistWarehouse.do",
		target:target,
		cols:[{
			label:'产品编号',
			field:'pro_number',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					href:webRoot+"/warehouse/productView.mvc?id="+data.id,
					target:'_blank'
				});
				return a;
			}
		},{
			label:'产品型号',
			field:'pro_model',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					href:webRoot+"/warehouse/productView.mvc?id="+data.id,
					target:'_blank'
				});
				return a;
			}
		},{
			label:'库存数量(当前)',
			field:'pro_num'
		},{
			label:'采购价',
			field:'pro_price'
		},{
			label:'库存金额 ',
			field:'total'
		}]
	});
	grid.init();
	
	$("#toolbar").buildform({
		actions:{
			search:function(){
				
				var params = this.getParamMap();
				
				grid.load(params);
				
				
			}
		}
	});
	
};
return module.exports;});