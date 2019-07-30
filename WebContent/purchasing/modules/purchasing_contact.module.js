(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('purchasing_contact',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let url = webRoot+"/purchasing/supplier!listContact.do";
	new Vue({
		el: '#app',
		data: {
			dataset: {
				url: url,
				method: 'post',
				pageSize: 50
			},
			form: {
				name: null
			}
		},
		mounted() {
		},
		methods: {
			getUrl:function(row){
				return webRoot+"/qcontact/c-main-1v.jsp?id="+row.nameid
			},
			query() {
				let datagrid = this.$refs["datagrid"];
				datagrid.setParams(this.form);
				datagrid.loadData();
			},
			toAdd() {
				window.open(webRoot+'/supplier/new.mvc');
			},
			toImport() {
				window.open(webRoot+'/supplier/import.mvc');
			}
		}
	});
	
};
return module.exports;});