
<%@ page import="com.tntxia.oa.warehouse.*"%>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<jsp:useBean id="infocrmdb" scope="page" class="infocrmdb.infocrmdb" />
<jsp:useBean id="warehouseManager" scope="page" class="com.tntxia.oa.warehouse.WarehouseManager" />
<%@ page contentType="text/html;charset=GB2312"%>
<%
String basePath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>出库确认</title>
<script language="JavaScript" src="<%=basePath%>/js/config.js"></script>
<script language="JavaScript" src="<%=basePath%>/js/jquery.js"></script>
<script language="JavaScript" src="<%=basePath%>/js/warehouse/outpro.js"></script>

<style type="text/css">
<!--
#bk {
	border: 1px solid #909ECD;
	padding-right: 5px;
	padding-left: 5px;
	padding-top: 5px;
	padding-bottom: 5px;
	font-family: "宋体";
	font-size: 12px;
}
.STYLE1 {color: #000000}
.STYLE2 {
	font-size: 18px;
	color: #FF0000;
}
.STYLE5 {color: #FF0000}
-->
</style>
</head>
<%
java.text.SimpleDateFormat simple=new java.text.SimpleDateFormat("yyyy-MM-dd");
String currentDate=simple.format(new java.util.Date());
String edate=currentDate;
java.text.SimpleDateFormat simplec=new java.text.SimpleDateFormat("yyyy");
String cd=simplec.format(new java.util.Date());
String m_year; 
String m_month; 
String m_day;
String m;
String m_time;
String pro_remark = "";
m=request.getParameter("m"); 
m_month=request.getParameter("month"); 
m_year =request.getParameter("year"); 
m_day =request.getParameter("date"); 
m_time =request.getParameter("time"); 
int year,month,day,hour,minute,second;
String time1,time2;
GregorianCalendar calendar;
calendar=new GregorianCalendar();
year=calendar.get(Calendar.YEAR);
month=calendar.get(Calendar.MONTH)+1;
day=calendar.get(Calendar.DAY_OF_MONTH);
hour=calendar.get(Calendar.HOUR_OF_DAY);
minute=calendar.get(Calendar.MINUTE);
second=calendar.get(Calendar.SECOND);
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
if(m!=null)
{
if(m.equals("m1"))
{
	if(m_year==null&&m_month==null&&m_day==null)
	{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
	}
	else
	{
		time1=m_year+"-"+m_month+"-"+m_day;
		time2=m_time;
	}
}	
else if(m.equals("m2"))
{
	if(m_year==null&&m_month==null&&m_day==null)
	{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
	}
	else
	{
		time1=m_time;
		time2=m_year+"-"+m_month+"-"+m_day;
	}
}
else
{
		time1=year+"-"+month+"-"+day;
		time2=year+"-"+month+"-"+day;
}
}   
String id1=request.getParameter("id");

String num1=request.getParameter("num");
int num = Integer.parseInt(num1); 
 String  ddid1=request.getParameter("ddid");
 String coname=request.getParameter("coname");
 String fynumber=request.getParameter("fynumber").trim();
 String co_number=request.getParameter("co_number");
  String proaddr=request.getParameter("proaddr");
  String pro_name=request.getParameter("pro_name");
  String dqnum=request.getParameter("dqnum");
  String widOrder = request.getParameter("widOrder");
  String wid = request.getParameter("wid"+widOrder);
 int phnum=Integer.parseInt(dqnum);
 int smallnum=0;
 if(phnum<num){smallnum=phnum;}
 else{smallnum=num;}
    String strSQLpro = "select id,epro from ddpro where id='"+id1+"'";
	ResultSet prs=infocrmdb.executeQuery(strSQLpro);
	if(!prs.next())
  {
    out.println("not have record");
    prs.close();
    infocrmdb.close();
    return;
  }
	
String pro_model = prs.getString("epro");

String restrain_name = (String)session.getAttribute("restrain_name");
	
	
%>
<script language="javascript">
function pic1_onclick() {
     window.open("waitcal1.jsp?m=m1&time=<%=time1%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
     window.open("waitcal1.jsp?m=m2&time=<%=time2%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
</script>
<body bgcolor="#ffffff"><div id="bk">
<form id="form1" name="form1" method="post" action="do_proout.jsp"  onSubmit="return isValid();">
  <p><font face="宋体">出库产品确认: 
    <input name="ddid" type="hidden" id="ddid" value="<%=ddid1%>">
    <input name="id" type="hidden" id="id" value="<%=id1%>">
    <input name="coname" type="hidden" id="coname" value="<%=coname%>">
    <input name="fynumber" type="hidden" id="fynumber" value="<%=fynumber%>">
    <input name="co_number" type="hidden" id="co_number" value="<%=co_number%>">
    </font></p>
  
  <p><font face="宋体"> 产品型号：<%=pro_model%>；备注：<%=pro_remark%>；
    
    本次出库数量： 
    <input name="num" type="text" id="num" value="<%=num%>" size="10">
    <input name="currentDate" type="text" id="currentDate" value="<%=currentDate%>" size="10">
    </font><font size="2"><a href="javascript:pic1_onclick()"><img border="0" src="../images/calendar.jpg" id="pic1" width="20" height="16"></a></font><font face="宋体">(注意日期格式)
    <select name="wid" id="wid" >
            <%
			int snum=0;
			String gp_gp="";
			int tmpigp=0;

			// 根据权限，看用户可以查看哪些仓库产品
			List<Warehouse> allWarehouse = warehouseManager.getWarehouseByModelAndRestain(restrain_name,pro_model);
		    
			// 将所有的产品以下拉列表的形式来显示
       		for(Warehouse warehouse : allWarehouse){
            	snum=warehouse.getPro_num();
            	String pro_supplier=warehouse.getPro_supplier();
            	String currname=warehouse.getPro_addr();
				String pro_no=warehouse.getPro_name();
            	String swid=warehouse.getId();
            	out.println("<option value='"+swid+"'>"+currname+","+pro_no+",库存:"+snum+"</option>");
               
           	}
            
         %>
          </select>
    
    <input type="button" id="submitButton" value="确  认" onclick="outpro()">
    <br>
    <font color="#FFA586">(请确认一次即可，无须重复确认，特别在服务器很慢的时候) </font></font> </p>
  <p><span class="STYLE2">警告！出库后无法返回！请确认出库数据无误！</span><br>
  </p>
</form></div>
</body>
</html>
