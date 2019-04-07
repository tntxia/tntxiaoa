(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_sale_outed',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	var grid = new BootstrapGrid({
		target:$("#datagrid"),
		url:'warehouse!listSaleOuted.do',
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'编号',
			field:'number',
			renderer : function(value,data) {
				var url = webRoot + "/warehouse/out/operate.mvc?id=" + data.id;
				
				var a = $("<a>",{
					text : value,
					href : url,
					target:'_blank'
				});
				return a;
			}
		},{
			label:'客户订单号',
			field:'custno'
		},{
			label:'客户名称',
			field:'coname'
		},{
			label:'发货日期',
			field:'sendDate'
		},{
			label:'申请人',
			field:'man'
		}]
	});
	
	grid.init();
	
	let form = $("#form").buildform({
		actions:{
			"search":function(){
				var params = this.getParamMap();
				grid.load(params);
			}
		}
	});
	
};
return module.exports;});