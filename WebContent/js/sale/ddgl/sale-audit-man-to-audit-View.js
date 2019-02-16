$(function(){
	
	new Vue({
		el:'#auditModal',
		data:{
			params:{
				opinion:null,
				state:'1'
			}
		},
		methods:{
			pass:function(){
				var data = this.params;
				data.id = $("[name=id]").val();
				$.ajax({
					url:webRoot+'/sale/sale!audit.do',
					data:data,
					success:function(data){
						
						if(data.success){
							alert("操作成功");
							if(window.opener){
								window.opener.location.reload();
							}
							window.close();
						}else{
							alert("操作失败");
						}
					},
					error:function(){
						alert("操作异常");
					}
				});
			}
		}
	});
	
	new Vue({
		el:'#auditSecondModal',
		data:{
			params:{
				opinion:null,
				state:'1'
			}
		},
		methods:{
			pass:function(){
				var data = this.params;
				data.id = $("[name=id]").val();
				$.ajax({
					url:webRoot+'/sale/sale!auditSecond.do',
					data:data,
					success:function(data){
						
						if(data.success){
							alert("操作成功");
							if(window.opener){
								window.opener.location.reload();
							}
							window.close();
						}else{
							alert("操作失败");
						}
					},
					error:function(){
						alert("操作异常");
					}
				});
			}
		}
	});
	
});

function toAudit(){
	$("#auditModal").modal('show');
}

function toAuditSecond(){
	$("#auditSecondModal").modal('show');
}