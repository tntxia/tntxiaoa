function MM_openBrWindow(theURL,winName,features) { //v2.0
	window.open(theURL,winName,features);
}

function isValid(params){
	
	if(!params.coname){
		alert("请先填写客户！");
		return;
	}
	
    var i,j,strTemp;
    strTemp="0123456789";
	var num1=params.source;
	ymd2=params.datetime.split("-");
	ymd3=params.send_date.split("-");
    month2=ymd2[1]-1;
	month3=ymd3[1]-1;
    var Date2 = new Date(ymd2[0],month2,ymd2[2]); 
	var Date3 = new Date(ymd3[0],month3,ymd3[2]); 
	if(params.coname1==""){
        alert("请选择客户!");
        return false;
    }
	else if (params.source==""){
        alert("请您输入付款天数!");
        $("[name=source]").focus();
        return false;
    }
	else if (params.send_date==""){
        alert("请填写发货日期!");
        $("[name=send_date]").focus();
        return false;
    }
	else if (params.yf_types==""){
        alert("请您选择运费负担方!");
        $("[name=yf_types]").focus();
        return false;
    }
	else if (params.datetime==""){
        alert("请填写日期!");
        $("[name=datetime]").focus();
        
        return false;
    }
  else  if (Date2.getMonth()+1!=ymd2[1]||Date2.getDate()!=ymd2[2]||Date2.getFullYear()!=ymd2[0]||ymd2[0].length!=4)
    {
       alert("非法日期,请依【YYYY-MM-DD】格式输入");
       $("[name=datetime]").focus();
      
        return false;          
    }
	else if (params.send_date==""){
        alert("请填写日期!");
        $("[name=send_date]").focus();
	return false;
    }
  else  if (Date3.getMonth()+1!=ymd3[1]||Date3.getDate()!=ymd3[2]||Date3.getFullYear()!=ymd3[0]||ymd3[0].length!=4)
    {
       alert("非法日期,请依【YYYY-MM-DD】格式输入");
       $("[name=send_date]").focus();
        return false;          
    }
   else {
         for (i=0;i<num1.length;i++)
        {
        j=strTemp.indexOf(num1.charAt(i));    
        if (j==-1)
        {
            alert("请填写数字格式!");
            $("[name=source]").focus();
        return false;
        }
    }
    return true;      
    }
}


$(function(){
	
	$("#newForm").buildform({
		actions:{
			sub:function(){
				
				var params = this.getParamMap();
				if(!isValid(params)){
					return;
				}
				params.tbyq = tkeditor.getData();
				
				util.ajax({
					url:webRoot+"/sale/sale!add.do",
					data:params,
					success:function(data){
						if(data.success){
							alert("增加成功");
							if(window.opener)
								window.opener.location.reload();
							window.close();
						}else{
							alert("操作失败！"+data.msg);
						}
					}
				});
			}
		}
	});
	
	OACommonSelects.fillCurrentSelect({
		sel:$("[name=money]")
	});
	
	OACommonSelects.fillTaxTypeSelect({
		sel:$("[name=item]")
	});
	
	$("#conameSearchBtn").click(function(){
		
		OACommonUse.openClientChooseDialog(function(data){
			$("[name=coname]").val(data.coname);
			$("[name=coname1]").val(data.coname);
			$("[name=senddate]").val(data.co_number);
			$("[name=tr_addr]").val(data.coaddr);
			$("[name=tr_man]").val(data.cojsfs);
			$("[name=tr_tel]").val(data.cotel);
			$("[name=tr]").val(data.cokhyh);
			$("[name=mode]").val(data.cozczb);
		});
		
	});
	
	
	var tkeditor = CKEDITOR.replace('tbyq');
	
	
	
});

