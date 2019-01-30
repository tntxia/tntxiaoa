
	
$(function(){
	
	var purchasingPath = webRoot + "/purchasing/purchasing.dispatch";
	
	$("#datagrid").datagrid({
		title:'新增订单',
		url:webRoot + "/purchasing/purchasing!list.do",
		
		cols:[{
			field:'number',
			label:'采购编号',
			renderer:function(data){
				var a = $("<a>",{
					text:data.number,
					click:function(){
						window.open(webRoot+"/purchasing/detail.mvc?id="+data.id);
					}
				});
				return a;
			}
		},{
			field:'coname',
			label:'供应商'
		},{
			field:'sub',
			label:'销售合同号'
		},{
			field:'money',
			label:'货币'
		},{
			field:'totalPrice',
			label:'采购金额'
		},{
			field:'pay_je',
			label:'运费'
		},{
			field:'senddate',
			label:'采购方向'
		},{
			field:'man',
			label:'责任人'
		},{
			field:'l_spqk',
			label:'状态'
		},{
			field:'datetime',
			label:'日期'
		}],
		search:{
			cols:[{
				field:'epro',
				label:'型号'
			},{
				field:'supplier',
				label:'品牌',
				type:'select',
				opt:{
					dataset:{
						url:purchasingPath,
						data:{
							method:'trademarkList'
						},
						field:'cpro',
						label:'cpro'
					},
					defaultOption:{
						label:'',
						value:''
					}
				}
				
			}]
		}
	});
	
	// 增加采购订单的按钮
	$("#addBtn").click(function(){
		OACommonUse.openOrderTemplateChoose("新增采购合同 - 选择模板","../ddgl/new.mvc");
		
	});
	
	
	$("#searchBtn").click(function(){
		var param = $("#searchDiv").getParamMap(true);
		$("#datagrid").datagrid("load",param);
	});
	
});


