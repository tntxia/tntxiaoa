$(function(){
	$("#checkInBtn").click(function(){
		if (confirm("确实要入库吗？")) {
			let id = $("[name=id]").val();
			$.ajax({
				url: webRoot+'/warehouse/warehouse!doInSample.do',
				type: 'post',
				data: {
					id: id
				}
			}).done(function(data){
				if(data.success) {
					alert("操作成功");
				}else {
					alert("操作失败:"+data.msg);
				}
			}).fail(function(e){
				debugger
				alert("操作异常");
			});
	 		// window.open('do_rout.jsp?id=<%=id1%>&coname=<%=coname%>&fynumber=<%=ddnumber%>&co_number=<%=dd6%>','_self', 'height=500, width=710, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no')
	 	}
	});
})