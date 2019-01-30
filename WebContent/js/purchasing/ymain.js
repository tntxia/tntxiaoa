require.config({
	baseUrl:'../js',
	paths:{
		
		// 公用js
		jquery:'lib/jquery',
		underscore:'lib/underscore',
		backbone:'lib/backbone',
		
		// tntxiaui js
		'tntxiaui-valid':'lib/tntxiaui/valid',
		'tntxiaui-getParamMap':'lib/tntxiaui/getParamMap',
		'tntxiaui-reset':'lib/tntxiaui/reset',
		'tntxiaui-dropdown':'lib/tntxiaui/dropdown',
		'tntxiaui-panel':'lib/tntxiaui/panel',
		'tntxiaui-mainlayout':'lib/tntxiaui/mainLayout',
		'tntxiaui-combox':'lib/tntxiaui/combox',
		'tntxiaui-loadPage':'lib/tntxiaui/loadPage',
		'tntxiaui-dialog':'lib/tntxiaui/dialog',
		'tntxiaui-datepick':'lib/tntxiaui/datepick',
		'tntxiaui-datagrid':'lib/tntxiaui/datagrid',
		'tntxiaui-accordion':'lib/tntxiaui/accordion',
		'tntxiaui-crud':'lib/tntxiaui/crud',
		
		// oa 公用js
		'oalib-top':'lib/oa/top',
		'oalib-leftbar':'lib/oa/leftbar'
			
	},
	shim:{
		'backbone':{
			deps:['underscore'],
			exports:'Backbone'
		}
	},
	urlArgs: "bust=" + (new Date()).getTime()// 定义每次加载 js 带上参数， 防止 cache 
});

require(['jquery'],function(){
	
	
	
	$(function() {
		
		function searchOrder(){
			var data = $("#searchForm").getParamMap(true);
			$("#datagrid").datagrid('reload',data);
		}
		
		function viewTop(){
			window.location.href = "top50.jsp";
		}
		
		var form = $("<div>",{id:"searchForm"});
		var datagridDiv = $("<div>",{id:"datagrid"});
		
		require(['tntxiaui-datagrid'],function(){
			form.form({
				inputs:[{
					name:'pro_model',
					label:'型号'
				},{
					name:'pro_pp',
					label:'品牌'
				},{
					name:'supplier',
					label:'供 应 商',
					size:10
				},{
					name:'number',
					label:'采购编号   ',
					size:10
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
				},{
					name:'senddate',
					label:'采购方向',
					type:'select',
					opt:{
						dataset:[{
							text:'请选择',
							value:''
						},{
							text:"国际采购",
							value:"国际采购"
						},{
							text:"国内采购",
							value:"国内采购"
						}]
					}
				},{
					name:'man',
					label:'责任人',
					type:'select',
					opt:{
						dataset:{
							url : basePath + "purchasing/purchasing.dispatch",
							data : {
								method : "getPurchasingManList"
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
					name:'status',
					label:'状态',
					type:'select',
					size:10,
					opt:{
						dataset:[{
							text:"审批通过",
							value:"2"
						},{
							text:"合同已确认",
							value:"3"
						},{
							text:"待入库",
							value:"4"
						},{
							text:"已入库",
							value:"6"
						},{
							text:"全部退货",
							value:"7"
						},{
							text:"部分退货",
							value:"8"
						}],
						label : 'name',
						field : 'name',
						defaultOption:{
							text:'全部',
							value:''
						}
					}
				}]
			});

			
			

			// 数据表格
			datagridDiv.datagrid({
				
				operations:operations,
				pageSize:50,
				url : basePath + 'purchasing/purchasing.dispatch',
				data : {
					method : 'listApproved'
				},
				cols : [{
					field : 'number',
					label : '采购编号',
					renderer:function(data){
						return "<a href='#' ddid='"+data.id+"'>"+data.number+"</a>";
					}
				},{
					field : 'coname',
					label : '供应商名称'
				},{
					field : 'sub',
					label : '销售合同号',
					renderer:function(data){
						return "<span class='saleNum'>"+data.sub+"</span>";
					}
				},{
					field : 'money',
					label : '货币'
				},{
					field : 'totalPrice',
					label : '采购金额'
				},{
					field : 'pay_je',
					label : '运费'
				},{
					field : 'senddate',
					label : '采购方向'
				},{
					field : 'man',
					label : '责任人'
				},{
					field : 'l_spqk',
					label : '状态'
				},{
					field : 'datetime',
					label : '合同日期'
				}],
				onFinish:function(){
					
					require(['oalib-util'],function(util){
						// 获取用户信息，判断用户是否可以查看销售订单信息
						util.getUserInfo(function(userInfo){
							if(userInfo.role=="总经理" || userInfo.role=="副总经理"){
								$(".saleNum").wrap("<a href='#'>");
								$(".saleNum").click(function(){
									window.open('ydd-view.jsp?id='+this.innerHTML,'_blank','height=540,width=820,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
								});
							}
						});
					});
					
					
					$("a[ddid]").click(function(){
						var id = $(this).attr("ddid");
						window.open(basePath+'purchasing/purchasing.dispatch?method=detailView&id='+id,'rtop','height=540,width=820,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
					});
				}
			});
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
		
		var view_top_record = false;
		if(userRightList){
			$.each(userRightList,function(){
				if(this=="view_top_record"){
					view_top_record = true;
					return false;
				}
			});
		}
		
		var operations = [{
			label:'查询',
			click:searchOrder
		}];
		if(view_top_record){
			operations.push({
				label:'产品最高记录',
				click:viewTop
			});
		}
		
		
		
		
		require(['tntxiaui-loadPage','tntxiaui-crud','oalib-leftbar'],function(){
			
			require(['oalib-top'],function(top){
				top.render();
			});
			$.ajax({
				url : basePath + 'main.dispatch',
				data : {
					method : 'getLeftBar',
					type:'purchasing'
				},
				success:function(data){
					$(".leftbar").leftbar(data.bars);
				}
			});
			$(".main_sec").append(form);
			$(".main_sec").append(datagridDiv);
		});
		
	});
});



