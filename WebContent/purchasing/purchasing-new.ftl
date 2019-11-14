
<form name="form1" method="post" action="${basePath}/purchasing/purchasing.dispatch"> 
<br>  
<table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">    
    <tr> 
      <td bgcolor="#e8ebf5">责 任 人</td>
      <td colspan="3">${username }</td>
    </tr>
    <tr> 
      <td width="12%" bgcolor="#e8ebf5">销售合同号</td>
      <td width="44%">
        <input name="sub" size="15">
      </td>
      <td height="2" bgcolor="#e8ebf5">供应商编号</td>
      <td height="2">
        <input name="co_number1" size="15" maxlength="15">
      </td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">供 应 商</td>
      <td>
        <input name="coname" type="text" size="40" ui="choose" choose-action="chooseSupplier">
	  </td>
      <td width="11%" bgcolor="#e8ebf5">联 系 人</td>
      <td width="33%">
      <input name="lxr" type="text" id="lxr" size="15" maxlength="15" ui="choose"  choose-action="chooseContact">
      </td>
    </tr>
    <tr>
      <td bgcolor="#e8ebf5">采购方向</td>
      <td>
        <select name="senddate" ui="select" url="/oa_static/json/purchasingType.json">
        </select>
      </td>
      <td bgcolor="#e8ebf5">运　　费</td>
      <td>
        <input type="text" name="pay_je" size="8" value="0">
		<select name="yfmoney" ui="select" url="../current!list.do" value="currname" label="currname" >
        </select>
		</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">公司地址</td>
      <td>
        <input size="50" type="text" name="supplier_addr" value="">
      </td>
      <td height="16" bgcolor="#e8ebf5">电    话</td>
      <td height="16">
        <input type="text" name="tel" value="">
       </td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">传     真&nbsp;</td>
      <td>
       <input type="text" name="fax" value="">
      </td>
      <td bgcolor="#e8ebf5">所得评分</td>
      <td>
		<div id="pfDiv" style="width:100%"></div>
      </td> 
    </tr>
    <tr><td colspan="4">
    货物自提：<input type="checkbox" value="self_carry" name="self_carry" id="self_carry" onClick="selfGet();">
    <hr>
    <fieldset id="express_options">
    <legend>快递收货选项：</legend>
    <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">收件人</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <input type="text" name="receiver" value="${username}">
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">收件人电话</font>&nbsp;</td>
      <td height="16"><font size="2">
      <input type="text" name="receiver_tel" value="${mode.q_tel}">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">收件人地址</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <input size="50" type="text" name="receiver_addr" value="${mode.q_addr}">
        </font>&nbsp;</td>
		
		
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">交易地点</font></td>
      
	  <td height="16"><font size="2">
<select id="jydd" name="jydd">
       	  <option value=""  selected="selected">请选择</option>
          <option value="深圳交货">深圳交货</option>
		  <option value="香港交货">香港交货</option>
		  <option value="深圳柜台地址">深圳柜台地址</option>
</select>
	  
	  </font></td>
    </tr>
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">运费负担</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <select id="freight" name="freight">
       	  <option value="">请选择</option>
          <option value="卖家支付">卖家支付</option>
		  <option value="买家支付">买家支付</option>
          <option value="Freight Collect">Freight Collect</option>
          <option value="Freight Prepaid">Freight Prepaid</option>
		  
		</select>
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">物流公司</font>&nbsp;</td>
      <td height="16"><font size="2"> 
      	<input type="text" name="express_company" value="">
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">快递帐号</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <input type="text" name="acct" value="">
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">服务类型</font>&nbsp;</td>
      <td height="16"><font size="2"> 
      <input type="text" name="service_type" value="">
        </font>&nbsp;</td>
    </tr>
    
    </table>
    </fieldset>
    </td></tr>
    
    <tr> 
      <td width="12%" height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">付款方式</font>&nbsp;</td>
      <td height="16"><font size="2"> 
       <select name="pay_type">
       	<#list payway as p>
       	<option>${p.payment }</option>
       	</#list>
        </select>
        </font>&nbsp;</td>
      <td height="16" bgcolor="#e8ebf5"><font size="2" color="#000000">含税率</font>&nbsp;</td>
      <td height="16">
      	<select name="rate">
        </select>
      </td>
    </tr>
    <tr>
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">交货日期</font>&nbsp;</td>
      <td height="2"><font size="2"> 
        <input name="pay_if" type="text" id="pay_if" size="10">
        <a href="javascript:pic2_onclick()"><img border="0" src="../images/calendar.jpg" id="pic2" width="20" height="16"></a></font>&nbsp;</td>
      <td height="2" bgcolor="#e8ebf5"><font size="2">签 订 地</font></td>
      <td height="2"><font size="2">
        <input type="text" name="ponum" size="12"  value="深圳市">
      </font></td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">采购日期</td>
      <td height="2">
        <input name="datetime" value="" size="10" ui="date" show-now="true">
      </td>
      <td bgcolor="#e8ebf5">采购货币</td>
      <td height="2">
        <select name="money" >
        	<#list currentTypeList as c>
			<option>${c.currname }</option>
			</#list>
        </select>
      </td>
    </tr>
    <tr> 
      <td width="12%" height="2" bgcolor="#e8ebf5"><p><font size="2" color="#000000">条</font></p>
          <p>&nbsp;</p>
          <p><font size="2" color="#000000">款</font></p></td>
      <td colspan="3" height="2"><font size="2"> 
        <textarea name="tbyq" cols="70" rows="20">${mode.q_tk}</textarea>
        </font>&nbsp;</td>
    </tr>
    <tr> 
      <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">备　　注</font>&nbsp;</td>
      <td colspan="3" height="2"> <font size="2"> 
        <textarea name="remarks" cols="70" rows="3"></textarea>
        </font>&nbsp;</td>
    </tr>
    <tr>
        <td height="2" bgcolor="#e8ebf5"><font size="2" color="#000000">审 批 人</font>&nbsp;</td>
        <td colspan="3" height="2"><FONT SIZE="2" COLOR="#000000">
            <SELECT NAME="spman" >
            	<#list cgsp as c>
            	<option>${c.dd_man }</option>
            	</#list>
            </SELECT>
        </font></td>
    </tr>
    
  </table>
</form>
	      <center>
            <input id="nextBut" type="button" name="Submit" value="下一步">
            <label>
            <input type="button" name="Submit2" value="重填" onclick="resetForm()">
            </label>
	      </center>

 
