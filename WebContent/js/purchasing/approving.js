$(function(){
	
});

function ForDight(Dight, How) {
	Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
	return Dight;
}

function doAuthBack(data) {

	var result = data;
	var respStr = "";

	for ( var i = 0; i < result.pros.length; i++) {
		if (result.pros[i].profit < 0.1) {
			respStr += "型号：" + result.pros[i].epro + " 利润："
					+ ForDight((result.pros[i].profit), 4) + "<br>";
		}
	}
	if (respStr != "") {

		$
				.blockUI({
					message : "待审核的订单中含有小于10%利润的产品，分别为：<br>"
							+ respStr
							+ "<br><br>是否确认要通过单核？<br>"
							+ "<input type='button' value='通过审核' onclick='finishAuth()'> "
							+ "<input type='button' value='取消' onclick='cancelAuth()'> "
				});

	} else {
		finishAuth();
	}

}

function finishAuth() {

	var params = $("#formArea").getParamMap(true);
	
	util.ajax({
		url : webRoot + '/purchasing/purchasing!audit.do',
		type : 'post',
		data : params,
		success : function(data) {
			if (data.success) {
				alert("操作成功！");
				window.opener.location.reload();
				window.close();
			} else {
				alert("操作失败");

			}
		}
	});

}

function cancelAuth() {
	$.unblockUI();
}

function doAuth() {

	var p_id = $("[name=id]").value;

	var spqk = $("#l_spqk").val();
	// 如果审批通过，先查询是否有利润小于10%的订单产品
	if (spqk == "审批通过") {
		var param = {
			id : p_id
		};
		$.ajax({
			type : 'post',
			url : webRoot + '/purchasing/purchasing!getProfitInfo.do',
			data : param,
			success : doAuthBack
		});

	} else {
		finishAuth();
	}

}

function openRproview(id) {
	window.open(
					webRoot + '/purchasing/pro-rview.jsp?id=' + id,
					'wnw',
					'height=260, width=600, top=50, left=50, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');

}
