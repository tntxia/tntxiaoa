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
			departmentList: [],
			form: {
				coname: null,
				fpnum: null,
				sdate: null,
				edate: null,
			}
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
				
				$.ajax({
					url: webRoot + "/finance/finance!listToGather.do",
					data: this.form
				}).done(res=> {
					console.log("gathering list,,,", res);
					me.$refs["gatheringTable"].setRows(res.rows);
				})
			},
			query() {
				this.loadData();
				console.log("query,,,", this.sdate, this.edate);
			},
			goGathering(row) {
				router.goRoute("finance_gathering_view", {
					id: row.id
				});
			}
		}
	});
	
	$("#searchBtn").click(function(){
		var param = $("#searchForm").getParamMap(true);
		grid.load(param);
		renderStatic();
	});
	
	renderStatic();
	
	function renderStatic(){
		
		var param = $("#searchForm").getParamMap();
		
		$.ajax({
			url:webRoot + "/finance/finance!gatherStatist.do",
			type:'post',
			data:param
		}).done(function(data){
			$("#statistSpan").html("合同总金额："+data.totalAll+" 出库总金额："+data.stotalAll+" 退货总金额："+data.rTotalAll+" 已收款："+data.gatheredAll + " 欠款："+data.leftAll);
		}).fail(function(){
			
		})
	}
	
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