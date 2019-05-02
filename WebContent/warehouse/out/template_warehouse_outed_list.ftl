<div id="outed">
	<strong>已出库列表 </strong>
			
			<div id="searchForm">
            		<div>
            		客户名称<input size="10" v-model="coname">
					产品型号<input name="pro_model" type="text" v-model="model" size="10">
					合同编号<input name="pro_types" type="text" v-model="number" size="10">
					公司合同号<input name="sub" type="text" v-model="sub" size="10">
					&nbsp;
					</div>
					<div>
					出库序号
                    <input name="number" type="text" id="number" size="10">
					起始日期
					<input name="startdate" type="text" id="startdate" size="10">
            		 终止日期
            		<input name="enddate" type="text" id="enddate" size="10">
            		
            		<button @click="fetchData">查询</button>
            		<input type="button" value="打印预览出库报表" 
            				onClick="window.open('out-report.jsp','nrtop')">
            		</label>
            		</strong></a>&nbsp;
            		</div>
            	</div>
			
			<div id="outedData">
				<br>  

<table height=8 width="100%" 
bordercolor="#CCBE5A" cellspacing="0" 
                        bordercolordark="#ffffff" cellpadding="3" 
                        align="center" bgcolor="#ffffff" bordercolorlight="#7f9db9" 
                        border="1" id="outedVue">
        <tr bgcolor="#d3d8eb">
          <td height="17" nowrap><div align="left">序号</div></td>
          <td nowrap><div align="left">合同编号&nbsp;</div></td>
          <td nowrap><div align="left">公司合同号&nbsp;</div></td>
          <td height="17" nowrap><div align="left">客户名称&nbsp;</div></td>
          <td height="17" nowrap><div align="left">产品型号&nbsp;</div></td>
          <td nowrap><div align="left">产品批号</div></td>
          <td nowrap><div align="left">税率</div></td>
          <td nowrap><div align="left">单价</div></td>
          <td nowrap><div align="left">货币</div></td>
          <td height="17" nowrap><div align="left">数量&nbsp;</div></td>
          <td height="17" nowrap><div align="left">出库日期&nbsp;</div></td>
          <td nowrap><div align="left">出库人&nbsp;</div></td>
          <td height="17" nowrap><div align="left">仓库名称&nbsp;</div></td>
          <td height="17" nowrap><div align="left">销售员&nbsp;</div></td>
      </tr>
        
        <tr v-for="(r,index) in rows">
            <td height="8" nowrap><div align="left"><font size="2">
            
		  <a :href="getUrl(r.ddid)" target="_blank">
            {{index+1}}</a></font>&nbsp;</div></td>
          <td nowrap><div align="left">
          
		  
			<a :href="getUrl(r.ddid)" target="_blank">{{r.pro_fynum}}</a>
			
		  &nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">{{r.sub}}
          
          
          </font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2"> {{r.pro_coname}}</font>&nbsp;</div>
          </td>
		  <td height="8" nowrap><div align="left"><font size="2">{{r.pro_model}}  
           </font>&nbsp;</div></td>
		  <td nowrap><div align="left">{{r.pro_name}} &nbsp;</div></td>
		  
          <td nowrap><div align="left"><font size="2">{{r.pro_rate}}</font>&nbsp;</div></td>
            <td nowrap><div align="left"><font size="2">
            	{{r.salejg}}
                
            </font>&nbsp;</div></td>
		  
          <td nowrap><div align="left"><font size="2">{{r.pro_price_hb}}</font>&nbsp;</div></td>
			<td height="8" nowrap><div align="left"><font size="2">{{r.pro_num}} {{r.pro_unit}}</font></div></td>
          <td height="8" nowrap><div align="left"><font size="2">{{r.pro_datetime}}</font>&nbsp;</div></td>
          <td nowrap><div align="left"><font size="2">{{r.slinkman}}</font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2">{{r.slinktel}}</font>&nbsp;</div></td>
          <td height="8" nowrap><div align="left"><font size="2">{{r.man}}</font>&nbsp;</div></td>
      </tr>
        
        <tr>
            <td colspan=14 align=center height="22">
            <div align="right"> 
            共{{totalAmount}}个已出库单,当前是第{{page}}页,共{{totalPage}}页 <font color="#C1D9FF" size="2">
                    
                    <a v-show="page<totalPage" @click="nextPage()">下一页</a>
             
                    <a v-show="page>1" @click="prevPage()">上一页</a>
                    
                    </font>&nbsp;</div></td>
        </tr>
  </table>

<br>
			
			</div>
</div>