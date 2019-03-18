
<style type="text/css">

.btn{
	background-color: #E1E1E1; 
	font-family: 宋体;
	font-size: 9pt; 
	vertical-align: -7; 
	border: 1 groove #C3D9FF;
}

</style>

<div id="quoteform" style="margin-left:30px;margin-right:30px;">
<br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1"> <tr> <td height="30" bgcolor="#e8ebf5"><font size="2" color="#213e9b"><strong>报价管理</strong></font></td>
</tr> 
</table>
  <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">报价日期</font>&nbsp;</td>
      <td><b><font size="2" color="#000000"> 
        <input type="text" name="quotedatetime" value="${currentDate?string('yyyy-MM-dd HH:mm') }">
        </font><FONT SIZE="2">
        </FONT> </b></td>
      <td bgcolor="#e8ebf5"><FONT SIZE="2">报 价 人</font>&nbsp;</td>
      <td><B> 
        <SELECT SIZE="1" NAME="man" id="man">
          <option>${username }</option>
        </SELECT>
        </B></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">询价编号</font>&nbsp;</td>
      <td> <b><font size="2" color="#000000"> </font><font size="2"> </font><font size="2" color="#000000"> 
        <input name="in_number" type="text" id="in_number" size="15">
        </font></b></td>
      <td bgcolor="#e8ebf5"><font size="2">行业性质</font>&nbsp;</td>
      <td><b><font size="2" color="#000000"> </font><font size="2"> 
        <input name="co_number" type="text" id="co_number">
        </font><font size="2" color="#000000"> </font> </b></td>
    </tr>
    <tr> 
      <td width="11%" height="28" bgcolor="#e8ebf5"><font size="2">报价客户 </font>&nbsp;</td>
      <td width="41%" height="28">
        <input type="text" name="coname" size="40" ui="choose" choose-action="chooseCust">
      * </td>
      <td width="9%" height="28" bgcolor="#e8ebf5"><font size="2">客户电话</font>&nbsp;</td>
      <td width="39%" height="28"><b>
        <input type="text" name="cotel">
      </b></td>
    </tr>
    <tr> 
      <td width="11%" height="28" bgcolor="#e8ebf5"><font size="2">客户地址</font>&nbsp;</td>
      <td colspan="3" height="28"><b><font size="2" color="#000000"> 
        <input name="coaddr" type="text" size="65">
        </font></b></td>
    </tr>
    <tr> 
      <td width="11%" height="28" bgcolor="#e8ebf5"><font size="2">客户传真</font>&nbsp;</td>
      <td width="41%" height="28"><b><font size="2" color="#000000"> 
        <input type="text" name="cofax" size="30">
      </font></b></td>
      <td width="9%" height="28" bgcolor="#e8ebf5"><font size="2">联 系 人</font>&nbsp;</td>
      <td width="39%" height="28"><b><font size="2" color="#000000"> 
        <input type="hidden" name="linktel">
        <input type="text" name="linkman">
      </font></b></td>
    </tr>
    <tr> 
      <td width="11%" height="28" bgcolor="#e8ebf5"><font size="2">联系邮件</font>&nbsp;</td>
      <td width="41%" height="28"> <b><font size="2" color="#000000"> 
        <input type="text" name="linkemail" size="30">
      </font></b></td>
      <td width="9%" height="28" bgcolor="#e8ebf5"><font size="2">移动电话</font>&nbsp;</td>
      <td width="39%" height="28"><b><font size="2" color="#000000"> 
        <input type="text" name="linkwap" id="linkwap">
      </font></b></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">收货地址</font>&nbsp;</td>
      <td colspan="3"><b><font size="2" color="#000000"> 
        <INPUT name="airport" size="80"  id="airport"></textarea>
        </font></b></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">运输方式<font face="Arial, Helvetica, sans-serif" size="2">&nbsp; 
        </font>&nbsp;</td>
      <td><b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input name="tr_types" type="text" id="courier">
        </font><font size="2" color="#000000"> </font></b></td>
      <td bgcolor="#e8ebf5"><font size="2">贸易条款</font></td>
      <td><b><font size="2" color="#000000">
        <select name="q_tr_date" id="q_tr_date">
        </select>
      </font></b></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">快递帐号&nbsp;</td>
      <td><input name="acct" type="text" id="acct"></td>
      <td bgcolor="#e8ebf5"><font size="2">付款方式</font>&nbsp;</td>
      <td><b><font size="2" color="#000000" face="Arial, Helvetica, sans-serif"> 
        <input name="payment" type="text" id="terms">
        </font><font size="2" color="#000000"> </font></b></td>
    </tr>
     <tr> 
      <td width="11%" bgcolor="#e8ebf5"><font size="2">备　　注</font>&nbsp;</td>
      <td colspan="3"><b><font size="2" color="#000000"> 
        <textarea name="content" cols="65" rows="6"></textarea>
        </font></b></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">货　　币</font>&nbsp;</td>
      <td> <font size="2"> </font> <b><font size="2" color="#000000"></font></b><b><font size="2" color="#000000"></font><font size="2" color="#000000" face="Arial, Helvetica, sans-serif"> 
        <select name="money" >
		<option value="CNY">CNY</option>
        </select>
        </font><font size="2"> 
        <input type="hidden" name="dept" size="8" value="<%=dept%>">
        <input type="hidden" name="deptjb" size="8" value="<%=deptjb%>">
        </font></b></td>
      <td bgcolor="#e8ebf5"><font size="2">审 批 人</font>&nbsp;</td>
      <td><font size="2" color="#000080"> 
        <select name="spman" size="1" id="spman">
       	
        </select>
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">运　　费</font>&nbsp;</td>
      <td> 
        <input name="fveight" type="text" id="fveight" value="0" size="10">      </td>
      <td bgcolor="#e8ebf5"><font size="2">保　　险</font>&nbsp;</td>
      <td> 
        <input name="insurance" type="text" id="insurance" value="0" size="10">      </td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">佣　　金</font>&nbsp;</td>
      <td> 
        <input name="commission" type="text" id="fveight3" value="0" size="6">
        %</td>
      <td bgcolor="#e8ebf5"><font size="2">折　　扣</font>&nbsp;</td>
      <td> 
        <input name="discount" type="text" id="fveight4" value="0" size="6">
        %</td>
    </tr>
  </table>
  <p align="center"><b><font size="2" color="#000000"> 
<button class="btn" action="confirm">登记报价产品</button>
<input type="reset" value="重新填写" name="B323" class="btn"> 
</font></b><font color="#000000" size="2"> 
<input type="button" value="关闭" name="B22342" class="btn" onClick="window.close()"> 
</font></p><br> 
</div>

