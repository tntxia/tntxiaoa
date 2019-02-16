
$(function(){
	$("#crud").datagrid({
		title:'待审批订单',
		url:webRoot+'/sale/sale!approvingList.do',
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'合同编号',
			field:'number',
			renderer:function(data){
				var a = $("<a>",{
					text:data.number,
					click:function(){
						window.open(webRoot+"/sale/approvingView.mvc?id="+data.id);
					}
				});
				return a;
			}
		},{
			label:'客户名称',
			field:'coname'
		},{
			label:'客户名称',
			field:'coname'
		},{
			label:'申请人',
			field:'man'
		},{
			label:'审批人',
			field:'spman'
		},{
			label:'状态',
			field:'state'
		}]
	});
});




