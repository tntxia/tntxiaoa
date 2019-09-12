
<div id="vue">
<input type="hidden" name="clientId" id="clientId" value="${coId }" />

<button id="addBtn">增加联系人</button>

<table class="table table-border table-hover">
	<thead>
		<tr>
			<th>联系人</th>
			<th>先生/小姐</th>
			<th>办公室电话</th>
			<th>职位</th>
			<th>QQ</th>
			<th>MSN</th>
			<th>SKYPE</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="r in rows">
			<td>{{r.name}}</td>
			<td>{{r.mr}}</td>
			<td>{{r.tel}}</td>
			<td>{{r.job}}</td>
			<td>{{r.qq}}</td>
			<td>{{r.msn}}</td>
			<td>{{r.skype}}</td>
			<td><button @click="del(r.nameid)">删除</button></td>
		</tr>
	</tbody>
</table>
</div>

