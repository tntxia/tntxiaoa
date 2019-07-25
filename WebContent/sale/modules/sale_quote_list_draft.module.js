(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_quote_list_draft',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let queryStatus = router.getParam("status");
	if (!queryStatus) {
		queryStatus = "";
	}
	
	let url = webRoot + '/sale/sale!listQuote.do';
	
	new Vue({
		el: '#app',
		data: {
			loading: false,
			dataset: {
				url: url,
				method: 'post',
				pageSize: 50
			},
			form: {
				model : null,
				coname : null,
				number : null,
				status: queryStatus
			}
		},
		mounted() {
		},
		methods: {
			getUrl:function(row){
				return webRoot
				+ "/sale/quote/view.mvc?id="+row.id;
			},
			query() {
				let datagrid = this.$refs["datagrid"];
				debugger
				datagrid.setParams(this.form);
				datagrid.loadData();
			},
			toAdd() {
				let url =  webRoot
				+ "/sale/quote/new.mvc";
				window.open(url);
			},
			chooseInquiry() {
				window.open(webRoot
						+ "/sale/quote/xjmain.jsp");
			}
		}
	});
	
};
return module.exports;});