<%@ page contentType="text/html;charset=gb2312" %>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>

<jsp:useBean id="einfodb" scope="page" class="infocrmdb.infocrmdb" /><%
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
java.sql.Connection sqlCon;
java.sql.Statement sqlStmt;
java.sql.ResultSet sqlRst;
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
if(strPage==null){
intPage = 1;
} else{
intPage = java.lang.Integer.parseInt(strPage);
if(intPage<1) intPage = 1; }
String dept = (String) session.getValue("dept");
String username1 = (String) session.getValue("username");
String restrain_name = (String) session.getValue("restrain_name");
String deptjb = (String) session.getValue("deptjb");
String  strSQLh = "select * from restrain_gp where restrain_name='"+restrain_name+"'";
java.sql.ResultSet sqlRsth = einfodb.executeQuery(strSQLh);
int tmpigp=0;
String gp_gp="";
String proview_yes="";
String proview_price="";
String gp_gpc="";
String  pro_zt="��";
String  proadd="��";
String pro_ck="��";
while(sqlRsth.next()){ 
 proview_price=sqlRsth.getString("proview_price").trim();
 pro_ck=sqlRsth.getString("pro_ck").trim();
 pro_zt=sqlRsth.getString("pro_zt").trim();
 String proadd1=sqlRsth.getString("pro_add").trim();
 if(proadd1.equals("��")){proadd="��";}
 if(pro_zt.equals("��")){
 gp_gp=gp_gp+" or pro_addr='"+pro_ck +"'";
 }
 tmpigp++;}
 sqlRsth.close();
 String modsql = "select * from restrain where restrain_name='" + restrain_name + "'";
 ResultSet rsmod = einfodb.executeQuery(modsql);
  if(rsmod.next()) { 
 String prock=rsmod.getString("profl").trim();
 String proview=rsmod.getString("outview").trim();
 String outadd=rsmod.getString("outadd").trim();
 if(proview.equals("��")){
strSQL = "select count(*) from ckview where states='�ѳ���'";
}else
strSQL = "select count(*) from ckview where states='�ѳ���'  and  slinktel='"+prock+"'";
sqlRst = einfodb.executeQuery(strSQL);
sqlRst.next();
intRowCount = sqlRst.getInt(1);
sqlRst.close();
intPageCount = (intRowCount+intPageSize-1) / intPageSize;
if(intPage>intPageCount) intPage = intPageCount;

int top = intPage*intPageSize;

 if(proview.equals("��")){
strSQL = "select top  "+top+"  ddid,pro_out_num,pro_fynum,sub,pro_coname,pro_model,pro_name,pro_rate,item,salejg pro_sales_price,pro_price_hb,pro_num,pro_unit,pro_supplier,pro_datetime,slinkman,slinktel,man from ckview where states='�ѳ���' order by id desc";

}else
strSQL = "select top  "+top+"  ddid,pro_out_num,pro_fynum,sub,pro_coname,pro_model,pro_name,pro_rate,item,salejg pro_sales_price,pro_price_hb,pro_num,pro_unit,pro_supplier,pro_datetime,slinkman,slinktel,man from ckview where states='�ѳ���' and  slinktel='"+prock+"' order by id desc";
sqlRst = einfodb.executeQuery(strSQL);
i = (intPage-1) * intPageSize;
for(j=0;j<i;j++) sqlRst.next(); 
%>
<html>
<head>
<title>���ⵥ�б�</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
td,p,select,input,textarea {font-size:12px}
.l15 {line-height:150%}
.l13 {line-height:130%}
.f10 {font-size:10px}
.f14 {font-size:14.9px}
.bdrclr01{color:#000000; border-color:#000000}
.c03{color:#000000;border-color:#000000;}                       A:link {text-decoration:none;color:#0000ff;}
A:visited {text-decoration:none;color:#800080;}
A:active {text-decoration:none;color:#0000ff;}
A:hover {text-decoration:underline;color:#0000ff;}
.STYLE1 {color: #000000}
.STYLE2 {
	font-family: "��Բ";
	font-weight: bold;
	font-size: 14px;
}
</style>
</SCRIPT>
<script language="javascript">
function pic1_onclick() {
     window.open("waitcal.jsp?m=m1&time=<%=time1%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
function pic2_onclick() {
     window.open("waitcal.jsp?m=m2&time=<%=time2%>",'newwindow', 'height=230, width=230, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=n o, States=no');
}
</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0">
<form name="form1" method="post" action="szouthousem.jsp">
    <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
        <tr>
            <td height="17" colspan="14"><p class="STYLE2"><font size="2" color="#213e9b"><strong>�ѳ����б� </strong></font></p>            </td>
        </tr>
        <tr bgcolor="#DFEBFF">
            <td height="17" colspan="14" bgcolor="#ffffff">�ͻ�����
                <input name="supplier" type="text" id="supplier2" size="10">
��Ʒ�ͺ�
<input name="pro_model" type="text" id="pro_model" size="10">
��ͬ���
<input name="pro_types" type="text" id="pro_types" size="10">
<FONT SIZE="2">��˾��ͬ��<span class="STYLE1"><input name="sub" type="text" id="sub" size="10">
�ֿ�</span></FONT><FONT COLOR="#000000" SIZE="2">
<SELECT SIZE="1" NAME="profl">
    <OPTION></OPTION>
    <%
  try
  {
    ResultSet rs=einfodb.executeQuery("select cpro from profl ");
    while(rs.next())
    {
      String proflname=rs.getString("cpro");
      out.println("<option value='"+proflname+"'>"+proflname+"</option>");
    }
    rs.close();
   }catch(Exception e){}
   %>
</SELECT>
</font>&nbsp;</td>
        </tr>
        <tr bgcolor="#DFEBFF">
            <td height="17" colspan="14" bgcolor="#ffffff"> <font size="2"><font size="2"><span class="STYLE1">�������
                        <input name="number" type="text" id="number" size="10">
��ʼ����
<input name="startdate" type="text" id="startdate" size="10">
            <a href="javascript:pic1_onclick()"><img border="0" src="../images/calendar.JPG" id="pic1" width="20" height="16"></a> ��ֹ����
            <input name="enddate" type="text" id="enddate" value="<%=currentDate%>" size="10">
            <a href="javascript:pic2_onclick()"><img border="0" src="../images/calendar.JPG" id="pic2" width="20" height="16"></a></span></font><FONT SIZE="2"></font></font><font color="#000000" size="2"><FONT color="#000000" SIZE="2"><font color="#000000" size="2"><font color="#000000" size="2"><font color="#000000" size="2">
            <input type="submit" name="Submit" value="��ѯ">
            <input type="button" name="Submit2" value="��ӡԤ�����ⱨ��" onClick="window.open('out-report.jsp','nrtop', 'height=500, width=650, top=50, left=50, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=no, status=no')">
            </label>
            </strong></a></font></font></font></font></font>&nbsp;</td>
        </tr>
        <tr bgcolor="#d3d8eb">
          <td height="17" nowrap><div align="left"><font size="2">���</font></div></td>
          <td nowrap><div align="left"><font size="2">��ͬ���</font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">��˾��ͬ��</font>&nbsp;</div></td>
          <td height="17" nowrap><div align="left"><font size="2">�ͻ�����</font>&nbsp;</div></td>
          <td height="17" nowrap><div align="left"><font size="2">��Ʒ�ͺ�</font>&nbsp;</div></td>
          <td nowrap><div align="left">��Ʒ����</div></td>
          <td nowrap><div align="left">˰��</div></td>
          <td nowrap><div align="left">����</div></td>
          <td nowrap><div align="left">����</div></td>
          <td height="17" nowrap><div align="left"><font size="2">����</font>&nbsp;</div></td>
          <td height="17" nowrap><div align="left"><font size="2">��������</font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">������</font>&nbsp;</div></td>
          <td height="17" nowrap><div align="left"><font size="2">�ֿ�����</font>&nbsp;</div></td>
          <td height="17" nowrap><div align="left"><font size="2">����Ա</font>&nbsp;</div></td>
      </tr>
        <%
   i = 0;
   while(i<intPageSize && sqlRst.next()){ 
   		String ddid = sqlRst.getString("ddid");
   		%>
        <tr bgcolor="<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>" style="cursor:hand;" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='<%if ((i % 2)==0) out.print("#eeeeee");
        	       else out.print("#ffffff");%>'" height="8">
            <td height="8" nowrap><div align="left"><font size="2">
                    <%
		  String pro_fynum=sqlRst.getString(2);%>
		  <a href="ydd-view.jsp?id=<%=ddid%>" target="w" onClick="javascript:window.open('ydd-view.jsp?id=<%=ddid%>','w','height=550,width=955,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
            <%=pro_fynum%></a></font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">
		  <%String pro_fynumc=sqlRst.getString(3);
			//String item="";%>
			<a href="ydd-view.jsp?id=<%=ddid%>" target="w" onClick="javascript:window.open('ydd-view.jsp?id=<%=ddid%>','w','height=550,width=955,left=25,top=25,toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')"><%=pro_fynumc %></a>
			
		  </font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">
          <%String sub=sqlRst.getString("sub");
			out.print(sub);
			%>
          </font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2"><%=sqlRst.getString(5)%> </font>&nbsp;</div></td>
<td height="8" nowrap><div align="left"><font size="2">
                    <%String pro_model1=sqlRst.getString(6);
					String pro_name=sqlRst.getString(7);
					%>
          <%=pro_model1%> </font>&nbsp;</div></td>
		  <td nowrap><div align="left"><font size="2"><%=pro_name
	
			%></font>&nbsp;</div></td>
		  <%String pro_rate=sqlRst.getString(8);
			String item=sqlRst.getString(9);%>
          <td nowrap><div align="left"><font size="2"><%=item
	
			%></font>&nbsp;</div></td>
<% 
			 String pro_sales_price=sqlRst.getString(10);%>
            <td nowrap><div align="left"><font size="2">
                <%if(proview_price.equals("��")) {out.print(pro_sales_price);}%>
            </font>&nbsp;</div></td>
		  <%String pro_price_hb=sqlRst.getString(11);%>
          <td nowrap><div align="left"><font size="2"><%=pro_price_hb%></font>&nbsp;</div></td>
<td height="8" nowrap><div align="left"><font size="2">
  <%int num=sqlRst.getInt(12);%>
  <%=num%><%=sqlRst.getString(13)%></font></div></td>
          <td height="8" nowrap><div align="left"><font size="2"><%=sqlRst.getString(15)%></font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2"><%=sqlRst.getString(16)%></font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2"><%=sqlRst.getString(17)%></font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2"><%=sqlRst.getString("man")%></font>&nbsp;</div></td>
      </tr>
        <% i++; } %>
        <tr>
            <td colspan=14 align=center height="22"><div align="right"><font color="#000000" size="2"> ��<%=intRowCount%>���ѳ��ⵥ</font><FONT COLOR="#000000"><FONT COLOR="#000000"><FONT SIZE="2">,��ǰ�ǵ�<%=intPage%>ҳ,��<%=intPageCount%>ҳ</FONT></FONT></FONT> <font color="#C1D9FF" size="2">
                    <%if(intPage<intPageCount){%>
                    <a href="zouthousem.jsp?page=<%=intPage+1%>">��һҳ</a>
                    <% } %>
                    <%if(intPage>1){%>
                    <a href="zouthousem.jsp?page=<%=intPage-1%>">��һҳ</a>
                    <%  }  %>
                    </font>&nbsp;</div></td>
        </tr>
  </table>
    <%
  
  sqlRst.close();
 einfodb.closeStmt();
 einfodb.closeConn();
}%>
</form>
<br>
</body>
</html>
