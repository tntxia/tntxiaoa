<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.sql.*,java.util.*"%>
<% String getLoginmessage = (String) session.getValue("loginSign");
   if(getLoginmessage!="OK"){
%>
<script language=javascript>
    window.location="../refuse.jsp"
</script>
<% }     
%>
<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<%
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
String id=request.getParameter("id");
strSQL = "select count(*) from company ";
sqlRst = einfodb.executeQuery(strSQL);
sqlRst.next();
intRowCount = sqlRst.getInt(1);
sqlRst.close();
intPageCount = (intRowCount+intPageSize-1) / intPageSize;
if(intPage>intPageCount) intPage = intPageCount;
    strSQL = "select id,companyname,companyaddr,bank_dm  from company ";
sqlRst = einfodb.executeQuery(strSQL);
i = (intPage-1) * intPageSize;
for(j=0;j<i;j++) sqlRst.next(); %>

<html>
<head>
<title>公司信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">


</head>
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
</style>

<body bgcolor="#ffffff" text="#000000" topmargin="0" marginwidth="0" marginheight="0">
<form name="form1" method="post" action="">   <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"> 
<TR BGCOLOR="#d3d8eb"> <td width="60"> <div align="left"><font size="2">序号</font></div></td><TD WIDTH="424"> 
<DIV ALIGN="left"><FONT SIZE="2">公司名称</font>&nbsp;</div></TD><TD WIDTH="523"> <DIV ALIGN="left"><FONT SIZE="2">开户名称</font>&nbsp;</div></TD></tr> 
<%
   i = 0;
   while(i<intPageSize && sqlRst.next()){ 
   int  cid=sqlRst.getInt("id");
   %> <tr bgcolor="<%if ((i % 2)==0) out.print("#eeeeee"); else out.print("#ffffff");%>" height="8" > <td width="60" height="8"><%=i+1%></td><TD WIDTH="424"> 
<DIV ALIGN="left"><FONT SIZE="2" COLOR="#000000"><A HREF="ffprintvc.jsp?id=<%=id%>&cid=<%=cid%>"><%=sqlRst.getString(2)%></A></font>&nbsp;</div></TD><TD WIDTH="523"> 
<DIV ALIGN="left"><FONT SIZE="2" COLOR="#000000"><%=sqlRst.getString(4)%></font>&nbsp;</div></TD></tr> 
<% i++; } %> <tr> <td colspan=3 align=center height="16"> <div align="right"><font color="#000000" size="2"> 
共<%=intRowCount%>个公司信息</font> <font color="#C1D9FF" size="2">
 <%if(intPage<intPageCount){%> 
<a href="company1.jsp?page=<%=intPage+1%>&id=<%=id%>">下一页</a> 
<% }%> 
<%if(intPage>1){%> 
<a href="company1.jsp?page=<%=intPage-1%>&id=<%=id%>">上一页</a> 
<% }%> </font>&nbsp;</div></td></tr> </table></form><p>&nbsp;</p>
</body> 
</html> 
