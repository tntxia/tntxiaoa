function selectOption(id,value){
	var o = document.getElementById(id);
	if(o){
		for(var i=0;i<o.options.length;i++){
			var option = o.options[i];
			if(option.value==value){
				option.selected = true;
			}
		}
	}
}

/**
 * 
 * 用Ajax获取部门信息
 * 
 * @param departElementId
 */
function getDepartments(options){
	
	var departElementId = options.target;
	var label = options.label;
	var value = options.value;
	var changeCallback = options.changeCallback;
	
	var selLabel = "name";
	var selValue = "id";
	
	if(label){
		selLabel = label;
	}
	
	if(value){
		selValue = value;
	}
	
	if(changeCallback){
		$("#"+departElementId).change(function(){
			var departmentId = $(this).val();
			var target = $(this).attr("changeTarget");
			changeCallback({
				target:target,
				departmentId:departmentId,
				defaultValue:'',
				defaultLabel:'销售员'
			});
		});
	}
	
	var url;
	if(webRoot){
		url = webRoot+"/main.dispatch";
	}else{
		url = basePath+"main.dispatch";
	}
	
	$.ajax({
		url:url,
		type:'post',
		data:{
			method:'getDepartmentList'
		},
		success:function(data){
			
			var defaultValue = $("#"+departElementId).attr("defaultValue");

			$.each(data,function(i,item){
				
				if(defaultValue==item[selValue]){
					var op = $("<option value='"+item[selValue]+"' selected='selected'>"+item[selLabel]+"</option>");
					$("#"+departElementId).append(op);
				}else{
					var op = $("<option value='"+item[selValue]+"'>"+item[selLabel]+"</option>");
					$("#"+departElementId).append(op);
				}
				
			});
		},
		dataType:'json'
	});
}

/**
 * 用Ajax获取用户信息
 * @param userElementId
 */
function getUserList(userElementId,defaultTarget,label,value){
	
	var selLabel = "name";
	var selValue = "id";
	
	if(label){
		selLabel = label;
	}
	
	if(value){
		selValue = value;
		
	}
	
	var url;
	if(typeof webRoot=='string'){
		url= webRoot+"/user.do";
	}else{
		url = basePath+"user.do";
	}
	
	$.ajax({
		url:url,
		data:{
			method:'list'
		},
		type:'post',
		success:function(data){
			
			var defaultValue = $("#"+userElementId).attr("defaultValue");
			
			$.each(data,function(i,item){
				
				var op;
				
				if(defaultValue==item[selValue]){
					op = $("<option value='"+item[selValue]+"' selected>"+item[selLabel]+"</option>");
				}else{
					op = $("<option value='"+item[selValue]+"'>"+item[selLabel]+"</option>");
				}
				
				$("#"+userElementId).append(op);
			});
		},
		dataType:'json'
	});
}

/**
 * 用Ajax获取用户信息
 * @param userElementId
 */
function getSaleUserList(options){
	
	var target = options.target;
	
	var departmentId = options.departmentId;
	
	var defaultValue = options.defaultValue;
	
	var defaultLabel = options.defaultLabel;
	
	$("#"+target).empty();
	if(defaultValue==""||defaultValue){
		var defaultOption = $("<option>",{
			value:defaultValue,
			text:defaultLabel
		});
		$("#"+target).append(defaultOption);
	}
	
	$.ajax({
		url:basePath+"system/user/user.dispatch",
		data:{
			method:'listSale',
			departmentId:departmentId
		},
		type:'post',
		success:function(data){
			
			$.each(data,function(i,item){
				var op = $("<option value='"+item["name"]+"'>"+item["name"]+"</option>");
				$("#"+target).append(op);
			});
		},
		dataType:'json'
	});
}

/**
 * 
 * 用Ajax获取职位信息
 * 
 * @param departElementId
 */
function getPositions(positionId,defaultTarget){
	
	$.ajax({
		url:basePath+"position!list.do",
		type:'post',
		success:function(data){
			
			var defaultValue=null;
			
			if(defaultTarget){
				defaultValue = $("#"+defaultTarget).val();
			}

			$.each(data.rows,function(i,item){
				var roleName = item["role_name"];
				if(defaultValue==roleName){
					var op = $("<option selected='selected'>"+roleName+"</option>");
					$("#"+positionId).append(op);
				}else{
					var op = $("<option>"+roleName+"</option>");
					$("#"+positionId).append(op);
				}
				
			});
		},
		dataType:'json'
	});
}

/**
 * 
 * 用Ajax获取职位信息
 * 
 * @param departElementId
 */
function getRestrains(positionId,defaultTarget){
	
	$.ajax({
		url:basePath+"system/restrain.dispatch",
		type:'post',
		data:{
			method:'getRestrainList'
		},
		success:function(data){
			
			var defaultValue=null;
			
			if(defaultTarget){
				defaultValue = $("#"+defaultTarget).val();
			}

			$.each(data,function(i,item){
				
				if(defaultValue==item["id"]){
					var op = $("<option selected='selected' value='"+item["id"]+"'>"+item["name"]+"</option>");
					$("#"+positionId).append(op);
				}else{
					var op = $("<option  value='"+item["id"]+"'>"+item["name"]+"</option>");
					$("#"+positionId).append(op);
				}
				
			});
		},
		dataType:'json'
	});
}

function getUserInfo(callback){
	$.ajax({
		url:basePath+'main.dispatch',
		data:{
			method:'getUserInfo'
		},
		success:callback
	});
}

function getUserInfoAll(callback){
	$.ajax({
		url:basePath+'main.dispatch',
		data:{
			method:'getUserInfoAll'
		},
		success:callback
	});
}

(function(globe,name,fun){
	globe[name] = fun();
})(window,"OACommonSelects",function(){
	
	return {
		
		fillDepartmentSelect:function(opt){
			
			var sel = opt.sel;
			$.ajax({
				url:webRoot+"/department!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.departname,
							text:r.departname
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		
		fillPaymentSelect:function(opt){
			
			var sel = opt.sel;
			
			$.ajax({
				url:webRoot+"/payment!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.payment,
							text:r.payment
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillCurrentSelect:function(opt){
			var sel = opt.sel;
			$.ajax({
				url:webRoot+"/current!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.currname,
							text:r.currname
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillBrandSelect:function(opt){
			var sel = opt.sel;
			$.ajax({
				url:webRoot+"/brand!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.cpro,
							text:r.cpro
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillUserSelect:function(opt){
			
			var sel = opt.sel;
			var selLabel = "name";
			var selValue = "name";
			
			if(opt.label){
				selLabel = opt.label;
			}
			
			if(opt.value){
				selValue = opt.value;
				
			}
			
			var url= webRoot+"/user!list.do";
			
			$.ajax({
				url:url,
				type:'post',
				success:function(data){
					$.each(data,function(i,item){
						var op = $("<option value='"+item[selValue]+"'>"+item[selLabel]+"</option>");
						sel.append(op);
					});
				},
				dataType:'json'
			});
		},
		fillUnitSelect:function(opt){
			var sel = opt.sel;
			var url= webRoot+"/unit!list.do";
			
			$.ajax({
				url:url,
				type:'post',
				success:function(data){
					
					$.each(data,function(i,item){
						var op = $("<option value='"+item.punit_name+"'>"+item.punit_name+"</option>");
						sel.append(op);
					});
					
				},
				dataType:'json'
			});
		},
		fillTaxTypeSelect:function(opt){
			var sel = opt.sel;
			var arr = ["不含税","增值发票3%","普通发票3%","普通发票13%","增值发票13%","普通发票16%","增值发票16%","普通发票17%","增值发票17%","商业发票"];
			$.each(arr,function(i,item){
				var op = $("<option>"+item+"</option>");
				sel.append(op);
			});
		}
	};
	
});

(function(globe,name,fun){
	globe[name] = fun();
})(window,"OACommonUse",function(){
	return {
		
		// 公共的客户选择
		openClientChooseDialog : function(callbackFun){
			
			BootstrapUtils.createDialog({
				id:'chooseClientModal',
				template:webRoot+'/template/chooseClient.mvc',
				onFinish:function(){
					
					var dialog = this;
					var datagrid = dialog.find("#datagrid");
					var grid;
					
					var searchBtn = dialog.find("#searchBtn");
					searchBtn.click(function(){
						var coname = dialog.find("[name=coname]").val();
						grid.load({
							coname:coname
						});
					})
					
					if(typeof BootstrapGrid != "undefined"){
						renderClientGrid(datagrid);
					}else{
						var head = $("head");
						var script = $("<script>",{
							src:"/static/lib/bootstrap-plus/js/bootstrap.grid.js"
						});
						head.append(script);
						renderClientGrid(datagrid);
					}
					
					function renderClientGrid(target){
						grid = new BootstrapGrid({
							target:target,
							url:webRoot+"/client/client!list.do",
							cols : [{
								label : '序号',
								type : 'index'
							},{
								label : '客户编号',
								field : 'co_number'
							},{
								label : '客户名称',
								field : 'coname'
							},{
								label:'选择',
								renderer:function(val,row){
									var button = $("<button>",{
										text:'选择'
									});
									button.data("data",row);
									button.click(function(){
										var row = $(this).data("data");
										callbackFun(row);
										$("#chooseClientModal").modal('hide');
									});
									return button;
								}
							}]
						});
						grid.init();
						
						$("")
					}
					
				}
			});
			$("#chooseClientModal").modal('show');
		},
		
		// 公共的供应商选择
		openSupplierChooseDialog:function(callbackFun){
			BootstrapUtils.createDialog({
				id:'supplierChooseModal',
				title: '选择供应商',
				template:webRoot+'/template/chooseSupplier.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/purchasing/supplier!list.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data.rows;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#supplierChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#supplierChooseModal").modal('show');
		},
		
		// 公共的供应商联系人选择
		openSupplierContactChooseDialog:function(supplierNumber,callbackFun){
			
			BootstrapUtils.createDialog({
				id:'supplierContactChooseModal',
				title: '选择供应商联系人',
				template:webRoot+'/template/chooseSupplierContact.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{
								number:supplierNumber
							}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/purchasing/supplier!getContactList.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#supplierContactChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#supplierContactChooseModal").modal('show');
		},
		
		// 公共的银行选择
		openBankChooseDialog:function(callbackFun){
			
			BootstrapUtils.createDialog({
				id:'bankChooseModal',
				template:webRoot+'/finance/template/bankChoose.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{
					
							}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/finance/finance!getBankList.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#bankChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#bankChooseModal").modal('show');
		},
		openProductChooseDialog: function(callbackFun){
			var dialog = BootstrapUtils.createDialog({
				id:'chooseBatchDialog',
				title:'',
				template:webRoot+"/warehouse/template/chooseBatch.html",
				
				init:function(){
					
					var dialog = this;
					var div = dialog.find(".datagrid");
					var grid = new BootstrapGrid({
						target:div,
						check:true,
						url:webRoot+'/warehouse/warehouse!warehouseList.do',
						cols:[{
							label:"型号",
							field:'pro_model'
						},{
							label:"批号",
							field:'pro_name'
						},{
							label:'数量',
							field:'pro_num'
						}]
					});
					grid.init();
					
					var searchBtn = dialog.find(".searchBtn");
					searchBtn.click(function(){
						var paramMap = dialog.find(".form").getParamMap();
						grid.load(paramMap);
					});
					
					var chooseBtn = dialog.find(".chooseBtn");
					chooseBtn.click(function(){
						if(callbackFun){
							callbackFun(grid.getRowsChecked());
						}
					});
					
				}
			});
			$("#chooseBatchDialog").modal('show');
		},
		openSaleChooseDialog:function(callbackFun,opt){
			
			var url = opt.url;
			
			BootstrapUtils.createDialog({
				id:'chooseSaleDialog',
				template:webRoot+"/purchasing/template_choose_sale.mvc",
				init:function(){
					var grid = new BootstrapGrid({
						target:$("#chooseSaleDiv"),
						url:url,
						cols:[{
							label:'#',
							type:'index'
						},{
							label:'合同编号',
							field:'number'
						},{
							label:'客户',
							field:'coname'
						},{
							label:'含增值税率',
							field:'item'
						},{
							label:'发货日期',
							field:'send_date'
						},{
							label : '当前状态',
							field : 'state'
						},{
							label:'选择',
							renderer:function(value,row,field){
								var button = $("<button>",{
									text:'选择',
									click:function(){
										var data = $(this).data("data");
										console.log(data);
										callbackFun(data);
										$("#chooseSaleDialog").modal('hide');
									}
								});
								button.data("data",row);
								return button;
							}
							
						}]
					});
					grid.init();
					
					$("#chooseSaleSearchBtn").click(function(){
						grid.load({
							number:$("#searchSaleForm [name=number]").val()
						});
					})
					
				}
			});
			$("#chooseSaleDialog").modal('show');
			
		},
		openSaleProChooseDialog:function(opt){
			
			BootstrapUtils.createDialog({
				id:'chooseSaleProDialog',
				template:webRoot+"/sale/template/pro_choose.html",
				init:function(){
					let target = this.find("#datagrid");
					let grid = new BootstrapGrid({
						target:target,
						url:webRoot+"/sale/sale!listProByNumber.do?number="+opt.saleNumber,
						cols:[{
							label:'#',
							type:'index'
						},{
							label:'型号',
							field:'epro'
						},{
							label:'数量',
							field:'num',
							editable:true
						},{
							label:'选择',
							renderer:function(value,row,field){
								var button = $("<button>",{
									text:'选择',
									click:function(){
										var data = $(this).data("data");
										console.log(data);
										if(opt.callback)
											opt.callback(data);
										$("#chooseSaleProDialog").modal('hide');
									}
								});
								button.data("data",row);
								return button;
							}
							
						}]
					});
					grid.init();
					
					$("#chooseSaleSearchBtn").click(function(){
						grid.load({
							number:$("#searchSaleForm [name=number]").val()
						});
					})
					
				}
			});
			$("#chooseSaleProDialog").modal('show');
			
		}
	};
	
});






