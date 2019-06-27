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
	
	new Vue({
		el: '#gathering-container',
		data: {
			loading: false,
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
				this.loading = true;
				
				$.ajax({
					url: webRoot + "/finance/finance!listToGather.do",
					type:'post',
					data: this.form
				}).done(res=> {
					me.loading = false
					console.log("gathering list,,,", res);
					me.$refs["gatheringTable"].setRows(res.rows);
				}).fail(e=> {
					me.loading = false
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
				this.loadData();
				console.log("query,,,", this.sdate, this.edate);
			},
			goGathering(row) {
				this.gatheringId = row.id;
				this.$refs["view"].show();
			}
		}
	});
	
	$("#exportBtn").click(function(){
		
		http.post({
			url:webRoot + "/finance/finance!exportToGather.do"
		}).then(data=>{
			if(data.success){
				window.open("/ReportCenter/view.mvc?id="+data.uuid);
			}else{
				alert("操作失败："+data.msg);
			}
			
		},e=>{
			alert("操作异常");
		})
		
	});
	
};
return module.exports;});