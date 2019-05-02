<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<% String getLoginmessage = (String) session.getValue("loginSign");
   if(getLoginmessage!="OK"){
%>
<script language=javascript>
    window.location="../refuse.jsp"
    </script>
<% } else {    
%>
<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" /><%
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
intPageSize = 500;
strPage = request.getParameter("page");
if(strPage==null){
intPage = 1;
} else{
intPage = java.lang.Integer.parseInt(strPage);
if(intPage<1) intPage = 1; }
String dept = (String) session.getValue("dept");
String username1 = (String) session.getValue("username");
String restrain_name = (String) session.getValue("restrain_name");
String deptjb = (String) session.getValue("deptjb");
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet rsmod = einfodb.executeQuery(modsql);
  if(rsmod.next()) { 
 String prock=rsmod.getString("profl").trim();
 String proview=rsmod.getString("outview").trim();
 String outadd=rsmod.getString("outadd").trim();
 if(proview.equals("有")){ 
strSQL = "select count(*) from outhouse where states='已出库'";
}else
strSQL = "select count(*) from outhouse where states='已出库' and slinktel='"+prock+"'";
sqlRst = einfodb.executeQuery(strSQL);
sqlRst.next();
intRowCount = sqlRst.getInt(1);
sqlRst.close();
intPageCount = (intRowCount+intPageSize-1) / intPageSize;
if(intPage>intPageCount) intPage = intPageCount;
 if(proview.equals("有")){ 
strSQL = "select   id,pro_out_num,pro_fynum,pro_coname,pro_model,pro_num,pro_unit,pro_supplier,pro_datetime,slinktel from outhouse where states='已出库' order by id desc";
}else
strSQL = "select   id,pro_out_num,pro_fynum,pro_coname,pro_model,pro_num,pro_unit,pro_supplier,pro_datetime,slinktel from outhouse where states='已出库' and slinktel='"+prock+"' order by id desc";
sqlRst = einfodb.executeQuery(strSQL);
i = (intPage-1) * intPageSize;
for(j=0;j<i;j++) sqlRst.next(); 
java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String cdate=simple.format(new java.util.Date());
java.text.SimpleDateFormat simplec=new java.text.SimpleDateFormat("yyyy-MM");
String cd=simplec.format(new java.util.Date());
%>
<html>
<head>
<title>津利得国际贸易有限公司</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0">
<form name="form1" method="post" action="szouthousem.jsp">
    <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
        <tr>
            <td height="17" colspan="7"><div align="center"><font face="宋体">出 库 报 表</font>&nbsp;</div></td>
        </tr>
        <TR BGCOLOR="#d3d8eb">
            <td width="77" height="17"><div align="left"><font size="2">出库序号</font></div></td>
            <td width="110" bgcolor="#d3d8eb"><div align="left"><FONT SIZE="2">订单编号</font>&nbsp;</div></td>
            <td width="259" height="17"><div align="left"><font size="2">客户名称</font>&nbsp;</div></td>
            <td width="117" height="17"><div align="left"><font size="2">产品型号</font>&nbsp;</div></td>
            <td width="65" height="17"><div align="left"><font size="2">数量</font>&nbsp;</div></td>
            <td width="107" height="17"><div align="left"><font size="2">出库日期</font>&nbsp;</div></td>
            <td width="107" height="17"><div align="left"><font size="2">仓库名称</font>&nbsp;</div></td>
        </tr>
        <%
   i = 0;
   while(i<intPageSize && sqlRst.next()){ %>
        <tr bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>" style="cursor:hand;" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>'" height="8">
            <td width="77" height="8"><div align="left"><font size="2">
                    <%
		  String pro_fynum=sqlRst.getString(2);%>
                    <%=pro_fynum%></font>&nbsp;</div></td>
            <td><div align="left"><font size="2"><%=sqlRst.getString(3)%> </font>&nbsp;</div></td>
            <td width="259" height="8"><div align="left"><font size="2"><%=sqlRst.getString(4)%> </font>&nbsp;</div></td>
            <td width="117" height="8"><div align="left"><font size="2">
                    <%String pro_model1=sqlRst.getString(5);%>
                    <%=pro_model1%> </font>&nbsp;</div></td>
            <td width="65" height="8"><div align="left"><font size="2">
                    <%int num=sqlRst.getInt(6);%>
                    <%=num%><%=sqlRst.getString(7)%></font>&nbsp;</div></td>
            <td width="107" height="8"><font size="2"><%=sqlRst.getString(9)%></font>&nbsp;</td>
            <td width="107" height="8"><font size="2"><%=sqlRst.getString(10)%></font>&nbsp;</td>
        </tr>
        <% i++; } %>
        <tr>
            <td colspan=8 align=center height="22"><div align="right"><font color="#000000" size="2"> 共<%=intRowCount%>个已出库产品</font> <font color="#C1D9FF" size="2">
                    <%if(intPage>1){%>
                    <a href="out-report.jsp?page=<%=intPage-1%>">上一页</a>
                    <% }  %>
                    <%if(intPage<intPageCount){%>
                    <a href="out-report.jsp?page=<%=intPage+1%>">下一页</a>
                    <% } %>
                    </font>&nbsp;</div></td>
        </tr>
    </table>
    <%
  rsmod.close();
  sqlRst.close();
 einfodb.closeStmt();
 einfodb.closeConn();
}}%>
</form>
<br>
</body>
</html>
