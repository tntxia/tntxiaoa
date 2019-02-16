$(function(){
	$("#updateSubBtn").click(function(){
		var id = $("#id").val();
		var sub = $("[name=sub]").val();
		$.ajax({
			url:webRoot+'/sale/sale!updateSub.do',
			data:{
				id:id,
				sub:sub
			},
			success:function(data){
				if(data.success){
					alert("操作成功");
				}else{
					alert("操作失败");
				}
			},
			error:function(e){
				alert("操作异常");
			}
		});
		
	});
});