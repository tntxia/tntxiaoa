
$(function(){
	
	new Vue({
		el:'#changeRkDiv',
		data:{
			rows:[]
		},
		mounted:function(){
			this.fetchData();
		},
		methods:{
			fetchData:function(){
				var vm = this;
				var id = $("#id").val();
				$.ajax({
					url:webRoot+'/purchasing/purchasing!listRkPro.do',
					data:{
						id:id
					},
					success:function(data){
						vm.rows = data;
					}
					
				});
			},
			changeProRkNum:function(){
				var id = $("#id").val();
				var num = this.num;
				$.ajax({
					url:webRoot+'/purchasing/purchasing!updaeProRkNum.do',
					data:{
						id:id,
						num : num
					},
					success:function(data){
						if(data.success){
							alert("操作成功");
							window.opener.location.reload();
							window.close();
						}else{
							alert("操作失败！"+data.msg);
							
						}
					}
					
				});
			},
			changeRk:function(rk){
				$.ajax({
					url:webRoot+'/purchasing/purchasing!editInNum.do',
					data:{
						id:rk.id,
						
						num : rk.pro_num
					},
					success:function(data){
						if(data.success){
							alert("操作成功");
							window.opener.location.reload();
							window.close();
						}else{
							alert("操作失败！"+data.msg);
							
						}
					}
					
				});
			}
		}
		
	})
	
});

