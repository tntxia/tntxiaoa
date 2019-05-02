(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('assay_sale_ranking_product',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/assay_sale_ranking_product.html';
exports.init = function(){
	
	var dataAxis = [];
	
	var chartdata = [];
	
	var option = {
	    title: {
	        text: '产品销售情况统计',
	        subtext: ''
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    xAxis: {
	        data: dataAxis,
	    
	        axisTick: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        z: 10
	    },
	    yAxis: {
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#999'
	            }
	        }
	    },
	    series: [
	  
	        {
	            type: 'bar',
	            barWidth:30,
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#83bff6'},
	                            {offset: 0.5, color: '#188df0'},
	                            {offset: 1, color: '#188df0'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#2378f7'},
	                            {offset: 0.7, color: '#2378f7'},
	                            {offset: 1, color: '#83bff6'}
	                        ]
	                    )
	                }
	            },
	            data: chartdata
	        }
	    ]
	};
	
	var myChart = echarts.init(document.getElementById('diagram'));
	
	$("#form").buildform({
		actions:{
			search:function(){
				var params = this.getParamMap();
				
				if(!params.startdate){
					alert("请填写统计开始时间！");
					return;
				}
				
				if(!params.enddate){
					alert("请填写统计结束时间");
					return;
				}
				
				$.ajax({
					url:webRoot+'/assay/assay!getProductSaleStatist.do',
					type:'post',
					data:params
				}).done(function(data){
					
					data.sort(function(a,b){
						
						if(a.num > b.num){
							return -1;
						}else if(a.num==b.num){
							return 0;
						}else{
							return 1;
						}
						
					})
					
					$.each(data,function(i,d){
						dataAxis.push(d.epro);
						chartdata.push(d.num);
					});
					console.log("option",option);
					myChart.setOption(option);
					console.log(data);
				})
			}
		}
	});

	
	
	
	
};
return module.exports;});