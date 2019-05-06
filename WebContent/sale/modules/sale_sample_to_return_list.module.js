(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_sample_to_return_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = webRoot+'/sale/template/sale_sample_to_return.html';
exports.init = function(){
	
	var grid = new BootstrapGrid({
		target:$("#crud"),
		url: webRoot+'/sale/sample!listToReturn.do',
		cols : [ {
			label : '#',
			type : 'index'
		}, {
			label : '样品编号',
			field : 'number',
			renderer: function(value, row) {
				let a = $("<a>", {
					target: '_blank',
					text: value,
					href: "./ypgl/detailView.mvc?id="+row.id
				});
				return a;
			}
		}, {
			label : '客户名称',
			field : 'coname'
		}, {
			label : '运输方式',
			field : 'delivery_terms'
		}, {
			label : '责任人',
			field : 'man'
		},{
			label : '状态',
			field : 'state'
		},{
			label : '登记日期',
			field : 'datetime'
		}]
	});
	grid.init();
	
};
return module.exports;});