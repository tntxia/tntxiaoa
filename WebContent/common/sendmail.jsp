<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="com.tntxia.oa.mail.entity.*"%>
<% 

String basePath = request.getContextPath();

String ename1 = (String) session.getValue("ename");
String dept = (String) session.getValue("dept");
String deptjb = (String) session.getValue("deptjb");
String name1 = (String) session.getValue("username");

int non=1;
java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd-HH:mm");
String currentDate=simple.format(new java.util.Date());
java.text.SimpleDateFormat simple1=new java.text.SimpleDateFormat("yyMMdd");
String no=simple1.format(new java.util.Date());

String sendto = (String) request.getAttribute("sendto");
String title = (String) request.getAttribute("title");
String content = (String) request.getAttribute("content");


%>
<html>
<head>
<title>邮件发送</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript">
function isValid(){
    var i,j,strTemp;
    if (form1.mail_to.value==""){
        alert("请您选择收件人!");
  	return false;
    }
   else {
    return true;      
    }
}

function upload(){
	window.open("upload/index_pizhu.jsp","upload","width=100,height=200,toolbar=no,menubar=no,screenX=100,screenY=100");
}

var pizhus = [];

function addPizhu(pizhu){
	
	pizhus.push(pizhu);
	var span = document.getElementById("pizhu_span");
	var str = "";
	for(var i=0;i<pizhus.length;i++){
		str += pizhus[i]+"<br>";
	}
	span.innerHTML = str;
	document.form1.pizhu.value = str;
}

// 查询用户
function searchUser(target){
	window.open('../mail.dispatch?method=userSearch&target='+target,
			'_blank', 
			'height=450, width=930, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')
}

//-->
</script>
<style type="text/css">
.btn{
	background-color: #E1E1E1; 
	font-family: 宋体; 
	font-size: 9pt; 
	border: 1 groove #C3D9FF
}

</style>
</head>
<body>
<form name="form1" method="post" action="<%=basePath %>/mail.dispatch"   onSubmit="return isValid();"> 
<input name="method" type="hidden" value="newMail">
<table height=8 width="650" 
	bordercolor="#CCBE5A" cellspacing="0" 
    bordercolordark="#ffffff" cellpadding="3" 
    align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
    border="1"> 
     <tr> <td bgcolor="#d3d8eb"> 
     	<div align="left"><font size="2" color="#213e9b">
     		<strong>写邮件</strong></font><strong>&nbsp; </strong>
     		<font color="#000000" size="3"></font></div></td>
     </tr> 
	</table><br> <table height=407 width="650px" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
		<tr> <td colspan="2" bgcolor="#e8ebf5">
			<font size="2" color="#000000"> 
  				<input type="button" value="收件人" name="B222" class="btn"
  					 onClick="searchUser('mail_to')"> 
			</font>&nbsp;
		</td>
		<td colspan="3">
			<font size="2" color="#000000"> 
				<input name="mail_to" type="text" id="mail_to" size="60"  readonly  maxlength="100" value="<%=sendto %>"> 
			</font>
			<font size="2" color="#000000">&nbsp; </font>&nbsp;
		</td></tr> 
		<tr> <td colspan="2" bgcolor="#e8ebf5">
			<font size="2" color="#000000"> 
			<input type="button" value="抄送人" name="B2222" class="btn"
			 onClick="searchUser('mail_to2')"> 
			</font>&nbsp;
			</td>
			<td colspan="3"><font size="2" color="#000000"> 
				<input name="mail_to2" type="text" id="mail_to2" size="60"  readonly maxlength="100"> 
			</font><font size="2" color="#000000">&nbsp; </font>&nbsp;</td>
		</tr> 
		<tr> <td colspan="2" bgcolor="#e8ebf5"><font size="2" color="#000000"> 
			<input type="button" value="密送人" name="B2223" class="btn" 
			onClick="searchUser('mail_to3')"> 
			</font>&nbsp;</td>
		<td colspan="3"><font size="2" color="#000000"> 
			<input name="mail_to3" type="text" id="mail_to3" size="60"  readonly  maxlength="100"> 
		</font><font size="2" color="#000000">&nbsp; </font>&nbsp;</td>
		</tr> 
		<tr> <td width="63" bgcolor="#e8ebf5"><font color="#000080" size="2">主　　题</font>&nbsp;</td>
<td colspan="4"><font size="2" color="#000000"> 
<input name="mail_sub" type="text" id="mail_sub" value="<%=title %>" size="65"> </font>&nbsp;</td></tr> 
<tr> <td height="233" colspan="5"><font size="2" color="#000000"> 
        <textarea name="mail_nr" cols="80" rows="15" id="mail_nr"> <%=content %></textarea>
         </font>&nbsp;</td></tr> 
         <tr><td colspan="2" bgcolor="#e8ebf5"><font size="2">批论：</font></td>
         <td>
         <input type="hidden" value="" id="pizhu" name="pizhu">
         <font size="2"><span id="pizhu_span"></span></font></td>
         <td colspan="2" bgcolor="#e8ebf5"><input type="button" onclick="upload()" value="上传文件"></td><td></td>
         </tr>
         <tr> <td height="24" colspan="2" bgcolor="#e8ebf5">
         <font size="2">发件人:</font>&nbsp;</td>
         <td width="298" height="24"><font size="2" color="#000080"> 
<input name="w_dept" type="hidden" id="w_dept2" value="<%=dept%>" size="10"> <input name="deptjb" type="hidden" id="w_deptjb2" value="<%=deptjb%>" size="10"> 
<input name="mail_man" type="hidden" id="w_man2" value="<%=name1%>" size="10"><%=name1%> 
</font>&nbsp;</td><td width="89" height="24" bgcolor="#e8ebf5"><font size="2">发件时间<font color="#000000">:</font></font>&nbsp;</td>
<td width="126" height="24"><font size="2" color="#000080"> 
<input name="mail_datetime" type="hidden" id="w_datetime2" value="<%=currentDate%>" size="10"> 
</font><font size="2" color="#000000"><%=currentDate%></font>&nbsp;</td></tr> <tr> <td height="38" colspan="5"> 
<div align="center"><font size="2"><font color="#000000"> <input type="submit" name="Submit" value="发送邮件" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF"> 
</font></font> <font size="2">
<input type="reset" name="Submit5" value="取消邮件" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF">
<font size="2"><font color="#000000">
<input type="button" value="关    闭" name="B223422" style="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF" onClick="window.close()">
</font></font><font color="#000000"></font></font>&nbsp;</div></td></tr> </table>
<div align="center"><br> <font size="2" color="#000000"> 
</font>&nbsp;</div></form>
</body>
</html>

