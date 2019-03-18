<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" /> 
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="infocrmdb.DealString" %>
<%@ page contentType="text/html;charset=GBK"%>
<%     
String basePath = request.getContextPath();
java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String currentDate=simple.format(new java.util.Date());
String edate=currentDate;
java.text.SimpleDateFormat simplec=new java.text.SimpleDateFormat("yyyy");
String cd=simplec.format(new java.util.Date());
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
String sql="select   subck,fy_number,sub,custno,coname,senddate,datetime,money,mode,source,send_date,trade,yj,item,tr,yf_types,yf_money,tr_addr,tr_man,tr_tel,tbyq,remarks from subscribe where id='"+id1+"'";
ResultSet rs=einfodb.executeQuery(sql);
if(!rs.next())
  {
    out.println("not have record");
    return;
  }
  String subck=rs.getString("subck");
%>
<html>
<head>
<title>合同</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<script type="text/javascript" src="/static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/lib/jxiaui/getParamMap.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ckeditor.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/sale/ddgl/editdd.js"></script>
<script type="text/javascript">
	var webRoot = '<%=basePath%>';
</script>
<style type="text/css">
<!--
.STYLE1 {color: #000000}
.STYLE3 {font-size: 12px;}
body {
	background-color: #ffffff;
}
-->
</style>
</head>

<body>
	<input name="fy_number" type="hidden" id="tr_man" value="<%=rs.getString("fy_number")%>">
  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr bgcolor="#d3d8eb"> 
      <td width="15%" height="2"  bgcolor="#ffffff"> 
        <div align="right"></div>      </td>
      <td height="2" colspan="3"  bgcolor="#ffffff"> 
        <div align="right"><a href="#" class="STYLE3" onClick="history.back()">取消</a></div>      </td>
    </tr>
    <tr> 
      <td width="15%" height="5"  bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">公司合同号</font></span></td>
      <td height="5" ><font size="2"> 
        <input name="sub" type="text" id="tr_man" value="<%=rs.getString("sub")%>">
        </font>&nbsp;</td>
      <td width="15%" height="5"  bgcolor="#e8ebf5"><font size="2">PO#</font></td>
      <td height="5" ><font size="2"> 
        <input name="custno" type="text" id="custno" value="<%=rs.getString("custno")%>">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" height="2"  bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">客户名称</font> 
        </span></td>
      <td height="2" colspan="3" ><font size="2"> 
        <input type="hidden" name="coname1" disabled value="<%String coname=rs.getString("coname").trim();out.print(coname);%>" size="68"><%=coname%></font><font size="2">
        <input name="senddate" type="hidden" value="<%=rs.getString("senddate").trim()%>" size="10" maxlength="10">
        <input name="coname" type="hidden" value="<%=coname%>" size="10" maxlength="10">
        </font></td>
    </tr>
    <tr> 
      <td width="15%" height="28"  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><font size="2">合同日期</font>&nbsp;</div>      </td>
      <td width="36%" height="28" > 
        <div align="left"><FONT SIZE="2"> 
          <input type="text" name="datetime" value="<%=rs.getDate("datetime")%>" size="10">
          </FONT> <font size="2"  ><a href="javascript:pic1_onclick()"><img border="0" src="../../images/calendar.jpg" id="pic1" width="20" height="16"></a></font>&nbsp;</div>      </td>
      <td width="15%" height="28"  bgcolor="#e8ebf5"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">货　　币</font>&nbsp;</div>      </td>
      <td width="40%" height="28" > 
        <div align="left"><FONT SIZE="2" COLOR="#000000"> 
          <select name="money" >
            <%
            
            String money1=rs.getString("money").trim();
  			try{
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
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="2"  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><FONT SIZE="2">付款方式</font>&nbsp;</div>      </td>
      <td height="2" > 
        <div align="left"><FONT SIZE="2"> </font><font size="2" color="#000080">
         
            <SELECT NAME="mode" ID="mode" >
			<option ><%=rs.getString("mode")%></option>
            <%
			try
            {
            ResultSet hlrs=einfodb.executeQuery("select * from payment_fs");
            while(hlrs.next())
           {
            String currname=hlrs.getString(2);
            out.println("<option >"+currname+"</option>");
             }
             hlrs.close();
             }catch(Exception e){}
             %>
          </SELECT>
          </font><FONT SIZE="2"> 
          <input type="text" name="source" value="<%=rs.getString("source")%>" size="6">
          <font color="#000080">天</font></font>&nbsp;</div>      </td>
      <td width="15%" height="2"  bgcolor="#e8ebf5"> 
        <div align="left"><font size="2"><font color="#000080">发货日期</font></font>&nbsp;</div>      </td>
      <td width="40%" height="2" > 
        <div align="left"><font size="2"> 
          <input type="text" name="send_date" value="<%=rs.getString("send_date")%>" size="10">
          </font><font size="2"  ><a href="javascript:pic2_onclick()"><img border="0" src="../../images/calendar.jpg" id="pic2" width="20" height="16"></a></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="15%"  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><font size="2">快递帐号</font>&nbsp;</div>      </td>
      <td > 
        <div align="left"><FONT SIZE="2"> 
          <input type="text" name="trade" value="<%=rs.getString("trade")%>">
          </font>&nbsp;</div>      </td>
      <td width="15%" height="2"  bgcolor="#e8ebf5"> 
        <div align="left"><FONT SIZE="2"><font color="#000080">银行费用</font></font>&nbsp;</div>      </td>
      <td width="40%" height="2" > 
        <div align="left"><FONT SIZE="2"> 
          <input type="text" name="yj" value="<%=rs.getString("yj")%>" size="8">
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><FONT SIZE="2">发　　票</font>&nbsp;</div>      </td>
      <td > 
        <div align="left">
          <select name="item" >
          	<%String item1=rs.getString("item").trim(); %>
          	<option><%=item1 %></option>
          </select>
        &nbsp;</div>      </td>
      <td  bgcolor="#e8ebf5"> 
        <div align="left"><font color="#000080" size="2">运输方式</font>&nbsp;</div>      </td>
      <td > 
        <div align="left"><font size="2" color="#000080">
          <select name="tr" id="tr" >
            <%String tr1=rs.getString("tr").trim();
          String[] tr=new String[]{"FedEx","UPS","DHL","TNT","SF Express","Registered Post Parcel","EMS","AIR","顺风","快递","自提","送货","Other"};
          for(int i=0;i<tr.length;i++)
           {
             if(tr1.equals(tr[i]))
               out.println("<option selected>"+tr[i]+"</option>");
             else
               out.println("<option >"+tr[i]+"</option>");
           }
        %>
          </select>
        </font><FONT SIZE="2">&nbsp; </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="2"  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><FONT SIZE="2">运费负担</font>&nbsp;</div>      </td>
      <td height="2" > 
        <div align="left"> <FONT SIZE="2"> 
          <select name="yf_types" id="yf_types">
            <option value="<%String yf_types=rs.getString("yf_types");out.print(yf_types);%>">
            <%out.print(yf_types);%>
            </option>
            <option value="Freight To Collect">Freight To Collect</option>
            <option value="Freight Prepaid">Freight Prepaid</option>
            <option value="公司支付">公司支付</option>
            <option value="客户支付">客户支付</option>
          </select>
          &nbsp; </font>&nbsp;</div>      </td>
      <td height="2"  bgcolor="#e8ebf5"> 
        <div align="left"><font size="2" color="#000080">运费金额</font>&nbsp;</div>      </td>
      <td height="2" > 
        <div align="left"><FONT SIZE="2"> 
          <input type="text" name="yf_money" size="10" value="<%=rs.getDouble("yf_money")%>">
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><FONT SIZE="2">收货地址</font>&nbsp;</div>      </td>
      <td colspan="3" > 
        <div align="left"><FONT SIZE="2"> 
          <textarea name="tr_addr" id="tr_addr" cols="68"><%=DealString.unHtmlEncode(rs.getString("tr_addr"))%></textarea>
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><FONT SIZE="2">收 货 人</font>&nbsp;</div>      </td>
      <td > 
        <div align="left"><FONT SIZE="2"> 
          <input name="tr_man" type="text" id="tr_man" value="<%=rs.getString("tr_man")%>">
          </font>&nbsp;</div>      </td>
      <td  bgcolor="#e8ebf5"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">收货电话</font>&nbsp;</div>      </td>
      <td > 
        <div align="left"><FONT SIZE="2"> 
          <input name="tr_tel" type="text" id="tr_tel" value="<%=rs.getString("tr_tel")%>">
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="15%"  bgcolor="#e8ebf5"> 
        <div align="left" class="STYLE1"><font size="2">条　　款</font>&nbsp;</div>      </td>
      <td colspan="3" > 
        <div align="left"><FONT SIZE="2"> 
          <textarea name="tbyq" cols="68" ROWS="10"><%=DealString.unHtmlEncode(rs.getString("tbyq"))%></textarea>
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="2"  bgcolor="#e8ebf5">
        <div align="left" class="STYLE1"><FONT SIZE="2">备　　注</font>&nbsp;</div>      </td>
      <td height="2" colspan="3" >
        <div align="left"><FONT SIZE="2"> 
          <textarea name="remarks" cols="68"><%=DealString.unHtmlEncode(rs.getString("remarks"))%></textarea>
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="2" colspan="4" > 
        <div align="left" class="STYLE1"> 
          <center>
            <input type="hidden" name="id" value="<%=id1%>">
            <button id="submitBtn">保存</button></font></font></a>
          </center>
        </div>
        <div align="left"></div>      </td>
    </tr>
  </table>

  
</body>
</html>
