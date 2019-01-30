
$(function() {

	var purchasingPath = webRoot+"/purchasing/purchasing.do";
	
	var xclientPath = webRoot+"/xclient/product/thgl/";
	
	var grid = new BootstrapGrid({
		url:webRoot+"/purchasing/purchasing!listRefundDraft.do",
		target:$("#datagrid"),
		cols : [{
			field : 'number',
			label : '采购编号',
			renderer : function(value,row) {
				var a = $("<a>",{
					text : value,
					href:xclientPath+"dd-view.jsp?id="+ row.id,
					target:'_blank'
				});
				return a;
			}
		}, {
			field : 'coname',
				label : '供应商'
			}, {
				field : 'sub',
				label : '销售合同号'
			}, {
				field : 'money',
				label : '货币'
			}, {
				field : 'totalPrice',
				label : '采购金额'
			}, {
				field : 'pay_je',
				label : '运费'
			}, {
				field : 'senddate',
				label : '采购方向'
			}, {
				field : 'man',
				label : '责任人'
			}, {
				field : 'state',
				label : '状态'
			}, {
				field : 'datetime',
				label : '日期'
			} ]
	});
	grid.init();

	
	// 增加采购订单的按钮
	$("#addBtn").click(function() {

		window.open(webRoot+"/purchasing/refund/new.mvc");
	});

	$("#searchSaleBtn").click(function() {
		window.open(xclientPath + "return_order_search.jsp");
	});

});
