
$(function(){
	
	var tkeditor;
	
	var id = $("[name=id]").val();
	
	VueManager.createVue("nav",{
		el:'#nav',
		data:{
			id:id
		},
		methods:{
			showHistory:function(){
				$("#printHistoryModal").modal('show');
			},
			printSale:function(){
				VueManager.setVueData('chooseCompany','title','打印预览中文销售合同');
				VueManager.setVueData('chooseCompany','printPage','cs_o_p.jsp');
				$("#chooseCompanyModal").modal('show');
			},
			printPI:function(){
				
				VueManager.setVueData('chooseCompany','title','打印预览PI');
				VueManager.setVueData('chooseCompany','printPage','pi.jsp');
				$("#chooseCompanyModal").modal('show');
			},
			printCI:function(){
				
				VueManager.setVueData('chooseCompany','title','打印预览CI');
				VueManager.setVueData('chooseCompany','printPage','pii.jsp');
				$("#chooseCompanyModal").modal('show');
				
			},
			warehouseReturn(){
				var id = this.id;
				var url = webRoot+'/warehouse/warehouse!outReturn2.do';
				util.ajax({
					url:url,
					data:{
						id:id
					},
					success:function(){
						alert("操作成功");
						window.location.reload();
					},
					error:function(){
						alert("操作异常");
					}
				});
			},
			stockUp:function(){
				var id = this.id;
				var url = webRoot+'/sale/sale.do';
				util.ajax({
					url:url,
					data:{
						id:id,
						method:'stockUp'
					},
					success:function(){
						alert("操作成功");
						window.location.reload();
					},
					error:function(){
						alert("操作异常");
					}
					
					
				});
			},
			printList:function(){
				var id = this.id;
				window.open('ecompany4.jsp?id='+id,'_blank');
			},
			printListNoPrice:function(){
				var id = this.id;
				window.open('ecompany5.jsp?id='+id,'_blank');
			},
			returnOrder:function(){
				var id = this.id;
				$.dialog({
					title:'意见',
					url:"return2.ftl",
					width:400,
					height:200,
					buttons:[{
						text:'确定',
						click:function(){
							if(confirm('是否确定返回此订单')){
								var remark = $("[name=return_remark]").val();
								var url = webRoot+'/sale/sale.do';
								util.ajax({
									url:url,
									data:{
										id:id,
										remark:remark,
										method:'returnOrder'
									},
									success:function(data){
										if(data.success){
											alert("操作成功");
											window.location.reload();
										}else{
											alert("操作失败！"+data.msg);
										}
										
									},
									error:function(){
										alert("操作异常");
									}
									
								});
							}
						}
					}]
				});
			},
			editItems:function(){
				$.dialog({
					width:500,
					height:500,
					content:"<textarea name='tbyq'></textarea><button id='submitItemBtn'>提交</button>"
				});
				
				console.log($("#tbyqDiv").html());
				
				$("[name=tbyq]").html($("#tbyqDiv").html());
				
				tkeditor = CKEDITOR.replace('tbyq');
				
				$("#submitItemBtn").click(function(){
					
					var id = $("[name=id]").val();
					
					$.ajax({
						url:webRoot+'/sale/sale!updateItems.do',
						type:'post',
						data:{
							id:id,
							items:escape(tkeditor.getData())
						},
						success:function(data){
							if(data.success){
								alert("操作成功");
								window.location.reload();
							}else{
								alert("操作失败");
							}
						},
						error:function(e){
							alert("服务请求异常！");
						}
					});
					
					
				});
			}
		}
	});
	
	VueManager.createVue("chooseCompany",{
		el:'#chooseCompanyModal',
		data:{
			title:'',
			rows:[],
			printPage:null
		},
		created:function(){
			this.fetchData();
		},
		methods:{
			fetchData:function(){
				var vm = this;
				
				$.ajax({
					url:webRoot+'/company!list.do',
					success:function(data){
						var rows = data.rows;
						$.each(rows,function(i,r){
							r.page = 15;
							r.round = 2;
						});
						vm.rows = rows;
					}
				});
			},
			print:function(row){
				window.open(this.printPage+"?id="+id+"&cid="+row.id+"&intPageSize="+row.page+"&round="+row.round);
			}
		}
	});
	
	
	new Vue({
		el:'#printHistoryModal',
		data:{
			rows:[]
		},
		created:function(){
			this.fetchData();
		},
		methods:{
			fetchData:function(){
				var vm = this;
				var number = $("[name=number]").val();
				$.ajax({
					url:webRoot+'/print!getPrintHistory.do',
					data:{
						number:number
					},
					success:function(data){
						
						vm.rows = data;
					}
				});
			}
		}
	});
	
	
	
});


