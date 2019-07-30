(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('purchasing_supplier_shift',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let url = webRoot+"/purchasing/supplier!list.do";
	new Vue({
		el: '#app',
		data: {
			form: {
				man1: '',
				coname: null,
				man2: '',
				share: 'Y'
			}
		},
		mounted() {
		},
		methods: {
			sub() {
				var param = this.form;
				if (!param.man1 || !param.man2) {
					alert("请选择你要转移的用户");
					return;
				}
				$.ajax({
					url:basePath+"purchasing/supplier.dispatch",
					data:param,
					success:function(data){
						if(data.success){
							alert("操作成功");
						}else{
							alert("操作失败！"+data.msg);
						}
					},
					error:function(){
						alert("请求后台服务失败");
					}
				});
			}
		}
	});
	
};
return module.exports;});