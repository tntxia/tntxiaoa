
<div id="newForm">
  <br>
  <table width="100%" border="0" cellpadding="3" height="50">
    <tr>
      <td><div align="center"><font size="5"><b><font color="#213e9b">合　同　基　本　信　息</font></b></font></div>
          <hr width="100%" size="1" noshade color="#213e9b">
      </td>
    </tr>
  </table>  
  <table height=8 width="100%" 
	bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr> 
      <TD HEIGHT="2" bgcolor="#e8ebf5"><FONT SIZE="2" COLOR="#000000">责 任 人</font>&nbsp;</td>
      <TD HEIGHT="2">
      	${username }
        --- 
        ${dept }&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"></td>
      <td height="2"></td>
    </tr>
    <tr> 
      <td height="5" bgcolor="#e8ebf5"><font size="2">PO#</font></td>
      <td height="5"><label> 
        <input type="text" name="custno">
        </label></td>
      <td height="5" bgcolor="#e8ebf5">&nbsp;</td>
      <td height="5"><font size="2"> 
        <input name="sub" type="hidden" id="custno">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" height="7" bgcolor="#e8ebf5"><font size="2" color="#000000">客户名称</font>&nbsp;</td>
      <td colspan="3" height="7">
        <input type="text" name="coname1" disabled size="70" maxlength="50">
        * <img border="0" src="../images/search.gif" width="27" height="12" id="conameSearchBtn">
        <input type="hidden" name="senddate" size="15">
        <INPUT NAME="coname" TYPE="hidden" ID="coname">
      </td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2" color="#000000">收货地址</font>&nbsp;</td>
      <td colspan="3"><font size="2"> 
        <input name="tr_addr" type="text" id="mode5" size="75" maxlength="150">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2" color="#000000">联 系 人</font>&nbsp;</td>
      <td><font size="2"> 
        <input name="tr_man" type="text" id="tr_man">
        </font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2" color="#000000">联系电话</font>&nbsp;</td>
      <td><font size="2"> 
        <input name="tr_tel" type="text" id="tr_tel">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5">发票&nbsp;</td>
      <td height="2">
        <select name="item" >
        </select>
      &nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">银行费用</font>&nbsp;</td>
      <td height="2"> <font size="2"> 
        <input type="text" name="yj" size="10" value="0" maxlength="10">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" height="2" bgcolor="#e8ebf5">
      <font size="2" color="#000000">运输方式</font>&nbsp;</td>
      <td height="2"><font size="2" color="#000080">
        <input name="tr" type="text" id="courier">
      </font>&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">快递帐号</font>&nbsp;</td>
      <td height="2"><font size="2">
      <input name="trade" type="text" id="acct">
      </font></td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">付款方式</font>&nbsp;</td>
      <td height="2"> 
        <div align="left">
        	<select name="mode" ui="select" label="payment" value="payment" url="../../payway!list.do"></select>
          	付款天数 <input type="text" name="source" size="3" value="0">  天
          	&nbsp;&nbsp;</div>      
      </td>
      <td width="15%" height="2" bgcolor="#e8ebf5">发货日期&nbsp;</td>
      <td height="2" width="38%">
        <input type="text" name="send_date" size="10" ui="date" show-now="true">*&nbsp;&nbsp;</td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">运费负担</font>&nbsp;</td>
      <td height="2"> <font size="2"> 
        <select name="yf_types" id="yf_types">
    <option value="Freight Collection">Freight Collection</option>
          <option value="Freight Prepaid">Freight Prepaid</option>
          <option value="公司支付">公司支付</option>
          <option value="客户支付">客户支付</option>
        </select>
        </font>&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">运费金额</font>&nbsp;</td>
      <td height="2"><font size="2"> 
        <input type="text" name="yf_money" size="10" value="0">
        <font size="2"><font color="#000000">(如是公司支付运费金额填写0)</font></font><font color="#000000"></font></font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" bgcolor="#e8ebf5"><font size="2" color="#000000">其它费用</font>&nbsp;</td>
      <td><font size="2"> 
        <input type="text" name="other_fy" size="10" value="0" maxlength="10">
        </font><font size="2" color="#000080">仅作为核算利润不参与其它运算</font>&nbsp;</td>
      <td height="5" bgcolor="#e8ebf5"><font size="2" color="#000000">PI#</font>&nbsp;</td>
      <td height="5"><font size="2"> 
        <input name="fy_number" type="text" >
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" bgcolor="#e8ebf5"><font size="2"><font color="#000000">货　　币</font></font>&nbsp;</td>
      <td width="33%"> <font size="2" color="#000000"> 
        <select name="money" >
        </select>
        </font>&nbsp;</td>
      <td width="15%" height="2" bgcolor="#e8ebf5">客户订单日期&nbsp;</td>
      <td height="2" width="38%"><input type="text" name="datetime" size="10" ui="date" show-now="true">*&nbsp;&nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" bgcolor="#e8ebf5"><font size="2" color="#000000">条款</font>&nbsp;</td>
      
  
      <td colspan="3">
        <textarea name="tbyq" cols="73" rows="13">${tk }</textarea>
        &nbsp;</td>
    </tr>
    <tr> 
      <td width="15%" height="15" bgcolor="#e8ebf5"><font size="2" color="#000000">备　　注</font>&nbsp;</td>
      <td colspan="3"> 
        <textarea name="remarks" cols="73" rows="4" WRAP="PHYSICAL">${remarks }</textarea>
      </td>
    </tr>
  </table>


<p align="center">
    <button action="sub">登记产品</button> 
	<input type="reset" value="取消" > 
	<input type="button" value="关闭" name="B22342" onClick="window.close()">
</p>

</div>
