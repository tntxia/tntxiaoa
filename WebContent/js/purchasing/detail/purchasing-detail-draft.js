

function openWin(url,opt,strOpt){
	if(!strOpt){
		strOpt = "";
	}
	
	$.each(opt,function(index,data){
		if(index!="target"){
			strOpt+=","+index+"="+data;
		}
	});
	
	window.open(url,opt.target,strOpt);
}

function goToTheUrl(url,target){
	var opt = {
		target:target,
		height:280,
		width:270,
		top:200,
		left:30,
		toolbar:'yes',
		menubar:'no',
		scrollbars:'yes',
		resizable:'yes',
		location:'no',
		status:'no'
	};
	openWin(url,opt);
}

function delclick(){
	
	var id = $("#id").val();
	window.open(basePath+'/ddgl/del-dd.jsp?id='+id,'_blank', 'height=300, width=500, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
	return;
}


function savePro(){
	
	var pro_model = document.form1.pro_model_new.value;
	
	checkProBeforeSave(pro_model,function(){
		sub();
	});
	
}

function checkProBeforeSave(pro_model,callback){
	
	if(pro_model==""){
		callback();
		return;
	}
	$.ajax({
		url:webRoot+"/warehouse/warehouse!search.do",
		cache:false,
		type:'post',
		data:{
			model:pro_model
		},
		success:function(data){
			var result = data;
			var count = result.length;
			var $msg = $("<div>产品已经存在在仓库中</div>");
			var $table = $("<table class='pretty_table' border=1><tr><td>仓库类型</td><td>产品型号</td><td>品牌</td><td>封装</td><td>数量</td><td>备注</td><td>查看</td></tr></table>");
			function createTd(content){
				var td = $("<td>");
				return td.append(content);
			}
			for(var i=0;i<count;i++){
				var r = result[i];
				var $tr = $("<tr>");
				$tr.append(createTd(r.pro_addr));
				$tr.append(createTd(r.pro_model));
				$tr.append(createTd(r.pro_sup_number));
				$tr.append(createTd(r.pro_name));
				$tr.append(createTd(r.pro_num));
				$tr.append(createTd(r.pro_remark));
				var input = $("<button>",{
					text:'查看详情',
					click:function(){
						viewProduct(r.id);
					}
				});
				$tr.append(createTd(input));
				$table.append($tr);
				
				
			}
			$msg.append($table);
			$msg.append("<br>");
			
			var ignoreBtn = $("<button>",{
				text:'忽略',
				click:function(){
					callback();
				}
			});
			$msg.append(ignoreBtn);
			
			var closeBtn = $("<button>",{
				text:'关闭',
				click:function(){
					$.unblockUI();
				}
			});
			$msg.append(closeBtn);
			
			if(count>0){
				$.blockUI({
					message: $msg,
					css:{
						width:'50%',
						left:'20%'
					}
				});
			}else{
				callback();
			}
		}
	});
}

function viewProduct(pro_id){
	try{
		window.open(basePath+"warehouse/warehouse.dispatch?method=productView&id="+pro_id);
		$.unblockUI();
	}catch(e){
		alert(e);
	}
}

function sub(){
	
	var purchasingPath = webRoot+"/purchasing/purchasing!savePro.do";
	var params = $("[name=form1]").getParamMap(true);
	params.ddid = $("#id").val();
	$.ajax({
		url:purchasingPath,
		type:'post',
		cache:false,
		data:params,
		success:function(data){
			if(data.success){
				window.location.reload();
			}
		}
	});
	
}

function toEdit(){
	var id = $("#id").val();
	window.location.href = webRoot+"/ddgl/editdd.jsp?id="+id;
}

var purchasingPath = webRoot+"/purchasing/purchasing.do";

$(function(){
	
	$("#chooseSaleBtn").click(function(){
		OACommonUse.openSaleChooseDialog(function(data){
			OACommonUse.openSaleProChooseDialog({
				saleNumber:data.number,
				callback(data){
					checkProBeforeSave(data.epro,function(){
						$.ajax({
							url:webRoot+"/purchasing/purchasing!pushSalePro.do",
							data:{
								id:data.id,
								ddid:$("#id").val(),
								hb:$("#hb").val(),
								rate:''
							}
						}).done(function(){
							window.location.reload();
						}).fail(function(){
							alert("增加失败");
						});
					});
				}
			})
			
		},{
			url:webRoot+"/purchasing/purchasing!getSaleList.do"
		});
	});
	init();
});

function submitOrder(){
	var id = $("#id").val();
	$.ajax({
		url:webRoot+"/purchasing/purchasing!toAudit.do",
		type:'post',
		cache:false,
		data:{
			id:id
		},
		success:function(data){
			if(data.success){
				alert("操作成功");
				
				if(window.opener){
					window.opener.location.reload();
				}
				window.close();
			}else{
				alert("操作失败"+data.msg);
			}
		}
	});
}

function del(){
	var id = $("#id").val();
	util.ajax({
		url:purchasingPath,
		data:{
			method:'del',
			id:id
		},
		success:function(data){
			if(data.success){
				alert("操作成功");
				window.opener.location.reload();
				window.close();
			}
		}
	});
}

function init(){
	
	OACommonSelects.fillBrandSelect({
		sel:$("[name=2supplier]")
	});
	
	var id = $("#id").val();
	
	$.ajax({
		url:webRoot+"/purchasing/purchasing!getProList.do",
		data:{
			id:id
		},
		success:function(data){
			fillProList(data);
		}
	});
	
	function fillProList(data){
		$.each(data,function(i,d){
			var index = i+1;
			
			var tr = $("<tr>");
			var td = $("<td>",{
				text:index
			});
			var a = $("<a>",{
				href:'#',
				text:'删除',
				click:function(){
					$.ajax({
						url:purchasingPath,
						data:{
							method:"delPro",
							id:d.id
						},
						success:function(data){
							window.location.reload();
						}
					});
				}
			});
			td.append(a);
			tr.append(td);
			tr.append("<td><input name='id"+index+"' type='hidden' id='id"+index+"' value='"+d.id+"'>"+
							"<input type='text' name='pro_model"+index+"' value='"+d.epro+"' size='20'></td>" +
							"<td><input type='text' name='pro_name"+index+"' value='"+d.cpro+"' size='5' value='0' readonly='true'></td>" +
							"<td><input type='text' name='fz"+index+"' value='"+d.pro_number+"' size='5' readonly='true'></td>" +
							"<td><input type='text' name='num"+index+"' value='"+d.num+"' size='5'></td>" +
							"<td><select class='punit' name='punit"+index+"'><option>"+d.unit+"</option></select></td>" +
							"<td><input type='text' name='selljg"+index+"' value='"+d.selljg+"' size='6'></td>" +
							"<td><input type='text' name='rate"+index+"' value='"+d.rate+"' size='6'></td>" +
							"<td><input type='text' name='supplier"+index+"' value='"+d.supplier+"' size='10'></td>" +
							"<td><input type='text' name='pro_tr"+index+"' value='"+d.cgpro_ydatetime+"' size='10'></td>" +
							"<td><input type='text' name='pro_remark"+index+"' value='"+d.remark+"' size='28'></td>");
			$("#proList").append(tr);
			
		});
		$("[name=i]").val(data.length);
		
		$(".punit").each(function(){
			var sel = $(this);
			OACommonSelects.fillUnitSelect({
				sel:sel
			});
		});
		
		calTotalPrice();
		
	}
	
	function calTotalPrice(){
		
		var tbody = $("#proList");
		var trList = tbody.find("tr");
		var zprice = 0;
		trList.each(function(){
			var tr = $(this);
			var inputs = tr.find("input");
			var num = inputs.get(4).value;
			console.log("num",num);
			var selljg = inputs.get(5).value;
			console.log("selljg",selljg);
			zprice += num * selljg;
			console.log("zprice",zprice);
		});
		$("#zprice").html(zprice);
		
	}
	

}

function chooseBatch(){
	OACommonUse.openProductChooseDialog(function(rows){
		
		var id = $("#id").val();
		var ids = new Array();
		$.each(rows,function(i,r){
			ids.push(r.id);
		});
		
		$.ajax({
			url:webRoot+"/warehouse/warehouse!pushProductIntoPurchasingOrder.do",
			type:'post',
			cache:false,
			data:{
				ddid:id,
				ids:ids.join(",")
			}
		}).done(function(data){
			if(data.success){
				window.location.reload();
			}else{
				alert("操作失败");
			}
		}).fail(function(e){
			alert("操作异常");
		});
	
	})
}



