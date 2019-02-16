
$(function(){
	
	showDeletedMain();
	
	function showDeletedMain(){
		
		var crud = $("<div>",{
		id:'crud'
	});
	$(".main_sec").append(crud);

	var searchOpt = {
		escape:true,
		cols:[{
			name:'epro',
			label:'型　号'
		},{
			name:'coname',
			label:'客户名称 ',
			size:10
		},{
			name:'number',
			label:'合同编号  ',
			size:10
		},{
			name:'pro_number',
			label:'产品编号  ',
			size:10
		},{
			name:'depts',
			label:'选择部门',
			type:'select',
			size:10,
			opt:{
				dataset:{
					url : webRoot + "/main.dispatch",
					data : {
						method : "getDepartmentList"
					},
					label : 'name',
					field : 'id'
				},
				defaultOption:{
					text:'选择部门',
					value:''
				}
			}
		},{
			name:'man',
			label:'销售员',
			type:'select',
			size:10,
			opt:{
				dataset:{
					url : webRoot + "/system/user/user.dispatch",
					data : {
						method : "listSale"
					},
					label : 'name',
					field : 'name'
				},
				
				defaultOption:{
					text:'请选择',
					value:''
		
				}
			}
		},{
			name:"startdate",
			size:10,
			label:'起始日期 ',
			type:'date'
		},{
			name:"enddate",
			size:10,
			label:'终止日期 ',
			type:'date',
			opt:{
				showNowDate : true
			}
		}]
	};
	crud.datagrid({
		title:'已删合同',
		pageSize:50,
		url : webRoot + '/sale/sale!listDeleted.do',
		search:searchOpt,
		cols : [ {
			label:'序号',
			type:'index'
		},{
			field : 'number',
			label : '合同编号',
			renderer:function(data){
				var a = $("<a>",{
					href:'#',
					text:data.number,
					click:function(){
						window.open(basePath+"sale/ddgl/deleted-contact-view.jsp?id="+data.id);
					}
				});
				return a;
				
			}
		},{
			field : 'coname',
			label : '客户名称'
		},{
			field : 'del_remark',
			label : '删除原因'
		},{
			field : 'man',
			label : '责任人'
		},{
			field : 'state',
			label : '审批状态'
		} ]
	});

}

function showDeleted(){
	$(".main_sec").empty();
	require(['oalib-util'],function(util){
			util.getUserinfo(showDeletedMain);
		});
	}
});




