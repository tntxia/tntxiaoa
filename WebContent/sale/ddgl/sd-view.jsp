<jsp:useBean id="infocrmdb" scope="page" class="infocrmdb.infocrmdb" />
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<%@ page contentType="text/html;charset=GB2312"%>
<html>
<head>
<title>待审批销售订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="JavaScript">
function isValid(){
   if(form1.proid.value=="0"){
        alert("请填写产品信息!");
	return false;
    }
}
</script>

<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
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
</style>
<%
String basePath = request.getContextPath();

NumberFormat nf=NumberFormat.getNumberInstance(); 
  nf.setMaximumFractionDigits(4); 
 nf.setMinimumFractionDigits(4); 
 
double zrale=0.00;
double saletl=0.00;
String hb1="";
String dept = (String) session.getValue("dept");
String username = (String) session.getValue("username");
String restrain_name = (String) session.getValue("restrain_name");
String deptjb = (String) session.getValue("deptjb");
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet rsmod = infocrmdb.executeQuery(modsql);
  if(rsmod.next()) { 
 String submod=rsmod.getString("submod").trim();
String id1=request.getParameter("id");
String money1=request.getParameter("money");
String sql="select  id,number,man,sub,coname,custno,senddate,yj,money,item,mode,source,trade,habitus,datetime,tr,send_date,yf_types,yf_money,tr_addr,tr_man,tr_tel,tbyq,remarks,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,ware_remark from subscribe where id='"+id1+"'";
ResultSet rs=infocrmdb.executeQuery(sql);
if(!rs.next())
  {
    out.println("not have record");
    return;
  }
 String strSQLpros = "select count(*) from ddpro where ddid='"+id1+"' ";
ResultSet prors = infocrmdb.executeQuery(strSQLpros);
prors.next();
int pron = prors.getInt(1);
prors.close();
%>
<body bgcolor="#ffffff" text="#000000" topmargin="0">
<form name="form1" method="post" action="<%=basePath%>/sale/ddgl/do_spdd.jsp" onSubmit="return isValid();"> 
  <br />
  <table width="100%" border="0" cellpadding="3" height="50">
    <tr>
      <td><div align="center"><font size="5"><b><font color="#213e9b">合　同　基　本　信　息</font></b></font></div>
          <hr width="100%" size="1" noshade color="#213e9b">
      </td>
    </tr>
  </table>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"><INPUT TYPE="hidden" name="id" value="<%=id1%>">
    <tr> 
      <td width="14%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">合同编号</FONT><font size="2" color="#000000"> 
          <% 
    String ddnumber=rs.getString("number");
    String man1=rs.getString("man");
	String sub=rs.getString("sub");
	String coname=rs.getString("coname");
	String custno=rs.getString("custno");
    String dd6=rs.getString(7);
	String dd7=rs.getString(8);
	String dd8=rs.getString(9);
	String dd9=rs.getString(10);
	String mode=rs.getString(11);
	int datet=0;
	String dd12=rs.getString(13);
	%>
          </font>&nbsp;</div>      </td>
      <td width="36%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=ddnumber%></font>&nbsp;</div>      </td>
      <td width="13%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">负 责 人</font>&nbsp;</div>      </td>
      <td width="37%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=man1%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%" height="2"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">公司合同号</font>&nbsp;</div>      </td>
      <td height="2"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=sub%></font>&nbsp;</div>      </td>
      <td width="13%"><div align="left"><FONT SIZE="2" COLOR="#000080">客户合同号</font></div></td>
      <td height="2"><%=custno%></td>
    </tr>
    <tr> 
      <td width="14%" height="19"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">客户名称</font>&nbsp;</div>      </td>
      <td colspan="3" height="19"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=coname%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">客户类别</font>&nbsp;</div>      </td>
      <td> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=dd6%></font>&nbsp;</div>      </td>
      <td> 
        <div align="left"><font size="2" color="#000080">其它费用</font>&nbsp;</div>      </td>
      <td> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=dd7%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">货　　币</font>&nbsp;</div>      </td>
      <td> 
        <div align="left"><span class="STYLE1"> 
          <FONT SIZE="2">
          <%String hb=dd8;%>
          <%=hb%></font>&nbsp;</span></div>      </td>
      <td width="13%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">发　　票</font>&nbsp;</div>      </td>
      <td width="37%"> 
        <div align="left"><span class="STYLE1"><FONT SIZE="2"><%=dd9%></font>&nbsp;</span></div>      </td>
    </tr>
    <tr> 
      <td> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">付款方式</font>&nbsp;</div>      </td>
      <td height="17" colspan="3"> 
        <div align="left"><span class="STYLE1"><FONT SIZE="2"><%=mode%></font>&nbsp;</span></div>      </td>
    </tr>
    <tr> 
      <td width="14%"> 
        <div align="left"><font color="#000080" size="2">快递帐号</font>&nbsp;</div>      </td>
      <td width="36%"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=dd12%></font>&nbsp;</div>      </td>
      <td width="13%" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">合同日期</font>&nbsp;</div>      </td>
      <td width="37%" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getDate(15)%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">运输方式</font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(16)%></font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><font size="2" color="#000080">发货日期</font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><font size="2" color="#000000"><%=rs.getString("send_date")%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="2"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">运费支付方</font>&nbsp;</div>      </td>
      <td height="2"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(18)%></FONT><FONT SIZE="2">&nbsp; 
          </font>&nbsp;</div>      </td>
      <td height="2"> 
        <div align="left"><font size="2" color="#000080">运费金额</font>&nbsp;</div>      </td>
      <td height="2"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getDouble(19)%></FONT><FONT SIZE="2">&nbsp; 
          </font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">收货地址</font>&nbsp;</div>      </td>
      <td colspan="3" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(20)%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">收 货 人</font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(21)%></font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">收货电话</font>&nbsp;</div>      </td>
      <td height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(22)%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">特别要求</font>&nbsp;</div>      </td>
      <td colspan="3" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(23)%></font>&nbsp;</div>      </td>
    </tr>
    <tr> 
      <td width="14%" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000080">备　　注</font>&nbsp;</div>      </td>
      <td colspan="3" height="17"> 
        <div align="left"><FONT SIZE="2" COLOR="#000000"><%=rs.getString(24)%></FONT><FONT SIZE="2" COLOR="#000080"> 
          </font>&nbsp;</div></td>
    </tr>
    <tr> 
      <td height="17" colspan="4"><FONT SIZE="2"></font>&nbsp;</td>
    </tr>
  </table>
  <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"><tr> 
      <td width="37"> 
      <div align="left"><font color="#000080" size="2">#</font>&nbsp;</div>      </td>
      <td width="102"><div align="left"><font color="#000080"><font size="2">产品型号</font><font size="2" color="#000080" face="Arial, Helvetica, sans-serif">
        <%
    String strSQLpro = "select id,epro,mpn,supplier,cpro,fypronum,num,unit,salejg,selljg,remark,pricehb,s_tr_date,money2 from ddpro where ddid='"+id1+"' order by id asc";
	ResultSet prs=infocrmdb.executeQuery(strSQLpro);
    int tmpi=0;
	%>
      </font></font>&nbsp;</div></td>
      <td width="94"><div align="left"><font color="#000080"><font size="2">客户型号</font></font>&nbsp;</div></td>
      <td width="65"> 
      <div align="left"><font color="#000080"><font size="2">品牌</font></font>&nbsp;</div>      </td>
      <td width="51"> 
      <div align="left"><font color="#000080"><font size="2">批号</font></font>&nbsp;</div>      </td>
      <td width="52"> 
      <div align="left"><font color="#000080"><font size="2">封装</font></font>&nbsp;</div>      </td>
      <td width="94"> 
      <div align="center"><font color="#000080"><font size="2">数　量</font></font>&nbsp;</div>      </td>
      <td width="139"><div align="center"><font color="#000080" size="2">描述</font></div></td>
      
	  
	  <td width="103"> 
      <div align="center"><font color="#000080"><font size="2">销售单价</font></font>&nbsp;</div>      </td>
	  
	  
      <td width="85"><div align="center"><font color="#000080"><font size="2">成本单价</font></font>&nbsp;</div></td>
      <td width="86"> 
      <div align="center"><font color="#000080"><font size="2">货期</font></font>&nbsp;</div>      </td>
      <td width="157"> 
      <div align="center"><font color="#000080"><font size="2">合　计</font></font>&nbsp;</div>      </td>
    </tr>
    <%while(prs.next()){ 
    
    	String pro_model = prs.getString("epro");
    	int num=prs.getInt("num");
    	
    	double selljg = 0;
    	
    	String sqlWarehouse = "select * from warehouse where pro_model='"+pro_model+"'";
		ResultSet rs1 = infocrmdb.executeQuery(sqlWarehouse);
		if(rs1.next()){
			String pro_yyfw = rs1.getString("pro_yyfw");
			if(pro_yyfw!=null && pro_yyfw.trim().length()>0){
				 selljg = Double.parseDouble(rs1.getString("pro_yyfw"));
			}
			
		}
		rs1.close();
    
    %>
    <tr height="8" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" style="cursor:hand;"> 
      <%int id=prs.getInt(1);%>
      <td width="37" height="8"> 
        <div align="left"><font size="2" color="#000000"> </font><font size="2"><%=tmpi+1%></font><font size="2" color="#000000"><a href="#"  onClick="javascript:window.open('pro-rview.jsp?id=<%=id%>','nw', 'height=260,width=720, top=50, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"></a> 
      </font>&nbsp;</div>      </td>
      <td width="102" height="8"><div align="left"><font size="2" color="#000000"> <a href="#"  onClick="javascript:window.open('pro-rview.jsp?id=<%=id%>','nw', 'height=260,width=720, top=50, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=pro_model%></a> </font>&nbsp;</div></td>
      <td width="94"><%=prs.getString("mpn")%>&nbsp;</td>
      <td width="65" height="8"> 
      <div align="left"><font size="2" color="#000000"><%=prs.getString("supplier")%></font>&nbsp;</div>      </td>
      <td width="51" height="8"> 
      <div align="left"><font size="2" color="#000000"><%=prs.getString("cpro")%></font>&nbsp;</div>      </td>
      <td width="52" height="8"> 
      <div align="left"><font size="2" color="#000000"><%=prs.getString("fypronum")%></font>&nbsp;</div>      </td>
      <td width="94" height="8"> 
        <div align="center"><font size="2" color="#000000"> 
          <%=num%><%=prs.getString("unit")%></font>&nbsp;</div></td>
      <td width="139"><div align="center"><font size="2" color="#000000"><%=prs.getString("remark")%></font>&nbsp;</div></td>
      <td width="103" height="8"> 
        <div align="center"><font size="2" color="#000000"> 
          <%
	double salejg1=0.00;
    String tmpsalejg1=prs.getString("salejg");
    if(tmpsalejg1!=null)
    salejg1=Double.parseDouble(tmpsalejg1);
	double salehj=num*salejg1;
	
	
	%>
          <%String pricehb=prs.getString("pricehb");%>
          <%=nf.format(salejg1)%></font>&nbsp;</div></td>
		  
	  
		  
      <td width="85"><div align="right"><%=selljg%></div></td>
      
	  
	  <td width="86"> 
      <div align="center"><%=prs.getString("s_tr_date")%></div>      </td>
      <td width="157" height="8"> 
      <div align="right"><font size="2" color="#000000"><%=nf.format(salehj)%></font>&nbsp;</div>      </td>
    </tr>
    <% tmpi++;
	   hb1=pricehb;
       saletl=saletl+salehj;
   	 }
   %>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
<tr> <td width="13%">&nbsp;</td><td width="46%">&nbsp;</td><td width="41%"> <div align="right"></div></td></tr> 
<tr> <td width="13%"><FONT SIZE="2" COLOR="#000080">审批情况:</font>&nbsp;</td><td width="46%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td width="41%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td></tr> 
<tr> <td width="12%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td width="47%"><FONT COLOR="#000080" SIZE="2">是否通过:</FONT><FONT SIZE="2" COLOR="#000000"><%String state=rs.getString("state").trim();out.print(state);%></font>&nbsp;</td><td width="41%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td></tr> 
<tr> <td width="12%" height="2"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td width="47%" height="2"><FONT COLOR="#000080" SIZE="2">审批领导:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("spman")%></font>&nbsp;</td><td width="41%" height="2"><FONT COLOR="#000080" SIZE="2">审批时间:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("spdate")%></font>&nbsp;</td></tr> 
<tr> <td width="12%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td colspan="2"><FONT COLOR="#000080" SIZE="2">审批意见:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("spyj")%></font>&nbsp;</td></tr> 
<tr> <td width="12%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td colspan="2"><FONT COLOR="#000080" SIZE="2">是否复审:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("fif")%></FONT><FONT SIZE="2">　　　　　　　　<font color="#000080">复审人:</font></FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("cwman")%>　　　</FONT><FONT COLOR="#000080" SIZE="2">复审日期:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("cwdate")%></font>&nbsp;</td></tr> 
<tr> <td><FONT SIZE="2"></font>&nbsp;</td><td colspan="2"><FONT COLOR="#000080" SIZE="2">复审意见:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("cwyj")%></FONT><FONT SIZE="2">　</font>&nbsp;</td></tr> 
<tr> <td width="12%"><FONT SIZE="2">&nbsp;</font>&nbsp;</td><td colspan="2"><FONT COLOR="#000080" SIZE="2">仓库意见:</FONT><FONT SIZE="2" COLOR="#000000"><%=rs.getString("ware_remark")%></FONT><FONT SIZE="2">　　　</font>&nbsp;</td></tr> 
</table><div align="left"></div><div align="center"><font size="2" color="#000000"> 
</font><font size="2"> <input type="hidden" name="proid" size="20" style="border: 1 inset #C3D9FF" value="<%=pron%>"> 
<input type="hidden" name="id2" size="20" style="border: 1 inset #C3D9FF" value="<%=id1%>"> 
</font><font color="#000000" size="2" face="Arial, Helvetica, sans-serif"> </font> 
<font size="2" color="#000000"> </font><font color="#000000" size="2"><%if(state.equals("待审批")){ if(submod.equals("有")){%> 
<INPUT TYPE="button" VALUE="批量选择产品" NAME="B22342223" STYLE="background-color: #E1E1E1; font-family: 宋体; font-size: 9pt; border: 1 groove #C3D9FF" ONCLICK="window.open('<%=basePath%>/sale/ddgl/sspcnwarehousem.jsp?id=<%=id1%>&hb=<%=dd8%>&ddnumber=<%=ddnumber%>','_self', 'height=260, width=750, top=200, left=100, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
<input type="button" value="编辑订单" onClick="window.open('<%=basePath%>/sale/ddgl/editdd.jsp?id=<%=id1%>','_self')"> 
</font><font color="#000000" size="2" face="Arial, Helvetica, sans-serif"> </font> 
<%}%> <font color="#000000" size="2">  
<%}%> </font>&nbsp;</div></form>
</body>
</html>
<%
 rsmod.close();
 rs.close();
 infocrmdb.closeStmt();
 infocrmdb.closeConn();
 } 
%>
