$(function() {
	
	OACommonSelects.fillBrandSelect({
		sel:$("[name=pp]")
	});

	$("#searchBut").click(function() {

		var params = $("#searchArea").getParamMap(true);
		$("#datagrid").datagrid('load', params);

	});
	
	$("#datagrid")
			.datagrid(
					{
						title : '已审批订单',
						url : webRoot + '/sale/sale.do',
						data : {
							method : 'approvedList'
						},
						cols : [
								{
									label : '序号',
									type : 'index'
								},
								{
									label : '合同编号',
									field : 'number',
									renderer : function(data) {
										var url = webRoot + "/sale/ddgl/detailAudited.mvc?id=" + data.id;
										
										var a = $("<a>",{
											text : data.number,
											href : url,
											target:'_blank'
										});
										return a;
									}
								},
								{
									label : '客户订单号',
									field : 'custno'
								},
								{
									label : '客户名称',
									field : 'coname'
								},
								{
									label : '采购编号',
									field : 'purchasingNumber',
									renderer : function(data) {
										var a = $("<a>", {
											text : data.purchasingNumber,
											click : function() {
												window.open("cdd-view.jsp?id="
														+ purchasingNumber);
											}
										});
										return a;
									}
								},
								{
									label : '发票',
									field : 'item'
								},
								{
									label : '合同金额',
									field : 'totalPrice'
								},
								{
									label : '收款金额',
									field : 'smoney'
								},
								{
									label : '退货金额',
									field : 'refundPrice'
								},
								{
									label : '财务审批',
									field : 'mode',
									renderer : function(data) {
										return data.note ? data.node
												: data.mode;
									}
								}, {
									label : '发货日期',
									field : 'sendDate'
								}, {
									label : '申请人',
									field : 'man'
								}, {
									label : '当前状态',
									field : 'state'
								} ]
					});
});
