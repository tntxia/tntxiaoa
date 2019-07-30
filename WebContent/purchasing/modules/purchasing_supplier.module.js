(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('purchasing_supplier',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let url = webRoot+"/purchasing/supplier!list.do";
	new Vue({
		el: '#app',
		data: {
			countryDataset: {
				url: "/geography/country!list.do"
			},
			provinceDataset: {
				url: "/geography/province!list.do"
			},
			dataset: {
				url: url,
				method: 'post',
				pageSize: 50
			},
			countryList: [],
			form: {
				country: null,
				province: null,
				tradetypes: '',
				coname: null,
				cojsfs: null,
				scale: null	
			}
		},
		mounted() {
		},
		methods: {
			getUrl:function(row){
				return webRoot+"/supplier/view.mvc?id="+row.id
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