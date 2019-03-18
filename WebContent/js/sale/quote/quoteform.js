function isValid() {
	var quotedatetime = $("[name=quotedatetime]").val();
	var coname = $("[name=coname]").val();
	if (quotedatetime == "") {
		alert("请输入填写报价日期!");
		$("[name=quotedatetime]").focus();
		return false;
	} else if (coname == "") {
		alert("请填写客户名称!");
		form1.coname.focus();
		return false;
	} else {
		return true;
	}
}

function MM_openBrWindow(theURL, winName, features) { // v2.0
	window.open(theURL, winName, features);
}

$(function(){
	
	OACommonSelects.fillTaxTypeSelect({
		sel:$("[name=q_tr_date]")
	});
	
	$("#quoteform").buildform({
		actions:{
			chooseCust:function(){
				OACommonUse.openClientChooseDialog(function(data){
					console.log(data);
					$("[name=coname]").val(data.coname);
					$("[name=cofax]").val(data.cofax);
					$("[name=senddate]").val(data.co_number);
					$("[name=coaddr]").val(data.coaddr);
					$("[name=cotel]").val(data.cotel);
					$("[name=cofax]").val(data.cofax);
					$("[name=linkman]").val(data.cojsfs);
					$("[name=linktel]").val(data.cotel);
					$("[name=cofax]").val(data.cofax);
					$("[name=linkwap]").val(data.linkwap);
					$("[name=linkemail]").val(data.codzyj);
					$("[name=mode]").val(data.cozczb);
				});
			},
			confirm:function(){
				var params = this.getParamMap();
				if (!isValid()) {
					return false;
				}
				$.ajax({
					url : webRoot + "/sale/quote/quote!add.do",
					data : params,
					type: 'post',
					success : function(data) {
						if (data.success) {
							alert("操作成功！");
							if(window.opener){
								window.opener.location.reload();
							}
							window.location.href = "view.mvc?id=" + data.quoteId;
						} else {
							alert("操作失败");
						}
					},
					error : function(e) {
						alert("获取后台服务失败！");
					}
				});
			}
		}
	})
	
	getUserList("man",null,"name","name");
	getUserList("spman",null,"name","name");
	OACommonSelects.fillCurrentSelect({
		sel:$("[name=money]")
	});
});
