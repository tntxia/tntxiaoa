
<div id="Layer1"><span class="STYLE1">
①<br> 
  白<br> 
联<br>
：
<br>
存<br>
根
<br>
②<br>
红<br>
联<br>
：<br>
客<br>
户<br>

③<br>
绿
<br>
联<br>
：<br>
财<br>
务<br>

④<br>
黄<br>
联<br>
：<br>
回<br>
单</span><br>
</div>
<table width="752" border="0" cellpadding="3" cellspacing="0"> 
<tr> <td width="746" height="79" colspan="3">
<table width="99%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="19%" rowspan="6">
    	<div align="left">
    <img src="/file_center/file!showPic.do?uuid=${company.pic_id }" width="150" height="85" border="0"></div></td>
    <td colspan="2"><div align="left" class="STYLE2"><font color="#213e9b">
    	<font color="#000000">${company.companyname}</font></font><font color="#000000">&nbsp;</font></div>
        <div align="left"></div></td>
    <td width="33%" rowspan="4"><table  width="235" height="70" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap="nowrap" width="31%"><div align="left"><font size="2">合同编号：</font></div></td>
        <td nowrap="nowrap" width="69%"><div align="left"><font color="#000000" size="2" id="number">${sale_number}</font></div></td>
      </tr>
      <tr>
        <td><div align="left"><font size="2">客户名称：</font></div></td>
        <td nowrap="nowrap"><div align="left"><font size="2">${coname}</font></div></td>
      </tr>
      <tr>
        <td nowrap="nowrap"><div align="left"><font size="2">电　　话：</font></div></td>
        <td><div align="left"><font size="2">${co_tel}</font></div></td>
      </tr>
      <tr>
        <td nowrap="nowrap"><div align="left"><font size="2">日　　期：</font></div></td>
        <td><div align="left"><font size="2">${currentDate?string('yyyy-MM-dd') }</font></div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="18" colspan="2" nowrap="nowrap"><div align="left">
    <span class="STYLE1"><font color="#000000">${company.companyaddr }</font>&nbsp;</span></div></td>
  </tr>
  <tr>
    <td width="46%" height="9" nowrap="nowrap"><font size="2" color="#000000">电话:${company.companytel }</font><font size="2">&nbsp; 
    <font color="#000000">传真:${company.companyfax }</font></font></td>
    <td width="1%" rowspan="2" nowrap="nowrap">&nbsp;</td>
    <td width="1%" height="18" rowspan="2" nowrap="nowrap">&nbsp;</td>
  </tr>
  <tr>
    <td height="9" nowrap="nowrap"><font size="2"><font size="2"><font size="2" color="#000000">${company.companynet }</font></font>
    <font color="#000000">${company.companyfax }</font><font size="2" color="#000000">　</font><font size="2">&nbsp;</font></font></td>
  </tr>
   
</table></td>
</tr> <tr> 
  <td height="25" colspan="3">
    <div align="left"><font size="4" face="Arial, Helvetica, sans-serif">　
    　　　　　　　　　　　　　　　　　　销售清单　　　　　　　</font><font face="Arial, Helvetica, sans-serif">
    <span class="STYLE1">客户合同号：  &nbsp;${custno}　</span></font><font size="4" face="Arial, Helvetica, sans-serif">　　　<br>
        <br>
          </font> </div></td></tr> 
</table>
<TABLE  width="700" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff"  bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="0">
<TR height="12" valign="bottom"> 
	<TD width="40" height="12"> <DIV ALIGN="left" class="STYLE3"> 
  		<div align="left"><FONT COLOR="#000000">序号</font></div>
		</div>
    </TD>
	<TD width="155" height="12"> <DIV ALIGN="left" class="STYLE3"> 
  		<div align="left"><FONT COLOR="#000000">料号／产品型号</font></div>
		</div>
    </TD>
  <TD width="75" height="12" valign="bottom"><span class="STYLE3">品牌</span></TD>
  <TD width="40" height="12" valign="bottom"><span class="STYLE3"><font color="#000000">批号</font></span></TD>
  <TD width="66" height="12" valign="bottom"><div align="center" class="STYLE3"><font color="#000000">数量</font></div></TD>
  <TD width="65" height="12" valign="bottom"><div align="right" class="STYLE3"><font color="#000000">单价</font></div></TD>
  <TD width="80" height="12" valign="bottom"><div align="right" class="STYLE3"><font color="#000000">金　额</font></div></TD>
  <TD width="137" height="12"><div align="center" class="STYLE3">备注</div></TD>
</TR>
<TR HEIGHT="5">
  <TD HEIGHT="0.5" colspan="8" nowrap><hr style="line-height:10px;"></TD>
</TR>
<#list list as r>

<TR HEIGHT="12"> 
	<TD HEIGHT="9" nowrap><DIV ALIGN="left" class="STYLE3">${r_index+1 }</div>
	</TD>
	<TD HEIGHT="9" nowrap><DIV ALIGN="left" class="STYLE3">${r.model}</div>
	</TD>
  <TD nowrap><div align="left" class="STYLE3">
    <div align="left">${r.brand}</div>
  </div></td>
  <TD nowrap><div align="left" class="STYLE3">
    <div align="left">${r.batch}</div>
  </div></td>
  <TD HEIGHT="24" nowrap> 
<DIV ALIGN="left" class="STYLE3">
  <div align="center">
  ${r.num }
    
  </div>
</div> 
<DIV ALIGN="center" class="STYLE3"></div></TD>
  <TD HEIGHT="24" nowrap><div align="left" class="STYLE3">
    <div align="right">
    	${r.price?string('#.00') }
    </div>
  </div></TD>
  <TD HEIGHT="24" nowrap><div align="left" class="STYLE3">
    <div align="right">
    ${(r.num*r.price)?string('#.00') }
      
    </div>
  </div></TD>
  <TD nowrap><div align="center" class="STYLE3">${r.remark}</div></TD>
</TR> 
</#list>

 <TR> <TD height="20" COLSPAN="8">        
      
        <div class="spacer-large STYLE3">合计金额（大写）<strong>${money}</strong>：${totalBig}
            
          　　　　　　　　　　　　　　　　 <strong>${money}：</strong>${total}</div>
          <hr></TD>
</TR> 
<TR>
  <TD height="18" COLSPAN="8" dir="ltr"><span class="STYLE3">制单人：${man}　　　　
  收货单位及经手人(签章)： 　　　　　　　　收款人：　　　　　　　　经手人：</span></td>
</TR>
<TR>
  <TD height="15" COLSPAN="8" valign="top"><hr>  
  <div>
  	<div style="float:left;width:40px;font-size:12px;">
  		注：
  	</div>
  	<div style="float:left;width:450px;font-size:12px;">
  		<ol style="margin: 0;padding: 0;">
  			<li>客户收到货品后如有疑问，请于收货7天检查提出，否则视为验收合格。</li>
  			<li>货品须退还时，请凭此单并将原包装于30天内整批退还，损坏或上机安装后拆回的货品一律不予退换，也不承担因质量不良或其他原因造成的任何赔偿问题，多谢合作。</li>
  		</ol>
  	</div>
  </div>
  </TD>
</TR>
</TABLE>
