$(function(){
	$("body").contextPopup({
        title: '菜单',
        items: [
          {label:'打印', icon:webRoot+'/images/icons/printer.png',action:function() { printIt(); } }
        ]
    });
});