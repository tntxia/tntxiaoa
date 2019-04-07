function isValid() {

	if ($("[name=pay_type]").val() == "") {
		alert("付款方式不能为空！");
		return false;
	}

	ymd2 = form1.datetime.value.split("-");
	month2 = ymd2[1] - 1;
	month3 = ymd2[1] - 1;
	var Date2 = new Date(ymd2[0], month2, ymd2[2]);

	if (form1.coname.value == "") {
		alert("请填写供应商信息!");
		form1.coname.focus();
		return false;
	} else if (form1.datetime.value == "") {
		alert("请填写日期!");
		form1.datetime.focus();
		return false;
	} else if (Date2.getMonth() + 1 != ymd2[1] || Date2.getDate() != ymd2[2]
			|| Date2.getFullYear() != ymd2[0] || ymd2[0].length != 4) {
		alert("非法日期,请依【YYYY-MM-DD】格式输入");
		form1.datetime.focus();

		return false;
	} else {
		return true;
	}
}

function MM_openBrWindow(theURL, winName, features) { // v2.0
	window.open(theURL, winName, features);
}

// 
function MM_openSupplierContactWindow(theURL, winName, features) {
	var co_number = document.form1.co_number.value;
	if (!co_number || co_number == "") {
		alert("请先选择供应商！！");
		return;
	}
	window.open(theURL + "?co_number=" + co_number, winName, features);
}

var purchasingPath = webRoot + "/purchasing/purchasing.dispatch";

function setPf(pf) {
	var pfDiv = document.getElementById("pfDiv");
	var pfArr = pf.split(",");
	pfDiv.innerHTML = "<table style='font-size:12px;width:100%'><tr><td>询价单(RFQs)回应速度:</td><td>"
			+ pfArr[0]
			+ "</td></tr>"
			+ "<tr><td>发布的元器件的报价能力:</td><td>"
			+ pfArr[1]
			+ "</td></tr>"
			+ "<tr><td>订购的元器件的出货能力:</td><td>"
			+ pfArr[2]
			+ "</td></tr>"
			+ "<tr><td>交货的元器件符合订单要求:</td><td>"
			+ pfArr[3]
			+ "</td></tr>"
			+ "<tr><td>退货要求的处理:</td><td>"
			+ pfArr[4]
			+ "</td></tr></table>";
}

// 隐藏或显示快递选项
function selfGet() {
	var self_carry = document.getElementById("self_carry").checked;
	if (self_carry) {
		document.getElementById("express_options").style.display = "none";
		selectOption("jydd", "深圳交货");
	} else {
		document.getElementById("express_options").style.display = "";
	}

}

$(function() {
	
	let form = $("[name=form1]").buildform({
		actions:{
			chooseSupplier:function(){
				let form = this;
				OACommonUse.openSupplierChooseDialog(function(data){
					let supplierNumber = data.co_number;
					form.setValue("coname",data.coname);
					form.setValue("co_number1", supplierNumber);
					OACommonUse.openSupplierContactChooseDialog(supplierNumber,function(data){
						form.setValue("lxr",data.name);
					});
				});
			},
			chooseContact:function(){
				let form = this;
				var supplierNumber = form.getValue("co_number1");
				OACommonUse.openSupplierContactChooseDialog(supplierNumber,function(data){
					form.setValue("lxr",data.name);
				});
			}
		}
	});
	
	var tkeditor = CKEDITOR.replace('tbyq');
	
	OACommonSelects.fillTaxTypeSelect({
		sel:$("[name=rate]")
	});
	
	$("#nextBut").click(function() {
		sub();
	});
	
	function sub() {
		if (isValid()) {

			var param = $("[name=form1]").getParamMap();
			
			param.tbyq = tkeditor.getData();
			
			util.ajax({
				url : webRoot + "/purchasing/purchasing!create.do",
				data : param,
				success : function(data) {
					if (data.success) {
						alert("操作成功");
						if(window.opener)
							window.opener.location.reload();
						window.open(webRoot + "/purchasing/detail.mvc?id="
								+ data.ddid);
						window.close();
					} else {
						alert("操作失败！" + data.msg);
					}
				}
			});

		}
	}

});
