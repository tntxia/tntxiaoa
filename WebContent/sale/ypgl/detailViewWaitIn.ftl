
    <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"><INPUT TYPE="hidden" id="id" value="${id }">
    <tr bgcolor="#d3d8eb"> 
      <td width="12%"><span class="STYLE1"><font size="2">样品编号</font></span></td>
      <td width="37%"> <font size="2" color="#000000">${detail.number }</font>&nbsp;</td>
      <td width="10%"><span class="STYLE1"><font size="2">负 责 人</font></span></td>
      <td width="41%" bgcolor="#d3d8eb"> <font size="2" color="#000000">${detail.man }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">仓　　库</font></span></td>
      <td colspan="3" height="2"><font size="2" color="#000000">${detail.sub }　</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="19" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">客户名称</font></span></td>
      <td colspan="3" height="19"><font size="2" color="#000000">${detail.coname }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="19" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">送货地址</font></span></td>
      <td colspan="3" height="19"><font size="2" color="#000000">${detail.delivery_terms }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">客户编号</font></span></td>
      <td><font size="2" color="#000000">${detail.senddate }</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">联系电话</font></span></td>
      <td><font size="2" color="#000000">${detail.yj }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">联 系 人</font></span></td>
      <td><font size="2" color="#000000">${detail.linkman }</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">样品货币</font></span></td>
      <td><font size="2" color="#000000"> 
        ${detail.money }
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">登记日期</font></span></td>
      <td height="17"> <font size="2" color="#000000">${detail.datetime }</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">运输方式</font></span></td>
      <td> <font size="2" color="#000000">${detail.airport }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">发货日期</font></span></td>
      <td height="17"> <font size="2" color="#000000">${detail.delivery_date }</font>&nbsp;</td>
      <td height="17" bgcolor="#e8ebf5">&nbsp;</td>
      <td height="17"><font size="2"></font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">运　　费</font></span></td>
      <td height="2"><font size="2" color="#000000">${detail.fveight }  ${detail.money }</font>&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">保　　险</font></span></td>
      <td height="2"><font size="2" color="#000000">${detail.insurance }  ${detail.money }</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">特别要求</font></span></td>
      <td colspan="3" height="17"><font size="2" color="#000000">${detail.tbyq }　</font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="17" bgcolor="#e8ebf5"><span class="STYLE1"><font size="2">备　　注</font></span></td>
      <td colspan="3" height="17"><font size="2" color="#000000">${detail.remark }</font><font size="2" color="#000080"> 
      
        　 </font>&nbsp;</td>
    </tr>
  </table>
  <br>
  <br>  
  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"><TR BGCOLOR="#d3d8eb"> 
      <td width="257" > 
        <div align="left" class="STYLE1"><font size="2">产品型号</font>&nbsp;</div>      </td>
      <td width="388" > 
        <div align="left" class="STYLE1"><font size="2">产品批号
        </font>&nbsp;</div>      </td>
      <td width="78" > 
        <div align="right" class="STYLE1"><font size="2">数量</font>&nbsp;</div>      </td>
      <td width="165" > 
        <div align="right" class="STYLE1"><font size="2">单价</font>&nbsp;</div>      </td>
      <td width="97" > 
        <div align="right" class="STYLE1"><font size="2">合计</font>&nbsp;</div>      </td>
    </tr>
    <#list list as p>
    <tr height="8" onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" style="cursor:hand;"> 
      <td width="257" height="8" > 
        <div align="left"><font size="2" color="#000000"> <a href="#"  onClick="javascript:window.open('pro-view.jsp?id=${p.id }&ddid=${id }','nw', 'height=180, width=600, top=50, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">${p.epro }</a> 
          </font>&nbsp;</div>      </td>
      <td width="388" height="8" > 
        <div align="left"><font size="2" color="#000000">${p.cpro}</font>&nbsp;</div>      </td>
      <td width="78" height="8" > 
        <div align="right"><font size="2" color="#000000"> 
          ${p.num }
          </font>&nbsp;</div>      </td>
      <td width="165" height="8" > 
        <div align="right">
          ${p.salejg }
        &nbsp;</div>      
      </td>
     
      <td width="97" >${p.num*p.salejg }</td>
    </tr>
    </#list>
    <tr> 
      <td colspan="5"> 
        <div align="right">总计:${total }&nbsp;
        </div>      
      </td>
    </tr>
  </table>
  <div align="left">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr> 
        <td width="33%">&nbsp;</td>
        <td width="67%"> 
          <div align="right"></div>        </td>
      </tr>
      <tr> 
        <td width="33%"><font size="2" color="#000080" face="Arial, Helvetica, sans-serif">审批情况:</font><font face="Arial, Helvetica, sans-serif" size="2">&nbsp;</font>&nbsp;</td>
        <td width="67%"><font face="Arial, Helvetica, sans-serif" size="2">&nbsp;</font>&nbsp;</td>
      </tr>
      <tr> 
        <td width="33%"><font color="#000080" size="2">是否通过:</font><font size="2" color="#000000">${detail.state }</font>&nbsp;</td>
        <td width="67%"><font size="2">&nbsp;</font>&nbsp;</td>
      </tr>
      <tr> 
        <td width="33%" height="2"><font color="#000080" size="2">审批领导:</font>${detail.spman }&nbsp;</td>
        <td width="67%" height="2"><font color="#000080" size="2">审批时间:</font><font size="2" color="#000000">${detail.spdate }</font>&nbsp;</td>
      </tr>
      <tr> 
        <td colspan="2"><font color="#000080" size="2">审批意见:</font><font size="2" color="#000000">${detail.spyj }</font>&nbsp;</td>
      </tr>
      <tr> 
        <td><font color="#000080" size="2">是否复审:</font><font size="2" color="#000000">${detail.fif }</font>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr> 
        <td><font size="2"><font color="#000080">复 审 人:</font></font><font size="2" color="#000000">${detail.cwman }</font>&nbsp;</td>
        <td><font color="#000080" size="2">复审日期:</font><font size="2" color="#000000">${detail.cwdate }</font>&nbsp;</td>
      </tr>
      <tr> 
        <td colspan="2"><font color="#000080" size="2">复审意见:</font><font size="2" color="#000000">${detail.cwyj }</font><font size="2">　　　　　</font>&nbsp;</td>
      </tr>
    </table>
  </div>
  <div align="center">  
    <button onClick="window.close()">关闭</button>
</div>

