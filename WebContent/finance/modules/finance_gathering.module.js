(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_gathering',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/finance_gathering.html';
exports.init = function(){
	
	let url = webRoot + "/finance/finance!listToGather.do";
	
	new Vue({
		el: '#gathering-container',
		data: {
			loading: false,
			dataset: {
				url: url,
				method: 'post',
				pageSize: 50
			},
			stasticLoading: false,
			departmentList: [],
			form: {
				coname: null,
				fpnum: null,
				sdate: null,
				edate: null,
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
					url:webRoot + "/finance/finance!gatherStatist.do",
					type:'post',
					data:this.form
				}).done(function(data){
					me.stasticLoading = false;
					me.totalAll = data.totalAll;
					me.stotalAll = data.stotalAll;
					me.rTotalAll = data.rTotalAll;
					me.gatheredAll = data.gatheredAll;
					me.leftAll = data.leftAll;
				}).fail(function(){
					me.stasticLoading = false;
				})
			},
			query() {
				let datagrid = this.$refs["gatheringTable"];
				datagrid.setParams(this.form);
				datagrid.loadData();
				this.loadData();
				console.log("query,,,", this.sdate, this.edate);
			},
			goGathering(row) {
				this.gatheringId = row.id;
				this.$refs["view"].show();
			},
			exportGathering() {
				$.ajax({
					url:webRoot + "/finance/finance!exportToGather.do"
				}).done(res=> {
					if(res.success){
						window.open("/ReportCenter/view.mvc?id="+res.uuid);
					}else{
						alert("操作失败："+data.msg);
					}
				}).fail(e=> {
					alert("操作异常");
				});
			}
		}
	});
	
	
};
return module.exports;});