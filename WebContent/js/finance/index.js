$(function(){
	
	// 绑定路由，设置路由变化影响的区域
	router.register({
		target:$(".main_sec"),
		defaultModule:'finance_gathering'
	});
});