
$(function(){
	
	var target = $(".main_sec");
	
	var tabs = new BootstrapTabs({
		target:target
	});
	
	tabs.addTab({
		label:'未回复询价单',
		template:webRoot+'/purchasing/template_inquiry_to_reply.mvc',
		handler:function(){
			$("[name=startdate]").datepick();
			$("[name=enddate]").datepick({
				showNowDate:true
			});
			
			new Vue({
				el:'#dataTable',
				data:{
					rows:new Array(),
					totalAmount:0,
					page:1,
					pageToJump:2
				},
				created:function(){
					this.fetchData();
				},
				methods:{
					fetchData:function(){
						var self = this;
						$.ajax({
							url:webRoot+'/purchasing/purchasing!listInquiryToReply.do',
							data:{
								page:self.page
							}
						}).done(function(data){
							self.rows = data.rows;
							self.totalAmount = data.totalAmount;
							self.page = data.page;
							self.pageToJump = self.page+1;
						}).fail(function(e){
							console.error(e);
						})
					},
					getUrl:function(id){
						return "quote-view.jsp?id="+id;
					},
					jumpPage : function(){
						this.page = this.pageToJump;
						this.fetchData();
					},
					prevPage : function(){
						this.page--;
						this.fetchData();
					},
					nextPage : function(){
						this.page++;
						this.fetchData();
					}
				}
			})
		}
	});
	
	tabs.addTab({
		label:'询价单查询',
		template:webRoot+'/purchasing/template_inquiry_list.mvc',
		handler:function(){
			
			console.log("tab inside ",this);
			
			$("[name=startdate]").datepick();
			$("[name=enddate]").datepick({
				showNowDate:true
			});

			new Vue({
				el:'#dataTable2',
				data:{
					rows:new Array(),
					totalAmount:0,
					totalPage:0,
					page:1,
					pageToJump:2
				},
				created:function(){
					this.fetchData();
				},
				methods:{
					fetchData:function(){
						var self = this;
						$.ajax({
							url:webRoot+'/purchasing/purchasing!listInquiry.do',
							data:{
								page:self.page
							}
						}).done(function(data){
							self.rows = data.rows;
							self.totalAmount = data.totalAmount;
							self.page = data.page;
							self.totalPage = data.totalPage;
							self.pageToJump = self.page+1;
						}).fail(function(e){
							console.error(e);
						})
					},
					getUrl:function(id){
						return "dd-view.jsp?id="+id;
					},
					jumpPage : function(){
						this.page = this.pageToJump;
						this.fetchData();
					},
					prevPage : function(){
						this.page--;
						this.fetchData();
					},
					nextPage : function(){
						this.page++;
						this.fetchData();
					}
				}
			})
		}
	});
	tabs.init();
	
});


