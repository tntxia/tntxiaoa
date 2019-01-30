$(function(){
	$("#submitBtn").click(function(){

		var id = $("#id").val();
		var status = $("#id").val();
		var status = $("[name=l_spqk]").val();
		var remark = $("[name=l_spyj]").val();
		
		$.ajax({
			url:webRoot+"/purchasing/purchasing!auditSecond.do",
			type:'post',
			data:{
				id:id,
				status:status,
				remark:remark
			}
		}).done(function(data){
			if(data.success){
				alert("操作成功")
			}else{
				alert("操作失败")
			}
		})
	});
});
