<jsp:useBean id="infocrmdb" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="rmb" scope="page" class="com.tntxia.currency.Rmb"/>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<%@ page contentType="text/html;charset=GBK"%>
<% 

String round = request.getParameter("round");
int roundInt=2;
try{
	roundInt = Integer.parseInt(round);
}catch(Exception e){
	
}

String basePath = request.getContextPath();
NumberFormat nf=NumberFormat.getNumberInstance(); 
nf.setMaximumFractionDigits(roundInt); 
nf.setMinimumFractionDigits(roundInt); 
NumberFormat nfs=NumberFormat.getNumberInstance(); 
nfs.setMaximumFractionDigits(roundInt);
nfs.setMinimumFractionDigits(roundInt);
 
SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
String number1=simple.format(new java.util.Date());

double zrale=0.00;
double saletl=0.00;
String hb1="";
String hb="";
double rale=0.00;
String dept = (String) session.getValue("dept");
String username = (String) session.getValue("username");
String cid=request.getParameter("cid");
String id1=request.getParameter("id");
String money1=request.getParameter("money");
String sql="select id,custno,number,man,sub,coname,senddate,yj,money,item,mode,source,trade,habitus,datetime,send_date,tr,tr_addr,tr_man,tr_tel,tbyq,remarks,state,spman,spdate,yf_money,fif,cwman,cwdate,cwyj from subscribe where id='"+id1+"'";
ResultSet rs=infocrmdb.executeQuery(sql);
if(!rs.next())
  {
    out.println("not have record");
    return;
  }
    String ddnumber=rs.getString("custno");
	String number2=rs.getString("number");
    String man1=rs.getString("man").trim();
	String sub=rs.getString("sub");
	String coname=rs.getString("coname");
   
	double qt_fy=rs.getDouble("yj");
	String dd8=rs.getString(9);
	String dd9=rs.getString(10).trim();
	String mode=rs.getString(11).trim();
	int datet=0;
	String dd12=rs.getString(13);
	String dd13=rs.getString(14);
	java.sql.Date dd14=rs.getDate(15);
	String send_date=rs.getString("send_date");
	String tr=rs.getString("tr");
	String tr_addr=rs.getString("tr_addr");
	String linkman=rs.getString("tr_man");
	String tr_tel=rs.getString("tr_tel");
	String tbyq=rs.getString("tbyq").trim();
	String remark=rs.getString("remarks").trim();
    String state=rs.getString("state");
    String spman=rs.getString("spman");
    String spdate=rs.getString("spdate");
	double yf=rs.getDouble("yf_money");
	 if(dd9.equals("增值发票17%")){rale=0.17;}
	 if(dd9.equals("增值发票4%")){rale=0.04;}
	 if(dd9.equals("普通发票6%")){rale=0.06;}
   String cofax="";
   String co_code="";
   String coaddr="";
   String cotel="";
   String cojsfs="";
	String sqlc="select  coaddr,co_code,cotel,cofax,cojsfs,codzyj,conet from client where coname='"+coname+"'  and username='"+man1+"'";
ResultSet rsc=infocrmdb.executeQuery(sqlc);
if(rsc.next())
  {
   coaddr=rsc.getString("coaddr");
   co_code=rsc.getString("co_code").trim();
   cotel=rsc.getString("cotel").trim();
   cofax=rsc.getString("cofax");
   cojsfs=rsc.getString("cojsfs");
  }
  else {
	String sqlcc="select coname,coaddr,co_code,cotel,cofax,cojsfs,codzyj,conet from client where coname='"+coname+"' ";
ResultSet rscc=infocrmdb.executeQuery(sqlcc);
if(rscc.next())
  {
   coaddr=rscc.getString("coaddr");
   co_code=rscc.getString("co_code").trim();
   cotel=rscc.getString("cotel").trim();
   cofax=rscc.getString("cofax");
   cojsfs=rscc.getString("cojsfs");
  }
  }
  
 String strSQLq="select * from company  where  id='"+cid+"'";
 ResultSet rss=infocrmdb.executeQuery(strSQLq);
 if(!rss.next())
  {
    out.println("请通知系统管理员定义公司信息");
    return;
  }
  String q_company=rss.getString("companyname");
    String company2=rss.getString("companyname2");
  String q_addr=rss.getString("companyaddr");
  String companytel=rss.getString("companytel");
  String q_fax=rss.getString("companyfax");
  String bank_dm=rss.getString("bank_dm");
  String companybank2=rss.getString("companybank");
  String companynumber2=rss.getString("companynumber");
  String com_bank_code=rss.getString("com_bank_code");
  String com_sy_code= rss.getString("com_sy_code");
  String com_sy_name =rss.getString("com_sy_name");
  String q_email=rss.getString("companyemail");
  String lxr=rss.getString("companylxr");
  String q_net=rss.getString("companynet");
  String picpath=rss.getString("picpath");
    String name1 = (String) session.getValue("username");
  String taxNo = rss.getString("companysh");
  String sqlbn="select * from username where name='" + man1 + "'";
  ResultSet rsbn = infocrmdb.executeQuery(sqlbn); 
  String q_tel="";
  String  smtp="";
if(rsbn.next()) { 
   q_tel=rsbn.getString("worktel");
  rsbn.close();
}

int intPageSize; 
int intRowCount; 
int intPageCount;
int intPage; 
String strPage;

String strintPageSize;

int i,j,k; 

strintPageSize = request.getParameter("intPageSize");

intPageSize = java.lang.Integer.parseInt(strintPageSize);

strPage = request.getParameter("page");
if(strPage==null){  
	intPage=1;
}else{
 intPage = Integer.parseInt(strPage);
 if(intPage<1) intPage=1;
}

String sql8="select count(*) from ddpro where ddid ='"+id1+"'";

ResultSet  sqlRst = infocrmdb.executeQuery(sql8);
sqlRst.next();
intRowCount=sqlRst.getInt(1);
sqlRst.close();

intPageCount = (intRowCount+intPageSize-1)/intPageSize;
if(intPage>intPageCount) intPage = intPageCount; 

   String strSQLpro = "select id,epro,mpn,supplier,fypronum,s_tr_date,num,unit,salejg,pricehb,rale_types,rale,remark from ddpro where ddid='"+id1+"'  order by id asc";
	ResultSet prs=infocrmdb.executeQuery(strSQLpro);
i = (intPage-1)*intPageSize;   
for(j=0;j<i;j++) prs.next(); 
%>

<html>
<title>销售合同</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<link rel="stylesheet" href="<%=basePath%>/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/css/font-awesome/style.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/css/sale/cs_o_p.css" type="text/css">

<script type="text/javascript" src="/static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/sale/ddgl/cs_o_p.js"></script>
<script type="text/javascript">

var webRoot = "<%=basePath%>/";

</script>
</head>

<body>
<table width="100%" height="590" align="center">
  <tr><td height="584">
  <table width="100%" border="0" cellspacing="0" 
  cellpadding="0">
  <tr>
    <td width="20%" rowspan="5"><div align="left">
    <%if(picpath!=null){ %>
    <img src="<%=basePath%>/custo_log/<%=picpath%>" width="150" height="85" border="0">
    <%} %>
    </div></td>
    <td width="80%"><div align="left"><strong><font face="Arial, Helvetica, sans-serif" size="6"><%=q_company%></font></strong></div></td>
  </tr>
  <tr>
    <td width="80%"><div align="left" class="STYLE15">
    <font color="#000000">地址：<%=q_addr%></font>&nbsp;　</div></td>
  </tr>
  <tr>
    <td width="80%"><div align="left" class="f10 STYLE16 STYLE11">
    <span class="STYLE11"><font color="#000000">电话：<%=companytel%>
    </font>&nbsp;　 <font color="#000000">传真:<%=q_fax%></font>　</span></div>
    </td>
  </tr>
  <tr>
    <td width="80%"><div align="left" class="f10 STYLE16 STYLE11">
    <span class="STYLE11"> Http://<%=q_net%> 　E-mail:<%=q_email%> 　&nbsp;</span>
    </div></td>
  </tr>
</table>
  <table width="100%" border="0">
  <tr height="50" align="center"> 
    <td colspan="4"><strong><font size="5">销　售　合　同</font></strong></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0">
  <tr> 
    <td width="11%"><span class="STYLE1">客户名称：</span></td>
    <td width="56%"><span class="STYLE10"><font color="#000000"><%=coname%>
    </font>&nbsp;</span></td>
    <td width="9%"><span class="STYLE10">NO.：</span></td>
    <td width="24%"><span class="STYLE10"><font color="#000000"><%=number2%>
    </font>&nbsp;</span></td>
  </tr>
  <tr>
    <td><span class="STYLE1">收货地址：</span></td>
    <td> <span class=""><%=tr_addr%>&nbsp;</span></td>
    <td><span class="">日期：</span></td>
    <td><span class=""><%=number1%>&nbsp;</span></td>
  </tr>
  <tr> 
    <td width="11%"><span class="STYLE1">联系人：</span></td>
    <td><span class=""><%=linkman%>&nbsp;</span></td>
    <td><span class="">销售员：</span></td>
    <td><span class=""><%=username%>&nbsp;</span></td>
  </tr>
  <tr>
    <td height="22"><span class="STYLE1">电 话：</span></td>
    <td><span class=""><font color="#000000"><%=tr_tel%></font>&nbsp;</span>
    </td>
 <%if(!dd9.equals("不含税")){out.println("<td>税 率：</td>"+"<td>"+dd9+"</td>");} %>
 </tr>
 <tr>
<td>传 真：</td><td><font color="#000000"><%=cofax%></font></td><td colspan="2">
</td>
</tr>
</table>
<table width="100%" bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1" frame=hsides> 
  <tr>
    <td><div align="center" class="STYLE10">付款方式</div></td>
    <td><div align="center" class="STYLE10">货币</div></td>
    <td><div align="center" class="STYLE10">客户PO#</div></td>
    <td><div align="center">PO日期</div></td>
    <td><div align="center" class="STYLE10">交货期 </div></td>
  </tr>
  <tr>
    <td height="33"><div align="center" class="STYLE10"><%=mode%>&nbsp;</div>
    </td>
    <td><div align="center" class="STYLE10"><%=dd8%>&nbsp;</div></td>
    <td><div align="center" class="STYLE10"><%=ddnumber%>&nbsp;</div></td>
    <td><div align="center"><%=dd14%></div></td>
    <td><div align="center" class="STYLE10"><%=send_date%>&nbsp;</div></td>
  </tr>
</table>

<table height=8 width="100%" bordercolor="#CCBE5A" cellspacing="0" 
bordercolordark="#ffffff" cellpadding="3" align="center" bgcolor="#ffffff" 
bordercolorlight="#7f9db9" border="0" frame=above>
  <tr> 
    <td width="26"> 
      <div align="center" class=""><font color="#000000"># 
      </font></div>    </td>
    <td width="297"> 
      <div align="left">产品型号
    <% int tmpi=0;	%>
      </strong></div>     </td>
    <td width="167"><div align="left" >产品描述</div></td>

    <td width="130"> 
    <div align="left" class="">品牌</div></td>
    <td width="100">
      <div align="left"><font color="#000000">数量</font></div></td>
    <td width="100">
    <div align="left"><font color="#000000">单价</font></div></td>
    <td width="100"><div align="left"><font color="#000000">金额</font></div></td>
	 <td width="160"><div align="left" class="STYLE10">货期</div></td>
  </tr>
  
<tr height="8" style="cursor:hand;"><td height="8" colspan="8"><hr></td></tr>

<%    int sum=0; int c=0;
  while(prs.next()&c<intPageSize){ int id=prs.getInt(1); c++; %>
  
  <tr height="8" onMouseOver="this.bgColor='#B5DAFF'" 
  onMouseOut="this.bgColor='#ffffff'" style="cursor:hand;"> 
    <td> 
    <div align="left" class="">
      <div align="center"><font color="#000000">
        <%=tmpi+1%></font></div>
    </div></td>
    <td height="8"> 
      <div align="left" ><font color="#000000"> 
        <%String pro_model=prs.getString("epro");
		  String  mpn=prs.getString("mpn");
		  String re = prs.getString("remark");
		%>
        <%=pro_model%></font>&nbsp;</div>    </td>
    <td align="left"><%=mpn%>
      </td>

    <td height="8"> 
      <div align="left" class=""><font color="#000000">
      <%=prs.getString("supplier")%>&nbsp;&nbsp;</font></div>    </td>
    <td height="8"><div align="left"><font color="#000000">
      <%int num=prs.getInt("num");out.print(num);%>
    </font></div></td>
    <td width="100" height="8"><div align="left" >
      <div align="left">
        <%
	
    double salejg1=prs.getDouble("salejg");
	double salehj=num*salejg1;
	String pricehb=prs.getString("pricehb");
	String remak = prs.getString("remark");
	double  ssprice=0.00;
	ssprice=salejg1;
	%>
        <%=nf.format(ssprice)%></div>
      </div></td>
    <td><div align="left">
     <font color="#000000"><%=nf.format(salehj)%></font>
    </div></td>
	
	<td height="8">
<div align="left" ><%=prs.getString("s_tr_date")%></div></td>
  </tr>
  <% tmpi++;
	   hb1=pricehb;
       saletl=saletl+salehj;
	 
	   sum+=num;
   	 }
   %>
  <%
 int n=0;
   if(c%intPageSize!=0){
   		n=intPageSize-(c%intPageSize);
	}
  int h= 0;
  while(n>0){  %>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <%  n=n-1;  h++;}  %>
  <tr> 
    <%  saletl=saletl;
  	 	int saletlz=(int)(saletl*100+0.5);
        saletl=saletlz/100.0;
    String salet=Double.toString(saletl);
  %>
    <td colspan="8"></td> 
  </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="2">
    <tr>
      <td colspan="2" class="STYLE26">运输方式:<%=tr%> A/C #:<%=dd12%></td>
      <td width="25%"></td>
    </tr>
    <tr>
    <td colspan="2">总计(大写):<%=dd8%>
    <%  
    String salete=Double.toString(saletl+qt_fy+yf);
    out.println(rmb.lowerToUpper(salete));
    %></td>
    <td>运费:<%=nf.format(yf)%></td>
    </tr>
	
  <tr>
    <td height="17" colspan="2" nowrap="nowrap"><div align="left"><span class="STYLE10"><u><strong>备注</strong></u>：<%=remark%> </span></div>
      <div align="right"></div></td>
    <td>总计金额<%=dd8%>:<%=nf.format(saletl+qt_fy+yf)%> </td>
  </tr>
  <tr>
    
	<td colspan="3">
<hr>
	<div class="STYLE10">
		<u><strong>条款细则</strong></u> ：
	</div>
	<div>
	<%=tbyq%>
	</div>
    </td>
  </tr>

<tr>
<td height="118" colspan="2">

<div id="bankDiv">
<div class="toolbar">
	<span class="icon-pencil editButton"></span>
	<span class="icon-print printButton"></span>
</div>

<div id="banksDiv">
<table  id="banks">
  <tr>
    <td height="17" colspan="3">开户银行：<span class='name'><%=companybank2%></span></td>
  </tr>
  <tr>
    <td height="17" colspan="3">银行地址：<span class='address'><%=companynumber2%></span>&nbsp;</td>
  </tr>
  <tr>
    <td height="17" colspan="3">银行代码：<span class='code'><%=com_bank_code%></span></td>
  </tr>
  <tr>
    <td height="17" colspan="3">受益人名称：<span class='bname'><%=com_sy_name%></span></td>
    </tr>
  <tr>
    <td colspan="3">受益人账号：<span class='ban'><%=com_sy_code%></span></td>
    </tr>
  <tr>
  	<td colspan="3">税号：<span><%=taxNo%></span></td>
  </tr>
</table>
</div>
</div>
</td>
<td>
<select id="bankInput" name="bankInput" style="display:none">
</select>
<input type="button" name="subs" id="subs" value="确定" onClick="hideBankEdit()"  style="display:none">

</td>
</tr>

<tr> 
 <td colspan="3">
  
  <table width="100%" border="0">
  <tr>
    <td width="25%" height="37" valign="bottom"><div align="left" valign="buttom">
      <p align="left">买方签字（盖章）： </p>
      <hr align="left" width="200">
    </div></td>
    <td width="43%">
	<div align="center">Page  <%=intPage%> of <%=intPageCount%>
	   <%if(intPage>1){%> 
        <A HREF="cs_o_p.jsp?page=<%=intPage-1%>&id=<%=id1%>&cid=<%=cid%>&intPageSize=<%=intPageSize%>">上一页</A> 
        <% }%> 
        <%if(intPage<intPageCount){%> 
        <A HREF="cs_o_p.jsp?page=<%=intPage+1%>&id=<%=id1%>&cid=<%=cid%>&intPageSize=<%=intPageSize%>">下一页</A> 
        <% } %>
	</div>
	</td>
	
    <td width="32%" align="left"><table width="424" align="left">
      <tr><td align="left" valign="bottom"><p align="left">卖方签字（盖章）：</p></td><td>
      <img src="<%=basePath %>/sale/sale!getStampImg.do"><hr align="left" width="280">
    </td></tr></table>
    </td>
	</tr>
	</table>
	</td>
  </tr>
</table></td></tr></table>
</body>
</html>
<%
 rs.close();
 infocrmdb.closeStmt();
 infocrmdb.closeConn();
%>
