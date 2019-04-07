
function delclick(){
	
	var ddid = $("#ddid").val();
	var coname = escape($("#coname").val());
	var co_number = $("#co_number").val();
	var fynumber =  $("#fynumber").val();
	var wids = "";
	$("select[name=wid]").each(function(){
		if(wids==""){
			wids = this.value;
		}else{
			wids += ","+this.value;
		}
	});
	
	var sids = "";
	$("input[name=sid]").each(function(){
		if(sids==""){
			sids = this.value;
		}else{
			sids += ","+this.value;
		}
	});
	
	var options = {
		ddid : ddid,
		coname : coname,
		co_number : co_number,
		wids : wids,
		sids : sids,
		fynumber : fynumber
	};
	
	$.ajax({
		url:webRoot+"/warehouse/warehouse!doOutSale.do",
		
		cache:false,
		type:'post',
		data:options,
		success:function(data){
			if(data.success){
				alert("操作成功");
				window.close();
			}else{
				alert("操作失败:"+data.msg);
			}
		}
		
	});

	
}

/**
 * 返回合同
 */
function toReturn(){
	
	BootstrapUtils.createDialog({
		id:'returnModal',
		template:webRoot+"/warehouse/out/template_return.mvc",
		onFinish:function(){
			
			var content = this;
			content.find("#return_submit_btn").click(function(){
				var r = confirm("是否确认返回此合同！！");
				if(r){
					var ddid =$("#ddid").val();
					var p_states = $("[name=p_states]").val();
					$.ajax({
						url:webRoot+"/warehouse/warehouse!outReturn.do",
						type:'post',
						data:{
							ddid:ddid,
							p_states:p_states
						},
						success:function(data){
							if(data.success){
								alert("操作成功");
								window.opener.location.reload();
								window.close();
							}else{
								alert("操作失败"+data.msg);
							}
						}
					});
					
				}
			});
		}
	});
	
	$("#returnModal").modal('show');
	
//	var ddnumber = $("#ddnumber").val();
//	var man1 = escape($("#man1").val());
//	var ddid = $("#ddid").val();
//	window.location.href = webRoot+'/warehouse/out/return.jsp?number='+ddnumber+'&man='+man1+'&ddid='+ddid;
	
}

function outSingle(ddid,id){
	
	BootstrapUtils.createDialog({
		id:'warehouseSingleOutModal',
		title:"产品出库",
		template:webRoot+'/template/warehouseSingleOut.mvc',
		onFinish:function(){
			var button = this.find("button");
			var input = this.find("input");
			
			button.click(function(){
				var num = input.val();
				if(num==""){
					alert("出库数量不能为空！");
					return;
				}
				
				var wid = $("#wid"+id).val();
				$.ajax({
					url:webRoot+"/warehouse/warehouse!out.do",
					cache:false,
					type:'post',
					data:{
						id:id,
						ddid:ddid,
						wid:wid,
						num:num
					},
					success:function(data){
						button.prop("disabled",false);
						if(data.success){
							alert("入库成功！");
							window.location.reload();
						}else{
							alert("操作失败:"+data.msg);
						}
					},
					error:function(e){
						alert("服务器发生错误，请稍候重试！");
						button.prop("disabled",false);
						throw e;
						
					}
				});
			
				button.prop("disabled",true);
			})
			
		}
	});
	$("#warehouseSingleOutModal").modal('show');
	
}

function deliveryBill(){
	var id = $("#ddid").val();
	 window.open('company1.jsp?id='+id,'_blank');
	 
	 return;
	}