
<style type="text/css">
td,p,select,input,textarea {font-size:12px}
.l15 {line-height:150%}
.l13 {line-height:130%}
.f10 {font-size:10px}
.f14 {font-size:14.9px}
.bdrclr01{color:#000000; border-color:#000000}
.c03{color:#000000;border-color:#000000;}
A{text-transform: none; text-decoration:none;color:#0000ff}
a:hover {text-decoration:underline;color:#0000ff}
.STYLE1 {color: #000000}
</style>

<input id="ddnumber" value="${number }" type="hidden" />
<input id="man1" value="${man }" type="hidden" />
<INPUT TYPE="hidden" name="id" value="${id }" id="ddid">
<br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
  <tr> 
      <td height="36" bgcolor="#FFFFFF"><div align="right">
                    <#if trview_yes >
                    <font color="#000080"><a href="#" onClick="toReturn()">返回合同</a> | 
                    <a href="#" onClick="deliveryBill()">打印送货单</a>
					</#if>
					</font> | <a href="#" onClick="window.close()">关闭</a></font></font>&nbsp;</div></td>
    </tr> </table>
<br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"> 
    <tr> 
      <td width="18%" bgcolor="#e8ebf5"><font size="2">PI#</font></td>
      <td width="45%" bgcolor="#FFFFFF"> <FONT SIZE="2" COLOR="#000000">${number }</font>&nbsp;</td>
      
      <td width="15%" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">负 责 人</font></span></td>
      <td width="22%" bgcolor="#FFFFFF"> <font size="2" color="#000000">${man }</font>&nbsp;</td>
    </tr>
    <tr>
      <td height="19" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">客户名称</font></span></td>
      <td height="19"><font size="2" color="#000000">
      <INPUT TYPE="hidden" name="coname" value="${coname }" id="coname">
      <INPUT TYPE="hidden" name="co_number" value="${co_number }" id="co_number">
      ${coname }</font></td>
      <td height="19">PO#</td>
      <td height="19" colspan="3">${custno }</td>
    </tr>
    <tr> 
      <td width="18%" height="19" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">帐单地址</font></span></td>
      <td colspan="5" height="19">${coaddr }</td>
    </tr>
    <tr> 
      <td width="18%" bgcolor="#e8ebf5"><span class="STYLE1">发　　票</span></td>
      <td width="45%"><font size="2" color="#000000">${item }</font></td>
      <td width="15%" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">PO日期</font></span></td>
      <td colspan="3"><font size="2" color="#000000">${datetime}</font></td>
    </tr>
    <tr> 
      <td width="18%" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">运费是否预付</font></span></td>
      <td width="45%"><font size="2" color="#000000">${yf_types }(${yf_money })</font></td>
      <td width="15%" height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2"><font size="2">付款方式</font></FONT></span></td>
      <td height="17" colspan="3"><font size="2" color="#000000">${mode }</font></td>
    </tr>
    <tr> 
      <td width="18%" height="17" bgcolor="#e8ebf5">收 货 人tr,,,tr_man,tr_tel</td>
      <td height="17" width="45%"><font size="2" color="#000000">${tr_man }</font></td>
      <td width="15%" height="17" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">发货日期</font></span></td>
      <td height="17" colspan="3"><font size="2" color="#000000">${send_date }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="18%" height="17" bgcolor="#e8ebf5">联系电话</td>
      <td height="17"><font size="2" color="#000000">${tr_tel }</font></td>
      <td height="17" bgcolor="#e8ebf5">快递公司及帐户</td>
      <td height="17" colspan="3"><font size="2" color="#000000">${tr }</font>:${trade}&nbsp;</td>
    </tr>
    <tr>
      <td height="17" bgcolor="#e8ebf5"><FONT SIZE="2">收货地址</FONT></td>
      <td colspan="5" height="17"><font size="2" color="#000000">${tr_addr }</font></td>
    </tr>
    <tr> 
      <td width="18%" height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">特别要求</FONT></span></td>
      <td colspan="5" height="17"><FONT SIZE="2" COLOR="#000000">${tbyq }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="18%" height="17" bgcolor="#e8ebf5"><span class="STYLE1"><FONT SIZE="2">备　　注</FONT></span></td>
      <td colspan="5" height="17"><FONT SIZE="2" COLOR="#000000">${remarks }</FONT><FONT SIZE="2" COLOR="#000080">&nbsp; 
        </font>&nbsp;</td>
    </tr>
  </table>
<br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr bgcolor="#d3d8eb">
      <td width="46"  bgcolor="#d3d8eb">#</td> 
      <td width="171" height="2"  bgcolor="#d3d8eb"> 
        <div align="center" class="STYLE1"><font size="2">产品型号</font><font size="2" face="Arial, Helvetica, sans-serif"> 
      
          </font>&nbsp;</div></td>
      <td width="72" height="2" > 
      <div align="center" class="STYLE1"><font size="2">品牌</font>&nbsp;</div>      </td>
      <td width="72" height="2" ><div align="center" class="STYLE1"><font size="2">封装</font>&nbsp;</div></td>
	  <td width="72" height="2" ><div align="center" class="STYLE1"><font size="2">批号</font>&nbsp;</div></td>
      <td width="75" height="2" ><div align="center" class="STYLE1"><font size="2">合同数量</font>&nbsp;</div></td>
      <td width="86" bgcolor="#d3d8eb" ><div align="center" class="STYLE1"><font size="2">货期</font>&nbsp;</div></td>
      <td width="112" bgcolor="#d3d8eb" ><div align="center" class="STYLE1"><font size="2">备注</font>&nbsp;</div></td>
      <td width="129" height="2" > 
      <div align="center" class="STYLE1"><font size="2">已出库数量</font>&nbsp;</div>      </td>
      <td width="233" ><div align="center" class="STYLE1"><font size="2">批次/数量</font>&nbsp;</div></td>
      <td width="103" > 
      <div align="center" class="STYLE1"><font size="2">出库操作</font>&nbsp;</div>      </td>
    </tr>
    <#list list as p>
    <tr height="8" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" >
      <td >${p_index+1 }<input name="sid" type="hidden" value="${p.id }"></td> 
      <td height="2" > 
	  
        <div align="center"><font size="2" color="#000000">
        <a href="javascript:window.open('pro-wview.jsp?id=${p.id }','_blank')">${p.epro }</a> 
          
          </font><font color="#000000" size="2"> 
          
          </font><font size="2" color="#000000"></font>&nbsp;</div>    
		 
		    </td>
      <td height="2" > 
        <div align="center"><font size="2" color="#000000">${p.supplier }</font>&nbsp;</div>      </td>
      <td height="2" ><div align="center">
      <font size="2" color="#000000">${p.fypronum }</font>&nbsp;</div></td>
	  <td>${p.cpro }</td>
	  
      <td height="2" ><div align="center">
          ${p.num }${p.unit }&nbsp;</div></td>
      <td ><div align="center">${p.deliveryDate }　</div></td>
      <td ><div align="center">${p.remark }　</div></td>
      <td height="2" > <div align="center">${p.numOut } </div></td>
      <td ><div align="center"><font size="2">
          <select name="wid" id="wid${p.id }">
          	<#list p.warehouseList as w>
          	<option value='${w.id }'>${w.pro_name },库存:${w.pro_num }</option>
           	</#list>
          </select>
      </font>&nbsp;</div></td>
   
      <td width="103" height="2" align="center"> 
      	<button onclick="outSingle(${id },${p.id })">出库确认</button>
        
      　        </td>
    </tr>
    </#list>
  </table>
  <div align="center"><font size="2"><font size="2"><font size="2">
  <font size="2"><font size="2"><font size="2"><font size="2"><font size="2"><font size="2">
  <font size="2"><font size="2"><font size="2"><font size="2"><font size="2"><font size="2">
  <font size="2"><font size="2"><font size="2"><font size="2"><font size="2"><font color="#000000" size="2">
  <font color="#000000" size="2"><font size="2">
    
    
    <font size="2">
    <a href="javascript:delclick()">所有产品全部出库 </a>
    
    |<a href="#" onClick="javascript:window.close()"> 本次出库完成</a></font><br>
  </div>
  <table width="100%" border="1" cellpadding="3" cellspacing="0" bordercolor="#ffffff"> 
<tr> 
   <td><span class="STYLE1"><font size="2">是否通过:${state }&nbsp;(出入库以产品型号，仓库为唯一依据)</font></span></td></tr> 
<tr><td height="2"><span class="STYLE1"><font size="2">审批领导:${spman }　　　　　　 审批时间:${spdate }</font></span></td>
</tr> 
<tr><td><span class="STYLE1"><font size="2">审批意见:${spyj }</font></span></td>
</tr> 
<tr><td><span class="STYLE1"><font size="2">复审意见:${cwyj }</font></span></td>
</tr> 
<tr><td><span class="STYLE1"><font size="2">仓库意见:${ware_remark }</font></span></td>
</tr> 
</table>

<table width="100%" border="1" bordercolorlight="#7f9db9" bordercolordark="#ffffff">
<tr>
  <td colspan="6">已出库列表</td>
  </tr>
<tr>
  <td bgcolor="#d3d8eb">序号</td>
  <td bgcolor="#d3d8eb">产品型号</td>
  <td bgcolor="#d3d8eb">产品批号</td>
  <td bgcolor="#d3d8eb">出库数量</td>
  <td bgcolor="#d3d8eb">出库日期</td>
  <td bgcolor="#d3d8eb">仓库</td>
  </tr>
  <#list outList as o>
<tr>
  <td>${o_index }</td>
  <td>${o.pro_model }</td>
  <td>${o.pro_name }</td>
  <td>${o.pro_num }</td>
  <td>${o.pro_datetime }</td>
  <td>${o.slinktel }</td>
  </tr>
  </#list>
</table>

