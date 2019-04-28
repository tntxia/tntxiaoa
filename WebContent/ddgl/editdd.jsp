<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="einfodb2" scope="page" class="infocrmdb.infocrmdb" />
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="infocrmdb.DealString" %>
<%@ page contentType="text/html;charset=GB2312"%>
<% 
java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String currentDate=simple.format(new java.util.Date());
java.text.SimpleDateFormat simple1=new java.text.SimpleDateFormat("yyMMdd");
String number=simple1.format(new java.util.Date());
String m_year; 
String m_month; 
String m_day;
String m;
String m_time;
m=request.getParameter("m"); 
m_month=request.getParameter("month"); 
m_year =request.getParameter("year"); 
m_day =request.getParameter("date"); 
m_time =request.getParameter("time"); 
int year,month,day,hour,minute,second;
String time1,time2;
GregorianCalendar calendar;
calendar=new GregorianCalendar();
year=calendar.get(Calendar.YEAR);
month=calendar.get(Calendar.MONTH)+1;
day=calendar.get(Calendar.DAY_OF_MONTH);
hour=calendar.get(Calendar.HOUR_OF_DAY);
minute=calendar.get(Calendar.MINUTE);
second=calendar.get(Calendar.SECOND);
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
		
if(m!=null)
{
if(m.equals("m1"))
{
	if(m_year==null&&m_month==null&&m_day==null)
	{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
	}
	else
	{
		time1=m_year+"-"+m_month+"-"+m_day;
		time2=m_time;
	}
}	
else if(m.equals("m2"))
{
	if(m_year==null&&m_month==null&&m_day==null)
	{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
	}
	else
	{
		time1=m_time;
		time2=m_year+"-"+m_month+"-"+m_day;
	}
}
else
{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
}
}
String id1=request.getParameter("id");
String sql="select  * from procure where id='"+id1+"'";
ResultSet rs=einfodb.executeQuery(sql);
if(!rs.next())
  {
    out.println("not have record");
    return;}

String dd_number = rs.getString(2).trim();

String sale_number = rs.getString(4).trim();

String subck=rs.getString("subck");

String co_number = rs.getString("co_number");

String coname = rs.getString("coname");

String senddate = rs.getString("senddate");


String  pay_if =  rs.getString("pay_if");
String  pay_je =  rs.getString("pay_je");
String money1  =  rs.getString("money").trim();
String  tbyq   =  rs.getString("tbyq"); 
String remarks =  rs.getString("remarks");  

String ponum = rs.getString("ponum");
String lxr = rs.getString("lxr");

String receiver = rs.getString("receiver");
String receiver_tel = rs.getString("receiver_tel");
String receiver_addr = rs.getString("receiver_addr");
String freight = rs.getString("freight");
String express_company = rs.getString("express_company");
String acct = rs.getString("acct");
String service_type = rs.getString("service_type");
String pay_type = rs.getString("pay_type");
String jydd=rs.getString("jydd");
String coaddr = rs.getString("coaddr");
String cotel = rs.getString("cotel");
String cofax = rs.getString("cofax");
String rate = rs.getString("rate");
String yfmoney = rs.getString("yfmoney").trim();
int self_carry = rs.getInt("self_carry");

     String basePath = request.getContextPath();
%>
<html>
<head>
<title>采购</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script type="text/javascript" src="/static/lib/jquery/jquery.js"></script>
<script language="JavaScript" src="<%=basePath %>/js/common.js" ></script>
<script language="JavaScript" type="text/JavaScript">

$(function(){
	OACommonSelects.fillTaxTypeSelect({
		sel:$("[name=rate]")
	});
});

function pic2_onclick() {
     window.open("waitcal2.jsp?m=m2&time=<%=time2%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}

function setPf(pf){
	var pfDiv = document.getElementById("pfDiv");
	var pfArr = pf.split(",");
	pfDiv.innerHTML = "<table style='font-size:12px;width:40%'><tr><td>询价单(RFQs)回应速度:</td><td>"+pfArr[0]+"</td></tr>"
	+"<tr><td>发布的元器件的报价能力:</td><td>"+pfArr[1]+"</td></tr>"
	+"<tr><td>订购的元器件的出货能力:</td><td>"+pfArr[2]+"</td></tr>"
	+"<tr><td>交货的元器件符合订单要求:</td><td>"+pfArr[3]+"</td></tr>"
	+"<tr><td>退货要求的处理:</td><td>"+pfArr[4]+"</td></tr></table>"
}
</script>

<script language="JavaScript" type="text/JavaScript">
<!--
function isValid(){
	ymd2=form1.pay_if.value.split("-");
    month2=ymd2[1]-1
    var Date2 = new Date(ymd2[0],month2,ymd2[2]); 
   if(form1.coname.value==""){
        alert("请调用供应商!");
        form1.coname.focus();
	return false;
    }
	
	/*else if (form1.pay_if.value==""){
        alert("请填写日期!");
        form1.pay_if.focus();
	
	return false;
    }
  else  if (Date2.getMonth()+1!=ymd2[1]||Date2.getDate()!=ymd2[2]||Date2.getFullYear()!=ymd2[0]||ymd2[0].length!=4)
    {
       alert("非法日期,请依【YYYY-MM-DD】格式输入");
        form1.pay_if.focus();
		
        return false;          
    }*/
    else {
        return true;
    }
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

// 隐藏或显示快递选项
function selfGet(){
	var self_carry = document.getElementById("self_carry").checked;
	
	if(self_carry){
		document.getElementById("express_options").style.display = "none";
		selectOption("jydd","深圳交货");
	}else{
		document.getElementById("express_options").style.display = "";
	}
	
	
	
}

//-->
</script>

</head>


<body bgcolor="#ffffff" text="#000000" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1" method="post" action="do_editdd.jsp" onSubmit="return isValid();"><br>  <table height=79 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
  <tr bgcolor="#ffffff"> 
      <td width="14%" height="7" bgcolor="#d3d8eb"> 
        <div align="left"><font size="2" color="#000000">采购管理</font>&nbsp;</div>
        <div align="right"> </div>      </td>
      <td height="7" colspan="3" bgcolor="#d3d8eb"> 
      <div align="right"> 
            <input type="hidden" name="id" value="<%=id1%>">
          <input type="submit" name="Submit" value="确定" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF">
          <input type="reset" name="Submit5" value="取消" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF" onClick="history.back()">
        </div>      </td>
    </tr>
    <tr>
      <td height="2" bgcolor="#e8ebf5">合同编号</td>
      <td height="2" colspan="3" bgcolor="#FFFFFF"><font size="2">
        <input type="text" name="number" size="15" value="<%=dd_number%>">
      </font></td>
    </tr>
    <tr> 
      <td width="14%" height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">销售合同号</font>&nbsp;</td>
      <td height="2" bgcolor="#FFFFFF"><font size="2"> 
        <input type="text" name="sub" size="15" value="<%=sale_number%>">
        </font>&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000080">仓　　　库</font>&nbsp;</td>
      <td height="2" bgcolor="#FFFFFF">
        <select name="subck">
          <%
  String strSQL ="select cpro from profl  order  by id asc";
 String restrain_name = (String) session.getValue("restrain_name");
int tmpigp=0;
String gp_gp="";
String  strSQLh = "select * from restrain_gp where restrain_name='"+restrain_name+"'";
java.sql.ResultSet sqlRsth = einfodb.executeQuery(strSQLh);
while(sqlRsth.next()){ 
 String pro_ck=sqlRsth.getString("pro_ck").trim();
 String pro_zt=sqlRsth.getString("pro_view").trim();
 out.println(pro_zt);
 if(pro_zt.equals("有")){
 gp_gp=gp_gp+" or cpro='"+pro_ck +"'";
 }
 tmpigp++;}
  
 strSQL = "select cpro from profl  where cpro='' "+gp_gp+" order  by id asc";

  try
  {
    ResultSet rss=einfodb.executeQuery(strSQL);
    
    
	
    while(rss.next())
    {
      String proflname=rss.getString("cpro");
	  if (subck.equals(proflname))
      out.println("<option selected>"+proflname+"</option>");
	  else
	        out.println("<option >"+proflname+"</option>");
    }
    rss.close();
   }catch(Exception e){}
   %>
        </select></td>
    </tr>
    <tr> 
      <td width="14%" height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">供应商编号</font>&nbsp;</td>
      <td width="32%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="co_number1" type="text" id="co_number2" value="<%=co_number%>" size="15" maxlength="15">
          </font> 
          <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
      <td width="16%" height="2" bgcolor="#e8ebf5"><div align="left"><font size="2" color="#000000">供应商名称</font>&nbsp;</div></td>
      <td width="38%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="coname" type="text" id="coname" value="<%=coname%>">
		  <font color="#000000" size="2"><a href="#" onClick="MM_openBrWindow('search.jsp','','scrollbars=yes,width=940,height=520')"><img border="0" src="../images/search.gif" width="27" height="12"></a></font>
          </font> <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%" height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">电话</font>&nbsp;</td>
      <td width="32%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="tel" type="text" id="tel" value="<%=cotel%>" size="15" maxlength="15">
          </font> 
          <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
      <td width="16%" height="2" bgcolor="#e8ebf5"><div align="left"><font size="2" color="#000000">传真</font>&nbsp;</div></td>
      <td width="38%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="fax" type="text" id="fax" value="<%=cofax%>">
		  
          </font> <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
    </tr>
    <tr>
    <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">公司地址</font>&nbsp;</td>
      <td height="16" colspan="3"><font size="2"> 
        <input size="50" type="text" name="supplier_addr" value="<%=coaddr %>">
        </font>&nbsp;</td>
    </tr>
    <tr> 
    	<td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">所得评分</font>&nbsp;</td>
      	<td colspan="3" height="16"><font size="2"> <div id="pfDiv" style="width:100%"></div>
        </font>&nbsp;</td>
    </tr>
    <tr>
    <td colspan="4">
     货物自提：<input type="checkbox" value="self_carry" 
     name="self_carry" id="self_carry" onClick="selfGet();"
     <%
     
     if(self_carry==1){
    	out.print("checked");
     }
     %>
     >
    <hr>
    
    <fieldset id="express_options" style="display:<%if(self_carry==1){out.print("none");} %>">
    <legend>快递收货选项</legend>
    <table height=79 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr> 
      <td width="14%" height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">收件人</font>&nbsp;</td>
      <td width="32%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="receiver" type="text" id="receiver" value="<%=receiver%>" size="15" maxlength="15">
          </font> 
          <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
      <td width="16%" height="2" bgcolor="#e8ebf5"><div align="left"><font size="2" color="#000000">收件人电话</font>&nbsp;</div></td>
      <td width="38%" height="2"> 
        <div align="left"><font size="2"> 
          <input name="receiver_tel" type="text" id="receiver_tel" value="<%=receiver_tel%>">
		  
          </font> <font size="2">&nbsp; &nbsp; </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
    	<td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">收件人地址</font>&nbsp;</td>
      	<td colspan="3" height="16"><font size="2"> 
		<input size="50" type="text" name="receiver_addr" value="<%=receiver_addr %>">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">运费负担</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <select id="freight" name="freight">
       	  <option value="">请选择</option>
          <option value="卖家支付">卖家支付</option>
		  <option value="买家支付">买家支付</option>
          <option value="Freight Collect">Freight Collect</option>
          <option value="Freigh Prepaid">Freigh Prepaid</option>
        </select>
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">快递公司</font>&nbsp;</td>
      <td height="16"><font size="2"> 
      	<input type="text" name="express_company" value="<%=express_company %>">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">快递帐号</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <input type="text" name="acct" value="<%=acct %>">
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">服务类型</font>&nbsp;</td>
      <td height="16"><font size="2"> 
      <input type="text" name="service_type" value="<%=service_type %>">
        </font>&nbsp;</td>
    </tr>
    
    <tr> 
    	
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">交易地点</font></td>
      
	  <td height="16"><font size="2">
<select name="jydd">
       	  <option value="<%=jydd%>"  selected="selected"><%=jydd%></option>
          <option value="深圳交货">深圳交货</option>
		  <option value="香港交货">香港交货</option>
</select>
	  
	  </font></td>
	  
	  <td colspan="2">&nbsp;</td>
	  
    </tr>
    
    </table>
    </fieldset>
    
    
    </td>
    
    </tr>
    
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">付款方式</font>&nbsp;</td>
      <td height="16"><font size="2"> 
	  
       <select name="pay_type">
	   		<option value="">请选择</option>
          <%
			try
            {
            ResultSet pwrs=einfodb.executeQuery("select * from payway_cg");
            while(pwrs.next())
           {
            String paywayname=pwrs.getString(2);
            if(paywayname.equals(pay_type)){
            	out.println("<option selected>"+paywayname+"</option>");
            }else{
            	out.println("<option >"+paywayname+"</option>");
            }
            
             }
            pwrs.close();
             }catch(Exception e){}
             %>
      	</select>
		
      </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">含税率</font>&nbsp;</td>
      <td>
      	<select name="rate" >
      		<option><%=rate %></option>
        </select>
      </td>
    </tr>
    <tr> 
      <td width="14%" bgcolor="#e8ebf5"><font size="2" color="#000000">采购方向</font>&nbsp;</td>
      <td height="4"> 
        <div align="left"><font size="2"> 
          <select name="senddate">
            <option><font size="2"><%=senddate%></font></option>
            <option><font size="2">国内采购</font></option>
            <option><font size="2">国际采购</font></option>
          </select>
          </font>&nbsp;</div>      </td>
      <td height="4" bgcolor="#e8ebf5"><div align="left"><font size="2">签 订 地</font></div></td>
	  
      <td height="4"><input type="text" name="ponum" size="12" value="<%=ponum%>">&nbsp;</td>
    </tr>
	
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2" color="#000000">交货日期</font>&nbsp;</td>
      <td height="2"><font size="2"> 
        <input name="pay_if" type="text" id="pay_if" value="<%=pay_if%>" size="12">
        <a href="javascript:pic2_onclick()"><img border="0" src="../images/calendar.JPG" id="pic2" width="20" height="16"></a></font>&nbsp;</td>
      <td bgcolor="#e8ebf5" height="2"><font size="2">联 系 人</font></td>
      <td height="2"><input type="text" name="lxr" size="12" value="<%=lxr%>"></td>
    </tr>
    <tr> 
      <td width="14%" height="2" bgcolor="#e8ebf5"><font size="2" color="#000000"> 运费</font>&nbsp;</td>
      <td height="2"> 
        <div align="left"><font size="2"> 
          <input name="pay_je" type="text" value="<%=pay_je%>" size="8">
		  
		   <select name="yfmoney" >
          <%
  				 try
            {
            ResultSet hlrs2=einfodb2.executeQuery("select * from hltable order by id asc");
            while(hlrs2.next())
           {
            String departname2=hlrs2.getString(2);
            if(yfmoney.equals(departname2))
              out.println("<option  selected>"+departname2+"</option>");
            else            
              out.println("<option >"+departname2+"</option>");
             }
             hlrs2.close();
             }catch(Exception e){}
        %>
        </select>
		  
		  
		  
		  
		  
		  
          </font>&nbsp;</div>      </td>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">货币</font>&nbsp;</td>
      <td height="2"><font size="2" color="#000000"> 
        <select name="money" >
          <%
  				 try
            {
            ResultSet hlrs=einfodb.executeQuery("select * from hltable order by id asc");
            while(hlrs.next())
           {
            String departname=hlrs.getString(2);
            if(money1.equals(departname))
              out.println("<option  selected>"+departname+"</option>");
            else            
              out.println("<option >"+departname+"</option>");
             }
             hlrs.close();
             }catch(Exception e){}
        %>
        </select>
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="14%" height="38" bgcolor="#e8ebf5"><font size="2" color="#000000">条款</font>&nbsp;</td>
      <td height="38" colspan="3"> 
        <div align="left"><font size="2"> 
          <textarea name="tbyq" cols="70" rows="20"><%=DealString.unHtmlEncode(tbyq)%></textarea>
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%" height="18" bgcolor="#e8ebf5"><font size="2" color="#000000">备　　注</font>&nbsp;</td>
      <td height="18" colspan="3"> 
        <div align="left"><font size="2"> 
          <textarea name="remarks" cols="70" rows="3"><%=remarks%></textarea>
          </font>&nbsp;</div>      </td>
    </tr>
  </table>
</form>  
</body>
</html>
