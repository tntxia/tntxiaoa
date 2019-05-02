<jsp:useBean id="infocrmdb" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="infocrmdb2" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="infocrmdb3" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="rmb" scope="page" class="com.infoally.currency.Rmb"/>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<%@ page contentType="text/html;charset=GB2312"%>
<%
String basePath = request.getContextPath();
String cid=request.getParameter("cid");
String strSQLq="select   companyname,companyaddr,companytel,companyfax,companybank,companynumber,companyemail,companynet,picpath from company where id='"+cid+"'";
ResultSet rss=infocrmdb.executeQuery(strSQLq);
if(!rss.next())
 {
   out.println("请通知系统管理员定义公司信息");
   return;
 }
 String q_company=rss.getString("companyname");
 String q_addr=rss.getString("companyaddr");
 String q_tel=rss.getString("companytel");
 String q_fax=rss.getString("companyfax");
 String companybank=rss.getString("companybank");
 String companynumber=rss.getString("companynumber");
 String q_email=rss.getString("companyemail");
 String q_net=rss.getString("companynet");
 String picpath=rss.getString("picpath");
 rss.close();
 
 String username = (String) session.getValue("username");

NumberFormat nf=NumberFormat.getNumberInstance(); 
nf.setMaximumFractionDigits(4); 
nf.setMinimumFractionDigits(4); 


java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String currentDate=simple.format(new java.util.Date());
 
double zrale=0.00;
double saletl=0.00;
String hb1="";
String id1=request.getParameter("id");
String money=request.getParameter("money");

String sql="select  id,number,man,subck,sub,coname,senddate,yj,money,item,mode,source,trade,habitus,datetime,tr,send_date,tr_addr,tr_man,tr_tel,tbyq,remarks,yf_types,yf_money,state,spman,spdate,spyj,fif,cwman,cwdate,cwyj,ware_remark,custno,state from subscribe where id='"+id1+"'";

ResultSet rs=infocrmdb.executeQuery(sql);

if(!rs.next())
  {
    out.println("not have record");
    return;
  }

String ddnumber=rs.getString("number").trim();
	
	
	
    String man1=rs.getString("man");
	String subck=rs.getString("subck");
	String sub=rs.getString("sub");
	String coname=rs.getString("coname").trim();
    String co_number=rs.getString(7);
	String dd7=rs.getString(8);
	String dd8=rs.getString(9);
	String dd9=rs.getString(10);
	String mode=rs.getString(11);
	int datet=rs.getInt(12);
	String dd13=rs.getString(13);
	String dd15 =rs.getString(15).substring(0,10);
    String dd16 =rs.getString(16);
	String send_time= rs.getString("send_date");
	String dd18=rs.getString(18);
	String dd19=rs.getString(19);
	String dd20=rs.getString(20);
	String dd21=rs.getString(21);
	String dd22=rs.getString(22);
    String dd23=rs.getString(23);
	String dd24=rs.getString(24);
	String state=rs.getString("state");
	String spman=rs.getString("spman");
	String spdate=rs.getString("spdate");
	String spyj =rs.getString("spyj");
	String fif=rs.getString("fif");
	String cwman=rs.getString("cwman");
	String cwdate=rs.getString("cwdate");
	String cwyj=rs.getString("cwyj");
	String ware_remark=rs.getString("ware_remark");
	String custno=rs.getString("custno");
	String pro_add="无";
	String trview_yes="无";
	
String restrain_name = (String) session.getValue("restrain_name");
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet sqlRstyprd = infocrmdb2.executeQuery(modsql);
 while(sqlRstyprd.next())
 { 
  trview_yes=sqlRstyprd.getString("trview_yes").trim();
  pro_add=sqlRstyprd.getString("outadd").trim();
  }
 sqlRstyprd.close();

%>
<html>
<head>
<title>送货单</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/warehouse/out/print.css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/warehouse/out/print.js"></script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0">
<p>&nbsp;</p>
<!-- 编辑工具条start -->
<div id="edit_tool_bar">
	<input type="button" value="编辑送货单" onclick="edit()"> 
	<input type="button" value="隐藏工具条" onclick="hideEditToolBar()">
	<input type="button" value="完成编辑" onclick="finishEdit()">
	字体调整：
	<select id="fontChange" onchange="changeFont()">
		<option value="1">一号字体</option>
		<option value="2" selected>二号字体</option>
		<option value="3">三号字体</option>
	</select>
</div>
<!-- 编辑工具条end -->

<div id="printBoddy" class="font2">
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="0"> 
<tr> <td height="14">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="17%" rowspan="5"> 
          <div align="left"><font size="2"><img src="../../custo_log/logo.gif" width="105" height="59.5" border="0"></font></div>          </td>
          <td width="50%"> 
          <div align="left"><font color="#213e9b" size="5"><strong><font color="#000000" face="黑体"><%=q_company%></strong></font></font><font color="#000000" size="3">&nbsp;</font></div>          </td>
          <td width="33%"><font size="1">合同编号:<%=ddnumber%>&nbsp;</font></td>
        </tr>
        <tr> 
          <td width="50%"> 
          <div align="left"><font color="#000000" size="2"><%=q_addr%></font>&nbsp;</div>          </td>
          <td width="33%"><font size="1">客户名称：<%=coname%></font></td>
        </tr>
        <tr> 
          <td width="50%"> 
          <div align="left"><font color="#000000" size="2">电话:<%=q_tel%> 传真:<%=q_fax%></font>&nbsp;&nbsp;&nbsp;</div>          </td>
          <td width="33%"><font size="1">电　　话：<%=dd20%> </font></td>
        </tr>
        <tr> 
          <td> 
            <div align="left"><font color="#000000" size="2"><%=q_net%> <%=q_email%></font> </div></td>
          <td><font size="1">日　　期：<%=currentDate%></font></td>
        </tr>
        <tr> 
          <td colspan="2"> 
            <div align="left"></div>          </td>
        </tr>
      </table>
    </td></tr> 

<tr> <td height="25"> 
      <div align="center">
        <p>&nbsp;</p>
        <p><span class="STYLE1"><font size="5" face="黑体">送货单</font></span><font size="2">
          <%
	String coaddr="";
  String cofax="";
  String cokhyh="";
  String coyhzh="";
  String nsnumber="";
String hb="";
	String sqlc="select  coaddr,cofax,cokhyh,coyhzh,nsnumber from client where coname='"+coname+"'";
ResultSet rsc=infocrmdb.executeQuery(sqlc);
if(rsc.next())
  {
   coaddr=rsc.getString("coaddr");
   cofax=rsc.getString("cofax");
   cokhyh=rsc.getString("cokhyh");
   coyhzh=rsc.getString("coyhzh");
   nsnumber=rsc.getString("nsnumber");
  }rsc.close();
	%>
          </font>&nbsp;&nbsp;</p>
      </div>
    </td>
  </tr> 
</table><br>
<table width="95%" border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td><span class="STYLE4">#</span></td> 
    <td width="25%"> 
      <div align="left" class="STYLE4" style="text-align:center">
      <font color="#000000">产品型号</font>&nbsp;</div>    </td>
    <td> 
    <div align="center" class="STYLE4"><font color="#000000">批号</font>&nbsp;</div></td>
    
    <td> 
    <div align="center" class="STYLE4"><font color="#000000">数量</font>&nbsp;</div></td>
    
    <td><span class="hide_item">操作</span></td>
    
  </tr>
  <tr height="5">
    <td height="5" colspan="7" nowrap><hr></td>
  </tr>
  <%
   String strSQLpro = "select id,epro,supplier,cpro,fypronum,num,s_num,unit,salejg,pricehb,wid,s_tr_date,wid,p_check,rale,remark from ddpro where  ddid='"+id1+"' order by id asc";
	ResultSet prs=infocrmdb2.executeQuery(strSQLpro);
   int tmpi=0;
	%>
  <% 
  
  int b=1;
   int c = 0;
   
  while(prs.next()){ %>
  
  <tr height="5" align="center" name="productLine">
    <td nowra align="center"p><span class="STYLE4"><%=b%>&nbsp;</span></td> 
    <%int id=prs.getInt(1); c++;
	String promode=prs.getString("epro");                 // 型号
	String pro_name=prs.getString("supplier");            // 品牌
	//String pro_supplier=prs.getString("pro_supplier");
 	
	%>
	
    <td height="5" nowrap align="center"> 
    <div  class="STYLE4" style="text-align:center">
    <font color="#000000">
    <span id="edit_0_<%=c %>"><%=promode%></span> 
    <input id="edit_0_input_<%=c %>" type="text" value="<%=promode%>" class="hide_item">
    </font>&nbsp;
    </div>    </td>
    
    <td height="5" nowrap align="center">
    <div align="left" style="text-align:center">
    <span  id="edit_1_<%=c %>"><%=pro_name%></span>
    </div>
    <input id="edit_1_input_<%=c %>" type="text" value="<%=pro_name%>" class="hide_item">
    </td>
    
    <td height="5" nowrap align="center"> 
    <div align="center" class="STYLE4" style="text-align:center">
      <span  id="edit_2_<%=c %>">
        <%String num=prs.getString("fypronum");%>
        <%=num%></span>
    </div>
    <input id="edit_2_input_<%=c %>" type="text" value="<%=num%>" class="hide_item">
    </td>
        
    <td><input class="hide_item" type="button" value="删除" onclick="del(<%=c %>)"></td>
    
  </tr>
  <%  b++;
   }  
  
   int n=0;
   
  int h= 0;
  while(n>0){    %> 
  <tr>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
    <td height="23">&nbsp;</td>
  </tr><%  n=n-1;  h++;}  %>
  
  
  <tr>
    <td colspan="7" height="12"><hr></td>
  </tr>
  <tr> 
    <td colspan="7" height="35"  ><div align="left" class="STYLE4">
      备注: 1.客户收到货品后如有疑问，请于收货当天检查提出，否则视为验收合格。<br>
　　　 2.货品须退还时，请凭此单并将原包装于___天整批退还，损坏或上机安装后拆回的货品一律不予退换，多谢合作！<br>
    </div></td>
  </tr>
  <tr> 
    <td height="45" colspan="7"> 
      <div align="right" class="STYLE4"> 
        
        <hr width="100%">
        
        <table width="100%" border="0" align="center" cellpadding="5" cellspacing="0">
          <tr>
            
            <td width="14%" class="STYLE4"><span class="STYLE4">制单人</span>:<%=username%>&nbsp;</td>
            <td class="STYLE4"><div align="center">收货单位（盖章）及经手人签字</div></td>
            <td class="STYLE4"> 收款人：</td>
            <td width="18%" class="STYLE4">&nbsp;</td>
            <td width="18%" class="STYLE4">经手人:<%=man1%>&nbsp;</td>
          </tr>
        </table>
  </tr>
</table>

</div>

</body>
</html>
