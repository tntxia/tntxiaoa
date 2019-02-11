
	$(function(){

		
		var purchasingPath = webRoot + "/purchasing/purchasing.do";
		showToAudit();
		
		function showToAudit(){


			$("#main").datagrid({
				title:'已删订单',
				url:purchasingPath,
				data:{
					method:'listDeleted'
				},
				cols:[{
					field:'number',
					label:'采购编号',
					renderer:function(data){
						var a = $("<a>",{
							text:data.number,
							click:function(){
								window.open(webRoot+"/purchasing/purchasing.dispatch?method=detailView&id="+data.id);
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
				}]
			});
		
			
		
			
		}
		
	});


