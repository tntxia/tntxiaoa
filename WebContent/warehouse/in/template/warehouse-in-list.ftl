<input type="hidden" name="query" value="true">
<br>
<table id="searchTable" width="100%" bordercolor="#CCBE5A" cellspacing="0"
	bordercolordark="#ffffff" cellpadding="3" align="center"
	bgcolor="#ffffff" bordercolorlight="#7f9db9" border="1">
<tr id="searchVue">
<td colspan="6">
	 入库编号  <input name="ddnum"
		type="text" id="ddnum" size="10"> 采购单编号 <input
		name="orderNumber" type="text" id="ddnum" size="10"> 供 应 商
		<input
		type="text" name="coname" size="10"> 型号 <input
			name="pro_model" type="text" id="pro_model" size="10"> 单据号
			<input name="g_man" type="text" id="pro_model" size="10">
	<br>
	<table width="100%" border="0" cellspacing="0" cellpadding="4">
		<tr align="left">
			<td bgcolor="#ffffff">
				<div align="left">
					起始日期 <input type="text"
						name="startdate" size="10">
					终止日期 <input type="text" name="enddate" size="10"
							value="">
					<SELECT NAME="int_types" ID="int_types">
								<OPTION VALUE=" ">入库类型</OPTION>
								<OPTION VALUE="采购入库">采购入库</OPTION>
								<OPTION VALUE="调帐">调帐</OPTION>
								<OPTION VALUE="退货">退货</OPTION>
								<OPTION VALUE="盘赢">盘赢</OPTION>
								<OPTION VALUE="生产入库">生产入库</OPTION>
								<OPTION VALUE="借用归还">借用归还</OPTION>
						</SELECT> 
						<button action="search">查阅入库明细</button>
						<button action="add">新增入库单</button>

							<input type="button" value="入库单报表"
							onClick="window.open('in/rk_r.jsp?ddnum=&coname=&pro_model=&startdate=&enddate=&g_man=&int_types=','_blank', 'height=500, width=955, top=50, left=50, toolbar=yes, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')">
							
							&nbsp;
					</div></td>
			</tr>
		</table></td>
</tr>

</table>
<div id="inListDatagrid"></div>