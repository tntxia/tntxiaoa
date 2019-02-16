
var allBanks;

$(function(){
	$(".editButton").click(function(){
		noteIt();
	});
	
	$(".printButton").click(function(){
		document.getElementById("bankInput").style.display = "none";
		document.getElementById("subs").style.display = "none";
		window.print();
	});
	
	$("#bankInput").change(function(e){
		let bankId = $(this).val();
		let bank;
		$.each(allBanks,function(i, b){
			if(bankId==b.id) {
				bank = b;
				return false;
			}
		});
		$("#banks .name").html(bank.bname);
		$("#banks .address").html(bank.ba);
		$("#banks .code").html(bank.sc);
		$("#banks .bname").html(bank.ben);
		$("#banks .ban").html(bank.an);
	})
});



function noteIt(){
	
	$.ajax({
		url:webRoot+'bank!listAll.do',
		success:function(data){
			allBanks = data;
			$("#bankInput").empty();
			
			for(var i=0;i<allBanks.length;i++){
				var bank = allBanks[i];
				$("#bankInput").append($("<option>",{
					text:bank.bn,
					value:bank.id
				}));
			}

			document.getElementById("bankInput").style.display = "";

			document.getElementById("subs").style.display = "";
		}
		
	});

	

}
