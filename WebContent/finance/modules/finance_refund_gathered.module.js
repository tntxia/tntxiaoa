(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_refund_gathered',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/refund_gathered.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot + '/finance/finance!getGatheringRefundList.do?gathered=true',
		target:target,
		cols : [ {
			label:'序号',
			type:'index'
		},{
			field : 'orderform',
			label : '退货编号',
			renderer:function(value,data){
				let a = $("<a>",{
					text:value,
					target:'_blank',
					href:webRoot+'/finance/finance.dispatch?id='+data.id+'&method=toGatheringRefundView'
				});
				return a;
			}
		},{
			field : 'subNumber',
			label : '销售合同编号'
		},{
			field : 'coname',
			label : '客户名称'
		},{
			field : 'totalAmount',
			label : '退货金额'
		},{
			field : 'yjskdate',
			label : '退货日期'
		},{
			field : 'states',
			label : '当前状态'
		}]
	});
	grid.init();

};
return module.exports;});