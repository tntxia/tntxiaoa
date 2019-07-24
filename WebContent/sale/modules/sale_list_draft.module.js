(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_list_draft',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let url = webRoot + '/sale/sale!list.do';
	
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
			departmentList: [],
			userList: [],
			form: {
				model : null,
				coname : null,
				number : null,
				pro_number : null,
				depts : '',
				man : '',
				pStates : null,
				startdate : null,
				enddate : null
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
				return webRoot
				+ "/sale/ddgl/detailDraft.mvc?id="+row.id;
			},
			loadData() {
				let me = this;
				
				$.ajax({
					url:webRoot+"/department!list.do",
					type:'post',
					success:function(data){
						let departmentList = [];
						$.each(data,function(i,r){
							departmentList.push(r.departname);
						});
						me.departmentList = departmentList;
					},
					dataType:'json'
				});
				
				this.stasticLoading = true;
				$.ajax({
					url:webRoot + "/sale/sale!getSaleUserList.do",
					type:'post',
					data:this.form
				}).done(function(data){
					me.userList = data;
				}).fail(function(){
					me.stasticLoading = false;
				})
			},
			query() {
				let datagrid = this.$refs["datagrid"];
				datagrid.setParams(this.form);
				datagrid.loadData();
			},
			toAdd() {
				BootstrapUtils.createDialog({
					id:'chooseOrderTemplateModal',
					title:"新增销售合同 - 选择模板",
					template:webRoot+'/template/chooseOrderTemplate.mvc',
					onFinish:function(){
						var dialog = this;
						var vm = new Vue({
							data:{
								rows:[],
								page:1,
								params: {
									type: 'sale'
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
									return webRoot + "/sale/ddgl/new.mvc?id="+id;
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