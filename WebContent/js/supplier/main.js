
$(function(){
	
	$("#datagrid").datagrid({
		title:"供应商列表",
		form:{
			inputs:[{
				label:'国家',
				name:'country',
				type:'select',
				opt:{
					dataset:{
						url:webRoot+'/main.dispatch',
						data:{
							method:'getCountryList'
						},
						field:"id",
						label:'name'
					},
					defaultOption:{
						text:'请选择',
						value:''
					}
				}
				
			},{
				label:'省市',
				name:'province',
				type:'select',
				opt:{
					dataset:{
						url:webRoot+'/main.dispatch',
						data:{
							method:'getProvinceList'
						},
						field:"id",
						label:'name'
					},
					defaultOption:{
						text:'请选择',
						value:''
					}
				}
			},{
				label:'行业类型',
				name:'tradetypes',
				type:'select',
				opt:{
					dataset:[{
						text:'贸易商'
					},{
						text:'现货供应商'
					},{
						text:'代理商'
					},{
						text:'分销商'
					},{
						text:'生产商'
					},{
						text:'其它'
					}],
					defaultOption:{
						text:'请选择',
						value:''
					}
				}
			},{
				label:'供应商名称',
				name:'coname'
			},{
				label:'联系人 ',
				name:'cojsfs'
			},{
				label:'产品类别 ',
				name:'scale'
			}],
			operations:[{
				text:'检索',
				click:function(){
					var data = $("#datagrid .tntxiaui-form").getParamMap(true);
					$("#datagrid").datagrid('reload',data);
					
				}
			},{
				text:'新增',
				click:function(){
					window.open('new.mvc');
				}
			},{
				text:'导入',
				click:function(){
					window.open(webRoot+'/supplier/import.mvc');
				}
			}]
		},
		url:webRoot+"/purchasing/supplier.do",
		data:{
			method:'list'
		},
		cols:[{
			label:'#',
			renderer:function(data,i){
				return i+1;
			}
		},{
			label:'供应商编号',
			field:'co_number',
			renderer:function(data,i){
				return "<a href='view.mvc?id="+data.id+"' target='_blank'>"+data.co_number+"</a>";
				
				
			}
		},{
			label:'供应商名称',
			field:'coname',
			renderer:function(data,i){
				return "<a href='view.mvc?id="+data.id+"' target='_blank'>"+data.coname+"</a>";
				
			}
		},{
			label:'联系人',
			field:'cojsfs'
		},{
			label:'联系人手机 ',
			field:'nshm'
		},{
			label:'电话 ',
			field:'cotel'
		},{
			label:'生产销售产品',
			field:'scale'
		},{
			label:'行　　业',
			field:'tradetypes'
		}]
		
	});
});


