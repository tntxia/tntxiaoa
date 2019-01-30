
$(function() {

	$("[name=datetime]").datepick({
		showNowDate:true
	});
	
	$("#searchBtn").click(function(){
		OACommonUse.openSupplierChooseDialog(function(data){
			$("[name=coname]").val(data.coname);
			$("[name=co_number]").val(data.co_number);
		})
	});
	
	$("#submitBtn").click(function(){
		var params = $("#form").getParamMap();
		if(!params.coname){
			alert("请先输入供应商");
			return;
		}
		$.ajax({
			url:webRoot+'/purchasing/purchasing!createRefund.do',
			type:'post',
			data:params
		}).done(function(data){
			if(data.success){
				if(window.opener){
					window.opener.location.reload();
				}
				window.close();
				
			}else{
				alert("操作失败:"+data.msg);
			}
		}).fail(function(){
			alert("操作异常");
		})
	});

});
