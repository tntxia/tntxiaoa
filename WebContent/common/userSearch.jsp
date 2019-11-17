<%@ page contentType="text/html;charset=gb2312"%>
<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<%

String target = request.getParameter("target");
String basePath = request.getContextPath();

 %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>用户选择</title>
<style type="text/css">
<!--
.H1 {font-size:12pt; line-height:15pt; align=center}
.ourfont {font-size:9pt; line-height:15pt; }
.ourfont1 {font-size:8pt; line-height:13pt; }
A{text-transform: none; text-decoration:none;color:#0000ff}
a:hover {text-decoration:underline;color:#0000ff}
-->
</style>
<script language="JavaScript" src="<%=basePath%>/js/config.js"></script>
<script language="JavaScript" src="<%=basePath%>/js/jquery.js"></script>
<script language="JavaScript" src="<%=basePath%>/js/common.js"></script>
</head>

<body topmargin="0" >
<form name="form1" method="post" action="">
<input id="target" value="<%=target%>" type="hidden">
<table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <TR BGCOLOR="#d3d8eb"> 
      <td class="ourfont" align="center" width="10%"><input name="actionType" type="hidden" value="query">&nbsp;</td>
      <td class="ourfont" align="center" width="30%"><div align="left">
      	<font color="#000000">&nbsp;员工姓名</font>&nbsp;</div>
      </td>
      <td class="ourfont" align="center" width="30%"><div align="left">
      	<font color="#000000">&nbsp;所在部门</font>&nbsp;</div>
      </td>
      <td class="ourfont" align="center" width="30%"><div align="left">
      	<font color="#000000">&nbsp;权限组</font>&nbsp;</div>
      </td>
    </tr>
    <%i = 0;
    while(i<intPageSize && sqlRst.next()){ %>
    <%
	  int id=sqlRst.getInt("nameid");
	  String name=sqlRst.getString("name");
	  String departmentName = sqlRst.getString("departname");
	  String restrainName = sqlRst.getString("restrain_name");
	  
	  %>
    <tr> 
      <td> <input type="checkbox" name="email<%=id%>" value="<%=name%>"> 
      </td>
      <td><font size="2"><%=name%> </font>&nbsp;</td>
      <td><font size="2"><%=departmentName%> </font>&nbsp;</td>
      <td><font size="2"><%=restrainName%> </font>&nbsp;</td>
    </tr>
    <% i++; } %>
    <tr> 
      <td colspan=4 align=center height="22"> <div align="left"> <font color="#000000" size="2">全选</font> 
          <input type="checkbox" name="allSelect" value=""      
		     onclick="javascript:allSelect_onclick();">
          <input name="ok" type="button" value="确定" onClick="javascript:ok_onclick();">
          <font size="2"> </font>&nbsp;</div>
        </td>
    </tr>
    <tr> 
      <td colspan=4 align=center height=22><div align="right"><font color="#000000" size="2"> 共<%=intRowCount%>个员工</font> <font color="#C1D9FF" size="2"> 
          <%if(intPage<intPageCount){%>
          <a href="search2.jsp?page=<%=intPage+1%>">下一页</a> 
          <% 
         }
        %>
          <%if(intPage>1){%>
          <a href="search2.jsp?page=<%=intPage-1%>">上一页</a> 
          <% 
        }
        %>
          </font>&nbsp;</div></td>
    </tr>
  </table>
  </form>
</body> 
 
</html> 
<script language=javascript>
function allSelect_onclick()
{
   var length = document.form1.elements.length;
   var tocheck = document.form1.allSelect.checked;
   for (var i=0; i<length; i++)
   {
     if (document.form1.elements[i].name.indexOf("email") != -1)
	document.form1.elements[i].checked =tocheck;
   }
}
function ok_onclick()
{
   var returnValue="";
   var length=document.form1.elements.length;
   for(var i=0;i<length;i++)
   {
     if(document.form1.elements[i].name.indexOf("email")!=-1)
     {
        if(document.form1.elements[i].checked==true)
        {
          if(returnValue=="")
            returnValue=document.form1.elements[i].value;
          else
            returnValue=returnValue+","+document.form1.elements[i].value;
        }
     }
   }
   var target = $("#target").val();
  opener.form1[target].value=returnValue;
  window.close();
}
</script>



