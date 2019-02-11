
function goToThePage(url) {
	window.open(url, '_blank');
}

$(function() {
	$.ajax({
		url : "/oa_static/json/manage_items.json",
		success : function(data) {
			$.each(data, function(i, d) {
				var row = $("<div>");
				if (d.items) {
					$.each(d.items, function(i, d) {
						var but = $("<button>", {
							text : d.name,
							'class' : 'manageButton',
							url : d.url,
							click : function() {
								var url = $(this).attr("url");
								goToThePage(url);
							}
						});
						row.append(but);
					});
				} else {

					var hr = $("<div style='clear:both'><hr></div>");
					row.append(hr);
				}

				$("#manageBoard").append(row);
			});
		}
	});
});
