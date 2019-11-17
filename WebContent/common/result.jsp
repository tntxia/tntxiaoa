<%@ page contentType="text/html;charset=gb2312" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.tntxia.oa.common.*" %>
<%

String basePath = request.getContextPath();
Boolean success = (Boolean)request.getAttribute("success");
Boolean close = (Boolean)request.getAttribute("close");
Boolean openerReload = (Boolean)request.getAttribute("openerReload");
String msg = (String) request.getAttribute("message");

String target = (String) request.getAttribute("target");

String jump = (String) request.getAttribute("jump");


if(jump!=null){
	if(target==null){
	%>
	<script language="JavaScript">
	window.location = '<%=basePath %><%=jump%>';
	</script>
	<%
	return;
	}
} 

String clickAndJump = (String) request.getAttribute("clickAndJump");

String clickAndJumpLabel = (String) request.getAttribute("clickAndJumpLabel");

List<CommonOpen> openList = (List<CommonOpen>) request.getAttribute("openList");


String ico = null;

if(success!=null && success){
	msg = "操作成功";
	ico = basePath+"/images/common/success.jpg";
}else{
	String errormsg = (String)request.getAttribute("message");
	msg = "操作失败:"+errormsg;
	ico = basePath+"/images/common/alert.jpg";
}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>结果</title>
<link rel=stylesheet href="<%=basePath %>/css/common/result.css" type="text/css">
</head>
<body bgcolor="#ffffff">
<div align="center"> 
<p><img src="<%=ico %>" width="111" height="111"></p>
<hr size=1>
<p>
<font color="#ff0000" size="3"><%=msg %></font></b></p>
  <p>&nbsp;</p>
  
  <%if(clickAndJump!=null){ %>
  <p><font size="2" color="#000000"><a href="<%=clickAndJump%>"><%=clickAndJumpLabel %></a></font></p>
  <%} %>
  <p></p>
</div>
</body>

<script language="JavaScript">

	<%
	if(jump!=null){
		if(target==null){

		%>
		window.location = '<%=basePath %><%=jump%>';
		<%
		}else {
			%>
			window.open("<%=basePath %><%=jump%>", "<%=target%>");
			<%
		}
	} 
	
	if(openList!=null){
		for(CommonOpen open : openList){
			%>
			window.open('<%=basePath%><%=open.getUrl()%>','<%=open.getTarget()%>', '<%=open.getWinOpt()%>');
			<%
		}
	}
	
	if(openerReload!=null && openerReload){
		%>
		window.opener.location.reload();
		<%
	}
	
	if(close!=null && close){
		%>
		window.close();
		<%
	}
	
	%>
</script>

</html>