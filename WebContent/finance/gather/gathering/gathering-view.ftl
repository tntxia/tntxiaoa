
<div id="vue">
<table height=8 width="100%"
bordercolor="#CCBE5A" cellspacing="0" bordercolordark="#ffffff" cellpadding="3"
 align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" border="1">
 
	<tr bgcolor="#d3d8eb" height="5">
		<td width="17%"  bgcolor="#d3d8eb">
			<strong style="color:#213e9b">�տ���Ϣ </strong>
		</td>
	    <td colspan="3" bgcolor="#d3d8eb" align="right"> 
			<button @click="editInvoiceBtn">��д��Ʊ</button>
			<button @click="finishGathering">����տ�</button>
			<button @click="del">ɾ��</button>
	    </td>
    </tr>
	
    <tr> 
      <td bgcolor="#e8ebf5"><FONT SIZE="2">��ͬ���</font>&nbsp;</td>
      <td><FONT SIZE="2" COLOR="#000000">{{orderform}}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><FONT SIZE="2">PO#</font>&nbsp;</td>
      <td><FONT SIZE="2" COLOR="#000000">{{custno}}</font></td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">ʵ�ս��</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000">{{smoney }}</FONT><FONT SIZE="2" COLOR="#000080"> 
        ������</font>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2"> ��ͬ���</font>&nbsp;</td>
      <td><font size="2" color="#000000">{{priceTotal.orderTotal }}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2"> �˷ѽ��</font>&nbsp;</td>
      <td><font size="2" color="#000000">{{bank }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">�ͻ�����</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000"> <a href="#" @click="openCoView()">{{coname }}</a></font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><FONT SIZE="2">�ͻ����</font>&nbsp;</td>
      <td> <FONT SIZE="2" COLOR="#000000"> <a href="#" @click="openCoView()">{{co_number}}</a></font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">Ŀ��������</font>&nbsp;</td>
      <td width="27%"> <FONT SIZE="2" COLOR="#000000">{{yjskdate }}</font>&nbsp;</td>
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">ʵ��������</font>&nbsp;</td>
      <td width="40%"> <FONT SIZE="2" COLOR="#000000">{{sjdate }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="17%" bgcolor="#e8ebf5"><FONT SIZE="2">Ԥ���տ�����</font>&nbsp;</td>
      <td width="27%"> <FONT SIZE="2" COLOR="#000000">{{sjskdate }}</font>&nbsp;</td>
      <td width="17%" height="15" bgcolor="#e8ebf5"><FONT SIZE="2">���ʽ</font>&nbsp;</td>
      <td width="40%" height="15"> <FONT SIZE="2" COLOR="#000000">{{mode}}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td width="16%" bgcolor="#e8ebf5"><font size="2">�ҡ���ֵ</font>&nbsp;</td>
      <td width="29%"> <font size="2" color="#000000"></font>&nbsp;</td>
      <td bgcolor="#e8ebf5">�Ƿ��ѿ�Ʊ</td>
      <td>{{bankaccounts }}</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">������Ʊ</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{rate }}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">��Ʊ���</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{i_man }}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5">��Ʊ����&nbsp;</td>
      <td>{{sendcompany}}&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">Ʊ�ݺ�</font>&nbsp;</td>
      <td><font size="2" color="#000000" face="Arial, Helvetica, sans-serif">{{remark}}</font>&nbsp;</td>
    </tr>
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">�� �� Ա</font>&nbsp;</td>
      <td> <font size="2" color="#000000">{{saleman}}</font>&nbsp;</td>
      <td bgcolor="#e8ebf5"><font size="2">���ڲ���</font>&nbsp;</td>
      <td> <font size="2" color="#000000">{{sale_dept}}</font>&nbsp;</td>
    </tr>
	
    <tr> 
      <td bgcolor="#e8ebf5"><font size="2">�տ����</font>&nbsp;</td>
	  
	  
      <td colspan="3"><span id="noteShow">{{note}}</span>&nbsp;
      
      <div v-if="showNoteFlag">
      	<select v-model="noteValue">
		  <option value=""></option>
		  <option value="���տ�">���տ�</option>
		  <option value="δ�տ�">δ�տ�</option>
		  <option value="�տ���">�տ���</option>
	  	  <option value="�����տ�">�����տ�</option>
		</select>
		<button @click="noteIt">ȷ��</button><button @click="cancelNote">ȡ��</button>
		
      </div>
      <input v-if="!showNoteFlag" id="noteItBut" type="button" value="����" @click="toNoteIt">
	 
	  
	  
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
          
          ��Ʒ�ͺ�&nbsp;</div>      </td>
      <td width="436" > 
        <div align="left"><font size="2">��Ʒ����&nbsp;</font></div>      </td>
      <td width="119" > 
        <div align="right"><font size="2">��������&nbsp;</font></div>      </td>
      <td width="119" > 
        <div align="right"><font size="2">��������&nbsp;</font></div>      </td>
      <td width="110" > 
        <div align="right"><font size="2">����&nbsp;</font></div>      </td>
      <td width="111" > 
        <div align="right"><font size="2">�����ϼ�&nbsp;</font></div>      </td>
      <td width="111" > 
        <div align="right"><font size="2">�����ϼ�&nbsp;</font></div>      </td>
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
        <div align="right">�ϼƽ�</div>      </td>
      <td>
        <div align="right">{{priceTotal.orderTotal }}</div>      </td>
      <td> 
        <div align="right">{{priceTotal.outTotal }}</div>      </td>
    </tr>
    
	<tr> 
      <td colspan="5"> 
        <div align="right">
          <a @click="addCredit">����������Ŀ</a>
          
          <font size="2">Ƿ���</font></div>      </td>
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
