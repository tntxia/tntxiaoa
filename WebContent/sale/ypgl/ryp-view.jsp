<jsp:useBean id="infocrmdb" scope="page" class="infocrmdb.infocrmdb" />
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<%@ page contentType="text/html;charset=GB2312"%>
<%
	String basePath = request.getContextPath();
%>
<html>
<head>
<title>待入库样品</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script>
	var webRoot = "<%=basePath%>";
</script>
<script type="text/javascript" src="/static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/warehouse/in/sample-in-view.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #000000}
-->
</style>
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
.STYLE6 {
	color: #FF0000
}
.STYLE7 {font-size: 12px}
</style>
<% NumberFormat nf=NumberFormat.getNumberInstance(); 
  nf.setMaximumFractionDigits(4); 
 nf.setMinimumFractionDigits(4); 
 
double zrale=0.00;
double saletl=0.00;
String hb1="";
String username = (String) session.getValue("username");
 String dept = (String) session.getValue("dept");
 String deptjb = (String) session.getValue("deptjb");
 String restrain_name = (String) session.getValue("restrain_name");
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet rsmod = infocrmdb.executeQuery(modsql);
  if(rsmod.next()) { 
 String submod=rsmod.getString("r_sam_mod").trim();
 String subdel=rsmod.getString("r_sam_del").trim();
String id1=request.getParameter("id");
String money1=request.getParameter("money");
String sql="select  id,number,man,sub,coname,senddate,yj,linkman,money,habitus,datetime,delivery_terms,delivery_date,fveight,insurance,airport,tbyq,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj from sample where id='"+id1+"'";
ResultSet rs=infocrmdb.executeQuery(sql);
if(!rs.next())
  {
    out.println("not have record");
    return;
  }
  String dqnum="1";
%>
<body bgcolor="#ffffff" text="#000000" topmargin="0">
  <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"><INPUT TYPE="hidden" name="id" value="<%=id1%>"> <TR BGCOLOR="#d3d8eb"> <td width="14%" height="29" bgcolor="#ffffff"> 
<div align="left"> <font size="2" color="#000000">
    <% 
    String ddnumber=rs.getString("number");
    String man1=rs.getString("man");
	String sub=rs.getString("sub");
	String coname=rs.getString("coname");
    String dd6=rs.getString(6);
	String dd7=rs.getString(7);
	String dd8=rs.getString(8);
	String dd9=rs.getString(9);
	java.sql.Date dd14=rs.getDate(11);
	String dd15=rs.getString(12);
	String dd16=rs.getString(13);
	String dd17=rs.getString(14);
	String dd18=rs.getString(15);
	String dd21=rs.getString(16);
	%> </font>&nbsp;</div></td><td height="29" colspan="3" bgcolor="#ffffff"> <div align="right"> 
<font size="2" color="#000000"> </font><font size="2"> 
<input type="hidden" name="id" size="20" style="border: 1 inset #C0C0C0" value="<%=id1%>"> 
</font><font color="#000000" size="2" face="Arial, Helvetica, sans-serif"> </font> 
<font size="2" color="#000000"> </font><font color="#000000" size="2"> </font> 
<font color="#000000" size="2"> </font><font size="2" color="#000000"> </font>&nbsp;</div></td></tr> 
<tr> <td width="14%" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">样品编号</FONT></span></td>
<td width="36%" bgcolor="#e8ebf5"> 
<FONT SIZE="2" COLOR="#000000"><%=ddnumber%></FONT><FONT SIZE="2"> <input name="fynumber" type="hidden" id="fynumber" style="border: 1 inset #C0C0C0" value="<%=ddnumber%>" size="20"> 
</font>&nbsp;</td><td width="13%" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">负 责 人</FONT></span></td><td width="37%" bgcolor="#e8ebf5"> 
<FONT SIZE="2" COLOR="#000000"><%=man1%></font>&nbsp;</td></tr> <tr> <td width="14%" height="2" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">出库仓库</FONT></span></td>
<td colspan="3" height="2"><FONT SIZE="2" COLOR="#000000"><%=sub%>　</font>&nbsp;</td></tr> 
<tr> <td width="14%" height="19" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">客户名称</FONT></span></td>
<td colspan="3" height="19"><FONT SIZE="2" COLOR="#000000"><%=coname%></FONT><FONT SIZE="2"> 
<input name="coname" type="hidden" id="coname" style="border: 1 inset #C0C0C0" value="<%=coname%>" size="20"> 
</font>&nbsp;</td></tr> <tr> <td height="19" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">送货地址</FONT></span></td>
<td colspan="3" height="19"><FONT SIZE="2" COLOR="#000000"><%=dd21%></font>&nbsp;</td></tr> <tr> <td width="14%" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">客户编号</FONT></span></td>
<td><FONT SIZE="2" COLOR="#000000"><%=dd6%></FONT><FONT SIZE="2"> 
<input name="co_number" type="hidden" id="co_number" style="border: 1 inset #C0C0C0" value="<%=dd6%>" size="20"> 
</font>&nbsp;</td><td bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">联系电话</FONT></span></td>
<td><FONT SIZE="2" COLOR="#000000"><%=dd7%></font>&nbsp;</td></tr> 
<tr> <td bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">联 系 人</FONT></span></td>
<td><FONT SIZE="2" COLOR="#000000"><%=dd8%></font>&nbsp;</td><td width="13%" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">样品货币</FONT></span></td>
<td width="37%"><FONT SIZE="2" COLOR="#000000"> 
<%String hb=dd9;%> <%=hb%></font>&nbsp;</td></tr> <tr> <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">登记日期</FONT></span></td>
<td height="17"> 
<FONT SIZE="2" COLOR="#000000"><%=dd14%></font>&nbsp;</td><td bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">运输方式</FONT></span></td>
<td> 
<FONT SIZE="2" COLOR="#000000"><%=dd15%></font>&nbsp;</td></tr> <tr> <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">运费负担</FONT></span></td>
<td height="17"> 
<FONT SIZE="2" COLOR="#000000"><%=dd16%></font>&nbsp;</td><td height="17" bgcolor="#e8ebf5">&nbsp;</td>
<td height="17"><FONT SIZE="2"></font>&nbsp;</td></tr> 
<tr> <td height="2" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">运　　费</FONT></span></td>
<td height="2"><FONT SIZE="2" COLOR="#000000"><%=dd17%><%=hb%></font>&nbsp;</td><td height="2" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">押　　金</FONT></span></td>
<td height="2"><FONT SIZE="2" COLOR="#000000"><%=dd18%><%=hb%></font>&nbsp;</td></tr> 
<tr> <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">特别要求</FONT></span></td>
<td colspan="3" height="17"><FONT SIZE="2" COLOR="#000000"><%=rs.getString("tbyq")%>　</font>&nbsp;</td></tr> 
<tr> <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">备　　注</FONT></span></td>
<td colspan="3" height="17"><FONT SIZE="2" COLOR="#000000"><%=rs.getString("remarks")%></FONT><FONT SIZE="2" COLOR="#000080"> 
<%
    String strSQLpro = "select id,epro,cpro,num,pro_snum,unit,salejg,pricehb,rale_types,rale,pro_r_date,pro_sc_num,wid from sam_pro where ddid='"+id1+"' order by id asc";
	ResultSet prs=infocrmdb.executeQuery(strSQLpro);
    int tmpi=0;
	%>　 </font>&nbsp;</td></tr> </table>
  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"> 
<tr bgcolor="#d3d8eb"> <TD WIDTH="123" > <DIV ALIGN="left" class="STYLE1"><FONT SIZE="2">产品型号</font>&nbsp;</div></TD><TD WIDTH="174" > 
<DIV ALIGN="left" class="STYLE1"><FONT SIZE="2">产品批号
</font>&nbsp;</div></TD><TD WIDTH="158" ><span class="STYLE1"><FONT SIZE="2">预计归还日期</FONT></span></TD>
<TD WIDTH="94" > 
<DIV ALIGN="right" class="STYLE1"><FONT SIZE="2">样品数量</font>&nbsp;</div></TD><TD WIDTH="118" > 
<DIV ALIGN="right" class="STYLE1"><FONT SIZE="2">已归还数量</font>&nbsp;</div></TD><TD WIDTH="116" > 
<DIV ALIGN="right" class="STYLE1"><FONT SIZE="2">本次归还数量</font>&nbsp;</div></TD><TD WIDTH="162" > 
<DIV ALIGN="right" class="STYLE1"><FONT SIZE="2">仓库</font>&nbsp;</div></TD>
</TR> <%while(prs.next()){ %> 
<TR HEIGHT="8" ONMOUSEOVER="this.bgColor='#B5DAFF'" ONMOUSEOUT="this.bgColor='#ffffff'" STYLE="cursor:hand;"> 
<%int id=prs.getInt(1);%> <TD WIDTH="123" HEIGHT="8" > <DIV ALIGN="left"><FONT SIZE="2" COLOR="#000000"> 
<%String pro_model=prs.getString("epro");out.print(pro_model);%> </font>&nbsp;</div></TD><TD WIDTH="174" HEIGHT="8" > 
<DIV ALIGN="left"><FONT SIZE="2" COLOR="#000000"><%String pro_name=prs.getString("cpro");out.print(pro_name);%></font>&nbsp;</div></TD>
<TD WIDTH="158" HEIGHT="8" ><FONT SIZE="2" COLOR="#000000"><%=prs.getString("pro_r_date")%></font>&nbsp;</td><TD WIDTH="94" HEIGHT="8" > 
<DIV ALIGN="right"><FONT SIZE="2" COLOR="#000000"> <%int num=prs.getInt("num");%> 
<%=num%></font>&nbsp;</div></TD><TD WIDTH="118" HEIGHT="8" > <DIV ALIGN="right"><FONT SIZE="2" COLOR="#000000"> 
<%int pro_snum=prs.getInt("pro_snum");%> <%=pro_snum%></font>&nbsp;</div></TD><TD WIDTH="116" HEIGHT="8" > 
<DIV ALIGN="right"><FONT SIZE="2" COLOR="#000000"> <%int pro_sc_num=prs.getInt("pro_sc_num");
if(pro_sc_num>0){
String rk_states="ok";
}
%> <%=pro_sc_num%></font>&nbsp;</div></TD><%		  	String wid=prs.getString("wid").trim();
String sqlw="select  pro_num,pro_addr from warehouse where pro_model='"+pro_model+"' and pro_name='"+pro_name+"' ";
ResultSet rsw=infocrmdb.executeQuery(sqlw);
if(!rsw.next())
  {
    out.println("仓库暂无该型号产品，该型号产品必须重新增加！");
    return;
  }
	String pro_addr=rsw.getString("pro_addr");
	rsw.close();
	%> <TD WIDTH="162" HEIGHT="8" > <DIV ALIGN="right"><FONT SIZE="2" COLOR="#000000"><%=pro_addr%></font>&nbsp;</div></TD></TR> 
<% tmpi++;
   	 }
   %> </TABLE>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
<tr> <td width="13%">&nbsp;</td><td width="46%">&nbsp;</td><td width="41%"> <div align="right"></div></td></tr> 
<tr> <td width="13%"><span class="STYLE1"><font size="2" face="Arial, Helvetica, sans-serif">审批情况:</font></span></td>
<td width="46%">&nbsp;</td>
<td width="41%">&nbsp;</td>
</tr> 
<tr> <td width="12%">&nbsp;</td>
<td width="47%"><span class="STYLE1"><font size="2">是否通过:<%=rs.getString("state")%></font></span></td>
<td width="41%">&nbsp;</td>
</tr> 
<tr> <td width="12%" height="2">&nbsp;</td>
<td width="47%" height="2"><span class="STYLE1"><font size="2">审批领导:<%=rs.getString("spman")%></font></span></td>
<td width="41%" height="2"><span class="STYLE1"><font size="2">审批时间:<%=rs.getString("spdate")%></font></span></td>
</tr> 
<tr> <td width="12%">&nbsp;</td>
<td colspan="2"><span class="STYLE1"><font size="2">审批意见:<%=rs.getString("spyj")%></font></span></td>
</tr> 
<tr> <td><span class="STYLE1"></span></td>
<td><span class="STYLE1"><font size="2">是否复审:<%=rs.getString("fif")%></font></span></td>
<td><span class="STYLE1"></span></td>
</tr> 
<tr> <td width="12%">&nbsp;</td>
<td><span class="STYLE1">复 审 人:<%=rs.getString("cwman")%></span></td>
<td><span class="STYLE1"><font size="2">复审日期:<%=rs.getString("cwdate")%></font></span></td>
</tr> 
<tr> <td width="12%">&nbsp;</td>
<td colspan="2"><span class="STYLE1"><font size="2">复审意见:<%=rs.getString("cwyj")%>　　　　　</font></span></td>
</tr> 
</table>
  <div align="center">
    <p><br>
             
<FONT COLOR="#000000" size="2"><span class="STYLE3"><span class="STYLE7">
<a href="#" ONCLICK="window.open('dofh1.jsp?id=<%=id1%>','_self')">返回申请人</a>
</span></span></FONT><span class="STYLE7"> | 

<button id="checkInBtn">入库确认</button>  | <a href="#" onClick="window.close()">关闭窗口</a>
</div>
<div>(请务必不要重复点击入库确认，你只能操作一次或再有问题将该单返回去)</div>

</body>
</html>
<%
 rsmod.close();
 infocrmdb.closeStmt();
 infocrmdb.closeConn();
 } 
%>
