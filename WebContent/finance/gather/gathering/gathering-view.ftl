
<div id="vue">
<table height=8 width="100%"
bordercolor="#CCBE5A" cellspacing="0" bordercolordark="#ffffff" cellpadding="3"
 align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" border="1">
 
	<tr bgcolor="#d3d8eb" height="5">
		<td width="17%"  bgcolor="#d3d8eb">
			<strong style="color:#213e9b">收款信息 </strong>
		</td>
	    <td colspan="3" bgcolor="#d3d8eb" align="right"> 
			<button @click="editInvoiceBtn">填写发票</button>
			<button @click="finishGathering">完成收款</button>
			<button @click="del">删除</button>
	    </td>
    </tr>
	
    <tr> 
      <td bgcolor="#e8ebf5"><FONT SIZE="2">合同编号</font>&nbsp;</td>
      <td><FONT SIZE="2" COLOR="#000000">{{orderform}}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><FONT SIZE="2">PO#</font>&nbsp;</td>
      <td><FONT SIZE="2" COLOR="#000000">{{custno}}</font></td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">实收金额</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000">{{smoney }}</FONT><FONT SIZE="2" COLOR="#000080"> 
        　　　</font>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2"> 合同金额</font>&nbsp;</td>
      <td><font size="2" color="#000000">{{priceTotal.orderTotal }}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2"> 运费金额</font>&nbsp;</td>
      <td><font size="2" color="#000000">{{bank }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">客户名称</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000"> <a href="#" @click="openCoView()">{{coname }}</a></font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><FONT SIZE="2">客户类别</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000"> <a href="#" @click="openCoView()">{{co_number}}</a></font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">目标起运日</font>&nbsp;</td>
      <td width="27%"> <FONT SIZE="2" COLOR="#000000">{{yjskdate }}</font>&nbsp;</td>
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">实际起运日</font>&nbsp;</td>
      <td width="40%"> <FONT SIZE="2" COLOR="#000000">{{sjdate }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">预计收款日期</font>&nbsp;</td>
      <td width="27%"> <FONT SIZE="2" COLOR="#000000">{{sjskdate }}</font>&nbsp;</td>
      <td width="17%" height="15" bgcolor="#e8ebf5"><FONT SIZE="2">付款方式</font>&nbsp;</td>
      <td width="40%" height="15"> <FONT SIZE="2" COLOR="#000000">{{mode}}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="16%" bgcolor="#e8ebf5"><font size="2">币　　值</font>&nbsp;</td>
      <td width="29%"> <font size="2" color="#000000"></font>&nbsp;</td>
      <td bgcolor="#e8ebf5">是否已开票</td>
      <td>{{bankaccounts }}</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">发　　票</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{rate }}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">开票金额</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{i_man }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">开票日期&nbsp;</td>
      <td>{{sendcompany}}&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">票据号</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{remark}}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">销 售 员</font>&nbsp;</td>
      <td> <font size="2" color="#000000">{{saleman}}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">所在部门</font>&nbsp;</td>
      <td> <font size="2" color="#000000">{{sale_dept}}</font>&nbsp;</td>
    </tr>
	
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">收款情况</font>&nbsp;</td>
	  
	  
      <td colspan="3"><span id="noteShow">{{note}}</span>&nbsp;
      
      <div v-if="showNoteFlag">
      	<select v-model="noteValue">
		  <option value=""></option>
		  <option value="已收款">已收款</option>
		  <option value="未收款">未收款</option>
		  <option value="收款中">收款中</option>
	  	  <option value="部分收款">部分收款</option>
		</select>
		<button @click="noteIt">确定</button><button @click="cancelNote">取消</button>
		
      </div>
      <input v-if="!showNoteFlag" id="noteItBut" type="button" value="评论" @click="toNoteIt">
	 
	  
	  
	  </td>
    </tr>
  </table>
  <br>  <table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">
    <tr bgcolor="#d3d8eb"> 
      <td width="227" > 
        <div align="left"> 
          
          产品型号&nbsp;</div>      </td>
      <td width="436" > 
        <div align="left"><font size="2">产品批号&nbsp;</font></div>      </td>
      <td width="119" > 
        <div align="right"><font size="2">订单数量&nbsp;</font></div>      </td>
      <td width="119" > 
        <div align="right"><font size="2">出货数量&nbsp;</font></div>      </td>
      <td width="110" > 
        <div align="right"><font size="2">单价&nbsp;</font></div>      </td>
      <td width="111" > 
        <div align="right"><font size="2">订单合计&nbsp;</font></div>      </td>
      <td width="111" > 
        <div align="right"><font size="2">出货合计&nbsp;</font></div>      </td>
    </tr>
    
    <tr height="8" v-for="pro in proList"> 
      <td width="227" height="8" > 
        <div align="left">{{pro.epro }}
          &nbsp;</div>      </td>
      <td width="436" height="8" >
		{{pro.cpro }}&nbsp;
		</td>
      <td width="119" height="8" > 
        <div align="right"><font size="2" color="#000000">
        	{{pro.num }}
          </font><font size="2">&nbsp;</font></div>      </td>
      <td width="119" height="8" > 
        <div align="right"><font size="2" color="#000000"> 
          {{pro.s_num }} </font><font size="2">&nbsp;</font></div>      </td>
      <td width="110" height="8" > 
        <div align="right"><font size="2" color="#000000"> 
          {{pro.salejg }}
          {{pro.money }}
          
          </font>&nbsp;</div>      </td>
      <td width="111" height="8" > 
        <div align="right">{{pro.num*pro.salejg }}
        &nbsp;</div>      </td>
      <td width="111" height="8" > 
        <div align="right">{{pro.s_num*pro.salejg }}&nbsp;</div>      </td>
    </tr>
	
	
    <tr> 
      <td colspan="5"> 
        <div align="right">合计金额：</div>      </td>
      <td>
        <div align="right">{{priceTotal.orderTotal }}</div>      </td>
      <td> 
        <div align="right">{{priceTotal.outTotal }}</div>      </td>
    </tr>
    
	<tr> 
      <td colspan="5"> 
        <div align="right">
          <a @click="addCredit">新增往来帐目</a>
          
          <font size="2">欠款金额：</font></div>      </td>
      <td width="111">
        <div align="right">
        	{{priceTotal.outTotal-smoney}}
        </div>      
      </td>
      <td> 
          
      </td>
    </tr>
  </table>
</div>
