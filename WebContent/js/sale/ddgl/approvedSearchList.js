
	$(function(){
		
		var params = $("#searchArea").getParamMap(true);
		
		$("#searchBut").click(function(){
			
			var params = $("#searchArea").getParamMap(true);
			$("#datagrid").datagrid('load',params);
			
			
		});
		
		$("#datagrid").datagrid({
			title:'已审批订单',
			url:webRoot+'/sale/sale.do',
			data:{
				method:'approvedSearchList'
			},
			initParam:params,
			cols:[{
				label:'合同编号',
				field:'number',
				renderer:function(data){
					var a = $("<a>",{
						text:data.number,
						click:function(){
							window.open(webRoot+"/sale/ddgl/detailAudited.mvc?id="+data.id);
						}
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
				label:'产品型号',
				field:'epro'
			},{
				label:'品牌',
				field:'supplier'
			},{
				label:'数量',
				field:'num'
			},{
				label:'单价',
				field:'salejg'
			},{
				label:'合计',
				field:'totalPrice'
			},{
				label:'收款金额',
				field:'smoney'
			},{
				label:'财务审批',
				field:'mode',
				renderer:function(data){
					return data.note?data.node:data.mode;
				}
			},{
				label:'责任人',
				field:'man'
			},{
				label:'当前状态',
				field:'state'
			}]
		});
	});




