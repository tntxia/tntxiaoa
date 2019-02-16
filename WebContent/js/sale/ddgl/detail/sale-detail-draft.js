function isValid(){
	if(form1.proid.value=="0"){
	    alert("请填写产品信息!");
		return false;
	}
}

function delclick(){
	
	BootstrapUtils.createDialog({
		id:'delDialog',
		template:webRoot+"/sale/template/del.html",
		onConfirm(){
			var number = $("[name=number]").val();
			var id = $("#id").val();
			var remark = this.find("[name=remark]").val();
			
			$.ajax({
				url:webRoot+"/sale/sale!del.do",
				type:'post',
				data:{
					id,
					remark
				}
			}).done(function(res){
				if(res.success){
					alert("操作成功");
					if(window.opener){
						window.opener.location.reload();
					}
					window.close();
					
				}else{
					alert("操作失败："+res.msg);
				}
			}).fail(e=>{
				alert("操作异常");
			})
		}
	});
	
	$("#delDialog").modal('show');
	
	
}

function delPro(id){
	$.ajax({
		url:webRoot+"/sale/sale!delPro.do",
		cache:false,
		type:'post',
		data:{
			id:id
		}
	}).done(function(data){
		if(data.success){
			window.location.reload();
		}else{
			alert("删除失败");
		}
	}).fail(function(){
		alert("删除异常");
	})
	
}

$(function(){
		
	var id = $("#id").val();
	
	$("#form1").buildform({
		actions:{
			savePro:function(){
				var params = this.getParamMap();
				util.ajax({
					url:webRoot+"/sale/sale!savePro.do",
					data:params,
					success:function(data){
						if(data.success){
							window.location.reload();
						}else{
							alert("操作失败！"+data.msg);
						}
					}
				});
				console.log(params);
			}
		}
	});
		
	$("#submitBut").click(function(){
		
		function submitOrder(){
			util.ajax({
				url:webRoot+"/sale/sale!toAudit.do",
				data:{
					id:id
				},
				success:function(data){
					if(data.success){
						alert("审批提交成功,请等候审批!");
						window.opener.location.reload();
						window.close();
					}else{
						alert("操作失败！"+data.msg);
					}
				}
			});
		}
		
		var noSavePro = $("[name=2pro_model]").val();
		if(noSavePro && noSavePro!=""){
			if(confirm("还有产品没有保存，是否确定提交？")){
				submitOrder();
			}
		}else{
			submitOrder();
		}
			
	});
		
});

function chooseBatch(){
	OACommonUse.openProductChooseDialog(function(rows){
		
		var id = $("#id").val();
		var ids = new Array();
		$.each(rows,function(i,r){
			ids.push(r.id);
		});
		
		$.ajax({
			url:webRoot+"/warehouse/warehouse!pushProductIntoSaleOrder.do",
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
