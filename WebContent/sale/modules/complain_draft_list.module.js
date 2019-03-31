(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('complain_draft_list',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/complain/draft.html';
exports.init = function(){
	
	// 增加按钮
	$("#addBut").click(function(){
		
		window.open(webRoot+'/server/khts/new.mvc');
	});
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		url:webRoot+"/appeal!list.do",
		target:target,
		cols:[{
			label:'序号',
			type:'index'
		},{
			label:'投诉编号',
			field:'numeration',
			renderer:function(value,data){
				var a = $("<a>",{
					text:value,
					click:function(){
						window.open(webRoot+"/server/khts/khts-view.jsp?id="+data.aid);
					}
				});
				return a;
			}
		},{
			label:'投诉分类',
			field:'appealfl'
		},{
			label:'星级',
			field:'appealdj'
		},{
			label:'操作',
			renderer:function(value,row){
				let button =  $("<button>",{
					text:'删除',
					click:function(){
						let row = $(this).data("row");
						console.log(row);
						var vm = this;
						$.ajax({
							url:webRoot+"/appeal!del.do",
							data:{
								id:row.id
							},
							success:function(data){
								if(data.success){
									vm.fetchData();
								}else{
									alert("操作失败");
								}
							}
						});
					}
				});
				button.data("row",row);
				return button;
				
			}
		}]
	});
	grid.init();
	
};
return module.exports;});