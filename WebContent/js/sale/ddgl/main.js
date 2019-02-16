$(function() {
	
	$("#searchForm").form({
		inputs:[{
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
				url : basePath + "main.dispatch",
				data : {
					method : "getDepartmentList"
				},
				label : 'name',
				field : 'id',
				defaultOpt:{
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
				url : basePath + "system/user/user.dispatch",
				data : {
					method : "listSale"
				},
				label : 'name',
				field : 'name',
				defaultOpt:{
					text:'请选择',
					value:''
				}
			}
		},{
			name:'pStates',
			label:'返回原因',
			type:'select',
			size:10,
			opt:{
				dataset:[{
					name:"A、退回重新编辑"
				},{
					name:"B、货物检验有质量问题"
				},{
					name:"C、货物不符合合同要求"
				},{
					name:"D、客户推迟订货时间"
				},{
					name:"E、供应商没有货"
				},{
					name:"F、没有收到货款"
				},{
					name:"G、其他"
				}],
				label : 'name',
				field : 'name',
				defaultOpt:{
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
	});
	
	var userRightList = null;
	
	$.ajax({
		url:basePath +'main.dispatch',
		async:false,
		data:{
			method:'getUserRight'
		},
		success:function(data){
			userRightList = data;
		}
	});
	
	var containSubAdd = false;
	if(userRightList){
		$.each(userRightList,function(){
			if(this=="subadd"){
				containSubAdd = true;
			}
		});
	}
	
	var operations = null;
	if(containSubAdd){
		operations = [{
			label:'检索',
			click:searchOrder
		},{
			label:'新增合同',
			click:function(){
				$.ajax({
					url:basePath+'sale/sale.dispatch',
					data:{
						method:"getTemplateList"
					},
					success:function(data){
						var opt = {
						  trClick:function(){
							  var rowData = $(this).data("data");
							  window.location.href = "ddglt.jsp?q_tk="+rowData.id;
						  },
						  cols:[{
							  label:'合同名称',
							  renderer:function(data,i){
								  return i;
							  }
						  },{
							  field:'name',
							  label:'合同名称'
						  },{
							  field:'coname',
							  label:'公司名称'
						  },{
							  field:'createDate',
							  label:'日期'
						  }]
						};
						var table = $tntxiaui.createTable(opt,data);
						
						
						// 点击了新增合同后，弹出对话框
						$.dialog({
							width:800,
							height:400,
							css:{
								border:'1px solid'
							},
							content:table
						});
					}
				});
				
				// window.open('hlxx.jsp','rtop', 'height=542, width=1021, top=30, left=0, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
			}
		},{
			label:'由报价形成合同',
			click:function(){
				window.open('qhlxx.jsp','_blank', 'height=542, width=1021, top=50, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
			}
		},{
			label:'合同模板',
			click:function(){
				window.open('../../web/zdy/ht/shlxx.jsp','gnw', 'height=501, width=955, top=100, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
			}
		}];
	}

	// 数据表格
	$("#datagrid").datagrid({
		
		operations:operations,
		pageSize:50,
		url : basePath + 'sale/sale.dispatch',
		data : {
			method : 'list'
		},
		cols : [ {
			label:'序号',
			renderer:function(data,i){
				return "<a href='#' ddid='"+data.id+"'>"+(i+1)+"</a>";
			}
		},{
			field : 'number',
			label : '合同编号',
			renderer:function(data){
				return "<a href='#' ddid='"+data.id+"'>"+data.number+"</a>";
			}
		},{
			field : 'coname',
			label : '客户名称'
		},{
			field : 'mode',
			label : '付款方式'
		},{
			field : 'pstates',
			label : '返回原因'
		},{
			field : 'man',
			label : '责任人'
		},{
			field : 'state',
			label : '审批状态'
		} ],
		onFinish:function(){
			$("a[ddid]").click(function(){
				var ddid = $(this).attr("ddid");
				window.open('dd-view.jsp?id='+ddid,'rtop','height=540,width=820,left=50,top=50,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
			});
		}
	});
	
	function searchOrder(){
		var number = $("[name=number]").val();
		var model = $("[name=epro]").val();
		var coname = escape($("[name=coname]").val());
		var productNumber = $("[name=pro_number]").val();
		var dept = $("[name=depts]").val();
		var man = $("[name=man]").val();
		var pStates = $("[name=pStates]").val();
		
		var startdate = $("[name=startdate]").val();
		var enddate = $("[name=enddate]").val();
		
		$("#datagrid").datagrid('reload',{
			number:number,
			model:escape(model),
			coname:coname,
			productNumber:productNumber,
			dept:dept,
			man:man,
			pStates:pStates,
			startdate:startdate,
			enddate:enddate
			
		});
	}

});
