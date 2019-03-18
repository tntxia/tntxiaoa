
$(function(){
	
	var tkeditor = CKEDITOR.replace('tbyq');
	
	$("#submitBtn").click(function(){
		
		var params = $("body").getParamMap(true);
		
		var tbyq = tkeditor.getData();
		
		console.log(tbyq);
		
		params.tbyq = escape(tbyq);
		
		$.ajax({
			url:webRoot+'/sale/sale!updateOrder.do',
			data:params,
			success:function(data){
				if(data.success){
					alert("修改成功");
					if(window.opener && window.opener.location)
						window.opener.location.reload();
					window.close();
				}else{
					alert("修改失败");
				}
			},
			error:function(e){
				alert("后台请求失败");
			}
		});
	});
	
	OACommonSelects.fillTaxTypeSelect({
		sel:$("[name=item]")
	});
	
});

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function isValid(){
    var i,j,strTemp;
    strTemp="0123456789";
	var num1=form1.source.value;
	ymd2=form1.datetime.value.split("-");
	ymd3=form1.send_date.value.split("-");
    month2=ymd2[1]-1;
	month3=ymd3[1]-1;
    var Date2 = new Date(ymd2[0],month2,ymd2[2]); 
	var Date3 = new Date(ymd3[0],month3,ymd3[2]); 
   if(form1.coname1.value==""){
        alert("请选择客户!");
	return false;
    }
	else if (form1.source.value==""){
        alert("请您输入付款天数!");
        form1.source.focus();
	return false;
    }
	else if (form1.send_date.value==""){
        alert("请填写发货日期!");
        form1.send_date.focus();
	return false;
    }
	else if (form1.yf_types.value==""){
        alert("请您选择运费负担方!");
        form1.yf_types.focus();
	return false;
    }
	else if (form1.datetime.value==""){
        alert("请填写日期!");
        form1.datetime.focus();
	return false;
    }
  else  if (Date2.getMonth()+1!=ymd2[1]||Date2.getDate()!=ymd2[2]||Date2.getFullYear()!=ymd2[0]||ymd2[0].length!=4)
    {
       alert("非法日期,请依【YYYY-MM-DD】格式输入");
        form1.datetime.focus();
        return false;          
    }
	else if (form1.send_date.value==""){
        alert("请填写日期!");
        form1.send_date.focus();
	return false;
    }
  else  if (Date3.getMonth()+1!=ymd3[1]||Date3.getDate()!=ymd3[2]||Date3.getFullYear()!=ymd3[0]||ymd3[0].length!=4)
    {
       alert("非法日期,请依【YYYY-MM-DD】格式输入");
        form1.send_date.focus();
        return false;          
    }
   else {
         for (i=0;i<num1.length;i++)
        {
        j=strTemp.indexOf(num1.charAt(i));    
        if (j==-1)
        {
            alert("请填写数字格式!");
        form1.source.focus();
        return false;
        }
    }
    return true;      
    }
}

function pic1_onclick() {
     window.open("waitcal2.jsp?m=m1&time=<%=time1%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
     window.open("waitcal2.jsp?m=m2&time=<%=time2%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}