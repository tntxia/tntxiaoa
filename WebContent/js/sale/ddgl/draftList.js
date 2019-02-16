$(function() {

	new Vue({
		el : '#app',
		data : {
			searchModel:{
				model : null,
				coname : null,
				number : null,
				pro_number : null,
				depts : null,
				man : null,
				pStates : null,
				startdate : null,
				enddate : null
			},
			rows:[]
		},
		created:function(){
			this.search();
		},
		methods:{
			
			getUrl:function(id){
				return webRoot
				+ "/sale/ddgl/detailDraft.mvc?id="+id;
			},
			
			search:function(){
				var vm = this;
				$.ajax({
					url:webRoot + '/sale/sale!list.do',
					type:'post',
					data:vm.searchModel,
					success:function(data){
						vm.rows = data.rows;
					},
					error:function(e){
						alert("访问后台服务失败");
					}
				});
			},
			toAdd:function(){
				
				OACommonUse.openOrderTemplateChoose("新增销售合同 - 选择模板",webRoot + "/sale/ddgl/new.mvc");
				
			}
		}
	});

});
