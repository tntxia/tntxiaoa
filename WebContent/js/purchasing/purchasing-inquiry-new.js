$(function(){
	
	
	$("[name=datetime]").datepick({
		showNowDate:true
	});
	
	OACommonSelects.fillCurrentSelect({
		sel:$("[name=money]")
	});
	
	$("#supplierChooseBtn").click(function(){
		OACommonUse.openSupplierChooseDialog(function(supplier){
			$("[name=coname]").val(supplier.coname);
			$("[name=co_number]").val(supplier.co_number);
			$("[name=sup_tel]").val(supplier.cotel);
			$("[name=sup_fax]").val(supplier.cofax);
		});
	});
	
});

function addInquiry(){
	$.ajax({
		url:webRoot+"/purchasing/purchasing!addInquiry.do",
		type:'post',
		data:$("#mainContent").getParamMap()
	}).done(function(data){
		if(data.success){
			alert("操作成功");
			if(window.opener){
				window.location.reload();
			}
			window.close();
		}else{
			alert("操作失败");
		}
	}).fail(function(e){
		console.error(e);
	})
}