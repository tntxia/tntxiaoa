
$(function(){
	
	var purchasingPath = webRoot+'/purchasing/purchasing.dispatch';
	
	$("#printHistoryBtn").click(function(){
		
		BootstrapUtils.createDialog({
			id:'showPrintHistoryDialog',
			title:'打印历史',
			template:`${webRoot}/purchasing/template/printHistory.html`,
			onFinish:function(){
				let number = $("#number").val();
				let url = `${webRoot}/purchasing/purchasing!listPrintLog.do`;
				var target = this.find("#datagrid");
				let grid = new BootstrapGrid({
					target:target,
					url:url,
					paramMap:{
						number:number
					},
					cols:[{
						label:'订单号',
						field:'number'
					},{
						label:'操作人',
						field:'operator'
					},{
						label:'操作时间',
						field:'created_time'
					}]
				});
				grid.init();
			}
		});
		$("#showPrintHistoryDialog").modal('show');
	});
		
	$("#returnBut").click(function(){
		if (confirm("确实要返回吗？")) {
			
			var id = $("#id").val();
			
			util.ajax({
				url:webRoot+'/purchasing/purchasing.do',
				data:{
					method:'returnPurchasing',
					id:id
				},
				success:function(data){
					if(data.success){
						alert("操作成功");
						window.opener.location.reload();
						window.close();
					}else{
						alert("操作失败！"+data.msg);
					}		
				}
			});
		 }
	});
		
		var number = $("#supplierNumber").val();
		$.ajax({
			url:webRoot+'/purchasing/supplier.dispatch',
			type:'post',
			data:{
				method:'getPf',
				number:number
			},
			success:function(data){
				
				var startImg = $("<img>",{
					src:webRoot+"/images/stars.png"
				});
				
				if(data.rfq){
					$("#pfArea").append("询价单(RFQs)回应速度:"+data.rfq);
					for(var i=0;i<Math.floor(data.rfq);i++){
						$("#pfArea").append(startImg.clone());
						
					}
					$("#pfArea").append("<br>");
				}
				
				if(data.bj){
					$("#pfArea").append("发布的元器件的报价能力:"+data.bj);
					for(var i=0;i<Math.floor(data.bj);i++){
						$("#pfArea").append(startImg.clone());
					}
					$("#pfArea").append("<br>");
				}

				
				if(data.ch){
					$("#pfArea").append("订购的元器件的出货能力:"+data.ch);
					for(var i=0;i<Math.floor(data.ch);i++){
						$("#pfArea").append(startImg.clone());
					}
					$("#pfArea").append("<br>");
				}
				
				if(data.fh){
					$("#pfArea").append("交货的元器件符合订单要求:"+data.fh);
					for(var i=0;i<Math.floor(data.fh);i++){
						$("#pfArea").append(startImg.clone());
					}
					$("#pfArea").append("<br>");
				}
				
				if(data.th){
					$("#pfArea").append("退货要求的处理:"+data.th);
					for(var i=0;i<Math.floor(data.th);i++){
						$("#pfArea").append(startImg.clone());
					}
					$("#pfArea").append("<br>");
				}
				
				
			}
		});
	});




function toclick(){
	if (confirm("请确认供应商是否已确认合同并回传？")) {
		var id = $("#id").val();
		$.ajax({
			url:webRoot+'/purchasing/purchasing!confirmContact.do',
			data:{
				id:id
			},
			success:function(data){
				if(data.success){
					alert("供应商确认成功");
					if(window.opener){
						window.opener.location.reload();
					}
					window.location.reload();
				}else{
					alert(data.msg);
				}
				
			}
		});
	}
	return;
}


