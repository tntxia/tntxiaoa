
function initDatetime(){
	var today=new Date;
	var week=new Array(7);
	week[0]="天";
	week[1]="一";
	week[2]="二";
	week[3]="三";
	week[4]="四";
	week[5]="五";
	week[6]="六";
	$("#timeDiv").html("今天是:"+today.getFullYear()+"年"+(today.getMonth()+1)+"月"+today.getDate()+"日  星期"+week[today.getDay()]);
}

$(function(){
	let currLeftbar;
	initDatetime();
	router.register({
		target:$(".main_sec"),
		mapping: {
			"warehouse_manage": {
				path: webRoot + "/warehouse/modules/warehouse_manage.module.js",
				template: webRoot + "/warehouse/template/warehouse_manage.html"
			},
			"warehouse_in_manage": {
				path: webRoot + "/warehouse/modules/warehouse_in_manage.module.js",
				template: webRoot + "/warehouse/template/warehouse_in_manage.html"
			}
		},
		onChange(module) {
			let type = module.split("_")[0];
			if (type === currLeftbar) {
				$(".leftbar li").removeClass("selected");
				$(".leftbar li").each(function() {
					if ($(this).find("a").attr("href").indexOf(module) >= 0) {
						$(this).addClass("selected");
					}
				});
				return;
			}
			
			let leftbar = $(".leftbar");
			leftbar.empty();
			$.ajax({
				url: 'leftbar.do',
				data: {
					type: type
				}
			}).done(res=> {
				currLeftbar = type;
				res.forEach(item=> {
					let leftbarItem = $("<div>", {
						"class": "leftbar-item"
					});
					let itemHead = $("<div>", {
						'class': 'leftbar-header',
						text: item.text
					});
					leftbarItem.append(itemHead);
					
					let itemList = $("<ul>", {
						'class': 'leftbar-list'
					});
					leftbarItem.append(itemList);
					let buttons = item.buttons;
					buttons.forEach(button=> {
						let li = $("<li>");
						if (button.url.indexOf(module)>=0) {
							li.addClass("selected");
						}
						itemList.append(li);
						let a = $("<a>", {
							href: webRoot + "/" + button.url,
							text: button.text
						})
						li.append(a);
						
					})
					leftbar.append(leftbarItem);
				});
				console.log(res);
			})
		}
	});
	
		
});

function showToDo(){
	
	$("#toDoModal").modal('show');
}


