
<div id="mainDiv">
<div class="leftbar">
	<div class="leftbar-item">
		<div class="leftbar-header">首页</div>
		<ul class="leftbar-list">
			<li>
				<img src="${basePath}/svg/bbs.svg" height="14" width="14"> 
			   	<a href="#" onClick="window.open('/bbs/','_blank')">
			   		内部论坛
			   	</a>
			</li>
			<li>
				<img src="${basePath}/svg/notice.svg" height="14" width="14"> 
			   	<a href="#"   onClick="window.open('${basePath}/public/new.mvc','nw')">
			   		新增公告
			   	</a>
			</li>
			<li>
				<i class="glyphicon glyphicon-envelope"></i> <a href="${basePath}/mail.mvc">邮件管理</a>
			</li>
		</ul>
	</div>
</div>
<div class="main_sec">
	<div id="timeDiv" align="right" style="margin-right: 10px;"></div>
	<TABLE width="98%" border=0 align="center" cellPadding=1 cellSpacing=1>
  <TBODY>
  <TR> 
    <TD colspan="3" > 
  <table height=8 width="100%" class="customer-follow-table"
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1">        <tr bgcolor="#d3d8eb" > 
          <td width="43"> 
            <div align="left"> #&nbsp;</div>
          </td>
          <td width="701" height="19"> 
            <div align="left">洽谈内容&nbsp;</div>
          </td>
          <td width="237" height="19"> 
            <div align="left">客户名称&nbsp;</div>
          </td>
          <td width="143" height="19"> 
            <div align="left">联系电话&nbsp;</div>
          </td>
        </tr>
        <tbody>
        <#list customerChatList as c>
        <tr> 
          <td width="43">
          <font size="2" color="#000000"><a href="#" onClick="javascript:window.open('xclient/custo/conversationFollow.mvc?id=${c.id }','_blank')">${c_index+1 } 
            </a></font>&nbsp;</td>
          <td width="701" height="8"> 
            <div align="left"><font size="2"> <a href="#" onClick="javascript:window.open('xclient/custo/conversationFollow.mvc?id=${c.id }','_blank')">${c.c_nr }</a> 
              </font>&nbsp;</div>
          </td>
          <td width="237" height="8"> 
            <div align="left"><a href="#"  onClick="javascript:window.open('xclient/cview.jsp?coid=${c.coid }','nw')">${c.c_name }</a>&nbsp;</div>
          </td>
          <td width="143" height="8"> 
            <div align="left"><font size="2">${c.cotel }</font>&nbsp;</div>
          </td>
        </tr>
        </#list>
        </tbody>
        
      </table>
      
    </TD>
  </TR>
  </TBODY> 
</TABLE>

<p><font size="2" color="#213e9b"><strong>公告 >> </strong></font></p>
<table width="98%" id="pubTable"
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3"
                        align="center" bordercolorlight="#7f9db9" 
                        border="1">
  <tr bgcolor="#d3d8eb" > 
    <td width="43" height="24"> <div align="left"><font size="2"> #</font>&nbsp;</div></td>
    <td width="837" height="24"> <div align="left"><font size="2"> 公告主题</font>&nbsp;</div></td>
    <td width="132" height="24"> <div align="left"><font size="2">发布时间</font>&nbsp;</div></td>
    <td width="116" height="24"> <div align="left"><font size="2">发布人</font>&nbsp;</div></td>
  </tr>
  
  <tr style="cursor:hand;" 
   onMouseOver="this.bgColor='#B5DAFF'" onMouseOut="this.bgColor='#ffffff'" v-for="(r,index) in list"> 
    <td width="43" height="24">
    	<a :href="getUrl(r.id)" target="_blank">{{index+1}} </a>
    &nbsp;</td>
    <td width="837" height="24"> <div align="left">
    	<a :href="getUrl(r.id)" target="_blank">{{r.titel }}</a>&nbsp;</div>
    </td>
    <td width="132" height="24"> <div align="left">{{r.fvdate }}&nbsp;</div></td>
    <td width="116" height="24"> <div align="left">{{r.username }}&nbsp;</div></td>
  </tr>
  
</table>
</div>
</div>
<!-- 为了方便管理，所有的弹出窗口都放在这里 -->
<div id="dialogsDiv" style="position: relative;z-index: 10000;">
	<my-todo-dialog ref="myTodoDialog"></my-todo-dialog>
	<warehouse-send-bill-add-dialog ref="warehouseSendBillAddDialog"></warehouse-send-bill-add-dialog>
	<warehouse-send-bill-view-dialog ref="warehouseSendBillViewDialog"></warehouse-send-bill-view-dialog>
	<warehouse-send-bill-edit-dialog ref="warehouseSendBillEditDialog"></warehouse-send-bill-edit-dialog>
	<sale-approved-choose-dialog ref="saleApprovedChooseDialog"></sale-approved-choose-dialog>
</div>

