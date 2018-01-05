<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>事后监管统计</title>
	<%@ include file="/WEB-INF/views/backend/common/meta.jsp"%>
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/base.css" />
	<link rel="stylesheet" href="${ctx}/static/backend/xtbg/css/info-mgt.css" />
</head>
<body>
	<div id="container" style="height:5000px;margin-top:20px;"></div>
	<!-- <script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script> -->
	<script src="${ctx}/static/backend/js/highCharts/highcharts.js"></script>
	<script>
		var stationNameList = ${stationNameList};
		var stationIdList = ${stationIdList};
		var bookList = ${bookList};
		var realList = ${realList};
		var exceptionList = ${exceptionList};
		//用于HChart
		var dataLabelsObj = {
			enabled : true,
			align : 'right',
			color : '#FFFFFF',
			x : -10,
			formatter : function() {
				return this.y ? this.y : "";
			},
			style : {
				fontWeight : 'normal'
			}
		};

		// 图表配置
		var options = {
			chart : {
				type : 'bar' //指定图表的类型，默认是折线图（line）
			},
			title : {
				text : '事后监管统计' // 标题
			},
			xAxis : {
				categories : stationNameList, // x 轴分类
				labels : {
					useHTML:true,
					formatter : function() {
						var index = stationNameList.indexOf(this.value);
						var stationId = stationIdList[index];
						return '<a style="color:#0D7EC8;text-decoration:underline;" href="detail.do?stationId=' + stationId + '">' + this.value + '</a>';
					}
				}
			},
			yAxis : {
				allowDecimals : false,
				opposite : true,
				title : {
					enabled : false,
					text : '记录数量' // y 轴标题
				}
			},
			legend : {
				align : 'right',
				x : -30,
				verticalAlign : 'top',
				y : 0,
				floating : true,
				backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
						|| 'white',
				borderColor : '#CCC',
				borderWidth : 1,
				shadow : false
			},
			series : [ { // 数据列
				name : '预约数量', // 数据列名
				data : bookList, // 数据
				color : '#90ED7D',
				pointWidth : 15,
				dataLabels : dataLabelsObj,
				groupPadding : 0.1
			}, {
				name : '办理数量',
				data : realList,
				color : '#7CB5EC',
				pointWidth : 15,
				dataLabels : dataLabelsObj,
				groupPadding : 0.1
			}, {
				name : '异常数量',
				data : exceptionList,
				color : '#434348',
				pointWidth : 15,
				dataLabels : dataLabelsObj,
				groupPadding : 0.1
			} ]
		};
		// 图表初始化函数
		var chart = Highcharts.chart('container', options);
	</script>
</body>
</html>
