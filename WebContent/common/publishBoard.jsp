<%@ page contentType="text/html;charset=gb2312"%>
<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" />
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="java.util.*"%>
<%
	String dept = (String) session.getValue("dept");

	java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
			"yyyy-MM-dd-HH-mm");
	String Date = simple.format(new java.util.Date());
	java.text.SimpleDateFormat simple1 = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");
	String cDate = simple1.format(new java.util.Date());
%>
<html>
<head>
<title>在线人数</title>
<style type="text/css">
td,p,select,input,textarea {
	font-size: 12px
}

.l15 {
	line-height: 150%
}

.l13 {
	line-height: 130%
}

.f10 {
	font-size: 10px
}

.f14 {
	font-size: 14.9px
}

.bdrclr01 {
	color: #000000;
	border-color: #000000
}

.c03 {
	color: #000000;
	border-color: #000000;
}

A:link {
	text-decoration: none;
	color: #0000ff;
}

A:visited {
	text-decoration: none;
	color: #800080;
}

A:active {
	text-decoration: none;
	color: #0000ff;
}

A:hover {
	text-decoration: underline;
	color: #0000ff;
}

.STYLE1 {
	color: #000000;
}

</style>
</head>
<body bgcolor="#ffffff">
	<center>
		<div align="left">
			<center>
				<div align="left">
					<font size=2> <a href="#"
						onClick="window.open('../einfo/index.htm','_blank', 'height=500, width=650, top=10, left=70, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><img
							src="../image/new_lt.gif" width="87" height="25" border="0">
					</a>&nbsp;<a href="#"
						onClick="window.open('../public/ngz.jsp','_blank', 'height=500, width=650, top=10, left=70, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><img
							src="../image/new_add.gif" width="87" height="25" border="0">
					</a>
					</font><font size=2>&nbsp;<a href="#"
						onClick="window.open('../webmail/infomail.jsp','mail', 'height=500, width=650, top=50, left=70, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><img
							src="../image/mail.gif" width="87" height="25" border="0">
					</a>
					</font><font size="2" color="#000000"> <%
 	String strSQLp = "";
 	String username1 = (String) session.getValue("username");
 	strSQLp = "select id,titel,username,fvdate,dept from pub_table where dept  like '%"
 			+ dept
 			+ "%'  and  view_date>='"
 			+ cDate
 			+ "'   and  states='审批通过'  or dept='全体员工'  and  view_date>='"
 			+ cDate
 			+ "'  and  states='审批通过'   or   username='"
 			+ username1
 			+ "'   and  view_date>='"
 			+ cDate
 			+ "'   and  states='审批通过'   order by fvdate desc";
 	ResultSet prs = einfodb.executeQuery(strSQLp);
 	int tmpi = 0;
 %> </font><br>
					<table height=8 width="100%" bordercolor="#CCBE5A" cellspacing="0"
						bordercolordark="#ffffff" cellpadding="3" align="center"
						bgcolor="#ffffff" bordercolorlight="#7f9db9" border="1">
						<%
							while (prs.next()) {
								int id = prs.getInt(1);
								String titel = prs.getString("titel");
								String man = prs.getString("username");
								String fvdate = prs.getString("fvdate");
								String dept1 = prs.getString("dept");
						%>
						<tr height="8" onMouseOver="this.bgColor='#B5DAFF'"
							onMouseOut="this.bgColor='#ffffff'" style="cursor: hand;">
							<td width="57" height="8"><font size="2" color="#000000"><a
									href="#"
									onClick="javascript:window.open('../public/gz-view.jsp?id=<%=id%>','nw','height=300,width=750,left=25,top=10,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=tmpi + 1%>
								</a>
							</font>&nbsp;</td>
							<td width="760" height="8">
								<div align="left">
									<font size="2" color="#000000"><a href="#"
										onClick="javascript:window.open('../public/gz-view.jsp?id=<%=id%>','nw','height=300,width=750,left=25,top=10,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=titel%></a>
									</font>&nbsp;
								</div>
							</td>
							<td width="115" height="8">
								<div align="left">
									<font size="2" color="#000000"><%=man%></font>&nbsp;
								</div>
							</td>
							<td width="101" height="8">
								<div align="left">
									<font size="2" color="#000000"><%=fvdate%></font>&nbsp;
								</div>
							</td>
							<td width="91" height="8">
								<div align="left">
									<font size="2" color="#000000"><%=dept1%></font>&nbsp;
								</div>
							</td>
						</tr>
						<%
							tmpi++;
							}
						%>
					</table>
					<p>
						<font color="#990000" size="2"> </font>
					</p>
				</div>
			</center>
		</div>
	</center>
	<div align="left">
		<font size="2"> </font><font size="2"> </font>&nbsp;
	</div>
</body>
</html>
