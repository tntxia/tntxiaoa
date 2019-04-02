

function delclick(){
	
	if (confirm("确实要入库吗？")) {
		var id = $("#id").val();
		$.ajax({
			url:webRoot+'/warehouse/warehouse!checkIn.do',
			data:{
				method:'checkIn', 
				id:id
			},
			success:function(data){
				if(data.success){
					alert("操作成功");
					
					window.close();
				}else{
					alert("操作失败："+data.msg);
					
				}
			},
			error:function(e){
				alert(e);
				console.log(e+"");
				
			}
		});
	
		
	}
	
}

//取消入库单
function cancelRk(){
	
	if(confirm("是否确认要取消入库")){
		
		var id = $("#id").val();
		$.ajax({
			url:webRoot+'/warehouse/warehouse!cancelInWarehouse.do',
			data:{
				id:id
			},
			success:function(data){
				if(data.success){
					alert("操作成功");
					window.opener.location.reload();
					window.close();
				}else{
					alert("操作失败："+data.msg);
				}
			}
		});
		
	}
}

// 编辑
function edit(){
	
	var id = $("#id").val();
	window.open(webRoot+'/warehouse/in/edit.jsp?id='+id,'_self', 
			'height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	
}

// 增加仓库产品
function addProduct(){
	var id = $("#id").val();
	var hb = $("#hb").val();
	window.open(basePath+'warehouse/prosearch.jsp?id='+id+'&hb='+hb,'_self', 'height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
}

function openPurchasingChooseDialog(){
	
	var id = $("#id").val();
	BootstrapUtils.createDialog({
		id:'searchPurchasingDialog',
		template:webRoot+'/warehouse/template/searchPurchasing.html',
		
		onFinish:function(){
			
			var dialog = this;
			var grid = new BootstrapGrid({
				target:$("#datagrid"),
				url:webRoot+'/warehouse/warehouse!purchasingSearch.do',
				paramMap:{
					ddid:id
				},
				cols:[{
					label:'编号',
					field:'number'
				},{
					label:'供应商',
					field:'coname'
				},{
					label:'采购日期',
					field:'datetime'
				},{
					label:'采购员',
					field:'man'
				},{
					label:'选择',
					renderer:function(value,row){
						var button = $("<button>",{
							text:'选择',
							click:function(){
								var row = $(this).data("row");
								$("#searchPurchasingDialog").modal('hide');
								showPurchasingProduct(row.id);
							}
						});
						button.data("row",row);
						return button;
					}
				}]
			});
			grid.init();
			
			this.find("#searchBtn").click(function(){
				var params = dialog.getParamMap();
				grid.load(params);
			});
			
		}
	});
	$("#searchPurchasingDialog").modal('show');
}

// 从采购订单选择
function addFromPurchasing(){
	
	// 入库单ID
	var id = $("#id").val();
	// 采购单ID
	var purchasingId = parseInt($("#purchasingId").val());
	
	if(purchasingId){
		showPurchasingProduct(purchasingId);
	}else{
		openPurchasingChooseDialog();
	}
	
	
}

function showPurchasingProduct(purchasingId){
	
	BootstrapUtils.createDialog({
		id:'searchPurchasingProductDialog',
		template:webRoot+'/warehouse/template/searchPurchasingProduct.html',
		showDefaultButton:false,
		onFinish:function(){
			
			var dialog = this;
			
			var target = this.find("#datagrid");
			var grid = new BootstrapGrid({
				check:true,
				target:target,
				url:webRoot+'/warehouse/warehouse!purchasingProductSearch.do',
				paramMap:{
					ddid:purchasingId
				},
				cols:[{
					label:'型号',
					field:'epro'
				},{
					label:'数量',
					field:'numleft',
					editable:true
				},{
					label:'选择',
					renderer:function(value,row){
						
						var button = $("<button>",{
							text:'选择',
							click:function(){
								var id = $("#id").val();
								var selectedRows = grid.getRowsChecked();
								
								
								if(!selectedRows.length){
									alert("请选择一个产品入库");
									return;
								}
								
								$.ajax({
									url:webRoot+"/warehouse/warehouse!purchasingProductIn.do",
									type:'post',
									data:{
										refundId:id,
										products:JSON.stringify(selectedRows),
										ddid:purchasingId
									}
								}).done(function(data){
									if(data.success){
										alert("操作成功");
										window.location.reload();
									}else{
										alert("操作失败");
									}
								})
							}
						});
						button.data("row",row);
						return button;
					}
				}]
			});
			grid.init();
			
			
		}
	});
	$("#searchPurchasingProductDialog").modal('show');
	
	
}

function printPreview(){
	var id = $("#id").val();
	window.open(basePath+'warehouse/in/ffmprintvc.jsp?id='+id,'_blank', 'height=500, width=955, top=50, left=50, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no');
}

function rk(id){
	$.ajax({
		url:basePath+'warehouse/warehouse.dispatch',
		data:{
			method:'checkInSingle', 
			id:id
		},
		success:function(data){
			if(data.success){
				alert("操作成功");
				window.location.reload();
			}
		}
	});
}

function split(inId,id,num){
	window.location.href = basePath+"warehouse/in/split.jsp?inId="+inId+"&id="+id+"&num="+num;
}

function editNum(inId,id,num){
	window.location.href = webRoot+"/warehouse/in/editNum.jsp?inId="+inId+"&id="+id+"&num="+num;
}

function del(id){
	
	util.ajax({
		url:warehousePath,
		data:{
			method:'delRkhouse', 
			id:id
		},
		success:function(data){
			if(data.success){
				alert("操作成功");
				window.location.reload();
			}
		}
	});
	
}

// 将没有入库成功的产品重新入库
function reCheckin(id){
	
	$.ajax({
		url:warehousePath,
		data:{
			method:'reCheckin', 
			id:id
		},
		success:function(data){
			if(data.success){
				alert("操作成功");
				window.location.reload();
			}else{
				alert("操作失败");
			}
		}
	});
	
}

function toEditPro(id){
	var content = $("<div>");
	content.html("请输入你的修改的数量：<input name='editProNum'>");
	var button = $("<button>",{
		text:'确定',
		click:function(){
			var num = $(this).prev().val();
			$.ajax({
				url:warehousePath,
				data:{
					method:'changeInPro',
					id:id,
					num:num
				},
				success:function(data){
					if(data.success){
						alert("操作成功");
						window.location.reload();
					}else{
						alert("操作失败"+data.msg);
					}
				},
				error:function(e){
					alert(e);
				}
			});
		}
	});
	content.append(button);
	require(['tntxiaui-dialog'],function(){
		$.dialog({
			title:'修改产品信息',
			content:content,
			width:500,
			height:500
		});
	});
}


function openEditProDialog(id){
	BootstrapUtils.createDialog({
		id:'editProDialog',
		template:webRoot+'/warehouse/in/proview.mvc?id='+id,
		onFinish:function(){
			let dialog = this;
			this.find("#modifyNumBut").click(function(){
				var params = dialog.getParamMap();
				$.ajax({
					url:webRoot+"/warehouse/warehouse!editInNum.do",
					type:'post',
					data:{
						id:params.id,
						num:params.pro_num
					},
					success:function(data){
						if(data.success){
							window.location.reload();
						}else{
							alert("操作失败！");
						}
					},
					error:function(){
						alert("操作异常！");
					}
				});
			});
		}
	});
	$("#editProDialog").modal('show');
}

function delPro(id){
	$.ajax({
		url:webRoot+"/warehouse/warehouse!delInPro.do",
		type:'post',
		data:{
			id:id
		},
		success:function(data){
			if(data.success){
				window.location.reload();
			}else{
				alert("操作失败！");
			}
		},
		error:function(){
			alert("操作异常！");
		}
	});
}