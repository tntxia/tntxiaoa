(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('assay_sale',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/assay_sale.html';
exports.init = function(){
	
	var vue = new Vue({
		el:'#res_table',
		data:{
			rows:[],
			totals:[],
			count:0,
			page:1,
			pageJump:1,
			totalPage:0,
			userList:[],
			param:{
				
			}
		},
		created:function(){
			
		},
		methods:{
			
			getUrl:function(id){
				return webRoot+"/sale/ddgl/detailAudited.mvc?id="+id;
			},
			
			nextPage:function(){
				this.page = this.page+1;
				this.fetchData();
			},
			
			prePage:function(){
				this.page = this.page-1;
				this.fetchData();
			},
			
			jumpPage:function(){
				this.page = this.pageJump;
				this.fetchData();
			},
			
			fetchData:function(param){
				
				param.page = this.page;
				
				$.ajax({
					url:webRoot+'/sale/sale!getStatistic.do',
					type:'post',
					data:param,
					success:function(data){
						Vue.set(vue,"rows",data.rows);
						Vue.set(vue,"count",data.totalAmount);
						Vue.set(vue,"page",data.page);
						Vue.set(vue,"pageJump",data.page);
						Vue.set(vue,"totalPage",data.totalPage);
					},
					error:function(e){
						alert("获取信息异常");
					}
				});
				
				$.ajax({
					url:webRoot+'/sale/sale!getStatisticTotal.do',
					type:'post',
					data:param,
					success:function(data){
						Vue.set(vue,"totals",data);
					},
					error:function(e){
						alert("获取信息异常");
					}
				});
				
			}
		}
	});
	
	$("[name=sdate]").datepick();
	$("[name=edate]").datepick({
		showNowDate:true
	});
	
	getUserList('man',null,'name','name');
	
	$("#searchBar").buildform({
		actions: {
			sub() {
				var param = this.getParamMap();
				vue.fetchData(param);
			}
		}
	})
	
};
return module.exports;});