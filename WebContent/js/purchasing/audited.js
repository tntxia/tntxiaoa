$(function() {

	var purchasingPath = webRoot + "/purchasing/purchasing.dispatch";

	$("#searchBtn").click(function() {
		var data = $("#searchForm").getParamMap();
		$("#datagrid").datagrid('load', data);
	});

	var datagridDiv = $("#datagrid");
	// 数据表格
	datagridDiv.datagrid({
		pageSize : 50,
		url : webRoot + "/purchasing/purchasing!listApproved.do",
		cols : [
		{
			field : 'number',
			label : '采购编号',
			renderer : function(data) {
				var a = $(
						"<a>",
						{
							text : data.number,
							click : function() {
								window
										.open(webRoot
												+ '/purchasing/detail.mvc?id='
												+ data.id);
							}
						});
				return a;
				
			}
		},
		{
			field : 'coname',
			label : '供应商名称'
		},
		{
			field : 'sub',
			label : '销售合同号',
			renderer : function(data) {
				return "<span class='saleNum'>" + data.sub
						+ "</span>";
			}
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
			field : 'l_spqk',
			label : '状态'
		}, {
			field : 'datetime',
			label : '合同日期'
		} ]
	});

	$(".main_sec").append(datagridDiv);



});
