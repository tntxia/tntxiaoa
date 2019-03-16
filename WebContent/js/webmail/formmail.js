
$(function() {
	$.ajax({
		url: '../mail!getToGetCount.do'
	}).done(function(data) {
		if(data.success) {
			$("#getMailBtn").html("收取邮件:共"+data.count+"封新邮件");
		} else {
			alert("操作失败");
		}
	}).fail(function(){
		alert("操作异常");
	});
});

function pic1_onclick() {
     window.open("../checkwork/calendar_main.jsp?m=m1&time=",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
     window.open("../checkwork/calendar_main.jsp?m=m2&time=",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}

function filter(){
	if(form1.username.value=="allusers"&&form1.title.value==""&&form1.content==""&&form1.startDate==""&&form1.endDate.value==""){
		alert("请先填写过滤条件");
		return false;
	}
	window.location = "formmail.jsp?mail_man="+form1.username.value+"&&title="+form1.title.value+"&&content="+form1.content.value+"&&startdate="+form1.startdate.value+"&&enddate="+form1.enddate.value;
}

function goPage(pageNum){
	searchForm.mail_man.value = form1.username.value;
	searchForm.title.value = form1.title.value;
	searchForm.content.value = form1.content.value;
	searchForm.startdate.value = form1.startdate.value;
	searchForm.enddate.value = form1.enddate.value;
	searchForm.page.value = pageNum;
	searchForm.submit();
}

/**   
    判断输入框中输入的日期格式为yyyy-mm-dd和正确的日期   
  */   
  function   IsDate(sm,mystring)   {   
      var   reg   =   /^(\d{4})-(\d{2})-(\d{2})$/;   
      var   str   =   mystring;   
      var   arr   =   reg.exec(str);   
      if   (str=="")   return   true;   
      if   (!reg.test(str)&&RegExp.$2<=12&&RegExp.$3<=31){   
        alert("请保证"+sm+"中输入的日期格式为yyyy-mm-dd或正确的日期!");   
        return   false;   
        }   
        return   true;   
    } 
    
function doHandlingMail(){
	var idsValue = "";
	var inputs = document.getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].type=="checkbox" && inputs[i].name!="s_ally" && inputs[i].checked){
			idsValue+=","+inputs[i].value;
		}
	}
	handlingForm.ids.value = idsValue;
	handlingForm.submit();
}

function getMail() {
	$.ajax({
		url: '../mail!getMail.do'
	}).done(function(data) {
		if(data.success) {
			window.location.reload();
		} else {
			alert("操作失败："+data.msg);
		}
	}).fail(function(){
		alert("操作异常");
	});
}