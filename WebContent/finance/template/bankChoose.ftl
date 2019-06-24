<table class="table table-bordered table-hover">
	<thead>
		<tr><th>序号</th><th>开户名称</th><th>开户银行</th><th>开户帐号</th></tr>
	</thead>
	<tbody>
		<tr v-for="(r,index) in rows" @click="choose(r)">
			<td>{{index+1}}</td>
			<td>{{r.com_bank}}</td>
			<td>{{r.bank}}</td>
			<td>{{r.bank_num}}</td>
		</tr>
	</tbody>
</table>