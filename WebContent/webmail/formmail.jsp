<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*,java.util.*"%>

<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<%

String basePath = request.getContextPath();

java.sql.Connection sqlCon;
java.sql.Statement sqlStmt;
java.sql.ResultSet sqlRst;
java.sql.ResultSet sqlRstn;
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
if(strPage==null||strPage.trim().length()==0){
intPage = 1;
} else{
intPage = java.lang.Integer.parseInt(strPage);
if(intPage<1) intPage = 1; }
String dept = (String) session.getValue("dept");
String username1 = (String) session.getValue("username");
String restrain_name = (String) session.getValue("restrain_name");
String deptjb = (String) session.getValue("deptjb");

/**** 以下是小虾增加的内容   ******/
String username = request.getParameter("mail_man");   // 发件人
if(username==null){
	username = "";
}
String title = request.getParameter("title");   // 主题
if(title==null)
	title = "";
String content = request.getParameter("content");   // 内容
if(content==null)
	content = "";
String startdate = request.getParameter("startdate");   // 起始日期
if(startdate==null)
	startdate = "";
String enddate = request.getParameter("enddate");   // 终止日期
if(enddate==null)
	enddate = "";

/*****         end          ******/

String sql_where = "";

if(username!=null && username.trim().length()>0 && !username.equals("allusers")){
	sql_where += " and mail_man like '%"+username+"%'";
	
}

if(title!=null && title.trim().length()>0){
	sql_where += " and mail_sub like '%"+title+"%'";
}

if(content!=null && content.trim().length()>0){
	sql_where += " and mail_nr like '%"+content+"%'";
}

if(startdate.trim().length()>0){
	sql_where += " and substring(mail_datetime,1,10) >= '"+startdate+"'";
}

if(enddate.trim().length()>0){
	sql_where += " and substring(mail_datetime,1,10) <= '"+enddate+"'";
}

strSQL = "select count(*) from  getmail where  getman='"+username1+"'  and  states!='删除' and states!='待处理' "+sql_where;
sqlRst = einfodb.executeQuery(strSQL);
sqlRst.next();
intRowCount = sqlRst.getInt(1);
sqlRst.close();
intPageCount = (intRowCount+intPageSize-1) / intPageSize;
if(intPage>intPageCount) intPage = intPageCount;

/***   小虾对以下的内容进行了修改    **/
strSQL = "select  id,mail_man,mail_sub,mail_datetime  from getmail where   getman='"+username1+"'  and  states!='删除' and states!='待处理' ";
strSQL += sql_where + " order by id desc";
/*****         end          ******/

sqlRst = einfodb.executeQuery(strSQL);
i = (intPage-1) * intPageSize;
for(j=0;j<i;j++) sqlRst.next(); 

java.sql.ResultSet userRs = null;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>收件箱</title>
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

.but{
	background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF;
}

</style>
<script type="text/javascript" src="/static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/webmail/formmail.js"></script>
</head>
<body topmargin="0" marginwidth="0" marginheight="0">

<form id="searchForm" name="searchForm" action="">
	<input name="mail_man" type="hidden">
	<input name="title" type="hidden">
	<input name="content" type="hidden">
	<input name="startdate" type="hidden">
	<input name="enddate" type="hidden">
	<input name="page" type="hidden">
</form>

<form name="handlingForm" action="doHandlingMail.jsp">
<input name="ids" type="hidden">
<input name="type" type="hidden" value="1">
</form>

<div align="left">
	<button id="getMailBtn" onClick="getMail()" class="but">收取邮件:共?封新邮件</button>
    <!-- 这里增加了过滤界面    -->
    <input type="button" value="写邮件" name="B2223" class="but" onClick="window.open('new.jsp','_blank', 'height=450, width=930, top=0, left=50, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no')">
	<input type="button" value="待处理" class="but" onclick="doHandlingMail()">
</div>

<form id="form1" name="form1" method="post" action="allydel-get.jsp">
  <br>
  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
	<tr height="8">
      <td height="22" colspan="5" ><div align="left"><font size="2"><font color="#000000" size="2"> </font></font><font color="#000000" size="2"><span style="font-size:12px">发件人：
            <select name="username">
            <option value="allusers">所有用户</option>
            <%
		userRs=einfodb.executeQuery("select * from username  order by nameid asc");
		while(userRs.next()){
			String user_name=userRs.getString("name").trim();
		%>
            <option value="<%=user_name%>" <%if(user_name.equals(username)){%> selected<%}%>><%=user_name%></option>
            <%}userRs.close();%>
          </select>
主题：          </span></font>
            <label>
            <input name="title" type="text" size="15" value="<%=title%>">
            <span style="font-size:12px"><font color="#000000" style="font-size:12px"> 内容：</font></span>
            <input name="content" type="text" size="15" value="<%=content%>">
            <span style="font-size:12px"><font color="#000000" style="font-size:12px">&nbsp;起始日期
            <input type="text" name="startdate"  size="10" maxlength="25" value="<%=startdate.trim()%>" />
              <a href="javascript:pic1_onclick()"><img border="0" src="../../images/calendar.JPG" id="pic1" width="20" height="16" /></a> 终止日期</font></span><span style="font-size:12px"><font size="2"><font size="2">
                <input type="text" name="enddate"   size="10" maxlength="25" value="<%=enddate.trim()%>" />
                <a href="javascript:pic2_onclick()"><img border="0" src="../../images/calendar.JPG" id="pic1" width="20" height="16" /></a> </font><font size="2">
                <input type="button" value="查询" name="actiontype" onClick="filter()" />
                </font></font></span></label>
      </div></td>
    </tr>
    <tr bgcolor="#C3D9FF" height="8">
      <td width="5%" height="22" ><div align="left"><font size="2" face="宋体">&nbsp; </font>&nbsp;</div></td>
      <td width="5%" height="22" ><div align="left">
          <div align="left"><font color="#000000">序号</font></div>
      </div></td>
      <td width="11%" height="22" ><div align="left">
          <div align="left"><font color="#000000">发件人</font>&nbsp;</div>
      </div></td>
      <td width="68%" bgcolor="#e8ebf5" ><div align="left">
          <div align="left"><font color="#000000">主　　　题</font>&nbsp;</div>
      </div></td>
      <td width="16%" height="22" ><div align="left">
          <div align="left"><font color="#000000">发件日期</font>&nbsp;</div>
      </div></td>
    </tr>
    <%
		i = 0;
   while(i<intPageSize && sqlRst.next()){
   int id=sqlRst.getInt(1);
    %>
    <tr bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>" style="cursor:hand;"   onMouseOver="this.bgColor='#B5DAFF'"onMouseOut="this.bgColor='#ffffff'" height="8">
      <td width="5%" height="8" ><font size="2" face="宋体">
        <input name="s<%=i+1%>" type="checkbox" id="checkpro" value="<%=id%>" >
      </font>&nbsp;</td>
      <td width="5%" height="8" ><%=i+1%></td>
      <td width="11%" ><a href="get-view.jsp?id=<%=id%>" target="b" onClick="javascript:window.open('get-view.jsp?id=<%=id%>','b','height=500,width=750,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=sqlRst.getString("mail_man")%></a><font size="2">
        <input name="id<%=i+1%>" type="hidden" id="id<%=i+1%>" value="<%=id%>">
      </font>&nbsp;</td>
      <td width="68%" ><a href="get-view.jsp?id=<%=id%>" target="b" onClick="javascript:window.open('get-view.jsp?id=<%=id%>','b','height=500,width=750,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=sqlRst.getString("mail_sub")%></a></td>
      <td width="16%" ><%=sqlRst.getString("mail_datetime")%></td>
    </tr>
    <% i++; } %>
    <tr>
      <td colspan=3 align=center height="22"><div align="left"><font size="2" face="宋体"> 全选
        <input name="s_ally" type="checkbox" id="s_ally"  onClick="javascript:s_ally_onclick();">
          </font><font size="2"> </font><font color="#000000" size="2" face="Arial, Helvetica, sans-serif">
          <input type="submit" value="永久删除" name="B2232" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF" >
          </font><font size="2">
          <input name="i" type="hidden" id="id<%=i+1%>" value="<%=i+1%>">
      </font>&nbsp;</div></td>
      <td colspan=2 align=center height="22"><div align="right"><font color="#000000" size="2">共<%=intRowCount%>个邮件，当前是第<%=intPage%>页,共<%=intPageCount%>页</font> <font color="#000000" size="2">
          <%if(intPage<intPageCount){%>
          <a href="javascript:goPage('<%=intPage+1%>')">下一页</a>
          <% }  %>
          <%if(intPage>1){%>
          <a href="javascript:goPage('<%=intPage-1%>')">上一页</a>
          <%  }  %>
      </font>&nbsp;</div></td>
    </tr>
  </table>
</form>
</html> 
	  <script language=javascript>
	  function  s_ally_onclick()
{
   var length = document.form1.elements.length;
   var tocheck = document.form1.s_ally.checked;
   for (var i=0; i<length; i++)
   {
     if (document.form1.elements[i].name.indexOf("s")!= -1)
	document.form1.elements[i].checked =tocheck;
   }
}
</script>
<% sqlRst.close();
 einfodb.closeStmt();
 einfodb.closeConn();

%>


