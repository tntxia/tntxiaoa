<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<%

String basePath = request.getContextPath();

java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String currentDate=simple.format(new java.util.Date());
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
java.sql.Connection sqlCon;
java.sql.Statement sqlStmt;
java.sql.ResultSet sqlRst;
java.lang.String strCon;
java.lang.String strSQL;
int intPageSize; 
int intRowCount; 
int intPageCount;
int intPage; 
java.lang.String strPage;
int i,j,k; 
intPageSize = 50;
strPage = request.getParameter("page");
if(strPage==null){
intPage = 1;
} else{
intPage = java.lang.Integer.parseInt(strPage);
if(intPage<1) intPage = 1; }
String supplier = request.getParameter("supplier").trim();
String number = request.getParameter("number").trim();
String pro_types = request.getParameter("pro_types").trim();
String pro_model = request.getParameter("pro_model").trim();
String profl = request.getParameter("profl").trim();
String sdate = request.getParameter("startdate");
String edate = request.getParameter("enddate");
String sub = request.getParameter("sub");
String dept = (String) session.getValue("dept");
String username1 = (String) session.getValue("username");
String restrain_name = (String) session.getValue("restrain_name");
String deptjb = (String) session.getValue("deptjb");
String  strSQLh = "select * from restrain_gp where restrain_name='"+restrain_name+"'";
java.sql.ResultSet sqlRsth = einfodb.executeQuery(strSQLh);
int tmpigp=0;
String gp_gp="";
String proview_yes="";
String proview_price="";
String gp_gpc="";
String  pro_zt="无";
String  proadd="无";
String pro_ck="无";
while(sqlRsth.next()){ 
 proview_price=sqlRsth.getString("proview_price").trim();
 pro_ck=sqlRsth.getString("pro_ck").trim();
 pro_zt=sqlRsth.getString("pro_zt").trim();
 String proadd1=sqlRsth.getString("pro_add").trim();
 if(proadd1.equals("有")){proadd="有";}
 if(pro_zt.equals("有")){
 gp_gp=gp_gp+" or pro_addr='"+pro_ck +"'";
 }
 tmpigp++;}
 sqlRsth.close();
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet rsmod = einfodb.executeQuery(modsql);
  if(rsmod.next()) { 
 String prock=rsmod.getString("profl").trim();
 String proview=rsmod.getString("outview").trim();
 String outadd=rsmod.getString("outadd").trim();
 if(proview.equals("有")){
strSQL = "select count(*) from ckview where states='已出库'  and pro_model like '%"+ pro_model +"%' and pro_coname like '%"+ supplier +"%' and pro_out_num like '%"+ number +"%'  and pro_fynum like '%"+ pro_types +"%'  and slinktel like '%"+ profl +"%'   and pro_datetime>='"+sdate+"' and '"+edate+"'>=pro_datetime and sub like '%"+sub+"%'";
}else
strSQL = "select count(*) from ckview where states='已出库'  and pro_model like '%"+ pro_model +"%' and pro_coname like '%"+ supplier +"%' and pro_out_num like '%"+ number +"%'  and pro_fynum like '%"+ pro_types +"%'  and slinktel like '%"+ profl +"%'   and pro_datetime>='"+sdate+"' and '"+edate+"'>=pro_datetime  and slinktel='"+prock+"' and sub like '%"+sub+"%'";
sqlRst = einfodb.executeQuery(strSQL);
sqlRst.next();
intRowCount = sqlRst.getInt(1);
sqlRst.close();
intPageCount = (intRowCount+intPageSize-1) / intPageSize;
if(intPage>intPageCount) intPage = intPageCount;
 if(proview.equals("有")){
strSQL = "select ddid,pro_out_num,pro_fynum,pro_coname,pro_model,pro_name,pro_rate,pro_sales_price,pro_price_hb,pro_num,pro_unit,pro_supplier,pro_datetime,slinktel,sub,slinkman,man from ckview  where states='已出库'  and pro_model like '%"+ pro_model +"%' and pro_coname like '%"+ supplier +"%' and pro_out_num like '%"+ number +"%'  and pro_fynum like '%"+ pro_types +"%'   and slinktel like '%"+ profl +"%'    and pro_datetime>='"+sdate+"' and '"+edate+"'>=pro_datetime and sub like '%"+sub+"%' order by pro_out_num desc";

}else
strSQL = "select id,pro_out_num,pro_fynum,pro_coname,pro_model,pro_name,pro_rate,pro_sales_price,pro_price_hb,pro_num,pro_unit,pro_supplier,pro_datetime,slinktel,sub,slinkman,man from ckview  where states='已出库'  and pro_model like '%"+ pro_model +"%' and pro_coname like '%"+ supplier +"%' and pro_out_num like '%"+ number +"%'  and pro_fynum like '%"+ pro_types +"%'   and slinktel like '%"+ profl +"%'    and pro_datetime>='"+sdate+"' and '"+edate+"'>=pro_datetime  and slinktel='"+prock+"' and sub like '%"+sub+"%' order by pro_out_num desc";

System.out.print(strSQL);

sqlRst = einfodb.executeQuery(strSQL);
i = (intPage-1) * intPageSize;
for(j=0;j<i;j++) sqlRst.next(); 
int znum=0;



%>
<html>
<head>
<title>查询出库</title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<style type="text/css">
td,p,select,input,textarea {font-size:12px}
.l15 {line-height:150%}
.l13 {line-height:130%}
.f10 {font-size:10px}
.f14 {font-size:14.9px}
.bdrclr01{color:#000000; border-color:#000000}
.c03{color:#000000;border-color:#000000;}
A:link {text-decoration:none;color:#0000ff;}
A:visited {text-decoration:none;color:#800080;}
A:active {text-decoration:none;color:#0000ff;}
A:hover {text-decoration:underline;color:#0000ff;}
.STYLE1 {color: #000000}
</style>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script language="javascript">
function pic1_onclick() {
     window.open("waitcal.jsp?m=m1&time=<%=time1%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
     window.open("waitcal.jsp?m=m2&time=<%=time2%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}

$(function(){
	
	if(!$("#supplier2").val()){
		if(window.opener){
			var openerSupplier = window.opener.$("#supplier").val();
			$("#supplier2").val(openerSupplier);
			$("[name=form1]").submit();
		}
		
	}
	
});
</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0">

<br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"> 
<form name="form1" method="post" action="szouthousem.jsp"> 
<tr>
    <td height="17" colspan="14">
 	  
    客户名称
        <input name="supplier" type="text" id="supplier2" size="10" value="<%=supplier%>">
        产品型号
    <input name="pro_model" type="text" id="pro_model" size="10">
        合同编号
    <input name="pro_types" type="text" id="pro_types" size="10">
    <FONT SIZE="2">公司合同号<span class="STYLE1"><input name="sub" type="text" id="sub" size="10">
    仓库</span></FONT><FONT COLOR="#000000" SIZE="2">
    <SELECT SIZE="1" NAME="profl">
        <OPTION></OPTION>
        <%
  try
  {
    ResultSet rs=einfodb.executeQuery("select cpro from profl ");
    while(rs.next())
    {
      String proflname=rs.getString("cpro");
      out.println("<option value='"+proflname+"'>"+proflname+"</option>");
    }
    rs.close();
   }catch(Exception e){}
   %>
    </SELECT>
</font>&nbsp;</td>
  </tr>
<tr>
    <td height="17" colspan="14"><font size="2"><font size="2"><span class="STYLE1">出库序号
        <input name="number" type="text" id="number" size="10">
        起始日期
    <input name="startdate" type="text" id="startdate" size="10">
    <a href="javascript:pic1_onclick()"><img border="0" src="../images/calendar.JPG" id="pic1" width="20" height="16"></a> 终止日期
    <input name="enddate" type="text" id="enddate" value="<%=currentDate%>" size="10">
    <a href="javascript:pic2_onclick()"><img border="0" src="../images/calendar.JPG" id="pic2" width="20" height="16"></a></span></font><FONT SIZE="2"></font></font><font color="#000000" size="2">
    <label>
    <input type="submit" name="Submit" value="查询">
    </label>
    <input type="button" name="Submit2" value="打印预览出库报表" onClick="window.open('sout-report.jsp?supplier=<%=supplier%>&number=<%=number%>&pro_types=<%=pro_types%>&pro_model=<%=pro_model%>&sdate=<%=sdate%>&edate=<%=edate%>&profl=<%=profl%>','nrtop', 'height=500, width=650, top=50, left=50, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no')">
   </font></font></font>&nbsp;
   
   
   </td>
  </tr>
  </form>
 <tr bgcolor="#d3d8eb"> <td height="17"> 
   <div align="left"><font size="2">出库序号</font></div></td><td> 
      <div align="left"><font size="2">合同编号</font></div></td>
<td nowrap><div align="left"><font size="2">公司合同号</font></div></td>
<td height="17"> 
  <div align="left"><font size="2">客户名称</font></div></td><td height="17"> 
    <div align="left"><font size="2">产品型号</font></div></td>
<td><div align="left"><font size="2">产品批号</font></div></td>
<td nowrap><div align="left">税率</div></td>
<td nowrap><div align="left">单价</div></td>
<td nowrap><div align="left">货币</div></td>
<td height="17"> 
        <div align="left"><font size="2">数量</font></div></td><td height="17"> 
            <div align="left"><font size="2">出库日期</font></div></td>
        <td nowrap><div align="left"><font size="2">出库人</font></div></td>
        <td height="17"> 
          <div align="left"><font size="2">仓库名称</font></div></td>
     <td height="17" nowrap><div align="left"><font size="2">销售员</font>&nbsp;</div></td>     
     </tr> <%
   i = 0;
   while(i<intPageSize && sqlRst.next()){ %> <tr style="cursor:hand;" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" height="8"> 
<td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2"> 
  <%
  String ddid = sqlRst.getString("ddid");
		  String pro_fynum=sqlRst.getString(2);%> 
		  <a href="ydd-view.jsp?id=<%=ddid%>" target="w" onClick="javascript:window.open('ydd-view.jsp?id=<%=ddid%>','w','height=550,width=955,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
  <%=pro_fynum%></a></font></div></td>
  
  <td bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2">
        <a href="ydd-view.jsp?id=<%=ddid%>" target="w" onClick="javascript:window.open('ydd-view.jsp?id=<%=ddid%>','w','height=550,width=955,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">	       
        	       <%=sqlRst.getString(3)%> </a>
  </font></div></td>
<td nowrap bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2">
  <%String sub1=sqlRst.getString("sub");
			out.print(sub1);
			%>
</font>　</div></td>
<td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2"><%=sqlRst.getString(4)%> 
</font></div></td><td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2"> 
  <%String pro_model1=sqlRst.getString(5);%> 
  <%=pro_model1%> </font></div></td>
<td bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2"><%=sqlRst.getString(6)%> </font>　</div></td>
<td bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2">
  <%String pro_rate=sqlRst.getString(7);%>
  <%=pro_rate%></font></div></td>
<td bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2">
  <%String pro_sales_price=sqlRst.getString(8);%>
  <%if(proview_price.equals("有")) {out.print(pro_sales_price);}%>
</font></div></td>
<td bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2">
				   <%String pro_price_hb=sqlRst.getString(9);%>
        <%=pro_price_hb%></font></div></td>
<td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> 
    <div align="left"><font size="2"> 
        <%int num=sqlRst.getInt(10);%> 
        <%=num%>
        <%--<=sqlRst.getString(8)>--%>
        </font></div></td><td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2"><%=sqlRst.getString(13)%></font>　</div></td>
        <td nowrap bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"><div align="left"><font size="2"><%=sqlRst.getString(16)%></font>　</div></td>
        <td height="8" bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>"> <div align="left"><font size="2"><%=sqlRst.getString(14)%></font>　</div></td>
        <td height="8" nowrap><div align="left"><font size="2"><%=sqlRst.getString("man")%></font>&nbsp;</div></td>
        	       </tr> 
<% i++;
  znum=znum+num;
   }
   %> <tr> <td colspan=14 align=center height="22"> <div align="right"><font color="#000000" size="2"> 
出库总数量：<%=znum%>;已出库单<%=intRowCount%>个</font><FONT COLOR="#000000"><FONT COLOR="#000000"><FONT SIZE="2">,当前是第<%=intPage%>页,共<%=intPageCount%>页</FONT></FONT></FONT> 
<font color="#C1D9FF" size="2"> 
	
<%if(intPage>1){%>
	<button onclick="prevPage()">上一页</button>
  
<% } %> 
<%if(intPage<intPageCount){%>
<button onclick="nextPage()">下一页</button>
 
<% }  %> </font>&nbsp;</div></td></tr> </table>

<%
  rsmod.close();
  sqlRst.close();
 einfodb.closeStmt();
 einfodb.closeConn();
}%> 

<script type="text/javascript">

function prevPage(){
	$("[name=page]").val(<%=intPage%>-1);
	$("[name=form2]").submit();
}

function nextPage(){
	$("[name=page]").val(<%=intPage%>+1);
	$("[name=form2]").submit();
}


</script>

<form name="form2" action="szouthousem.jsp" method="post">
	<input type="hidden" value="<%=intPage%>" name="page">
	<input type="hidden" value="<%=supplier%>" name="supplier">
	<input type="hidden" value="<%=pro_types%>" name="pro_types">
	<input type="hidden" value="<%=pro_model%>" name="pro_model">
	<input type="hidden" value="<%=sdate%>" name="startdate">
	<input type="hidden" value="<%=edate%>" name="enddate">
	<input type="hidden" value="<%=profl%>" name="profl">
	<input type="hidden" value="<%=number%>" name="number">
	<input type="hidden" value="<%=sub%>" name="sub">
</form>
</body>
</html>
 
