(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('purchasing_list_draft',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let url = webRoot + '/purchasing/purchasing!list.do';
	new Vue({
		el: '#app',
		data: {
			loading: false,
			dataset: {
				url: url,
				method: 'post',
				pageSize: 50
			},
			stasticLoading: false,
			brandList: [],
			form: {
				epro : null,
				supplier : ''
			},
			gatheringId: null,
			totalAll: null,
			stotalAll: null,
			rTotalAll: null,
			gatheredAll: null,
			leftAll: null
		},
		mounted() {
			this.loadData();
		},
		methods: {
			getUrl:function(row){
				return webRoot+"/purchasing/detail.mvc?id="+row.id
			},
			loadData() {
				let me = this;
				
				$.ajax({
					url:webRoot+"/purchasing/purchasing!trademarkList.do",
					type:'post',
					success:function(data){
						me.brandList = data;
					},
					dataType:'json'
				});
			},
			query() {
				let datagrid = this.$refs["datagrid"];
				datagrid.setParams(this.form);
				datagrid.loadData();
			},
			toAdd() {
				BootstrapUtils.createDialog({
					id:'chooseOrderTemplateModal',
					title:"新增采购合同 - 选择模板",
					template:webRoot+'/template/chooseOrderTemplate.mvc',
					onFinish:function(){
						var dialog = this;
						var vm = new Vue({
							data:{
								rows:[],
								page:1,
								params:{
									type: 'purchase'
								}
							},
							created:function(){
								this.fetchData();
							},
							methods:{
								fetchData:function(){
									var vm = this;
									$.ajax({
										url:webRoot+"/template!list.do",
										type:'post',
										data:vm.params,
										success:function(data){
											vm.rows = data;
										}
									});
								},
								getUrl:function(id){
									return "../ddgl/new.mvc?id="+id;
								}
							}
						});
						
						vm.$mount(this.find(".modal-body").get(0));
					}
				});
				$("#chooseOrderTemplateModal").modal('show');
			}
		}
	});
	
};
return module.exports;});